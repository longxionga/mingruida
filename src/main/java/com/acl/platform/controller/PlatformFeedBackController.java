package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.PlatformFeedBackService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformFeedBackController
 *author:wangli
 *createDate:2017年7月4日 上午9:26:29
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/platform")
public class PlatformFeedBackController extends CoreBaseController {
	
	@Autowired
	private PlatformFeedBackService platformFeedBackService;
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/feedBack" })
	@RequestMapping("/feedBack")
	public String userInfo(){
		return "platform/platform_feed_back";
	}
	
	 /**
	 * 查询用户反馈信息
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param paramsMap
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/feedBack" })
	@RequestMapping("/queryFeedBack")
	public Object queryFeedBack(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap) {
		PageBounds pageBounds = new PageBounds(page, rows,Order.formString(""));
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
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		case "6"://经纪人
			paramsMap.put("dept_id", loginSession.getDept_id());
			break;
		}
		// 查询代理商部门信息
		PageList<?> list = (PageList<?>) platformFeedBackService.queryFeedBack(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}


	/**
	 * 查询用户反馈信息
	 *
	 * @param request
	 * @param page
	 * @param rows
	 * @param paramsMap
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/feedBack" })
	@RequestMapping("/queryFeedBackInfo")
	public Object queryFeedBackInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
								@RequestParam(defaultValue = "0", required = false) Integer page,
								@RequestParam(defaultValue = "10", required = false) Integer rows,
								@RequestParam HashMap<String, Object> paramsMap) {
		PageBounds pageBounds = new PageBounds(page, rows,Order.formString(""));
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
				paramsMap.put("dept_id", loginSession.getDept_id());
				break;
			case "6"://经纪人
				paramsMap.put("dept_id", loginSession.getDept_id());
				break;
		}
		// 查询反馈信息
		List<Map<String, Object>> list = platformFeedBackService.queryFeedBack(paramsMap);
		//Object json = this.getJsonMap(list);
		return list;
	}
	
}
