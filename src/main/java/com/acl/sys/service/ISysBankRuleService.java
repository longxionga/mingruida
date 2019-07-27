package com.acl.sys.service;

import java.util.HashMap;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ISysBankRuleService
 *author:wangli
 *createDate:2016年8月18日 下午7:44:16
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysBankRuleService {
	/**
	 * 查询充值提现规则
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBankRule(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
//	/**
//	 * 添加充值提现规则
//	 * @param paramsMap
//	 */
//	void insertBankRule(HashMap<String, Object> paramsMap);

	/**
	 * 修改充值提现规则
	 * @param paramsMap
	 */
	void updateBankRule(HashMap<String, Object> paHashMap);
//
//	/**
//	 * 删除充值提现规则
//	 * @param paramsMap
//	 */
//	void deleteBankRule(HashMap<String, Object> paHashMap);

	/**
	 * 查询充值提现规则
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBrandRule(HashMap<String, Object> paramsMap,
							  PageBounds pageBounds);
}
