package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Member extends MemberKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}