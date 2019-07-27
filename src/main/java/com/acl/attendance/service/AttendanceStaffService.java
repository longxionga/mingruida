package com.acl.attendance.service;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 *className:AttendanceStaffService
 *author:longxionga
 *createDate:2019年5月25日 上午10:04:31
 *department:铭锐达
 *description:
 */
public interface AttendanceStaffService {
	/**
	 * 查询员工考勤信息
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querystaffworkattendanceinfo(HashMap<String, Object> paramsMap,
                                       PageBounds pageBounds);

	/**
	 * 新增考勤信息
	 * @param paHashMap
	 */
	void insertstaffworkattendanceinfo(HashMap<String, Object> paHashMap);

	/**
	 * 修改考勤信息
	 * @param paHashMap
	 */
	void updatestaffworkattendanceinfo(HashMap<String, Object> paHashMap);

	/**
	 * 删除考勤明细信息
	 * @param paHashMap
	 */
	void deletestaffworkattendancedetail(HashMap<String, Object> paHashMap);

	/**
	 * 考勤明细信息导入
	 * @param file
	 */
	void staffworkattendancedetailimport(String attID,File file) throws ParseException;

	/**
	 * 考勤明细信息导入
	 * @param paramsMap
	 */
	 int insertstaffworkattendancedetail(Map<String, Object> paramsMap);
}
