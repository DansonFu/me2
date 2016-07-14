package com.pizza.wechat.emoji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pizza.wechat.util.Utils;

/**
 * html元素获取帮助类
 * 
 * @ClassName : WebUtils
 * @author : emmy.cheng
 * @date : 2015年11月27日 下午12:55:09
 */
public class WebUtils {
	/**
	 * 获取元素表达式
	 * 
	 * @param elementName
	 * @return
	 */
	public static Pattern element(final String elementName) {
		return Pattern.compile("<\\s*" + elementName + "\\s+([^>]*)\\s*>",
				Pattern.CASE_INSENSITIVE);
	}

	/**
	 * 获取元素属性表达式
	 * 
	 * @param attrName
	 * @return
	 */
	public static Pattern attr(String attrName) {
		return Pattern.compile(attrName
				+ "=(\"|'|&quot;|&apos;)?(.+)(\"|'|&quot;|&apos;)?",
				Pattern.CASE_INSENSITIVE);
	}

	/**
	 * 获取对应元素集合
	 * 
	 * @param html
	 * @param elementName
	 * @return
	 */
	public static List<String> elements(String html, final String elementName) {
		List<String> eles = new ArrayList<String>();
		if (!Utils.isEmpty(html)) {
			if (Utils.isEmpty(elementName))
				throw new NullPointerException("tagName is not empty!");
			Pattern e = element(elementName);
			Matcher list = e.matcher(html);
			while (list.find()) {
				eles.add(list.group());
			}
		}
		return eles;
	}

	/**
	 * 获取对应元素属性集合
	 * 
	 * @param html
	 * @param elementName
	 * @param attrName
	 * @return
	 */
	public static List<Element> attrs(String html, final String elementName,
			final String attrName) {
		if (Utils.isEmpty(attrName))
			throw new NullPointerException("attrName is not empty!");
		List<Element> attrs = new ArrayList<Element>();
		List<String> eles = elements(html, elementName);
		for (String string : eles) {
			Element element = new Element();
			element.setHtml(string);
			Pattern e = attr(attrName);
			Matcher list = e.matcher(string);
			Map<String, Attribute> attrMap = new HashMap<String, Attribute>();
			while (list.find()) {
				String attr = list.group(2);
				Attribute value = new Attribute(attrName, attr, list.group());
				attrMap.put(attrName, value);
			}
			element.setAttr(attrMap);
			attrs.add(element);
		}
		return attrs;
	}
}
