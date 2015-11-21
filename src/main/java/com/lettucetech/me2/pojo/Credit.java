package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Credit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer creditId;

    private Integer customerId;

    /**
     * 获得积分的具体场合
     */
    private Byte eventId;

    private Date createTime;

    private Integer amount;

    public Integer getCreditId() {
        return creditId;
    }

    public void setCreditId(Integer creditId) {
        this.creditId = creditId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 获得积分的具体场合
     */
    public Byte getEventId() {
        return eventId;
    }

    /**
     * @param eventId 
	 *            获得积分的具体场合
     */
    public void setEventId(Byte eventId) {
        this.eventId = eventId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}