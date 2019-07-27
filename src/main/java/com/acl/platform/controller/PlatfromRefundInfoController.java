package com.acl.platform.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformRefundInfoService;
import com.acl.pojo.LoginSession;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatfromRefundController
 *author:wangli
 *createDate:2017年4月5日 下午4:46:12
 *vsersion:3.0
 *department:安创乐科技
 *description:处理提现失败后的退款操作
 */
@Controller
@RequestMapping("/platform")
public class PlatfromRefundInfoController extends CoreBaseController {
	@Autowired
	private IPlatformRefundInfoService platformRefundInfoService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/refundInfo" })
	@RequestMapping("/refundInfo")
	public String refundInfo(){
		return "platform/platform_refund_info";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/refundInfo" })
	@RequestMapping("/queryRefundInfo")
	public Object queryRefundInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
								  @RequestParam(defaultValue = "0", required = false) Integer page,
								  @RequestParam(defaultValue = "10", required = false) Integer rows,
								  @RequestParam HashMap<String,Object> paramsMap){
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询提现审核信息
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		if(!dept_type.equals("0")){
			paramsMap.put("dept_id", loginSession.getDept_id());
		}
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
				paramsMap.put("agent_dept_id", loginSession.getDept_id());
				break;
		}
		if(!"".equals(StringUtils.checkString(paramsMap.get("b_mobile"))))
		{
			paramsMap.put("mobile",paramsMap.get("b_mobile").toString());
		}
		PageList<?> list = (PageList<?>)platformRefundInfoService.queryRefundInfo(paramsMap,pageBounds);
		//Object json = this.getJsonMap(list);
		//判断传过来的参数是否是带有解密
		//手机号数据库解密
		PageList<?> listMap =null;
		listMap=PhoneCodeSSL.getDataBaseListPage(list, paramsMap);
		Object json = this.getJsonMap(listMap);
		return json;
	}



	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/refundInfo" })
	@RequestMapping("/checkJinYunTongPayOrder")
	public Object checkJinYunTongPayOrder(@Session(create = false) SessionProvider session,HttpServletRequest request,
										  @RequestParam(defaultValue = "0", required = false) Integer page,
										  @RequestParam(defaultValue = "10", required = false) Integer rows,
										  @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		dbLog.setMethod_name("金运通出金检查"+paramsMap.get("refund_channel_rule_id"));
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String code=platformRefundInfoService.checkJinYunTongPayOrder(StringUtils.convertString(paramsMap.get("id")),StringUtils.convertString(paramsMap.get("refund_channel_rule_id")));
		switch (code) {
			case "0":
				msg.setMsg("处理中");
				msg.setSuccess(true);
				dbLog.setAction_message("处理中:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(1);
				break;
			case "1":
				msg.setMsg("成功");
				msg.setSuccess(true);
				dbLog.setAction_message("成功:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(1);
				break;
			case "2":
				msg.setMsg("返回结果异常");
				msg.setSuccess(true);
				dbLog.setAction_message("返回结果异常:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(1);
				break;
			case "3":
				msg.setMsg("该订单支付失败");
				msg.setSuccess(true);
				dbLog.setAction_message("该订单支付失败:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(0);
				break;
			case "4":
				msg.setMsg("原代付交易异常，请联系管理员");
				msg.setSuccess(true);
				dbLog.setAction_message("原代付交易异常，请联系管理员:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(0);
				break;
			default:
				msg.setMsg("系统异常，请联系管理员");
				msg.setSuccess(true);
				dbLog.setAction_message("系统异常，请联系管理员:"+paramsMap.get("tx_order_id"));
				dbLog.setIs_ok(1);
				break;
		}
		dbLogService.insertLog(dbLog);
		return msg;
	}

}
