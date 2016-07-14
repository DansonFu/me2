package com.pizza.wechat.emoji;

/**
 * html属性
 * 
 * @ClassName : Attribute
 * @author : emmy.cheng
 * @date : 2015年11月27日 下午12:34:51
 */
public class Attribute {
	/**
	 * 属性名
	 */
	private String name;
	/**
	 * 属性值
	 */
	private String value;
	/**
	 * 属性html内容
	 */
	private String html;

	public Attribute() {
	}

	public Attribute(String name, String value, String html) {
		this.name = name;
		this.value = value;
		this.html = html;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
