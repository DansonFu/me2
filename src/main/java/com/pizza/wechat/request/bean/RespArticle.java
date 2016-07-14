package com.pizza.wechat.request.bean;
/**
 * 回复图文消息
 * @ClassName : RespArticle
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午5:52:30
 */
public class RespArticle extends DynaBean{
	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String url;
	private String picurl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	
}
