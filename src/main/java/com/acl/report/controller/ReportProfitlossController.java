package com.acl.report.controller;

import java.util.HashMap;
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
import com.acl.report.service.IReportProfitlossService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:ReportProfitlossController
 *author:wangli
 *createDate:2017年3月2日 上午9:45:37
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportProfitlossController extends CoreBaseController {
	@Autowired
	private IReportProfitlossService reportProfitlossService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/userProfitloss" })
	@RequestMapping("userProfitloss")
	public String brokerMonth(){
		return "/report/report_user_profitloss";
	}
	
	/**
	 * 查询用户流水报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/userProfitloss" })
	@RequestMapping("/queryUserProfitloss")
	public Object queryUserProfitloss(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}	
		if(paramsMap.get("sort")!=null){
			paramsMap.put("order_type", paramsMap.get("sort").toString()+" "+paramsMap.get("order").toString());
			pageBounds = new PageBounds(page,rows,Order.formString(paramsMap.get("sort").toString()+"."+paramsMap.get("order").toString()));
		}	
		//查询用户流水 
		PageList<?> list = (PageList<?>)reportProfitlossService.queryReportUserProfitloss(paramsMap, pageBounds);
		PageList<?>  listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
		Object json = this.getJsonMap(listMap);		
        return json;
	}
	
	/**
	 * 用户流水报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/userProfitloss" })
	@RequestMapping("/queryUserProfitlossSum")
	public Object queryUserProfitlossSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}	
		//查询转入汇总	
		List<Map<String, Object>> list = reportProfitlossService.queryReportUserInto(paramsMap);
		//查询转出汇总
		List<Map<String, Object>> list1 = reportProfitlossService.queryReportUserRollOut(paramsMap);		
		JSONObject jObject=new JSONObject();
		jObject.put("into_count", list.get(0).get("intoCount"));
		jObject.put("roll_out_count",list1.get(0).get("outCount"));
		return jObject;

	}
	

}
