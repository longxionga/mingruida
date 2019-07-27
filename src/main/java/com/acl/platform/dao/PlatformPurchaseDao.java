package com.acl.platform.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 2017/11/8.
 */
public interface PlatformPurchaseDao {
    /**
     * 查询平台商品信息进行下单操作
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsInfoForSettle(HashMap<String, Object> paramsMap,
                               PageBounds pageBounds);

    /**
     * 查询服务商下单信息
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryPurchase(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);
    /**
     * 服务商下单
     * @param paramsMap
     */
    void insertPurchase(HashMap<String, Object> paramsMap);

    /**
     * 扣减平台可预订库存
     * @param paramsMap
     */
    void substractStock(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGoodsList(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGoodsSpec(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGoodsStock(HashMap<String, Object> paramsMap);
}
