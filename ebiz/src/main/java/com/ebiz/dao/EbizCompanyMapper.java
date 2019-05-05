package com.ebiz.dao;

import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizCompanyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EbizCompanyMapper {
    int countByExample(EbizCompanyExample example);

    int deleteByExample(EbizCompanyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EbizCompany record);

    int insertSelective(EbizCompany record);

    List<EbizCompany> selectByExample(EbizCompanyExample example);

    EbizCompany selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EbizCompany record, @Param("example") EbizCompanyExample example);

    int updateByExample(@Param("record") EbizCompany record, @Param("example") EbizCompanyExample example);

    int updateByPrimaryKeySelective(EbizCompany record);

    int updateByPrimaryKey(EbizCompany record);
}