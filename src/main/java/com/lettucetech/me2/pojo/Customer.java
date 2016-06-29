package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer customerId;

    /**
     * 在不同公众号之间标识用户。只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    private Boolean subscribe;

    /**
     * 用户的昵称
     */
    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Byte sex;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 用户所在国家
     */
    private String country;

    /**
     * 用户所在省份
     */
    private String province;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String headimgurl;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private Date subscribeTime;

    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark;

    /**
     * 用户所在的分组ID
     */
    private Integer groupid;

    /**
     * 用户真实姓名
     */
    private String realname;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户目前的总积分
     */
    private Integer totalCredit;

    /**
     * 用户当前正在与之交互的门店
     */
    private Integer currentShop;

    /**
     * 用户当前经度
     */
    private Float currentLatitude;

    /**
     * 用户当前纬度
     */
    private Float currentLongtitude;

    /**
     * 用户当前精度
     */
    private Float currentPrecision;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 蜜图呢称，做为登录使用
     */
    private String username;

    /**
     * 登录来源：0：本平台1：微信平台2：QQ平台3：新浪平台
     */
    private String source;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 第三方登录返回的uid
     */
    private String sourceid;

    /**
     * 删除标志0、正常使用1、逻辑删除 
     */
    private String del;

    /**
     * 是否内部用户 0 否，1 是
     */
    private String inneruser;

    /**
     * 手机唯一标识，APP消息推送
     */
    private String apppushtoken;

    /**
     * 用户所在学校
     */
    private String school;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 普通账户
     */
    private Integer generalAccount;

    /**
     * 众筹账户
     */
    private Integer raisePublicAccounts;
    /**
     * 是否补全个人信息 0未补全 1补全
     */
    private Integer isinfoComplete;
    /**
     * 昵称是否重复 0未重复 1重复
     */
    private Integer isNameDuplicated;
    /**
     * 入学年份
     */
    private String yearEnterSchool;
    
    public Integer getIsinfoComplete() {
        return isinfoComplete;
    }

    public void setIsinfoComplete(Integer isinfoComplete) {
        this.isinfoComplete = isinfoComplete;
    }
    public Integer getIsNameDuplicated() {
        return isNameDuplicated;
    }

    public void setIsNameDuplicated(Integer isNameDuplicated) {
        this.isNameDuplicated = isNameDuplicated;
    }
    public String getYearEnterSchool() {
        return yearEnterSchool;
    }

    public void setYearEnterSchool(String yearEnterSchool) {
        this.yearEnterSchool = yearEnterSchool;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 在不同公众号之间标识用户。只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * @param unionid 
	 *            在不同公众号之间标识用户。只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * @return 用户的标识，对当前公众号唯一
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * @param openid 
	 *            用户的标识，对当前公众号唯一
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * @return 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    public Boolean getSubscribe() {
        return subscribe;
    }

    /**
     * @param subscribe 
	 *            用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     */
    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * @return 用户的昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname 
	 *            用户的昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * @param sex 
	 *            用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * @return 用户所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 
	 *            用户所在城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 用户所在国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country 
	 *            用户所在国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return 用户所在省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province 
	 *            用户所在省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return 用户的语言，简体中文为zh_CN
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language 
	 *            用户的语言，简体中文为zh_CN
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    /**
     * @param headimgurl 
	 *            用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    /**
     * @return 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * @param subscribeTime 
	 *            用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    /**
     * @return 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 用户所在的分组ID
     */
    public Integer getGroupid() {
        return groupid;
    }

    /**
     * @param groupid 
	 *            用户所在的分组ID
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    /**
     * @return 用户真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname 
	 *            用户真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 用户目前的总积分
     */
    public Integer getTotalCredit() {
        return totalCredit;
    }

    /**
     * @param totalCredit 
	 *            用户目前的总积分
     */
    public void setTotalCredit(Integer totalCredit) {
        this.totalCredit = totalCredit;
    }

    /**
     * @return 用户当前正在与之交互的门店
     */
    public Integer getCurrentShop() {
        return currentShop;
    }

    /**
     * @param currentShop 
	 *            用户当前正在与之交互的门店
     */
    public void setCurrentShop(Integer currentShop) {
        this.currentShop = currentShop;
    }

    /**
     * @return 用户当前经度
     */
    public Float getCurrentLatitude() {
        return currentLatitude;
    }

    /**
     * @param currentLatitude 
	 *            用户当前经度
     */
    public void setCurrentLatitude(Float currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    /**
     * @return 用户当前纬度
     */
    public Float getCurrentLongtitude() {
        return currentLongtitude;
    }

    /**
     * @param currentLongtitude 
	 *            用户当前纬度
     */
    public void setCurrentLongtitude(Float currentLongtitude) {
        this.currentLongtitude = currentLongtitude;
    }

    /**
     * @return 用户当前精度
     */
    public Float getCurrentPrecision() {
        return currentPrecision;
    }

    /**
     * @param currentPrecision 
	 *            用户当前精度
     */
    public void setCurrentPrecision(Float currentPrecision) {
        this.currentPrecision = currentPrecision;
    }

    /**
     * @return 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 
	 *            手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 蜜图呢称，做为登录使用
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 
	 *            蜜图呢称，做为登录使用
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 登录来源：0：本平台1：微信平台2：QQ平台3：新浪平台
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source 
	 *            登录来源：0：本平台1：微信平台2：QQ平台3：新浪平台
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime 
	 *            创建时间
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 
	 *            修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 最后登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime 
	 *            最后登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return 第三方登录返回的uid
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            第三方登录返回的uid
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    /**
     * @return 删除标志0、正常使用1、逻辑删除 
     */
    public String getDel() {
        return del;
    }

    /**
     * @param del 
	 *            删除标志0、正常使用1、逻辑删除 
     */
    public void setDel(String del) {
        this.del = del;
    }

    /**
     * @return 是否内部用户 0 否，1 是
     */
    public String getInneruser() {
        return inneruser;
    }

    /**
     * @param inneruser 
	 *            是否内部用户 0 否，1 是
     */
    public void setInneruser(String inneruser) {
        this.inneruser = inneruser;
    }

    /**
     * @return 手机唯一标识，APP消息推送
     */
    public String getApppushtoken() {
        return apppushtoken;
    }

    /**
     * @param apppushtoken 
	 *            手机唯一标识，APP消息推送
     */
    public void setApppushtoken(String apppushtoken) {
        this.apppushtoken = apppushtoken;
    }

    /**
     * @return 用户所在学校
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school 
	 *            用户所在学校
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return 积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score 
	 *            积分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @return 等级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade 
	 *            等级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * @return 普通账户
     */
    public Integer getGeneralAccount() {
        return generalAccount;
    }

    /**
     * @param generalAccount 
	 *            普通账户
     */
    public void setGeneralAccount(Integer generalAccount) {
        this.generalAccount = generalAccount;
    }

    /**
     * @return 众筹账户
     */
    public Integer getRaisePublicAccounts() {
        return raisePublicAccounts;
    }

    /**
     * @param raisePublicAccounts 
	 *            众筹账户
     */
    public void setRaisePublicAccounts(Integer raisePublicAccounts) {
        this.raisePublicAccounts = raisePublicAccounts;
    }
}