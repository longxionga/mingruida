package com.acl.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportProfitlossService
 *author:wangli
 *createDate:2017年3月2日 上午9:54:06
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportProfitlossService {

	/**
	 * 查询用户流水信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportUserProfitloss(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 *查询用户转入统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryReportUserInto(Map<String,Object> map);
	/**
	 *查询用户转出统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryReportUserRollOut(Map<String,Object> map);

}
