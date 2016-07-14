package com.pizza.wechat;

import java.text.MessageFormat;

public class APIRuntimeException extends RuntimeException {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Integer errcode;
	private String errmsg;
	private static String templ = "api exception info [{0}]";

	public static String format(String str) {
		return MessageFormat.format(templ, str);
	}

	public APIRuntimeException(Integer errcode) {
		super(format("errcode : " + errcode));
		this.errcode = errcode;
	}

	public APIRuntimeException(String errmsg) {
		super(format("errmsg : " + errmsg));
		this.errmsg = errmsg;
	}

	public APIRuntimeException(String errmsg, Throwable cause) {
		super(format("errmsg : " + errmsg), cause);
		this.errmsg = errmsg;
	}

	public APIRuntimeException(Integer errcode, String errmsg) {
		super(format("errcode : " + errcode + ",errmsg : " + errmsg));
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	/**
	 * 获取错误码
	 * 
	 * @return Integer
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * 获取错误码对应消息
	 * 
	 * @return String
	 */
	public String getErrmsg() {
		return errmsg;
	}
}
