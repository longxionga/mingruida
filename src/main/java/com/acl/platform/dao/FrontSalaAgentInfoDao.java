package com.acl.platform.dao;


import com.acl.pojo.FrontSalaAgentInfo;

import java.util.Map;

public interface FrontSalaAgentInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(FrontSalaAgentInfo record);

    int insertSelective(FrontSalaAgentInfo record);

    FrontSalaAgentInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FrontSalaAgentInfo record);

    int updateByPrimaryKey(FrontSalaAgentInfo record);

    int deleteSalaAgentAll(Map paramsMap);
}