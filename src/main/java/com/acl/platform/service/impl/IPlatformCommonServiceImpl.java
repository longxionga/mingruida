package com.acl.platform.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acl.platform.dao.IPlatformCommonDao;
import com.acl.platform.service.IPlatformCommonService;
import com.acl.pojo.UserInfo;
import com.acl.utils.util.PaymentCommonUtil;

@Service
public class IPlatformCommonServiceImpl implements IPlatformCommonService {

	@Autowired
	private IPlatformCommonDao commonDao;

	@Override
	public int insertMsgSend(Map<String, Object> map) {
		return commonDao.insertMsgSend(map);
	}

	@Override
	public Map<String, Object> getWxAccessTokenByAgentId(String agent_id) {
		return commonDao.getWxAccessTokenByAgentId(agent_id);
	}

	@Override
	public int updateWxAccessTokenByAgentId(Map<String, Object> map) {
		return commonDao.updateWxAccessTokenByAgentId(map);
	}
	
	@Override
	public int insertWxAccessToken(Map<String, Object> map) {
		return commonDao.insertWxAccessToken(map);
	}

	@Override
	public Map<String, Object> queryPaymentInfoByType(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		List<Map<String, Object>> list = commonDao.getPaymentRule(map);
		if (list == null || list.isEmpty()) {
			return new HashMap<String, Object>();
		}
		return list.get(0);
	}

	@Override
	public List<Map<String, Object>> queryPaymentInfoOfRecharge() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cz_is_use", "1");
		return commonDao.getPaymentRule(map);
	}

	@Override
	public List<Map<String, Object>> queryPaymentInfoOfWithdraw() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tx_is_use", "1");
		return commonDao.getPaymentRule(map);
	}

	/**
	 * @param
	 * @param money
	 * @param type
	 * @param payType
	 */
	@Override
	public Map<String, Object> paymentCheck(UserInfo userinfo, int money, String type, String payType) {
		// 反馈信息
		Map<String, Object> rstMap = new HashMap<String, Object>();
		rstMap.put("isOk", false);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		List<Map<String, Object>> list = commonDao.getPaymentRule(map);
		if (list == null || list.size() != 1) {
			rstMap.put("message", "未查询到支付验证信息");
			return rstMap;
		}
		// 支付方式
		Map<String, Object> ruleMap = list.get(0);

		if (payType.equals(PaymentCommonUtil.PAYMENT_RECHARGE)) {
			//
			// 充值判断
			//
			// 充值时间
			String czTimes = ruleMap.get("cz_time").toString();
			// 充值星期
			String czDates = ruleMap.get("cz_date").toString();
			// 单笔金额最小范围
			double reminmoney = Double.valueOf(ruleMap.get("cz_min_money").toString());
			// 单笔金额最大范围
			double remaxmoney = Double.valueOf(ruleMap.get("cz_max_money").toString());
			// 用户状态

			// 时间验证
			if (!checkPaymentDate(czDates, czTimes)) {
				rstMap.put("message", "当前时间不可充值");
				return rstMap;
			}


			rstMap.put("isOk", true);
			return rstMap;
		} else if (payType.equals(PaymentCommonUtil.PAYMENT_WITHDRAW)) {
			//
			// 提现判断
			//
			// 提现时间
			String txTimes = ruleMap.get("tx_time").toString();
			// 提现星期
			String txDates = ruleMap.get("tx_date").toString();
			// 单笔最小范围
			double wiminmoney = Double.valueOf(ruleMap.get("tx_min_money").toString());
			// 单笔最大范围
			double wimaxmoney = Double.valueOf(ruleMap.get("tx_max_money").toString());
			// 手续费=仓储费
			// int withfee = (int) ruleMap.get("tx_fee");
			// 每天提现额度
			double todaywiquota = Double.valueOf(ruleMap.get("tx_day_max_money").toString());


			// 时间验证
			if (!checkPaymentDate(txDates, txTimes)) {
				rstMap.put("message", "当前时间不可提现");
				return rstMap;
			}
			// 金额验证
			if (money < wiminmoney) {
				rstMap.put("message", "单笔提现金额小于最低限额");
				return rstMap;
			}
			if (money > wimaxmoney) {
				rstMap.put("message", "单笔提现金额大于最高限额");
				return rstMap;
			}
		}
		rstMap.put("message", "数据验证未通过");
		return rstMap;
	}

	private static SimpleDateFormat hmSDF = new SimpleDateFormat("HHmm");

	public static boolean checkPaymentDate(String dates, String times) {
		// String[] weeks = dates.split(","); TODO 星期
		String[] unAvailableTimes = times.split(",");

		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 6; // 数组6为星期天
		} else {
			dayOfWeek = dayOfWeek - 2; // 2-7 对应 数组0-5
		}

		if (unAvailableTimes[dayOfWeek].contains("-")) {
			String[] unAvailableRange = unAvailableTimes[dayOfWeek].split("-");
			int starttime = Integer.parseInt(unAvailableRange[0].replace(":", ""));
			int endtime = Integer.parseInt(unAvailableRange[1].replace(":", ""));
			int nowtime = Integer.parseInt(hmSDF.format(date));
			if (nowtime >= starttime && nowtime <= endtime) {
				return true;
			}
		}
		return false;
	}
}
