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
import com.acl.report.service.IReportSettleDayAndMonthService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:ReportSettleDayAndMonthController
 *author:wangli
 *createDate:2016年9月8日 下午5:00:00
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportSettleDayAndMonthController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();
	@Autowired
	private IReportSettleDayAndMonthService reportSettleDayAndMonthService;
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleDay" })
	@RequestMapping("settleDay")
	public String settleDay(){
		return "/report/report_settle_day";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleMonth" })
	@RequestMapping("settleMonth")
	public String settleMonth(){
		return "/report/report_settle_month";
	}
	
	/**
	 * 查询结算会员日报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleDay" })
	@RequestMapping("/querySettleDayReport")
	public Object querysettleDayReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询字典信息
		PageList<?> list = (PageList<?>)reportSettleDayAndMonthService.queryReportSettleDay(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 查询结算会员月报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleMonth" })
	@RequestMapping("/querySettleMonthReport")
	public Object querysettleMonthReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询字典信息
		PageList<?> list = (PageList<?>)reportSettleDayAndMonthService.queryReportSettleMonth(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 结算会员日报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settleDay" })
	@RequestMapping("/exportSettleDayReportInfo")
	public void exportReportAgentDayInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		/*LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}*/
		//查询经纪人信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleDayResultExport(map);		
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "结算会员日报表", response);
		map.clear();
		}

	}
	
	/**
	 * 结算会员月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settleMonth" })
	@RequestMapping("/exportSettleMonthReportInfo")
	public void exportBrokerMonthReportInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		/*LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}*/
		//查询经纪人信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleMonthResultExport(map);		
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "结算会员月报表", response);
		map.clear();
		}

	}
	
	/**
	 * 结算会员日报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settleDay" })
	@RequestMapping("/querySettleDaySum")
	public Object querySettleDaySum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		//查询经纪人信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleDaySum(paramsMap);		
		return list;

	}
	
	/**
	 * 结算会员月报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/settleMonth" })
	@RequestMapping("/querySettleMonthSum")
	public Object querySettleMonthSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		//查询结算会员信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleMonthSum(paramsMap);		
		return list;

	}
	
	
	/**
	 * 查询结算会员日报表后导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleDay" })
	@RequestMapping("/querySettleDayReportForExcel")
	public Object querysettleDayReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		//查询结算会员信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleDayResultExport(paramsMap);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportSettleDayReportInfo");
        return object;
	}
	
	/**
	 * 查询结算会员月报表后导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/settleDay" })
	@RequestMapping("/querySettleMonthReportForExcel")
	public Object querySettleMonthReportForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
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
		default:
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		//查询结算会员信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportSettleDayAndMonthService.querySettleMonthResultExport(paramsMap);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportSettleMonthReportInfo");
        return object;
	}
	

}
