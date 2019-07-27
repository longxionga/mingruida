package com.acl.platform.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineLogDao;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.platform.service.IPlatformMachineLogService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPatformMachineInfoServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:机具调拨记录查询
 */
@Service
@Transactional
public class IPatformMachineLogServiceImpl implements IPlatformMachineLogService {

	@Autowired
	private IPlatformMachineLogDao iPlatformMachineLogDao;

	@Override
	public PageList<?> queryMachineLogPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		   if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
			return iPlatformMachineLogDao.queryMachineLogPageList(paramsMap, pageBounds);

			}
			return iPlatformMachineLogDao.queryMachineLogPageList(paramsMap, pageBounds);

	}

}
