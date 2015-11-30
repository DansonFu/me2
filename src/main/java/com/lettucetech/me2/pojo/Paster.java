package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Paster implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pasterId;

    /**
     * 贴纸标题
     */
    private String title;

    /**
     * 内容说明
     */
    private String account;

    private String qiniukey;

    /**
     * 是否封面
     */
    private String cover;

    /**
     * 顺序
     */
    private Integer num;

    /**
     * 从属ID
     */
    private Integer parentId;

    private Date createTime;

    /**
     * 删除标记
     */
    private String del;
    
//    private List<Paster> children;

    public Integer getPasterId() {
        return pasterId;
    }

    public void setPasterId(Integer pasterId) {
        this.pasterId = pasterId;
    }

    /**
     * @return 贴纸标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            贴纸标题
     */
    public void setTitle(String title) {
        this.title = title;
    }



    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getQiniukey() {
        return qiniukey;
    }

    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }

    /**
     * @return 是否封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover 
	 *            是否封面
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * @return 顺序
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num 
	 *            顺序
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return 从属ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId 
	 *            从属ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 删除标记
     */
    public String getDel() {
        return del;
    }

    /**
     * @param del 
	 *            删除标记
     */
    public void setDel(String del) {
        this.del = del;
    }

//	public List<Paster> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<Paster> children) {
//		this.children = children;
//	}
    
}