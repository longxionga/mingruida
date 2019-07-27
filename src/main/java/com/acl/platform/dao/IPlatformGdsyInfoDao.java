package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:IPlatformGdsyInfoDao
 *author:wangzhe
 *createDate:2016年8月12日 上午10:45:23
 *version:3.0
 *department:安创乐科技
 *description: 模式二信息接口
 */
public interface IPlatformGdsyInfoDao {
	
	/**
	 * 查询模式二信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryGdsyInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 插入模式二信息
	 * @param paramsMap
	 */
	void insertGdsyInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 更新模式二信息
	 * @param paramsMap
	 */
	void updateGdsyInfo(HashMap<String, Object> paramsMap);

	/**
	 * 验证模式二商品名是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> gdsyGoodsValidate(HashMap<String, Object> paramsMap);
	
	/**
	 * 插入模式二部门中间表
	 * @param paramsMap
	 */
	void insertDeptGdsyInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 删除模式二部门中间表
	 * @param paramsMap
	 */
	void deleteGdsyDept(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询部门与商品的关系
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGdsyRelationDeptInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询商品信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGdsyGoodsInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询模式二商品信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGdsyGoodsInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询模式二商品组
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGdsyGroup(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询模式二关系表
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> selectGdsyRelation(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询商品和部门信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGdsyDeptInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询关系表代理商不存在的数据
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGdsyDeptNoexist(HashMap<String, Object> paramsMap);
}
