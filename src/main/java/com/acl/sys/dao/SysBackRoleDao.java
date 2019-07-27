package com.acl.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:SysBackRoleDao
 *author:wangli
 *createDate:2016年8月12日 下午5:03:36
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface SysBackRoleDao {
	/**
	 * 查询初始化角色树信息
	 * @param map
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
	 * 删除角色菜单中间表信息
	 * @param paramsMap
	 */
	void deleteBackRoleMenu(Map<String,Object> map);
	
	/**
	 * 删除人员角色中间表信息
	 * @param paramsMap
	 */
	void deleteBackRoleUser(Map<String,Object> map);
	
	/**
	 * 验证角色名是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> roleValidate(HashMap<String, Object> paramsMap);
	
	/**
	 * 添加角色菜单信息
	 * @param paramsMap
	 */
	void insertBackRoleMenu(Map<String,Object> map);
	
	/**
	 * 添加角色人员信息
	 * @param paramsMap
	 */
	void insertBackRoleUser(Map<String,Object> map);
	/**
	 * 查询初始化菜单树信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryMenuForRole(Map<String, Object> map);
	/**
	 * 查询角色和菜单信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryRoleMenu(HashMap<String, Object> paramsMap);
	/**
	 * 查询角色和用户信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryRoleUser(HashMap<String, Object> paramsMap);
	
	/**
	 * 删除人员角色中间表信息根据user_id,避免人员具有重复权限
	 * @param map
	 */
	void deleteBackRoleUserByUser(Map<String,Object> map);
	/**
	 * 查询角色信息
	 * @param paramsMap
	 * @return
			 */
	List<Map<String, Object>> fandBackRole(HashMap<String, Object> paramsMap);
}
