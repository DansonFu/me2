package com.pizza.wechat.emoji;

/**
 * 微信qqface
 * 
 * @ClassName : EmojiMap
 * @author : emmy.cheng
 * @date : 2015年11月26日 下午10:46:23
 */
public class QQFaceMap {
	/**
	 * 目标key
	 */
	private String key;
	/**
	 * 目标值/+key
	 */
	private String value;
	/**
	 * 目标url
	 */
	private String url;
	/**
	 * 目标说明
	 */
	private String desc;

	public QQFaceMap(String key, String value, String url, String desc) {
		this.key = key;
		this.value = value;
		this.url = url;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
