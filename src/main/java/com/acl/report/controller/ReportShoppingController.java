package com.acl.report.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tools.ant.types.resources.selectors.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.component.MongodbBaseDao;
import com.acl.core.CoreBaseController;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportShoppingService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.distributedlock.DistributedLock;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:ReportShoppingOrderController
 *author:wangli
 *createDate:2017年3月27日 下午3:19:14
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportShoppingController extends CoreBaseController {
	@Autowired
	private IReportShoppingService reportShoppingService;
    @Autowired
	private MongodbBaseDao<T> mongodbBaseDao;
    @Autowired
    private DistributedLock distributedLock;
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/shoppingOrder" })
	@RequestMapping("shoppingOrder")
	public String shoppingOrder(){
		return "/report/report_shopping_order";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/shoppingRefund" })
	@RequestMapping("shoppingRefund")
	public String shoppingOrderRefund(){
		return "/report/report_shopping_refund";
	}
	
	/**
	 * 查询用户购买订单
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/shoppingOrder" })
	@RequestMapping("/queryShoppingOrder")
	public Object queryShoppingOrder(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
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
		case "6"://经纪人
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));		
		//查询经纪人日报表
		PageList<?> list = (PageList<?>)reportShoppingService.queryShoppingOrder(paramsMap, pageBounds);	
		Object json = this.getJsonMap(list);		
        return json;
	}
	
	/**
     * 设置快递单号
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/report/shoppingOrder" })
    @RequestMapping("/setTrackingNumber")
    public Object setTrackingNumber(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
	    // 设置快递单号
		paramsMap.put("status", "2");
		reportShoppingService.setTrackingNumber(paramsMap);
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
	 * 查询用户退款订单
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/shoppingRefund" })
	@RequestMapping("/queryShoppingRefund")
	public Object queryShoppingRefund(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
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
		case "6"://经纪人
			paramsMap.put("DID", loginSession.getDept_id());
			break;
		}
		String sortString = "";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));		
		//查询经纪人日报表
		PageList<?> list = (PageList<?>)reportShoppingService.queryShoppingRefund(paramsMap, pageBounds);	
		Object json = this.getJsonMap(list);		
        return json;
	}
	
	/**
     * 用户退款审核同意
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/report/shoppingRefund" })
    @RequestMapping("/agreeShoppingRefund")
    public Object agreeShoppingRefund(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
		
		/* 修改t_front_shopping_refund状态为1  修改t_front_shopping_order状态为6
		 * 同意退款后 需要将流水写入mongodb和MySQL中，并修改用户在redis中的金额
		 * 以及MySQL中t_front_user_wallet表中的user_money
		 * 根据refund_type字段进行判断
		 * 如果refund>=0and <=2，则往mongodb中的TradeSellOrderMongodb表中插入一条历史订单数据
		 * 如果refund>=3and <=4 ,则修改mongodb中的状态，并增加退款金额信息
		 */
		//查询退款订单信息，并且判断退款金额不得大于订单的amount
		List<Map<String,Object>> list=reportShoppingService.queryRefundInfo(paramsMap);
		paramsMap.put("refund_money", list.get(0).get("refund_money"));
		BigDecimal refund_money=new BigDecimal(StringUtils.convertString(list.get(0).get("refund_money")));
		BigDecimal amount=new BigDecimal(StringUtils.convertString(list.get(0).get("amount")));
		if(refund_money.subtract(amount).doubleValue()>0){
			 msg.setMsg("操作失败，退款金额不得大于订单金额");
			 msg.setSuccess(false);
			 return msg;
		}
		
		if(StringUtils.convertString(list.get(0).get("status")).equals("3")){
				 msg.setMsg("操作失败，买家已确认收货");
				 msg.setSuccess(false);
				 return msg;
		}
		String lockOrderStatus = distributedLock.getLock("t_front_shpping_order", StringUtils.convertString("shopping_order_id"));
		try{
		//修改订单状态,订单状态中status字段改为4（订单关闭）shopping_refund_status改为3（退款成功）
		paramsMap.put("status", "4");
		paramsMap.put("shopping_refund_status", "3");		
		reportShoppingService.updateShoppingOrderStatus(paramsMap);
		} finally {
	        distributedLock.releaseLock("t_front_shpping_order", StringUtils.convertString(paramsMap.get("shopping_order_id")), StringUtils.convertString(lockOrderStatus));
	    }      
		paramsMap.put("refund_status", "1");
		reportShoppingService.updateShoppingRefundStatus(paramsMap);
		//修改余额，写入流水
		reportShoppingService.updateUserBalance(paramsMap);
		//修改TradeSellOrderMongodb表
		String refund_type=StringUtils.convertString(list.get(0).get("refund_type"));
		if(refund_type.equals("3") || refund_type.equals("4")){
			 Map<String, Object> map=new HashMap<>();
			 map.put("shopping_order_id", paramsMap.get("shopping_order_id").toString());
			 Update update=new Update();
			 update.set("status","4");
			 update.set("shopping_refund_status","3");
			 update.set("refund_money",list.get(0).get("refund_money"));
		}else if(refund_type.equals("0") || refund_type.equals("1")|| refund_type.equals("2")){
			paramsMap.put("order_id", StringUtils.convertString(list.get(0).get("order_id")));
			paramsMap.put("p_id", StringUtils.convertString(list.get(0).get("p_id")));
			paramsMap.put("ch_id", StringUtils.convertString(list.get(0).get("ch_id")));
			paramsMap.put("ce_id", StringUtils.convertString(list.get(0).get("ce_id")));
			paramsMap.put("settle_id", StringUtils.convertString(list.get(0).get("settle_id")));
			paramsMap.put("settle_name", StringUtils.convertString(list.get(0).get("settle_name")));
			paramsMap.put("agent_id", StringUtils.convertString(list.get(0).get("agent_id")));
			paramsMap.put("agent_name",StringUtils.convertString(list.get(0).get("agent_name")));
			paramsMap.put("dept_id", StringUtils.convertString(list.get(0).get("dept_id")));
			paramsMap.put("dept_name", StringUtils.convertString(list.get(0).get("dept_name")));
			paramsMap.put("broker_id", StringUtils.convertString(list.get(0).get("broker_id")));
			paramsMap.put("broker_name",StringUtils.convertString(list.get(0).get("broker_name")));
			paramsMap.put("user_id", StringUtils.convertString(list.get(0).get("user_id")));
		    paramsMap.put("user_name",StringUtils.convertString(list.get(0).get("user_name")));
		    paramsMap.put("user_mobile",StringUtils.convertString(list.get(0).get("user_mobile")));
		    paramsMap.put("buy_type", StringUtils.convertString(list.get(0).get("buy_type")));
		    paramsMap.put("buy_point", StringUtils.convertString(list.get(0).get("buy_point")));
		    paramsMap.put("buy_unit", StringUtils.convertString(list.get(0).get("buy_unit")));
		    paramsMap.put("buy_time", StringUtils.convertString(list.get(0).get("buy_time")));
		    paramsMap.put("buy_itemtype", StringUtils.convertString(list.get(0).get("buy_itemtype")));
		    paramsMap.put("buy_itemcode",StringUtils.convertString(list.get(0).get("buy_itemcode")));
		    paramsMap.put("buy_itemname",StringUtils.convertString(list.get(0).get("item_name")));
		    paramsMap.put("buy_price", StringUtils.convertString(list.get(0).get("buy_price")));
		    paramsMap.put("buy_point", StringUtils.convertString(list.get(0).get("buy_point")));
		    paramsMap.put("buy_amount", StringUtils.convertString(list.get(0).get("buy_amount")));
		    paramsMap.put("buy_brokerage", StringUtils.convertString(list.get(0).get("buy_brokerage")));
		    paramsMap.put("buy_all_price",StringUtils.convertString(list.get(0).get("buy_all_price")));
		    paramsMap.put("sell_point",StringUtils.convertString(list.get(0).get("sell_point")));
		    paramsMap.put("sell_time", StringUtils.convertString(list.get(0).get("sell_time")));//t_shopping_order里的创建时间
		    paramsMap.put("gdsy_buy_ratio", 0);
		    paramsMap.put("gdsy_buy_xz_time", 0);
		    paramsMap.put("gzp_profit", 0);
		    paramsMap.put("gzp_sell_zy_point", 0);
		    paramsMap.put("gzp_sell_zs_point", 0);
		    paramsMap.put("sell_type", 0);
		    paramsMap.put("sell_profit_loss", StringUtils.convertString(list.get(0).get("balance_payment")));     //尾款
		    paramsMap.put("settlement_value", 0);   
			paramsMap.put("is_use","1");
			paramsMap.put("trading_rule", "01");
			paramsMap.put("item_id", StringUtils.convertString(list.get(0).get("refund_type")));
			 
			paramsMap.put("shopping_order_id", StringUtils.convertString(list.get(0).get("shopping_order_id")));
			paramsMap.put("item_name_alias",StringUtils.convertString(list.get(0).get("item_name_alias")));
			paramsMap.put("amount", StringUtils.convertString(list.get(0).get("amount")));
			paramsMap.put("pre_payment", StringUtils.convertString(list.get(0).get("pre_payment")));
			paramsMap.put("balance_payment", StringUtils.convertString(list.get(0).get("balance_payment")));
			paramsMap.put("receiver_name", StringUtils.convertString(list.get(0).get("receiver_name")));
			paramsMap.put("receiver_mobile", StringUtils.convertString(list.get(0).get("receiver_mobile")));
			paramsMap.put("receiver_addr",StringUtils.convertString(list.get(0).get("receiver_addr")));
			paramsMap.put("create_date",new Date());    //下单时间
			//paramsMap.put("status", "4");
			paramsMap.put("tracking_number", StringUtils.convertString(list.get(0).get("tracking_number")));
			paramsMap.put("express", StringUtils.convertString(list.get(0).get("express")));
			 
			 
			paramsMap.put("buy_number", StringUtils.convertString(list.get(0).get("buy_number")));
			
			//paramsMap.put("refund_money", list.get(0).get("refund_money"));
			mongodbBaseDao.save(paramsMap, "TradeSellOrderMongodb");
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
     * 用户退款审核不同意
     * 
     * @return
     */
    @ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/report/shoppingRefund" })
    @RequestMapping("/disagreeShoppingRefund")
    public Object disagreeShoppingRefund(@Session(create = false) SessionProvider session, HttpServletRequest request,
	    @RequestParam HashMap<String, Object> paramsMap) {
	try {
		List<Map<String,Object>> list=reportShoppingService.queryRefundInfo(paramsMap);
		Update update = new Update();
		String refund_type=StringUtils.convertString(list.get(0).get("refund_type"));
		/*if(refund_type.equals("3") || refund_type.equals("4")){
			//修改订单状态
			//paramsMap.put("status", "7");
			//update.set("status", "7");
		}else if(refund_type.equals("0") || refund_type.equals("1")|| refund_type.equals("2")){
			//修改订单状态
			paramsMap.put("status", "5");
			update.set("status", "5");
		}*/
		paramsMap.put("shopping_refund_status", "2");
		update.set("shopping_refund_status", "2");
		paramsMap.put("shopping_order_id", list.get(0).get("shopping_order_id"));
		if(StringUtils.convertString(list.get(0).get("status")).equals("3")){
			 msg.setMsg("操作失败，买家已确认收货");
			 msg.setSuccess(false);
			 return msg;
		}
		String lockOrderStatus = distributedLock.getLock("t_front_shpping_order", StringUtils.convertString("shopping_order_id"));
		try{
			reportShoppingService.updateShoppingOrderStatus(paramsMap);
		}finally {
	        distributedLock.releaseLock("t_front_shpping_order", StringUtils.convertString(paramsMap.get("shopping_order_id")), StringUtils.convertString(lockOrderStatus));
	    }
		
	    //审核不同意把该申请订单状态改为2 refund_status=2 
		paramsMap.put("refund_status", "2");
		reportShoppingService.updateShoppingRefundStatus(paramsMap);
		//写入mongodb		
		update.set("reject_reason", paramsMap.get("reject_reason"));
		HashMap< String, Object> map1=new HashMap<>();
		map1.put("shopping_order_id", StringUtils.convertString(list.get(0).get("shopping_order_id")));
	    msg.setMsg("操作成功");
	    msg.setSuccess(true);
	} catch (Exception e) {
	    msg.setMsg("操作失败");
	    msg.setSuccess(false);
	    e.printStackTrace();
	}
	return msg;
    }
    
    
    
}
