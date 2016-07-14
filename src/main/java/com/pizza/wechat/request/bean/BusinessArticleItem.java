package com.pizza.wechat.request.bean;

public class BusinessArticleItem {
	/**
	 * 素材主键
	 */
	private String media_id;
	/**
	 * 素材内容
	 */
	private BusinessArticleContent content;
	/**
	 * 更新时间戳
	 */
	private Long update_time;
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public BusinessArticleContent getContent() {
		return content;
	}
	public void setContent(BusinessArticleContent content) {
		this.content = content;
	}
	public Long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Long update_time) {
		this.update_time = update_time;
	}
	
}
