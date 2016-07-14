package com.pizza.wechat.request.pay;

import java.util.*;
import java.io.IOException;

import com.pizza.wechat.APIException;

/**
 * 支付配置文件
 * 
 * @ClassName : PayConfig
 * @author : emmy.cheng
 * @date : 2015年11月16日 下午4:15:26
 */
public final class WebPayConfig {
	private static WebPayConfig instance = new WebPayConfig();
	private static Properties config = new Properties();

	public static WebPayConfig getInstance() {
		return instance;
	}

	private WebPayConfig() {

	}

	static {
		try {
			config.load(WebPayConfig.class.getResourceAsStream("/pay.properties"));
		} catch (IOException e) {
			new APIException("pay properties file load fail!", e);
		}
	}

	/**
	 * 支付appid
	 * 
	 * @return
	 */
	public String getAppId() {
		return config.getProperty("appid");
	}

	/**
	 * 商户秘钥key
	 * 
	 * @return
	 */
	public String getPartnerKey() {
		return config.getProperty("partnerKey");
	}

	/**
	 * 支付商户id
	 * 
	 * @return
	 */
	public String getPartnerId() {
		return config.getProperty("partner");
	}
	
	/**
	 * 支付完成通知地址
	 * 
	 * @return
	 */
	public String getNotifyUrl() {
		return config.getProperty("notifyUrl");
	}
	/**
	 * 是否开启开发调试模式，默认不开启，开启后将显示详细的请求过程
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return Boolean.valueOf(config.getProperty("debug", "false"));
	}
}
