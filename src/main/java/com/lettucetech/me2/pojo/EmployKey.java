package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class EmployKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer serverId;

    private Integer shopId;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}