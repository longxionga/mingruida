package com.acl.report.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

/**
 * className:IReportTradeWarningService
 * author:wang
 * createDate:2017年9月6日 上午9:54:06
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface IReportTradeWarningService {
    /**
     * 查询用户预警信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryReportTradeWarning(HashMap<String, Object> paramsMap, PageBounds pageBounds);
}
