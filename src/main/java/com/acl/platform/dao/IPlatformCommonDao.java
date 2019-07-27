package com.acl.platform.dao;

import java.util.List;
import java.util.Map;

public interface IPlatformCommonDao {

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
	 * 取得支付方式 
	 * 
	 */
	public List<Map<String, Object>> getPaymentRule(Map<String, Object> map);
	
	/**
	 * 取得用户最大可提现金额
	 * 
	 */
	public Map<String, Object> queryWithdrawalsUserAccessLimit(Map<String, Object> map);
	
	/**
	 * 取得经纪人最大可提现金额 
	 * 
	 */
	public Map<String, Object> queryWithdrawalsBrokersAccessLimit(Map<String, Object> map);

	

}
