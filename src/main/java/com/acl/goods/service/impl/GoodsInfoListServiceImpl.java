package com.acl.goods.service.impl;

import com.acl.goods.dao.GoodsInfoListDao;
import com.acl.goods.service.GoodsInfoListService;


import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * className:GoodsInfoListServiceImpl
 * author:wangzhe
 * createDate:2017年04月22日 下午03:00:18
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Service
@Transactional
public class GoodsInfoListServiceImpl implements GoodsInfoListService {

    @Autowired
    private GoodsInfoListDao goodsInfoListDao;

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
    public PageList<?> queryGoodsListInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
        return goodsInfoListDao.queryGoodsListInfo(paramsMap, pageBounds);
    }

    @Override
    public void insertGoodsInfo(HashMap<String, Object> paramsMap) {
        goodsInfoListDao.insertGoodsInfo(paramsMap);
    }

    @Override
    public void modGoodsInfo(HashMap<String, Object> paramsMap) {
        goodsInfoListDao.updateGoodsInfo(paramsMap);

    }

    @Override
    public void insertGoodsDeptInfo(HashMap<String, Object> paramsMap) {
        goodsInfoListDao.insertGoodsDeptInfo(paramsMap);
    }

    @Override
    public void deleteGoodsDept(HashMap<String, Object> paramsMap) {
        goodsInfoListDao.deleteGoodsDept(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsDeptInfo(HashMap<String, Object> paramsMap) {
        return goodsInfoListDao.queryGoodsDeptInfo(paramsMap);
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

        List<Map<String, Object>> list = goodsInfoListDao.queryIsValidationInfo(paramsMap);
        String isValidation = null;

        if (list.size() > 0) {
            isValidation = list.get(0).get("is_validation").toString();
        }

        return isValidation;
    }

    @Override
    public List<Map<String, Object>> selectGoodsList(HashMap<String, Object> paramsMap) {
        return goodsInfoListDao.selectGoodsList(paramsMap);
    }

    @Override
    public List<Map<String, Object>> queryGoodsValidationInfo(HashMap<String, Object> paramsMap) {
        return goodsInfoListDao.queryGoodsValidationInfo(paramsMap);
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


}
