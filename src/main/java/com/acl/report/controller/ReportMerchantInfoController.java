package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportDataInfoService;
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
 * 描述:
 *
 * @author 胡锋
 * @create 2019-03-21 19:45
 */

@Controller
@RequestMapping("/report")
public class ReportMerchantInfoController extends CoreBaseController {


    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/merchantreport"})
    @RequestMapping("/merchantreport")
    public String CounterFeeInfo() {
        return "report/report_merchant_info";
    }


    @Autowired
    private IReportDataInfoService reportDataInfoService;
    /**
     * 手续费报表信息 ==仓储费
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/merchantreport" })
    @RequestMapping("/queryReportMerchantInfo")
    public Object queryReportMerchantInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                      @RequestParam(defaultValue = "0", required = false) Integer page,
                                      @RequestParam(defaultValue = "10", required = false) Integer rows,
                                      @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows);
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type=loginSession.getDept_type();
        //paramsMap.put("dept_id", loginSession.getDept_id());
        if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
        {
            paramsMap.put("create_day",paramsMap.get("begindate").toString().replaceAll("-",""));
            //paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
            //paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
        }

        if(dept_type.equals("4"))
        {
            paramsMap.put("agent_id", loginSession.getAgent_id());
        }
        if(dept_type.equals("3"))
        {
            paramsMap.put("settle_id", loginSession.getSettle_id());
        }
        if(dept_type.equals("2"))
        {
            paramsMap.put("ch_id", loginSession.getDept_id());
        }
        if(dept_type.equals("1"))
        {
            paramsMap.put("ce_id", loginSession.getDept_id());
        }
        if(dept_type.equals("5"))
        {
            paramsMap.put("dept_id", loginSession.getDept_id());
        }
        if(dept_type.equals("6"))
        {
            paramsMap.put("dept_id", loginSession.getDept_id());
        }

        //查询当天的代理商信息
        //获取登录的代理商新进行条件筛选，如果没有则为空。
        PageList<?> list = (PageList<?>)reportDataInfoService.queryReportMerchantInfo(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;

    }
}
