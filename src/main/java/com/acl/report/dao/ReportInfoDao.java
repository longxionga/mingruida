package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:ReportInfoDao
 *author:wangzhe
 *createDate:2016年9月7日 下午3:37:43
 *version:3.0
 *department:安创乐科技
 *description:
 */

public interface ReportInfoDao {
	/**
	 * 查询服务商报表信息
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
	 * 服务商导出日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryAgentDayResultExport(Map<String,Object> map);
	
	/**
	 * 服务商导出月报表信息
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
