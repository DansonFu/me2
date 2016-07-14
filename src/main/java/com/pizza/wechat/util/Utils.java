package com.pizza.wechat.util;


/**
 * 工具类
 * 
 * @ClassName : Utils
 * @author : emmy.cheng
 * @date : 2015年10月23日 下午9:50:34
 */
public class Utils {
	/**
	 * 对象转字符串默认值为空非null""
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return isEmpty(obj) ? "" : obj.toString();
	}
	/**
	 * 字符对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return (obj == null) || (obj.toString().trim().length() < 1);
	}

	/**
	 * 对象转整型默认值为0
	 * 
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		try {
			return Integer.parseInt(obj.toString());
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	/**
	 * 对象转长整型默认值0
	 * 
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj) {
		if (obj == null)
			return 0;
		try {
			return Long.parseLong(obj.toString());
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	/**
	 * 获取字符串长度一个中文占两个字节
	 * 
	 * @param value
	 * @return
	 */
	public static int stringLen(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
}
