package com.acl.utils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

	public static String convertCurrentDate(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	
	public static String convertDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date convertDate(String time, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(time);
	}

	/**
	 * 获取前一个月数据
	 * @param repeatDate
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMonth(String repeatDate) throws ParseException {
		String lastMonth = "";

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");

		int year = Integer.parseInt(repeatDate.substring(0, 4));

		String monthsString = repeatDate.substring(4, 6);

		int month;

		if ("0".equals(monthsString.substring(0, 1))) {

			month = Integer.parseInt(monthsString.substring(1, 2));

		} else {

			month = Integer.parseInt(monthsString.substring(0, 2));

		}

		cal.set(year,month-2,Calendar.DATE);

		lastMonth = dft.format(cal.getTime());

		return lastMonth;
	}

	public static boolean comparetoTime(String beginTime, String endTime) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 Date bt=sdf.parse(beginTime);
		 Date et=sdf.parse(endTime);
		 if (bt.getTime()>=et.getTime()){
		// 表示bt小于et
		 return true;
		 }else{
			 return false;
		 }
	}
	/**
	 * 获取当前月份第一天
	 * @param repeatDate
	 * @return
	 * @throws ParseException
	 */
	public static String getFirstDay(String repeatDate) throws ParseException {
		String firstDay = "";

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		int year = Integer.parseInt(repeatDate.substring(0, 4));

		String monthsString = repeatDate.substring(5, 7);

		int month;

		if ("0".equals(monthsString.substring(0, 1))) {

			month = Integer.parseInt(monthsString.substring(1, 2));

		} else {

			month = Integer.parseInt(monthsString.substring(0, 2));

		}

		cal.set(year,month-1,01);
		firstDay = dft.format(cal.getTime());

		return firstDay;
	}
	/**
	 * 获取当前月份最后一天
	 * @param repeatDate
	 * @return
	 * @throws ParseException
	 */
	public static String getLastDay(String repeatDate) throws ParseException {
		String lastDay = "";

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		int year = Integer.parseInt(repeatDate.substring(0, 4));

		String monthsString = repeatDate.substring(5, 7);

		int month;

		if ("0".equals(monthsString.substring(0, 1))) {

			month = Integer.parseInt(monthsString.substring(1, 2));

		} else {

			month = Integer.parseInt(monthsString.substring(0, 2));

		}

		cal.set(year,month,0);
		lastDay = dft.format(cal.getTime());

		return lastDay;
	}

	/**
	 * 获取下个月时间
	 * @param repeatDate
	 * @return
	 * @throws ParseException
	 */
	public static String lastMonth(String repeatDate) throws ParseException {
		String lastMonth = "";

		Calendar cal = Calendar.getInstance();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		Date  d = convertDate(repeatDate,"yyyy-MM-dd");
		cal.setTime(d);
		cal.add(cal.MONTH,1);
		lastMonth = dft.format(cal.getTime());

		return lastMonth;
	}

	/**
	 * 获取指定日期后一天时间
	 * @param repeatDate
	 * @return
	 * @throws ParseException
	 */
	public static String getSpecifiedDayAfter(String repeatDate) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(repeatDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;

	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param startdate
	 * @param enddate
	 * @return
	 * @throws Exception
	 */
	public static int differentDays(String startdate,String enddate) {
		Date date1 = null;
		Date date2= null;
		try {
			date1 = convertDate(startdate,"yyyy-MM-dd");
			date2 = convertDate(enddate,"yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) +1;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1+1;
		}

	}

	public static int getdays(String  dates){
	    if (dates==null){
	        return  0;
        }
		Date date1 = null;
		try {
			date1 = convertDate(dates,"yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		int day1= cal1.get(Calendar.DATE);
		return day1;
	}

	public static void main(String[] args) throws  Exception {
		Calendar calendar = Calendar.getInstance();
		        calendar.add(Calendar.MONTH, -1);    //得到前一个月
		        
		        System.out.println(convertDate(calendar.getTime(), "yyyy-MM-dd 00:00:00"));
		System.out.println(differentDays("2019-03-10","2019-03-11"));

		System.out.println(getLastMonth("201903"));



	}
}
