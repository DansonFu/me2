package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 密图id
     */
    private Integer pid;

    /**
     * 评论者id
     */
    private Integer customerId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date creatTime;

    /**
     * 7牛key
     */
    private String qiniukey;

    /**
     * @return 评论id
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * @param commentId 
	 *            评论id
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * @return 密图id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            密图id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 评论者id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            评论者id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 评论时间
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime 
	 *            评论时间
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return 7牛key
     */
    public String getQiniukey() {
        return qiniukey;
    }

    /**
     * @param qiniukey 
	 *            7牛key
     */
    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }
}