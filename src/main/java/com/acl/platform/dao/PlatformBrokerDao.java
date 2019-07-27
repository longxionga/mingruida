package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.acl.utils.mongo.Pagination;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformBrokerDao
 *author:wangli
 *createDate:2016年8月29日 下午5:06:36
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface PlatformBrokerDao {
	/**
	 * 重置前台代理商密码
	 * @param paramsMap
	 */
	void updateBrokerPwd(HashMap<String, Object> paramsMap);
	/**
	 * 禁用/启用前台代理商
	 * @param paramsMap
	 */
	void updateBrokerState(HashMap<String, Object> paramsMap);
	
	
	public Pagination<T> queryBroker(HashMap<String, Object> paramsMap) ;
	
	/**
	 * 查询代理商信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBrokerInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询代理商所属部门下的所有代理商
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBrokerUser(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 更换代理商所属上级代理商
	 * @param paramsMap
	 */
	void updateBrokerUser(HashMap<String, Object> paramsMap);
	
	/**
	 * 
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBrokers(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 * 查询所有的代理商信息信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryBrokerForMon(Map<String, Object> map);
	
	/**
	 * 查询该代理商下面所有的用户
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryBrokerUserForMon(Map<String, Object> map);
	/**
	 * 升级代理商等级
	 * @param paramsMap
     */
	void upBroker(HashMap<String, Object> paramsMap);	
	/**
	 * 查询各个层级分配比例 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryBrokerAllot(Map<String, Object> map);
	
	/**
	 * 添加代理商分成比例到临时表
	 * @param paramsMap
	 */
	void insertBrokerProrateTemp(HashMap<String, Object> paramsMap);
	
	/**
	 * 将历史分成比例信息设置为不可用
	 * @param paramsMap
     */
	void updateBrokerProrateTemp(HashMap<String, Object> paramsMap);	
	
	/**
	 * 查询部门表各个层级分成比例
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAgentAllot(Map<String, Object> map);
	
	/**
	 * 将历史分成比例信息设置为不可用
	 * @param paramsMap
     */
	void updateBrokerProrate(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询比例分配表中是否存在该代理商的记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryBrokerForProrate(Map<String, Object> map);
	/**
	 * 往比例分配表插入一条代理商比例分配数据
	 * @param paramsMap
     */
	void insertBrokerProrate(HashMap<String, Object> paramsMap);
	
	/**
	 * 根据手机号加x或者加X进行加密搜索，查询该用户是否已经注销
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryUserByMobile(Map<String, Object> map);

	/**
	 * 根据手机号加x或者加X进行加密搜索，查询该用户是否已经注销
	 * @param map
	 * @return
	 */
	Map<String, Object> queryUserById(Map<String, Object> map);
	/**
	 *
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryUserInfoDept(HashMap<String, Object> paramsMap,
							 PageBounds pageBounds);


	/**
	 *
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	List<Map<String, Object>> queryUserInfoDeptList(HashMap<String, Object> paramsMap);
}
