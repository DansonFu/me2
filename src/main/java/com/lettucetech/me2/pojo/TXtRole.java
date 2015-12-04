package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private String name;

    private String detail;

    /**
     * ¼
     */
    private Date createTime;

    private Date updateTime;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return ¼
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            ¼
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}