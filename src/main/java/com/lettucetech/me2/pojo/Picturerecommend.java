package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Picturerecommend implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 蜜图id
     */
    private Integer pid;
    private Picture1 picture;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 有效期
     */
    private Date period;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 蜜图id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            蜜图id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort 
	 *            排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * @return 有效期
     */
    public Date getPeriod() {
        return period;
    }

    /**
     * @param period 
	 *            有效期
     */
    public void setPeriod(Date period) {
        this.period = period;
    }

	public Picture1 getPicture() {
		return picture;
	}

	public void setPicture(Picture1 picture) {
		this.picture = picture;
	}
    
}