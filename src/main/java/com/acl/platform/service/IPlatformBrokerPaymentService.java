package com.acl.platform.service;

import com.acl.pojo.CzOrder;
import com.acl.pojo.TxOrder;

public interface IPlatformBrokerPaymentService {
  
  /**
   * 新增经纪人充值订单
   **/
  boolean insertBrokerCzOrder(CzOrder czOrder);

  /**
   * 处理经纪人充值订单
   **/
  boolean processBrokerRechargeOrder(CzOrder czOrder);
  
  /**
   * 新增经纪人提现订单
   **/
  boolean insertBrokerTxOrder(TxOrder txOrder);
  
  /**
   * 处理经纪人提现订单
   **/
  boolean processBrokerWithdrawOrder(TxOrder txOrder);
  
  /**
   * 处理经纪人中南提现订单
   **/
  //boolean processBrokerWithdrawOrderZn(TxOrder txOrder);
  
  public boolean rebackBrokerWithdrawOrder(TxOrder txOrder);
}
