package com.acl.sys.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.pojo.DeptInfo;
import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysFrontBrokerService;
import com.acl.sys.service.ISysOptDeptInfoService;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ExcelUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysDeptInfoController author:huangs createDate:2016年8月10日 下午6:11:13
 * vsersion:3.0 department:安创乐科技 description:部门管理
 */
@Controller
@RequestMapping("/sys")
public class SysOptDeptInfoController extends CoreBaseController {
	Map<String,Object> map=new HashMap<>();
	@Autowired
	private ISysOptDeptInfoService sysOptDeptInfoService;

	@Autowired
	private ISysFrontBrokerService sysFrontBrokerService;


	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/opdeptInfo")
	public String deptInfo(@Session(create = false) SessionProvider session, ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		return "sys/sys_op_dept_info";
	}	
	

	/**
	 * 查询部门信息 代码层面组装 组织架构树
	 * 
	 * @return
	 */
/*	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/deptInfo" })
	@RequestMapping("/queryDeptInfos")
	public Object queryDeptInfos(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		List<DeptTreeGrid> list = new ArrayList<DeptTreeGrid>();
		DeptTreeGrid tree = new DeptTreeGrid();
//		HashMap<String, Object> map = new HashMap<>();
		paramsMap.put("dept_id", loginSession.getDept_id());
		// map.put("dept_parent_id", "-1");
		// map.put("dept_id", loginSession.getDept_id().equals("0") ? "-1" :
		// loginSession.getDept_id());
		List<Map<String, Object>> deptGrids = sysOptDeptInfoService.queryDeptInfo(paramsMap);
		if (deptGrids != null) {
			for (Map<String, Object> deptMap : deptGrids) {
				tree.setDept_id(deptMap.get("dept_id").toString());
				tree.setDept_code(deptMap.get("dept_code").toString());
				tree.setDept_name(deptMap.get("dept_name").toString());
				tree.setDept_mobile(deptMap.get("dept_mobile") == null ? "" : deptMap.get("dept_mobile").toString());
				tree.setDept_money(deptMap.get("dept_money") == null ? "" : deptMap.get("dept_money").toString());
				tree.setDept_ratio(Integer.parseInt(deptMap.get("dept_ratio").toString()));
				tree.setDept_type(deptMap.get("dept_type").toString());
				tree.setDept_database(
						deptMap.get("dept_database") == null ? "" : deptMap.get("dept_database").toString());
				tree.setDept_url(deptMap.get("dept_url") == null ? "" : deptMap.get("dept_url").toString());
				tree.setBroker_url(deptMap.get("broker_url") == null ? "" : deptMap.get("broker_url").toString());
				tree.setIs_tj_man(deptMap.get("is_tj_man") == null ? "" : deptMap.get("is_tj_man").toString());
				tree.setTj_ratio(deptMap.get("tj_ratio") == null ? "" : deptMap.get("tj_ratio").toString());
				tree.setDept_parent_id(deptMap.get("dept_parent_id").toString());
				tree.setIs_use(deptMap.get("is_use").toString());
				tree.setDept_app_id(deptMap.get("dept_app_id") == null ? "" : deptMap.get("dept_app_id").toString());
				tree.setDept_app_secret(
						deptMap.get("dept_app_secret") == null ? "" : deptMap.get("dept_app_secret").toString());
				tree.setChildren(initTree(deptMap.get("dept_id").toString()));
			}
		}
		list.add(tree);
		return list;
	}*/

	/*private List<DeptTreeGrid> initTree(String dept_id) {
		List<DeptTreeGrid> list = new ArrayList<DeptTreeGrid>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("dept_parent_id", dept_id);
		List<Map<String, Object>> deptGrids = sysOptDeptInfoService.queryDeptInfo(map);
		if (deptGrids != null) {
			for (Map<String, Object> deptMap : deptGrids) {
				DeptTreeGrid tree = new DeptTreeGrid();
				tree.setDept_id(StringUtils.checkString(deptMap.get("dept_id")));
				tree.setDept_code(StringUtils.checkString(deptMap.get("dept_code")));
				tree.setDept_name(StringUtils.checkString(deptMap.get("dept_name")));
				tree.setDept_mobile(StringUtils.checkString(deptMap.get("dept_mobile")));
				tree.setDept_money(StringUtils.checkString(deptMap.get("dept_money")));
				tree.setDept_ratio(StringUtils.checkInt(deptMap.get("dept_ratio")));//int
				tree.setDept_type(StringUtils.checkString(deptMap.get("dept_type")));
				tree.setDept_database(StringUtils.checkString(deptMap.get("dept_database")));
				tree.setDept_url(StringUtils.checkString(deptMap.get("dept_url")));
				tree.setBroker_url(StringUtils.checkString(deptMap.get("broker_url")));
				tree.setIs_tj_man(StringUtils.checkString(deptMap.get("is_tj_man")));
				tree.setTj_ratio(StringUtils.checkString(deptMap.get("tj_ratio")));
				tree.setDept_parent_id(StringUtils.checkString(deptMap.get("dept_parent_id")));
				tree.setIs_use(StringUtils.checkString(deptMap.get("is_use")));
				tree.setDept_app_id(StringUtils.checkString(deptMap.get("dept_app_id")));
				tree.setDept_app_secret(StringUtils.checkString(deptMap.get("dept_app_secret")));
				tree.setChildren(initTree(StringUtils.checkString(deptMap.get("dept_id"))));
				if("4".equals(StringUtils.checkString(deptMap.get("dept_type")))){
					tree.setState("closed");
				}
				list.add(tree);
			}
		}
		return list;
	}*/

	/**
	 * 查询部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryOPDeptInfoByPa")
	public Object queryDeptInfoByPa(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		HashMap<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("dept_id", loginSession.getDept_id());
		sysOptDeptInfoService.queryDeptChildNodeInfo(parmas);
		if (paramsMap.isEmpty()) {
			paramsMap.put("dept_id", loginSession.getDept_id());
		}
		//paramsMap.put("search", loginSession.getDept_id());

		List<Map<String, Object>> list = sysOptDeptInfoService.queryDeptInfo(paramsMap);
		Object json = JSONObject.toJSON(list);
		return json;
	}


	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/queryOPDeptInfos")
	public Object queryDeptInfos(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		int dept_id = StringUtils.checkInt(loginSession.getDept_id())==0?-1:StringUtils.checkInt(loginSession.getDept_id());
		paramsMap.put("dept_id", dept_id);
//		List<Map<String, Object>> treeClist = sysOptDeptInfoService.queryDeptChildInfo(paramsMap);
		sysOptDeptInfoService.queryDeptChildNodeInfo(paramsMap);
		List<DeptInfo> treeClist = sysOptDeptInfoService.queryDeptChildInfoPojo(paramsMap);
		Map<String, Object> mapjson = new HashMap<>();
		mapjson.put("total", treeClist.size());
		mapjson.put("rows", JSONObject.toJSON(treeClist));
		Object json = JSONObject.toJSON(mapjson);
		return json;
	}

	/**
	 * 查询子部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryOPDeptChildInfo")
	public Object queryDeptChildInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		if (paramsMap.isEmpty()) {
			paramsMap.put("dept_id", loginSession.getDept_id());
		}
		List<Map<String, Object>> list = sysOptDeptInfoService.queryDeptChildInfo(paramsMap);
		Map<String, Object> mapjson = new HashMap<>();
		mapjson.put("total", list.size());
		mapjson.put("rows", JSONObject.toJSON(list));
		Object json = JSONObject.toJSON(mapjson);
		return json;
	}

	/**
	 * 编辑部门信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/updOPDeptInfo")
	public Object updateDeptInfo(@Session(create = false) SessionProvider session,
			@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		int dept_type = StringUtils.checkInt(paramsMap.get("dept_type"));
		if (3 != dept_type) {
			paramsMap.put("is_tj_man", "0");
			paramsMap.put("tj_ratio", "0");
		} else {
			int tj_ratio = StringUtils.checkInt(paramsMap.get("tj_ratio"));
			if (0 > tj_ratio || tj_ratio > 100) {
				msg.setMsg("编辑失败,09021824推荐人比率分配范围为：0 ~ 100 。");
				msg.setSuccess(false);
				return msg;
			}
		}
		try {
			LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
			dbLog.setMethod_name("编辑部门信息");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
			sysOptDeptInfoService.updateDeptInfo(paramsMap);
			dbLog.setAction_message("成功,编辑后信息为部门编码:"+StringUtils.convertString(paramsMap.get("dept_code"))+"部门名称："+StringUtils.convertString(paramsMap.get("dept_name"))+"是否可用："+StringUtils.convertString(paramsMap.get("is_use"))+"分配比例："+StringUtils.convertString(paramsMap.get("dept_ratio"))+"部门金额："+StringUtils.convertString(paramsMap.get("dept_money"))+"联系电话"+StringUtils.convertString(paramsMap.get("dept_mobile"))+"微信id"+StringUtils.convertString(paramsMap.get("dept_app_id"))+"微信秘钥"+StringUtils.convertString(paramsMap.get("dept_app_secret"))+"是否有推荐人"+StringUtils.convertString(paramsMap.get("is_tj_man"))+"推荐人分成比例"+StringUtils.convertString(paramsMap.get("tj_ratio")));
			dbLog.setIs_ok(0);
			msg.setMsg("编辑成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			dbLog.setAction_message("失败");
			dbLog.setIs_ok(1);
			msg.setMsg("编辑失败,08111305");
			msg.setSuccess(false);
			e.printStackTrace();
		}finally{
			dbLogService.insertLog(dbLog);
		}
		return msg;
	}

//	/**
//	 * 验证
//	 * 
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequestMapping("/deptOPCodeValidate")
//	public Object deptCodeValidate(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
//		msg.setSuccess(false);
//		if (null == paramsMap.get("dept_code") || "".equals(paramsMap.get("dept_code"))) {
//			return msg;
//		}
//		if (2 > paramsMap.get("dept_code").toString().length()) {
//			return msg;
//		}
//		paramsMap.put("countBy", "codeOrName");
//		Map<String, Object> count = sysOptDeptInfoService.countDeptInfo(paramsMap);
//		int cou = StringUtils.checkInt(count.get("num"));
//		//int cou = Integer.parseInt(count.get("num").toString());
//		if (cou > 0) {
//			msg.setSuccess(false);
//		} else {
//			msg.setSuccess(true);
//		}
//		return msg;
//	}

	/**
	 * 插入部门信息 dept_parent_id dept_type 父节点的值 dept_code dept_name 验证 必须
	 * dept_money dept_mobile dept_parent_id 必须 dept_database dept_url可为空
	 * dept_type dept_ratio 特殊
	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/sys/opdeptInfo" })
//	@RequestMapping("/insOPDeptInfo")
//	public Object insertDeptInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
//		int dept_type = StringUtils.checkInt(null == paramsMap.get("dept_type") ? "-1" : paramsMap.get("dept_type"));
//		//int dept_type = Integer.parseInt(null == paramsMap.get("dept_type") ? "-1" : paramsMap.get("dept_type").toString());
//		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
//		try {
//		dbLog.setMethod_name("新增部门信息");
//		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
//		dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
//		if (-1 == dept_type) {
//			msg.setMsg("操作失败");
//			msg.setSuccess(false);
//			dbLog.setAction_message("操作失败");
//			dbLog.setIs_ok(1);
//			return msg;
//		}
//		
//			paramsMap.put("countBy", "codeOrName");
//			Map<String, Object> count = sysOptDeptInfoService.countDeptInfo(paramsMap);
//			//int cou = Integer.parseInt(count.get("num").toString());
//			int cou = StringUtils.checkInt(count.get("num"));
//			if (cou > 0) {
//				msg.setMsg("插入失败,08111003部门编码或名称已存在。");
//				msg.setSuccess(false);
//				dbLog.setAction_message("插入失败,08111003部门编码或名称已存在。");
//				dbLog.setIs_ok(2);
//				return msg;
//			}
//			paramsMap.remove("countBy");
//			// dept_type dept_ratio
//			// 平台 0 0
//			// 交易中心 1 10A
//			// 渠道部 2 10B
//			// 中亿富融 3 20C < (100-10A-10B)
//			// 财趣 4 20D =< (100-10A-10B-20C)
//			// 部门一 5 0
//			// 10A+10B+20C+20D =< 100
//			/*
//			 * 已经分配了的比率
//			 */
//			if (dept_type >= 4) {
//				dept_type = 5;
//				paramsMap.put("dept_ratio", "0");
//			} else {
//				dept_type += 1;
//			}
//			paramsMap.put("dept_type", dept_type + "");
//			paramsMap.put("is_tj_man",
//					paramsMap.get("is_tj_man") == null ? "0" : paramsMap.get("is_tj_man").toString());
//			paramsMap.put("tj_ratio", paramsMap.get("tj_ratio") == null ? "0" : paramsMap.get("tj_ratio").toString());
//			if (dept_type != 3) {
//				paramsMap.put("is_tj_man", "0");
//				paramsMap.put("tj_ratio", "0");
//			} else {
//				int tj_ratio = Integer.parseInt(paramsMap.get("tj_ratio").toString());
//				if (0 > tj_ratio && tj_ratio > 100) {
//					msg.setMsg("插入失败,09021824推荐人比率分配范围为：0 ~ 100 。");
//					msg.setSuccess(false);
//					dbLog.setAction_message("插入失败,09021824推荐人比率分配范围为：0 ~ 100 。");
//					dbLog.setIs_ok(3);
//					return msg;
//				}
//			}
//			paramsMap.put("is_use", paramsMap.get("is_use") == null ? "0" : paramsMap.get("is_use").toString());// 默认为1
//			int dept_ratio = Integer.parseInt(paramsMap.get("dept_ratio").toString());
//			if (dept_ratio != 0) {
//				HashMap<String, Object> queryMap = new HashMap<String, Object>();
//				queryMap.put("dept_id", paramsMap.get("dept_parent_id").toString());
//				List<Map<String, Object>> pqrentList = sysOptDeptInfoService.queryDeptParentInfo(queryMap);
//				int current_ratio = 0;
//				for (Map<String, Object> map : pqrentList) {
//					current_ratio += Integer.parseInt(map.get("dept_ratio").toString());
//				}
//				if (dept_ratio > 100 - current_ratio) {
//					msg.setMsg("插入失败,08111033交易比率分配不得超过" + (100 - current_ratio) + "。");
//					msg.setSuccess(false);
//					dbLog.setAction_message("插入失败,08111033交易比率分配不得超过" + (100 - current_ratio) + "。");
//					dbLog.setIs_ok(4);
//					return msg;
//				}
//			}
//			// 从0计数就不用加一 不再使用uuid
//			paramsMap.put("dept_id", sysOptDeptInfoService.countDeptInfo(null).get("num1").toString());
//			paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
//			sysOptDeptInfoService.insertDeptInfo(paramsMap);
//			if (dept_type == 4) {
//				/**
//				 * 初始化一个部门
//				 */
//				String dept_code = paramsMap.get("dept_code").toString();
//				String dept_name = paramsMap.get("dept_name").toString();
//				paramsMap.put("dept_parent_id", paramsMap.get("dept_id").toString());
//				paramsMap.put("dept_name", dept_name + "-初始化部门");
//				paramsMap.put("dept_code", dept_code + "-1");
//				paramsMap.put("dept_ratio", "0");
//				paramsMap.put("dept_money", "0");
//				paramsMap.put("dept_mobile", "-1");
//				paramsMap.put("dept_type", "5");
//				paramsMap.remove("dept_app_id");
//				paramsMap.remove("dept_app_secret");
//				// paramsMap.put("dept_id", UUIDGenerator.generate());
//				paramsMap.put("dept_id", sysOptDeptInfoService.countDeptInfo(null).get("num1").toString());
//				sysOptDeptInfoService.insertDeptInfo(paramsMap);
//
//				/**
//				 * 创建代理商 初始化一个顶级经纪人 t_front_broker
//				 * 
//				 * broker_code 时间戳 broker_name 时间戳 broker_parent_id -1 dept_id
//				 * 本id broker_incode 000000 is_use 1
//				 */
//				paramsMap.put("dept_code", dept_code);
//				paramsMap.put("dept_name", dept_name);
//				sysFrontBrokerService.insertFrontInitBroker(paramsMap);
//			}
//			if (dept_type == 4) {
//				dbLog.setAction_message("新增成功,部门编码:"+StringUtils.convertString(paramsMap.get("dept_code"))+"部门名称："+StringUtils.convertString(paramsMap.get("dept_name"))+"是否可用："+StringUtils.convertString(paramsMap.get("is_use"))+"分配比例："+StringUtils.convertString(paramsMap.get("dept_ratio"))+"部门金额："+StringUtils.convertString(paramsMap.get("dept_money"))+"联系电话:"+StringUtils.convertString(paramsMap.get("dept_mobile"))+"微信id:"+StringUtils.convertString(paramsMap.get("dept_app_id"))+"微信秘钥:"+StringUtils.convertString(paramsMap.get("dept_app_secret"))+"是否有推荐人:"+StringUtils.convertString(paramsMap.get("is_tj_man"))+"推荐人分成比例:"+StringUtils.convertString(paramsMap.get("tj_ratio")));
//				dbLog.setIs_ok(1);
//				msg.setMsg("新增成功，请注意添加代理商初始分配比率模板");
//			}else{
//				dbLog.setAction_message("新增成功,部门编码:"+StringUtils.convertString(paramsMap.get("dept_code"))+"部门名称："+StringUtils.convertString(paramsMap.get("dept_name"))+"是否可用："+StringUtils.convertString(paramsMap.get("is_use"))+"分配比例："+StringUtils.convertString(paramsMap.get("dept_ratio"))+"部门金额："+StringUtils.convertString(paramsMap.get("dept_money"))+"联系电话:"+StringUtils.convertString(paramsMap.get("dept_mobile"))+"微信id:"+StringUtils.convertString(paramsMap.get("dept_app_id"))+"微信秘钥:"+StringUtils.convertString(paramsMap.get("dept_app_secret"))+"是否有推荐人:"+StringUtils.convertString(paramsMap.get("is_tj_man"))+"推荐人分成比例:"+StringUtils.convertString(paramsMap.get("tj_ratio")));
//				dbLog.setIs_ok(1);
//				msg.setMsg("新增成功");
//			}
//			msg.setSuccess(true);
//		} catch (Exception e) {
//			msg.setMsg("插入失败,08111304");
//			msg.setSuccess(false);
//			dbLog.setAction_message("插入失败,08111304");
//			dbLog.setIs_ok(5);
//			e.printStackTrace();
//		}finally{
//			dbLogService.insertLog(dbLog);
//		}
//		return msg;
//	}

	
	/**
	 * 获取代理商的父节点分配比率模板
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/getOPDeptRatioModel")
	public Object getDeptRatioModel(HttpServletRequest request, 			
			@RequestParam HashMap<String,Object> paramsMap){
		List<Map<String, Object>> DeptRatioModelList = sysOptDeptInfoService.queryDeptParentInfo(paramsMap);
		return DeptRatioModelList;
	}
	
	/**
	 * 部门转部门操作
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/saveOOPDeptToNDeptInfo")
	public Object saveODeptToNDeptInfo(HttpServletRequest request, 			
			@RequestParam HashMap<String,Object> paramsMap){
		
		boolean retstr = (boolean) sysOptDeptInfoService.savaOdeptToNdept(paramsMap);
		if(retstr)
		{
			msg.setSuccess(true);
			msg.setMsg("转移成功!");
		}else
		{
			msg.setSuccess(false);
			msg.setMsg("转移失败,请确认数据问题!");
		}		
		return msg;
	}
	
	/**
	 * 会员退出操作
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/quitDeptInfo")
	public Object quitDeptInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,	@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		dbLog.setMethod_name("会员退出操作");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		JSONObject object=new JSONObject();
		JSONObject objectm =new JSONObject();
		List<HashMap<String, Object>> DeptslList =sysOptDeptInfoService.queryDeptsInfo(paramsMap);
		String strdeptid = DeptslList.get(0).get("dept_id").toString();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日
		String cnt = year + "-" + (month + 1) + "-" + day + " 00:00:00";
		
		paramsMap.put("depts", strdeptid);
		paramsMap.put("order_date", cnt);
		object = sysOptDeptInfoService.queryQuitDeptInfo(paramsMap);
		//boolean retstr = true;
		if(object.get("success").equals("true"))
		{
			objectm = sysOptDeptInfoService.clearDeptWeixinInfo(paramsMap);
			if(objectm.get("success").equals("true"))
			{ 
				System.out.print("mongodb修改成功!dept_id为:"+StringUtils.convertString(objectm.get("dept_id"))+",dept_type为:"+StringUtils.convertString(objectm.get("dept_type")));
							
			}else
			{
				System.out.print("mongodb修改失败!没找到该dept_id,dept_id为:"+StringUtils.convertString(objectm.get("dept_id"))+",dept_type为:"+StringUtils.convertString(objectm.get("dept_type")));
			}			
			dbLog.setAction_message("退出成功!dept_id为:"+StringUtils.convertString(object.get("dept_id")));
			dbLog.setIs_ok(0);	
			msg.setSuccess(true);
			msg.setMsg("退出成功!");
		}else
		{
			dbLog.setAction_message("退出失败!dept_id为:"+StringUtils.convertString(object.get("dept_id"))+"_信息为:"+StringUtils.convertString(object.get("message")));
			dbLog.setIs_ok(1);	
			msg.setSuccess(false);
			msg.setMsg("退出失败,请确认数据是否已经处理!");
		}
		dbLogService.insertLog(dbLog);
		return msg;
	}

	/**
	 * 清算操作
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/opSettlementInfo")
	public Object opSettlementInfo(@Session(create = false) SessionProvider session,HttpServletRequest request, 			
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
		try{

			dbLog.setMethod_name("会员清算操作");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			JSONObject object = new JSONObject();
			object = sysOptDeptInfoService.settlementInfo(paramsMap);

			if (object.get("success").equals("true")) {
				updUserInfo(paramsMap);
				updBrokerInfo(paramsMap);

				dbLog.setAction_message("清算成功!dept_id为:" + StringUtils.convertString(object.get("dept_id")));
				dbLog.setIs_ok(0);
				msg.setSuccess(true);
				msg.setMsg("清算成功!");

			} else {
				dbLog.setAction_message("清算失败,请确认数据是否正确!dept_id为:" + StringUtils.convertString(object.get("dept_id")));
				dbLog.setIs_ok(1);
				msg.setSuccess(false);
				msg.setMsg("清算失败,请确认数据是否正确!");
			}
			
		} 
		catch (Exception e) 
		{
			dbLog.setAction_message("清算异常！" + StringUtils.convertString(e.getMessage()));
			dbLog.setIs_ok(1);
			msg.setSuccess(false);
			msg.setMsg("清算异常,请确认!");
			
			//System.out.println(e.getMessage());

		} finally {
			System.out.println("结束");
			dbLogService.insertLog(dbLog);			
		}
		return msg;
	}

	
	/**
	 * 部门余额信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryDeptBalaceInfo") 
	public Object queryDeptBalaceInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		//paramsMap.put("dept_id", loginSession.getDept_id());	
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)sysOptDeptInfoService.queryDeptBalanceInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 部门余额统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryDeptBalaceInfoSum")
	public Object queryDeptBalaceInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		//查询会员信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = sysOptDeptInfoService.queryDeptBalanceCount(paramsMap);		
		return list;
	}
	
	/**
	 * 用户余额信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryUserBalaceInfo")
	public Object queryUserBalaceInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//paramsMap.put("dept_id", loginSession.getDept_id());	
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)sysOptDeptInfoService.queryDeptUserBalanceInfo(paramsMap, pageBounds);
		
		Object json=null;
		if(list.size()>0)
		{
			PageList<?> listMap=PhoneCodeSSL.getDataBaseListPage2(list, paramsMap);
			json = this.getJsonMap(listMap);
		}else
		{
			json = this.getJsonMap(list);
		}		
		//Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 用户余额统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryUserBalaceInfoSum")
	public Object queryUserBalaceInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		//查询会员信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = sysOptDeptInfoService.queryUserBalanceCount(paramsMap);		
		return list;
	}
	
	/**
	 * 经纪人余额信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryBrokerBalaceInfo")
	public Object queryBrokerBalaceInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows);
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		List<HashMap<String, Object>> DeptslList =sysOptDeptInfoService.queryDeptsInfo(paramsMap);
		String strdeptid = DeptslList.get(0).get("dept_id").toString();
		paramsMap.put("depts", strdeptid);
		//paramsMap.put("dept_id", loginSession.getDept_id());	
		//查询当天的代理商信息
		//获取登录的代理商新进行条件筛选，如果没有则为空。
		PageList<?> list = (PageList<?>)sysOptDeptInfoService.queryDeptBrokerBalanceInfo(paramsMap, pageBounds);		
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 经纪人余额统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/queryBrokerBalaceInfoSum")
	public Object queryBrokerBalaceInfoSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
		List<HashMap<String, Object>> DeptslList =sysOptDeptInfoService.queryDeptsInfo(paramsMap);
		String strdeptid = DeptslList.get(0).get("dept_id").toString();
		paramsMap.put("depts", strdeptid);
		//查询会员信息		
		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
		list = sysOptDeptInfoService.queryBrokerBalanceCount(paramsMap);		
		return list;
	}
	
	/**
	 * 清空微信信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/opWeiXinClearInfo")
	public Object opWeiXinClearInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		dbLog.setMethod_name("清空微信操作");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		JSONObject object=new JSONObject();		
		object = sysOptDeptInfoService.clearDeptWeixinInfo(paramsMap);
		if(object.get("success").equals("true"))
		{ 
			dbLog.setAction_message("清空微信成功!dept_id为:"+StringUtils.convertString(object.get("dept_id"))+",dept_type为:"+StringUtils.convertString(object.get("dept_type")));
			dbLog.setIs_ok(0);	
			msg.setSuccess(true);
			msg.setMsg("清空微信成功!");
			
		}else
		{
			dbLog.setAction_message("清空微信失败!没找到该订单号,dept_id为:"+StringUtils.convertString(object.get("dept_id"))+",dept_type为:"+StringUtils.convertString(object.get("dept_type")));
			dbLog.setIs_ok(1);
			msg.setSuccess(false);
			msg.setMsg("清空微信失败!");
		}
		dbLogService.insertLog(dbLog);
		return msg;
	}
	
	/**
	 * 查询经纪人余额导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/queryBrokerForExcel")
	public Object queryBrokerForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		List<HashMap<String, Object>> DeptslList =sysOptDeptInfoService.queryDeptsInfo(paramsMap);
		String strdeptid = DeptslList.get(0).get("dept_id").toString();
		paramsMap.put("depts", strdeptid);
		//查询信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryBrokerResultExport(paramsMap);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportBrokerInfo");
        return object;
	}
	
	/**
	 * 查询用户余额导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/opdeptInfo" })
	@RequestMapping("/queryUserForExcel")
	public Object queryUserForExcel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		
		//查询信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryUserResultExport(paramsMap);		
		JSONObject object=new JSONObject();
		if(list.size()>0){
			object.put("num", 1);
		}
		map=paramsMap;
		object.put("url", "exportUserInfo");
        return object;
	}
	
	/**
	 * 经纪人导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/exportBrokerInfo")
	public void exportBrokerInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		//查询经纪人信息
//		List<HashMap<String, Object>> list = new LinkedList<HashMap<String,Object>>();
//		HashMap<String, Object> has=null;
//		has.put("",map.get("dept_id").toString());
//		has.put("",map.get("type").toString());
//		List<HashMap<String, Object>> DeptslList =sysOptDeptInfoService.queryDeptsInfo(has);
//		String strdeptid = DeptslList.get(0).get("dept_id").toString();
//		map.put("depts", strdeptid);
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryBrokerResultExport(map);		
		if(list.size()>0){
		ExcelUtil.buildXSLXExcel(list, "经纪人余额", response);
		map.clear();
		}

	}
	
	/**
	 * 经纪人月报表导出
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/opdeptInfo" })
	@RequestMapping("/exportUserInfo")
	public void exportUserInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String, Object> paramsMap,HttpServletResponse response){
		//查询经纪人信息		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		List<Map<String, Object>> listafter = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryUserResultExport(map);		
		//手机号解密
		listafter = PhoneCodeSSL.getUserPhoneList(list, paramsMap);
		
		if(listafter.size()>0){
		ExcelUtil.buildXSLXExcel(listafter, "用户余额", response);
		map.clear();
		}

	}
	
	private void updUserInfo(HashMap<String,Object> paramsMap)
	{
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryUserResultExport(paramsMap);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				if (list.get(i).containsKey("用户电话")&&!"".equals(StringUtils.checkString(list.get(i).get("用户电话")))) {
					String mobile= list.get(i).get("用户电话").toString();
					String uid= list.get(i).get("用户ID").toString();
					paramsMap.put("user_id", uid);
					paramsMap.put("user_mobile", mobile);
					sysOptDeptInfoService.updUserMobileInfo(paramsMap);
				 }			
				
			}
		}
	}
	
	private void updBrokerInfo(HashMap<String,Object> paramsMap)
	{
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		list = sysOptDeptInfoService.queryBrokerResultExport(paramsMap);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				if (list.get(i).containsKey("经纪人电话")&&!"".equals(StringUtils.checkString(list.get(i).get("经纪人电话")))) {
					String mobile= list.get(i).get("经纪人电话").toString();
					String bid= list.get(i).get("broker_id").toString();
					if(mobile.indexOf("X")==-1)
					{
						mobile = mobile+"X";
					}
					
					paramsMap.put("broker_id", bid);
					paramsMap.put("broker_mobile", mobile);
					sysOptDeptInfoService.updBrokerMobileInfo(paramsMap);
				 }			
				
			}
		}
	}
	
}
