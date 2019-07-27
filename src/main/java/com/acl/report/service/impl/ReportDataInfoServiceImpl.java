package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.report.dao.IReportDataInfoDao;
import com.acl.report.service.IReportDataInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

@Service
@Transactional
public class ReportDataInfoServiceImpl implements IReportDataInfoService{

	@Autowired
	private IReportDataInfoDao reportDataInfoDao;
	
	@Override
	public PageList<?> queryReportDataInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportDataInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportDataCount(HashMap<String, Object> paramsMap) {
		return reportDataInfoDao.queryReportDataCount(paramsMap);
	}

	@Override
	public List<HashMap<String, Object>> queryReportDeptDataInfo(HashMap<String, Object> paramsMap) {		
		return reportDataInfoDao.queryReportDeptDataInfo(paramsMap);
	}

	@Override
	public PageList<?> queryReportDataDetailInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportDataDetailInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportDataMonthInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportDataMonthInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportDeptDataMonthInfo(HashMap<String, Object> paramsMap) {
		return reportDataInfoDao.queryReportDeptDataMonthInfo(paramsMap);
	}

	@Override
	public PageList<?> queryReportDataDetailMonthInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportDataDetailInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryDeptReportDataInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryDeptReportDataInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportDeptDataCount(HashMap<String, Object> paramsMap) {
		return reportDataInfoDao.queryReportDeptDataCount(paramsMap);
	}

	@Override
	public PageList<?> queryReportCenterDataInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportCenterDataInfo(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportCenterDetailInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportCenterDetailInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryReportCenterDataCount(HashMap<String, Object> paramsMap) {
		return reportDataInfoDao.queryReportCenterDataCount(paramsMap);
	}




	@Override
	public PageList<?> queryReportMerchantInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportDataInfoDao.queryReportMerchantInfo(paramsMap, pageBounds);
	}

}
