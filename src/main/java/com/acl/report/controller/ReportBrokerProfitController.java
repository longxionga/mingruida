package com.acl.report.controller;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.acl.report.service.IReportBrokerProfitService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;

import com.acl.pojo.LoginSession;

import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;


/**
 *className:ReportBrokerDayAndMonthController
 *author:wangli
 *createDate:2017年9月4日 上午11:26:51
 *vsersion:3.0
 *department:安创乐科技
 *description:经纪人净充值报表
 */
@Controller
@RequestMapping("/report")
public class ReportBrokerProfitController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();
	@Autowired
	private IReportBrokerProfitService reportBrokerProfitService;

	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerProfit" })
	@RequestMapping("brokerProfit")
	public String brokerProfit(){
		return "/report/report_broker_profit";
	}
	

	
	/**
	 * 查询经纪人净充值报表
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/brokerProfit" })
	@RequestMapping("/queryBrokerProfitReport")
	public Object queryBrokerProfitReport(@Session(create = false) SessionProvider session,HttpServletRequest request,
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
		//查询经纪人净充值报表
		PageList<?> list = (PageList<?>)reportBrokerProfitService.queryReportBrokerProfit(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);		
        return json;
	}
	


}
