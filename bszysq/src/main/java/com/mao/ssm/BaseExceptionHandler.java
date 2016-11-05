package com.mao.ssm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 调用 BaseController的resolveException方法
 * @author Mao 2015年12月27日 上午12:49:50
 */
public class BaseExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = null;
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Object bean = hm.getBean();
			if (bean instanceof BaseController) {
				BaseController ctrl = (BaseController) bean;
				String url = ctrl.resolveException(request, response, handler, ex);
				if(url != null && url.length() > 0) mv = new ModelAndView(url);
			}
		}
		return mv;
	}
}