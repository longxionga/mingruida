package com.acl.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.platform.dao.PlatformAgentDeptDao;
import com.acl.platform.service.PlatformAgentDeptService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformAgentDeptServiceImpl
 *author:wangli
 *createDate:2016年9月6日 上午11:03:28
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class PlatformAgentDeptServiceImpl implements PlatformAgentDeptService {
	 @Autowired
	  private PlatformAgentDeptDao platformAgentDeptDao;
	 

		@Override
		public PageList<?> queryAgentDept(HashMap<String, Object> paramsMap,
				PageBounds pageBounds) {
			return platformAgentDeptDao.queryAgentDept(paramsMap,pageBounds);
		}

		@Override
		public void deleteAgentdeptBrokers(HashMap<String, Object> paramsMap) {
			platformAgentDeptDao.deleteAgentdeptBrokers(paramsMap);	
		}

		@Override
		public PageList<?> queryAgentDeptBrokerInfo(
				HashMap<String, Object> paramsMap, PageBounds pageBounds) {
			return platformAgentDeptDao.queryAgentDeptBrokerInfo(paramsMap,pageBounds);
		}

		@Override
		public void insertAgentdeptBrokers(HashMap<String, Object> paramsMap) {
			// 往临时表中添加经纪人与部门之间的修改关联表
			platformAgentDeptDao.insertAgentdeptBrokers(paramsMap);
		}
		
		@Override
		public void insertAgentDept(HashMap<String, Object> paramsMap) {
			platformAgentDeptDao.insertAgentDept(paramsMap);
		}
	   
		/**
		 * 更新代理商部门信息
		 */
		@Override
		public void updateAgentdept(HashMap<String, Object> paramsMap) {
			platformAgentDeptDao.updateAgentdept(paramsMap);
		}
		@Override
		public void updateAgentdeptBrokers(HashMap<String, Object> paramsMap) {
			platformAgentDeptDao.updateAgentdeptBrokers(paramsMap);	
		}

		@Override
		public List<Map<String, Object>> queryDeptForMon(Map<String, Object> map) {
			return platformAgentDeptDao.queryDeptForMon(map);
		}
		  
}
