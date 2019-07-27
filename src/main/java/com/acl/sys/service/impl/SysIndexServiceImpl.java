package com.acl.sys.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acl.sys.dao.ISysIndexDao;
import com.acl.sys.service.ISysIndexService;

/**
 * className:SysIndexServiceImpl author:huangs createDate:2016年8月18日 下午3:05:05
 * vsersion:3.0 department:安创乐科技 description:
 */
@Service
@Transactional
public class SysIndexServiceImpl implements ISysIndexService {

	@Autowired
	private ISysIndexDao sysIndexDao;

	@Override
	public List<Map<String, Object>> queryloginInfo(HashMap<String, Object> paramsMap) {
		return sysIndexDao.queryLoginInfo(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryUserRole(HashMap<String, Object> paramsMap) {
		return sysIndexDao.queryUserRole(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryUserRoleByView(HashMap<String, Object> paramsMap) {
		return sysIndexDao.queryUserRoleByView(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryMenuRoleByView(HashMap<String, Object> paramsMap) {
		return sysIndexDao.queryMenuRoleByView(paramsMap);
	}

	@Override
	// @Cacheable(value = CacheConstants.CACHE_MY_MENU_USER_ID, key =
	// "#map['USER_ID']")
	public List<Map<String, Object>> getUserMenu(Map<String, Object> map) {

		Map<String, Object> userLogin = new HashMap<String, Object>();
		// 查询根菜单
		List<Map<String, Object>> RESULTmenuList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> menuList = this.queryAuthorization(map);

		// 查询菜单权限
		for (int i = 0; i < menuList.size(); i++) {
			userLogin.put("MENU_ID", menuList.get(i).get("MENU_ID"));

			// 子菜单数据
			Map<String, Object> TMP = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			TMP.put("PID", menuList.get(i).get("MENU_ID").toString());
			TMP.put("USER_ID", map.get("USER_ID").toString());

			list = this.queryAuthorization(TMP);

			Map<String, Object> TMP1 = new HashMap<String, Object>();
			TMP1.put("MENU_NAME", menuList.get(i).get("MENU_NAME").toString());
			TMP1.put("URL", menuList.get(i).get("URL").toString());
			// TMP1.put("MENU_ICON",
			// menuList.get(i).get("MENU_ICON").toString());

			TMP1.put("CHILD_MENU", list);

			RESULTmenuList.add(TMP1);
		}
		return RESULTmenuList;
	}

	public List<Map<String, Object>> queryAuthorization(Map<String, Object> map) {
		return sysIndexDao.queryAuthorization(map);
	}

	@Override
	public List<String> queryUserRoleForShiro(String currentUserId) {
		return sysIndexDao.queryUserRoleForShiro(currentUserId);
	}

	@Override
	public List<Map<String, Object>> queryUserRoleMenuForShiro(String currentUserId) {
		return sysIndexDao.queryUserRoleMenuForShiro(currentUserId);
	}

	@Override
	public List<String> queryMenuRoleForShiro(String roleId) {
		return sysIndexDao.queryMenuRoleForShiro(roleId);
	}

	@Override
	public int countFrontUser(HashMap<String, Object> paramsMap) {
		return sysIndexDao.countFrontUser(paramsMap);
	}

	@Override
	public BigDecimal sumFrontUserWallet(HashMap<String, Object> paramsMap) {
		return sysIndexDao.sumFrontUserWallet(paramsMap);
	}

	@Override
	public BigDecimal sumFrontUserBrokerage(HashMap<String, Object> paramsMap) {
		return sysIndexDao.sumFrontUserBrokerage(paramsMap);
	}

	@Override
	public BigDecimal sumFrontCZ(HashMap<String, Object> paramsMap) {
		return sysIndexDao.sumFrontCZ(paramsMap);
	}

	@Override
	public BigDecimal sumFrontTX(HashMap<String, Object> paramsMap) {
		return sysIndexDao.sumFrontTX(paramsMap);
	}

	@Override
	public List<Map<String, Object>> countFrontUserMonth(HashMap<String, Object> paramsMap) {
		return sysIndexDao.countFrontUserMonth(paramsMap);
	}

	@Override
	public List<Map<String, Object>> countFrontUserBrokerageMonth(HashMap<String, Object> paramsMap) {
		return sysIndexDao.countFrontUserBrokerageMonth(paramsMap);
	}

	@Override
	public List<Map<String, Object>> queryParentDeptIds(String dept_id) {
		return sysIndexDao.queryParentDeptIds(dept_id);
	}

}
