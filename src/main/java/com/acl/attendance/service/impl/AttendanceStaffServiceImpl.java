package com.acl.attendance.service.impl;

import com.acl.attendance.dao.AttendanceStaffDao;
import com.acl.attendance.service.AttendanceStaffService;
import com.acl.utils.excel.*;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.text.ParseException;
import java.util.*;

/**
 *className:AttendanceStaffServiceImpl
 *author:longxionga
 *createDate:2019年5月25日 上午10:06:09
 *department:铭锐达
 *description:
 */
@Service
public class AttendanceStaffServiceImpl implements AttendanceStaffService {

	@Autowired
	private AttendanceStaffDao attendanceStaffDao;
	
	@Override
	public PageList<?> querystaffworkattendanceinfo(HashMap<String, Object> paramsMap, PageBounds pageBounds) {
		return attendanceStaffDao.querystaffworkattendanceinfo(paramsMap, pageBounds);
	}

	@Override
	public void insertstaffworkattendanceinfo(HashMap<String, Object> paHashMap) {
		paHashMap.put("id", UUIDGenerator.generate());
		paHashMap.put("insert_time", new Date());
		paHashMap.put("create_time", new Date());
		attendanceStaffDao.insertstaffworkattendanceinfo(paHashMap);
	}
	@Override
	public void updatestaffworkattendanceinfo(HashMap<String, Object> paHashMap) {
		paHashMap.put("update_time", new Date());
		attendanceStaffDao.updatestaffworkattendanceinfo(paHashMap);
	}

	@Override
	public void deletestaffworkattendancedetail(HashMap<String, Object> paHashMap) {
		attendanceStaffDao.deletestaffworkattendancedetail(paHashMap);
	}

	@Override
	public void staffworkattendancedetailimport(String attID,File file) throws ParseException {
		ParsingStaffAttendanceExcelUtil  parsingStaffAttendanceExcelUtil = new ParsingStaffAttendanceExcelUtil();
		BasicExcelDataEntity basicExcelDataEntity = parsingStaffAttendanceExcelUtil.getParsingExcelResult(file);
		List<StaffAttendanceinfo> attdetailInfoList = attDatailExcelToList(basicExcelDataEntity);
		if(CollectionUtil.isNotEmpty(attdetailInfoList)){
			List<Map> list = new ArrayList<>();
			for (int j=0;j<attdetailInfoList.size();j++){
				StaffAttendanceinfo staffAttendanceinfo =  attdetailInfoList.get(j);
				System.out.println(staffAttendanceinfo.toString());
				Map<String, Object> paramsMap = new HashMap<>();
				paramsMap.put("id", UUIDGenerator.generate());
				paramsMap.put("attendance_id",attID);
				paramsMap.put("staff_name", staffAttendanceinfo.getStaff_name());
				paramsMap.put("department", staffAttendanceinfo.getDepartment());
				paramsMap.put("staff_code", staffAttendanceinfo.getStaff_code());
				paramsMap.put("position",staffAttendanceinfo.getPosition());
				paramsMap.put("entry_time","".equals(staffAttendanceinfo.getEntry_time())?null:FormatDateUtil.StrToDate2(staffAttendanceinfo.getEntry_time().toString()));
				paramsMap.put("departure_time","".equals(staffAttendanceinfo.getDeparture_time())?null:FormatDateUtil.StrToDate2(staffAttendanceinfo.getDeparture_time().toString()));
				paramsMap.put("state",staffAttendanceinfo.getState());
				paramsMap.put("attendance_days",staffAttendanceinfo.getAttendance_days());
				paramsMap.put("rest_days",staffAttendanceinfo.getRest_days());
				paramsMap.put("working_hours", staffAttendanceinfo.getWorking_hours());
				paramsMap.put("late_ness", staffAttendanceinfo.getLate_ness());
				paramsMap.put("late_arrival_time", staffAttendanceinfo.getLate_arrival_time());
				paramsMap.put("severe_arrival_time", staffAttendanceinfo.getSevere_arrival_time());
				paramsMap.put("ads_late_days", staffAttendanceinfo.getAds_late_days());
				paramsMap.put("early_time", staffAttendanceinfo.getEarly_time());
				paramsMap.put("early_hours", staffAttendanceinfo.getEarly_hours());
				paramsMap.put("goto_absence_num", staffAttendanceinfo.getGoto_absence_num());
				paramsMap.put("gooff_absence_num", staffAttendanceinfo.getGooff_absence_num());
				paramsMap.put("abs_days", staffAttendanceinfo.getAbs_days());
				paramsMap.put("goout", staffAttendanceinfo.getGoout());
				paramsMap.put("compassionate_leave", staffAttendanceinfo.getCompassionate_leave());
				paramsMap.put("marriage_leave", staffAttendanceinfo.getMarriage_leave());
				paramsMap.put("special_leave", staffAttendanceinfo.getSpecial_leave());
				paramsMap.put("sick_leave", staffAttendanceinfo.getSick_leave());
				paramsMap.put("road_leave", staffAttendanceinfo.getRoad_leave());
				paramsMap.put("h_1", staffAttendanceinfo.getH_1());
				paramsMap.put("h_2", staffAttendanceinfo.getH_2());
				paramsMap.put("h_3", staffAttendanceinfo.getH_3());
				paramsMap.put("h_4", staffAttendanceinfo.getH_4());
				paramsMap.put("h_5", staffAttendanceinfo.getH_5());
				paramsMap.put("h_6", staffAttendanceinfo.getH_6());
				paramsMap.put("h_7", staffAttendanceinfo.getH_7());
				paramsMap.put("h_8", staffAttendanceinfo.getH_8());
				paramsMap.put("h_9", staffAttendanceinfo.getH_9());
				paramsMap.put("h_10", staffAttendanceinfo.getH_10());
				paramsMap.put("h_11", staffAttendanceinfo.getH_11());
				paramsMap.put("h_12", staffAttendanceinfo.getH_12());
				paramsMap.put("h_13", staffAttendanceinfo.getH_13());
				paramsMap.put("h_14", staffAttendanceinfo.getH_14());
				paramsMap.put("h_15", staffAttendanceinfo.getH_15());
				paramsMap.put("h_16", staffAttendanceinfo.getH_16());
				paramsMap.put("h_17", staffAttendanceinfo.getH_17());
				paramsMap.put("h_18", staffAttendanceinfo.getH_18());
				paramsMap.put("h_19", staffAttendanceinfo.getH_19());
				paramsMap.put("h_20", staffAttendanceinfo.getH_20());
				paramsMap.put("h_21", staffAttendanceinfo.getH_21());
				paramsMap.put("h_22", staffAttendanceinfo.getH_22());
				paramsMap.put("h_23", staffAttendanceinfo.getH_23());
				paramsMap.put("h_24", staffAttendanceinfo.getH_24());
				paramsMap.put("h_25", staffAttendanceinfo.getH_25());
				paramsMap.put("h_26", staffAttendanceinfo.getH_26());
				paramsMap.put("h_27", staffAttendanceinfo.getH_27());
				paramsMap.put("h_28", staffAttendanceinfo.getH_28());
				paramsMap.put("h_29", staffAttendanceinfo.getH_29());
				paramsMap.put("h_30", staffAttendanceinfo.getH_30());
				paramsMap.put("h_31", staffAttendanceinfo.getH_31());
				paramsMap.put("h_32", staffAttendanceinfo.getH_32());
				paramsMap.put("h_33", staffAttendanceinfo.getH_33());
				paramsMap.put("h_34", staffAttendanceinfo.getH_34());
				paramsMap.put("h_35", staffAttendanceinfo.getH_35());
				list.add(paramsMap);

			}
			Map m = new HashMap();
			m.put("itemMap",list);
			attendanceStaffDao.insertstaffworkattendancedetail(m);
		}
	}

	/**
	 *
	 * @param basicExcelDataEntity
	 * @return
	 */
	private  List<StaffAttendanceinfo> attDatailExcelToList(BasicExcelDataEntity basicExcelDataEntity) throws ParseException {
		Map<String,String> titleIndex = basicExcelDataEntity.getExcelTitleIndex();
		//sysBackDictInfoDao.
		List<StaffAttendanceinfo> basicInfoList = new ArrayList<>();
		System.out.println("titleIndex:"+titleIndex.toString());
		for(Map<String,String> map:basicExcelDataEntity.getExcelData()){
			System.out.println("excel:"+map.toString());
			StaffAttendanceinfo staffAttendanceinfo = new StaffAttendanceinfo();
			Map paramap1 = new HashMap();
			for (String str:basicExcelDataEntity.getExcelTitleMap().keySet()){
				String colName = basicExcelDataEntity.getExcelTitleMap().get(str);

				if( colName.equals("姓名")){
                    staffAttendanceinfo.setStaff_name_n(map.get(str));
					if (map.get(str)!=null && !"".equals(map.get(str))){
						String strname = "";
						int in = map.get(str).indexOf("（");
						if (in>=0){
							 strname = map.get(str).substring(0,in);
						}else {
							strname = map.get(str);
						}

						staffAttendanceinfo.setStaff_name(strname);
						int intnum = map.get(str).indexOf("离职");
						if (intnum>0){
							staffAttendanceinfo.setState("02");
						}else {
							staffAttendanceinfo.setState("01");
						}
					}else {
						staffAttendanceinfo.setStaff_name("");
					}
					continue;
				}
				if( colName.equals("部门")){
					staffAttendanceinfo.setDepartment(map.get(str));
					continue;
				}
				if( colName.equals("工号")){
					staffAttendanceinfo.setStaff_code(map.get(str));
					continue;
				}

				if( colName.equals("职位")){
					staffAttendanceinfo.setPosition(map.get(str));
					continue;
				}
				if(colName.equals("入职时间")){
					staffAttendanceinfo.setEntry_time(map.get(str));
					continue;
				}
				if( colName.equals("离职时间")){
					staffAttendanceinfo.setDeparture_time(map.get(str));

					if (!StringUtils.isBlank(map.get(str))){
						Date enddate = StringUtils.strtodate(map.get(str));
						if (enddate.getTime()<=new Date().getTime()){
							staffAttendanceinfo.setState("02");
						}
					}
					continue;
				}
				if( colName.equals("出勤天数")){
					staffAttendanceinfo.setAttendance_days(map.get(str));
					continue;
				}
				if( colName.equals("休息天数")){
					staffAttendanceinfo.setRest_days(map.get(str));
					continue;
				}
				if( colName.equals("工作时长")){
					staffAttendanceinfo.setWorking_hours(map.get(str));
					continue;
				}
				if( colName.equals("迟到次数")){
					staffAttendanceinfo.setLate_ness(map.get(str));
					continue;
				}
				if( colName.equals("迟到时长")){
					staffAttendanceinfo.setLate_arrival_time(map.get(str));
					continue;
				}
				if( colName.equals("严重迟到次数")){
					staffAttendanceinfo.setSevere_lateness(map.get(str));
					continue;
				}
				if( colName.equals("严重迟到时长")){
					staffAttendanceinfo.setSevere_arrival_time(map.get(str));
					continue;
				}
				if( colName.equals("旷工迟到天数")){
					staffAttendanceinfo.setAds_late_days(map.get(str));
					continue;
				}if( colName.equals("早退次数")){
					staffAttendanceinfo.setEarly_time(map.get(str));
					continue;
				}
				if( colName.equals("早退时长")){
					staffAttendanceinfo.setEarly_hours(map.get(str));
					continue;
				}if( colName.equals("上班缺卡次数")){
					staffAttendanceinfo.setGoto_absence_num(map.get(str));
					continue;
				}if( colName.equals("下班缺卡次数")){
					staffAttendanceinfo.setGooff_absence_num(map.get(str));
					continue;
				}if( colName.equals("旷工天数")){
					staffAttendanceinfo.setAbs_days(map.get(str));
					continue;
				}if( colName.equals("外出(天)")){
					staffAttendanceinfo.setGoout(map.get(str));
					continue;
				}if( colName.equals("事假(天)")){
					staffAttendanceinfo.setCompassionate_leave(map.get(str));
					continue;
				}
				if( colName.equals("婚假(天)")){
					staffAttendanceinfo.setMarriage_leave(map.get(str));
					continue;
				}
				if( colName.equals("特殊请假(天)")){
					staffAttendanceinfo.setSpecial_leave(map.get(str));
					continue;
				}
				if( colName.equals("病假(天)")){
					staffAttendanceinfo.setSick_leave(map.get(str));
					continue;
				}
				if( colName.equals("路途假(天)")){
					staffAttendanceinfo.setRoad_leave(map.get(str));
					continue;
				}
				if( colName.equals("1")){
					staffAttendanceinfo.setH_1(map.get(str));
					continue;
				}
				if( colName.equals("2")){
					staffAttendanceinfo.setH_2(map.get(str));
					continue;
				}
				if( colName.equals("3")){
					staffAttendanceinfo.setH_3(map.get(str));
					continue;
				}
				if( colName.equals("4")){
					staffAttendanceinfo.setH_4(map.get(str));
					continue;
				}
				if( colName.equals("5")){
					staffAttendanceinfo.setH_5(map.get(str));
					continue;
				}
				if( colName.equals("6")){
					staffAttendanceinfo.setH_6(map.get(str));
					continue;
				}

				if( colName.equals("7")){
					staffAttendanceinfo.setH_7(map.get(str));
					continue;
				}
				if( colName.equals("8")){
					staffAttendanceinfo.setH_8(map.get(str));
					continue;
				}
				if( colName.equals("9")){
					staffAttendanceinfo.setH_9(map.get(str));
					continue;
				}
				if( colName.equals("10")){
					staffAttendanceinfo.setH_10(map.get(str));
					continue;
				}
				if( colName.equals("11")){
					staffAttendanceinfo.setH_11(map.get(str));
					continue;
				}
				if( colName.equals("12")){
					staffAttendanceinfo.setH_12(map.get(str));
					continue;
				}
				if( colName.equals("13")){
					staffAttendanceinfo.setH_13(map.get(str));
					continue;
				}
				if( colName.equals("14")){
					staffAttendanceinfo.setH_14(map.get(str));
					continue;
				}
				if( colName.equals("15")){
					staffAttendanceinfo.setH_15(map.get(str));
					continue;
				}
				if( colName.equals("16")){
					staffAttendanceinfo.setH_16(map.get(str));
					continue;
				}
				if( colName.equals("17")){
					staffAttendanceinfo.setH_17(map.get(str));
					continue;
				}
				if( colName.equals("18")){
					staffAttendanceinfo.setH_18(map.get(str));
					continue;
				}
				if( colName.equals("19")){
					staffAttendanceinfo.setH_19(map.get(str));
					continue;
				}
				if( colName.equals("20")){
					staffAttendanceinfo.setH_20(map.get(str));
					continue;
				}
				if( colName.equals("21")){
					staffAttendanceinfo.setH_21(map.get(str));
					continue;
				}
				if( colName.equals("22")){
					staffAttendanceinfo.setH_22(map.get(str));
					continue;
				}
				if( colName.equals("23")){
					staffAttendanceinfo.setH_23(map.get(str));
					continue;
				}
				if( colName.equals("24")){
					staffAttendanceinfo.setH_24(map.get(str));
					continue;
				}
				if( colName.equals("25")){
					staffAttendanceinfo.setH_25(map.get(str));
					continue;
				}
				if( colName.equals("26")){
					staffAttendanceinfo.setH_26(map.get(str));
					continue;
				}
				if( colName.equals("27")){
					staffAttendanceinfo.setH_27(map.get(str));
					continue;
				}
				if( colName.equals("28")){
					staffAttendanceinfo.setH_28(map.get(str));
					continue;
				}
				if( colName.equals("29")){
					staffAttendanceinfo.setH_29(map.get(str));
					continue;
				}
				if( colName.equals("30")){
					staffAttendanceinfo.setH_30(map.get(str));
					continue;
				}
				if( colName.equals("31")){
					staffAttendanceinfo.setH_31(map.get(str));
					continue;
				}
				if( colName.equals("32")){
					staffAttendanceinfo.setH_32(map.get(str));
					continue;
				}
				if( colName.equals("33")){
					staffAttendanceinfo.setH_33(map.get(str));
					continue;
				}
				if( colName.equals("34")){
					staffAttendanceinfo.setH_34(map.get(str));
					continue;
				}
				if( colName.equals("35")){
					staffAttendanceinfo.setH_35(map.get(str));
					continue;
				}


			}

			if(org.apache.commons.lang3.StringUtils.isEmpty(staffAttendanceinfo.getStaff_name())
					|| org.apache.commons.lang3.StringUtils.isEmpty(staffAttendanceinfo.getStaff_name())){
				System.out.println("数据不完整,跳过处理:"+staffAttendanceinfo.toString());
				continue;
			}
			basicInfoList.add(staffAttendanceinfo);
		}
		return basicInfoList;
	}

	@Override
	public int insertstaffworkattendancedetail(Map<String, Object> paramsMap) {
		return attendanceStaffDao.insertstaffworkattendancedetail(paramsMap);
	}
}
