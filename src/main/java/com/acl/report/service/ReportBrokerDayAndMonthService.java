package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportAgentDailyService
 *author:wangli
 *createDate:2016年9月8日 上午11:34:29
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ReportBrokerDayAndMonthService {
	/**
	 * 查询代理商日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportBrokerDay(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 * 查询代理商月报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportBrokerMonth(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 *查询导出代理商日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryBrokerDayResultExport(Map<String,Object> map);
	
	/**
	 *查询导出代理商月报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryBrokerMonthResultExport(Map<String,Object> map);
	
	/**
	 *查询代理商日报表汇总信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryBrokerDaySum(Map<String,Object> map);
	
	/**
	 *查询代理商月报表汇总信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryBrokerMonthSum(Map<String,Object> map);
	
	 /**
     * 查询符合条件的代理商日报表计数
     * @param hashMap
     * @return
     */
    Map<String, Object> countBrokerDay(HashMap<String, Object> hashMap);
    
    /**
     * 查询符合条件的代理商月报表计数
     * @param hashMap
     * @return
     */
    Map<String, Object> countBrokerMonth(HashMap<String, Object> hashMap);
}
