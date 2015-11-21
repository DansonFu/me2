package com.lettucetech.me2.common.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串处理工具类
 * 
 * @author 刘洋
 * 
 */
public class StringUtil {

	/** 日期格式字符串 */
	public static final String DF_YMD = "yyyy-MM-dd";
	/** 日期格式字符串 */
	public static final String DF_YMD_24 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 空字符串
	 */
	public static final String EMPTY = "";

	/**
	 * 判断字符串是否为空
	 * 
	 * @param obj
	 * @return ture:为空 false:不为空
	 */
	public static boolean isNullOrEmpty(String value) {
		return value == null || EMPTY.equals(value);
	}

	/**
	 * 删除前后全角半角空格和tab
	 * 
	 * @param value
	 *            处理值
	 * @return 处理字符串
	 */
	public static String delSpace(String value) {
		if (isNullOrEmpty(value)) {
			return EMPTY;
		}
		value = value.replaceAll("^[　 \t]+|[　 \t]+$", "");
		return value;
	}

	/**
	 * 当字符串为null的时候、返回空字符串 ("") 。<br>
	 * 不为null的场合返回传入字符串。
	 * 
	 * @param str
	 *            处理值
	 * @return 处理字符串
	 */
	public static String nullToEmpty(String str) {
		return (str != null) ? str : EMPTY;
	}

	/**
	 * 字符串转换成日期<br>
	 * value为yyyy-MM-dd格式
	 * 
	 * @param value
	 *            处理值
	 * @return 转换字符串
	 */
	public static Date strToDate(String value) {
		return strToDate(value, DF_YMD);
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param value
	 *            处理值
	 * @param format
	 *            处理值日期格式
	 * @return 转换字符串
	 */
	public static Date strToDate(String value, String format) {
		Date result;
		if (value == null) {
			result = null;
		} else {
			SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
			sdf.applyPattern(format);
			sdf.setLenient(false);
			result = sdf.parse(value, new ParsePosition(0));
		}
		return result;
	}

	/**
	 * 日期转换字符串<br>
	 * 默认yyyy-MM-dd格式
	 * 
	 * @param value
	 *            处理值
	 * @return String 转换值
	 * 
	 */
	public static String dateFormat(Date value) {
		return dateFormat(value, DF_YMD);
	}

	/**
	 * 日期转换字符串
	 * 
	 * @param value
	 *            处理值
	 * @param format
	 *            日期格式
	 * @return String 转换值
	 * 
	 */
	public static String dateFormat(Date value, String format) {
		if (value == null || format == null) {
			return EMPTY;
		}

		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		sdf.applyPattern(format);
		return sdf.format(value);
	}
}
