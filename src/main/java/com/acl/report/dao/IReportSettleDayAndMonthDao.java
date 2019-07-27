package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportSettleDayAndMonth
 *author:wangli
 *createDate:2016年9月8日 下午5:01:18
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportSettleDayAndMonthDao {
	/**
	 * 查询结算服务商日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportSettleDay(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 * 查询结算服务商月报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportSettleMonth(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 *查询导出结算会员日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleDayResultExport(Map<String,Object> map);
	/**
	 *查询导出结算会员月报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleMonthResultExport(Map<String,Object> map);
	
	/**
	 *查询导出结算会员日报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleDaySum(Map<String,Object> map);
	/**
	 *查询导出结算会员月报表信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> querySettleMonthSum(Map<String,Object> map);
}
