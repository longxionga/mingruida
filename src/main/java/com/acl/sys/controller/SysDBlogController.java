package com.acl.sys.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.DBLog;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.DBLogService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

@Controller
@RequestMapping("/sys")
public class SysDBlogController extends CoreBaseController {
	@Autowired
	private DBLogService dbLogService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/dbLog" })
	@RequestMapping("/dbLog")
	public String dbLog(@Session(create = false) SessionProvider session) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		if (0!=StringUtils.checkInt(loginSession.getDept_type())) {
			return "401";
		}
		return "sys/sys_db_log";
	}
	
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/dbLog" })
	@RequestMapping("/queryDBLog")
	public Object queryDBLog(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap) {
		if (!"".equals(StringUtils.checkString(paramsMap.get("begin_date")))) {
			paramsMap.put("begin_date", paramsMap.get("begin_date").toString()+" 00:00:00");
			if ("".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
				paramsMap.put("end_date", DateFormatUtil.convertCurrentDate("yyyy-MM-dd 23:59:59"));
			}
		}
		if (!"".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
			paramsMap.put("end_date", paramsMap.get("end_date").toString()+" 23:59:59");
		}
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString("log_time.desc"));
		PageList<DBLog> list = (PageList<DBLog>) dbLogService.searchLogBean(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
	
}
