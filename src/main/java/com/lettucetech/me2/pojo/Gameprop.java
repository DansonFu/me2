package com.lettucetech.me2.pojo;

import java.io.Serializable;
import java.util.Date;

public class Gameprop implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer customerId;

    /**
     * 密图id
     */
    private Integer pid;

    /**
     * 小红花数量
     */
    private Integer flower;

    /**
     * 彩虹数量
     */
    private Integer rainbow;

    /**
     * 炸弹数量
     */
    private Integer bomb;

    /**
     * 报警的数量
     */
    private Integer alarm;

    /**
     * 送道具的时间
     */
    private Date createTime;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 用户id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId 
	 *            用户id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return 密图id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            密图id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return 小红花数量
     */
    public Integer getFlower() {
        return flower;
    }

    /**
     * @param flower 
	 *            小红花数量
     */
    public void setFlower(Integer flower) {
        this.flower = flower;
    }

    /**
     * @return 彩虹数量
     */
    public Integer getRainbow() {
        return rainbow;
    }

    /**
     * @param rainbow 
	 *            彩虹数量
     */
    public void setRainbow(Integer rainbow) {
        this.rainbow = rainbow;
    }

    /**
     * @return 炸弹数量
     */
    public Integer getBomb() {
        return bomb;
    }

    /**
     * @param bomb 
	 *            炸弹数量
     */
    public void setBomb(Integer bomb) {
        this.bomb = bomb;
    }

    /**
     * @return 报警的数量
     */
    public Integer getAlarm() {
        return alarm;
    }

    /**
     * @param alarm 
	 *            报警的数量
     */
    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    /**
     * @return 送道具的时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            送道具的时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}