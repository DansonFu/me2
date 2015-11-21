package com.lettucetech.me2.common.exception;

public class BaseException extends RuntimeException {

	private String key;

	private Object[] arg;

	public BaseException(String key) {
		super(key);
		this.key = key;
	}

	public BaseException(String key, Object[] arg) {
		super(key);
		this.key = key;
		this.arg = arg;
	}

	public BaseException(String key, String msg) {
		super(msg);
		this.key = key;
	}

	public BaseException(String key, String msg, Object[] arg) {
		super(msg);
		this.key = key;
		this.arg = arg;
	}

	public BaseException(String key, Throwable t) {
		super(t);
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public Object[] getArg() {
		return arg;
	}
}
