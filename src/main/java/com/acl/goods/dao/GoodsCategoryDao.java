package com.acl.goods.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * className:GoodCategoryDao
 * author:wangli
 * createDate:2017年4月22日 上午10:02:19
 * version:3.0
 * department:安创乐科技
 * description:商品类目管理
 */
public interface GoodsCategoryDao {
    /**
     * 查询商品类目信息
     *
     * @param hashMap
     * @param
     * @return
     */
    List<Map<String, Object>> queryGoodsCategory(HashMap<String, Object> hashMap);

    /**
     * 增加商品类目信息
     *
     * @param paramsMap
     */
    void insertGoodsCategory(HashMap<String, Object> paramsMap);

    /**
     * 修改商品类目信息
     *
     * @param paramsMap
     */
    void updateGoodsCategory(HashMap<String, Object> paramsMap);

    /**
     * 验证商品类目名称是否重复
     *
     * @param hashMap
     * @param
     * @return
     */
    List<Map<String, Object>> categoryValidate(HashMap<String, Object> hashMap);

    /**
     * 查询商品类目id和名称
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> getGoodsCategory(HashMap<String, Object> paramsMap);
}
