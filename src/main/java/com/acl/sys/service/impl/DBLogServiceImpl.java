package com.acl.sys.service.impl;

import java.util.HashMap;

import com.acl.sys.service.DBLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.acl.component.DBLogRunable;
import com.acl.pojo.DBLog;
import com.acl.sys.dao.DBLogDao;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

@Service
public class DBLogServiceImpl implements DBLogService {
	@Autowired
	private DBLogDao logDBDao;
	@Autowired
	@Qualifier (value="taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Override
	public void insertLog(DBLog dbLog) {
		Runnable logDBRunable = new DBLogRunable(dbLog, logDBDao);
		taskExecutor.execute(new Thread(logDBRunable));
	}
	
	@Override
	public PageList<DBLog> searchLogBean(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return logDBDao.searchLogBean(paramsMap, pageBounds);
	}
	

}
