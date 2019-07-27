package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformMachineInfoService {
    PageList<?> queryMachineInfoPageList(HashMap<String, Object> paramsMap,
                                              PageBounds pageBounds, LoginSession loginSession);

    /**
     * 删除机具信息
     * @param paramsMap
     */
    int deleteMachineInfoAll(Map<String, Object> paramsMap);

    /**
     * 转移员工操作
     * @param hashMap
     * @return
     */
    Object savaMachaneToStaff(HashMap<String, Object> hashMap ,LoginSession loginSession);

    PageList<?> getMchineInfoPageList(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds, LoginSession loginSession);

    /**
     * pos机调拨下级
     * @param hashMap
     * @return
     */
    Object machineAllocation(HashMap<String, Object> hashMap,LoginSession loginSession);

    /**
     * pos机回调
     * @param hashMap
     * @return
     */
    Object machineCallback(HashMap<String, Object> hashMap,LoginSession loginSession);

    /**
     * pos机回调
     * @param hashMap
     * @return
     */
    Object machineCallbackSingle(HashMap<String, Object> hashMap,LoginSession loginSession);

    /**
     *查询导出机具信息
     * @param map
     * @return
     */
    List<Map<String,Object>> exportMachineReportInfo(Map<String,Object> map,LoginSession loginSession);

    /**
     * 查询符合条件的机具计数
     * @param hashMap
     * @return
     */
    Map<String, Object> countMachineInfo(HashMap<String, Object> hashMap);


}
