package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportStaffAchievementDao {

    /**
     * 乐刷刷宝直营员工流水提成报表查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryshuabaoStaffAchievementPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 乐刷刷宝查询所有组员流水
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryshuabaoStaffPaymentFlowSum(Map<String, Object> paramsMap);

    /**
     * 直营员工统计达标业绩报表
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryStandardAchievementPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 海科查询员工后台机具数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantTotalSum(Map<String, Object> paramsMap);

    /**
     * 海科查询员工后台机具未激活数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantWJHSum(Map<String, Object> paramsMap);

    /**
     * 海科查询员工后台机具已绑定数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantJHSum(Map<String, Object> paramsMap);


    /**
     * 海科查询员工后台机具已达标数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantDBSum(Map<String, Object> paramsMap);

    /**
     * 海科查询员工后台机具已达标数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantGSDBSum(Map<String, Object> paramsMap);

    /**
     * 海科查询员工后台机具已达标数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantGSDBSum_30(Map<String, Object> paramsMap);

    /**
     * 海科查询员工上月未达标本月已达标数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantSHYGSDBSum(Map<String, Object> paramsMap);

    /**
     * 员工业绩详细信息查询
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryStandardAchievementDetailPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 海科查询员工上月未达标本月已达标数量
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> merchantTradeCount(Map<String, Object> paramsMap);

    /**
     * 商户达标信息数量统计
     * @param paramsMap
     * @return
     */
    Map<String, Object> countMerchantInfo(Map<String, Object> paramsMap);

    /**
     *查询商户达标信息
     * @param map
     * @return
     */
    List<Map<String,Object>> exportMerchantReportInfo(Map<String,Object> map);

}
