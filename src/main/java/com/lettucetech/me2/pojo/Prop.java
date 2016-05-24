package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Prop implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 道具名称
     */
    private String title;

    /**
     * 道具类型,1 小红花,2 彩虹,3 炸弹
     */
    private Integer proptype;

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
     * @return 道具名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            道具名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 道具类型,1 小红花,2 彩虹,3 炸弹
     */
    public Integer getProptype() {
        return proptype;
    }

    /**
     * @param proptype 
	 *            道具类型,1 小红花,2 彩虹,3 炸弹
     */
    public void setProptype(Integer proptype) {
        this.proptype = proptype;
    }
}