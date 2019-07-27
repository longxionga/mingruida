package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformFeedBackService
 *author:wangli
 *createDate:2017年7月4日 上午9:12:36
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface PlatformFeedBackService {
	/**
	 * 查询用户反馈信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryFeedBack(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);

	/**
	 * 查询反馈详情
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryFeedBack(HashMap<String, Object> paramsMap);
}
