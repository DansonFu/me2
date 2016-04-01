package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 消息所有者
     */
    private Integer customerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 蜜图ID
     */
    private Integer pid;
    private Picture picture;
    /**
     * 消息类型：1、申请解蜜消息 2、解蜜达成 3、点赞 4、踩 5、评论
     */
    private String type;

    /**
     * 发送消息人者
     */
    private Integer proposer;
    private Customer customer;
    
    /**
     * 是否已处理:0、未处理  1、已处理
     */
    private String processed;

    /**
     * 消息内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 帖子所有者
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            帖子所有者
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param string 
	 *            创建时间
     */
    public void setCreateTime(Date string) {
        this.createTime = string;
    }

    /**
     * @return 蜜图ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            蜜图ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 消息类型：
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            消息类型：
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 帖子查看者
     */
    public Integer getProposer() {
        return proposer;
    }

    /**
     * @param proposer 
	 *            帖子查看者
     */
    public void setProposer(Integer proposer) {
        this.proposer = proposer;
    }

    /**
     * @return 是否已处理:0、未处理  1、已处理
     */
    public String getProcessed() {
        return processed;
    }

    /**
     * @param processed 
	 *            是否已处理:0、未处理  1、已处理
     */
    public void setProcessed(String processed) {
        this.processed = processed;
    }

    /**
     * @return 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    
}