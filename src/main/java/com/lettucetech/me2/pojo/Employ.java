package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Employ extends EmployKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户角色：0、店主；1、店员
     */
    private Boolean role;

    private Date createTime;

    /**
     * @return 商户角色：0、店主；1、店员
     */
    public Boolean getRole() {
        return role;
    }

    /**
     * @param role 
	 *            商户角色：0、店主；1、店员
     */
    public void setRole(Boolean role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}