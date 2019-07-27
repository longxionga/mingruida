package com.acl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.SysBackDictInfoDao;
import com.acl.sys.service.SysBackDictInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
@Service
@Transactional
public class SysBackDictInfoServiceImpl implements SysBackDictInfoService {
	@Autowired
	private SysBackDictInfoDao sysBackDictInfoDao;

	@Override
	public PageList<?> queryBackDictInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return sysBackDictInfoDao.queryBackDictInfo(paramsMap, pageBounds);
	}

	@Override
	public void insertBackDictInfo(HashMap<String, Object> paramsMap) {
		sysBackDictInfoDao.insertBackDictInfo(paramsMap);

	}

	@Override
	public void updateBackDictInfo(HashMap<String, Object> paramsMap) {
		sysBackDictInfoDao.updateBackDictInfo(paramsMap);

	}

	@Override
	public void deleteBackDictInfo(HashMap<String, Object> paramsMap) {
		sysBackDictInfoDao.deleteBackDictInfo(paramsMap);

	}

	@Override
	public List<Map<String, Object>> dictValidate(HashMap<String, Object> paramsMap) {
		return sysBackDictInfoDao.dictValidate(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryDictByCodeId(HashMap<String, Object> paramsMap) {
		return sysBackDictInfoDao.queryDictByCodeId(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryDictDesc(HashMap<String, Object> paramsMap) {
		return sysBackDictInfoDao.queryDictDesc(paramsMap);
	}
}
