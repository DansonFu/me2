package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Advertis implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 广告的图片
     */
    private String adpicture;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * @return 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 广告的图片
     */
    public String getAdpicture() {
        return adpicture;
    }

    /**
     * @param adpicture 
	 *            广告的图片
     */
    public void setAdpicture(String adpicture) {
        this.adpicture = adpicture;
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
}