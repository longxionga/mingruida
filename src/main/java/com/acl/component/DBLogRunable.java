package com.acl.component;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.acl.pojo.DBLog;
import com.acl.sys.dao.DBLogDao;
import com.acl.utils.util.LogUtils;

/**
 * 系统日志线程
 * @author huangshuang
 * 20161010
 */
public class DBLogRunable implements Runnable{
	private DBLog dbLog;
	@Autowired
	private DBLogDao logDBDao;
	public DBLogRunable(DBLog dbLog, DBLogDao logDBDao) {
		this.dbLog = dbLog;
		this.logDBDao = logDBDao;
	}
	@Override
	public void run() {
		try {
			LogUtils.info("**日志记录线程:" + Thread.currentThread().getName());
			dbLog.setLog_id(UUID.randomUUID().toString());
			logDBDao.insert(dbLog);
		} catch (Exception e) {
			LogUtils.error("日志信息记录失败" + new Date() + "= cqParams: " + dbLog.getCq_params() + " methodName: " + dbLog.getMethod_name()
					+ " actionMessage: " + dbLog.getAction_message()+" isOk:"+dbLog.getIs_ok());
			e.printStackTrace();
		}
	}

}
