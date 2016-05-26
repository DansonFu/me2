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
}