package com.pizza.wechat.request.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * 微信接口请求数据bean
 * @ClassName : DynaBean
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午5:50:25
 */
public class DynaBean implements Serializable, DataMap {

	/** 
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toJSONString() {
		return JSONObject.fromObject(this).toString();
	}

}
