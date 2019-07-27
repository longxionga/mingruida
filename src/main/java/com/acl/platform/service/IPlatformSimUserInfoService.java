package com.acl.platform.service;

import java.util.HashMap;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformSimUserInfoService
 *author:hufeng
 *createDate:2016年10月13日 下午2:56:53
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformSimUserInfoService {
	 
	/**
	 * 查询模拟用户信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querySimUserInfo(HashMap<String, Object> paramsMap,PageBounds pageBounds);
	 

	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void saveSimUserInfo(HashMap<String, Object> paramsMap);
  
	
	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void saveSimUserLogin(HashMap<String, Object> paramsMap);
	
	
	/**
	 * 插入后台用户信息
	 * 
	 * @param paramsMap
	 */
	void saveSimUserWallet(HashMap<String, Object> paramsMap);


	/**
	 * 通过user_id查询用户单个对象
	 * @param paramsMap
	 * @return
	 */
	Map<String, Object> querySimUserInfoByMap(Map<String, Object> paramsMap);


	
	
	
	/**
	 * 修改用户信息
	 * @param paramsMap
	 */
	void updateSimUserLogonWalletProfitLoss(HashMap<String, Object> paramsMap,Map<String, Object> userInfoMap);
	
	
	/**
	 * 修改用户信息
	 * @param paramsMap
	 */
	void updateSimUserInfo(HashMap<String, Object> paramsMap,Map<String, Object> userInfoMap);


	/**
	 * 重置登录密码 和用户可用标志
	 * @param paramsMap
	 */
	public void resetSimUserInfoPwdIsuse(Map<String, Object> paramsMap,Map<String, Object> userInfoMap);


	/**
	 * 保存用户流水表
	 * @param paramsMap
	 */
	void saveSimUserProfitLoss(Map<String, Object> paramsMap);
	
	
	/**
	 * 更新钱包
	 * @param paramsMap
	 */
	void updateSimUserWallet(Map<String, Object> paramsMap);


	void resetSimUserInfoPwd(Map<String, Object> paramsMap);
 
}
