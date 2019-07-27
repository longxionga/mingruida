package com.acl.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.goods.dao.GoodsKsnListDao;
import com.acl.goods.service.GoodsKsnListService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

@Service
@Transactional
public class GoodsKsnListServiceImpl implements GoodsKsnListService {

    @Autowired
    private GoodsKsnListDao goodsKsnListDao;

//	@Autowired
//	private GoodsRedisService goodsRedisService;
//
//	@Autowired
//	private GoodsDao goodsDao;
//
//	@Autowired
//	private  GoodsSpecDao goodsSpecDao;
//
//	@Autowired
//	private  GoodsParamDao goodsParamDao;
//
//	@Autowired
//	private  GoodsProrateDao goodsProrateDao;

    @Override
    public PageList<?> queryKsnListInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsKsnListDao.queryKsnListInfo(paramsMap, pageBounds);
    }

    @Override
    public void insertKsnInfo(HashMap<String, Object> paramsMap) {
        goodsKsnListDao.insertKsnInfo(paramsMap);
    }

    @Override
    public void modKsnInfo(HashMap<String, Object> paramsMap) {
        goodsKsnListDao.updateKsnInfo(paramsMap);

    }

    @Override
    public void insertGoodsDeptInfo(HashMap<String, Object> paramsMap) {
        goodsKsnListDao.insertGoodsDeptInfo(paramsMap);
    }

    @Override
    public void deleteGoodsDept(HashMap<String, Object> paramsMap) {
        goodsKsnListDao.deleteGoodsDept(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsDeptInfo(HashMap<String, Object> paramsMap) {
        return goodsKsnListDao.queryGoodsDeptInfo(paramsMap);
    }

//	@Override
//	public void saveGoodsInfo(Goods goods) throws Exception {
//		goodsDao.save(goods);
//	}
//
//	@Override
//	public Goods getGoodsInfo(Long goodsId) throws Exception {
//		return goodsDao.get(goodsId);
//	}
//
//	@Override
//	public void saveGoodsToRedis(Long goodsId) throws Exception {
//		goodsRedisService.initGoodsToRedis(goodsId);
//	}
//
//	@Override
//	public void initGoodsToRedis() throws Exception {
//		goodsRedisService.initGoodsToRedis();
//	}
//
//	@Override
//	public void initStockToRedis() throws Exception {
//		goodsRedisService.initGoodsStockToRedis();
//	}

    @Override
    public String getIsValidation(HashMap<String, Object> paramsMap) {

        List<Map<String, Object>> list = goodsKsnListDao.queryIsValidationInfo(paramsMap);
        String isValidation = null;

        if (list.size() > 0) {
            isValidation = list.get(0).get("is_validation").toString();
        }

        return isValidation;
    }

    @Override
    public List<Map<String, Object>> selectGoodsList(HashMap<String, Object> paramsMap) {
        return goodsKsnListDao.selectGoodsList(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsValidationInfo(HashMap<String, Object> paramsMap) {
        return goodsKsnListDao.queryGoodsValidationInfo(paramsMap);
    }

    @Override
    public void removeGoodsToRedis(Long goodsId) throws Exception {
        //goodsRedisService.removeGoodsToRedis(goodsId);
    }

    @Override
    public void saveGoodsSpecToRedis(Long goodsId) {
//			// 规格集合
//			GoodsSpec spec = new GoodsSpec();
//			spec.setGoodsId(goodsId);
//			spec.setIsUse(CommonEnums.YES.getStrCode());
//			List<GoodsSpec> goodsSpecList = goodsSpecDao.get(spec);
//			// 构建规格集合
//			if (CollectionUtils.isNotEmpty(goodsSpecList)) {
//				goodsSpecDao.saveToRedis(goodsId, goodsSpecList);
//			}
    }

    @Override
    public void saveGoodsParamToRedis(Long goodsId) {
        // 参数集合
//		GoodsParam param = new GoodsParam();
//		param.setGoodsId(goodsId);
//		param.setIsUse(CommonEnums.YES.getStrCode());
//		List<GoodsParam> goodsParamList = goodsParamDao.get(param);
//		// 构建商品参数集合
//		if (CollectionUtils.isNotEmpty(goodsParamList)) {
//			goodsParamDao.saveToRedis(goodsId, goodsParamList);
//		}
    }

    @Override
    public void saveGoodsProrateToRedis(Long goodsId) {
        // 提成比率集合
//		GoodsProrate prorate = new GoodsProrate();
//		prorate.setGoodsId(goodsId);
//		prorate.setIsUse(CommonEnums.YES.getStrCode());
//		List<GoodsProrate> goodsProrateList = goodsProrateDao.get(prorate);
//		// 构建商品提成比率集合
//		if (CollectionUtils.isNotEmpty(goodsProrateList)) {
//			goodsProrateDao.saveToRedis(goodsId, goodsProrateList);
//		}
    }

    @Override
    public PageList<?> queryCenterKsnListInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsKsnListDao.queryCenterKsnListInfo(paramsMap, pageBounds);
    }

    @Override
    public List<Map<String,Object>> queryMerchantInfo(HashMap<String, Object> paramsMap) {
        return goodsKsnListDao.queryMerchantInfo(paramsMap);
    }

    @Override
    public PageList<?> queryTradeOrderInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsKsnListDao.queryTradeOrderInfoPageList(paramsMap, pageBounds);
    }

    @Override
    public List<Map<String, Object>> queryMerchantTransfList(HashMap<String, Object> paramsMap) {
        return goodsKsnListDao.queryMerchantTransfList(paramsMap);
    }

    @Override
    public PageList<?> queryMerchantInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsKsnListDao.queryMerchantInfoPageList(paramsMap, pageBounds);
    }

    @Override
    public List<Map<String, Object>> giftMerchantInfoDetail(Map<String, Object> paramsMap) {
        return goodsKsnListDao.giftMerchantInfoDetail(paramsMap);
    }
}
