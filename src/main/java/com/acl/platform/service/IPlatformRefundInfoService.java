package com.acl.platform.service;

import java.util.HashMap;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformRefundService
 *author:wangli
 *createDate:2017年4月5日 下午4:46:47
 *vsersion:3.0
 *department:安创乐科技
 *description:处理微信提现失败后的退款操作
 */
public interface IPlatformRefundInfoService {
	/**
	 * 查询微信提现审核失败的订单信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryRefundInfo(HashMap<String, Object> paramsMap,
								PageBounds pageBounds);


	String checkJinYunTongPayOrder(String order_id,String tx_type);
}
