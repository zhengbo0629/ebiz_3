package com.ebiz.service;


import com.ebiz.common.ebizEnum.EbizOperationNameEnum;
import com.ebiz.common.ebizEnum.EbizPackagePayStatusEnum;
import com.ebiz.common.ebizEnum.EbizPackageStatusEnum;
import com.ebiz.common.ebizEnum.EbizStatusEnum;
import com.ebiz.dao.OperationRecordMapper;
import com.ebiz.dao.PackageListMapper;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.*;
import com.ebiz.utils.GeneralMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ProductListService {


    @Autowired
    private ProductListMapper productListMapper;


    @Autowired
    private PackageListMapper packageListMapper;

    @Autowired
    private OperationRecordMapper operationRecordMapper;


    //为进入到领票、预报页面准备的数据
    public List<ProductList> searchAllActiveAndAliveDealProducts(String companyName) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyNameEqualTo(companyName);
        List<String > list = new ArrayList<>();
        list.add(EbizStatusEnum.Active.getName());
        list.add(EbizStatusEnum.LiveDeal.getName());
        criteria.andStatusNotIn(list);
        example.setOrderByClause("id");
        example.isDistinct();
        List<ProductList> productLists = new ArrayList<>();
        productLists = productListMapper.selectByExample(example);
        if(productLists != null && productLists.size() > 0){
            return productLists ;
        }else{
            return null;
        }
    }

    //获取还未发货和还确认的包裹
    public Map<String,Object> searchUnReceivedAndUnConfirmedSellOrOBOPackSetForUser(EbizUser currentUser, Integer currentPage, Integer pageSize) {
        String status=EbizPackageStatusEnum.UnReceived.getColumnName();
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria1 = example.createCriteria();
        criteria1.andStatusEqualTo(status);
        criteria1.andUserNameEqualTo(currentUser.getUserName());
        example.or(criteria1);

        PackageListExample.Criteria criteria2 = example.createCriteria();
        criteria2.andStatusLike("UnConfirmed");
        criteria2.andUserNameEqualTo(currentUser.getUserName());
        example.or(criteria2);

        PackageListExample.Criteria criteria3 = example.createCriteria();
        criteria3.andStatusEqualTo("Refused");
        criteria3.andUserNameEqualTo(currentUser.getUserName());
        example.or(criteria3);

        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> packageLists = new ArrayList<>();
        packageLists = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String , Object > map = new HashMap<>();
        map.put("totalCount" , totalCount);
        map.put("data" , packageLists);
        return map;
    }

    //为进入更新信用卡信息页面做准备
    public Map<String,Object> searchUnpaidSetForUser(EbizUser currentUser, Integer currentPage, Integer pageSize) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andPayStatusEqualTo(EbizPackagePayStatusEnum.Paid.getName());
        criteria.andUserNameEqualTo(currentUser.getUserName());
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> packageLists = new ArrayList<>();
        packageLists = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String , Object > map = new HashMap<>();
        map.put("totalCount" , totalCount);
        map.put("data" , packageLists);
        return map;
    }

    //为进入打包包裹页面做准备
    public Map<String,Object> searchSetForUser(EbizUser currentUser, Integer currentPage, Integer pageSize) {
        String status = EbizPackageStatusEnum.EmailedLabel.getColumnName();
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        if(status!=null&&status.length()!=0){
            criteria.andStatusNotEqualTo(status);
            criteria.andUserNameEqualTo(currentUser.getUserName());
        }else{
            criteria.andStatusNotEqualTo(status);
            criteria.andUserNameEqualTo(currentUser.getUserName());
        }
        example.setOrderByClause("id");
        example.isDistinct();
        List<PackageList> packageLists = new ArrayList<>();
        packageLists = packageListMapper.selectByExample(example);
        //查询在一共有多少数据
        long totalCount = packageListMapper.countByExample(example);
        Map<String , Object > map = new HashMap<>();
        map.put("totalCount" , totalCount);
        map.put("data" , packageLists);
        return map;
    }


    //去往正在收购页面做准备           一定要主要使用，小心,这是坑，小心参数传递
    public List<ProductList> searchSet(String companyName, String status) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andStatusEqualTo(status);
        example.setOrderByClause("id");
        example.isDistinct();
        List<ProductList> productLists = new ArrayList<>();
        productLists = productListMapper.selectByExample(example);
        if(productLists != null && productLists.size() > 0){
            return productLists ;
        }else{
            return null;
        }
    }

    //通过id查找商品
    public ProductList findProduct(int productUID) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(productUID);
        List<ProductList> list = productListMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public boolean updateProductTicket(EbizUser currentUser, EbizCompany currentCompany, Integer id, int leftTickets) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = productListMapper.selectByExample(example);
        ProductList productList = new ProductList();
        if(list != null && list.size() > 0){
            productList = list.get(0);
        }else{
            productList = null;
            return false;
        }
        productList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        productList.setTickets(leftTickets);
        int count = productListMapper.updateByExample(productList , example);
        if(count > 0){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList" ,  "Tickets", id, Integer.toString(leftTickets));
        }else{
            return false;
        }

    }

    private boolean addUpdateOperationRecord(EbizUser currentUser, EbizCompany currentCompany, String tableName ,  String column, Integer id, String s) {
        OperationRecord record = new OperationRecord();
        record.setUserName(currentUser.getUserName());
        record.setCompanyName(currentCompany.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.UpdateColumn.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setColumnName(column);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        record.setNewValue(s);
        return  addOperationRecord(record);
    }

    private boolean addOperationRecord(OperationRecord record) {
        int count = operationRecordMapper.insert(record);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    public int addPackage(EbizUser currentUser, EbizCompany currentCompany, PackageList tempPackage) {
        int id = packageListMapper.insertAndGetMaxId(tempPackage);
        boolean flag = addAddOperationRecord(currentUser,currentCompany,"packageList",tempPackage.getId());
        if(flag){
            return tempPackage.getId();
        }
        return -1;
    }

    private boolean addAddOperationRecord(EbizUser currentUser, EbizCompany currentCompany, String tableName, int id) {
        OperationRecord record=new OperationRecord();
        record.setUserName(currentUser.getUserName());
        record.setCompanyName(currentCompany.getCompanyName());
        record.setOperationName(EbizOperationNameEnum.AddRow.getName());
        record.setTableName(tableName);
        record.setRowId(id);
        record.setTimeString(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
        return addOperationRecord(record);
    }

    public ProductList findProduct(String companyName, String modelNumber) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("Deleted");
        criteria.andCompanyNameEqualTo(companyName);
        criteria.andModelEqualTo(modelNumber);
        List<ProductList> list = productListMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public boolean deletePackage(EbizUser currentUser, EbizCompany currentCompany, int uid) {
        //执行package更新操作
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(uid);
        List< PackageList > list = packageListMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            PackageList packageList = list.get(0);
            packageList.setStatus(EbizStatusEnum.Deleted.getName());
            packageList.setUpdateTime(GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000));
            packageListMapper.updateByExample(packageList , example);
            //执行记录的添加操作
            return addUpdateOperationRecord(currentUser, currentCompany, "packageList", "Status", uid, EbizStatusEnum.Deleted.getName());
        }
        return false;
    }




    public boolean updatePackageQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int quantity) {
        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List< PackageList > list = packageListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(PackageList packageList : list){
                packageList.setQuantity(quantity);
                packageList.setUpdateTime( GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis() / 1000));
                int count = packageListMapper.updateByExample(packageList , example);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public boolean updateProductUPC(EbizUser currentUser, EbizCompany currentCompany, Integer id, String upc) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "UPC", id, upc);
        }
        return false;
    }

    public boolean updateProductASIN(EbizUser currentUser, EbizCompany currentCompany, Integer id, String asin) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "ASIN", id, asin);
        }
        return false;
    }

    public boolean updateProductBrand(EbizUser currentUser, EbizCompany currentCompany, Integer id, String brand) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "BRAND", id, brand);
        }
        return false;
    }

    public boolean updateProductModel(EbizUser currentUser, EbizCompany currentCompany, Integer id, String model) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "model", id, model);
        }
        return false;
    }

    public boolean updateProductSKU(EbizUser currentUser, EbizCompany currentCompany, ProductList product, String sku) {

        updatePackSKUwithProduct newUpdate = new updatePackSKUwithProduct(currentUser, currentCompany, product, sku);
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "SKU", product.getId(), sku);
        }
        return false;
    }

    public boolean updateProductStatus(EbizUser currentUser, EbizCompany currentCompany, Integer id, String status) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "STATUS", id, status);
        }
        return false;
    }

    public boolean updateProductName(EbizUser currentUser, EbizCompany currentCompany, Integer id, String productName) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "PRODUCTNAME", id, productName);
        }
        return false;
    }

    public boolean updateProductURL(EbizUser currentUser, EbizCompany currentCompany, Integer id, String url) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "URL", id, url);
        }
        return false;
    }

    public boolean updateProductLimitPerPerson(EbizUser currentUser, EbizCompany currentCompany, Integer id, int personlimits) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "LimitPerPerson", id, personlimits+"");
        }
        return false;
    }

    public boolean updateProductWeight(EbizUser currentUser, EbizCompany currentCompany, Integer id, double weight) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "Weight", id, weight+"");
        }
        return false;
    }

    public boolean updateProductLength(EbizUser currentUser, EbizCompany currentCompany, Integer id, double length) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "Length", id, length+"");
        }
        return false;
    }

    public boolean updateProductWidth(EbizUser currentUser, EbizCompany currentCompany, Integer id, double width) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "Width", id, width+"");
        }
        return false;
    }

    public boolean updateProductHeight(EbizUser currentUser, EbizCompany currentCompany, Integer id, double height) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "Height", id, height+"");
        }
        return false;
    }

    public boolean updateProductPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double price) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "Price", id, price+"");
        }
        return false;
    }

    public boolean updateProductPromotQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int promotQuantity) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "PromotQuantity", id, promotQuantity+"");
        }
        return false;
    }

    public boolean updateProductPromotPrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double promotprice) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "PromotPrice", id, promotprice+"");
        }
        return false;
    }

    public boolean updateProductWarehousePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double warehouseprice) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePrice", id, warehouseprice+"");
        }
        return false;
    }

    public boolean updateProductWarehousePromotQuantity(EbizUser currentUser, EbizCompany currentCompany, Integer id, int warehousepromotquantity) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotQuantity", id, warehousepromotquantity+"");
        }
        return false;
    }

    public boolean updateProductWarehousePromotePrice(EbizUser currentUser, EbizCompany currentCompany, Integer id, double warehousepromotprice) {
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
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "WarehousePromotePrice", id, warehousepromotprice+"");
        }
        return false;
    }

    public boolean updateProductUserNote(EbizUser currentUser, EbizCompany currentCompany, Integer id, String userNote) {
        ProductListExample example = new ProductListExample();
        ProductListExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<ProductList> list = new ArrayList<>();
        list = productListMapper.selectByExample(example);
        boolean flag = false;
        if(list != null && list.size() > 0){
            for(ProductList productList : list){
                productList.setUserNote(userNote);
                int count = productListMapper.updateByPrimaryKey(productList);
                if(count > 0){
                    flag = true;
                }else{
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            return addUpdateOperationRecord(currentUser, currentCompany, "productList", "UserNote", id, userNote);
        }
        return false;
    }


    public class updatePackSKUwithProduct extends Thread{
        private EbizUser user;
        private EbizCompany company;
        private ProductList product;
        private String newSKU;


        public updatePackSKUwithProduct(EbizUser user,EbizCompany company,ProductList product,String newSKU)
        {
            this.user=user;
            this.company=company;
            this.product = product;
            this.newSKU = newSKU;

        }
        @Override
        public void run()
        {
            updatePackageSKUWithProductSKUChange(user,company, product, newSKU);
        }

    }

    public int updatePackageSKUWithProductSKUChange(EbizUser user,EbizCompany company,ProductList product,String newSKU){
        //////////////////////////////////////////////////
        //
        return 0;
    }


    /**
    * @Auther:sunyinghao
    * @Description:添加产品并且添加记录
    * @Date: 22:00 2018/11/15
    */
    public int addProduct(EbizUser currentUser, EbizCompany currentCompany, ProductList product) {
        product.setCompanyName(currentCompany.getCompanyName());
        int id = productListMapper.insertGetPrimaryId(product);
        addAddOperationRecord(currentUser, currentCompany, "productList", id);
        return 0;
    }
}
