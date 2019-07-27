package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;


/**
 *className:IPlatformBrokerDeptDao
 *author:wangli
 *createDate:2016年9月6日 上午10:54:17
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface PlatformAgentDeptDao {
	/**
	 * 查询代理商部门信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryAgentDept(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 删除经纪人到部门关联
	 * @param paramsMap
	 */
	void deleteAgentdeptBrokers(HashMap<String, Object> paramsMap);
   
	/**
	 * 查询代理商下面的所有经纪人及部门
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryAgentDeptBrokerInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 增加经纪人部门临时表历史信息
	 * @param paramsMap
	 */
	void insertAgentdeptBrokers(HashMap<String, Object> paramsMap);
	
	/**
	 * 插入代理商部门
	 * @param paramsMap
	 */
	void insertAgentDept(HashMap<String, Object> paramsMap);
   
	
	/**
	 * 更新代理商部门信息
	 * @param paramsMap
	 */
	void updateAgentdept(HashMap<String, Object> paramsMap);
	/**
	 * 设置经纪人到部门
	 * @param paramsMap
	 */
	void updateAgentdeptBrokers(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询所有的部门信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDeptForMon(Map<String, Object> map);
}
