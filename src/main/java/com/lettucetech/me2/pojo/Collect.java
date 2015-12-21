package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 收藏人
     */
    private Integer customerId;

    /**
     * 收藏蜜图
     */
    private Integer pid;

    /**
     * 收藏时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 收藏人
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            收藏人
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 收藏蜜图
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            收藏蜜图
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 收藏时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            收藏时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}