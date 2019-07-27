package com.acl.platform.service.impl;


import com.acl.platform.dao.PlatformPurchaseDao;
import com.acl.platform.service.PlatformPurchaseService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/11/8.
 */
@Service
public class PlatformPurchaseServiceImpl implements PlatformPurchaseService {
    @Autowired
    private PlatformPurchaseDao platformPurchaseDao;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public PageList<?> queryGoodsInfoForSettle(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformPurchaseDao.queryGoodsInfoForSettle(paramsMap,pageBounds);
    }

    @Override
    public PageList<?> queryPurchase(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformPurchaseDao.queryPurchase(paramsMap,pageBounds);
    }

    @Override
    public void insertPurchase(HashMap<String, Object> paramsMap) {
        /*
        1.增加下单记录
        2.扣减平台可预订库存
         */
        paramsMap.put("create_time", sdf.format(new java.util.Date()));
        platformPurchaseDao.insertPurchase(paramsMap);
        platformPurchaseDao.substractStock(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsList(HashMap<String, Object> paramsMap) {
        return platformPurchaseDao.queryGoodsList(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsSpec(HashMap<String, Object> paramsMap) {
        return platformPurchaseDao.queryGoodsSpec(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsStock(HashMap<String, Object> paramsMap) {
        return platformPurchaseDao.queryGoodsStock(paramsMap);
    }
}
