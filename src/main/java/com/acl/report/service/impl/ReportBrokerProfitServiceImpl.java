package com.acl.report.service.impl;

import java.util.HashMap;


import com.acl.report.dao.IReportBrokerProfitDao;
import com.acl.report.service.IReportBrokerProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:ReportBrokerProfitServiceImpl
 * author:wangli
 * createDate:2017年9月5日 上午11:35:02
 * vsersion:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class ReportBrokerProfitServiceImpl implements IReportBrokerProfitService {

	@Autowired
	private IReportBrokerProfitDao reportBrokerProfitDao;


	@Override
	public PageList<?> queryReportBrokerProfit(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return reportBrokerProfitDao.queryReportBrokerProfit(paramsMap, pageBounds);
	}

}
