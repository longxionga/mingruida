package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformGoodsImageDao;
import com.acl.platform.service.IPlatformGoodsImageService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformGoodsImageServiceImpl
 *author:wangli
 *createDate:2017年2月6日 下午4:46:51
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class IPlatformGoodsImageServiceImpl implements IPlatformGoodsImageService{

	@Autowired
	private IPlatformGoodsImageDao platformGoodsImageDao;
	@Override
	public PageList<?> queryGoodsImage(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return platformGoodsImageDao.queryGoodsImage(paramsMap, pageBounds);
	}

	@Override
	public void deleteGoodsImage(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsImageDao.deleteGoodsImage(paramsMap);
	}

	@Override
	public void insertGoodsImage(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsImageDao.insertGoodsImage(paramsMap);
	}

	@Override
	public void updateGoodsImage(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGoodsImageDao.updateGoodsImage(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGoodsName(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGoodsImageDao.queryGoodsName(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGoodsUrl(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGoodsImageDao.queryGoodsUrl(paramsMap);
	}

}
