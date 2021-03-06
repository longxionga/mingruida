package com.acl.report.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportSalesGoodsService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangli on 2017/11/16.
 */
@Controller
@RequestMapping("/report")
public class ReportSalesGoodsController extends CoreBaseController {
    @Autowired
    private IReportSalesGoodsService iReportSalesGoodsService;

    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/salesGoods" })
    @RequestMapping("salesGoods")
    public String salesGoods(){
        return "/report/report_sales_goods";
    }


    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/activityGoods" })
    @RequestMapping("activityGoods")
    public String activityGoods(){
        return "/report/report_activity_goods";
    }

    /**
     * 查询自营商品报表
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/salesGoods" })
    @RequestMapping("/querySalesGoodsReport")
    public Object querySalesGoodsReport(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                       @RequestParam(defaultValue = "0", required = false) Integer page,
                                       @RequestParam(defaultValue = "10", required = false) Integer rows,
                                       @RequestParam HashMap<String,Object> paramsMap){
        paramsMap.put("type",1);//自营商品报表
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type=loginSession.getDept_type();
        switch (dept_type) {
            case "0":
                break;
            case "1"://交易中心
                paramsMap.put("ce_id", loginSession.getDept_id());
                break;
            case "2"://渠道
                paramsMap.put("ch_id", loginSession.getDept_id());
                break;
            case "3"://服务商
                paramsMap.put("settle_id", loginSession.getDept_id());
                break;
            default:
                paramsMap.put("dept_id", loginSession.getDept_id());
                break;
        }
        String sortString = "";
        if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
        {
            paramsMap.put("mobile",paramsMap.get("mobile").toString());
        }
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(sortString));
        //查询字典信息
        PageList<?> list = (PageList<?>)iReportSalesGoodsService.queryReportSalesGoods(paramsMap, pageBounds);
        PageList<?> listMap= PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
        Object json = this.getJsonMap(listMap);
        return json;
    }


    /**
     * 查询活动商品报表
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/report/activityGoods" })
    @RequestMapping("/queryActivityGoodsReport")
    public Object queryActivityGoodsReport(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String,Object> paramsMap){
        paramsMap.put("type",2);//活动商品报表
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type=loginSession.getDept_type();
        switch (dept_type) {
            case "0":
                break;
            case "1"://交易中心
                paramsMap.put("ce_id", loginSession.getDept_id());
                break;
            case "2"://渠道
                paramsMap.put("ch_id", loginSession.getDept_id());
                break;
            case "3"://服务商
                paramsMap.put("settle_id", loginSession.getDept_id());
                break;
            default:
                paramsMap.put("dept_id", loginSession.getDept_id());
                break;
        }
        String sortString = "";
        if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
        {
            paramsMap.put("mobile", paramsMap.get("mobile").toString());
        }
        PageBounds pageBounds = new PageBounds(page,rows, Order.formString(sortString));
        //查询字典信息
        PageList<?> list = (PageList<?>)iReportSalesGoodsService.queryReportSalesGoods(paramsMap, pageBounds);
        PageList<?> listMap= PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
        Object json = this.getJsonMap(listMap);
        return json;
    }

    /**
     * 统计
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryReportSalesSum")
    public Object queryReportSalesSum(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                    @RequestParam HashMap<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_type=loginSession.getDept_type();
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
            case "4"://服务商
                paramsMap.put("agent_id", loginSession.getDept_id());
                break;
            case "5"://服务商部门
                paramsMap.put("DID", loginSession.getDept_id());
                break;
            case "6"://代理商
                paramsMap.put("DID", loginSession.getDept_id());
                break;
        }
        //查询代理商信息
        List<HashMap<String, Object>> list =iReportSalesGoodsService.queryReportSalesSum(paramsMap);

        return list;

    }


}
