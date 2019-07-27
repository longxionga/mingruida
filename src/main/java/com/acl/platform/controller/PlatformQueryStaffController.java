package com.acl.platform.controller;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformCompanyStaffService;
import com.acl.pojo.LoginSession;
import com.acl.pojo.SysFile;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import java.util.Map;


/**
 * className:PlatformRefundvouchersController
 * author:longxionga
 * createDate:2019年5月7日 上午10:48:57
 * department: 铭锐达
 * description:查询直营员工信息
 */
@Controller
@RequestMapping("/platform")
public class PlatformQueryStaffController extends CoreBaseController {
    @Autowired
    private IPlatformCompanyStaffService iPlatformCompanyStaffService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/platform/queryStaffInfo"})
    @RequestMapping("/queryStaffInfo")
    public String queryStaffInfo() {
        return "platform/platform_query_staff";
    }

    /**
     * 查询直营员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/queryStaffInfoPageList")
    public Object queryStaffInfoPageList(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                @RequestParam(defaultValue = "0", required = false) Integer page,
                                @RequestParam(defaultValue = "10", required = false) Integer rows,
                                @RequestParam HashMap<String,Object> paramsMap){
        PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));

        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

        String dept_id = loginSession.getDept_id();//用户所属部门
        String user_name = loginSession.getUser_name();
        paramsMap.put("user_name",user_name);
        paramsMap.put("dept_ids",dept_id);

        //查询
        PageList<?> list = (PageList<?>)iPlatformCompanyStaffService.queryCompanyStaffInfoPageList(paramsMap,pageBounds);
        Object json = this.getJsonMap(list);
        return json;
    }


    /**
     * 新增员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/insStaffInfo")
    public Object insStaffInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        try{
            //新增员工信息
           String  str = iPlatformCompanyStaffService.insCompanyStaff2(paramsMap,loginSession);
           if ("1".equals(str)){
               msg.setMsg("登陆员工信息异常，保存失败");
               msg.setSuccess(false);
           }else if ("2".equals(str)){
               msg.setMsg("主管上级不能是主管，保存失败");
               msg.setSuccess(false);
           }else if ("3".equals(str)){
               msg.setMsg("未找到登陆员工信息，保存失败");
               msg.setSuccess(false);
           }else if ("4".equals(str)){
               msg.setMsg("保存成功");
               msg.setSuccess(true);
           }else if ("5".equals(str)){
               msg.setMsg("保存失败，上级员工不存在");
               msg.setSuccess(false);
           }else {
               msg.setMsg("保存失败");
               msg.setSuccess(false);
           }


        }catch(Exception e){
            msg.setMsg("保存失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 修改员工信息
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/updStaffInfo")
    public Object updStaffInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
            iPlatformCompanyStaffService.updCompanyStaff(paramsMap);
            msg.setMsg("更新成功");
            msg.setSuccess(true);
        }catch(Exception e){
            msg.setMsg("更新失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 员工编号重复校验
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/staffCodeValidate")
    public Object staffCodeValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = iPlatformCompanyStaffService.staffValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 员工名称重复校验
     *
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/staffNamesValidate")
    public Object staffNamesValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
        List<Map<String, Object>> list = iPlatformCompanyStaffService.staffNameValidate(paramsMap);
        if (list.size() == 0) {
            msg.setSuccess(true);
        } else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 组员升职
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/queryStaffInfo" })
    @RequestMapping("/staffPromotion")
    public Object staffPromotion(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
        try{
           String str =  iPlatformCompanyStaffService.staffPromotion(paramsMap);
           if (str.equals("1")){
               msg.setMsg("升职成功");
               msg.setSuccess(true);
           }else   if (str.equals("2")){
               msg.setMsg("员工不存在，数据错误");
               msg.setSuccess(false);
           }else   if (str.equals("3")){
               msg.setMsg("升职失败，组员才能升职");
               msg.setSuccess(false);
           }else   if (str.equals("4")){
               msg.setMsg("升职失败，组员已升职");
               msg.setSuccess(false);
           }

        }catch(Exception e){
            msg.setMsg("升职失败");
            msg.setSuccess(false);
            e.printStackTrace();
        }
        return msg;
    }
}
