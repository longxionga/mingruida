package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.report.dao.ReportInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.report.service.ReportInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:ReportInfoServiceImpl
 *author:wangzhe
 *createDate:2016年9月7日 下午3:49:26
 *version:3.0
 *department:安创乐科技
 *description:
 */
@Service
public class ReportInfoServiceImpl implements ReportInfoService {

	@Autowired
	private ReportInfoDao reportInfoDao;
	
	@Override
	public PageList<?> queryReportAgentInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryReportAgentInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportAgentMonthInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryReportAgentMonthInfo(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> queryAgentDayResultExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryAgentDayResultExport(map);
	}

	@Override
	public List<Map<String, Object>> queryAgentMonthResultExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryAgentMonthResultExport(map);
	}

	@Override
	public List<HashMap<String, Object>> queryAgentDayCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryAgentDayCount(paramsMap);
	}

	@Override
	public List<HashMap<String, Object>> queryAgentMonthCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportInfoDao.queryAgentMonthCount(paramsMap);
	}

}
