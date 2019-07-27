package com.acl.attendance.controller;

import com.acl.attendance.service.AttendanceStaffDetailService;
import com.acl.attendance.service.AttendanceStaffService;
import com.acl.core.CoreBaseController;
import com.acl.report.service.IReportStaffWagesService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * className:AttendanceStaffDetailController
 * author:longxionga
 * createDate:2019年5月11日 上午10:48:57
 * department: 铭锐达
 * description:员工考勤明细查询查询
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceStaffDetailController extends CoreBaseController {
    @Autowired
    private AttendanceStaffDetailService attendanceStaffDetailService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/attendance/querystaffworkattendancedetail"})
    @RequestMapping("/querystaffworkattendancedetail")
    public String staffWages() {
        return "attendance/attendance_staff_detail";
    }

    /**
     * 查询员工考勤明细
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/attendance/querystaffworkattendancedetail" })
    @RequestMapping("/queryStaffWorkAttendanceDetailPageList")
    public Object queryAttendanceStaffDetailPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

//        if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
//        {
//            paramsMap.put("attendance_time",paramsMap.get("begindate").toString().replaceAll("-",""));
//        }

        paramsMap.put("attendance_time",paramsMap.get("begindate").toString());
        //查询
        PageList<?> list = null;
        try {
            list = (PageList<?>)attendanceStaffDetailService.queryAttendanceStaffDetailPageList(paramsMap,pageBounds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object json = this.getJsonMap(list);
        return json;
    }


}
