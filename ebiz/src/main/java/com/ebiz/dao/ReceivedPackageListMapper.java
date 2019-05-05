package com.ebiz.dao;

import com.ebiz.model.ReceivedPackageList;
import com.ebiz.model.ReceivedPackageListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReceivedPackageListMapper {
    int countByExample(ReceivedPackageListExample example);

    int deleteByExample(ReceivedPackageListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReceivedPackageList record);

    int insertSelective(ReceivedPackageList record);

    List<ReceivedPackageList> selectByExample(ReceivedPackageListExample example);

    ReceivedPackageList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReceivedPackageList record, @Param("example") ReceivedPackageListExample example);

    int updateByExample(@Param("record") ReceivedPackageList record, @Param("example") ReceivedPackageListExample example);

    int updateByPrimaryKeySelective(ReceivedPackageList record);

    int updateByPrimaryKey(ReceivedPackageList record);
}