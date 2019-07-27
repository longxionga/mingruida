package com.acl.report.service;

import java.util.HashMap;
import java.util.List;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:IReportDataInfoService
 *author:wangzhe
 *createDate:2016年10月28日 下午4:06:33
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportDataInfoService {
	
	/**
	 * 查询月=仓储费手续费
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询月=仓储费手续费
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
	 * 查询月手续费=仓储费
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDeptDataInfo(HashMap<String,Object> paramsMap);
	
	/**
	 * 查询部门月手续费=仓储费
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportDeptDataMonthInfo(HashMap<String,Object> paramsMap);
	
	/**
	 * 查询明细手续费=仓储费
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataDetailInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询月明细手续费=仓储费
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportDataDetailMonthInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询各个部门手续费=仓储费
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
	 * 查询各个交易中心手续费=仓储费
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportCenterDataInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询月明细手续费=仓储费 == 交易中心明细
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportCenterDetailInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 手续费=仓储费汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryReportCenterDataCount(HashMap<String,Object> paramsMap);


	/**
	 * 查询月=仓储费手续费
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportMerchantInfo(HashMap<String, Object> paramsMap,
									PageBounds pageBounds);
}
