package com.acl.platform.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.SystemConfig;
import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformBrokerPaymentService;
import com.acl.platform.service.IPlatformUserPaymentService;
import com.acl.platform.service.IPlatformWithDrawalsApplyService;
import com.acl.pojo.LoginSession;
import com.acl.pojo.TxOrder;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.ClientExecuteProxyUtils;
import com.acl.utils.util.MySecurity;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.distributedlock.DistributedLock;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformWithDrawalsApplyController
 *author:wangli
 *createDate:2016年8月19日 下午8:00:22
 *vsersion:3.0
 *department:安创乐科技
 *description:出入金
 */
@Controller
@RequestMapping("/platform")
public class PlatformWithDrawalsApplyController extends CoreBaseController {
	@Autowired
	private IPlatformWithDrawalsApplyService platformWithDrawalsApplyService;

	@Autowired
	private IPlatformBrokerPaymentService platformBrokerPaymentService;

	@Autowired
	private IPlatformUserPaymentService platformUserPaymentService;

	@Autowired
	private DistributedLock distributedLock;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/withdrawalsApply" })
	@RequestMapping("/withdrawalsApply")
	public String withdrawalsApply(){
		return "platform/platform_withdrawals_apply";
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/withdrawalsApply" })
	@RequestMapping("/queryWithDrawalsApply")
	public Object queryWithDrawalsApply(@Session(create = false) SessionProvider session,HttpServletRequest request,
										@RequestParam(defaultValue = "0", required = false) Integer page,
										@RequestParam(defaultValue = "10", required = false) Integer rows,
										@RequestParam HashMap<String,Object> paramsMap){
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询提现审核信息
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_type=loginSession.getDept_type();
		if(!dept_type.equals("0")){
			msg.setMsg("您没有提现审核的权限，请勿操作");
			msg.setSuccess(false);
			return msg;
		}
		PageList<?> list = (PageList<?>)platformWithDrawalsApplyService.queryWithDrawalsApply(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}


	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/withdrawalsApply" })
	@RequestMapping(value = "/withdrawals", method = RequestMethod.POST)
	public Object withDrawals(HttpServletRequest request,
							  HttpServletResponse response, @RequestParam HashMap<String, Object> loginMap,@Session(create = false) SessionProvider session)  {
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		JSONObject object=new JSONObject();
		if (null != session) {
			dbLog.setMethod_name("提现审核");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		}
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			//获取 订单id
			//查询订单是否已经审核  并查询提现类型和提现金额

			String id = StringUtils.convertString(request.getParameter("id"));//提现订单id
			//给tx_order表加redis分布式锁
			HashMap< String, Object> map=new HashMap<>();
			map.put("id", id);
			String lockTxOrder = distributedLock.getLock("t_pay_refund_order", StringUtils.convertString(id));
			List<Map<String,Object>> list=platformWithDrawalsApplyService.queryOrderWithdrawalsIsUse(map);
			//判断订单是否已经审核
			if(list.size()==0){
				distributedLock.releaseLock("t_pay_refund_order", StringUtils.convertString(id), StringUtils.convertString(lockTxOrder));
				msg.setSuccess(false);
				msg.setMsg("该订单已审核，不可重复审核");
				return msg;
			}
			//更改订单状态
			Map<String, Object> ma = new HashMap<String, Object>();
			ma.put("status", 2);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
			ma.put("id",  id);
			platformWithDrawalsApplyService.updateTxOrder(ma);
			//提现类型
			String tx_type=list.get(0).get("refund_channel_rule_id").toString();
			switch (tx_type) {
				case "jytpay":
					object= platformWithDrawalsApplyService.getJinYunTongPay(id,tx_type);
					if(object.get("success").equals("1")){
						dbLog.setAction_message("成功");
						dbLog.setIs_ok(0);
						msg.setSuccess(true);
					}else{
						dbLog.setAction_message(object.get("id")+"提现http请求发送失败,当前的提现渠道为金运通提现");
						dbLog.setIs_ok(1);
						msg.setSuccess(false);
					}
					break;
				default:
					break;
			}
			distributedLock.releaseLock("t_pay_refund_order", StringUtils.convertString(id), StringUtils.convertString(lockTxOrder));
		} catch (IOException e1) {

			e1.printStackTrace();
		}finally{

			dbLogService.insertLog(dbLog);
		}
		System.out.println(StringUtils.convertString(object.get("msg")));
		msg.setMsg(StringUtils.convertString(object.get("msg")));
		return msg;
	}


	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/querySettleInfoForCombox")
	public Object querySettleInfoForCombox(@Session(create = false) SessionProvider session,HttpServletRequest request,
										   @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_id=loginSession.getDept_id();
		String dept_type=loginSession.getDept_type();
		String settle_id=loginSession.getSettle_id();
		String agent_id=loginSession.getAgent_id();
		paramsMap.put("dept_id", dept_id);
		paramsMap.put("dept_type", dept_type);
		paramsMap.put("settle_id", settle_id);
		paramsMap.put("agent_id", agent_id);
		//查询结算服务商
		List<Map<String,Object>> list=platformWithDrawalsApplyService.querySettleInfoForCombox(paramsMap);
		return list;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryAgentInfoForCombox")
	public Object queryAgentInfoForCombox(@Session(create = false) SessionProvider session,HttpServletRequest request,
										  @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_id=loginSession.getDept_id();
		String dept_type=loginSession.getDept_type();
		paramsMap.put("dept_id", dept_id);
		paramsMap.put("dept_type", dept_type);
		paramsMap.put("agent_id", loginSession.getAgent_id());
		//查询代理商部门
		List<Map<String,Object>> list=platformWithDrawalsApplyService.queryAgentInfoForCombox(paramsMap);
		return list;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryDeptInfoForCombox")
	public Object queryDeptInfoForCombox(@Session(create = false) SessionProvider session,HttpServletRequest request,
										 @RequestParam HashMap<String,Object> paramsMap){
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		String dept_id=loginSession.getDept_id();
		String dept_type=loginSession.getDept_type();
		paramsMap.put("dept_id", dept_id);
		paramsMap.put("dept_type", dept_type);
		//查询代理商部门
		List<Map<String,Object>> list=platformWithDrawalsApplyService.queryDeptInfoForCombox(paramsMap);
		return list;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryAgentForCombox")
	public Object queryAgentForCombox(@Session(create = false) SessionProvider session,HttpServletRequest request,
									  @RequestParam HashMap<String,Object> paramsMap){

		paramsMap.put("dept_type", 3.5);
		//查询代理商部门
		List<Map<String,Object>> list=platformWithDrawalsApplyService.queryAgentInfoForCombox(paramsMap);
		return list;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/rebackWithDraw")
	public Object rebackWithDraw(HttpServletRequest request,
								 HttpServletResponse response, @RequestParam HashMap<String, Object> loginMap,@Session(create = false) SessionProvider session){
		JSONObject object=new JSONObject();
		boolean b=false;
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		if (null != session) {
			dbLog.setMethod_name("提现审核打回");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		}
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			//查询是否已经审核
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("id", request.getParameter("id"));
			//查询订单信息
			Map<String, Object> map= platformWithDrawalsApplyService.queryOrderWithdrawalsMoney(paramsMap);
			if(!map.get("status").equals("1")){
				object.put("success", 2);
				dbLog.setAction_message(request.getParameter("tx_order_id")+"已经审核，不可重复审核");
				dbLog.setIs_ok(1);
				return object;
			}

			//更改订单状态
			Map<String, Object> ma = new HashMap<String, Object>();
			ma.put("refund_time",date);
			ma.put("update_time",date);
			ma.put("status", 3);//状态(1,待审核,2,审核通过，提现中,3.拒绝提现申请,4.审核通过，提现成功,5.提现失败,6.作废)
			ma.put("id",  request.getParameter("id"));
			platformWithDrawalsApplyService.updateTxOrder(ma);

			paramsMap.put("user_id",map.get("user_id"));

			b= platformWithDrawalsApplyService.rebackUserWithDraw(paramsMap);

			if(b=true){
				object.put("success", 1);
				dbLog.setAction_message("成功");
				dbLog.setIs_ok(0);
			}else {
				object.put("success", 0);
				dbLog.setAction_message(request.getParameter("id")+"打回失败");
				dbLog.setIs_ok(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			dbLogService.insertLog(dbLog);
		}
		return object;

	}

	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/querySumUserTX")
	public Object querySumUserTX(@Session(create = false) SessionProvider session,HttpServletRequest request,
								 @RequestParam HashMap<String,Object> paramsMap){
		List<Map<String,Object>> list=platformWithDrawalsApplyService.querySumUserTX(paramsMap);
		List<Map<String,Object>> list2=platformWithDrawalsApplyService.querySumOutTX(paramsMap);
		JSONObject object=new JSONObject();
		object.put("sumTX", list.get(0).get("sumTX"));
		object.put("sumOut", list2.get(0).get("sumOut"));
		return object;
	}

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/withdrawalsApply" })
	@RequestMapping(value = "/allWithDrawals", method = RequestMethod.POST)
	public JSONObject allWithDrawals(HttpServletRequest request,
									 HttpServletResponse response, @RequestParam HashMap<String, Object> loginMap,@Session(create = false) SessionProvider session)  {

		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		JSONObject object=new JSONObject();
		if (null != session) {
			dbLog.setMethod_name("批量提现审核通过");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
			try {
				dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String [] orders_id=loginMap.get("order_ids").toString().split(",");
		System.out.println("订单id"+orders_id);
		if(orders_id.length>0){
			for(int i=0;i<orders_id.length;i++){
				Map<String, Object> paramsMap=new HashMap<String, Object>();
				paramsMap.put("id", orders_id[i]);
				//给tx_order表加redis分布式锁
				String lockTxOrder = distributedLock.getLock("t_pay_refund_order", StringUtils.convertString(orders_id[i]));
				List<Map<String,Object>> list=platformWithDrawalsApplyService.queryOrderWithdrawalsIsUse(paramsMap);
				//判断订单是否已经审核
				if(list.size()==0){
					distributedLock.releaseLock("t_pay_refund_order", StringUtils.convertString(orders_id[i]), StringUtils.convertString(lockTxOrder));
					continue;
				}
				//更改订单状态
				Map<String, Object> ma = new HashMap<String, Object>();
				ma.put("status", 2);//0表示不可用
				ma.put("id",  orders_id[i]);
				ma.put("refund_time",date);
				ma.put("update_time",date);
				platformWithDrawalsApplyService.updateTxOrder(ma);

				//查询订单信息
				Map<String, Object> orderMap = platformWithDrawalsApplyService.queryOrderWithdrawalsMoney(paramsMap);

				switch (orderMap.get("refund_channel_rule_id").toString()) {
					case "jytpay":
						object= platformWithDrawalsApplyService.getJinYunTongPay(orders_id[i],orderMap.get("refund_channel_rule_id").toString());
						if(object.get("success").equals("1")){
							dbLog.setAction_message("成功");
							dbLog.setIs_ok(0);
							msg.setSuccess(true);
						}else{
							dbLog.setAction_message(object.get("orderNo")+"提现http请求发送失败,当前的提现渠道为金运通提现");
							dbLog.setIs_ok(1);
							msg.setSuccess(false);
						}
						break;

					default:
						break;
				}
				dbLog.setAction_message("批量审核"+orders_id[i]);
				dbLog.setIs_ok(0);
				distributedLock.releaseLock("t_pay_refund_order", StringUtils.convertString(orders_id[i]), StringUtils.convertString(lockTxOrder));
				dbLogService.insertLog(dbLog);

			}
		}

		return object;
	}


	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/allRebackWithDraw")
	public Object allRebackWithDraw(HttpServletRequest request,
									HttpServletResponse response, @RequestParam HashMap<String, Object> loginMap,@Session(create = false) SessionProvider session){
		String [] orders_id=loginMap.get("order_ids").toString().split(",");
		JSONObject object=new JSONObject();
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
		if (null != session) {
			dbLog.setMethod_name("批量提现审核打回");
			dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		}
		if(orders_id.length>0){
			for(int i=0;i<orders_id.length;i++){
				boolean b;
				try {
					dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));

					Map<String, Object> paramsMap=new HashMap<String, Object>();
					paramsMap.put("orderid", orders_id[i]);
					//查询订单信息
					Map<String, Object> orderMap = platformWithDrawalsApplyService.queryOrderWithdrawalsMoney(paramsMap);
					if(!StringUtils.convertString(orderMap.get("status")).equals("1")){
						dbLog.setAction_message(orders_id[i]+"已经审核，不可重复审核");
						dbLog.setIs_ok(1);
						continue;
					}

					HashMap<String,Object> map=new HashMap<>();
					map.put("user_id",orderMap.get("user_id"));
					map.put("id",orders_id[i]);
					b= platformWithDrawalsApplyService.rebackUserWithDraw(map);
					if(b==true){
						dbLog.setAction_message("成功");
						dbLog.setIs_ok(0);
					}else{
						dbLog.setAction_message(orders_id[i]+"审核打回失败");
						dbLog.setIs_ok(1);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					dbLogService.insertLog(dbLog);
				}
			}
		}
		return object;

	}

    @ResponseBody
    @RequiresAuthentication
    @RequestMapping("/queryAgentInfoForCombox2")
    public Object queryAgentInfoForCombox2(@Session(create = false) SessionProvider session,HttpServletRequest request,
                                          @RequestParam HashMap<String,Object> paramsMap){
        LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
        String dept_id=loginSession.getDept_id();
        String dept_type=loginSession.getDept_type();
        paramsMap.put("dept_id", dept_id);
        paramsMap.put("dept_type", dept_type);
        //查询代理商部门
        List<Map<String,Object>> list=platformWithDrawalsApplyService.queryAgentInfoForCombox2(paramsMap);
        return list;
    }
}
