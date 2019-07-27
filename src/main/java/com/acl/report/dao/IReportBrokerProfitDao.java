package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportBrokerProfitDao
 *author:wangli
 *createDate:2017年9月5日 上午11:27:34
 *vsersion:3.0
 *department:安创乐科技
 *description:经纪人充值提现统计报表
 */
public interface IReportBrokerProfitDao {
	/**
	 * 查询经纪人充值提现统计
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportBrokerProfit(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);

}
