package com.acl.report.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;

/**
 *className:IReportTradeWarningDao
 *author:wang
 *createDate:2017年9月6日 上午9:47:51
 *version:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportTradeWarningDao {
	/**
	 * 查询用户预警信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportTradeWarning(HashMap<String, Object> paramsMap, PageBounds pageBounds);

}
