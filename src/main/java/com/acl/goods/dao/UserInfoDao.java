package com.acl.goods.dao;


import com.acl.pojo.UserInfo;

import java.util.List;

public interface UserInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> getUserInfoList(UserInfo record);
}