package com.acl.platform.dao;

import java.util.Map;

import com.acl.pojo.CzOrder;
import com.acl.pojo.CzOrderRs;
import com.acl.pojo.TxOrder;
import com.acl.pojo.TxOrderRs;
import com.acl.pojo.Wallet;


public interface IPlatformCommonPaymentDao {

  /**
   * 新增充值订单表
   **/
  int insertCzOrder(CzOrder czOrder);

  /**
   * 新增充值订单回调表
   **/
  int insertCzOrderRs(CzOrderRs czOrderRs);

  /**
   * 更新充值订单
   **/
  int updateCzOrder(Map<String, Object> map);

  /**
   * 新增提现订单
   **/
  int insertTxOrder(TxOrder txOrder);

  /**
   * 新增提现回调订单
   **/
  int insertTxOrderRs(TxOrderRs txOrderRs);

  /**
   * 更新提现订单
   **/
  int updateTxOrder(Map<String, Object> map);

  /**
   * 通过订单号查询充值订单
   **/
  CzOrder queryCzOrderByOrderId(Map<String, Object> map);

  /**
   * 通过订单号查询提现订单
   **/
  TxOrder queryTxOrderByOrderId(Map<String, Object> map);

  /**
   * 通过订单号查询充值用户
   **/
  Map<String, Object> queryBrokerParentInfo(Map<String, Object> map);

  /**
   * 通过用户ID查询用户钱包
   **/
  Wallet queryUserWalletByUserId(String userId);
}
