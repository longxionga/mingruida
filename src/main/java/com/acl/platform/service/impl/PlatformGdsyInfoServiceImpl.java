package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformGdsyInfoDao;
import com.acl.platform.service.IPlatformGdsyInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 
 *className:PlatformGdsyInfoServiceImpl
 *author:wangzhe
 *createDate:2016年8月12日 上午11:03:12
 *version:3.0
 *department:安创乐科技
 *description: 模式二信息service实现类
 */
@Service
@Transactional
public class PlatformGdsyInfoServiceImpl implements IPlatformGdsyInfoService {

	@Autowired
	private IPlatformGdsyInfoDao platformGdsyInfoDao;
	
	public PageList<?> queryGdsyInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.queryGdsyInfo(paramsMap, pageBounds);
	}

	@Override
	public void insertGdsyInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGdsyInfoDao.insertGdsyInfo(paramsMap);
	}

	@Override
	public void updateGdsyInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGdsyInfoDao.updateGdsyInfo(paramsMap);
	}
	
	@Override
	public List<Map<String, Object>> gdsyGoodsValidate(HashMap<String, Object> paramsMap) {
		return platformGdsyInfoDao.gdsyGoodsValidate(paramsMap);
	}
	
	@Override
	public void insertDeptGdsyInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGdsyInfoDao.insertDeptGdsyInfo(paramsMap);
	}
	

	@Override
	public List<Map<String, Object>> queryGdsyRelationDeptInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.queryGdsyRelationDeptInfo(paramsMap);
	}
	
	@Override
	public List<Map<String, Object>> queryGdsyGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.queryGdsyGoodsInfo(paramsMap);
	}

	@Override
	public void deleteGdsyDept(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGdsyInfoDao.deleteGdsyDept(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGdsyGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		 return platformGdsyInfoDao.selectGdsyGoodsInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGdsyGroup(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.selectGdsyGroup(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGdsyDeptGroup(HashMap<String, Object> paramsMap) {
		return null;
	}

	@Override
	public List<Map<String, Object>> selectGdsyRelation(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.selectGdsyRelation(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGdsyDeptInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.queryGdsyDeptInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGdsyDeptNoexist(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGdsyInfoDao.queryGdsyDeptNoexist(paramsMap);
	}

}
