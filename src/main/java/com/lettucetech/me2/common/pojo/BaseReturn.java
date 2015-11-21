package com.lettucetech.me2.common.pojo;

/**
 * Ext Ajax返回对象
 * 
 * @author chenxin
 * @date 2011-3-10 下午09:43:35
 */
public class BaseReturn {

	public BaseReturn() {
	}

	/**
	 * 是否更新成功的构造方法
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            消息
	 */
	public BaseReturn(boolean success, Object msg) {
		this.success = success;
		this.msg = msg;
		this.obj = "";
	}

	/**
	 * 是否更新成功的构造方法
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            消息
	 * @param other
	 *            其他对象
	 */
	public BaseReturn(boolean success, Object msg, Object other) {
		this.success = success;
		this.msg = msg;
		this.obj = other;
	}

	/**
	 * 异常时的构造方法
	 * 
	 * @param msg
	 *            异常消息
	 */
	public BaseReturn(Object errormsg) {
		// 异常情况
		this.success = false;
		this.msg = errormsg;
		this.obj = "";
	}

	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 返回消息
	 */
	private Object msg;
	/**
	 * 其他对象
	 */
	private Object obj;

	/**
	 * @return 是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            是否成功
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return 返回消息
	 */
	public Object getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            返回消息
	 */
	public void setMsg(Object msg) {
		this.msg = msg;
	}

	/**
	 * @return 其他对象
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param o
	 *            其他对象
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
