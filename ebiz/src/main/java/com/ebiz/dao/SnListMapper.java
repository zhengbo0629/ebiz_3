package com.ebiz.dao;

import com.ebiz.model.SnList;
import com.ebiz.model.SnListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SnListMapper {
    int countByExample(SnListExample example);

    int deleteByExample(SnListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SnList record);

    int insertSelective(SnList record);

    List<SnList> selectByExample(SnListExample example);

    SnList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SnList record, @Param("example") SnListExample example);

    int updateByExample(@Param("record") SnList record, @Param("example") SnListExample example);

    int updateByPrimaryKeySelective(SnList record);

    int updateByPrimaryKey(SnList record);
}