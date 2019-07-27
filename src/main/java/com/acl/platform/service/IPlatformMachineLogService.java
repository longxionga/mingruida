package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.Map;

public interface IPlatformMachineLogService {
    PageList<?> queryMachineLogPageList(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds, LoginSession loginSession);


}
