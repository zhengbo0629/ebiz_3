/**
 * @(#)com.ebiz.controller.EbizProductController Copyright (c) 2014-2018 ...
 * <p>
 * DESC:
 */
package com.ebiz.controller;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.SpringContextUtils;
import com.ebiz.common.Constant;
import com.ebiz.common.ebizEnum.EbizNurseGroupTypeEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.common.ebizEnum.EbizUserPermissionEnum;
import com.ebiz.common.ebizEnum.EbizUserTypeEnum;
import com.ebiz.controller.model.ProductCondition;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.*;
import com.ebiz.service.DealListService;
import com.ebiz.service.EBizUserService;
import com.ebiz.service.EbizProductService;
import com.ebiz.utils.GeneralMethod;
import com.ebiz.utils.SendEmailCenter;
import com.ebiz.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.ebiz.common.Constant.SESSION_KEY_USER;

/**
 * @author 王润松
 * @version 1.0  2018/10/30 0030
 */
@RestController
@RequestMapping("/product")
public class EbizProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EbizProductController.class);
    @Autowired
    private EbizProductService ebizProductService;

    @Autowired
    private EBizUserService eBizUserService;

    @Autowired
    private DealListService dealListService;

    @Autowired
    private ProductListMapper productListMapper;

    @GetMapping(value = "/live-deal/list")
    public ResultData getLiveDeaProduct(ProductList record,
                                        @RequestParam int pageIndex,
                                        @RequestParam int pageSize,
                                        @RequestParam String priceLevel) {
        EbizUser currentUser = (EbizUser) SpringContextUtils.getSession().getAttribute(SESSION_KEY_USER);
        String[] userTypes = {EbizUserTypeEnum.SelfEmployedDoctor.getName(),
                EbizUserTypeEnum.Doctor.getName(),
                EbizUserTypeEnum.Administrator.getName()};
        if (!Arrays.asList(userTypes).contains(currentUser.getUserType())
                && !currentUser.getPermissions().contains(EbizUserPermissionEnum.LiveDeal.getName())) {
            return new ResultData(ResultState.BIZ_FAIL, "权限不足，请联系管理员申请权限！");
        }
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyNameEqualTo(currentUser.getCompanyName());
        criteria.andStatusEqualTo("LiveDeal");
        if (StringUtils.isNotEmpty(priceLevel)) {
            switch (priceLevel) {
                case "1":
                    criteria.andPriceBetween(0D, 200D);
                    break;
                case "2":
                    criteria.andPriceBetween(200D, 500D);
                    break;
                case "3":
                    criteria.andPriceBetween(500D, 800D);
                    break;
                case "4":
                    criteria.andPriceBetween(800D, 1000D);
                    break;
                case "5":
                    criteria.andPriceGreaterThanOrEqualTo(1000D);
                    break;
                default:
                    break;
            }
        }
        if (StringUtils.isNotEmpty(record.getModel())) {
            criteria.andModelLike("%" + record.getModel() + "%");
        }
        if (StringUtils.isNotEmpty(record.getProductName())) {
            criteria.andProductNameLike("%" + record.getProductName() + "%");
        }
        int count = productListMapper.countByExample(example);
        PageSplitHelper helper = new PageSplitHelper(pageIndex, pageSize);
        helper.setTotalCount(count);
        //查询数据
        example.setPageSize(pageSize);
        example.setPageIndex(pageIndex);
        List<ProductList> packageLists = productListMapper.selectByExample(example);
        //返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("data", packageLists);
        map.put("page", helper);
        return new ResultData(map);
    }


    /*
       产品分页
     */
    @ResponseBody
    @RequestMapping("/getProductPage")
    public String getProductPage(@RequestParam Integer pageNumber, Integer pageSize,
                                 HttpServletResponse response) {
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNumber, pageSize);
        List<ProductList> list = ebizProductService.getProductList(new ProductList());
        PageInfo pageInfo = new PageInfo(list);
        Map map = new HashMap();
        map.put("pageInfo", pageInfo);
        new JSONObject(map);
        String json = new JSONObject(map).toString();
        return json;
    }

    @RequestMapping("/findByExample")
    public String findByExample(@RequestParam Integer pageNumber, Integer pageSize, ProductList productList) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNumber, pageSize);
        List<ProductList> list = ebizProductService.getProductList(productList);
        PageInfo pageInfo = new PageInfo(list);
        Map map = new HashMap();
        map.put("pageInfo", pageInfo);
        new JSONObject(map);
        String json = new JSONObject(map).toString();
        return json;
    }


    ///////////////////////////////////////////////防止发生冲突////////////////////////////////////////////


    /**
     * @Description 条件查询产品列表
     * @Auther sunyinghao
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Map getAllProductByPage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //设置查询条件
        ProductCondition productCondition = getProductCondition(request);
        System.out.println(productCondition);
        try {

            //执行查询
            List<ProductList> productLists = ebizProductService.getEbizProductByCondition(productCondition);
            //设置数据
            ResultData resultData = new ResultData(productLists, ResultState.SUCCESS, "SUCCESS");
            map.put("data", resultData);
            if (productCondition != null && productCondition.getPageIndex() != null && productCondition.getPageIndex() > 0 && productCondition.getPageSize() != null && productCondition.getPageSize() > 0) {
                //初始化分页对象 用于前台展示
                //查找数量
                int totalCount = ebizProductService.getEbizProductCountByCondition(productCondition);
                PageSplitHelper helper = new PageSplitHelper(productCondition.getPageIndex(), productCondition.getPageSize());
                helper.setTotalCount(totalCount);
                map.put("page", helper);
                System.out.println(helper.getTotalCount());
            }
            //返回结果
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            ResultData resultData = new ResultData(ResultState.FAIL, "条件查找商品失败");
            map.put("resultDate", resultData);
            return map;
        }
    }


    /**
     * @Description 添加产品
     * @Auther sunyinghao
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map addProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String UPC = request.getParameter("upc").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("asin").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("sku").replace(",", " ").replace(";", " ");
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
        String Brand = request.getParameter("brand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("uri");
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
            ResultData resultData = new ResultData("201", message);
            map.put("resultData", resultData);
            return map;
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
            ProductList producttemp = ebizProductService.findProduct(product.getCompanyName(), model);
            System.out.println(product);
            if (producttemp != null) {
                message = "Model is already exist. Please use the exsting product \n";
                ResultData resultData = new ResultData(ResultState.FAIL, message);
                map.put("data", resultData);
                return map;
            } else {
                try {
                    ebizProductService.addProduct(currentUser, currentCompany, product);
                    message = "Product Added. \n";
                    ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                    map.put("data", resultData);
                    return map;
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Product Added Failed, Please Try Again. \n";
                    ResultData resultData = new ResultData(ResultState.FAIL, message);
                    map.put("data", resultData);
                    return map;

                }
            }
        }
    }

    /**
     * 修改页面，需要通过产品ID查找产品详情
     * @param request
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String productId = request.getParameter("productId");
        ProductList productList = null;
        if (productId != null) {
            productList = ebizProductService.findProductListById(Integer.parseInt(productId));
        }
        ResultData resultData = new ResultData(productList, ResultState.SUCCESS, "SUCCESS");
        map.put("data", resultData);
        return map;
    }

    /**
     * @Description 修改产品
     * @Auther sunyinghao
     * @param request
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map editProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String idString = request.getParameter("id");
        int uid = 0;
        if (idString != null && idString.length() != 0) {
            uid = Double.valueOf(idString).intValue();
        }
        String UPC = request.getParameter("upc").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("asin").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("sku").replace(",", " ").replace(";", " ");
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
        String Brand = request.getParameter("brand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("uri");
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
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("resultData", resultData);
            return map;
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
            product = new ProductList();
            product.setId(Integer.parseInt(idString));
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
            product.setUpdateTime(timeString);
            product.setOperationRecord("created by " + currentUser.getUserName() + " on " + timeString);
            try {
                message = ebizProductService.updateProductList(currentUser, currentCompany, product);
                if (message.length() > 0) {
                    ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                    map.put("data", resultData);
                    return map;
                } else {
                    ResultData resultData = new ResultData(ResultState.SUCCESS, "Nothing Updated");
                    map.put("data", resultData);
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                ResultData resultData = new ResultData(ResultState.FAIL, "更新失败");
                map.put("data", resultData);
                return map;
            }
        }
    }


    /**
     * @Description 通过产品id删除产品
     * @Auther sunyinghao
     * @param productListId
     * @return
     */
    @RequestMapping(value = "/del/{productListId}")
    @ResponseBody
    public Map deleteProduct(@PathVariable(value = "productListId") Integer productListId) {
        Map<String, Object> map = new HashMap<>();
        try {
            ebizProductService.deleteProductById(productListId);
            map.put("data", ResultData.SUCCESS);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            ;
            ResultData resultData = new ResultData(ResultState.FAIL, "删除失败");
            map.put("data", resultData);
            return map;
        }
    }


    /**
     * @Description 通过产品id查找产品详情
     * @Auther sunyinghao
     * @param productListId
     * @return
     */
    @RequestMapping(value = "/detail/{productListId}", method = RequestMethod.GET)
    @ResponseBody
    public Map findProductById(@PathVariable(value = "productListId") Integer productListId) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductList productList = ebizProductService.findProductListById(productListId);
            ResultData resultData = new ResultData(productList, ResultState.SUCCESS, "SUCCESS");
            map.put("resultData", resultData);
            return map;
        } catch (Exception e) {
            ResultData resultData = new ResultData(ResultState.FAIL, "SUCCESS");
            map.put("resultData", resultData);
            return map;
        }
    }


    /**
     * @Descrition 获取所有的产品状态
     * @Auther sunyinghao
     * @return
     */
    @RequestMapping("/getAllEbizStatusEnum")
    @ResponseBody
    public Map getAllEbizStatusEnum() {
        Map<String, Object> map = new HashMap<>();
        map.put("ebizStatusEnums ", EbizStatusEnum.values());
        return map;
    }


    /**
     * 发送Deal
     * @param request
     * @return
     */
    @RequestMapping("/sendDealGroup")
    @ResponseBody
    public Map sendDealGroup(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String message = "";
        EbizCompany currentCompany = (EbizCompany) request.getSession().getAttribute("currentCompany");
        EbizUser currentUser = (EbizUser) request.getSession().getAttribute("currentUser");
        String idString = request.getParameter("id");
        int uid = 0;
        if (idString != null && idString.length() != 0) {
            uid = Double.valueOf(idString).intValue();
        }
        String UPC = request.getParameter("upc").replace(",", " ").replace(";", " ");
        String ASIN = request.getParameter("asin").replace(",", " ").replace(";", " ");
        String SKU = request.getParameter("sku").replace(",", " ").replace(";", " ");
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
        String Brand = request.getParameter("brand").replace(",", " ").replace(";", " ");
        if (Brand == null || Brand.length() == 0) {
            message = message + "Brand is required \n";
        }
        String url = request.getParameter("uri");
        String ticketString = request.getParameter("tickets");
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
            ResultData resultData = new ResultData(ResultState.FAIL, message);
            map.put("data", resultData);
            return map;
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
                ResultData resultData = new ResultData(ResultState.FAIL, message);
                map.put("data", resultData);
                return map;
            } else {
                boolean update = false;
                product = ebizProductService.findProductListById(uid);
                if (product.getUPC() == null || (product.getUPC() != null && !product.getUPC().equals(UPC))) {
                    try {
                        ebizProductService.updateProductUPC(currentUser, currentCompany, product.getId(), UPC);
                        product.setUPC(UPC);
                        message = message + "UPC Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "UPC Update Failed,Please Try Again \n";
                    }
                }
                if (product.getASIN() == null || (product.getASIN() != null && !product.getASIN().equals(ASIN))) {
                    try {
                        ebizProductService.updateProductASIN(currentUser, currentCompany, product.getId(), ASIN);
                        product.setASIN(ASIN);
                        message = message + "ASIN Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "ASIN Update Failed,Please Try Again \n";
                    }
                }
                if (product.getBrand() == null || (product.getBrand() != null && !product.getBrand().equals(Brand))) {
                    try {
                        ebizProductService.updateProductBrand(currentUser, currentCompany, product.getId(), Brand);
                        product.setBrand(Brand);
                        message = message + "Brand Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Brand Update Failed,Please Try Again \n";
                    }
                }
                if (product.getModel() == null || (product.getModel() != null && !product.getModel().equals(model))) {
                    try {
                        ebizProductService.updateProductModel(currentUser, currentCompany, product.getId(), model);
                        product.setModel(model);
                        message = message + "Model Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Model Update Failed,Please Try Again \n";
                    }
                }
                if (product.getSKU() == null || (product.getSKU() != null && !product.getSKU().equals(SKU))) {
                    try {
                        ebizProductService.updateProductSKU(currentUser, currentCompany, product, SKU);
                        product.setSKU(SKU);
                        message = message + "SKU Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "SKU Update Failed,Please Try Again \n";
                    }
                }
                if (!product.getStatus().equals(status) && (status.equals(EbizStatusEnum.Active.getName())
                        || status.equals(EbizStatusEnum.LiveDeal.getName()))) {
                    try {
                        ebizProductService.updateProductStatus(currentUser, currentCompany, product.getId(), status);
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                } else if (!product.getStatus().equals(status)
                        && status.equals(EbizStatusEnum.UnActive.getName())) {
                } else if (product.getStatus().equals(status)
                        && status.equals(EbizStatusEnum.UnActive.getName())) {
                    try {
                        ebizProductService.updateProductStatus(currentUser, currentCompany, product.getId(),
                                EbizStatusEnum.Active.getName());
                        product.setStatus(status);
                        message = message + "Status Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Status Update Failed,Please Try Again \n";
                    }
                }
                if (product.getProductName() == null || (product.getProductName() != null && !product.getProductName().equals(productName))) {
                    try {
                        ebizProductService.updateProductName(currentUser, currentCompany, product.getId(), productName);
                        product.setProductName(productName);
                        message = message + "Product name Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product name Update Failed,Please Try Again \n";
                    }
                }
                if (product.getURI() == null || (product.getURI() != null && !product.getURI().equals(url))) {
                    try {
                        ebizProductService.updateProductURL(currentUser, currentCompany, product.getId(), url);
                        product.setURI(url);
                        message = message + "Product webaddress Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product webaddress Update Failed,Please Try Again \n";
                    }
                }
                if (product.getTickets() == null || (product.getTickets() != null && product.getTickets() != tickets)) {
                    try {
                        ebizProductService.updateProductTicket(currentUser, currentCompany, product.getId(), tickets);
                        product.setTickets(tickets);
                        message = message + "Product Ticket Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Ticket Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLimitPerPerson() == null || (product.getLimitPerPerson() != null && product.getLimitPerPerson() != personlimits)) {
                    try {
                        ebizProductService.updateProductLimitPerPerson(currentUser, currentCompany, product.getId(), personlimits);
                        product.setLimitPerPerson(personlimits);
                        message = message + "Product LimitPerPerson Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
                    }
                }

                if (product.getWeight() == null || (product.getWeight() != null && product.getWeight() != weight)) {
                    try {
                        ebizProductService.updateProductWeight(currentUser, currentCompany, product.getId(), weight);
                        product.setWeight(weight);
                        message = message + "Product Weight Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Weight Update Failed,Please Try Again \n";
                    }
                }
                if (product.getLength() == null || (product.getLength() != null && product.getLength() != length)) {
                    try {
                        ebizProductService.updateProductLength(currentUser, currentCompany, product.getId(), length);
                        product.setLength(length);
                        message = message + "Product Length Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Length Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWeight() == null || (product.getWeight() != null && product.getWeight() != width)) {
                    try {
                        ebizProductService.updateProductWidth(currentUser, currentCompany, product.getId(), width);
                        product.setWeight(width);
                        message = message + "Product Width Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Width Update Failed,Please Try Again \n";
                    }
                }
                if (product.getHeight() == null || (product.getHeight() != null && product.getHeight() != height)) {
                    try {
                        ebizProductService.updateProductHeight(currentUser, currentCompany, product.getId(), height);
                        product.setHeight(height);
                        message = message + "Product Height Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Height Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPrice() != price) {
                    try {
                        ebizProductService.updateProductPrice(currentUser, currentCompany, product.getId(), price);
                        product.setPrice(price);
                        message = message + "Product Price Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product Price Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotQuantity() != promotQuantity) {
                    try {
                        ebizProductService.updateProductPromotQuantity(currentUser, currentCompany, product.getId(), promotQuantity);
                        product.setPromotQuantity(promotQuantity);
                        message = message + "Product PromotQuantity Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getPromotPrice() != promotprice) {
                    try {
                        ebizProductService.updateProductPromotPrice(currentUser, currentCompany, product.getId(), promotprice);
                        product.setPromotPrice(promotprice);
                        message = message + "Product PromotPrice Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product PromotPrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePrice() != warehouseprice) {
                    try {
                        ebizProductService.updateProductWarehousePrice(currentUser, currentCompany, product.getId(), warehouseprice);
                        product.setWarehousePrice(warehouseprice);
                        message = message + "Product WarehousePrice Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotQuantity() != warehousepromotquantity) {
                    try {
                        ebizProductService.updateProductWarehousePromotQuantity(currentUser, currentCompany, product.getId(),
                                warehousepromotquantity);
                        product.setWarehousePromotQuantity(warehousepromotquantity);
                        message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
                    }
                }
                if (product.getWarehousePromotePrice() != warehousepromotprice) {
                    try {
                        ebizProductService.updateProductWarehousePromotePrice(currentUser, currentCompany, product.getId(), warehousepromotprice);
                        product.setWarehousePromotePrice(warehousepromotprice);
                        message = message + "Product WarehousePromotePrice Update Sucessfully \n";
                    } catch (Exception e) {
                        e.printStackTrace();
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
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.OnePackagesUser.getName())) {
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.FivePackagesUser.getName());
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());
                            List<String> newUserEmaillist = eBizUserService.readPackagesUserNameInLastTwoMonthForCompany(currentCompany.getCompanyName());
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }
                        }

                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.FivePackagesUser.getName())) {
                            userGroupNameSet.remove(EbizNurseGroupTypeEnum.TenPackagesUser.getName());

                            List<String> newUserEmaillist = eBizUserService.readMoreThanFivePackagesUserNameInLastTwoMonthForCompany(
                                    currentCompany.getCompanyName());
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TenPackagesUser.getName())) {
                            List<String> newUserEmaillist = eBizUserService.readMoreThanTenPackagesUserNameInLastTwoMonthForCompany(
                                    currentCompany.getCompanyName());
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.TrustedUser.getName())) {
                            List<String> newUserEmaillist = eBizUserService.readAllActiveTrustedNurseNameEmailForCompany(currentCompany.getCompanyName());
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }
                        }
                        if (userGroupNameSet.contains(EbizNurseGroupTypeEnum.UnTrustedUser.getName())) {
                            List<String> newUserEmaillist = eBizUserService.readAllActiveUnTrustedNurseNameEmailForCompany(currentCompany.getCompanyName());
                            if (newUserEmaillist != null && newUserEmaillist.size() > 0) {
                                userEmailList.addAll(newUserEmaillist);
                            }

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
                    boolean send = SendEmailCenter.sendDealEmail(product, currentUser, currentCompany, addressName, emailList, emailContent);
                    if (send) {
                        message = message + "Email Has Been Send To Chosen Group; \n";
                        ResultData resultData = new ResultData(ResultState.SUCCESS, message);
                        map.put("data", resultData);
                    } else {
                        message = message + "Send Email Failed, Please Check; \n";
                    }
                } else {
                    message = message + "Send Goup Email Failed Since Product Update Failed, Please Try Again; \n";
                }
                //这里DealList有问题
                if (product != null) {
                    //dealListService.addDeal(currentUser , currentCompany , product);
                }
                if (map.get("data") != null) {
                    return map;
                }
                ResultData resultData = new ResultData(ResultState.FAIL, message);
                map.put("data", resultData);
                return map;
            }
        }
    }


    /**
     * @Description 该类主要是处理产品的查询条件，封装产品查询条件对象
     * @Auther sunyinghao
     * @param request
     * @return
     */
    public ProductCondition getProductCondition(HttpServletRequest request) {
        //分页相关属性
        String pageSizeStr = request.getParameter("pageSize");
        String pageIndexStr = request.getParameter("pageIndex");
        int pageIndex = 0;//1;
        int pageSize = 0;//5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageIndexStr != null && !"".equals(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        //其他条件查询属性
        //1、从session中获取公司对象
        EbizCompany company = (EbizCompany) request.getSession().getAttribute(Constant.SESSION_KEY_COMPANY);
        //2、是否激活  1:全部   2：激活
        String status = request.getParameter("status");
        if (status == null || "".equals(status)) {
            status = "1";
        }

        ProductCondition productCondition = new ProductCondition();
        productCondition.setPageIndex(pageIndex);
        productCondition.setPageSize(pageSize);
        productCondition.setStatus(status);
        productCondition.setProductName(request.getParameter("productName"));
        productCondition.setCompanyName(company.getCompanyName());
        productCondition.setId(request.getParameter("id"));
        productCondition.setBrand(request.getParameter("brand"));
        productCondition.setUPC(request.getParameter("UPC"));
        productCondition.setPrice(request.getParameter("price"));
        productCondition.setWarePrice(request.getParameter("warePrice"));
        productCondition.setModel(request.getParameter("model"));
        return productCondition;
    }

}
