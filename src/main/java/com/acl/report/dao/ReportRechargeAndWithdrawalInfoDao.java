package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *
 *className:IReportRechargeInfoDao
 *author:wangzhe
 *createDate:2016年10月7日 下午3:35:32
 *version:3.0
 *department:安创乐科技
 *description:
 */

public interface ReportRechargeAndWithdrawalInfoDao {

	/**
	 * 查询充值报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportRechargeInfo(HashMap<String, Object> paramsMap,
										PageBounds pageBounds);


	/**
	 * 查询保证金充值报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportRechargeEnsureInfo(HashMap<String, Object> paramsMap,
											  PageBounds pageBounds);

	/**
	 * 查询提现报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportWithdrawalInfo(HashMap<String, Object> paramsMap,
										  PageBounds pageBounds);

	/**
	 * 查询充值数据COUNT
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportRechargeCount(HashMap<String,Object> paramsMap);

	/**
	 * 查询保证金充值数据COUNT
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportRechargeEnsureCount(HashMap<String,Object> paramsMap);

	/**
	 * 查询提现数据COUNT
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportWithdrawalCount(HashMap<String,Object> paramsMap);


	/**
	 *查询导出充值信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryRechargeExport(Map<String,Object> map);

	/**
	 *查询导出提现信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryWithdrawalExport(Map<String,Object> map);

	/**
	 *查询导出保证金信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryEnsureInfoExport(Map<String,Object> map);

}
