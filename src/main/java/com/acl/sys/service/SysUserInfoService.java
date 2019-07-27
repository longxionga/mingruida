package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.FrontSalaAgentInfo;
import com.acl.pojo.UserInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:ISysUsrInfoService author:malx createDate:2016年8月10日 下午5:52:17
 * vsersion:3.0 department:安创乐科技 description:
 */
public interface SysUserInfoService {

	/**
	 * 查询用户信息
	 * 
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryUserInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds);

	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void insertUserInfo(HashMap<String, Object> paramsMap);

	/**
	 * 查询初始化部门树信息
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryTreeDeptInfo(Map<String, Object> map);

	/**
	 * 验证用户名是否重复
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> userValidate(HashMap<String, Object> paramsMap);

	/**
	 * 验证手机号是否重复
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> mobileValidate(HashMap<String, Object> paramsMap);

	/**
	 * 更新用户信息
	 * 
	 * @param paramsMap
	 */
	void updateUserInfo(HashMap<String, Object> paramsMap);

	void sendPhoneCode(String mobile, String message,String rd) throws Exception;

	/**
	 * 重置后台用户密码
	 * 
	 * @param paramsMap
	 */
	void resetUserInfoPwd(HashMap<String, Object> paramsMap);

	/**
	 * 查询用户权限列表
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryRoleMenuBase(Map<String, Object> map);

	/**
	 * 查询用户部门信息
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryUserDeptInfo(Map<String, Object> map);

	/**
	 * 删除角色用户表中间表信息
	 * 
	 * @param paramsMap
	 */
	void deleteUserRole(Map<String, Object> map);

	List<Map<String, Object>> queryDeptInfoByChildList(Map<String, Object> map);
	
	/**
	 * 查询用户余额信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryUserBalanceInfo(Map<String, Object> map);
	
	PageList<?> queryReportUserBalanceInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds);
	
	/**
	 *查询导出用户信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryUserBalanceExport(Map<String,Object> map);


	/**
	 *更新用户电话
	 * @param map
	 * @return
	 */
	void updateUserMobile(Map<String,Object> map);


	int saveUserInfo(UserInfo userInfo);


	List<UserInfo> getUserInfoList();


	int updateUserInfo(UserInfo userInfo);


	UserInfo getUserInfoById(String id);


	/**
	 *
	 * @param userInfo
	 * @param frontSalaAgentInfo
	 * @return
	 */
	int saveUserInfoAndFrontSalaAgentInfo(UserInfo userInfo,FrontSalaAgentInfo frontSalaAgentInfo);

	/**
	 *
	 * @param userInfo
	 * @param frontSalaAgentInfo
	 * @return
	 */
	int updateUserInfoAndFrontSalaAgentInfo(UserInfo userInfo,FrontSalaAgentInfo frontSalaAgentInfo);
}
