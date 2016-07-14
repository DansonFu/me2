package com.pizza.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pizza.wechat.message.event.AuthNotify;
import com.pizza.wechat.util.MessageUtil;

/**
 * 公众号授权事件通知服务，可以控制log4j debug级别显示请求内容
 * 
 * @ClassName : OSAuthorizeNotify
 * @author : emmy.cheng
 * @date : 2015年11月8日 下午3:38:22
 */
public class OSAuthorizeNotify {
	private Map<String, String> requestMap;
	private AuthNotify service;

	public OSAuthorizeNotify(AuthNotify service, HttpServletRequest request) {
		try {
			this.service = service;
			this.requestMap = MessageUtil.decryptMsg(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回内容为空
	 * 处理授权信息
	 * @return
	 * @throws Exception
	 */
	public void process() {
		String infoType = requestMap.get("InfoType");
		String AuthorizerAppid = requestMap.get("AuthorizerAppid");// 取消授权公众号appid
		String ticket = requestMap.get("ComponentVerifyTicket");
		if ("unauthorized".equals(infoType)) {// 公众号取消授权操作
			this.service.unAuthorized(AuthorizerAppid);
		} else if ("component_verify_ticket".equals(infoType)) {// 微信推送过来的component_verify_ticket
																// 每10分钟推送一次
																// 必要时存储起来
			this.service.verifyTicket(ticket);
		}
	}
}
