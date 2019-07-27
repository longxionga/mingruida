package com.acl.utils.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期转换处理
 * 
 * @author malixi
 *
 */
public class FormatDateUtil {

	/**
	 * 转换时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date DatetoDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime = formatter.parse(dateString, pos);
		return currentTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateYYMMDD(Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateYYMMDDHHMMSS(Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @throws ParseException
	 * 
	 * @return返回字符串格式 yyyy-MM-dd
	 */
	public static String convertDate(String actionDay) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
		Date date = formatter.parse(actionDay);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, 12);

		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		return format2.format(cal.getTime());
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @return date
	 */
	public static Date StrToDate3(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}


	/**
	 * 字符串转换成日期
	 *
	 * @param str
	 * @return date
	 */
	public static Date StrToDate2(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = (Date) format.parseObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Map<String, Object> mapToDate(Map<String, Object> map) {
		Date d = new Date();
		@SuppressWarnings("deprecation")
		int hours = d.getHours();
		String dd = FormatDateUtil.getStringDateYYMMDD(d);
		if (hours > 4) {
			String startDate = dd + " " + "07:00:00";
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String endDate = sf.format(cal.getTime());
			endDate = endDate + " " + "04:00:00";
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		}
		if (hours < 4) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = sf.format(cal.getTime()) + " " + "07:00:00";
			String endDate = dd + " " + "04:00:00";
			map.put("startDate", startDate);
			map.put("endDate", endDate);
		}
		return map;
	}

	/**
	 * @author LS
	 * @param createDate
	 * @return 格式化当天日期和list里的时间做对比,list的时间如果是当天的就显示时间,如果不是就显示月份和时间,如果年份不同就显示年份月份时间
	 */
	public static String newDate(String createDate) {
		// 格式化当天日期,2015-10-16
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());
		String[] dateTime1 = time.split("-");
		String newDate = "";
		for (int i = 0; i < dateTime1.length; i++) {
			newDate += dateTime1[i];
		}

		// 格式化时间2015-10-16 16:50:18.937
		// 把所有查询到的时间格式化后在放回到list里,发送到前台
		String date1 = createDate.substring(0, 10);// 截取字符串的年月日
		String[] dateTime2 = date1.split("-");
		String oldDate = "";
		for (int j = 0; j < dateTime2.length; j++) {
			oldDate += dateTime2[j];
		}
		String day = createDate.substring(11, 16);// 取时间
		String month = createDate.substring(5, 16);// 取月份和时间
		String year = createDate.substring(0, 16);// 取年月日
		if (Integer.parseInt(newDate) == Integer.parseInt(oldDate)) {// 判断是否是当前日期
			return day;// 返回小时和分钟
		} else {
			if (Integer.parseInt(dateTime1[0]) == Integer.parseInt(dateTime2[0])) {// 判断年份是否一样
				return month;// 返回月份,小时和分钟
			} else {
				return year;// 返回年份,月份,小时和分钟
			}
		}
	}
	/**
	 * 获取当前日期的星期几
	 * @param createDate
	 * @return 1-6 周一到周六  0 周日
	 */
	public static int getWeek(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK)-1;
	}

	/**
	 * 字符串时间截取年月日,例:2015-10-16 16:50
	 * 
	 * @author LS
	 * @param createDate
	 * @return
	 */
	public static String newDateForRrcord(String createDate) {
		String date = createDate.substring(0, 16);// 取年月日
		return date;
	}

	/**
	 * 时间转成long字符串
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long longTime(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		long millionSeconds = sdf.parse(date).getTime();// 毫秒
		return millionSeconds;
	}

	/**
	 * 获取当前中国时间 去除时区干扰
	 * huangs
	 * @return Date
	 * @throws ParseException
	 */
	public static Date currentDateTime() throws ParseException {
	    TimeZone timeZoneSH = TimeZone.getTimeZone("Asia/Shanghai");
		SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.CHINA);
		outputFormat.setTimeZone(timeZoneSH);
		Date date = new Date(System.currentTimeMillis());
		return date;
	}
}
