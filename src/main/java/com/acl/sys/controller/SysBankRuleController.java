package com.acl.sys.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.acl.sys.service.ISysSalaRuleService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;


import com.acl.sys.service.ISysBankRuleService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:SysBankRuleController
 *author:wangli
 *createDate:2016年8月18日 下午7:42:44
 *vsersion:3.0
 *department:安创乐科技
 *description:交易规则
 */
@Controller
@RequestMapping("/sys")
public class SysBankRuleController extends CoreBaseController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/bankRule" })
	@RequestMapping("/bankRule")
	public String dictInfo(){
		return "sys/sys_bank_rule";
	}






	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/salaRule" })
	@RequestMapping("/salaRule")
	public String salaRule(){
		return "sys/sys_sala_rule";
	}

	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/salaRule" })
	@RequestMapping("/querySalaRule")
	public Object querySalaRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
								@RequestParam(defaultValue = "0", required = false) Integer page,
								@RequestParam(defaultValue = "10", required = false) Integer rows,
								@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		paramsMap.put("sala_type","01");
		PageList<?> list = (PageList<?>)sysSalaRuleService.querySalaRule(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}



	/**
	 * 修改提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/salaRule" })
	@RequestMapping("/updSalaRule")
	public Object updSalaRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			sysSalaRuleService.updateSalaRule(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}


	@Autowired
	private ISysBankRuleService sysBankRuleService;

	@Autowired
	private ISysSalaRuleService sysSalaRuleService;
	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/bankRule" })
	@RequestMapping("/queryBankRule")
	public Object queryBankRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		//查询提现充值规则
		PageList<?> list = (PageList<?>)sysBankRuleService.queryBankRule(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

//	/**
//	 * 添加提现充值规则
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/sys/bankRule" })
//	@RequestMapping("/insBankRule")
//	public Object insertBankRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			String sys_id=UUIDGenerator.generate();
//			paramsMap.put("sys_id", sys_id);
//			paramsMap.put("create_date", sdf.format(new Date()));
//			//添加提现充值规则
//			sysBankRuleService.insertBankRule(paramsMap);
//			msg.setMsg("新增成功");
//			msg.setSuccess(true);
//		}catch(Exception e){
//			msg.setMsg("插入失败");
//			msg.setSuccess(false);
//			e.printStackTrace();
//		}
//		return msg;
//	}

	/**
	 * 修改提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/brandRule" })
	@RequestMapping("/updBankRule")
	public Object updateBankRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			//更新提现充值规则
			sysBankRuleService.updateBankRule(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}



//	/**
//	 * 删除提现充值规则
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/sys/bankRule" })
//	@RequestMapping("/delBankRule")
//	public Object deleteBankRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			//删除提现充值规则
//			sysBankRuleService.deleteBankRule(paramsMap);
//			msg.setMsg("删除成功");
//			msg.setSuccess(true);
//		}catch(Exception e){
//			msg.setMsg("删除失败");
//			msg.setSuccess(false);
//			e.printStackTrace();
//		}
//		return msg;
//	}


	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/brandRule" })
	@RequestMapping("/brandRule")
	public String brandRule(){
		return "sys/sys_brand_rule";
	}

	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/brandRule" })
	@RequestMapping("/queryBrandRule")
	public Object queryBrandRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
								 @RequestParam(defaultValue = "0", required = false) Integer page,
								 @RequestParam(defaultValue = "10", required = false) Integer rows,
								 @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		//查询提现充值规则
		PageList<?> list = (PageList<?>)sysBankRuleService.queryBrandRule(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/brandRule" })
	@RequestMapping("/doSumBrandRule")
	public Object doSumBrandRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
								 @RequestParam(defaultValue = "0", required = false) Integer page,
								 @RequestParam(defaultValue = "10", required = false) Integer rows,
								 @RequestParam HashMap<String,Object> paramsMap){
		try{
			paramsMap.put("create_day",paramsMap.get("begindate").toString().replaceAll("-",""));
			sysSalaRuleService.doSumBrandRule(paramsMap);
			msg.setMsg("计算成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("计算失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
}
