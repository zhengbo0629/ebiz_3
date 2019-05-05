package com.ebiz.controller;

import com.ebiz.common.ebizEnum.EbizCompanyAddressEnum;
import com.ebiz.common.ebizEnum.EbizPackagePayStatusEnum;
import com.ebiz.common.ebizEnum.EbizPackageStatusEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.PackageList;
import com.ebiz.model.ProductList;
import com.ebiz.service.PackageListService;
import com.ebiz.service.ProductListService;
import com.ebiz.utils.GeneralMethod;
import com.ebiz.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 任务管理
 *  领票、预报
 *  求收购、议价
 *  更新包裹信息
 *  更新信用卡信息
 *  打包包裹
 *  正在收购
 */




@Controller
@RequestMapping("/taskManager")
public class TaskManagerController {

    @Autowired
    private ProductListService productListService;

    @Autowired
    private PackageListService packageListService;

    //-------------------------------------领票或预报--------------start---------------------
    //跳转到领票或预报页面
    @RequestMapping("/readAllActiveAndAliveDealProductsToReportPackage")
    public String readAllActiveAndAliveDealProductsToReportPackage(HttpServletRequest request , Model model){
        List<ProductList> products=new ArrayList<>();
        //获取当前用户的所属的公司
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = company.getCompanyName();
        products = productListService.searchAllActiveAndAliveDealProducts(companyName);
        model.addAttribute("productLists" , products);
        return "EbizTask/reportPackage";
    }

    //在领票或预报页面执行提交操作
    @RequestMapping("/reportPackage")
    @ResponseBody
    public List<String> reportPackage(HttpServletRequest request ){
        String productString = (String) request.getParameter("product");
        String[] productArray = productString.split(",");
        int productUID = Integer.parseInt(productArray[0]);
        // int productPersonalLimit = Integer.parseInt(productArray[2]);
        int reportingQuantity = Integer.parseInt(request.getParameter("quantity"));
        int ticket = Integer.parseInt(request.getParameter("ticket"));
        String creditCard = (String) request.getParameter("CreditCardNumber");
        String address = (String) request.getParameter("address");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String username = currentUser.getUserName();
        List<String> info = new ArrayList<String>();
        if (productUID == 0) {
            info.add("Please Add A Product");
        } else {
            ProductList selectedProduct = productListService.findProduct(productUID);
            if (selectedProduct == null) {
                //如果没有相关商品
                info.add("Can Not Find Product In DataBase");
            } else if (selectedProduct.getTickets() == 0) {
                //如果存在该商品，但是票没有票了
                info.add("No unit is needed, please try other product");
            } else if (reportingQuantity == 0) {
                //填写的要领取的票的数量为0
                info.add("Can not report 0 units, Please try again");
            } else {
                String modelNumber = selectedProduct.getModel();

                if (reportingQuantity > ticket) {
                    //如果当前用户想要的数量大于允许的最大量
                    info.add("You only have " + ticket + " tickets left, please contact administrator to get more");
                } else if (selectedProduct.getTickets() >= reportingQuantity) {
                    //如果没什么问题，可以进行领票了
                    int leftTickets = selectedProduct.getTickets() - reportingQuantity;//如果正常领取之后还剩这么些张票
                    //然后更新该商品的可以领取票的数量等操作
                    if (!productListService.updateProductTicket(currentUser ,currentCompany ,selectedProduct.getId(),leftTickets)) {
                        //如果更新失败
                        info.add("Please check you connection or Try Again");
                    } else {
                        //领取成功
                        selectedProduct.setTickets(leftTickets);
                        String timestring = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
                        double price = selectedProduct.getPrice();
                        int promQuantity = 0;
                        double basePrice = 0;
                        double promPrice = 0;

                        if (EbizCompanyAddressEnum.isCompanyAddress(address)) {
                            promQuantity = selectedProduct.getWarehousePromotQuantity();
                            promPrice = selectedProduct.getWarehousePromotePrice();
                            basePrice = selectedProduct.getWarehousePrice();

                        } else {
                            promQuantity = selectedProduct.getPromotQuantity();
                            promPrice = selectedProduct.getPromotPrice();
                            basePrice = selectedProduct.getPrice();
                        }
                        if (reportingQuantity >= promQuantity) {
                            //如果领取的票数大于某个限额，就打折
                            price = promPrice;
                        } else {
                            price = basePrice;
                        }
                        String email = currentUser.getEmail();
                        String paystatus=EbizPackagePayStatusEnum.UnPaid.getName();
                        PackageList tempPackage = new PackageList();
                        tempPackage.setCompanyName(selectedProduct.getCompanyName());
                        tempPackage.setModelNumber(modelNumber);
                        tempPackage.setProductName(selectedProduct.getProductName());
                        tempPackage.setUPC(selectedProduct.getUPC());
                        tempPackage.setASIN(selectedProduct.getASIN());
                        tempPackage.setSKU(selectedProduct.getSKU());
                        tempPackage.setBrand(selectedProduct.getBrand());
                        tempPackage.setPrice(price);
                        tempPackage.setBasePrice(basePrice);
                        tempPackage.setPromPrice(promPrice);
                        tempPackage.setQuantity(reportingQuantity);
                        tempPackage.setPromQuantity(promQuantity);
                        tempPackage.setUserName(username);
                        tempPackage.setShippingAddress(address);
                        tempPackage.setEmail(email);
                        tempPackage.setPhoneNumber(currentUser.getPhoneNumber());
                        tempPackage.setUpdateTime(timestring);
                        tempPackage.setCreatedTime(timestring);
                        tempPackage.setCreditCardNumber(creditCard);
                        tempPackage.setStatus("UnReceived");
                        tempPackage.setPayStatus(paystatus);
                        //生成新的包裹
                        int flag = productListService.addPackage(currentUser,currentCompany,tempPackage);
                        if(flag > 0){
                            //保存成功     成功生成新的包裹

                        }else{
                            //保存失败      生成新的包裹失败
                            info.add("生成新的包裹失败, Please lower the quantity and Try Again");
                        }
                    }

                } else {
                    info.add("Do not have enough Tickets Left, Please lower the quantity and Try Again");
                }
            }
        }

        return info;
    }

    //-------------------------------------领票或预报--------------end---------------------








    //-------------------------------------求收购、议价--------------start---------------------

    //跳转到领票或预报页面
    @RequestMapping("/readAllActiveAndAliveDealProductsToSellorobo")
    public String readAllActiveAndAliveDealProductsToSellorobo(HttpServletRequest request , Model model){
        List<ProductList> products=new ArrayList<>();
        //获取当前用户的所属的公司
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        System.out.println(company);
        System.out.println("公司地址："+ company.getAddressName1()+" : "+company.getAddressDetail1());
        System.out.println("公司id："+ company.getId());
        System.out.println("公司名称："+ company.getCompanyName());
        String companyName = company.getCompanyName();
        products = productListService.searchAllActiveAndAliveDealProducts(companyName);

        model.addAttribute("productLists" , products);
        return "EbizTask/sellorobo";
    }

    //进入到收购、议价页面点击提交操作，执行感该方法
    @RequestMapping("/sellOrOBO")
    @ResponseBody
    public String sellOrOBO(HttpServletRequest request){
        String productModel = (String) request.getParameter("productModel").trim();

        String productName = (String) request.getParameter("productBrand").replace(";", "").replace(",", "").trim();

        String productBrand = (String) request.getParameter("productBrand").trim();
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        double price = Double.parseDouble(request.getParameter("price"));
        String address = request.getParameter("address");
        String creditCard = (String) request.getParameter("CreditCardNumber").trim();
        String status[] = request.getParameterValues("checkbox");
        String statusString = "";
        for (int i = 0; i < status.length; i++) {
            System.out.println(status[i]);
            statusString = status[i];
        }
        if (statusString.equals("orderUnplaced")) {
            statusString = EbizPackageStatusEnum.UnConfirmedUnPlaced.getColumnName();
        } else if (statusString.equals("orderPlaced")) {
            statusString = EbizPackageStatusEnum.UnConfirmedUnReceived.getColumnName();
        } else if (statusString.equals("instock")) {
            statusString = EbizPackageStatusEnum.UnConfirmedInStock.getColumnName();
        }

        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String username = currentUser.getUserName();

        String timestring = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
        int promQuantity = 0;
        double basePrice = 0;
        double promPrice = 0;

        promQuantity = 0;
        promPrice = price;
        basePrice = price;

        String email = currentUser.getEmail();
        String paystatus = EbizPackagePayStatusEnum.UnPaid.getName();

        String uidString = request.getParameter("packageId");

        if (uidString != null) {
            // means update existing package
            int uid = Integer.parseInt(uidString);
            PackageList pack = packageListService.findPackage(uid);
            boolean update = false;
            String message = "";
            if (pack.getStatus().toLowerCase().contains("unconfirmed")) {
                if (!pack.getModelNumber().equals(productModel)) {

                    update = packageListService.updatePackageModel(currentUser,currentCompany,pack.getId(), productModel);
                    if (update) {
                        pack.setModelNumber(productModel);
                        message = message + "ProductModel Update Sucessfully \n";
                    } else {
                        message = message + "ProductModel Update Failed,Please Try Again \n";
                    }
                }
                if (!pack.getBrand().equals(productBrand)) {
                    update = packageListService.updatePackageBrand(currentUser,currentCompany,pack.getId(), productBrand);
                    if (update) {
                        pack.setBrand(productBrand);
                        message = message + "Product Brand Update Sucessfully \n";
                    } else {
                        message = message + "Product Brand Update Failed,Please Try Again \n";
                    }
                }
                if (!pack.getProductName().equals(productName)) {
                    update = packageListService.updatePackageName(currentUser,currentCompany,pack.getId(), productName);
                    if (update) {
                        pack.setProductName(productName);
                        message = message + "Product Name Update Sucessfully \n";
                    } else {
                        message = message + "Product Name Update Failed,Please Try Again \n";
                    }
                }
                if (pack.getQuantity() != quantity) {
                    update = packageListService.updatePackageQuantity(currentUser,currentCompany,pack.getId(), quantity);
                    if (update) {
                        pack.setQuantity(quantity);
                        message = message + "Product Quantity Update Sucessfully \n";
                    } else {
                        message = message + "Product Quantity Update Failed,Please Try Again \n";
                    }
                }
                if (pack.getPrice() != price) {
                    update = packageListService.updatePackagePrice(currentUser,currentCompany,pack.getId(), price);
                    if (update) {
                        pack.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } else {
                        message = message + "Product Price Update Failed,Please Try Again \n";
                    }
                }
                if (!pack.getCreditCardNumber().equals(creditCard)) {
                    update = packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(), creditCard);
                    if (update) {
                        pack.setCreditCardNumber(creditCard);
                        message = message + "CreditCard Information Update Sucessfully \n";
                    } else {
                        message = message + "CreditCard Information Update Failed,Please Try Again \n";
                    }
                }
                if (!pack.getShippingAddress().equals(address)) {
                    update = packageListService.updatePackageAddress(currentUser,currentCompany,pack.getId(), address);
                    if (update) {
                        pack.setShippingAddress(address);
                        message = message + "Address Update Sucessfully \n";
                    } else {
                        message = message + "Address Update Failed,Please Try Again \n";
                    }
                }
                if (!pack.getStatus().equals(statusString)) {
                    update = packageListService.updatePackageStatus(currentUser,currentCompany,pack.getId(), statusString);
                    if (update) {
                        pack.setStatus(statusString);
                        message = message + "Status Update Sucessfully \n";
                    } else {
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                }
                if (message.length() == 0) {
                    message = "Nothing Updated";
                }
            } else {
                message = message
                        + "Package has been confirmed, you can not update it here, please update it through other way; \n";
            }
            return message;
        } else {
            // create a new package
            PackageList tempPackage = new PackageList();
            tempPackage.setCompanyName(currentUser.getCompanyName());
            tempPackage.setModelNumber(productModel);
            tempPackage.setProductName(productName);
            tempPackage.setBrand(productBrand);
            tempPackage.setPrice(price);
            tempPackage.setBasePrice(basePrice);
            tempPackage.setPromPrice(promPrice);
            tempPackage.setQuantity(quantity);
            tempPackage.setPromQuantity(promQuantity);
            tempPackage.setUserName(username);
            tempPackage.setShippingAddress(address);
            tempPackage.setEmail(email);
            tempPackage.setPhoneNumber(currentUser.getPhoneNumber());
            tempPackage.setUpdateTime(timestring);
            tempPackage.setCreatedTime(timestring);
            tempPackage.setCreditCardNumber(creditCard);
            tempPackage.setStatus(statusString);
            tempPackage.setPayStatus(paystatus);
            System.out.println(tempPackage);
            if (productListService.addPackage(currentUser,currentCompany,tempPackage) > 0) {
                return "Your Package Has Been Added Sucessfully";
            } else {
                return "Your Package Has Not Been Added, Please Try Again";
            }
        }
    }


    //-------------------------------------求收购、议价--------------end---------------------


    //-------------------------------------更新包裹信息--------------start---------------------

    @RequestMapping("/readUnReceivedAndUnConfirmedSellOrOBOPackagesForUser")
    public String readUnReceivedAndUnConfirmedSellOrOBOPackagesForUser(HttpServletRequest request , Model model){
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
        List<ProductList > productLists = new ArrayList<>();

        Map<String , Object > map = productListService.searchUnReceivedAndUnConfirmedSellOrOBOPackSetForUser(currentUser , currentPage , pageSize);
        productLists = ( List<ProductList >)map.get("data");
        model.addAttribute("listmap" ,productLists);
        ProductListVo bo = new ProductListVo();
        bo.setCurrentPage(currentPage);
        bo.setPageSize(pageSize);
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        EbizCompanyAddressEnum []  EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("ebizCompanyAddressEnums" , EbizCompanyAddressEnums);
        return "EbizTask/unReceivedPacAndUnConfirmed";
    }

    //在未发货和未确认包裹(UnReceived And Unconfirmed)执行更新操作   分为两种，一种是地址在enum中的，一种是不再在num中的
    //1、跳转到包含的中
    @RequestMapping("/toEditWareHousePackagePage")
    public String toEditWareHousePackagePage(HttpServletRequest request , Model model){
        String packageListId= request.getParameter("packageListId");
        PackageList packageList = packageListService.findPackage(Integer.parseInt(packageListId));
        model.addAttribute("pack" , packageList);
        return "EbizTask/editWareHousePackage";
    }

    //2、执行更新操作
    @RequestMapping("/editWareHousePackage")
    @ResponseBody
    public String editWareHousePackage(HttpServletRequest request){
        int quantity =Integer.parseInt(request.getParameter("quantity"));
        String creditcard = (String) request.getParameter("creditCardNumber");
        int uid = Integer.parseInt( request.getParameter("id"));
        PackageList pack=packageListService.findPackage(uid);
        String allTrackingNumber= (String) request.getParameter("trackingnumber");
        int totalQuantity=Integer.parseInt( request.getParameter("totalQuantity"));
        double basePrice=Double.parseDouble(request.getParameter("basePrice"));
        double promPrice=Double.parseDouble( request.getParameter("promPrice"));
        int promQuantity=Integer.parseInt(request.getParameter("promQuantity"));
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        double price=basePrice;
        if (quantity>=promQuantity){
            price=promPrice;
        }
        //没有填写tracking，只是更新数量或者信用卡；
        if(totalQuantity==0||allTrackingNumber==null||allTrackingNumber.length()<=5){
            boolean update=false;
            String message="";
            if(pack.getQuantity()!=quantity){
                update=packageListService.updatePackageQuantityAndPrice(currentUser,currentCompany,pack.getId(),quantity,price);
                if(update){
                    pack.setQuantity(quantity);
                    pack.setPrice(price);
                    message=message+"Quantity Update Sucessfully \n";
                }else{
                    message=message+"Quantity Update Failed, Please Try Again \n";
                }
            }
            if(pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditcard))){
                update=packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(),creditcard);
                if(update){
                    pack.setCreditCardNumber(creditcard);
                    message=message+"CreditCard Information Update Sucessfully \n";
                }else{
                    message=message+"CreditCard Information Update Failed, Please Try Again \n";
                }
            }
            if(message.length()>0){
                return message;
            }else{
                return "No Need Update";
            }
        }else{
            //check tracking
            List<String> badTrackingList=new ArrayList<>();
            String[] trackAndQuantity=allTrackingNumber.split("\\?");
            for(int i=0;i<trackAndQuantity.length;i++){
                String oneTrackingString=trackAndQuantity[i].split("_")[0];

                int count=count(allTrackingNumber,oneTrackingString);
                if (count>1){
                    badTrackingList.add(oneTrackingString);
                    continue;
                }
                List<PackageList> packagesList=packageListService.readPackagesByTracking(oneTrackingString);
                for(int j=0;j<packagesList.size();j++){
                    //only the same user with different product can have same tracking;
                    if(!packagesList.get(j).getUserName().equals(pack.getUserName())||packagesList.get(j).getModelNumber().equals(pack.getModelNumber())){
                        // this tracking has been reported by other user or has been reported befor for the same product;
                        badTrackingList.add(oneTrackingString);
                    }
                }
            }
            if(badTrackingList.size()!=0){
                //返回客户端结果
                String result = getResponseResult(200,badTrackingList);
                //将result返回客户端
                return result;
            }else{
                if(pack.getQuantity()!=quantity){
                    if(packageListService.updatePackageQuantityAndPrice(currentUser,currentCompany,pack.getId(),quantity,price)){
                        pack.setQuantity(quantity);
                        pack.setPrice(price);
                    }
                }
                if(pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditcard))){
                    if(packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(),creditcard)){
                        pack.setCreditCardNumber(creditcard);
                    }
                }
                pack.setTrackingNumber(allTrackingNumber);
                pack.setStatus(EbizPackageStatusEnum.Shipped.getColumnName());
                packageListService.updatePackageTrackingAndStatus(pack);
                return "";
            }
        }
    }

    public static String getResponseResult(int status,List<String> message){
        String re="";
        for (int i=0;i<message.size();i++){
            re=re+message.get(i)+"\n";
        }
        re=re+"Above tracking have been reported, Please check or contact admistrator";
        return  re;
    }


    //3、跳转到不包含的中
    @RequestMapping("/toEditUnreceivedPackagePage")
    public String toEditUnreceivedPackagePage(HttpServletRequest request , Model model){
        String packageListId= request.getParameter("packageListId");
        PackageList packageList = packageListService.findPackage(Integer.parseInt(packageListId));
        model.addAttribute("pack" , packageList);
        return "EbizTask/editUnreceivedPackage";
    }

    //4、执行更新操作
    @RequestMapping("/editUnreceivedPackage")
    @ResponseBody
    public String editUnreceivedPackage(HttpServletRequest request ){
        int quantity =Integer.parseInt(request.getParameter("quantity"));
        String creditcard = (String) request.getParameter("creditcard");
        int uid = Integer.parseInt( request.getParameter("id"));
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        PackageList pack=packageListService.findPackage(uid);
        boolean myCheckBox = request.getParameter( "InStock" ) != null;
        String statusString="";
        if(myCheckBox){
            statusString=EbizPackageStatusEnum.InStock.getColumnName();
        }else{
            statusString=EbizPackageStatusEnum.UnReceived.getColumnName();
        }
        double basePrice=Double.parseDouble(request.getParameter("basePrice"));
        double promPrice=Double.parseDouble( request.getParameter("promPrice"));
        int promQuantity=Integer.parseInt(request.getParameter("promQuantity"));
        double price=basePrice;
        if (quantity>=promQuantity){
            price=promPrice;
        }
        boolean update=false;
        String message="";
        if(pack.getQuantity()!=quantity){

            update=packageListService.updatePackageQuantityAndPrice(currentUser,currentCompany,pack.getId(),quantity,price);
            if(update){
                pack.setQuantity(quantity);
                pack.setPrice(price);
                message=message+"Quantity Update Sucessfully \n";
            }else{
                message=message+"Quantity Update Failed,Please Try Again \n";
            }
        }
        if(!pack.getCreditCardNumber().equals(creditcard)){

            update=packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(),creditcard);
            if(update){
                pack.setCreditCardNumber(creditcard);
                message=message+"CreditCard Information Update Sucessfully \n";
            }else{
                message=message+"CreditCard Information Update Failed,Please Try Again \n";
            }
        }
        if(!pack.getStatus().equals(statusString)&&statusString.equals(EbizPackageStatusEnum.InStock.getColumnName())){

            update=packageListService.updatePackageStatus(currentUser,currentCompany,pack.getId(),statusString);
            if(update){
                pack.setStatus(statusString);
                message=message+"Status Update Sucessfully \n";
            }else{
                message=message+"Status Update Failed,Please Try Again \n";
            }
        }
        if(message.length()>0){
            return message;
        }else{
            return "Nothing Updated";
        }
    }

    //在未发货和未确认包裹(UnReceived And Unconfirmed)执行删除操作
    @RequestMapping("/deletNursePack")
    public String deletNursePack(HttpServletRequest request){
        int uid = Integer.parseInt(request.getParameter("deletPackage"));
        PackageList pack = packageListService.findPackage(uid);
        EbizCompany currentCompany=(EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser=(EbizUser) request.getSession().getAttribute("currentUser");
        ProductList product=productListService.findProduct(currentCompany.getCompanyName(), pack.getModelNumber());
        int newQuantity=product.getTickets()+pack.getQuantity();
        if (productListService.deletePackage(currentUser,currentCompany,uid)) {
            //tickets returnd back to product list
            productListService.updateProductTicket(currentUser,currentCompany,product.getId(), newQuantity);
            //email both here
            return "Your Package Has Been Deleted Sucessfully";
        }else{
            return "Your Package Has Not Been Deleted, Please Try Again";
        }
    }


    public static int count(String s, String key) {
        int count=0;
        int d=0;
        while((d=s.indexOf(key,d))!=-1){
            s=s.substring(d+key.length());
            count++;
        }
        return count;
    }

    //-------------------------------------更新包裹信息--------------end---------------------





    //-------------------------------------更新信用卡信息--------------start---------------------
    //转到更新信用卡信息页面
    @RequestMapping("/readUnpaidPackagesForUser")
    public String readUnpaidPackagesForUser(HttpServletRequest request , Model model){
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        if(pageSizeStr == null){
            pageSizeStr = "10";
        }

        if(currentPageStr == null ){
            currentPageStr = "1";
        }
        Integer pageSize = Integer.parseInt(pageSizeStr);
        Integer currentPage = Integer.parseInt(currentPageStr);
        if(pageSize == null){

        }
        List<ProductList > productLists = new ArrayList<>();

        Map<String , Object > map = productListService.searchUnpaidSetForUser(currentUser , currentPage , pageSize);
        productLists = ( List<ProductList >)map.get("data");
        model.addAttribute("listmap" ,productLists);
        ProductListVo bo = new ProductListVo();
        bo.setCurrentPage(currentPage);
        bo.setPageSize(pageSize);
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizTask/unPaidPac";
    }

    //去单个更新页面
    @RequestMapping("/toUpdateCreditCardPage")
    public String toUpdateCreditCardPage(HttpServletRequest request , Model model){
        int uid = Integer.parseInt(request.getParameter("packageId"));
        PackageList pack = packageListService.findPackage(uid);
        model.addAttribute("pack" , pack);
        return "EbizTask/updateCreditCard";
    }

    //执单个行更新操作
    @RequestMapping("/editUnreceivedPack")
    @ResponseBody
    public  String editUnreceivedPack(HttpServletRequest request){
        int quantity =Integer.parseInt(request.getParameter("quantity"));
        String creditcard = (String) request.getParameter("creditcard");
        int uid = Integer.parseInt( request.getParameter("packageId"));
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        PackageList pack=packageListService.findPackage(uid);
        boolean myCheckBox = request.getParameter( "InStock" ) != null;
        String statusString="";
        if(myCheckBox){
            statusString=EbizPackageStatusEnum.InStock.getColumnName();
        }else{
            statusString=EbizPackageStatusEnum.UnReceived.getColumnName();
        }
        double basePrice=Double.parseDouble(request.getParameter("basePrice"));
        double promPrice=Double.parseDouble( request.getParameter("promPrice"));
        int promQuantity=Integer.parseInt(request.getParameter("promQuantity"));
        double price=basePrice;
        if (quantity>=promQuantity){
            price=promPrice;
        }
        boolean update=false;
        String message="";
        if(pack.getQuantity() == null || (pack.getQuantity() != null && pack.getQuantity()!=quantity)){
            update=packageListService.updatePackageQuantityAndPrice(currentUser,currentCompany,pack.getId(),quantity,price);
            if(update){
                pack.setQuantity(quantity);
                pack.setPrice(price);
                message=message+"Quantity Update Sucessfully \n";
            }else{
                message=message+"Quantity Update Failed,Please Try Again \n";
            }
        }
        if(pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditcard))){

            update=packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(),creditcard);
            if(update){
                pack.setCreditCardNumber(creditcard);
                message=message+"CreditCard Information Update Sucessfully \n";
            }else{
                message=message+"CreditCard Information Update Failed,Please Try Again \n";
            }
        }
        if((pack.getStatus() == null || (pack.getStatus() != null && !pack.getStatus().equals(statusString)))&&EbizPackageStatusEnum.InStock.getColumnName().equals(statusString)){
            update=packageListService.updatePackageStatus(currentUser,currentCompany,pack.getId(),statusString);
            if(update){
                pack.setStatus(statusString);
                message=message+"Status Update Sucessfully \n";
            }else{
                message=message+"Status Update Failed,Please Try Again \n";
            }
        }
        if(message.length()>0){
            return message;
        }else{
            return "Nothing Updated";
        }
    }


    //跳转到批量更新页面
    @RequestMapping("/toUpdateCreditCardForPackagesPage")
    public String toUpdateCreditCardForPackagesPage(HttpServletRequest request){
        String uidStrings = (String) request.getParameter("packageIds");
        if(uidStrings.endsWith(";")){
            uidStrings=uidStrings.substring(0,uidStrings.length()-1);
        }
        String[] uidPares=uidStrings.split(";");
        String newUidStrings="";

        double totalValue=0;
        for (int i=0;i<uidPares.length;i++){
            String[] temp=uidPares[i].split(",");
            String uid=temp[0];
            int quantity=Integer.parseInt(temp[1]);
            double price=Double.parseDouble(temp[2]);
            newUidStrings=newUidStrings+uid+";";
            totalValue=totalValue+quantity*price;
        }
        request.setAttribute("newUidStrings" , "newUidStrings");
        request.setAttribute("totalValue" , "totalValue");
        return "/EbizTask/updateCreditCardForPackages";
    }

    //执行批量更新操作
    @RequestMapping("/updateCreditCardForPackages")
    @ResponseBody
    public String updateCreditCardForPackages(HttpServletRequest request){
        String message="";
        int failednumber=0;
        String uIDsString = (String) request.getParameter("unPaidPackagesUid");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        if(uIDsString.endsWith(";")){
            uIDsString=uIDsString.substring(0,uIDsString.length()-1);
        }
        String[] uidStrings=uIDsString.split(";");
        ArrayList<Integer> UIDList=new ArrayList<>();
        for(int i=0;i<uidStrings.length;i++){
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        Collections.sort(UIDList);
        Collections.reverse(UIDList);
        String creditcardInform = (String) request.getParameter("creditcard");
        int maxuid=0;
        for(int i=0;i<UIDList.size();i++){
            if(maxuid==0){
                maxuid=UIDList.get(i);
            }else{
                creditcardInform="Same As UID "+maxuid;
            }
            boolean update=packageListService.updatePackageCreditCard(currentUser,currentCompany,UIDList.get(i),creditcardInform);
            if(update){
                message=message+"UID "+UIDList.get(i)+" Update Sucessfully \n";
            }else{
                message=message+"UID "+UIDList.get(i)+" Update Failed \n";
                failednumber++;
            }

        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button)";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button)";
            }
        }
        return message;
    }




    //-------------------------------------更新信用卡信息--------------end---------------------



    //-------------------------------------打包包裹--------------start---------------------
    @RequestMapping("/readPackagesForUser")
    public String readPackagesForUser(HttpServletRequest request , Model model){
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
        List<ProductList > productLists = new ArrayList<>();

        Map<String , Object > map = productListService.searchSetForUser(currentUser , currentPage , pageSize);
        productLists = ( List<ProductList >)map.get("data");
        model.addAttribute("listmap" ,productLists);
        ProductListVo bo = new ProductListVo();
        bo.setCurrentPage(currentPage);
        bo.setPageSize(pageSize);
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizTask/packingPac";
    }

    //-------------------------------------打包包裹--------------end---------------------



    //-------------------------------------正在收购--------------start---------------------

    //去往正在收购页面
    @RequestMapping("/readAllLiveDealProducs")
    public  String readAllLiveDealProducs(HttpServletRequest request , Model model){
        List<ProductList> list = new ArrayList<>();
        //获取当前用户的所属的公司
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = company.getCompanyName();
        list = productListService.searchSet(companyName,EbizStatusEnum.LiveDeal.getName());
        model.addAttribute("listmap" , list);
        return "EbizTask/livingDeal";
    }

    //-------------------------------------正在收购--------------end---------------------





}
