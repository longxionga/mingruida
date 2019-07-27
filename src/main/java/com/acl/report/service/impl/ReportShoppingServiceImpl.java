package com.acl.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.dao.IPlatformUserPaymentDao;
import com.acl.pojo.UserProfitLoss;
import com.acl.pojo.Wallet;
import com.acl.report.dao.IReportShoppingDao;
import com.acl.report.service.IReportShoppingService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.distributedlock.DistributedLock;

/**
 *className:ReportShoppingServiceImpl
 *author:wangli
 *createDate:2017年3月28日 上午10:45:04
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class ReportShoppingServiceImpl implements IReportShoppingService {
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@Autowired
	private IReportShoppingDao reportShoppingDao;
	
	@Autowired
	private IPlatformCommonPaymentDao commonPaymentDao;
	
	@Autowired
	private DistributedLock distributedLock;
	  
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public PageList<?> queryShoppingOrder(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportShoppingDao.queryShoppingOrder(paramsMap, pageBounds);
	}
	
	@Override
	public void setTrackingNumber(HashMap<String, Object> paramsMap) {
		reportShoppingDao.setTrackingNumber(paramsMap);
	}

	@Override
	public PageList<?> queryShoppingRefund(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportShoppingDao.queryShoppingRefund(paramsMap, pageBounds);
	}

	@Override
	public void updateShoppingRefundStatus(HashMap<String, Object> paramsMap) {
		reportShoppingDao.updateShoppingRefundStatus(paramsMap);
	}

	@Override
	public void updateUserBalance(HashMap<String, Object> paramsMap) {
		// 更新redis的用户余额
	    String lockUserWallet = distributedLock.getLock("wallet", StringUtils.convertString(paramsMap.get("user_id")));
		try{
			
		//查询钱包表当前余额信息
		Wallet wallet = commonPaymentDao.queryUserWalletByUserId(StringUtils.convertString(paramsMap.get("user_id")));//查询用户balance
		  HashMap<String, Object> map=new HashMap<>();
	      map.put("profitloss_id", UUIDGenerator.generate());
	      map.put("user_id", StringUtils.convertString(paramsMap.get("user_id")));
	      map.put("user_money_before", wallet.getUser_money());
	      BigDecimal refund_money=new BigDecimal(StringUtils.convertString(paramsMap.get("refund_money")));
	      BigDecimal user_money_after=new BigDecimal(wallet.getUser_money()).add(refund_money);
	      map.put("user_money_after", user_money_after);
	      map.put("user_money", refund_money);
	      map.put("money_type", "21");
	      map.put("create_date", new Date());
	      map.put("is_use", "1");
	      map.put("order_id", paramsMap.get("shopping_refund_id"));
	      //添加用户退款流水 类型为21
	      reportShoppingDao.insertUserProfitLoss(map);
	      //修改用户金额
	      reportShoppingDao.updateUserBalance(paramsMap);	      
	      //写入mongodb流水
	      mongoTemplate.save(map, "UserProfitLoss");
	      //修改用户redis金额
	      String key = "wallet_" + paramsMap.get("user_id");
	      BigDecimal userWallet = new BigDecimal(redisTemplate.opsForValue().get(key)); // 用户钱包
	      BigDecimal newWallet = userWallet.add(refund_money);
	       	redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
	      } finally {
	        distributedLock.releaseLock("wallet", StringUtils.convertString(paramsMap.get("user_id")), StringUtils.convertString(lockUserWallet));
	      }
	      
	}

	@Override
	public List<Map<String, Object>> queryRefundInfo(Map<String, Object> map) {
		return reportShoppingDao.queryRefundInfo(map);
	}

	@Override
	public void updateShoppingOrderStatus(HashMap<String, Object> paramsMap) {
		reportShoppingDao.updateShoppingOrderStatus(paramsMap);
	}

	

}
