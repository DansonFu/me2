package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Attention implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 被关注用户的id
     */
    private Integer attentionCustomerId;

    /**
     * @return 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 用户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            用户id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 被关注用户的id
     */
    public Integer getAttentionCustomerId() {
        return attentionCustomerId;
    }

    /**
     * @param attentionCustomerId 
	 *            被关注用户的id
     */
    public void setAttentionCustomerId(Integer attentionCustomerId) {
        this.attentionCustomerId = attentionCustomerId;
    }
}