package com.acl.sys.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.ISysCompanyTXDetailedDao;
import com.acl.sys.service.ISysCompanyTXDetailedService;

/**
 * className:SysUserInfoServiceImpl author:malx createDate:2016年8月10日 下午5:52:44
 * vsersion:3.0 department:安创乐科技 description:
 */
@Service
@Transactional
public class ISysCompanyTXDetailedServiceImpl implements ISysCompanyTXDetailedService {

	@Autowired
	private ISysCompanyTXDetailedDao sysCompanyTXDetailedDao;

	@Override
	public void insertCompanyTXDetailed(HashMap<String, Object> paramsMap) {
		sysCompanyTXDetailedDao.insertCompanyTXDetailed(paramsMap);		
	}

}
