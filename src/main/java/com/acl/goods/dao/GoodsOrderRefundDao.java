package com.acl.goods.dao;

import java.util.HashMap;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsOrderRefundDao
 * author:wangli
 * createDate:2017年4月25日 下午3:46:07
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsOrderRefundDao {
    /**
     * 查询退货订单信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsOrderRefund(HashMap<String, Object> paramsMap,
                                      PageBounds pageBounds);

    /**
     * 审核退货订单
     *
     * @param paramsMap
     */
    void updateGoodsOrderRefund(HashMap<String, Object> paramsMap);


    /**
     *
     **/
    Map<String, Object> getGoodsOrderRefund(Map<String, Object> paramsMap);

}
