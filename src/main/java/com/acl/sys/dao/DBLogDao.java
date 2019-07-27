package com.acl.sys.dao;

import java.util.HashMap;

import com.acl.pojo.DBLog;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

public interface DBLogDao {

	int insert(DBLog dbLog);

	PageList<DBLog> searchLogBean(HashMap<String, Object> paramsMap, PageBounds pageBounds);

}
