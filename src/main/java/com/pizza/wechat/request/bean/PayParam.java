package com.pizza.wechat.request.bean;

/**
 * 支付参数 字符类型默认长度都是32位 除个别有自己的长度限制
 * 
 * @ClassName : PayParam
 * @author : emmy.cheng
 * @date : 2015年11月16日 下午4:38:28
 */
public class PayParam {
	/**
	 * 商品描述，128个字节以内
	 */
	private String body;
	/**
	 * 支付金额
	 */
	private Integer totalFee;
	/**
	 * 附加信息127，回调时原样返回 【不必须】
	 */
	private String attach;
	/**
	 * 商户订单号必须唯一 32个字节以下 【不必须】
	 */
	private String outTradeNo;
	/**
	 * 订单生产者ip地址
	 */
	private String spbillCreateIp;
	/**
	 * 交易起始时间14 格式yyyyMMddHHmmss 【不必须】
	 */
	private String timeStart;
	/**
	 * 交易结束时间 14格式yyyyMMddHHmmss 【不必须】
	 */
	private String timeExipre;
	/**
	 * 设备号 【不必须】
	 */
	private String deviceInfo;
	/**
	 * 商品详情8192 【不必须】
	 */
	private String detail;
	/**
	 * 商品费用 【不必须】
	 */
	private String productId;
	/**
	 * 商品ID 【不必须】 tradeType=NATIVE时必填
	 */
	private String goodsTag;
	/**
	 * 指定支付方式 【不必须】no_credit--指定不能使用信用卡支付
	 */
	private String limitPay;
	/**
	 * 用户标识128 【不必须】
	 */
	private String openid;
	/**
	 * 货币类型16【不必须】 默认人民币：CNY
	 */
	private String feeType;

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExipre() {
		return timeExipre;
	}

	public void setTimeExipre(String timeExipre) {
		this.timeExipre = timeExipre;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}
