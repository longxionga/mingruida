package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;


public interface IPlatformUserAgentRechargeService {
	/**
	 * 查询用户服务商代付信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryUserAgentRecharge(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 改变订单状态
	 * @param paramsMap
	 */
	void updateStatus(HashMap<String, Object> paramsMap);
	
	void updateMoney(HashMap<String, Object> paramsMap);
	
	/**
	 *查询符合条件充值成功的总额信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryRechargeSum(Map<String,Object> map);
	

}
