package com.ebiz.dao;

import com.ebiz.model.WarehouseProductList;
import com.ebiz.model.WarehouseProductListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseProductListMapper {
    int countByExample(WarehouseProductListExample example);

    int deleteByExample(WarehouseProductListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseProductList record);

    int insertSelective(WarehouseProductList record);

    List<WarehouseProductList> selectByExample(WarehouseProductListExample example);

    WarehouseProductList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WarehouseProductList record, @Param("example") WarehouseProductListExample example);

    int updateByExample(@Param("record") WarehouseProductList record, @Param("example") WarehouseProductListExample example);

    int updateByPrimaryKeySelective(WarehouseProductList record);

    int updateByPrimaryKey(WarehouseProductList record);
}