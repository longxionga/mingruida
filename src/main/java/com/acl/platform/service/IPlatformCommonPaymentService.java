package com.acl.platform.service;

import java.util.Map;

import com.acl.pojo.CzOrder;

public interface IPlatformCommonPaymentService {
  /**
   * 通过订单号查询用户充值订单
   **/
  CzOrder queryCzOrderByOrderId(Map<String, Object> map);
  
  /**
   * 通过订单号查询充值用户
   **/
  Map<String, Object> queryBrokerParentInfo(Map<String, Object> map);
  
}
