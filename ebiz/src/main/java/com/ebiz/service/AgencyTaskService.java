/**
 * @(#)com.ebiz.service.AgencyTaskService Copyright (c) 2014-2018 ...
 * <p>
 * DESC:
 */
package com.ebiz.service;

import com.ebiz.dao.AgencyTaskMapper;
import com.ebiz.dao.ProductListMapper;
import com.ebiz.model.AgeTaskAndPro;
import com.ebiz.model.AgencyTask;
import com.ebiz.model.AgencyTaskExample;
import com.ebiz.model.ProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 王润松
 * @version 1.0  2018/11/7 0007
 */
@Service
public class AgencyTaskService {
    @Autowired
    private AgencyTaskMapper agencyTaskMapper;
    private ProductListMapper productListMapper;
    /*
       添加代购任务
     */
    public void addTask(AgencyTask agencyTask){
        agencyTaskMapper.insertSelective(agencyTask);
    }

    /*
       领取任务
     */
    public void updateByKey(AgencyTask agencyTask){
        agencyTaskMapper.updateByPrimaryKeySelective(agencyTask);
    }

    /*
       按条件查询任务
    */
    public List<AgeTaskAndPro> getAgencyTaskList(AgencyTask agencyTask){
        AgeTaskAndPro ageTaskAndPro = new AgeTaskAndPro();
        List<AgeTaskAndPro> list = new ArrayList<AgeTaskAndPro>();
        AgencyTaskExample example = new AgencyTaskExample();
        AgencyTaskExample.Criteria criteria = example.createCriteria();
        //相等查询
        if (agencyTask.getBuyerName()!= "" && agencyTask.getBuyerName()!= null) {
            criteria.andBuyerNameEqualTo(agencyTask.getBuyerName());
        }
        //任务按状态查询
        if (agencyTask.getTaskStatus()!= "" && agencyTask.getTaskStatus()!= null) {
            criteria.andBuyerNameEqualTo(agencyTask.getTaskStatus());
        }
        List<AgencyTask> listAT =  agencyTaskMapper.selectByExample(example);
        if(listAT!=null&&listAT.size()>0){
            for (int i=0,j=listAT.size();i<j;i++ ){
                AgencyTask agencyTaskOne = listAT.get(i);
                ageTaskAndPro.setATid(agencyTaskOne.getATid());
                ageTaskAndPro.setBuyerAddress(agencyTaskOne.getBuyerAddress());
                ageTaskAndPro.setBuyerName(agencyTaskOne.getBuyerName());
                ageTaskAndPro.setBuyerLeaveMag(agencyTaskOne.getBuyerLeaveMag());
                ageTaskAndPro.setBuyerTel(agencyTaskOne.getBuyerTel());
                String proId = agencyTaskOne.getProductId();//产品Id
                ProductList productList = productListMapper.selectByPrimaryKey(Integer.valueOf(proId));
                ageTaskAndPro.setPrice(productList.getPrice());//当前价格
                ageTaskAndPro.setBrand(productList.getBrand());
                ageTaskAndPro.setModel(productList.getModel());//产品型号
                ageTaskAndPro.setPromotPrice(productList.getPromotPrice());//加价价格
                ageTaskAndPro.setPromotQuantity(productList.getPromotQuantity());//购买数量
                list.add(ageTaskAndPro);
            }
        }

        return list;
    }
}
