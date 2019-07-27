package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.Map;

public interface IPlatformExceptionMachineInfoService {
    PageList<?> queryExceptionMachineInfoPageList(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds, LoginSession loginSession);

    /**
     * 保留机具信息到临时表，删除原数据
     * @param paramsMap
     */
    String removeMachineInfoAll(Map<String, Object> paramsMap) throws Exception;



}
