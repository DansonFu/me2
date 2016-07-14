package com.pizza.wechat.request.bean;

import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.util.ArticleUtils;
import com.pizza.wechat.util.Utils;

/**
 * 图文消息bean
 * 
 * @ClassName : BusinessArticle
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午5:55:44
 */
public class BusinessArticle extends DynaBean {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标题(必填)
	 */
	private String title;
	/**
	 * 封面图片id(必填)
	 */
	private String thumb_media_id;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 说明备注，单图文有效
	 */
	private String digest;
	/**
	 * 是否显示封面图片默认不显示
	 */
	private Integer show_cover_pic;
	/**
	 * 内容(必填)1M（2W字内）
	 */
	private String content;
	/**
	 * 阅读原文地址（去空格）
	 */
	private String content_source_url;

	/**
	 * 图文消息预览地址
	 */
	private String url;

	public BusinessArticle() {
	}

	public BusinessArticle(String title, String thumb_media_id, String author,
			String digest, Integer show_cover_pic, String content,
			String content_source_url, String url) {
		if (!ArticleUtils.validateContent(content)) {
			throw new APIRuntimeException(
					"content not empty and maxlength 20000 or max size 1MB!");
		}
		if (Utils.isEmpty(title) || Utils.isEmpty(thumb_media_id)) {
			throw new APIRuntimeException(
					"{title,thumb_media_id} is not empty!");
		}
		this.title = title;
		this.thumb_media_id = thumb_media_id;
		this.author = author;
		this.digest = digest;
		this.show_cover_pic = show_cover_pic;
		this.content = content;
		this.content_source_url = Utils.toString(content_source_url).trim();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (Utils.isEmpty(title)) {
			throw new APIRuntimeException("title is not empty!");
		}
		this.title = title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		if (Utils.isEmpty(thumb_media_id)) {
			throw new APIRuntimeException("thumb_media_id is not empty!");
		}
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getShow_cover_pic() {
		return Utils.toInt(show_cover_pic);
	}

	public void setShow_cover_pic(Integer show_cover_pic) {
		this.show_cover_pic = Utils.toInt(show_cover_pic);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if (!ArticleUtils.validateContent(content)) {
			throw new APIRuntimeException(
					"content not empty and maxlength 20000 or max size 1MB!");
		}
		this.content = content;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = Utils.toString(content_source_url).trim();
	}

}
