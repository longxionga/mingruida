package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportStaffWagesDao {

    /**
     * 直营员工流水提成报表查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryshuabaoStaffWagesPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 查询所有组员流水
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryshuabaoStaffPaymentFlowSum(Map<String, Object> paramsMap);

}
