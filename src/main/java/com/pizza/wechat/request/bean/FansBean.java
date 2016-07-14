package com.pizza.wechat.request.bean;

import java.util.Date;

public class FansBean {
	/**
	 * 关注时间
	 */
	private Date subscribeTime;
	/**
	 * unionid
	 */
	private String unionId;
	/**
	 * 微信设置语言
	 */
	private String language;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 身份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 头像
	 */
	private String headImgUrl;
	/**
	 * openid
	 */
	private String openId;
	/**
	 * 是否关注0未关注
	 */
	private Integer subscribe;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 用户所在分组
	 */
	private Integer groupId;
	

	

	public FansBean(Date subscribeTime, String unionId, String language,
			Integer sex, String country, String province, String city,
			String headImgUrl, String openId, Integer subscribe, String remark,
			Integer groupId, String nickName) {
		this.subscribeTime = subscribeTime;
		this.unionId = unionId;
		this.language = language;
		this.sex = sex;
		this.country = country;
		this.province = province;
		this.city = city;
		this.headImgUrl = headImgUrl;
		this.openId = openId;
		this.subscribe = subscribe;
		this.remark = remark;
		this.groupId = groupId;
		this.nickName = nickName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private String nickName;
}
