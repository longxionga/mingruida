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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;


import com.acl.pojo.LoginSession;
import com.acl.report.service.ReportMemberInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

@Controller
@RequestMapping("/report")
public class ReportMemberInfoContorller extends CoreBaseController{

	Map<String,Object> map=new HashMap<>();

	@Autowired
	private ReportMemberInfoService reportMemberInfoService;
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/membermonthreport"})
	@RequestMapping("/membermonthreport")
	public String agentMonthInfo(){
		return "report/report_member_info_month";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/memberdayreport"})
	@RequestMapping("/memberdayreport")
	public String agentDayInfo(){
		return "report/report_member_info_day";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settledayreport"})
	@RequestMapping("/settledayreport")
	public String settleDayInfo(){
		return "report/report_settle_info_day";
	}
	
	
	/**
	 * 会员日报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/memberdayreport" })
	@RequestMapping("/queryReportMemberDayInfo")
	public Object queryReportAgentDayInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
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
		
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportMemberInfoService.queryDayReportMemberInfo(paramsMap, pageBounds);
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
  		
		//Object json = this.getJsonMap(listMap);
		//return json;

	}
	
	/**
	 * 会员月报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/membermonthreport" })
	@RequestMapping("/queryReportMemberMonthInfo")
	public Object queryReportAgentMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		String dept_type=loginSession.getDept_type();
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
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
		//查询月代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportMemberInfoService.queryMonthReportMemberInfo(paramsMap, pageBounds);
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
		
//  		PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
//		Object json = this.getJsonMap(listMap);
//		return json;

	}
	
	/**
	 * 会员月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/membermonthreport" })
	@RequestMapping("/exportMemberMonthReportInfo")
	public void exportReportAgentMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());		
		//查询月代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		if(paramsMap.get("mobile") !=null)
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
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
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportMemberInfoService.queryMonthResultExport(paramsMap);		
		//Object json = this.getJsonMap(list);
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "用户月报表", response);
		}

	}
	
	/**
	 * 会员日报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/memberdayreport" })
	@RequestMapping("/exportMemberdayReportInfo")
	public void exportReportAgentDayInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		String dept_type=loginSession.getDept_type();
		if(paramsMap.get("mobile") !=null)
		{		
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
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
		//查询月代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportMemberInfoService.queryDayResultExport(paramsMap);		
		//Object json = this.getJsonMap(list);
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "用户日报表", response);
		}

	}
	
	/**
	 * 会员信息日统计汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/memberdayreport" })
	@RequestMapping("/queryMemberDaySum")
	public Object queryMemberDaySum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
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
		//查询会员信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = reportMemberInfoService.queryMemberDayCount(paramsMap);		
		return list;
	}
	
	/**
	 * 会员信息月统计汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/membermonthreport" })
	@RequestMapping("/queryMemberMonthSum")
	public Object queryMemberMonthSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
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
		list = reportMemberInfoService.queryMemberMonthCount(paramsMap);		
		return list;
	}
	
	/**
	 * 服务商充值和提现手续费信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settledayreport" })
	@RequestMapping("/querySettleFeeInfo")
	public Object querySettleFeeInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportMemberInfoService.querySettleFeeInfo(paramsMap, pageBounds);		
		Object  json = this.getJsonMap(list);
		return json;  	

	}
	
	/**
	 * 服务商充值和体现手续费数据汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settledayreport" })
	@RequestMapping("/querySettleFeeSum")
	public Object querySettleFeeSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		//查询手续费总汇信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = reportMemberInfoService.querySettleFeeCount(paramsMap);		
		return list;
	}
	
	
	/**
	 * 查询服务商手续费导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settledayreport" })
	@RequestMapping("/queryFeeforExcel")
	public Object queryFeeforExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		
		//查询信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		//JSONObject job = JSONObject.parseObject();
		map.put("settle_id", paramsMap.get("settle_id"));
		map.put("begindate", paramsMap.get("begindate"));
		map.put("enddate", paramsMap.get("enddate"));
		list = reportMemberInfoService.querySettleFeeExport(map);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		//map=paramsMap;
		object.put("url", "exportQueryFeeInfo");
        return object;
	}
	
	/**
	 * 服务商手续费报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settledayreport" })
	@RequestMapping("/exportQueryFeeInfo")
	public void exportQueryFeeInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		//查询经纪人信息
		//List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();
		listafter = reportMemberInfoService.querySettleFeeExport(map);		
		if(listafter.size()>0){
		ExcelUtil.buildXSLXExcel(listafter, "用户余额", response);
		map.clear();
		}

	}
}
