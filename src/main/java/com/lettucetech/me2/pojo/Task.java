package com.lettucetech.me2.pojo;

import java.io.Serializable;

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
}