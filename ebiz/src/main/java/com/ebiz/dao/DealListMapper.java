package com.ebiz.dao;

import com.ebiz.model.DealList;
import com.ebiz.model.DealListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DealListMapper {
    int countByExample(DealListExample example);

    int deleteByExample(DealListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DealList record);

    int insertSelective(DealList record);

    List<DealList> selectByExample(DealListExample example);

    DealList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DealList record, @Param("example") DealListExample example);

    int updateByExample(@Param("record") DealList record, @Param("example") DealListExample example);

    int updateByPrimaryKeySelective(DealList record);

    int updateByPrimaryKey(DealList record);

    int insertAndGetId(DealList dealList);
}