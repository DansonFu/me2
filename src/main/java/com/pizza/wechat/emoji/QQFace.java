package com.pizza.wechat.emoji;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 微信使用qqface
 * 
 * @ClassName : QQFaceCache
 * @author : emmy.cheng
 * @date : 2015年11月27日 下午1:08:22
 */
public class QQFace {
	private static String URL = "https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/";
	public final static String[] FACE_KEY = { "微笑", "撇嘴", "色", "发呆", "得意",
			"流泪", "害羞", "闭嘴", "睡", "大哭", "尴尬", "发怒", "调皮", "呲牙", "惊讶", "难过",
			"酷", "冷汗", "抓狂", "吐", "偷笑", "可爱", "白眼", "傲慢", "饥饿", "困", "惊恐",
			"流汗", "憨笑", "大兵", "奋斗", "咒骂", "疑问", "嘘", "晕", "折磨", "衰", "骷髅",
			"敲打", "再见", "擦汗", "抠鼻", "鼓掌", "糗大了", "坏笑", "左哼哼", "右哼哼", "哈欠",
			"鄙视", "委屈", "快哭了", "阴险", "亲亲", "吓", "可怜", "菜刀", "西瓜", "啤酒", "篮球",
			"乒乓", "咖啡", "饭", "猪头", "玫瑰", "凋谢", "示爱", "爱心", "心碎", "蛋糕", "闪电",
			"炸弹", "刀", "足球", "瓢虫", "便便", "月亮", "太阳", "礼物", "拥抱", "强", "弱",
			"握手", "胜利", "抱拳", "勾引", "拳头", "差劲", "爱你", "NO", "OK", "爱情", "飞吻",
			"跳跳", "发抖", "怄火", "转圈", "磕头", "回头", "跳绳", "挥手", "激动", "街舞", "献吻",
			"左太极", "右太极" };
	private static QQFace cache = new QQFace();
	private static List<QQFaceMap> faceMaps = new ArrayList<QQFaceMap>(105);
	static {
		for (int i = 0; i < FACE_KEY.length; i++) {
			String url = URL + i + ".gif";
			faceMaps.add(new QQFaceMap(FACE_KEY[i], "/" + FACE_KEY[i], url,
					FACE_KEY[i]));
		}
	}

	private QQFace() {

	}

	public static QQFace instance() {
		return cache;
	}
	
	public List<QQFaceMap> getDataMap() {
		return faceMaps;
	}

	public void add(QQFaceMap map) {
		faceMaps.add(map);
	}

	public int size() {
		return faceMaps.size();
	}

	public QQFaceMap get(String key) {
		for (int i = 0; i < faceMaps.size(); i++) {
			if (faceMaps.get(i).getKey().equals(key)) {
				return faceMaps.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取qqface图片列表 如[0]<img class="wechat-emoji" id="emoji_0"
	 * src="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/0.gif"
	 * alt="微笑" title="/微笑">
	 * 
	 * @param maps
	 * @return
	 */
	public List<String> getQQFaces() {
		List<String> li = new ArrayList<String>(faceMaps.size());
		for (int i = 0; i < faceMaps.size(); i++) {
			QQFaceMap map = faceMaps.get(i);
			String face = "<img class=\"wechat-emoji\" id=\"{0}\" src=\"{1}\" alt=\"{2}\" title=\"{3}\">";
			face = MessageFormat.format(face, map.getUrl(), "emoji_" + i,
					map.getKey(), map.getDesc());
			li.add(face);
		}
		return li;
	}

	/**
	 * <img class="wechat-emoji" id="emoji_0"
	 * src="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/0.gif"
	 * alt="微笑" title="/微笑"> 将图片转换成微信可识别的qq表情内容
	 * 此图片必须包含key属性key属性必须是包含在qq表情里面的内容key 如 微笑==/微笑
	 */
	public static String convertKey(String html, String key) {
		List<Element> list = WebUtils.attrs(html, "img", key);
		String result = html;
		int i = 0;
		for (Element element : list) {
			result = result.replaceFirst(element.getHtml(), "{" + i + "}");
			Map<String, Attribute> attr = element.getAttr();
			Iterator<String> keys = attr.keySet().iterator();
			while (keys.hasNext()) {
				String name = keys.next();
				Attribute attribute = attr.get(name);
				result = result.replace("{" + i + "}",
						"/" + attribute.getValue());
			}
			i++;
		}
		return result;
	}

	/**
	 * 将图片转换成微信可识别的qq表情内容 此图片必须包含alt属性alt属性必须是包含在qq表情里面的内容alt 如 微笑==/微笑
	 * 
	 * @param html
	 * @return
	 */
	public static String convertAlt(String html) {
		return convertKey(html, "alt");
	}

	/**
	 * 解析微信返回的qq表情内容 并转换成{num}占位符形式对应FACE_KEY下标
	 */
	public static String parseFace(String message) {
		int i = 0;
		String regex = "^.*/.(?!>){1,3}(?!>).*$";
		if (message.matches(regex)) {
			for (String string : FACE_KEY) {
				if (!message.matches(regex)) {
					return message;
				}
				message = message.replaceAll("\\/" + string, "{" + i + "}");
				i++;
			}
		}
		return message;
	}

	/**
	 * 解析微信返回的qq表情内容 并转换成固定图片格式 <img class="wechat-emoji" id="emoji_0"
	 * src="https://res.wx.qq.com/mpres/htmledition/images/icon/emotion/0.gif"
	 * alt="微笑" title="/微笑">
	 * 
	 * @param message
	 * @return
	 */
	public static String parse(String message) {
		message = parseFace(message);
		String regex = "^.*\\{\\d+\\}.*$";
		if (message.matches(regex)) {
			for (int i = 0; i < FACE_KEY.length; i++) {
				message = message.replaceAll("\\{" + i + "\\}",
						"<img class=\"wechat-emoji\" id=\"emoji_" + i
								+ "\" src=\"" + URL + i
								+ ".gif\" alt=\"微笑\" title=\"/微笑\">");
			}
		}
		return message;
	}
}
