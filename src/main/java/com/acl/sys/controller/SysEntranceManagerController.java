package com.acl.sys.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.acl.pojo.LoginSession;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:SysEntranceManagerController
 *author:wangli
 *createDate:2017年5月31日 上午10:46:46
 *vsersion:3.0
 *department:安创乐科技
 *description:充值提现入口管理
 */
@Controller
@RequestMapping("/sys")
public class SysEntranceManagerController extends CoreBaseController {
		
		@Autowired
		private  StringRedisTemplate redisTemplate;
	
		@RequiresAuthentication
	    @RequiresPermissions(value = { "/sys/entranceManage" })
	    @RequestMapping("/entranceManage")		
		public String entranceManager(@Session(create = false) SessionProvider session,ModelMap modelMap){
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			modelMap.addAttribute("deptType", loginSession.getDept_type());
			return "sys/sys_entrance_manager";
		}
	
	
	//查询redis中entrances的值
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = { "/sys/entranceManage" })
		@RequestMapping("/queryEntranceManager")
		public Object queryEntranceManager(@Session(create = false) SessionProvider session,HttpServletRequest request,
				@RequestParam HashMap<String, Object> paramsMap) {
			JSONObject json=new JSONObject();
			//权限判断  如果不是平台管理员则无法查询出结果
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
			if(!dept_type.equals("0")){
				json.put("total", 0);
				json.put("rows", "");
				return json;
			}
			
			String key = "entrances";
			String keyName=redisTemplate.opsForValue().get(key);
			if(keyName==null ||keyName.equals("") ){
				json.put("total", 0);
				json.put("rows", "");
			}else {
				JSONArray jsonArray=JSONArray.parseArray(redisTemplate.opsForValue().get(key));
				List<JSONObject> list = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class);
				Collections.sort(list, new Comparator<JSONObject>() {
				    @Override
				    public int compare(JSONObject o1, JSONObject o2) {
				        int a = o1.getInteger("entrance");
				        int b = o2.getInteger("entrance");
				        if (a > b) {
				            return 1;
				        } else if(a == b) {
				            return 0;
				        } else
				            return -1;
				        }
				});
				jsonArray = JSONArray.parseArray(list.toString());
				json.put("total", jsonArray.size());
				json.put("rows",jsonArray);
			}			
			return json;
		}
		
		//修改redis中entrances的值
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = { "/sys/entranceManage" })
		@RequestMapping("/updEntranceManager")
		public Object updEntranceManager(@Session(create = false) SessionProvider session,HttpServletRequest request,
				@RequestParam HashMap<String, Object> paramsMap) {
			try {
				String key = "entrances";
		        JSONArray jsonArray=JSONArray.parseArray(redisTemplate.opsForValue().get(key));		   
		        JSONArray jArray=new JSONArray();
		        for(int i=0 ; i < jsonArray.size() ;i++)
		        {
		        	
		         //获取每一个JsonObject对象
		         JSONObject myjObject = jsonArray.getJSONObject(i);
		         if(paramsMap.get("default").equals("1")&&!myjObject.get("entrance").equals(paramsMap.get("entrance"))){
		        	 myjObject.put("default", "0");
				    }
		         if(myjObject.get("entrance").equals(paramsMap.get("entrance"))){
		        	// JSONObject jObject=new JSONObject();
		        	 myjObject.put("agent_code", paramsMap.get("agent_code"));
		        	 myjObject.put("url", paramsMap.get("url"));
		        	 myjObject.put("entrance", paramsMap.get("entrance"));
		        	 myjObject.put("default", paramsMap.get("default"));
		         }
		         jArray.add(myjObject);
		     }
		        redisTemplate.opsForValue().set(key, jArray.toJSONString());
		        msg.setMsg("编辑成功");
				msg.setSuccess(true);
			} catch (Exception e) {
				msg.setMsg("编辑失败");
				msg.setSuccess(false);
			}
		
			return msg;
		}
		
		
		
		        //修改redis中entrances的值
				@ResponseBody
				@RequiresAuthentication
				@RequiresPermissions(value = { "/sys/entranceManage" })
				@RequestMapping("/insEntranceManager")
				public Object insEntranceManager(@Session(create = false) SessionProvider session,HttpServletRequest request,
						@RequestParam HashMap<String, Object> paramsMap) {	
					
					try {	
			        	String key = "entrances";
					    JSONArray jsonArray=JSONArray.parseArray(redisTemplate.opsForValue().get(key));	
					   //判断入口是否重复 
					    for(int i=0 ; i < jsonArray.size() ;i++)
				        {
					    	JSONObject jObject =jsonArray.getJSONObject(i);
					    	//jObject.get(jsonArray.get(i));
				         //获取每一个JsonObject对象
				         if(jObject.get("entrance").equals(paramsMap.get("entrance"))){
				        	 msg.setMsg("新增失败,入口不能重复！");
							 msg.setSuccess(false);
							 return msg;
				         }
				        }
					   
					  
					    //将原始数据添加到数组中
					    JSONArray jArray=new JSONArray(); 
					    JSONObject myjObject = new JSONObject();
						 myjObject.put("agent_code", paramsMap.get("agent_code"));
			        	 myjObject.put("url", paramsMap.get("url"));
			        	 myjObject.put("entrance", paramsMap.get("entrance"));
			        	 myjObject.put("default", paramsMap.get("default"));
			        	 jArray.add(myjObject);
					    for(int i=0 ; i < jsonArray.size() ;i++)
				        {
					    	myjObject=jsonArray.getJSONObject(i);
					    	  //判断新增是否为默认  如果为默认则将其余改为非默认
						    if(paramsMap.get("default").equals("1")){
						    	myjObject.put("default", "0");
						    }
						    jArray.add(myjObject);
				        }
					    
					    
				        redisTemplate.opsForValue().set(key, jArray.toJSONString());
				        msg.setMsg("新增成功");
						msg.setSuccess(true);
					} catch (Exception e) {
						msg.setMsg("新增失败");
						msg.setSuccess(false);
					}
				
					return msg;
				}
	
				/**
				 * 删除字典信息
				 * @return
				 */
				@ResponseBody
				@RequiresAuthentication
			    @RequiresPermissions(value = { "/sys/entranceManage" })
				@RequestMapping("/delEntranceManager")
				public Object delEntranceManager(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
					try{	
						//删除字典信息
			        	String key = "entrances";
					    JSONArray jsonArray=JSONArray.parseArray(redisTemplate.opsForValue().get(key));
					    JSONArray jArray=new JSONArray();
					    for(int i=0 ; i < jsonArray.size() ;i++)
				        {
				        	
				         //获取每一个JsonObject对象
				         JSONObject myjObject = jsonArray.getJSONObject(i);
				         if(myjObject.get("entrance").equals(paramsMap.get("entrance"))){
				        	 if(myjObject.get("default").equals("1")){
				        		 msg.setMsg("删除失败,该入口为默认入口不可删除");
								 msg.setSuccess(false);
								 return msg;
				        	 }
				         }
				         if(!myjObject.get("entrance").equals(paramsMap.get("entrance"))){
				        	 jArray.add(myjObject);
				         }
				        
				     }				       
				        redisTemplate.opsForValue().set(key, jArray.toJSONString());
						msg.setMsg("删除成功");
						msg.setSuccess(true);
					}catch(Exception e){
						msg.setMsg("删除失败");
						msg.setSuccess(false);
						e.printStackTrace();
					}
					return msg;
				}
				

}
