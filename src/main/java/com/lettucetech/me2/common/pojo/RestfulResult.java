package com.lettucetech.me2.common.pojo;

/**
 * restful 请求通用返回类
 * @author vc
 *
 */
public class RestfulResult {
	/**
	 * 请求是否执行成功
	 */
	private boolean success;
	
	/**
	 * 消息说明
	 */
	private String message;
	/**
	 * http状态码
		200 – OK – 一切正常
		201 – OK – 新的资源已经成功创建
		204 – OK – 资源已经成功删除
		304 – Not Modified – 客户端使用缓存数据
		400 – Bad Request – 请求无效，需要附加细节解释如 "JSON无效"
		401 – Unauthorized – 请求需要用户验证
		403 – Forbidden – 服务器已经理解了请求，但是拒绝服务或这种请求的访问是不允许的。
		404 – Not found – 没有发现该资源
		422 – Unprocessable Entity – 只有服务器不能处理实体时使用，比如图像不能被格式化，或者重要字段丢失。
		500 – Internal Server Error 。
	 */
	private String code;
	
	/**
	 * 返回体
	 */
	private Object obj;



	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
