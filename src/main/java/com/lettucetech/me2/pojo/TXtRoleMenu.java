package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleMenuId;

    private Integer roleId;

    private Integer mId;

    /**
     * ¼
     */
    private Date createTime;

    private Date updateTime;

    public Integer getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Integer roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
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