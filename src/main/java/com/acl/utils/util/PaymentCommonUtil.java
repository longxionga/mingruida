package com.acl.utils.util;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acl.platform.service.IPlatformCommonService;
import com.acl.pojo.UserInfo;
import com.acl.utils.msg.MobileMsgSend;
import com.acl.utils.msg.WechatMsgSend;

/**
 * @className:PaymentUtil
 * @author:lj
 * @createDate:2016年5月31日 下午4:39:45
 * @vsersion:1.0
 * @department:安创乐科技
 * @description:
 */
@Component
public class PaymentCommonUtil {
	protected Log logger = LogFactory.getLog(PaymentCommonUtil.class);

	@Autowired
	private IPlatformCommonService commonService;

	@Autowired
	private WechatMsgSend wechatMsgSend;

	@Autowired
	private MobileMsgSend mobileMsgSend;
	
	public final static String PAYMENT_RECHARGE = "01";
	public final static String PAYMENT_WITHDRAW = "02";

	public final static String PAYMENT_WECHATPAY = "1";
	public final static String PAYMENT_UNIONPAY = "2";
	public final static String PAYMENT_MOBAOPAY = "3";
	public final static String PAYMENT_ECPSSPAY = "4";
	public final static String PAYMENT_DINPAY = "5";

	public final static String PAYMENT_ROLE_USER = "01";
	public final static String PAYMENT_ROLE_BROKER = "02";

	/**
	 * 发送微信验证消息
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 * 
	 */
	public void sendVerifyMessage(HttpSession session, UserInfo userInfo) throws UnsupportedEncodingException {
		// 生成六位生成随机码
		String randomCode = RandomValidateCode.getRandomNumber(6);

		try {
			String message = "支付 验证码：" + randomCode;

			// 发送微信消息
			wechatMsgSend.sendMessage(userInfo, new String(message.getBytes("utf-8"), "iso8859-1"));

			// 发送手机消息
			mobileMsgSend.sendPhoneCode(userInfo.getMobile(), message, true, null, null);

			logger.debug("微信验证码：" + randomCode);

			session.setAttribute(PaymentSessionKey.PAY_OPENID_VERIFY, randomCode);
			session.setAttribute(PaymentSessionKey.PAY_OPENID_VERIFY_NUM, 0);
			session.setAttribute(PaymentSessionKey.PAY_OPENID_VERIFY_USER, userInfo);

			// 插入消息数据
			Map<String, Object> insMap = new HashMap<String, Object>();
			insMap.put("id", UUID.randomUUID().toString());
			insMap.put("content", message);
			insMap.put("status", "1"); // 1已发 0未发
			insMap.put("create_date", new Date());
			int rst = commonService.insertMsgSend(insMap);
			if (rst != 1) {
				logger.error("插入支付微信验证消息失败");
			}
		} catch (Exception e) {
			logger.error(
					"发送验证消息失败：" + userInfo.getMobile() + " mobile:" + userInfo.getMobile() + " randomCode:" + randomCode, e);
		}
	}
}
