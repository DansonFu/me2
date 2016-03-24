package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Recommend implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer tagId;

    private String tagname;

    private Integer hits;

    private Integer sort;

    private Integer acount;

    private Integer mefriends;

    private Date lasttime;

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
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
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
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getQiniukey() {
        return qiniukey;
    }

    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }
}