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
     * 学校经度
     */
    private String schoolLongtitude;

    /**
     * 纬度
     */
    private String schoolLatitude;

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
     * @return 学校经度
     */
    public String getSchoolLongtitude() {
        return schoolLongtitude;
    }

    /**
     * @param schoolLongtitude 
	 *            学校经度
     */
    public void setSchoolLongtitude(String schoolLongtitude) {
        this.schoolLongtitude = schoolLongtitude;
    }

    /**
     * @return 纬度
     */
    public String getSchoolLatitude() {
        return schoolLatitude;
    }

    /**
     * @param schoolLatitude 
	 *            纬度
     */
    public void setSchoolLatitude(String schoolLatitude) {
        this.schoolLatitude = schoolLatitude;
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