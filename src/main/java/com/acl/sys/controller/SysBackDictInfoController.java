package com.acl.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;


import com.acl.sys.service.SysBackDictInfoService;
import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;
@Controller
@RequestMapping("/sys")
public class SysBackDictInfoController extends CoreBaseController {
    
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
    @RequestMapping("/dictInfo")
	public String dictInfo(){
		return "sys/sys_dict_info";
	}


	@Autowired
	private SysBackDictInfoService sysBackDictInfoService;
	/**
	 * 查询字典信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/queryBackDictInfo")
	public Object queryBackDictInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		String sortString = "dict_code.asc";
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(sortString));
		//查询字典信息
		PageList<?> list = (PageList<?>)sysBackDictInfoService.queryBackDictInfo(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;
	}

	/**
	 * 添加字典信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/insBackDictInfo")
	public Object insertBackDictInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{		
			//添加字典信息
			sysBackDictInfoService.insertBackDictInfo(paramsMap);
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
	 * 修改字典信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/updBackDictInfo")
	public Object updateBackDictInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			//更新字典信息
			sysBackDictInfoService.updateBackDictInfo(paramsMap);
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
	 * 删除字典信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/delBackDictInfo")
	public Object deleteBackDictInfo(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			//删除字典信息
			sysBackDictInfoService.deleteBackDictInfo(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("删除失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * 字典名称重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/dictValidate")
	public Object dictValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackDictInfoService.dictValidate(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
			msg.setMsg("名称和编码未重复");
		}else{			
			msg.setSuccess(false);
			msg.setMsg("名称和编码重复");
		}
		return msg;
	}
	/**
	 * 根据编码查询字典信息
	 * @return
	 */
	@RequestMapping("/dictCodeIdValidate")
	@RequiresAuthentication
    @RequiresPermissions(value = { "/sys/dictInfo" })
	@ResponseBody
	public Object dictCodeIdValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackDictInfoService.queryDictByCodeId(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
			msg.setMsg("编码和id未重复");
		}else{			
			msg.setSuccess(false);
			msg.setMsg("编码和id重复");
		}
		return msg;
	}
	
	/**
	 * 根据编码查询字典信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
   //@RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/getDict")
	public Object getDict(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackDictInfoService.queryDictByCodeId(paramsMap);		
		return list;
	}
	
	/**
	 * 查询字典类型
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
   //@RequiresPermissions(value = { "/sys/dictInfo" })
	@RequestMapping("/getDictDesc")
	public Object getDictDesc(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackDictInfoService.queryDictDesc(paramsMap);		
		return list;
	}
}
