package com.acl.platform.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acl.platform.service.IPlatformExcelSerivce;
import com.acl.platform.service.IPlatformUserInfoService;
import com.acl.pojo.FrontSalaAgentInfo;
import com.acl.pojo.SysFile;
import com.acl.pojo.UserInfo;
import com.acl.sys.service.*;
import com.acl.utils.excel.*;
import com.acl.utils.util.*;
import com.alibaba.fastjson.JSONArray;
import com.anchol.common.util.file.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformUserAgentRechargeService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *className:PlatformUserAgentRechargeController
 *author:wangli
 *createDate:2017年8月9日 上午9:23:32
 *version:3.0
 *department:安创乐科技
 *description:
 */

@Controller
@RequestMapping("/platform")
public class PlatformAgentUserController extends CoreBaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformAgentUserController.class);

	
	@Autowired
	private IPlatformUserAgentRechargeService platformUserAgentRechargeService;
	
	@Autowired
	private ISysIndexService sysIndexService;
	
	@Autowired
	private ISysWithDrawService sysWithDrawService;


	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/agentUserInfo")
	public String agentUserInfo(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
		return "platform/platform_user_agent";
	}


	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/queryUserAgentInfo")
	public Object queryUserAgentRecharge(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		String sortString = "";

		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询提现审核信息
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		paramsMap.put("dept_id", loginSession.getDept_id());
		String brand_id = paramsMap.get("brand_id")!=null?paramsMap.get("brand_id").toString():null;
		if (brand_id!=null){
			HashMap<String,Object> m = new HashMap<String,Object>();
			m.put("id",brand_id);
			List<Map<String, Object>> ma= sysSalaRuleService.queryDictBrandRule(m);
			if (ma!=null && ma.size()>0){
				paramsMap.put("brand",ma.get(0).get("name"));
			}
		}

		/*if(!"".equals(StringUtils.checkString(paramsMap.get("b_mobile"))))
		{
			paramsMap.put("mobile",MySecurity.encryptAES(paramsMap.get("b_mobile").toString(), SystemConfig.keyMy));
		}	*/
		//PageList<?> list = (PageList<?>)platformUserAgentRechargeService.queryUserAgentRecharge(paramsMap, pageBounds);
		PageList<?> list = (PageList<?>) platformUserInfoService.queryUserAgentInfo(paramsMap, pageBounds);
		//手机号数据库解密
		PageList<?> listMap = PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
   	     Object json = this.getJsonMap(listMap);
		 return json;
	}
	

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userAgentRecharge" })
	@RequestMapping(value = "/agreeUserAgentRecharge", method = RequestMethod.POST)
	public Object agreeUserAgentRecharge(HttpServletRequest request,
		HttpServletResponse response, @RequestParam HashMap<String, Object> paramsMap,@Session(create = false) SessionProvider session)  {
		
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		
		if (null != session) {
		dbLog.setMethod_name("用户代理商代付审核");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		}
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			
		
		int dept_type = StringUtils.checkInt(loginSession.getDept_type());
	
		if (dept_type < 2 || dept_type > 4) {
			msg.setMsg("审核失败,您暂时没有审批权限");
			msg.setSuccess(false);
			return msg;
		}
		HashMap<String, Object> deptMap = new HashMap<>();
		deptMap.put("loginName", StringUtils.checkString(loginSession.getUser_name()));//当前登录结算会员名称
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(deptMap);
		BigDecimal deptMoney = StringUtils.checkBigDecimal(userList.get(0).get("dept_money"));
		//比例值
		BigDecimal propvalue = StringUtils.checkBigDecimal(userList.get(0).get("prop_values"));
		
		BigDecimal earnestMoney = new BigDecimal(0);
		BigDecimal withDrawMoney = new BigDecimal(0);
		BigDecimal order_money = StringUtils.checkBigDecimal(paramsMap.get("order_money"));//用户代充金额
		
		//提现的手续费  公式：提现金额*比例=手续费
		//BigDecimal counter_fee_money = StringUtils.checkBigDecimal(paramsMap.get("tx_money")).multiply(propvalue);
		//实际提现金额  公式  提现金额-手续费=到账金额
		//BigDecimal real_tx_money = StringUtils.checkBigDecimal(paramsMap.get("tx_money"));
		//tx_money = multiply*provalue;
		
		//服务商  判断金额是否充足
		if (3==StringUtils.checkInt(loginSession.getDept_type())) {
			//单位下所有用户资金
			BigDecimal totalMoney = StringUtils.checkBigDecimal(sysWithDrawService.sumUserWalletByDept(loginSession.getDept_id()));
			earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
			if (0>earnestMoney.compareTo(new BigDecimal(30000))){
				earnestMoney = new BigDecimal(30000);
			}
//			首先  查询结算的保证金  ,保证金 = 结算会员下面所有用户资金和x20%    ,
//					如果  这个计算记过小于10000,那么保证金就取10000,
//					如果这个计算结果大于10000,那么保证金就取计算的结果,
//					保证金最低要10000,  
//					可提现金额 = 当前结算服务商的余额-保证金
			withDrawMoney = deptMoney.subtract(earnestMoney);
			if (0<order_money.compareTo(withDrawMoney)) {
				msg.setMsg("审核失败,审核额度不得大于您的可用余额");
				msg.setSuccess(false);
				return msg;
			}
		}
		
		//扣减服务商余额 t_back_dept_info  写入服务商流水t_back_settle_detailed
		
		//增加服务商余额  及流水 同步到redis中
		paramsMap.put("user_id", paramsMap.get("user_id"));
		paramsMap.put("settle_id", loginSession.getUser_id());//当前登陆的服务商用户id
		paramsMap.put("user_name", loginSession.getUser_nick_name());
		paramsMap.put("dept_id", loginSession.getDept_id());
		paramsMap.put("dept_name", loginSession.getDept_name());
		paramsMap.put("dept_type", loginSession.getDept_type());
		paramsMap.put("dept_money", deptMoney);//服务商当前余额
		//paramsMap.put("company_id", UUIDGenerator.generate());
		paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		paramsMap.put("is_status", "2");
		BigDecimal new_money = StringUtils.checkBigDecimal(deptMoney).subtract(order_money);
		paramsMap.put("new_money", new_money);//扣除后的服务商余额
		paramsMap.put("dept_counter_fee",0);//手续费 暂时为0
		paramsMap.put("real_tx_money", order_money);//实际到账金额 暂时为用户充值金额
		//写入流水  更改金额
		platformUserAgentRechargeService.updateMoney(paramsMap);
		//	更改订单状态
		HashMap<String, Object> statusMap=new HashMap<>();
		statusMap.put("recharge_status", "0");
		statusMap.put("reason", "通过");
		statusMap.put("update_date",FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		statusMap.put("order_id", paramsMap.get("order_id"));
		platformUserAgentRechargeService.updateStatus(statusMap);
			msg.setMsg("审核成功");
			msg.setSuccess(true);
			return msg;
		} catch (Exception e) {
			msg.setMsg("审核失败");
			msg.setSuccess(false);
			e.printStackTrace();
			return msg;
		}finally {
			dbLogService.insertLog(dbLog);
		}
	}
	
	
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userAgentRecharge" })
	@RequestMapping(value = "/disAgreeUserAgentRecharge", method = RequestMethod.POST)
	public Object disAgreeUserAgentRecharge(HttpServletRequest request,
		HttpServletResponse response, @RequestParam HashMap<String, Object> paramsMap,@Session(create = false) SessionProvider session)  {
		
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");

		
		if (null != session) {
		dbLog.setMethod_name("用户代理商代付审核");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		}
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			
		
		int dept_type = StringUtils.checkInt(loginSession.getDept_type());
	
		if (dept_type < 2 || dept_type > 4) {
			msg.setMsg("审核失败,您暂时没有审批权限");
			msg.setSuccess(false);
			return msg;
		}
		paramsMap.put("recharge_status", "2");
		paramsMap.put("reason", "不通过");	
		paramsMap.put("update_date",FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		platformUserAgentRechargeService.updateStatus(paramsMap);
			msg.setMsg("审核成功");
			msg.setSuccess(true);
			return msg;
		} catch (Exception e) {
			msg.setMsg("审核失败");
			msg.setSuccess(false);
			e.printStackTrace();
			return msg;
		}finally {
			dbLogService.insertLog(dbLog);
		}
	}
	
	
	

	/**
	 * 结算会员当前可提现余额统计
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/platform/userAgentRecharge" })
	@RequestMapping("/queryTxSum")
	public Object queryTxSum(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam Map<String,Object> paramsMap,HttpServletResponse response){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
		
		HashMap<String, Object> deptMap = new HashMap<>();
		deptMap.put("loginName", StringUtils.checkString(loginSession.getUser_name()));//当前登录结算会员名称
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(deptMap);
		BigDecimal deptMoney = StringUtils.checkBigDecimal(userList.get(0).get("dept_money"));
		
		BigDecimal earnestMoney = new BigDecimal(0);
		BigDecimal withDrawMoney = new BigDecimal(0);
		
		//服务商  判断金额是否充足
		if (3==StringUtils.checkInt(loginSession.getDept_type())) {
			//单位下所有用户资金
			BigDecimal totalMoney = StringUtils.checkBigDecimal(sysWithDrawService.sumUserWalletByDept(loginSession.getDept_id()));
			earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
			if (0>earnestMoney.compareTo(new BigDecimal(30000))){
				earnestMoney = new BigDecimal(30000);
			}
//			首先  查询结算的保证金  ,保证金 = 结算会员下面所有用户资金和x20%    ,
//					如果  这个计算记过小于10000,那么保证金就取10000,
//					如果这个计算结果大于10000,那么保证金就取计算的结果,
//					保证金最低要10000,  
//					可提现金额 = 当前结算服务商的余额-保证金
			withDrawMoney = deptMoney.subtract(earnestMoney);
		}
		
		JSONObject object=new JSONObject();
		object.put("withDrawMoney", withDrawMoney);
		paramsMap.put("dept_id", loginSession.getDept_id());
		List<Map<String,Object>> list=platformUserAgentRechargeService.queryRechargeSum(paramsMap);
		
			object.put("sumMoney", list.get(0).get("sumMoney"));
		
		
		
		//统计充值成功的总额
		
		
		return object;

	}

	@Autowired
	private ISysSalaRuleService sysSalaRuleService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
	@RequestMapping("/agentSalaRule")
	public String agentSalaRule(){
		return "platform/platform_agent_sala_rule";
	}

	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
	@RequestMapping("/queryAgentSalaRule")
	public Object queryAgentSalaRule(@Session(create = false) SessionProvider session, HttpServletRequest request,
									 @RequestParam(defaultValue = "0", required = false) Integer page,
									 @RequestParam(defaultValue = "10", required = false) Integer rows,
									 @RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows, Order.formString(""));
		//查询提现充值规则
		paramsMap.put("sala_type","02");
		PageList<?> list = (PageList<?>)sysSalaRuleService.querySalaRule(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}



	@Autowired
	private IPlatformUserInfoService platformUserInfoService;
	/**
	 * 查询前台用户信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/queryAgentUserInfo")
	public Object queryAgentUserInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
								 @RequestParam(defaultValue = "0", required = false) Integer page,
								 @RequestParam(defaultValue = "10", required = false) Integer rows,
								 @RequestParam HashMap<String, Object> paramsMap) {

		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		//判断传过来的参数是否是带有解密
		String decryption =StringUtils.checkString( paramsMap.get("decryption"));
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
			case "6"://经纪人
				paramsMap.put("DID", loginSession.getDept_id());
				break;
		}
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString(""));

		if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("mobile").toString());
		}

		if(!"".equals(StringUtils.checkString(paramsMap.get("id_card"))))
		{
			paramsMap.put("id_card",paramsMap.get("id_card").toString());
		}
		//查询所有启用的用户
		paramsMap.put("status",1);
        paramsMap.put("account_type","02");
		PageList<?> list = (PageList<?>) platformUserInfoService.queryUserAgentInfo(paramsMap, pageBounds);
		//手机号数据库解密
		PageList<?> listMap =null;
		if(decryption.equals("decryption")){
			//带有解密参数就不加****
			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
		}else{
			//listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
			listMap=PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
		}
		Object json = this.getJsonMap(listMap);
		return json;
	}




	/**
	 * 修改提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
	@RequestMapping("/insSalaRule")
	public Object insSalaRule(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			//更新提现充值规则
			sysSalaRuleService.insSalaRule(paramsMap);
			msg.setMsg("新增成功");
			msg.setSuccess(true);

		}catch(Exception e){
			msg.setMsg("新增失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}



	/**
	 * 修改提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
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
	private SysUserInfoService sysUserInfoService;

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/uploadfile")
	public Object uploadfile(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		String salaRuleId = (String)request.getParameter("salaRuleId");
		SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");
		try {
			//获得临时文件
			String path = systemConfig.getTempPath();
			//创建临时目录
			File fileDir = new File(path);
			if(!fileDir.exists() && !fileDir.isDirectory()) {
				fileDir.mkdir();
			}

			for (int i = 0; i < file.length; i++) {
				MultipartFile multipartFiles = file[i];
				//SysFile sysFile = sysFileService.fileUpload(file[i], "image");

				String fileName = UUIDGenerator.generate() + multipartFiles.getOriginalFilename();
				String filePath = path + System.getProperty("file.separator") + fileName;
				File destTemp = new File(filePath);
				InputStream is = null;
				FileUtil.writeFile(file[i].getInputStream(), destTemp);
				is = new FileInputStream(destTemp);

				ParsingAgentExcelUtil ParsingAgentExcelUtil = new ParsingAgentExcelUtil();
				List<BasicInfo> basicInfoList = ParsingAgentExcelUtil.getParsingExcelResult(destTemp);
				if(CollectionUtil.isNotEmpty(basicInfoList)){
					StringBuffer sb = new StringBuffer();
					for (int j=0;j<basicInfoList.size();j++) {
						BasicInfo basicInfo = basicInfoList.get(j);
						if (!(org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getMobile())
								&& basicInfo.getMobile().length() == 11
								&& org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getIdCard())
								&& basicInfo.getIdCard().length() == 18)) {
							LOGGER.info("数据不完整:" + j + " : " + basicInfo.toString());
							sb.append(basicInfo.getMobile()+",");
							continue;
						}
					}
					if(org.apache.commons.lang3.StringUtils.isNotEmpty(sb.toString())){
						result.put("state", sb.toString());
						return result;
					}
				}
				List<UserInfo> userInfoList = sysUserInfoService.getUserInfoList();
				Map<String,UserInfo> map = new HashMap<>();
				if(CollectionUtil.isNotEmpty(userInfoList)){
					userInfoList.parallelStream().forEach(userInfo -> {
						map.put(userInfo.getMobile(),userInfo);
					});
				}
				//List<Integer> l = new ArrayList<>();
				if(CollectionUtil.isNotEmpty(basicInfoList)){
					for (int j=0;j<basicInfoList.size();j++){
						BasicInfo basicInfo =  basicInfoList.get(j);
						if(!(org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getMobile())
								&& basicInfo.getMobile().length() == 11
								&& org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getIdCard())
								&& basicInfo.getIdCard().length() ==18)){
							LOGGER.info("数据不完整:"+j+" : "+basicInfo.toString());
							continue;
						}
						if(org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getMobile())
								&& basicInfo.getMobile().length() == 11
								&& org.apache.commons.lang3.StringUtils.isNotEmpty(basicInfo.getIdCard())
								&& basicInfo.getIdCard().length() ==18 ){
							LOGGER.info("手机号码:"+basicInfo.getMobile()+" ,数据为："+basicInfo.toString());
							if(!map.containsKey(basicInfo.getMobile()) ){
								UserInfo userInfo = new UserInfo();
								userInfo.setId(UUIDGenerator.generate());
								userInfo.setAccountLevel(1);
								//账号类型（01普通,02代理商）
								userInfo.setAccountType("02");
								userInfo.setCreateTime(new Date());
								userInfo.setMobile(basicInfo.getMobile());
								userInfo.setIdCard(basicInfo.getIdCard());
								userInfo.setName(org.apache.commons.lang3.StringUtils.defaultString(basicInfo.getName(),basicInfo.getAgentName()));
								userInfo.setStatus(1);
								userInfo.setUpPassword("02");
								userInfo.setPassword(MD5Utils.sign(org.apache.commons.lang3.StringUtils.substring(basicInfo.getIdCard(),basicInfo.getIdCard().length()-6,basicInfo.getIdCard().length()), MD5Utils.PWD_KEY, MD5Utils.DEFAULT_UTF_8_INPUT_CHARSET));
								FrontSalaAgentInfo frontSalaAgentInfo = getFrontSalaAgentInfoByExcel(basicInfo);
								frontSalaAgentInfo.setSalaRuleId(salaRuleId);
								sysUserInfoService.saveUserInfoAndFrontSalaAgentInfo(userInfo,frontSalaAgentInfo);
								map.put(userInfo.getMobile(),userInfo);
								LOGGER.info("数据入库:"+j+" : "+basicInfo.toString());
								//l.add(j);
							}else{
								UserInfo userInfo = map.get(basicInfo.getMobile());
								UserInfo updateUserInfo = new UserInfo();
								//账号类型（01普通,02代理商）
								updateUserInfo.setAccountType("02");
								updateUserInfo.setId(userInfo.getId());
								updateUserInfo.setIdCard(basicInfo.getIdCard());
								updateUserInfo.setUpdateTime(new Date());
								updateUserInfo.setStatus(1);
								updateUserInfo.setName(org.apache.commons.lang3.StringUtils.defaultString(basicInfo.getName(),basicInfo.getAgentName()));
								FrontSalaAgentInfo frontSalaAgentInfo = getFrontSalaAgentInfoByExcel(basicInfo);
								frontSalaAgentInfo.setSalaRuleId(salaRuleId);
								sysUserInfoService.updateUserInfoAndFrontSalaAgentInfo(updateUserInfo,frontSalaAgentInfo);
								LOGGER.info("数据入库:"+j+" : "+basicInfo.toString());
								//l.add(j);
							}
						}
					}
				}
				//SysFile sysFilePrefix = sysFileService.fileUpload(file[i].getOriginalFilename(), "image", is, filePath);
				SysFile sysFilePrefix = new SysFile();
				sysFilePrefix.setFileName(fileName);
				sysFilePrefix.setFileType("xlsx");
				sysFilePrefix.setFilePath(path);
				sysFilePrefix.setFileSize(multipartFiles.getSize());
				String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
				String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
				result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
				result.put("original", sysFilePrefix.getFileName());
				result.put("size", sysFilePrefix.getFileSize());
				result.put("type", sysFilePrefix.getFileType());
				result.put("state", "SUCCESS");
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("state", "上传失败");
		}
		return result;
	}

	/**
	 * 解析excel里每一行数据
	 * @param basicInfo
	 * @return
	 */
	private FrontSalaAgentInfo getFrontSalaAgentInfoByExcel(BasicInfo basicInfo) {
		FrontSalaAgentInfo frontSalaAgentInfo = new FrontSalaAgentInfo();
		frontSalaAgentInfo.setId(UUIDGenerator.generate());
		frontSalaAgentInfo.setCreateTime(new Date());
		frontSalaAgentInfo.setJob(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getJob(),""));
		frontSalaAgentInfo.setWordDays(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getWordDays(),""));
		frontSalaAgentInfo.setLeaveDays(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getLeaveDays(),""));
		frontSalaAgentInfo.setTotalAmount(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getTotalAmount(),""));
		frontSalaAgentInfo.setRealAmount(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getRealAmount(),""));
		frontSalaAgentInfo.setDeductAmount(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),""));
		frontSalaAgentInfo.setNotes(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getNotes(),""));
		frontSalaAgentInfo.setMonthCheck(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getMonthCheck(),""));
		frontSalaAgentInfo.setMonthUncheck(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getMonthUnCheck(),""));
		frontSalaAgentInfo.setDeductAmount(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getDeductAmount(),""));
		frontSalaAgentInfo.setChName(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getChName(),""));
		frontSalaAgentInfo.setAgentName(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getAgentName(),""));
		frontSalaAgentInfo.setBackName(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getBackName(),""));
		frontSalaAgentInfo.setBrand(org.apache.commons.lang3.StringUtils.defaultIfEmpty(basicInfo.getBrand(),""));
		Map<String,ParsingAgentExcelUtil.MergedRowTitle> totalMap = basicInfo.getTotalMap();
		if(totalMap!=null){
            List<ParsingAgentExcelUtil.MergedRowTitle> totalMergedRowTitles = new ArrayList<>();
            for(String s:totalMap.keySet()){
                ParsingAgentExcelUtil.MergedRowTitle mergedRowTitle = totalMap.get(s);
                //System.out.println("合并的 key : "+s+" value : "+mergedRowTitle);
                totalMergedRowTitles.add(mergedRowTitle);
            }
            frontSalaAgentInfo.setTotalMap(JSONArray.toJSONString(totalMergedRowTitles));
        }
		Map<String,ParsingAgentExcelUtil.MergedRowTitle> deductMap = basicInfo.getDeductMap();
		if(deductMap!=null){
            List<ParsingAgentExcelUtil.MergedRowTitle> deductMergedRowTitles = new ArrayList<>();
            for(String s:deductMap.keySet()){
                ParsingAgentExcelUtil.MergedRowTitle mergedRowTitle = deductMap.get(s);
                //System.out.println("合并的 key : "+s+" value : "+mergedRowTitle);
                deductMergedRowTitles.add(mergedRowTitle);
            }
            frontSalaAgentInfo.setDeductMap(JSONArray.toJSONString(deductMergedRowTitles));
        }
		return frontSalaAgentInfo;
	}


	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
//	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/queryDictSalaRule")
	public Object queryDictSalaRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
								@RequestParam(defaultValue = "0", required = false) Integer page,
								@RequestParam(defaultValue = "10", required = false) Integer rows,
								@RequestParam HashMap<String,Object> paramsMap){
		paramsMap.put("sala_type","02");
		paramsMap.put("sala_status","01");
		List<Map<String,Object>> list = sysSalaRuleService.queryDictSalaRule(paramsMap);
		Map<String,Object> map  = new HashMap<>() ;
		map.put("sala_title","全部");
		map.put("id","");
		list.add(0,map);
		return list;
	}

	/**
	 * 查询提现品牌
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
//	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/queryBrandList")
	public Object queryBrandList(@Session(create = false) SessionProvider session,HttpServletRequest request,
									@RequestParam(defaultValue = "0", required = false) Integer page,
									@RequestParam(defaultValue = "10", required = false) Integer rows,
									@RequestParam HashMap<String,Object> paramsMap){
		List<Map<String,Object>> list = sysSalaRuleService.queryBrandList(paramsMap);
		Map<String,Object> map  = new HashMap<>() ;
		map.put("id","全部");
		map.put("textname","全部");
		list.add(0,map);
		return list;
	}

	/**
	 *
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/allAgentDisAgree")
	public Object allAgentDisAgree(@Session(create = false) SessionProvider session, HttpServletRequest request,
							  @RequestParam HashMap<String, Object> paramsMap) {
		try {
			String [] orders_id=paramsMap.get("order_ids").toString().split(",");
			for (int i=0;i<orders_id.length;i++){
				paramsMap.put("id",orders_id[i]);
				platformUserInfoService.deleteSalaAgentAll(paramsMap);
			}
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
	 * 重置密码
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserInfo" })
	@RequestMapping("/closeAgentAll")
	public Object closeAgentAll(@Session(create = false) SessionProvider session, HttpServletRequest request,
						   @RequestParam HashMap<String, Object> paramsMap) {
		try {
			// 重置密码
			platformUserInfoService.deleteSalaAgentAll(paramsMap);
			paramsMap.put("account_type","02");
			platformUserInfoService.updateCloseAll(paramsMap);
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
	 * 重置密码
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
	@RequestMapping("/deleteSalaRule")
	public Object deleteSalaRule(@Session(create = false) SessionProvider session, HttpServletRequest request,
								@RequestParam HashMap<String, Object> paramsMap) {
		try {
			platformUserInfoService.deleteSalaAgentAll(paramsMap);
			msg.setMsg("操作成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("操作失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}


	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequestMapping("/agentProrateBrand")
	public String agentProrateBrand(@Session(create = false) SessionProvider session,ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		return "platform/platform_agent_prorate_brand";
	}

	@Autowired
	private ISysDeptProrateTempService sysDeptProrateTempService;
	/**
	 * 查询分配比率信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/userInfo" })
	@RequestMapping("/queryDeptProrateBrand")
	public Object queryDeptProrateBrand(@Session(create = false) SessionProvider session, HttpServletRequest request,
										@RequestParam(defaultValue = "0", required = false) Integer page,
										@RequestParam(defaultValue = "10", required = false) Integer rows,
										@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type = loginSession.getDept_type();
		String dept_id = loginSession.getDept_id();
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
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		// 通过登陆用户所属dept_id 查询 该dept树下所有子节点dept_id
		PageList<?> list = (PageList<?>) sysDeptProrateTempService.queryAgentProrateBrand(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}



	/**
	 * 查询提现充值规则
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentSalaRule" })
	@RequestMapping("/queryDictBrandRule")
	public Object queryDictBrandRule(@Session(create = false) SessionProvider session,HttpServletRequest request,
									@RequestParam(defaultValue = "0", required = false) Integer page,
									@RequestParam(defaultValue = "10", required = false) Integer rows,
									@RequestParam HashMap<String,Object> paramsMap){
		paramsMap.put("status","01");
		List<Map<String,Object>> list = sysSalaRuleService.queryDictBrandRule(paramsMap);
		Map<String,Object> map  = new HashMap<>() ;
		map.put("name","全部");
		map.put("id","");
		list.add(0,map);
		return list;
	}


	@Autowired
	private IPlatformExcelSerivce platformExcelSerivce;


	@ResponseBody
	@RequiresAuthentication
	@RequestMapping(value = "/brandUploadfile")
	public Object brandUploadfile(@RequestParam("upfile") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		String brandId = (String)request.getParameter("brandId");
		String brandType = (String)request.getParameter("brandType");
		SystemConfig systemConfig = (SystemConfig) applicationContext.getBean("systemConfig");
		try {
			//获得临时文件
			String path = systemConfig.getTempPath();
			//创建临时目录
			File fileDir = new File(path);
			if(!fileDir.exists() && !fileDir.isDirectory()) {
				fileDir.mkdir();
			}

			for (int i = 0; i < file.length; i++) {
				MultipartFile multipartFiles = file[i];
				//SysFile sysFile = sysFileService.fileUpload(file[i], "image");
				String fileName = UUIDGenerator.generate() + multipartFiles.getOriginalFilename();
				String filePath = path + System.getProperty("file.separator") + fileName;
				File destTemp = new File(filePath);
				InputStream is = null;
				FileUtil.writeFile(file[i].getInputStream(), destTemp);
				is = new FileInputStream(destTemp);
				//乐刷刷宝支付
				if ("6".equals(brandId)) {
					if("1".equals(brandType)){
						platformExcelSerivce.shuaBaoMerchant(brandId, destTemp);
					} else if("2".equals(brandType)){
						platformExcelSerivce.shuaBaoTradeOrder(brandId, destTemp);
					}
					else if("3".equals(brandType)){
						platformExcelSerivce.shuaBaoMachine(brandId, destTemp);
					}
				}
				//海科码盒
				if ("10".equals(brandId)) {
					if("1".equals(brandType)){
						platformExcelSerivce.HKMerchant(brandId, destTemp);
					} else if("2".equals(brandType)){
						platformExcelSerivce.haiKeTradeOrder(brandId, destTemp);
					}
					else if("3".equals(brandType)){
						platformExcelSerivce.haikeMachine(brandId, destTemp);
					}
					else if("4".equals(brandType)){
						platformExcelSerivce.haikeStaffImport(brandId, destTemp);
					}
				}

				//SysFile sysFilePrefix = sysFileService.fileUpload(file[i].getOriginalFilename(), "image", is, filePath);
				SysFile sysFilePrefix = new SysFile();
				sysFilePrefix.setFileName(fileName);
				sysFilePrefix.setFileType("xlsx");
				sysFilePrefix.setFilePath(path);
				sysFilePrefix.setFileSize(multipartFiles.getSize());
				String url = systemConfig.getFileServBaseUrl() + sysFilePrefix.getFilePath();
				String u = Base64.encodeBase64String(url.getBytes("UTF-8"));
				result.put("url", SystemConfig.localUrl + "/upload/getImage/" + u);
				result.put("original", sysFilePrefix.getFileName());
				result.put("size", sysFilePrefix.getFileSize());
				result.put("type", sysFilePrefix.getFileType());
				result.put("state", "SUCCESS");
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("state", "上传失败");
		}
		return result;
	}





	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserTransf" })
	@RequestMapping("/agentUserTransf")
	public String agentUserTransf(@Session(create = false) SessionProvider session,ModelMap modelMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptName", loginSession.getDept_name());
		modelMap.addAttribute("roleName", loginSession.getRole_name());
		modelMap.addAttribute("dept_type",loginSession.getDept_type());
		modelMap.addAttribute("agent_id",loginSession.getAgent_id());
		return "platform/platform_agent_transf";
	}


	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/agentUserTransf" })
	@RequestMapping("/queryUserTransf")
	public Object queryUserTransf(@Session(create = false) SessionProvider session,HttpServletRequest request,
										 @RequestParam(defaultValue = "0", required = false) Integer page,
										 @RequestParam(defaultValue = "10", required = false) Integer rows,
										 @RequestParam HashMap<String,Object> paramsMap){
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询提现审核信息
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("dept_id", loginSession.getDept_id());
		paramsMap.put("account_type","02");
		/*if(!"".equals(StringUtils.checkString(paramsMap.get("b_mobile"))))
		{
			paramsMap.put("mobile",MySecurity.encryptAES(paramsMap.get("b_mobile").toString(), SystemConfig.keyMy));
		}	*/
		//PageList<?> list = (PageList<?>)platformUserAgentRechargeService.queryUserAgentRecharge(paramsMap, pageBounds);
		PageList<?> list = (PageList<?>) platformUserInfoService.queryUserTransf(paramsMap, pageBounds);
		//手机号数据库解密
		PageList<?> listMap = PhoneCodeSSL.getDataBaseListPage1(list, paramsMap);
		Object json = this.getJsonMap(listMap);
		return json;
	}



	/**
	 * 经纪人更换部门
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/platform/agentUserTransf"})
	@RequestMapping("/updateUserTransf")
	public Object updateUserTransf(@Session(create = false) SessionProvider session,
										 @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		try {
			dbLog.setMethod_name("设置品牌代理商");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			//设置经纪人到不同的部门
			platformUserInfoService.insertUserTransf(paramsMap);

			dbLog.setAction_message("成功," + StringUtils.convertString(paramsMap.get("brokers_names")) + "更换部门为：" + StringUtils.convertString(paramsMap.get("dept_name")));
			dbLog.setIs_ok(0);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		} catch (Exception e) {
			dbLog.setAction_message("失败");
			dbLog.setIs_ok(1);
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		} finally {
			dbLogService.insertLog(dbLog);
		}
		return msg;
	}


	@Autowired
	private SysDeptInfoService sysDeptInfoService;
	/**
	 * 查询部门信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryDeptInfoByType")
	public Object queryDeptInfoByType(@Session(create = false) SessionProvider session, HttpServletRequest request,
									@RequestParam HashMap<String, Object> paramsMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		HashMap<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("dept_id", loginSession.getDept_id());
		//sysDeptInfoService.queryDeptChildNodeInfo(parmas);
		if (paramsMap.isEmpty()) {
			paramsMap.put("dept_id", loginSession.getDept_id());
		}
		//paramsMap.put("search", loginSession.getDept_id());

		List<Map<String, Object>> list = sysDeptInfoService.queryDeptInfoByType(paramsMap);
		Object json = JSONObject.toJSON(list);
		return json;
	}




	/**
	 * 部门转部门操作
	 *
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/savaAgentToNdept")
	public Object savaAgentToNdept(HttpServletRequest request,
									   @RequestParam HashMap<String, Object> paramsMap) {
		boolean retstr = (boolean) sysDeptInfoService.savaAgentToNdept(paramsMap);
		if (retstr) {
			msg.setSuccess(true);
			msg.setMsg("转移成功!");
		} else {
			msg.setSuccess(false);
			msg.setMsg("转移失败,请确认数据问题!");
		}
		return msg;
	}

}
