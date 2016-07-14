package com.pizza.wechat.request.api;

import com.pizza.wechat.APIException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.util.Utils;
/**
 * 带参数二维码
 * @ClassName : QRCodeApi
 * @author : emmy.cheng
 * @date : 2015年11月28日 下午5:17:27
 */
public class QRCodeApi {
	private static final String SCENE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	public static final String SHOW = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	/**
	 * 创建带参数临时二维码
	 * 
	 * @param sceneParam
	 * @param expireTime
	 *            过期时间最大值7天
	 * @param accessToken
	 * @return 返回ticket可以通过QRCode.SHOW+ticket直接显示 此ticket有效期是30分钟可以将此二维码下载保存到服务器
	 * @throws APIException
	 */
	public String createTempTicket(int sceneParam, int expireTime,
			String accessToken) throws APIException {
		URLParam url = new URLParam(SCENE, accessToken);
		JSONInput out = new JSONInput();
		out.add("expire_seconds", Math.min(expireTime, 604800));
		out.add("action_name", "QR_SCENE");
		JSONInput scene = new JSONInput();
		JSONInput sceneId = new JSONInput();
		sceneId.add("scene_id", sceneParam);
		scene.add("scene", sceneId.toJSONString());
		out.add("action_info", scene.toJSONString());
		return APIHttpConnect.post(url, out).getString("ticket");
	}

	/**
	 * 创建永久带参数二维码
	 * 
	 * @param sceneParam
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public String createTicket(String sceneParam, String accessToken)
			throws APIException {
		URLParam url = new URLParam(SCENE, accessToken);
		JSONInput out = new JSONInput();
		out.add("action_name", "QR_LIMIT_STR_SCENE");
		JSONInput scene = new JSONInput();
		JSONInput sceneId = new JSONInput();
		sceneId.add("scene_str", Utils.toString(sceneParam));
		scene.add("scene", sceneId.toJSONString());
		out.add("action_info", scene.toJSONString());
		return APIHttpConnect.post(url, out).getString("ticket");
	}
}
