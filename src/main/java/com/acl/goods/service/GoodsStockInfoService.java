package com.acl.goods.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsStockInfoService
 * author:wangzhe
 * createDate:2017年04月22日 下午03:00:18
 * version:3.0
 * department:安创乐科技
 * description:
 */
public interface GoodsStockInfoService {

    /**
     * 查询产品信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsStockListInfo(HashMap<String, Object> paramsMap,
                                        PageBounds pageBounds);


    /**
     * 更新商品信息
     *
     * @param paramsMap
     */
    void updateGoodsStockInfo(HashMap<String, Object> paramsMap);


    void initStockToRedis() throws Exception;


    //void initSignStockToRedis(GoodsStock stock) throws Exception;

    //void removeStockToRedis(GoodsStock stock,int type) throws Exception;


    //GoodsStock selectStockToMysql(Long goodsId);

    List<Map<String, Object>> queryGoodsSpecSum(HashMap<String, Object> paramsMap);


}
