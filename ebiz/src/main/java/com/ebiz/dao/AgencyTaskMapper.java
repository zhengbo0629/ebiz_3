package com.ebiz.dao;

import com.ebiz.model.AgencyTask;
import com.ebiz.model.AgencyTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgencyTaskMapper {
    int countByExample(AgencyTaskExample example);

    int deleteByExample(AgencyTaskExample example);

    int deleteByPrimaryKey(Integer ATid);

    int insert(AgencyTask record);

    int insertSelective(AgencyTask record);

    List<AgencyTask> selectByExample(AgencyTaskExample example);

    AgencyTask selectByPrimaryKey(Integer ATid);

    int updateByExampleSelective(@Param("record") AgencyTask record, @Param("example") AgencyTaskExample example);

    int updateByExample(@Param("record") AgencyTask record, @Param("example") AgencyTaskExample example);

    int updateByPrimaryKeySelective(AgencyTask record);

    int updateByPrimaryKey(AgencyTask record);
}