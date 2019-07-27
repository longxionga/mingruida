package com.acl.goods.service;

import java.util.HashMap;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsOrderRefundService
 * author:wangli
 * createDate:2017年4月25日 下午3:46:53
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsOrderRefundService {
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
     *
     同意流程:
     修改 t_front_mall_order_refund   status=2,reject_status=2,reject_time=now,update_time=now
     修改 t_front_mall_order          order_status=51,update_time=now
     增加 t_front_mall_order_status   order_status=51,create_time=now
     增加 t_front_user_profitloss  use_type=27
     更新mysql 用户余额
     删除redis order_id: {0}
     更新redis余额
     * 审核通过退货订单
     * @param paramsMap
     */
    //void agreeGoodsOrderRefund(HashMap<String, Object> paramsMap) throws Exception;

    /**
     *  拒绝流程:
     修改 t_front_mall_order_refund   status=2,reject_status=3,reject_time=now,update_time=now
     修改 t_front_mall_order          order_status=51,update_time=now,assign_status=1
     增加 t_front_mall_order_status   order_status=51,create_time=now
     * 审核不通过退货订单
     * @param paramsMap
     */
    //void disAgreeGoodsOrderRefund(HashMap<String, Object> paramsMap);


    /**
     * 更新 退货表数据
     *
     * @param paramsMap
     */
    void updGoodsOrderRefund(HashMap<String, Object> paramsMap);


}
