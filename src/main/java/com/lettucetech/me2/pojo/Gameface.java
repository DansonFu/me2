package com.lettucetech.me2.pojo;

import java.io.Serializable;

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
}