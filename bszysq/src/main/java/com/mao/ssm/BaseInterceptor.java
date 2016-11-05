package com.mao.ssm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 基本的拦截器, 只作栏截，具体执行将交由 BaseController及派生类对应的方法
 * @author Mao 2015年8月26日 下午4:23:29
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 预处理<br>
	 * 可以进行编码、安全控制等处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Object bean = hm.getBean();
			if(bean instanceof BaseController){
				BaseController ctrl = (BaseController)bean;
				return ctrl.preHandle(request, response, handler);
			}
		}else if(handler instanceof BaseController){
			BaseController ctrl = (BaseController)handler;
			return ctrl.preHandle(request, response, handler);
		}
		return true;
	}
	
	/**
	 * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）<br>
	 * 有机会修改ModelAndView
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Object bean = hm.getBean();
			if(bean instanceof BaseController){
				BaseController ctrl = (BaseController)bean;
				ctrl.postHandle(request, response, handler, modelAndView);
			}
		}else if(handler instanceof BaseController){
			BaseController ctrl = (BaseController)handler;
			ctrl.postHandle(request, response, handler, modelAndView);
		}
	}

	/**
	 * 返回处理（已经渲染了页面） <br>
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			Object bean = hm.getBean();
			if(bean instanceof BaseController){
				BaseController ctrl = (BaseController)bean;
				ctrl.afterCompletion(request, response, handler, ex);
			}
		}else if(handler instanceof BaseController){
			BaseController ctrl = (BaseController)handler;
			ctrl.afterCompletion(request, response, handler, ex);
		}
	}
	
}
