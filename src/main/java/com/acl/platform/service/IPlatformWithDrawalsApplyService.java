package com.acl.platform.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.pojo.TxOrder;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.alibaba.fastjson.JSONObject;

/**
 *className:PlatformWithDrawalsApplyService
 *author:wangli
 *createDate:2016年8月19日 下午8:04:56
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformWithDrawalsApplyService {
	/**
	 * 查询字典信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryWithDrawalsApply(HashMap<String, Object> paramsMap,
									  PageBounds pageBounds);
	//查询结算服务商
	List<Map<String, Object>>querySettleInfoForCombox(Map<String, Object> paramsMap);
	//查询代理商信息
	List<Map<String, Object>>queryAgentInfoForCombox(Map<String, Object> paramsMap);
	//查询部门信息
	List<Map<String, Object>>queryDeptInfoForCombox(Map<String, Object> paramsMap);

	//查询当天用户提现申请总额
	List<Map<String, Object>>querySumUserTX(Map<String, Object> paramsMap);

	//查询当天出金总额
	List<Map<String, Object>>querySumOutTX(Map<String, Object> paramsMap);


	Map<String, Object> queryOrderWithdrawalsMoney(Map<String, Object> paramsMap);


	//金运通提现审核
	public JSONObject getJinYunTongPay(String orderNo,String txType);

	/**
	 * 更新提现订单
	 **/
	int updateTxOrder(Map<String, Object> map);

	//查询该订单状态是否为1（待审核）
	List<Map<String, Object>>queryOrderWithdrawalsIsUse(Map<String, Object> paramsMap);

	public boolean rebackUserWithDraw(Map<String, Object> paramsMap);

	//查询代理商信息
	List<Map<String, Object>>queryAgentInfoForCombox2(Map<String, Object> paramsMap);
}
