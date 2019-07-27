package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformExceptionMachineInfoService;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.pojo.LoginSession;
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
public class PlatformQueryExceptionMachineInfoController extends CoreBaseController {
    @Autowired
    private IPlatformExceptionMachineInfoService iPlatformExceptionMachineInfoService;


    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryExceptionMachineInfo"})
    @RequestMapping("/queryExceptionMachineInfo")
    public String queryExceptionMachineInfo() {
        return "platform/platform_query_exception_machine_info";
    }


    /**
     * 查询机具信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryExceptionMachineInfo" })
    @RequestMapping("/queryExceptionMachineInfoPageList")
    public Object queryExceptionMachineInfoPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer rows,
                                           @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        //查询
        PageList<?> list = (PageList<?>)iPlatformExceptionMachineInfoService.queryExceptionMachineInfoPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }





    /**
     *  保留机具信息到临时表，删除原数据
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryExceptionMachineInfo"})
    @RequestMapping("/removeMachineInfo")
    public Object removeMachineInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                        @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String machineid= request.getParameter("machine_id");


        String Str = null;
        try {
            Map<String ,Object> map = new HashMap<>();
            map.put("machineid",machineid);
            Str = (String) iPlatformExceptionMachineInfoService.removeMachineInfoAll(map);

        } catch (Exception e) {
            e.printStackTrace();
            msg.setSuccess(false);
            msg.setMsg("删除失败,请确认数据问题!");
        }
        if (Str!=null && !"".equals(Str)) {
            msg.setSuccess(true);
            msg.setMsg(Str);
        } else {
            msg.setSuccess(false);
            msg.setMsg("删除失败,请确认数据问题!");
        }
        return msg;
    }

    /**
     *  保留机具信息到临时表，删除原数据
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryExceptionMachineInfo"})
    @RequestMapping("/removeMachineInfoAll")
    public Object removeMachineInfoAll(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                       @RequestParam HashMap<String, Object> paramsMap) {
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String [] orders_id=paramsMap.get("order_ids").toString().split(",");
        String Str = null;
        try {
            for (int i=0;i<orders_id.length;i++){

                paramsMap.put("machineid",orders_id[i]);
                Str = (String) iPlatformExceptionMachineInfoService.removeMachineInfoAll(paramsMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg.setSuccess(false);
            msg.setMsg("删除失败,请确认数据问题!");
        }
        if (Str!=null && !"".equals(Str)) {
            msg.setSuccess(true);
            msg.setMsg(Str);
        } else {
            msg.setSuccess(false);
            msg.setMsg("删除失败,请确认数据问题!");
        }
        return msg;
    }

}
