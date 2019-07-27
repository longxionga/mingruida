package com.acl.platform.dao;

import java.util.Map;

import com.acl.pojo.BrokerProfitLoss;
import com.acl.pojo.DeptProrate;

public interface IPlatformBrokerPaymentDao {

  Map<String, Object> queryDeptInfoByBrokerCode(Map<String, Object> map);
	/**
   * 写入支付ID
   * 
   **/
  int updatePayWxId(Map<String, Object> map);
  
  /**
   * 查询经纪人
   **/
  Map<String, Object> queryBroker(String brokerId);
  
  /**
   * 更新经纪人
   **/
  int updateBroker(Map<String, Object> map);
  
  /**
//   * 新增经纪人充值订单
//   **/
//  int insertBrokerCzOrder(CzOrder czOrder);
  
  /**
   * 新增经纪人流水
   **/
  int insertBrokerProfitLoss(BrokerProfitLoss brokerProfitLoss);
  
  /**
   * 新增部门比例分配
   **/
  int insertDeptProrate(DeptProrate deptProrate);
  
  /**
   * 新增临时部门比例分配
   **/
  int insertDeptProrateTemp(DeptProrate deptProrate);
}
