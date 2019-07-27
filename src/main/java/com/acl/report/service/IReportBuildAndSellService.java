package com.acl.report.service;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.acl.pojo.BuildAndSellInfo;
import com.acl.utils.mongo.Pagination;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 *className:IReportBuildPositionService
 *author:wangzhe
 *createDate:2016年9月23日 上午9:45:23
 *version:3.0
 *department:安创乐科技
 *description:
 */

public interface IReportBuildAndSellService {
	
	public Pagination<T> queryBuildPositionInfo(HashMap<String, Object> paramsMap);
	
	public Pagination<T> querySellPositionInfo(HashMap<String, Object> paramsMap);
	
	public Pagination<T> querySellPositionBackInfo(HashMap<String, Object> paramsMap);
	
	public List<BuildAndSellInfo> queryBuildInfoCount(HashMap<String, Object> paramsMap);
	
	public List<BuildAndSellInfo> querySellInfoCount(HashMap<String, Object> paramsMap);
	
	public JSONObject updatChargeBackInfo(HashMap<String, Object> paramsMap);
	
	public Object queryRSellmoneyInfo(HashMap<String, Object> paramsMap);

}
