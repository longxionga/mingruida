package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformMachineLogDao {

    /**
     * 机具调拨信息查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMachineLogPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     *  保存机具调拨信息
     * @param paramsMap
     * @return
     */
    int savaMachaneLog(Map<String, Object> paramsMap);

}
