package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformSysControlDao;
import com.acl.platform.service.IPlatformSysControlService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformSysControlServiceImpl
 *author:wangli
 *createDate:2016年10月29日 上午10:51:10
 *vsersion:3.0
 *department:安创乐科技
 *description:系统控制管理
 */
@Service
@Transactional
public class IPlatformSysControlServiceImpl implements IPlatformSysControlService {
	@Autowired
	private  IPlatformSysControlDao  platformSysControlDao;
	 
	@Override
	public PageList<?> querySysControl(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// 查询系统控制信息
		return platformSysControlDao.querySysControl(paramsMap, pageBounds);
	}

	@Override
	public void deleteSysControl(HashMap<String, Object> paramsMap) {
		// 删除系统控制信息
		platformSysControlDao.deleteSysControl(paramsMap);
	}

	@Override
	public void insertSysControl(HashMap<String, Object> paramsMap) {
		// 新增系统控制信息
		platformSysControlDao.insertSysControl(paramsMap);
	}

	@Override
	public void updateSysControl(HashMap<String, Object> paramsMap) {
		// 修改系统控制信息
	    platformSysControlDao.updateSysControl(paramsMap);
		
	}

	@Override
	public List<Map<String, Object>> checkUse(HashMap<String, Object> hashMap) {
		// 查询开关是否开启
		return platformSysControlDao.checkUse(hashMap);
	}

	@Override
	public List<Map<String, Object>> typeValidate(HashMap<String, Object> paramsMap) {
		return platformSysControlDao.typeValidate(paramsMap);
	}

}
