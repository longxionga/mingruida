package com.acl.report.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportManageTransactionService {
    /**
     * 主管流水统计
     * @param paramsMap
     * @param pageBounds
     * @param loginSession
     * @return
     */
    PageList<?> queryManageTransactionPageList(HashMap<String, Object> paramsMap,
                                               PageBounds pageBounds, LoginSession loginSession);

    /**
     * 员工数量统计
     * @param hashMap
     * @return
     */
    Map<String, Object> countManageTradeInfo(HashMap<String, Object> hashMap, LoginSession loginSession);

    /**
     * 员工流水导出
     * @param map
     * @return
     */
    List<Map<String, Object>> exportManageTradeReportInfo(Map<String, Object> map, LoginSession loginSession);
}
