package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformGoodsInfoDao;
import com.acl.platform.service.IPlatformGoodsInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformGoodsInfoServiceImpl
 *author:wangli
 *createDate:2017年2月9日 下午6:36:06
 *vsersion:3.0
 *department:安创乐科技
 *description:商品属性管理
 */
@Service
@Transactional
public class IPlatformGoodsInfoServiceImpl implements IPlatformGoodsInfoService {

	@Autowired
	private IPlatformGoodsInfoDao platformGoodsInfoDao;
	@Override
	public PageList<?> queryGoodsInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return platformGoodsInfoDao.queryGoodsInfo(paramsMap, pageBounds);
	}

	@Override
	public void deleteGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsInfoDao.deleteGoodsInfo(paramsMap);
	}

	@Override
	public void insertGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsInfoDao.insertGoodsInfo(paramsMap);
	}

	@Override
	public void updateGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsInfoDao.updateGoodsInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGoodsInfoDao.queryGoodsName(paramsMap);
	}

	

}
