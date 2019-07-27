package com.acl.goods.service.impl;


import com.acl.goods.dao.GoodsStockInfoDao;
import com.acl.goods.service.GoodsStockInfoService;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * className:PlatformGzpInfoServiceImpl
 * author:wangzhe
 * createDate:2016年8月12日 上午10:00:30
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class GoodsStockInfoServiceImpl implements GoodsStockInfoService {

    @Autowired
    private GoodsStockInfoDao goodsStockInfoDao;

//	@Autowired
//	private GoodsRedisService goodsRedisService;
//
//	@Autowired
//	private GoodsStockDao goodsStockDao;

    @Override
    public PageList<?> queryGoodsStockListInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsStockInfoDao.queryGoodsStockListInfo(paramsMap, pageBounds);
    }

    @Override
    public void updateGoodsStockInfo(HashMap<String, Object> paramsMap) {
        goodsStockInfoDao.updateGoodsStockInfo(paramsMap);

    }

    @Override
    public void initStockToRedis() throws Exception {
        //goodsRedisService.initGoodsStockToRedis();
    }

    @Override
    public List<Map<String, Object>> queryGoodsSpecSum(HashMap<String, Object> paramsMap) {
        return goodsStockInfoDao.queryGoodsSpecSum(paramsMap);
    }

//	@Override
//	public void initSignStockToRedis(GoodsStock stock) throws Exception {
//		//goodsRedisService.initGoodsStockToRedis(stock);
//	}

//	@Override
//	public void removeStockToRedis(GoodsStock stock,int type) throws Exception {
//
//		if(type ==1)
//		{
//			//通过服务商ID、商品ID、SKUID更新redis
//			goodsRedisService.removeGoodsStockSkuToRedis(stock.getSettleId(),stock.getGoodsId(),stock.getSkuId());
//		}
//		if(type ==2)
//		{
//			//服务商ID,商品ID
//			goodsRedisService.removeGoodsStockToRedis(stock.getSettleId(),stock.getGoodsId());
//		}
//		if(type ==3)
//		{
//			//服务商ID
//			goodsRedisService.removeGoodsStockToRedis(stock.getSettleId());
//		}
//
//	}


//	@Override
//	public GoodsStock selectStockToMysql(Long goodsId) {
//		return goodsStockDao.get(goodsId);
//	}

}
