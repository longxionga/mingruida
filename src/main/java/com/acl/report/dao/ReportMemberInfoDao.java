package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

public interface ReportMemberInfoDao {
	
	/**
	 * 查询会员信息日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDayReportMemberInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询会员信息月报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryMonthReportMemberInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询会员日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryDayResultExport(Map<String,Object> map);
	
	/**
	 * 查询会员月报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryMonthResultExport(Map<String,Object> map);
	
	/**
	 * 日统计信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryMemberDayCount(HashMap<String,Object> paramsMap);
	
	/**
	 * 月统计信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryMemberMonthCount(HashMap<String,Object> paramsMap);
	
	/**
	 * 查询服务商和用户手续费信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querySettleFeeInfo(HashMap<String, Object> paramsMap,PageBounds pageBounds);
	
	/**
	 * 统计手续费汇总信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> querySettleFeeCount(HashMap<String,Object> paramsMap);
	
	/**
	 *查询导出用户信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleFeeExport(Map<String,Object> map);

}
