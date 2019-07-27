package com.acl.goods.dao;


import com.acl.pojo.UserCapital;

public interface UserCapitalDao {
    int deleteByPrimaryKey(String id);

    int insert(UserCapital record);

    int insertSelective(UserCapital record);

    UserCapital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCapital record);

    int updateByPrimaryKey(UserCapital record);
}