package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformCompanyStaffDao {

    /**
     * 员工信息查询查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryCompanyStaffInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

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
    int deleteCompanyStaffAll(Map<String, Object> paramsMap);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> findConpanyStaffByName(Map<String, Object> paramsMap);


    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> querystaffInfoByType(Map<String, Object> paramsMap);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> querystaffCompanyInfo(Map<String, Object> paramsMap);
    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> querystaffInfoForCombox(Map<String, Object> paramsMap);
    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> querystaffCompanyInfoForCombox(Map<String, Object> paramsMap);
    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> queryCompanyManagerForCombox(Map<String, Object> paramsMap);

    /**
     * 修改员工信息
     * @param paHashMap
     */
    void updCompanyStaff(HashMap<String, Object> paHashMap);

    /**
     * 新增员工信息
     * @param paHashMap
     */
    int  insCompanyStaff(HashMap<String, Object> paHashMap);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> queryStaffDeptInfoForCombox(Map<String, Object> paramsMap);

    /**
     * 验证员工编号是否重复
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> staffValidate(HashMap<String, Object> paramsMap);

    /**
     * 验证员工名称是否重复
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> staffNameValidate(HashMap<String, Object> paramsMap);
    /**
     *
     * @param paramsMap
     * @return
     */
    int deleteCompanyStaff(Map<String, Object> paramsMap);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> getCompanyStaffBox(Map<String, Object> paramsMap);

    /**
     * 根据id查询部门信息
     * @String name
     * @return
     */
    List<Map<String, Object>> findDeptInfoById(Map<String, Object> paramsMap);

    /**
     * 查询员工信息
     * @String name
     * @return
     */
    List<Map<String, Object>> findStaffInfoById(Map<String, Object> paramsMap);

    /**
     * 员工信息查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querystaffInfoInfoList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     *
     * @String name
     * @return
     */
    List<Map<String, Object>> queryStaffzhuguanForCombox(Map<String, Object> paramsMap);

    /**
     * 查询员工分公司经理信息
     * @String name
     * @return
     */
    List<Map<String, Object>> queryStaffjingli(Map<String, Object> paramsMap);



}
