package com.pizza.wechat.message.event.impl;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pizza.wechat.APIException;
import com.pizza.wechat.message.event.MessageNotify;
import com.pizza.wechat.request.AuthorizerToken;
import com.pizza.wechat.request.api.AccessTokenApi;
import com.pizza.wechat.request.api.CustomerServiceApi;

/**
 * 全放发布时时使用此实现
 * 
 * @ClassName : OSReleaseMode
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午8:44:18
 */
public class OSReleaseMode implements MessageNotify {
	protected static Logger logger = LogManager.getLogger(OSReleaseMode.class);
	private Timer timer = null;
	/**
	 * 第三方token
	 */
	private String serviceToken;

	public OSReleaseMode(String serviceToken) {
		this.serviceToken = serviceToken;
		this.timer = new Timer();
	}

	@Override
	public String text(String appId, String openId, String content,
			Map<String, String> requestMap) {
		logger.info("全网发布测试，文本消息api");
		String test = "QUERY_AUTH_CODE:";
		String result = content + "_callback";// 开放平台全网发布 2.0 > 返回固定格式内容
												// content+"_callback"
												// content为解密后的Content字段内容
		if (content.startsWith(test)) {// 开放平台全网发布 3.2 > 在3.1完成后执行此步骤
										// 此步骤中Content字段内容以"QUERY_AUTH_CODE:"为前缀字符串，后面紧跟是授权码
			logger.info("全网发布测试，回复用户固定内容api");
			final String code = content.replace(test, "");
			final String toUser = openId;
			timer.schedule(new TimerTask() {
				public void run() {// 开放平台全网发布 3.2.1 >
					testReplyMessage(code, toUser);
					timer.cancel();
				}
			}, 1000);
			result = "";// 开放平台全网发布 3.1 > 返回空文本
		}
		return result;
	}

	@Override
	public String image(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String location(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String link(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String voice(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String subscribe(String appId, String openId,
			Map<String, String> requestMap) {
		logger.info("全放发布测试，关注回复固定内容");
		return requestMap.get("Event") + "from_callback";// 开放平台全网发布 1.0
	}

	@Override
	public void unSubscribe(String appId, String openId,
			Map<String, String> requestMap) {

	}

	@Override
	public String qrscan(String appId, String openId, String scanParam,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String click(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	@Override
	public String event(String appId, String openId,
			Map<String, String> requestMap) {
		return "success";
	}

	/**
	 * 调用客服回复消息接口
	 * 
	 * @param code
	 * @param openId
	 */
	private void testReplyMessage(String code, String openId) {
		String content = code + "_from_api";
		AccessTokenApi tokenApi = new AccessTokenApi();
		try {
			AuthorizerToken authorizerToken = tokenApi.getAuthToken(
					serviceToken, code);
			// 开放平台全网发布 3.2.1
			CustomerServiceApi api = new CustomerServiceApi();
			api.replyText(content, openId, authorizerToken.getAccessToken());
		} catch (APIException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
