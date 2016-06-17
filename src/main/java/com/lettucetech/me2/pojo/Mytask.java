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
     * 任务类型,1系统任务 2个性任务
     */
    private Integer taskType;

    /**
     * 任务id
     */
    private Integer taskid;

    /**
     * 领取时间
     */
    private Date createTime;

    /**
     * 1,长期任务 2,一次性任务
     */
    private Integer taskstyle;

    /**
     * 是否完成,0否, 1是
     */
    private Integer complete;

    /**
     * 图片id
     */
    private Integer pid;

    /**
     * 提交任务时间
     */
    private Date lastTime;

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
     * @return 任务类型,1系统任务 2个性任务
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * @param taskType 
	 *            任务类型,1系统任务 2个性任务
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    /**
     * @return 任务id
     */
    public Integer getTaskid() {
        return taskid;
    }

    /**
     * @param taskid 
	 *            任务id
     */
    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
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
     * @return 是否完成,0否, 1是
     */
    public Integer getComplete() {
        return complete;
    }

    /**
     * @param complete 
	 *            是否完成,0否, 1是
     */
    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    /**
     * @return 图片id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            图片id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 提交任务时间
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime 
	 *            提交任务时间
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}