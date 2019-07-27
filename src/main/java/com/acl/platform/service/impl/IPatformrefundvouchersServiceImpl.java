package com.acl.platform.service.impl;


import com.acl.platform.dao.IPlatformRefundvouchersDao;

import com.acl.platform.service.IPlatformRefundvouchersService;

import com.acl.utils.excel.BasicExcelDataEntity;
import com.acl.utils.excel.ParsingShuaBaoMExcelUtil;
import com.acl.utils.excel.RefundVouchersInfo;
import com.acl.utils.excel.ShuaBaoInfo;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.CollectionUtil;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 *className:IPatformrefundvouchersServiceImpl
 *author:longxionga
 *createDate:2019年4月30日
 *department:铭锐达
 *description:充值券查询
 */
@Service
@Transactional
public class IPatformrefundvouchersServiceImpl implements IPlatformRefundvouchersService {

	@Autowired
	private IPlatformRefundvouchersDao platformRefundvouchersDao;

	@Override
	public PageList<?> queryRefundvouchersInfoPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return platformRefundvouchersDao.queryRefundvouchersInfoPageList(paramsMap, pageBounds);
	}

	@Override
	public void refundvouchersimport(File file) {
		ParsingShuaBaoMExcelUtil parsingAgentExcelUtil = new ParsingShuaBaoMExcelUtil();
		BasicExcelDataEntity basicExcelDataEntity = parsingAgentExcelUtil.getParsingExcelResult(file);
		List<RefundVouchersInfo> shuaBaoInfoList = RefundvouchersExcelToList(basicExcelDataEntity);
		if(CollectionUtil.isNotEmpty(shuaBaoInfoList)){
			List<Map> list = new ArrayList<>();
			for (int j=0;j<shuaBaoInfoList.size();j++){
				RefundVouchersInfo basicInfo =  shuaBaoInfoList.get(j);
				System.out.println(basicInfo);
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("id", UUIDGenerator.generate());
				paramsMap.put("rechargedate", FormatDateUtil.StrToDate(basicInfo.getRechargedate()));
				paramsMap.put("returndate", FormatDateUtil.StrToDate(basicInfo.getReturndate()));
				paramsMap.put("expiredate", FormatDateUtil.StrToDate(basicInfo.getExpiredate()));
				paramsMap.put("merchantNO",basicInfo.getMerchantNO());
				paramsMap.put("merchantname",basicInfo.getMerchantname());
				paramsMap.put("agentNO",basicInfo.getAgentNO());
				paramsMap.put("agentname",basicInfo.getAgentname());
				paramsMap.put("implementstype",basicInfo.getImplementstype());
				paramsMap.put("rechargeamount",basicInfo.getRechargeamount());
				paramsMap.put("returnamout", basicInfo.getReturnamout());
				paramsMap.put("alreadyamount", basicInfo.getAlreadyamount());

				list.add(paramsMap);

			}
			Map m = new HashMap();
			m.put("itemMap",list);
			this.insertRefundvouchers(m);
		}
	}

	/**
	 *
	 * @param basicExcelDataEntity
	 * @return
	 */
	private  List<RefundVouchersInfo> RefundvouchersExcelToList(BasicExcelDataEntity basicExcelDataEntity){
		Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
		List<RefundVouchersInfo> basicInfoList = new ArrayList<>();
		System.out.println("titleIndex:"+titleIndex.toString());
		for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
			System.out.println("excel:"+map.toString());
			RefundVouchersInfo refundvou = new RefundVouchersInfo();
			for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
				String colName = basicExcelDataEntity.getExcelTitleMap().get(str);
				if( colName.equals("充值时间")){
					refundvou.setRechargedate(map.get(str));
					continue;
				}
				if( colName.equals("返券时间")){
					refundvou.setReturndate(map.get(str));
					continue;
				}
				if( colName.equals("到期时间")){
					refundvou.setExpiredate(map.get(str));
					continue;
				}

				if( colName.equals("商户编号")){
					refundvou.setMerchantNO(map.get(str));
					continue;
				}
				if(colName.equals("商户名称")){
					refundvou.setMerchantname(map.get(str));
					continue;
				}
				if( colName.equals("代理商编号")){
					refundvou.setAgentNO(map.get(str));
					continue;
				}
				if( colName.equals("代理商名称")){
					refundvou.setAgentname(map.get(str));
					continue;
				}
				if( colName.equals("机具类型")){
					refundvou.setImplementstype(map.get(str));
					continue;
				}
				if( colName.equals("充值金额")){
					refundvou.setRechargeamount(map.get(str));
					continue;
				}
				if( colName.equals("返券金额")){
					refundvou.setReturnamout(map.get(str));
					continue;
				}
				if( colName.equals("已用金额")){
					refundvou.setAlreadyamount(map.get(str));
					continue;
				}
			}

			/*if(org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMachineCode())
					|| org.apache.commons.lang3.StringUtils.isEmpty(basicInfo.getMerchantCode())){
				System.out.println("数据不完整,跳过处理:"+basicInfo.toString());
				continue;
			}*/
			basicInfoList.add(refundvou);
		}
		return basicInfoList;
	}

	@Override
	public int insertRefundvouchers(Map<String, Object> paramsMap) {
		return platformRefundvouchersDao.insertRefundvouchers(paramsMap);
	}

	@Override
	public int deleteRefundVoucherAll(Map<String, Object> paramsMap) {
		return platformRefundvouchersDao.deleteRefundVoucherAll(paramsMap);
	}
}
