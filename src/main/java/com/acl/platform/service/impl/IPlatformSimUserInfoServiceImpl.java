package com.acl.platform.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.acl.utils.util.UUIDGenerator;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.SimMongodbBaseDao;
import com.acl.platform.dao.IPlatformSimUserInfoDao;
import com.acl.platform.service.IPlatformSimUserInfoService;
import com.acl.pojo.SimUserInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.redis.RedisUtils;
import com.acl.utils.util.MD5Utils;
/**
 *author:hufeng
 *createDate:2016年10月14日 下午2:57:55
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class IPlatformSimUserInfoServiceImpl implements IPlatformSimUserInfoService {
	
	
	@Autowired
	private IPlatformSimUserInfoDao platformUserInfoDao;
	
	@Autowired
	private SimMongodbBaseDao<T> mongodbBaseDao;
	
	@Autowired
	private RedisUtils redisUtils ;
	   
	 
	
	//从数据库查询所有数据
	@Override
	public PageList<?> querySimUserInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformUserInfoDao.querySimUserInfo(paramsMap,pageBounds);
	}


	@Override
	public void saveSimUserInfo(HashMap<String, Object> paramsMap) {
		
		//保存用户基本信息
		paramsMap.put("user_id", UUIDGenerator.generate());
		paramsMap.put("wallet_id", UUIDGenerator.generate());
		paramsMap.put("user_password", MD5Utils.MD5((String)paramsMap.get("user_password")));
		paramsMap.put("broker_id", UUIDGenerator.generate());
		
		// TODO Auto-generated method stub
		Map<String,Object> simUserInfoMap = new HashMap<String,Object>();
		simUserInfoMap.putAll(paramsMap);
		simUserInfoMap.put("p_id", "10000");
		simUserInfoMap.put("ch_id", "10001");
		simUserInfoMap.put("ch_name", "模拟渠道");
		simUserInfoMap.put("ce_id", "10002");
		simUserInfoMap.put("ce_name", "模拟交易中心");
		simUserInfoMap.put("settle_id", "10003");
		simUserInfoMap.put("settle_name", "模拟结算");
		simUserInfoMap.put("agent_id", "4");
		simUserInfoMap.put("agent_name", "模拟代理商");
		simUserInfoMap.put("agent_code", "simulation");
		
		//保存用户基本信息。
		platformUserInfoDao.insertSimUserInfo(simUserInfoMap);
		
		SimUserInfo userInfo = new SimUserInfo();
		userInfo.setUser_id((String)paramsMap.get("user_id"));
		userInfo.setBroker_id((String)paramsMap.get("broker_id"));
		userInfo.setBroker_name("模拟用户-初始化经纪人");
		userInfo.setBroker_incode("000000");
		userInfo.setUser_name((String)paramsMap.get("user_name"));
		userInfo.setCreate_date(new Date());
		userInfo.setIs_use("1");
		userInfo.setUser_ip("127.0.0.1");
		userInfo.setAgent_id("4");
		userInfo.setDept_id("23232");
		userInfo.setDept_name("模拟用户-初始化部门");
		userInfo.setAgent_code("simulation");
		userInfo.setAgent_name("模拟代理商");
		userInfo.setSettle_id("10003");
		userInfo.setSettle_name("模拟结算");
		userInfo.setHead_url("http://wx.qlogo.cn/mmopen/7sZlpRyWtibrxxp1BMiaibHx3OnUVKO5aMHA7FooZDjeMTB7JMiaKG2TvBu9rp0hCTeVHGlbwaSYlzsWWZQbfk3jDVQ208iafK0Ya/0");
		userInfo.setCe_id("10002");
		userInfo.setCh_id("10001");
		userInfo.setCe_name("模拟交易中心");
		userInfo.setCh_name("模拟渠道");
		userInfo.setP_id("10000");
		userInfo.setWallet_id((String)paramsMap.get("wallet_id"));
		userInfo.setUser_money((String)paramsMap.get("user_money"));
		userInfo.setUser_mobile((String)paramsMap.get("user_mobile"));
		userInfo.setUser_wxid("xxxxxx");
		userInfo.setUser_password((String)paramsMap.get("user_password"));
		userInfo.setUser_login_id("");
		userInfo.setUser_login_date(new Date());
		userInfo.setUser_last_update("2016-10-13 09:36:56");
		
		
		//保存用户登录信息
		this.saveSimUserLogin(paramsMap);
		//保存用户钱包信息
		this.saveSimUserWallet(paramsMap);
		
		//保存用户流水信息
		paramsMap.put("profitloss_id", UUIDGenerator.generate());
		//充值之前。
		paramsMap.put("user_money_before", "0");
		//充值以后
		paramsMap.put("user_money_after", paramsMap.get("user_money")); 
		//充值类型
		paramsMap.put("money_type", "05"); 
		this.saveSimUserProfitLoss(paramsMap);
		

		//保存到mongo数据库 
		mongodbBaseDao.save(userInfo, "t_front_user_login");
	}



	@Override
	public void saveSimUserLogin(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformUserInfoDao.insertSimUserLogin(paramsMap);
	}



	@Override
	public void saveSimUserWallet(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformUserInfoDao.insertSimUserWallet(paramsMap);
		redisUtils.opsForValue().set("wallet_"+(String)paramsMap.get("user_id"), (String)paramsMap.get("user_money"));
	}

	@Override
	public void saveSimUserProfitLoss(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformUserInfoDao.insertSimUserProfitLoss(paramsMap);
		
		Map<String,Object> mongoMap = new HashMap<String,Object>();
		mongoMap.put("profitloss_id", (String)paramsMap.get("profitloss_id"));
		mongoMap.put("create_date", new Date());
		mongoMap.put("money_type", (String)paramsMap.get("money_type"));
		mongoMap.put("user_money_after", paramsMap.get("user_money_after"));
		mongoMap.put("user_money_before", paramsMap.get("user_money_before"));
		mongoMap.put("user_id", (String)paramsMap.get("user_id"));
		mongoMap.put("order_id", "");
		mongoMap.put("user_money", paramsMap.get("user_money"));
		mongoMap.put("is_use", "1");
		mongodbBaseDao.save(mongoMap, "UserProfitLoss");
	}

	

	@Override
	public void updateSimUserInfo(HashMap<String, Object> paramsMap,Map<String, Object> userInfoMap) {
		
	}





	@Override
	public Map<String, Object> querySimUserInfoByMap(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformUserInfoDao.querySimUserInfoByMap(paramsMap);
	}


	@Override
	public void updateSimUserWallet(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformUserInfoDao.updateSimUserWallet(paramsMap);
		
		redisUtils.opsForValue().set("wallet_"+(String)paramsMap.get("user_id"), (String)paramsMap.get("user_money"));
	}


	

	@Override
	public void resetSimUserInfoPwd(Map<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformUserInfoDao.updateSimUserLogin(paramsMap);
		
		
		Map<String,Object> map=new HashMap<>();
		map.put("user_id", (String)paramsMap.get("user_id"));
		Update update = new Update();
		update.set("user_password",(String)paramsMap.get("user_password"));		
		mongodbBaseDao.updateFirst(map, update, "t_front_user_login");
	}


	@Override
	public void resetSimUserInfoPwdIsuse(Map<String, Object> paramsMap,Map<String, Object> userInfoMap) {
		// TODO Auto-generated method stub
		//判断用户是否需要
		Update update = new Update();
		boolean isUpate = false ;
		
    	
    	if(!((String)paramsMap.get("is_use")).equals((String)userInfoMap.get("is_use")) ){
    		Map<String,Object> sqlUserInfo = new HashMap<String, Object>();
    		sqlUserInfo.put("user_id", (String)userInfoMap.get("user_id"));
    		sqlUserInfo.put("is_use", (String)paramsMap.get("is_use"));
    		platformUserInfoDao.updateSimUserInfo(sqlUserInfo);
    		
    		update.set("is_use",(String)paramsMap.get("is_use"));		
    		
    		isUpate = true ;
    	}
    	
    	//如果密码没有修改，则使用以前的密码，如果密码修改了，则重新加密。
    	if(!((String)paramsMap.get("user_password")).equals((String)userInfoMap.get("user_password")) ){
	    	//paramsMap.put("user_password", (String)userInfoMap.get("user_password"));
    		paramsMap.put("user_password", MD5Utils.MD5((String)paramsMap.get("user_password")));
    		
    		Map<String,Object> sqlUserInfo = new HashMap<String, Object>();
    		sqlUserInfo.put("user_id", (String)userInfoMap.get("user_id"));
    		sqlUserInfo.put("user_password", (String)paramsMap.get("user_password"));
    		// TODO Auto-generated method stub
    		platformUserInfoDao.updateSimUserLogin(sqlUserInfo);
    		
    		update.set("user_password",(String)paramsMap.get("user_password"));
    		
    		isUpate = true ;
    	}
    	
    	if(isUpate){
    		Map<String,Object> maongoMap =  new HashMap<String, Object>(); ;
    		maongoMap.put("user_id", (String)userInfoMap.get("user_id"));
    		mongodbBaseDao.updateFirst(maongoMap, update, "t_front_user_login");
    	}
	}


	@Override
	public void updateSimUserLogonWalletProfitLoss(
			HashMap<String, Object> paramsMap, Map<String, Object> userInfoMap) {
    	
        this.resetSimUserInfoPwdIsuse(paramsMap, userInfoMap);
		
		//判定流水更新操作。。。。
		BigDecimal user_money_before_temp =  (BigDecimal)userInfoMap.get("user_money");
		BigDecimal user_money_ofter_tmp = new BigDecimal((String)paramsMap.get("user_money")) ;
		
		//
		BigDecimal user_money_abc_tmp = user_money_ofter_tmp.subtract(user_money_before_temp).abs();
		
			//保存用户流水信息
		paramsMap.put("profitloss_id", UUIDGenerator.generate());
		//充值之前。
		paramsMap.put("user_money_before", user_money_before_temp);
		//充值以后
		paramsMap.put("user_money_after", user_money_ofter_tmp); 
		
		//如果没有修改 则不更新流水表
		if(user_money_ofter_tmp.compareTo(user_money_before_temp)==0){
			
			//如果修改后 的数据比之前的数据小
 		}else if(user_money_ofter_tmp.compareTo(user_money_before_temp)==-1){
 			///	user_money_tmp < user_money_before_temp 
 			this.updateSimUserWallet(paramsMap);
			//充值类型
			paramsMap.put("money_type", "06"); 
 			paramsMap.put("user_money",user_money_abc_tmp);
			this.saveSimUserProfitLoss(paramsMap);
 		}else if(user_money_ofter_tmp.compareTo(user_money_before_temp)==1){
 			// user_money_tmp > user_money_before_temp
			//充值类型
 			this.updateSimUserWallet(paramsMap);
			paramsMap.put("money_type", "05"); 
 			paramsMap.put("user_money",user_money_abc_tmp);
			this.saveSimUserProfitLoss(paramsMap);
 		}
	}
	 
}


