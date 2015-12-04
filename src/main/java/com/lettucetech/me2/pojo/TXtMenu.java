package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class TXtMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer mId;

    private String name;

    private String type;

    private String code;

    private Integer pId;

    private Integer num;

    /**
     * ¼
     */
    private Date addTime;

    private Date updateTime;

    /**
     * 描述
     */
    private String note;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;
    
    private Boolean expanded=true;
    
    private Boolean loaded=true;
    
    private String icons;

    public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return ¼
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * @param addTime 
	 *            ¼
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 描述
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note 
	 *            描述
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level 
	 *            层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return 是否为叶子节点
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf 
	 *            是否为叶子节点
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLoaded() {
		return loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}
    
}