package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportTransactionService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MachineExcelUtil;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月11日 上午10:48:57
 * department: 铭锐达
 * description:流水统计查询报表
 */
@Controller
@RequestMapping("/report")
public class ReportTransactionController extends CoreBaseController {
    @Autowired
    private IReportTransactionService iReportTransactionService;

    Map<String,Object> map=new HashMap<>();
    Map<String,Object> managemap=new HashMap<>();

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/queryTransactionReport"})
    @RequestMapping("/queryTransactionReport")
    public String queryTransactionReport() {
        return "report/report_transaction";
    }

    /**
     * 查询员工流水统计
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/queryTransactionReport" })
    @RequestMapping("/queryTransactionPageList")
    public Object queryTransactionPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        if(!"".equals(StringUtils.checkString(paramsMap.get("start_date"))))
        {
            paramsMap.put("startdate",paramsMap.get("start_date").toString().replaceAll("-",""));
        }
        if(!"".equals(StringUtils.checkString(paramsMap.get("end_date"))))
        {
            paramsMap.put("enddate",paramsMap.get("end_date").toString().replaceAll("-",""));

        }
        //查询
        PageList<?> list = (PageList<?>)iReportTransactionService.queryTransactionPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }
    /**
     * 员工流水导出查询
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/queryTransactionReport" })
    @RequestMapping("/queryTradeReportForExcel")
    public Object queryTradeReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                              @RequestParam HashMap<String,Object> paramsMap){

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        if(!"".equals(StringUtils.checkString(paramsMap.get("start_date"))))
        {
            paramsMap.put("startdate",paramsMap.get("start_date").toString().replaceAll("-",""));
        }
        if(!"".equals(StringUtils.checkString(paramsMap.get("end_date"))))
        {
            paramsMap.put("enddate",paramsMap.get("end_date").toString().replaceAll("-",""));

        }

        Map<String, Object> count =iReportTransactionService.countTradeInfo(paramsMap,loginSession);
        JSONObject object=new JSONObject();
        int num = StringUtils.checkInt(count.get("mcount"));
        object.put("num", num);
        object.put("url", "exportTradeReportInfo");
        map=paramsMap;
        return object;
    }

    /**
     * 流水导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/queryTransactionReport" })
    @RequestMapping("/exportTradeReportInfo")
    public void exportTradeReportInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                         @RequestParam Map<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
//        //查询代理商信息
        List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
        list = iReportTransactionService.exportTradeReportInfo(map,loginSession);
        if(list.size()>0){
            MachineExcelUtil.TradebuildXSLXExcel(list, "员工流水", response);
            map.clear();
        }

    }

}
