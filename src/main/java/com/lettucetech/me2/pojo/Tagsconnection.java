package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Tagsconnection implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer tagsId;
    
    private Tagshot tagshot;
    
    private String tagslistId;
   
    public Integer getId() {
        return id;
    }

    public Tagshot getTagshot() {
		return tagshot;
	}

	public void setTagshot(Tagshot tagshot) {
		this.tagshot = tagshot;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagsId() {
        return tagsId;
    }

    public void setTagsId(Integer tagsId) {
        this.tagsId = tagsId;
    }

    public String getTagslistId() {
        return tagslistId;
    }

    public void setTagslistId(String tagslistId) {
        this.tagslistId = tagslistId;
    }
}