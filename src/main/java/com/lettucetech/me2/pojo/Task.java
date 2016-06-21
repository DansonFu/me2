package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String title;

    /**
     * 任务说明
     */
    private String content;

    /**
     * 任务类型,1 系统任务, 2 个性任务
     */
    private Integer tasktype;

    /**
     * 1,长期任务 2,一次性任务
     */
    private Integer taskstyle;

    /**
     * 任务发布时间
     */
    private Date createTime;

    /**
     * 任务过期时间
     */
    private Date lastTime;

    /**
     * 消费的糖块
     */
    private Integer custom;

    /**
     * 奖励的糖块
     */
    private Integer award;

    /**
     * 是否逻辑删除
     */
    private Integer del;
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
     * @return 任务名称
     */
    public String getTitle() {
        return title;
    }

    public Integer getDel() {
        return del;
    }
    /**
     * @param title 
	 *            任务名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 任务说明
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            任务说明
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 任务类型,1 系统任务, 2 个性任务
     */
    public Integer getTasktype() {
        return tasktype;
    }

    /**
     * @param tasktype 
	 *            任务类型,1 系统任务, 2 个性任务
     */
    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    /**
     * @return 1,长期任务 2,一次性任务
     */
    public Integer getTaskstyle() {
        return taskstyle;
    }

    /**
     * @param taskstyle 
	 *            1,长期任务 2,一次性任务
     */
    public void setTaskstyle(Integer taskstyle) {
        this.taskstyle = taskstyle;
    }

    /**
     * @return 任务发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            任务发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 任务过期时间
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime 
	 *            任务过期时间
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * @return 消费的糖块
     */
    public Integer getCustom() {
        return custom;
    }

    /**
     * @param custom 
	 *            消费的糖块
     */
    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    /**
     * @return 奖励的糖块
     */
    public Integer getAward() {
        return award;
    }

    /**
     * @param award 
	 *            奖励的糖块
     */
    public void setAward(Integer award) {
        this.award = award;
    }
    public void setDel(Integer del) {
        this.del = del;
    }
}