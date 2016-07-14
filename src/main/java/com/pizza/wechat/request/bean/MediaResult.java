package com.pizza.wechat.request.bean;
/**
 * 上传媒体返回数据
 * @ClassName : MediaResult
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午7:11:12
 */
public class MediaResult {
	private String mediaId;
	private String url;
	public MediaResult() {
	}
	
	public MediaResult(String mediaId, String url) {
		this.mediaId = mediaId;
		this.url = url;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
