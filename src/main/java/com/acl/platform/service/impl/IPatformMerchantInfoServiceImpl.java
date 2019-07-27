package com.acl.platform.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineInfoDao;
import com.acl.platform.dao.IPlatformMachineLogDao;
import com.acl.platform.dao.IPlatformMerchatInfoDao;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.platform.service.IPlatformMerchartInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *className:IPatformMachineInfoServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IPatformMerchantInfoServiceImpl implements IPlatformMerchartInfoService {

	@Autowired
	private IPlatformMerchatInfoDao iPlatformMerchatInfoDao;

	/**
	 *  商户信息分页查询
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	@Override
	public PageList<?> queryMerchantInfoPageLists(HashMap<String, Object> paramsMap, PageBounds pageBounds) {

		return iPlatformMerchatInfoDao.queryMerchantInfoPageLists(paramsMap, pageBounds);

	}

	@Override
	public List<Map<String, Object>> queryMerchantInfoList(HashMap<String, Object> hashMap) {
		return iPlatformMerchatInfoDao.queryMerchantInfoList(hashMap);
	}

	@Override
	public List<Map<String, Object>> getMerchantAuditStartsCombobox(HashMap<String, Object> hashMap) {
		return iPlatformMerchatInfoDao.getMerchantAuditStartsCombobox(hashMap);
	}



	@Override
	public Map<String, Object> countMerchantInfo(HashMap<String, Object> hashMap) {

		Map<String, Object> merchantlist = null ;

		merchantlist=iPlatformMerchatInfoDao.countMerchantInfo(hashMap);
			return  merchantlist;
	}

	@Override
	public List<Map<String, Object>> exportMerchantInfoReportInfo(Map<String, Object> map) {

		List<Map<String, Object>> Mechantlist = null ;

			Mechantlist=iPlatformMerchatInfoDao.exportMerchantInfoReportInfo(map);
		return  Mechantlist;
	}

}
