package com.acl.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:SysUserInfoDao
 *author:malx
 *createDate:2016年8月10日 下午5:53:06
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface SysUserInfoDao {
   
	/**
	 * 查询用户信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryUserInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 插入用户信息
	 * @param paramsMap
	 */
	void insertUserInfo(HashMap<String, Object> paramsMap);
    
	/**
	 * 查询初始化部门树信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryTreeDeptInfo(Map<String, Object> map);
   
	/**
	 * 验证用户是否正确
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> userValidate(HashMap<String, Object> paramsMap);
    
	/**
	 * 验证用户手机号是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> mobileValidate(HashMap<String, Object> paramsMap);
	
	/**
	 * 更新用户信息
	 * @param paramsMap
	 */
	void updateUserInfo(Map<String, Object> paramsMap);
   
	/**
	 * 重置用户密码
	 * @param paramsMap
	 */
	void resetUserInfoPwd(Map<String, Object> paramsMap);
   
	
	/**
	 * 查询用户菜单权限
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryRoleMenuBase(Map<String, Object> map);
	
	/**
	 * 查询用户部门信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryUserDeptInfo(Map<String, Object> map);
	
	/**
	 * 删除角色用户表中间表信息
	 * @param map
	 */
	void deleteUserRole(Map<String,Object> map);

	List<Map<String, Object>> queryDeptInfoByChildList(Map<String, Object> paramsMap);
	
	/**
	 * 查询用户余额信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryUserBalanceInfo(Map<String, Object> map);
	
	PageList<?> queryReportUserBalanceInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 *查询导出用户信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryUserBalanceExport(Map<String,Object> map);



	/**
	 *
	 * @param map
	 * @return
	 */
	void updateUserMobile(Map<String,Object> map);

	/**
	 * 删除员工用户信息
	 * @param map
	 */
	void deleteStaffUser(Map<String,Object> map);
}
