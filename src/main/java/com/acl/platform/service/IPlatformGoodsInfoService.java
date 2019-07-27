package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformGoodsInfoService
 *author:wangli
 *createDate:2017年2月9日 下午6:34:13
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformGoodsInfoService {
	/**
	 * 查询商品属性信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryGoodsInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 删除商品属性信息
	 * @param paramsMap
	 */
	void deleteGoodsInfo(HashMap<String, Object> paramsMap);
	/**
	 * 增加商品属性信息
	 * @param paramsMap
	 */
	void insertGoodsInfo(HashMap<String, Object> paramsMap);
	/**
	 * 更新商品属性信息
	 * @param paramsMap
	 */
	void updateGoodsInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询字典表中所有商品名称
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap);
	
}
