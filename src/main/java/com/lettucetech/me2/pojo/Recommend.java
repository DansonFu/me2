package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Recommend implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer tagId;

    private String tagName;

    private Integer hits;

    private Integer sort;

    private Integer acount;

    private Integer mefriends;

    private Date lastTime;

    private String qiniukey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagname() {
        return tagName;
    }

    public void setTagname(String tagName) {
        this.tagName = tagName;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getAcount() {
        return acount;
    }

    public void setAcount(Integer acount) {
        this.acount = acount;
    }

    public Integer getMefriends() {
        return mefriends;
    }

    public void setMefriends(Integer mefriends) {
        this.mefriends = mefriends;
    }

    public Date getLasttime() {
        return lastTime;
    }

    public void setLasttime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getQiniukey() {
        return qiniukey;
    }

    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }
}