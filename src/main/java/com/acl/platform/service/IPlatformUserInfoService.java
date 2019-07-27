package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformFrontUserInfo
 *author:wangli
 *createDate:2016年8月25日 下午2:56:53
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformUserInfoService {
	/**
	 * 重置前台用户密码
	 * @param paramsMap
	 */
	void updateUserInfoPwd(HashMap<String, Object> paramsMap);
	
	/**
	 * 重置前台微信信息
	 * @param paramsMap
	 */
	void updateUserWeiXinInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 禁用前台用户
	 * @param paramsMap
	 */
	void updateUserInfoState(HashMap<String, Object> paramsMap);
	
	/**
	 * 注销前台用户
	 * @param paramsMap
	 */
	void logoutUserInfoState(HashMap<String, Object> paramsMap);
	
	/**
	 * 注销前台经纪人
	 * @param paramsMap
	 */
	void logoutBrokerInfoState(HashMap<String, Object> paramsMap);
	
	HashMap<String, Object>  selectUserCz(HashMap<String, Object> paramsMap);
	
	//public Pagination<T> queryUserInfo(HashMap<String, Object> paramsMap) ; 
	
	PageList<?> queryUserInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 更改用户所属经纪人
	 * @param paramsMap
	 */
	void updateUserBroker(HashMap<String, Object> paramsMap);
	
	
	/**
	 * 删除前台用户临时表历史信息
	 * @param paramsMap
	 */
	void deleteUserBroker(HashMap<String, Object> paramsMap);
	
	/**
	 * 增加前台用户临时表信息
	 * @param paramsMap
	 */
	void insertUserBroker(HashMap<String, Object> paramsMap);
	

	/**
	 * 经纪人信息查询
	 * 
	 * @param paramsMap
	 * @return
	 */
	List<Map<String, Object>> queryBrokerLevel(HashMap<String, Object> paramsMap);
	/**
	 * 将注销的经纪人的邀请码在t_back_number_info状态改为不可用
	 * @param paramsMap
	 */
	void updateNumberState(HashMap<String, Object> paramsMap);
	
	/**
	 *用户余额统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryUserBalanceSum(Map<String,Object> map);


	/**
	 * 禁用前台用户
	 * @param paramsMap
	 */
	int updateUserInfoidCardAuth(HashMap<String, Object> paramsMap);


	/**
	 * 关闭所有用户：禁用
	 * @param paramsMap
	 */
	void updateCloseAll(HashMap<String, Object> paramsMap);


	PageList<?> queryUserAgentInfo(HashMap<String, Object> paramsMap,
								   PageBounds pageBounds);



	/**
	 * 关闭所有用户：禁用
	 * @param paramsMap
	 */
	int deleteSalaAgentAll(HashMap<String, Object> paramsMap);


	/**
	 * 插入代理商用户
	 * @param paramsMap
	 */
	int insertDataMerchant(Map<String, Object> paramsMap);


	/**
	 * 插入交易信息
	 * @param paramsMap
	 */
	int insertDataTradeOrder(Map<String, Object> paramsMap);


	/**
	 * 插入交易信息
	 * @param paramsMap
	 */
	int insertDataTradeOrderAll(Map<String, Object> paramsMap);



	PageList<?> queryUserTransf(HashMap<String, Object> paramsMap,
								   PageBounds pageBounds);


	int insertUserTransf(HashMap<String, Object> paramsMap);



	PageList<?> giftMerchantInfo(HashMap<String, Object> paramsMap,
								PageBounds pageBounds);

	/**
	 * 禁用前台用户
	 * @param paramsMap
	 */
	int savaAgentToNdept(HashMap<String, Object> paramsMap);

	/**
	 * 插入机具信息
	 * @param paramsMap
	 */
	int insertDataMachineInfoAll(Map<String, Object> paramsMap);

	/**
	 * 插入机具信息
	 * @param paramsMap
	 */
	int insertDataHKMachineInfoAll(Map<String, Object> paramsMap);

	/**
	 * 插入交易信息
	 * @param paramsMap
	 */
	int insertHKDataTradeOrderAll(Map<String, Object> paramsMap);

	/**
	 * 插入海科码盒代理商
	 * @param paramsMap
	 */
	int insertHKMerchant(Map<String, Object> paramsMap);


}
