package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer cardId;

    /**
     * 卡券的商户 logo
     */
    private String logoUrl;

    /**
     * 商户名字
     */
    private String brandName;

    /**
     * 券名,字数上限为 9 个汉字。(建 议涵盖卡券属性、服务及金额)
     */
    private String title;

    /**
     * 券名的副标题
     */
    private String subTitle;

    /**
     * 使用提醒。(一句话描述,展示在首页, 示例:请出示二维码核销卡券)
     */
    private String notice;

    /**
     * 使用说明，长文本描述,可以分行。
     */
    private String description;

    /**
     * 卡券失效时间
     */
    private Date effectiveDate;

    /**
     * 卡券失效时间
     */
    private Date expireDate;

    /**
     * 是否指定用户领取
     */
    private Boolean isCustomerAppointed;

    /**
     * 领取卡券原生页面是否可分享
     */
    private Boolean isSharable;

    /**
     * 卡券是否可转赠
     */
    private Boolean isTransferable;

    /**
     * 客服电话
     */
    private String servicePhone;

    private Integer shopId;

    private Integer serviceId;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    /**
     * @return 卡券的商户 logo
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl 
	 *            卡券的商户 logo
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * @return 商户名字
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName 
	 *            商户名字
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return 券名,字数上限为 9 个汉字。(建 议涵盖卡券属性、服务及金额)
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            券名,字数上限为 9 个汉字。(建 议涵盖卡券属性、服务及金额)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 券名的副标题
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * @param subTitle 
	 *            券名的副标题
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * @return 使用提醒。(一句话描述,展示在首页, 示例:请出示二维码核销卡券)
     */
    public String getNotice() {
        return notice;
    }

    /**
     * @param notice 
	 *            使用提醒。(一句话描述,展示在首页, 示例:请出示二维码核销卡券)
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * @return 使用说明，长文本描述,可以分行。
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            使用说明，长文本描述,可以分行。
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 卡券失效时间
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate 
	 *            卡券失效时间
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return 卡券失效时间
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * @param expireDate 
	 *            卡券失效时间
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * @return 是否指定用户领取
     */
    public Boolean getIsCustomerAppointed() {
        return isCustomerAppointed;
    }

    /**
     * @param isCustomerAppointed 
	 *            是否指定用户领取
     */
    public void setIsCustomerAppointed(Boolean isCustomerAppointed) {
        this.isCustomerAppointed = isCustomerAppointed;
    }

    /**
     * @return 领取卡券原生页面是否可分享
     */
    public Boolean getIsSharable() {
        return isSharable;
    }

    /**
     * @param isSharable 
	 *            领取卡券原生页面是否可分享
     */
    public void setIsSharable(Boolean isSharable) {
        this.isSharable = isSharable;
    }

    /**
     * @return 卡券是否可转赠
     */
    public Boolean getIsTransferable() {
        return isTransferable;
    }

    /**
     * @param isTransferable 
	 *            卡券是否可转赠
     */
    public void setIsTransferable(Boolean isTransferable) {
        this.isTransferable = isTransferable;
    }

    /**
     * @return 客服电话
     */
    public String getServicePhone() {
        return servicePhone;
    }

    /**
     * @param servicePhone 
	 *            客服电话
     */
    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}