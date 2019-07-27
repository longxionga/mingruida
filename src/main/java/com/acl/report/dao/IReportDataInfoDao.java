package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:IReportDataInfoDao
 *author:wangzhe
 *createDate:2016年10月28日 下午3:57:43
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportDataInfoDao {
	
	/**
	 * 手续费=仓储费查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 手续费=仓储费月查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataMonthInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 手续费=仓储费汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDataCount(HashMap<String,Object> paramsMap);
	
	/**
	 * 手续费=仓储费部门查询
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDeptDataInfo(HashMap<String,Object> paramsMap);
	
	/**
	 * 手续费=仓储费部门月查询
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDeptDataMonthInfo(HashMap<String,Object> paramsMap);
	
	/**
	 * 手续费=仓储费明细查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataDetailInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 手续费=仓储费月明细查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataDetailMonthInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	
	/**
	 * 手续费=仓储费月明细查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptReportDataInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	
	/**
	 * 手续费=仓储费汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDeptDataCount(HashMap<String,Object> paramsMap);
	
	
	/**
	 * 手续费=仓储费月明细查询 交易中心
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportCenterDataInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);	
	
	/**
	 * 手续费=仓储费月明细查询 交易中心  ===明细
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportCenterDetailInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);	
	
	/**
	 * 手续费=仓储费汇总 交易中心
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportCenterDataCount(HashMap<String,Object> paramsMap);

	/**
	 * 手续费=仓储费月明细查询 交易中心  ===明细
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportMerchantInfo(HashMap<String, Object> paramsMap,
											PageBounds pageBounds);
}
