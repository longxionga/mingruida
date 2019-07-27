package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformGzpInfoDao;
import com.acl.platform.service.IPlatformGzpInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;;
/**
 * 
 *className:PlatformGzpInfoServiceImpl
 *author:wangzhe
 *createDate:2016年8月12日 上午10:00:30
 *version:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class PlatformGzpInfoServiceImpl implements IPlatformGzpInfoService {

	@Autowired
	private  IPlatformGzpInfoDao platformGzpInfo;
	
	public PageList<?> queryGzpInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return platformGzpInfo.queryGzpInfo(paramsMap, pageBounds);
	}

	@Override
	public void insertGzpInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGzpInfo.insertGzpInfo(paramsMap);
	}

	@Override
	public void updateGzpInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGzpInfo.updateGzpInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> gzpGoodsValidate(HashMap<String, Object> paramsMap) {
		return platformGzpInfo.gzpGoodsValidate(paramsMap);
	}
	
	@Override
	public void insertDeptGzpInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGzpInfo.insertDeptGzpInfo(paramsMap);
	}

	@Override
	public void deleteGzpDept(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		platformGzpInfo.deleteGzpDept(paramsMap);
		
	}

	@Override
	public List<Map<String, Object>> queryGzpRelationDeptInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.queryGzpRelationDeptInfo(paramsMap);
	}
	
	@Override
	public List<Map<String, Object>> queryGzpGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.queryGzpGoodsInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGzpGoodsInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.selectGzpGoodsInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGzpDeptGoodsInfo(HashMap<String, Object> paramsMap) {
		return platformGzpInfo.selectGzpDeptGoodsInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGzpGroup(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.selectGzpGroup(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGzpDeptGroup(HashMap<String, Object> paramsMap) {
		return platformGzpInfo.selectGzpDeptGroup(paramsMap);
	}

	@Override
	public List<Map<String, Object>> selectGzpRelation(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.selectGzpRelation(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGzpDeptInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.queryGzpDeptInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryGzpDeptNoexist(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformGzpInfo.queryGzpDeptNoexist(paramsMap);
	}


}
