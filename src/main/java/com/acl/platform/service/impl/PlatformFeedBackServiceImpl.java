package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.platform.service.PlatformFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.PlatformFeedBackDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformFeedBackServiceImpl
 *author:wangli
 *createDate:2017年7月4日 上午9:13:16
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
public class PlatformFeedBackServiceImpl implements PlatformFeedBackService {

	@Autowired
	private PlatformFeedBackDao platformFeedBackDao;
	
	@Override
	public PageList<?> queryFeedBack(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformFeedBackDao.queryFeedBack(paramsMap, pageBounds);
	}

	@Override
	public List<Map<String, Object>> queryFeedBack(HashMap<String, Object> paramsMap) {
		return platformFeedBackDao.queryFeedBack(paramsMap);
	}

}
