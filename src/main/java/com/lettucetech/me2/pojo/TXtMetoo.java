package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtMetoo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 发布人
     */
    private Integer userId;
    private TXtUser user;

    /**
     * 密图或评评论id
     */
    private Integer metoo;
    
    private Picture1 picture;
    
    private Comment comment;

    /**
     * 发布时间
     */
    private Date creatTime;

    /**
     * 0:密图 1:评论
     */
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 发布人
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId 
	 *            发布人
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return 密图或评评论id
     */
    public Integer getMetoo() {
        return metoo;
    }

    /**
     * @param metoo 
	 *            密图或评评论id
     */
    public void setMetoo(Integer metoo) {
        this.metoo = metoo;
    }

    /**
     * @return 发布时间
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime 
	 *            发布时间
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return 0:密图 1:评论
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            0:密图 1:评论
     */
    public void setState(String state) {
        this.state = state;
    }

	public Picture1 getPicture() {
		return picture;
	}

	public void setPicture(Picture1 picture) {
		this.picture = picture;
	}
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public TXtUser getUser() {
		return user;
	}

	public void setUser(TXtUser user) {
		this.user = user;
	}
    
}