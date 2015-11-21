package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Server implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer serverId;

    private String unionid;

    private String openid;

    private Boolean subscribe;

    private String nickname;

    private Byte sex;

    private String city;

    private String country;

    private String province;

    private String language;

    private String headimgurl;

    private Date subscribeTime;

    /**
     * 商户目前正在交互
     */
    private String remark;

    private Integer groupid;

    private String realname;

    private String password;

    /**
     * 商户当前正在与之交互的门店
     */
    private Integer currentShop;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    /**
     * @return 商户目前正在交互
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            商户目前正在交互
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 商户当前正在与之交互的门店
     */
    public Integer getCurrentShop() {
        return currentShop;
    }

    /**
     * @param currentShop 
	 *            商户当前正在与之交互的门店
     */
    public void setCurrentShop(Integer currentShop) {
        this.currentShop = currentShop;
    }
}