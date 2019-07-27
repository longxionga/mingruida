package com.acl.platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.platform.service.PlatformBrokerService;
import com.acl.utils.util.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:PlatformBrokerController author:wangli createDate:2016年8月29日
 * 下午5:05:57 vsersion:3.0 department:安创乐科技 description:前台代理商管理
 */
@Controller
@RequestMapping("/platform")
public class PlatformBrokerController extends CoreBaseController {
    @Autowired
    private PlatformBrokerService platformBrokerService;
    //private static String mkey ="ANCHOL2016";
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/broker")
	 public String brokerInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
		return "platform/platform_broker";
    }

    /**
     * 查询前台用户信息
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/queryBroker")
    public Object queryUserlogin(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    	String sortString = "";
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
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		case "6"://代理商
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));	
		//查询字典信息
		PageList<Map> list = (PageList<Map>)platformBrokerService.queryBrokerInfo(paramsMap,pageBounds);
		list.forEach(x -> {
			x.put("broker_mobile",StringUtils.getHideMobile(StringUtils.checkString(x.get("broker_mobile"))));
			if(org.apache.commons.lang3.StringUtils.isNotEmpty((String)x.get("parent_broker_mobile"))){
				x.put("parent_broker_mobile",StringUtils.getHideMobile((String)x.get("parent_broker_mobile")));
			}
		});
		Object json = this.getJsonMap(list);
		return json;
    }

    /**
     * 重置密码
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/updBrokerPwd")
    public Object updateBrokerPwd(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
	    // 重置密码
	    platformBrokerService.updateBrokerPwd(paramsMap);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}
	return msg;
    }

    /**
     * 禁用
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/updBrokerState")
    public Object updateBrokerState(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
	    // 禁用用户
	    platformBrokerService.updateBrokerState(paramsMap);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}
	return msg;
    }
    
    /**
     * 查询某代理商所属部门下的所有代理商
     * @param session
     * @param request
     * @param page
     * @param rows
     * @param paramsMap
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/queryBrokerUser")
    public Object queryBrokerUser(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam(defaultValue = "0", required = false) Integer page,
	    @RequestParam(defaultValue = "10", required = false) Integer rows,
	    @RequestParam HashMap<String, Object> paramsMap) {
    
    	PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
    	if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}	
    	
    	PageList<?> list = (PageList<?>) platformBrokerService.queryBrokerUser(paramsMap, pageBounds);
		//手机号数据库解密
   		PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
		Object json = this.getJsonMap(listMap);
    
		return json;
    }

    
    /**
     * 更改代理商所属上级代理商
     * @param session
     * @param request
     * @param paramsMap
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/broker" })
    @RequestMapping("/updBrokerUser")
    public Object updateBrokerUser(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap, @RequestParam HashMap<String, Object> loginMap) {
    	//String broker_id=StringUtils.convertString(paramsMap.get("broker_id"));
    	String broker_name=StringUtils.convertString(paramsMap.get("broker_name"));
    	//String current_broker_id=StringUtils.convertString(paramsMap.get("current_broker_id"));
    	String current_broker_name=StringUtils.convertString(paramsMap.get("current_broker_name"));
    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
    	try {
		dbLog.setMethod_name("代理商更换上级代理商");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
	    platformBrokerService.updateBrokerUser(paramsMap);
	    dbLog.setAction_message(broker_name+"代理商更换上级代理商为:"+current_broker_name+"成功");
		dbLog.setIs_ok(0);
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
		dbLog.setAction_message(broker_name+"代理商更换上级代理商为:"+current_broker_name+"失败");
		dbLog.setIs_ok(1);
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}finally{
		dbLogService.insertLog(dbLog);
	}
	return msg;
    }
    
    /**
	 * 查询formongo
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/broker" })	
	@RequestMapping("/queryBrokerForMon")
	public Object queryBrokerForMon(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			//查询数据库所有代理商信息
			List<Map<String,Object>> list=platformBrokerService.queryBrokerForMon(paramsMap);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=new HashMap<String,Object>();
				
				String dept_code=StringUtils.convertString(list.get(i).get("dept_code"));
				String dept_name=StringUtils.convertString(list.get(i).get("dept_name"));
				
				//String is_use=StringUtils.convertString(list.get(i).get("is_use"));
				/*String dept_ratio=StringUtils.convertString(list.get(i).get("dept_ratio"));
				String dept_money=StringUtils.convertString(list.get(i).get("dept_money"));
				String dept_mobile=StringUtils.convertString(list.get(i).get("dept_mobile"));
				String is_tj_man=StringUtils.convertString(list.get(i).get("is_tj_man"));
				String tj_ratio=StringUtils.convertString(list.get(i).get("tj_ratio"));
				String dept_url=StringUtils.convertString(list.get(i).get("dept_url"));
				String broker_url=StringUtils.convertString(list.get(i).get("broker_url"));*/
				String dept_id=StringUtils.convertString(list.get(i).get("dept_id"));
				/*String dept_parent_id=StringUtils.convertString(list.get(i).get("dept_parent_id"));
				String dept_type=StringUtils.convertString(list.get(i).get("dept_type"));
				String create_date=StringUtils.convertString(list.get(i).get("create_date"));
				*/
				String broker_id=StringUtils.convertString(list.get(i).get("broker_id"));
				/*
				String broker_name=StringUtils.convertString(list.get(i).get("broker_name"));
				String broker_password=StringUtils.convertString(list.get(i).get("broker_password"));
				String broker_parent_id=StringUtils.convertString(list.get(i).get("broker_parent_id"));
				String broker_incode=StringUtils.convertString(list.get(i).get("broker_incode"));
				
				String dept_app_id=StringUtils.convertString(list.get(i).get("dept_app_id"));
				String dept_app_secret=StringUtils.convertString(list.get(i).get("dept_app_secret"));
				
				
				String broker_mobile=StringUtils.convertString(list.get(i).get("broker_mobile"));
				String broker_parent_incode=StringUtils.convertString(list.get(i).get("broker_parent_incode"));
				String broker_wx_id=StringUtils.convertString(list.get(i).get("broker_wx_id"));
				String broker_money=StringUtils.convertString(list.get(i).get("broker_money"));
				String broker_login_id=StringUtils.convertString(list.get(i).get("broker_login_id"));
				String broker_zf_wx_id=StringUtils.convertString(list.get(i).get("broker_zf_wx_id"));*/
				//查询mongodb中是否有此条数据		
				//如果有执行修改操作
				//如果没有执行新增操作
				map.put("broker_id", broker_id);	
				Update update = new Update();
			    update.set("dept_code",dept_code);
			/*	update.set("dept_name",dept_name);
				update.set("is_use",is_use);
				update.set("dept_ratio",dept_ratio);
				update.set("dept_money",dept_money);
				update.set("dept_mobile",dept_mobile);
				update.set("is_tj_man",is_tj_man);
				update.set("tj_ratio",tj_ratio);				
				update.set("dept_url",dept_url);
				update.set("broker_url",broker_url);*/
				update.set("dept_id",dept_id);
				/*update.set("dept_parent_id",dept_parent_id);					
				update.set("dept_type",dept_type);
				update.set("create_date",create_date);				
				update.set("broker_id", broker_id);
				update.set("broker_name", broker_name);
				update.set("broker_password", broker_password);
				update.set("broker_parent_id", broker_parent_id);
				update.set("broker_incode", broker_incode);				
				update.set("dept_app_id",dept_app_id);
				update.set("dept_app_secret",dept_app_secret);					
				update.set("broker_mobile",broker_mobile);
				update.set("broker_parent_incode",broker_parent_incode);
				update.set("broker_wx_id",broker_wx_id);
				update.set("broker_money",broker_money);
				update.set("broker_login_id",broker_login_id);
				update.set("broker_zf_wx_id",broker_zf_wx_id);*/
			//	mongoTemplate.upsert(new Query(Criteria.where("broker_id").is(broker_id)),update, "t_front_broker");
				paramsMap.clear();
				paramsMap.put("broker_id", broker_id);
				List<Map<String,Object>> list1=platformBrokerService.queryBrokerUserForMon(paramsMap);
				if(list1.size()>0){
					for(int j=0;j<list1.size();j++){
						Map<String,Object> map1=new HashMap<String,Object>();
						map1.put("user_id", list1.get(j).get("user_id"));	
						Update update1 = new Update();
					    update1.set("dept_id",dept_id);
					    update1.set("dept_name",dept_name);
					}
				}
					
			}
			
		}
			
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
		 * 普通代理商升级为高级代理商
		 * @return
		 */
		@ResponseBody
		@RequiresAuthentication
		@RequiresPermissions(value = { "/platform/broker" })	
		@RequestMapping("/upBroker")
		public Object upBroker(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
			try{
				//判断绑定的用户是否已经注销，如果已经注销则不能升级
				//String broker_mobile=request.getParameter("broker_mobile");
				//String user_mobile=MySecurity.encryptAES(broker_mobile, SystemConfig.keyMy);

				paramsMap.put("broker_id",paramsMap.get("b_id"));
				Map<String,Object> mapBroker = platformBrokerService.queryUserById(paramsMap);
				if(mapBroker == null || mapBroker.size() ==0 || "2".equals(mapBroker.get("broker_level"))){
					msg.setMsg("升级失败，该用户已经注销不可升级！");
					msg.setSuccess(false);
					return msg;
				}
				//更改代理商等级
				paramsMap.put("broker_level", "2");
				platformBrokerService.upBroker(paramsMap);
				//更改mongodb中front_broker
				Map<String,Object> map1=new HashMap<String,Object>();
				map1.put("broker_id", request.getParameter("b_id"));	
				Update update = new Update();
			    update.set("broker_level","2");
				//查询各个层级的分成比例
				//List<Map<String,Object>> list=platformBrokerService.queryBrokerAllot(paramsMap);
				List<Map<String,Object>> list=platformBrokerService.queryAgentAllot(paramsMap);
				//计算代理商分成比例
				//根据代理商id查询代理商，查询t_back_dept_info，查询各个层级的比例分配
				if(list.size()>0){
					int ce_allot=Integer.parseInt(list.get(0).get("ce_allot").toString());
					int ch_allot=Integer.parseInt(list.get(0).get("ch_allot").toString());
					int s_allot=Integer.parseInt(list.get(0).get("s_allot").toString());;
					int a_allot=Integer.parseInt(list.get(0).get("a_allot").toString());;				
					int b_allot=100-ce_allot-ch_allot-s_allot-a_allot;
					HashMap<String, Object> map=new HashMap<>();
					map.put("sys_id", UUIDGenerator.generate());
					map.put("p_id", StringUtils.convertString(list.get(0).get("p_id").toString()));
					map.put("ce_id", StringUtils.convertString(list.get(0).get("ce_id").toString()));
					map.put("ch_id", StringUtils.convertString(list.get(0).get("ch_id").toString()));
					map.put("s_id", StringUtils.convertString(list.get(0).get("s_id").toString()));
					map.put("a_id", StringUtils.convertString(list.get(0).get("a_id").toString()));
					map.put("b_id", paramsMap.get("b_id"));
					map.put("p_allot", StringUtils.convertString(list.get(0).get("p_allot").toString()));
					map.put("ce_allot", StringUtils.convertString(list.get(0).get("ce_allot").toString()));
					map.put("ch_allot",StringUtils.convertString( list.get(0).get("ch_allot").toString()));
					map.put("s_allot", StringUtils.convertString(list.get(0).get("s_allot").toString()));
					map.put("a_allot", StringUtils.convertString(list.get(0).get("a_allot").toString()));
					if(b_allot==0){
						map.put("b_allot", "0");
					}else{
						map.put("b_allot", b_allot);
					}					
					map.put("is_use", "1");
					map.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));					
					//将临时表中历史信息置为不可用
					platformBrokerService.updateBrokerProrateTemp(paramsMap);
					//存入临时表中
					platformBrokerService.insertBrokerProrateTemp(map);
					//查询分配比例表，如果没有记录则插入一条
					List<Map<String,Object>> list1=platformBrokerService.queryBrokerForProrate(map);
					if(list1.size()==0)
					{
						map.put("is_use", "0");
						platformBrokerService.insertBrokerProrate(map);
					}		
					}
				
				
			 msg.setMsg("升级成功");
			 msg.setSuccess(true);
			}catch(Exception e){
			 msg.setMsg("升级失败");
			 msg.setSuccess(false);
			 e.printStackTrace();
			}
			return msg;
		}
		
		
		   /**
			 * 普通代理商升级为高级代理商
			 * @return
			 */
			@ResponseBody
			@RequiresAuthentication
			@RequiresPermissions(value = { "/platform/broker" })	
			@RequestMapping("/downBroker")
			public Object downBroker(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
				try{
					//更改代理商等级_降级
					paramsMap.put("broker_level", "1");
					platformBrokerService.upBroker(paramsMap);
					//将mongodb中的front_broker中broke_level置为1
					Map<String,Object> map1=new HashMap<String,Object>();
					map1.put("broker_id", request.getParameter("b_id"));	
					Update update = new Update();
				    update.set("broker_level","1");
					//将比例表中历史信息置为不可用  t_back_dept_prorate
					platformBrokerService.updateBrokerProrate(paramsMap);
					//将临时表中历史信息置为不可用 t_back_dept_prorate_temp
					platformBrokerService.updateBrokerProrateTemp(paramsMap);					
				 msg.setMsg("降级成功");
				 msg.setSuccess(true);
				}catch(Exception e){
				 msg.setMsg("降级失败");
				 msg.setSuccess(false);
				 e.printStackTrace();
				}
				return msg;
			}



	@RequiresAuthentication
	@RequestMapping("/userinfoDept")
	public String userinfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
		return "platform/platform_userinfo_dept";
	}

	/**
	 * 查询前台用户信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryUserInfoDept")
	public Object queryUserInfoDept(@Session(create = false) SessionProvider session, HttpServletRequest request,
								 @RequestParam(defaultValue = "0", required = false) Integer page,
								 @RequestParam(defaultValue = "10", required = false) Integer rows,
								 @RequestParam HashMap<String, Object> paramsMap) {
		String sortString = "";
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
				paramsMap.put("DID", loginSession.getDept_id());
				break;
			case "6"://代理商
				paramsMap.put("DID", loginSession.getDept_id());
				break;
		}
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询字典信息
		PageList<Map> list = (PageList<Map>)platformBrokerService.queryUserInfoDept(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
}

