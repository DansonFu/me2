package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Picturehot implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer pid;
    
    private Picture picture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
    
    
}