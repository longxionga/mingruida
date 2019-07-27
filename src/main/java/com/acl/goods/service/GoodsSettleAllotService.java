package com.acl.goods.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsSettleAllotService
 * author:wangzhe
 * createDate:2017年4月26日 下午03:09:32
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsSettleAllotService {
    /**
     * 查询服务商比例
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querySettleAllot(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);

    /**
     * 增加服务商比例
     *
     * @param paramsMap
     */
    void insertSettleAllot(HashMap<String, Object> paramsMap);

    /**
     * 更新比例详情
     *
     * @param paramsMap
     */
    void updateGSettleAllot(HashMap<String, Object> paramsMap);

    /**
     * 查询该服务商是否已经有比例
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> settleValidate(HashMap<String, Object> paramsMap);

    /**
     * 查询服务商id和名称
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> querySettleName(HashMap<String, Object> paramsMap);

    /**
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> querySettleAllotList(HashMap<String, Object> paramsMap);

    /**
     * @param paramsMap
     */
    void setSettleAllotToRedis(HashMap<String, Object> paramsMap);
}
