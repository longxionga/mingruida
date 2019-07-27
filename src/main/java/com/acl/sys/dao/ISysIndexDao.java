package com.acl.sys.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:SysUserInfoDao author:huangs createDate:2016年8月10日 下午5:53:06
 * vsersion:3.0 department:安创乐科技 description:
 */
public interface ISysIndexDao {
	/**
	 * 查询登陆信息
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryLoginInfo(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryUserRole(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryUserRoleByView(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryMenuRoleByView(HashMap<String, Object> paramsMap);

	/**
	 * 菜单权限查询
	 */
	List<Map<String, Object>> queryAuthorization(Map<String, Object> map);

	List<String> queryUserRoleForShiro(String currentUserId);

	List<Map<String, Object>> queryUserRoleMenuForShiro(String currentUserId);

	List<String> queryMenuRoleForShiro(String roleId);

	/**
	 * 首页相关查询
	 * @param paramsMap
	 * @return
	 */
	int countFrontUser(HashMap<String, Object> paramsMap);

	BigDecimal sumFrontUserWallet(HashMap<String, Object> paramsMap);

	BigDecimal sumFrontUserBrokerage(HashMap<String, Object> paramsMap);

	BigDecimal sumFrontCZ(HashMap<String, Object> paramsMap);

	BigDecimal sumFrontTX(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> countFrontUserMonth(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> countFrontUserBrokerageMonth(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryParentDeptIds(String dept_id);
}
