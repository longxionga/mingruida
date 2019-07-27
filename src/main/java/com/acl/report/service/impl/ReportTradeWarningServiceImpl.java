package com.acl.report.service.impl;

import com.acl.report.dao.IReportTradeWarningDao;
import com.acl.report.service.IReportTradeWarningService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * className:ReportTradeWarningServiceImpl
 * author:wang
 * createDate:2017年9月6日 上午9:54:58
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
public class ReportTradeWarningServiceImpl implements IReportTradeWarningService {
    @Autowired
    private IReportTradeWarningDao reportTradeWarningDao;


    @Override
    public PageList<?> queryReportTradeWarning(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return reportTradeWarningDao.queryReportTradeWarning(paramsMap, pageBounds);
    }
}
