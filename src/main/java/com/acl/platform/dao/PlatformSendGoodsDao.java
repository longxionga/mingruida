package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 2017/11/9.
 */
public interface PlatformSendGoodsDao {
    /**
     * 查询服务商下单信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querySendGoods(HashMap<String, Object> paramsMap,
                              PageBounds pageBounds);

    //查询该服务商是否已有该规格商品的库存信息
    List<Map<String, Object>> querySettleStock(HashMap<String, Object> paramsMap);
    List<Map<String, Object>>   queryPurchaseInfo (HashMap<String, Object> paramsMap);
    /**
     * 审核改变订单状态
     * @param paramsMap
     */
    void sendGoods(HashMap<String, Object> paramsMap);

    /**
     * 增加供货商库存信息
     * @param paramsMap
     */
    void insertSettleStock(HashMap<String, Object> paramsMap);

    /**
     * 增加供货商库存
     * @param paramsMap
     */
    void addSettleStock(HashMap<String, Object> paramsMap);

    /**
     * 审核不通过  增加平台的可订库存
     * @param paramsMap
     */
    void addStock(HashMap<String, Object> paramsMap);

    /**
     * 审核通过  减少平台的库存总量
     * @param paramsMap
     */
    void substractStock(HashMap<String, Object> paramsMap);
}
