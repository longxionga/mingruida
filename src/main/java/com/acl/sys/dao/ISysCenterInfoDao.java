package com.acl.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ISysCenterInfoDao
 *author:wangli
 *createDate:2016年8月16日 下午1:58:41
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface ISysCenterInfoDao {
	/**
	 * 查询消息中心信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryCenterInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 查询有效期内切为重要的消息中心信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryMsg(HashMap<String, Object> paramsMap);
    
	/**
	 * 插入消息中心信息
	 * @param paramsMap
	 */
	void insertCenterInfo(HashMap<String, Object> paramsMap);
       
	/**
	 * 验证消息中心是否正确
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> CenterValidate(HashMap<String, Object> paramsMap);
    
	/**
	 * 更新消息中心信息
	 * @param paramsMap
	 */
	void updateCenterInfo(HashMap<String, Object> paramsMap);
	/**
	 * 删除消息中心信息
	 * @param paramsMap
	 */
	void deleteCenterInfo(HashMap<String, Object> paramsMap);
	/**
	 * 主页查询消息详情
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryMsgInfo(HashMap<String, Object> paramsMap);
	/**
	 * 主页查询消息
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryMessage(HashMap<String, Object> paramsMap);
}
