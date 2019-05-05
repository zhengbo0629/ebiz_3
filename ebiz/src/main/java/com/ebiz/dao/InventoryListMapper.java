package com.ebiz.dao;

import com.ebiz.model.InventoryList;
import com.ebiz.model.InventoryListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryListMapper {
    int countByExample(InventoryListExample example);

    int deleteByExample(InventoryListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InventoryList record);

    int insertSelective(InventoryList record);

    List<InventoryList> selectByExample(InventoryListExample example);

    InventoryList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InventoryList record, @Param("example") InventoryListExample example);

    int updateByExample(@Param("record") InventoryList record, @Param("example") InventoryListExample example);

    int updateByPrimaryKeySelective(InventoryList record);

    int updateByPrimaryKey(InventoryList record);
}