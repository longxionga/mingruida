package com.acl.component;

import java.util.HashMap;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.anchol.common.component.session.SessionProvider;

/**
 * className:ShiroToken author:huangs createDate:2016年8月18日 下午6:00:24
 * vsersion:3.0 department:安创乐科技 description:重写token 令其携带更多业务承载关键点
 */
public class ShiroToken extends UsernamePasswordToken {

	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> loginMap = null;
	private SessionProvider session = null;
	// private String currentIp = "";
	// private Date current_date = new Date();

	public ShiroToken(String loginName, String password, boolean flag, HashMap<String, Object> loginMap,
			SessionProvider session) {
		super(loginName, password, flag);
		this.loginMap = loginMap;
		this.session = session;
	}

	public HashMap<String, Object> getLoginMap() {
		return loginMap;
	}

	public SessionProvider getSession() {
		return session;
	}

}