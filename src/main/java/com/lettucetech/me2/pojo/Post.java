package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer postId;

    private Integer customerId;

    private Integer serviceId;

    /**
     * 用户对该商品的评介
     */
    private String content;

    /**
     * 用户是否为该商品点赞
     */
    private Boolean isFavored;

    /**
     * 用户是否转发该商品
     */
    private Boolean isForwarded;

    private Date createTime;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return 用户对该商品的评介
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            用户对该商品的评介
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 用户是否为该商品点赞
     */
    public Boolean getIsFavored() {
        return isFavored;
    }

    /**
     * @param isFavored 
	 *            用户是否为该商品点赞
     */
    public void setIsFavored(Boolean isFavored) {
        this.isFavored = isFavored;
    }

    /**
     * @return 用户是否转发该商品
     */
    public Boolean getIsForwarded() {
        return isForwarded;
    }

    /**
     * @param isForwarded 
	 *            用户是否转发该商品
     */
    public void setIsForwarded(Boolean isForwarded) {
        this.isForwarded = isForwarded;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}