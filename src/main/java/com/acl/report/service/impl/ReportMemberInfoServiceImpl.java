package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.report.service.ReportMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.report.dao.ReportMemberInfoDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

@Service
public class ReportMemberInfoServiceImpl implements ReportMemberInfoService {

	
	@Autowired
	private ReportMemberInfoDao reportMemberInfoDao;
	
	@Override
	public PageList<?> queryDayReportMemberInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryDayReportMemberInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryMonthReportMemberInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryMonthReportMemberInfo(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> queryDayResultExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryDayResultExport(map);
	}

	@Override
	public List<Map<String, Object>> queryMonthResultExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryMonthResultExport(map);
	}

	@Override
	public List<HashMap<String, Object>> queryMemberDayCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryMemberDayCount(paramsMap);
	}

	@Override
	public List<HashMap<String, Object>> queryMemberMonthCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.queryMemberMonthCount(paramsMap);
	}

	@Override
	public PageList<?> querySettleFeeInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.querySettleFeeInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> querySettleFeeCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.querySettleFeeCount(paramsMap);
	}

	@Override
	public List<Map<String, Object>> querySettleFeeExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return reportMemberInfoDao.querySettleFeeExport(map);
	}

}
