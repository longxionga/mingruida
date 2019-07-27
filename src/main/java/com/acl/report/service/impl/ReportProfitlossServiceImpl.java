package com.acl.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.report.dao.IReportProfitlossDao;
import com.acl.report.service.IReportProfitlossService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ReportProfitlossServiceImpl
 *author:wangli
 *createDate:2017年3月2日 上午9:54:58
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
public class ReportProfitlossServiceImpl implements IReportProfitlossService {
	@Autowired
	private IReportProfitlossDao reportProfitlossDao;

	@Override
	public PageList<?> queryReportUserProfitloss(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportProfitlossDao.queryReportUserProfitloss(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> queryReportUserInto(Map<String, Object> map) {
		return reportProfitlossDao.queryReportUserInto(map);
	}

	@Override
	public List<Map<String, Object>> queryReportUserRollOut(Map<String, Object> map) {
		return reportProfitlossDao.queryReportUserRollOut(map);
	}



}
