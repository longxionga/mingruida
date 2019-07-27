package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.report.service.ReportLotteryService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
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

@Controller
@RequestMapping("/report")
public class ReportLotteryController extends CoreBaseController {

    @Autowired
    private ReportLotteryService  reportLotteryService;

    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/lottery" })
    @RequestMapping("lottery")
    public String lottery(){
        return "/report/report_lottery";
    }


    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/lottery" })
    @RequestMapping("/queryLotteryInfo")
    public Object queryLotteryInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String,Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
        PageList<?> list = (PageList<?>)reportLotteryService.queryLotteryInfo(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);

        return json;
    }
}
