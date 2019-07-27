package com.acl.component;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class RequestKeyWordsInterceptor extends HandlerInterceptorAdapter {


	/**
	 * 不用拦截的路径集合
	 */
	private static Set<String> excludeSet;
	
	/*@Autowired
	private SessionFactory sessionFactory;*/
	static {
		excludeSet = new HashSet<>();
		excludeSet.add("/login");
		//		excludeSet.add("/com/");
		//		excludeSet.add("/batch/");
		excludeSet.add("/loginAuth");
		excludeSet.add("/getVerifyCodeText");
		excludeSet.add("/slide");
		excludeSet.add("/static/");
		excludeSet.add("/sys/insCenterInfo");
		excludeSet.add("/sys/updCenterInfo");
		//经纪人更换部门
		excludeSet.add("/platform/updAgentdeptBrokers");
		//经纪人更换上级经纪人
		excludeSet.add("/platform/updBrokerUser");
		excludeSet.add("/platform/queryBrokerUser");
		//用户更换经纪人
		excludeSet.add("/platform/updUserBroker");
		excludeSet.add("/platform/queryBrokers");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean result = true;
		if (hasPassport(request.getContextPath(), request.getRequestURI())) {
			return result;
		}

		Map<String, Object > m = getQueryParams(request);
		System.out.println(m.size());
		if (m.size()==0) {
			return result;
		}else{
			for (Object dataKey : m.values()) {

				if (checkUrl(dataKey.toString().toLowerCase())) {
//					RequestDispatcher rd = request.getRequestDispatcher("/loginOut");
//					rd.forward(request, response);
					result = false;
					//SessionProvider session = sessionFactory.getSession(request, response, false);
					//session.invalidate();
					response.sendError(405);
					break;
				} 

			}
		}
		






		//		String actionString = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/"));
		//		
		//		SessionProvider session = sessionFactory.getSession(request, response, false);
		//		
		//		if (session == null || session.getAttribute(SessionConstans.KEY_SYS_USER_ID) == null) {
		//			result = false;
		//			// ajax请求
		//			if (Servlets.isAjaxRequest(request)) {
		//				// 在响应头设置session状态
		//				Servlets.setTimeoutHeader(response);
		//			} else {
		////				response.sendRedirect(request.getContextPath() + "/doLogin");
		//  			RequestDispatcher rd = request.getRequestDispatcher("/doLogin");
		//  			rd.forward(request, response);
		//			}
		//		} else if (StringUtils.equals(actionString, "/")) {
		//			if (session == null || session.getAttribute(SessionConstans.KEY_SYS_USER_ID) == null) {
		//				RequestDispatcher rd = request.getRequestDispatcher("/doLogin");
		//  			rd.forward(request, response);
		//			} else {
		//				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/desktop.jsp");
		//  			rd.forward(request, response);
		//			}
		//		}

		return result;
	}

	/**
	 * 是否能够通行
	 * 
	 * @param uri
	 * @return
	 */
	private boolean hasPassport(String ctx, String uri) {
		Iterator<String> iter = excludeSet.iterator();
		while (iter.hasNext()) {
			Pattern p = Pattern.compile(ctx + iter.next());
			Matcher m = p.matcher(uri);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查代码url，过滤脚本注入
	 * @param value
	 * @return
	 */
	public Boolean checkUrl(String value) {
		if (StringUtils.isNotBlank(value)) {
			if((StringUtils.indexOf(value,"src") != -1) ||
					(StringUtils.indexOf(value,"eval") != -1) ||
					(StringUtils.indexOf(value,"e­xpression") != -1) ||
					(StringUtils.indexOf(value,"javascript") != -1) ||
					(StringUtils.indexOf(value,"vbscript") != -1) ||
					(StringUtils.indexOf(value,"script") != -1) ||
					(StringUtils.indexOf(value,"alert") != -1) ||
					(StringUtils.indexOf(value,"onload") != -1) ||
					(StringUtils.indexOf(value,"select") != -1) ||
					(StringUtils.indexOf(value,"where") != -1) ||
					(StringUtils.indexOf(value,"insert") != -1) ||
					(StringUtils.indexOf(value,"update") != -1) //||
				//	(StringUtils.indexOf(value,"from") != -1) ||
				//	(StringUtils.indexOf(value,"and") != -1) ||
				//	(StringUtils.indexOf(value,"or") != -1) ||
					/*(StringUtils.indexOf(value,"(") != -1) ||
					(StringUtils.indexOf(value,")") != -1) ||
					(StringUtils.indexOf(value,"<") != -1) ||
					(StringUtils.indexOf(value,">") != -1) ||
					(StringUtils.indexOf(value,"\"") != -1) ||
					(StringUtils.indexOf(value,"\'") != -1)*/ ){

				return true;//包含
			}
		}
		//不包含
		return false;
	}

	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		Map<String, String[]> map;
		if (request.getMethod().equalsIgnoreCase("post")) {
			map = request.getParameterMap();
		} else {
			String s = request.getQueryString();
			if (StringUtils.isBlank(s)) {
				return new HashMap<String, Object>();
			}
			try {
				s = URLDecoder.decode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				//log.error("encoding " + "UTF-8" + " not support?", e);
			}
			map = parseQueryString(s);
		}

		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	public static Map<String, String[]> parseQueryString(String s) {
		String valArray[] = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}
}
