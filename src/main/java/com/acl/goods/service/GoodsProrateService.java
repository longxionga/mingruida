package com.acl.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:GoodsProrateService
 * author:wangli
 * createDate:2017年4月26日 下午5:02:16
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsProrateService {
    /**
     * 查询商品提成比率信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsProrate(HashMap<String, Object> paramsMap,
                                  PageBounds pageBounds);

    /**
     * 增加商品提成比率信息
     *
     * @param paramsMap
     */
    void insertGoodsProrate(HashMap<String, Object> paramsMap);

    /**
     * 更新商品提成比率信息
     *
     * @param paramsMap
     */
    void updateGoodsProrate(HashMap<String, Object> paramsMap);

    /**
     * 查询该商品是否已有对应该等级的提成比率
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> prorateValidate(HashMap<String, Object> paramsMap);

    /**
     * 查询等级id和name
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> getMallLevel(HashMap<String, Object> paramsMap);

}
