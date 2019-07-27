package com.acl.sys.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:SysKeyControlController
 *author:wangli
 *createDate:2016年12月2日 下午3:11:59
 *vsersion:3.0
 *department:安创乐科技
 *description:redis use_key控制
 */
@Controller
@RequestMapping("/sys")
public class SysKeyControlController extends CoreBaseController {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/keyControl" })
    @RequestMapping("/keyControl")
	public String dictInfo(){
		return "sys/sys_key_control";
	}
	
	//查询redis中use_key的值
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/keyControl" })
	@RequestMapping("/queryKeyControl")
	public Object queryKeyControl(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		JSONObject json=new JSONObject();
		String key = "use_key";
		String keyName=StringUtils.convertString(redisTemplate.opsForValue().get(key));
		if(keyName==null ||keyName.equals("") ){
			json.put("status", "关闭");
		}else {
			json.put("status", keyName);
		}
		return json;
	}
	
	//修改redis中use_key的值
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/keyControl" })
	@RequestMapping("/updKeyControl")
	public Object updKeyControl(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		try {
			String key = "use_key";
			String openKey=paramsMap.get("openKey").toString();
	        redisTemplate.opsForValue().set(key, String.valueOf(openKey));
	        msg.setMsg("开启成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("开启失败");
			msg.setSuccess(false);
		}
	
		return msg;
	}
	
	//删除redis中use_key的值
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/keyControl" })
	@RequestMapping("/delKeyControl")
	public Object delKeyControl(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		try {
			redisTemplate.delete("use_key");
			msg.setMsg("关闭成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("关闭失败");
			msg.setSuccess(false);
		}        
		return msg;
	}
	
}
