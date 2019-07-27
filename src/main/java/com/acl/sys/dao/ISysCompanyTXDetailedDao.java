package com.acl.sys.dao;

import java.util.HashMap;

/**
 * className:ISysWithDrawInfoDao author:huangs createDate:2016年9月23日 下午5:53:06
 * vsersion:3.0 department:安创乐科技 description:渠道 服务商 代理商提现流水
 */
public interface ISysCompanyTXDetailedDao {

	// t_back_channel_detailed 渠道 2
	// t_back_settle_detailed 服务商 3
	// t_back_agent_detailed 代理商4

	void insertCompanyTXDetailed(HashMap<String, Object> paramsMap);

}
