package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class CustomerPrivilege implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 特权id
     */
    private Integer privilegeid;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
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
     * @return 特权id
     */
    public Integer getPrivilegeid() {
        return privilegeid;
    }

    /**
     * @param privilegeid 
	 *            特权id
     */
    public void setPrivilegeid(Integer privilegeid) {
        this.privilegeid = privilegeid;
    }
}