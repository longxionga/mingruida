package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformBankCardService
 *author:wangli
 *createDate:2017年6月1日 下午1:48:55
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface PlatformBankCardService {
	/**
	 * 查询银行卡信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBankCard(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询用户银行卡信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryBankCardInfo(HashMap<String,Object> paramsMap);
}
