package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer deviceId;

    /**
     * 设备类型：0、二维码1、iBeacon
     */
    private Boolean type;

    /**
     * 有权限使用该设备的商户角色：0、店主；1、店员2、客户
     */
    private Boolean userRole;

    /**
     * 二维码：该二维码所对应万象图片url的fileid；iBeacon：设备id
     */
    private String param1;

    /**
     * 二维码：未定义；iBeacon：UUID
     */
    private String param2;

    /**
     * 二维码：未定义；iBeacon：Major
     */
    private String param3;

    /**
     * 二维码：未定义；iBeacon：Minor
     */
    private String param4;

    private Integer shopId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return 设备类型：0、二维码1、iBeacon
     */
    public Boolean getType() {
        return type;
    }

    /**
     * @param type 
	 *            设备类型：0、二维码1、iBeacon
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * @return 有权限使用该设备的商户角色：0、店主；1、店员2、客户
     */
    public Boolean getUserRole() {
        return userRole;
    }

    /**
     * @param userRole 
	 *            有权限使用该设备的商户角色：0、店主；1、店员2、客户
     */
    public void setUserRole(Boolean userRole) {
        this.userRole = userRole;
    }

    /**
     * @return 二维码：该二维码所对应万象图片url的fileid；iBeacon：设备id
     */
    public String getParam1() {
        return param1;
    }

    /**
     * @param param1 
	 *            二维码：该二维码所对应万象图片url的fileid；iBeacon：设备id
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }

    /**
     * @return 二维码：未定义；iBeacon：UUID
     */
    public String getParam2() {
        return param2;
    }

    /**
     * @param param2 
	 *            二维码：未定义；iBeacon：UUID
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * @return 二维码：未定义；iBeacon：Major
     */
    public String getParam3() {
        return param3;
    }

    /**
     * @param param3 
	 *            二维码：未定义；iBeacon：Major
     */
    public void setParam3(String param3) {
        this.param3 = param3;
    }

    /**
     * @return 二维码：未定义；iBeacon：Minor
     */
    public String getParam4() {
        return param4;
    }

    /**
     * @param param4 
	 *            二维码：未定义；iBeacon：Minor
     */
    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}