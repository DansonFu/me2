package com.pizza.wechat;


/**
 * 框架处理异常
 * 
 * @ClassName : FrameException
 * @author : emmy.cheng
 * @Description : TODO
 * @date : 2015年10月22日 下午11:15:38
 */
public class APIException extends Exception {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	public APIException(String message) {
		super(message);
	}
	public APIException(String message,Throwable e) {
		super(message,e);
	}

}
