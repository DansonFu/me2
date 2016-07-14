package com.pizza.wechat.emoji;

import java.util.Map;

/**
 * html元素
 * 
 * @ClassName : ElementAttrs
 * @author : emmy.cheng
 * @date : 2015年11月27日 下午12:31:00
 */
public class Element {
	private String html;
	private Map<String, Attribute> attr;

	public Element() {
	}

	public Element(String html, Map<String, Attribute> attr) {
		this.html = html;
		this.attr = attr;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Map<String, Attribute> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, Attribute> attr) {
		this.attr = attr;
	}

}
