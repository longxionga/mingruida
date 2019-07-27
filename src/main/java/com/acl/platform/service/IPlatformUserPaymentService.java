package com.acl.platform.service;

import com.acl.pojo.CzOrder;
import com.acl.pojo.TxOrder;

public interface IPlatformUserPaymentService {
  
  /**
   * 新增用户充值订单
   **/
  boolean insertCzOrder(CzOrder czOrder);

  /**
   * 处理用户充值订单
   **/
  boolean processUserRechargeOrder(CzOrder czOrder);
  
  /**
   * 处理用户提现订单
   **/
  boolean processUserWithdrawOrder(TxOrder txOrder);
  
  /**
   * 中南提现处理用户提现订单
   **/
  //boolean processUserWithdrawOrderZn(TxOrder txOrder);
  
  /**
   * 新增用户提现订单
   **/
  boolean insertTxOrder(TxOrder TxOrder);
  
  /*
   * 不通过用户提现订单
   */
  public boolean rebackUserWithDraw(TxOrder txOrder);
}
