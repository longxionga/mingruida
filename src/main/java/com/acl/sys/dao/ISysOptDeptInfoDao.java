package com.acl.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.DeptInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:ISysOptDeptInfoDao
 *author:huangs
 *createDate:2016年8月10日 下午6:56:29
 *vsersion:3.0
 *department:安创乐科技
 *description:部门管理
 */
public interface ISysOptDeptInfoDao {
    /**
     * 获取
     */
    List<Map<String,Object>> queryDeptInfo(HashMap<String, Object> hashMap);

    /*
     * 插入新的数据
     */
    void insertDeptInfo(HashMap<String, Object> hashMap);

    /*
     * 计数
     */
    Map<String, Object> countDeptInfo(HashMap<String, Object> hashMap);

    /*
     * 获取制定子节点的所有父节点
     */
    List<Map<String, Object>> queryDeptParentInfo(HashMap<String, Object> hashMap);
    
    /*
     * 通过dept_id获取子节点的所有父节点
     */
    List<Map<String, Object>> queryOdeptOrNdeptInfo(HashMap<String, Object> hashMap);

    /*
     * 获取指定节点的所有子节点
     */
    List<Map<String, Object>> queryDeptChildInfo(HashMap<String, Object> hashMap);
    List<DeptInfo> queryDeptChildInfoPojo(HashMap<String, Object> hashMap);

    /**
     * 编辑信息
     */
    void updateDeptInfo(HashMap<String, Object> hashMap);
    
    /**
     * 调用存储过程
     * @param hashMap
     */
    void queryDeptChildNodeInfo(HashMap<String, Object> hashMap);
    
    /**
     * 调用存储过程
     * @param hashMap
     */
    void saveOdeptToNdeptInfo(HashMap<String, Object> hashMap);
    
	/**
	 * 查询服务商和代理商余额信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptMoneyInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 *查询服务商和代理商报表汇总信息
	 * @param map
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptMoneySum(HashMap<String,Object> map);
	
	
	public Object queryQuitDeptInfo(HashMap<String, Object> paramsMap);
	
	public Object settlementInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 会员OR代理余额查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptBalanceInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 用户余额
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptUserBalanceInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 * 经纪人余额
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryDeptBrokerBalanceInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	/**
	 *查询服务商和代理商余额总数
	 * @param map
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptBalanceCount(HashMap<String,Object> map);
	
	/**
	 *用户余额总数
	 * @param map
	 * @return
	 */
	List<HashMap<String,Object>> queryUserBalanceCount(HashMap<String,Object> map);
	
	/**
	 *经纪人余额总数
	 * @param map
	 * @return
	 */
	List<HashMap<String,Object>> queryBrokerBalanceCount(HashMap<String,Object> map);
	
	/**
	 * 获取选中的单位或者代理的dept_ID
	 * @param map
	 * @return
	 */
	List<HashMap<String,Object>> queryDeptsInfo(HashMap<String,Object> map);
	
	/**
	 *查询导出经纪人信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryBrokerResultExport(Map<String,Object> map);
	
	/**
	 *查询导出用户信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryUserResultExport(Map<String,Object> map);
	
	/**
	 * 修改用户手机号
	 * @param paramsMap
	 */
	void updUserMobileInfo(HashMap<String, Object> paramsMap);
	
	/**
	 * 修改经纪人手机号
	 * @param paramsMap
	 */
	void updBrokerMobileInfo(HashMap<String, Object> paramsMap);

	/*
	 * 通过dept_name 查询部门信息
	 */
	List<Map<String, Object>> findDeptInfoByDeptName(HashMap<String, Object> hashMap);

}
