package com.acl.attendance.service.impl;


import com.acl.attendance.dao.AttendanceStaffDetailDao;
import com.acl.attendance.service.AttendanceStaffDetailService;
import com.acl.report.dao.IReportStaffWagesDao;
import com.acl.report.service.IReportStaffWagesService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.DoubleUtils;
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
public class AttendanceStaffDetailServiceImpl implements AttendanceStaffDetailService {

	@Autowired
	private AttendanceStaffDetailDao attendanceStaffDetailDao;


	@Override
	public PageList<?> queryAttendanceStaffDetailPageList(HashMap<String, Object> paramsMap, PageBounds pageBounds) throws Exception {
		PageList<?>  stawalist = null ;

           stawalist=attendanceStaffDetailDao.queryAttendanceStaffDetailPageList(paramsMap, pageBounds);
			this.getdate(stawalist);

		return stawalist;
	}

    private void getdate(PageList<?> stawalist) throws  Exception {
		if (stawalist != null && stawalist.size() > 0) {
			for (int i = 0; i < stawalist.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) stawalist.get(i);

				String stratdate = String.valueOf(map.get("entry_time"));//入职日期

				String enddate = String.valueOf(map.get("departure_time"));//离职日期
				String sdete = String.valueOf(map.get("attendance_time"));//当前月份

				String state = String.valueOf(map.get("state"));//员工状态

				String firstdate = DateFormatUtil.getFirstDay(sdete);//当前月第一天
				String lsstdate = DateFormatUtil.getLastDay(sdete);//当前月最后一天
				if (stratdate != null && stratdate!="null") {
					String lastdate = DateFormatUtil.lastMonth(stratdate);//入职日期 下个月时间
					String followdate = DateFormatUtil.getSpecifiedDayAfter(lastdate);//入职日期 后一天时间
						if (enddate != null && enddate!="null" && !"".equals(enddate)) {//离职
							if (DateFormatUtil.comparetoTime(stratdate, firstdate)) {//本月入职
								if (DateFormatUtil.comparetoTime(stratdate, lsstdate)) {  //入职时间大于当月第一天 本月还未入职

								} else {
									if (DateFormatUtil.comparetoTime(enddate, lsstdate)) {// 当月入职 第二月离职 无责期 stratdate到lsstdate
										map.put("wzksrq", stratdate);
										map.put("wzjsrq", lsstdate);
										getdays(map,stratdate,lsstdate,"01");

										map.put("jxcsrq",stratdate);
										map.put("jxjsrq",lsstdate);
									} else { // 当月入职 当月月离职 无责期 stratdate到enddate
										map.put("wzksrq", stratdate);
										map.put("wzjsrq", enddate);
										getdays(map,stratdate,enddate,"01");
										map.put("jxcsrq",stratdate);
										map.put("jxjsrq",enddate);
									}

								}
							} else {//上月入职
								if (DateFormatUtil.comparetoTime(enddate, lsstdate)) { //上月入职本月未离职
									map.put("wzksrq", firstdate);
									map.put("wzjsrq", lastdate);
									//getdays(map,stratdate,lastdate,"01");
									map.put("yzksrq", followdate);
									map.put("yzjsrq", lsstdate);
									getdays(map,followdate,lsstdate,"02");

									map.put("jxcsrq",firstdate);
									map.put("jxjsrq",lsstdate);
								} else { //上月入职，本月离职
									if (DateFormatUtil.comparetoTime(lastdate, enddate)) {
										map.put("wzksrq", stratdate);
										map.put("wzjsrq", enddate);
										getdays(map,stratdate,enddate,"01");

										map.put("jxcsrq",stratdate);
										map.put("jxjsrq",enddate);
									} else {
										map.put("wzksrq", firstdate);
										map.put("wzjsrq", lastdate);
									//	getdays(map,stratdate,lastdate,"01");
										map.put("yzksrq", followdate);
										map.put("yzjsrq", enddate);
										getdays(map,followdate,enddate,"02");

										map.put("jxcsrq",firstdate);
										map.put("jxjsrq",enddate);
									}
								}

							}

					} else {//未离职
						if (DateFormatUtil.comparetoTime(stratdate, firstdate)) {  //入职时间大于当月第一天
							if (DateFormatUtil.comparetoTime(stratdate, lsstdate)) {  //入职时间大于当月第一天 本月还未入职

							} else {  // 当月入职 无责期 stratdate到lsstdate
								map.put("wzksrq", stratdate);
								map.put("wzjsrq", lsstdate);
								getdays(map,stratdate,lsstdate,"01");
								map.put("jxcsrq",stratdate);
								map.put("jxjsrq",lsstdate);
							}

						} else {//上月入职
							if (DateFormatUtil.comparetoTime(lastdate, firstdate)) {//上月入职  无责期 firstdate到lastdate
								map.put("wzksrq", firstdate);
								map.put("wzjsrq", lastdate);
								//getdays(map,firstdate,lastdate,"01");
								map.put("yzksrq", followdate);
								map.put("yzjsrq", lsstdate);
								getdays(map,firstdate,lsstdate,"02");
								map.put("jxcsrq",firstdate);
								map.put("jxjsrq",lsstdate);
							} else {
								map.put("yzksrq", firstdate);
								map.put("yzjsrq", lsstdate);
								getdays(map,firstdate,lsstdate,"02");
								map.put("jxcsrq",firstdate);
								map.put("jxjsrq",lsstdate);
							}
						}
					}


				}
			//	getdaysutil(map);


			}

		}
	}

	/**
	 * 获取员工的工作天数
	 * @param map
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private  int getdays( Map<String, Object> map,String startdate ,String enddate ,String s){
		//获取员工的工作天数
		int dnum = DateFormatUtil.differentDays(startdate,enddate);
		int startday = DateFormatUtil.getdays(startdate);
		int endday = DateFormatUtil.getdays(enddate);
		//员工休息天数
        int xxdays =0;
		//员工事假天数
		int sjdays= 0;
		//员工病假天数
		int bjdays= 0;
		//员工矿工天数
		int kgdays= 0;
		//迟到次数
		int cddays = 0;
		//迟到扣款
		double amd= 0.0;

		//空数据
		int nulls = 0;

		for (int i = startday ;i<=endday;i++){
			String strn = String.valueOf(i);
			String strs =map.get(strn).toString();
			if (strs!=null) {
				if (strs != null && !"".equals(strs)) {
					if (strs.indexOf("休息") >= 0) {
						xxdays = xxdays + 1;
					}

					if (strs.indexOf("事假") >= 0 || strs.indexOf("请假") >= 0 || strs.indexOf("婚假") >= 0 ) {
						sjdays = sjdays + 1;
					}
					if (strs.indexOf("病假") >= 0) {
						bjdays = bjdays + 1;
					}
					if (strs.indexOf("旷工") >= 0) {  //旷工暂时当请假处理
						kgdays = kgdays + 1;
					}
					if (strs.indexOf("迟到") >= 0) {
						cddays = cddays + 1;
						//迟到扣款  迟到：1-10分钟罚款10元，10-20分钟罚款20元，迟到30分钟以上(包含30分钟)扣除半天工资
						if (strs.indexOf("小时") >= 0) {
							amd = DoubleUtils.add(amd, 50);
						} else if (strs.indexOf("分钟") >= 0) {
							int ii = strs.indexOf("分钟");
                            int nn = strs.indexOf("迟到");
							String fz = strs.substring(nn+2, ii);
							int da = Integer.valueOf(fz);
							if (da > 0 && da <= 10) {
								amd = DoubleUtils.add(amd, 10);
							} else if (da > 10 && da <= 20) {
								amd = DoubleUtils.add(amd, 20);
							} else {
								amd = DoubleUtils.add(amd, 50);
							}

						}

					}
				}else {
					nulls = nulls+1;
				}
			}else {
				nulls = nulls+1;
			}
		}

//			map.put("wzgzrdays",dnum-xxdays-nulls);//无责工作日 天数
//			map.put("wzqjdays",sjdays);//无责请假 天数
//			map.put("wzbjdays",bjdays);//无责病假 天数
//			map.put("wzcddays",cddays);//无责迟到次数
//			map.put("wzcdkk",amd);     //无责迟到扣款
//			map.put("wzsjgzts",dnum-xxdays-sjdays-bjdays-nulls);     //无责实际上班天数

			int ygqqts=dnum-xxdays-nulls;
			int ygsjts = dnum-xxdays-nulls-sjdays-bjdays-kgdays;
			map.put("dxts",dnum);//员工底薪应计天数

			map.put("ygqqts",ygqqts);//计薪期间员工全勤应有工作日 天数

			map.put("ygsjts",ygsjts);//员工实际工作日 天数
			map.put("sjdays",sjdays);//员工事假 天数
			map.put("bjdays",bjdays);//员工病假 天数

			//员工全勤200 迟到1天扣100 迟到2天没有全勤
			int qqsum = 0;
			int d = ygqqts-ygsjts;
			if (d>=0 && d<=2){
				qqsum = (2-d)*100;
			}
			map.put("qqsum",qqsum);//全勤

			map.put("cdkk",amd);//扣款


		return  dnum;
	}

	/**
	 * 获取员工的工作天数
	 * @param map
	 * @return
	 */
	private  int getdaysutil( Map<String, Object> map){

		int sjgzdays = Integer.valueOf(map.get("wzgzrdays")!=null ? map.get("wzgzrdays").toString():"0")+Integer.valueOf(map.get("yzgzrdays")!=null ? map.get("yzgzrdays").toString():"0");//员工实际工作天数
		map.put("sjgzdays",sjgzdays);
		int qqgzdays = Integer.valueOf(map.get("wzsjgzts")!=null ? map.get("wzsjgzts").toString():"0")+Integer.valueOf(map.get("yzsjgzts")!=null ? map.get("yzsjgzts").toString():"0");//员工全勤工作天数
		map.put("qqgzdays",qqgzdays);
		int bjdays =  Integer.valueOf(map.get("wzbjdays")!=null ? map.get("wzbjdays").toString():"0")+Integer.valueOf(map.get("yzbjdays")!=null ? map.get("yzbjdays").toString():"0");//病假天数
		map.put("bjdays",bjdays);
		int qjdays =  Integer.valueOf(map.get("wzqjdays")!=null ? map.get("wzqjdays").toString():"0")+Integer.valueOf(map.get("yzqjdays")!=null ? map.get("yzqjdays").toString():"0");//请假天数
		map.put("qjdays",qjdays);
		double sum1= Double.valueOf(map.get("wzcdkk")!=null ? map.get("wzcdkk").toString():"0");//无责迟到扣款
		double sum2 = Double.valueOf(map.get("yzcdkk")!=null ? map.get("yzcdkk").toString():"0");//有责迟到扣款

		double cdkkjeh = DoubleUtils.add(sum2,sum1);//迟到扣款金额合计
		map.put("cdkkjeh",cdkkjeh);
		int qqts = Integer.valueOf( map.get("day_count")!=null ? map.get("day_count").toString():"0");//全勤工作天数

		//员工全勤200 迟到1天扣100 迟到2天没有全勤
		int qqsum = 0;
		int d = qqts-sjgzdays;
		if (d>=0 && d<=2){
			qqsum = (2-d)*100;
		}
		map.put("qqsum",qqsum);
		return  sjgzdays;
	}

}
