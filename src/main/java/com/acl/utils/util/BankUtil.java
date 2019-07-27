package com.acl.utils.util;
/**
 *className:BankUtil
 *author:wangli
 *createDate:2017年6月2日 上午11:43:48
 *vsersion:3.0
 *department:安创乐科技
 *description:
 */
public class BankUtil {

	
	  /**
     * 将指定长度字符串替换成*
     * @param content
     * @param begin
     * @param end
     * @return
     */
    public static String replaceStar(String content, int begin, int end) {
        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());
    }
}
