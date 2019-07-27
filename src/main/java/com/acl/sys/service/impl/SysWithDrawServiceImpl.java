package com.acl.sys.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.ISysCompanyTXDetailedDao;
import com.acl.sys.dao.ISysWithDrawInfoDao;
import com.acl.sys.service.ISysWithDrawService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;

/**
 * className:SysUserInfoServiceImpl author:malx createDate:2016年8月10日 下午5:52:44
 * vsersion:3.0 department:安创乐科技 description:
 */
@Service
@Transactional
public class SysWithDrawServiceImpl implements ISysWithDrawService {

	@Autowired
	private ISysWithDrawInfoDao sysWithDrawInfoDao;
	@Autowired
	private ISysCompanyTXDetailedDao sysCompanyTXDetailedDao;

	@Override
	public PageList<?> queryWithDrawInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return sysWithDrawInfoDao.queryWithDrawInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryWithDrawInfo(HashMap<String, Object> paramsMap) {
		return sysWithDrawInfoDao.queryWithDrawInfo(paramsMap);
	}

	@Override
	public int insertWithDrawInfo(HashMap<String, Object> paramsMap) {
		return sysWithDrawInfoDao.insertWithDrawInfo(paramsMap);
	}

	@Override
	public int updateWithDrawApproveInfo(HashMap<String, Object> paramsMap) {
		return sysWithDrawInfoDao.updateWithDrawApproveInfo(paramsMap);
	}

	@Override
	public int updateDeptMoneyInfo(HashMap<String, Object> paramsMap) {
		return sysWithDrawInfoDao.updateDeptMoneyInfo(paramsMap);
	}

	@Override
	public void updateMoney (HashMap<String, Object> paramsMap) {
		//修改部门余额
		HashMap<String, Object> deptparamsMap = new HashMap<>();
		deptparamsMap.put("dept_id", paramsMap.get("dept_id"));
		deptparamsMap.put("money", paramsMap.get("new_money"));
		deptparamsMap.put("dept_money", paramsMap.get("dept_money"));
		deptparamsMap.put("prop_values", paramsMap.get("prop_values"));
		sysWithDrawInfoDao.updateDeptMoneyInfo(deptparamsMap);
		//添加申请单
		sysWithDrawInfoDao.insertWithDrawInfo(paramsMap);

		HashMap<String, Object> detailMap = new HashMap<>();
		
		detailMap.put("sys_id", UUIDGenerator.generate());
		detailMap.put("order_no", paramsMap.get("company_id"));
		detailMap.put("money", paramsMap.get("tx_money"));
		detailMap.put("fund_type", "06");
		detailMap.put("before_balance", paramsMap.get("dept_money"));
		detailMap.put("after_balance", paramsMap.get("new_money"));
		detailMap.put("create_date", paramsMap.get("create_date"));
		detailMap.put("prop_values", paramsMap.get("prop_values"));
		
		//添加流水
		if (2 == StringUtils.checkInt(paramsMap.get("dept_type"))) {
			detailMap.put("channel_id", paramsMap.get("dept_id"));
		}
		if (3 == StringUtils.checkInt(paramsMap.get("dept_type"))) {
			detailMap.put("settle_id", paramsMap.get("dept_id"));
		}
		if (4 == StringUtils.checkInt(paramsMap.get("dept_type"))) {
			detailMap.put("agent_id", paramsMap.get("dept_id"));
		}
		sysCompanyTXDetailedDao.insertCompanyTXDetailed(detailMap);
	}
	
	
	@Override
	public BigDecimal sumUserWalletByDept(String dept_id) {
		return sysWithDrawInfoDao.sumUserWalletByDept(dept_id);
	}

	@Override
	public BigDecimal queryDeptMoney(String dept_id) {
		return sysWithDrawInfoDao.queryDeptMoney(dept_id);
	}

	@Override
	public PageList<?> queryDepositInfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return sysWithDrawInfoDao.queryDepositInfo(paramsMap, pageBounds);
	}

	@Override
	public List<HashMap<String, Object>> queryDepositCount(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return sysWithDrawInfoDao.queryDepositCount(paramsMap);
	}

	@Override
	public Object queryDeptMoneyInfo(HashMap<String, Object> paramsMap) {
		// TODO Auto-generated method stub
		return sysWithDrawInfoDao.queryDeptMoneyInfo(paramsMap);
	}

}
