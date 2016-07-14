package com.pizza.wechat.util;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;


/**
 * 微信支付工具类
 * 
 * @ClassName : PayUtil
 * @author : emmy.cheng
 * @date : 2015年11月16日 下午3:45:31
 */
public class WebPayUtil {
	
	/**
	 * 生成指定长度支付随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 生成16位支付随机数
	 * 
	 * @return
	 */
	public static String createNoncestr() {
		return createNoncestr(16);
	}


	/**
	 * 是否数字类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 集合转xml数据输出
	 * 
	 * @param arr
	 * @return
	 */
	public static String arrayToXml(SortedMap<String, String> arr) {
		String xml = "<xml>";
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (isNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}
	/**
	 * 返回给微信的编码以及错误信息
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

}
