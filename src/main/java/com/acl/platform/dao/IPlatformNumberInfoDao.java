package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformNumberInfoDao
 *author:wangli
 *createDate:2017年2月21日 下午3:20:51
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformNumberInfoDao {
	/**
	 * 查询靓号信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryNumberInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
    
	/**
	 * 删除靓号
	 * @param paramsMap
	 */
	void deleteNumberInfo(HashMap<String, Object> paramsMap);
	/**
	 * 增加商品图片信息
	 * @param paramsMap
	 */
	void insertNumberInfo(HashMap<String, Object> paramsMap);
	/**
	 * 更新靓号信息
	 * @param paramsMap
	 */
	void updateNumberInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 查询靓号是否与t_front_broker表中已有邀请码重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryBrokerCode(HashMap<String, Object> paramsMap);
	/**
	 * 查询靓号是否与t_back_number_info表中已生成靓号重复
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryNumberInfoCode(HashMap<String, Object> paramsMap);
}
