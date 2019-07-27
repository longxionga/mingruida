package com.acl.sys.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.ISysBankRuleDao;
import com.acl.sys.service.ISysBankRuleService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:SysBankRuleServiceImpl
 *author:wangli
 *createDate:2016年8月18日 下午7:50:01
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class SysBankRuleServiceImpl implements ISysBankRuleService {
	@Autowired
	private ISysBankRuleDao sysBankRuleDao;
	
	@Override
	public PageList<?> queryBankRule(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return sysBankRuleDao.queryBankRule(paramsMap, pageBounds);
	}

//	@Override
//	public void insertBankRule(HashMap<String, Object> paramsMap) {
//		sysBankRuleDao.insertBankRule(paramsMap);
//	}

	@Override
	public void updateBankRule(HashMap<String, Object> paHashMap) {
		sysBankRuleDao.updateBankRule(paHashMap);
	}

	@Override
	public PageList<?> queryBrandRule(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return sysBankRuleDao.queryBrandRule(paramsMap, pageBounds);
	}

//	@Override
//	public void deleteBankRule(HashMap<String, Object> paHashMap) {
//		sysBankRuleDao.deleteBankRule(paHashMap);
//	}

}
