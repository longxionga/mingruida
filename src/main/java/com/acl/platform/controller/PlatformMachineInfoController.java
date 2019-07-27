package com.acl.platform.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformCompanyStaffService;
import com.acl.platform.service.IPlatformMachineInfoService;
import com.acl.pojo.LoginSession;
import com.acl.pojo.SysFile;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.anchol.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
public class PlatformMachineInfoController extends CoreBaseController {
    @Autowired
    private IPlatformMachineInfoService iPlatformMachineInfoService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/machineInfo"})
    @RequestMapping("/machineInfo")
    public String machineInfo() {
        return "platform/platform_machine_info";
    }

    /**
     * 查询机具信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/machineInfo" })
    @RequestMapping("/queryMachineInfoPageList")
    public Object queryMachineInfoPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
     * 删除机具信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/machineInfo" })
    @RequestMapping("/deleteMachineInfoAll")
    public Object deleteMachineInfoAll(@Session(create = false) SessionProvider session, HttpServletRequest request,
                                @RequestParam HashMap<String, Object> paramsMap) {
        try {
            iPlatformMachineInfoService.deleteMachineInfoAll(paramsMap);
            msg.setMsg("操作成功");
            msg.setSuccess(true);
        } catch (Exception e) {
            msg.setMsg("操作失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     *  机具转移所属员工操作
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/machineInfo"})
    @RequestMapping("/savaMachaneToStaff")
    public Object savaMachaneToStaff(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                   @RequestParam HashMap<String, Object> paramsMap) {

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        boolean retstr = (boolean) iPlatformMachineInfoService.savaMachaneToStaff(paramsMap,loginSession);
        if (retstr) {
            msg.setSuccess(true);
            msg.setMsg("转移成功!");
        } else {
            msg.setSuccess(false);
            msg.setMsg("转移失败,请确认数据问题!");
        }
        return msg;
    }



}
