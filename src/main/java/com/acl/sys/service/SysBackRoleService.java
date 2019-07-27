package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ISysBackRoleService
 *author:wangli
 *createDate:2016年8月12日 下午4:50:34
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface SysBackRoleService {

	/**
	 * 查询角色信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBackRole(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
   
	
	/**
	 * 添加角色信息
	 * @param paramsMap
	 */
	void insertBackRole(HashMap<String, Object> paramsMap);

	/**
	 * 修改角色信息
	 * @param paramsMap
	 */
	void updateBackRole(HashMap<String, Object> paHashMap);

	/**
	 * 删除角色信息
	 * @param paramsMap
	 */
	void deleteBackRole(HashMap<String, Object> paHashMap);
	
	/**
	 * 查询人员分配信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryTreeUser(Map<String, Object> map);

	/**
	 * 验证角色名是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> roleValidate(HashMap<String, Object> paramsMap);
	
	/**
	 * 菜单分配
	 * @param paramsMap
	 */
	void updateBackRoleMenu(HashMap<String, Object> paramsMap);
	
	/**
	 * 角色分配
	 * @param paramsMap
	 */
	void updateBackRoleUser(HashMap<String, Object> paramsMap);
	/**
	 * 查询初始化菜单树信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryMenuForRole(Map<String, Object> map);
	/**
	 * 查询角色与菜单信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryRoleMenu(HashMap<String, Object> paramsMap);
	/**
	 * 查询角色与用户信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryRoleUser(HashMap<String, Object> paramsMap);

}
