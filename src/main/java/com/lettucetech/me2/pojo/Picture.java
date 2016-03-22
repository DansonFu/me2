package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Picture implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 蜜图id
     */
    private Integer pid;

    /**
     * 7牛的图片KEY 
     */
    private String qiniukey;

    /**
     * 发布人
     */
    private Integer customerId;

    private Customer customer;
    /**
     * 发布时间
     */
    private Date creatTime;

    /**
     * 热度
     */
    private Integer hits;

    /**
     * 心情
     */
    private String mood;
    private String bmood;
    public String getBmood() {
		return bmood;
	}

	public void setBmood(String bmood) {
		this.bmood = bmood;
	}

	/**
     * a:a面   b:b面
     */
    private String front;

    /**
     * B面类型:
1、图片
2、文字
3、视频
4、音频
     */
    private String type;

    /**
     * 如果front字段为B，此字段代表所从属的A面id
     */
    private Integer parentId;

    /**
     * 顶贴人数
     */
    private Integer agree;

    /**
     * 踩贴人数
     */
    private Integer disagree;

    /**
     * 位置标题
     */
    private String locationTitle;

    /**
     * 地理位置
     */
    private String locationContent;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 标签 逗号隔开
     */
    private String tags;

    /**
     * 匿名：0 否 1 是
     */
    private String anonymity;

    /**
     * 解蜜游戏ID
     */
    private Integer gameId;

    /**
     * B面
     */
    private Picture bpicture;
    
    /**
     * 评论数
     */
    private int commentCount;
    /**
     * 逻辑删除0否 1 删除
     */
    private String del;

    /**
     * 是否推荐0 :否 1:是
     */
    private String recommend;
    
    /**
     * 被@的用户
     */
    private Integer atCustomerId;
    private Customer atCustomer;
    /**
     * 是否已查看过 0:否 1:是
     */
    private String atSeen;
    
    public Integer getAtCustomerId() {
		return atCustomerId;
	}

	public Customer getAtCustomer() {
		return atCustomer;
	}

	public void setAtCustomer(Customer atCustomer) {
		this.atCustomer = atCustomer;
	}

	public void setAtCustomerId(Integer atCustomerId) {
		this.atCustomerId = atCustomerId;
	}

	public String getAtSeen() {
		return atSeen;
	}

	public void setAtSeen(String atSeen) {
		this.atSeen = atSeen;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	/**
     * @return 蜜图id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            蜜图id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 7牛的图片KEY 
     */
    public String getQiniukey() {
        return qiniukey;
    }

    /**
     * @param qiniukey 
	 *            7牛的图片KEY 
     */
    public void setQiniukey(String qiniukey) {
        this.qiniukey = qiniukey;
    }

    /**
     * @return 发布人
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            发布人
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 发布时间
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * @param creatTime 
	 *            发布时间
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * @return 热度
     */
    public Integer getHits() {
        return hits;
    }

    /**
     * @param hits 
	 *            热度
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * @return 心情
     */
    public String getMood() {
        return mood;
    }

    /**
     * @param mood 
	 *            心情
     */
    public void setMood(String mood) {
        this.mood = mood;
    }

    /**
     * @return a:a面   b:b面
     */
    public String getFront() {
        return front;
    }

    /**
     * @param front 
	 *            a:a面   b:b面
     */
    public void setFront(String front) {
        this.front = front;
    }

    /**
     * @return B面类型:
1、图片
2、文字
3、视频
4、音频
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            B面类型:
1、图片
2、文字
3、视频
4、音频
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 如果front字段为B，此字段代表所从属的A面id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId 
	 *            如果front字段为B，此字段代表所从属的A面id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return 顶贴人数
     */
    public Integer getAgree() {
        return agree;
    }

    /**
     * @param agree 
	 *            顶贴人数
     */
    public void setAgree(Integer agree) {
        this.agree = agree;
    }

    /**
     * @return 踩贴人数
     */
    public Integer getDisagree() {
        return disagree;
    }

    /**
     * @param disagree 
	 *            踩贴人数
     */
    public void setDisagree(Integer disagree) {
        this.disagree = disagree;
    }

    /**
     * @return 位置标题
     */
    public String getLocationTitle() {
        return locationTitle;
    }

    /**
     * @param locationTitle 
	 *            位置标题
     */
    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    /**
     * @return 地理位置
     */
    public String getLocationContent() {
        return locationContent;
    }

    /**
     * @param locationContent 
	 *            地理位置
     */
    public void setLocationContent(String locationContent) {
        this.locationContent = locationContent;
    }

    /**
     * @return 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude 
	 *            经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude 
	 *            纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return 标签 逗号隔开
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags 
	 *            标签 逗号隔开
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return 匿名：0 否 1 是
     */
    public String getAnonymity() {
        return anonymity;
    }

    /**
     * @param anonymity 
	 *            匿名：0 否 1 是
     */
    public void setAnonymity(String anonymity) {
        this.anonymity = anonymity;
    }

    /**
     * @return 解蜜游戏ID
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * @param gameId 
	 *            解蜜游戏ID
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

	public Picture getBpicture() {
		return bpicture;
	}

	public void setBpicture(Picture bpicture) {
		this.bpicture = bpicture;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    
}