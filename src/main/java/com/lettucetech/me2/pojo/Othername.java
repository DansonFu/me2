package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Othername implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 学校id
     */
    private Integer schoolid;

    /**
     * 学校别名
     */
    private String othername;

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
     * @return 学校id
     */
    public Integer getSchoolid() {
        return schoolid;
    }

    /**
     * @param schoolid 
	 *            学校id
     */
    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }

    /**
     * @return 学校别名
     */
    public String getOthername() {
        return othername;
    }

    /**
     * @param othername 
	 *            学校别名
     */
    public void setOthername(String othername) {
        this.othername = othername;
    }
}