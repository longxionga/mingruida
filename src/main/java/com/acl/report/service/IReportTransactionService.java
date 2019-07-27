package com.acl.report.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportTransactionService {
    PageList<?> queryTransactionPageList(HashMap<String, Object> paramsMap,
                                        PageBounds pageBounds, LoginSession loginSession);

    /**
     * 查询员工流水数量统计
     * @param hashMap
     * @return
     */
    Map<String, Object> countTradeInfo(HashMap<String, Object> hashMap,LoginSession loginSession);

    /**
     * 员工流水导出
     * @param map
     * @return
     */
    List<Map<String, Object>> exportTradeReportInfo(Map<String,Object> map,LoginSession loginSession);


}
