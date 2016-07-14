package com.pizza.wechat.message.event;

public interface AuthNotify {
	/**
	 * 公众号取消授权事件
	 * @param appId 授权方appid
	 */
	public void unAuthorized(String appId);
	/**
	 * 开放平台每10分钟推送一次ticket接收事件
	 * @param ticket
	 */
	public void verifyTicket(String ticket);
}
