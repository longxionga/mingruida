package com.acl.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.GoodsSpec;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsSpecService
 * author:wangli
 * createDate:2017年4月25日 下午7:09:12
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsSpecService {
    /**
     * 查询商品属性信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsSpec(HashMap<String, Object> paramsMap,
                               PageBounds pageBounds);

    /**
     * 增加商品属性信息
     *
     * @param goodsSpec
     */
    void insertKsnSpec(GoodsSpec goodsSpec);

    /**
     * 更新商品属性信息
     *
     * @param paramsMap
     */
    void updateKsnSpec(HashMap<String, Object> paramsMap);

    /**
     * 查询该商品是否已经有同名属性
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> specValidate(HashMap<String, Object> paramsMap);

	void updateGoodsSpec(HashMap<String, Object> paramsMap);

	void insertGoodsSpec(GoodsSpec goodsSpec);



}
