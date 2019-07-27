package com.acl.component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import com.anchol.common.component.session.SessionFactory;
import com.anchol.common.component.session.SessionProvider;
import com.anchol.common.framework.spring.SpringContextHolder;
/**
 * className:ShiroRealm author:huangs createDate:2016年8月22日 上午11:13:51
 * vsersion:3.0 department:安创乐科技 description:自定义请求监控策略 处理json请求时问题
 */
public class AjaxUserFilter extends UserFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		SessionFactory sessionFactory = SpringContextHolder.getBean(SessionFactory.class);
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest req = WebUtils.toHttp(request);
		HttpServletResponse res = WebUtils.toHttp(response);
		SessionProvider session = sessionFactory.getSession(httpRequest, httpResponse, false);
		if (null != session) {
			long sessionLastTime = session.getLastAccessedTime();
			long currrentTime = System.currentTimeMillis();
			if (currrentTime - sessionLastTime > 30*60*1000) {
				res.sendError(403);
			}
			/*LoginSession loginSession = (LoginSession) session.getAttribute("userSession");
			if (null != loginSession) {
				
//				ShiroToken token = loginSession.getShiroToken();
				ShiroToken token = new ShiroToken(
						StringUtils.checkString(loginSession.getUser_name()),
						MD5Utils.MD5(StringUtils.checkString(paramsMap.get("passWord"))),
						true, 
						loginSession,
						NetUtils.getIpAddress(httpRequest),
						FormatDateUtil.currentDateTime()
						);

				Subject currentUser = SecurityUtils.getSubject();
				if (currentUser.isRemembered()) {
					currentUser.login(token);
				}
			}*/
		}
		String xmlHttpRequest = req.getHeader("X-Requested-With");
		if (xmlHttpRequest != null) {
			if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
				res.sendError(403);
				return false;
			}
		}
		return super.onAccessDenied(request, response);
	}
}