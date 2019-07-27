package com.acl.goods.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsItemService
 * author:wangzhe
 * createDate:2017年4月26日 上午15:09:32
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsItemService {
    /**
     * 查询商品详情
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsItem(HashMap<String, Object> paramsMap,
                               PageBounds pageBounds);

    /**
     * 增加商品详情
     *
     * @param paramsMap
     */
    void insertGoodsItem(HashMap<String, Object> paramsMap);

    /**
     * 更新商品详情
     *
     * @param paramsMap
     */
    void updateGoodsItem(HashMap<String, Object> paramsMap);

    /**
     * 查询该商品是否已经有详情
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> itemValidate(HashMap<String, Object> paramsMap);

    /**
     * 查询商品id和名称
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryItemGoodsName(HashMap<String, Object> paramsMap);
}
