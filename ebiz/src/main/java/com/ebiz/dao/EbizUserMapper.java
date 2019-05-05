package com.ebiz.dao;

import com.ebiz.model.EbizUser;
import com.ebiz.model.EbizUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EbizUserMapper {
    int countByExample(EbizUserExample example);

    int deleteByExample(EbizUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EbizUser record);

    int insertSelective(EbizUser record);

    List<EbizUser> selectByExample(EbizUserExample example);

    EbizUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EbizUser record, @Param("example") EbizUserExample example);

    int updateByExample(@Param("record") EbizUser record, @Param("example") EbizUserExample example);

    int updateByPrimaryKeySelective(EbizUser record);

    int updateByPrimaryKey(EbizUser record);
}