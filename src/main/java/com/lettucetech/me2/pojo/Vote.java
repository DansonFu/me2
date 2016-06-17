package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Vote implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id]
     */
    private Integer customerid;

    /**
     * 投票a面
     */
    private String va;

    /**
     * 投票b面
     */
    private String vb;

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
     * @return 用户id]
     */
    public Integer getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            用户id]
     */
    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    /**
     * @return 投票a面
     */
    public String getVa() {
        return va;
    }

    /**
     * @param va 
	 *            投票a面
     */
    public void setVa(String va) {
        this.va = va;
    }

    /**
     * @return 投票b面
     */
    public String getVb() {
        return vb;
    }

    /**
     * @param vb 
	 *            投票b面
     */
    public void setVb(String vb) {
        this.vb = vb;
    }
}