package com.acl.report.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.pojo.LoginSession;
import com.acl.report.dao.IReportStaffWagesDao;
import com.acl.report.dao.IReportTransactionDao;
import com.acl.report.service.IReportStaffWagesService;
import com.acl.report.service.IReportTransactionService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DoubleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class IReportTransactionServiceImpl implements IReportTransactionService {

	@Autowired
	private IReportTransactionDao iReportTransactionDao;

	@Autowired
	private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

	@Override
	public PageList<?> queryTransactionPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		PageList<?>  translist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			translist=iReportTransactionDao.queryTransactionPageList(paramsMap, pageBounds);
		}else {

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginid",loginid);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门
				String position = staffmap.get(0).get("position").toString();//岗位

				if (position!=null && position.equals("1")){ //分公司经理
					paramsMap.put("branch",branch);
				}else {
					paramsMap.put("id","-1");
				}
			}else{//账号未绑定员工
				paramsMap.put("id","-1");
			}
			translist=iReportTransactionDao.queryTransactionPageList(paramsMap, pageBounds);
		}
        //统计员工交易笔数交易量
		if (translist!=null && translist.size()>0){
			int totlecoun = 0;
			Double totlesum = 0.00;
			for (Object o : translist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String staffid = sl.get("id").toString();
				paramsMap.put("staffid",staffid);
				//查询员工交易合
				List<Map<String, Object>> tlist = iReportTransactionDao.querytradeSum(paramsMap);
				if (tlist!=null && tlist.size()>0){
					String mcount = tlist.get(0).get("mcount").toString();
					String msum = tlist.get(0).get("msum").toString();
					sl.put("mcount",mcount);
					sl.put("msum",msum);
					totlecoun = totlecoun+ Integer.valueOf(mcount);// 合计交易笔数
					totlesum =DoubleUtils.add(totlesum,Double.valueOf(msum));//交易金额

					sl.put("totlecoun",totlecoun);
					sl.put("totlesum",totlesum);

				}

			}
		}

		return translist;
	}
	@Override
	public Map<String, Object> countTradeInfo(HashMap<String, Object> hashMap,LoginSession loginSession) {

		Map<String, Object> stawalist = null ;
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			stawalist=iReportTransactionDao.countTradeInfo(hashMap);
		}else { //分公司经理登陆

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginid",loginid);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门
				String position = staffmap.get(0).get("position").toString();//岗位
				if (position!=null && position.equals("1")){ //分公司经理
					hashMap.put("branch",branch);
				}
			}else{//账号未绑定员工
				hashMap.put("staffid","-1");
			}
			stawalist= iReportTransactionDao.countTradeInfo(hashMap);
		}
		// 查询代理商日报表符合条件的记录总数
		return stawalist;
	}

	@Override
	public List<Map<String, Object>> exportTradeReportInfo(Map<String, Object> map,LoginSession loginSession) {

		List<Map<String,Object>> translist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			translist=iReportTransactionDao.exportTradeReportInfo(map);
		}else {

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginid",loginid);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门
				String position = staffmap.get(0).get("position").toString();//岗位

				if (position!=null && position.equals("1")){ //分公司经理
					map.put("branch",branch);
				}else {
					map.put("id","-1");
				}
			}else{//账号未绑定员工
				map.put("id","-1");
			}
			translist=iReportTransactionDao.exportTradeReportInfo(map);
		}

		//统计员工交易笔数交易量
		if (translist!=null && translist.size()>0){
//			int totlecoun = 0;
////			Double totlesum = 0.00;
			for (Object o : translist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String staffid = sl.get("id").toString();
				map.put("staffid",staffid);
				//查询员工交易合
				List<Map<String, Object>> tlist = iReportTransactionDao.querytradeSum(map);
				if (tlist!=null && tlist.size()>0){
					String mcount = tlist.get(0).get("mcount").toString();
					String msum = tlist.get(0).get("msum").toString();
					sl.put("交易量",mcount);
					sl.put("流水合计",msum);
				/*	totlecoun = totlecoun+ Integer.valueOf(mcount);// 合计交易笔数
					totlesum =DoubleUtils.add(totlesum,Double.valueOf(msum));//交易金额

					sl.put("totlecoun",totlecoun);
					sl.put("totlesum",totlesum);*/

				}

			}
		}
		return  translist;
	}

}
