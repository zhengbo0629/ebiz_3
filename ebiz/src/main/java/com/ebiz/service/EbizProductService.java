/**
 * @(#)com.ebiz.service.EbizProductService Copyright (c) 2014-2018 ...
 * <p>
 * DESC:
 */
package com.ebiz.service;

import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.controller.model.ProductCondition;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.*;
import com.ebiz.utils.GeneralMethod;
import com.ebiz.utils.UpdatePackSKUwithProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王润松
 * @version 1.0  2018/10/30 0030
 */
@Service
public class EbizProductService {
    @Autowired
    private ProductListMapper productListMapper;

    @Autowired
    private OperationRecordService operationRecordService;

    /*
       多条件查询及分页
     */
    public List getProductList(ProductList productList) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        //模糊查询
        if (productList.getProductName() != "" && productList.getProductName() != null) {
            criteria.andProductNameLike("%" + productList.getProductName() + "%");
        }
        //相等查询
        if (productList.getSKU() != "" && productList.getSKU() != null) {
            criteria.andSKUEqualTo(productList.getSKU());
        }
        List<ProductList> list = productListMapper.selectByExample(example);
        return list;
    }

    /*
    添加代购任务中是商品
     */
    public void addTaskProduct(ProductList productList){
        int n = productListMapper.insertSelective(productList);
    }




    ////////////////////////////////////////////防止发生冲突/////////////////////////////////////////////

    /**
     * @Description 条件查询符合条件的产品列表
     * @Auther sunyinghao
     * @param productCondition
     * @return
     */
    public List<ProductList> getEbizProductByCondition(ProductCondition productCondition) {
        List<ProductList> productLists = new ArrayList<>();
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        String status = productCondition.getStatus();
        if(status.equals("0")){
            criteria.andStatusNotEqualTo("Deleted");
        }else if(status.equals("1")){
            List<String > statusList = new ArrayList<>();
            statusList.add(EbizStatusEnum.Active.getName());
            statusList.add(EbizStatusEnum.LiveDeal.getName());
            criteria.andStatusIn(statusList);
        }else if(status.equals("2")){
            criteria.andStatusEqualTo(EbizStatusEnum.UnActive.getName());
        }
        //正在收购需要查询的
        if(EbizStatusEnum.LiveDeal.getName().equals(status)){
            criteria.andStatusEqualTo(status);
        }

        if(productCondition.getBrand() != null && !productCondition.getBrand().equals("null") && !"".equals(productCondition.getBrand())){
            criteria.andBrandEqualTo(productCondition.getBrand());
        }
        if(productCondition.getModel() != null && !productCondition.getModel().equals("null") && !"".equals(productCondition.getModel())){
            criteria.andModelEqualTo(productCondition.getModel());
        }
        if(productCondition.getPrice() != null && !productCondition.getPrice().equals("null") && !"".equals(productCondition.getPrice())){
            switch (productCondition.getPrice()){
                case "1": criteria.andPriceBetween(0D , 200D); break;
                case "2": criteria.andPriceBetween(200D , 500D); break;
                case "3": criteria.andPriceBetween(500D , 800D); break;
                case "4": criteria.andPriceBetween(800D , 1000D); break;
                case "5": criteria.andPriceGreaterThanOrEqualTo(1000D);break;
                default: break;
            }
        }
        if(productCondition.getUPC() != null && !productCondition.getUPC().equals("null") && !"".equals(productCondition.getUPC())){
            criteria.andUPCEqualTo(productCondition.getUPC());
        }
        if(productCondition.getProductName() != null && !productCondition.getProductName().equals("null") && !"".equals(productCondition.getProductName())){
            criteria.andProductNameLike(productCondition.getProductName());
        }
        if(productCondition.getWarePrice() != null && !productCondition.getWarePrice().equals("null") && !"".equals(productCondition.getWarePrice())){
            switch (productCondition.getWarePrice()){
                case "1": criteria.andWarehousePriceBetween(0D , 200D); break;
                case "2": criteria.andWarehousePriceBetween(200D , 500D); break;
                case "3": criteria.andWarehousePriceBetween(500D , 800D); break;
                case "4": criteria.andWarehousePriceBetween(800D , 1000D); break;
                case "5": criteria.andWarehousePriceGreaterThanOrEqualTo(1000D); break;
                default: break;
            }
        }
        if(productCondition.getId() != null && !"".equals(productCondition.getId()) && !productCondition.getId().equals("null") ){
            criteria.andIdEqualTo(Integer.parseInt(productCondition.getId()));
        }
        criteria.andCompanyNameEqualTo(productCondition.getCompanyName());
        if(productCondition != null && productCondition.getPageIndex() != null && productCondition.getPageIndex() > 0 && productCondition.getPageSize() != null && productCondition.getPageSize() > 0) {
            example.setPageSize(productCondition.getPageSize());
            example.setPageIndex(productCondition.getPageIndex());//开始分页查询的行
        }
        example.setOrderByClause("id");
        example.isDistinct();
        productLists = productListMapper.selectByExample(example);
        return productLists;
    }

    /**
     * @Description 条件查询符合条件的数量
     * @Auther sunyinghao
     * @param productCondition
     * @return
     */
    public int getEbizProductCountByCondition(ProductCondition productCondition) {
        System.out.println(productCondition);
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        String status = productCondition.getStatus();
        if(status.equals("0")){
            criteria.andStatusNotEqualTo("Deleted");
        }else if(status.equals("1")){
            List<String > statusList = new ArrayList<>();
            statusList.add(EbizStatusEnum.Active.getName());
            statusList.add(EbizStatusEnum.LiveDeal.getName());
            criteria.andStatusIn(statusList);
        }else if(status.equals("2")){
            criteria.andStatusEqualTo(EbizStatusEnum.UnActive.getName());
        }

        //正在收购需要查询的
        if(EbizStatusEnum.LiveDeal.getName().equals(status)){
            criteria.andStatusEqualTo(status);
        }


        if(productCondition.getBrand() != null && !productCondition.getBrand().equals("null") && !"".equals(productCondition.getBrand())){
            criteria.andBrandEqualTo(productCondition.getBrand());
        }
        if(productCondition.getModel() != null && !productCondition.getModel().equals("null") && !"".equals(productCondition.getModel())){
            criteria.andModelEqualTo(productCondition.getModel());
        }
        if(productCondition.getPrice() != null && !productCondition.getPrice().equals("null") && !"".equals(productCondition.getPrice())){
            switch (productCondition.getPrice()){
                case "1": criteria.andPriceBetween(0D , 200D); break;
                case "2": criteria.andPriceBetween(200D , 500D); break;
                case "3": criteria.andPriceBetween(500D , 800D); break;
                case "4": criteria.andPriceBetween(800D , 1000D); break;
                case "5": criteria.andPriceGreaterThanOrEqualTo(1000D);break;
                default: break;
            }
        }
        if(productCondition.getUPC() != null && !productCondition.getUPC().equals("null") && !"".equals(productCondition.getUPC())){
            criteria.andUPCEqualTo(productCondition.getUPC());
        }
        if(productCondition.getProductName() != null && !productCondition.getProductName().equals("null") && !"".equals(productCondition.getProductName())){
            criteria.andProductNameLike(productCondition.getProductName());
        }
        if(productCondition.getWarePrice() != null && !productCondition.getWarePrice().equals("null") && !"".equals(productCondition.getWarePrice())){
            switch (productCondition.getWarePrice()){
                case "1": criteria.andWarehousePriceBetween(0D , 200D); break;
                case "2": criteria.andWarehousePriceBetween(200D , 500D); break;
                case "3": criteria.andWarehousePriceBetween(500D , 800D); break;
                case "4": criteria.andWarehousePriceBetween(800D , 1000D); break;
                case "5": criteria.andWarehousePriceGreaterThanOrEqualTo(1000D); break;
                default: break;
            }
        }
        if(productCondition.getId() != null && !"".equals(productCondition.getId()) && !productCondition.getId().equals("null")){
            criteria.andIdEqualTo(Integer.parseInt(productCondition.getId()));
        }
        criteria.andCompanyNameEqualTo(productCondition.getCompanyName());
        //设置分页
        example.setPageIndex(productCondition.getPageIndex());
        example.setPageSize(productCondition.getPageSize());
        return productListMapper.countByExample(example);
    }


    /**
     * 通过公司名称和产品的model来查找产品对象
     * @param companyName
     * @param model
     * @return
     */
    public ProductList findProduct(String companyName, String model) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andModelEqualTo(model);
        List<ProductList> list = productListMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * @Description 通过产品ID查找产品详情
     * @Auther sunyinghao
     * @param productId
     * @return
     */
    public ProductList findProductListById(Integer productId){
        return productListMapper.selectByPrimaryKey(productId);
    }

    /**
     * @Description 添加产品，并且添加记录
     * @Auther sunyinghao
     * @param currentUser
     * @param currentCompany
     * @param product
     * @return
     */
    public void addProduct(EbizUser currentUser, EbizCompany currentCompany, ProductList product) {
        product.setCompanyName(currentCompany.getCompanyName());
        int id = productListMapper.insertGetPrimaryId(product);
        operationRecordService.addOperationRecord(currentUser , currentCompany , "productList" , id);

    }


    /**
     * @Description 更新产品信息
     * @Auther sunyinghao
     * @param
     */
    public String updateProductList(EbizUser currentUser, EbizCompany currentCompany,ProductList newProduct) {
        String message = "";
        ProductList product = findProductListById(newProduct.getId());
        if(product != null) {
            //产品存在,执行更新操作
            productListMapper.updateByPrimaryKeySelective(newProduct);
            System.out.println("uri = " + newProduct.getURI());
            //记录添加
            if (product.getUPC() == null || (product.getUPC() != null && !product.getUPC().equals(newProduct.getUPC()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "UPC", newProduct.getId(), newProduct.getUPC() );
                if (count > 0) {
                    message = message + "UPC Update Sucessfully \n";
                } else {
                    message = message + "UPC Update Failed,Please Try Again \n";
                }
            }

            if (product.getASIN() == null || (product.getASIN() != null && !product.getASIN().equals(newProduct.getASIN()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "ASIN", newProduct.getId(), newProduct.getASIN() );
                if (count > 0) {
                    message = message + "ASIN Update Sucessfully \n";
                } else {
                    message = message + "ASIN Update Failed,Please Try Again \n";
                }
            }

            if (product.getBrand() == null || ( product.getBrand() != null && !product.getBrand().equals(newProduct.getBrand()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Brand", newProduct.getId(), newProduct.getBrand() );
                if (count > 0) {
                    message = message + "Brand Update Sucessfully \n";
                } else {
                    message = message + "Brand Update Failed,Please Try Again \n";
                }
            }


            if (product.getModel() == null || (product.getModel() != null && !product.getModel().equals(newProduct.getModel()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Model", newProduct.getId(), newProduct.getModel() );
                if (count > 0) {
                    message = message + "Model Update Sucessfully \n";
                } else {
                    message = message + "Model Update Failed,Please Try Again \n";
                }
            }

            if (product.getSKU() == null || (product.getSKU() != null && !product.getSKU().equals(newProduct.getSKU()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "SKU", newProduct.getId(), newProduct.getSKU() );
                if (count > 0) {
                    message = message + "SKU Update Sucessfully \n";
                } else {
                    message = message + "SKU Update Failed,Please Try Again \n";
                }
            }


            if (product.getProductName() == null || (product.getProductName() != null && !product.getProductName().equals(newProduct.getProductName()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "productName", newProduct.getId(), newProduct.getProductName() );
                if (count > 0) {
                    message = message + "Product name Update Sucessfully \n";
                } else {
                    message = message + "Product name Update Failed,Please Try Again \n";
                }
            }


            if (product.getStatus() == null || (product.getStatus() != null &&!product.getStatus().equals(newProduct.getStatus()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Status", newProduct.getId(), newProduct.getStatus() );
                if (count > 0) {
                    message = message + "Status Update Sucessfully \n";
                } else {
                    message = message + "Status Update Failed,Please Try Again \n";
                }
            }

            if (product.getURI() == null || (product.getURI() != null && !product.getURI().equals(newProduct.getURI()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "URL", newProduct.getId(), newProduct.getURI() );
                if (count > 0) {
                    message = message + "Product webaddress Update Sucessfully \n";
                } else {
                    message = message + "Product webaddress Update Failed,Please Try Again \n";
                }
            }

            if (product.getTickets()!=newProduct.getTickets()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Ticket", newProduct.getId(), newProduct.getTickets() + "" );
                if (count > 0) {
                    message = message + "Product Ticket Update Sucessfully \n";
                } else {
                    message = message + "Product Ticket Update Failed,Please Try Again \n";
                }
            }

            if (product.getLimitPerPerson()!=newProduct.getLimitPerPerson()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "LimitPerPerson", newProduct.getId(), newProduct.getLimitPerPerson() + "" );
                if (count > 0) {
                    message = message + "Product LimitPerPerson Update Sucessfully \n";
                } else {
                    message = message + "Product LimitPerPerson Update Failed,Please Try Again \n";
                }
            }


            if (product.getWeight()!=newProduct.getWeight()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Weight", newProduct.getId(), newProduct.getWeight() + "" );
                if (count > 0) {
                    message = message + "Product Weight Update Sucessfully \n";
                } else {
                    message = message + "Product Weight Update Failed,Please Try Again \n";
                }
            }

            if (product.getLength()!=newProduct.getLength()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Length", newProduct.getId(), newProduct.getLength() + "" );
                if (count > 0) {
                    message = message + "Product Length Update Sucessfully \n";
                } else {
                    message = message + "Product Length Update Failed,Please Try Again \n";
                }
            }


            if (product.getWidth() != newProduct.getWidth()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Width", newProduct.getId(), newProduct.getWidth() + "" );
                if (count > 0) {
                    message = message + "Product Width Update Sucessfully \n";
                } else {
                    message = message + "Product Width Update Failed,Please Try Again \n";
                }
            }


            if (product.getHeight()!=newProduct.getHeight()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Height", newProduct.getId(), newProduct.getHeight() + "" );
                if (count > 0) {
                    message = message + "Product Height Update Sucessfully \n";
                } else {
                    message = message + "Product Height Update Failed,Please Try Again \n";
                }
            }

            if (product.getPrice()!=newProduct.getPrice()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "Price", newProduct.getId(), newProduct.getPrice() + "" );
                if (count > 0) {
                    message = message + "Product Price Update Sucessfully \n";
                } else {
                    message = message + "Product Price Update Failed,Please Try Again \n";
                }
            }


            if (product.getPromotQuantity()!=newProduct.getPromotQuantity()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "PromotQuantity", newProduct.getId(), newProduct.getPromotQuantity() + "" );
                if (count > 0) {
                    message = message + "Product PromotQuantity Update Sucessfully \n";
                } else {
                    message = message + "Product PromotQuantity Update Failed,Please Try Again \n";
                }
            }

            if (product.getPromotPrice()!=newProduct.getPromotPrice()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "PromotPrice", newProduct.getId(), newProduct.getPromotPrice() + "" );
                if (count > 0) {
                    message = message + "Product PromotPrice Update Sucessfully \n";
                } else {
                    message = message + "Product PromotPrice Update Failed,Please Try Again \n";
                }
            }

            if (product.getWarehousePrice()!=newProduct.getWarehousePrice()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "WarehousePrice", newProduct.getId(), newProduct.getWarehousePrice() + "" );
                if (count > 0) {
                    message = message + "Product WarehousePrice Update Sucessfully \n";
                } else {
                    message = message + "Product WarehousePrice Update Failed,Please Try Again \n";
                }
            }

            if (product.getWarehousePromotQuantity()!=newProduct.getWarehousePromotQuantity()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotQuantity", newProduct.getId(), newProduct.getWarehousePromotQuantity() + "" );
                if (count > 0) {
                    message = message + "Product WarehousePromotQuantity Update Sucessfully \n";
                } else {
                    message = message + "Product WarehousePromotQuantity Update Failed,Please Try Again \n";
                }
            }
            if (product.getWarehousePromotePrice()!=newProduct.getWarehousePromotePrice()) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotePrice", newProduct.getId(), newProduct.getWarehousePromotePrice() + "" );
                if (count > 0) {
                    message = message + "Product WarehousePromotePrice Update Sucessfully \n";
                } else {
                    message = message + "Product WarehousePromotePrice Update Failed,Please Try Again \n";
                }
            }

            if (product.getUserNote() == null || (product.getUserNote() != null && !product.getUserNote().equals(newProduct.getUserNote()))) {
                int count = operationRecordService.addOperationRecord(currentUser, currentCompany, "productList", "UserNote", newProduct.getId(), newProduct.getUserNote() + "" );
                if (count > 0) {
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
            message = "不存在该产品";
        }
        return message;
    }

    /**
     * @Description 通过产品id删除产品
     * @Auther sunyinghao
     * @param productListId
     */
    public void deleteProductById(Integer productListId) {
        productListMapper.deleteByPrimaryKey(productListId);
    }

    public void updateProductUPC(EbizUser currentUser, EbizCompany currentCompany, Integer id, String upc) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setUPC(upc);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "UPC", id, upc);
    }

    public void updateProductASIN(EbizUser currentUser, EbizCompany currentCompany, Integer id, String asin) {
        System.out.println(currentCompany.getId());
        System.out.println(currentUser.getId());
        System.out.println(id);
        System.out.println(asin);
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setASIN(asin);
                //int count = productListMapper.updateByExample(productList , example);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        System.out.println(flag);
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "ASIN", id, asin);
    }

    public void updateProductBrand(EbizUser currentUser, EbizCompany currentCompany, Integer id, String brand) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setBrand(brand);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "BRAND", id, brand);
    }

    public void updateProductModel(EbizUser currentUser, EbizCompany currentCompany, Integer id, String model) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setModel(model);
                int count = productListMapper.updateByPrimaryKey(productList );
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "model", id, model);
    }

    public void updateProductSKU(EbizUser currentUser, EbizCompany currentCompany, ProductList product, String sku) {

        UpdatePackSKUwithProduct newUpdate = new UpdatePackSKUwithProduct(currentUser, currentCompany, product, sku);
        newUpdate.start();

        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(product.getId());
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setSKU(sku);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "SKU", product.getId(), sku);
    }

    public void updateProductStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String status) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setStatus(status);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "STATUS", id, status);
    }

    public void updateProductName(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productName) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setProductName(productName);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "PRODUCTNAME", id, productName);
    }

    public void updateProductURL(EbizUser currentUser, EbizCompany currentCompany, Integer id, String url) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setURI(url);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "URL", id, url);
    }

    public void updateProductTicket(EbizUser currentUser, EbizCompany currentCompany, Integer id, int leftTickets) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = productListMapper.selectByExample(example);
        ProductList productList = new ProductList();
        if(list != null && list.size() > 0){
            productList = list.get(0);
        }else{
            productList = null;
            return ;
        }
        productList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        productList.setTickets(leftTickets);
        int count = productListMapper.updateByExample(productList , example);
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList" ,  "Tickets", id, Integer.toString(leftTickets));
    }

    public void updateProductLimitPerPerson(EbizUser currentUser, EbizCompany currentCompany, Integer id, int personlimits) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setLimitPerPerson(personlimits);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "LimitPerPerson", id, personlimits+"");
    }

    public void updateProductWeight(EbizUser currentUser, EbizCompany currentCompany, Integer id, double weight) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setWeight(weight);
                int count = productListMapper.updateByPrimaryKey(productList );
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "Weight", id, weight+"");
    }

    public void updateProductLength(EbizUser currentUser, EbizCompany currentCompany, Integer id, double length) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setLength(length);
                int count = productListMapper.updateByPrimaryKey(productList );
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "Length", id, length+"");
    }

    public void updateProductWidth(EbizUser currentUser, EbizCompany currentCompany, Integer id, double width) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setWidth(width);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "Width", id, width+"");
    }

    public void updateProductHeight(EbizUser currentUser, EbizCompany currentCompany, Integer id, double height) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setHeight(height);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "Height", id, height+"");
    }



    public void updateProductPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double price) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setPrice(price);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "Price", id, price+"");
    }

    public void updateProductPromotQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int promotQuantity) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setPromotQuantity(promotQuantity);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "PromotQuantity", id, promotQuantity+"");
    }

    public void updateProductPromotPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double promotprice) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setPromotPrice(promotprice);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "PromotPrice", id, promotprice+"");
    }

    public void updateProductWarehousePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double warehouseprice) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setWarehousePrice(warehouseprice);
                int count = productListMapper.updateByPrimaryKey(productList );
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePrice", id, warehouseprice+"");
    }

    public void updateProductWarehousePromotQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int warehousepromotquantity) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setWarehousePromotQuantity(warehousepromotquantity);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotQuantity", id, warehousepromotquantity+"");
    }

    public void updateProductWarehousePromotePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double warehousepromotprice) throws  Exception {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setWarehousePromotePrice(warehousepromotprice);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        operationRecordService.addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotePrice", id, warehousepromotprice+"");
    }


}
