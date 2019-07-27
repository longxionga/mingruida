package com.acl.report.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.utils.util.*;
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
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;


/**
 *
 *className:ReportRechargeInfoController
 *author:wangzhe
 *createDate:2016年10月7日 下午3:45:29
 *version:3.0
 *department:安创乐科技
 *description:
 */

@Controller
@RequestMapping("/report")
public class ReportRechargeAndWithdrawalInfoController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();


	@Autowired
	private ReportRechargeAndWithdrawalInfoService reportRecAndWithdraInfoService;

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargereport"})
	@RequestMapping("/rechargereport")
	public String rechargeInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		return "report/report_recharge_info";
	}

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brechargereport"})
	@RequestMapping("/brechargereport")
	public String brechargeInfo(){
		return "report/report_brecharge_info";
	}

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/withdrawalreport"})
	@RequestMapping("/withdrawalreport")
	public String withdrawalInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		return "report/report_withdrawal_info";
	}

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/bwithdrawalreport"})
	@RequestMapping("/bwithdrawalreport")
	public String bwithdrawalInfo(){
		return "report/report_bwithdrawal_info";
	}

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargeensurereport"})
	@RequestMapping("/rechargeensurereport")
	public String rechargeensureInfo(){
		return "report/report_rechargeensure_info";
	}

	/**
	 * 查询充值信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/rechargereport" })
	@RequestMapping("/queryReportRechargeInfo")
	public Object queryReportRechargeInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
										  @RequestParam(defaultValue = "0", required = false) Integer page,
										  @RequestParam(defaultValue = "10", required = false) Integer rows,
										  @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "01");
		paramsMap.put("dept_type",loginSession.getDept_type());
//		if("".equals(StringUtils.checkString(paramsMap.get("cz_state"))))
//		{
//			paramsMap.put("cz_state","1");
//		}
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
	 * 查询经纪人充值信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/rechargereport" })
	@RequestMapping("/queryReportBRechargeInfo")
	public Object queryReportBRechargeInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
										   @RequestParam(defaultValue = "0", required = false) Integer page,
										   @RequestParam(defaultValue = "10", required = false) Integer rows,
										   @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "02");
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
		Object json = this.getJsonMap(list);
		return json;


	}

	/**
	 * 查询保证金充值信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/rechargereport" })
	@RequestMapping("/queryReportRechargeEnsureInfo")
	public Object queryReportRechargeEnsureInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
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
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)reportRecAndWithdraInfoService.queryReportRechargeEnsureInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

	/**
	 * 查询提现信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/withdrawalreport" })
	@RequestMapping("/queryReportWithdrawalInfo")
	public Object queryReportWithdrawalInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
											@RequestParam(defaultValue = "0", required = false) Integer page,
											@RequestParam(defaultValue = "10", required = false) Integer rows,
											@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "01");
		paramsMap.put("dept_type",loginSession.getDept_type());
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
	 * 查询经纪人提现信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/bwithdrawalreport" })
	@RequestMapping("/queryReportBWithdrawalInfo")
	public Object queryReportBWithdrawalInfo(@Session(create = false) SessionProvider session,ModelMap modelMap,HttpServletRequest request,
											 @RequestParam(defaultValue = "0", required = false) Integer page,
											 @RequestParam(defaultValue = "10", required = false) Integer rows,
											 @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "02");
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
		PageList<?> list = (PageList<?>)reportRecAndWithdraInfoService.queryReportWithdrawalInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

	/**
	 * 充值信息统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargereport" })
	@RequestMapping("/queryRechargeSum")
	public Object queryRechargeSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
	 * 经纪人充值信息统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brechargereport" })
	@RequestMapping("/queryBRechargeSum")
	public Object queryBRechargeSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
									@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "02");
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
	@RequiresPermissions(value = {"/report/withdrawalreport" })
	@RequestMapping("/queryWithdrawalSum")
	public Object queryWithdrawalSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
									 @RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "01");
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
		list = reportRecAndWithdraInfoService.queryReportWithdrawalCount(paramsMap);
		return list;
	}

	/**
	 * 经纪人提现信息统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/bwithdrawalreport" })
	@RequestMapping("/queryBWithdrawalSum")
	public Object queryBWithdrawalSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
									  @RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("role_type", "02");
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
		list = reportRecAndWithdraInfoService.queryReportWithdrawalCount(paramsMap);
		return list;
	}

	/**
	 * 保证金充值信息统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargereport" })
	@RequestMapping("/queryRechargeEnsureSum")
	public Object queryRechargeEnsureSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
										 @RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());

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
		//查询统计信息
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = reportRecAndWithdraInfoService.queryReportRechargeEnsureCount(paramsMap);
		return list;
	}

	/**
	 * 查询充值结果导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/rechargereport" })
	@RequestMapping("/queryRechargeForExcel")
	public Object queryRechargeForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
										@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		paramsMap.put("dept_type",loginSession.getDept_type());
		//查询信息
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportRecAndWithdraInfoService.queryRechargeExport(paramsMap);

		JSONObject object=new JSONObject();

		if (daysBetween2(paramsMap.get("begindate").toString(),paramsMap.get("enddate").toString())>24)
		{
			object.put("data", "error");
			return object;
		}
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportRechargeInfo");
		return object;
	}

	/**
	 * 充值结果导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargereport" })
	@RequestMapping("/exportRechargeInfo")
	public void exportRechargeInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
								   @RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();

		if(!"".equals(StringUtils.checkString(map.get("mobile"))))
		{
			paramsMap.put("mobile",map.get("mobile").toString());
		}

		list = reportRecAndWithdraInfoService.queryRechargeExport(map);
		//手机号解密
		listafter = PhoneCodeSSL.getUserPhoneList(list, paramsMap);
		if(listafter.size()>0){
			ExcelUtil.buildXSLXExcel(listafter, "充值结果", response);
			map.clear();
		}

	}

	/**
	 * 查询提现结果导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/withdrawalreport" })
	@RequestMapping("/queryWithdrawalForExcel")
	public Object queryWithdrawalForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
										  @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("dept_type",loginSession.getDept_type());
		//查询信息
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportRecAndWithdraInfoService.queryWithdrawalExport(paramsMap);
		JSONObject object=new JSONObject();

		if (daysBetween2(paramsMap.get("begindate").toString(),paramsMap.get("enddate").toString())>24)
		{
			object.put("data", "error");
			return object;
		}

		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportWithdrawalInfo");
		return object;
	}

	/**
	 * 提现结果导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/withdrawalreport" })
	@RequestMapping("/exportWithdrawalInfo")
	public void exportWithdrawalInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
									 @RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();
		if(!"".equals(StringUtils.checkString(map.get("mobile"))))
		{
			paramsMap.put("mobile",map.get("mobile").toString());
		}
		list = reportRecAndWithdraInfoService.queryWithdrawalExport(map);
		//手机号解密
		listafter = PhoneCodeSSL.getUserPhoneList(list, paramsMap);
		if(listafter.size()>0){
			ExcelUtil.buildXSLXExcel(listafter, "提现结果", response);
			map.clear();
		}

	}


	/**
	 * 查询保证金结果
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/rechargeensurereport" })
	@RequestMapping("/queryEnsureForExcel")
	public Object queryEnsureForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
									  @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("dept_type",loginSession.getDept_type());
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
		//查询信息
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = reportRecAndWithdraInfoService.queryEnsureInfoExport(paramsMap);
		JSONObject object=new JSONObject();

//		if (daysBetween2(paramsMap.get("begindate").toString(),paramsMap.get("enddate").toString())>24)
//		{
//			object.put("data", "error");
//			return object;
//		}

		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportEnsureInfo");
		return object;
	}

	/**
	 * 保证金结果导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/rechargeensurereport" })
	@RequestMapping("/exportEnsureInfo")
	public void exportEnsureInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
								 @RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();
//		if(!"".equals(StringUtils.checkString(map.get("mobile"))))
//		{
//			paramsMap.put("mobile",MySecurity.encryptAES(map.get("mobile").toString(), SystemConfig.keyMy));
//		}
		list = reportRecAndWithdraInfoService.queryEnsureInfoExport(map);
		//手机号解密
		//listafter = PhoneCodeSSL.getUserPhoneList(list, paramsMap);
		if(list.size()>0){
			ExcelUtil.buildXSLXExcel(list, "保证金结果", response);
			map.clear();
		}

	}


	public int daysBetween2(String startTime, String endTime) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar cal = Calendar.getInstance();
		long time1 = 0;
		long time2 = 0;

		try{
			cal.setTime(sdf.parse(startTime));
			time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(endTime));
			time2 = cal.getTimeInMillis();
		}catch(Exception e){
			e.printStackTrace();
		}
		long between_days=(time2-time1)/(1000*3600);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
