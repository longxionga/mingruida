package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformMachineInfoDao {

    /**
     * 机具查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMachineInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     *
     * @param paramsMap
     * @return
     */
    int insertCompanyStaff(Map<String, Object> paramsMap);
    /**
     *
     * @param paramsMap
     * @return
     */
    int deleteMachineInfoAll(Map<String, Object> paramsMap);

    /**
     *  查询机具信息
     * @String name
     * @return
     */
    List<Map<String, Object>> findMachineById(Map<String, Object> paramsMap);

    int savaMachaneToStaff(Map<String, Object> paramsMap);
    /**
     *  机具调拨/回调
     * @String name
     * @return
     */
    int machineAllocation(Map<String, Object> paramsMap);
    /**
     *  机具调拨/回调
     * @String name
     * @return
     */
    int machineAllocations(Map<String, Object> paramsMap);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> findMachineInfoList(Map<String, Object> paramsMap);

    /**
     *  机具信息更新状态
     * @String name
     * @return
     */
    int updateMachineinfo(Map<String, Object> paramsMap);

    /**
     *  机具信息更新状态
     * @String name
     * @return
     */
    int updateMachineinfo_2(Map<String, Object> paramsMap);


    /**
     *  删除机具信息
     * @param paramsMap
     * @return
     */
    int deleteMachineInfo(Map<String, Object> paramsMap);

    /**
     *查询导出机具信息
     * @param map
     * @return
     */
    List<Map<String,Object>> exportMachineReportInfo(Map<String,Object> map);

    /**
     * 查询符合条件的机具计数
     * @param hashMap
     * @return
     */
    Map<String, Object> countMachineInfo(HashMap<String, Object> hashMap);

    /**
     *  机具变更归属员工
     * @String name
     * @return
     */
    int updateMachineForStaffid(Map<String, Object> paramsMap);
}
