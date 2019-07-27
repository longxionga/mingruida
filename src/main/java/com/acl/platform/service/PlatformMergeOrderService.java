package com.acl.platform.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/9/21.
 */
public interface PlatformMergeOrderService {
    /**
     * 查询用户总共代发货的订单数
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryMergeOrder(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);


    List<Map<String, Object>> queryOrderDetail(HashMap<String, Object> paramsMap);

    /**
     * 合并订单
     * @param ids
     */
    int mergeOrder(String ids);


    /**
     * 查询用户总共代发货的订单数
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> tradeOrder(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);
}
