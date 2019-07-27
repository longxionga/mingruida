package com.acl.sys.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.acl.sys.service.ISysCenterInfoService;
import com.acl.sys.service.SysDeptInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:SysCenterInfoController
 *author:wangli
 *createDate:2016年8月16日 下午3:21:18
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */

@Controller
@RequestMapping("/sys")
public class SysCenterInfoController extends CoreBaseController {
	@Autowired
	private ISysCenterInfoService sysCenterInfoService;
	
	@Autowired
	private SysDeptInfoService sysDeptInfoService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/centerInfo" })
	@RequestMapping("/centerInfo")
	public String CenterInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		return "sys/sys_center_info";
	}
	
	/**
	 * 查询后台消息中心信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/centerInfo" })
	@RequestMapping("/queryCenterInfo")
	public Object queryCenterInfo(HttpServletRequest request, 
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(orderByCreateDate));
		//查询用户信息
        
		PageList<?> list = (PageList<?>)sysCenterInfoService.queryCenterInfo(paramsMap,pageBounds);
		//paramsMap.put("roleName", loginSession.getRole_name());
		Object json = this.getJsonMap(list);
		
		return json;

	}
	
	/**
	 * 查询后台消息中心信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryMsg")
	public Object queryMsg(HttpServletRequest request, 
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		
		//查询用户信息
		//long newDate = System.currentTimeMillis();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newDate=format.format(date);
		paramsMap.put("newDate", newDate);
		PageList<?> list=sysCenterInfoService.queryMsg(paramsMap);
		return list;

	}
	
	/**
	 * 插入后台用户信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/centerInfo" })
	@RequestMapping("/insCenterInfo")
	public Object insertCenterInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{		
			List<Map<String, Object>> list = sysDeptInfoService.queryDeptInfo(paramsMap);
			paramsMap.put("dept_type", list.get(0).get("dept_type"));
		 sysCenterInfoService.insertCenterInfo(paramsMap);
		 msg.setMsg("新增成功");
		 msg.setSuccess(true);
		}catch(Exception e){
		 msg.setMsg("插入失败");
		 msg.setSuccess(false);
		 e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 更新后台消息中心信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/centerInfo" })
	@RequestMapping("/updCenterInfo")
	public Object updateCenterInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
		List<Map<String, Object>> list = sysDeptInfoService.queryDeptInfo(paramsMap);
		paramsMap.put("dept_type", list.get(0).get("dept_type"));
		 sysCenterInfoService.updateCenterInfo(paramsMap);
		 msg.setMsg("更新成功");
		 msg.setSuccess(true);
		}catch(Exception e){
		 msg.setMsg("更新失败");
		 msg.setSuccess(false);
		 e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 删除菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/centerInfo" })
	@RequestMapping("/delCenterInfo")
	public Object deleteCenterInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			paramsMap.put("center_id", paramsMap.get("center_id"));
			sysCenterInfoService.deleteCenterInfo(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("删除失败");
			msg.setSuccess(false);

		}
		return msg;
	}
	
}
