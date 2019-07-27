package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.IPlatformMallLevelDao;
import com.acl.platform.service.IPlatformMallLevelService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformMallLevelServiceImpl
 *author:wangli
 *createDate:2017年4月24日 下午3:09:33
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
public class PlatformMallLevelServiceImpl implements IPlatformMallLevelService {
	@Autowired
	private IPlatformMallLevelDao platformMallLevelDao;
	
	@Override
	public PageList<?> queryMallLevel(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformMallLevelDao.queryMallLevel(paramsMap, pageBounds);
	}

	@Override
	public void insertMallLevel(HashMap<String, Object> paramsMap) {
		platformMallLevelDao.insertMallLevel(paramsMap);
	}

	@Override
	public void updateMallLevel(HashMap<String, Object> paramsMap) {
		platformMallLevelDao.updateMallLevel(paramsMap);
	}

	@Override
	public List<Map<String, Object>> mallLevelValidate(HashMap<String, Object> hashMap) {
		return platformMallLevelDao.mallLevelValidate(hashMap);
	}

	@Override
	public List<Map<String, Object>> levelValidate(HashMap<String, Object> paramsMap) {
		return platformMallLevelDao.levelValidate(paramsMap);
	}

	

}
