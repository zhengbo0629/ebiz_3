package com.ebiz.utils;

import com.ebiz.dao.PackageListMapper;
import com.ebiz.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UpdatePackSKUwithProduct extends Thread{

    @Autowired
    private PackageListMapper packageListMapper;

    private EbizUser user;
    private EbizCompany company;
    private ProductList product;
    private String newSKU;


    public UpdatePackSKUwithProduct(EbizUser user, EbizCompany company, ProductList product, String newSKU)
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

    public void updatePackageSKUWithProductSKUChange(EbizUser user,EbizCompany company,ProductList product,String newSKU){
        String timeString=GeneralMethod.getTimeStringForSeconds(System.currentTimeMillis()/1000);

        PackageListExample example = new PackageListExample();
        PackageListExample.Criteria criteria = example.createCriteria();
        criteria.andModelNumberEqualTo(product.getModel());
        criteria.andCompanyNameEqualTo(company.getCompanyName());
        criteria.andSKUNotEqualTo(newSKU);
        List<String> status = new ArrayList<>();

        status.add("unreceived");
        status.add("instock");
        criteria.andStatusIn(status);

        List<PackageList> list = new ArrayList<>();
        list = packageListMapper.selectByExample(example);
        for(PackageList packageList : list){
            packageList.setSKU(newSKU);
            packageList.setUpdateTime(timeString);
            packageListMapper.updateByPrimaryKeySelective(packageList);
        }
    }
}
