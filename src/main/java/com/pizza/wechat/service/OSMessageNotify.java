package com.pizza.wechat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pizza.wechat.OpenAccountConfig;
import com.pizza.wechat.aes.WXBizMsgCrypt;
import com.pizza.wechat.message.event.MessageNotify;
import com.pizza.wechat.util.MessageUtil;

/**
 * 接收授权方推送过来的任何消息事件
 * 
 * @ClassName : OSMessageNotify
 * @author : emmy.cheng
 * @date : 2015年11月8日 下午4:47:02
 */
public class OSMessageNotify {
	private Map<String, String> requestMap;
	private MessageNotify service;

	public OSMessageNotify(MessageNotify service, HttpServletRequest request) {
		try {
			this.service = service;
			this.requestMap = MessageUtil.decryptMsg(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回加密后的xml内容 如需加密
	 * 处理推送事件
	 * @return
	 * @throws Exception
	 */
	public String process() throws Exception {
		String respMessage = "";
		String msgType = requestMap.get("MsgType");
		String openId = requestMap.get("FromUserName");
		String appId = requestMap.get("ToUserName");
		String isEncrypt = requestMap.get("encrypt_type");
		String nonce = requestMap.get("nonce");
		String timestamp = requestMap.get("timestamp");
		// 文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String content = requestMap.get("Content");// 文本消息内容
			respMessage = service.text(appId, openId, content, requestMap);
		}
		// 图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			respMessage = service.image(appId, openId, requestMap);
		}
		// 地理位置消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			respMessage = service.location(appId, openId, requestMap);
		}
		// 链接消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			respMessage = service.link(appId, openId, requestMap);
		}
		// 音频消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			respMessage = service.voice(appId, openId, requestMap);
		}
		// 事件推送
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = requestMap.get("Event");
			// 订阅
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				String eventKey = requestMap.get("EventKey");
				// 用户扫描二维码认证进行绑定认证
				if (eventKey.startsWith(MessageUtil.EVENT_TYPE_BIND_QRSCAN)) {
					// String Ticket = requestMap.get("Ticket"); 扫码返回的令牌
				}
				respMessage = service.subscribe(appId, openId, requestMap);
			} else if (eventType.equals(MessageUtil.EVENT_TYPE_QRSCAN)) {// 扫描带参数二维码
				// String Ticket = requestMap.get("Ticket");
				String eventKey = requestMap.get("EventKey");
				String param = eventKey.replace(
						MessageUtil.EVENT_TYPE_BIND_QRSCAN, "");
				respMessage = service.qrscan(appId, openId, param, requestMap);
			}
			// 取消订阅
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				service.unSubscribe(appId, openId, requestMap);
			}
			// 自定义菜单点击事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// 事件KEY值，与创建自定义菜单时指定的KEY值对应
				// String eventKey = requestMap.get("EventKey");
				/*
				 * if (eventKey.equals("10")) { respContent = "我的菜单项被点击！"; }
				 * else if (eventKey.equals("21")) { respContent = "自定义菜单项被点击！";
				 * }
				 */
				respMessage = service.click(appId, openId, requestMap);
			} else {
				respMessage = service.event(appId, openId, requestMap);
			}
		}
		WXBizMsgCrypt pc = new WXBizMsgCrypt(OpenAccountConfig.getInstance()
				.getToken(), OpenAccountConfig.getInstance()
				.getEncodingaeskey(), OpenAccountConfig.getInstance()
				.getAppId());
		if ("aes".equalsIgnoreCase(isEncrypt)) {// 加密模式已加密的方式返回内容
			respMessage = pc.encryptMsg(respMessage, timestamp, nonce);
		}
		return respMessage;
	}

}
