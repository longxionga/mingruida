package com.acl.goods.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsParamService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
import com.itextpdf.text.List;
import com.sun.javafx.collections.MappingChange.Map;

/**
 * className:GoodsParamController
 * author:wangli
 * createDate:2017年4月25日 上午9:09:00
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsParamController extends CoreBaseController {

    @Autowired
    private GoodsParamService goodsParamService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsParam"})
    @RequestMapping("/goodsParam")
    public String GoodsParam() {
        return "goods/goods_param";
    }


	/**
	 * 查询商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/goods/goodsParam" })
	@RequestMapping("/queryGoodsParam")
	public Object queryGoodsParam(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
		//查询用户信息
		PageList<?> list = (PageList<?>)goodsParamService.queryGoodsParam(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}


	/**
	 * 修改商品属性信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/goods/goodsParam" })
	@RequestMapping("/updGoodsParam")
	public Object updGoodsParam(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			goodsParamService.updateGoodsParam(paramsMap);
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
	@RequiresPermissions(value = { "/goods/goodsParam" })
	@RequestMapping("/insGoodsParam")
	public Object insGoodsParam(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			//第三步操作商品参数

            goodsParamService.insertGoodsParam(paramsMap);
			msg.setMsg("新增成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("新增失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}


/*	*//**
	 * 验证同一商品的商品参数是否重复
	 * @return
	 *//*
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/goods/goodsParam" })
	@RequestMapping("/paramValidate")
	public Object paramValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = goodsParamService.paramValidate(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}

	*//**
	 * 获取系统中商品编码和商品名称信息
	 * @return
	 *//*
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryGoodsName")
	public Object queryGoodsName(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = goodsParamService.queryGoodsName(paramsMap);
		return list;
	}*/
}
