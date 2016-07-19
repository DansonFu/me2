package com.pizza.wechat.request;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.lettucetech.me2.common.pojo.MyX509TrustManager;
import com.pizza.wechat.APIException;
import com.pizza.wechat.APIRuntimeException;
import com.pizza.wechat.OpenAccountConfig;
import com.pizza.wechat.request.bean.DataMap;
import com.pizza.wechat.util.MessageUtil;

/**
 * 微信接口请求工具类
 * 
 * @ClassName : APIHttpConnect
 * @author : emmy.cheng
 * @date : 2015年10月23日 下午9:18:44
 */
/**
 * @ClassName : APIHttpConnect
 * @author : emmy.cheng
 * @date : 2015年10月24日 下午4:01:07
 */
public class APIHttpConnect {
	protected static Logger log = LogManager.getLogger(APIHttpConnect.class);
	static {
		/**
		 * 开启调试模式
		 */
		if (OpenAccountConfig.getInstance().isDebug()) {
			log.setLevel(Level.DEBUG);
		}
	}
	/**
	 * 微信接口参数编码格式固定UTF-8
	 */
	public static final String API_ENCODING = "UTF-8";
	/**
	 * 请求失败返回值errcode未知异常
	 */
	public static final Integer FAIL_CODE = -1;
	/**
	 * 请求成功返回值errcode
	 */
	public static final Integer SUCCESS_CODE = 0;

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param url
	 *            请求地址
	 * @param method
	 *            请求方式（GET、POST）
	 * @param param
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject https(URLParam url, HttpMethod method,
			DataMap param) throws APIException {
		HttpsURLConnection httpUrlConn = null;
		JSONObject jsonObject = null;
		BufferedReader bufferedReader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			log.debug("begin api request info url : " + url.requestURL()
					+ " , method : " + method + " , param : "
					+ param.toJSONString() + "");
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL httpUrl = new URL(url.requestURL());
			httpUrlConn = (HttpsURLConnection) httpUrl.openConnection();
			// httpUrlConn.setConnectTimeout(5000);
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(method.name());

			if (HttpMethod.GET.equals(method))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != param) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(param.toJSONString().getBytes(API_ENCODING));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, API_ENCODING);
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			inputStreamReader.close();
			inputStreamReader = null;
			// 释放资源
			inputStream.close();
			inputStream = null;
			if (buffer.length() > 0) {
				jsonObject = JSONObject.fromObject(buffer.toString());
			}
			log.debug("end api request result info json : " + jsonObject);
			if (jsonObject == null) {
				throw new APIException("api request result json is null ["
						+ url.requestURL() + "]");
			} else if ((jsonObject.get("errcode") != null && jsonObject
					.getInt("errcode") != 0)) {
				throw new APIRuntimeException(jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		} catch (APIRuntimeException e) {
			throw e;
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException("api request fail!", e);
		} finally {
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
				httpUrlConn = null;
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
					bufferedReader = null;
				} catch (IOException e) {
					throw new APIException("file stream close fail!", e);
				}
			}
		}
		return jsonObject;
	}

	/**
	 * 发送xml请求数据
	 * 
	 * @param url
	 * @param xml
	 * @return
	 * @throws APIException
	 */
	public static Map<String, String> xmlHttps(URLParam url, String xml)
			throws APIException {
		HttpsURLConnection httpUrlConn = null;
		Map<String, String> map = null;
		BufferedReader bufferedReader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			log.debug("begin api request info url : " + url.requestURL()
					+ " , xml : " + xml + "");
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL httpUrl = new URL(url.requestURL());
			httpUrlConn = (HttpsURLConnection) httpUrl.openConnection();
			// httpUrlConn.setConnectTimeout(5000);
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("POST");
			// 当有数据需要提交时
			if (null != xml) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(xml.getBytes(API_ENCODING));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, API_ENCODING);
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			inputStreamReader.close();
			inputStreamReader = null;
			// 释放资源
			inputStream.close();
			inputStream = null;
			if (buffer.length() > 0) {
				map = MessageUtil.getElement(buffer.toString());
			}
			log.debug("end api request result info xml : " + buffer);
			if (map == null) {
				throw new APIException("api request result xml is null ["
						+ url.requestURL() + "]");
			}
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException("api request fail!", e);
		} finally {
			if (httpUrlConn != null) {
				httpUrlConn.disconnect();
				httpUrlConn = null;
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
					bufferedReader = null;
				} catch (IOException e) {
					throw new APIException("file stream close fail!", e);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * 文件上传类接口调用
	 * 
	 * @param url
	 *            请求地址
	 * @param uploadFile
	 *            上传文件
	 * @param type
	 *            多媒体类型
	 * @param title
	 *            视频文件标题
	 * @param introduction
	 *            视频文件描述
	 * @return
	 * @throws APIException
	 */
	public static JSONObject uploadMedia(URLParam url, File uploadFile,
			MediaFileType type, String title, String introduction)
			throws APIException {
		JSONObject jsonObj = null;
		HttpURLConnection con = null;
		BufferedReader reader = null;
		try {
			log.debug("begin api request info url : " + url.requestURL()
					+ " ,uploadFile : " + uploadFile
					+ ", method : post , param : {mediaType:" + type
					+ ",title:" + title + ",introduction:" + introduction + "}");
			URL urlObj = new URL(url.requestURL());
			con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod(HttpMethod.POST.name()); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Charset", API_ENCODING);
			// 设置边界
			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ uploadFile.getName() + "\"\r\n");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			if (MediaFileType.VIDEO.equals(type)) {
				sb.append("Content-Type: video/mp4 \r\n\r\n".getBytes());
				out.write(sb.toString().getBytes(API_ENCODING));
				byte[] data = new byte[1024];
				int len = 0;
				FileInputStream input = new FileInputStream(uploadFile);
				while ((len = input.read(data)) != -1) {
					out.write(data, 0, len);
				}
				input.close();
				input = null;
				sb.append(("--" + BOUNDARY + "\r\n").getBytes());
				sb.append("Content-Disposition: form-data; name=\"description\";\r\n\r\n"
						.getBytes());
				sb.append(String.format(
						"{\"title\":\"%s\", \"introduction\":\"%s\"}", title,
						introduction).getBytes());
			} else {
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
			}
			byte[] head = sb.toString().getBytes(API_ENCODING);

			// 输出表头
			out.write(head);
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(
					uploadFile));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			// 结尾部分
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n")
					.getBytes(API_ENCODING);// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();

			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			jsonObj = JSONObject.fromObject(buffer.toString());
			if (buffer.length() > 0) {
				jsonObj = JSONObject.fromObject(buffer.toString());
			}
			log.debug("end api result info json :" + jsonObj);
			if (jsonObj == null) {
				throw new APIException("api request result json is null ["
						+ url.requestURL() + "]");
			} else if ((jsonObj.get("errcode") != null && jsonObj
					.getInt("errcode") != 0)) {
				throw new APIRuntimeException(jsonObj.getInt("errcode"),
						jsonObj.getString("errmsg"));
			}
		} catch (APIRuntimeException e) {
			throw e;
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException("file upload api request fail!", e);
		} finally {
			if (con != null) {
				con.disconnect();
				con = null;
			}
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					throw new APIException("file stream close fail!", e);
				}
			}
		}
		return jsonObj;
	}

	/**
	 * 多媒体文件上传接口
	 * 
	 * @param url
	 *            接口地址
	 * @param uploadFile
	 *            上传文件
	 * @return
	 * @throws APIException
	 */
	public static JSONObject upload(URLParam url, File uploadFile)
			throws APIException {
		return uploadMedia(url, uploadFile, null, null, null);
	}

	/**
	 * 多媒体文件下载接口，视频文件无法下载
	 * 
	 * @param savePath
	 *            保存文件路径
	 * @param url
	 * @param method
	 * @return
	 * @throws APIException
	 */
	public static File download(String savePath, URLParam url, HttpMethod method)
			throws APIException {
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		FileOutputStream fileOut = null;
		BufferedReader reader = null;
		BufferedInputStream bis = null;
		File file = null;
		try {
			log.debug("begin api request info url :" + url.requestURL()
					+ " , savePath : " + savePath + " , method: " + method);
			conn = (HttpURLConnection) new URL(url.requestURL())
					.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method.name());
			inputStream = conn.getInputStream();
			String contentType = conn.getHeaderField("Content-Type");
			if ("text/plain".equals(contentType)) {// 返回错误信息
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				StringBuffer buffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				log.debug("end api result error file info : " + buffer);
				JSONObject jsonObj = JSONObject.fromObject(buffer.toString());
				if (jsonObj == null) {
					throw new APIException("api request result json is null ["
							+ url.requestURL() + "]");
				} else if ((jsonObj.get("errcode") != null && jsonObj
						.getInt("errcode") != 0)) {
					throw new APIRuntimeException(jsonObj.getInt("errcode"),
							jsonObj.getString("errmsg"));
				}
			} else {
				String fileName = conn.getHeaderField("Content-disposition");
				Matcher match = Pattern.compile(
						"filename\\s*=\\s*\"?(.*?)(\"|\\s+)").matcher(fileName);
				if (match.find()) {
					fileName = match.group(1);
				}
				if (!savePath.endsWith("/") && !savePath.endsWith("\\")) {
					savePath += "/";
				}
				bis = new BufferedInputStream(inputStream);
				file = new File(savePath + fileName);
				fileOut = new FileOutputStream(file);
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1)
					fileOut.write(buf, 0, size);
				log.debug("end api result file : " + file);
			}
			conn.disconnect();
		} catch (APIRuntimeException e) {
			throw e;
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException("file download api request fail!", e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (fileOut != null) {
					fileOut.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				throw new APIException("file stream close fail!", e);
			}
		}
		return file;
	}

	/**
	 * 发送https post请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws APIException
	 */
	public static JSONObject post(URLParam url, DataMap map)
			throws APIException {
		return https(url, HttpMethod.POST, map);
	}

	/**
	 * 发送https get请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws APIException
	 */
	public static JSONObject get(URLParam url, DataMap map) throws APIException {
		return https(url, HttpMethod.GET, map);
	}

	/**
	 * 微信接口调用返回时候成功
	 * 
	 * @param result
	 * @return
	 */
	protected static boolean success(JSONObject result) {
		if (result == null
				|| (result.get("errcode") != null && result.getInt("errcode") != SUCCESS_CODE)) {
			return false;
		}
		return true;
	}
}
