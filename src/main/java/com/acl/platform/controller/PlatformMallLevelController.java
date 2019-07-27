package com.acl.platform.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;
import com.acl.platform.service.IPlatformMallLevelService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:PlatformMallLevelController
 *author:wangli
 *createDate:2017年4月24日 下午2:25:43
 *vsersion:3.0
 *department:安创乐科技
 *description:等级表管理
 */
@Controller
@RequestMapping("/platform")
public class PlatformMallLevelController extends CoreBaseController {

	@Autowired
	private IPlatformMallLevelService platformMallLevelService;
	
	@RequiresAuthentication
    @RequiresPermissions(value = { "/platform/mallLevel" })
    @RequestMapping("/mallLevel")
    public String goodsCategory(@Session(create = false) SessionProvider session,ModelMap modelMap){
	return "/platform/platform_mall_level";
    }
	
	/**
	 * 查询等级信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/mallLevel" })
	@RequestMapping("/queryMallLevel")
	public Object queryMallLevel(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){		
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(""));		
		//查询用户信息
		PageList<?> list = (PageList<?>)platformMallLevelService.queryMallLevel(paramsMap, pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}
	
	/**
	 * 插入等级信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/mallLevel" })
	@RequestMapping("/insMallLevel")
	public Object insMallLevel(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			platformMallLevelService.insertMallLevel(paramsMap);
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
	 * 修改等级信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/mallLevel" })
	@RequestMapping("/updMallLevel")
	public Object updMallLevel(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			platformMallLevelService.updateMallLevel(paramsMap);
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
	 * 验证等级名称是否重复
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/platform/mallLevel" })
	@RequestMapping("/levelValidate")
	public Object levelValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = platformMallLevelService.levelValidate(paramsMap);		
		if(list.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}
}
