package com.acl.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsParamService
 * author:wangli
 * createDate:2017年4月25日 上午9:09:32
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsParamService {
    /**
     * 查询商品属性信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsParam(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);

    /**
     * 增加商品属性信息
     *
     * @param paramsMap
     */
    void insertGoodsParam(HashMap<String, Object> paramsMap);

    /**
     * 更新商品属性信息
     *
     * @param paramsMap
     */
    void updateGoodsParam(HashMap<String, Object> paramsMap);

    /**
     * 查询该商品是否已经有同名属性
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> paramValidate(HashMap<String, Object> paramsMap);

    /**
     * 查询商品id和名称
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap);
}
