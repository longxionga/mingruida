package com.acl.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformBrokerPaymentDao;
import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.service.IPlatformBrokerPaymentService;
import com.acl.pojo.BrokerProfitLoss;
import com.acl.pojo.CzOrder;
import com.acl.pojo.CzOrderRs;
import com.acl.pojo.DeptProrate;
import com.acl.pojo.TxOrder;
import com.acl.pojo.TxOrderRs;
import com.anchol.common.component.distributedlock.DistributedLock;


@Service
@Transactional
public class IPlatformBrokerPaymentServiceImpl implements IPlatformBrokerPaymentService {

  @Autowired
  private IPlatformBrokerPaymentDao brokerPaymentDao;
  
  @Autowired
  protected MongoTemplate mongoTemplate;
  
  @Autowired
  private DistributedLock distributedLock;

  @Autowired
  private IPlatformCommonPaymentDao commonPaymentDao;

///////////////////////////////////////////////经纪人充值////////////////////////////////////////////////////////
  
  /**
   * 新增经纪人充值订单
   **/
  public boolean insertBrokerCzOrder(CzOrder czOrder) {
    if (commonPaymentDao.insertCzOrder(czOrder) == 1)
      return true;
    else
      return false;
  }
  /**
   * 循环累加分配比例
   **/
  public List<Map<String, Object>> loop(Map<String, Object> map, List<Map<String, Object>> list) {
    if (StringUtils.equals(map.get("dept_parent_id").toString(), "-1")) {
      return list;
    }
    map.put("dept_id", map.get("dept_parent_id").toString());
    Map<String, Object> rsMap = brokerPaymentDao.queryDeptInfoByBrokerCode(map);
//    a = a + Integer.parseInt(rsMap.get("dept_ratio").toString());
//    rsMap.put("prorate", a);
    list.add(rsMap);
    return loop(rsMap, list);

  }

  /**
   * 处理经纪人充值订单
   **/
  @Override
  public boolean processBrokerRechargeOrder(CzOrder czOrder) {
    Map<String, Object> userCzOrderMap = new HashMap<String, Object>();
    Date date = new Date();
    userCzOrderMap.put("old_create_date", czOrder.getCreate_date());
    userCzOrderMap.put("new_create_date", date);
    userCzOrderMap.put("order_id", czOrder.getOrder_id());
    if (commonPaymentDao.updateCzOrder(userCzOrderMap) == 1) {//乐观锁
    	
    	// 
    	Map<String, Object> queryMap1 = new HashMap<String, Object>();
    	queryMap1.put("user_id", czOrder.getUser_id());
    	Map<String, Object> parentBroker = commonPaymentDao.queryBrokerParentInfo(queryMap1); // 查询上级经纪人信息
    	String parent_id = parentBroker.get("broker_id").toString();
    	String parent_incode=  parentBroker.get("broker_incode").toString();
    	
    	
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
      
      if (!(StringUtils.equals(parent_incode, "000000") && StringUtils.equals(parent_id, "-1"))) {
        //1.上级经纪人余额加80元
        Map<String, Object> parentBrokerMap = new HashMap<String, Object>();
        parentBrokerMap.put("broker_id", parentBroker.get("broker_id").toString()); // 上级经纪人ID
        parentBrokerMap.put("broker_money", "80");
        parentBrokerMap.put("type", "add");
        brokerPaymentDao.updateBroker(parentBrokerMap);
        //2.新增上级经纪人流水
        BrokerProfitLoss brokerProfitLoss = new BrokerProfitLoss();
        brokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
        brokerProfitLoss.setBroker_id(parentBroker.get("broker_id").toString());
        brokerProfitLoss.setBroker_money_before(Double.parseDouble(parentBroker.get("broker_money").toString()));
        brokerProfitLoss.setBroker_money(80);
        brokerProfitLoss.setBroker_money_after(Double.parseDouble(parentBroker.get("broker_money").toString()) + 80);
        brokerProfitLoss.setMoney_type("09"); // 上级经纪人收益
        brokerProfitLoss.setCreate_date(date);
        brokerProfitLoss.setIs_use("1");
        brokerPaymentDao.insertBrokerProfitLoss(brokerProfitLoss);
        
        //3.更新mongodb经纪人状态
        Criteria updateCriteria = new Criteria();
        updateCriteria.andOperator(Criteria.where("broker_id").is(czOrder.getUser_id()));
        Update update  = new Update();
        update.set("is_use", "1");
        mongoTemplate.updateMulti(new Query(updateCriteria),update, "t_front_broker");
        
        //4.查询上级经纪人
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("broker_id").is(parent_id));   
//        BrokerInfo brokerInfo = mongoTemplate.findOne(new Query(criteria), BrokerInfo.class, "t_front_broker");
//        
//        //5.上级经纪人余额加80元
//        Criteria updateCriteria2 = new Criteria();
//        updateCriteria2.andOperator(Criteria.where("broker_id").is(brokerInfo.getBroker_id()));
//        Update update2  = new Update();
//        update2.set("broker_money", brokerInfo.getBroker_money() + 80);
//        mongoTemplate.updateMulti(new Query(updateCriteria2), update2, "t_front_broker");
      }
      //1.改自己状态
      Map<String, Object> selfBrokerMap = new HashMap<String, Object>();
      selfBrokerMap.put("broker_id", czOrder.getUser_id());
      selfBrokerMap.put("is_use", "1");
      brokerPaymentDao.updateBroker(selfBrokerMap);
      
      //2.写自己流水
      BrokerProfitLoss selfBrokerProfitLoss = new BrokerProfitLoss();
      selfBrokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      selfBrokerProfitLoss.setBroker_id(czOrder.getUser_id());
      selfBrokerProfitLoss.setBroker_money_before(0);
      selfBrokerProfitLoss.setBroker_money(200);
      selfBrokerProfitLoss.setBroker_money_after(0);
      selfBrokerProfitLoss.setMoney_type("08"); // 经纪人开通推广权限
      selfBrokerProfitLoss.setCreate_date(new Date());
      selfBrokerProfitLoss.setIs_use("1");
      brokerPaymentDao.insertBrokerProfitLoss(selfBrokerProfitLoss);
      
      //新增用户比例
      Map<String, Object> brokerMap = brokerPaymentDao.queryBroker(czOrder.getUser_id());
      List<Map<String, Object>> list = loop(brokerMap, new ArrayList<Map<String, Object>>());
      String ce_allot = ""; // 交易中心比率
      String ch_allot = ""; // 渠道比率
      String s_allot  = ""; // 服务商比率
      String a_allot  = ""; // 代理商比率
      int prorate = 0;
      for (int i = 0; i < list.size(); i++) {
        Map<String, Object> m = (Map<String, Object>)list.get(i);
        if (StringUtils.equals(m.get("dept_type").toString(), "1")) { // 交易中心
          ce_allot = m.get("dept_ratio").toString();
        }
        if (StringUtils.equals(m.get("dept_type").toString(), "2")) { // 渠道
          ch_allot = m.get("dept_ratio").toString();
        }
        if (StringUtils.equals(m.get("dept_type").toString(), "3")) { // 服务商
          s_allot = m.get("dept_ratio").toString();
        }
        if (StringUtils.equals(m.get("dept_type").toString(), "4")) { // 代理商
          a_allot = m.get("dept_ratio").toString();
        }
        prorate = prorate + Integer.parseInt(m.get("dept_ratio").toString());
      }
      
      DeptProrate deptProrate = new DeptProrate();
      deptProrate.setSys_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      deptProrate.setP_id(brokerMap.get("p_id").toString());
      deptProrate.setCe_id(czOrder.getCe_id());
      deptProrate.setCh_id(czOrder.getCh_id());
      deptProrate.setS_id(czOrder.getSettle_id());
      deptProrate.setA_id(czOrder.getAgent_id());
      deptProrate.setB_id(czOrder.getUser_id());
      deptProrate.setP_allot("0"); // 平台比率为0
      deptProrate.setCe_allot(ce_allot); // 交易中心比率
      deptProrate.setCh_allot(ch_allot); // 渠道比率
      deptProrate.setS_allot(s_allot);   // 服务商比率
      deptProrate.setA_allot(a_allot);   // 代理商比率
      deptProrate.setB_allot(String.valueOf(100 - prorate)); // 经纪人比率
      deptProrate.setIs_use("1");
      deptProrate.setCreate_date(date);
      brokerPaymentDao.insertDeptProrate(deptProrate);
      brokerPaymentDao.insertDeptProrateTemp(deptProrate);
    } else
      return false;
    return true;
  }
  
/////////////////////////////////////////////////经纪人提现////////////////////////////////////////////////////////  
  
  /**
   * 新增经纪人提现订单
   **/
  public boolean insertBrokerTxOrder(TxOrder txOrder) {
    String lockUserWithdraw = distributedLock.getLock("withdraw", txOrder.getUser_id());
    if (lockUserWithdraw == null) 
      return false;
    try {
      Map<String, Object> brokerMap = brokerPaymentDao.queryBroker(txOrder.getUser_id());
      if (txOrder.getTx_money() <= Double.parseDouble(brokerMap.get("broker_money").toString())) {
        BrokerProfitLoss selfBrokerProfitLoss = new BrokerProfitLoss();
        selfBrokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
        selfBrokerProfitLoss.setBroker_id(txOrder.getUser_id());
        selfBrokerProfitLoss.setBroker_money_before(Double.parseDouble(brokerMap.get("broker_money").toString()));
        selfBrokerProfitLoss.setBroker_money(txOrder.getTx_money());
        selfBrokerProfitLoss.setBroker_money_after(txOrder.getTx_money() + Double.parseDouble(brokerMap.get("broker_money").toString()));
        selfBrokerProfitLoss.setMoney_type("06"); // 提现
        selfBrokerProfitLoss.setCreate_date(new Date());
        selfBrokerProfitLoss.setIs_use("1");
        brokerPaymentDao.insertBrokerProfitLoss(selfBrokerProfitLoss); // 新增经纪人流水
        
        commonPaymentDao.insertTxOrder(txOrder); // 新增提现订单
        
        Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
        userBrokerWalletMap.put("type", "subtract");
        userBrokerWalletMap.put("broker_money", txOrder.getTx_money());
        userBrokerWalletMap.put("broker_id", txOrder.getUser_id());
        brokerPaymentDao.updateBroker(userBrokerWalletMap);  // 更新经纪人
        
        // 查询经纪人信息(mongodb)
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));   
////        BrokerMongodb BrokerMongodb = (BrokerMongodb) mongoTemplate.findOne(new Query(criteria), BrokerMongodb.class);
//        BrokerInfo brokerInfo = mongoTemplate.findOne(new Query(criteria), BrokerInfo.class, "t_front_broker");
//        
//        // 更新经纪人(mongodb)
//        Criteria updateCriteria2 = new Criteria();
//        updateCriteria2.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));
//        Update update2  = new Update();
//        update2.set("broker_money", brokerInfo.getBroker_money() - txOrder.getTx_money());
//        mongoTemplate.updateMulti(new Query(updateCriteria2), update2, "t_front_broker");
      } else
        return false;
    } finally {
      distributedLock.releaseLock("withdraw", txOrder.getUser_id(), lockUserWithdraw);
    }
    return true;
  }
  
  /**
   * 处理经纪人提现订单
   **/
  @Override
  public boolean processBrokerWithdrawOrder(TxOrder txOrder) {
	  //更改订单状态
	  /*Map<String, Object> ma = new HashMap<String, Object>();
      ma.put("is_use", 0);//0表示不可用
      ma.put("tx_order_id", txOrder.getTx_order_id());
      commonPaymentDao.updateTxOrder(ma);*/
      //更改订单状态结束
      //插入一条记录到rs表中
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_order_id(StringUtils.remove(UUID.randomUUID().toString(), '-')); // 主键id
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id()); // 用户id
      txOrderRs.setTx_order_id(txOrder.getTx_order_id()); // 用户提现订单id
      txOrderRs.setTx_rs_status(txOrder.getReturnMsg()); // 回调信息
      txOrderRs.setTx_number(txOrder.getOrder_rs_no()); // 商户流水号
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
    if (StringUtils.equals(txOrder.getIs_use(), "1")) {  
    //提现成功插入一条记录到rs表中	
      txOrderRs.setIs_use("1"); // 0：失败 1：成功
      commonPaymentDao.insertTxOrderRs(txOrderRs);
    } else {
    //提现失败
    //插入一条记录到rs表中	
     txOrderRs.setIs_use("0"); // 0：失败 1：成功
     commonPaymentDao.insertTxOrderRs(txOrderRs); 
     //以下为退款失败后的流水和余额处理
    //根据经纪人id查询经纪人信息
     // Map<String, Object> brokerMap = brokerPaymentDao.queryBroker(txOrder.getUser_id());
    //插入一条记录到经纪人流水表中 
     /* BrokerProfitLoss selfBrokerProfitLoss = new BrokerProfitLoss();
      selfBrokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      selfBrokerProfitLoss.setBroker_id(txOrder.getUser_id());
      selfBrokerProfitLoss.setBroker_money_before(Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setBroker_money(txOrder.getTx_money());
      selfBrokerProfitLoss.setBroker_money_after(txOrder.getTx_money() + Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setMoney_type("07"); // 退款
      selfBrokerProfitLoss.setCreate_date(new Date());
      selfBrokerProfitLoss.setIs_use("1");
      selfBrokerProfitLoss.setOrder_id(txOrder.getTx_order_id());*/
    //新增经纪人流水  
     // brokerPaymentDao.insertBrokerProfitLoss(selfBrokerProfitLoss); 
    //更新经纪人余额      
    /*  Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("broker_money", txOrder.getTx_money());
      userBrokerWalletMap.put("broker_id", txOrder.getUser_id());
      brokerPaymentDao.updateBroker(userBrokerWalletMap);  // 更新经纪人
*/      
    //以上为退款失败后的流水和余额处理
     
     /*// 查询经纪人信息(mongodb)
      Criteria criteria = new Criteria();
      criteria.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));   
      BrokerInfo brokerInfo = mongoTemplate.findOne(new Query(criteria), BrokerInfo.class, "t_front_broker");
      
      // 更新经纪人(mongodb)
      Criteria updateCriteria2 = new Criteria();
      updateCriteria2.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));
      Update update2  = new Update();
      update2.set("broker_money", brokerInfo.getBroker_money() + txOrder.getTx_money());
      mongoTemplate.updateMulti(new Query(updateCriteria2), update2, "t_front_broker");
      */
   // 新增mongodb经纪人流水
     // mongoTemplate.save(selfBrokerProfitLoss, "BrokerProfitLoss");
 }
    
    return true;
  }
  
  
  
  /**
   * 处理经纪人提现订单
   **/
  @Override
  public boolean rebackBrokerWithdrawOrder(TxOrder txOrder) {
	  //更改订单状态
	  Map<String, Object> ma = new HashMap<String, Object>();
      ma.put("is_use", 0);//0表示不可用
      ma.put("tx_order_id", txOrder.getTx_order_id());
      commonPaymentDao.updateTxOrder(ma);
      //更改订单状态结束
      //插入一条记录到rs表中
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_order_id(StringUtils.remove(UUID.randomUUID().toString(), '-')); // 主键id
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id()); // 用户id
      txOrderRs.setTx_order_id(txOrder.getTx_order_id()); // 用户提现订单id
      txOrderRs.setIs_use("4");
      txOrderRs.setTx_rs_status("平台审核退回"); // 回调信息
      txOrderRs.setTx_number(""); // 商户流水号
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
  
    //提现失败
    //插入一条记录到rs表中	
     txOrderRs.setIs_use("0"); // 0：失败 1：成功
     commonPaymentDao.insertTxOrderRs(txOrderRs); 	
    //根据经纪人id查询经纪人信息
      Map<String, Object> brokerMap = brokerPaymentDao.queryBroker(txOrder.getUser_id());
    //插入一条记录到经纪人流水表中 
      BrokerProfitLoss selfBrokerProfitLoss = new BrokerProfitLoss();
      selfBrokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      selfBrokerProfitLoss.setBroker_id(txOrder.getUser_id());
      selfBrokerProfitLoss.setBroker_money_before(Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setBroker_money(txOrder.getTx_money());
      selfBrokerProfitLoss.setBroker_money_after(txOrder.getTx_money() + Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setMoney_type("07"); // 退款
      selfBrokerProfitLoss.setCreate_date(new Date());
      selfBrokerProfitLoss.setIs_use("1");
      selfBrokerProfitLoss.setOrder_id(txOrder.getTx_order_id());
    //新增经纪人流水  
      brokerPaymentDao.insertBrokerProfitLoss(selfBrokerProfitLoss); 
    //更新经纪人余额      
      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("broker_money", txOrder.getTx_money());
      userBrokerWalletMap.put("broker_id", txOrder.getUser_id());
      brokerPaymentDao.updateBroker(userBrokerWalletMap);  // 更新经纪人
      
    /*  // 查询经纪人信息(mongodb)
      Criteria criteria = new Criteria();
      criteria.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));   
      BrokerInfo brokerInfo = mongoTemplate.findOne(new Query(criteria), BrokerInfo.class, "t_front_broker");
      
      // 更新经纪人(mongodb)
      Criteria updateCriteria2 = new Criteria();
      updateCriteria2.andOperator(Criteria.where("broker_id").is(txOrder.getUser_id()));
      Update update2  = new Update();
      update2.set("broker_money", brokerInfo.getBroker_money() + txOrder.getTx_money());
      mongoTemplate.updateMulti(new Query(updateCriteria2), update2, "t_front_broker");
      */
   // 新增mongodb经纪人流水
     // mongoTemplate.save(selfBrokerProfitLoss, "BrokerProfitLoss");
    
    
    return true;
  }
  /**
   * 处理经纪人提现订单
   **/
 /* @Override
  public boolean processBrokerWithdrawOrderZn(TxOrder txOrder) {	  
      //插入一条记录到rs表中
      TxOrderRs txOrderRs = new TxOrderRs();
      txOrderRs.setTx_order_id(StringUtils.remove(UUID.randomUUID().toString(), '-')); // 主键id
      txOrderRs.setTx_rs_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      txOrderRs.setUser_id(txOrder.getUser_id()); // 用户id
      txOrderRs.setTx_order_id(txOrder.getTx_order_id()); // 用户提现订单id
      txOrderRs.setTx_rs_status(txOrder.getReturnMsg()); // 回调信息
      txOrderRs.setTx_number(txOrder.getOrder_rs_no()); // 商户流水号
      txOrderRs.setTx_rs_money(txOrder.getTx_money());
      txOrderRs.setTx_rs_brokerage(1);
      txOrderRs.setCreate_date(new Date());
      txOrderRs.setTx_type(txOrder.getTx_type()); //提现方式(例：wechat等)
    if (StringUtils.equals(txOrder.getIs_use(), "1")) {  
    //提现成功插入一条记录到rs表中	
      txOrderRs.setIs_use("1"); // 0：失败 1：成功
      commonPaymentDao.insertTxOrderRs(txOrderRs);
    } else {
    //提现失败
    //插入一条记录到rs表中	
     txOrderRs.setIs_use("0"); // 0：失败 1：成功
     commonPaymentDao.insertTxOrderRs(txOrderRs); 	
    //根据经纪人id查询经纪人信息
      Map<String, Object> brokerMap = brokerPaymentDao.queryBroker(txOrder.getUser_id());
    //插入一条记录到经纪人流水表中 
      BrokerProfitLoss selfBrokerProfitLoss = new BrokerProfitLoss();
      selfBrokerProfitLoss.setBroker_profiloss_id(StringUtils.remove(UUID.randomUUID().toString(), '-'));
      selfBrokerProfitLoss.setBroker_id(txOrder.getUser_id());
      selfBrokerProfitLoss.setBroker_money_before(Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setBroker_money(txOrder.getTx_money());
      selfBrokerProfitLoss.setBroker_money_after(txOrder.getTx_money() + Double.parseDouble(brokerMap.get("broker_money").toString()));
      selfBrokerProfitLoss.setMoney_type("07"); // 退款
      selfBrokerProfitLoss.setCreate_date(new Date());
      selfBrokerProfitLoss.setIs_use("1");
      selfBrokerProfitLoss.setOrder_id(txOrder.getTx_order_id());
    //新增经纪人流水  
      brokerPaymentDao.insertBrokerProfitLoss(selfBrokerProfitLoss); 
    //更新经纪人余额      
      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
      userBrokerWalletMap.put("type", "add");
      userBrokerWalletMap.put("broker_money", txOrder.getTx_money());
      userBrokerWalletMap.put("broker_id", txOrder.getUser_id());
      brokerPaymentDao.updateBroker(userBrokerWalletMap);  // 更新经纪人
    }
    
    return true;
  }*/
}
