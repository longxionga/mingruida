package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformBankCardService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.PhoneCodeSSL;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformBankCardController
 *author:wangli
 *createDate:2017年6月1日 上午9:12:49
 *vsersion:3.0
 *department:安创乐科技
 *description:用户银行卡管理
 */
@Controller
@RequestMapping("/platform")
public class PlatformBankCardController extends CoreBaseController {
	
	@Autowired
	private PlatformBankCardService platformBankCardService;
	
	
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/bankCard" })
    @RequestMapping("/bankCard")
    public String bankCard(){
	return "platform/platform_bank_card";
    }
	
	/**
	 * 查询商品图片信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/bankCard" })
	@RequestMapping("/queryBankCard")
	public Object queryBankCard(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		String dept_type=loginSession.getDept_type();
		paramsMap.put("dept_type", dept_type);
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));		
		//查询用户信息
		PageList<?> list = (PageList<?>)platformBankCardService.queryBankCard(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap =null;
   		listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);   		 		
   	     Object json = this.getJsonMap(listMap);
		 return json;

	}
	
	
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/platform/bankCard" })
	@RequestMapping("/queryBankCardInfo")	
	public Object queryBankCardInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		String dept_type=loginSession.getDept_type();
		paramMap.put("dept_type", dept_type);
		List<HashMap<String, Object>> dataList = platformBankCardService.queryBankCardInfo(paramMap);
		dataList=PhoneCodeSSL.getPhone(dataList, paramMap);
		return dataList;
	}
}
