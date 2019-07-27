package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportTransactionDao {

    /**
     * 流水统计查询报表
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryTransactionPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 员工流水合计
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> querytradeSum(Map<String, Object> paramsMap);

    /**
     * 员工流水信息数量统计
     * @param paramsMap
     * @return
     */
    Map<String, Object> countTradeInfo(Map<String, Object> paramsMap);

    /**
     * 员工流水导出
     * @param map
     * @return
     */
    List<Map<String,Object>> exportTradeReportInfo(Map<String,Object> map);


}
