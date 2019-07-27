package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformGoodsImageDao
 *author:wangli
 *createDate:2017年2月6日 下午4:27:46
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformGoodsImageDao {
	/**
	 * 查询商品图片信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryGoodsImage(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 删除商品属性信息
	 * @param paramsMap
	 */
	void deleteGoodsImage(HashMap<String, Object> paramsMap);
	/**
	 * 增加商品图片信息
	 * @param paramsMap
	 */
	void insertGoodsImage(HashMap<String, Object> paramsMap);
	/**
	 * 更新商品图片信息
	 * @param paramsMap
	 */
	void updateGoodsImage(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询商品code和商品名称
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap);
	/**
	 * 查询数据库中t_goods_image表中
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryGoodsUrl(HashMap<String, Object> paramsMap);
}
