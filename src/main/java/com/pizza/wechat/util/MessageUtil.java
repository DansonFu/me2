package com.pizza.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.pizza.wechat.OpenAccountConfig;
import com.pizza.wechat.aes.WXBizMsgCrypt;
import com.pizza.wechat.message.resp.Article;
import com.pizza.wechat.message.resp.MusicMessage;
import com.pizza.wechat.message.resp.NewsMessage;
import com.pizza.wechat.message.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 * 
 * @author rango
 * @date 2013-05-19
 */
public class MessageUtil {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	/**
	 * 事件类型用户扫描带参数二维码微信绑定
	 */
	public static final String EVENT_TYPE_BIND_QRSCAN = "qrscene_";
	/**
	 * 用户扫描微信验证码已关注过的
	 */
	public static final String EVENT_TYPE_QRSCAN = "SCAN";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return String
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	/**
	 * 解析解密后的xml内容
	 * 
	 * @param xmltext
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @return String
	 */
	public static Map<String, String> getElement(String xmltext)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xmltext);
		InputSource is = new InputSource(sr);
		org.w3c.dom.Document document = db.parse(is);
		org.w3c.dom.Element root = document.getDocumentElement();
		Map<String, String> result = new HashMap<String, String>();
		if (root != null) {
			NodeList node = root.getChildNodes();
			if (node != null) {
				for (int i = 0; i < node.getLength(); i++) {
					String value = node.item(i).getTextContent();
					String key = node.item(i).getNodeName();
					result.put(key, value);
				}
			}
		}
		return result;
	}

	public static Map<String, String> decryptMsg(HttpServletRequest request)
			throws Exception {
		String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		Map<String, String> requestMap = MessageUtil.parseXml(request);
		WXBizMsgCrypt pc = new WXBizMsgCrypt(OpenAccountConfig.getInstance()
				.getToken(), OpenAccountConfig.getInstance()
				.getEncodingaeskey(), OpenAccountConfig.getInstance()
				.getAppId());
		String isEncrypt = requestMap.get("encrypt_type");
		String nonce = requestMap.get("nonce");
		String signature = requestMap.get("msg_signature");
		String timestamp = requestMap.get("timestamp");
		/*
		 * String msgType = requestMap.get("MsgType"); String openId =
		 * requestMap.get("FromUserName"); String appId =
		 * requestMap.get("ToUserName");
		 */
		if ("aes".equalsIgnoreCase(isEncrypt)) {// 采用加密的模式接收回复消息需要对消息进行解密
			String encrypt = requestMap.get("Encrypt");
			String fromXML = String.format(xmlFormat, encrypt);
			// 解密xml数据
			String result = pc.decryptMsg(signature, timestamp, nonce, fromXML);
			Map<String, String> resultMap = MessageUtil.getElement(result);
			requestMap = resultMap;
		}
		return requestMap;
	}
}
