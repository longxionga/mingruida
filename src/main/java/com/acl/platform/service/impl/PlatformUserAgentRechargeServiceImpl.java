package com.acl.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.dao.IPlatformUserAgentRechargeDao;
import com.acl.platform.dao.IPlatformUserPaymentDao;
import com.acl.platform.service.IPlatformUserAgentRechargeService;
import com.acl.pojo.UserProfitLoss;
import com.acl.pojo.Wallet;
import com.acl.sys.dao.ISysCompanyTXDetailedDao;
import com.acl.sys.dao.ISysWithDrawInfoDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.distributedlock.DistributedLock;


@Service
public class PlatformUserAgentRechargeServiceImpl implements IPlatformUserAgentRechargeService {

	@Autowired
	private IPlatformUserAgentRechargeDao platformUserAgentRechargeDao;
	
	@Autowired
	private ISysWithDrawInfoDao sysWithDrawInfoDao;
	
	@Autowired
	private ISysCompanyTXDetailedDao sysCompanyTXDetailedDao;
	
	@Autowired
	private IPlatformCommonPaymentDao commonPaymentDao;
	@Autowired
	private IPlatformUserPaymentDao userPaymentDao;
	@Autowired
	private StringRedisTemplate redisTemplate;	  
	@Autowired
	protected MongoTemplate mongoTemplate;
	@Autowired
	private DistributedLock distributedLock;
	
	@Override
	public PageList<?> queryUserAgentRecharge(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return platformUserAgentRechargeDao.queryUserAgentRecharge(paramsMap, pageBounds);
	}

	@Override
	public void updateStatus(HashMap<String, Object> paramsMap) {
	
		
		platformUserAgentRechargeDao.updateStatus(paramsMap);
	}

	@Override
	public void updateMoney(HashMap<String, Object> paramsMap) {
		
				//修改服务商余额
				HashMap<String, Object> deptparamsMap = new HashMap<>();
				deptparamsMap.put("dept_id", paramsMap.get("dept_id"));
				deptparamsMap.put("money", paramsMap.get("new_money"));
				deptparamsMap.put("dept_money", paramsMap.get("dept_money"));
				
				sysWithDrawInfoDao.updateDeptMoneyInfo(deptparamsMap);
				
				//添加服务商流水信息
				HashMap<String, Object> detailMap = new HashMap<>();
				detailMap.put("sys_id", UUIDGenerator.generate());
				detailMap.put("order_no", UUIDGenerator.generate());
				detailMap.put("money", paramsMap.get("order_money"));
				detailMap.put("fund_type", "29");
				detailMap.put("before_balance", paramsMap.get("dept_money"));
				detailMap.put("after_balance", paramsMap.get("new_money"));
				detailMap.put("create_date",FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
				detailMap.put("settle_id", paramsMap.get("dept_id"));
				sysCompanyTXDetailedDao.insertCompanyTXDetailed(detailMap);
				
				//增加用户流水
				Wallet wallet = commonPaymentDao.queryUserWalletByUserId(StringUtils.convertString(paramsMap.get("user_id")));//查询用户balance
			      UserProfitLoss userProfitLoss = new UserProfitLoss();
			      userProfitLoss.setProfitloss_id(UUIDGenerator.generate());
			      userProfitLoss.setUser_id(wallet.getUser_id());
			      userProfitLoss.setUser_money_before(wallet.getUser_money());
			     
			      userProfitLoss.setUser_money(Double.parseDouble(paramsMap.get("order_money").toString()));
			      BigDecimal money_after=new BigDecimal(wallet.getUser_money() ).add(new BigDecimal(StringUtils.convertString(paramsMap.get("real_tx_money"))));
			      userProfitLoss.setUser_money_after(money_after.doubleValue());
			      userProfitLoss.setMoney_type("28"); // 退款
			      userProfitLoss.setCreate_date(new Date());
			      userProfitLoss.setIs_use("1");
			      userProfitLoss.setOrder_id(paramsMap.get("order_id").toString());
			      userProfitLoss.setOrder_rs_no("");// 提现流水号或批次号
			      userPaymentDao.insertUserProfitLoss(userProfitLoss);
			      
			      Map<String, Object> userBrokerWalletMap = new HashMap<String, Object>();
			      userBrokerWalletMap.put("type", "add");
			      userBrokerWalletMap.put("order_money", paramsMap.get("real_tx_money"));
			      userBrokerWalletMap.put("wallet_id", wallet.getWallet_id());
			      userPaymentDao.updateWallet(userBrokerWalletMap);
			      
			      // 新增mongodb用户流水
			      mongoTemplate.save(userProfitLoss, "UserProfitLoss");
			      
			      // 更新redis的用户余额
			      String lockUserWallet = distributedLock.getLock("wallet", StringUtils.convertString(paramsMap.get("user_id")));
			      try {
			        String key = "wallet_" + StringUtils.convertString(paramsMap.get("user_id"));
			       // double userWallet = Double.valueOf(redisTemplate.opsForValue().get(key)); // 用户钱包
			       // double newWallet = userWallet + Double.parseDouble(tx_money);
			        
			        BigDecimal userWallet=new BigDecimal(redisTemplate.opsForValue().get(key));
			        BigDecimal newWallet=userWallet.add(new BigDecimal(StringUtils.convertString(paramsMap.get("real_tx_money"))));
			        redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
			      } finally {
			        distributedLock.releaseLock("wallet", StringUtils.convertString(paramsMap.get("user_id")), lockUserWallet);
			      }
				
		
	}

	@Override
	public List<Map<String, Object>> queryRechargeSum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return platformUserAgentRechargeDao.queryRechargeSum(map);
	}

}
