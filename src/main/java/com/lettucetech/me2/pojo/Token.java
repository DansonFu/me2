package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 发帖时道具的名称
     */
    private String title;

    /**
     * 发帖时道具的类型 1,投票令 2,悬赏令
     */
    private Integer tokentype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 发帖时道具的名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            发帖时道具的名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 发帖时道具的类型 1,投票令 2,悬赏令
     */
    public Integer getTokentype() {
        return tokentype;
    }

    /**
     * @param tokentype 
	 *            发帖时道具的类型 1,投票令 2,悬赏令
     */
    public void setTokentype(Integer tokentype) {
        this.tokentype = tokentype;
    }
}