package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportManageTransactionDao {

    /**
     * 流水统计查询报表
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryManageTransactionPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);


    /**
     * 员工流水分组统计
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryManagetradeSum(Map<String, Object> paramsMap);

    /**
     * 员工统计
     * @param paramsMap
     * @return
     */
    Map<String, Object> countManageTradeInfo(Map<String, Object> paramsMap);

    /**
     * 员工流水合计导出
     * @param map
     * @return
     */
    List<Map<String,Object>> exportManageTradeReportInfo(Map<String,Object> map);

}
