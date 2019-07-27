package com.acl.sys.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 * className:ISysWithDrawInfoDao author:huangs createDate:2016年9月23日 下午5:53:06
 * vsersion:3.0 department:安创乐科技 description:
 */
public interface ISysWithDrawInfoDao {

	PageList<?> queryWithDrawInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds);
	
	List<HashMap<String, Object>> queryWithDrawInfo(HashMap<String, Object> paramsMap);

	int insertWithDrawInfo(HashMap<String, Object> paramsMap);

	int updateWithDrawApproveInfo(HashMap<String, Object> paramsMap);

	BigDecimal sumUserWalletByDept(String dept_id);

	int updateDeptMoneyInfo(HashMap<String, Object> paramsMap);
	BigDecimal queryDeptMoney(String dept_id);
	
	PageList<?> queryDepositInfo(HashMap<String, Object> paramsMap,
			PageBounds pageBounds);
	
	List<HashMap<String,Object>> queryDepositCount(HashMap<String,Object> paramsMap);
	
	public Object queryDeptMoneyInfo(HashMap<String, Object> paramsMap);

}
