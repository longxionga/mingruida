package com.acl.platform.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import com.acl.utils.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.SystemConfig;
import com.acl.platform.dao.IPlatformCommonPaymentDao;
import com.acl.platform.dao.IPlatformWithDrawalsApplyDao;
import com.acl.platform.service.IPlatformBrokerPaymentService;
import com.acl.platform.service.IPlatformUserPaymentService;
import com.acl.platform.service.IPlatformWithDrawalsApplyService;
import com.acl.pojo.TransactionBean;
import com.acl.pojo.TxOrder;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.alibaba.fastjson.JSONObject;

/**
 *className:PlatformWithDrawalsApplyServiceImpl
 *author:wangli
 *createDate:2016年8月19日 下午8:05:39
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class PlatformWithDrawalsApplyServiceImpl implements IPlatformWithDrawalsApplyService {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private IPlatformWithDrawalsApplyDao platformWithDrawalsApplyDao;

	@Override
	public PageList<?> queryWithDrawalsApply(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformWithDrawalsApplyDao.queryWithDrawalsApply(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> querySettleInfoForCombox(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.querySettleInfoForCombox(paramsMap);
	}



	@Override
	public List<Map<String, Object>> queryAgentInfoForCombox(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.queryAgentInfoForCombox(paramsMap);
	}



	@Override
	public List<Map<String, Object>> queryDeptInfoForCombox(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.queryDeptInfoForCombox(paramsMap);
	}



	@Override
	public List<Map<String, Object>> querySumUserTX(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.querySumUserTX(paramsMap);
	}



	@Override
	public List<Map<String, Object>> querySumOutTX(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.querySumOutTX(paramsMap);
	}



	@Override
	public Map<String, Object> queryOrderWithdrawalsMoney(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.queryOrderWithdrawalsMoney(paramsMap);
	}



	@Override
	public int updateTxOrder(Map<String, Object> map) {
		return platformWithDrawalsApplyDao.updateTxOrder(map);
	}



	@Override
	public List<Map<String, Object>> queryOrderWithdrawalsIsUse(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.queryOrderWithdrawalsIsUse(paramsMap);
	}

	@Override
	public boolean rebackUserWithDraw(Map<String, Object> paramsMap) {//user_id   id
		Date date=new Date();
		//查询订单信息
		Map<String, Object> orderMap = platformWithDrawalsApplyDao.queryOrderWithdrawalsMoney(paramsMap);
		HashMap<String,Object> record=new HashMap<>();
		record.put("user_id",StringUtils.convertString(paramsMap.get("user_id")));

		//查询用户当前余额
		List<Map<String,Object>> list=platformWithDrawalsApplyDao.queryUserCapital(record);
		BigDecimal balance=new BigDecimal(list.get(0).get("amount").toString()).add(new BigDecimal(StringUtils.convertString(orderMap.get("money"))));


		//更新t_user_capital 表中余额信息
		record.put("balance",balance);
		record.put("update_time",date);
		platformWithDrawalsApplyDao.updateCaptical(record);
		//增加用户余额  写入退款流水
		record.put("id", UUIDGenerator.generate());
		record.put("amount",StringUtils.convertString(orderMap.get("money")));
		record.put("operate","subtract");
		record.put("purpose","103");
		record.put("correlation",paramsMap.get("id"));
		record.put("description","拒绝提现申请");
		record.put("create_time",date);
		record.put("update_time","");
		platformWithDrawalsApplyDao.insertCapticalBilling(record);
		return true;
	}


	@Override
	public JSONObject getJinYunTongPay(String id,String txType){
		JSONObject jObject = new JSONObject();
		//首先查询表单状态是否为已审核
		/*修改表单状态为已审核
		 * 封装参数
		 * 处理返回信息*/

		jObject.put("success", "0");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", id);
		jObject.put("id", id);
		//查询订单信息
		Map<String, Object> orderMap = platformWithDrawalsApplyDao.queryOrderWithdrawalsMoney(paramsMap);

		String bankName = StringUtils.convertString(orderMap.get("user_bank_no"));
		String accountNo = StringUtils.convertString(orderMap.get("user_bank_no"));//最后一位4-9
		String accountName = StringUtils.convertString(orderMap.get("user_name"));
		String accountType = "00";  //00对私，01对公
		//提现金额转换成分
		String tx_money=StringUtils.convertString(orderMap.get("transaction_money"));
		if(tx_money.equals("")){
			jObject.put("id", id);
			jObject.put("success", "4");
			jObject.put("msg", "提现金额不能为空");
			return jObject;
		}

		String tranAmt=tx_money;
		String bsnCode = "09400";//虚拟账户提现代码
		String xml="";
		String mac="";
		String respMsg="";
		String respCode="";
		String respDesc="";
		String tranState="";
		String tranFlowid="";
		try {
			switch(txType){
				case "jytpay":
					/*xml=JytBaseTran.getTC1002Xml(bankName, accountNo, accountName,accountType, tranAmt, bsnCode);
					tranFlowid = JytBaseTran.getMsgRespHead(xml, "tran_flowid");
					mac=JytBaseTran.signMsg(xml);//加签，用商户端私钥进行加签
					respMsg = JytBaseTran.sendAndReceiveMsg(xml,mac);
					respCode = JytBaseTran.getMsgRespHead(respMsg, "resp_code");
					respDesc = JytBaseTran.getMsgRespHead(respMsg, "resp_desc");
					tranState = JytBaseTran.getMsgRespBody(respMsg, "tran_state");*/
					break;
			}
			System.out.println("上送报文"+xml);
			System.out.println("响应报文"+respMsg);
			System.out.println("返回码"+respCode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}//接收响应报文

		HashMap<String,Object> map=new HashMap<>();
		map.put("out_trade_id",tranFlowid);//第三方订单
		map.put("id",id);
		Date date=new Date();
		if("S0000000".equals(respCode)) {
			if("01".equals(tranState)){
				jObject.put("success", "1");
				map.put("status",4);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
				jObject.put("msg","成功");
				System.out.println("交易结果成功");
			} else if("00".equals(tranState)) {
				map.put("status",2);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
				jObject.put("msg","处理中");
				System.out.println("交易受理成功，但交易结果尚未明确"+respDesc);
			}
		} else if(!"E0000000".equals(respCode)) {
			if("03".equals(tranState)) {
				map.put("status",5);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
				map.put("refund_time",date);
				jObject.put("msg","代付失败"+respDesc);
				System.out.println("代付失败"+respDesc);
				System.out.println(respDesc);
				//如果代付失败，需要进行退款操作
				HashMap<String,Object> record=new HashMap<>();
				record.put("user_id",StringUtils.convertString(orderMap.get("user_id")));

				//查询用户当前余额
				List<Map<String,Object>> list=platformWithDrawalsApplyDao.queryUserCapital(record);
				BigDecimal balance=new BigDecimal(list.get(0).get("amount").toString()).add(new BigDecimal(StringUtils.convertString(orderMap.get("money"))));
				//更新t_user_capital 表中余额信息
				record.put("balance",balance);
				platformWithDrawalsApplyDao.updateCaptical(record);
				//增加用户余额  写入退款流水
				record.put("id", UUIDGenerator.generate());
				record.put("amount",StringUtils.convertString(orderMap.get("money")));
				record.put("operate","subtract");
				record.put("purpose","103");
				record.put("correlation",id);
				record.put("description","提现失败退款");
				record.put("create_time",date);
				platformWithDrawalsApplyDao.insertCapticalBilling(record);


			}
		} else if("E0000000".equals(respCode)) {
			if("15".equals(tranState)) {
				map.put("status",2);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
				jObject.put("msg","处理中");
				System.out.println("待运营审核"+respDesc);
				System.out.println(respDesc);
			} else {
				map.put("status",2);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
				jObject.put("msg","处理中");
				System.out.println("处理中"+respDesc);
				System.out.println(respDesc);
			}
		} else {
			map.put("status",2);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
			jObject.put("msg","处理中");
			System.out.println("处理中"+respDesc);
			System.out.println(respDesc);
		}

		//获取返回信息后  更改审核状态 更新时间 如果失败需要更新退款时间
		map.put("update_time",date);
		platformWithDrawalsApplyDao.updateTxOrder(map);



		return jObject;
	}

	@Override
	public List<Map<String, Object>> queryAgentInfoForCombox2(Map<String, Object> paramsMap) {
		return platformWithDrawalsApplyDao.queryAgentInfoForCombox2(paramsMap);
	}

}
