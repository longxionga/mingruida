package com.acl.platform.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformCommonService;
import com.acl.platform.service.IPlatformSimUserInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * 模拟用户操作
 * @author hufeng
 *
 */
@Controller
public class PlatformSimUserInfoController extends CoreBaseController {
	
    @Autowired
    private IPlatformSimUserInfoService platformSimUserInfoService;
    
    @Autowired
    private static IPlatformCommonService platformCommonService;
    
    
	@RequiresAuthentication
    @RequiresPermissions(value = {"/platform/simUserInfo" })
    @RequestMapping("/platform/simUserInfo")
    public String simUserInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
	return "platform/platform_sim_user_info";
    }
 
    /**
     * 查询前台用户信息
     * 
     * @return
     */
    @ResponseBody
    @RequiresAuthentication
    @RequiresPermissions(value = { "/platform/simUserInfo" })
    @RequestMapping("/platform/querySimUserlogin")
    public Object querySimUserlogin(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	
    	//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
    	//判断传过来的参数是否是带有解密
    	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
    	
    	if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("user_mobile",paramsMap.get("mobile").toString());
		}	
    	
   		PageList<?> list = (PageList<?>) platformSimUserInfoService.querySimUserInfo(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage2(list, paramsMap);
   	     Object json = this.getJsonMap(listMap);
		 return json;
    }

	/**
	 * 插入后台用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = {"/platform/simUserInfo" })
	@RequestMapping("/platform/saveSimUserInfo")
	public Object saveSimUserInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		try {
	    	if(!"".equals(StringUtils.checkString(paramsMap.get("user_mobile"))))
			{
				paramsMap.put("user_mobile",paramsMap.get("user_mobile").toString());
			}	
	    	
	    	
	    	Map<String, Object> userInfoMap = platformSimUserInfoService.querySimUserInfoByMap(paramsMap);
			if(userInfoMap != null){
				msg.setMsg("手机号码【"+(String)paramsMap.get("user_mobile")+"】已经添加！");
				msg.setSuccess(false);
				return msg;
			}
			
			//保存用户基本信息
			platformSimUserInfoService.saveSimUserInfo(paramsMap);
			
			msg.setMsg("新增成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("插入失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	
	

	/**
	 * 修改后台用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = {"/platform/simUserInfo" })
	@RequestMapping("/platform/updSimUserInfo")
	public Object updateSimUserInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		try {


			
	    	if(!"".equals(StringUtils.checkString(paramsMap.get("user_mobile"))))
			{
				paramsMap.put("user_mobile",paramsMap.get("user_mobile").toString());
			}	
	    	
			Map<String, Object> userInfoMap = platformSimUserInfoService.querySimUserInfoByMap(paramsMap);
			if(userInfoMap == null){
				msg.setMsg("更新失败,注册信息不存在！");
				msg.setSuccess(false);
				return msg;
			}
			platformSimUserInfoService.updateSimUserLogonWalletProfitLoss(paramsMap,userInfoMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 重置后台用户登陆密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = {"/platform/simUserInfo" })
	@RequestMapping("/platform/resetSimPwd")
	public Object resetSimPwd(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		// 重置后台登陆密码为SystemConfig.AncholPWD
		paramsMap.put("user_id", (String)paramsMap.get("user_id"));
		paramsMap.put("user_password", MD5Utils.MD5("888888"));
		try {
			platformSimUserInfoService.resetSimUserInfoPwd(paramsMap);
			msg.setMsg("重置成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("重置失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}	
    
}
