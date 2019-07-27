package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

public interface ReportLotteryDao {
    /**
     * 查询彩票信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryLotteryInfo(HashMap<String, Object> paramsMap,
                              PageBounds pageBounds);
}
