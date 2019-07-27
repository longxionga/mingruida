package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.acl.pojo.BuildAndSellInfo;
import com.acl.utils.mongo.Pagination;

/**
 * 
 *className:IReportBuildPositionDao
 *author:wangzhe
 *createDate:2016年9月23日 上午9:40:43
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportBuildAndSellInfoDao {

	public Pagination<T> queryBuildPositionInfo(HashMap<String, Object> paramsMap);
	
	public Pagination<T> querySellPositionInfo(HashMap<String, Object> paramsMap);
	
	public Pagination<T> querySellPositionBackInfo(HashMap<String, Object> paramsMap);
	
	public List<BuildAndSellInfo> queryBuildInfoCount(HashMap<String, Object> paramsMap);
	
	public List<BuildAndSellInfo> querySellInfoCount(HashMap<String, Object> paramsMap);
	
	public void updatChargeBackInfo(HashMap<String, Object> paramsMap);
	
	public Object queryRSellmoneyInfo(HashMap<String, Object> paramsMap);

}
