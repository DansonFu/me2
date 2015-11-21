package com.lettucetech.me2.pojo;

import java.io.Serializable;

public class Shop implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer shopId;

    private Integer poiId;

    /**
     * 门店名称(仅为商户名,如:国美、麦当劳,不应包 含地区、店号等信息,错误示例:北京国美)
     */
    private String businessName;

    /**
     * 分店名称(不应包含地区信息、不应与门店名重复, 错误示例:北京王府井店)
     */
    private String branchName;

    /**
     * 门店所在的省份(直辖市填城市名,如:北京市) 
     */
    private String province;

    /**
     * 门店所在的城市
     */
    private String city;

    /**
     * 门店所在地区
     */
    private String district;

    /**
     * 门店所在的详细街道地址(不要填写省市信息)
     */
    private String address;

    /**
     * 门店的电话(纯数字,区号、分机号均由“-”隔开)
     */
    private String telephone;

    /**
     * 门店的类型(详细分类参见分类附表,不同级分类用 “,”隔开,如:美食,川菜,火锅) 
     */
    private String categories;

    /**
     * 坐标类型,1 为火星坐标(目前只能选 1) 
     */
    private Boolean offsetType;

    /**
     * 门店所在地理位置的经度
     */
    private Float longitude;

    /**
     * 门店所在地理位置的纬度(经纬度均为火星坐标,最好选用腾讯地图标记的坐标)
     */
    private Float latitude;

    /**
     * 图片列表,url 形式,可以有多张图片,尺寸为 640*340px。必须为上一接口生成的 url
     */
    private String photoList;

    /**
     * 推荐品,餐厅可为推荐菜;酒店为推荐套房;景点为推荐游玩景点等,针对自己行业的推荐内容
     */
    private String recommend;

    /**
     * 特色服务,如免费 wifi,免费停车,送货上门等商户 能提供的特色功能或服务
     */
    private String special;

    /**
     * 商户简介,主要介绍商户信息等
     */
    private String introduction;

    /**
     * 营业时间,24 小时制表示,用“-”连接,如 8:00-20:00
     */
    private String openTime;

    /**
     * 人均价格,大于 0 的整数
     */
    private Integer avgPrice;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPoiId() {
        return poiId;
    }

    public void setPoiId(Integer poiId) {
        this.poiId = poiId;
    }

    /**
     * @return 门店名称(仅为商户名,如:国美、麦当劳,不应包 含地区、店号等信息,错误示例:北京国美)
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName 
	 *            门店名称(仅为商户名,如:国美、麦当劳,不应包 含地区、店号等信息,错误示例:北京国美)
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return 分店名称(不应包含地区信息、不应与门店名重复, 错误示例:北京王府井店)
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName 
	 *            分店名称(不应包含地区信息、不应与门店名重复, 错误示例:北京王府井店)
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return 门店所在的省份(直辖市填城市名,如:北京市) 
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province 
	 *            门店所在的省份(直辖市填城市名,如:北京市) 
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return 门店所在的城市
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 
	 *            门店所在的城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 门店所在地区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district 
	 *            门店所在地区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return 门店所在的详细街道地址(不要填写省市信息)
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            门店所在的详细街道地址(不要填写省市信息)
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return 门店的电话(纯数字,区号、分机号均由“-”隔开)
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone 
	 *            门店的电话(纯数字,区号、分机号均由“-”隔开)
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return 门店的类型(详细分类参见分类附表,不同级分类用 “,”隔开,如:美食,川菜,火锅) 
     */
    public String getCategories() {
        return categories;
    }

    /**
     * @param categories 
	 *            门店的类型(详细分类参见分类附表,不同级分类用 “,”隔开,如:美食,川菜,火锅) 
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * @return 坐标类型,1 为火星坐标(目前只能选 1) 
     */
    public Boolean getOffsetType() {
        return offsetType;
    }

    /**
     * @param offsetType 
	 *            坐标类型,1 为火星坐标(目前只能选 1) 
     */
    public void setOffsetType(Boolean offsetType) {
        this.offsetType = offsetType;
    }

    /**
     * @return 门店所在地理位置的经度
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude 
	 *            门店所在地理位置的经度
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return 门店所在地理位置的纬度(经纬度均为火星坐标,最好选用腾讯地图标记的坐标)
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude 
	 *            门店所在地理位置的纬度(经纬度均为火星坐标,最好选用腾讯地图标记的坐标)
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return 图片列表,url 形式,可以有多张图片,尺寸为 640*340px。必须为上一接口生成的 url
     */
    public String getPhotoList() {
        return photoList;
    }

    /**
     * @param photoList 
	 *            图片列表,url 形式,可以有多张图片,尺寸为 640*340px。必须为上一接口生成的 url
     */
    public void setPhotoList(String photoList) {
        this.photoList = photoList;
    }

    /**
     * @return 推荐品,餐厅可为推荐菜;酒店为推荐套房;景点为推荐游玩景点等,针对自己行业的推荐内容
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * @param recommend 
	 *            推荐品,餐厅可为推荐菜;酒店为推荐套房;景点为推荐游玩景点等,针对自己行业的推荐内容
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * @return 特色服务,如免费 wifi,免费停车,送货上门等商户 能提供的特色功能或服务
     */
    public String getSpecial() {
        return special;
    }

    /**
     * @param special 
	 *            特色服务,如免费 wifi,免费停车,送货上门等商户 能提供的特色功能或服务
     */
    public void setSpecial(String special) {
        this.special = special;
    }

    /**
     * @return 商户简介,主要介绍商户信息等
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction 
	 *            商户简介,主要介绍商户信息等
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * @return 营业时间,24 小时制表示,用“-”连接,如 8:00-20:00
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * @param openTime 
	 *            营业时间,24 小时制表示,用“-”连接,如 8:00-20:00
     */
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    /**
     * @return 人均价格,大于 0 的整数
     */
    public Integer getAvgPrice() {
        return avgPrice;
    }

    /**
     * @param avgPrice 
	 *            人均价格,大于 0 的整数
     */
    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }
}