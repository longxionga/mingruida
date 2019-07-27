package com.acl.sys.service;

import java.util.HashMap;

import com.acl.pojo.DBLog;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * 系统关键日志记录入库
 * 
 * @author hsh19
 *
 */
public interface DBLogService {
	/*
	 * 记录日志
	 */
	void insertLog(DBLog dbLog);

	/*
	 * 查询记录
	 */
	PageList<DBLog> searchLogBean(HashMap<String, Object> paramsMap, PageBounds pageBounds);

}
