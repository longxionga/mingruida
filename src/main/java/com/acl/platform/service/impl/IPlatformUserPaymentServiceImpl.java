package com.acl.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.dao.IPlatformUserPaymentDao;
import com.acl.platform.service.IPlatformUserPaymentService;
import com.acl.pojo.CzOrder;
import com.acl.pojo.CzOrderRs;
import com.acl.pojo.TxOrder;
import com.acl.pojo.TxOrderRs;
import com.acl.pojo.UserProfitLoss;
import com.acl.pojo.Wallet;
import com.anchol.common.component.distributedlock.DistributedLock;


@Service
@Transactional
public class IPlatformUserPaymentServiceImpl implements IPlatformUserPaymentService {

  @Autowired
  private IPlatformUserPaymentDao userPaymentDao;
  
  @Autowired
  private DistributedLock distributedLock;
  
  @Autowired
  private StringRedisTemplate redisTemplate;
  
  @Autowired
  protected MongoTemplate mongoTemplate;
  
  @Autowired
  private IPlatformCommonPaymentDao commonPaymentDao;
  
/////////////////////////////////////////////////////用户冲值//////////////////////////////////////////////////////
  
  /**
   * 新增用户充值订单
   **/
  public boolean insertCzOrder(CzOrder czOrder) {
    if (commonPaymentDao.insertCzOrder(czOrder) == 1)
      return true;
    else
      return false;
  }

  /**
   * 处理用户充值订单
   **/
  @Override
  public boolean processUserRechargeOrder(CzOrder czOrder) {
    Map<String, Object> userCzOrderMap = new HashMap<String, Object>();
    Date date = new Date();
    userCzOrderMap.put("old_create_date", czOrder.getCreate_date());
    userCzOrderMap.put("new_create_date", date);
    userCzOrderMap.put("order_id", czOrder.getOrder_id());
    if (commonPaymentDao.updateCzOrder(userCzOrderMap) == 1) {//乐观锁
		// 更新redis的用户余额
	    String lockUserWallet = distributedLock.getLock("wallet", czOrder.getUser_id());
	    try {
	      CzOrderRs czOrderRs = new CzOrderRs();
	      czOrderRs.setOrder_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
	      czOrderRs.setOrder_id(czOrder.getOrder_id());
	      czOrderRs.setOrder_rs_no(czOrder.getOrder_rs_no());
	      czOrderRs.setUser_id(czOrder.getUser_id());
	      czOrderRs.setOrder_rs_fee(czOrder.getOrder_money());
	      czOrderRs.setOrder_rs_brokeage(0);
	      czOrderRs.setOrder_rs_type(czOrder.getOrder_rs_type());
	      czOrderRs.setOrder_rs_status("1");
	      czOrderRs.setCreate_date(date);
	      czOrderRs.setIs_use("1");
	      commonPaymentDao.insertCzOrderRs(czOrderRs);
	      
	      Wallet wallet = commonPaymentDao.queryUserWalletByUserId(czOrder.getUser_id());//查询用户balance
	      UserProfitLoss userProfitLoss = new UserProfitLoss();
	      userProfitLoss.setProfitloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
	      userProfitLoss.setUser_id(wallet.getUser_id());
	      userProfitLoss.setUser_money_before(wallet.getUser_money());
	      userProfitLoss.setUser_money(czOrder.getOrder_money());
	      userProfitLoss.setUser_money_after(wallet.getUser_money() + czOrder.getOrder_money());
	      userProfitLoss.setMoney_type("05"); // 充值
	      userProfitLoss.setCreate_date(new Date());
	      userProfitLoss.setIs_use("1");
	      userProfitLoss.setOrder_rs_no(czOrder.getOrder_id());
	      userPaymentDao.insertUserProfitLoss(userProfitLoss);
	      
	      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
	      userBrokerWalletMap.put("type", "add");
	      userBrokerWalletMap.put("order_money", czOrder.getOrder_money());
	      userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
	      userPaymentDao.updateWallet(userBrokerWalletMap);
	      
	      // 新增mongodb用户流水
	      mongoTemplate.save(userProfitLoss, "UserProfitLoss");
      
      
        String key = "wallet_" + czOrder.getUser_id();
        double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
        double newWallet = userWallet + czOrder.getOrder_money();
        redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
      } finally {
        distributedLock.releaseLock("wallet", czOrder.getUser_id(), lockUserWallet);
      }
    } else
      return false;
    return true;
  }
  
///////////////////////////////////////////////////////用户提现//////////////////////////////////////////////////////

  
  /**
   * 新增用户提现订单
   **/
  @Override
  public boolean insertTxOrder(TxOrder txOrder) {
    String lockUserWithdraw = distributedLock.getLock("withdraw", txOrder.getUser_id());
    if (lockUserWithdraw == null) 
      return false;
    try {
      Wallet wallet = commonPaymentDao.queryUserWalletByUserId(txOrder.getUser_id());
      if (txOrder.getTx_money() <= wallet.getUser_money()) {
        UserProfitLoss userProfitLoss = new UserProfitLoss();
        userProfitLoss.setProfitloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
        userProfitLoss.setUser_id(wallet.getUser_id());
        userProfitLoss.setUser_money_before(wallet.getUser_money());
        userProfitLoss.setUser_money(txOrder.getTx_money());
        userProfitLoss.setUser_money_after(wallet.getUser_money() - txOrder.getTx_money());
        userProfitLoss.setMoney_type("06"); // 提现
        userProfitLoss.setCreate_date(new Date());
        userProfitLoss.setIs_use("1");
        userProfitLoss.setOrder_rs_no(txOrder.getOrder_rs_no());
        userPaymentDao.insertUserProfitLoss(userProfitLoss);
        
        commonPaymentDao.insertTxOrder(txOrder);
        
        Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
        userBrokerWalletMap.put("type", "subtract");
        userBrokerWalletMap.put("order_money", txOrder.getTx_money());
        userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
        userPaymentDao.updateWallet(userBrokerWalletMap);
        
        // 新增mongodb用户流水
        mongoTemplate.save(userProfitLoss, "UserProfitLoss");
        
        // 更新redis的用户余额
        String lockUserWallet = distributedLock.getLock("wallet", txOrder.getUser_id());
        try {
          String key = "wallet_" + txOrder.getUser_id();
          double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
          double newWallet = userWallet - txOrder.getTx_money();
          redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
        } finally {
          distributedLock.releaseLock("wallet", txOrder.getUser_id(), lockUserWallet);
        }
      } else
        return false;
    } finally {
      distributedLock.releaseLock("withdraw", txOrder.getUser_id(), lockUserWithdraw);
    }
    return true;
  }

  /**
   * 处理用户提现订单
   **/
  @Override
  public boolean processUserWithdrawOrder(TxOrder txOrder) {
	  //更改订单状态
	  /*Map<String, Object> ma = new HashMap<String, Object>();
      ma.put("is_use", "0");//0表示不可用
      ma.put("tx_order_id", txOrder.getTx_order_id());
      commonPaymentDao.updateTxOrder(ma);*/
    if (StringUtils.equals(txOrder.getIs_use(), "1")) {//订单状态为成功
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id());
      txOrderRs.setTx_order_id(txOrder.getTx_order_id());
      txOrderRs.setIs_use("1");
      txOrderRs.setTx_rs_status(com.acl.utils.util.StringUtils.convertString(txOrder.getReturnMsg()));  
      txOrderRs.setTx_number(com.acl.utils.util.StringUtils.convertString(txOrder.getOrder_rs_no()));
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
      commonPaymentDao.insertTxOrderRs(txOrderRs);
      return true;
    } else {
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id());
      txOrderRs.setTx_order_id(txOrder.getTx_order_id());
      txOrderRs.setIs_use("0");
      txOrderRs.setTx_rs_status(com.acl.utils.util.StringUtils.convertString(txOrder.getReturnMsg())); // 提现失败
      txOrderRs.setTx_number(com.acl.utils.util.StringUtils.convertString(txOrder.getOrder_rs_no()));
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
      commonPaymentDao.insertTxOrderRs(txOrderRs);
      
     /* Wallet wallet = commonPaymentDao.queryUserWalletByUserId(txOrder.getUser_id());//查询用户balance
      UserProfitLoss userProfitLoss = new UserProfitLoss();
      userProfitLoss.setProfitloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      userProfitLoss.setUser_id(wallet.getUser_id());
      userProfitLoss.setUser_money_before(wallet.getUser_money());
      userProfitLoss.setUser_money(txOrder.getTx_money());
      userProfitLoss.setUser_money_after(wallet.getUser_money() + txOrder.getTx_money());
      userProfitLoss.setMoney_type("07"); // 退款
      userProfitLoss.setCreate_date(new Date());
      userProfitLoss.setIs_use("1");
      userProfitLoss.setOrder_id(txOrder.getTx_order_id());
      userProfitLoss.setOrder_rs_no(txOrder.getOrder_rs_no());// 提现流水号或批次号
      userPaymentDao.insertUserProfitLoss(userProfitLoss);
      
      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("order_money", txOrder.getTx_money());
      userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
      userPaymentDao.updateWallet(userBrokerWalletMap);
      
      // 新增mongodb用户流水
      mongoTemplate.save(userProfitLoss, "UserProfitLoss");
      
      // 更新redis的用户余额
      String lockUserWallet = distributedLock.getLock("wallet", txOrder.getUser_id());
      try {
        String key = "wallet_" + txOrder.getUser_id();
        double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
        double newWallet = userWallet + txOrder.getTx_money();
        redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
      } finally {
        distributedLock.releaseLock("wallet", txOrder.getUser_id(), lockUserWallet);
      }*/
      return false;
    }
  }
  
  
  /**
   * 不通过用户提现订单
   **/
  @Override
  public boolean rebackUserWithDraw(TxOrder txOrder) {
	  //更改订单状态
	  Map<String, Object> ma = new HashMap<String, Object>();
      ma.put("is_use", "0");//0表示不可用
      ma.put("tx_order_id", txOrder.getTx_order_id());
      commonPaymentDao.updateTxOrder(ma);//修改提现订单状态为0
       //增加返回结果
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id());
      txOrderRs.setTx_order_id(txOrder.getTx_order_id());
      txOrderRs.setIs_use("4");
      txOrderRs.setTx_rs_status("平台管理退回"); // 提现失败
      txOrderRs.setTx_number("");
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
      commonPaymentDao.insertTxOrderRs(txOrderRs);
      //修改钱包余额
      Wallet wallet = commonPaymentDao.queryUserWalletByUserId(txOrder.getUser_id());//查询用户balance
      //增加退单流水
      UserProfitLoss userProfitLoss = new UserProfitLoss();
      userProfitLoss.setProfitloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      userProfitLoss.setUser_id(wallet.getUser_id());
      userProfitLoss.setUser_money_before(wallet.getUser_money());
      userProfitLoss.setUser_money(txOrder.getTx_money());
      userProfitLoss.setUser_money_after(wallet.getUser_money() + txOrder.getTx_money());
      userProfitLoss.setMoney_type("07"); // 退款
      userProfitLoss.setCreate_date(new Date());
      userProfitLoss.setIs_use("1");
      userProfitLoss.setOrder_id(txOrder.getTx_order_id());
      userProfitLoss.setOrder_rs_no("");// 提现流水号或批次号
      userPaymentDao.insertUserProfitLoss(userProfitLoss);
      
      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("order_money", txOrder.getTx_money());
      userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
      userPaymentDao.updateWallet(userBrokerWalletMap);
      
      // 新增mongodb用户流水
      mongoTemplate.save(userProfitLoss, "UserProfitLoss");
      
      // 更新redis的用户余额
      String lockUserWallet = distributedLock.getLock("wallet", txOrder.getUser_id());
      try {
        String key = "wallet_" + txOrder.getUser_id();
        double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
        double newWallet = userWallet + txOrder.getTx_money();
        redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
      } finally {
        distributedLock.releaseLock("wallet", txOrder.getUser_id(), lockUserWallet);
      }
      return true;  
  }
  
  
  //中南支付提现
  /**
   * 处理用户提现订单
   **/

 /* public boolean processUserWithdrawOrderZn(TxOrder txOrder) {
	 
    if (StringUtils.equals(txOrder.getIs_use(), "1")) {//订单状态为成功
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id());
      txOrderRs.setTx_order_id(txOrder.getTx_order_id());
      txOrderRs.setIs_use("1");
      txOrderRs.setTx_rs_status(com.acl.utils.util.StringUtils.convertString(txOrder.getReturnMsg()));  
      txOrderRs.setTx_number(com.acl.utils.util.StringUtils.convertString(txOrder.getOrder_rs_no()));
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
      commonPaymentDao.insertTxOrderRs(txOrderRs);
      return true;
    } else {
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id());
      txOrderRs.setTx_order_id(txOrder.getTx_order_id());
      txOrderRs.setIs_use("0");
      txOrderRs.setTx_rs_status(com.acl.utils.util.StringUtils.convertString(txOrder.getReturnMsg())); // 提现失败
      txOrderRs.setTx_number(com.acl.utils.util.StringUtils.convertString(txOrder.getOrder_rs_no()));
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
      commonPaymentDao.insertTxOrderRs(txOrderRs);
      
      Wallet wallet = commonPaymentDao.queryUserWalletByUserId(txOrder.getUser_id());//查询用户balance
      UserProfitLoss userProfitLoss = new UserProfitLoss();
      userProfitLoss.setProfitloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      userProfitLoss.setUser_id(wallet.getUser_id());
      userProfitLoss.setUser_money_before(wallet.getUser_money());
      userProfitLoss.setUser_money(txOrder.getTx_money());
      userProfitLoss.setUser_money_after(wallet.getUser_money() + txOrder.getTx_money());
      userProfitLoss.setMoney_type("07"); // 退款
      userProfitLoss.setCreate_date(new Date());
      userProfitLoss.setIs_use("1");
      userProfitLoss.setOrder_id(txOrder.getTx_order_id());
      userProfitLoss.setOrder_rs_no(txOrder.getOrder_rs_no());// 提现流水号或批次号
      userPaymentDao.insertUserProfitLoss(userProfitLoss);
      
      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("order_money", txOrder.getTx_money());
      userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
      userPaymentDao.updateWallet(userBrokerWalletMap);
      
      // 新增mongodb用户流水
      mongoTemplate.save(userProfitLoss, "UserProfitLoss");
      
      // 更新redis的用户余额
      String lockUserWallet = distributedLock.getLock("wallet", txOrder.getUser_id());
      try {
        String key = "wallet_" + txOrder.getUser_id();
        double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
        double newWallet = userWallet + txOrder.getTx_money();
        redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
      } finally {
        distributedLock.releaseLock("wallet", txOrder.getUser_id(), lockUserWallet);
      }
      return false;
    }
    
  }*/
  

}
