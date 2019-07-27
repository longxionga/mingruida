package com.acl.platform.dao;

import java.util.Map;

import com.acl.pojo.UserProfitLoss;

public interface IPlatformUserPaymentDao {

  /**
   * 新增用户流水表
   **/
  int insertUserProfitLoss(UserProfitLoss userProfitLoss);
  
  /**
   * 更新用户钱包表
   **/
  int updateWallet(Map<String, Object> map);
  
  /**
   * 写入支付ID
   * 
   **/
  int updatePayWxId(Map<String, Object> map);
}
