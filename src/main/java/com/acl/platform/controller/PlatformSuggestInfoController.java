package com.acl.platform.controller;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformSimUserInfoService;
import com.acl.platform.service.IPlatformSuggestInfoService;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.platform.service.PlatformBrokerService;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysIndexService;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.RandomValidateCode;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:PlatformFrontUserInfo author:wangli createDate:2016年8月25日 下午2:56:16
 * vsersion:3.0 department:安创乐科技 description:
 */
@Controller
@RequestMapping("/platform")
public class PlatformSuggestInfoController extends CoreBaseController {
    @Autowired
    private IPlatformSuggestInfoService platformSuggestInfoService;

	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/suggestInfo" })
    @RequestMapping("/suggestInfo")
    public String dictInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
	return "platform/platform_suggest_info";
    }

    /**
     * 查询前台用户信息
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/suggestInfo" })
    @RequestMapping("/querySuggestInfo")
    public Object querySuggestInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
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

		if(!"".equals(StringUtils.checkString(paramsMap.get("id_card"))))
		{
			paramsMap.put("id_card",paramsMap.get("id_card").toString());
		}
		/*//查询所有启用的用户
		paramsMap.put("status",1);*/
    	
   		PageList<?> list = (PageList<?>) platformSuggestInfoService.querySuggestInfo(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap =null;
   		if(decryption.equals("decryption")){   			
   			//带有解密参数就不加****
   			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   		}else{
   			//listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   		}  		
   	     Object json = this.getJsonMap(listMap);
		 return json;
    }

}
