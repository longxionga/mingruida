package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:IPlatfomrGzpInfoService
 *author:wangzhe
 *createDate:2016年8月12日 上午10:00:18
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformGzpInfoService {
   
	/**
	 * 查询模式一信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryGzpInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
   
	/**
	 * 插入模式一信息
	 * @param paramsMap
	 */
	void insertGzpInfo(HashMap<String, Object> paramsMap);
   

	/**
	 * 更新模式一信息
	 * @param paramsMap
	 */
	void updateGzpInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 验证商品名是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> gzpGoodsValidate(HashMap<String, Object> paramsMap);
	
	/**
	 * 插入模式一部门中间表
	 * @param paramsMap
	 */
	void insertDeptGzpInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 插入模式一部门中间表
	 * @param paramsMap
	 */
	void deleteGzpDept(HashMap<String, Object> paramsMap);
	
	/**
	 * 模式一查询部门与商品的关系
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGzpRelationDeptInfo(HashMap<String, Object> paramsMap);
	
	
	/**
	 * 查询查询模式一收益信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGzpGoodsInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询模式一商品的信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGzpGoodsInfo(HashMap<String, Object> paramsMap);

	/**
	 * 查询模式一商品的信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGzpDeptGoodsInfo(HashMap<String, Object> paramsMap);

	/**
	 * 查询模式一商品的组
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGzpGroup(HashMap<String, Object> paramsMap);

	/**
	 * 查询模式一商品的组
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGzpDeptGroup(HashMap<String, Object> paramsMap);

	/**
	 * 查询模式一商品关系
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGzpRelation(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询商品与部门信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGzpDeptInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询关系表代理商不存在的数据
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGzpDeptNoexist(HashMap<String, Object> paramsMap);

}
