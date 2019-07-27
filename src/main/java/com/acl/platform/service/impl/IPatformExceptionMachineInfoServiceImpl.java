package com.acl.platform.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformExceptionMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineLogDao;
import com.acl.platform.service.IPlatformExceptionMachineInfoService;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPatformMachineInfoServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IPatformExceptionMachineInfoServiceImpl implements IPlatformExceptionMachineInfoService {

	@Autowired
	private IPlatformExceptionMachineInfoDao iPlatformExceptionMachineInfoDao;

	@Autowired
	private IPlatformMachineInfoDao iPlatformMachineInfoDao;


	@Override
	public PageList<?> queryExceptionMachineInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id

		return iPlatformExceptionMachineInfoDao.queryExceptionMachineInfoPageList(paramsMap, pageBounds);
	}

	@Override
	public String removeMachineInfoAll(Map<String, Object> paramsMap) throws Exception {
		try {
			List<Map<String, Object>>  list = iPlatformExceptionMachineInfoDao.queryExceptionMachineInfoById(paramsMap);
			if (list!=null && list.size()==1) {
				for (Map<String, Object> stringObjectMap : list) {
					stringObjectMap.put("id",UUIDGenerator.generate());
					stringObjectMap.put("remore_time",new Date());
					iPlatformExceptionMachineInfoDao.intertMachineTempAllocation(stringObjectMap);

				}
			}else {
				return  "删除失败,请确认数据问题";
			}

			//删除机具信息
			for (Map<String, Object> stringObjectMap : list) {
				iPlatformMachineInfoDao.deleteMachineInfo(stringObjectMap);
			}


		} catch (Exception e) {
			throw new Exception(e);
		}

		return "移除成功";
	}


}
