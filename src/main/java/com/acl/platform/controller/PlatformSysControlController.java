package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformSysControlService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

import net.sf.json.JSONArray;

/**
 *className:PlatformSysControlController
 *author:wangli
 *createDate:2016年10月29日 上午10:49:57
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/platform")
public class PlatformSysControlController extends CoreBaseController {
	@Autowired
	private IPlatformSysControlService platformSysControlService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/sysControl" })
    @RequestMapping("/sysControl")
    public String sysControl(@Session(create = false) SessionProvider session,ModelMap modelMap){
	return "platform/platform_sys_control";
    }
	 /**
	 * 查询系统控制信息
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param paramsMap
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/querySysControl")
	public Object querySysControl(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap) {
		PageBounds pageBounds = new PageBounds(page, rows,Order.formString(""));	
		// 查询代理商部门信息
		PageList<?> list = (PageList<?>) platformSysControlService.querySysControl(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 更新系统控制信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })	
	@RequestMapping("/updSysControl")
	public Object updateSysControl(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
		 platformSysControlService.updateSysControl(paramsMap);		
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
	 * 插入系统控制信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })	
	@RequestMapping("/insSysControl")
	public Object insertSysControl(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap,HttpServletRequest request) {		
		try {
			paramsMap.put("sys_id", UUIDGenerator.generate());
			platformSysControlService.insertSysControl(paramsMap);
			msg.setMsg("新增成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("新增失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 删除系统控制信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })	
	@RequestMapping("/delSysControl")
	public Object deleteSysControl(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap,HttpServletRequest request) {		
		try {
			platformSysControlService.deleteSysControl(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("删除失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * 查询开关是否开启
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/checkUse")
	public Object checkUse(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String, Object>> list = platformSysControlService.checkUse(paramsMap);		
		return list;
	}
	
	/**
	 * 开关重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/typeValidate")
	public Object typeValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = platformSysControlService.typeValidate(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
	
	/*
	 * 查询模式一与部门信息关系
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/queryDeptDisableInfo")
	public Object queryDeptDisableInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		
		//String str = redisTemplate.opsForValue().get("disable_agent_id").toString();
		String str = StringUtils.checkString(redisTemplate.opsForValue().get("disable_agent_id"));
		//str.replace("##", ",");
		str = str.replaceAll("##", ",");
		str = str.replaceAll("#", "");
		paramsMap.put("dept_id", str);
		//List<Map<String,Object>> list =paramsMap;
		String jsonStr = JSONArray.fromObject(paramsMap).toString();         
	    JSONArray ja = JSONArray.fromObject(jsonStr);
	    return ja;
	}
	
	/**
	 * 保存禁用代理商信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/saveDeptDisable")
	public Object saveDeptDisable(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白	
		try {
			StringBuffer sb = new StringBuffer();
			String dept_id = paramsMap.get("dept_id").toString();
			if (dept_id != "") 
			{			
				if(dept_id.split(",").length>0)
				{
					for(int i = 0; i<dept_id.split(",").length;i++)
					{
						sb.append("#"+ dept_id.split(",")[i].toString()  +"#");
					}
				}
				redisTemplate.opsForValue().set("disable_agent_id", sb.toString());

				// platformGdsyInfoService.updateGdsyInfo(paramsMap);
				msg.setMsg("保存成功");
				msg.setSuccess(true);
		}else
		{
			//没有选择一个代理的时候删除redis里的key
			redisTemplate.delete("disable_agent_id");
			msg.setMsg("保存成功");
			msg.setSuccess(true);
//			msg.setMsg("请选择部门");
//			msg.setSuccess(false);
		}
		} catch (Exception e) {
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}



	/**
	 * 禁用代理商信息提现
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/saveDeptNoRecharge")
	public Object saveDeptNoRecharge(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白
		try {
			StringBuffer sb = new StringBuffer();
			String dept_id = paramsMap.get("dept_id").toString();
			if (dept_id != "")
			{
				if(dept_id.split(",").length>0)
				{
					for(int i = 0; i<dept_id.split(",").length;i++)
					{
						sb.append("#"+ dept_id.split(",")[i].toString()  +"#");
					}
				}
				redisTemplate.opsForValue().set("recharge_agent_id", sb.toString());

				// platformGdsyInfoService.updateGdsyInfo(paramsMap);
				msg.setMsg("保存成功");
				msg.setSuccess(true);
			}else
			{
				//没有选择一个代理的时候删除redis里的key
				redisTemplate.delete("recharge_agent_id");
				msg.setMsg("保存成功");
				msg.setSuccess(true);
				//msg.setMsg("请选择部门");
				//msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 禁用代理商信息出金
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/saveDeptNoWithdrawals")
	public Object saveDeptNoWithdrawals(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白
		try {
			StringBuffer sb = new StringBuffer();
			String dept_id = paramsMap.get("dept_id").toString();
			if (dept_id != "")
			{
				if(dept_id.split(",").length>0)
				{
					for(int i = 0; i<dept_id.split(",").length;i++)
					{
						sb.append("#"+ dept_id.split(",")[i].toString()  +"#");
					}
				}
				redisTemplate.opsForValue().set("withdrawals_agent_id", sb.toString());

				// platformGdsyInfoService.updateGdsyInfo(paramsMap);
				msg.setMsg("保存成功");
				msg.setSuccess(true);
			}else
			{
				//没有选择一个代理的时候删除redis里的key
				redisTemplate.delete("withdrawals_agent_id");
				msg.setMsg("保存成功");
				msg.setSuccess(true);
//				msg.setMsg("请选择部门");
//				msg.setSuccess(false);
			}
		} catch (Exception e) {
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/*
	 * 查询提现代理信息
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/queryDeptWithdrawalsInfo")
	public Object queryDeptWithdrawalsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){

		//String str = redisTemplate.opsForValue().get("disable_agent_id").toString();
		String str = StringUtils.checkString(redisTemplate.opsForValue().get("withdrawals_agent_id"));
		//str.replace("##", ",");
		str = str.replaceAll("##", ",");
		str = str.replaceAll("#", "");
		paramsMap.put("dept_id", str);
		//List<Map<String,Object>> list =paramsMap;
		String jsonStr = JSONArray.fromObject(paramsMap).toString();
		JSONArray ja = JSONArray.fromObject(jsonStr);
		return ja;
	}

	/*
	 * 查询充值信息
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/sysControl" })
	@RequestMapping("/queryDeptRechargeInfo")
	public Object queryDeptRechargeInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){

		//String str = redisTemplate.opsForValue().get("disable_agent_id").toString();
		String str = StringUtils.checkString(redisTemplate.opsForValue().get("recharge_agent_id"));
		//str.replace("##", ",");
		str = str.replaceAll("##", ",");
		str = str.replaceAll("#", "");
		paramsMap.put("dept_id", str);
		//List<Map<String,Object>> list =paramsMap;
		String jsonStr = JSONArray.fromObject(paramsMap).toString();
		JSONArray ja = JSONArray.fromObject(jsonStr);
		return ja;
	}
}
