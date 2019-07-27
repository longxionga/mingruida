package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformMerchartInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MachineExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 描述:
 *
 * @author longxionga
 * @create 2019-07-01 11:45
 */
@Controller
@RequestMapping("/platform")
public class PlatformMerchatInfoController extends CoreBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformMerchatInfoController.class);
    Map<String,Object> map=new HashMap<>();

    @Autowired
    private IPlatformMerchartInfoService iPlatformMerchartInfoService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMerchantInfo"})
    @RequestMapping("/queryMerchantInfo")
    public String queryMerchantInfo() {
        return "platform/platform_query_merchant_info";
    }


    /**
     * 商户信息分页查询
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMerchantInfo"})
    @RequestMapping("/queryMerchantInfoPageLists")
    public Object queryMerchantInfoPageLists(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0", required = false) Integer page,
                                        @RequestParam(defaultValue = "10", required = false) Integer rows,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));

        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }
        PageList<?> list = (PageList<?>) iPlatformMerchartInfoService.queryMerchantInfoPageLists(paramsMap, pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 查询查询商户审状态下拉框
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryMerchantInfo" })
    @RequestMapping("/getMerchantAuditStartsCombobox")
    public Object getMerchantAuditStartsCombobox(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer rows,
                                     @RequestParam HashMap<String,Object> paramsMap){
//        paramsMap.put("status","01");
        List<Map<String,Object>> list = iPlatformMerchartInfoService.getMerchantAuditStartsCombobox(paramsMap);
        return list;
    }

    /**
     * 查询商户信息导出数量统计
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryMerchantInfo" })
    @RequestMapping("/queryMerchantInfoReportForExcel")
    public Object queryMerchantInfoReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                              @RequestParam HashMap<String,Object> paramsMap){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        if (paramsMap.get("start_date") != null) {
            paramsMap.put("start_date", StringUtils.replace((String) paramsMap.get("start_date"),"-",""));
        }
        if (paramsMap.get("end_date") != null) {
            paramsMap.put("end_date", StringUtils.replace((String) paramsMap.get("end_date"),"-",""));
        }

        Map<String, Object> count =iPlatformMerchartInfoService.countMerchantInfo(paramsMap);
        JSONObject object=new JSONObject();
        int num = com.acl.utils.util.StringUtils.checkInt(count.get("mcount"));
        object.put("num", num);
        object.put("url", "exportMerchantInfoReportInfo");
        map=paramsMap;
        return object;
    }

    /**
     * 商户信息导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMerchantInfo" })
    @RequestMapping("/exportMerchantInfoReportInfo")
    public void exportMerchantInfoReportInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                         @RequestParam Map<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
//        //查询代理商信息
        List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
        list = iPlatformMerchartInfoService.exportMerchantInfoReportInfo(map);
        if(list.size()>0){
            MachineExcelUtil.MerchantInfobuildXSLXExcel(list, "商户信息", response);
            map.clear();
        }

    }

}

