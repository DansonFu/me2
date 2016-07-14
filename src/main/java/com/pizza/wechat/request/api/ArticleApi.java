package com.pizza.wechat.request.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.request.APIHttpConnect;
import com.pizza.wechat.request.JSONInput;
import com.pizza.wechat.request.MediaFileType;
import com.pizza.wechat.request.MediaType;
import com.pizza.wechat.request.URLParam;
import com.pizza.wechat.request.bean.BusinessArticle;
import com.pizza.wechat.request.bean.BusinessArticleResults;
import com.pizza.wechat.request.bean.MediaCount;
import com.pizza.wechat.request.bean.MediaResult;
import com.pizza.wechat.util.Utils;

/**
 * 永久图文消息接口管理(限制在5000个) 对于图文消息的图片素材 建议压缩上传
 * 上下文内容中的图片现在不支持外部域名图片必须先通过uploadCTPic上传通过返回的url才能使用
 * 
 * @ClassName : ArticleApi
 * @author : emmy.cheng
 * @date : 2015年11月14日 下午6:02:03
 */
public class ArticleApi {
	private static final String ADD = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	private static final String GET = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	private static final String DELETE = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	private static final String UPDATE = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	private static final String GET_COUNT = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	private static final String QUERY = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	private static final String UPLOAD_CONTENT_PIC = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	private static final String UPLOAD_THUMB = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";

	/**
	 * 新增图文消息 返回mediaid
	 * 
	 * @param articles
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public String create(List<BusinessArticle> articles, String accessToken)
			throws APIException {
		URLParam url = new URLParam(ADD, accessToken);
		JSONInput out = new JSONInput();
		out.add("articles", JSONArray.fromObject(articles).toString());
		return APIHttpConnect.post(url, out).getString("media_id");
	}

	/**
	 * 获取一条整个图文消息
	 * 
	 * @param mediaId
	 *            新增时返回的mediaid
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessArticle> findById(String mediaId, String accessToken)
			throws APIException {
		validateMediaId(mediaId);
		URLParam url = new URLParam(GET, accessToken);
		JSONInput out = new JSONInput();
		out.add("media_id", mediaId);
		JSONObject result = APIHttpConnect.post(url, out);
		JSONArray array = result.getJSONArray("news_item");
		List<BusinessArticle> articles = new ArrayList<BusinessArticle>();
		articles.addAll(JSONArray.toCollection(array, BusinessArticle.class));
		return articles;
	}

	/**
	 * 删除图文消息
	 * 
	 * @param mediaId
	 *            新增时返回的mediaid
	 * @param accessToken
	 * @throws APIException
	 */
	public void delete(String mediaId, String accessToken) throws APIException {
		validateMediaId(mediaId);
		URLParam url = new URLParam(DELETE, accessToken);
		JSONInput out = new JSONInput();
		out.add("media_id", mediaId);
		APIHttpConnect.post(url, out);
	}

	/**
	 * 单个图文消息更新
	 * 
	 * @param article
	 * @param mediaId
	 *            新增时返回的mediaid
	 * @param index
	 *            索引位置 0开始
	 * @param accessToken
	 * @throws APIException
	 */
	public void updateSingle(BusinessArticle article, String mediaId,
			int index, String accessToken) throws APIException {
		validateMediaId(mediaId);
		if (index < 0) {
			throw new APIRuntimeException("updateSingle min index value 0!");
		}
		URLParam url = new URLParam(UPDATE, accessToken);
		JSONInput out = new JSONInput();
		out.add("media_id", mediaId);
		out.add("index", index);
		out.add("articles", article.toJSONString());
		APIHttpConnect.post(url, out);
	}

	private void validateMediaId(String mediaId) {
		if (Utils.isEmpty(mediaId)) {
			throw new APIRuntimeException("mediaId is not empty!");
		}
	}

	/**
	 * 更新整个图文消息 更新的时候注意必须与新增时的数量保持一致 多了会另外新增一条
	 * 
	 * @param articles
	 * @param mediaId
	 *            新增时返回的mediaid
	 * @param accessToken
	 * @throws APIException
	 */
	public void update(List<BusinessArticle> articles, String mediaId,
			String accessToken) throws APIException {
		for (int i = 0; i < articles.size(); i++) {
			updateSingle(articles.get(i), mediaId, i, accessToken);
		}
	}

	/**
	 * 获取媒体总数
	 * 
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public MediaCount getMediaCount(String accessToken) throws APIException {
		URLParam url = new URLParam(GET_COUNT, accessToken);
		JSONObject result = APIHttpConnect.post(url, null);
		MediaCount count = new MediaCount(result.getInt("voice_count"),
				result.getInt("video_count"), result.getInt("image_count"),
				result.getInt("news_count"));
		return count;
	}

	/**
	 * 获取指定数量的图文消息
	 * 
	 * @param type
	 * @param offset
	 *            0-无限大
	 * @param count
	 *            获取数量 1-20之间
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public BusinessArticleResults query(MediaType type, int offset, int count,
			String accessToken) throws APIException {
		URLParam url = new URLParam(QUERY, accessToken);
		JSONInput out = new JSONInput();
		out.add("type", type.Type());
		out.add("offset", Math.max(offset, 0));
		out.add("count", Math.min(count < 1 ? 1 : count, 20));
		JSONObject result = APIHttpConnect.post(url, null);
		BusinessArticleResults articleResults = (BusinessArticleResults) JSONObject
				.toBean(result, BusinessArticleResults.class);
		return articleResults;
	}

	/**
	 * 上传图文上下文内容图片返回图片url
	 * 
	 * @param uploadPic
	 *            格式只能是jpg/png 大小1M 无上限
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public String uploadCTXPic(File uploadPic, String accessToken)
			throws APIException {
		URLParam url = new URLParam(UPLOAD_CONTENT_PIC, accessToken);
		return APIHttpConnect.uploadMedia(url, uploadPic, MediaFileType.IMAGE,
				null, null).getString("url");
	}

	/**
	 * 上传封面图片 (图片大小不超过2M，支持bmp/png/jpeg/jpg/gif格式) 上限为5000
	 * 
	 * @param uploadThumb
	 * @param accessToken
	 * @return
	 * @throws APIException
	 */
	public MediaResult uploadThumb(File uploadThumb, String accessToken)
			throws APIException {
		URLParam url = new URLParam(UPLOAD_THUMB, accessToken);
		JSONObject result = APIHttpConnect.uploadMedia(url, uploadThumb,
				MediaFileType.THUMB, null, null);
		return new MediaResult(result.getString("media_id"),
				result.getString("url"));
	}
}
