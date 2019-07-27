package com.acl.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acl.sys.service.SysBackRoleService;
import com.acl.utils.util.UUIDGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acl.core.CoreBaseController;



import com.acl.utils.paginator.domain.Order;
import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;
import com.acl.utils.tree.GridTree;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

import net.sf.json.JSONArray;


/**
 *className:SysBackRoleController
 *author:wangli
 *createDate:2016年8月13日 上午9:47:43
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Controller
@RequestMapping("/sys")
public class SysBackRoleController extends CoreBaseController{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private SysBackRoleService sysBackRoleService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/backRole")
	public String backRole(){
		return "/sys/sys_back_role";
	}
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/queryBackRole")
	public Object queryBackRole(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam(defaultValue = "0", required = false) Integer page,
			@RequestParam(defaultValue = "10", required = false) Integer rows,
			@RequestParam HashMap<String,Object> paramsMap){
		PageBounds pageBounds = new PageBounds(page,rows,Order.formString(orderByCreateDate));
		//查询角色信息
		PageList<?> list = (PageList<?>)sysBackRoleService.queryBackRole(paramsMap,pageBounds);
		Object json = this.getJsonMap(list);
		return json;

	}

	/**
	 * 添加角色信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/insBackRole")
	public Object insertBackRole(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{					
			paramsMap.put("role_id", UUIDGenerator.generate());
			paramsMap.put("create_date",sdf.format(new java.util.Date()));
			sysBackRoleService.insertBackRole(paramsMap);
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
	 * 修改角色信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/updBackRole")
	public Object updateBackRole(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			sysBackRoleService.updateBackRole(paramsMap);
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
	 * 删除菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/delBackRole")
	public Object deleteBackRole(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{	
			paramsMap.put("role_id", paramsMap.get("role_id"));
			sysBackRoleService.deleteBackRole(paramsMap);
			msg.setMsg("删除成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("删除失败");
			msg.setSuccess(false);
		}
		return msg;
	}
	
	/**
	 * 查询人员分配信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = {"/sys/backRole" })
	@RequestMapping("/queryTreeUser")
	public Object queryTreeUser(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		/* 1、查询t_back_role表中role_name
		 * 2、查询t_back_dict_info表中的dict_name=role_name 的dict_id
		 * 3、查询t_back_dept_info中dept_type=dict_id的dept_id
		 * 4、查询t_back_back_user 中dept_id=dept_id
		 */
		List<Map<String,Object>> list=sysBackRoleService.queryTreeUser(paramsMap);
		return list;
	}
	
	/**
	 * 菜单分配
	 * @return
	 */

	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/saveMenu")
	public Object saveMenu(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{				
			/*
			 * 逻辑说明，先删除t_back_role_menu角色菜单中间表中的所有关于这个角色的信息
			 * 然后添加更新后的角色信息
			 */
			sysBackRoleService.updateBackRoleMenu(paramsMap );
			msg.setMsg("保存成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 人员分配
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/saveUser")
	public Object saveUser(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{				
			/*
			 * 逻辑说明，先删除t_back_role_menu角色菜单中间表中的所有关于这个角色的信息
			 * 然后添加更新后的角色信息
			 */
			sysBackRoleService.updateBackRoleUser(paramsMap);
			msg.setMsg("保存成功");
			msg.setSuccess(true);
		}catch(Exception e){
			msg.setMsg("保存失败");
			msg.setSuccess(false);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 角色名称重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/roleValidate")
	public Object roleValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackRoleService.roleValidate(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}

	
	
	
	/**
	 * 查询所有菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequestMapping("/queryMenuForRole")
	public Object queryMenuForRole(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		String role_id=paramsMap.get("role_id").toString();
		List<GridTree> list=new ArrayList<GridTree>();
		GridTree tree=new GridTree();
		tree.setMenu_id("-1");
		tree.setMenu_name("系统管理");
		tree.setChildren(initTree("-1",role_id));
		list.add(tree);
		return list;
	}

	/**
	 * 初始化树
	 * @param deptId
	 * @return
	 */
	private List<GridTree> initTree(String menu_id,String role_id) {
		List<GridTree> list = new ArrayList<GridTree>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu_id", menu_id);
		//map.put("role_id", role_id);
		List<Map<String,Object>> menus=sysBackRoleService.queryMenuForRole(map);
		if(menus!=null){
			for(Map<String, Object> u : menus){
				GridTree tree = new GridTree();
				tree.setMenu_id(u.get("MENU_ID").toString());
				tree.setMenu_name(u.get("MENU_NAME").toString());
				tree.setMenu_url(u.get("MENU_URL").toString());
				tree.setIs_use(u.get("IS_USE").toString());		    			    	
				tree.setChildren(initTree(u.get("MENU_ID").toString(),role_id));
				if(Integer.valueOf(u.get("checks").toString())>0){
					tree.setChecked(true);
				}else{
					tree.setChecked(false);
				}
				list.add(tree);
			}
		}
		return list;
	}
	/*
	 * 查询角色和菜单信息
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/queryRoleMenu")
	public Object queryRoleMenu(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){		
		if(paramsMap != null && paramsMap.containsKey("role_id")){
			paramsMap.put("role_id", paramsMap.get("role_id").toString().trim());
		}
		List<Map<String,Object>> list = sysBackRoleService.queryRoleMenu(paramsMap);
		String jsonStr = JSONArray.fromObject(list).toString();         
	    JSONArray ja = JSONArray.fromObject(jsonStr);
	    return ja;
	}
	
	/*
	 * 查询角色和人员信息
	 * @return
	 **/
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backRole" })
	@RequestMapping("/queryRoleUser")
	public Object queryRoleUser(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){		
		if(paramsMap != null && paramsMap.containsKey("role_id")){
			paramsMap.put("role_id", paramsMap.get("role_id").toString().trim());
		}
		List<Map<String,Object>> list = sysBackRoleService.queryRoleUser(paramsMap);
		String jsonStr = JSONArray.fromObject(list).toString();         
	    JSONArray ja = JSONArray.fromObject(jsonStr);
	    return ja;
	}
	
}
