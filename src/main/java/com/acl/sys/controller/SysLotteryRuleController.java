package com.acl.sys.controller;

import com.acl.core.CoreBaseController;
import com.acl.sys.service.SysLotteryRuleService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Controller
@RequestMapping("/sys")
public class SysLotteryRuleController extends CoreBaseController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/lotteryRule" })
	@RequestMapping("/lotteryRule")
	public String lotteryRule(){
		return "sys/sys_lottery_rule";
	}


	@Autowired
	private SysLotteryRuleService sysLotteryRuleService;
	/**
	 * 查询活动规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/lotteryRule" })
	@RequestMapping("/queryLotteryRule")
	public Object queryLotteryRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		//查询活动规则
		PageList<?> list = (PageList<?>)sysLotteryRuleService.queryLotteryRule(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

	/**
	 * 修改活动规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/lotteryRule" })
	@RequestMapping("/updateLotteryRule")
	public Object updateLotteryRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			//更新活动规则
			sysLotteryRuleService.updateLotteryRule(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}


}
