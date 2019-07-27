package com.acl.report.controller;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportStaffAchievementervice;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.MachineExcelUtil;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import net.sf.json.JSONArray;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月11日 上午10:48:57
 * department: 铭锐达
 * description:查询员工业绩提成报表查询
 */
@Controller
@RequestMapping("/report")
public class ReportStaffAchievementController extends CoreBaseController {
    Map<String,Object> map=new HashMap<>();

    @Autowired
    private IReportStaffAchievementervice iReportStaffAchievementervice;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/achievement"})
    @RequestMapping("/achievement")
    public String achievement() {
        return "report/report_staff_achievement";
    }

    /**
     * 查询员工交易流水业绩报表信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/achievement" })
    @RequestMapping("/queryStaffAchievementPageList")
    public Object queryStaffAchievementPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap) throws ParseException {
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
        {
            paramsMap.put("create_day",paramsMap.get("begindate").toString().replaceAll("-",""));
            String s =DateFormatUtil.getLastMonth(paramsMap.get("begindate").toString().replaceAll("-",""));
            paramsMap.put("last_day", s);
        }
        //查询
        PageList<?> list = (PageList<?>)iReportStaffAchievementervice.queryStaffAchievementPageList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }


    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/standardachievement"})
    @RequestMapping("/standardachievement")
    public String standardachievement() {
        return "report/report_standard_achievement";
    }

    /**
     * 统计员工业绩报明细表
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/standardachievement" })
    @RequestMapping("/queryStandardAchievementPageList")
    public Object queryStandardAchievementPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                                @RequestParam HashMap<String,Object> paramsMap) throws ParseException {
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
            paramsMap.put("datestate","-1");


        //查询
        PageList<?> list = (PageList<?>)iReportStaffAchievementervice.queryStandardAchievementPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/totalstandardachievement"})
    @RequestMapping("/totalstandardachievement")
    public String totalstandardachievement() {
        return "report/report_total_standard_achievement";
    }

    /**
     * 统计员工业绩汇总报表
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/totalstandardachievement" })
    @RequestMapping("/queryTotalStandardAchievementPageList")
    public Object queryTotalStandardAchievementPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                                   @RequestParam(defaultValue = "10", required = false) Integer rows,
                                                   @RequestParam HashMap<String,Object> paramsMap) throws ParseException {
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

       /* if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
        {
            String s = paramsMap.get("begindate").toString();

            String startdate =DateFormatUtil.getFirstDay(s);
            String enddate =DateFormatUtil.getLastDay(s);

            paramsMap.put("startdate",startdate.replaceAll("-",""));
            paramsMap.put("enddate", enddate.replaceAll("-",""));

            String lastmonth = DateFormatUtil.getLastMonth(s.replaceAll("-",""));

            String laststartdate =DateFormatUtil.getFirstDay(lastmonth);
            String lastenddate =DateFormatUtil.getLastDay(lastmonth);

            paramsMap.put("laststartdate",laststartdate.replaceAll("-",""));
            paramsMap.put("lastenddate", lastenddate.replaceAll("-",""));

        }else{
            paramsMap.put("datestate","-1");
        }*/

        if(!"".equals(StringUtils.checkString(paramsMap.get("start_date"))))
        {
            paramsMap.put("startdate",paramsMap.get("start_date").toString().replaceAll("-",""));
        }
        if(!"".equals(StringUtils.checkString(paramsMap.get("end_date"))))
        {
            paramsMap.put("enddate",paramsMap.get("end_date").toString().replaceAll("-",""));

        }
        paramsMap.put("datestate","-1");

        //查询
        PageList<?> list = (PageList<?>)iReportStaffAchievementervice.queryTotalStandardAchievementPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }

    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/standardachievementdetail"})
    @RequestMapping("/standardachievementdetail")
    public String standardachievementdetail() {
        return "report/report_standard_achievement_detail";
    }

    /**
     * 员工业绩详细信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/standardachievementdetail" })
    @RequestMapping("/queryStandardAchievementDPageList")
    public Object queryStandardAchievementDPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                                   @RequestParam(defaultValue = "0", required = false) Integer page,
                                                   @RequestParam(defaultValue = "10", required = false) Integer rows,
                                                   @RequestParam HashMap<String,Object> paramsMap) throws ParseException {
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
        PageList<?> list = (PageList<?>)iReportStaffAchievementervice.queryStandardAchievementDetailPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 查询商户达标后导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/standardachievementdetail" })
    @RequestMapping("/queryMerchantReportForExcel")
    public Object queryMerchantReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
        System.err.println("paramsMap:"+paramsMap);

        Map<String, Object> count =iReportStaffAchievementervice.countMerchantInfo(paramsMap,loginSession);
        JSONObject object=new JSONObject();
        int num = StringUtils.checkInt(count.get("mcount"));
        object.put("num", num);
        object.put("url", "exportMerchantReportInfo");
        map=paramsMap;
        return object;
    }

    /**
     * 商户达标导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/report/standardachievementdetail" })
    @RequestMapping("/exportMerchantReportInfo")
    public void exportMerchantReportInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam Map<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
//        //查询代理商信息
        List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();

        list = iReportStaffAchievementervice.exportMerchantReportInfo(map,loginSession);
        if(list.size()>0){
            MachineExcelUtil.MerchantbuildXSLXExcel(list, "商户达标信息", response);
            map.clear();
        }

    }
}
