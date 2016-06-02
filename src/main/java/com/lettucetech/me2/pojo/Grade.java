package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 积分
     */
    private Integer score;

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
     * @return 等级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade 
	 *            等级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * @return 积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @param score 
	 *            积分
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}