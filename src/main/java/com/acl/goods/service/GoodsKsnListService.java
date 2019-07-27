package com.acl.goods.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface GoodsKsnListService {

    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryKsnListInfo(HashMap<String, Object> paramsMap,
                                   PageBounds pageBounds);

    /**
     * 插入商品信息
     *
     * @param paramsMap
     */
    void insertKsnInfo(HashMap<String, Object> paramsMap);


    /**
     * 更新商品信息
     *
     * @param paramsMap
     */
    void modKsnInfo(HashMap<String, Object> paramsMap);

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

    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryCenterKsnListInfo(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);



    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    List<Map<String,Object>> queryMerchantInfo(HashMap<String, Object> paramsMap);



    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryTradeOrderInfoPageList(HashMap<String, Object> paramsMap,
                                  PageBounds pageBounds);



    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param
     * @return
     */
    List<Map<String, Object>> queryMerchantTransfList(HashMap<String, Object> paramsMap);

    PageList<?> queryMerchantInfoPageList(HashMap<String, Object> paramsMap,
                                       PageBounds pageBounds);

    List<Map<String, Object>> giftMerchantInfoDetail(Map<String, Object> paramsMap);
}
