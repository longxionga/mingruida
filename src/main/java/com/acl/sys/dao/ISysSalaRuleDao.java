package com.acl.sys.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:ISysBankRuleDao
 *author:wangli
 *createDate:2016年8月18日 下午8:43:23
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysSalaRuleDao {
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
	 * @param paramsMap
	 */
	void updateSalaRule(HashMap<String, Object> paHashMap);


 	int  insertSalaRule(HashMap<String, Object> paHashMap);

	/**
	 * 根据编码查询字段信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryDictSalaRule(HashMap<String, Object> paramsMap);


	/**
	 * 根据编码查询字段信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryDictBrandRule(HashMap<String, Object> paramsMap);


	/**
	 *
	 * @param paramsMap
	 * @return
	 */
	int insertStatisticsMerchant(Map<String, Object> paramsMap);


	/**
	 *
	 * @param paramsMap
	 * @return
	 */
	int deleteStatisticsMerchant(Map<String, Object> paramsMap);

	List<Map<String, Object>> queryBrandList(HashMap<String, Object> paramsMap);

}
