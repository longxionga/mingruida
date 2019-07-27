package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportShoppingService
 *author:wangli
 *createDate:2017年3月28日 上午10:44:27
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportShoppingService {
	/**
	 * 查询所有用户订单，已付款状态的置顶
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryShoppingOrder(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 设置快递单号
	 * @param paramsMap
	 */
	void setTrackingNumber(HashMap<String, Object> paramsMap);
	
	/**
	 * 修改客户订单的状态
	 * @param paramsMap
	 */
	void updateShoppingOrderStatus(HashMap<String, Object> paramsMap);
	/**
	 * 查询所有用户退款订单
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryShoppingRefund(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 修改退款订单的状态
	 * @param paramsMap
	 */
	void updateShoppingRefundStatus(HashMap<String, Object> paramsMap);
	
	/**
	 * 修改用户余额
	 * @param paramsMap
	 */
	void updateUserBalance(HashMap<String, Object> paramsMap);
	
	
	/**
	 * 查询退款金额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryRefundInfo(Map<String, Object> map);
}
