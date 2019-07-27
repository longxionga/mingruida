package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.report.dao.IReportSettleDayAndMonthDao;
import com.acl.report.service.IReportSettleDayAndMonthService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ReportSettleDayAndMonthServiceImpl
 *author:wangli
 *createDate:2016年9月8日 下午5:03:28
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class ReportSettleDayAndMonthServiceImpl implements IReportSettleDayAndMonthService {

	@Autowired
	private IReportSettleDayAndMonthDao reportSettleDayAndMonthDao;
	
	@Override
	public PageList<?> queryReportSettleDay(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// 查询结算服务商日报表
		return reportSettleDayAndMonthDao.queryReportSettleDay(paramsMap, pageBounds);
	}

	@Override
	public PageList<?> queryReportSettleMonth(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// 查询结算服务商月报表
		return reportSettleDayAndMonthDao.queryReportSettleMonth(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> querySettleDayResultExport(Map<String, Object> map) {
		//导出会员日报表
		return reportSettleDayAndMonthDao.querySettleDayResultExport(map);
	}

	@Override
	public List<Map<String, Object>> querySettleMonthResultExport(Map<String, Object> map) {
		//导出会员月报表
		return reportSettleDayAndMonthDao.querySettleMonthResultExport(map);
	}

	@Override
	public List<Map<String, Object>> querySettleDaySum(Map<String, Object> map) {
		// 查询结算会员日汇总信息
		return reportSettleDayAndMonthDao.querySettleDaySum(map);
	}

	@Override
	public List<Map<String, Object>> querySettleMonthSum(Map<String, Object> map) {
		// 查询结算会员月汇总信息
		return reportSettleDayAndMonthDao.querySettleMonthSum(map);
	}

	

}
