package com.acl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acl.sys.dao.SysBackRoleDao;
import com.acl.sys.service.SysBackRoleService;
import com.acl.utils.util.UUIDGenerator;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.component.MongodbBaseDao;

import com.acl.utils.paginator.domain.PageBounds;
import com.acl.utils.paginator.domain.PageList;

/**
 *className:SysBackRoleServicceImpl
 *author:wangli
 *createDate:2016年8月12日 下午5:01:48
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
@Service
@Transactional
public class SysBackRoleServicceImpl implements SysBackRoleService {

	@Autowired
	private SysBackRoleDao sysBackRoleDao;
	@Autowired
	private MongodbBaseDao<T> mongodbBaseDao;
	@Override
	public PageList<?> queryBackRole(HashMap<String, Object> paramsMap,
			PageBounds pageBounds) {
		return sysBackRoleDao.queryBackRole(paramsMap, pageBounds);
	}  


	@Override
	public void insertBackRole(HashMap<String, Object> paramsMap) {
		sysBackRoleDao.insertBackRole(paramsMap);
		
	}

	@Override
	public void updateBackRole(HashMap<String, Object> paramsMap) {
		sysBackRoleDao.updateBackRole(paramsMap);
	}

	@Override
	public void deleteBackRole(HashMap<String, Object> paramsMap) {
		sysBackRoleDao.deleteBackRole(paramsMap);
	}

	@Override
	public List<Map<String, Object>> roleValidate(HashMap<String, Object> paramsMap) {
		return sysBackRoleDao.roleValidate(paramsMap);
	}


	@Override
	public List<Map<String, Object>> queryTreeUser(Map<String, Object> map) {
		return sysBackRoleDao.queryTreeUser(map);
	}
	
	@Override
	public void updateBackRoleMenu(HashMap<String, Object> paramsMap) {
		
		String role_id=paramsMap.get("role_id").toString();
		String menuIds=paramsMap.get("menu_id").toString();
		
		try{
		Map<String,Object> parm = new HashMap<String,Object>();
		
		parm.put("role_id",role_id );
		sysBackRoleDao.deleteBackRoleMenu(parm);
		//mongodbBaseDao.remove(parm, "roleMenu");
		if(menuIds!=null&&!menuIds.equals("")){
			
			String[] ids = menuIds.split(",");
			
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					Map<String,Object> tmp = new HashMap<String,Object>();	
					tmp.put("sys_id", UUIDGenerator.generate());
					tmp.put("role_id",role_id );					
					tmp.put("menu_id", ids[i]);					
					sysBackRoleDao.insertBackRoleMenu(tmp);
					//mongodbBaseDao.save(tmp, "roleMenu");
				}
			}
			
		}}catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public List<Map<String, Object>> queryMenuForRole(Map<String, Object> map) {
		return sysBackRoleDao.queryMenuForRole(map);
	}


	@Override
	public void updateBackRoleUser(HashMap<String, Object> paramsMap) {
		String role_id=paramsMap.get("roleId").toString();
		String userIds=paramsMap.get("userIds").toString();
		
		try{
		Map<String,Object> parm = new HashMap<String,Object>();
		
		parm.put("role_id",role_id );
		sysBackRoleDao.deleteBackRoleUser(parm);
		//mongodbBaseDao.remove(parm, "roleUser");
		if(userIds!=null&&!userIds.equals("")){
			
			String[] ids = userIds.split(",");
			
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					Map<String,Object> tmp = new HashMap<String,Object>();	
					tmp.put("sys_id", UUIDGenerator.generate());
					tmp.put("role_id",role_id );					
					tmp.put("user_id", ids[i]);	
					/*Map<String,Object> user = new HashMap<String,Object>();
					user.put("user_id", ids[i]);
					sysBackRoleDao.deleteBackRoleUserByUser(user);
					mongodbBaseDao.remove(user, "roleUser");*/
					sysBackRoleDao.insertBackRoleUser(tmp);
					//mongodbBaseDao.save(tmp, "roleUser");
				}
			}
			
		}}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	@Override
	public List<Map<String, Object>> queryRoleMenu(HashMap<String, Object> paramsMap) {
		return sysBackRoleDao.queryRoleMenu(paramsMap);
	}
	
	@Override
	public List<Map<String, Object>> queryRoleUser(HashMap<String, Object> paramsMap) {
		return sysBackRoleDao.queryRoleUser(paramsMap);
	}
	
	
}
