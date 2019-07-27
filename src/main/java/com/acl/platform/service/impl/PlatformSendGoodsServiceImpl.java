package com.acl.platform.service.impl;

import com.acl.platform.dao.PlatformSendGoodsDao;
import com.acl.platform.service.PlatformSendGoodsService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 2017/11/9.
 */
@Service
public class PlatformSendGoodsServiceImpl implements PlatformSendGoodsService {
    @Autowired
    private PlatformSendGoodsDao platformSendGoodsDao;

    @Override
    public PageList<?> querySendGoods(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return platformSendGoodsDao.querySendGoods(paramsMap,pageBounds);
    }

    @Override
    public void agree(HashMap<String, Object> paramsMap) {
        /*
        1.更改订单状态为已发货
        2.增加该服务商该规格商品下的库存
        3.减少平台的库存总量
         */
        paramsMap.put("status","1");
        paramsMap.put("update_time", new Date());
        platformSendGoodsDao.sendGoods(paramsMap);
        List<Map<String, Object>> listPurchase=platformSendGoodsDao.queryPurchaseInfo(paramsMap);
        paramsMap.put("goods_id",listPurchase.get(0).get("goods_id"));
        paramsMap.put("spec_id",listPurchase.get(0).get("spec_id"));
        paramsMap.put("settle_id",listPurchase.get(0).get("settle_id"));
        //判断该商品是否已有库存记录，如果已有库存记录 则直接增加库存数量 如果没有则增加记录
        List<Map<String, Object>> list=platformSendGoodsDao.querySettleStock(paramsMap);
        paramsMap.put("create_time", new Date());
        if(list.size()>0){
            paramsMap.put("num",listPurchase.get(0).get("num").toString());
            platformSendGoodsDao.addSettleStock(paramsMap);
        }else {
            paramsMap.put("settle_id",listPurchase.get(0).get("settle_id").toString());
            paramsMap.put("num",listPurchase.get(0).get("num").toString());
            platformSendGoodsDao.insertSettleStock(paramsMap);
        }
        platformSendGoodsDao.substractStock(paramsMap);
    }

    @Override
    public void disagree(HashMap<String, Object> paramsMap) {
        /*
        1.更改订单状态为 审核拒绝
        2.增加平台的可订货库存
         */
        paramsMap.put("status","2");
        paramsMap.put("update_time", new Date());
        platformSendGoodsDao.sendGoods(paramsMap);
        List<Map<String, Object>> listPurchase=platformSendGoodsDao.queryPurchaseInfo(paramsMap);
        paramsMap.put("goods_id",listPurchase.get(0).get("goods_id"));
        paramsMap.put("spec_id",listPurchase.get(0).get("spec_id"));
        paramsMap.put("num",listPurchase.get(0).get("num"));
        platformSendGoodsDao.addStock(paramsMap);
    }
}
