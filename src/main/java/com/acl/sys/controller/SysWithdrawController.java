package com.acl.sys.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
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
import com.acl.sys.service.ISysIndexService;
import com.acl.sys.service.ISysWithDrawService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * className:SysIndex author:huangs createDate:2016年9月23日 下午2:50:30 vsersion:3.0
 * department:安创乐科技 description:提现管理
 */
@Controller
@RequestMapping("/sys")
public class SysWithdrawController extends CoreBaseController {

	@Autowired
	private ISysWithDrawService sysWithDrawService;
	@Autowired
	private ISysIndexService sysIndexService;

	/**
	 * 申请
	 * @return
	 */
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/withDraw" })
	@RequestMapping("/withDraw")
	public String withDraw(@Session(create = false) SessionProvider session, ModelMap modelMap) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		HashMap<String, Object> deptMap = new HashMap<>();
		deptMap.put("loginName", StringUtils.checkString(loginSession.getUser_name()));
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(deptMap);
		BigDecimal deptMoney = StringUtils.checkBigDecimal(userList.get(0).get("dept_money"));
		BigDecimal earnestMoney = new BigDecimal(0);
		BigDecimal withDrawMoney = new BigDecimal(0);
		//服务商
		if (3==StringUtils.checkInt(loginSession.getDept_type())) {
			//单位下所有用户资金
			BigDecimal totalMoney = StringUtils.checkBigDecimal(sysWithDrawService.sumUserWalletByDept(loginSession.getDept_id()));
			earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
			if (0>earnestMoney.compareTo(new BigDecimal(30000))){
				earnestMoney = new BigDecimal(30000);
			}
//			首先  查询结算的保证金  ,保证金 = 结算会员下面所有用户资金和x20%    ,
//					如果  这个计算记过小于30000,那么保证金就取30000,
//					如果这个计算结果大于30000,那么保证金就取计算的结果,
//					保证金最低要30000,  
//					可提现金额 = 当前结算服务商的余额-保证金
			withDrawMoney = deptMoney.subtract(earnestMoney);
		}else {
			//代理商 渠道
			withDrawMoney = deptMoney;
		}
			
			
		/*	if(4==StringUtils.checkInt(loginSession.getDept_type())){
			//单位下所有用户资金
			BigDecimal totalMoney = StringUtils.checkBigDecimal(sysWithDrawService.sumUserWalletByDept(loginSession.getDept_id()));
			earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
			if (0>earnestMoney.compareTo(new BigDecimal(10000))){
				earnestMoney = new BigDecimal(10000);
			}
//			首先  查询结算的保证金  ,保证金 = 结算会员下面所有用户资金和x20%    ,
//					如果  这个计算记过小于10000,那么保证金就取10000,
//					如果这个计算结果大于10000,那么保证金就取计算的结果,
//					保证金最低要10000,  
//					可提现金额 = 当前结算服务商的余额-保证金
			withDrawMoney = deptMoney.subtract(earnestMoney);
		
		}*/
		modelMap.addAttribute("loginName", loginSession.getUser_nick_name());
		modelMap.addAttribute("deptType", loginSession.getDept_type());
		modelMap.addAttribute("deptMoney", deptMoney);
		modelMap.addAttribute("earnestMoney", earnestMoney);
		modelMap.addAttribute("withDrawMoney", withDrawMoney);
		return "sys/sys_with_draw";
	}
	
	/**
	 * 页面数据
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/withDraw" })
	@RequestMapping("/queryWithDrawInfo")
	public Object queryWithDrawInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "20", required = false) Integer rows,
			@RequestParam HashMap<String, Object> paramsMap)  {
//		如果查询开始时间存在  则判断结束日期是否也存在  如不存在则自动取今天为结束日期（开始日期为必须时间）
		if (!"".equals(StringUtils.checkString(paramsMap.get("begin_date")))) {
			paramsMap.put("begin_date", paramsMap.get("begin_date").toString()+" 00:00:00");
			if ("".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
				paramsMap.put("end_date", DateFormatUtil.convertCurrentDate("yyyy-MM-dd 23:59:59"));
			}
		}
		if (!"".equals(StringUtils.checkString(paramsMap.get("end_date")))) {
			paramsMap.put("end_date", paramsMap.get("end_date").toString()+" 23:59:59");
		}
		PageBounds pageBounds = new PageBounds(page, rows, Order.formString("is_status,create_date"));
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		paramsMap.put("user_id", loginSession.getUser_id());
		PageList<?> list = (PageList<?>) sysWithDrawService.queryWithDrawInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}
	
	/**
	 * 提交新的提现申请
	 * @param session
	 * @param paramsMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/withDraw" })
	@RequestMapping("/insWithDrawInfo")
	public Object insertWithDrawInfo(@Session(create = false) SessionProvider session, @RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		int dept_type = StringUtils.checkInt(loginSession.getDept_type());
//		if("1".equals("1"))
//		{
//			msg.setMsg("申请失败,此时间段暂时不能申请提现,请查看相关通告！");
//			msg.setSuccess(false);
//			return msg;
//		}		
		if (dept_type < 2 || dept_type > 4) {
			msg.setMsg("申请失败,所在单位无法通过此功能提现");
			msg.setSuccess(false);
			return msg;
		}
		HashMap<String, Object> deptMap = new HashMap<>();
		deptMap.put("loginName", StringUtils.checkString(loginSession.getUser_name()));
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(deptMap);
		BigDecimal deptMoney = StringUtils.checkBigDecimal(userList.get(0).get("dept_money"));
		//比例值
		BigDecimal propvalue = StringUtils.checkBigDecimal(userList.get(0).get("prop_values"));
		
		BigDecimal earnestMoney = new BigDecimal(0);
		BigDecimal withDrawMoney = new BigDecimal(0);
		BigDecimal tx_money = StringUtils.checkBigDecimal(paramsMap.get("tx_money"));
		
		//提现的手续费  公式：提现金额*比例=手续费
		BigDecimal counter_fee_money = StringUtils.checkBigDecimal(paramsMap.get("tx_money")).multiply(propvalue);
		//实际提现金额  公式  提现金额-手续费=到账金额
		BigDecimal real_tx_money = StringUtils.checkBigDecimal(paramsMap.get("tx_money")).subtract(counter_fee_money);
		//tx_money = multiply*provalue;
		if (0>tx_money.compareTo(new BigDecimal(100))){
			msg.setMsg("申请失败,申请额度不得小于100");
			msg.setSuccess(false);
			return msg;
		}
		//服务商
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
			if (0<tx_money.compareTo(withDrawMoney)) {
				msg.setMsg("申请失败,申请额度不得大于您的可申请额度");
				msg.setSuccess(false);
				return msg;
			}
		}/*else if(4==StringUtils.checkInt(loginSession.getDept_type())){
			//结算会员余额
			BigDecimal Money=StringUtils.checkBigDecimal(sysWithDrawService.queryDeptMoney(loginSession.getDept_parent_id()));
			//单位下所有用户资金
			BigDecimal totalMoney = StringUtils.checkBigDecimal(sysWithDrawService.sumUserWalletByDept(loginSession.getDept_parent_id()));
			earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
			if (0>earnestMoney.compareTo(new BigDecimal(10000))){
				earnestMoney = new BigDecimal(10000);
				
			}else{
				earnestMoney=earnestMoney.add( new BigDecimal(10000));
			}
			BigDecimal aBigDecimal=Money.subtract(earnestMoney);
			if (aBigDecimal.compareTo(new BigDecimal(0))<0) {				
				msg.setMsg("申请失败,申请额度不符合所属结算服务商条件");
				msg.setSuccess(false);
				return msg;
			}		
		}*/else{
			//代理商 渠道
			withDrawMoney = deptMoney;
		}
		if (0<tx_money.compareTo(withDrawMoney)) {
			msg.setMsg("申请失败,申请额度不得大于您的可申请额度");
			msg.setSuccess(false);
			return msg;
		}

		paramsMap.put("user_id", loginSession.getUser_id());
		paramsMap.put("user_name", loginSession.getUser_nick_name());
		paramsMap.put("dept_id", loginSession.getDept_id());
		paramsMap.put("dept_name", loginSession.getDept_name());
		paramsMap.put("dept_type", loginSession.getDept_type());
		paramsMap.put("dept_money", deptMoney);
		paramsMap.put("company_id", UUIDGenerator.generate());
		paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		paramsMap.put("is_status", "0");
		BigDecimal new_money = StringUtils.checkBigDecimal(deptMoney).subtract(tx_money);
		paramsMap.put("new_money", new_money);
		paramsMap.put("prop_values", propvalue);
		paramsMap.put("dept_counter_fee", counter_fee_money.doubleValue());
		paramsMap.put("real_tx_money", real_tx_money);
		try {
//			if(1==sysWithDrawService.updateDeptMoneyInfo(deptparamsMap)){
//				if(1==sysWithDrawService.insertWithDrawInfo(paramsMap)){
//					loginSession.setDept_money(new_money.toString());
//					session.setAttribute("userSession", loginSession);
//					msg.setMsg("申请成功，已提交审核");
//					msg.setSuccess(true);
//					return msg;
//				}else{
//					//钱已经扣了，单据未生成
//					msg.setMsg("申请失败，请稍后再试");
//					msg.setSuccess(true);
//					return msg;
//				}
//			}else{
//				msg.setMsg("申请失败，请稍后再试");
//				msg.setSuccess(true);
//				return msg;
//			}
			sysWithDrawService.updateMoney(paramsMap);
			
			msg.setMsg("申请成功，已提交审核");
			msg.setSuccess(true);
			return msg;
		} catch (Exception e) {
			msg.setMsg("申请失败,09261529");
			msg.setSuccess(false);
			e.printStackTrace();
			return msg;
		}
	}
	
}