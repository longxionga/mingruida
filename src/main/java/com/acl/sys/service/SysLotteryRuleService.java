package com.acl.sys.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

public interface SysLotteryRuleService {
	/**
	 * 查询活动规则
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryLotteryRule(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);
	/**
	 * 修改活动规则
	 * @param
	 */
	void updateLotteryRule(HashMap<String, Object> paHashMap);


}
