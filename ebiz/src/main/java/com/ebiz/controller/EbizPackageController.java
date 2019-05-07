package com.ebiz.controller;

import com.ebiz.SpringContextUtils;
import com.ebiz.common.Constant;
import com.ebiz.common.ebizEnum.*;
import com.ebiz.controller.model.LabelPackage;
import com.ebiz.controller.model.PackageCondition;
import com.ebiz.controller.model.PayPackage;
import com.ebiz.dao.PackageListMapper;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.*;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.EbizPackageService;
import com.ebiz.service.EbizProductService;
import com.ebiz.service.OperationRecordService;
import com.ebiz.utils.FileUtil;
import com.ebiz.utils.GeneralMethod;
import com.ebiz.utils.SendEmailUtil;
import com.ebiz.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ebiz.common.Constant.SESSION_KEY_COMPANY;
import static com.ebiz.common.Constant.SESSION_KEY_USER;

@RestController
@RequestMapping("/package")
public class EbizPackageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EbizPackageController.class);

    @Autowired
    private EbizPackageService ebizPackageService;

    @Autowired
    private PackageListMapper packageListMapper;

    @Autowired
    private EbizProductService ebizProductService;

    @Autowired
    private OperationRecordService operationRecordService;

    @Autowired
    private ProductListMapper productListMapper;

    @Autowired
    private EBizUserService eBizUserService;

    //上面的更新包裹信息的方法会用到
    public static int count(String s, String key) {
        int count = 0;
        int d = 0;
        while ((d = s.indexOf(key, d)) != -1) {
            s = s.substring(d + key.length());
            count++;
        }
        return count;
    }

    @PostMapping("/update")
    public ResultData update(PackageList record) {
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(Constant.SESSION_KEY_USER);
        PackageList oldRecord = packageListMapper.selectByPrimaryKey(record.getId());
        PackageList newRecord = new PackageList();
        newRecord.setId(record.getId());
        if ("InStock".equals(record.getStatus())) {
            LOGGER.info("更新status");
            newRecord.setStatus("InStock");
        }
        if (record.getQuantity() < oldRecord.getQuantity()) {
            LOGGER.info("更新Quantity为" + record.getQuantity());
            newRecord.setQuantity(record.getQuantity());
        }
        if (StringUtils.isNotEmpty(record.getCreditCardNumber())) {
            LOGGER.info("更新CreditCardNumber为" + record.getCreditCardNumber());
            newRecord.setCreditCardNumber(record.getCreditCardNumber());
        }
        packageListMapper.updateByPrimaryKeySelective(newRecord);
        LOGGER.info("更新成功 userName" + currentUser.getUserName());
        operationRecordService.addOperationRecord(currentUser, "packageList", EbizOperationNameEnum.UpdateColumn);

        return new ResultData(ResultState.SUCCESS);
    }

    @GetMapping("/{id}")
    public ResultData get(@PathVariable(name = "id") int id) {
        PackageList packageList = packageListMapper.selectByPrimaryKey(id);
        return new ResultData(packageList);
    }

    //上面的方法用到
    public static String getResponseResult(int status, List<String> message) {
        String re = "";
        for (int i = 0; i < message.size(); i++) {
            re = re + message.get(i) + "\n";
        }
        re = re + "Above tracking have been reported, Please check or contact admistrator";
        return re;
    }

    /**
     * 获取所有的包裹
     *
     * @param pageIndex 起始页 1,2,3...
     * @param pageSize  一页数量
     * @param record    实体类
     * @return 列表
     */
    @PostMapping("/list")
    public ResultData search(PackageList record,
                             @RequestParam int pageIndex,
                             @RequestParam int pageSize, HttpServletRequest request) {
        PackageListExample example = new PackageListExample();
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany company = (EbizCompany) request.getSession().getAttribute(SESSION_KEY_COMPANY);
        PackageListExample.Criteria criteria = example.createCriteria();
        if (record != null) {
            if (record.getId() != null) {
                criteria.andIdEqualTo(record.getId());
            }
            if (StringUtils.isNotEmpty(record.getUserName())) {
                criteria.andUserNameLike(record.getUserName());
            }
            if (StringUtils.isNotEmpty(record.getBrand())) {
                criteria.andBrandLike(record.getBrand());
            }
            if (StringUtils.isNotEmpty(record.getModelNumber())) {
                criteria.andModelNumberEqualTo(record.getModelNumber());
            }
            if (StringUtils.isNotEmpty(record.getTrackingNumber())) {
                criteria.andTrackingNumberLike(record.getTrackingNumber());
            }
            if (StringUtils.isNotEmpty(record.getUPC())) {
                criteria.andUPCLike(record.getUPC());
            }
            if (StringUtils.isNotEmpty(record.getCompanyName())) {
                criteria.andCompanyNameLike(record.getCompanyName());
            }
            if (StringUtils.isNotEmpty(record.getStatus())) {
                criteria.andStatusEqualTo(record.getStatus());
            }
            if (StringUtils.isNotEmpty(record.getPayStatus())) {
                criteria.andPayStatusEqualTo(record.getPayStatus());
            }
            if (StringUtils.isNotEmpty(record.getShippingAddress())) {
                criteria.andShippingAddressEqualTo(record.getShippingAddress());
            }
            if (StringUtils.isNotEmpty(request.getParameter("type"))) {
                String type = request.getParameter("type");
                List<String> statusList = new ArrayList<>();
                String lingqu = request.getParameter("lingqu");
                switch (type) {
                    case "allPackForCompany"://包裹管理
                        criteria.andStatusNotEqualTo("Deleted");
                        criteria.andCompanyNameEqualTo(company.getCompanyName());
                        break;
                    case "UnLabel"://发送label
                        if (lingqu != null && !"".equals(lingqu)) {
                            if ("1".equals(lingqu)) {
                                //已经领取label
                                criteria.andLabelerEqualTo(EbizPackageLabelStatusEnum.MakingLable.getName());
                                criteria.andLabelerEqualTo(currentUser.getUserName());
                            } else if ("2".equals(lingqu)) {
                                //已经完成的label
                                criteria.andLabelerEqualTo(EbizPackageLabelStatusEnum.MadeLabel.getName());
                                criteria.andLabelerEqualTo(currentUser.getUserName());
                                criteria.andCompanyNameEqualTo(company.getCompanyName());
                            } else {
                                //未领取(默认)
                                List<String> labelStatusList = new ArrayList<>();
                                labelStatusList.add("UnMade");
                                labelStatusList.add("");
                                labelStatusList.add(null);
                                criteria.andLabelStatusIn(labelStatusList);
                                criteria.andCompanyNameEqualTo(company.getCompanyName());
                            }
                        }
                        break;
                    case "NeedPaid"://支付
                        statusList.add("Deleted");
                        statusList.add("UnReceived");
                        criteria.andStatusNotIn(statusList);
                        criteria.andCompanyNameEqualTo(company.getCompanyName());

                        if (lingqu != null && !"".equals(lingqu)) {
                            if ("1".equals(lingqu)) {
                                //已经领取
                                criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paying.getName());
                                criteria.andPayerEqualTo(currentUser.getUserName());
                            } else if ("2".equals(lingqu)) {
                                //已经完成的
                                criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paid.getName());
                                criteria.andPayerEqualTo(currentUser.getUserName());
                            } else {
                                //未领取(默认)
                                List<String> payStatusList = new ArrayList<>();
                                payStatusList.add("UnPaid");
                                payStatusList.add("PartlyPaid");
                                payStatusList.add(null);
                                criteria.andPayStatusIn(payStatusList);
                            }
                        }
                        break;
                    case "UnChecked"://对单
                        statusList.add("Delivered");
                        statusList.add("EmailedLabel");
                        statusList.add("Shipped");
                        criteria.andStatusIn(statusList);
                        criteria.andCompanyNameEqualTo(company.getCompanyName());
                        break;
                    case "unReceivedAndUnConfirmed":
                        statusList.add("UnConfirmed UnPlaced");
                        statusList.add("UnConfirmed UnReceived");
                        statusList.add("UnConfirmed InStock");
                        statusList.add("Refused");
                        criteria.andStatusIn(statusList);
                        criteria.andUserNameEqualTo(currentUser.getUserName());
                        break;
                    case "UnPaid":
                        criteria.andStatusNotEqualTo("Deleted");
                        criteria.andPayStatusNotEqualTo(EbizPackagePayStatusEnum.Paid.getName());
                        criteria.andUserNameEqualTo(currentUser.getUserName());
                        break;
                    case "packing":
                        criteria.andStatusEqualTo(EbizPackageStatusEnum.EmailedLabel.getColumnName());
                        criteria.andUserNameEqualTo(currentUser.getUserName());
                        break;
                }
            }
        }
        //初始化分页对象 用于前台展示
        int count = packageListMapper.countByExample(example);
        PageSplitHelper helper = new PageSplitHelper(pageIndex, pageSize);
        helper.setTotalCount(count);
        //查询数据
        example.setPageSize(pageSize);
        example.setPageIndex(pageIndex);
        List<PackageList> packageLists = packageListMapper.selectByExample(example);
        //返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("data", packageLists);
        map.put("page", helper);
        return new ResultData(map);
    }

    @DeleteMapping("/{id}")
    public ResultData delete(@PathVariable(name = "id") int id) {
        PackageList pack = packageListMapper.selectByPrimaryKey(id);
        EbizCompany currentCompany = (EbizCompany) SpringContextUtils.getSession().getAttribute(Constant.SESSION_KEY_COMPANY);
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(Constant.SESSION_KEY_USER);
        ProductList product = ebizProductService.findProduct(currentCompany.getCompanyName(), pack.getModelNumber());
        if (product == null) {
            return new ResultData(ResultState.BIZ_FAIL, "不能删除");
        }
        int newQuantity = product.getTickets() + pack.getQuantity();

        LOGGER.info("开始删除 包裹 id:" + id);
        PackageList deletePackage = new PackageList();
        deletePackage.setId(pack.getId());
        deletePackage.setStatus("Deleted");
        packageListMapper.updateByPrimaryKeySelective(deletePackage);
        LOGGER.info("删除 包裹 成功 id:" + id);
        operationRecordService.addOperationRecord(currentUser, "packageList", EbizOperationNameEnum.DeleteRow, id);
        LOGGER.info("添加操作记录成功");

        //tickets returnd back to product list
        ProductList newProduct = new ProductList();
        newProduct.setId(product.getId());
        newProduct.setTickets(newQuantity);
        LOGGER.info("更新商品 newQuantity" + newQuantity
                + "  oldQuantity:" + pack.getQuantity()
                + "当前商品 Tickets" + product.getTickets());
        productListMapper.updateByPrimaryKeySelective(newProduct);
        LOGGER.info("更新商品成功");
        operationRecordService.addUpdateOperationRecord(
                currentUser, currentCompany, "productList", "Tickets", product.getId(), newQuantity + "");
        LOGGER.info("更新操作记录成功");
        //TODO email both here
        return ResultData.SUCCESS;
    }

    /**
     * 通过ids查找包裹(发送label给用户会用到)
     *
     * @param request
     * @return
     */
    @RequestMapping("/findPackageByIds")
    public Map findPackageByIds(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        LabelPackage labelPackage = new LabelPackage();
        String packageIds = request.getParameter("packageIDStrings");
        labelPackage.setPackageIDStrings(packageIds);
        String[] uidStrings = packageIds.split(";");
        String uidNewString = "";
        List<Integer> uidList = new ArrayList<Integer>();
        for (int i = 0; i < uidStrings.length; i++) {
            if (uidStrings[0].length() > 0) {
                uidList.add(Integer.parseInt(uidStrings[i]));
                uidNewString = uidNewString + uidStrings[i] + " ";
            }
        }
        uidNewString = uidNewString.substring(0, uidNewString.length() - 1);
        labelPackage.setUidNewString(uidNewString);
        List<PackageList> packs = ebizPackageService.findPackages(uidList);
        String userName = "";
        String email = "";
        if (packs.size() > 0) {
            userName = packs.get(0).getUserName();
            email = packs.get(0).getEmail();
        }
        labelPackage.setUserName(userName);
        labelPackage.setEmail(email);
        double value = 0;
        String packageListInfor = "";
        for (int i = 0; i < packs.size(); i++) {
            packageListInfor = packageListInfor + "UID: " + packs.get(i).getId() + ", " + packs.get(i).getQuantity() + " units, " + packs.get(i).getUPC() + ", " + packs.get(i).getProductName() + "\n";
        }
        labelPackage.setPackageListInfor(packageListInfor);
        List<EbizUser> thisUsers = eBizUserService.getEbizUsersByName(userName);
        double balance = thisUsers.get(0).getBalance();
        EbizUser thisUser = thisUsers.get(0);
        String shippingAddress = thisUser.getAddress();
        labelPackage.setShippingAddress(shippingAddress);
        String lastName = thisUser.getLastName();
        labelPackage.setLastName(lastName);
        String firstName = thisUser.getFirstName();
        labelPackage.setFirstName(firstName);
        request.getSession().setAttribute("packList", packs);
        request.getSession().setAttribute("opUser", thisUser);
        ResultData resultData = new ResultData(labelPackage, ResultState.SUCCESS, "获取数据成功");
        map.put("data", resultData);
        return map;
    }

    /**
     * 发送Label菜单中用到
     *
     * @param request
     * @return
     */
    @RequestMapping("/sendLabeltoOneRecipient")
    public Map sendLabeltoOneRecipient(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String message = "";
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");

        String emailContentStringFromWeb = request.getParameter("emailContent");
        String emailcontant = "";
        String trackingInfor = request.getParameter("trackingInfor");
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
            emailcontant = emailcontant + "UID: " + packs.get(i).getId() + ", " + packs.get(i).getModelNumber() + ", " + packs.get(i).getQuantity() + " units, " + packs.get(i).getProductName() + "\n";
        }

        String tracking = trackingInfor.replace("\n", "?");
        tracking = tracking.replace(" ", "?");
        tracking = tracking.replace(",", "?");
        tracking = tracking.replace(";", "?");
        while (tracking.endsWith("?")) {
            tracking = tracking.substring(0, tracking.length() - 1);
        }

        String shipid = shipIdString.replace("\n", "?");
        shipid = shipid.replace(" ", "?");
        shipid = shipid.replace(",", "?");
        shipid = shipid.replace(";", "?");
        while (shipid.endsWith("?")) {
            shipid = shipid.substring(0, shipid.length() - 1);
        }

        if (!bb) {
            for (int i = 0; i < packs.size(); i++) {
                try {
                    ebizPackageService.updatePackageTrackingLabelStatusAndLabeler(currentUser, company,
                            packs.get(i).getId(), tracking, shipid);
                    message = message + packs.get(i).getId() + " Update Sucessfully;\n";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = message + packs.get(i).getId() + " Update Failed;\n";
                }
            }
            String emailContentString;
            emailContentString = "Dear " + ebizUser.getUserName() + "\n\n" + emailcontant + "\n" + emailContentStringFromWeb + "\n" + "\n" + "attached are labels" + "\n" + "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";
            ;
            emailContentString = emailContentString.replace("\n", "<br>");
            String emailTitleString;
            emailTitleString = "Shipping request: uid  " + uidString;
            List<File> uploadedFiles = FileUtil.saveUploadedFiles(request, company);
            List<String> chosenFileStrings = new ArrayList<>();
            for (int i = 0; i < uploadedFiles.size(); i++) {
                chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
            }
            try {
                if (SendEmailUtil.sendEmailtoOneRecipientFromCompany(company.getEmail(), company.getEmailPassword(), emailAddress,
                        emailTitleString, emailContentString, chosenFileStrings)) {
                    message = message + "Email Send Sucessfully; \n";
                    ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                    map.put("data", resultData);
                    return map;
                } else {
                    message = message + "Send Email Failed, Please Try Again; \n";
                    ResultData resultData = new ResultData(ResultState.FAIL, message);
                    map.put("data", resultData);
                    return map;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ResultData resultData = new ResultData(ResultState.FAIL, "发送邮件出现异常");
                map.put("data", resultData);
                return map;
            } finally {
                FileUtil.deleteUploadFiles(uploadedFiles);
            }
        } else {
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
            return map;
        }
    }

    /**
     * 单个或者批量取消label任务
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancelGroupTasks")
    public Map cancelGroupTasks(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int checkTaskLimit = 50;
        String message = "";
        int failednumber = 0;
        int sucessednumber = 0;
        String uIDsString = (String) request.getParameter("packagesUID");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        if (uIDsString.endsWith(";")) {
            uIDsString = uIDsString.substring(0, uIDsString.length() - 1);
        }
        String[] uidStrings = uIDsString.split(";");
        ArrayList<Integer> UIDList = new ArrayList<>();
        for (int i = 0; i < uidStrings.length; i++) {
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        for (int i = 0; i < UIDList.size(); i++) {

            boolean update = ebizPackageService.cancelLabelTask(currentUser, currentCompany, UIDList.get(i));
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber != 0) {
            if (failednumber == 1) {
                message = message + "You have " + failednumber + " Package Update Failed Please Try Again (click submit button) \n";
            } else {
                message = message + "You have " + failednumber + " Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber = ebizPackageService.getLabelTaskCount(currentUser, currentCompany);
        message = message + "You have Cancel " + sucessednumber + " tasks, you current task number is: " + currentTaskNumber + "; \n";
        if (failednumber != 0) {
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
        } else {
            ResultData resultData = new ResultData(ResultState.SUCCESS, message);
            map.put("data", resultData);
        }
        return map;
    }

    public Map takeOneUserLabelTask(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int checkTaskLimit = 50;
        String message = "";
        int failednumber = 0;
        int sucessednumber = 0;
        String pickedUserName = (String) request.getParameter("packageUserName");
        System.out.println(pickedUserName);
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages = ebizPackageService.readNeedLabeledPackForUser(currentUser, currentCompany, pickedUserName);

        int currentTaskNumber = ebizPackageService.getLabelTaskCount(currentUser, currentCompany);
        for (int i = 0; i < packages.size(); i++) {
            if (currentTaskNumber < checkTaskLimit) {
                boolean update = ebizPackageService.takeLabelTasks(currentUser, currentCompany, packages.get(i).getId());
                if (update) {
                    message = message + "UID " + packages.get(i).getId() + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    message = message + "UID " + packages.get(i).getId() + " Update Failed \n";
                    failednumber++;
                }
            } else {
                message = message + "Totally you have add " + sucessednumber + " tasks; \n";
                message = message + "You have reached the maxmum task number, please finish your task before you take more; \n";
            }

        }
        if (failednumber != 0) {
            if (failednumber == 1) {
                message = message + "You have " + failednumber + " Package Update Failed Please Try Again (click submit button) \n";
            } else {
                message = message + "You have " + failednumber + " Packages Update Failed Please Try Again (click submit button) \n";
            }
        } else {
            message = message + "You have taken " + sucessednumber + " tasks, you current task number is: " + currentTaskNumber + "; \n";
        }
        if (failednumber != 0) {
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
        } else {
            ResultData resultData = new ResultData(ResultState.SUCCESS, message);
            map.put("data", resultData);
        }
        return map;
    }

    /**
     * 通过包裹ID查找包裹详情
     *
     * @param
     * @return
     */
    @RequestMapping("/findPackageById")
    public Map findPackageById(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String id = request.getParameter("packageId");
        if (id != null) {

            try {
                PackageList packageList = ebizPackageService.findPackageById(Integer.parseInt(id));
                ResultData resultData = new ResultData(packageList, ResultState.SUCCESS, "通过ID查找成功");
                map.put("data", resultData);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                ResultData resultData = new ResultData(ResultState.FAIL, "通过ID查找失败");
                map.put("data", resultData);
                return map;
            }
        }
        ResultData resultData = new ResultData(ResultState.FAIL, "ID不能为空");
        map.put("data", resultData);
        return map;
    }

    //执行更新操作
    @RequestMapping("/edit")
    public Map editPackage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String productModel = (String) request.getParameter("modelNumber").trim();
        String productName = (String) request.getParameter("productName").replace(";", "").replace(",", "").trim();
        String productBrand = (String) request.getParameter("brand").trim();
        String ASIN = (String) request.getParameter("asin").trim();
        String UPC = (String) request.getParameter("upc").trim();
        String SKU = (String) request.getParameter("sku").trim();
        String trackingNumber = (String) request.getParameter("trackingNumber").trim();
        String shipID = (String) request.getParameter("shipID").trim();
        String note = (String) request.getParameter("note").trim();
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        double price = Double.parseDouble(request.getParameter("price"));
        String address = (String) request.getParameter("address").trim();
        String creditCard = (String) request.getParameter("creditCardNumber").trim();
        String shipstatus = (String) request.getParameter("status");
        String payStatus = (String) request.getParameter("payStatus");

        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        String uidString = request.getParameter("id");

        int uid = Integer.parseInt(uidString);
        PackageList pack = ebizPackageService.findPackageById(uid);
        boolean update = true;
        String message = "";

        if (pack.getModelNumber() == null || (pack.getModelNumber() != null && !pack.getModelNumber().equals(productModel))) {
            try {
                ebizPackageService.updatePackageModel(currentUser, currentCompany, pack.getId(), productModel);
                pack.setModelNumber(productModel);
                message = message + "ProductModel Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "ProductModel Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getBrand() == null || (pack.getBrand() != null && !pack.getBrand().equals(productBrand))) {
            try {
                ebizPackageService.updatePackageBrand(currentUser, currentCompany, pack.getId(), productBrand);
                pack.setBrand(productBrand);
                message = message + "Product Brand Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Product Brand Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getPayStatus() == null || (pack.getPayStatus() != null && !pack.getPayStatus().equals(payStatus))) {
            try {
                ebizPackageService.updatePackagePayStatus(currentUser, currentCompany, pack.getId(), payStatus);
                pack.setPayStatus(payStatus);
                message = message + "Pay Status Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Pay Status Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getStatus() == null || (pack.getStatus() != null && !pack.getStatus().equals(shipstatus))) {
            try {
                ebizPackageService.updatePackageStatus(currentUser, currentCompany, pack.getId(), shipstatus);
                pack.setStatus(shipstatus);
                message = message + "Status Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Status Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getProductName() == null || (pack.getProductName() != null && !pack.getProductName().equals(productName))) {
            try {
                ebizPackageService.updatePackageName(currentUser, currentCompany, pack.getId(), productName);
                pack.setProductName(productName);
                message = message + "Product Name Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Product Name Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getQuantity() != quantity) {
            try {
                ebizPackageService.updatePackageQuantity(currentUser, currentCompany, pack.getId(), quantity);
                pack.setQuantity(quantity);
                message = message + "Product Quantity Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Product Quantity Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getPrice() != price) {
            try {
                ebizPackageService.updatePackagePrice(currentUser, currentCompany, pack.getId(), price);
                pack.setPrice(price);
                message = message + "Product Price Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Product Price Update Failed,Please Try Again \n";
                update = false;
            }
        }

        if (pack.getSKU() == null || (pack.getSKU() != null && !pack.getSKU().equals(SKU))) {
            try {
                ebizPackageService.updatePackageSKU(currentUser, currentCompany, pack.getId(), SKU);
                pack.setSKU(SKU);
                message = message + "SKU Information Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "SKU Information Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getUPC() == null || (pack.getUPC() != null && !pack.getUPC().equals(UPC))) {
            try {
                ebizPackageService.updatePackageUPC(currentUser, currentCompany, pack.getId(), UPC);
                pack.setUPC(UPC);
                message = message + "UPC Information Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "UPC Information Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getASIN() == null || (pack.getASIN() != null && !pack.getASIN().equals(ASIN))) {
            try {
                ebizPackageService.updatePackageASIN(currentUser, currentCompany, pack.getId(), ASIN);
                pack.setASIN(ASIN);
                message = message + "ASIN Information Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "ASIN Information Update Failed,Please Try Again \n";
                update = false;
            }
        }

        if (pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditCard))) {
            try {
                ebizPackageService.updatePackageCreditCard(currentUser, currentCompany, pack.getId(), creditCard);
                pack.setCreditCardNumber(creditCard);
                message = message + "CreditCard Information Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "CreditCard Information Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getShippingAddress() == null || (pack.getShippingAddress() != null && !pack.getShippingAddress().equals(address))) {
            try {
                ebizPackageService.updatePackageAddress(currentUser, currentCompany, pack.getId(), address);
                pack.setShippingAddress(address);
                message = message + "Address Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Address Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getTrackingNumber() == null || (pack.getTrackingNumber() != null && !pack.getTrackingNumber().equals(trackingNumber))) {
            try {
                ebizPackageService.updatePackageTracking(currentUser, currentCompany, pack.getId(), trackingNumber);
                pack.setTrackingNumber(trackingNumber);
                message = message + "tracking Number Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "tracking Number Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getShipID() == null || (pack.getShipID() != null && !pack.getShipID().equals(shipID))) {
            try {
                ebizPackageService.updatePackageShipId(currentUser, currentCompany, pack.getId(), shipID);
                pack.setShipID(shipID);
                message = message + "Ship ID Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Ship ID Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (pack.getNote() == null || (pack.getNote() != null && !pack.getNote().equals(note))) {
            try {
                ebizPackageService.updatePackageNote(currentUser, currentCompany, pack.getId(), note);
                pack.setNote(note);
                message = message + "Note Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Note Update Failed,Please Try Again \n";
                update = false;
            }
        }
        if (message.length() == 0) {
            message = "Nothing Updated";
        }
        if (update) {
            ResultData resultData = new ResultData(ResultState.SUCCESS, message);
            map.put("data", resultData);
        } else {
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
        }
        return map;
    }

    /**
     * 包裹管理页面的发送邮件
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendEmailToUser")
    public Map sendEmailToUser(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        request.setCharacterEncoding("UTF-8");
        String message = "";
        EbizCompany company = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String emailAddress = request.getParameter("email");
        String emailTitle = request.getParameter("emailTitle");
        //邮件内容
        String emailContent = request.getParameter("emailContent");
        String userName = request.getParameter("userName");
        //邮件最终格式
        emailContent = "Dear " + userName + "\n\n" + emailContent + "\n\nRegards\n" + currentUser.getUserName() + "(" + company.getCompanyName() + ")";

        emailContent = emailContent.replace("\n", "<br>");

        List<File> uploadedFiles = FileUtil.saveUploadedFiles(request, company);
        List<String> chosenFileStrings = new ArrayList<>();
        for (int i = 0; i < uploadedFiles.size(); i++) {
            chosenFileStrings.add(uploadedFiles.get(i).getAbsolutePath());
        }

        try {
            if (SendEmailUtil.sendEmailtoOneRecipientFromCompany(company.getEmail(), company.getEmailPassword(), emailAddress, emailTitle,
                    emailContent, chosenFileStrings)) {
                message = message + "Email Send Sucessfully; \n";
                ResultData resultData = new ResultData(ResultState.SUCCESS, "发送成功");
                map.put("data", resultData);
                return map;
            } else {
                message = message + "Send Email Failed, Please Try Again; \n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            FileUtil.deleteUploadFiles(uploadedFiles);
        }
        ResultData resultData = new ResultData(ResultState.FAIL, message);
        map.put("data", resultData);
        return map;
    }

    /**
     * 对单页面领取任务
     *
     * @return
     */
    @RequestMapping("/takeOneCheckTask")
    public Map takeOneCheckTask(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int checkTaskLimit = 50;
        String message = "";
        int failednumber = 0;
        int sucessednumber = 0;
        String uIDsString = (String) request.getParameter("packageid");
        //获取当前session中用户对象
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        //获取当前用户对象所属的公司对象
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        if (uIDsString.endsWith(";")) {
            uIDsString = uIDsString.substring(0, uIDsString.length() - 1);
        }
        String[] uidStrings = uIDsString.split(";");
        ArrayList<Integer> UIDList = new ArrayList<>();
        for (int i = 0; i < uidStrings.length; i++) {
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        int currentTaskNumber = ebizPackageService.getLabelTaskCount(currentUser, currentCompany);
        for (int i = 0; i < UIDList.size(); i++) {
            if (currentTaskNumber < checkTaskLimit) {
                boolean update = ebizPackageService.takeLabelTasks(currentUser, currentCompany, UIDList.get(i));
                if (update) {
                    message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                    sucessednumber++;
                    currentTaskNumber++;
                } else {
                    message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                    failednumber++;
                }
            } else {
                message = message + "Totally you have add " + sucessednumber + " tasks; \n";
                message = message + "You have reached the maxmum task number, please finish your task before you take more; \n";
            }
        }
        if (failednumber != 0) {
            if (failednumber == 1) {
                message = message + "You have " + failednumber + " Package Update Failed Please Try Again (click submit button) \n";
            } else {
                message = message + "You have " + failednumber + " Packages Update Failed Please Try Again (click submit button) \n";
            }
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
        } else {
            message = message + "You have taken " + sucessednumber + " tasks, you current task number is: " + currentTaskNumber + "; \n";
            ResultData resultData = new ResultData(ResultState.SUCCESS, message);
            map.put("data", resultData);
        }
        return map;
    }

    /**
     * 领取一个支付任务
     *
     * @param request
     * @return
     */
    @RequestMapping("/takeOneUserPayTask")
    public Map takeOneUserPayTask(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int checkTaskLimit = 50;
        String message = "";
        int failednumber = 0;
        int sucessednumber = 0;
        String pickedUserName = (String) request.getParameter("packageUserName");
        System.out.println(pickedUserName);
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");

        List<PackageList> packages = ebizPackageService.readNeedPaidPackForUser(currentUser, currentCompany, pickedUserName);

        int currentTaskNumber = ebizPackageService.getPayTaskCount(currentUser, currentCompany);
        if (packages != null && packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                if (currentTaskNumber < checkTaskLimit) {
                    boolean update = ebizPackageService.takePayTasks(currentUser, currentCompany, packages.get(i).getId());
                    if (update) {
                        message = message + "UID " + packages.get(i).getId() + " Update Sucessfully \n";
                        sucessednumber++;
                        currentTaskNumber++;
                    } else {
                        message = message + "UID " + packages.get(i).getId() + " Update Failed \n";
                        failednumber++;
                    }
                } else {
                    message = message + "Totally you have add " + sucessednumber + " tasks; \n";
                    message = message + "You have reached the maxmum task number, please finish your task before you take more; \n";
                }
            }
        }

        if (failednumber != 0) {
            if (failednumber == 1) {
                message = message + "You have " + failednumber + " Package Update Failed Please Try Again (click submit button) \n";
            } else {
                message = message + "You have " + failednumber + " Packages Update Failed Please Try Again (click submit button) \n";
            }
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
        } else {
            message = message + "You have taken " + sucessednumber + " tasks, you current task number is: " + currentTaskNumber + "; \n";
            ResultData resultData = new ResultData(ResultState.SUCCESS, message);
            map.put("data", resultData);
        }
        return map;
    }

    /**
     * 在支付页面获取的数据
     *
     * @return
     */
    @RequestMapping("/payPackage")
    public Map payPackage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            PayPackage payPackage = new PayPackage();
            String packageIDStrings = request.getParameter("packageIDStrings");
            payPackage.setPackageIDStrings(packageIDStrings);
            String[] uidStrings = packageIDStrings.split(";");
            String uidNewString = "";
            List<Integer> uidList = new ArrayList<Integer>();
            for (int i = 0; i < uidStrings.length; i++) {
                if (uidStrings[0].length() > 0) {
                    uidList.add(Integer.parseInt(uidStrings[i]));
                    uidNewString = uidNewString + uidStrings[i] + " ";
                }
            }
            uidNewString = uidNewString.substring(0, uidNewString.length() - 1);
            payPackage.setUidNewString(uidNewString);
            List<PackageList> packs = ebizPackageService.findPackages(uidList);
            String userName = "";
            String email = "";
            if (packs.size() > 0) {
                userName = packs.get(0).getUserName();
                email = packs.get(0).getEmail();
            }
            payPackage.setUserName(userName);
            payPackage.setEmail(email);
            double value = 0;
            String creditString = "";
            String packageListInfor = "";
            for (int i = 0; i < packs.size(); i++) {
                creditString = creditString + "UID： " + packs.get(i).getId() + ", " + " CreditCardInfor: " + packs.get(i).getCreditCardNumber() + "\n";
                if (packs.get(i).getNote().length() > 0) {
                    creditString = creditString + "Note: " + packs.get(i).getNote() + "\n";
                }
                double value1 = +packs.get(i).getQuantity() * packs.get(i).getPrice();
                packageListInfor = packageListInfor + "UID: " + packs.get(i).getId() + ", " + packs.get(i).getQuantity() + "*" + packs.get(i).getPrice() + "=" + value1 + ", " + packs.get(i).getSKU() + ", " + packs.get(i).getProductName() + "\n";
                value = value + packs.get(i).getPrice() * packs.get(i).getQuantity();
            }
            payPackage.setCreditString(creditString);
            payPackage.setValue(value);
            List<EbizUser> thisUsers = eBizUserService.getEbizUsersByName(userName);
            double balance = thisUsers.get(0).getBalance();
            double shouldpay = value + balance;
            payPackage.setBalance(balance);
            payPackage.setShouldpay(shouldpay);
            payPackage.setNowPay(shouldpay);
            packageListInfor = "Previouse Balance: " + balance + "\n" + packageListInfor;
            payPackage.setPackageListInfor(packageListInfor);
            EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
            request.getSession().setAttribute("packList", packs);
            request.getSession().setAttribute("opUser", thisUsers.get(0));
            ResultData resultData = new ResultData(payPackage, ResultState.SUCCESS, "SUCCESS");
            map.put("data", resultData);
        } catch (Exception e) {
            e.printStackTrace();
            ResultData resultData = new ResultData(ResultState.FAIL, "载入数据错误");
            map.put("data", resultData);
        }
        return map;
    }

    /**
     * 再已经领取的支付任务页面的取消任务操作
     */
    @PostMapping("/cancelPayTask")
    public ResultData cancelPayTask(@RequestParam(name = "packagesUID") String uIDsString) {
        int checkTaskLimit = 50;
        String message = "";
        int failednumber = 0;
        int sucessednumber = 0;
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        EbizCompany currentCompany = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        if (uIDsString.endsWith(";")) {
            uIDsString = uIDsString.substring(0, uIDsString.length() - 1);
        }
        String[] uidStrings = uIDsString.split(";");
        ArrayList<Integer> UIDList = new ArrayList<>();
        for (int i = 0; i < uidStrings.length; i++) {
            UIDList.add(Integer.parseInt(uidStrings[i]));
        }
        boolean flag = true;
        for (int i = 0; i < UIDList.size(); i++) {

            boolean update = ebizPackageService.cancelPayTask(currentUser, currentCompany, UIDList.get(i));
            if (update) {
                message = message + "UID " + UIDList.get(i) + " Update Sucessfully \n";
                sucessednumber++;
            } else {
                flag = false;
                message = message + "UID " + UIDList.get(i) + " Update Failed \n";
                failednumber++;
            }
        }
        if (failednumber != 0) {
            if (failednumber == 1) {
                message = message + "You have " + failednumber + " Package Update Failed Please Try Again (click submit button) \n";
            } else {
                message = message + "You have " + failednumber + " Packages Update Failed Please Try Again (click submit button) \n";
            }
        }
        int currentTaskNumber = ebizPackageService.getPayTaskCount(currentUser, currentCompany);
        message = message + "You have Cancel " + sucessednumber + " tasks, you current task number is: " + currentTaskNumber + "; \n";
        if (flag) {
            return ResultData.SUCCESS;
        } else {
            return new ResultData(ResultState.FAIL, message);
        }
    }

    //在领票或预报页面执行提交操作
    @RequestMapping("/reportPackage")
    public Map reportPackage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String productString = (String) request.getParameter("product");
        String[] productArray = productString.split(",");
        int productUID = Integer.parseInt(productArray[0]);
        // int productPersonalLimit = Integer.parseInt(productArray[2]);
        int reportingQuantity = Integer.parseInt(request.getParameter("quantity"));
        int ticket = Integer.parseInt(request.getParameter("ticket"));
        String creditCard = (String) request.getParameter("CreditCardNumber");
        String address = (String) request.getParameter("address");
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        EbizCompany currentCompany = (EbizCompany) SpringContextUtils.getSession().getAttribute(SESSION_KEY_COMPANY);
        String username = currentUser.getUserName();
        List<String> info = new ArrayList<String>();
        if (productUID == 0) {
            info.add("Please Add A Product");
            ResultData resultData = new ResultData(ResultState.FAIL, "Please Add A Product");
            map.put("data", resultData);
            return map;
        } else {
            ProductList selectedProduct = ebizProductService.findProductListById(productUID);
            if (selectedProduct == null) {
                //如果没有相关商品
                info.add("Can Not Find Product In DataBase");
                ResultData resultData = new ResultData(ResultState.FAIL, "Can Not Find Product In DataBase");
                map.put("data", resultData);
                return map;
            } else if (selectedProduct.getTickets() == 0) {
                //如果存在该商品，但是票没有票了
                info.add("No unit is needed, please try other product");
                ResultData resultData = new ResultData(ResultState.FAIL, "No unit is needed, please try other product");
                map.put("data", resultData);
                return map;
            } else if (reportingQuantity == 0) {
                //填写的要领取的票的数量为0
                info.add("Can not report 0 units, Please try again");
                ResultData resultData = new ResultData(ResultState.FAIL, "Can not report 0 units, Please try again");
                map.put("data", resultData);
                return map;
            } else {
                String modelNumber = selectedProduct.getModel();

                if (reportingQuantity > ticket) {
                    //如果当前用户想要的数量大于允许的最大量
                    info.add("You only have " + ticket + " tickets left, please contact administrator to get more");
                    ResultData resultData = new ResultData(ResultState.FAIL, "You only have " + ticket + " tickets left, please contact administrator to get more");
                    map.put("data", resultData);
                    return map;
                } else if (selectedProduct.getTickets() >= reportingQuantity) {
                    //如果没什么问题，可以进行领票了
                    int leftTickets = selectedProduct.getTickets() - reportingQuantity;//如果正常领取之后还剩这么些张票
                    //然后更新该商品的可以领取票的数量等操作
                    try {
                        ebizProductService.updateProductTicket(currentUser, currentCompany, selectedProduct.getId(), leftTickets);
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
                        String paystatus = EbizPackagePayStatusEnum.UnPaid.getName();
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
                        try {
                            ebizPackageService.addPackage(currentUser, currentCompany, tempPackage);
                            //保存成功     成功生成新的包裹
                            ResultData resultData = new ResultData(ResultState.SUCCESS, "SUCCESS");
                            map.put("data", resultData);
                            return map;
                        } catch (Exception e) {
                            e.printStackTrace();
                            //保存失败      生成新的包裹失败
                            info.add("生成新的包裹失败, Please lower the quantity and Try Again");
                            ResultData resultData = new ResultData(ResultState.FAIL, "生成新的包裹失败, Please lower the quantity and Try Again");
                            map.put("data", resultData);
                            return map;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        info.add("Please check you connection or Try Again");
                        ResultData resultData = new ResultData(ResultState.FAIL, "Please check you connection or Try Again");
                        map.put("data", resultData);
                        return map;
                    }
                } else {
                    info.add("Do not have enough Tickets Left, Please lower the quantity and Try Again");
                    ResultData resultData = new ResultData(ResultState.FAIL, "Do not have enough Tickets Left, Please lower the quantity and Try Again");
                    map.put("data", resultData);
                    return map;
                }
            }
        }
    }

    /**
     * 求购\议价
     *
     * @param request
     * @return
     */
    @RequestMapping("/sellorbo")
    public Map sellorbo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
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
            PackageList pack = ebizPackageService.findPackageById(uid);
            boolean update = false;
            boolean flag = true;
            String message = "";
            if (pack.getStatus().toLowerCase().contains("unconfirmed")) {
                if (!pack.getModelNumber().equals(productModel)) {
                    try {
                        ebizPackageService.updatePackageModel(currentUser, currentCompany, pack.getId(), productModel);
                        pack.setModelNumber(productModel);
                        message = message + "ProductModel Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "ProductModel Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (!pack.getBrand().equals(productBrand)) {
                    try {
                        ebizPackageService.updatePackageBrand(currentUser, currentCompany, pack.getId(), productBrand);
                        pack.setBrand(productBrand);
                        message = message + "Product Brand Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Brand Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (!pack.getProductName().equals(productName)) {
                    try {
                        ebizPackageService.updatePackageName(currentUser, currentCompany, pack.getId(), productName);
                        pack.setProductName(productName);
                        message = message + "Product Name Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Name Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (pack.getQuantity() != quantity) {
                    try {
                        ebizPackageService.updatePackageQuantity(currentUser, currentCompany, pack.getId(), quantity);
                        pack.setQuantity(quantity);
                        message = message + "Product Quantity Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Quantity Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (pack.getPrice() != price) {
                    try {
                        ebizPackageService.updatePackagePrice(currentUser, currentCompany, pack.getId(), price);
                        pack.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Price Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (!pack.getCreditCardNumber().equals(creditCard)) {
                    try {
                        ebizPackageService.updatePackageCreditCard(currentUser, currentCompany, pack.getId(), creditCard);
                        pack.setCreditCardNumber(creditCard);
                        message = message + "CreditCard Information Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "CreditCard Information Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (!pack.getShippingAddress().equals(address)) {
                    try {
                        ebizPackageService.updatePackageAddress(currentUser, currentCompany, pack.getId(), address);
                        pack.setShippingAddress(address);
                        message = message + "Address Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Address Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (!pack.getStatus().equals(statusString)) {
                    try {
                        ebizPackageService.updatePackageStatus(currentUser, currentCompany, pack.getId(), statusString);
                        pack.setStatus(statusString);
                        message = message + "Status Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Status Update Failed,Please Try Again \n";
                        flag = false;
                    }
                }
                if (message.length() == 0) {
                    message = "Nothing Updated";
                }
            } else {
                message = message
                        + "Package has been confirmed, you can not update it here, please update it through other way; \n";
            }
            if (flag) {
                ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                map.put("data", resultData);
                return map;
            } else {
                ResultData resultData = new ResultData(ResultState.FAIL, message);
                map.put("data", resultData);
                return map;
            }
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
            try {
                ebizPackageService.addPackage(currentUser, currentCompany, tempPackage);
                ResultData resultData = new ResultData(ResultState.SUCCESS, "Your Package Has Been Added Sucessfully");
                map.put("data", resultData);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                ResultData resultData = new ResultData(ResultState.FAIL, "Your Package Has Not Been Added, Please Try Again");
                map.put("data", resultData);
                return map;
            }
        }
    }

    /**
     * 更新信用卡信息和更新包裹页面的地址属于enum中的包裹
     *
     * @param request
     * @return
     */
    @RequestMapping("/editUnreceivedPack")
    public Map editUnreceivedPack(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String creditcard = (String) request.getParameter("creditcard");
        int uid = Integer.parseInt(request.getParameter("packageId"));
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        PackageList pack = ebizPackageService.findPackageById(uid);
        boolean myCheckBox = request.getParameter("InStock") != null;
        String statusString = "";
        if (myCheckBox) {
            statusString = EbizPackageStatusEnum.InStock.getColumnName();
        } else {
            statusString = EbizPackageStatusEnum.UnReceived.getColumnName();
        }
        double basePrice = Double.parseDouble(request.getParameter("basePrice"));
        double promPrice = Double.parseDouble(request.getParameter("promPrice"));
        int promQuantity = Integer.parseInt(request.getParameter("promQuantity"));
        double price = basePrice;
        if (quantity >= promQuantity) {
            price = promPrice;
        }
        boolean update = false;
        String message = "";
        boolean flag = true;
        if (pack.getQuantity() != quantity) {
            try {
                ebizPackageService.updatePackageQuantityAndPrice(currentUser, currentCompany, pack.getId(), quantity, price);
                pack.setQuantity(quantity);
                pack.setPrice(price);
                message = message + "Quantity Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Quantity Update Failed,Please Try Again \n";
                flag = false;
            }
        }
        if (!pack.getCreditCardNumber().equals(creditcard)) {
            try {
                ebizPackageService.updatePackageCreditCard(currentUser, currentCompany, pack.getId(), creditcard);
                pack.setCreditCardNumber(creditcard);
                message = message + "CreditCard Information Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "CreditCard Information Update Failed,Please Try Again \n";
                flag = false;
            }
        }
        if (!pack.getStatus().equals(statusString) && statusString.equals(EbizPackageStatusEnum.InStock.getColumnName())) {
            try {
                ebizPackageService.updatePackageStatus(currentUser, currentCompany, pack.getId(), statusString);
                pack.setStatus(statusString);
                message = message + "Status Update Sucessfully \n";
            } catch (Exception e) {
                e.printStackTrace();
                message = message + "Status Update Failed,Please Try Again \n";
                flag = false;
            }
        }
        if (flag) {
            if (message.length() > 0) {
                ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                map.put("data", resultData);
                return map;
            } else {
                ResultData resultData = new ResultData(ResultState.SUCCESS, "Nothing Updated");
                map.put("data", resultData);
                return map;
            }
        } else {
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
            return map;
        }
    }

    @RequestMapping("/deletePackageById")
    public Map deletePackageById(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int uid = Integer.parseInt(request.getParameter("packageId"));
        PackageList pack = ebizPackageService.findPackageById(uid);
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        ProductList product = ebizProductService.findProduct(currentCompany.getCompanyName(), pack.getModelNumber());
        int newQuantity = product.getTickets() + pack.getQuantity();
        try {
            ebizPackageService.deletePackage(currentUser, currentCompany, uid);
            //tickets returnd back to product list
            ebizProductService.updateProductTicket(currentUser, currentCompany, product.getId(), newQuantity);
            //email both here
            ResultData resultData = new ResultData(ResultState.SUCCESS, "Your Package Has Been Deleted Sucessfully");
            map.put("data", resultData);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            ResultData resultData = new ResultData(ResultState.SUCCESS, "Your Package Has Not Been Deleted, Please Try Again");
            map.put("data", resultData);
            return map;
        }
    }

    /**
     * 未发货和未确认包裹页面更行包裹信息页面（地址不在enum内的）
     *
     * @param request
     * @return
     */
    @RequestMapping("/editWareHousePackage")
    public Map editWareHousePackage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String creditcard = (String) request.getParameter("creditCardNumber");
        int uid = Integer.parseInt(request.getParameter("packageId"));
        PackageList pack = ebizPackageService.findPackageById(uid);
        String allTrackingNumber = (String) request.getParameter("trackingnumber");
        int totalQuantity = Integer.parseInt(request.getParameter("totalQuantity"));
        double basePrice = Double.parseDouble(request.getParameter("basePrice"));
        double promPrice = Double.parseDouble(request.getParameter("promPrice"));
        int promQuantity = Integer.parseInt(request.getParameter("promQuantity"));
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        double price = basePrice;
        if (quantity >= promQuantity) {
            price = promPrice;
        }
        //没有填写tracking，只是更新数量或者信用卡；
        if (totalQuantity == 0 || allTrackingNumber == null || allTrackingNumber.length() <= 5) {
            boolean update = false;
            String message = "";
            boolean flag = true;
            if (pack.getQuantity() != quantity) {
                try {
                    ebizPackageService.updatePackageQuantityAndPrice(currentUser, currentCompany, pack.getId(), quantity, price);
                    pack.setQuantity(quantity);
                    pack.setPrice(price);
                    message = message + "Quantity Update Sucessfully \n";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = message + "Quantity Update Failed, Please Try Again \n";
                    flag = false;
                }
            }
            if (pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditcard))) {
                try {
                    ebizPackageService.updatePackageCreditCard(currentUser, currentCompany, pack.getId(), creditcard);
                    pack.setCreditCardNumber(creditcard);
                    message = message + "CreditCard Information Update Sucessfully \n";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = message + "CreditCard Information Update Failed, Please Try Again \n";
                    flag = false;
                }
            }
            if (flag) {
                if (message.length() > 0) {
                    ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                    map.put("data", resultData);
                    return map;
                } else {
                    ResultData resultData = new ResultData(ResultState.SUCCESS, "No Need Update");
                    map.put("data", resultData);
                    return map;
                }
            } else {
                ResultData resultData = new ResultData(ResultState.FAIL, message);
                map.put("data", resultData);
                return map;
            }
        } else {
            //check tracking
            List<String> badTrackingList = new ArrayList<>();
            String[] trackAndQuantity = allTrackingNumber.split("\\?");
            for (int i = 0; i < trackAndQuantity.length; i++) {
                String oneTrackingString = trackAndQuantity[i].split("_")[0];
                int count = count(allTrackingNumber, oneTrackingString);
                if (count > 1) {
                    badTrackingList.add(oneTrackingString);
                    continue;
                }
                List<PackageList> packagesList = ebizPackageService.readPackagesByTracking(oneTrackingString);
                for (int j = 0; j < packagesList.size(); j++) {
                    //only the same user with different product can have same tracking;
                    if (!packagesList.get(j).getUserName().equals(pack.getUserName()) || packagesList.get(j).getModelNumber().equals(pack.getModelNumber())) {
                        // this tracking has been reported by other user or has been reported befor for the same product;
                        badTrackingList.add(oneTrackingString);
                    }
                }
            }
            if (badTrackingList.size() != 0) {
                //返回客户端结果
                String result = getResponseResult(200, badTrackingList);
                ResultData resultData = new ResultData(ResultState.SUCCESS, result);
                map.put("data", resultData);
                //将result返回客户端
                return map;
            } else {
                if (pack.getQuantity() != quantity) {
                    try {
                        ebizPackageService.updatePackageQuantityAndPrice(currentUser, currentCompany, pack.getId(), quantity, price);
                        pack.setQuantity(quantity);
                        pack.setPrice(price);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (pack.getCreditCardNumber() == null || (pack.getCreditCardNumber() != null && !pack.getCreditCardNumber().equals(creditcard))) {
                    try {
                        ebizPackageService.updatePackageCreditCard(currentUser, currentCompany, pack.getId(), creditcard);
                        pack.setCreditCardNumber(creditcard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pack.setTrackingNumber(allTrackingNumber);
                pack.setStatus(EbizPackageStatusEnum.Shipped.getColumnName());
                try {
                    ebizPackageService.updatePackageTrackingAndStatus(pack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ResultData resultData = new ResultData(ResultState.SUCCESS, "");
                map.put("data", resultData);
                //将result返回客户端
                return map;
            }
        }
    }

    //封装包裹对象
    private PackageCondition getPackageCondition(HttpServletRequest request) {
        //分页相关属性
        String pageSizeStr = request.getParameter("pageSize");
        String pageIndexStr = request.getParameter("pageIndex");
        int pageIndex = 1;
        int pageSize = 5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageIndexStr != null && !"".equals(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        //其他条件查询属性
        //1、从session中获取公司对象
        EbizCompany company = (EbizCompany) request.getSession().getAttribute(Constant.SESSION_KEY_COMPANY);
        String timeString = GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000);
        PackageCondition condition = new PackageCondition();
        condition.setPageIndex(pageIndex);
        condition.setPageSize(pageSize);
        String idStr = request.getParameter("id");
        condition.setASIN(request.getParameter("asin"));
        condition.setBrand(request.getParameter("brand"));
        condition.setCompanyName(company.getCompanyName());
        String heightStr = request.getParameter("height");
        if (heightStr != null && !"".equals(heightStr)) {
            condition.setHeight(Double.parseDouble(heightStr));
        }
        String widthStr = request.getParameter("width");
        if (widthStr != null && !"".equals(widthStr)) {
            condition.setWidth(Double.parseDouble(widthStr));
        }
        String lengthStr = request.getParameter("length");
        if (lengthStr != null && !"".equals(lengthStr)) {
            condition.setLength(Double.parseDouble(lengthStr));
        }
        String weightStr = request.getParameter("weight");
        if (weightStr != null && !"".equals(weightStr)) {
            condition.setWeight(Double.parseDouble(weightStr));
        }
        condition.setModel(request.getParameter("model"));
        String limitPerPersonStr = request.getParameter("limitPerPerson");
        if (limitPerPersonStr != null && !"".equals(limitPerPersonStr)) {
            condition.setLimitPerPerson(Integer.parseInt(limitPerPersonStr));
        }
        if (idStr != null && !"".equals(idStr)) {
            condition.setId(Integer.parseInt(idStr));
            condition.setCreatedTime(timeString);
        }
        String priceStr = request.getParameter("price");
        if (priceStr != null && !"".equals(priceStr)) {
            condition.setPrice(Double.parseDouble(priceStr));
        }
        String promotPriceStr = request.getParameter("promotPrice");
        if (promotPriceStr != null && !"".equals(promotPriceStr)) {
            condition.setPromotPrice(Double.parseDouble(promotPriceStr));
        }
        String promotQuantityStr = request.getParameter("promotQuantity");
        if (promotQuantityStr != null && !"".equals(promotQuantityStr)) {
            condition.setPromotQuantity(Integer.parseInt(promotQuantityStr));
        }
        String ticketsStr = request.getParameter("tickets");
        if (ticketsStr != null && !"".equals(ticketsStr)) {
            condition.setTickets(Integer.parseInt(ticketsStr));
        }
        String warehousePriceStr = request.getParameter("warehousePrice");
        if (warehousePriceStr != null && !"".equals(warehousePriceStr)) {
            condition.setWarehousePrice(Double.parseDouble(warehousePriceStr));
        }
        String warehousePromotePriceStr = request.getParameter("warehousePromotePrice");
        if (warehousePromotePriceStr != null && !"".equals(warehousePromotePriceStr)) {
            condition.setWarehousePromotePrice(Double.parseDouble(warehousePromotePriceStr));
        }
        String warehousePromotQuantityStr = request.getParameter("warehousePromotQuantity");
        if (warehousePromotQuantityStr != null && !"".equals(warehousePromotQuantityStr)) {
            condition.setWarehousePromotQuantity(Integer.parseInt(warehousePromotQuantityStr));
        }
        condition.setOperatingStatus(request.getParameter("payStatus"));
        condition.setOperationRecord(request.getParameter("operationRecord"));
        condition.setParameterString(request.getParameter("parameterString"));
        condition.setProductName(request.getParameter("productName"));
        condition.setSKU(request.getParameter("sku"));
        condition.setStatus(request.getParameter("status"));
        condition.setUPC(request.getParameter("upc"));
        condition.setURI(request.getParameter("uri"));
        condition.setUserNote(request.getParameter("userNote"));
        condition.setUpdateTime(timeString);
        return condition;
    }

}