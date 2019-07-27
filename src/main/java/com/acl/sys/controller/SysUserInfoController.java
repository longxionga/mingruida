package com.acl.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.utils.util.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.tree.ComboTree;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.distributedlock.DistributedLock;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysUserInfoController author:malx createDate:2016年8月10日 下午4:26:46
 * vsersion:3.0 department:安创乐科技 description:后台用户管理
 */

@Controller
@RequestMapping("/sys")
public class SysUserInfoController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();

	@Autowired
	private SysUserInfoService sysUserInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private DistributedLock distributedLock;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/userInfo")
	public String userInfo() {
		return "sys/sys_user_info";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userBalanceInfo" })
	@RequestMapping("/userBalanceInfo")
	public String userBalanceInfo() {
		return "sys/sys_back_operation_info";
	}

	/**
	 * 查询后台用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/queryUserInfo")
	public Object queryUserInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap) {
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString(orderByCreateDate));
		PageList<?> list = (PageList<?>) sysUserInfoService.queryUserInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}

	/**
	 * 插入后台用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/insUserInfo")
	public Object insertUserInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		try {
			// trim登陆名前后空白
			if (paramsMap != null && paramsMap.containsKey("user_name")) {
				paramsMap.put("user_name", paramsMap.get("user_name").toString().trim());
			}
			paramsMap.put("user_id", UUIDGenerator.generate());
			paramsMap.put("user_password", MD5Utils.MD5(SystemConfig.AncholPWD));
			paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
			sysUserInfoService.insertUserInfo(paramsMap);
			msg.setMsg("新增成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("插入失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 查询所有部门信息 应该只有超级管理员用到 需要强制权限
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/queryDeptInfo")
	public Object queryDeptInfo() {
		List<ComboTree> list = new ArrayList<ComboTree>();
		ComboTree tree = new ComboTree();
		tree.setId("0");// 默认的总缺省值
		tree.setText("平台管理");
		tree.setChildren(initTree("0"));
		list.add(tree);
		return list;

		/*
		 * 用一条语句查出来的数据  未能构建成树
		 * Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("dept_id", "0");
		List<Map<String, Object>> depts = sysUserInfoService.queryDeptInfoByChildList(paramsMap);
		return depts;*/
	}

	/**
	 * 初始化树
	 * 
	 * @param dept_id
	 * @return
	 */
	private List<ComboTree> initTree(String dept_id) {
		List<ComboTree> list = new ArrayList<ComboTree>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dept_id", dept_id);
		List<Map<String, Object>> depts = sysUserInfoService.queryTreeDeptInfo(map);
		if (depts != null) {
			for (Map<String, Object> u : depts) {
				ComboTree tree = new ComboTree();
				tree.setId(u.get("DEPT_ID").toString());
				tree.setText(u.get("DEPT_NAME").toString());
				tree.setChildren(initTree(u.get("DEPT_ID").toString()));
				list.add(tree);
			}
		}
		return list;
	}

	/**
	 * 登录名称重复性验证
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/userValidate")
	public Object userValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		List<Map<String, Object>> list = sysUserInfoService.userValidate(paramsMap);
		if (list.size() == 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
		}
		return msg;
	}

	/**
	 * 后台用户注册手机号重复性验证
	 * @param paramsMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/mobileValidate")
	public Object mobileValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		List<Map<String, Object>> list = sysUserInfoService.mobileValidate(paramsMap);
		if (list.size() == 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
		}
		return msg;
	}//mobileValidate
	
	
	/**
	 * 修改后台用户信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/updUserInfo")
	public Object updateUserInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		try {
			/*1、查询该用户的原始部门信息
			 * 2、比对部门信息是否修改
			 * 3、如果部门信息修改则删除
			 * t_back_user_role表中的用户与角色的关联信息
			 * （避免后期用户重新分配权限后造成的数据冗余）		 * 
			 */
			List<Map<String, Object>> list=sysUserInfoService.queryUserDeptInfo(paramsMap);			
			String dept_id=StringUtils.convertString(list.get(0).get("dept_id"));
			if(!paramsMap.get("dept_id").toString().equals(dept_id)){
				sysUserInfoService.deleteUserRole(paramsMap);
			}			
			sysUserInfoService.updateUserInfo(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 重置后台用户登陆密码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userInfo" })
	@RequestMapping("/resetPwd")
	public Object resetPwd(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		// 重置后台登陆密码为SystemConfig.AncholPWD
		paramsMap.put("user_id", paramsMap.get("user_id").toString());
		paramsMap.put("user_password", MD5Utils.MD5(SystemConfig.AncholPWD));
		try {
			sysUserInfoService.resetUserInfoPwd(paramsMap);
			msg.setMsg("重置成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("重置失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	
	
	/**
	 * 用户余额查询
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/userBalanceInfo" })
	@RequestMapping("/queryUserBalanceInfo")
	public Object queryUserBalanceInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
	
		boolean ismoney = false;
		//用户余额信
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
		List<Map<String, Object>> list =  sysUserInfoService.queryUserBalanceInfo(paramsMap);
	
		if(list.size()>0)
		{
			//list =
			//redis获取钱包的值
			String mobiledes = paramsMap.get("mobile").toString();
			String walletText = redisTemplate.opsForValue().get("wallet_"+list.get(0).get("user_id"));
			Map<String, Object> map = new HashMap<String, Object>(); 
			
			if(Double.valueOf(walletText) ==Double.valueOf(list.get(0).get("user_money").toString()))
			{
				ismoney=true;
			}
			map.put("RBalance", walletText);
			map.put("mobiledes", mobiledes);
			map.put("ismoney", ismoney);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 用户余额查询
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/userBalanceInfo" })
	@RequestMapping("/queryReportUserBalanceInfo")
	public Object queryReportUserBalanceInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap) {
	
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));
		//用户余额信
		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}
		PageList<?> list =  sysUserInfoService.queryReportUserBalanceInfo(paramsMap, pageBounds);

		if(list.size()>0)
		{
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
				HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
				if (map.containsKey("user_mobile")&&!"".equals(StringUtils.checkString(map.get("user_mobile")))) {
					map.put("user_mobile", StringUtils.checkString(map.get("user_mobile")));
				}
				if (map.containsKey("user_id")&&!"".equals(StringUtils.checkString(map.get("user_id")))) {
					map.put("userredismoney", StringUtils.checkString(redisTemplate.opsForValue().get("wallet_"+map.get("user_id").toString())));
				}

			}
		}
		
		PageList<?> listmap = PhoneCodeSSL.getUserMobileList(list,paramsMap);
		Object json = this.getJsonMap(listmap);
		return json;

	}
	
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userBalanceInfo" })
	@RequestMapping("/updUserBalanceInfo")
	public Object updateUserBalanceInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白		
//		try {
//			
//				//String user_id = paramsMap.get("user_id").toString();
//			if (!"".equals(StringUtils.checkString(paramsMap.get("user_id")))) {
//				List<Map<String, Object>> list = sysUserInfoService.queryUserBalanceInfo(paramsMap);
//
//				String lockKeyActivityLuckydrawOfWallet = distributedLock.getLock("wallet",list.get(0).get("user_id").toString());
//				if (lockKeyActivityLuckydrawOfWallet == null) {
//					return false;
//				}
//				redisTemplate.opsForValue().set("wallet_"+list.get(0).get("user_id"), list.get(0).get("user_money").toString());
//				msg.setMsg("修正成功");
//				msg.setSuccess(true);
//			} else {
//				msg.setMsg("修正失败，请核对用户ID");
//				msg.setSuccess(false);
//			}
//		} catch (Exception e) {
//			msg.setMsg("修正失败，请核对用户ID");
//			msg.setSuccess(false);
//			e.printStackTrace();
//		}
//		finally {
//			distributedLock.releaseLock(LockConstants.LOCK_WALLET, sellOrder.getUser_id(),
//					lockKeyActivityLuckydrawOfWallet);
//			}
		
		List<Map<String, Object>> list = sysUserInfoService.queryUserBalanceInfo(paramsMap);
		String lockKeyActivityLuckydrawOfWallet = distributedLock.getLock("wallet",	list.get(0).get("user_id").toString());
		if (lockKeyActivityLuckydrawOfWallet == null) {
			msg.setMsg("修正失败，请核对信息");
			msg.setSuccess(false);
			return false;			
		}
		try {			
			double userWallet = Double.valueOf(list.get(0).get("user_money").toString()); // 用户钱包
			//double newWallet = userWallet + orderAmount;
			redisTemplate.opsForValue().set("wallet_"+list.get(0).get("user_id"), String.valueOf(userWallet));
			msg.setMsg("修正成功");
			msg.setSuccess(true);
			
		} finally {
			distributedLock.releaseLock("wallet", list.get(0).get("user_id").toString(),lockKeyActivityLuckydrawOfWallet);
		}

		return msg;
	}
	
	/**
	 * 查询用户余额导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/userBalanceInfo" })
	@RequestMapping("/queryUserBalanceExcel")
	public Object queryUserBalanceExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		
		//查询信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysUserInfoService.queryUserBalanceExport(paramsMap);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportUserBalanceInfo");
        return object;
	}
	
	
	/**
	 * 经纪人月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/userBalanceInfo" })
	@RequestMapping("/exportUserBalanceInfo")
	public void exportUserBalanceInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		//查询经纪人信息
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();
		list = sysUserInfoService.queryUserBalanceExport(map);		
		//手机号解密
		if(list.size()>0)
		{
			for(int i =0;i<list.size();i++)
			{
				paramsMap.put("user_mb",StringUtils.getString(list.get(i).get("user_mb").toString()));
				paramsMap.put("user_id",list.get(i).get("user_id").toString());

				sysUserInfoService.updateUserMobile(paramsMap);
			}

		}

//		listafter = PhoneCodeSSL.getUserPhoneList(list, paramsMap);
		
		if(listafter.size()>0){
//		ExcelUtil.buildXSLXExcel(listafter, "用户余额", response);
		map.clear();
		}

	}
	
	
	
}