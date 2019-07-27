package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:ReportInfoService
 *author:wangzhe
 *createDate:2016年9月7日 下午3:37:26
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface ReportInfoService {
	/**
	 * 查询服务商日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportAgentInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询服务商月报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportAgentMonthInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询会员日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryAgentDayResultExport(Map<String,Object> map);
	
	
	/**
	 * 查询会员月报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryAgentMonthResultExport(Map<String,Object> map);
	
	/**
	 * 日统计信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryAgentDayCount(HashMap<String,Object> paramsMap);
	
	/**
	 * 月统计信息
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryAgentMonthCount(HashMap<String,Object> paramsMap);
}
