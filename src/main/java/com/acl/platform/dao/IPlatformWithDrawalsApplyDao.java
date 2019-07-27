package com.acl.platform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:IPlatformWithDrawalsApplyDao
 *author:wangli
 *createDate:2016年8月19日 下午8:03:45
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public interface IPlatformWithDrawalsApplyDao {
	/**
	 * 查询提现订单信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> queryWithDrawalsApply(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	List<Map<String, Object>>querySettleInfoForCombox(Map<String, Object> paramsMap);

	List<Map<String, Object>>queryAgentInfoForCombox(Map<String, Object> paramsMap);
	


	int updateTxOrder(Map<String,Object>paramsMap);

	Map<String, Object> queryOrderWithdrawalsMoney(Map<String, Object> paramsMap);


	List<Map<String, Object>> queryUserInfo(Map<String, Object> paramsMap);

	List<Map<String, Object>> queryUserCapital(Map<String, Object> paramsMap);

	void updateCaptical(Map<String,Object> paramsMap);

	void insertCapticalBilling(Map<String,Object>paramsMap);

	//查询部门信息
    List<Map<String, Object>>queryDeptInfoForCombox(Map<String, Object> paramsMap);	
    //查询当天用户提现申请总额
    List<Map<String, Object>>querySumUserTX(Map<String, Object> paramsMap);	
    
  //查询当天出金总额
    List<Map<String, Object>>querySumOutTX(Map<String, Object> paramsMap);	
    
    //查询该订单状态是否为1（待审核）
    List<Map<String, Object>>queryOrderWithdrawalsIsUse(Map<String, Object> paramsMap);

	List<Map<String, Object>>queryAgentInfoForCombox2(Map<String, Object> paramsMap);
   
}
