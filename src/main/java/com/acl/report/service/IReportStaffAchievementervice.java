package com.acl.report.service;

import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IReportStaffAchievementervice {
    PageList<?> queryStaffAchievementPageList(HashMap<String, Object> paramsMap,
                                        PageBounds pageBounds);

    /**
     *  业绩明细分页查询
     * @param
     */
        PageList<?> queryStandardAchievementPageList(HashMap<String, Object> paramsMap,
                                                     PageBounds pageBounds, LoginSession loginSession);


    /**
     *  业绩汇总分页查询
     * @param
     */
    PageList<?> queryTotalStandardAchievementPageList(HashMap<String, Object> paramsMap,
                                                 PageBounds pageBounds, LoginSession loginSession);

    /**
     *  业绩明细分页查询
     * @param
     */
    PageList<?> queryStandardAchievementDetailPageList(HashMap<String, Object> paramsMap,
                                                 PageBounds pageBounds, LoginSession loginSession) throws ParseException;

    /**
     * 查询符合条件的商户达标明细
     * @param hashMap
     * @return
     */
    Map<String, Object> countMerchantInfo(HashMap<String, Object> hashMap,LoginSession loginSession);

    /**
     * 商户达标导出
     * @param map
     * @return
     */
    List<Map<String, Object>> exportMerchantReportInfo(Map<String,Object> map,LoginSession loginSession);
}
