package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportTradeWarningService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.PhoneCodeSSL;
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
 * className:ReportTradeWarningController
 * author:wang
 * createDate:2017年9月6日 上午9:45:37
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/report")
public class ReportTradeWarningController extends CoreBaseController {
    @Autowired
    private IReportTradeWarningService reportTradeWarningService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/userWarning"})
    @RequestMapping("userWarning")
    public String userWarningInfo() {
        return "/report/report_user_warning";
    }


    /**
     * 查询用户预警信息
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/userWarning"})
    @RequestMapping("/queryTradeWarning")
    public Object queryTradeWarning(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                    @RequestParam(defaultValue = "10", required = false) Integer rows,
                                    @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type = loginSession.getDept_type();
        switch (dept_type) {
            case "1"://交易中心
                paramsMap.put("ce_id", loginSession.getDept_id());
                break;
            case "2"://渠道
                paramsMap.put("ch_id", loginSession.getDept_id());
                break;
            case "3"://服务商
                paramsMap.put("settle_id", loginSession.getDept_id());
                break;
            case "4"://代理商
                paramsMap.put("agent_id", loginSession.getDept_id());
                break;
            case "5"://代理商部门
                paramsMap.put("DID", loginSession.getDept_id());
                break;
            case "6"://经纪人
                paramsMap.put("DID", loginSession.getDept_id());
                break;
        }
        String sortString = "";
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(sortString));
        if (paramsMap.get("sort") != null) {
            paramsMap.put("order_type", paramsMap.get("sort").toString() + " " + paramsMap.get("order").toString());
            pageBounds = new PageBounds(page, rows, Order.formString(paramsMap.get("sort").toString() + "." + paramsMap.get("order").toString()));
        }
        //查询经纪人流水报表
        PageList<?> list = (PageList<?>) reportTradeWarningService.queryReportTradeWarning(paramsMap, pageBounds);

        Object json=null;
        if(list.size()>0)
        {
            PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage3(list, paramsMap);
            json = this.getJsonMap(listMap);
        }else
        {
            json = this.getJsonMap(list);
        }
        //Object json = this.getJsonMap(list);
        return json;
    }

}
