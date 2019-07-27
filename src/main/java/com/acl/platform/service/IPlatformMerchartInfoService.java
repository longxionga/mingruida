package com.acl.platform.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPlatformMerchartInfoService {
    /**
     * 分页查询商户信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMerchantInfoPageLists(HashMap<String, Object> paramsMap,
                                         PageBounds pageBounds);

    /**
     * 查询商户信息
     * @param hashMap
     * @return
     */
    List<Map<String, Object>> queryMerchantInfoList(HashMap<String, Object> hashMap);

    /**
     * 查询商户审核状态下拉框
     * @param hashMap
     * @return
     */
    List<Map<String, Object>> getMerchantAuditStartsCombobox(HashMap<String, Object> hashMap);


    /**
     * 查询符合条件的商户达标明细
     * @param hashMap
     * @return
     */
    Map<String, Object> countMerchantInfo(HashMap<String, Object> hashMap);

    /**
     * 商户达标导出
     * @param map
     * @return
     */
    List<Map<String, Object>> exportMerchantInfoReportInfo(Map<String,Object> map);



}
