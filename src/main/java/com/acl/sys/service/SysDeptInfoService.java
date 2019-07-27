package com.acl.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.DeptInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.alibaba.fastjson.JSONObject;

/**
 *className:SysDeptInfoService
 *author:huangs
 *createDate:2016年8月10日 下午7:05:03
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface SysDeptInfoService {
	/**
	 * 查询部门信息 列表
	 * @param hashMap
	 * @param
	 * @return listMap
	 */
	List<Map<String,Object>> queryDeptInfo(HashMap<String, Object> hashMap);
	/**
	 * 获取当前部门的父部门节点及本部门
	 * @param hashMap
	 * @return
	 */
	List<Map<String,Object>> queryDeptParentInfo(HashMap<String, Object> hashMap);

	/**
	 * 获取当前部门的子部门及本部门
	 * @param hashMap
	 * @return
	 */
	List<Map<String,Object>> queryDeptChildInfo(HashMap<String, Object> hashMap);
	/**
	 * 创建新部门
	 * @param hashMap
	 */
	void insertDeptInfo(HashMap<String, Object> hashMap);
	/**
	 * 查询计数
	 * @param hashMap
	 * @return
	 */
	Map<String, Object> countDeptInfo(HashMap<String, Object> hashMap);
	/**
	 * 编辑部门信息
	 * @param paramsMap
	 */
	void updateDeptInfo(HashMap<String, Object> paramsMap);

	/**
	 * 调用存储过程
	 * @param paramsMap
	 */
	void queryDeptChildNodeInfo(HashMap<String, Object> paramsMap);


	List<DeptInfo> queryDeptChildInfoPojo(HashMap<String, Object> hashMap);

	/**
	 * 查询服务商和代理商余额信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptMoneyInfo(HashMap<String, Object> paramsMap,
								   PageBounds pageBounds);

	/**
	 * 手续费=仓储费汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptMoneyCount(HashMap<String,Object> paramsMap);

	/**
	 * 转移部门操作
	 * @param hashMap
	 * @return
	 */
	Object savaOdeptToNdept(HashMap<String, Object> hashMap);

	/**
	 * 退出部门操作
	 * @param hashMap
	 * @return
	 */
	JSONObject queryQuitDeptInfo(HashMap<String, Object> hashMap);


	/**
	 * 清空部门微信信息操作
	 * @param hashMap
	 * @return
	 */
	JSONObject clearDeptWeixinInfo(HashMap<String, Object> hashMap);


	/**
	 * 清算操作
	 * @param hashMap
	 * @return
	 */
	JSONObject settlementInfo(HashMap<String, Object> hashMap);

	/**
	 * 部门余额汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptBalanceCount(HashMap<String,Object> paramsMap);

	/**
	 * 用户余额汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryUserBalanceCount(HashMap<String,Object> paramsMap);

	/**
	 * 经纪人余额汇总
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryBrokerBalanceCount(HashMap<String,Object> paramsMap);

	/**
	 * 查询部门余额信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptBalanceInfo(HashMap<String, Object> paramsMap,
									 PageBounds pageBounds);

	/**
	 * 查询用户余额信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptUserBalanceInfo(HashMap<String, Object> paramsMap,
										 PageBounds pageBounds);

	/**
	 * 查询经纪人余额信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptBrokerBalanceInfo(HashMap<String, Object> paramsMap,
										   PageBounds pageBounds);

	/**
	 * 获取选中的单位或者代理的dept_ID
	 * @param paramsMap
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptsInfo(HashMap<String,Object> paramsMap);

	void initAgentAllotInfo(HashMap<String,Object> paramsMap);


	void clearUserWeiXinInfo(HashMap<String, Object> paramsMap);

	/**
	 * 转移部门操作
	 * @param hashMap
	 * @return
	 */
	Object savaAgentToNdept(HashMap<String, Object> hashMap);


	/**
	 * 查询部门信息 列表
	 * @param hashMap
	 * @param
	 * @return listMap
	 */
	List<Map<String,Object>> queryDeptInfoByType(HashMap<String, Object> hashMap);
}
