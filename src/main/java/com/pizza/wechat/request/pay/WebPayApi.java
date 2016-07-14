package com.pizza.wechat.request.pay;

import java.util.*;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pizza.wechat.APIException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.PayParam;
import com.pizza.wechat.util.MD5Util;
import com.pizza.wechat.util.Utils;
import com.pizza.wechat.util.WebPayUtil;
/**
 * 公众号web端js api 支付
 * @ClassName : WebPayApi
 * @author : emmy.cheng
 * @date : 2015年11月17日 下午5:30:34
 */
public class WebPayApi {
	protected static Logger log = LogManager.getLogger(WebPayApi.class);
	private static final String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	static {
		/**
		 * 开启调试模式
		 */
		if (WebPayConfig.getInstance().isDebug()) {
			log.setLevel(Level.DEBUG);
		}
	}
	private SortedMap<String, String> parameters = new TreeMap<String, String>();
	private static final String CHATSET = "UTF-8";
	private static WebPayConfig config = WebPayConfig.getInstance();
	
	public WebPayApi(PayParam param) {
		parameters.put("appid", config.getAppId());
		parameters.put("mch_id", config.getPartnerId());
		parameters.put("notify_url", config.getNotifyUrl());
		parameters.put("input_charset", CHATSET);
		parameters.put("trade_type", "JSAPI");
		try {
			parsePayParam(param);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		log.debug("web pay api request param " + parameters);
	}
	/**
	 * 生成订单
	 * @return
	 * @throws APIException
	 */
	public Map<String, String> createOrder() throws APIException {
		String sign = createSign(this.parameters);
		setParameters("sign", sign);
		String xml = toXML();
		URLParam url = new URLParam(ORDER_URL);
		return APIHttpConnect.xmlHttps(url, xml);
	}
	/**
	 * 获取字符请求参数
	 * @param key
	 * @param value
	 */
	public String getParameters(String key) {
		return this.parameters.get(key);
	}
	/**
	 * 设置支付请求参数
	 * @param key
	 * @return
	 */
	public void setParameters(String key, String value) {
		this.parameters.put(key, value);
	}
	/**
	 * 字符url编码
	 * @param src
	 * @return
	 */
	public String encode(String src) {
		try {
			return URLEncoder.encode(src, CHATSET).replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建md5加密签名
	 * 
	 * @param parameters
	 * @return
	 */
	public String createSign(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WebPayConfig.getInstance().getPartnerKey());
		String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
		log.debug("web pay api request md5 sign {" + sign + "}");
		return sign;
	}
	
	public String genPackage() {
		String sign = createSign(this.parameters);
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + encode(v) + "&");
		}

		String packageValue = sb.append("sign=" + sign).toString();
		log.debug("web pay api request package {" + packageValue + "}");
		return packageValue;
	}
	/**
	 * 验证签名是否正确
	 * @param signParams
	 * @return
	 */
	public boolean verifyMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String sign = MD5Util.MD5Encode(sb.toString()).toLowerCase();
		String tenpaySign = this.parameters.get("sign").toLowerCase();
		return tenpaySign.equals(sign);
	}
	/**
	 * 字符请求参数转xml字符串
	 * @return
	 */
	public String toXML() {
		return WebPayUtil.arrayToXml(parameters);
	}

	private void parsePayParam(PayParam param) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Class clazz = param.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		Method.setAccessible(methods, true);
		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("get")) {
				name = name.replaceFirst("get", "");
				name = attr2Key(name);
				parameters.put(name, Utils.toString(method.invoke(param)));
			}
		}
	}

	private String attr2Key(String attr) {
		char[] oldName = attr.toCharArray();
		StringBuilder sbs = new StringBuilder();
		for (char c : oldName) {
			if ((c >= 'A' && c <= 'Z') && sbs.length() > 0) {
				sbs.append(("_" + c).toLowerCase());
			} else {
				sbs.append(("" + c).toLowerCase());
			}
		}
		return sbs.toString();
	}

}
