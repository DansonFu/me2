/**
 * 
 */
package com.lettucetech.me2.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 实例化config.properties方法
 * 
 * @author 张荣
 */
public class PropertiesUtil {

	private static Properties properties = new Properties();

	// 调用方法将配置文件转化为类
	static {
		InputStreamReader reader = null;
		InputStream is = ClassLoaderUtil.getResourceAsStream("config/others/config.properties", PropertiesUtil.class);
		if (null != is) {
			try {
				reader = new InputStreamReader(is, "UTF-8");
				properties.load(reader);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取参数值
	 * 
	 * @param key
	 *            参数名称
	 * @return the property
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public static void setProperties(Properties properties) {
		PropertiesUtil.properties = properties;
	}

}
