package com.acl.utils.msg;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.acl.utils.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.acl.platform.service.IPlatformCommonService;
import com.acl.pojo.UserInfo;
import com.acl.utils.util.ClientExecuteProxyUtils;
import com.acl.utils.util.DateFormatUtil;
import com.acl.utils.util.HttpClientConnectionManager;
import com.alibaba.fastjson.JSONObject;
import com.anchol.common.component.distributedlock.DistributedLock;

@Component
public class WechatMsgSend {
	private static Logger logger = Logger.getLogger(WechatMsgSend.class);

	private final static String LOCK_AGENT_TOKEN = "LOCK_AGENT_TOKEN";

	public String WECHAT_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public String WECHAT_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

	@Autowired
	private ClientExecuteProxyUtils proxyUtils;

	@Autowired
	private IPlatformCommonService commonService;

	@Autowired
	private DistributedLock distributedLock;

	@Autowired
	private MongoTemplate mongoTemplate;

	@SuppressWarnings({ "rawtypes", "static-access" })
	public boolean sendMessage(UserInfo userInfo, String content) {



		try {
			JSONObject textJson = new JSONObject();
			textJson.put("content", content);

		} catch (Exception e) {
			logger.error("发送微信客服消息异常", e);
		}
		return false;
	}

	private boolean updateAccessToken(String agent_id, String appid, String secret) {
		// 加锁
		String lockKeyActivityLuckydraw = distributedLock.getLock(LOCK_AGENT_TOKEN, agent_id);
		if (lockKeyActivityLuckydraw == null) {
			return false;
		}
		try {
			Map<String, Object> tokenMap = commonService.getWxAccessTokenByAgentId(agent_id); // 查询代理商TOKENID
			if (tokenMap == null) {

				JSONObject tokenJson = getAccessToken(appid, secret);

				if (tokenJson != null && StringUtils.isNotBlank(tokenJson.getString("access_token"))) {
					// 插入
					Map<String, Object> insMap = new HashMap<String, Object>();
					insMap.put("token_id", UUIDGenerator.generate());
					insMap.put("app_id", appid);
					insMap.put("securet", secret);
					insMap.put("wx_token", tokenJson.getString("access_token"));
					insMap.put("dept_id", agent_id);
					insMap.put("create_date", new Date());
					commonService.insertWxAccessToken(insMap);
				}
			} else {
				long tokenTime = DateFormatUtil.convertDate(tokenMap.get("create_date").toString(), "yyyy-MM-dd hh:mm:ss")
						.getTime();
				if ((System.currentTimeMillis() - tokenTime) < 7000000) {
					return false;
				}
				JSONObject tokenJson = getAccessToken(appid, secret);

				if (tokenJson != null && StringUtils.isNotBlank(tokenJson.getString("access_token"))) {
					// 更新
					Map<String, Object> updMap = new HashMap<String, Object>();
					updMap.put("agent_id", agent_id);
					updMap.put("wx_token", tokenJson.getString("access_token"));
					updMap.put("create_date", new Date());
					commonService.updateWxAccessTokenByAgentId(updMap);
				}
			}
		} catch (ParseException e) {
			logger.error("解释时间错误");
		} finally {
			distributedLock.releaseLock(LOCK_AGENT_TOKEN, agent_id, lockKeyActivityLuckydraw);
		}
		return false;
	}

	/**
	 * 获取基础AccessToken
	 */
	@SuppressWarnings("static-access")
	private JSONObject getAccessToken(String appid, String secret) {
		try {
			String getAccessToken = WECHAT_ACCESS_TOKEN.replace("APPID", appid).replace("SECRET", secret);

			CloseableHttpClient httpclient = proxyUtils.getHttpClient();
			CloseableHttpResponse response = httpclient.execute(HttpClientConnectionManager.getGetMethod(getAccessToken));
			JSONObject jObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));

			httpclient.close();
			response.close();
			return jObject;
		} catch (IOException e) {
			logger.error("");
		}
		return null;
	}

}
