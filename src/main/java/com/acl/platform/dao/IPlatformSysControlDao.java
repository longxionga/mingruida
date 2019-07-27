package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformSysControlDao
 *author:wangli
 *createDate:2016年10月29日 上午10:50:18
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformSysControlDao {
	/**
	 * 查询系统控制信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querySysControl(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 删除系统控制信息
	 * @param paramsMap
	 */
	void deleteSysControl(HashMap<String, Object> paramsMap);
	/**
	 * 增加系统控制信息
	 * @param paramsMap
	 */
	void insertSysControl(HashMap<String, Object> paramsMap);
	/**
	 * 更新系统控制信息
	 * @param paramsMap
	 */
	void updateSysControl(HashMap<String, Object> paramsMap);
	
	 /*
     * 查询开关是否开启
     */
	List<Map<String, Object>> checkUse(HashMap<String, Object> hashMap);
	
	/**
	 * 验证该开关是否已经存在
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> typeValidate(HashMap<String, Object> paramsMap);
}
