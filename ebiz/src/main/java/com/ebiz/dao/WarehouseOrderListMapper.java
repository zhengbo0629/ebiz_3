package com.ebiz.dao;

import com.ebiz.model.WarehouseOrderList;
import com.ebiz.model.WarehouseOrderListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseOrderListMapper {
    int countByExample(WarehouseOrderListExample example);

    int deleteByExample(WarehouseOrderListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseOrderList record);

    int insertSelective(WarehouseOrderList record);

    List<WarehouseOrderList> selectByExample(WarehouseOrderListExample example);

    WarehouseOrderList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WarehouseOrderList record, @Param("example") WarehouseOrderListExample example);

    int updateByExample(@Param("record") WarehouseOrderList record, @Param("example") WarehouseOrderListExample example);

    int updateByPrimaryKeySelective(WarehouseOrderList record);

    int updateByPrimaryKey(WarehouseOrderList record);
}