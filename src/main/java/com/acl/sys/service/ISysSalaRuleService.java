package com.acl.sys.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:ISysBankRuleService
 *author:wangli
 *createDate:2016年8月18日 下午7:44:16
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysSalaRuleService {
	/**
	 * 查询充值提现规则
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querySalaRule(HashMap<String, Object> paramsMap,
                              PageBounds pageBounds);
//	/**
//	 * 添加充值提现规则
//	 * @param paramsMap
//	 */
//	void insertBankRule(HashMap<String, Object> paramsMap);

	/**
	 * 修改充值提现规则
	 * @param paHashMap
	 */
	void updateSalaRule(HashMap<String, Object> paHashMap);
//
//	/**
//	 * 删除充值提现规则
//	 * @param paramsMap
//	 */
//	void deleteBankRule(HashMap<String, Object> paHashMap);

	void insSalaRule(HashMap<String, Object> paHashMap);


	List<Map<String, Object>> queryDictSalaRule(HashMap<String, Object> paramsMap) ;

	List<Map<String, Object>> queryDictBrandRule(HashMap<String, Object> paramsMap) ;

    int doSumBrandRule(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryBrandList(HashMap<String, Object> paramsMap) ;
}
