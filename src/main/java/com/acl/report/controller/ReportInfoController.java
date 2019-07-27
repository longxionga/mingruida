package com.acl.report.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.acl.core.CoreBaseController;


import com.acl.pojo.LoginSession;
import com.acl.report.service.ReportInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * 
 *className:ReportInfoController
 *author:wangzhe
 *createDate:2016年9月7日 下午3:54:17
 *version:3.0
 *department:安创乐科技
 *description:
 */

@Controller
@RequestMapping("/report")
public class ReportInfoController extends CoreBaseController{
	
	@Autowired
	private ReportInfoService reportInfoService;
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/agentmonthreport" })
	@RequestMapping("/agentmonthreport")
	public String agentMonthInfo(){
		return "report/report_agent_info_month";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/agentdayreport" })
	@RequestMapping("/agentdayreport")
	public String agentDayInfo(){
		return "report/report_agent_info_day";
	}
	
	
	/**
	 * 服务商日报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/agentdayreport" })
	@RequestMapping("/queryReportAgentDayInfo")
	public Object queryReportAgentDayInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

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
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//查询当天的服务商信息
		//获取登录的服务商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportInfoService.queryReportAgentInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 服务商月报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/agentmonthreport" })
	@RequestMapping("/queryReportAgentMonthInfo")
	public Object queryReportAgentMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(loginSession.getDept_type().equals("4"))
		{
			paramsMap.put("agent_id", loginSession.getAgent_id());
		}
		if(loginSession.getDept_type().equals("3"))
		{
			paramsMap.put("settle_id", loginSession.getDept_id());
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
		PageBounds pageBounds = new PageBounds(page,rows);
//		pageBounds.setAsyncTotalCount(true);
//		if(paramsMap.get("sort")!=null){
//			paramsMap.put("order_type", paramsMap.get("sort").toString()+" "+paramsMap.get("order").toString());
//			pageBounds = new PageBounds(page,rows,Order.formString(paramsMap.get("sort").toString()+"."+paramsMap.get("order").toString()));
//		}		
		//查询月服务商信息
		
		PageList<?> list = (PageList<?>)reportInfoService.queryReportAgentMonthInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 服务商月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentmonthreport" })
	@RequestMapping("/exportAgentMonthReportInfo")
	public void exportReportAgentMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		//paramsMap.put("dept_id", loginSession.getDept_id());
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
		//查询月服务商信息
		//获取登录的服务商新进行条件筛选，如果没有则为空。
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportInfoService.queryAgentMonthResultExport(paramsMap);		
		//Object json = this.getJsonMap(list);
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "服务商月报表", response);
		msg.setMsg("导出成功");
		msg.setSuccess(true);
		}else
		{
			msg.setMsg("暂无数据，导出失败！");
			msg.setSuccess(true);
		}

	}
	
	/**
	 * 服务商日报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentdayreport" })
	@RequestMapping("/exportAgentdayReportInfo")
	public void exportReportAgentDayInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		//paramsMap.put("dept_id", loginSession.getDept_id());
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
		//查询月服务商信息
		//获取登录的服务商新进行条件筛选，如果没有则为空。
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportInfoService.queryAgentDayResultExport(paramsMap);		
		//Object json = this.getJsonMap(list);
		if(list.size()>0)
		{
			ExcelUtil.buildXSLXExcel(list, "服务商日报表", response);
		}

	}
	
	
	/**
	 * 服务商信息日统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentdayreport" })
	@RequestMapping("/queryAgentDaySum")
	public Object queryAgentDaySum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		//paramsMap.put("dept_id", loginSession.getDept_id());
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
		//paramsMap.put("dept_id", loginSession.getDept_type()==4?loginSession.getDept_id()"");
		//服务商日信息
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = reportInfoService.queryAgentDayCount(paramsMap);		
		return list;
	}
	
	/**
	 * 服务商信息月统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentmonthreport" })
	@RequestMapping("/queryAgentMonthSum")
	public Object queryAgentMonthSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
	
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
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
		//服务商月信息
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = reportInfoService.queryAgentMonthCount(paramsMap);		
		return list;
	}

}
