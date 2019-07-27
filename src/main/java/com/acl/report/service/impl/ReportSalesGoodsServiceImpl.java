package com.acl.report.service.impl;


import com.acl.report.service.IReportSalesGoodsService;
import com.acl.report.dao.IReprotSalesGoodsDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangli on 2017/11/16.
 */
@Service
public class ReportSalesGoodsServiceImpl implements IReportSalesGoodsService {
    @Autowired
    private IReprotSalesGoodsDao reportSalesGoodsDao;

    @Override
    public PageList<?> queryReportSalesGoods(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return reportSalesGoodsDao.queryReportSalesGoods(paramsMap,pageBounds);
    }

    @Override
    public List<HashMap<String, Object>> queryReportSalesSum(HashMap<String, Object> paramsMap) {
        return reportSalesGoodsDao.queryReportSalesSum(paramsMap);
    }

}
