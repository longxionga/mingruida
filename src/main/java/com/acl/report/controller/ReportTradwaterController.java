package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportTradwaterService;
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


/*** 
* @Description:  交易流水统计
* @Param:  
* @return:  
* @Author: 易昙
* @Date: 2019/7/25 
*/ 

@Controller
@RequestMapping("/report")
public class ReportTradwaterController extends CoreBaseController {




    @Autowired
    private IReportTradwaterService iReportTradwaterService;

    Map<String,Object> map=new HashMap<>();
    Map<String,Object> managemap=new HashMap<>();

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/queryTradwaterReport"})
    @RequestMapping("/queryTradwaterReport")
    public String queryTradwaterReport() {
        return "report/report_tradwater";
    }

    /**
     * 查询员工流水统计
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/queryTradwaterReport" })
    @RequestMapping("/queryTradwaterPageList")
    public Object queryTradwaterPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
        if(!"".equals(StringUtils.checkString(paramsMap.get("brindid"))))
        {
            paramsMap.put("brindid",paramsMap.get("brindid").toString());

        }


        System.err.println("map:"+paramsMap);
        //查询
        PageList<?> list = (PageList<?>)iReportTradwaterService.queryTradwaterPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }


    /***
    * @Description:  交易流水导出查询
    * @Param: [session, request, paramsMap]
    * @return: java.lang.Object
    * @Author: 易昙
    * @Date: 2019/7/25
    */

    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/queryTradwaterReport" })
    @RequestMapping("/queryTradwaterForExcel")
    public Object queryTradwaterForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
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

        if(!"".equals(StringUtils.checkString(paramsMap.get("brindid"))))
        {
            paramsMap.put("brindid",paramsMap.get("brindid").toString());

        }
        Map<String, Object> count =iReportTradwaterService.countTradwaterInfo(paramsMap,loginSession);
        JSONObject object=new JSONObject();
        int num = StringUtils.checkInt(count.get("mcount"));
        object.put("num", num);
        object.put("url", "exportTradwaterInfo");
        map=paramsMap;
        return object;
    }



    /**
     * 流水导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/queryTradwaterReport" })
    @RequestMapping("/exportTradwaterInfo")
    public void exportTradwaterInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                         @RequestParam Map<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
//        //查询代理商信息
        List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
        list = iReportTradwaterService.exportTradwaterInfo(map,loginSession);
        if(list.size()>0){
            MachineExcelUtil.TradwaterXSLXExcel(list, "交易流水", response);
            map.clear();
        }

    }


}
