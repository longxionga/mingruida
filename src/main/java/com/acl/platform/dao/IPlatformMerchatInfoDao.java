package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformMerchatInfoDao {

    /**
     *  分页查询查询商户信息
     * @param paramsMap
     * @return
     */
    PageList<?> queryMerchantInfoPageLists(Map<String, Object> paramsMap , PageBounds pageBounds);

    /**
     *  查询商户信息
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryMerchantInfoList(Map<String, Object> paramsMap);
    /**
     *  查询商户审核状态下拉框
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> getMerchantAuditStartsCombobox(Map<String, Object> paramsMap);

    /**
     * 更新商户信息
     * @param paramsMap
     */
    void updateMerchantInfo(Map<String, Object> paramsMap);

    /**
     *  查询商户商户数量
     * @param paramsMap
     * @return
     */
    Map<String, Object> countMerchantInfo(Map<String, Object> paramsMap);

    /**
     *  商户信息导出查询
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> exportMerchantInfoReportInfo(Map<String, Object> paramsMap);

}
