package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformExceptionMachineInfoDao {

    /**
     * 机具查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryExceptionMachineInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     *  查询机具信息
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryExceptionMachineInfoById(Map<String, Object> paramsMap);

    /**
     *  机具临时表插入
     * @String name
     * @return
     */
    int intertMachineTempAllocation(Map<String, Object> paramsMap)  ;



}
