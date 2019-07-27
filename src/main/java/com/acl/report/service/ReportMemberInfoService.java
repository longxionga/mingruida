package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:ReportMemberInfoService
 *author:wangzhe
 *createDate:2016年9月8日 下午2:46:01
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface ReportMemberInfoService {

	/**
	 * 查询用户日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDayReportMemberInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询用户商月报表信息
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
	 * 查询服务商手续费信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querySettleFeeInfo(HashMap<String, Object> paramsMap,PageBounds pageBounds);
	
	/**
	 * 提现和充值的手续费统计信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> querySettleFeeCount(HashMap<String,Object> paramsMap);
	
	/**
	 *查询导出手续费信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleFeeExport(Map<String,Object> map);
}
