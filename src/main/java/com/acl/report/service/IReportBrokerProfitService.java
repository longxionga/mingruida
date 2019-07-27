package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportBrokerProfitService
 *author:wangli
 *createDate:2017年9月5日 上午11:34:29
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportBrokerProfitService {
	/**
	 * 查询经纪人日报表信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportBrokerProfit(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
}
