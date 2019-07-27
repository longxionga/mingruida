package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.report.dao.ReportRechargeAndWithdrawalInfoDao;
import com.acl.report.service.ReportRechargeAndWithdrawalInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

@Service
@Transactional
public class ReportRechargeAndWithdrawalInfoServiceImpl implements ReportRechargeAndWithdrawalInfoService {

	@Autowired
	private ReportRechargeAndWithdrawalInfoDao reportRecAndWithdraInfoDao;

	@Override
	public PageList<?> queryReportRechargeInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportRechargeInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportWithdrawalInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportWithdrawalInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportRechargeCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportRechargeCount(paramsMap);
	}

	@Override
	public List<HashMap<String, Object>> queryReportWithdrawalCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportWithdrawalCount(paramsMap);
	}

	@Override
	public PageList<?> queryReportRechargeEnsureInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportRechargeEnsureInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportRechargeEnsureCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return reportRecAndWithdraInfoDao.queryReportRechargeEnsureCount(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryRechargeExport(Map<String, Object> map) {
		return reportRecAndWithdraInfoDao.queryRechargeExport(map);
	}

	@Override
	public List<Map<String, Object>> queryWithdrawalExport(Map<String, Object> map) {
		return reportRecAndWithdraInfoDao.queryWithdrawalExport(map);
	}

	@Override
	public List<Map<String, Object>> queryEnsureInfoExport(Map<String, Object> map) {
		return reportRecAndWithdraInfoDao.queryEnsureInfoExport(map);
	}

}
