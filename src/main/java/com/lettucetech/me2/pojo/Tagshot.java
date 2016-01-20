package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Tagshot implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

	private String tag;

    private Integer acount;

    private Integer hits;

    private String qiniukey;

    private Integer mefriends;

    private Date lastTime;

    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getAcount() {
        return acount;
    }

    public void setAcount(Integer acount) {
        this.acount = acount;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getQiniukey() {
        return qiniukey;
    }

    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }

    public Integer getMefriends() {
        return mefriends;
    }

    public void setMefriends(Integer mefriends) {
        this.mefriends = mefriends;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}