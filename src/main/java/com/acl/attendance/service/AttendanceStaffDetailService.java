package com.acl.attendance.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

public interface AttendanceStaffDetailService {
    PageList<?> queryAttendanceStaffDetailPageList(HashMap<String, Object> paramsMap,
                                        PageBounds pageBounds) throws Exception;

}
