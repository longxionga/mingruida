package com.acl.goods.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsSettleAllotDao
 * author:wangzhe
 * createDate:2017年4月26日 下午05:09:17
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsSettleAllotDao {
    /**
     * 查询商品详情
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> querySettleAllot(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);

    /**
     * 新增商品详情
     *
     * @param paramsMap
     */
    void insertSettleAllot(HashMap<String, Object> paramsMap);

    /**
     * 更新商品详情
     *
     * @param paramsMap
     */
    void updateSettleAllot(HashMap<String, Object> paramsMap);

    /**
     * 查询该商品是否已经有详情
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
     * 查询服务商id和名称
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> querySettleAllotList(HashMap<String, Object> paramsMap);
}
