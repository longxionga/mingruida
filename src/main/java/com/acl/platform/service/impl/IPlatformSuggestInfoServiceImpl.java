package com.acl.platform.service.impl;

import com.acl.component.MongodbBaseDao;
import com.acl.platform.dao.IPlatformSuggestInfoDao;
import com.acl.platform.dao.IPlatformUserInfoDao;
import com.acl.platform.service.IPlatformSuggestInfoService;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.pojo.UserInfo;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPlatformUserInfoServiceImpl
 *author:wangli
 *createDate:2016年8月25日 下午2:57:55
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class IPlatformSuggestInfoServiceImpl implements IPlatformSuggestInfoService {
	@Autowired
	private IPlatformSuggestInfoDao platformSuggestInfoDao;

	//从数据库查询所有数据
	@Override
	public PageList<?> querySuggestInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformSuggestInfoDao.querySuggestInfo(paramsMap,pageBounds);
	}

}


