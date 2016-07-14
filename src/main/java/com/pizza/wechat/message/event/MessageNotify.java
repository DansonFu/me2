package com.pizza.wechat.message.event;

import java.util.Map;
/**
 * 授权方消息处理
 * @ClassName : MessageNotify
 * @author : emmy.cheng
 * @date : 2015年11月8日 下午4:49:34
 */
public interface MessageNotify {
	/**
	 * 文本消息通知
	 * 
	 * @param appId
	 *            授权方appid
	 * @param openId
	 *            授权方粉丝openid
	 * @param content 粉丝输入的内容
	 * @param requestMap 其他微信推送请求内容
	 * @return
	 */
	public String text(String appId, String openId,String content,
			Map<String, String> requestMap);

	/**
	 * 图文消息通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String image(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 地址消息通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String location(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 连接消息通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String link(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 语音消息通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String voice(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 关注消息通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String subscribe(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 取消关注通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public void unSubscribe(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 扫描微信二维码通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String qrscan(String appId, String openId,String scanParam,
			Map<String, String> requestMap);

	/**
	 * 点击自定义菜单通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String click(String appId, String openId,
			Map<String, String> requestMap);

	/**
	 * 事件通知
	 * 
	 * @param requestMap
	 * @return String
	 */
	public String event(String appId, String openId,
			Map<String, String> requestMap);
}
