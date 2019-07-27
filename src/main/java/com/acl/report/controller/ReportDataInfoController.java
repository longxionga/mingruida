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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportDataInfoService;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * 
 *className:ReportDataInfoController
 *author:wangzhe
 *createDate:2016年10月28日 下午4:13:17
 *version:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportDataInfoController extends CoreBaseController {

	@Autowired
	private IReportDataInfoService reportDataInfoService;
	
	@Autowired
	private SysDeptInfoService sysDeptInfoService;

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feereport"})
	@RequestMapping("/feereport")
	public String CounterFeeInfo() {
		return "report/report_conter_fee_info";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feemonthreport"})
	@RequestMapping("/feemonthreport")
	public String CounterFeeMonthInfo() {
		return "report/report_conter_fee_month_info";
	}
	
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feedeptreport"})
	@RequestMapping("/feedeptreport")
	public String CounterFeeDeptInfo() {
		return "report/report_conter_fee_dept_info";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feecenterreport"})
	@RequestMapping("/feecenterreport")
	public String CounterFeeCenterInfo() {
		return "report/report_channel_fee_month_info";
	}
	
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settlemoneyinfo"})
	@RequestMapping("/settlemoneyinfo")
	public String settlemoneyinfo() {
		return "report/report_settle_money_info";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentmoneyinfo"})
	@RequestMapping("/agentmoneyinfo")
	public String agentmoneyinfo() {
		return "report/report_agent_money_info";
	}
	
	
	/**
	 * 手续费报表信息 ==仓储费
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feereport" })
	@RequestMapping("/queryReportDataInfo")
	public Object queryReportDataInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportDataInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 手续费报表月信息 ==仓储费
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feemonthreport" })
	@RequestMapping("/queryReportDataMonthInfo")
	public Object queryReportDataMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportDataMonthInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 部门报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feedeptreport" })
	@RequestMapping("/queryDeptReportDataInfo")
	public Object queryDeptReportDataInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		PageList<?> list = (PageList<?>)reportDataInfoService.queryDeptReportDataInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 会员信息日统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feereport" })
	@RequestMapping("/queryReportDataInfoSum")
	public Object queryReportDataInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		list = reportDataInfoService.queryReportDataCount(paramsMap);		
		return list;
	}
	
	
	/**
	 * 部门信息统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feedeptreport" })
	@RequestMapping("/queryReportDataDeptInfoSum")
	public Object queryReportDataDeptInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		list = reportDataInfoService.queryReportDeptDataCount(paramsMap);		
		return list;
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feedeptreport" })
	@RequestMapping("/queryReportDeptDataInfo")	
	public Object queryReportDeptDataInfo(HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramMap){
		List<HashMap<String, Object>> dataList = null;		
		dataList = reportDataInfoService.queryReportDeptDataInfo(paramMap);
		
		return dataList;
	}
	

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feemonthreport" })
	@RequestMapping("/queryReportDeptDataMonthInfo")	
	public Object queryReportDeptDataMonthInfo(HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramMap){
		List<HashMap<String, Object>> dataList = null;		
		dataList = reportDataInfoService.queryReportDeptDataMonthInfo(paramMap);
		
		return dataList;
	}
	
	/**
	 * 手续费明细报表信息==仓储费
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feereport" })
	@RequestMapping("/queryReportDataDetailInfo")
	public Object queryReportDataDetailInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		// loginSession = (LoginSession) session.getAttribute("userSession");
		//String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
//		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
//		{			
//			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
//			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
//			paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
//		}
		
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportDataDetailInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 部门手续费明细报表信息 ==仓储费
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feedeptreport" })
	@RequestMapping("/queryReportDeptDetailInfo")
	public Object queryReportDeptDetailInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);		
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportDataDetailInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 手续费明细报表信息 ==仓储费
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feemonthreport" })
	@RequestMapping("/queryReportDataDetailMonthInfo")
	public Object queryReportDataDetailMonthInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		// loginSession = (LoginSession) session.getAttribute("userSession");
		//String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
//		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
//		{			
//			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
//			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
//			paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
//		}
		
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportDataDetailMonthInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 部门会员代理余额报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentmoneyInfo" })
	@RequestMapping("/queryDeptMoneyInfo")
	public Object queryDeptMoneyInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());	
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		String dept_type=loginSession.getDept_type();
		switch (dept_type) {
		case "0":
			break;
		case "1"://交易中心
			paramsMap.put("ce_id", loginSession.getDept_id());
			break;
		case "2"://渠道
			paramsMap.put("ch_id", loginSession.getDept_id());
			break;
		case "3"://服务商
			paramsMap.put("settle_id", loginSession.getDept_id());
			break;
		case "4":
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		
		PageList<?> list = (PageList<?>)sysDeptInfoService.queryDeptMoneyInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}

	/**
	 * 部门服务商余额报表信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settlemoneyInfo" })
	@RequestMapping("/queryDeptsMoneyInfo")
	public Object queryDeptsMoneyInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
									 @RequestParam(defaultValue = "0", required = false) Integer page,
									 @RequestParam(defaultValue = "10", required = false) Integer rows,
									 @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows);
		//LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		switch (dept_type) {
			case "0":
				break;
			case "1"://交易中心
				paramsMap.put("ce_id", loginSession.getDept_id());
				break;
			case "2"://渠道
				paramsMap.put("ch_id", loginSession.getDept_id());
				break;
			case "3"://服务商
				paramsMap.put("settle_id", loginSession.getDept_id());
				break;
			case "4":
				paramsMap.put("agent_id", loginSession.getDept_id());
				break;
			default:
				paramsMap.put("dept_id", loginSession.getDept_id());
				break;
		}

		PageList<?> list = (PageList<?>)sysDeptInfoService.queryDeptMoneyInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}

	/**
	 * 部门金额汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settlemoneyInfo" })
	@RequestMapping("/queryDeptsMoneyInfoSum")
	public Object queryDeptsMoneyInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
										@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		switch (dept_type) {
			case "0":
				break;
			case "1"://交易中心
				paramsMap.put("ce_id", loginSession.getDept_id());
				break;
			case "2"://渠道
				paramsMap.put("ch_id", loginSession.getDept_id());
				break;
			case "3"://服务商
				paramsMap.put("settle_id", loginSession.getDept_id());
				break;
			case "4":
				paramsMap.put("agent_id", loginSession.getDept_id());
				break;
			default:
				paramsMap.put("dept_id", loginSession.getDept_id());
				break;
		}
		//查询汇总信息
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = sysDeptInfoService.queryDeptMoneyCount(paramsMap);
		return list;
	}
	
	
	/**
	 * 查询部门金额汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/agentmoneyInfo" })
	@RequestMapping("/queryDeptMoneyInfoSum")
	public Object queryDeptMoneyInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		String dept_type=loginSession.getDept_type();
		switch (dept_type) {
		case "0":
			break;
		case "1"://交易中心
			paramsMap.put("ce_id", loginSession.getDept_id());
			break;
		case "2"://渠道
			paramsMap.put("ch_id", loginSession.getDept_id());
			break;
		case "3"://服务商
			paramsMap.put("settle_id", loginSession.getDept_id());
			break;
		case "4":
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		//查询汇总信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = sysDeptInfoService.queryDeptMoneyCount(paramsMap);		
		return list;
	}
	
	
	/**
	 * 交易中心报表月信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feecenterreport" })
	@RequestMapping("/queryReportCenterDataInfo")
	public Object queryReportCenterDataInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportCenterDataInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 查询交易中心数据明细
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feecenterreport" })
	@RequestMapping("/queryReportCenterDataDetailInfo")
	public Object queryReportCenterDataDetailInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		PageList<?> list = (PageList<?>)reportDataInfoService.queryReportCenterDetailInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 交易中心数据汇总
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/feecenterreport" })
	@RequestMapping("/queryReportCenterDataSum")
	public Object queryReportCenterDataSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());
		//paramsMap.put("dept_type", loginSession.getDept_type());
		if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
		{			
			paramsMap.put("year",paramsMap.get("begindate").toString().split("-")[0]);
			paramsMap.put("month",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[1]));
			//paramsMap.put("day",Integer.parseInt(paramsMap.get("begindate").toString().split("-")[2]));
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
		list = reportDataInfoService.queryReportCenterDataCount(paramsMap);		
		return list;
	}
	
}
