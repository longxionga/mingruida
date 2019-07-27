package com.acl.sys.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:ISysUsrInfoService author:malx createDate:2016年8月10日 下午5:52:17
 * vsersion:3.0 department:安创乐科技 description:
 */
public interface ISysIndexService {
	/**
	 * 用户信息查询
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryloginInfo(HashMap<String, Object> paramsMap);

	/**
	 * 查询用户角色
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryUserRole(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryUserRoleByView(HashMap<String, Object> paramsMap);

	List<Map<String, Object>> queryMenuRoleByView(HashMap<String, Object> paramsMap);

	/**
	 * 查询用户菜单
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getUserMenu(Map<String, Object> map);

	List<String> queryUserRoleForShiro(String currentUserId);

	List<Map<String, Object>> queryUserRoleMenuForShiro(String currentUserId);

	List<String> queryMenuRoleForShiro(String roleId);

	/**
	 * 首页相关数据查询
	 * 
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
