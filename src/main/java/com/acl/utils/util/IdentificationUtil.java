/*
package com.acl.utils.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class IdentificationUtil {

	public static String generateId() {
		// return DateFormatUtil.convertCurrentDate("yyyyMMddHHmmssSSS") + new
		// Random().nextInt(9999);
		return StringUtils.remove(UUID.randomUUID().toString(), '-');
	}

	public static String generateLoginId() {
		return DateFormatUtil.convertCurrentDate("yyMMddHHmmss") + new Random().nextInt(999);
	}
	
	public static String generateTimestamp16() {
		return DateFormatUtil.convertCurrentDate("yyyyMMdd") + new Random().nextInt(99999999);
	}

	public static void main(String[] args) {

		System.out.println(generateTimestamp16());
	}
}
*/
