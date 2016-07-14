package com.pizza.wechat.request;

/**
 * 获取（刷新）授权公众号的令牌 有效期 2小时
 * 
 * @copyright gold-tech
 * @author emmy
 * @date 2015年9月2日 上午10:46:09
 */
public class AuthorizerToken {
	/**
	 * 授权方令牌
	 */
	private String accessToken;
	/**
	 * 刷新令牌用以换取 授权方令牌在失效时 公众号授权时由微信返回
	 */
	private String refreshAccessToken;
	/**
	 * 授权方appid
	 */
	private String authorizerAppId;
	

	public AuthorizerToken(String accessToken, String refreshAccessToken,
			String authorizerAppId) {
		this.accessToken = accessToken;
		this.refreshAccessToken = refreshAccessToken;
		this.authorizerAppId = authorizerAppId;
	}

	public String getAuthorizerAppId() {
		return authorizerAppId;
	}

	public void setAuthorizerAppId(String authorizerAppId) {
		this.authorizerAppId = authorizerAppId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshAccessToken() {
		return refreshAccessToken;
	}

	public void setRefreshAccessToken(String refreshAccessToken) {
		this.refreshAccessToken = refreshAccessToken;
	}

}
