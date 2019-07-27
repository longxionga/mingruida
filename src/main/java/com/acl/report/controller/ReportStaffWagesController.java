package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformMachineInfoService;
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
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月11日 上午10:48:57
 * department: 铭锐达
 * description:查询员工提成报表查询
 */
@Controller
@RequestMapping("/report")
public class ReportStaffWagesController extends CoreBaseController {
    @Autowired
    private IReportStaffWagesService iReportStaffWagesService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/staffWages"})
    @RequestMapping("/staffWages")
    public String staffWages() {
        return "report/report_staff_wages";
    }

    /**
     * 查询员工报表信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/staffWages" })
    @RequestMapping("/queryStaffWagesPageList")
    public Object queryStaffWagesPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
        {
            paramsMap.put("create_day",paramsMap.get("begindate").toString().replaceAll("-",""));
            //paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
            //paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
        }
        //查询
        PageList<?> list = (PageList<?>)iReportStaffWagesService.queryStaffWagesPageList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }


}
