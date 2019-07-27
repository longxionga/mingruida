package com.acl.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.component.SystemConfig;
import com.acl.goods.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.goods.dao.GoodsCategoryDao;

/**
 *className:IGoodsCategoryServiceImpl
 *author:wangli
 *createDate:2017年4月22日 上午10:03:58
 *version:3.0
 *department:安创乐科技
 *description:商品类目管理
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	@Override
	public List<Map<String,Object>> queryGoodsCategory(HashMap<String, Object> hashMap) {
		// 查询商品类目信息
		return goodsCategoryDao.queryGoodsCategory(hashMap);
	}

	@Override
	public void insertGoodsCategory(HashMap<String, Object> paramsMap) {
		// 新增商品类目
		goodsCategoryDao.insertGoodsCategory(paramsMap);
	}

	@Override
	public void updateGoodsCategory(HashMap<String, Object> paramsMap) {
		// 修改商品类目

		goodsCategoryDao.updateGoodsCategory(paramsMap);
	}

	@Override
	public List<Map<String, Object>> categoryValidate(HashMap<String, Object> hashMap) {
		// 验证商品类目名称是否重复
		return goodsCategoryDao.categoryValidate(hashMap);
	}

	@Override
	public List<Map<String, Object>> getGoodsCategory(HashMap<String, Object> paramsMap) {
		return goodsCategoryDao.getGoodsCategory(paramsMap);
	}
	
}
