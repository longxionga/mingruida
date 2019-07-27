package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformUserAgentRechargeDao
 *author:wangli
 *createDate:2017年8月9日 上午10:02:38
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformUserAgentRechargeDao {
	
	/**
	 * 查询代付信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryUserAgentRecharge(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 更改订单状态
	 * @param paramsMap
	 */
	void updateStatus(HashMap<String, Object> paramsMap);
	
	/**
	 *查询导出结算会员日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryRechargeSum(Map<String,Object> map);

}
