package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.IPlatformNumberInfoDao;
import com.acl.platform.service.IPlatformNumberInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformNumberInfoServiceImpl
 *author:wangli
 *createDate:2017年2月21日 下午3:22:35
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class IPlatformNumberInfoServiceImpl implements IPlatformNumberInfoService {
	@Autowired
	private IPlatformNumberInfoDao platformNumberInfo;
	
	@Override
	public PageList<?> queryNumberInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformNumberInfo.queryNumberInfo(paramsMap, pageBounds);
	}

	@Override
	public void deleteNumberInfo(HashMap<String, Object> paramsMap) {
		platformNumberInfo.deleteNumberInfo(paramsMap);
	}

	@Override
	public void insertNumberInfo(HashMap<String, Object> paramsMap) {
		platformNumberInfo.insertNumberInfo(paramsMap);
	}

	@Override
	public void updateNumberInfo(HashMap<String, Object> paramsMap) {
		platformNumberInfo.updateNumberInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryBrokerCode(HashMap<String, Object> paramsMap) {
		return platformNumberInfo.queryBrokerCode(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryNumberInfoCode(HashMap<String, Object> paramsMap) {
		return platformNumberInfo.queryNumberInfoCode(paramsMap);
	}

}
