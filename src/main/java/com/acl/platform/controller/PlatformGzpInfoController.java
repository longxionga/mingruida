package com.acl.platform.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.redis.RedisUtils;
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


import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

import net.sf.json.JSONArray;

import com.acl.platform.service.IPlatformGzpInfoService;

/**
 * 
 *className:PlatformGzpInfoController
 *author:wangzhe
 *createDate:2016年8月12日 上午10:14:58
 *version:3.0
 *department:安创乐科技
 *description:模式一信息管理
 */

@Controller
@RequestMapping("/platform")
public class PlatformGzpInfoController extends CoreBaseController{
	
	@Autowired
	private  IPlatformGzpInfoService  platformGzpInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private RedisUtils redisUtils ;


	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/gzpInfo")
	public String userInfo(){
		return "platform/platform_gzp_info";
	}	
	
	/**
	 * 查询模式一信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/queryGzpInfo")
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
		PageList<?> list = (PageList<?>)platformGzpInfoService.queryGzpInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/*
	 * 查询模式一与部门信息关系
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/queryGzpDpetInfo")
	public Object queryGzpDeptInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		
		if(paramsMap != null && paramsMap.containsKey("gzp_id")){
			paramsMap.put("gzp_id", paramsMap.get("gzp_id").toString().trim());
		}
		List<Map<String,Object>> list = platformGzpInfoService.queryGzpDeptInfo(paramsMap);
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
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/insGzpInfo")
	public Object insertGzpInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
		// trim登陆名前后空白
		if(paramsMap != null && paramsMap.containsKey("gzp_name")){
			paramsMap.put("gzp_name", paramsMap.get("gzp_name").toString().trim());
		}
		 paramsMap.put("gzp_id", UUIDGenerator.generate());
		 paramsMap.put("create_date", FormatDateUtil.getStringDateYYMMDDHHMMSS(new Date()));
		 platformGzpInfoService.insertGzpInfo(paramsMap);
		//更新Redis
		 updateGzpGoodsRedis(paramsMap);
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
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/updGzpInfo")
	public Object updateGzpInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try {
			platformGzpInfoService.updateGzpInfo(paramsMap);
			// 更新Redis
			updateGzpGoodsRedis(paramsMap);
			updateGzpDeptRedis(paramsMap);
			strGzpGoodsRedis(paramsMap);
			strGzpGoodsDeptRedis(paramsMap);
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
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/gzpGoodsValidate")
	public Object gzpGoodsValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白
				if(paramsMap != null && paramsMap.containsKey("name")){
					paramsMap.put("gzp_name", paramsMap.get("name").toString().trim());
				}
		 List<Map<String,Object>> list = platformGzpInfoService.gzpGoodsValidate(paramsMap);
		 if(list.size()==0){
			 msg.setSuccess(true);
			
		 }else{
			 msg.setSuccess(false);
			 msg.setMsg("商品名重复");
		 }
		 return msg;
	}
	
	
	/**
	 * 初始化模式一商品信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/initGzpGoodsInfo")
	public Object initGzpInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		updateGzpDeptRedis(paramsMap);
		updateGzpGoodsRedis(paramsMap);
		strGzpGoodsRedis(paramsMap);
		strGzpGoodsDeptRedis(paramsMap);
		msg.setSuccess(true);
		msg.setMsg("商品初始化成功!");
		return msg;
	}


	/**
	 * 初始化模拟盘商品信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/initSimGoodsInfo")
	public Object initSimGoodsInfo(@RequestParam HashMap<String, Object> paramsMap, HttpServletRequest request) {
		strSimGzpGoodsRedis(paramsMap);
		updateSimGoodsIdRedis(paramsMap);
		updateSimGzpGoodsRedis(paramsMap);
		msg.setSuccess(true);
		msg.setMsg("模拟盘初始化成功!");
		return msg;
	}
	
	/**
	 * 保存模式二商品与部门的信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/gzpInfo" })
	@RequestMapping("/saveGzpGoodsInfo")
	public Object saveGzpGoodsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		// trim登陆名前后空白	
		try {
			String dept_id = paramsMap.get("dept_id").toString();
			if (dept_id != "") 
			{
				//先删除在添加
				paramsMap.put("gzp_id", paramsMap.get("gzp_id").toString().trim());
				platformGzpInfoService.deleteGzpDept(paramsMap);
				
				if (paramsMap != null && paramsMap.containsKey("dept_id")) {
					// paramsMap.put("dept_id",
					// paramsMap.get("dept_id").toString().trim());
					String[] dept_ida = dept_id.split(",");
					for (int i = 0; i < dept_ida.length; i++) {
						paramsMap.put("sys_id", UUIDGenerator.generate());
						paramsMap.put("dept_id", dept_ida[i].toString());
						paramsMap.put("gzp_id", paramsMap.get("gzp_id").toString().trim());
						// 插入模式二中间表
						platformGzpInfoService.insertDeptGzpInfo(paramsMap);
					}
					// paramsMap.put("dept_id",
					// paramsMap.get("dept_id").toString().trim());
					// 加入到redis
					// redisTemplate.opsForValue().set("dept_id", "josn");
					updateGzpDeptRedis(paramsMap);
					strGzpGoodsRedis(paramsMap);
					strGzpGoodsDeptRedis(paramsMap);
					RemoveGzpRedisInfo(paramsMap);
				}
				// platformGdsyInfoService.updateGdsyInfo(paramsMap);
				msg.setMsg("保存成功");
				msg.setSuccess(true);
		}else
		{
			//当一个代理商都没有选择的时候,删除当前商品ID所有的信息
			paramsMap.put("gzp_id", paramsMap.get("gzp_id").toString().trim());

			platformGzpInfoService.deleteGzpDept(paramsMap);
			updateGzpDeptRedis(paramsMap);
			strGzpGoodsRedis(paramsMap);
			strGzpGoodsDeptRedis(paramsMap);
			RemoveGzpRedisInfo(paramsMap);
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
	
	private void updateGzpDeptRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
		 //HashMap<String,Object> paramsMapGoods =null;
		
		Set keys = redisTemplate.keys("gzp_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();
			if (obj1.toString().indexOf("goods_") == -1)
			{
				redisTemplate.delete(obj1.toString());
				//System.out.println(obj1.toString());
			}					
		}		
		 List<Map<String,Object>> list = platformGzpInfoService.queryGzpRelationDeptInfo(paramsMap);		 
		 if(list.size()>0)
		 {
			 for(int i=0;i<list.size();i++)
			 {
				 //redisTemplate.opsForValue().get(key);
				 if(null != list.get(i).get("dept_id"))
				 {
					 redisTemplate.opsForValue().set("gzp_"+list.get(i).get("dept_id").toString()+"_"+list.get(i).get("gzp_id").toString(),list.get(i).get("gzp_id").toString());
				 }				 			
			 }			
		 }
		//更新redis
	}

	private void updateSimGoodsIdRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
		//HashMap<String,Object> paramsMapGoods =null;

		Set keys = redisTemplate.keys("gzp_4_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();
			if (obj1.toString().indexOf("goods_") == -1)
			{
				redisTemplate.delete(obj1.toString());
				//System.out.println(obj1.toString());
			}
		}
		paramsMap.put("db_type","sim");
		List<Map<String,Object>> list = platformGzpInfoService.queryGzpRelationDeptInfo(paramsMap);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				redisUtils.opsForValue().set("gzp_4_"+list.get(i).get("gzp_id").toString(),list.get(i).get("gzp_id").toString());
			}
		}
		//更新redis
	}

	private void updateSimGzpGoodsRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
		//HashMap<String,Object> paramsMapGoods =null;

				Set keys = redisTemplate.keys("gzp_goods_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
				Iterator t1 = keys.iterator();
				while (t1.hasNext()) {
					Object obj1 = t1.next();
					if (obj1.toString().indexOf("goods_") == -1)
					{
						redisTemplate.delete(obj1.toString());
						//System.out.println(obj1.toString());
					}
				}
		paramsMap.put("db_type","sim");
		List<Map<String,Object>> list = platformGzpInfoService.queryGzpGoodsInfo(paramsMap);
		if(list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				//redisTemplate.opsForValue().get(key);
				redisUtils.opsForValue().set("gzp_goods_"+list.get(i).get("gzp_id").toString()+"", JSONObject.toJSONString(list.get(i)));
			}
		}
		//更新redis
	}
	
	private void updateGzpGoodsRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
		 //HashMap<String,Object> paramsMapGoods =null;	
		
		Set keyss = redisTemplate.keys("gzp_goods_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator ts1 = keyss.iterator();
		while (ts1.hasNext()) {
			Object obj1 = ts1.next();
			if (obj1.toString().indexOf("goods_") > -1) {
				redisTemplate.delete(obj1.toString());
			}
		}	
		
		 List<Map<String,Object>> list = platformGzpInfoService.queryGzpGoodsInfo(paramsMap);		 
		 if(list.size()>0)
		 {
			 for(int i=0;i<list.size();i++)
			 {			 
				 //redisTemplate.opsForValue().get(key);				 
				 redisTemplate.opsForValue().set("gzp_goods_"+list.get(i).get("gzp_id").toString()+"", JSONObject.toJSONString(list.get(i)));
			 }			
		 }
		//更新redis
	}
	
	/*
	private void gzpGoodsRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
		 //HashMap<String,Object> paramsMapGoods =null;	
		 List<Map<String,Object>> list = platformGzpInfoService.queryGzpGoodsInfo(paramsMap);		 
		 if(list.size()>0)
		 {
			 for(int i=0;i<list.size();i++)
			 {			 
				 //redisTemplate.opsForValue().get(key);				 
				 redisTemplate.delete("gzp_goods_"+list.get(i).get("gzp_id").toString());
			 }			
		 }
		//更新redis
	}
	*/
	
	private void strGzpGoodsRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作

		Set keys = redisTemplate.keys("*_gzp");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();			
			redisTemplate.delete(obj1.toString());			
		}
		
		 //HashMap<String,Object> paramsMapGoods =null;	
		 List<Map<String,Object>> listrelation = platformGzpInfoService.selectGzpRelation(paramsMap);
		 if(listrelation.size()>0)
		 {
			
			for (int r = 0; r < listrelation.size(); r++) {
				StringBuffer sb = new StringBuffer();
				paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
				sb.append("{'type':'1', ");
				sb.append(" 'list':[");
				List<Map<String, Object>> listgroup = platformGzpInfoService.selectGzpGroup(paramsMap);
				if (listgroup.size() > 0) {
					
					for (int i = 0; i < listgroup.size(); i++) 
					{
						sb.append("{");
						sb.append("'type':{");
						paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
						paramsMap.put("gzp_type", listgroup.get(i).get("gzp_type").toString());
						List<Map<String, Object>> listgoods = platformGzpInfoService.selectGzpGoodsInfo(paramsMap);
						sb.append("'code':" +"'"+ listgroup.get(i).get("gzp_number").toString() +"'"+ ",");
						sb.append("'name':" +"'"+ listgroup.get(i).get("gzp_name").toString() + "'"+ ",");
						sb.append("'alias':" +"'"+ listgroup.get(i).get("gzp_alias").toString() + "'"+",");
						sb.append("'is_zc':" +"'"+ listgroup.get(i).get("is_zc").toString() + "'"+",");
						sb.append("'detail':" +"'"+ listgroup.get(i).get("goods_detail").toString() + "'"+",");
						sb.append("'img_1':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"1",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_2':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"2",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_3':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"3",SystemConfig.imgUrl) + "'"+"");
						sb.append("},");
						sb.append("'list':[");
						if (listgoods.size() > 0) {
							for (int g = 0; g < listgoods.size(); g++) {

								sb.append("{");
								sb.append("'gzp_id':"+"'"+listgoods.get(g).get("gzp_id").toString()+"'"+",");
								sb.append("'gzp_name':"+"'"+listgoods.get(g).get("gzp_name").toString()+"'"+",");
								sb.append("'gzp_alias':"+"'"+listgoods.get(g).get("gzp_alias").toString()+"'"+",");
								sb.append("'gzp_code':"+"'"+listgoods.get(g).get("gzp_code").toString()+"'"+",");
								sb.append("'gzp_number':"+"'"+listgoods.get(g).get("gzp_number").toString()+"'"+",");
								sb.append("'gzp_order':"+"'"+listgoods.get(g).get("gzp_order").toString()+"'"+",");
								sb.append("'gzp_unit':"+"'"+listgoods.get(g).get("gzp_unit").toString()+"'"+",");
								sb.append("'gzp_type':"+"'"+listgoods.get(g).get("gzp_type").toString()+"'"+",");
								sb.append("'is_use':"+"'"+listgoods.get(g).get("is_use").toString()+"'"+",");
								sb.append("'create_date':"+"'"+listgoods.get(g).get("create_date").toString()+"'"+",");
								sb.append("'gzp_money':"+"'"+listgoods.get(g).get("gzp_money").toString()+"'"+",");
								sb.append("'gzp_poundage':"+"'"+listgoods.get(g).get("gzp_poundage").toString()+"'"+",");
								sb.append("'gzp_profit':"+"'"+listgoods.get(g).get("gzp_profit").toString()+"'"+",");
								sb.append("'gzp_lot':"+"'"+listgoods.get(g).get("gzp_lot").toString()+"'"+",");
								sb.append("'gzp_buy_no_time':"+"'"+listgoods.get(g).get("gzp_buy_no_time").toString()+"'"+",");
								sb.append("'gzp_buy_no_date':"+"'"+listgoods.get(g).get("gzp_buy_no_date").toString()+"'"+"");
								if(g==listgoods.size()-1)
								{
									sb.append("}");
								}
								else
								{
									sb.append("},");
								}
							}
							//sb.append(sb.substring(0,(sb.length() - 1)));
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
				redisTemplate.opsForValue().set(listrelation.get(r).get("dept_id").toString()+"_gzp", str);
				//sb.append("=======+" + listrelation.get(r).get("dept_id").toString() + "+=======+"+JSONObject.toJSONString(sb.toString())+"");
				//System.out.print(sb.toString());
				//System.out.print(sb.toString());
			}
			
			
		 }

	}


	private void strSimGzpGoodsRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作
//		Set keys = redisTemplate.keys("4_gzp");// 列出所有的key，查找特定的key如：redis.keys("foo")
//		Iterator t1 = keys.iterator();
//		while (t1.hasNext()) {
//			Object obj1 = t1.next();
//			redisTemplate.delete(obj1.toString());
//		}

		//HashMap<String,Object> paramsMapGoods =null;
		//List<Map<String,Object>> listrelation = platformGzpInfoService.selectGzpRelation(paramsMap);
		//if(listrelation.size()>0)
		//{

			//for (int r = 0; r < listrelation.size(); r++) {
				StringBuffer sb = new StringBuffer();
				//paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
				sb.append("{'type':'1', ");
				sb.append(" 'list':[");
				List<Map<String, Object>> listgroup = platformGzpInfoService.selectGzpGroup(paramsMap);
				if (listgroup.size() > 0) {

					for (int i = 0; i < listgroup.size(); i++)
					{
						sb.append("{");
						sb.append("'type':{");
						//paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
						paramsMap.put("gzp_type", listgroup.get(i).get("gzp_type").toString());
						List<Map<String, Object>> listgoods = platformGzpInfoService.selectGzpGoodsInfo(paramsMap);
						sb.append("'code':" +"'"+ listgroup.get(i).get("gzp_number").toString() +"'"+ ",");
						sb.append("'name':" +"'"+ listgroup.get(i).get("gzp_name").toString() + "'"+ ",");
						sb.append("'alias':" +"'"+ listgroup.get(i).get("gzp_alias").toString() + "'"+",");
						sb.append("'is_zc':" +"'"+ listgroup.get(i).get("is_zc").toString() + "'"+",");
						sb.append("'detail':" +"'"+ listgroup.get(i).get("goods_detail").toString() + "'"+",");
						sb.append("'img_1':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"1",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_2':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"2",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_3':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"3",SystemConfig.imgUrl) + "'"+"");
						sb.append("},");
						sb.append("'list':[");
						if (listgoods.size() > 0) {
							for (int g = 0; g < listgoods.size(); g++) {

								sb.append("{");
								sb.append("'gzp_id':"+"'"+listgoods.get(g).get("gzp_id").toString()+"'"+",");
								sb.append("'gzp_name':"+"'"+listgoods.get(g).get("gzp_name").toString()+"'"+",");
								sb.append("'gzp_alias':"+"'"+listgoods.get(g).get("gzp_alias").toString()+"'"+",");
								sb.append("'gzp_code':"+"'"+listgoods.get(g).get("gzp_code").toString()+"'"+",");
								sb.append("'gzp_number':"+"'"+listgoods.get(g).get("gzp_number").toString()+"'"+",");
								sb.append("'gzp_order':"+"'"+listgoods.get(g).get("gzp_order").toString()+"'"+",");
								sb.append("'gzp_unit':"+"'"+listgoods.get(g).get("gzp_unit").toString()+"'"+",");
								sb.append("'gzp_type':"+"'"+listgoods.get(g).get("gzp_type").toString()+"'"+",");
								sb.append("'is_use':"+"'"+listgoods.get(g).get("is_use").toString()+"'"+",");
								sb.append("'create_date':"+"'"+listgoods.get(g).get("create_date").toString()+"'"+",");
								sb.append("'gzp_money':"+"'"+listgoods.get(g).get("gzp_money").toString()+"'"+",");
								sb.append("'gzp_poundage':"+"'"+listgoods.get(g).get("gzp_poundage").toString()+"'"+",");
								sb.append("'gzp_profit':"+"'"+listgoods.get(g).get("gzp_profit").toString()+"'"+",");
								sb.append("'gzp_lot':"+"'"+listgoods.get(g).get("gzp_lot").toString()+"'"+",");
								sb.append("'gzp_buy_no_time':"+"'"+listgoods.get(g).get("gzp_buy_no_time").toString()+"'"+",");
								sb.append("'gzp_buy_no_date':"+"'"+listgoods.get(g).get("gzp_buy_no_date").toString()+"'"+"");
								if(g==listgoods.size()-1)
								{
									sb.append("}");
								}
								else
								{
									sb.append("},");
								}
							}
							//sb.append(sb.substring(0,(sb.length() - 1)));
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
				//写入模拟盘数据
				redisUtils.opsForValue().set("4_gzp",str);
			//}
	}

	private void strGzpGoodsDeptRedis(HashMap<String,Object> paramsMap)
	{
		//更新操作

		Set keys = redisTemplate.keys("*_gzp_*");// 列出所有的key，查找特定的key如：redis.keys("foo")
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object obj1 = t1.next();
			redisTemplate.delete(obj1.toString());
		}

		//HashMap<String,Object> paramsMapGoods =null;
		List<Map<String,Object>> listrelation = platformGzpInfoService.selectGzpRelation(paramsMap);
		if(listrelation.size()>0)
		{

			for (int r = 0; r < listrelation.size(); r++) {
				StringBuffer sb = new StringBuffer();
				paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
				sb.append("{'type':'1', ");
				sb.append(" 'list':[");
				List<Map<String, Object>> listgroup = platformGzpInfoService.selectGzpDeptGroup(paramsMap);
				if (listgroup.size() > 0) {

					for (int i = 0; i < listgroup.size(); i++)
					{
						sb.append("{");
						sb.append("'type':{");
						paramsMap.put("dept_id", listrelation.get(r).get("dept_id").toString());
						paramsMap.put("gzp_type", listgroup.get(i).get("gzp_type").toString());
						List<Map<String, Object>> listgoods = platformGzpInfoService.selectGzpDeptGoodsInfo(paramsMap);
						sb.append("'code':" +"'"+ listgroup.get(i).get("gzp_number").toString() +"'"+ ",");
						sb.append("'name':" +"'"+ listgroup.get(i).get("gzp_name").toString() + "'"+ ",");
						sb.append("'alias':" +"'"+ listgroup.get(i).get("gzp_alias").toString() + "'"+",");
						sb.append("'is_zc':" +"'"+ listgroup.get(i).get("is_zc").toString() + "'"+",");
						sb.append("'is_use':" +"'"+ isAvailable(listgroup.get(i).get("is_use").toString())+ "'"+",");
						sb.append("'detail':" +"'"+ listgroup.get(i).get("goods_detail").toString() + "'"+",");
						sb.append("'img_1':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"1",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_2':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"2",SystemConfig.imgUrl) + "'"+",");
						sb.append("'img_3':" +"'"+ ImgInfo(listgroup.get(i).get("goods_img").toString(),"3",SystemConfig.imgUrl) + "'"+"");
						sb.append("},");
						sb.append("'list':[");
						if (listgoods.size() > 0) {
							for (int g = 0; g < listgoods.size(); g++) {

								sb.append("{");
								sb.append("'gzp_id':"+"'"+listgoods.get(g).get("gzp_id").toString()+"'"+",");
								sb.append("'gzp_name':"+"'"+listgoods.get(g).get("gzp_name").toString()+"'"+",");
								sb.append("'gzp_alias':"+"'"+listgoods.get(g).get("gzp_alias").toString()+"'"+",");
								sb.append("'gzp_code':"+"'"+listgoods.get(g).get("gzp_code").toString()+"'"+",");
								sb.append("'gzp_number':"+"'"+listgoods.get(g).get("gzp_number").toString()+"'"+",");
								sb.append("'gzp_order':"+"'"+listgoods.get(g).get("gzp_order").toString()+"'"+",");
								sb.append("'gzp_unit':"+"'"+listgoods.get(g).get("gzp_unit").toString()+"'"+",");
								sb.append("'gzp_type':"+"'"+listgoods.get(g).get("gzp_type").toString()+"'"+",");
								sb.append("'is_use':"+"'"+listgoods.get(g).get("is_use").toString()+"'"+",");
								sb.append("'create_date':"+"'"+listgoods.get(g).get("create_date").toString()+"'"+",");
								sb.append("'gzp_money':"+"'"+listgoods.get(g).get("gzp_money").toString()+"'"+",");
								sb.append("'gzp_poundage':"+"'"+listgoods.get(g).get("gzp_poundage").toString()+"'"+",");
								sb.append("'gzp_profit':"+"'"+listgoods.get(g).get("gzp_profit").toString()+"'"+",");
								sb.append("'gzp_lot':"+"'"+listgoods.get(g).get("gzp_lot").toString()+"'"+",");
								sb.append("'gzp_buy_no_time':"+"'"+listgoods.get(g).get("gzp_buy_no_time").toString()+"'"+",");
								sb.append("'gzp_buy_no_date':"+"'"+listgoods.get(g).get("gzp_buy_no_date").toString()+"'"+"");
								if(g==listgoods.size()-1)
								{
									sb.append("}");
								}
								else
								{
									sb.append("},");
								}
							}
							//sb.append(sb.substring(0,(sb.length() - 1)));
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
				redisTemplate.opsForValue().set(listrelation.get(r).get("dept_id").toString()+"_gzp_goods", str);
				//sb.append("=======+" + listrelation.get(r).get("dept_id").toString() + "+=======+"+JSONObject.toJSONString(sb.toString())+"");
				//System.out.print(sb.toString());
				//System.out.print(sb.toString());
			}


		}
		//System.out.print(sb.toString());
		//redisTemplate.opsForValue().get(key);
		//redisTemplate.delete("gdsy_goods_"+list.get(i).get("gzp_id").toString());
		//更新redis
	}
	
	//移除不存在的关系的代理商Redis
	private void RemoveGzpRedisInfo(HashMap<String,Object> paramsMap)
	{
		List<Map<String,Object>> listdept = platformGzpInfoService.queryGzpDeptNoexist(paramsMap);
		if(listdept.size()>0)
		{
			for(int i=0;i<listdept.size();i++)
			{
				redisTemplate.delete(listdept.get(i).get("dept_id").toString()+"_gzp");
			}
		}
		//redisTemplate.delete(listdept.get(i).get("dept_id").toString()+"_gzp");
		//更新redis
	}


	private String isAvailable(String struse)
	{
		String isStr="0";
		String[] arrstr = struse.split(",");
		if(arrstr.length>0)
		{
			for(int i=0; i<arrstr.length;i++)
			{
				if(arrstr[i].equals("1"))
				{
					return "1";
				}
			}
		}else
			{
				if(arrstr[0].equals("1"))
				{
					return  "1";
				}
			}
		return isStr;
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
