package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Mytask implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 任务名称
     */
    private String taskTitle;

    /**
     * 任务说明
     */
    private String taskContent;

    /**
     * 领取时间
     */
    private Date createTime;

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
     * @return 用户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            用户id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 任务类型
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * @param taskType 
	 *            任务类型
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * @return 任务名称
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * @param taskTitle 
	 *            任务名称
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /**
     * @return 任务说明
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * @param taskContent 
	 *            任务说明
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * @return 领取时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            领取时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}