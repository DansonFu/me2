package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtRoleUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleUserId;

    /**
     * id
     */
    private Integer roleId;

    private Integer userId;

    /**
     * ¼
     */
    private Date createTime;

    private Date updateTime;

    public Integer getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Integer roleUserId) {
        this.roleUserId = roleUserId;
    }

    /**
     * @return id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId 
	 *            id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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