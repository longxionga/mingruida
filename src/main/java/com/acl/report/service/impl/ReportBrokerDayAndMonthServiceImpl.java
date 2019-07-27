package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.report.dao.ReportBrokerDayAndMonthDao;
import com.acl.report.service.ReportBrokerDayAndMonthService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ReportAgentDailyServiceImpl
 *author:wangli
 *createDate:2016年9月8日 上午11:35:02
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class ReportBrokerDayAndMonthServiceImpl implements ReportBrokerDayAndMonthService {
	@Autowired
	private ReportBrokerDayAndMonthDao reportBrokerDayAndMonthDao;
	
	@Override
	public PageList<?> queryReportBrokerDay(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportBrokerDayAndMonthDao.queryReportBrokerDay(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportBrokerMonth(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportBrokerDayAndMonthDao.queryReportBrokerMonth(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> queryBrokerDayResultExport(Map<String, Object> map) {
		return reportBrokerDayAndMonthDao.queryBrokerDayResultExport(map);
	}

	@Override
	public List<Map<String, Object>> queryBrokerMonthResultExport(Map<String, Object> map) {
		return reportBrokerDayAndMonthDao.queryBrokerMonthResultExport(map);
	}

	@Override
	public List<Map<String, Object>> queryBrokerDaySum(Map<String, Object> map) {
		// 查询汇总信息日报表
		return reportBrokerDayAndMonthDao.queryBrokerDaySum(map);
	}

	@Override
	public List<Map<String, Object>> queryBrokerMonthSum(Map<String, Object> map) {
		// 查询月报表汇总信息
		return reportBrokerDayAndMonthDao.queryBrokerMonthSum(map);
	}

	@Override
	public Map<String, Object> countBrokerDay(HashMap<String, Object> hashMap) {
		// 查询代理商日报表符合条件的记录总数
		return reportBrokerDayAndMonthDao.countBrokerDay(hashMap);
	}

	@Override
	public Map<String, Object> countBrokerMonth(HashMap<String, Object> hashMap) {
		// 查询代理商月报表符合条件的记录总数
		return reportBrokerDayAndMonthDao.countBrokerMonth(hashMap);
	}

}
