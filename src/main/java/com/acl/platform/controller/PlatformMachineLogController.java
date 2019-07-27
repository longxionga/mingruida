package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.platform.service.IPlatformMachineLogService;
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


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月7日 上午10:48:57
 * department: 铭锐达
 * description:查询机具调拨信息
 */
@Controller
@RequestMapping("/platform")
public class PlatformMachineLogController extends CoreBaseController {
    @Autowired
    private IPlatformMachineLogService iPlatformMachineLogService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryMachineLog"})
    @RequestMapping("/queryMachineLog")
    public String queryMachineLog() {
        return "platform/platform_machine_Log";
    }

    /**
     * 查询机具调拨信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryMachineLog" })
    @RequestMapping("/queryMachineLogPageList")
    public Object queryMachineLogPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        //查询
        PageList<?> list = (PageList<?>)iPlatformMachineLogService.queryMachineLogPageList(paramsMap,pageBounds,loginSession);
        Object json = this.getJsonMap(list);
        return json;
    }


}
