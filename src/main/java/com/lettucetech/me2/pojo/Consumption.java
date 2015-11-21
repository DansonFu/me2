package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Consumption implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消费ID
     */
    private Integer consumptionId;

    /**
     * 蜜图文件key
     */
    private String metooImgKey;

    /**
     * 消费者ID
     */
    private Integer customerId;

    /**
     * 消费商店ID
     */
    private Integer shopId;

    /**
     * 消费者当前经度
     */
    private Float latitude;

    /**
     * 消费者当前纬度
     */
    private Float longtitude;

    /**
     * 消费者当前精度
     */
    private Float precision;

    /**
     * 消费金额
     */
    private Integer cost;

    /**
     * 本次消费记录是否需要匿名
     */
    private Boolean isPrivate;

    /**
     * 参数一
     */
    private String param1;

    /**
     * 参数二
     */
    private String param2;

    /**
     * 参数三
     */
    private String param3;

    /**
     * 参数四
     */
    private String param4;

    /**
     * 参数五
     */
    private String param5;

    /**
     * 蜜图是否生成
     */
    private Boolean isMetooGenerated;

    /**
     * 蜜图初次创建时间
     */
    private Date createTime;

    /**
     * 参考消费ID
     */
    private Integer referId;

    /**
     * @return 消费ID
     */
    public Integer getConsumptionId() {
        return consumptionId;
    }

    /**
     * @param consumptionId 
	 *            消费ID
     */
    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    /**
     * @return 蜜图文件key
     */
    public String getMetooImgKey() {
        return metooImgKey;
    }

    /**
     * @param metooImgKey 
	 *            蜜图文件key
     */
    public void setMetooImgKey(String metooImgKey) {
        this.metooImgKey = metooImgKey;
    }

    /**
     * @return 消费者ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            消费者ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 消费商店ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * @param shopId 
	 *            消费商店ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * @return 消费者当前经度
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude 
	 *            消费者当前经度
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return 消费者当前纬度
     */
    public Float getLongtitude() {
        return longtitude;
    }

    /**
     * @param longtitude 
	 *            消费者当前纬度
     */
    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * @return 消费者当前精度
     */
    public Float getPrecision() {
        return precision;
    }

    /**
     * @param precision 
	 *            消费者当前精度
     */
    public void setPrecision(Float precision) {
        this.precision = precision;
    }

    /**
     * @return 消费金额
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * @param cost 
	 *            消费金额
     */
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    /**
     * @return 本次消费记录是否需要匿名
     */
    public Boolean getIsPrivate() {
        return isPrivate;
    }

    /**
     * @param isPrivate 
	 *            本次消费记录是否需要匿名
     */
    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * @return 参数一
     */
    public String getParam1() {
        return param1;
    }

    /**
     * @param param1 
	 *            参数一
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * @return 参数二
     */
    public String getParam2() {
        return param2;
    }

    /**
     * @param param2 
	 *            参数二
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * @return 参数三
     */
    public String getParam3() {
        return param3;
    }

    /**
     * @param param3 
	 *            参数三
     */
    public void setParam3(String param3) {
        this.param3 = param3;
    }

    /**
     * @return 参数四
     */
    public String getParam4() {
        return param4;
    }

    /**
     * @param param4 
	 *            参数四
     */
    public void setParam4(String param4) {
        this.param4 = param4;
    }

    /**
     * @return 参数五
     */
    public String getParam5() {
        return param5;
    }

    /**
     * @param param5 
	 *            参数五
     */
    public void setParam5(String param5) {
        this.param5 = param5;
    }

    /**
     * @return 蜜图是否生成
     */
    public Boolean getIsMetooGenerated() {
        return isMetooGenerated;
    }

    /**
     * @param isMetooGenerated 
	 *            蜜图是否生成
     */
    public void setIsMetooGenerated(Boolean isMetooGenerated) {
        this.isMetooGenerated = isMetooGenerated;
    }

    /**
     * @return 蜜图初次创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            蜜图初次创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 参考消费ID
     */
    public Integer getReferId() {
        return referId;
    }

    /**
     * @param referId 
	 *            参考消费ID
     */
    public void setReferId(Integer referId) {
        this.referId = referId;
    }
}