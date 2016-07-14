package com.pizza.wechat.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pizza.wechat.emoji.Attribute;
import com.pizza.wechat.emoji.Element;
import com.pizza.wechat.emoji.WebUtils;

/**
 * 图文工具类
 * 
 * @ClassName : ArticleUtils
 * @author : emmy.cheng
 * @date : 2015年11月28日 下午5:29:44
 */
public class ArticleUtils {
	/**
	 * 校验图文消息图片地址正则
	 * 
	 * @return
	 */
	public static Pattern ctxPicUri() {
		return Pattern.compile("^http://mmbiz.qpic.cn/(.|/)+");
	}

	/**
	 * 校验图文上下文内容中的图片地址是否为微信服务器地址 否则无法现在在图文消息里面
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateCtxPic(String content) {
		List<Element> imgs = WebUtils.attrs(content, "img", "src");
		Pattern pic = ctxPicUri();
		for (Element element : imgs) {
			Attribute src = element.getAttr().get("src");
			String value = Utils.toString(src.getValue());
			Matcher e = pic.matcher(value);
			if (!e.find()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 验证上下文内容是否超过限制长度大小 最大长度20000字符，1M
	 * @param content
	 * @return
	 */
	public static boolean validateContent(String content) {
		if (Utils.isEmpty(content) || content.length() > 20000) {
			return false;
		}
		int size = content.getBytes().length / (1024 * 1024);
		return (size > 1);
	}
}
