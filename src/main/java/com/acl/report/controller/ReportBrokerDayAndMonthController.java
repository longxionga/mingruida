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

import com.acl.core.CoreBaseController;

import com.acl.pojo.LoginSession;
import com.acl.report.service.ReportBrokerDayAndMonthService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;


/**
 *className:ReportAgentDailyController
 *author:wangli
 *createDate:2016年9月8日 上午11:26:51
 *vsersion:3.0
 *department:安创乐科技
 *description:代理商日报表
 */
@Controller
@RequestMapping("/report")
public class ReportBrokerDayAndMonthController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();
	@Autowired
	private ReportBrokerDayAndMonthService reportBrokerDayAndMonthService;
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerDay" })
	@RequestMapping("brokerDay")
	public String brokerDay(){
		return "/report/report_broker_day";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerMonth" })
	@RequestMapping("brokerMonth")
	public String brokerMonth(){
		return "/report/report_broker_month";
	}
	
	/**
	 * 查询代理商日报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerDay" })
	@RequestMapping("/queryBrokerDayReport")
	public Object queryBrokerDayReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));		
		//查询代理商日报表
		PageList<?> list = (PageList<?>)reportBrokerDayAndMonthService.queryReportBrokerDay(paramsMap, pageBounds);	
		Object json = this.getJsonMap(list);		
        return json;
	}
	
	/**
	 * 查询代理商月报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerMonth" })
	@RequestMapping("/queryBrokerMonthReport")
	public Object queryBrokerMonthReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
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
		case "4"://
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));		
		//查询代理商月报表
		PageList<?> list = (PageList<?>)reportBrokerDayAndMonthService.queryReportBrokerMonth(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
        return json;
	}
	
	/**
	 * 代理商日报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brokerDay" })
	@RequestMapping("/exportBrokerDayReportInfo")
	public void exportBrokerDayReportInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		//查询代理商信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportBrokerDayAndMonthService.queryBrokerDayResultExport(map);		
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "代理商日报表", response);	
		map.clear();
		}
		
	}
	
	/**
	 * 代理商月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brokerMonth" })
	@RequestMapping("/exportBrokerMonthReportInfo")
	public void exportBrokerMonthReportInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		//查询代理商信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportBrokerDayAndMonthService.queryBrokerMonthResultExport(map);		
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "代理商月报表", response);
		map.clear();
		}

	}
	
	/**
	 * 代理商日报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brokerDay" })
	@RequestMapping("/queryBrokerDaySum")
	public Object queryBrokerDaySum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		case "4"://
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		//查询代理商信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportBrokerDayAndMonthService.queryBrokerDaySum(paramsMap);		
		return list;

	}
	
	/**
	 * 代理商月报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brokerMonth" })
	@RequestMapping("/queryBrokerMonthSum")
	public Object queryBrokerMonthSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		case "4"://
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		//查询代理商信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportBrokerDayAndMonthService.queryBrokerMonthSum(paramsMap);		
		return list;
	}
	
	
	/**
	 * 查询代理商日报表后导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerDay" })
	@RequestMapping("/queryBrokerDayReportForExcel")
	public Object queryBrokerDayReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		case "4"://
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		Map<String, Object> count =reportBrokerDayAndMonthService.countBrokerDay(paramsMap);
		JSONObject object=new JSONObject();
		int num = StringUtils.checkInt(count.get("num"));
		object.put("num", num);
		object.put("url", "exportBrokerDayReportInfo");
		map=paramsMap;				
        return object;
	}
	
	/**
	 * 查询代理商月报表后导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerMonth" })
	@RequestMapping("/queryBrokerMonthReportForExcel")
	public Object queryBrokerMonthReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		String dept_type=loginSession.getDept_type();
		switch (dept_type) {
		case "1"://交易中心
			paramsMap.put("ce_id", loginSession.getDept_id());
			break;
		case "2"://渠道
			paramsMap.put("ch_id", loginSession.getDept_id());
			break;
		case "3"://服务
			paramsMap.put("settle_id", loginSession.getDept_id());
			break;
		case "4"://
			paramsMap.put("agent_id", loginSession.getDept_id());
			break;
		case "5"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		Map<String, Object> num =reportBrokerDayAndMonthService.countBrokerMonth(paramsMap);
		JSONObject object=new JSONObject();
		object.put("num", num);		
		object.put("url", "exportBrokerMonthReportInfo");
		map=paramsMap;		
        return object;
	}

}
