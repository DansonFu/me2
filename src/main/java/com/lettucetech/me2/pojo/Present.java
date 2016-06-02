package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Present implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 送礼物的用户id
     */
    private Integer customerId;

    /**
     * 收礼物的用户id
     */
    private Integer presentCustomerId;

    /**
     * 礼物的类型
     */
    private Integer presentType;

    /**
     * 送礼物的时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 送礼物的用户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            送礼物的用户id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 收礼物的用户id
     */
    public Integer getPresentCustomerId() {
        return presentCustomerId;
    }

    /**
     * @param presentCustomerId 
	 *            收礼物的用户id
     */
    public void setPresentCustomerId(Integer presentCustomerId) {
        this.presentCustomerId = presentCustomerId;
    }

    /**
     * @return 礼物的类型
     */
    public Integer getPresentType() {
        return presentType;
    }

    /**
     * @param presentType 
	 *            礼物的类型
     */
    public void setPresentType(Integer presentType) {
        this.presentType = presentType;
    }

    /**
     * @return 送礼物的时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            送礼物的时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}