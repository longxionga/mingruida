package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.UserProfitLoss;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportShoppingDao
 *author:wangli
 *createDate:2017年3月28日 上午10:44:05
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportShoppingDao {
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
	 * 将退款订单状态改为已审核（1成功2失败）
	 * @param paramsMap
	 */
	void updateShoppingRefundStatus(HashMap<String, Object> paramsMap);
	
	/**
	 * 修改用户钱包金额
	 * @param paramsMap
	 */
	void updateUserBalance(HashMap<String, Object> paramsMap);
	
	 /**
	   * 新增用户流水表
	  **/
	void insertUserProfitLoss(HashMap<String, Object> hashMap);
	
	/**
	 * 查询退款金额
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryRefundInfo(Map<String, Object> map);
}
