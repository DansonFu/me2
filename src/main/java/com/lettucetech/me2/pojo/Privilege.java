package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Privilege implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 特权名称
     */
    private String privilege;

    /**
     * 特权类型
     */
    private Integer privilegeType;

    /**
     * 对应等级
     */
    private Integer grade;

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
     * @return 特权名称
     */
    public String getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege 
	 *            特权名称
     */
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    /**
     * @return 特权类型
     */
    public Integer getPrivilegeType() {
        return privilegeType;
    }

    /**
     * @param privilegeType 
	 *            特权类型
     */
    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }

    /**
     * @return 对应等级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade 
	 *            对应等级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}