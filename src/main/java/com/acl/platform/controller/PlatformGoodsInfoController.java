package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformGoodsInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformGoodsInfoController
 *author:wangli
 *createDate:2017年2月9日 下午6:25:11
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("platform")
public class PlatformGoodsInfoController extends CoreBaseController {
	@Autowired
	private IPlatformGoodsInfoService platformGoodsInfoService;
	@RequiresAuthentication
	@RequiresPermissions(value = {"/platform/goodsInfo" })
	@RequestMapping("/goodsInfo")
	public String goodsInfo(){
	return "platform/platform_goods_info";
	}
	/**
	 * 查询商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/goodsInfo" })
	@RequestMapping("/queryGoodsInfo")
	public Object querygoodsInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));		
		//查询用户信息
		PageList<?> list = (PageList<?>)platformGoodsInfoService.queryGoodsInfo(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	
	/**
	 * 修改商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/goodsInfo" })
	@RequestMapping("/updGoodsInfo")
	public Object updGoodsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			platformGoodsInfoService.updateGoodsInfo(paramsMap);
			msg.setMsg("更新成功");
			msg.setSuccess(true);
		    }catch(Exception e){
			msg.setMsg("更新失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	
	
	/**
	 * 添加商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/goodsInfo" })
	@RequestMapping("/insGoodsInfo")
	public Object insgoodsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			String [] goods_pros=paramsMap.get("goods_pros").toString().split(",");
			if(goods_pros.length>0){
				for(int i=0;i<goods_pros.length;i++){
					HashMap<String,Object> map=new HashMap<String,Object>();
					map.put("goods_id", UUIDGenerator.generate());
					map.put("goods_pro", goods_pros[i]);
					map.put("goods_name", paramsMap.get("goods_name"));
					map.put("goods_ename","");
					map.put("goods_code", paramsMap.get("goods_code"));
					map.put("goods_type", StringUtils.convertString(paramsMap.get("goods_type")));
					map.put("goods_desc", paramsMap.get("goods_desc"));
					
					map.put("is_use", paramsMap.get("is_use"));
					if(paramsMap.get("is_order")==null || paramsMap.get("is_order")==""){
						map.put("is_order", i+1);	
					}else {
						map.put("is_order", paramsMap.get("is_order"));	
					}
					
					platformGoodsInfoService.insertGoodsInfo(map);
				}
			}
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
	 * 删除商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/goodsInfo" })
	@RequestMapping("/delGoodsInfo")
	public Object delgoodsInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			platformGoodsInfoService.deleteGoodsInfo(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("删除失败");
			msg.setSuccess(false);

		}
		return msg;
	}
	/**
	 * 获取系统中商品编码和商品名称信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
   //@RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/getGoodsName")
	public Object getGoodsName(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = platformGoodsInfoService.queryGoodsName(paramsMap);		
		return list;
	}
}
