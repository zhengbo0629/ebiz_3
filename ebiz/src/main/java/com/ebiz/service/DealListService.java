package com.ebiz.service;


import com.ebiz.dao.DealListMapper;
import com.ebiz.model.DealList;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizUser;
import com.ebiz.model.ProductList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DealListService {


    @Autowired
    private DealListMapper dealListMapper;

    @Autowired
    private OperationRecordService operationRecordService;

    public void addDeal(EbizUser currentUser, EbizCompany currentCompany, ProductList product) {
        DealList dealList = new DealList();
        BeanUtils.copyProperties(product, dealList);
        dealList.setCompanyName(currentCompany.getCompanyName());
        dealListMapper.insertAndGetId(dealList);
        //添加记录
        operationRecordService.addOperationRecord(currentUser , currentCompany , "DealList" , dealList.getId());

    }
}
