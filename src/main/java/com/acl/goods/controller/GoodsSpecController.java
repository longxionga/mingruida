package com.acl.goods.controller;

import com.acl.goods.service.GoodsSpecService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acl.core.CoreBaseController;

/**
 * className:GoodsSpecController
 * author:wangli
 * createDate:2017年4月25日 下午7:04:07
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsSpecController extends CoreBaseController {

    @Autowired
    private GoodsSpecService goodsSpecService;

    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsSpec"})
    @RequestMapping("/goodsSpec")
    public String goodsSpec() {
        return "goods/goods_spec";
    }

//	/**
//	 * 查询商品属性信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsSpec" })
//	@RequestMapping("/queryGoodsSpec")
//	public Object queryGoodsSpec(@Session(create = false) SessionProvider session,HttpServletRequest request,
//			@RequestParam(defaultValue = "0", required = false) Integer page,
//			@RequestParam(defaultValue = "10", required = false) Integer rows,
//			@RequestParam HashMap<String,Object> paramsMap){
//		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
//		//查询用户信息
//		PageList<?> list = (PageList<?>)goodsSpecService.queryGoodsSpec(paramsMap, pageBounds);
//		Object json = this.getJsonMap(list);
//		return json;
//
//	}
//
//
//	/**
//	 * 修改商品属性信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsSpec" })
//	@RequestMapping("/updGoodsSpec")
//	public Object updGoodsSpec(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsSpecService.updateGoodsSpec(paramsMap);
//			msg.setMsg("更新成功");
//			msg.setSuccess(true);
//		    }catch(Exception e){
//			msg.setMsg("更新失败");
//			msg.setSuccess(false);
//			e.printStackTrace();
//		}
//		return msg;
//	}
//
//
//	/**
//	 * 添加商品属性信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsSpec" })
//	@RequestMapping("/insGoodsSpec")
//	public Object insGoodsSpec(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsSpecService.insertGoodsSpec(paramsMap);
//			//第四步操作商品规格
//			msg.setMsg("新增成功");
//			msg.setSuccess(true);
//		}catch(Exception e){
//			msg.setMsg("新增失败");
//			msg.setSuccess(false);
//			e.printStackTrace();
//		}
//		return msg;
//	}
//
//
//	/**
//	 * 验证同一商品的商品参数是否重复
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = {"/goods/GoodsSpec" })
//	@RequestMapping("/specValidate")
//	public Object specValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		List<Map<String,Object>> list = goodsSpecService.specValidate(paramsMap);
//		if(list.size()==0){
//			msg.setSuccess(true);
//		}else{
//			msg.setSuccess(false);
//		}
//		return msg;
//	}
}
