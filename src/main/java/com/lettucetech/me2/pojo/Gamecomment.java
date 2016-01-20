package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Gamecomment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer customerId;

    private String comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}