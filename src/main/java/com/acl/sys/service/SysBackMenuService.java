package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *className:SysBackMenuService
 *author:wangli
 *createDate:2016年8月10日 下午8:34:49
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface SysBackMenuService {
	/**
	 * 查询初始化菜单树信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryTreeBackMenu(Map<String, Object> map);
	/**
	 * 添加菜單信息
	 * @param paramsMap
	 */
	void insertBackMenu(HashMap<String, Object> paramsMap);

	/**
	 * 修改菜單信息
	 * @param paramsMap
	 */
	void updateBackMenu(HashMap<String, Object> paHashMap);

	/**
	 * 验证菜单名是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> menuValidate(HashMap<String, Object> paramsMap);
	/**
	 * 修改前查询信息
	 * @param paramsMap
	 */
	Object queryBackMenuInfo(HashMap<String, Object> paHashMap);
	
	List<Map<String, Object>> queryRoleMenuView(HashMap<String, Object> map);


}
