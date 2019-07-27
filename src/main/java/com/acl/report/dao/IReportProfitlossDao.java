package com.acl.report.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IReportProfitlossDao
 *author:wangli
 *createDate:2017年3月2日 上午9:47:51
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IReportProfitlossDao {

	/**
	 * 查询用户流水信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryReportUserProfitloss(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	/**
	 *查询用户充值统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryReportUserInto(Map<String,Object> map);
	/**
	 *查询用户提现统计
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryReportUserRollOut(Map<String,Object> map);


}
