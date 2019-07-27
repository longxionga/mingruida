package com.acl.report.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.pojo.LoginSession;
import com.acl.report.dao.IReportManageTransactionDao;
import com.acl.report.dao.IReportTransactionDao;
import com.acl.report.service.IReportManageTransactionService;
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
public class IReportManageTransactionServiceImpl implements IReportManageTransactionService {

	@Autowired
	private IReportManageTransactionDao iReportManageTransactionDao;

	@Autowired
	private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

	@Override
	public PageList<?> queryManageTransactionPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		PageList<?>  translist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			translist=iReportManageTransactionDao.queryManageTransactionPageList(paramsMap, pageBounds);
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
			translist=iReportManageTransactionDao.queryManageTransactionPageList(paramsMap, pageBounds);
		}
		//统计员工交易时间交易量
		if (translist!=null && translist.size()>0){
			int totlecoun = 0;
			Double totlesum = 0.00;
			for (Object o : translist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String staffid = sl.get("id").toString();
				paramsMap.put("staffid",staffid);
				String bufstate = paramsMap.get("bufstate").toString();

				if (bufstate!=null && bufstate.equals("1")){
					Map<String,Double> maplist = new HashMap<>();
					Double totalsum = 0.00;
					Double daijikasum = 0.00;
					Double zhundaijikasum = 0.00;
					Double jijikasum = 0.00;
					Double zhifubaosum = 0.00;
					Double weixinsum = 0.00;
					Double yinliansum = 0.00;
					maplist.put("totalsum",totalsum);
					maplist.put("daijikasum",daijikasum);
					maplist.put("zhundaijikasum",zhundaijikasum);
					maplist.put("jijikasum",jijikasum);
					maplist.put("zhifubaosum",zhifubaosum);
					maplist.put("weixinsum",weixinsum);
					maplist.put("yinliansum",yinliansum);

					ybdmachinesum(paramsMap,maplist);
					totalsum = maplist.get("totalsum");
					daijikasum = maplist.get("daijikasum");
					zhundaijikasum = maplist.get("zhundaijikasum");
					jijikasum = maplist.get("jijikasum");
					zhifubaosum = maplist.get("zhifubaosum");
					weixinsum = maplist.get("weixinsum");
					yinliansum = maplist.get("yinliansum");

					sl.put("totalsum",totalsum);
					sl.put("daijikasum",daijikasum);
					sl.put("zhundaijikasum",zhundaijikasum);
					sl.put("jijikasum",jijikasum);
					sl.put("zhifubaosum",zhifubaosum);
					sl.put("weixinsum",weixinsum);
					sl.put("yinliansum",yinliansum);

				}else {
					//查询员工交易合
					List<Map<String, Object>> tlist = iReportManageTransactionDao.queryManagetradeSum(paramsMap);
					if (tlist!=null && tlist.size()>0){
						String totalsum = tlist.get(0).get("totalsum").toString();
						String daijikasum = tlist.get(0).get("daijikasum").toString();
						String zhundaijikasum = tlist.get(0).get("zhundaijikasum").toString();
						String jijikasum = tlist.get(0).get("jijikasum").toString();
						String zhifubaosum = tlist.get(0).get("zhifubaosum").toString();
						String weixinsum = tlist.get(0).get("weixinsum").toString();
						String yinliansum = tlist.get(0).get("yinliansum").toString();
						sl.put("totalsum",totalsum);
						sl.put("daijikasum",daijikasum);
						sl.put("zhundaijikasum",zhundaijikasum);
						sl.put("jijikasum",jijikasum);
						sl.put("zhifubaosum",zhifubaosum);
						sl.put("weixinsum",weixinsum);
						sl.put("yinliansum",yinliansum);
					}else {
						sl.put("totalsum","0.00");
						sl.put("daijikasum","0.00");
						sl.put("zhundaijikasum","0.00");
						sl.put("jijikasum","0.00");
						sl.put("zhifubaosum","0.00");
						sl.put("weixinsum","0.00");
						sl.put("yinliansum","0.00");
					}
				}


			}
		}

		return translist;
	}

	//统计员工已绑定机具交易量
	public  void ybdmachinesum (Map<String,Object> paramap ,Map<String,Double> sumap) {

		List<Map<String, Object>> tlist = iReportManageTransactionDao.queryManagetradeSum(paramap);
		Double totalsum =sumap.get("totalsum");
		Double daijikasum =sumap.get("daijikasum");
		Double zhundaijikasum =sumap.get("zhundaijikasum");
		Double jijikasum =sumap.get("jijikasum");
		Double zhifubaosum =sumap.get("zhifubaosum");
		Double weixinsum =sumap.get("weixinsum");
		Double yinliansum =sumap.get("yinliansum");

		if (tlist!=null && tlist.size()>0){
			double to = Double.valueOf( tlist.get(0).get("totalsum").toString());
			double dai = Double.valueOf( tlist.get(0).get("daijikasum").toString());
			double zhun = Double.valueOf( tlist.get(0).get("zhundaijikasum").toString());
			double ji = Double.valueOf( tlist.get(0).get("jijikasum").toString());
			double zhi = Double.valueOf( tlist.get(0).get("zhifubaosum").toString());
			double wei = Double.valueOf( tlist.get(0).get("weixinsum").toString());
			double yin = Double.valueOf( tlist.get(0).get("yinliansum").toString());

			totalsum =  DoubleUtils.add(totalsum,to);
			daijikasum =  DoubleUtils.add(daijikasum,dai);
			zhundaijikasum =  DoubleUtils.add(zhundaijikasum,zhun);
			jijikasum =  DoubleUtils.add(jijikasum,ji);
			zhifubaosum =  DoubleUtils.add(zhifubaosum,zhi);
			weixinsum =  DoubleUtils.add(weixinsum,wei);
			yinliansum =  DoubleUtils.add(yinliansum,yin);

		}
		sumap.put("totalsum",totalsum);
		sumap.put("daijikasum",daijikasum);
		sumap.put("zhundaijikasum",zhundaijikasum);
		sumap.put("jijikasum",jijikasum);
		sumap.put("zhifubaosum",zhifubaosum);
		sumap.put("weixinsum",weixinsum);
		sumap.put("yinliansum",yinliansum);

		HashMap<String ,Object> ma = new HashMap<>();
		String  Staffid = paramap.get("staffid").toString();
		ma.put("parentid",Staffid);

		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);

		if (staffmap!=null && staffmap.size()>0) {
			for (Map<String, Object> stringObjectMap : staffmap) {
				String sid = stringObjectMap.get("id").toString();
				paramap.put("staffid",sid);
				ybdmachinesum(paramap,sumap);
			};
		}
	}


	@Override
	public Map<String, Object> countManageTradeInfo(HashMap<String, Object> hashMap,LoginSession loginSession) {

		Map<String, Object> stawalist = null ;
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			stawalist=iReportManageTransactionDao.countManageTradeInfo(hashMap);
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
					stawalist.put("branch",branch);
				}else {
					stawalist.put("id","-1");
				}
			}else{//账号未绑定员工
				stawalist.put("id","-1");
			}
			stawalist=iReportManageTransactionDao.countManageTradeInfo(hashMap);
		}
		// 查询代理商日报表符合条件的记录总数
		return stawalist;
	}

	@Override
	public List<Map<String, Object>> exportManageTradeReportInfo(Map<String, Object> map,LoginSession loginSession) {

		List<Map<String,Object>> translist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			translist=iReportManageTransactionDao.exportManageTradeReportInfo(map);
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
			translist=iReportManageTransactionDao.exportManageTradeReportInfo(map);
		}
		//统计员工交易时间交易量
		if (translist!=null && translist.size()>0){
			int totlecoun = 0;
			Double totlesum = 0.00;
			for (Object o : translist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String staffid = sl.get("id").toString();
				map.put("staffid",staffid);
				String bufstate = map.get("bufstate").toString();

				if (bufstate!=null && bufstate.equals("1")){
					Map<String,Double> maplist = new HashMap<>();
					Double totalsum = 0.00;
					Double daijikasum = 0.00;
					Double zhundaijikasum = 0.00;
					Double jijikasum = 0.00;
					Double zhifubaosum = 0.00;
					Double weixinsum = 0.00;
					Double yinliansum = 0.00;
					maplist.put("totalsum",totalsum);
					maplist.put("daijikasum",daijikasum);
					maplist.put("zhundaijikasum",zhundaijikasum);
					maplist.put("jijikasum",jijikasum);
					maplist.put("zhifubaosum",zhifubaosum);
					maplist.put("weixinsum",weixinsum);
					maplist.put("yinliansum",yinliansum);

					ybdmachinesum(map,maplist);
					totalsum = maplist.get("totalsum");
					daijikasum = maplist.get("daijikasum");
					zhundaijikasum = maplist.get("zhundaijikasum");
					jijikasum = maplist.get("jijikasum");
					zhifubaosum = maplist.get("zhifubaosum");
					weixinsum = maplist.get("weixinsum");
					yinliansum = maplist.get("yinliansum");

					sl.put("交易总金额",totalsum);
					sl.put("贷记卡",daijikasum);
					sl.put("准贷记卡",zhundaijikasum);
					sl.put("借记卡",jijikasum);
					sl.put("支付宝",zhifubaosum);
					sl.put("微信支付",weixinsum);
					sl.put("银联二维码",yinliansum);

				}else {
					//查询员工交易合
					List<Map<String, Object>> tlist = iReportManageTransactionDao.queryManagetradeSum(map);
					if (tlist!=null && tlist.size()>0){
						String totalsum = tlist.get(0).get("totalsum").toString();
						String daijikasum = tlist.get(0).get("daijikasum").toString();
						String zhundaijikasum = tlist.get(0).get("zhundaijikasum").toString();
						String jijikasum = tlist.get(0).get("jijikasum").toString();
						String zhifubaosum = tlist.get(0).get("zhifubaosum").toString();
						String weixinsum = tlist.get(0).get("weixinsum").toString();
						String yinliansum = tlist.get(0).get("yinliansum").toString();
						sl.put("交易总金额",totalsum);
						sl.put("贷记卡",daijikasum);
						sl.put("准贷记卡",zhundaijikasum);
						sl.put("借记卡",jijikasum);
						sl.put("支付宝",zhifubaosum);
						sl.put("微信支付",weixinsum);
						sl.put("银联二维码",yinliansum);
					}else {
						sl.put("交易总金额","0.00");
						sl.put("贷记卡","0.00");
						sl.put("准贷记卡","0.00");
						sl.put("借记卡","0.00");
						sl.put("支付宝","0.00");
						sl.put("微信支付","0.00");
						sl.put("银联二维码","0.00");
					}
				}


			}
		}
		return  translist;
	}

}
