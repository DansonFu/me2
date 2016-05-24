package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class School implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 学校的全称
     */
    private String schoolName;

    /**
     * 学校的地址
     */
    private String schoolAddress;

    /**
     * 学校的别名(简称)
     */
    private String schoolOtherName;

    /**
     * 所在的城市
     */
    private String city;

    /**
     * 所在的省
     */
    private String province;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 学校的全称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName 
	 *            学校的全称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return 学校的地址
     */
    public String getSchoolAddress() {
        return schoolAddress;
    }

    /**
     * @param schoolAddress 
	 *            学校的地址
     */
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    /**
     * @return 学校的别名(简称)
     */
    public String getSchoolOtherName() {
        return schoolOtherName;
    }

    /**
     * @param schoolOtherName 
	 *            学校的别名(简称)
     */
    public void setSchoolOtherName(String schoolOtherName) {
        this.schoolOtherName = schoolOtherName;
    }

    /**
     * @return 所在的城市
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 
	 *            所在的城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 所在的省
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province 
	 *            所在的省
     */
    public void setProvince(String province) {
        this.province = province;
    }
}