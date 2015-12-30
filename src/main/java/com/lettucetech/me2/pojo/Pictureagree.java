package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Pictureagree implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 蜜图ID
     */
    private Integer pid;

    /**
     * 赞踩人
     */
    private Integer customerId;

    /**
     * 0:踩  1 :赞
     */
    private String type;

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
     * @return 赞踩人
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            赞踩人
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 0:踩  1 :赞
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            0:踩  1 :赞
     */
    public void setType(String type) {
        this.type = type;
    }
}