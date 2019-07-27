package com.acl.goods.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acl.core.CoreBaseController;
import com.acl.goods.service.GoodsProrateService;

/**
 * className:GoodsProrateController
 * author:wangli
 * createDate:2017年4月26日 下午5:01:06
 * version:3.0
 * department:安创乐科技
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsProrateController extends CoreBaseController {

    @Autowired
    private GoodsProrateService goodsProrateService;


    @RequiresAuthentication
    @RequiresPermissions(value = {"/goods/goodsProrate"})
    @RequestMapping("/goodsProrate")
    public String GoodsProrate() {
        return "goods/goods_prorate";
    }


//	/**
//	 * 查询商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/queryGoodsProrate")
//	public Object queryGoodsProrate(@Session(create = false) SessionProvider session,HttpServletRequest request,
//			@RequestParam(defaultValue = "0", required = false) Integer page,
//			@RequestParam(defaultValue = "10", required = false) Integer rows,
//			@RequestParam HashMap<String,Object> paramsMap){
//		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
//		//查询用户信息
//		PageList<?> list = (PageList<?>)goodsProrateService.queryGoodsProrate(paramsMap, pageBounds);
//		Object json = this.getJsonMap(list);
//		return json;
//
//	}
//
//
//	/**
//	 * 修改商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/updGoodsProrate")
//	public Object updGoodsProrate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsProrateService.updateGoodsProrate(paramsMap);
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
//	 * 添加商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/insGoodsProrate")
//	public Object insGoodsProrate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsProrateService.insertGoodsProrate(paramsMap);
//
//			//第五步操作商品比率
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
//	/**
//	 * 验证同一商品的商品参数是否重复
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = {"/goods/goodsProrate" })
//	@RequestMapping("/prorateValidate")
//	public Object paramValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		List<Map<String,Object>> list = goodsProrateService.prorateValidate(paramsMap);
//		if(list.size()==0){
//			msg.setSuccess(true);
//		}else{
//			msg.setSuccess(false);
//		}
//		return msg;
//	}
//
//	/**
//	 * 获取等级id和名称
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequestMapping("/getMallLevel")
//	public Object getMallLevel(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		List<Map<String,Object>> list = goodsProrateService.getMallLevel(paramsMap);
//		return list;
//	}
//	/**
//	 * 查询商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/queryGoodsProrate")
//	public Object queryGoodsProrate(@Session(create = false) SessionProvider session,HttpServletRequest request,
//			@RequestParam(defaultValue = "0", required = false) Integer page,
//			@RequestParam(defaultValue = "10", required = false) Integer rows,
//			@RequestParam HashMap<String,Object> paramsMap){
//		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));
//		//查询用户信息
//		PageList<?> list = (PageList<?>)goodsProrateService.queryGoodsProrate(paramsMap, pageBounds);
//		Object json = this.getJsonMap(list);
//		return json;
//
//	}
//
//
//	/**
//	 * 修改商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/updGoodsProrate")
//	public Object updGoodsProrate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsProrateService.updateGoodsProrate(paramsMap);
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
//	 * 添加商品提成比率信息
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = { "/goods/goodsProrate" })
//	@RequestMapping("/insGoodsProrate")
//	public Object insGoodsProrate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		try{
//			goodsProrateService.insertGoodsProrate(paramsMap);
//
//			//第五步操作商品比率
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
//	/**
//	 * 验证同一商品的商品参数是否重复
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequiresPermissions(value = {"/goods/goodsProrate" })
//	@RequestMapping("/prorateValidate")
//	public Object prorateValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		List<Map<String,Object>> list = goodsProrateService.prorateValidate(paramsMap);
//		if(list.size()==0){
//			msg.setSuccess(true);
//		}else{
//			msg.setSuccess(false);
//		}
//		return msg;
//	}
//
//	/**
//	 * 获取等级id和名称
//	 * @return
//	 */
//	@ResponseBody
//	@RequiresAuthentication
//	@RequestMapping("/getMallLevel")
//	public Object getMallLevel(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
//		List<Map<String,Object>> list = goodsProrateService.getMallLevel(paramsMap);
//		return list;
//	}

}
