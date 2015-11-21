package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class MemberKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer customerId;

    private Integer shopId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}