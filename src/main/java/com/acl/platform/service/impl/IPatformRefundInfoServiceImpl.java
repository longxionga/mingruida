package com.acl.platform.service.impl;


import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.acl.platform.dao.*;

import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.acl.platform.service.IPlatformRefundInfoService;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;


import com.acl.utils.util.StringUtils;

import com.anchol.common.component.distributedlock.DistributedLock;
import org.springframework.transaction.annotation.Transactional;

/**
 *className:IPatformRefundServiceImpl
 *author:wangli
 *createDate:2017年4月5日 下午4:47:25
 *vsersion:3.0
 *department:安创乐科技
 *description:处理微信提现失败后的退款操作
 */
@Service
@Transactional
public class IPatformRefundInfoServiceImpl implements IPlatformRefundInfoService {
	@Autowired
	private IPlatformRefundInfoDao platformRefundInfoDao;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	protected MongoTemplate mongoTemplate;
	@Autowired
	private DistributedLock distributedLock;
	@Autowired
	private IPlatformWithDrawalsApplyDao platformWithDrawalsApplyDao;

	@Override
	public PageList<?> queryRefundInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformRefundInfoDao.queryRefundInfo(paramsMap, pageBounds);
	}

	@Override
	public String checkJinYunTongPayOrder(String orderNo, String txType) {
		Date date=new Date();
		//查询订单详情  查询状态 以及商户号
		HashMap<String, Object> map=new HashMap<>();
		map.put("id", orderNo);
		Map<String, Object> mapOrder=platformWithDrawalsApplyDao.queryOrderWithdrawalsMoney(map);
		String reString="";


		String tranFlow = StringUtils.convertString(mapOrder.get("out_trade_id"));
		try {
			String xml="";
			String mac="";
			String respMsg="";
			String respCode="";
			String tranRespCode="";
			String tranState ="";
			String tranRespDesc="";
			String tranFlowid="";
			switch (txType){
				case "jytpay":
					/*xml = JytBaseTran.getTC2002Xml(tranFlow);
					mac = JytBaseTran.signMsg(xml);
					respMsg = JytBaseTran.sendAndReceiveMsg(xml,mac);
					respCode = JytBaseTran.getMsgRespHead(respMsg, "resp_code");
					tranRespCode = JytBaseTran.getMsgRespBody(respMsg, "tran_resp_code");
					tranState = JytBaseTran.getMsgRespBody(respMsg, "tran_state");
					tranRespDesc = JytBaseTran.getMsgRespBody(respMsg, "tran_resp_desc");
					tranFlowid = JytBaseTran.getMsgRespHead(respMsg, "tran_flowid");*/
					break;
			}



			System.out.println("上送报文"+xml);
			//加签，用商户端私钥进行加签
			System.out.println(respMsg);
			System.out.println("响应报文"+respMsg);
			System.out.println("返回码"+respCode);
			HashMap<String, Object> record=new HashMap<>();
			if("S0000000".equals(respCode)) { //查询成功
				if("S0000000".equals(tranRespCode)) {
					if("00".equals(tranState)) {
						reString="0";
						System.out.println("处理中：" + tranRespDesc);
					} else if("01".equals(tranState)) {
						reString="1";
						//修改订单状态为4
						map.put("status", "4");
						map.put("update_time",date);
						platformWithDrawalsApplyDao.updateTxOrder(map);
						System.out.println("代付成功: " + tranRespDesc);
					} else {
						reString="2";
						System.out.println(tranRespDesc);
					}
				} else if ("E0000000".equals(tranRespCode)) {
					if("15".equals(tranState)) {
						reString="0";
						System.out.println("待运营审核：" + tranRespDesc);
					} else if("06".equals(tranState)) {
						reString="0";
						System.out.println("运营审核通过待处理: " + tranRespDesc);
					} else {
						reString="2";
						System.out.println(tranRespDesc);
					}
				} else if (!"E0000000".equals(tranRespCode) && tranRespCode.startsWith("E") && "03".equals(tranState)){
					System.out.println("代付失败： " + tranRespDesc);
					reString="3";

					//执行退款操作，并将rs表中订单状态改为：4 人工审核退款
					//修改订单状态为4  //状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
					map.put("status", "5");
					map.put("update_time",date);
					map.put("refund_time",date);
					platformWithDrawalsApplyDao.updateTxOrder(map);

					record.put("user_id",mapOrder.get("user_id"));
					//查询用户当前余额
					List<Map<String,Object>> list=platformWithDrawalsApplyDao.queryUserCapital(record);
					BigDecimal balance=new BigDecimal(list.get(0).get("amount").toString()).add(new BigDecimal(StringUtils.convertString(mapOrder.get("money"))));


					//更新t_user_capital 表中余额信息
					record.put("balance",balance);
					record.put("update_time",date);
					platformWithDrawalsApplyDao.updateCaptical(record);
					//增加用户余额  写入退款流水
					record.put("id", UUIDGenerator.generate());
					record.put("amount",StringUtils.convertString(mapOrder.get("money")));
					record.put("operate","subtract");
					record.put("purpose","103");
					record.put("correlation",orderNo);
					record.put("description","提现审核失败");
					record.put("create_time",date);
					record.put("update_time","");
					platformWithDrawalsApplyDao.insertCapticalBilling(record);



					// 更新redis的用户余额
			/*		String lockUserWallet = distributedLock.getLock("wallet", StringUtils.convertString(mapOrder.get("user_id")));
					try {
						String key = "wallet_" + StringUtils.convertString(mapOrder.get("user_id"));
						BigDecimal userWallet=new BigDecimal(redisTemplate.opsForValue().get(key));
						BigDecimal newWallet=userWallet.add(new BigDecimal(mapOrder.get("money").toString()));
						redisTemplate.opsForValue().set(key, String.valueOf(newWallet));
					} finally {
						distributedLock.releaseLock("wallet", StringUtils.convertString(list.get(0).get("user_id")), lockUserWallet);
					}*/
				}
			} else if("E0000000".equals(respCode)){
				reString="4";
				System.out.println("原代付交易异常，请联系渠道!");

			}


		} catch (Exception e) {
			e.printStackTrace();
		}   //发送请求并接收响应报文
		return reString;
	}


}
