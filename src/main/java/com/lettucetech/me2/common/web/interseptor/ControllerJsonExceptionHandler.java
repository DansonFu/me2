package com.lettucetech.me2.common.web.interseptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.lettucetech.me2.common.annotation.ResponseJson;
import com.lettucetech.me2.common.constant.WebConstants;
import com.lettucetech.me2.common.exception.BaseException;
import com.lettucetech.me2.common.pojo.BaseReturn;
import com.lettucetech.me2.common.utils.WebUtil;

/**
 * controller异常返回json格式数据通用处理
 * 
 * @author wangta
 */
public class ControllerJsonExceptionHandler extends DefaultHandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(ControllerJsonExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		logger.error("Controller Exception: ", e);
		if (handler != null && handler instanceof HandlerMethod) {
			if (((HandlerMethod) handler).getMethod().getAnnotation(ResponseJson.class) != null
					|| ((HandlerMethod) handler).getMethod().getAnnotation(ResponseBody.class) != null
					|| ((HandlerMethod) handler).getMethod().getReturnType() == void.class) {
				String msgStr = WebUtil.getMessage(WebConstants.MSG_CD.ERROR_CONTROLLER_EXCEPTION);
				ModelAndView mv = new ModelAndView("common/json-result");
				((HandlerMethod) handler).getMethod().getAnnotation(Controller.class);
				if (e instanceof BaseException) {
					msgStr = WebUtil.getMessage(((BaseException) e).getKey(), ((BaseException) e).getArg());
				}
				mv.addObject("exp", new BaseReturn(false, msgStr));
				return mv;
			}
		}
		return super.resolveException(request, response, handler, e);
	}
}
