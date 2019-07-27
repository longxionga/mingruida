package com.acl.report.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.util.UUIDGenerator;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.MongodbBaseDao;
import com.acl.pojo.BuildAndSellInfo;
import com.acl.report.dao.IReportBuildAndSellInfoDao;
import com.acl.report.service.IReportBuildAndSellService;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.mongo.Pagination;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.distributedlock.DistributedLock;



@Service
@Transactional
public class ReportBuildAndSellServiceImpl implements IReportBuildAndSellService{

	@Autowired
	private MongodbBaseDao<T> mongodbBaseDao;
	
	@Autowired
	private IReportBuildAndSellInfoDao reportBuildAndSellInfoDao;
	
	@Autowired
	private SysUserInfoService sysUserInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private DistributedLock distributedLock;
	
	/**
	 * 建仓信息
	 */
	@Override
	public Pagination<T> queryBuildPositionInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		
		int pageNo=Integer.parseInt(paramsMap.get("page").toString());
		int pageSize=Integer.parseInt(paramsMap.get("rows").toString());
		String strmark=paramsMap.get("mark").toString();
		//Map<String, Object> map=new HashMap<>();
		try {
			return mongodbBaseDao.pagingQuerySellAndBuild(pageNo, pageSize, paramsMap, "TradeBuyOrderMongodb",strmark);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 平仓信息
	 */
	@Override
	public Pagination<T> querySellPositionInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		int pageNo=Integer.parseInt(paramsMap.get("page").toString());
		int pageSize=Integer.parseInt(paramsMap.get("rows").toString());
		String strmark=paramsMap.get("mark").toString();
		//Map<String, Object> map=new HashMap<>();
		try {
			return mongodbBaseDao.pagingQuerySellAndBuild(pageNo, pageSize, paramsMap, "TradeSellOrderMongodb",strmark);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BuildAndSellInfo> queryBuildInfoCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		String strmark=paramsMap.get("mark").toString();
		try {
			return mongodbBaseDao.pagingQuerySellAndBuildCount(paramsMap, "TradeBuyOrderMongodb",strmark);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BuildAndSellInfo> querySellInfoCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		String strmark=paramsMap.get("mark").toString();
		try {
			return mongodbBaseDao.pagingQuerySellAndBuildCount(paramsMap, "TradeSellOrderMongodb",strmark);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询平仓信息的退单
	 */
	@Override
	public Pagination<T> querySellPositionBackInfo(HashMap<String, Object> paramsMap) {
		int pageNo=Integer.parseInt(paramsMap.get("page").toString());
		int pageSize=Integer.parseInt(paramsMap.get("rows").toString());
		String strmark=paramsMap.get("mark").toString();
		try {
			return mongodbBaseDao.pagingQuerySellAndBuild(pageNo, pageSize, paramsMap, "TradeSellOrderMongodb",strmark);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public JSONObject updatChargeBackInfo(HashMap<String, Object> paramsMap) {
		String user_id = paramsMap.get("user_id").toString();
		String order_id = paramsMap.get("order_id").toString();
		String tr_rule=paramsMap.get("trading_rule").toString();
		
		JSONObject jObject = new JSONObject();
		//jObject.put("success", "0");
		
		jObject.put("order_id", order_id);
		jObject.put("user_id", user_id);
		jObject.put("trading_rule", tr_rule);
		//String id = paramsMap.get("id").toString();
		Date currentTime = new Date();
		//boolean retstate = true;
		
		try {
			//众筹或者已转单
			Map<String, Object> parm = new HashMap<String, Object>(); //上级的
			//众筹或者已转单
			Map<String, Object> parm1 = new HashMap<String, Object>(); //下级的

			//Update update = Update.update("name", user.getName()).set("age", user.getAge());  
//			parm.put("user_id", user_id);
//			parm.put("order_id", order_id);
//			parm.put("trading_rule", tr_rule);
//			parm.put("profitloss_id", UUIDGenerator.generate());
//			parm.put("create_date", currentTime);
//			parm.put("money_type", "12");
//			parm.put("user_money_after", "0");
//			parm.put("user_money_before","0");
//			parm.put("user_id", user_id);
//			parm.put("order_id", order_id);
//			//parm.put("user_money", "0");
//			parm.put("is_use", "1");
//			
			if(!"undefined".equals(paramsMap.get("is_zc")) && "1".equals(paramsMap.get("is_zc")))
			{ 
				
				parm.put("user_id", user_id);
				parm.put("order_id", order_id);
				parm.put("trading_rule", tr_rule);
				parm.put("profitloss_id", UUIDGenerator.generate());
				parm.put("create_date", currentTime);
				parm.put("money_type", "12");
				parm.put("user_money_after", "0");
				parm.put("user_money_before","0");
				parm.put("is_use", "1");
				parm.put("is_success","T");

				//parm.put("user_money", "0");
				
				//当前订单信息
				List<BuildAndSellInfo> listzc = mongodbBaseDao.QuerySellAndBuildInfo(order_id, "TradeSellOrderMongodb","2");
				
				parm.put("zc_order_id",listzc.get(0).getZcOrderId());
				
				//下级订单信息或者上级
				List<BuildAndSellInfo> list = mongodbBaseDao.QuerySellAndBuildInfo(listzc.get(0).getZcOrderId(), "TradeSellOrderMongodb","2");
				
				parm1.put("user_id", list.get(0).getUserId());
				parm1.put("order_id", list.get(0).getOrderId());
				parm1.put("trading_rule", list.get(0).getTradeRule());
				parm1.put("profitloss_id", UUIDGenerator.generate());
				parm1.put("create_date", currentTime);
				parm1.put("money_type", "12");
				parm1.put("user_money_after", "0");
				parm1.put("user_money_before","0");
				parm1.put("is_use", "1");
				parm1.put("is_success","T");
				

			}else
			{
				parm1.put("user_id", user_id);
				parm1.put("order_id", order_id);
				parm1.put("trading_rule", tr_rule);
				parm1.put("profitloss_id", UUIDGenerator.generate());
				parm1.put("create_date", currentTime);
				parm1.put("money_type", "12");
				parm1.put("user_money_after", "0");
				parm1.put("user_money_before","0");
				parm1.put("is_use", "1");
				parm1.put("is_success","T");
				//parm.put("user_money", "0");
				
				//下级订单信息
				List<BuildAndSellInfo> listzc = mongodbBaseDao.QuerySellAndBuildInfo(order_id, "TradeSellOrderMongodb","1");
				
				if (listzc.size() > 0) {
					parm.put("user_id", listzc.get(0).getUserId());
					parm.put("order_id", listzc.get(0).getOrderId());
					parm.put("trading_rule", listzc.get(0).getTradeRule());
					parm.put("zc_order_id", listzc.get(0).getZcOrderId());
					parm.put("profitloss_id", UUIDGenerator.generate());
					parm.put("create_date", currentTime);
					parm.put("money_type", "12");
					parm.put("user_money_after", "0");
					parm.put("user_money_before", "0");
					parm.put("is_use", "1");
					parm.put("is_success", "T");
				} else {
					parm.put("is_success", "F");
				}
			}			
			
//			if(queryRSellmoneyInfo(paramsMap)!=null)
//			{
//				//调用存储过程后得到返回值
				//插入mongo流水表UserProfitLoss数据
				//更新mongo平仓订单表TradeSellOrderMongodb数据is_user=0
				//reportBuildAndSellInfoDao.queryRSellmoneyInfo(paramsMap);
			//paramsMap.put("retStr", "");
			queryRSellmoneyInfo(paramsMap);
			if(null != paramsMap.get("retStr") && !"E".equals(paramsMap.get("retStr").toString()))
			{
				
				System.out.println(paramsMap.get("retStr"));
				String retresult = (String) paramsMap.get("retStr");
				String[] ret = retresult.split(",");
				if(ret.length>1)
				{
					String[] ret0 = ret[0].split("\\|");
					String[] ret1 = ret[1].split("\\|");					
					parm.put("user_money",ret0[0].toString());
					parm1.put("user_money",ret1[0].toString());
					parm.put("rettype0",ret0[1].toString());
					parm1.put("rettype1",ret1[1].toString());

				}else
				{
					String[] ret2 = retresult.split("\\|");
					parm1.put("user_money",ret2[0].toString());
					parm1.put("rettype1",ret2[1].toString());
				}				

				if("T".equals(parm1.get("is_success").toString()))
				{
					mongodbBaseDao.save(parm1, "UserProfitLoss");
					String z_order_id = parm1.get("order_id").toString();
					mongodbBaseDao.updateFitstByOId(z_order_id, Update.update("is_use", "0"), "TradeSellOrderMongodb");
				}
				if("T".equals(parm.get("is_success").toString()))
				{
					mongodbBaseDao.save(parm, "UserProfitLoss");
					String orderid = parm.get("order_id").toString();
					mongodbBaseDao.updateFitstByOId(orderid, Update.update("is_use", "0"), "TradeSellOrderMongodb");
					//mongodbBaseDao.updateFitstByOId(is_order_id, Update.update("is_use", "0"), "TradeSellOrderMongodb");
				}
				jObject.put("user_money",  paramsMap.get("retStr").toString());
				if(parm1.get("rettype1").equals("1"))
				{
					paramsMap.put("user_id", user_id);
					List<Map<String, Object>> list = sysUserInfoService.queryUserBalanceInfo(paramsMap);
					String lockKeyActivityLuckydrawOfWallet = distributedLock.getLock("wallet",	list.get(0).get("user_id").toString());
					if (lockKeyActivityLuckydrawOfWallet == null) {
						// msg.setMsg("修正失败，请核对信息");
						// msg.setSuccess(false);
						jObject.put("redis_update", "redis修改失败，请核对信息");
						jObject.put("redis_success", "false");
						return jObject;
					}
					try {
						double userWallet = Double.valueOf(list.get(0).get("user_money").toString()); // 用户钱包
						// double newWallet = userWallet + orderAmount;
						redisTemplate.opsForValue().set("wallet_" + list.get(0).get("user_id"),String.valueOf(userWallet));
						jObject.put("redis_update", "redis修正成功");
						jObject.put("redis_success", "true");
						jObject.put("success", "true");
						// msg.setMsg("修正成功");
						// msg.setSuccess(true);
						// return false;

					} finally {
						distributedLock.releaseLock("wallet", list.get(0).get("user_id").toString(),lockKeyActivityLuckydrawOfWallet);
						jObject.put("success", "true");
						jObject.put("redis_success", "true");
						jObject.put("redis_update", "redis修正成功");
					}				
				}
				else
				{
					jObject.put("success", "true");
					jObject.put("redis_success", "false");
					jObject.put("redis_update", "无需修改redis");
				}			
				
				//return true;
			}else
			{
				jObject.put("success", "false");			
				jObject.put("redis_success", "false");
				jObject.put("redis_update", "redis修改失败，请核对信息");
				//retstate = false;
			}				
//			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jObject;		
	}

	@Override
	public Object queryRSellmoneyInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportBuildAndSellInfoDao.queryRSellmoneyInfo(paramsMap);
	}
}
