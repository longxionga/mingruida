package com.acl.platform.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.platform.service.PlatformBrokerService;
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
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysIndexService;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.RandomValidateCode;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:PlatformFrontUserInfo author:wangli createDate:2016年8月25日 下午2:56:16
 * vsersion:3.0 department:安创乐科技 description:
 */
@Controller
@RequestMapping("/platform")
public class PlatformUserInfoController extends CoreBaseController {
	@Autowired
	private ISysIndexService sysIndexService;
	@Autowired
	private SysUserInfoService sysUserInfoService;
    @Autowired
    private IPlatformUserInfoService platformUserInfoService;
    @Autowired
    private PlatformBrokerService platformBrokerService;
    
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/userInfo")
    public String dictInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
	return "platform/platform_user_info";
    }

    /**
     * 查询前台用户信息
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/queryUserlogin")
    public Object queryUserlogin(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
    	//判断传过来的参数是否是带有解密
    	String decryption =StringUtils.checkString( paramsMap.get("decryption"));
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
		case "4"://代理商
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://代理商部门
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://经纪人
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
    	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
    	
    	if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}

		if(!"".equals(StringUtils.checkString(paramsMap.get("id_card"))))
		{
			paramsMap.put("id_card",paramsMap.get("id_card").toString());
		}
		//查询所有启用的用户
		paramsMap.put("status",1);
		paramsMap.put("account_type","01");
    	
   		PageList<?> list = (PageList<?>) platformUserInfoService.queryUserInfo(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap =null;
   		if(decryption.equals("decryption")){   			
   			//带有解密参数就不加****
   			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   		}else{
   			//listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   		}  		
   	     Object json = this.getJsonMap(listMap);
		 return json;
    }

    /**
     * 重置密码
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/agentUserInfo" })
    @RequestMapping("/updUserInfoPwd")
    public Object updateUserInfoPwd(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
	    // 重置密码
	    platformUserInfoService.updateUserInfoPwd(paramsMap);
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
	 * 重置密码
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequestMapping("/closeAll")
	public Object closeAll(@Session(create = false) SessionProvider session, HttpServletRequest request,
									@RequestParam HashMap<String, Object> paramsMap) {
		try {
			// 重置密码
			paramsMap.put("account_type","01");
			platformUserInfoService.updateCloseAll(paramsMap);
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
     * 重置密码
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/updUserWeiXinInfo")
    public Object updateUserWeiXinInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
	    // 重置密码
	    platformUserInfoService.updateUserWeiXinInfo(paramsMap);
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
     * 禁用
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/agentUserInfo" })
    @RequestMapping("/updUserInfoState")
    public Object updateUserInfoState(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
    	try {
    		if(paramsMap.get("status").equals("1")){
    			dbLog.setMethod_name("启用前台用户");
    		}else if(paramsMap.get("status").equals("0")){
    			dbLog.setMethod_name("禁用前台用户");
    		}	
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
	    // 禁用用户
	    platformUserInfoService.updateUserInfoState(paramsMap);
	    dbLog.setAction_message("成功，操作对象名称："+StringUtils.convertString(paramsMap.get("user_name")));
		dbLog.setIs_ok(0);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
		dbLog.setAction_message("失败");
	    dbLog.setIs_ok(1);
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}finally{
		dbLogService.insertLog(dbLog);
	}
	return msg;
    }

    /**
     * 查询用户所属代理商下的所有经纪人
     * @param session
     * @param request
     * @param page
     * @param rows
     * @param paramsMap
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/queryBrokers")
    public Object queryBrokers(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
    	paramsMap.put("agent_id", loginSession.getDept_id());
    	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
    	if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}	
    	
    	PageList<?> list = (PageList<?>) platformBrokerService.queryBrokers(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
		Object json = this.getJsonMap(listMap);
    
    	return json;  	
    }

    /**
     * 更改用户所属经纪人
     * @param session
     * @param request
     * @param paramsMap
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/updUserBroker")
    public Object updateUserBroker(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
    	try {
		dbLog.setMethod_name("用户更换经纪人");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
		
		String [] user_id=paramsMap.get("user_id").toString().split(",");
		//及时操作mongoDB里面的数据
		String user_ids = paramsMap.get("user_id").toString();
		paramsMap.put("user_ids", user_ids);
		/*if(user_id.length>0){
			//将修改信息保存到临时表中，多次修改采用先删除后添加的方式	
			paramsMap.put("user_id", user_id);
			platformUserInfoService.deleteUserBroker(paramsMap);
			for(int i=0;i<user_id.length;i++){
				HashMap<String,Object> tmp = new HashMap<String,Object>();	
				tmp.put("sys_id", UUIDGenerator.generate());
				tmp.put("broker_id",paramsMap.get("broker_id").toString());					
				tmp.put("user_id", user_id[i]);	
				platformUserInfoService.insertUserBroker(tmp);
		     }
		}*/
	    // 及时更换用户所属经纪人操作mysql
		paramsMap.put("user_id", user_id);
	    platformUserInfoService.updateUserBroker(paramsMap);
	    String user_name=StringUtils.convertString(paramsMap.get("user_name"));
	    dbLog.setAction_message("成功,用户："+user_name+"更改经纪人为："+StringUtils.convertString(paramsMap.get("broker_name")));
		dbLog.setIs_ok(0);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
		dbLog.setAction_message("失败");
	    dbLog.setIs_ok(1);
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}finally{
		dbLogService.insertLog(dbLog);
	}
	return msg;
    }

    
    /**
     * 前台用户注销
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/logoutUserInfoState")
    public Object logoutUserInfoState(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession"); 	
    	//查询是否有充值记录及相关数据
    	HashMap<String, Object> map =platformUserInfoService.selectUserCz(paramsMap);
    	long count = StringUtils.checkLong(map.get("count"));
    	String userwxid = StringUtils.checkString(map.get("open_id"));
    	String mobile=StringUtils.checkString(map.get("mobile"));
    	//查询该用户是否为高级经纪人
    	paramsMap.put("mobile", mobile);
    	List<Map<String, Object>> list=platformUserInfoService.queryBrokerLevel(paramsMap);
    	HashMap<String, Object> map1=new HashMap<>();         
    	if(list.size()!=0){
    		if(StringUtils.convertString(list.get(0).get("broker_level").toString()).equals("2")){
    			msg.setMsg("注销失败，该用户为高级经纪人不可注销！");
				msg.setSuccess(false);
				return msg;
    		}else{
    			 map1.put("user_mobile", mobile);
    			 map1.put("broker_mobile", mobile+"x");
    	         map1.put("broker_wx_id", StringUtils.convertString(list.get(0).get("broker_wx_id"))+"x");
    	         map1.put("broker_zf_wx_id", StringUtils.convertString(list.get(0).get("broker_zf_wx_id"))+"x");
    	         map1.put("status", "4");
    	         map1.put("broker_incode", "");
    	         //获取经纪人的broker_incode字段中的邀请码
    	         String [] brokers_incode=StringUtils.convertString(list.get(0).get("broker_incode")).split(",");    	         
    	         //循环匹配t_back_number_info表 并把相应的statsus改为不可用
    	         if(brokers_incode.length>0){
    	        	 for(int i=0;i<brokers_incode.length;i++){
    	        		 HashMap<String, Object> map2=new HashMap<>();    
        	        	 map2.put("broker_incode", brokers_incode[i]);
        	        	 map2.put("status", "0");
        	        	 platformUserInfoService.updateNumberState(paramsMap);
    	        	 }
    	        	
    	         }
    	         //把经纪人的broker_incode字段清空
    	         //注销用户同时注销经纪人信息 将经纪人状态改为4      
    	         platformUserInfoService.logoutBrokerInfoState(map1);
    		}
    	}
    	String mobiles="";
    	//获取当前系统时间
    	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    	
    	//时间截断为161012格式 加在微信id后面
    	String times=userwxid+tempDate.format(new java.util.Date()).substring(2,10).replace("-", "").trim();
    	paramsMap.put("user_last_update", tempDate.format(new java.util.Date()));
    	paramsMap.put("open_id", times);
    	try {
        dbLog.setMethod_name("前台用户注销");
    	dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
    	dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
		if(count>0){		   
	          mobiles= mobile+"";
	    }else{
	          mobiles = mobile+"";
	    }
		//手机号加密
    	paramsMap.put("mobile",mobiles);
        platformUserInfoService.logoutUserInfoState(paramsMap);
             
        dbLog.setAction_message("成功，用户："+StringUtils.convertString(paramsMap.get("name"))+"已注销");
		dbLog.setIs_ok(0);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
		dbLog.setAction_message("失败");
		dbLog.setIs_ok(1);
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}finally{
		dbLogService.insertLog(dbLog);
	}
	return msg;
    }

    /**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/sendPhoneCode")
	public Object sendPhoneCode(HttpServletRequest request, HttpServletResponse response,
			@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap){
		try {
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			paramsMap.put("loginName",loginSession.getUser_name());
			List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);			
			dbLog.setMethod_name("解密用户手机号短信验证码请求");
			dbLog.setCq_params(StringUtils.checkString(paramsMap.get("loginName")));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			dbLog.setCq_paramsAdd(StringUtils.checkString(loginSession.getUser_mobile()));
	//		判断已发送的短信验证码是否在1分钟之内不允许重新发送
			if(!"".equals(StringUtils.checkString(userList.get(0).get("decryption_code")))){
				if (System.currentTimeMillis() - StringUtils.checkLong(StringUtils.checkString(userList.get(0).get("decryption_code")).split(" ")[1]) < 1*60*1000) {
					msg.setMsg("短信验证码已经发送，请稍等");
					msg.setSuccess(true);
					dbLog.setAction_message("短信验证码已经发送，请稍等 "+StringUtils.checkString(userList.get(0).get("decryption_code")).split(" ")[0]);
					dbLog.setIs_ok(2);
					return msg;
				}
			}
			// 写进数据库
			paramsMap.put("user_id", loginSession.getUser_id());
			paramsMap.put("decryption_code", RandomValidateCode.getRandomNumber(4) + " " + System.currentTimeMillis());
			String textMessage = "您的解密验证码为： " + StringUtils.checkString(paramsMap.get("decryption_code")).split(" ")[0] + " ，三分钟内输入有效。";
			sysUserInfoService.sendPhoneCode(StringUtils.checkString(userList.get(0).get("user_mobile")), textMessage,"1");
			sysUserInfoService.updateUserInfo(paramsMap);
			session.setAttribute("loginName", paramsMap.get("loginName").toString());
			msg.setMsg("验证码短信发送成功");
			msg.setSuccess(true);
			dbLog.setAction_message(textMessage);
			dbLog.setIs_ok(0);
			return msg;
		} catch (Exception e) {
			msg.setMsg("验证码短信发送失败，请稍后再试");
			msg.setSuccess(false);
			dbLog.setAction_message(e.getMessage());
			dbLog.setIs_ok(1);
			e.printStackTrace();
			return msg;
		}finally {
			dbLogService.insertLog(dbLog);
		}
	}
	
	 /**
     * 比对验证码
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/userInfo" })
    @RequestMapping("/checkCode")
    public Object checkCode(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {   
    	// 比对验证码
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("loginName",loginSession.getUser_name());
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);
		String decryption_code=StringUtils.convertString(userList.get(0).get("decryption_code"));
		if(decryption_code!=null && !decryption_code.equals("")){
			decryption_code=decryption_code.substring(0,4);
		}
		if(decryption_code.equals(paramsMap.get("decryption_code").toString())){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
	return msg;
    }


	/**
	 * 禁用
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequestMapping("/updUserInfoidCardAuth")
	public Object updateUserInfoidCardAuth(@Session(create = false) SessionProvider session, HttpServletRequest request,
									  @RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		try {
			if(paramsMap.get("id_card_auth").equals("02")){
				dbLog.setMethod_name("身份认证通过");
			}else if(paramsMap.get("id_card_auth").equals("03")){
				dbLog.setMethod_name("身份认证打回，已拒绝");
			}
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			// 禁用用户
			platformUserInfoService.updateUserInfoidCardAuth(paramsMap);
			dbLog.setAction_message("成功，操作对象名称："+StringUtils.convertString(paramsMap.get("user_name")));
			dbLog.setIs_ok(0);
			msg.setMsg("操作成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			dbLog.setAction_message("失败");
			dbLog.setIs_ok(1);
			msg.setMsg("操作失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}finally{
			dbLogService.insertLog(dbLog);
		}
		return msg;
	}





	/**
	 * 重置密码
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequestMapping("/allDisAgree")
	public Object allDisAgree(@Session(create = false) SessionProvider session, HttpServletRequest request,
						   @RequestParam HashMap<String, Object> paramsMap) {
		try {
			String [] orders_id=paramsMap.get("order_ids").toString().split(",");
			for (int i=0;i<orders_id.length;i++){
				// 禁用用户
				paramsMap.put("status",2);
				paramsMap.put("id",orders_id[i]);
				platformUserInfoService.updateUserInfoState(paramsMap);
			}
			msg.setMsg("操作成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("操作失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
}
