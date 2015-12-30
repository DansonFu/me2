package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Gameface implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 图片的7牛key
     */
    private String qiniukey;

    /**
     * 请求解蜜文字
     */
    private String content;

    /**
     * 蜜图ID
     */
    private Integer pid;

    /**
     * 发贴人
     */
    private Integer issuer;

    /**
     * 请求解蜜者
     */
    private Integer proposer;
    private Customer customer;
    /**
     * 是否已处理:0、未处理  1、已处理
     */
    private String processed;

    /**
     * 申请时间
     */
    private Date createTime;

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 图片的7牛key
     */
    public String getQiniukey() {
        return qiniukey;
    }

    /**
     * @param qiniukey 
	 *            图片的7牛key
     */
    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }

    /**
     * @return 请求解蜜文字
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            请求解蜜文字
     */
    public void setContent(String content) {
        this.content = content;
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
     * @return 发贴人
     */
    public Integer getIssuer() {
        return issuer;
    }

    /**
     * @param issuer 
	 *            发贴人
     */
    public void setIssuer(Integer issuer) {
        this.issuer = issuer;
    }

    /**
     * @return 请求解蜜者
     */
    public Integer getProposer() {
        return proposer;
    }

    /**
     * @param proposer 
	 *            请求解蜜者
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
     * @return 申请时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            申请时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}