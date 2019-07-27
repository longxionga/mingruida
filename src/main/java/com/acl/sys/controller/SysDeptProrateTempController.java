package com.acl.sys.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;


import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysDeptProrateTempService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysDeptProrateTempController author:huangs createDate:2016年8月13日
 * 下午4:21:34 vsersion:3.0 department:安创乐科技 description:
 */
@Controller
@RequestMapping("/sys")
public class SysDeptProrateTempController extends CoreBaseController {
    @Autowired
    private ISysDeptProrateTempService sysDeptProrateTempService;

	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/deptProrateTemp" })
    @RequestMapping("/deptProrateTemp")
    public String deptProrateTemp(@Session(create = false) SessionProvider session,ModelMap modelMap) {
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
    	modelMap.addAttribute("deptType", loginSession.getDept_type());
    	return "sys/sys_dept_prorate_temp";
    }

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/agentProrateTemp" })
	@RequestMapping("/agentProrateTemp")
	public String agentProrateTemp(@Session(create = false) SessionProvider session,ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		return "sys/sys_agent_prorate_temp";
	}




	/**
     * 查询分配比率信息
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/deptProrateTemp" })
    @RequestMapping("/queryDeptProrateTemp")
    public Object queryDeptProrateTemp(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
	String dept_type = loginSession.getDept_type();
	String dept_id = loginSession.getDept_id();
	switch (dept_type) {
	case "0":
	    paramsMap.put("p_id", dept_id);
	    break;
	case "1":
	    paramsMap.put("ce_id", dept_id);
	    break;
	case "2":
	    paramsMap.put("ch_id", dept_id);
	    break;
	case "3":
	    paramsMap.put("s_id", dept_id);
	    break;
	case "4":
	    paramsMap.put("a_id", dept_id);
	    break;
	case "5":
		paramsMap.put("b_id", dept_id);
		break;
	// default:
	// //默认平台
	// paramsMap.put("p_id", dept_code);
	// break;
	}
	// 通过登陆用户所属dept_id 查询 该dept树下所有子节点dept_id
	// 然后通过获得的dept_id获取分配比率清单
	// PageList<?> list =
	// (PageList<?>)sysDeptProrateTempService.queryDeptProrateTemp(paramsMap,pageBounds);
	PageList<?> list = (PageList<?>) sysDeptProrateTempService.queryDeptProrateTempByView(paramsMap, pageBounds);
	Object json = this.getJsonMap(list);
	return json;
    }



	/**
	 * 查询代理商分配比率信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/agentProrateTemp" })
	@RequestMapping("/queryAgentProrateTemp")
	public Object queryAgentProrateTemp(@Session(create = false) SessionProvider session, HttpServletRequest request,
									   @RequestParam(defaultValue = "0", required = false) Integer page,
									   @RequestParam(defaultValue = "10", required = false) Integer rows,
									   @RequestParam HashMap<String, Object> paramsMap) {
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type = loginSession.getDept_type();
		String dept_id = loginSession.getDept_id();
		switch (dept_type) {
			case "0":
				paramsMap.put("p_id", dept_id);
				break;
			case "1":
				paramsMap.put("ce_id", dept_id);
				break;
			case "2":
				paramsMap.put("ch_id", dept_id);
				break;
			case "3":
				paramsMap.put("s_id", dept_id);
				break;
			case "4":
				paramsMap.put("a_id", dept_id);
				break;
			case "5":
				paramsMap.put("b_id", dept_id);
				break;
			// default:
			// //默认平台
			// paramsMap.put("p_id", dept_code);
			// break;
		}
		// 通过登陆用户所属dept_id 查询 该dept树下所有子节点dept_id
		// 然后通过获得的dept_id获取分配比率清单
		// PageList<?> list =
		// (PageList<?>)sysDeptProrateTempService.queryDeptProrateTemp(paramsMap,pageBounds);
		PageList<?> list = (PageList<?>) sysDeptProrateTempService.queryAgentProrateTemp(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

    /**
     * 分配比率
     * 
     * @param paramsMap
     * @param request
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/deptProrateTemp" })
    @RequestMapping("/updDeptProrateTemp")
    public Object updateDeptProrateTemp(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
    	// 判断是否为双休 双休时间不允许修改
    	int week = FormatDateUtil.getWeek(Calendar.getInstance());
    	if (0 == week || 6 == week) {
    		msg.setMsg("双休日不得进行比率分配调整！");
    		msg.setSuccess(false);
    		return msg;
    	}
//    	if (20>StringUtils.checkInt(paramsMap.get("b_allot"))) {
//    		msg.setMsg("经纪人比率分配不得超过20%，请重新分派");
//    		msg.setSuccess(false);
//    		return msg;
//		}
		try {
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
			dbLog.setMethod_name("比率变更");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			String ce_allot=StringUtils.convertString(paramsMap.get("ce_allot"));
			String ch_allot=StringUtils.convertString(paramsMap.get("ch_allot"));
			String s_allot=StringUtils.convertString(paramsMap.get("s_allot"));
			String a_allot=StringUtils.convertString(paramsMap.get("a_allot"));
			String b_allot=StringUtils.convertString(paramsMap.get("b_allot"));
			// 置旧记录为历史
			sysDeptProrateTempService.updateDeptProrateTemp(paramsMap);
			// 添加新的纪录
			paramsMap.put("sys_id", UUIDGenerator.generate());
			paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
			sysDeptProrateTempService.insertDeptProrateTemp(paramsMap);
			dbLog.setAction_message("成功,分配后比率为：交易中心:"+ce_allot+"渠道:"+ch_allot+"结算会员："+s_allot+"代理商："+a_allot+"经纪人："+b_allot);
			dbLog.setIs_ok(0);	
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			dbLog.setAction_message("失败");
			dbLog.setIs_ok(1);
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}finally{
			dbLogService.insertLog(dbLog);
		}
	return msg;
    }


	/**
	 * 分配比率
	 *
	 * @param paramsMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/agentProrateTemp" })
	@RequestMapping("/updAgentProrateTemp")
	public Object updAgentProrateTemp(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		// 判断是否为双休 双休时间不允许修改
		int week = FormatDateUtil.getWeek(Calendar.getInstance());
		if (0 == week || 6 == week) {
			msg.setMsg("双休日不得进行比率分配调整！");
			msg.setSuccess(false);
			return msg;
		}
//    	if (20>StringUtils.checkInt(paramsMap.get("b_allot"))) {
//    		msg.setMsg("经纪人比率分配不得超过20%，请重新分派");
//    		msg.setSuccess(false);
//    		return msg;
//		}
		try {
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			dbLog.setMethod_name("比率变更");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			String ce_allot=StringUtils.convertString(paramsMap.get("ce_allot"));
			String ch_allot=StringUtils.convertString(paramsMap.get("ch_allot"));
			String s_allot=StringUtils.convertString(paramsMap.get("s_allot"));
			String a_allot=StringUtils.convertString(paramsMap.get("a_allot"));
			String b_allot_1=StringUtils.convertString(paramsMap.get("b_allot_1"));
			String b_allot_2=StringUtils.convertString(paramsMap.get("b_allot_2"));
			String b_allot_3=StringUtils.convertString(paramsMap.get("b_allot_3"));
			// 置旧记录为历史
			sysDeptProrateTempService.updateAgentProrateTemp(paramsMap);
			// 添加新的纪录
			paramsMap.put("sys_id", UUIDGenerator.generate());
			paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
			sysDeptProrateTempService.insertAgentProrateTemp(paramsMap);
			dbLog.setAction_message("成功,分配后比率为：交易中心:"+ce_allot+"渠道:"+ch_allot+"结算会员："+s_allot+"代理商："+a_allot+"比例1："+b_allot_1+"比例2："+b_allot_2+"比例3："+b_allot_3);
			dbLog.setIs_ok(0);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			dbLog.setAction_message("失败");
			dbLog.setIs_ok(1);
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}finally{
			dbLogService.insertLog(dbLog);
		}
		return msg;
	}
}
