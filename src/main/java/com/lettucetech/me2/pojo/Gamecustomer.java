package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Gamecustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 蜜图ID
     */
    private Integer pid;

    /**
     * 用户id，如果customer_id等-1代表对所有用户解蜜,等于0的时候拒绝所有用户解密
     */
    private Integer customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 蜜图ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            蜜图ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 用户id，如果customer_id等-1代表对所有用户解蜜,等于0的时候拒绝所有用户解密
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            用户id，如果customer_id等-1代表对所有用户解蜜,等于0的时候拒绝所有用户解密
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}