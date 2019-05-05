package com.ebiz.dao;

import com.ebiz.model.BuyerPackageList;
import com.ebiz.model.BuyerPackageListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BuyerPackageListMapper {
    int countByExample(BuyerPackageListExample example);

    int deleteByExample(BuyerPackageListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BuyerPackageList record);

    int insertSelective(BuyerPackageList record);

    List<BuyerPackageList> selectByExample(BuyerPackageListExample example);

    BuyerPackageList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BuyerPackageList record, @Param("example") BuyerPackageListExample example);

    int updateByExample(@Param("record") BuyerPackageList record, @Param("example") BuyerPackageListExample example);

    int updateByPrimaryKeySelective(BuyerPackageList record);

    int updateByPrimaryKey(BuyerPackageList record);
}