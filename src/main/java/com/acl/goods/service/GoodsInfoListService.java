package com.acl.goods.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsInfoListService
 * author:wangzhe
 * createDate:2017年04月22日 下午03:00:18
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsInfoListService {

    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsListInfo(HashMap<String, Object> paramsMap,
                                   PageBounds pageBounds);

    /**
     * 插入商品信息
     *
     * @param paramsMap
     */
    void insertGoodsInfo(HashMap<String, Object> paramsMap);


    /**
     * 更新商品信息
     *
     * @param paramsMap
     */
    void modGoodsInfo(HashMap<String, Object> paramsMap);

    /**
     * 插入商品库存关系表
     *
     * @param paramsMap
     */
    void insertGoodsDeptInfo(HashMap<String, Object> paramsMap);

    /**
     * 删除商品部门信息表
     *
     * @param paramsMap
     */
    void deleteGoodsDept(HashMap<String, Object> paramsMap);


    /**
     * 查询商品与部门信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsDeptInfo(HashMap<String, Object> paramsMap);

    //void saveGoodsInfo(Goods goods) throws Exception;

    //Goods getGoodsInfo(Long goodsId) throws Exception;

//    void saveGoodsToRedis(Long goodsId) throws Exception;
//
//    void initGoodsToRedis() throws Exception;
//
//    void initStockToRedis() throws Exception;

    String getIsValidation(HashMap<String, Object> paramsMap);

    /**
     * 查询商品信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> selectGoodsList(HashMap<String, Object> paramsMap);

    /**
     * 查询商品信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsValidationInfo(HashMap<String, Object> paramsMap);

    void removeGoodsToRedis(Long goodsId) throws Exception;

    void saveGoodsSpecToRedis(Long goodsId);

    void saveGoodsParamToRedis(Long goodsId);

    void saveGoodsProrateToRedis(Long goodsId);


}
