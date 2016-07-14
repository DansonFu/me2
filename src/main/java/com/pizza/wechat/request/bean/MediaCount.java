package com.pizza.wechat.request.bean;
/**
 * 微信媒体数量
 * @ClassName : ArticleCount
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午6:23:50
 */
public class MediaCount {
	/**
	 * 语音总数
	 */
	private Integer voiceCount;
	/**
	 * 视频总数
	 */
	private Integer videoCount;
	/**
	 * 图片总数
	 */
	private Integer imageCount;
	/**
	 * 图文总数
	 */
	private Integer newsCount;
	
	public MediaCount(Integer voiceCount, Integer videoCount,
			Integer imageCount, Integer newsCount) {
		this.voiceCount = voiceCount;
		this.videoCount = videoCount;
		this.imageCount = imageCount;
		this.newsCount = newsCount;
	}
	public Integer getVoiceCount() {
		return voiceCount;
	}
	public void setVoiceCount(Integer voiceCount) {
		this.voiceCount = voiceCount;
	}
	public Integer getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(Integer videoCount) {
		this.videoCount = videoCount;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	public Integer getNewsCount() {
		return newsCount;
	}
	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}
	
}
