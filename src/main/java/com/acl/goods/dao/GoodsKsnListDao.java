package com.acl.goods.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsKsnListDao {

    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryKsnListInfo(HashMap<String, Object> paramsMap,
                                   PageBounds pageBounds);

    /**
     * 插入模式一信息
     *
     * @param paramsMap
     */
    void insertKsnInfo(HashMap<String, Object> paramsMap);

    /**
     * 更新模式一信息
     *
     * @param paramsMap
     */
    void updateKsnInfo(HashMap<String, Object> paramsMap);


    /**
     * 插入模式一部门中间表
     *
     * @param paramsMap
     */
    void insertGoodsDeptInfo(HashMap<String, Object> paramsMap);


    /**
     * 删除商品部门信息
     *
     * @param paramsMap
     */
    void deleteGoodsDept(HashMap<String, Object> paramsMap);

    /**
     * 查询商品和部门信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryGoodsDeptInfo(HashMap<String, Object> paramsMap);


    /**
     * 查询商品验证信息
     *
     * @param paramsMap
     * @return
     */
    List<Map<String, Object>> queryIsValidationInfo(HashMap<String, Object> paramsMap);

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



    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryCenterKsnListInfo(HashMap<String, Object> paramsMap,
                                 PageBounds pageBounds);

    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    List<Map<String,Object>> queryMerchantInfo(HashMap<String, Object> paramsMap);

    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryTradeOrderInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds);

    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    List<Map<String, Object>> queryMerchantTransfList(HashMap<String, Object> paramsMap);


    PageList<?> queryMerchantInfoPageList(HashMap<String, Object> paramsMap,
                                    PageBounds pageBounds);


    List<Map<String, Object>> giftMerchantInfoDetail(Map<String, Object> paramsMap);

    /**
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    List<Map<String, Object>> queryMerchantInfoList(Map<String, Object> paramsMap);


    /**
     * 查询模式一信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    List<Map<String, Object>> queryTradeOrderInfoList(Map<String, Object> paramsMap);
}
