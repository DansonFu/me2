package com.pizza.wechat.request.bean;

public class CustomerServiceBean {
	private String kfAccount;
	private String kfNick;
	private Integer kfId;
	private String kfHeadimgurl;
	public String getKfAccount() {
		return kfAccount;
	}
	public void setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
	}
	public String getKfNick() {
		return kfNick;
	}
	public void setKfNick(String kfNick) {
		this.kfNick = kfNick;
	}
	public Integer getKfId() {
		return kfId;
	}
	public void setKfId(Integer kfId) {
		this.kfId = kfId;
	}
	public String getKfHeadimgurl() {
		return kfHeadimgurl;
	}
	public void setKfHeadimgurl(String kfHeadimgurl) {
		this.kfHeadimgurl = kfHeadimgurl;
	}
	public CustomerServiceBean(String kfAccount, String kfNick, Integer kfId,
			String kfHeadimgurl) {
		this.kfAccount = kfAccount;
		this.kfNick = kfNick;
		this.kfId = kfId;
		this.kfHeadimgurl = kfHeadimgurl;
	}

}
