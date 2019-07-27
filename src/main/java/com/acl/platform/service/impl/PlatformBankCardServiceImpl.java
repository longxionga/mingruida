package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.PlatformBankCardDao;
import com.acl.platform.service.PlatformBankCardService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:PlatformBankCardServiceImpl
 *author:wangli
 *createDate:2017年6月1日 下午1:49:29
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
public class PlatformBankCardServiceImpl implements PlatformBankCardService {

	@Autowired
	private PlatformBankCardDao platformBankCardDao;
	
	@Override
	public PageList<?> queryBankCard(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformBankCardDao.queryBankCard(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryBankCardInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return platformBankCardDao.queryBankCardInfo(paramsMap);
	}

}
