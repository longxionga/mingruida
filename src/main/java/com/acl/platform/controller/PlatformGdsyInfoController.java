package com.acl.platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
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


import com.acl.platform.service.IPlatformGdsyInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

import net.sf.json.JSONArray;

/**
 * 
 *className:PlatformGdsyInfoController
 *author:wangzhe
 *createDate:2016年8月12日 上午11:08:40
 *version:3.0
 *department:安创乐科技
 *description:模式二信息管理
 */

@Controller
@RequestMapping("/platform")
public class PlatformGdsyInfoController extends CoreBaseController{

	@Autowired
	private IPlatformGdsyInfoService platformGdsyInfoService;	
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/gdsyInfo")
	public String userInfo(){
		return "platform/platform_gdsy_info";
	}
	
	
	/**
	 * 查询模式二信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/queryGdsyInfo")
	public Object queryGzpInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(orderByCreateDate));
		if(paramsMap.get("sort")!=null){
			paramsMap.put("order_type", paramsMap.get("sort").toString()+" "+paramsMap.get("order").toString());
			pageBounds = new PageBounds(page,rows,Order.formString(paramsMap.get("sort").toString()+"."+paramsMap.get("order").toString()));
		}		
		//查询用户信息
		PageList<?> list = (PageList<?>)platformGdsyInfoService.queryGdsyInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/*
	 * 查询模式二与部门信息关系
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/queryGdsyDpetInfo")
	public Object queryGdsyDeptInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		
		if(paramsMap != null && paramsMap.containsKey("gdsy_id")){
			paramsMap.put("gdsy_id", paramsMap.get("gdsy_id").toString().trim());
		}
		List<Map<String,Object>> list = platformGdsyInfoService.queryGdsyDeptInfo(paramsMap);
		String jsonStr = JSONArray.fromObject(list).toString();         
	    JSONArray ja = JSONArray.fromObject(jsonStr);
	    return ja;
	}
	
	/**
	 * 新增模式一商品操作
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/insGdsyInfo")
	public Object insertGzpInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
		// trim登陆名前后空白
		if(paramsMap != null && paramsMap.containsKey("gdsy_name")){
			paramsMap.put("gdsy_name", paramsMap.get("gdsy_name").toString().trim());
		}
		 paramsMap.put("gdsy_id", UUIDGenerator.generate());
		 paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		 platformGdsyInfoService.insertGdsyInfo(paramsMap);
		 updateGdsyGoodsRedis(paramsMap,"gdsy");
		 updateGdsyGoodsRedis(paramsMap,"dwqq");
		 msg.setMsg("新增成功");
		 msg.setSuccess(true);
		}catch(Exception e){
		 msg.setMsg("插入失败");
		 msg.setSuccess(false);
		 e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 更新模式一信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/updGdsyInfo")
	public Object updateGdsyInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		try 
		{
			platformGdsyInfoService.updateGdsyInfo(paramsMap);			
			updateGdsyGoodsRedis(paramsMap,"gdsy");
			updateGdsyGoodsRedis(paramsMap,"dwqq");
			updateGdsyDeptRedis(paramsMap,"gdsy");
			updateGdsyDeptRedis(paramsMap,"dwqq");
			strGdsyGoodsRedis(paramsMap,"gdsy","2");
			strGdsyGoodsRedis(paramsMap,"dwqq","3");
			//更新redis
			//redisTemplate.delete("");
			//加入新的redis
			//redisTemplate.opsForValue().set("", "");
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
	 * 登录名称重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/gdsyGoodsValidate")
	public Object gdsyGoodsValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白
		if(paramsMap != null && paramsMap.containsKey("name")){
			paramsMap.put("gdsy_name", paramsMap.get("name").toString().trim());
		}
		List<Map<String,Object>> list = platformGdsyInfoService.gdsyGoodsValidate(paramsMap);
		if(list.size()==0){
			 msg.setSuccess(true);
			
		 }else{
			 msg.setSuccess(false);
			 msg.setMsg("商品名重复");
		 }
		 return msg;
	}
	
	
	/**
	 * 初始化模式二商品信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/initGdsyGoodsInfo")
	public Object initGzpInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		updateGdsyDeptRedis(paramsMap,"gdsy");
		updateGdsyDeptRedis(paramsMap,"dwqq");
		updateGdsyGoodsRedis(paramsMap,"gdsy");
		updateGdsyGoodsRedis(paramsMap,"dwqq");
		//strGdsyGoodsRedis(paramsMap);
		strGdsyGoodsRedis(paramsMap,"gdsy","2");
		strGdsyGoodsRedis(paramsMap,"dwqq","3");
		msg.setSuccess(true);
		msg.setMsg("商品初始化成功!");
		return msg;
	}
	
	/**
	 * 保存模式二商品与部门的信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gdsyInfo" })
	@RequestMapping("/saveGdsyGoodsInfo")
	public Object saveGdsyGoodsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白		
		try {
			String dept_id = paramsMap.get("dept_id").toString();
			if (dept_id != "") 
			{
				//先删除在添加
				paramsMap.put("gdsy_id", paramsMap.get("gdsy_id").toString().trim());
				platformGdsyInfoService.deleteGdsyDept(paramsMap);
				
				if (paramsMap != null && paramsMap.containsKey("dept_id")) {
					// paramsMap.put("dept_id",
					// paramsMap.get("dept_id").toString().trim());
					String[] dept_ida = dept_id.split(",");
					for (int i = 0; i < dept_ida.length; i++) {
						paramsMap.put("sys_id", UUIDGenerator.generate());
						paramsMap.put("dept_id", dept_ida[i].toString());
						paramsMap.put("gdsy_id", paramsMap.get("gdsy_id").toString().trim());
						// 插入模式二中间表
						platformGdsyInfoService.insertDeptGdsyInfo(paramsMap);
					}
					//加入到redis
					//updateGdsyDeptRedis(paramsMap);
					updateGdsyDeptRedis(paramsMap,"gdsy");
					updateGdsyDeptRedis(paramsMap,"dwqq");
					//strGdsyGoodsRedis(paramsMap);
					strGdsyGoodsRedis(paramsMap,"gdsy","2");
					strGdsyGoodsRedis(paramsMap,"dwqq","3");
					RemoveGdsyRedisInfo(paramsMap);
				}
				// platformGdsyInfoService.updateGdsyInfo(paramsMap);
				msg.setMsg("保存成功");
				msg.setSuccess(true);
		}else
		{
			//不选择代理商的情况下，将删除所有的可保存乘公共
			paramsMap.put("gdsy_id", paramsMap.get("gdsy_id").toString().trim());
			platformGdsyInfoService.deleteGdsyDept(paramsMap);
			//并更新redis
			updateGdsyDeptRedis(paramsMap,"gdsy");
			updateGdsyDeptRedis(paramsMap,"dwqq");
			//strGdsyGoodsRedis(paramsMap);
			strGdsyGoodsRedis(paramsMap,"gdsy","2");
			strGdsyGoodsRedis(paramsMap,"dwqq","3");
			RemoveGdsyRedisInfo(paramsMap);

			msg.setMsg("保存成功");
			msg.setSuccess(true);
			//msg.setMsg("请选择部门");
			//msg.setSuccess(false);
		}
		} catch (Exception e) {
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}

		return msg;
	}
	
	
	private void updateGdsyDeptRedis(HashMap<String,Object> paramsMap,String goodstype)
	{
		// 更新操作
		// HashMap<String,Object> paramsMapGoods =null;
//		
//		Set keyss = redisTemplate.keys("gdsy_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
//		Iterator ts1 = keyss.iterator();
//		while (ts1.hasNext()) {
//			Object obj1 = ts1.next();			
//			//redisTemplate.delete(obj1.toString());
//			if (obj1.toString().indexOf("goods_") == -1)
//			{
//				redisTemplate.delete(obj1.toString());
//				//System.out.println(obj1.toString());
//			}
//		}		
		goodsClearRedis(goodstype);
		if(goodstype.equals("gdsy"))
		{
			paramsMap.put("gdsy_rule", "02");
		}
		if(goodstype.equals("dwqq"))
		{
			paramsMap.put("gdsy_rule", "03");
		}
		List<Map<String, Object>> list = platformGdsyInfoService.queryGdsyRelationDeptInfo(paramsMap);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// redisTemplate.opsForValue().get(key);
				redisTemplate.opsForValue().set(
						""+goodstype+"_" + list.get(i).get("dept_id").toString() + "_" + list.get(i).get("gdsy_id").toString(),
						list.get(i).get("gdsy_id").toString());
			}
		}
	}
	
	private void updateGdsyGoodsRedis(HashMap<String,Object> paramsMap,String goodstype)
	{
		//更新操作
		 //HashMap<String,Object> paramsMapGoods =null;	
		
		Set keys = redisTemplate.keys(""+goodstype+"_goods_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();
			if (obj1.toString().indexOf("goods_") >-1){
				redisTemplate.delete(obj1.toString());
			}
			
		}
		
		if(goodstype.equals("gdsy"))
		{
			paramsMap.put("gdsy_rule", "02");
		}
		if(goodstype.equals("dwqq"))
		{
			paramsMap.put("gdsy_rule", "03");
		}
		
		 List<Map<String,Object>> list = platformGdsyInfoService.queryGdsyGoodsInfo(paramsMap);		 
		 if(list.size()>0)
		 {
			 for(int i=0;i<list.size();i++)
			 {			 
				 //redisTemplate.opsForValue().get(key);				 
				 redisTemplate.opsForValue().set(""+goodstype+"_goods_"+list.get(i).get("gdsy_id").toString()+"", JSONObject.toJSONString(list.get(i)));
			 }			
		 }		
	}
	
	private void strGdsyGoodsRedis(HashMap<String,Object> paramsMap,String goodstype,String type)
	{
		Set keys = redisTemplate.keys("*_"+goodstype+"");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();			
			redisTemplate.delete(obj1.toString());			
		}
		if(type.equals("2"))
		{
			paramsMap.put("gdsy_rule", "02");
		}
		if(type.equals("3"))
		{
			paramsMap.put("gdsy_rule", "03");
		}
		//更新操作	
		//关系查找 通过关系列出有多少代理商 
		//为最外层基础，有多少代理商循环多少次
		 List<Map<String,Object>> listrelation = platformGdsyInfoService.selectGdsyRelation(paramsMap);
		 if(listrelation.size()>0)
		 {		
			for (int r = 0; r < listrelation.size(); r++) {
				StringBuffer sb = new StringBuffer();
				paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
				sb.append("{'type':'"+type+"', ");
				sb.append(" 'list':[");
				//商品分组
				List<Map<String, Object>> listgroup = platformGdsyInfoService.selectGdsyGroup(paramsMap);
				if (listgroup.size() > 0) {
					
					//分组循环，为第二层基础(包括同类商品下的另一款商品)
					for (int i = 0; i < listgroup.size(); i++) 
					{
						sb.append("{");
						sb.append("'type':{");						
						sb.append("'code':" +"'"+ listgroup.get(i).get("gdsy_number").toString() +"'"+ ",");
						sb.append("'name':" +"'"+ listgroup.get(i).get("gdsy_name").toString() +"'"+",");
						sb.append("'alias':" +"'"+ listgroup.get(i).get("gdsy_alias").toString() +"'"+",");
						sb.append("'detail':" +"'"+ listgroup.get(i).get("goods_detail").toString() + "'"+",");
						sb.append("'img_1':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"1",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_2':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"2",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_3':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"3",SystemConfig.imgUrl) + "'"+"");
						sb.append("},");
						sb.append("'list':[");
						if(listgroup.get(i).get("gdsy_code").toString().split(",").length>0)
						{
							paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
							//解析同组商品多个不同产品以逗号分隔
							for(int intc=0;intc<listgroup.get(i).get("gdsy_code").toString().split(",").length;intc++)
							{
								paramsMap.put("gdsy_code", listgroup.get(i).get("gdsy_code").toString().split(",")[intc].toString());
								
								//通过代理商和分组的code查询对应的商品								
								List<Map<String, Object>> listgoods = platformGdsyInfoService.selectGdsyGoodsInfo(paramsMap);								
								
								//if (listgoods.size() > 0) {
									if(listgoods.get(0) != null)
									{
									//循环挂接商品信息，同类型产品都在一个LIST下
									for (int g = 0; g < listgoods.size(); g++) {
										sb.append("{");
										if (listgoods.get(g) != null) {
											// sb.append("'gdsy_id':"+"'"+listgoods.get(g).get("gdsy_id").toString()+"'"+",");
											sb.append("'gdsy_name':" + "'" + listgoods.get(g).get("gdsy_name").toString() + "'"	+ ",");
											sb.append("'gdsy_alias':" + "'" + listgoods.get(g).get("gdsy_alias").toString() + "'"	+ ",");
											sb.append("'gdsy_type':" + "'" + listgoods.get(g).get("gdsy_type").toString() + "'" + ",");
											sb.append("'gdsy_number':" + "'" + listgoods.get(g).get("gdsy_number").toString() + "'" + ",");
											sb.append("'gdsy_code':" + "'" + listgoods.get(g).get("gdsy_code").toString() + "'" + ",");
											sb.append("'gdsy_ratio':" + "'" + listgoods.get(g).get("gdsy_ratio").toString() + "'" + ",");
											sb.append("'gdsy_time':" + strSplit(listgoods.get(g).get("condata").toString()) + "");
											//sb.append("'gdsy_poundage':" + "'"+ listgoods.get(g).get("gdsy_poundage").toString() + "'" + ",");
											sb.append("'gdsy_money':" + "'" + listgoods.get(g).get("gdsy_money").toString()	+ "'" + ",");
											sb.append("'gdsy_unit':" + "'" + listgoods.get(g).get("gdsy_unit").toString() + "'"	+ ",");
											// sb.append("'is_use':"+"'"+listgoods.get(g).get("is_use").toString()+"'"+",");
											// sb.append("'create_date':"+"'"+listgoods.get(g).get("create_date").toString()+"'"+",");
											// sb.append("'gdsy_order':"+"'"+listgoods.get(g).get("gdsy_order").toString()+"'"+",");
											sb.append("'gdsy_lot':" + "'" + listgoods.get(g).get("gdsy_lot").toString() + "'"+ ",");
											//sb.append("'gdsy_rule':" + "'" + listgoods.get(g).get("gdsy_rule").toString() + "'"+ ",");
											//sb.append("'gdsy_unit':" + "'" + listgoods.get(g).get("gdsy_unit").toString() + "'"+ ",");
											sb.append("'gdsy_buy_no_time':" + "'"+ listgoods.get(g).get("gdsy_buy_no_time").toString() + "'" + ",");
											sb.append("'gdsy_buy_no_date':" + "'"+ listgoods.get(g).get("gdsy_buy_no_date").toString() + "'" + "");
										}										
										sb.append("},");
									}
									//sb.append(sb.substring(0,(sb.length() - 1)));									
								}
								//}							
							}
						}	
						sb.append("]");						
						if(i==listgroup.size()-1)
						{
							sb.append("}");
						}
						else
						{
							sb.append("},");
						}
						//sb.append("},");
					}					
				}
				sb.append("]");
				sb.append("}");
				//循环写入redis 
				String str=null;
				str= sb.toString();
				str = str.replaceAll("'", "\"");				
				//写入redis
				redisTemplate.opsForValue().set(listrelation.get(r).get("dept_id").toString()+"_"+goodstype+"", str.replaceAll(",]", "]"));
				//sb.append(JSONObject.toJSONString(sb.toString())+"=======+" + listrelation.get(r).get("dept_id").toString() + "+=======");
				//System.out.print(str.replaceAll(",]", "]"));
			}			
		 }
		 //System.out.print(sb.toString());
		//redisTemplate.opsForValue().get(key);				 
		 //redisTemplate.delete("gdsy_goods_"+list.get(i).get("gzp_id").toString());
		//更新redis
	}
	
	private String strSplit(String str)
	{
		//"5b7887bd096040b89983c17ef2562b4b_120_70.00,27e89a65dea349aba981eb357000fdba_300_70.00"
		StringBuffer sb = new StringBuffer();
		//String retStr="";
		String[] strArr=str.split(",");
		if(strArr.length>0)
		{
			sb.append("[");
			for(int i=0;i<strArr.length;i++)
			{
				if(strArr[i].toString().split("_").length>0)
				{				
						sb.append("{'gdsy_id':'"+ strArr[i].toString().split("_")[0].toString() +"',");
						sb.append("'gdsy_time':'"+ strArr[i].toString().split("_")[1].toString() +"',");
						sb.append("'gdsy_ratio':'"+ strArr[i].toString().split("_")[2].toString() +"',");
						//sb.append("'gdsy_rule':" + "'" + strArr[i].toString().split("_")[3].toString() +"',");
						sb.append("'gdsy_poundage':'"+ strArr[i].toString().split("_")[4].toString() +"'}");//"{'gdsy_id:''"+ strArr[0].toString() +"','gdsy_time:''"+ strArr[1].toString()  +"','gdsy_ratio:''"+ strArr[2].toString() +"'}";	
				}
				
				if(i==strArr.length-1)
				{
					sb.append("],");
				}
				else
				{
					sb.append(",");
				}
			}			
			//retStr="{'gdsy_id:''"+ strArr[0].toString() +"','gdsy_time:''"+ strArr[1].toString()  +"','gdsy_ratio:''"+ strArr[2].toString() +"'}";
		}
		return sb.toString();
	}
	
	// 移除不存在的关系的代理商Redis
	private void RemoveGdsyRedisInfo(HashMap<String, Object> paramsMap) {
		
		
		// 更新操作
		// HashMap<String,Object> paramsMapGoods =null;

		List<Map<String, Object>> listdept = platformGdsyInfoService.queryGdsyDeptNoexist(paramsMap);
		if (listdept.size() > 0) {
			for (int i = 0; i < listdept.size(); i++) {
				redisTemplate.delete(listdept.get(i).get("dept_id").toString() + "_gdsy");
				//redisTemplate.delete(listdept.get(i).get("dept_id").toString() + "_gdsy");
			}
		}
		// redisTemplate.delete(listdept.get(i).get("dept_id").toString()+"_gzp");
		// 更新redis
	}
	

	//修改不可用的状态
	//1.删除对应的代理商与商品关系
	//2.刷新关系的代理商Redis
	/*private void gdsyStateChangeInfo(HashMap<String, Object> paramsMap) {
		List<Map<String, Object>> listdept = platformGdsyInfoService.queryGdsyDeptNoexist(paramsMap);
		if (listdept.size() > 0) {
			for (int i = 0; i < listdept.size(); i++) {
				redisTemplate.delete(listdept.get(i).get("dept_id").toString() + "_gdsy");
			}
		}
		// redisTemplate.delete(listdept.get(i).get("dept_id").toString()+"_gzp");
		// 更新redis
	}*/
	
	
	private void goodsClearRedis(String goodstype)
	{
		
		Set keyss = redisTemplate.keys(""+goodstype+"_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator ts1 = keyss.iterator();
		while (ts1.hasNext()) {
			Object obj1 = ts1.next();			
			//redisTemplate.delete(obj1.toString());
			if (obj1.toString().indexOf("goods_") == -1)
			{
				redisTemplate.delete(obj1.toString());
				//System.out.println(obj1.toString());
			}
		}	
	}
	
	/**
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	private String ImgInfo(String str,String type,String url) {
		// redisTemplate.delete(listdept.get(i).get("dept_id").toString()+"_gzp");
		StringBuffer sb = new StringBuffer();
		if(!str.equals(""))
		{
			String[] arrstr = str.split(",");

			if (arrstr.length > 0) {
				for (int i = 0; i < arrstr.length; i++) {
					if (arrstr[i].split("#")[1].equals(type)) {
						sb.append(arrstr[i].split("#")[0].toString() + ",");
					}
				}
		}
		}
		if(sb.length()>0)
		{
			return sb.toString().substring(0,sb.length()-1);
		}else
		{
			return sb.toString();
		}
				
	}
	

}


