package com.acl.platform.dao;

import java.util.HashMap;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *author:hufeng
 *createDate:2016年10月13日 下午3:03:04
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformSimUserInfoDao {
	 
	
	PageList<?> querySimUserInfo(HashMap<String, Object> paramsMap,PageBounds pageBounds);
	 
	/**
	 * 增加前台用户临时表信息
	 * @param paramsMap
	 */
	void insertSimUserInfo(Map<String, Object> paramsMap);
	
	
	
	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void insertSimUserLogin(Map<String, Object> paramsMap);
	
	
	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void insertSimUserWallet(Map<String, Object> paramsMap);

	/**
	 * 保存用户流水信息
	 * @param paramsMap
	 */
	void insertSimUserProfitLoss(Map<String, Object> paramsMap);
	
	
	
	
	/**
	 * 通过user_id查询用户单个对象
	 * @param paramsMap
	 * @return
	 */
	Map<String, Object> querySimUserInfoByMap(Map<String, Object> paramsMap);
	
	
	/**
	 * 更新用户名和密码
	 * @param paramsMap
	 */
	void updateSimUserInfo(Map<String, Object> paramsMap);
	
	
	//更新金额
	void updateSimUserWallet(Map<String, Object> paramsMap);
	
	
	void updateSimUserLogin(Map<String, Object> paramsMap);
	
}
