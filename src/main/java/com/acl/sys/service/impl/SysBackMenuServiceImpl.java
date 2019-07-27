package com.acl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.sys.service.SysBackMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.SysBackMenuDao;

/**
 *className:SysBackMenuServiceImpl
 *author:wangli
 *createDate:2016年8月10日 下午8:46:54
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class SysBackMenuServiceImpl implements SysBackMenuService {
	@Autowired
	private SysBackMenuDao sysBackMenuDao;

	@Override
	public void insertBackMenu(HashMap<String, Object> paramsMap) {
		sysBackMenuDao.insertBackMenu(paramsMap);
	}

	@Override
	public void updateBackMenu(HashMap<String, Object> paramsMap) {
		sysBackMenuDao.updateBackMenu(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryTreeBackMenu(Map<String, Object> map) {
		return sysBackMenuDao.queryTreeBackMenu(map);
	}

	@Override
	public List<Map<String, Object>> menuValidate(HashMap<String, Object> paramsMap) {
		return sysBackMenuDao.menuValidate(paramsMap);
	}

	@Override
	public Object queryBackMenuInfo(HashMap<String, Object> paramsMap) {
		return sysBackMenuDao.queryBackMenuInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryRoleMenuView(HashMap<String, Object> map) {
		return sysBackMenuDao.queryRoleMenuView(map);
	}
	
}
