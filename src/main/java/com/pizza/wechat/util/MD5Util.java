package com.pizza.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.pizza.wechat.APIRuntimeException;

/**
 * md5密码工具类
 * 
 * @ClassName : MD5Util
 * @author : emmy.cheng
 * @date : 2015年11月17日 下午3:45:07
 */
public class MD5Util {
	private static final String CHARSET = "UTF-8";
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 对数据进行MD5加密处理
	 * 
	 * @param origin
	 * @return
	 * @throws APIRuntimeException
	 */
	public static String MD5Encode(String origin) throws APIRuntimeException {
		String resultString = null;
		resultString = new String(origin);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes(CHARSET)));
		} catch (UnsupportedEncodingException e) {
			throw new APIRuntimeException("character encode fail!", e);
		} catch (NoSuchAlgorithmException e) {
			throw new APIRuntimeException("MD5 encode init fail!", e);
		}
		return resultString;
	}

}
