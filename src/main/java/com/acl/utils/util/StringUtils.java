package com.acl.utils.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * <b>模块描述	: </b>字符类型数据处理工具类<br/>
 * <b>使用描述	: </b><br/>
 * <b>JDK版本	: </b>JDK 1.6<br/>
 * <b>作者		: </b>辜家乐<br/>
 * <b>创建日期	: </b>2009-01-01<br/>
 * <b>创建版本	: </b>v1.0<br/>
 * ###############修改记录###############<br/>
 * …………v1.0<br/>
 * 修改日期	: 2009-01-01<br/>
 * 修改人		: 辜家乐<br/>
 * 修改原因	: -<br/>
 * 修改说明	: 初始建立<br/>
 * 代码备注	: -<br/>
 * ####################################<br/>
 * @author 辜家乐
 */
public final class StringUtils {
	
	private StringUtils(){}

	/**
	 * 功能描述：是否为空白,包括null和""
	 * 
	 * @param str String
	 * @return boolean
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 功能描述：去掉字符串中重复的字符串
	 * @param str 原字符串
	 * @param strSplit 重复的字符串
	 * @return String 返回去掉重复子字符串后的字符串
	 */
	public static String removeSameString(String str,String strSplit) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();
		String[] strArray = str.split(strSplit);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i]);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 判断字符串是否为null或""，如果是则返回"";
	 * @param string String
	 * @return String
	 */
	public static String getString(String string){
		String s = "";
		if (!isBlank(string)) {
			s = string;
		}
		return s;
	}
	
	/**
	 * 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
	 * 
	 * @param str
	 *            String 原始字符串
	 * @return String 替换后的字符串
	 */
	public static String htmlShow(String str) {
		if (str == null) {
			return null;
		}
		String strTmp = "";
		strTmp = replace("<", "&lt;", str);
		strTmp = replace(" ", "&nbsp;", strTmp);
		strTmp = replace("\r\n", "<br/>", strTmp);
		strTmp = replace("\n", "<br/>", strTmp);
		strTmp = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", strTmp);
		return str;
	}
	
	/**
	 * 功能描述：替换字符串
	 * 
	 * @param from
	 *            String 原始字符串
	 * @param to
	 *            String 目标字符串
	 * @param source
	 *            String 母字符串
	 * @return String 替换后的字符串
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null){
			return null;
		}
		StringBuffer str = new StringBuffer("");
		int index = -1;
		String sourceTmp = source;
		while ((index = sourceTmp.indexOf(from)) != -1) {
			str.append(sourceTmp.substring(0, index) + to);
			sourceTmp = sourceTmp.substring(index + from.length());
			index = sourceTmp.indexOf(from);
		}
		str.append(sourceTmp);
		return str.toString();
	}
	
	
	 public static String zhuanhuan(String chenese) {
	    	String abc=null;
			try {
				abc = java.net.URLDecoder.decode(chenese,"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	return abc;
	       }
	 
	 
	 public static String StringToUtf八(String 八) {
	    	String abc=null;
			try {
				if(八!=null&&!八.equals("")){
				abc=new String(八.getBytes("ISO-8859-1"),"UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	return abc;
	       }
	 /**
	  * 转换提交的手机号码为 ###****####
	  * @param obj
	  * @return
	  */
	 public static String getHideMobile(String mobile){
		 return mobile.substring(0,3) + "****" + mobile.substring(7, mobile.length());
	 }
	 
	 public static String convertString(Object obj){
		 if(obj == null){
			 return "";
		 }
		 return obj.toString();
	 }
	 /**
	  * 检查提交对象是否为String  否则返回""
	  * @param obj
	  * @return
	  */
	 public static String checkString(Object obj){
		 if(null == obj || "".equals(obj)){
			 return "";
		 }
		 return obj.toString();
	 }
	 /**
	  * 检查提交对象并格式化string日期   否则返回当前日期 
	  * 格式：yyyy-MM-dd hh:mm
	  * @param obj
	  * @return
	  */
	 public static String checkDate(String obj){
		 if(null == obj || "".equals(obj)){
			 String date = DateFormatUtil.convertDate(new Date(), "yyyy-MM-dd hh:mm");
			 return date;
		 }
		 return obj.toString();
	 }
	 /**
	  * 检查提交对象是否为int数字  否则返回0
	  * @param obj
	  * @return
	  */
	 public static int checkInt(Object obj){
		 if(null == obj || "".equals(obj)){
			 return 0;
		 }
		 return Integer.parseInt(obj.toString());
	 }
	 /**
	  * 检查提交对象是否为long  否则返回0
	  * @param obj
	  * @return
	  */
	 public static long checkLong(Object obj){
		 if(null == obj || "".equals(obj)){
			 return 0;
		 }
		 return Long.parseLong(obj.toString());
	 }
	 /**
	  * 检查提交对象是否为bigDecimal  否则返回0
	  * @param obj
	  * @return
	  */
	 public static BigDecimal checkBigDecimal(Object obj){
		 if(null == obj || "".equals(obj)){
			 return new BigDecimal(0);
		 }
		 return new BigDecimal(obj.toString());
	 }

	 public  static  Date strtodate(String str) throws ParseException {
		 if (isBlank(str)){

		 }
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(str);
	 }
	 public static void main(String[] args) {
		 BigDecimal totalMoney = new BigDecimal(477472.23);
		 BigDecimal earnestMoney = totalMoney.multiply(new BigDecimal(0.2)).setScale(2,BigDecimal.ROUND_HALF_UP);
		 System.out.println(earnestMoney);
	}
	 
}
