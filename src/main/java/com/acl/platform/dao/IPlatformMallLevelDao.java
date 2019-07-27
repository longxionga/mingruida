package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformMallLevelDao
 *author:wangli
 *createDate:2017年4月24日 下午2:27:00
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformMallLevelDao {
	/**
	 * 查询等级表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryMallLevel(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 增加等级表信息
	 * @param paramsMap
	 */
	void insertMallLevel(HashMap<String, Object> paramsMap);
	
	/**
	 * 修改等级表信息
	 * @param paramsMap
	 */
	void updateMallLevel(HashMap<String, Object> paramsMap);
	
	/**
	 * 验证等级表信息
	 * 
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	List<Map<String, Object>> mallLevelValidate(HashMap<String, Object> hashMap);
	
	/**
	 * 验证等级名称是否重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> levelValidate(HashMap<String, Object> paramsMap);
}
