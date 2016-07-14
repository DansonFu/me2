package com.pizza.wechat.request.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.OpenAccountConfig;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.AuthorizerToken;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;

/**
 * accesstoken 管理
 * 
 * @ClassName : AccessToken
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午5:03:09
 */
public class AccessTokenApi {
	/**
	 * 获取授权方accessToken
	 */
	private static final String PROXY_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=ACCESS_TOKEN";
	/**
	 * 获取第三方服务方accessToken
	 */
	private static final String SERVICE_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
	/**
	 * 第三方预授权码
	 */
	private static final String PRE_AUTH_CODE = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=ACCESS_TOKEN";

	private static final String AUTH_CODE_GET_ACCOUNT_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=";

	/**
	 * 根据授权时的刷新token换取授权方token
	 * 
	 * @param authAppId
	 *            授权方appid
	 * @param refreshToken
	 *            授权方刷新token
	 * @param serviceToken
	 *            开放平台token
	 * @return
	 * @throws APIException
	 */
	public AuthorizerToken refreshAuthToken(String authAppId,
			String refreshToken, String serviceToken) throws APIException {
		URLParam url = new URLParam(PROXY_ACCESS_TOKEN, serviceToken);
		JSONInput output = new JSONInput();
		output.add("component_appid", OpenAccountConfig.getInstance()
				.getAppId());
		output.add("authorizer_appid", authAppId);
		output.add("authorizer_refresh_token", refreshToken);
		JSONObject result = APIHttpConnect.post(url, output);
		return new AuthorizerToken(result.getString("authorizer_access_token"),
				result.getString("authorizer_refresh_token"), authAppId);
	}

	/**
	 * 通过授权码换取授权方信息
	 * 
	 * @param serviceToken
	 * @param authorizationCode
	 * @return
	 * @throws APIException
	 */
	public AuthorizerToken getAuthToken(String serviceToken,
			String authorizationCode) throws APIException {
		URLParam url = new URLParam(AUTH_CODE_GET_ACCOUNT_INFO + serviceToken);
		JSONInput output = new JSONInput();
		output.add("authorization_code", authorizationCode);
		output.add("component_appid", OpenAccountConfig.getInstance()
				.getAppId());
		JSONObject result = APIHttpConnect.post(url, output);
		return new AuthorizerToken(result.getString("authorizer_access_token"),
				result.getString("authorizer_refresh_token"),
				result.getString("authorizer_appid"));
	}

	/**
	 * 获取开放平台accesstoken
	 * 
	 * @param componetVerifyTicket
	 *            此内容由微信开放平台每10分钟推送一次过来
	 * @return
	 * @throws APIException
	 */
	public String getServiceToken(String componetVerifyTicket)
			throws APIException {
		URLParam url = new URLParam(SERVICE_ACCESS_TOKEN);
		JSONInput output = new JSONInput();
		output.add("component_appid", OpenAccountConfig.getInstance()
				.getAppId());
		output.add("component_appsecret", OpenAccountConfig.getInstance()
				.getSecret());
		output.add("component_verify_ticket", componetVerifyTicket);
		JSONObject result = APIHttpConnect.post(url, output);
		return result.getString("component_access_token");
	}

	/**
	 * 获取第三方预授权码 有效期20分钟 600秒
	 * 
	 * @param serviceToken
	 *            开放平台accesstoken
	 * @return
	 * @throws APIException
	 */
	public String getPreAuthCode(String serviceToken) throws APIException {
		URLParam url = new URLParam(PRE_AUTH_CODE, serviceToken);
		JSONInput output = new JSONInput();
		output.add("component_appid", OpenAccountConfig.getInstance()
				.getAppId());
		JSONObject result = APIHttpConnect.post(url, output);
		return result.getString("pre_auth_code");
	}

	/**
	 * 创建微信公众号登录授权地址
	 * 
	 * @param preAuthCode
	 * @param callbackUrl
	 *            回调地址 @无需url编码 此地址域名必须与开放平台配置的域名一致否则无法发起授权
	 * @return
	 * @throws APIRuntimeException
	 *             String
	 */
	public String createLoginAuthURL(String preAuthCode, String callbackUrl)
			throws APIRuntimeException {
		String back = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid={0}&pre_auth_code={1}&redirect_uri={2}";
		try {
			return MessageFormat.format(back, OpenAccountConfig.getInstance()
					.getAppId(), preAuthCode, URLEncoder.encode(callbackUrl,
					APIHttpConnect.API_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new APIRuntimeException("auth login url encode fail!", e);
		}
	}

	/**
	 * 发起网页2.0授权请求
	 * 
	 * @param authAppId
	 * @param state
	 * @param method
	 * @param callbackUrl
	 *            请求成功后的回调地址 此地址必须在安全域名下
	 * @return
	 * @throws APIRuntimeException
	 */
	private String createWebAuthUrl(String authAppId, String state,
			String method, String callbackUrl) throws APIRuntimeException {
		String webAuth = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope={2}&state={3}&component_appid={4}#wechat_redirect";
		try {
			String appid = OpenAccountConfig.getInstance().getAppId();
			String result = MessageFormat
					.format(webAuth, authAppId, URLEncoder.encode(callbackUrl,
							APIHttpConnect.API_ENCODING), "snsapi_base", state,
							appid);
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new APIRuntimeException("web auth2.0 url encode fail!", e);
		}
	}

	/**
	 * 网页2.0授权静默的方式 只能获取用户openid无论用户是否关注公众号
	 * 
	 * @param authAppId
	 *            授权方appid
	 * @param state
	 *            自定义参数 128个字节 在返回成功是会返回给到回调地址上
	 * @param callbackUrl
	 *            成功后的回调地址，必须在第三方配置的安全域名下
	 * @return 用户点击此链接地址，成功后微信会请求callbackUrl地址并将state跟code授权码返回过来
	 *         通过此授权码就可以获取点击人的openid
	 * @throws APIRuntimeException
	 */
	public String baseUrl(String authAppId, String state, String callbackUrl)
			throws APIRuntimeException {
		return createWebAuthUrl(authAppId, state, "snsapi_base", callbackUrl);
	}

	/**
	 * 网页2.0授权 带确认提示的方式 可以获取点击人的openid拿到昵称、性别、所在地无论点击人是否关注公众号
	 * 
	 * @param authAppId
	 * @param state
	 * @param callbackUrl
	 * @return
	 * @throws APIRuntimeException
	 */
	public String userInfoUrl(String authAppId, String state, String callbackUrl)
			throws APIRuntimeException {
		return createWebAuthUrl(authAppId, state, "snsapi_userinfo",
				callbackUrl);
	}

}
