package com.acl.attendance.dao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import java.util.HashMap;
import java.util.Map;

/**
 *className:AttendanceStaffDao
 *author:longxionga
 *createDate:2019年5月25日 上午10:02:38
 *department:铭锐达
 *description:
 */
public interface AttendanceStaffDao {

	 /**
	 * 查询员工考勤
	 * @param paramsMap
	 * @param pageBounds
	 * @return
	 */
	PageList<?> querystaffworkattendanceinfo(HashMap<String, Object> paramsMap,
                                       PageBounds pageBounds);

	/**
	 * 新增员工考勤信息
	 * @param paHashMap
	 */
	void insertstaffworkattendanceinfo(HashMap<String, Object> paHashMap);

	/**
	 * 修改员工考勤明细
	 * @param paHashMap
	 */
	void updatestaffworkattendanceinfo(HashMap<String, Object> paHashMap);
	/**
	 * 删除员工考勤明细
	 * @param paHashMap
	 */
	void deletestaffworkattendancedetail(HashMap<String, Object> paHashMap);
	/**
	 *
	 * @param paramsMap
	 * @return
	 */
	int insertstaffworkattendancedetail(Map<String, Object> paramsMap);

}
