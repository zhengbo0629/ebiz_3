package com.ebiz.dao;

import com.ebiz.model.PackageList;
import com.ebiz.model.PackageListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PackageListMapper {
    int countByExample(PackageListExample example);

    int deleteByExample(PackageListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PackageList record);

    int insertSelective(PackageList record);

    List<PackageList> selectByExample(PackageListExample example);

    PackageList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PackageList record, @Param("example") PackageListExample example);

    int updateByExample(@Param("record") PackageList record, @Param("example") PackageListExample example);

    int updateByPrimaryKeySelective(PackageList record);

    int updateByPrimaryKey(PackageList record);

    int insertAndGetMaxId(PackageList tempPackage);

    List<String> getUserNameByCountPackage(@Param("companyName") String companyName, @Param("count") Integer count,@Param("month") Integer month);
}