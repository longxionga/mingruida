package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformCompanyStaffService {
    PageList<?> queryCompanyStaffInfoPageList(HashMap<String, Object> paramsMap,
                                                PageBounds pageBounds);

    /**
     * 直营员工导入
     * @param file
     */
    void companystaffimport(File file);

    /**
     * 直营员工插入
     * @param paramsMap
     */
    int insertCompanyStaff(Map<String, Object> paramsMap);

    /**
     * 删除直营员工信息
     * @param paramsMap
     */
    int deleteCompanyStaffAll(Map<String, Object> paramsMap);
    /**
     * 查询员工信息 列表
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> querystaffInfoByType(HashMap<String, Object> hashMap);

    /**
     * 查询员工所属公司信息
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> querystaffCompanyInfo(HashMap<String, Object> hashMap);
    /**
     * 公司品牌下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> querystaffInfoForCombox(HashMap<String, Object> hashMap);
    /**
     * 分公司下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> querystaffCompanyInfoForCombox(HashMap<String, Object> hashMap);

    /**
     * 公司经理下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> queryCompanyManagerForCombox(HashMap<String, Object> hashMap);
    /**
     * 新增员工信息
     * @param paHashMap
     * @param
     * @return listMap
     */
    String insCompanyStaff(HashMap<String, Object> paHashMap, LoginSession loginSession) throws Exception;

    /**
     * 新增员工信息
     * @param paHashMap
     * @param
     * @return listMap
     */
    void updCompanyStaff(HashMap<String, Object> paHashMap);

    /**
     * 公司品牌下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> queryStaffDeptInfoForCombox(HashMap<String, Object> hashMap);

    /**
     * 员工编号重复校验
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> staffValidate(HashMap<String, Object> paramsMap);

    /**
     * 员工名称重复校验
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> staffNameValidate(HashMap<String, Object> paramsMap);

    /**
     * 删除直营员工信息
     * @param paramsMap
     */
    String deleteCompanyStaff(Map<String, Object> paramsMap) throws Exception;

    /**
     * 上级员工搜索下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> getCompanyStaffBox(HashMap<String, Object> hashMap , LoginSession loginSession );

    /**
     * 下级员工下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> querySubStaffForCombox(HashMap<String, Object> hashMap , LoginSession loginSession);

    /**
     * 创建员工登陆账户
     * @param paHashMap
     * @param position
     * @return listMap
     */
    String  foundUserbyStaff(Map<String ,Object>  paHashMap,String position ) throws Exception;
    /**
       * 新增员工信息
     * @param paHashMap
     * @param
     * @return listMap
     */
    String insCompanyStaff2(HashMap<String, Object> paHashMap, LoginSession loginSession) throws Exception;

    /**
     * 查询公司员工信息
     * @param hashMap
     * @param
     * @return listMap
     */

    PageList<?> querystaffInfoInfoList(HashMap<String, Object> hashMap,
                                              PageBounds pageBounds);


    /**
     * 员工主管下拉框
     * @param hashMap
     * @param
     * @return listMap
     */
    List<Map<String,Object>> queryStaffzhuguanForCombox(HashMap<String, Object> hashMap);

    /**
     * 组员升职
     * @param paHashMap
     * @param
     * @return listMap
     */
    String staffPromotion(HashMap<String, Object> paHashMap) throws Exception;

}
