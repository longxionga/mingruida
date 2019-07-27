package com.acl.report.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.pojo.BuildAndSellInfo;
import com.acl.pojo.LoginSession;
import com.acl.report.service.IReportBuildAndSellService;
import com.acl.utils.mongo.Pagination;
import com.acl.utils.util.NetUtils;
import com.acl.utils.util.PhoneCodeSSL;
import com.acl.utils.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 * 
 *className:ReportBuildAndSellController
 *author:wangzhe
 *createDate:2016年9月23日 上午10:25:15
 *version:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/report")
public class ReportBuildAndSellController extends CoreBaseController{

	@Autowired
	private IReportBuildAndSellService reportBuildAndSellService;

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/buildInfo"})
	@RequestMapping("/buildInfo")
	public String BuildInfo() {
		return "report/report_build_info";
	}

	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/sellInfo"})
	@RequestMapping("/sellInfo")
	public String SellInfo() {
		return "report/report_sell_info";
	}
	
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/chargebackInfo"})
	@RequestMapping("/chargebackInfo")
	public String ChargeBackInfo() {
		return "report/report_chargeback_info";
	}
	
	/**
	 * 查询建仓信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/buildInfo" })
	@RequestMapping("/queryBuildInfo")
	public Object queryBuildInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
		    @RequestParam(defaultValue = "0", required = false) Integer page,
		    @RequestParam(defaultValue = "10", required = false) Integer rows,
		    @RequestParam HashMap<String, Object> paramsMap) {
	    	//String sortString = "";
			paramsMap.put("mark", "build");
	    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
//			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
//			{
//				paramsMap.put("mobile",MySecurity.encryptAES(paramsMap.get("mobile").toString(), SystemConfig.keyMy));
//			}	
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
//			if(dept_type.equals("5"))
//			{
//				paramsMap.put("dept_id", loginSession.getDept_id());
//			}
	    	//paramsMap.put("dept_id", loginSession.getDept_id());
			//PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));	
		
			//paramsMap.put(key, value);
			//查询
			Pagination<T> list = reportBuildAndSellService.queryBuildPositionInfo(paramsMap);
			Pagination<T> listMap = null;
			//list.getTotal()>0
			//Pagination<T> listMap = PhoneCodeSSL.getDataBasePagination(list, paramsMap);
			if(list.getTotal()>0)
			{
				listMap = PhoneCodeSSL.getDataBasePagination(list, paramsMap);
				return listMap;

			}else
			{
				
				return list;
			}			
	    }
	
	/**
	 * 查询平仓信息
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/sellInfo" })
	@RequestMapping("/querySellInfo")
	public Object querySellInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
		    @RequestParam(defaultValue = "0", required = false) Integer page,
		    @RequestParam(defaultValue = "10", required = false) Integer rows,
		    @RequestParam HashMap<String, Object> paramsMap) throws ParseException {
	    	//String sortString = "";
	    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
			paramsMap.put("mark", "sell");
//			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
//			{
//				paramsMap.put("mobile",MySecurity.encryptAES(paramsMap.get("mobile").toString(), SystemConfig.keyMy));
//			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
			{				
		       	paramsMap.put("begindate",paramsMap.get("begindate").toString());
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("enddate"))))
			{				
		       	paramsMap.put("enddate",paramsMap.get("enddate").toString());
			}
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
//			if(dept_type.equals("5"))
//			{
//				paramsMap.put("dept_id", loginSession.getDept_id());
//			}
	    	//paramsMap.put("dept_id", loginSession.getDept_id());
			//PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));	
					
			Pagination<T> list = reportBuildAndSellService.querySellPositionInfo(paramsMap);			
			Pagination<T> listMap = null;
			if(list.getTotal()>0)
			{
				listMap = PhoneCodeSSL.getDataBasePagination(list, paramsMap);
				return listMap;

			}else
			{
				return list;
			}			
			//return list;
	    }
	
	/**
	 * 查询平仓退单信息
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/chargebackInfo" })
	@RequestMapping("/queryChargeBackInfo")
	public Object queryChargeBackInfo(@Session(create = false) SessionProvider session, HttpServletRequest request,
		    @RequestParam(defaultValue = "0", required = false) Integer page,
		    @RequestParam(defaultValue = "10", required = false) Integer rows,
		    @RequestParam HashMap<String, Object> paramsMap) throws ParseException {
	    	//String sortString = "";
	    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
			paramsMap.put("mark","sell");
			paramsMap.put("marktype","back");
			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
			{
				paramsMap.put("mobile",paramsMap.get("mobile").toString());
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("state_type"))))
			{
				paramsMap.put("is_use",paramsMap.get("state_type").toString());
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("order_id"))))
			{
				paramsMap.put("order_id",paramsMap.get("order_id").toString());
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("begindate"))))
			{				
		       	paramsMap.put("begindate",paramsMap.get("begindate").toString());
			}
			if(!"".equals(StringUtils.checkString(paramsMap.get("enddate"))))
			{				
		       	paramsMap.put("enddate",paramsMap.get("enddate").toString());
			}
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}					
			Pagination<T> list = reportBuildAndSellService.querySellPositionBackInfo(paramsMap);			
//			Pagination<T> listMap = null;
//			if(list.getTotal()>0)
//			{
//				listMap = PhoneCodeSSL.getDataBasePagination(list, paramsMap);
//				return listMap;
//
//			}else
//			{
//				return list;
//			}			
			return list;
	    }
	
	/**
	 * 查询建仓总数据
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/buildInfo" })
	@RequestMapping("/queryBuildInfoSum")
	public Object queryBuildInfoCount(@Session(create = false) SessionProvider session, HttpServletRequest request,
		    @RequestParam(defaultValue = "0", required = false) Integer page,
		    @RequestParam(defaultValue = "10", required = false) Integer rows,
		    @RequestParam HashMap<String, Object> paramsMap) {
	    	//String sortString = "";
	    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
			paramsMap.put("mark", "build");
//			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
//			{
//				paramsMap.put("mobile",paramsMap.get("mobile").toString());
//			}	
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
//			if(dept_type.equals("5"))
//			{
//				paramsMap.put("dept_id", loginSession.getDept_id());
//			}
			HashMap<String, Object> infomap = new HashMap<String, Object>();
			List<BuildAndSellInfo> list = (List<BuildAndSellInfo>) reportBuildAndSellService.queryBuildInfoCount(paramsMap);
			if(list.size()>0)
			{
				infomap.put("buy_amount_count", list.get(0).getSum_1());
				infomap.put("buy_brokerage_count", list.get(0).getSum_2());
				//infomap.put("buy_price_count", list.get(0).getSum_3());
			}else
			{
				infomap.put("buy_amount_count", 0);
				infomap.put("buy_brokerage_count", 0);
				//infomap.put("buy_price_count", 0);
			}
			return infomap;
	    }
	
	/**
	 * 查询平仓信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/report/sellInfo" })
	@RequestMapping("/querySellInfoSum")
	public Object querySellInfoCount(@Session(create = false) SessionProvider session, HttpServletRequest request,
		    @RequestParam(defaultValue = "0", required = false) Integer page,
		    @RequestParam(defaultValue = "10", required = false) Integer rows,
		    @RequestParam HashMap<String, Object> paramsMap) {
	    	//String sortString = "";
	    	LoginSession loginSession = (LoginSession) session.getAttribute("userSession");		
			String dept_type=loginSession.getDept_type();
			paramsMap.put("mark", "sell");
//			if(!"".equals(StringUtils.checkString(paramsMap.get("mobile"))))
//			{
//				paramsMap.put("mobile",paramsMap.get("mobile").toString());
//			}	
			if(dept_type.equals("4"))
			{
				paramsMap.put("agent_id", loginSession.getAgent_id());
			}
			if(dept_type.equals("3"))
			{
				paramsMap.put("settle_id", loginSession.getSettle_id());
			}
			if(dept_type.equals("2"))
			{
				paramsMap.put("ch_id", loginSession.getDept_id());
			}
			if(dept_type.equals("1"))
			{
				paramsMap.put("ce_id", loginSession.getDept_id());
			}
			if(dept_type.equals("5"))
			{
				paramsMap.put("dept_id", loginSession.getDept_id());
			}
//			if(dept_type.equals("5"))
//			{
//				paramsMap.put("dept_id", loginSession.getDept_id());
//			}
			HashMap<String, Object> infomap = new HashMap<String, Object>();
			List<BuildAndSellInfo> list = (List<BuildAndSellInfo>) reportBuildAndSellService.querySellInfoCount(paramsMap);
			if(list.size()>0)
			{
				infomap.put("buy_amount_count", list.get(0).getSum_1());
				infomap.put("buy_brokerage_count", list.get(0).getSum_2());
				infomap.put("buy_price_count", list.get(0).getSum_3());
			}else
			{
				infomap.put("buy_amount_count", 0);
				infomap.put("buy_brokerage_count", 0);
				infomap.put("buy_price_count", 0);
			}
			return infomap;
	    }
	
	/**
	 * 操作退单功能
	 * 1、moon写入一条流水
	 * 2、更新平仓信息订单状态
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/report/chargebackInfo" })
	@RequestMapping("/updchargebackInfo")
	public Object updateChargeBackInfo(@Session(create = false) SessionProvider session,@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) throws ParseException {
			
		JSONObject object=new JSONObject();
		//超过当月10日无法退上月的单
		String selltime=paramsMap.get("sell_time").toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		long lt = new Long(selltime);
		Date date = new Date(lt);
		String res = simpleDateFormat.format(date);

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH);// 获取月份
		//int day = cal.get(Calendar.DATE);// 获取日
		String rnt = year + "-" + (month + 1) + "-10 00:00:00";
		String rco = year + "-" + (month + 1) + "-1 07:00:00";
		String rcn = year + "-" + (month + 1) + "-9 00:00:00";
		//String rpl = year + "-" + (month) + "-"+getDaysByYearMonth(year,month);
		String rdo = year + "-" + (month) + "-1 07:00:00";

		//System.out.println(cal.add(Calendar.DATE, -1));
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		cal1.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天 

		Date dt1 = df.parse(rnt); // 当月10日
		Date dt2 = df.parse(res); // 平仓时间
		Date dt3 = df.parse(df.format(new Date())); // 当前时间
		Date dt4 = df.parse(rco); // 当前时间的1号
		//Date dt5 = df.parse(rpl); // 上个月的最后一天
		Date dt6 = df.parse(rcn); // 这个月的9号
		Date dt7 = df.parse(rdo); // 上个月的1号
		System.out.println("selltime:" + df.format(new Date()));
		LoginSession loginSession = (LoginSession) session.getAttribute("userSession");	
		dbLog.setMethod_name("退单操作");
		dbLog.setCq_params(StringUtils.checkString(loginSession.getUser_name()));
		try {
			dbLog.setCq_paramsAdd(NetUtils.getIpAddress(request));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dt1.getTime() <= dt3.getTime()) {
			if (dt2.getTime() < dt4.getTime()) 
			{
				dbLog.setAction_message("退单,失败!已过退单时间,订单编号为:"+StringUtils.convertString(paramsMap.get("order_id"))+","+"用户id为:"+StringUtils.convertString(paramsMap.get("user_id")));
				dbLog.setIs_ok(1);
				msg.setSuccess(false);
				msg.setMsg("退单失败,已过退单时间！");
			}
			else 
				{
					object = reportBuildAndSellService.updatChargeBackInfo(paramsMap);
					if(object.get("success").equals("true"))
					{ 
						dbLog.setAction_message("退单,成功!订单编号为:"+StringUtils.convertString(object.get("order_id"))+","+"用户id为:"+StringUtils.convertString(object.get("user_id"))+","+"退款金额:"+StringUtils.convertString(object.get("user_money"))+","+"redis修改:"+StringUtils.convertString(object.get("redis_success"))+","+"redids信息:"+StringUtils.convertString(object.get("redis_update")));
						dbLog.setIs_ok(0);	
						msg.setSuccess(true);
						msg.setMsg("退单成功!");
						
					}else
					{
						dbLog.setAction_message("退单,失败!没找到该订单号,订单编号为:"+StringUtils.convertString(object.get("order_id"))+","+"用户id为:"+StringUtils.convertString(object.get("user_id"))+","+"redis修改:"+StringUtils.convertString(object.get("redis_success"))+","+"redids信息:"+StringUtils.convertString(object.get("redis_update")));
						dbLog.setIs_ok(1);
						msg.setSuccess(false);
						msg.setMsg("退单失败,没找到该订单号!");
					}		
				}
		}else
		{
			if(dt7.getTime()<dt2.getTime() && dt2.getTime()<dt6.getTime())
			{
				object = (JSONObject) reportBuildAndSellService.updatChargeBackInfo(paramsMap);
				if(object.get("success").equals("true"))
				{
					dbLog.setAction_message("退单,成功!订单编号为:"+StringUtils.convertString(object.get("order_id"))+","+"用户id为:"+StringUtils.convertString(object.get("user_id"))+","+"退款金额:"+StringUtils.convertString(object.get("user_money"))+","+"redis修改:"+StringUtils.convertString(object.get("redis_success"))+","+"redids信息:"+StringUtils.convertString(object.get("redis_update")));
					dbLog.setIs_ok(0);	
					msg.setSuccess(true);
					msg.setMsg("退单成功!");
				}else
				{
					dbLog.setAction_message("退单,失败!没找到该订单号,订单编号为:"+StringUtils.convertString(object.get("order_id"))+","+"用户id为:"+StringUtils.convertString(object.get("user_id"))+","+"redis修改:"+StringUtils.convertString(object.get("redis_success"))+","+"redids信息:"+StringUtils.convertString(object.get("redis_update")));
					dbLog.setIs_ok(1);
					msg.setSuccess(false);
					msg.setMsg("退单失败,没找到该订单号!");
				}		
			}else
			{
				dbLog.setAction_message("退单,失败!已过退单时间,订单编号为:"+StringUtils.convertString(paramsMap.get("order_id"))+","+"用户id为:"+StringUtils.convertString(paramsMap.get("user_id")));
				dbLog.setIs_ok(1);
				msg.setSuccess(false);
				msg.setMsg("退单失败,已过退单时间！");
			}
		}
		dbLogService.insertLog(dbLog);
		return msg;
	}
	
//	/* 
//     * 将时间转换为时间戳
//     */    
//    private static Date strToDate(String d) throws ParseException{
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date StrDate = sdf.parse(d);	
//		//Date endDate	 = sdf.parse(end_time);
//        return StrDate;
//    }
	
	 /** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
          
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  
}
