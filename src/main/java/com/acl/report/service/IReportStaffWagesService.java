package com.acl.report.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.Map;

public interface IReportStaffWagesService {
    PageList<?> queryStaffWagesPageList(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds);

}
