package com.ebiz.controller;


import com.ebiz.bo.PackageListBo;
import com.ebiz.common.Constant;
import com.ebiz.common.ebizEnum.*;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.PackageList;
import com.ebiz.model.ProductList;
import com.ebiz.service.DealListService;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.PackageListService;
import com.ebiz.service.ProductListService;
import com.ebiz.utils.EmailSenderCenter;
import com.ebiz.utils.GeneralMethod;
import com.ebiz.utils.Pagination;
import com.ebiz.vo.EmailLabelVo;
import com.ebiz.vo.PackageVo;
import com.ebiz.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * 公司管理
 */

@Controller
@RequestMapping("/companyManager")
public class CompanyManagementController {

    @Autowired
    private PackageListService packageListService;

    @Autowired
    private ProductListService productListService;

    @Autowired
    private EBizUserService eBizUserService;


    @Autowired
    private DealListService dealListService ;


    //-----------------------------------产品管理----------------------start------------------------------------
    //产品管理页面需要的数据和库存管理页面的数据是一样的，所以接口都是相同的，为我们减轻了不少的麻烦
    @RequestMapping("/toAllProductManagePage")
    public String toallProductManagePage(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = currentCompany.getCompanyName();
        List<ProductList> list = new ArrayList<>();
        Map<String , Object > map = packageListService.searchAllNonDeletedProductSet(currentCompany , currentPage , pageSize);
        list = (List<ProductList>) map.get("data");
        for(ProductList productList : list){
            System.out.println("productList : " + productList.getModel());
        }
        model.addAttribute("listmap" ,list);
        //分页信息
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(Integer.parseInt(currentPage));
        pagination.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        pagination.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ Integer.parseInt(pageSize));
        pagination.setTotalPage(totalPage.intValue());
        model.addAttribute("pagination" , pagination);
        ProductListVo vo = new ProductListVo();
        vo.setCurrentPage(Integer.parseInt(currentPage));
        vo.setPageSize(Integer.parseInt(pageSize));
        vo.setTotalCount(total);
        vo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , vo);
        return "EbizCompanyProducts/allProductManage.html";
    }



    //去往添加产品信息的页面       addOrEditProduct.html
    @RequestMapping("/toAddProductPage")
    public String toAddProductPage(HttpServletRequest request , Model model){
        //需要查找所有的EbizStatusEnum
        EbizStatusEnum [] EbizStatusEnums = EbizStatusEnum.values();
        model.addAttribute("EbizStatusEnums" , EbizStatusEnums);
        return "EbizCompanyProducts/addProduct";
    }

    //执行添加操作
    @RequestMapping("/addProduct")
    @ResponseBody
    public String addProduct(HttpServletRequest request){
//        String productListStr = request.getParameter("productList");
//        JSONObject jsonObject=JSONObject.fromObject(productListStr);
//        ProductList stu=(ProductList)JSONObject.toBean(jsonObject, ProductList.class);
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String UPC = request.getParameter("productUPC").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("productASIN").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("productSKU").replace(",", " ").replace(";", " ");
        String model = request.getParameter("model").replace(",", " ").replace(";", " ");
        if (model == null || model.length() == 0) {
            message = message + "Model is required \n";
        }
        String status = request.getParameter("status");
        if (status == null || status.length() == 0 || status == "0") {
            message = message + "Please choose a status \n";
        }
        String productName = request.getParameter("productName").replace(",", " ").replace(";", " ");
        if (productName == null || productName.length() == 0) {
            message = message + "ProductName is required \n";
        }
        String Brand = request.getParameter("productBrand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("webAddress");
        String ticketString = request.getParameter("productTickets");
        int tickets = 0;
        if (ticketString != null && ticketString.length() != 0) {
            tickets = Double.valueOf(ticketString).intValue();
        }
        String personalLimitString = request.getParameter("personalLimit");
        int personlimits = 0;
        if (personalLimitString != null && personalLimitString.length() != 0) {
            personlimits = Double.valueOf(personalLimitString).intValue();
        }
        if (message.length() > 0) {
            return message;
        } else {
            message = "";
            double weight = Double.parseDouble(request.getParameter("productWeight"));
            double length = Double.parseDouble(request.getParameter("productLength"));
            double width = Double.parseDouble(request.getParameter("productWidth"));
            double height = Double.parseDouble(request.getParameter("productHeight"));
            double price = Double.parseDouble(request.getParameter("productPrice"));
            double promotprice = Double.parseDouble(request.getParameter("promotPrice"));
            int promotQuantity = Double.valueOf(request.getParameter("promotQuantity")).intValue();
            double warehouseprice = Double.parseDouble(request.getParameter("productWarehousePrice"));
            int warehousepromotquantity = Double.valueOf(request.getParameter("warehousePromotQuantity")).intValue();
            double warehousepromotprice = Double.parseDouble(request.getParameter("warehousePromotPrice"));
            String userNote = request.getParameter("userNote");
            String timeString = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
            ProductList product = null;
            product = new ProductList();
            product.setUPC(UPC);
            product.setASIN(ASIN);
            product.setBrand(Brand);
            product.setCompanyName(currentCompany.getCompanyName());
            product.setModel(model);
            product.setSKU(SKU);
            product.setStatus(status);
            product.setProductName(productName);
            product.setURI(url);
            product.setTickets(tickets);
            product.setLimitPerPerson(personlimits);
            product.setWeight(weight);
            product.setLength(length);
            product.setWidth(width);
            product.setHeight(height);
            product.setPrice(price);
            product.setPromotQuantity(promotQuantity);
            product.setPromotPrice(promotprice);
            product.setWarehousePrice(warehouseprice);
            product.setWarehousePromotQuantity(warehousepromotquantity);
            product.setWarehousePromotePrice(warehousepromotprice);
            product.setUserNote(userNote);
            product.setCreatedTime(timeString);
            product.setUpdateTime(timeString);
            product.setOperationRecord("created by " + currentUser.getUserName() + " on " + timeString);
            ProductList producttemp = productListService.findProduct(product.getCompanyName(), model);
            System.out.println(product);
            if(producttemp!=null){
                message = "Model is already exist. Please use the exsting product \n";
                System.out.println("1");
            }else{
                // id>=0 means added sucessfully
                int id = productListService.addProduct(currentUser,currentCompany,product);
                if (id >= 0) {
                    message = "Product Added. \n";
                } else {
                    message = "Product Added Failed, Please Try Again. \n";
                }
            }
            return message;
        }
    }

    //去往更新产品信息的页面       addOrEditProduct.html
    @RequestMapping("/toEditProductPage")
    public String toEditProductPage(HttpServletRequest request , Model model){
        String productId = request.getParameter("productId");
        System.out.println("productId : " + productId);
        ProductList productList = new ProductList();
        if(productId != null && !"".equals(productId)){
            productList = productListService.findProduct(Integer.parseInt(productId));
            model.addAttribute("productList" , productList);
        }else{
            model.addAttribute("productList" , null);
        }        //需要查找所有的EbizStatusEnum
        EbizStatusEnum [] EbizStatusEnums = EbizStatusEnum.values();
        model.addAttribute("EbizStatusEnums" , EbizStatusEnums);
        return "EbizCompanyProducts/editProduct";
    }

    //执行更新操作
    @RequestMapping("/editProduct")
    @ResponseBody
    public String editProduct(HttpServletRequest request){
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String idString = request.getParameter("id");
        int uid = 0;
        if (idString != null && idString.length() != 0) {
            uid = Double.valueOf(idString).intValue();
        }
        String UPC = request.getParameter("UPC").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("ASIN").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("SKU").replace(",", " ").replace(";", " ");
        String model = request.getParameter("model").replace(",", " ").replace(";", " ");
        if (model == null || model.length() == 0) {
            message = message + "Model is required \n";
        }
        String status = request.getParameter("status");
        if (status == null || status.length() == 0 || status == "0") {
            message = message + "Please choose a status \n";
        }
        String productName = request.getParameter("productName").replace(",", " ").replace(";", " ");
        if (productName == null || productName.length() == 0) {
            message = message + "ProductName is required \n";
        }
        String Brand = request.getParameter("Brand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("URI");
        String ticketString = request.getParameter("tickets");
        int tickets = 0;
        if (ticketString != null && ticketString.length() != 0) {
            tickets = Double.valueOf(ticketString).intValue();
        }
        String personalLimitString = request.getParameter("limitPerPerson");
        int personlimits = 0;
        if (personalLimitString != null && personalLimitString.length() != 0) {
            personlimits = Double.valueOf(personalLimitString).intValue();
        }
        if (message.length() > 0) {
            return message;
        } else {
            message = "";
            double weight = Double.parseDouble(request.getParameter("weight"));
            double length = Double.parseDouble(request.getParameter("length"));
            double width = Double.parseDouble(request.getParameter("width"));
            double height = Double.parseDouble(request.getParameter("height"));
            double price = Double.parseDouble(request.getParameter("price"));
            double promotprice = Double.parseDouble(request.getParameter("promotPrice"));
            int promotQuantity = Double.valueOf(request.getParameter("promotQuantity")).intValue();
            double warehouseprice = Double.parseDouble(request.getParameter("warehousePrice"));
            int warehousepromotquantity = Double.valueOf(request.getParameter("warehousePromotQuantity")).intValue();
            double warehousepromotprice = Double.parseDouble(request.getParameter("warehousePromotePrice"));
            String userNote = request.getParameter("userNote");
            String timeString = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
            ProductList product = null;
            System.out.println("-------------------------------开始测试---------------------------");
            boolean update=false;
            product=productListService.findProduct(uid);
            if(product != null){
                //产品存在
                if (product.getUPC() == null || (product.getUPC() != null && !product.getUPC().equals(UPC))) {
                    update = productListService.updateProductUPC(currentUser,currentCompany,product.getId(), UPC);
                    if (update) {
                        product.setUPC(UPC);
                        message = message + "UPC Update Sucessfully \n";
                    } else {
                        message = message + "UPC Update Failed,Please Try Again \n";
                    }
                }
                if (product.getUPC() == null || (product.getUPC() != null && !product.getUPC().equals(UPC))) {

                    update = productListService.updateProductUPC(currentUser,currentCompany,product.getId(), UPC);
                    if (update) {
                        product.setUPC(UPC);
                        message = message + "UPC Update Sucessfully \n";
                    } else {
                        message = message + "UPC Update Failed,Please Try Again \n";
                    }
                }

                if (product.getASIN() == null || (product.getASIN() != null && !product.getASIN().equals(ASIN))) {
                    update = productListService.updateProductASIN(currentUser,currentCompany,product.getId(), ASIN);
                    if (update) {
                        product.setASIN(ASIN);
                        message = message + "ASIN Update Sucessfully \n";
                    } else {
                        message = message + "ASIN Update Failed,Please Try Again \n";
                    }
                }



                if (product.getBrand() == null || ( product.getBrand() != null && !product.getBrand().equals(Brand))) {
                    update = productListService.updateProductBrand(currentUser,currentCompany,product.getId(), Brand);
                    if (update) {
                        product.setBrand(Brand);
                        message = message + "Brand Update Sucessfully \n";
                    } else {
                        message = message + "Brand Update Failed,Please Try Again \n";
                    }
                }

                if (product.getModel() == null || (product.getModel() != null && !product.getModel().equals(model))) {
                    update = productListService.updateProductModel(currentUser,currentCompany,product.getId(), model);
                    if (update) {
                        product.setModel(model);
                        message = message + "Model Update Sucessfully \n";
                    } else {
                        message = message + "Model Update Failed,Please Try Again \n";
                    }
                }

                if (product.getSKU() == null || (product.getSKU() != null && !product.getSKU().equals(SKU))) {
                    update = productListService.updateProductSKU(currentUser,currentCompany,product, SKU);
                    if (update) {
                        product.setSKU(SKU);
                        message = message + "SKU Update Sucessfully \n";
                    } else {
                        message = message + "SKU Update Failed,Please Try Again \n";
                    }

                    if (product.getProductName() == null || (product.getProductName() != null && !product.getProductName().equals(productName))) {
                        update = productListService.updateProductName(currentUser,currentCompany,product.getId(), productName);
                        if (update) {
                            product.setProductName(productName);
                            message = message + "Product name Update Sucessfully \n";
                        } else {
                            message = message + "Product name Update Failed,Please Try Again \n";
                        }
                    }
                }

                if (product.getStatus() == null || (product.getStatus() != null &&!product.getStatus().equals(status))) {
                    update = productListService.updateProductStatus(currentUser,currentCompany,product.getId(), status);
                    if (update) {
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } else {
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                }

                if (product.getURI() == null || (product.getURI() != null && !product.getURI().equals(url))) {
                    update = productListService.updateProductURL(currentUser,currentCompany,product.getId(), url);
                    if (update) {
                        product.setURI(url);
                        message = message + "Product webaddress Update Sucessfully \n";
                    } else {
                        message = message + "Product webaddress Update Failed,Please Try Again \n";
                    }
                }

                if (product.getTickets()!=tickets) {
                    update = productListService.updateProductTicket(currentUser,currentCompany,product.getId(), tickets);
                    if (update) {
                        product.setTickets(tickets);
                        message = message + "Product Ticket Update Sucessfully \n";
                    } else {
                        message = message + "Product Ticket Update Failed,Please Try Again \n";
                    }
                }


                if (product.getLimitPerPerson()!=personlimits) {
                    update = productListService.updateProductLimitPerPerson(currentUser,currentCompany,product.getId(), personlimits);
                    if (update) {
                        product.setLimitPerPerson(personlimits);
                        message = message + "Product LimitPerPerson Update Sucessfully \n";
                    } else {
                        message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
                    }
                }

                if (product.getWeight()!=weight) {
                    update = productListService.updateProductWeight(currentUser,currentCompany,product.getId(), weight);
                    if (update) {
                        product.setWeight(weight);
                        message = message + "Product Weight Update Sucessfully \n";
                    } else {
                        message = message + "Product Weight Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLength()!=length) {
                    update = productListService.updateProductLength(currentUser,currentCompany,product.getId(), length);
                    if (update) {
                        product.setLength(length);
                        message = message + "Product Length Update Sucessfully \n";
                    } else {
                        message = message + "Product Length Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWeight()!=width) {
                    update = productListService.updateProductWidth(currentUser,currentCompany,product.getId(), width);
                    if (update) {
                        product.setWidth(width);
                        message = message + "Product Width Update Sucessfully \n";
                    } else {
                        message = message + "Product Width Update Failed,Please Try Again \n";
                    }
                }
                if (product.getHeight()!=height) {
                    update = productListService.updateProductHeight(currentUser,currentCompany,product.getId(), height);
                    if (update) {
                        product.setHeight(height);
                        message = message + "Product Height Update Sucessfully \n";
                    } else {
                        message = message + "Product Height Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPrice()!=price) {
                    update = productListService.updateProductPrice(currentUser,currentCompany,product.getId(), price);
                    if (update) {
                        product.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } else {
                        message = message + "Product Price Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotQuantity()!=promotQuantity) {
                    update = productListService.updateProductPromotQuantity(currentUser,currentCompany,product.getId(), promotQuantity);
                    if (update) {
                        product.setPromotQuantity(promotQuantity);
                        message = message + "Product PromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotPrice()!=promotprice) {
                    update = productListService.updateProductPromotPrice(currentUser,currentCompany,product.getId(), promotprice);
                    if (update) {
                        product.setPromotPrice(promotprice);
                        message = message + "Product PromotPrice Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotPrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePrice()!=warehouseprice) {
                    update = productListService.updateProductWarehousePrice(currentUser,currentCompany,product.getId(), warehouseprice);
                    if (update) {
                        product.setWarehousePrice(warehouseprice);
                        message = message + "Product WarehousePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotQuantity()!=warehousepromotquantity) {
                    update = productListService.updateProductWarehousePromotQuantity(currentUser,currentCompany,product.getId(), warehousepromotquantity);
                    if (update) {
                        product.setWarehousePromotQuantity(warehousepromotquantity);
                        message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotePrice()!=warehousepromotprice) {
                    update = productListService.updateProductWarehousePromotePrice(currentUser,currentCompany,product.getId(), warehousepromotprice);
                    if (update) {
                        product.setWarehousePromotePrice(warehousepromotprice);
                        message = message + "Product WarehousePromotePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
                    }
                }

                if (product.getUserNote() == null || (product.getUserNote() != null && !product.getUserNote().equals(userNote))) {
                    update = productListService.updateProductUserNote(currentUser,currentCompany,product.getId(), userNote);
                    if (update) {
                        product.setUserNote(userNote);
                        message = message + "Product UserNote Update Sucessfully \n";
                    } else {
                        message = message + "Product UserNote Update Failed,Please Try Again \n";
                    }
                }
                if (message.length() > 0) {
                    return message;
                } else {
                    return "Nothing Updated";
                }
            }else{
                //产品不存在
                return "product is not exist";
            }
        }
    }

    //去往更新或者添加产品信息的页面       addOrEditProduct.html
    @RequestMapping("/toAddOrEditProductPage")
    public String toAddOrEditProductPage(HttpServletRequest request , Model model){
        String productId = request.getParameter("productId");
        System.out.println("productId : " + productId);
        ProductList productList = new ProductList();
        if(productId != null && !"".equals(productId)){
            productList = productListService.findProduct(Integer.parseInt(productId));
            model.addAttribute("productList" , productList);
        }else{
            model.addAttribute("productList" , null);
        }
        //需要查找所有的EbizStatusEnum
        EbizStatusEnum [] EbizStatusEnums = EbizStatusEnum.values();
        model.addAttribute("EbizStatusEnums" , EbizStatusEnums);
        return "EbizCompanyProducts/addOrEditProduct";
    }



    //执行更新或者添加产品信息的操作
    @RequestMapping("/addOrEditProduct")
    @ResponseBody
    public String addOrEditProduct(HttpServletRequest request){
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        boolean isNewProduct = false;
        String idString = request.getParameter("productID");
        int uid = 0;
        if (idString != null && idString.length() != 0) {
            uid = Double.valueOf(idString).intValue();
        } else {
            isNewProduct = true;
        }
        System.out.println("----------" + request.getParameter("productID"));
        System.out.println("----------" + request.getParameter("productUPC"));
        System.out.println("----------" + request.getParameter("model"));
        System.out.println("----------" + request.getParameter("productTickets"));
        System.out.println("----------" + request.getParameter("productUPC"));
        String UPC = request.getParameter("productUPC").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("productASIN").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("productSKU").replace(",", " ").replace(";", " ");
        String model = request.getParameter("model").replace(",", " ").replace(";", " ");
        if (model == null || model.length() == 0) {
            message = message + "Model is required \n";
        }
        String status = request.getParameter("status");
        if (status == null || status.length() == 0 || status == "0") {
            message = message + "Please choose a status \n";
        }
        String productName = request.getParameter("productName").replace(",", " ").replace(";", " ");
        if (productName == null || productName.length() == 0) {
            message = message + "ProductName is required \n";
        }
        String Brand = request.getParameter("productBrand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("webAddress");
        String ticketString = request.getParameter("productTickets");
        int tickets = 0;
        if (ticketString != null && ticketString.length() != 0) {
            tickets = Double.valueOf(ticketString).intValue();
        }
        String personalLimitString = request.getParameter("personalLimit");
        int personlimits = 0;
        if (personalLimitString != null && personalLimitString.length() != 0) {
            personlimits = Double.valueOf(personalLimitString).intValue();
        }
        if (message.length() > 0) {
            return message;
        } else {
            message = "";
            double weight = Double.parseDouble(request.getParameter("productWeight"));
            double length = Double.parseDouble(request.getParameter("productLength"));
            double width = Double.parseDouble(request.getParameter("productWidth"));
            double height = Double.parseDouble(request.getParameter("productHeight"));
            double price = Double.parseDouble(request.getParameter("productPrice"));
            double promotprice = Double.parseDouble(request.getParameter("promotPrice"));
            int promotQuantity = Double.valueOf(request.getParameter("promotQuantity")).intValue();
            double warehouseprice = Double.parseDouble(request.getParameter("productWarehousePrice"));
            int warehousepromotquantity = Double.valueOf(request.getParameter("warehousePromotQuantity")).intValue();
            double warehousepromotprice = Double.parseDouble(request.getParameter("warehousePromotPrice"));
            String userNote = request.getParameter("userNote");
            String timeString = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
            ProductList product = null;
            System.out.println("-------------------------------开始测试---------------------------");
            if(uid == 0){
                isNewProduct = true;//是一个新的产品
            }
            System.out.println("-------------------------------"+ isNewProduct +"---------------------------");
            if (isNewProduct) {
                product = new ProductList();
                product.setUPC(UPC);
                product.setASIN(ASIN);
                product.setBrand(Brand);
                product.setCompanyName(currentCompany.getCompanyName());
                product.setModel(model);
                product.setSKU(SKU);
                product.setStatus(status);
                product.setProductName(productName);
                product.setURI(url);
                product.setTickets(tickets);
                product.setLimitPerPerson(personlimits);
                product.setWeight(weight);
                product.setLength(length);
                product.setWidth(width);
                product.setHeight(height);
                product.setPrice(price);
                product.setPromotQuantity(promotQuantity);
                product.setPromotPrice(promotprice);
                product.setWarehousePrice(warehouseprice);
                product.setWarehousePromotQuantity(warehousepromotquantity);
                product.setWarehousePromotePrice(warehousepromotprice);
                product.setUserNote(userNote);
                product.setCreatedTime(timeString);
                product.setUpdateTime(timeString);
                product.setOperationRecord("created by " + currentUser.getUserName() + " on " + timeString);
                ProductList producttemp = productListService.findProduct(product.getCompanyName(), model);
                System.out.println(product);
                if(producttemp!=null){
                    message = "Model is already exist. Please use the exsting product \n";
                    System.out.println("1");
                }else{
                    // id>=0 means added sucessfully
                    int id = productListService.addProduct(currentUser,currentCompany,product);
                    System.out.println("2");
                    if (id >= 0) {
                        message = "Product Added. \n";
                        System.out.println("3");
                    } else {
                        message = "Product Added Failed, Please Try Again. \n";
                        System.out.println("4");
                    }
                }
                System.out.println("5");
                return message;
            } else {
                boolean update=false;
                product=productListService.findProduct(uid);
                if (!product.getUPC().equals(UPC)) {

                    update = productListService.updateProductUPC(currentUser,currentCompany,product.getId(), UPC);
                    if (update) {
                        product.setUPC(UPC);
                        message = message + "UPC Update Sucessfully \n";
                    } else {
                        message = message + "UPC Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getASIN().equals(ASIN)) {

                    update = productListService.updateProductASIN(currentUser,currentCompany,product.getId(), ASIN);
                    if (update) {
                        product.setASIN(ASIN);
                        message = message + "ASIN Update Sucessfully \n";
                    } else {
                        message = message + "ASIN Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getBrand().equals(Brand)) {

                    update = productListService.updateProductBrand(currentUser,currentCompany,product.getId(), Brand);
                    if (update) {
                        product.setBrand(Brand);
                        message = message + "Brand Update Sucessfully \n";
                    } else {
                        message = message + "Brand Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getModel().equals(model)) {
                    update = productListService.updateProductModel(currentUser,currentCompany,product.getId(), model);
                    if (update) {
                        product.setModel(model);
                        message = message + "Model Update Sucessfully \n";
                    } else {
                        message = message + "Model Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getSKU().equals(SKU)) {
                    update = productListService.updateProductSKU(currentUser,currentCompany,product, SKU);
                    if (update) {
                        product.setSKU(SKU);
                        message = message + "SKU Update Sucessfully \n";
                    } else {
                        message = message + "SKU Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getStatus().equals(status)) {
                    update = productListService.updateProductStatus(currentUser,currentCompany,product.getId(), status);
                    if (update) {
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } else {
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getProductName().equals(productName)) {
                    update = productListService.updateProductName(currentUser,currentCompany,product.getId(), productName);
                    if (update) {
                        product.setProductName(productName);
                        message = message + "Product name Update Sucessfully \n";
                    } else {
                        message = message + "Product name Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getURI().equals(url)) {
                    update = productListService.updateProductURL(currentUser,currentCompany,product.getId(), url);
                    if (update) {
                        product.setURI(url);
                        message = message + "Product webaddress Update Sucessfully \n";
                    } else {
                        message = message + "Product webaddress Update Failed,Please Try Again \n";
                    }
                }
                if (product.getTickets()!=tickets) {
                    update = productListService.updateProductTicket(currentUser,currentCompany,product.getId(), tickets);
                    if (update) {
                        product.setTickets(tickets);
                        message = message + "Product Ticket Update Sucessfully \n";
                    } else {
                        message = message + "Product Ticket Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLimitPerPerson()!=personlimits) {
                    update = productListService.updateProductLimitPerPerson(currentUser,currentCompany,product.getId(), personlimits);
                    if (update) {
                        product.setLimitPerPerson(personlimits);
                        message = message + "Product LimitPerPerson Update Sucessfully \n";
                    } else {
                        message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
                    }
                }

                if (product.getWeight()!=weight) {
                    update = productListService.updateProductWeight(currentUser,currentCompany,product.getId(), weight);
                    if (update) {
                        product.setWeight(weight);
                        message = message + "Product Weight Update Sucessfully \n";
                    } else {
                        message = message + "Product Weight Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLength()!=length) {
                    update = productListService.updateProductLength(currentUser,currentCompany,product.getId(), length);
                    if (update) {
                        product.setLength(length);
                        message = message + "Product Length Update Sucessfully \n";
                    } else {
                        message = message + "Product Length Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWeight()!=width) {
                    update = productListService.updateProductWidth(currentUser,currentCompany,product.getId(), width);
                    if (update) {
                        product.setWidth(width);
                        message = message + "Product Width Update Sucessfully \n";
                    } else {
                        message = message + "Product Width Update Failed,Please Try Again \n";
                    }
                }
                if (product.getHeight()!=height) {
                    update = productListService.updateProductHeight(currentUser,currentCompany,product.getId(), height);
                    if (update) {
                        product.setHeight(height);
                        message = message + "Product Height Update Sucessfully \n";
                    } else {
                        message = message + "Product Height Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPrice()!=price) {
                    update = productListService.updateProductPrice(currentUser,currentCompany,product.getId(), price);
                    if (update) {
                        product.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } else {
                        message = message + "Product Price Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotQuantity()!=promotQuantity) {
                    update = productListService.updateProductPromotQuantity(currentUser,currentCompany,product.getId(), promotQuantity);
                    if (update) {
                        product.setPromotQuantity(promotQuantity);
                        message = message + "Product PromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotPrice()!=promotprice) {
                    update = productListService.updateProductPromotPrice(currentUser,currentCompany,product.getId(), promotprice);
                    if (update) {
                        product.setPromotPrice(promotprice);
                        message = message + "Product PromotPrice Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotPrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePrice()!=warehouseprice) {
                    update = productListService.updateProductWarehousePrice(currentUser,currentCompany,product.getId(), warehouseprice);
                    if (update) {
                        product.setWarehousePrice(warehouseprice);
                        message = message + "Product WarehousePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotQuantity()!=warehousepromotquantity) {
                    update = productListService.updateProductWarehousePromotQuantity(currentUser,currentCompany,product.getId(), warehousepromotquantity);
                    if (update) {
                        product.setWarehousePromotQuantity(warehousepromotquantity);
                        message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotePrice()!=warehousepromotprice) {
                    update = productListService.updateProductWarehousePromotePrice(currentUser,currentCompany,product.getId(), warehousepromotprice);
                    if (update) {
                        product.setWarehousePromotePrice(warehousepromotprice);
                        message = message + "Product WarehousePromotePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getUserNote().equals(userNote)) {
                    update = productListService.updateProductUserNote(currentUser,currentCompany,product.getId(), userNote);
                    if (update) {
                        product.setUserNote(userNote);
                        message = message + "Product UserNote Update Sucessfully \n";
                    } else {
                        message = message + "Product UserNote Update Failed,Please Try Again \n";
                    }
                }
                if (message.length() > 0) {
                    return message;
                } else {
                    return "Nothing Updated";
                }
            }
        }
    }

    //去往激活产品的页面     activeProductManage.html
    @RequestMapping("/readAllActiveAndAliveDealProducts")
    public String readAllActiveAndAliveDealProducs(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = currentCompany.getCompanyName();
        Map<String , Object > map = packageListService.searchAllActiveAndAliveDealProductsSet(companyName , currentPage , pageSize);
        List<ProductList> list = new ArrayList<>();
        list = (List<ProductList>)map.get("data");
        model.addAttribute("listmap" ,list);
        ProductListVo bo = new ProductListVo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizCompanyProducts/activeProductManage.html";
    }


    //-----------------------------------产品管理----------------------end------------------------------------


    //-----------------------------------Deal管理----------------------start------------------------------------
    //Deal管理页面需要的数据和库存管理页面的数据是一样的，所以接口都是相同的，为我们减轻了不少的麻烦
    @RequestMapping("/allDealSendToNurseManage")
    public String allDealSendToNurseManage(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = currentCompany.getCompanyName();
        List<ProductList> list = new ArrayList<>();
        Map<String , Object > map = packageListService.searchAllNonDeletedProductSet(currentCompany , currentPage , pageSize);
        list = (List<ProductList>) map.get("data");
        for(ProductList productList : list){
            System.out.println("productList : " + productList.getModel());
        }
        model.addAttribute("listmap" ,list);
        //分页信息
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(Integer.parseInt(currentPage));
        pagination.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        pagination.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ Integer.parseInt(pageSize));
        pagination.setTotalPage(totalPage.intValue());
        model.addAttribute("pagination" , pagination);
        ProductListVo vo = new ProductListVo();
        vo.setCurrentPage(Integer.parseInt(currentPage));
        vo.setPageSize(Integer.parseInt(pageSize));
        vo.setTotalCount(total);
        vo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , vo);
        return "EbizCompanyProducts/allDealSendToNurseManage.html";
    }

    //Deal管理页面的SendDeal操作       转向到群发邮件的页面，需要查找该产品
    @RequestMapping("/toSendDealToGroupPage")
    public String findProduct(HttpServletRequest request , Model model){
        String productId = request.getParameter("productId");
        ProductList productList = new ProductList();
        productList = productListService.findProduct(Integer.parseInt(productId));
        model.addAttribute("productLisit" , productList);
        //需要查找所有的EbizStatusEnum
        EbizStatusEnum [] EbizStatusEnums = EbizStatusEnum.values();
        model.addAttribute("EbizStatusEnums" , EbizStatusEnums);
        EbizNurseGroupTypeEnum [] ebizNurseGroupTypeEnums;
        ebizNurseGroupTypeEnums = EbizNurseGroupTypeEnum.values();
        model.addAttribute("ebizNurseGroupTypeEnums" , ebizNurseGroupTypeEnums);
        return "EbizCompanyProducts/sendDealToGroup";
    }

    //执行群发Deal的操作
    @RequestMapping("/sendDealGroup")
    @ResponseBody
    public String sendDeal(HttpServletRequest request){
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String idString = request.getParameter("id");
        int uid = Double.valueOf(idString).intValue();

        String UPC = request.getParameter("UPC");
        String ASIN = request.getParameter("ASIN");
        String SKU = request.getParameter("SKU");
        String model = request.getParameter("model");
        if (model == null || model.length() == 0) {
            message = message + "Model is required \n";
        }
        String status = request.getParameter("status");
        if (status == null || status.length() == 0 || status == "0") {
            message = message + "Please choose a status \n";
        }
        String productName = request.getParameter("productName");
        if (productName == null || productName.length() == 0) {
            message = message + "ProductName is required \n";
        }
        String Brand = request.getParameter("Brand");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("URI");
        String ticketString = request.getParameter("productTickets");
        int tickets = 0;
        if (ticketString != null && ticketString.length() != 0) {
            tickets = Double.valueOf(ticketString).intValue();
        }
        String personalLimitString = request.getParameter("personalLimit");
        int personlimits = 0;
        if (personalLimitString != null && personalLimitString.length() != 0) {
            personlimits = Double.valueOf(personalLimitString).intValue();
        }
        if (message.length() > 0) {
            return message;
        } else {
            message = "";
            double weight = Double.parseDouble(request.getParameter("weight"));
            double length = Double.parseDouble(request.getParameter("length"));
            double width = Double.parseDouble(request.getParameter("width"));
            double height = Double.parseDouble(request.getParameter("height"));
            double price = Double.parseDouble(request.getParameter("price"));
            double promotprice = Double.parseDouble(request.getParameter("promotPrice"));
            int promotQuantity = Double.valueOf(request.getParameter("promotQuantity")).intValue();
            double warehouseprice = Double.parseDouble(request.getParameter("warehousePrice"));
            int warehousepromotquantity = Double.valueOf(request.getParameter("warehousePromotQuantity")).intValue();
            double warehousepromotprice = Double.parseDouble(request.getParameter("warehousePromotePrice"));
            String emailContent = request.getParameter("emailContent");

            String addressName[] = request.getParameterValues("addressCheckbox");
            String userGroupName[] = request.getParameterValues("userGroupCheckbox");

            ProductList product = null;

            if (addressName == null || addressName.length == 0) {
                message = message
                        + "Send Goup Email Failed; You need chose at least one address to send a group email; \n";
            }
            if (userGroupName == null || userGroupName.length == 0) {
                message = message
                        + "Send Goup Email Failed; You need chose at least one group to send a group email; \n";
            }
            if (currentCompany.getEmail() == null || currentCompany.getEmail().length() == 0
                    || currentCompany.getEmailPassword() == null || currentCompany.getEmailPassword().length() == 0) {
                message = "Send Goup Email Failed; You can not send deal by email throught system since You did not setupt your company email and password yet. \n";
            }
            if (message.length() != 0) {
                return message;
            } else {
                boolean update = false;
                product = productListService.findProduct(uid);
                if (product.getUPC() == null || (product.getUPC() != null && !product.getUPC().equals(UPC))) {

                    update = productListService.updateProductUPC(currentUser,currentCompany,product.getId(), UPC);
                    if (update) {
                        product.setUPC(UPC);
                        message = message + "UPC Update Sucessfully \n";
                    } else {
                        message = message + "UPC Update Failed,Please Try Again \n";
                    }
                }
                if (product.getASIN() == null || (product.getASIN() != null && !product.getASIN().equals(ASIN))) {

                    update = productListService.updateProductASIN(currentUser,currentCompany,product.getId(), ASIN);
                    if (update) {
                        product.setASIN(ASIN);
                        message = message + "ASIN Update Sucessfully \n";
                    } else {
                        message = message + "ASIN Update Failed,Please Try Again \n";
                    }
                }
                if (product.getBrand() == null || (product.getBrand() != null && !product.getBrand().equals(Brand))) {

                    update = productListService.updateProductBrand(currentUser,currentCompany,product.getId(), Brand);
                    if (update) {
                        product.setBrand(Brand);
                        message = message + "Brand Update Sucessfully \n";
                    } else {
                        message = message + "Brand Update Failed,Please Try Again \n";
                    }
                }
                if (product.getModel() == null || (product.getModel() != null && !product.getModel().equals(model))) {
                    update = productListService.updateProductModel(currentUser,currentCompany,product.getId(), model);
                    if (update) {
                        product.setModel(model);
                        message = message + "Model Update Sucessfully \n";
                    } else {
                        message = message + "Model Update Failed,Please Try Again \n";
                    }
                }
                if (product.getSKU() == null || (product.getSKU() != null && !product.getSKU().equals(SKU))) {
                    update = productListService.updateProductSKU(currentUser,currentCompany,product, SKU);
                    if (update) {
                        product.setSKU(SKU);
                        message = message + "SKU Update Sucessfully \n";
                    } else {
                        message = message + "SKU Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getStatus().equals(status) && (status.equals(EbizStatusEnum.Active.getName())
                        || status.equals(EbizStatusEnum.LiveDeal.getName()))) {
                    update = productListService.updateProductStatus(currentUser,currentCompany,product.getId(), status);
                    if (update) {
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } else {
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                } else if (!product.getStatus().equals(status)
                        && status.equals(EbizStatusEnum.UnActive.getName())) {
                } else if (product.getStatus().equals(status)
                        && status.equals(EbizStatusEnum.UnActive.getName())) {
                    update = productListService.updateProductStatus(currentUser,currentCompany,product.getId(),
                            EbizStatusEnum.Active.getName());
                    if (update) {
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } else {
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                }
                if (product.getProductName() == null || (product.getProductName() != null && !product.getProductName().equals(productName))) {
                    update = productListService.updateProductName(currentUser,currentCompany,product.getId(), productName);
                    if (update) {
                        product.setProductName(productName);
                        message = message + "Product name Update Sucessfully \n";
                    } else {
                        message = message + "Product name Update Failed,Please Try Again \n";
                    }
                }
                if (product.getURI() == null || (product.getURI() != null && !product.getURI().equals(url))) {
                    update = productListService.updateProductURL(currentUser,currentCompany,product.getId(), url);
                    if (update) {
                        product.setURI(url);
                        message = message + "Product webaddress Update Sucessfully \n";
                    } else {
                        message = message + "Product webaddress Update Failed,Please Try Again \n";
                    }
                }
                if (product.getTickets() == null || (product.getTickets() != null && product.getTickets() != tickets)) {
                    update = productListService.updateProductTicket(currentUser,currentCompany,product.getId(), tickets);
                    if (update) {
                        product.setTickets(tickets);
                        message = message + "Product Ticket Update Sucessfully \n";
                    } else {
                        message = message + "Product Ticket Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLimitPerPerson() == null || (product.getLimitPerPerson() != null && product.getLimitPerPerson() != personlimits)) {
                    update = productListService.updateProductLimitPerPerson(currentUser,currentCompany,product.getId(), personlimits);
                    if (update) {
                        product.setLimitPerPerson(personlimits);
                        message = message + "Product LimitPerPerson Update Sucessfully \n";
                    } else {
                        message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
                    }
                }

                if (product.getWeight() == null || (product.getWeight() != null && product.getWeight() != weight)) {
                    update = productListService.updateProductWeight(currentUser,currentCompany,product.getId(), weight);
                    if (update) {
                        product.setWeight(weight);
                        message = message + "Product Weight Update Sucessfully \n";
                    } else {
                        message = message + "Product Weight Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLength() == null || (product.getLength() != null && product.getLength() != length)) {
                    update = productListService.updateProductLength(currentUser,currentCompany,product.getId(), length);
                    if (update) {
                        product.setLength(length);
                        message = message + "Product Length Update Sucessfully \n";
                    } else {
                        message = message + "Product Length Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWeight() == null || (product.getWeight() != null && product.getWeight() != width)) {
                    update = productListService.updateProductWidth(currentUser,currentCompany,product.getId(), width);
                    if (update) {
                        product.setWeight(width);
                        message = message + "Product Width Update Sucessfully \n";
                    } else {
                        message = message + "Product Width Update Failed,Please Try Again \n";
                    }
                }
                if (product.getHeight() == null || (product.getHeight() != null && product.getHeight() != height)) {
                    update =productListService.updateProductHeight(currentUser,currentCompany,product.getId(), height);
                    if (update) {
                        product.setHeight(height);
                        message = message + "Product Height Update Sucessfully \n";
                    } else {
                        message = message + "Product Height Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPrice() != price) {
                    update = productListService.updateProductPrice(currentUser,currentCompany,product.getId(), price);
                    if (update) {
                        product.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } else {
                        message = message + "Product Price Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotQuantity() != promotQuantity) {
                    update = productListService.updateProductPromotQuantity(currentUser,currentCompany,product.getId(), promotQuantity);
                    if (update) {
                        product.setPromotQuantity(promotQuantity);
                        message = message + "Product PromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotPrice() != promotprice) {
                    update = productListService.updateProductPromotPrice(currentUser,currentCompany,product.getId(), promotprice);
                    if (update) {
                        product.setPromotPrice(promotprice);
                        message = message + "Product PromotPrice Update Sucessfully \n";
                    } else {
                        message = message + "Product PromotPrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePrice() != warehouseprice) {
                    update = productListService.updateProductWarehousePrice(currentUser,currentCompany,product.getId(), warehouseprice);
                    if (update) {
                        product.setWarehousePrice(warehouseprice);
                        message = message + "Product WarehousePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotQuantity() != warehousepromotquantity) {
                    update = productListService.updateProductWarehousePromotQuantity(currentUser,currentCompany,product.getId(),
                            warehousepromotquantity);
                    if (update) {
                        product.setWarehousePromotQuantity(warehousepromotquantity);
                        message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotePrice() != warehousepromotprice) {
                    update = productListService.updateProductWarehousePromotePrice(currentUser,currentCompany,product.getId(), warehousepromotprice);
                    if (update) {
                        product.setWarehousePromotePrice(warehousepromotprice);
                        message = message + "Product WarehousePromotePrice Update Sucessfully \n";
                    } else {
                        message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
                    }
                }
                if (message.length() == 0) {
                    message = message + "Nothing Updated; \n";
                }
                // 如果产品信息跟新不成功就暂时不发deal
                if (!message.contains("Update Failed")) {

                    HashSet<String> userEmailList = new HashSet<>();
                    HashSet<String> userGroupNameSet = new HashSet<>();
                    HashMap<String, String> userEmailMap = eBizUserService.readAllActiveNurseNameEmailForCompany(currentCompany.getCompanyName());

                    for (int i = 0; i < userGroupName.length; i++) {
                        userGroupNameSet.add(userGroupName[i]);
                    }

                    boolean readySendEmail = false;

                    if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.AllUser.getName())) {
                        userEmailList.addAll(userEmailMap.values());
                        readySendEmail = true;
                    }
                    if (!readySendEmail) {

                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.NewUser.getName())) {
                            List<String> newUserEmaillist = eBizUserService.readActiveNurseEmailRegistratedInlastTwoMonthForCompany(
                                    currentCompany.getCompanyName());
                            userEmailList.addAll(newUserEmaillist);
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.OnePackagesUser.getName())) {
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.FivePackagesUser.getName());
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());
                            List<String> userNameList = eBizUserService.readPackagesUserNameInLastTwoMonthForCompany(currentCompany.getCompanyName());
                            for (int i = 0; i < userNameList.size(); i++) {
                                userEmailList.add(userEmailMap.get(userNameList.get(i)));
                            }
                        }

                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.FivePackagesUser.getName())) {
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());

                            List<String> userNameList = eBizUserService.readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(
                                    currentCompany.getCompanyName());
                            for (int i = 0; i < userNameList.size(); i++) {
                                userEmailList.add(userEmailMap.get(userNameList.get(i)));
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TenPackagesUser.getName())) {
                            List<String> userNameList = eBizUserService.readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(
                                    currentCompany.getCompanyName());
                            for (int i = 0; i < userNameList.size(); i++) {
                                userEmailList.add(userEmailMap.get(userNameList.get(i)));
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TrustedUser.getName())) {
                            List<String> trustedUserEmailList = eBizUserService.readAllActiveTrustedNurseNameEmailForCompany(currentCompany.getCompanyName());
                            userEmailList.addAll(trustedUserEmailList);
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.UnTrustedUser.getName())) {
                            List<String> trustedUserEmailList = eBizUserService.readAllActiveUnTrustedNurseNameEmailForCompany(currentCompany.getCompanyName());
                            userEmailList.addAll(trustedUserEmailList);
                        }

                    }
                    List<String> emailList = new ArrayList<String>();
                    userEmailList.remove(null);
                    int i = 0;
                    for (String string : userEmailList) {
                        String[] temp = string.split("\n");
                        emailList.add(temp[temp.length - 1]);
                        System.out.println(i + ":" + temp[temp.length - 1]);
                        i++;
                    }
                    // send email to emailList next;
                    //emailList.clear();
                    //emailList.add("miketian668@gmail.com");
                    //emailList.add("tgxfff@hotmail.com");
                    boolean send = sendDealEmail(product, currentUser,currentCompany, addressName, emailList, emailContent);
                    if (send) {
                        message = message + "Email Has Been Send To Chosen Group; \n";
                    } else {
                        message = message + "Send Email Failed, Please Check; \n";
                    }
                } else {
                    message = message + "Send Goup Email Failed Since Product Update Failed, Please Try Again; \n";
                }
//这里DealList有问题
                addDealToDataBase(currentUser,currentCompany,product);
                return message;
            }
        }
    }

    private void addDealToDataBase(EbizUser currentUser, EbizCompany currentCompany, ProductList product) {
        if (product == null) {
            return;
        }
        dealListService.addDeal(currentUser , currentCompany , product);
    }

    private boolean sendDealEmail(ProductList product, EbizUser currentUser, EbizCompany currentCompany, String[] addressName, List<String> emailList, String emailContent) {
        HashSet<String> addressSet = new HashSet<>();
        for (int i = 0; i < addressName.length; i++) {
            addressSet.add(addressName[i]);
        }
        ProductList tempEbizProduct = product;
        if (tempEbizProduct == null)
            return false;
        String eastebizlinkString = "<a href=http://eastebiz.com/>www.eastebiz.com</a> <br>";

        String emailContentString = "Dear All: \n";
        emailContentString = emailContentString + "Product we want:\n";

        String[] uris = tempEbizProduct.getURI().split("\n");
        String uuuString = "<a href=" + uris[0] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
        if (uris.length > 1) {
            for (int i = 1; i < uris.length; i++) {
                uuuString = uuuString + "or<br>";
                if (uris[i].length() >= 5) {
                    uuuString = uuuString + "<a href=" + uris[i] + ">" + tempEbizProduct.getProductName() + "</a> <br>";
                } else {
                    uuuString = uuuString + tempEbizProduct.getProductName() + "</a> <br>";
                }

            }
        }
        String contentfromPanel = emailContent;
        emailContentString = emailContentString + uuuString;
        emailContentString = emailContentString + "\n";
        contentfromPanel = contentfromPanel.replace("\n\n", "");
        if (!contentfromPanel.endsWith("\n")) {
            contentfromPanel = contentfromPanel + "\n";
        }
        if (contentfromPanel.length() > 5) {
            emailContentString = emailContentString + contentfromPanel + "\n";
        }
        String shippingpreString = "Accept order shipped to ";
        boolean tt = false;
        if (addressSet.contains("Home")) {
            tt = true;
            shippingpreString = shippingpreString + "Home ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail1()) && currentCompany.getAddressDetail1().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail1() + " ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail2()) && currentCompany.getAddressDetail2().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail2() + " ";
        }
        if (addressSet.contains(currentCompany.getAddressDetail3()) && currentCompany.getAddressDetail3().length() > 0) {
            if (tt) {
                shippingpreString = shippingpreString + ",";
            }
            tt = true;
            shippingpreString = shippingpreString + currentCompany.getAddressDetail3() + " ";
        }
        if (shippingpreString.endsWith(" ")) {
            shippingpreString = shippingpreString.substring(0, shippingpreString.length() - 1);
        }

        emailContentString = emailContentString + shippingpreString + ".\n";

        if (addressSet.contains("Home")) {

            if (tempEbizProduct.getPrice() == tempEbizProduct.getPromotPrice()) {
                emailContentString = emailContentString + "Ship to home price $" + tempEbizProduct.getPrice() + "\n";
            } else {
                emailContentString = emailContentString + " Ship to home price $" + tempEbizProduct.getPrice();
                emailContentString = emailContentString + " and promotional price $" + tempEbizProduct.getPromotPrice()
                        + " for " + tempEbizProduct.getPromotQuantity() + " or more Units.\n";
            }
        }
        boolean haswareHouseAddress = addressSet.contains(currentCompany.getAddressName1())
                && currentCompany.getAddressName1().length() > 0
                || addressSet.contains(currentCompany.getAddressName2()) && currentCompany.getAddressName2().length() > 0
                || addressSet.contains(currentCompany.getAddressName3()) && currentCompany.getAddressName3().length() > 0;
        if (haswareHouseAddress) {
            if (tempEbizProduct.getWarehousePrice() == tempEbizProduct.getWarehousePromotePrice()) {
                emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.getWarehousePrice()
                        + "\n";
            } else {
                emailContentString = emailContentString + "Ship to warehouse price $" + tempEbizProduct.getWarehousePrice();
                emailContentString = emailContentString + " and promotional price $"
                        + tempEbizProduct.getWarehousePromotePrice() + " for " + tempEbizProduct.getWarehousePromotePrice()
                        + " or more units.\n";
            }
        }
        emailContentString = emailContentString + "\n";
        emailContentString = emailContentString + "Please get tickets or report your products on\n";
        emailContentString = emailContentString + eastebizlinkString;
        emailContentString = emailContentString + "\n";

        String warehouseAddressPreString = "WareHouse address:\n";

        if (haswareHouseAddress) {
            emailContentString = emailContentString + warehouseAddressPreString;
        }
        boolean temp = false;
        if (addressSet.contains(currentCompany.getAddressName1()) && currentCompany.getAddressName1().length() > 0) {
            emailContentString = emailContentString + currentCompany.getAddressName1() + ":\n" + currentCompany.getAddressDetail1();
            temp = true;
        }
        if (addressSet.contains(currentCompany.getAddressName2()) && currentCompany.getAddressName2().length() > 0) {
            if (temp) {
                emailContentString = emailContentString + "\nOr ";
            }
            emailContentString = emailContentString + currentCompany.getAddressName2() + ":\n" + currentCompany.getAddressDetail2();
            temp = true;
        }

        if (addressSet.contains(currentCompany.getAddressName3()) && currentCompany.getAddressName3().length() > 0) {
            if (temp) {
                emailContentString = emailContentString + "\nOr ";
            }
            emailContentString = emailContentString + currentCompany.getAddressName2() + ":\n" + currentCompany.getAddressDetail2();
            temp = true;
        }
        emailContentString = emailContentString + "\n";
        emailContentString = emailContentString + "Regards\n" + currentUser.getUserName()+"("+currentCompany.getCompanyName()+")" + "\n";

        emailContentString = emailContentString.replace("\n", "<br>");
        if (emailList == null) {
            return false;
        }
        // System.out.println(emailAddressList.size());
        // if(true){
        // return;
        // }

        String emailTitleString = "We Want: " + tempEbizProduct.getProductName();

        if (emailList == null || emailList.size() == 0) {
            return false;
        }

        //emailList.clear();
        ///emailList.add("miketian668@gmail.com");
        //emailList.add("tgxfff@hotmail.com");
        return EmailSenderCenter.getInstance().sendEmailtoMultipleReciForCompany(currentCompany, emailList, emailTitleString,
                emailContentString, null);

    }


    //-----------------------------------Deal管理----------------------end------------------------------------


    //-----------------------------------包裹管理----------------------end------------------------------------
    @RequestMapping("/readAllPackagesForCompany")
    public String readAllPackagesForCompany(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //System.out.println("currenPage = " + currentPage + " ; pageSize = " + pageSize);
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = currentCompany.getCompanyName();
        Map<String , Object > map = packageListService.readAllPackagesForCompany(companyName , currentPage , pageSize);
        List<PackageList> list = new ArrayList<>();

        list = (List<PackageList>)map.get("data");
        //System.out.println("大小为 : " + list.size());
        model.addAttribute("listmap" ,list);
        PackageVo vo = new PackageVo();
        vo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        vo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        vo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ vo.getPageSize());
        vo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , vo);
        return "EbizCompanyProducts/allPackForCompany.html";
    }

    //跳转到更新页面   editPackageByDoctor.html
    @RequestMapping("/toEditPackageByDoctorPage")
    public String toEditPackageByDoctorPage(HttpServletRequest request , Model model){
        String packageListId = request.getParameter("packageListId");
        PackageList packageList = packageListService.findPackage(Integer.parseInt(packageListId));
        model.addAttribute("packageList" , packageList);
        //包裹支付的状态
        EbizPackagePayStatusEnum [] ebizPackagePayStatusEnums = EbizPackagePayStatusEnum.values();
        model.addAttribute("ebizPackagePayStatusEnums" , ebizPackagePayStatusEnums);

        //包裹的邮寄状态
        EbizPackageStatusEnum[] ebizPackageStatusEnum = EbizPackageStatusEnum.values();
        model.addAttribute("EbizPackageStatusEnums" , ebizPackageStatusEnum);
        return "EbizCompanyProducts/editPackageByDoctor";
    }
    //执行更新操作
    @RequestMapping("/editPackByDocotor")
    @ResponseBody
    public String editPackByDocotor(HttpServletRequest request){
        String productModel = (String) request.getParameter("modelNumber").trim();
        String productName = (String) request.getParameter("productName").replace(";", "").replace(",", "").trim();
        String productBrand = (String) request.getParameter("brand").trim();
        String ASIN = (String) request.getParameter("ASIN").trim();
        String UPC = (String) request.getParameter("UPC").trim();
        String SKU = (String) request.getParameter("SKU").trim();
        String trackingNumber = (String) request.getParameter("trackingNumber").trim();
        String shipID = (String) request.getParameter("shipID").trim();
        String note=(String) request.getParameter("note").trim();
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        double price = Double.parseDouble(request.getParameter("price"));
        String address = (String) request.getParameter("address").trim();
        String creditCard = (String) request.getParameter("creditCardNumber").trim();
        String shipstatus = (String) request.getParameter("packageStatus");
        String payStatus = (String) request.getParameter("payStatus");

        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String uidString = request.getParameter("id");

        int uid = Integer.parseInt(uidString);
        PackageList pack = packageListService.findPackage(uid);
        boolean update = false;
        String message = "";

        if (pack.getModelNumber() == null || (pack.getModelNumber() != null && !pack.getModelNumber().equals(productModel))) {
            update = packageListService.updatePackageModel(currentUser,currentCompany,pack.getId(), productModel);
            if (update) {
                pack.setModelNumber(productModel);
                message = message + "ProductModel Update Sucessfully \n";
            } else {
                message = message + "ProductModel Update Failed,Please Try Again \n";
            }
        }
        if (pack.getBrand() == null || (pack.getBrand() != null && !pack.getBrand().equals(productBrand))) {
            update = packageListService.updatePackageBrand(currentUser,currentCompany,pack.getId(), productBrand);
            if (update) {
                pack.setBrand(productBrand);
                message = message + "Product Brand Update Sucessfully \n";
            } else {
                message = message + "Product Brand Update Failed,Please Try Again \n";
            }
        }
        if (pack.getPayStatus() == null || (pack.getPayStatus() != null && !pack.getPayStatus().equals(payStatus))) {
            update = packageListService.updatePackagePayStatus(currentUser,currentCompany,pack.getId(), payStatus);
            if (update) {
                pack.setPayStatus(payStatus);
                message = message + "Pay Status Update Sucessfully \n";
            } else {
                message = message + "Pay Status Update Failed,Please Try Again \n";
            }
        }
        if (pack.getStatus() == null || (pack.getStatus() != null && !pack.getStatus().equals(shipstatus))) {
            update = packageListService.updatePackageStatus(currentUser,currentCompany,pack.getId(), shipstatus);
            if (update) {
                pack.setStatus(shipstatus);
                message = message + "Status Update Sucessfully \n";
            } else {
                message = message + "Status Update Failed,Please Try Again \n";
            }
        }
        if (pack.getProductName() == null || (pack.getProductName() != null && !pack.getProductName().equals(productName))) {
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

        if (pack.getSKU() == null || (pack.getSKU() != null && !pack.getSKU().equals(SKU))) {
            update = packageListService.updatePackageSKU(currentUser,currentCompany,pack.getId(), SKU);
            if (update) {
                pack.setSKU(SKU);
                message = message + "SKU Information Update Sucessfully \n";
            } else {
                message = message + "SKU Information Update Failed,Please Try Again \n";
            }
        }
        if (pack.getUPC() == null || (pack.getUPC() != null && !pack.getUPC().equals(UPC))) {
            update = packageListService.updatePackageUPC(currentUser,currentCompany,pack.getId(), UPC);
            if (update) {
                pack.setUPC(UPC);
                message = message + "UPC Information Update Sucessfully \n";
            } else {
                message = message + "UPC Information Update Failed,Please Try Again \n";
            }
        }
        if (pack.getASIN() == null || (pack.getASIN() != null && !pack.getASIN().equals(ASIN))) {
            update = packageListService.updatePackageASIN(currentUser,currentCompany,pack.getId(), ASIN);
            if (update) {
                pack.setASIN(ASIN);
                message = message + "ASIN Information Update Sucessfully \n";
            } else {
                message = message + "ASIN Information Update Failed,Please Try Again \n";
            }
        }



        if (pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditCard))) {

            update = packageListService.updatePackageCreditCard(currentUser,currentCompany,pack.getId(), creditCard);
            if (update) {
                pack.setCreditCardNumber(creditCard);
                message = message + "CreditCard Information Update Sucessfully \n";
            } else {
                message = message + "CreditCard Information Update Failed,Please Try Again \n";
            }
        }
        if (pack.getShippingAddress() == null || (pack.getShippingAddress() != null && !pack.getShippingAddress().equals(address))) {
            update = packageListService.updatePackageAddress(currentUser,currentCompany,pack.getId(), address);
            if (update) {
                pack.setShippingAddress(address);
                message = message + "Address Update Sucessfully \n";
            } else {
                message = message + "Address Update Failed,Please Try Again \n";
            }
        }
        if (pack.getTrackingNumber() == null || (pack.getTrackingNumber() != null && !pack.getTrackingNumber().equals(trackingNumber))) {
            update = packageListService.updatePackageTracking(currentUser,currentCompany,pack.getId(), trackingNumber);
            if (update) {
                pack.setTrackingNumber(trackingNumber);
                message = message + "tracking Number Update Sucessfully \n";
            } else {
                message = message + "tracking Number Update Failed,Please Try Again \n";
            }
        }
        if (pack.getShipID() == null || (pack.getShipID() != null && !pack.getShipID().equals(shipID))) {
            update = packageListService.updatePackageShipId(currentUser,currentCompany,pack.getId(), shipID);
            if (update) {
                pack.setShipID(shipID);
                message = message + "Ship ID Update Sucessfully \n";
            } else {
                message = message + "Ship ID Update Failed,Please Try Again \n";
            }
        }
        if (pack.getNote() == null || (pack.getNote() != null && !pack.getNote().equals(note))) {

            update = packageListService.updatePackageNote(currentUser,currentCompany,pack.getId(), note);
            if (update) {
                pack.setNote(note);
                message = message + "Note Update Sucessfully \n";
            } else {
                message = message + "Note Update Failed,Please Try Again \n";
            }
        }
        if (message.length() == 0) {
            message = "Nothing Updated";
        }
        return message;
    }
    //跳转到发送email页面      sendEmailToUser.html
    @RequestMapping("/toSendEmailToUserPage")
    public String toSendEmailToUserPage(HttpServletRequest request , Model model){
        String packageListId = request.getParameter("packageListId");
        PackageList packageList = packageListService.findPackage(Integer.parseInt(packageListId));
        model.addAttribute("packageList" , packageList);
        //EbizPackagePayStatusEnum
        EbizPackagePayStatusEnum [] ebizPackagePayStatusEnums = EbizPackagePayStatusEnum.values();
        model.addAttribute("ebizPackagePayStatusEnums" , ebizPackagePayStatusEnums);

        //
        EbizPackageStatusEnum[] ebizPackageStatusEnum = EbizPackageStatusEnum.values();
        model.addAttribute("EbizPackageStatusEnums" , ebizPackageStatusEnum);
        return "EbizCompanyProducts/sendEmailToUser";
    }

    //发送email
    @RequestMapping("/sendEmailtoOneRecipient")
    @ResponseBody
    public String sendEmailtoOneRecipient(HttpServletRequest request) throws  Exception{
        request.setCharacterEncoding("UTF-8");
        String message = "";
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String emailAddress = request.getParameter("email");
        String emailTitle = request.getParameter("emailTitle");
        String emailContent = request.getParameter("emailContent");
        String userName = request.getParameter("userName");
        emailContent = "Dear "+userName+"\n\n"+emailContent + "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";

        emailContent=emailContent.replace("\n", "<br>");
        List<File> uploadedFiles = saveUploadedFiles(request, company);
        List<String> chosenFileStrings = new ArrayList<>();
        for (int i = 0; i < uploadedFiles.size(); i++) {
            chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
        }

        try {
            if (EmailSenderCenter.getInstance().sendEmailtoOneRecipientFromCompany(company, emailAddress, emailTitle,
                    emailContent, chosenFileStrings)) {
                message = message + "Email Send Sucessfully; \n";
            } else {
                message = message + "Send Email Failed, Please Try Again; \n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            deleteUploadFiles(uploadedFiles);
        }
        if (message.length() > 0) {
            return message;
        }
        return "OK";
    }

    //-----------------------------------包裹管理----------------------end------------------------------------



    //------------------------------------库存管理----------------start----------------------------------//
    //去往库存列表页面
    @RequestMapping("/readAllNonDeletedProductSet")
    public String readAllNonDeletedProductSet(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String companyName = currentCompany.getCompanyName();
        List<ProductList> list = new ArrayList<>();
        Map<String , Object > map = packageListService.searchAllNonDeletedProductSet(currentCompany , currentPage , pageSize);
        list = (List<ProductList>) map.get("data");
        model.addAttribute("listmap" ,list);
        //分页信息
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(Integer.parseInt(currentPage));
        pagination.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        pagination.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ Integer.parseInt(pageSize));
        pagination.setTotalPage(totalPage.intValue());
        model.addAttribute("pagination" , pagination);
        ProductListVo vo = new ProductListVo();
        vo.setCurrentPage(Integer.parseInt(currentPage));
        vo.setPageSize(Integer.parseInt(pageSize));
        vo.setTotalCount(total);
        vo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , vo);
        return "EbizCompanyProducts/inventory.html";
    }
    //------------------------------------库存管理----------------end----------------------------------//


    //------------------------------------对单管理----------------start----------------------------------//
    //进入到对单页面要显示的数据
    //http://localhost:8080/companyManager/readAllNeedCheckPackagesForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readAllNeedCheckPackagesForCompany")
    public String readAllNeedCheckPackagesForCompany(Model model ,HttpServletRequest request ){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.findAllUnCheckedPackForCompany(currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println("对单管理：" + packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);
        return "EbizCompanyProducts/allUnCheckedPackForCompany.html";
    }

    //去往所有领取的对单任务页面所需要的数据(当前已经领取但是还未完成的任务)
    //http://localhost:8080/companyManager/readCheckingTasksForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readCheckingTasksForCompany")
    public String readCheckingTasksForCompany(Model model ,HttpServletRequest request){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        List<PackageList> packages=new ArrayList<>();

        Map<String , Object > map  = packageListService.readCheckingTasksForCompany( currentUser,currentCompany,currentPage,pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println(packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(currentPage));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);


        //查找所有的任务的状态
        EbizPackageStatusEnum[] EbizPackageStatusEnums = EbizPackageStatusEnum.values();
        model.addAttribute("EbizPackageStatusEnums" , EbizPackageStatusEnums);

        //查找所有的地址
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);

        return "EbizCompanyProducts/allCurrentCheckingTasksForUser.html";
    }

    //去往所有完成的对单任务的页面的所需要的数据(完成的任务)
    //http://localhost:8080/companyManager/readAllCheckedPackagesForUser?currentPage=1&pageSize=10
    @RequestMapping("/readAllCheckedPackagesForUser")
    public String readAllCheckedPackagesForUser(Model model , HttpServletRequest request){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        List<PackageList> packages=new ArrayList<>();

        Map<String , Object > map  = packageListService.readAllCheckedPackagesForUser( currentUser,currentCompany,currentPage,pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println(packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(currentPage));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizCompanyProducts/allCheckedPackForUser.html";
    }



    //------------------------------------对单管理----------------end----------------------------------//


    //------------------------------------Label管理----------------start----------------------------------//
    //进入到label页面显示的数据
    //http://localhost:8080/companyManager/readAllNeedLabeListPackagesForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readAllNeedLabeListPackagesForCompany")
    public String readAllNeedLabeListPackagesForCompany(Model model ,HttpServletRequest request ){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.readAllNeedLabeListPackagesForCompany(currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println("发送Label:" + packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizCompanyProducts/allUnLabeledPackForCompany.html";
    }


    //当前已经领取过的label
    //http://localhost:8080/companyManager/readMakingLabelTasksForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readMakingLabelTasksForCompany")
    public String readMakingLabelTasksForCompany(Model model , HttpServletRequest request){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        List<PackageList> packages=new ArrayList<>();

        Map<String , Object > map  = packageListService.readMakingLabelTasksForCompany( currentUser,currentCompany,currentPage,pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println(packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(currentPage));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);

        //查找所有的地址
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);

        return "EbizCompanyProducts/allCurrentLabelingTasksForUser.html";
    }

    //查找已经完成的任务Label
    @RequestMapping("/readAllLabeledkagesForUser")
    public String readAllLabeledkagesForUser(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.readAllLabeledkagesForUser(currentUser ,currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");

        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);
        return "EbizCompanyProducts/allLabeledPackForUser.html";
    }
    ///查询任务结束,开始其他操作（更新、其他操作）

    //转向发送label的页面
    @RequestMapping("/sendLabelToUser")
    public String sendLabelToUser(HttpServletRequest request , Model model){
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        //获取将要发送的label的id集合
        String packageIDStrings = request.getParameter("sendPackagesUID");
        String[] uidStrings=packageIDStrings.split(";");
        String uidNewString="";
        List<Integer> uidList=new ArrayList<Integer>();
        for(int i=0;i<uidStrings.length;i++){
            if(uidStrings[0].length()>0){
                uidList.add(Integer.parseInt(uidStrings[i]));
                uidNewString=uidNewString+uidStrings[i]+" ";
            }
        }
        uidNewString=uidNewString.substring(0, uidNewString.length()-1);
        List<PackageList> packs = packageListService.findPackages(uidList);
        String userName="";
        String email="";
        if(packs.size()>0){
            userName=packs.get(0).getUserName();
            email=packs.get(0).getEmail();
        }
        double value=0;

        String packageListInfor="";
        for (int i=0;i<packs.size();i++){
            packageListInfor=packageListInfor+"UID: "+packs.get(i).getId()+", "+packs.get(i).getQuantity()+" units, "+packs.get(i).getSKU()+", "+packs.get(i).getProductName()+"\n";
        }
        EbizUser thisUser=eBizUserService.getEbizUserByName(userName);
        EmailLabelVo vo = new EmailLabelVo();
        vo.setEmail(email);
        vo.setFirstName(thisUser.getFirstName());
        vo.setLastName(thisUser.getLastName());
        vo.setPackageListInfor(packageListInfor);
        vo.setUidNewString(uidNewString);
        vo.setUserName(userName);
        vo.setShippingAddress(thisUser.getAddress());
        model.addAttribute("vo" , vo);
        return "EbizCompanyProducts/sendLabelToUser";
    }

    @RequestMapping("/sendLabeltoOneRecipient")
    @ResponseBody
    public String sendLabeltoOneRecipient(HttpServletRequest request) throws IOException, ServletException {
        String message = "";
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");

        String emailContentStringFromWeb = request.getParameter("emailContent");
        String emailcontant="";
        String trackingInfor =request.getParameter("trackingInfor");
        String shipIdString = request.getParameter("shipId");

        EbizUser ebizUser = (EbizUser) request.getSession().getAttribute("opUser");
        String emailAddress = ebizUser.getEmail();
        request.removeAttribute("opUser");

        List<PackageList> packs = (List<PackageList>) request.getSession().getAttribute("packList");
        System.out.println(emailContentStringFromWeb);
        request.removeAttribute("packList");

        String uidString = "";
        String temp = "";
        boolean bb = false;
        for (int i = 0; i < packs.size(); i++) {
            if (temp.length() == 0) {
                temp = packs.get(i).getUserName();
            } else {
                if (!temp.equals(packs.get(i).getUserName())) {
                    message = message + "Pay Failed since you can not Email Label to multiple user at the same email; \n";
                    bb = true;
                    break;
                }

            }
            uidString = uidString + packs.get(i).getId() + " ";
            emailcontant=emailcontant+"UID: "+packs.get(i).getId()+", "+packs.get(i).getModelNumber()+", "+packs.get(i).getQuantity()+" units, "+packs.get(i).getProductName()+"\n";
        }

        String tracking=trackingInfor.replace("\n", "?");
        tracking=tracking.replace(" ", "?");
        tracking=tracking.replace(",", "?");
        tracking=tracking.replace(";", "?");
        while(tracking.endsWith("?")){
            tracking=tracking.substring(0,tracking.length()-1);
        }

        String shipid=shipIdString.replace("\n", "?");
        shipid=shipid.replace(" ", "?");
        shipid=shipid.replace(",", "?");
        shipid=shipid.replace(";", "?");
        while(shipid.endsWith("?")){
            shipid=shipid.substring(0,shipid.length()-1);
        }

        if (!bb) {
            for (int i = 0; i < packs.size(); i++) {

                if (packageListService.updatePackageTrackingLabelStatusAndLabeler(currentUser, company,
                        packs.get(i).getId(),tracking,shipid)) {
                    message = message + packs.get(i).getId() + " Update Sucessfully;\n";
                } else {
                    message = message + packs.get(i).getId() + " Update Failed;\n";
                }
            }

            String emailContentString;

            emailContentString="Dear "+ebizUser.getUserName()+"\n\n"+emailcontant+"\n"+emailContentStringFromWeb+"\n"+"\n"+"attached are labels"+"\n"+ "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";;

            emailContentString = emailContentString.replace("\n", "<br>");

            String emailTitleString;
            emailTitleString = "Shipping request: uid  " + uidString;

            List<File> uploadedFiles = saveUploadedFiles(request, company);
            List<String> chosenFileStrings = new ArrayList<>();
            for (int i = 0; i < uploadedFiles.size(); i++) {
                chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
            }
            try {
                if (EmailSenderCenter.getInstance().sendEmailtoOneRecipientFromCompany(company, emailAddress,
                        emailTitleString, emailContentString, chosenFileStrings)) {
                    message = message + "Email Send Sucessfully; \n";
                } else {
                    message = message + "Send Email Failed, Please Try Again; \n";
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            } finally {
                deleteUploadFiles(uploadedFiles);
            }
        }
        if (message.length() > 0) {
            return message;
        }
        return "";
    }



    //------------------------------------Label管理----------------end----------------------------------//


    //------------------------------------支付用户管理----------------start----------------------------------//

    //进入到支付用户页面显示的数据
    //http://localhost:8080/companyManager/readAllNeedPayListPackagesForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readAllNeedPayListPackagesForCompany")
    public String readAllNeedPayListPackagesForCompany(Model model ,HttpServletRequest request ){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.readAllNeedPayListPackagesForCompany(currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println("支付用户："+ packageList);
        }
        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizCompanyProducts/allUnPaidPackForCompany.html";
    }

    //去往当前要支付的页面，准备所需要的列表数据
    //http://localhost:8080/companyManager/readPayingTasksForCompany?currentPage=1&pageSize=10
    @RequestMapping("/readPayingTasksForCompany")
    public String readPayingTasksForCompany(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.readPayingTasksForCompany(currentUser ,currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");

        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);
        return "EbizCompanyProducts/allCurrentPayingTasksForUser.html";
    }

    //查看已经支付过的任务
    @RequestMapping("/readPayedTasksForUser")
    public String readPayedTasksForUser(HttpServletRequest request , Model model){
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.readAllPaidkagesForUser(currentUser ,currentCompany.getCompanyName()  , currentPage , pageSize);
        packages = (List<PackageList>) map.get("data");

        model.addAttribute("listmap" ,packages);
        PackageListBo bo = new PackageListBo();
        bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        bo.setPageSize(Integer.parseInt(pageSize));
        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        EbizCompanyAddressEnum[] EbizCompanyAddressEnums = EbizCompanyAddressEnum.values();
        model.addAttribute("EbizCompanyAddressEnums" , EbizCompanyAddressEnums);
        return "EbizCompanyProducts/allPaidPackForUser.html";
    }

    //------------------------------------支付用户管理----------------end----------------------------------//










    //将正在交易的Label单个或者批量取消任务
    @RequestMapping("/cancelLabelTasks")
    public String cancelLabelTasks(HttpServletRequest request){
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String uIDsString = (String) request.getParameter("packagesUID");
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
        for(int i=0;i<UIDList.size();i++){
            boolean update = packageListService.cancelLabelTask(currentUser, currentCompany, UIDList.get(i));
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber=packageListService.getLabelTaskCount(currentUser,currentCompany);
        message=message+"You have Cancel "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        return message;
    }









    //更新任务当前状态
    @RequestMapping("/updateOneOrMoreCheckedTask")
    @ResponseBody
    public String updateOneOrMoreCheckedTask(Model model, HttpServletRequest request){
        int failednumber=0;//领取失败的任务量
        int sucessednumber=0;//领取成功的任务量
        String message="";//要返回的信息
        String uIDsString = (String) request.getParameter("packageid");
        String status = (String) request.getParameter("onePackageStatus");
        //获取当前用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        if(uIDsString.endsWith(";")){
            uIDsString=uIDsString.substring(0,uIDsString.length()-1);
        }
        String[] uidStrings=uIDsString.split(";");
        ArrayList<Integer> UIDList=new ArrayList<>();
        for(int i=0;i<uidStrings.length;i++){
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        //遍历该要领取的任务的任务id
        for(int i=0;i<UIDList.size();i++){
            //判断该任务是否已经被更新成功
            boolean update =  packageListService.finishCheckTask(currentUser, currentCompany, UIDList.get(i) , status);
            //如果更新成功
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                //更新失败
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber=packageListService.getCheckTaskCount(currentUser,currentCompany);
        message=message+"You have finished "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        return message;
    }

    //取消已经领取的任务
    @RequestMapping("/cancelCheckTasks")
    @ResponseBody
    public String cancelCheckTasks(HttpServletRequest request){
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String uIDsString = (String) request.getParameter("packagesUID");
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

        for(int i=0;i<UIDList.size();i++){

            boolean update = packageListService.cancelCheckTask(currentUser, currentCompany, UIDList.get(i));
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber=packageListService.getCheckTaskCount(currentUser,currentCompany);
        message=message+"You have Cancel "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        return message;
    }








    //跳转到支付页面
    @RequestMapping("/toPayPackagesPage")
    public  String toPayPackagesPage(){

        return "";
    }

    //执行支付操作
    @RequestMapping("/sendMoneytoOneRecipient")
    public String sendMoneytoOneRecipient(){

        return "";
    }

    //取消支付任务
    @RequestMapping("/CancelPayTasks")
    @ResponseBody
    public String CancelPayTasks(HttpServletRequest request , Model model){
        int checkTaskLimit=50;
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String uIDsString = (String) request.getParameter("packagesUID");
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

        for(int i=0;i<UIDList.size();i++){
            boolean update = packageListService.cancelPayTask(currentUser, currentCompany, UIDList.get(i));
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber=packageListService.getPayTaskCount(currentUser,currentCompany);
        message=message+"You have Cancel "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        return message;
    }






    //对单多条件查找
    @RequestMapping("/readAllNeedCheckPackagesForCompanyByCondition")
    public String readAllNeedCheckPackagesForCompanyByCondition(Model model ,HttpServletRequest request ){
        //获取当前用户对象所属的公司
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        PackageListBo bo = new PackageListBo();

        if(request.getParameter("id") != null && !"".equals(request.getParameter("id")))
            bo.setId(Integer.parseInt(request.getParameter("id")));

        bo.setUserName(request.getParameter("userName"));
        bo.setPrice(request.getParameter("price"));
        bo.setBrand(request.getParameter("brand"));
        bo.setUpc(request.getParameter("upc"));
        if(request.getParameter("status") != null && !"".equals(request.getParameter("status")))
            bo.setStatus(Integer.parseInt(request.getParameter("status")));

        bo.setShippingAddress(request.getParameter("shippingAddress"));

        if(request.getParameter("currentPage") != null && !"".equals(request.getParameter("currentPage")))
            bo.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));

        System.out.println(bo);
        List<PackageList> packages=new ArrayList<PackageList>();
        Map<String , Object > map = packageListService.findAllUnCheckedPackForCompanyByCondition(bo , currentCompany.getCompanyName());
        packages = (List<PackageList>) map.get("data");
        for(PackageList packageList : packages){
            System.out.println(packageList);
        }
        model.addAttribute("listmap" ,packages);

        Long totalCount = (long) map.get("totalCount");
        int total = totalCount.intValue();
        bo.setTotalCount(total);
        Double totalPage = Math.ceil((total * 1.0 )/ bo.getPageSize());
        bo.setTotalPage(totalPage.intValue());
        model.addAttribute("vo" , bo);
        return "EbizCompanyProducts/allUnCheckedPackForCompany.html";
    }









    //在对单页面领取任务(可以单个领取也可以批量领取)
    @RequestMapping("/taskOneOrMoreCheckTask")
    @ResponseBody
    public String taskOneOrMoreCheckTask(HttpServletRequest request){
        int checkTaskLimit=50;
        int failednumber=0;//领取失败的任务量
        int sucessednumber=0;//领取成功的任务量
        String message="";//要返回的信息
        String uIDsString = (String) request.getParameter("packageid");
        //获取当前用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        if(uIDsString.endsWith(";")){
            uIDsString=uIDsString.substring(0,uIDsString.length()-1);
        }
        String[] uidStrings=uIDsString.split(";");
        ArrayList<Integer> UIDList=new ArrayList<>();
        for(int i=0;i<uidStrings.length;i++){
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        //通过当前数据该公司的该人领取的任务量
        int currentTaskNumber=packageListService.getCheckTaskCount(currentUser,currentCompany);
        //遍历该要领取的任务的任务id
        for(int i=0;i<UIDList.size();i++){
            //如果领取的任务量少于上线
            if (currentTaskNumber < checkTaskLimit) {
                //判断该任务是否已经被领取
                boolean update =  packageListService.takeCheckTasks(currentUser, currentCompany, UIDList.get(i));
                //如果领取成功
                if (update) {
                    message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    //领取失败
                    message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                    failednumber++;
                }
            }else{
                message=message+"Totally you have add "+sucessednumber+" tasks; \n";
                message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }else{
            message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        }
        return message;
    }


    //批量领取Label任务（和单个领取用户的区别在于：一个是通过领取的包裹的用户名来查找到对应的所有的Label任务，然后取出id，和批量领取的任务执行相同的操作，不同在于接受参数一个是ids一个是uername）
    @RequestMapping("/takeLabelTasks")
    public String takeLabelTasks(HttpServletRequest request){
        int checkTaskLimit=50;
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String uIDsString = (String) request.getParameter("packagesUID");
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

        int currentTaskNumber=packageListService.getLabelTaskCount(currentUser,currentCompany);
        for(int i=0;i<UIDList.size();i++){
            if (currentTaskNumber < checkTaskLimit) {
                boolean update = packageListService.takeLabelTasks(currentUser, currentCompany, UIDList.get(i));
                if (update) {
                    message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                    failednumber++;
                }
            }else{
                message=message+"Totally you have add "+sucessednumber+" tasks; \n";
                message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }else{
            message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        }
        return message;
    }


    //领取该用户（单个Label）
    @RequestMapping("/takeOneUserLabelTask")
    @ResponseBody
    public String takeOneUserLabelTask(HttpServletRequest request , Model model){
        int checkTaskLimit=50;
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String pickedUserName = (String) request.getParameter("packageUserName");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        //获取该公司下的该用户的label
        List<PackageList> packages = packageListService.readNeedLabeledPackForUser(currentUser,currentCompany,pickedUserName);

        System.out.println("------开始测试-------");
        for(PackageList packageList : packages){
            System.out.println("id = " + packageList.getId());
        }
        System.out.println("------结束测试-------");



        int currentTaskNumber = packageListService.getLabelTaskCount(currentUser,currentCompany);
        for(int i=0;i<packages.size();i++){
            if (currentTaskNumber < checkTaskLimit) {
                boolean update = packageListService.takeLabelTasks(currentUser, currentCompany, packages.get(i).getId());
                if (update) {
                    message = message + "UID " + packages.get(i).getId() + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    message = message + "UID " + packages.get(i).getId() + " Update Failed \n";
                    failednumber++;
                }
            }else{
                message=message+"Totally you have add "+sucessednumber+" tasks; \n";
                message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }else{
            message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        }
        return message;
    }

    //领取用户支付任务
    @RequestMapping("/takeOneUserPayTask")
    @ResponseBody
    public String takeOneUserPayTask(HttpServletRequest request){
        int checkTaskLimit=50;
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String pickedUserName = (String) request.getParameter("packageUserName");
        System.out.println(pickedUserName);
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages = packageListService.readNeedPaidPackForUser(currentUser,currentCompany,pickedUserName);

        int currentTaskNumber=packageListService.getPayTaskCount(currentUser,currentCompany);
        if(packages != null && packages.size() > 0){
            for(int i=0;i<packages.size();i++){
                if (currentTaskNumber < checkTaskLimit) {
                    boolean update = packageListService.takePayTasks(currentUser, currentCompany, packages.get(i).getId());
                    if (update) {
                        message = message + "UID " + packages.get(i).getId() + " Update Sucessfully \n";
                        sucessednumber++;
                        currentTaskNumber++;
                    } else {
                        message = message + "UID " + packages.get(i).getId() + " Update Failed \n";
                        failednumber++;
                    }
                }else{
                    message=message+"Totally you have add "+sucessednumber+" tasks; \n";
                    message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";
                }
            }
        }

        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }else{
            message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        }
        return message;
    }


    //批量领取支付任务通过ids
    @RequestMapping("/takePayTasks")
    @ResponseBody
    public String takePayTasks(HttpServletRequest request){
        int checkTaskLimit=50;
        String message="";
        int failednumber=0;
        int sucessednumber=0;
        String uIDsString = (String) request.getParameter("packagesUID");
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

        int currentTaskNumber=packageListService.getPayTaskCount(currentUser,currentCompany);
        for(int i=0;i<UIDList.size();i++){
            if (currentTaskNumber < checkTaskLimit) {
                boolean update = packageListService.takePayTasks(currentUser, currentCompany, UIDList.get(i));
                if (update) {
                    message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                    failednumber++;
                }
            }else{
                message=message+"Totally you have add "+sucessednumber+" tasks; \n";
                message=message+"You have reached the maxmum task number, please finish your task before you take more; \n";
            }
        }
        if (failednumber!=0){
            if(failednumber==1){
                message=message+"You have "+failednumber+" Package Update Failed Please Try Again (click submit button) \n";
            }else {
                message=message+"You have "+failednumber+" Packages Update Failed Please Try Again (click submit button) \n";
            }
        }else{
            message=message+"You have taken "+sucessednumber+" tasks, you current task number is: "+currentTaskNumber+"; \n";
        }
        return message;
    }


    //进入到发送邮件的页面
    @RequestMapping("/toSendEmailPage")
    public String toSendEmailPage(){
        return "EbizCompanyProducts/sendEmail.html";
    }


    //发送邮件的操作
    @RequestMapping("/sendEmailtoOneEmailAddress")
    @ResponseBody
    public String sendEmailtoOneEmailAddress(Model model , HttpServletRequest request) throws  Exception{
        String message = "";
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String emailAddress = request.getParameter("emailAddress");
        String emailTitle = request.getParameter("emailTitle");
        String emailContent = request.getParameter("emailContent");
        emailContent = emailContent + "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";

        emailContent=emailContent.replace("\n", "<br>");
        List<File> uploadedFiles = saveUploadedFiles(request, company);
        List<String> chosenFileStrings = new ArrayList<>();
        for (int i = 0; i < uploadedFiles.size(); i++) {
            chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
        }

        try {
            if (EmailSenderCenter.getInstance().sendEmailtoOneRecipientFromCompany(company, emailAddress, emailTitle,
                    emailContent, chosenFileStrings)) {
                message = message + "Email Send Sucessfully; \n";
            } else {
                message = message + "Send Email Failed, Please Try Again; \n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            deleteUploadFiles(uploadedFiles);
        }
        return message;
    }





    /**
     * Saves files uploaded from the client and return a list of these files
     * which will be attached to the e-mail message.
     */
    private List<File> saveUploadedFiles(HttpServletRequest request, EbizCompany company)
            throws IllegalStateException, IOException, ServletException {
        List<File> listFiles = new ArrayList<File>();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        Collection<Part> multiparts = request.getParts();
        if (multiparts.size() > 0) {
            for (Part part : request.getParts()) {
                // creates a file to be saved
                String fileName = extractFileName(part);
                if (fileName == null || fileName.equals("")) {
                    // not attachment part, continue
                    continue;
                }
                String temp = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000).replace(":", "")
                        .replace("-", "").replace(" ", "") + "_" + fileName;
                String time=Integer.toString(GeneralMethod.getYear(System.currentTimeMillis()));
                String directoryName = Constant.FilePath + "\\" + company.getCompanyName()+"\\"+time;
                File directory = new File(directoryName);
                if (!directory.exists()) {
                    directory.mkdirs();
                    // If you require it to make the entire directory path
                    // including parents,
                    // use directory.mkdirs(); here instead.
                }

                File saveFile = new File(directoryName, temp);
                System.out.println("saveFile: " + saveFile.getAbsolutePath());
                FileOutputStream outputStream = new FileOutputStream(saveFile);

                // saves uploaded file
                InputStream inputStream = part.getInputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();

                listFiles.add(saveFile);
            }
        }
        return listFiles;
    }

    /**
     * Retrieves file name of a upload part from its HTTP header
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    /**
     * Deletes all uploaded files, should be called after the e-mail was sent.
     */
    private void deleteUploadFiles(List<File> listFiles) {
        if (listFiles != null && listFiles.size() > 0) {
            for (File aFile : listFiles) {
                aFile.delete();
            }
        }
    }

}
