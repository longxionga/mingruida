package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

public interface SysBackDictInfoService {
	/**
	 * 查询字典信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryBackDictInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 * 添加字典信息
	 * @param paramsMap
	 */
	void insertBackDictInfo(HashMap<String, Object> paramsMap);

	/**
	 * 修改字典信息
	 * @param paramsMap
	 */
	void updateBackDictInfo(HashMap<String, Object> paHashMap);

	/**
	 * 删除字典信息
	 * @param paramsMap
	 */
	void deleteBackDictInfo(HashMap<String, Object> paHashMap);
	/**
	 * 验证字典名称是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> dictValidate(HashMap<String, Object> paramsMap);

	/**
	 * 根据字段编码查询字段信息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryDictByCodeId(HashMap<String, Object> paramsMap);
	/**
	 * 查询字典类型
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryDictDesc(HashMap<String, Object> paramsMap);

}
