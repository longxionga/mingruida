package com.acl.report.service.impl;

import com.acl.report.dao.ReportLotteryDao;
import com.acl.report.service.ReportLotteryService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class ReportLotteryServiceImpl implements ReportLotteryService {



    @Autowired
    private ReportLotteryDao reportLotteryDao;

    @Override
    public PageList<?> queryLotteryInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return reportLotteryDao.queryLotteryInfo(paramsMap,pageBounds);
    }
}
