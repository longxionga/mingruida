package com.acl.sys.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.ShiroToken;
import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.SysBackMenuService;
import com.acl.sys.service.ISysCenterInfoService;
import com.acl.sys.service.ISysIndexService;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.MD5Utils;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.RandomValidateCode;
import com.acl.utils.util.StringUtils;
import com.acl.utils.util.VerifyCodeUtil;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysIndex author:huangs createDate:2016年8月11日 下午2:50:30 vsersion:3.0
 * department:安创乐科技 description:处理登陆及首页事务
 */
@Controller
@RequestMapping("/")
public class SysIndexController extends CoreBaseController {
	@Autowired
	private ISysIndexService sysIndexService;
	@Autowired
	private SysUserInfoService sysUserInfoService;
	@Autowired
	private ISysCenterInfoService sysCenterInfoService;
    @Autowired
    private SysBackMenuService sysBackMenuService;

	/**
	 * 未授权
	 * @return
	 */
	@RequestMapping("/401")
	public String unAuth() {
		return "401";
	}

	/**
	 * 未登录
	 * @return
	 */
	@RequestMapping("/403")
	public String unLogin() {
		return "403";
	}
	/**
	 * 页面不存在等
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping("/404")
	public String noPage() {
		return "404";
	}
	
	/**
	 * 未授权
	 * @return
	 */
	@RequestMapping("/405")
	public String unLaw() {
		return "405";
	}
	
	/**
	 * 未授权
	 * @return
	 */
	@RequestMapping("/505")
	public String unDisplay() {
		return "505";
	}
	
	/**
	 * 登录页
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public Object login(HttpServletRequest request) {
		return "login";
	}
	/**
	 * 首页
	 */
	@RequiresAuthentication
	@RequestMapping("/main")
	public String main(@Session(create = false) SessionProvider session, ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("pwdSec", loginSession.isDefaultPWD());
		return "main";
	}
	/**
	 * 获取页面菜单
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
    @ResponseBody
	@RequiresAuthentication
    @RequestMapping("/queryRoleMenuBase")
	public Object queryRoleMenuBase(@Session(create = false) SessionProvider session, HttpServletRequest request,
			HttpServletResponse response) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		// String user_id = loginSession.getUser_id();
		String role_id = loginSession.getRole_id();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role_id", role_id);
		map.put("menu_use", "1");
		List<Map<String, Object>> maps = sysBackMenuService.queryRoleMenuView(map);
		return maps;
	}
	/**
	 * home页面
	 */
	@RequiresAuthentication
	@RequestMapping("/home")
	public String home(@Session(create = false) SessionProvider session, ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		/*欢迎部分		 */
		modelMap.addAttribute("currentIp", loginSession.getCurrent_ip());
		modelMap.addAttribute("userIp", loginSession.getUser_ip());
		modelMap.addAttribute("loginDate",FormatDateUtil.newDateForRrcord(StringUtils.checkDate(loginSession.getLogin_date())));
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		HashMap<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("dept_id", loginSession.getDept_id());
		/*系统公告		时间倒序头十条*/
		List<Map<String, Object>> listMsg = sysCenterInfoService.queryMessage(paramsMap);
		modelMap.addAttribute("info", listMsg);		
		return "home";
	}

	/**信息总揽及
	 * 今日数据
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryNewsAndData")
	public Object queryNewsAndData(@Session(create = false) SessionProvider session, HttpServletRequest request){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		HashMap<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("dept_id", loginSession.getDept_id());
		paramsMap.put("loginName", StringUtils.checkString(loginSession.getUser_name()));
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);
		Map<String, Object> map=new HashMap<>();
		/*信息总揽部分	*/
		map.put("total_client", StringUtils.checkInt(sysIndexService.countFrontUser(paramsMap)));
		map.put("total_balance",  StringUtils.checkBigDecimal(sysIndexService.sumFrontUserWallet(paramsMap)));
		map.put("total_fee", StringUtils.checkBigDecimal(sysIndexService.sumFrontUserBrokerage(paramsMap)));//视图 v_front_user_info_brokerage
		map.put("balance",  StringUtils.checkBigDecimal(userList.get(0).get("dept_money")));
		/*今日数据部分*/
		if(loginSession.getDept_type().equals("0")){
			paramsMap.put("dept_id", "");
		}
		paramsMap.put("create_time", DateFormatUtil.convertCurrentDate("yyyy-MM-dd 00:00:00"));
		map.put("new_user",  StringUtils.checkInt(sysIndexService.countFrontUser(paramsMap)));
		map.put("brokerage",StringUtils.checkBigDecimal(sysIndexService.sumFrontUserBrokerage(paramsMap)));
		map.put("charge_amount", StringUtils.checkBigDecimal(sysIndexService.sumFrontCZ(paramsMap))); //8 -23
		map.put("withdraw_amount", StringUtils.checkBigDecimal(sysIndexService.sumFrontTX(paramsMap))); //8 -20	
		return map;
	}
	/**新用户注册	
	 * 	一个月内每天用户注册数量
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/newUserlist")
	public Object newUserlist(@Session(create = false) SessionProvider session, HttpServletRequest request, 			
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("dept_id", loginSession.getDept_id());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		//calendar.add(Calendar.DATE, 1);
		//calendar.add(Calendar.DAY, 1);calendar.add(calendar.DATE,1)
		paramsMap.put("create_time", DateFormatUtil.convertDate(calendar.getTime(),"yyyy-MM-dd 00:00:00"));
		List<Map<String, Object>> newUserlist = sysIndexService.countFrontUserMonth(paramsMap);
		List<String> dateMap = new ArrayList<>();
		List<String> numMap = new ArrayList<>();
		for (Map<String, Object> map : newUserlist) {
			dateMap.add(map.get("year")+"/"+map.get("month")+"/"+map.get("day"));
			numMap.add(map.get("num").toString());
		}
		HashMap<String, Object> newUserlistHashMap = new HashMap<>() ;
		newUserlistHashMap.put("date", dateMap);
		newUserlistHashMap.put("num", numMap);
		return newUserlistHashMap;
	}
	/**手续费=仓储费
	 * 	一个月内每天交易手续费=仓储费 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/listBrokerage")
	public Object listBrokerage(@Session(create = false) SessionProvider session, HttpServletRequest request, 			
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("dept_id", loginSession.getDept_id());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		paramsMap.put("create_time", DateFormatUtil.convertDate(calendar.getTime(),"yyyy-MM-dd 00:00:00"));
		List<Map<String, Object>> listBrokerage = sysIndexService.countFrontUserBrokerageMonth(paramsMap);
		List<String> dateMap = new ArrayList<>();
		List<String> numMap = new ArrayList<>();
		for (Map<String, Object> map : listBrokerage) {
			//B_date.add(map.get("year")+"年"+map.get("month")+"月"+map.get("day")+"日");
			dateMap.add(map.get("year")+"/"+map.get("month")+"/"+map.get("day"));
			numMap.add(map.get("num").toString());
		}
		HashMap<String, Object> listBrokerageHashMap = new HashMap<>() ;
		listBrokerageHashMap.put("date", dateMap);
		listBrokerageHashMap.put("num", numMap);
		return listBrokerageHashMap;

	}
	
	/**
	 * 查询后台消息详情
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryMsgInfo")
	public Object queryMsg(HttpServletRequest request, @RequestParam HashMap<String, Object> paramsMap) {
		List<Map<String, Object>> list = sysCenterInfoService.queryMsgInfo(paramsMap);
		return list;
	}

	/**
	 * 登陆验证及session
	 * 
	 * @param session
	 * @param request
	 * @param
	 * @return
	 * @throws Exception
	 * @throws IOException
	 * @throws ServletException
	 */
	@ResponseBody
	@RequestMapping("/loginAuth")
	public Object loginAuth(@Session(create = true) SessionProvider session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam HashMap<String, Object> loginMap) throws Exception {
		dbLog.setMethod_name("登录认证");
		dbLog.setCq_params(StringUtils.checkString(loginMap.get("loginName")));
		dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
		try {
			if (0 == loginMap.size() || "".equals(StringUtils.checkString(loginMap.get("loginName")))
					|| "".equals(StringUtils.checkString(loginMap.get("passWord")))) {
				request.getRequestDispatcher("/404").forward(request, response);
			}
			msg.setSuccess(false);
			// 图片验证码
			if (4>StringUtils.checkInt(session.getAttribute("loginCount"))) {
				String sc = StringUtils.checkString(session.getAttribute("web_code"));
				String wc = StringUtils.checkString(loginMap.get("web_code")).toLowerCase();
				if (!sc.equals(wc)) {
					msg.setMsg("验证码错误");
					return msg;
				}
			}
			String current_ip = NetUtils.getIpAddress(request);
			loginMap.put("current_ip", current_ip);
			ShiroToken token = new ShiroToken(
					StringUtils.checkString(loginMap.get("loginName")),
					MD5Utils.MD5(StringUtils.checkString(loginMap.get("passWord"))),
					true, 
					loginMap,
					session
					);
			// 在页面进行短信验证码的判断
			token.setHost(current_ip);
			token.setRememberMe(true);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.login(token);
			response.reset();
			msg.setSuccess(true);
			dbLog.setAction_message("成功");
			dbLog.setIs_ok(0);
			return msg;
		} catch (Exception e) {
			String mString = e.getMessage();
			if (mString.contains("请检查帐号和密码是否正确")) {
				msg.setLogin_count(StringUtils.checkInt(mString.split("#")[1]));
				mString = mString.split("#")[0];
			}else {
				mString = "账号状态异常，请联系客服。";
				msg.setMsg(mString);
				dbLog.setAction_message(mString.split("&")[0]);
				dbLog.setIs_ok(4);
				e.printStackTrace();
				return msg;
			}
			msg.setMsg(mString.split("&")[1]);
			dbLog.setAction_message(mString.split("&")[0]);
			dbLog.setIs_ok(4);
			e.printStackTrace();
			return msg;
		}finally{
			dbLogService.insertLog(dbLog);
		}
	}

	/**
	 * 登出验证及session
	 * 
	 * @param session
	 * @param request
	 * @param
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/loginOut")
	public Object loginOut(@Session(create = false) SessionProvider session, HttpServletRequest request,
			HttpServletResponse response) {
		dbLog.setMethod_name("登出系统");
		try {
			if (null != session) {
				LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
				dbLog.setCq_params(loginSession.getUser_name());
				dbLog.setCq_paramsAdd(loginSession.getUser_ip());
				dbLog.setAction_message("session清除成功。");
				dbLog.setIs_ok(0);
				session.invalidate();
			}
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser.isAuthenticated()) {
				dbLog.setAction_message("权限清除成功,"+dbLog.getAction_message());
				dbLog.setIs_ok(1);
				currentUser.logout();
			}
			msg.setSuccess(true);
			return msg;
		} finally {
			dbLogService.insertLog(dbLog);
		}
	}

	/**
	 * 获取web验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getVerifyCodeImage")
	public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response,
			@Session(create = false) SessionProvider session) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_UPPER, 4, "10ilo");
		session.setAttribute("web_code", verifyCode.toLowerCase());
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 4, true, Color.white, null,
				null);
		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getVerifyCodeText")
	public Object getVerifyCodeText(HttpServletRequest request, HttpServletResponse response,
			@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap){
		try {
			List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);			
			LoginSession loginSession = new LoginSession(userList.get(0));
			dbLog.setMethod_name("短信验证码请求");
			dbLog.setCq_params(StringUtils.checkString(paramsMap.get("loginName")));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			dbLog.setCq_paramsAdd(StringUtils.checkString(loginSession.getUser_mobile()));
			String usermobile=StringUtils.convertString(paramsMap.get("user_mobile"));
			if (!usermobile.equals(StringUtils.convertString(userList.get(0).get("user_mobile")))) {
				msg.setMsg("您输入的手机号与登录名不匹配，请检查后重新输入！");
				msg.setSuccess(false);
				dbLog.setAction_message("您输入的手机号"+usermobile+"与登录名不匹配，请检查后重新输入！ ");
				dbLog.setIs_ok(3);
				return msg;
			}
	//		判断已发送的短信验证码是否在1分钟之内不允许重新发送
			if(!"".equals(StringUtils.checkString(userList.get(0).get("auth_code")))){
				if (System.currentTimeMillis() - StringUtils.checkLong(StringUtils.checkString(userList.get(0).get("auth_code")).split(" ")[1]) < 1*60*1000) {
					msg.setMsg("短信验证码已经发送，请稍等");
					msg.setSuccess(true);
					dbLog.setAction_message("短信验证码已经发送，请稍等 "+StringUtils.checkString(userList.get(0).get("auth_code")).split(" ")[0]);
					dbLog.setIs_ok(2);
					return msg;
				}
			}
			// 写进数据库
			paramsMap.put("user_id", loginSession.getUser_id());
			paramsMap.put("auth_code", RandomValidateCode.getRandomNumber(4) + " " + System.currentTimeMillis());
			String textMessage = "您的登录验证码为： " + StringUtils.checkString(paramsMap.get("auth_code")).split(" ")[0] + " ，三分钟内输入有效。";
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
	 * 滑动完后完成,保存登录信息
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/slide", method = RequestMethod.POST)
	public Object slide(@Session(create = true) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		if ("".equals(StringUtils.checkString(paramsMap.get("loginName")))) {
			msg.setMsg("请输入登录名");
			msg.setSuccess(false);
		} else {
			paramsMap.put("loginName", StringUtils.checkString(paramsMap.get("loginName")));
			List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);
			if (0 != userList.size()) {
				LoginSession loginSession = new LoginSession(userList.get(0));
				session.setAttribute("loginName", loginSession.getUser_name());
				session.setAttribute("loginCount", loginSession.getError_count());
				msg.setLogin_count(StringUtils.checkInt(loginSession.getError_count()));
			} else {
				session.setAttribute("loginName", StringUtils.checkString(paramsMap.get("loginName")));
				session.setAttribute("loginCount", 0);
				msg.setLogin_count(0);
			}
			msg.setSuccess(true);
		}
		return msg;
	}

	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/modifyPwd")
	public Object modifyPwd(@Session(create = false) SessionProvider session, HttpServletRequest request,
			HttpServletResponse response, @RequestParam HashMap<String, Object> paramsMap) {
		dbLog.setMethod_name("用户修改密码");
		try {
			dbLog.setCq_params(NetUtils.getIpAddress(request));
			msg.setSuccess(true);
		if (null == session) {
			msg.setMsg("请登录");
			msg.setSuccess(false);
			dbLog.setAction_message("未登录请求");
			dbLog.setIs_ok(1);
			return msg;
		}
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		if (null == loginSession) {
			msg.setMsg("请登录");
			msg.setSuccess(false);
			dbLog.setAction_message("用户session失效后请求");
			dbLog.setIs_ok(2);
			return msg;
		}
		dbLog.setCq_paramsAdd(loginSession.getUser_name());

		paramsMap.put("loginName", loginSession.getUser_name());
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(paramsMap);
		if (!StringUtils.checkString(userList.get(0).get("user_password")).equals(MD5Utils.MD5(StringUtils.checkString(paramsMap.get("oldPwd"))))) {
			msg.setMsg("旧密码验证失败！");
			msg.setSuccess(false);
			dbLog.setAction_message("旧密码验证失败");
			dbLog.setIs_ok(3);
			return msg;
		}
			paramsMap.put("user_password", MD5Utils.MD5(StringUtils.checkString(paramsMap.get("newPwd"))));
			paramsMap.put("user_id", loginSession.getUser_id());
			sysUserInfoService.updateUserInfo(paramsMap);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			session.invalidate();
			msg.setMsg("密码修改成功，请重新登录");
			dbLog.setAction_message("成功");
			dbLog.setIs_ok(0);
			return msg;
		} catch (Exception e) {
			msg.setMsg("失败");
			msg.setSuccess(false);
			dbLog.setAction_message(e.getMessage());
			dbLog.setIs_ok(4);
			e.printStackTrace();
			return msg;
		} finally {
			dbLogService.insertLog(dbLog);
		}
	}

}