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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.pojo.LoginSession;
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
 *className:ReportUserBalanceController
 *author:wangli
 *createDate:2017年3月17日 上午11:30:51
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportUserBalanceInfoController extends CoreBaseController{
	@Autowired
	private IPlatformUserInfoService platformUserInfoService;
	
	@RequiresAuthentication
    @RequiresPermissions(value = { "/report/balanceInfo" })
    @RequestMapping("/balanceInfo")
    public String dictInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		return "report/report_balance_info";
    }

	 /**
     * 查询前台用户信息
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/report/balanceInfo" })
    @RequestMapping("/queryUserBalance")
    public Object queryUserBalance(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
    	//判断传过来的参数是否是带有解密
    	String decryption =StringUtils.checkString( paramsMap.get("decryption"));
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
    	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
    	
    	if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}	
    	
   		PageList<?> list = (PageList<?>) platformUserInfoService.queryUserInfo(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap =null;
   		if(decryption.equals("decryption")){   			
   			//带有解密参数就不加****
   			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   		}else{
   			listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
   		}  		
   	     Object json = this.getJsonMap(listMap);
		 return json;
    }

    
    /**
	 * 用户余额报表统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/brokerProfitloss" })
	@RequestMapping("/queryUserBalanceSum")
	public Object queryBrokerProfitlossSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		//查询转入汇总	
		List<Map<String, Object>> list = platformUserInfoService.queryUserBalanceSum(paramsMap);
		JSONObject jObject=new JSONObject();
		jObject.put("balance_count", list.get(0).get("balance_count"));
		return jObject;

	}
	
	
}
