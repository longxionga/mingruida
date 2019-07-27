package com.acl.report.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.acl.pojo.LoginSession;
import com.acl.report.service.ReportRechargeAndWithdrawalInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ClientExecuteProxyUtils;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:ReportRechargeAndWithdrawalForAccController
 *author:wangli
 *createDate:2017年3月22日 下午4:13:18
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportRechargeAndWithdrawalAccController extends CoreBaseController {
	
		@Autowired
		private ReportRechargeAndWithdrawalInfoService reportRecAndWithdraInfoService;
		
		@RequiresAuthentication
		@RequiresPermissions(value = {"/report/rechargeaccreport"})
		@RequestMapping("/rechargeaccreport")
		public String rechargeInfo(){
			return "report/report_recharge_acc_info";
		}
		
		
		@RequiresAuthentication
		@RequiresPermissions(value = {"/report/withdrawalaccreport"})
		@RequestMapping("/withdrawalaccreport")
		public String withdrawalInfo(){
			return "report/report_withdrawal_acc_info";
		}
	
	
	
		
		/**
		 * 查询充值信息
		 * @return
		 */
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = { "/report/rechargeaccreport" })
		@RequestMapping("/queryReportRechargeAccInfo")
		public Object queryReportRechargeAccInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
				@RequestParam(defaultValue = "0", required = false) Integer page,
				@RequestParam(defaultValue = "10", required = false) Integer rows,
				@RequestParam HashMap<String,Object> paramsMap){		
			PageBounds pageBounds = new PageBounds(page,rows);
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			paramsMap.put("role_type", "01");
			if("".equals(StringUtils.checkString(paramsMap.get("cz_state"))))
			{
				paramsMap.put("cz_state","1");
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
			{
				paramsMap.put("mobile",paramsMap.get("mobile").toString());
			}
			if(loginSession.getDept_type().equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(loginSession.getDept_type().equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(loginSession.getDept_type().equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("6"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			//查询当天的代理商信息
			//获取登录的代理商新进行条件筛选，如果没有则为空。		
			PageList<?> list = (PageList<?>)reportRecAndWithdraInfoService.queryReportRechargeInfo(paramsMap, pageBounds);
			//Object json = this.getJsonMap(list);
			//return json;
			Object json=null;
			if(list.size()>0)
			{
				PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
				json = this.getJsonMap(listMap);
			}else
			{
				json = this.getJsonMap(list);
			}
			return json;
	
		}
		
		
	
		/**
		 * 查询提现信息
		 * @return
		 */
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = { "/report/withdrawalaccreport" })
		@RequestMapping("/queryReportWithdrawalAccInfo")
		public Object queryReportWithdrawalAccInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
				@RequestParam(defaultValue = "0", required = false) Integer page,
				@RequestParam(defaultValue = "10", required = false) Integer rows,
				@RequestParam HashMap<String,Object> paramsMap){		
			PageBounds pageBounds = new PageBounds(page,rows);
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			paramsMap.put("role_type", "01");
			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
			{
				paramsMap.put("mobile",paramsMap.get("mobile").toString());
			}
			if(!"9".equals(StringUtils.checkString(paramsMap.get("use_type"))))
			{
				paramsMap.put("use_type",paramsMap.get("use_type").toString());
			}else
			{
				paramsMap.put("use_type","");
			}
			if(loginSession.getDept_type().equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(loginSession.getDept_type().equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(loginSession.getDept_type().equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			if(loginSession.getDept_type().equals("6"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			//查询当天的代理商信息
			//获取登录的代理商新进行条件筛选，如果没有则为空。		
			PageList<?> list = (PageList<?>)reportRecAndWithdraInfoService.queryReportWithdrawalInfo(paramsMap, pageBounds);
			//Object json = this.getJsonMap(list);
			//return json;
			Object json=null;
			if(list.size()>0)
			{
				PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
				json = this.getJsonMap(listMap);
			}else
			{
				json = this.getJsonMap(list);
			}
			return json;
		}
		
	
	
		/**
		 * 充值信息统计
		 * @return
		 */
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = {"/report/rechargeaccreport" })
		@RequestMapping("/queryRechargeAccSum")
		public Object queryRechargeAccSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
				@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			paramsMap.put("role_type", "01");
	
			if("".equals(StringUtils.checkString(paramsMap.get("cz_state"))))
			{
				paramsMap.put("cz_state","1");
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
			{
				paramsMap.put("mobile",paramsMap.get("mobile").toString());
			}
			String dept_type = loginSession.getDept_type();
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			if(dept_type.equals("6"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			//查询经纪人信息		
			List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
			list = reportRecAndWithdraInfoService.queryReportRechargeCount(paramsMap);		
			return list;
		}
	
	
		
		/**
		 * 提现信息统计
		 * @return
		 */
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = {"/report/withdrawalaccreport" })
		@RequestMapping("/queryWithdrawalAccSum")
		public Object queryWithdrawalAccSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
				@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			paramsMap.put("role_type", "01");
			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
			{
				paramsMap.put("mobile",paramsMap.get("mobile").toString());
			}
			if(!"9".equals(StringUtils.checkString(paramsMap.get("use_type"))))
			{
				paramsMap.put("use_type",paramsMap.get("use_type").toString());
			}else
			{
				paramsMap.put("use_type","");
			}
			String dept_type = loginSession.getDept_type();
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			if(dept_type.equals("6"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
			//查询经纪人信息		
			List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
			list = reportRecAndWithdraInfoService.queryReportWithdrawalCount(paramsMap);
			return list;
		}
		
		

}
