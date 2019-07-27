package com.acl.report.service.impl;


import com.acl.platform.dao.IPlatformCompanyStaffDao;
import com.acl.platform.dao.IPlatformMachineOrderDao;
import com.acl.pojo.LoginSession;
import com.acl.report.dao.IReportStaffAchievementDao;
import com.acl.report.dao.IReportStaffWagesDao;
import com.acl.report.service.IReportStaffAchievementervice;
import com.acl.report.service.IReportStaffWagesService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.DoubleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
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
public class IReportStaffAchievementServiceImpl implements IReportStaffAchievementervice {

	@Autowired
	private IReportStaffAchievementDao iReportStaffAchievementDao;

	@Autowired
	private IPlatformCompanyStaffDao iPlatformCompanyStaffDao;

	@Autowired
	private IPlatformMachineOrderDao iPlatformMachineOrderDao;


	private  int mcont = 0;

	@Override
	public PageList<?> queryStaffAchievementPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		PageList<?>  stawalist = null ;
		if (paramsMap!=null && paramsMap.get("position")!=null && !"".equals(paramsMap.get("brindnameid"))){
			if(paramsMap.get("brindnameid").equals("6")){//乐刷刷宝
				 stawalist=iReportStaffAchievementDao.queryshuabaoStaffAchievementPageList(paramsMap, pageBounds);
			}

		}else {
           stawalist=iReportStaffAchievementDao.queryshuabaoStaffAchievementPageList(paramsMap, pageBounds);
		//	this.shuabaoStaffWages(stawalist,null);
        }
		return stawalist;
	}

    private void shuabaoStaffWages(PageList<?> stawalist, List<Map<String, Object>> mp) {
	    if (stawalist!=null && stawalist.size()>0){
            for (int i = 0; i < stawalist.size(); i++) {
				Map<String,Object> map = (Map<String, Object>) stawalist.get(i);
				String s= String.valueOf(map.get("id"));
				Double jjkxkzf1= Double.valueOf(String.valueOf(map.get("jjkxkzf")));
				Double jjkkjzf1= Double.valueOf(String.valueOf(map.get("jjkkjzf")));
                if (mp!=null && mp.size()>0){
					for (int j = 0; j < mp.size(); j++) {
						Map<String, Object> map2 = mp.get(j);
						String p= String.valueOf(map2.get("parentid"));
						Double jjkxkzf2= Double.valueOf(String.valueOf(map2.get("jjkxkzf")));
						Double jjkkjzf2= Double.valueOf(String.valueOf(map2.get("jjkkjzf")));

						if (s.equals(p)){
							jjkxkzf1 = DoubleUtils.add(jjkxkzf1,jjkxkzf2);
							jjkkjzf1=DoubleUtils.add(jjkkjzf1,jjkkjzf2);
						}

					}

				}
				Double skzftc = DoubleUtils.mul(jjkxkzf1,0.0003);
				Double kjzftc = DoubleUtils.mul(jjkkjzf1,0.0002);
				Double hj= DoubleUtils.add(skzftc,kjzftc);
				Double shtc = DoubleUtils.mul(hj,0.92);
				map.put("jjkkjzf",jjkkjzf1);
				map.put("kjzftc",kjzftc);
				map.put("kjzffl","0.0002");
				map.put("jjkxkzf",jjkxkzf1);
				map.put("skzffl","0.0003");
				map.put("skzftc",skzftc);
				map.put("hj",hj);
				map.put("dksd","0.92%");
				map.put("shtc",shtc);
            }
        }

    }

	@Override
	public PageList<?> queryStandardAchievementPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		PageList<?>  stawalist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);
		}else {

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginname",loginname);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门

				if (dept_ids!=null && dept_ids.equals("3")){ //服务商登陆
					paramsMap.put("brand_id",ibrand_id);
				}else {
					paramsMap.put("orgid",orgid);
					paramsMap.put("brand_id",ibrand_id);
					paramsMap.put("branch",branch);
				}
				if (staffmap.get(0).containsKey("loginuserid") ){
					Object loginuserid = staffmap.get(0).get("loginuserid");//登陆账号未和员工信息绑定
					if (loginuserid==null || "".equals(loginuserid)){
						paramsMap.put("id","-1");
					}
				}
			}else{//账号未绑定员工
				paramsMap.put("id","-1");
			}
			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);
		}

//			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);

			if (stawalist!=null && stawalist.size()>0){
				for (Object o : stawalist) {
					HashMap<String,Object>  sl = (HashMap<String, Object>) o;
					String staffid = sl.get("id").toString();
					paramsMap.put("staffid",staffid);
					//查询员工后台机具
					List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantTotalSum(paramsMap);
					if (tlist!=null && tlist.size()>0){
						String tsum = tlist.get(0).get("mcount").toString();
						sl.put("tsum",tsum);
					}

					//查询员工后台未激活机具
					List<Map<String, Object>> wjhlist = iReportStaffAchievementDao.queryMerchantWJHSum(paramsMap);
					if (wjhlist!=null && wjhlist.size()>0){
						String wjhsum = wjhlist.get(0).get("mcount").toString();
						sl.put("wjhsum",wjhsum);
					}

					//查询员工已激活机具
					List<Map<String, Object>> jlist = iReportStaffAchievementDao.queryMerchantJHSum(paramsMap);
					if (jlist!=null && jlist.size()>0){
						String jsum = jlist.get(0).get("mcount").toString();
						sl.put("jsum",jsum);
					}else {
                        sl.put("jsum","0");
                    }

					// 查询员工已达标机具
					List<Map<String, Object>> dlist = iReportStaffAchievementDao.queryMerchantDBSum(paramsMap);
					if (dlist!=null && dlist.size()>0){
						String dsum = dlist.get(0).get("mcount").toString();
						sl.put("dsum",dsum);
					}else {
                        sl.put("dsum","0");
                    }

                    // 查询完成公司达标任务机具
//                    List<Map<String, Object>> gsdblist = iReportStaffAchievementDao.queryMerchantGSDBSum(paramsMap);
					// 查询完成公司达标任务机具，计算入网时间30天商户交易量
					List<Map<String, Object>> gsdblist = iReportStaffAchievementDao.queryMerchantGSDBSum_30(paramsMap);
                    if (gsdblist!=null && gsdblist.size()>0){
                        String gsdbsum = gsdblist.get(0).get("mcount").toString();
                        sl.put("gsdbsum",gsdbsum);
                    }else {
                        sl.put("gsdbsum","0");
                    }
				}
			}

		return stawalist;
	}

	/**
	 *  员工业绩汇总表
	 * @param paramsMap
	 * @param pageBounds
	 * @param loginSession
	 * @return
	 */
	@Override
	public PageList<?> queryTotalStandardAchievementPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) {
		PageList<?>  stawalist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		if (dept_ids!=null && dept_ids.equals("0")){//管理员和服务商权限
			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);
		}else {

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginname",loginname);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门

				if (dept_ids!=null && dept_ids.equals("3")){ //服务商登陆
					paramsMap.put("brand_id",ibrand_id);
				}else {
					paramsMap.put("orgid",orgid);
					paramsMap.put("brand_id",ibrand_id);
					paramsMap.put("branch",branch);
				}
				if (staffmap.get(0).containsKey("loginuserid") ){
					Object loginuserid = staffmap.get(0).get("loginuserid");//登陆账号未和员工信息绑定
					if (loginuserid==null || "".equals(loginuserid)){
						paramsMap.put("id","-1");
					}
				}
			}else{//账号未绑定员工
				paramsMap.put("id","-1");
			}
			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);
		}

//			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);

		if (stawalist!=null && stawalist.size()>0){
			for (Object o : stawalist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String staffid = sl.get("id").toString();
				paramsMap.put("staffid",staffid);

				//机具所属品牌
				String  brand_id = paramsMap.get("brand_id")!=null ? paramsMap.get("brand_id").toString() :"";
				// 开始时间
				String startdate =paramsMap.get("startdate")!=null ? paramsMap.get("startdate").toString():"";
				// 结束时间
				String enddate =paramsMap.get("enddate") !=null ? paramsMap.get("enddate").toString():"";

				//查询员工后台机具
				int machinenum = 0 ;
				Map<String,Integer> tsummap = new HashMap<>();
				tsummap.put("tsum",machinenum);
				tmachinesum(staffid,tsummap);
				int tsum = tsummap.get("tsum");
				sl.put("tsum",tsum);

				// 查询员工已达标机具
				int ybdsum = 0 ;
				Map<String,Integer> ybdmap = new HashMap<>();
				ybdmap.put("ybdsum",ybdsum);
				Map<String,Object> pybdmap = new HashMap<>();
				pybdmap.put("staffid",staffid);
				pybdmap.put("brand_id",brand_id);
				pybdmap.put("startdate",startdate);
				pybdmap.put("enddate",enddate);
				ybdmachinesum(pybdmap,ybdmap);
				ybdsum = ybdmap.get("ybdsum");

				sl.put("ybdsum",ybdsum);

//				// 查询员工已达标机具

				int ydbsum = 0 ;
				Map<String,Integer> ydbmap = new HashMap<>();
				ydbmap.put("ydbsum",ydbsum);
				Map<String,Object> pydbmap = new HashMap<>();
				pydbmap.put("staffid",staffid);
				pydbmap.put("brand_id",brand_id);
				pydbmap.put("startdate",startdate);
				pydbmap.put("enddate",enddate);
				ydbmachinesum(pydbmap,ydbmap);
				ydbsum = ydbmap.get("ydbsum");

				sl.put("ydbsum",ydbsum);

				// 查询员工完成公司达标机具
				int gsdbsum = 0 ;
				Map<String,Integer> gsdbmap = new HashMap<>();
				gsdbmap.put("gsdbsum",gsdbsum);
				Map<String,Object> pgsdbmap = new HashMap<>();
				pgsdbmap.put("staffid",staffid);
				pgsdbmap.put("brand_id",brand_id);
				pgsdbmap.put("startdate",startdate);
				pgsdbmap.put("enddate",enddate);
				gsdbmachinesum(pgsdbmap, gsdbmap);
				gsdbsum = gsdbmap.get("gsdbsum");

				sl.put("gsdbsum",gsdbsum);

			/*	//上月绑定本月达标
				String laststartdate =paramsMap.get("laststartdate")!=null ? paramsMap.get("laststartdate").toString():"";
				String lastenddate = paramsMap.get("lastenddate")!=null ? paramsMap.get("lastenddate").toString():"";
				int shydbsum = 0 ;
				Map<String,Integer> shydbmap = new HashMap<>();
				shydbmap.put("shydbsum",shydbsum);
				Map<String,Object> pshydbmap = new HashMap<>();
				pshydbmap.put("staffid",staffid);
				pshydbmap.put("brand_id",brand_id);
				pshydbmap.put("startdate",startdate);
				pshydbmap.put("enddate",enddate);

				pshydbmap.put("laststartdate",laststartdate);
				pshydbmap.put("lastenddate",lastenddate);

				shydbmachinesum(pshydbmap, shydbmap);
				shydbsum = shydbmap.get("shydbsum");

				sl.put("shydbsum",shydbsum);*/
			}
		}

		return stawalist;
	}

	//统计员工机具数量
    public  void tmachinesum (String  Staffid ,Map<String,Integer> sumap) {
		Map<String ,Object> smap = new HashMap<>();
		smap.put("staffid",Staffid);
		List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantTotalSum(smap);
       int machinenum =sumap.get("tsum");
		if (tlist!=null && tlist.size()>0){
			int b = Integer.parseInt( tlist.get(0).get("mcount").toString());
			machinenum =  machinenum+b;
		}
		sumap.put("tsum",machinenum);
		HashMap<String ,Object> ma = new HashMap<>();
         ma.put("parentid",Staffid);

        List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);

        if (staffmap!=null && staffmap.size()>0) {
            for (Map<String, Object> stringObjectMap : staffmap) {
                String sid = stringObjectMap.get("id").toString();
				tmachinesum(sid,sumap);
            };
        }
    }

	//统计员工已绑定机具数量
	public  void ybdmachinesum (Map<String,Object> paramap ,Map<String,Integer> sumap) {

		List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantJHSum(paramap);
		int machinenum =sumap.get("ybdsum");
		if (tlist!=null && tlist.size()>0){
			int b = Integer.parseInt( tlist.get(0).get("mcount").toString());
			machinenum =  machinenum+b;
		}
		sumap.put("ybdsum",machinenum);
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

	//统计员工已达标机具数量
	public  void ydbmachinesum (Map<String,Object> paramap ,Map<String,Integer> sumap) {

		List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantDBSum(paramap);
		int ybdsum =sumap.get("ydbsum");
		if (tlist!=null && tlist.size()>0){
			int b = Integer.parseInt( tlist.get(0).get("mcount").toString());
			ybdsum =  ybdsum+b;
		}
		sumap.put("ydbsum",ybdsum);
		HashMap<String ,Object> ma = new HashMap<>();
		String  Staffid = paramap.get("staffid").toString();
		ma.put("parentid",Staffid);

		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);

		if (staffmap!=null && staffmap.size()>0) {
			for (Map<String, Object> stringObjectMap : staffmap) {
				String sid = stringObjectMap.get("id").toString();
				paramap.put("staffid",sid);
				ydbmachinesum(paramap,sumap);
			};
		}
	}

	//统计员工公司达标机具数量
	public  void gsdbmachinesum (Map<String,Object> paramap ,Map<String,Integer> sumap) {

		List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantGSDBSum_30(paramap);
		int gsdbsum =sumap.get("gsdbsum");
		if (tlist!=null && tlist.size()>0){
			int b = Integer.parseInt( tlist.get(0).get("mcount").toString());
			gsdbsum =  gsdbsum+b;
		}
		sumap.put("gsdbsum",gsdbsum);
		HashMap<String ,Object> ma = new HashMap<>();
		String  Staffid = paramap.get("staffid").toString();
		ma.put("parentid",Staffid);

		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);

		if (staffmap!=null && staffmap.size()>0) {
			for (Map<String, Object> stringObjectMap : staffmap) {
				String sid = stringObjectMap.get("id").toString();
				paramap.put("staffid",sid);
				gsdbmachinesum(paramap,sumap);
			};
		}
	}

	//统计员上月绑定本月达标机具数量
	public  void shydbmachinesum (Map<String,Object> paramap ,Map<String,Integer> sumap) {

		List<Map<String, Object>> tlist = iReportStaffAchievementDao.queryMerchantSHYGSDBSum(paramap);
		int shydbsum =sumap.get("shydbsum");
		if (tlist!=null && tlist.size()>0){
			int b = Integer.parseInt( tlist.get(0).get("mcount").toString());
			shydbsum =  shydbsum+b;
		}
		sumap.put("shydbsum",shydbsum);
		HashMap<String ,Object> ma = new HashMap<>();
		String  Staffid = paramap.get("staffid").toString();
		ma.put("parentid",Staffid);

		List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);

		if (staffmap!=null && staffmap.size()>0) {
			for (Map<String, Object> stringObjectMap : staffmap) {
				String sid = stringObjectMap.get("id").toString();
				paramap.put("staffid",sid);
				shydbmachinesum(paramap,sumap);
			};
		}
	}

	/**
	 * 	员工业绩详细信息
	 * @param paramsMap
	 * @param pageBounds
	 * @param loginSession
	 * @return
	 */
	@Override
	public PageList<?> queryStandardAchievementDetailPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds, LoginSession loginSession) throws ParseException {
		PageList<?>  stawalist = null ;

		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			stawalist=iReportStaffAchievementDao.queryStandardAchievementDetailPageList(paramsMap, pageBounds);
		}else {

//			Map<String,Object> ma= new HashMap<String, Object>();
//			ma.put("loginname",loginname);
//			PageList<?> pageList = new PageList<>();
//			//根据登陆名称查询员工信息
//			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);

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
//                    paramsMap.put("orgid",orgid);
//                    paramsMap.put("brand_id",ibrand_id);
//                    paramsMap.put("branch",branch);
				}
			}else{//账号未绑定员工
				paramsMap.put("id","-1");
			}
			stawalist=iReportStaffAchievementDao.queryStandardAchievementDetailPageList(paramsMap, pageBounds);
		}

//			stawalist=iReportStaffAchievementDao.queryStandardAchievementPageList(paramsMap, pageBounds);

		if (stawalist!=null && stawalist.size()>0){
			for (Object o : stawalist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String merchant_code = sl.get("merchant_code").toString();
				HashMap<String ,Object> nummap = new HashMap<>();
				nummap.put("merchantcode",merchant_code);

				String bind_day = sl.get("bind_day").toString();// 商户入网时间
				//根据商户编号统计商户一个月交易量
				List<Map<String, Object>> clist = iReportStaffAchievementDao.merchantTradeCount(nummap);
				if (clist!=null && clist.size()>0){
					String mcount = clist.get(0).get("mcount").toString();
					sl.put("mcount",mcount);
					int num =Integer.valueOf(mcount);
					if (num>= 2){  //商户达标标准，商户交易量大于2笔
						sl.put("state","达标");
						//达标商户显示 达标日期（第2笔交易时间）
						nummap.put("merchant_code",merchant_code);
						List<Map<String, Object>> numlist =iPlatformMachineOrderDao.queryMachineOrderById(nummap);
						String dbdate = numlist.get(0).get("trade_day").toString();
						sl.put("dbdate",dbdate);
					}else {

						// 获取入网时间下个月时间 ， 如果大于当前时间 ， 商户状态达标中，小于当前时间 ，商户未达标
							int lastday =Integer.valueOf(bind_day)+30;
						String strdate = DateFormatUtil.convertDate(new Date(),"yyyy-MM-dd").replaceAll("-","");
						if (lastday>=Integer.valueOf(strdate)){
							sl.put("state","达标中");
						}else {
							sl.put("state","未达标");
						}

					}

				}

			}
		}
		return stawalist;
	}

	public static void main(String[] args) {
		int lastday =Integer.valueOf(20190719)+30;
		System.out.println(lastday);
		String strdate = DateFormatUtil.convertDate(new Date(),"yyyy-MM-dd").replaceAll("-","");
		System.out.println(strdate);
	}

	@Override
	public Map<String, Object> countMerchantInfo(HashMap<String, Object> hashMap,LoginSession loginSession) {

		Map<String, Object> stawalist = null ;
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

		if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
			stawalist=iReportStaffAchievementDao.countMerchantInfo(hashMap);
		}else { //分公司经理登陆

			Map<String,Object> ma= new HashMap<String, Object>();
			ma.put("loginid",loginid);
			PageList<?> pageList = new PageList<>();
			//根据登陆名称查询员工信息
//            List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
			if (staffmap!=null && staffmap.size()>0){
				String orgid = staffmap.get(0).get("id").toString();//员工id
				String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
				String branch = staffmap.get(0).get("branch").toString();//部门
				String position = staffmap.get(0).get("position").toString();//岗位
				if (position!=null && position.equals("1")){ //分公司经理
					hashMap.put("branch",branch);
				}else {
//                    paramsMap.put("orgid",orgid);
//                    paramsMap.put("brand_id",ibrand_id);
//                    paramsMap.put("branch",branch);
				}

			}else{//账号未绑定员工
				hashMap.put("staffid","-1");
			}
			stawalist= iReportStaffAchievementDao.countMerchantInfo(hashMap);
		}
		// 查询代理商日报表符合条件的记录总数
		return stawalist;
	}

	@Override
	public List<Map<String, Object>> exportMerchantReportInfo(Map<String, Object> map,LoginSession loginSession) {

		List<Map<String, Object>> stawalist = null ;
		//根据登陆账户所属员工信息过滤数据‘
		String loginname = loginSession.getUser_name();//登陆用户名
		String dept_ids =loginSession.getDept_id(); //登陆用户部门id
		String loginid = loginSession.getUser_id();//登陆用户id

        if (dept_ids!=null && (dept_ids.equals("0") || dept_ids.equals("3"))){//管理员和服务商权限
            stawalist=iReportStaffAchievementDao.exportMerchantReportInfo(map);
        }else { //分公司经理登陆

            Map<String,Object> ma= new HashMap<String, Object>();
            ma.put("loginid",loginid);
            PageList<?> pageList = new PageList<>();
            //根据登陆名称查询员工信息
//            List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.querystaffInfoByType(ma);
			List<Map<String, Object>>  staffmap= iPlatformCompanyStaffDao.findStaffInfoById(ma);
            if (staffmap!=null && staffmap.size()>0){
                String orgid = staffmap.get(0).get("id").toString();//员工id
                String ibrand_id = staffmap.get(0).get("brandid").toString();//品牌
                String branch = staffmap.get(0).get("branch").toString();//部门
				String position = staffmap.get(0).get("position").toString();//岗位
                if (position!=null && position.equals("1")){ //分公司经理
					map.put("branch",branch);
                }else {
//                    paramsMap.put("orgid",orgid);
//                    paramsMap.put("brand_id",ibrand_id);
//                    paramsMap.put("branch",branch);
                }

            }else{//账号未绑定员工
				map.put("staffid","-1");
            }
			stawalist= iReportStaffAchievementDao.exportMerchantReportInfo(map);
        }

		if (stawalist!=null && stawalist.size()>0){
			for (Object o : stawalist) {
				HashMap<String,Object>  sl = (HashMap<String, Object>) o;
				String merchant_code = sl.get("商户编号").toString();
				HashMap<String ,Object> nummap = new HashMap<>();
				nummap.put("merchantcode",merchant_code);
				//根据商户编号统计商户一个月交易量

				List<Map<String, Object>> clist = iReportStaffAchievementDao.merchantTradeCount(nummap);
				if (clist!=null && clist.size()>0){
					String mcount = clist.get(0).get("mcount").toString();
					sl.put("交易大于100交易",mcount);
					int num =Integer.valueOf(mcount);
					if (num>= 2){  //商户达标标准，商户交易量大于2笔
						sl.put("是否达标","达标");
						//达标商户显示 达标日期（第2笔交易时间）
						nummap.put("merchant_code",merchant_code);
						List<Map<String, Object>> numlist =iPlatformMachineOrderDao.queryMachineOrderById(nummap);
						String dbdate = numlist.get(0).get("trade_day").toString();
						sl.put("达标日期",dbdate);
					}else {
						sl.put("是否达标","未达标");
					}

				}

			}
		}
		return  stawalist;
	}
}
