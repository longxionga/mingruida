package com.acl.platform.service;

import java.util.List;
import java.util.Map;

import com.acl.pojo.UserInfo;;

public interface IPlatformCommonService {

	/**
	 * 插入消息信息
	 * 
	 */
	int insertMsgSend(Map<String, Object> map);

	/**
	 * 获取代理商微信Access_Token
	 * 
	 */
	Map<String, Object> getWxAccessTokenByAgentId(String agent_id);
	
	/**
	 * 更新代理商微信Access_Token
	 * 
	 */
	int updateWxAccessTokenByAgentId(Map<String, Object> map);
	
	/**
	 * 新增代理商微信Access_Token
	 * 
	 */
	int insertWxAccessToken(Map<String, Object> map);

	
	/**
	 * 根据type取支付信息
	 * 
	 */
	public Map<String, Object> queryPaymentInfoByType(String type);

	/**
	 * 取充值支付接口
	 * 
	 */
	public List<Map<String, Object>> queryPaymentInfoOfRecharge();

	/**
	 * 取提现支付接口
	 * 
	 */
	public List<Map<String, Object>> queryPaymentInfoOfWithdraw();

	/**
	 * 验证用户支付信息
	 * 
	 */
	public Map<String, Object> paymentCheck(UserInfo userinfo, int money, String type, String payType);
}
