package com.mao.ssm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class BsseMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override  
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {  
        ModelAndView result = super.doResolveException(request, response, handler, ex);  
        // result.addObject("url", request.getRequestURI());
        System.out.println(request.getRequestURI());
        return result;  
    }  
}
