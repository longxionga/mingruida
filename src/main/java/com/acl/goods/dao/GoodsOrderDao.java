package com.acl.goods.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsOrderDao
 * author:wangli
 * createDate:2017年4月25日 下午8:15:18
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsOrderDao {
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
     * 修改订单
     *
     * @param paramsMap
     */
    void updateGoodsOrder(HashMap<String, Object> paramsMap);

    /**
     * 添加商品状态更改记录
     *
     * @param paramsMap
     */
    void insertOrderStatus(HashMap<String, Object> paramsMap);

    /**
     * 查询该订单的用户id
     *
     * @param paramsMap
     */
    List<Map<String, Object>> queryUserIdByOrderId(HashMap<String, Object> hashMap);
}
