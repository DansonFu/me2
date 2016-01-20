package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Taglist implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String qiniukey;

    /**
     * 顺序
     */
    private String num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 图片
     */
    public String getQiniukey() {
        return qiniukey;
    }

    /**
     * @param qiniukey 
	 *            图片
     */
    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }

    /**
     * @return 顺序
     */
    public String getNum() {
        return num;
    }

    /**
     * @param num 
	 *            顺序
     */
    public void setNum(String num) {
        this.num = num;
    }
}