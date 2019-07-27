package com.acl.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.acl.pojo.LoginSession;
import com.acl.sys.service.ISysIndexService;
import com.acl.sys.service.SysUserInfoService;
import com.acl.utils.util.FormatDateUtil;
import com.acl.utils.util.StringUtils;
import com.anchol.common.component.session.SessionProvider;

/**
 * className:ShiroRealm author:huangs createDate:2016年8月22日 上午11:13:51
 * vsersion:3.0 department:安创乐科技 description:权限控制认证及验证
 */
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private ISysIndexService sysIndexService;
	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String currentUserId = (String) super.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		List<Map<String, Object>> list = new ArrayList<>();
		list = sysIndexService.queryUserRoleMenuForShiro(currentUserId);
		if (list.size() == 0) {
			throw new IncorrectCredentialsException("该帐号未被授权！");
		}
		roleList.add(list.get(0).get("role_id").toString());
		for (Map<String, Object> map : list) {
			permissionList.add(map.get("menu_url").toString());
		}
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		ShiroToken token = (ShiroToken) authcToken;
		HashMap<String, Object> loginMap = token.getLoginMap();
		List<Map<String, Object>> userList = sysIndexService.queryloginInfo(loginMap);
		if (userList.isEmpty()) {
			throw new LockedAccountException("系统内查到该账号信息数量不为1&帐号状态异常，请联系系统管理员");
		}
		LoginSession loginSession = new LoginSession(userList.get(0));
		if (20 < StringUtils.checkInt(loginSession.getError_count())) {
			// 短信验证
			String authCodeTime = StringUtils.checkString(userList.get(0).get("auth_code"));
			long timeA = StringUtils.checkLong(authCodeTime.split(" ")[1]);
			long timeNo = System.currentTimeMillis();
			if (timeNo - timeA > 200000) {
				// 200秒， 3分20秒
				throw new ExcessiveAttemptsException("短信验证码输入超时&短信验证码输入超时");
			}
			int au = StringUtils.checkInt(authCodeTime.split(" ")[0]);
			int tc = StringUtils.checkInt(loginMap.get("web_code"));
			if (au == 0 || tc == 0 || au != tc) {
				throw new ExcessiveAttemptsException("短信验证码错误&短信验证码错误");
			}
		}
		if ("".equals(loginSession.getRole_id()) // 无角色
				|| "".equals(loginSession.getDept_id())// 无单位
				|| 0 == loginSession.getUser_use() // 被禁用
				|| 0 == loginSession.getRole_use()// 角色被禁用
				|| 0 == loginSession.getDept_use()) {// 单位被禁用
			//加入监控打印信息
			String mes = "角色: "+loginSession.getRole_name()+
					"单位: "+loginSession.getDept_name()+
					"用户: "+loginSession.getUser_use()=="1"?"启用":"禁用"+
					"角色: "+loginSession.getRole_use()=="1"?"启用":"禁用"+
					"单位: "+loginSession.getDept_use()=="1"?"启用":"禁用";
			System.out.println(mes);
			throw new LockedAccountException(mes+"&帐号状态异常，请联系系统管理员");
		}
		if (!new String(token.getPassword()).equals(new String(StringUtils.checkString(userList.get(0).get("user_password")).toCharArray()))) {
			loginSession.setError_count(loginSession.getError_count() + 1);
			HashMap<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("user_id", loginSession.getUser_id());
			paramsMap.put("error_count", loginSession.getError_count());
			// if (10<loginSession.getError_count()) {
			// paramsMap.put("is_use", "0");//超过十次锁定用户
			// }
			sysUserInfoService.updateUserInfo(paramsMap);
			String mes = loginSession.getUser_nick_name()+"第"+loginSession.getError_count()+"次登录密码验证失败";
			throw new ExcessiveAttemptsException(mes+"&请检查帐号和密码是否正确#"+loginSession.getError_count());
		}
		try {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(),
					StringUtils.checkString(loginSession.getUser_nick_name()));
			// 加入当前loginsession中
			List<Map<String, Object>> parentDeptInfos = sysIndexService.queryParentDeptIds(loginSession.getDept_id());
			if (!parentDeptInfos.isEmpty()) {
				for (Map<String, Object> deptInfo : parentDeptInfos) {
					if (3==StringUtils.checkInt(deptInfo.get("dept_type"))) {
						loginSession.setSettle_id(StringUtils.checkString(deptInfo.get("dept_id")));
					}
					if (4==StringUtils.checkInt(deptInfo.get("dept_type"))) {
						loginSession.setAgent_id(StringUtils.checkString(deptInfo.get("dept_id")));
					}
				}
			}
			loginSession.setCurrent_ip(StringUtils.checkString(loginMap.get("current_ip")));
			loginSession.setCurrent_date(FormatDateUtil.currentDateTime());
			SessionProvider session = token.getSession();
			session.setAttribute("userSession", loginSession);
			// 登录成功ip 时间 写入数据库
			HashMap<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("user_id", loginSession.getUser_id());
			paramsMap.put("user_ip", loginSession.getCurrent_ip());
			paramsMap.put("login_date", loginSession.getCurrent_date());
			paramsMap.put("error_count", "0");
			sysUserInfoService.updateUserInfo(paramsMap);
			return authcInfo;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new AuthenticationException(t.getMessage()+"&验证失败,请联系系统管理员");
		}
	}

}