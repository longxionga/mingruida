package com.acl.goods.dao;

import java.util.HashMap;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsImageDao
 * author:wangli
 * createDate:2017年4月26日 下午2:12:58
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsImageDao {
    /**
     * 查询商品图片信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsImage(HashMap<String, Object> paramsMap,
                                PageBounds pageBounds);

    /**
     * 增加商品图片信息
     *
     * @param paramsMap
     */
    void insertGoodsImage(HashMap<String, Object> paramsMap);

    /**
     * 更新商品图片信息
     *
     * @param paramsMap
     */
    void updateGoodsImage(HashMap<String, Object> paramsMap);
}
