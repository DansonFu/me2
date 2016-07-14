package com.pizza.wechat.request;

import org.springframework.util.StringUtils;

import com.pizza.wechat.APIException;
import com.pizza.wechat.util.Utils;

/**
 * 微信API接口请求参数
 * 
 * @ClassName : URLParam
 * @author : emmy.cheng
 * @Description : TODO
 * @date : 2015年10月22日 下午11:46:48
 */
public class URLParam {
	/**
	 * 接口地址
	 */
	private String url;
	/**
	 * accessToken接口凭证
	 */
	private String accessToken;

	public URLParam(String url) {
		this.url = url;
	}

	public URLParam(String url, String accessToken) {
		this.url = url;
		this.accessToken = accessToken;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 返回转换后的接口地址
	 * 
	 * @return
	 */
	public String requestURL() {
		if (StringUtils.isEmpty(url)) {
			new APIException("api request url is null!");
		}
		return url.replace("ACCESS_TOKEN", Utils.toString(accessToken));
	}
}
