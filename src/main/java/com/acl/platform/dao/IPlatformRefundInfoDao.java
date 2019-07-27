package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformRefundInfoDao
 *author:wangli
 *createDate:2017年4月6日 上午9:47:08
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformRefundInfoDao {
	/**
	 * 查询微信提现审核失败的订单信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryRefundInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);


}
