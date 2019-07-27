package com.acl.goods.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:GoodsStockInfoDao
 * author:wangzhe
 * createDate:2017年04月22日 下午2:44:51
 * version:3.0
 * department:安创乐科技
 * description:实物接口
 */
public interface GoodsStockInfoDao {

    /**
     * 查询库存信息
     *
     * @param paramsMap
     * @param pageBounds
     * @return
     */
    PageList<?> queryGoodsStockListInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds);


    /**
     * 更新库存信息
     *
     * @param paramsMap
     */
    void updateGoodsStockInfo(HashMap<String, Object> paramsMap);

    List<Map<String, Object>> queryGoodsSpecSum(HashMap<String, Object> paramsMap);


}
