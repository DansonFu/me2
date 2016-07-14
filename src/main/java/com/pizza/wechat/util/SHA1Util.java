package com.pizza.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import com.pizza.wechat.APIRuntimeException;

/**
 * SHA1加密算法工具类
 * 
 * @ClassName : SHA1Util
 * @author : emmy.cheng
 * @date : 2015年11月17日 下午3:47:55
 */
public class SHA1Util {
	/**
	 * 生成随机数
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)));
	}

	/**
	 * 生成时间戳
	 * 
	 * @return
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取sha1签名
	 * 
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	public static String createSHA1Sign(SortedMap<String, String> signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
		return getSha1(params);
	}

	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 生成啥sha1算法加密串
	 * 
	 * @param str
	 * @return
	 */
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (UnsupportedEncodingException e) {
			throw new APIRuntimeException("character encode fail!", e);
		} catch (NoSuchAlgorithmException e) {
			throw new APIRuntimeException("sha-1 encode init fail!", e);
		}
	}
}
