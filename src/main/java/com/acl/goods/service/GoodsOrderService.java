package com.acl.goods.service;

import java.util.HashMap;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsOrderService
 * author:wangli
 * createDate:2017年4月25日 下午8:15:48
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsOrderService {
    /**
     * 查询订单信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsOrder(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);


    /**
     * 填写快递公司及订单编号
     *
     * @param paramsMap
     */
    void putInLogistics(HashMap<String, Object> paramsMap);

    /**
     * 改变订单状态为已发货
     *
     * @param paramsMap
     */
    void sendOutGoodsOrder(HashMap<String, Object> paramsMap);
}
