package com.acl.sys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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


import com.acl.sys.service.SysBackMenuService;
import com.acl.utils.tree.GridTree;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.component.session.support.Session;

/**
 *className:SysBackMenuController
 *author:wangli
 *createDate:2016年8月11日 上午9:07:18
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */


@Controller
@RequestMapping("/sys")
public class SysBackMenuController extends CoreBaseController{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private SysBackMenuService sysBackMenuService;

	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/backMenu")
	public String dictInfo(){
		return "sys/sys_back_menu";
	}
	/**
	 * 查询所有菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/queryBackMenu")
	public Object queryBackMenu(){
		List<GridTree> list=new ArrayList<GridTree>();
		GridTree tree=new GridTree();
		tree.setMenu_id("-1");
		tree.setMenu_name("菜单管理");
		tree.setMenu_parent_id("");
		tree.setIs_use("");
		tree.setIs_leaf("0");
		tree.setMenu_index("0");
		tree.setChildren(initTree("-1"));
		list.add(tree);
		return list;
	}

	/**
	 * 初始化树
	 * @param deptId
	 * @return
	 */
	private List<GridTree> initTree(String menu_id) {
		List<GridTree> list = new ArrayList<GridTree>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu_id", menu_id);
		List<Map<String,Object>> menus=sysBackMenuService.queryTreeBackMenu(map);
		if(menus!=null){
			for(Map<String, Object> u : menus){
				GridTree tree = new GridTree();
				tree.setMenu_id(StringUtils.convertString(u.get("MENU_ID")));
				tree.setMenu_name(StringUtils.convertString(u.get("MENU_NAME")));
				tree.setMenu_icon(StringUtils.convertString(u.get("MENU_ICON")));
				tree.setMenu_url(StringUtils.convertString(u.get("MENU_URL")));
				tree.setMenu_parent_id(StringUtils.convertString(u.get("MENU_PARENT_ID")));
				tree.setIs_use(StringUtils.convertString(u.get("IS_USE")));
				tree.setIs_leaf(StringUtils.convertString(u.get("IS_LEAF")));
				tree.setMenu_index(StringUtils.convertString(u.get("MENU_INDEX")));
				tree.setChildren(initTree(StringUtils.convertString(u.get("MENU_ID"))));
				list.add(tree);
			}
		}
		return list;
	}


	/**
	 * 添加菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/insBackMenu")
	public Object insertBackMenu(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			paramsMap.put("menu_id", UUIDGenerator.generate());
			paramsMap.put("create_date",sdf.format(new java.util.Date()));
			sysBackMenuService.insertBackMenu(paramsMap);
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
	 * 修改菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/updBackMenu")
	public Object updateBackMenu(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		try{
			sysBackMenuService.updateBackMenu(paramsMap);
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
	 * 菜单名称重复性验证
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/menuValidate")
	public Object menuValidate(@RequestParam HashMap<String,Object> paramsMap,HttpServletRequest request){
		List<Map<String,Object>> list = sysBackMenuService.menuValidate(paramsMap);
		if(list.size()==0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
		}
		return msg;
	}

	/**
	 * 查询后台菜单信息
	 * @return
	 */
	@ResponseBody
	@RequiresAuthentication
	@RequiresPermissions(value = { "/sys/backMenu" })
	@RequestMapping("/queryBackMenuInfo")
	public Object queryBackMenuInfo(@Session(create = false) SessionProvider session,HttpServletRequest request,
			@RequestParam HashMap<String,Object> paramsMap){
		//查询菜单信息
		Object object =sysBackMenuService.queryBackMenuInfo(paramsMap);
		return object;

	}
	
    public String getName(String myFileName){  
        //获取一个50以内的随机值  
        Random ra =new Random();  
        int raNum = ra.nextInt(100);  
        //获取当前系统时间  
        Long nowTime =System.currentTimeMillis();  
        String resultName = nowTime + "" + raNum + myFileName;  
        return resultName;  
          
    }  

}
