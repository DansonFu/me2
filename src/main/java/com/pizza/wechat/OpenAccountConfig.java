package com.pizza.wechat;

import java.io.IOException;
import java.util.Properties;

/**
 * 开放平台账户配置信息
 * 
 * @ClassName : OpenAccountConfig
 * @author : emmy.cheng
 * @date : 2015年10月24日 上午1:46:56
 */
public final class OpenAccountConfig {
	private static OpenAccountConfig instance = new OpenAccountConfig();
	private static Properties config = new Properties();

	public static OpenAccountConfig getInstance() {
		return instance;
	}

	private OpenAccountConfig() {

	}

	static {
		try {
			config.load(OpenAccountConfig.class
					.getResourceAsStream("/account.properties"));
		} catch (IOException e) {
			new APIException("open account properties file load fail!", e);
		}
	}
	/**
	 * 开放平台appid
	 * @return
	 */
	public String getAppId() {
		return config.getProperty("appid");
	}
	/**
	 * 开放平台secret
	 * @return
	 */
	public String getSecret() {
		return config.getProperty("secret");
	}
	/**
	 * 开放平台token
	 * @return
	 */
	public String getToken() {
		return config.getProperty("token");
	}
	/**
	 * 开放平台encodingaeskey
	 * @return
	 */
	public String getEncodingaeskey() {
		return config.getProperty("encodingaeskey");
	}
	/**
	 * 是否开启开发调试模式，默认不开启，开启后将显示详细的请求过程
	 * @return
	 */
	public boolean isDebug() {
		return Boolean.valueOf(config.getProperty("debug", "false"));
	}
}
