package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月7日 上午10:48:57
 * department: 铭锐达
 * description:查询机具信息
 */
@Controller
@RequestMapping("/platform")
public class PlatformQueryMachineInfoController extends CoreBaseController {
    Map<String,Object> map=new HashMap<>();

    @Autowired
    private IPlatformMachineInfoService iPlatformMachineInfoService;


    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMachineInfo"})
    @RequestMapping("/queryMachineInfo")
    public String queryMachineInfo() {
        return "platform/platform_query_machine_info";
    }


    /**
     * 查询机具信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryMachineInfo" })
    @RequestMapping("/getMachineInfoPageLists")
    public Object getMachineInfoPageLists(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer rows,
                                           @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        //查询
        PageList<?> list = (PageList<?>)iPlatformMachineInfoService.queryMachineInfoPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }


    /**
     *  机具调拨下级
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/machineAllocation")
    public Object machineAllocation(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                     @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try {
            String Str = (String) iPlatformMachineInfoService.machineAllocation(paramsMap,loginSession);
            if (Str!=null && !"".equals(Str)) {
                msg.setSuccess(true);
                msg.setMsg(Str);
            } else {
                msg.setSuccess(false);
                msg.setMsg("调拨失败,请确认数据问题!");
            }
        } catch (Exception e) {
            msg.setSuccess(false);
            msg.setMsg("调拨失败,请确认数据问题!");
        }

        return msg;
    }

    /**
     *  机具回调
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/machineCallback")
    public Object machineCallback(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                    @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String Str = (String) iPlatformMachineInfoService.machineCallback(paramsMap,loginSession);
        if (Str!=null && !"".equals(Str)) {
            msg.setSuccess(true);
            msg.setMsg(Str);
        } else {
            msg.setSuccess(false);
            msg.setMsg("回调失败,请确认数据问题!");
        }
        return msg;
    }

    /**
     *  机具回调
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/machineCallbackSingle")
    public Object machineCallbackSingle(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                  @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String machineid= request.getParameter("machine_id");
        String Str = (String) iPlatformMachineInfoService.machineCallbackSingle(paramsMap,loginSession);
        if (Str!=null && !"".equals(Str)) {
            msg.setSuccess(true);
            msg.setMsg(Str);
        } else {
            msg.setSuccess(false);
            msg.setMsg("回调失败,请确认数据问题!");
        }
        return msg;
    }


    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/findMachineInfo"})
    @RequestMapping("/findMachineInfo")
    public String findMachineInfo() {
        return "platform/platform_find_machine_info";
    }

    /**
     * 查询机具信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/findMachineInfo" })
    @RequestMapping("/findMachineInfoPageLists")
    public Object findMachineInfoPageLists(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                          @RequestParam(defaultValue = "10", required = false) Integer rows,
                                          @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        //查询
        PageList<?> list = (PageList<?>)iPlatformMachineInfoService.queryMachineInfoPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }

    /**
     * 查询机具信息后导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryMachineInfo" })
    @RequestMapping("/queryMachineReportForExcel")
    public Object queryMachineReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                               @RequestParam HashMap<String,Object> paramsMap){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

//        PageList<?> list = (PageList<?>)iPlatformMachineInfoService.queryMachineInfoPageList(paramsMap,pageBounds,loginSession);

        Map<String, Object> count =iPlatformMachineInfoService.countMachineInfo(paramsMap);
        JSONObject object=new JSONObject();
        int num = StringUtils.checkInt(count.get("num"));
        object.put("num", num);
        object.put("url", "exportMachineReportInfo");
        map=paramsMap;
        return object;
    }

    /**
     * 机具信息导出导出
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMachineInfo" })
    @RequestMapping("/exportMachineReportInfo")
    public void exportMachineReportInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                          @RequestParam Map<String,Object> paramsMap, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
//        //查询代理商信息
        List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
        list = iPlatformMachineInfoService.exportMachineReportInfo(map,loginSession);
        if(list.size()>0){
            MachineExcelUtil.buildXSLXExcel(list, "商户信息", response);
            map.clear();
        }

    }
}
