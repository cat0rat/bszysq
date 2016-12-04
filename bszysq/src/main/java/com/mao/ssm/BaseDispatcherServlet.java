package com.mao.ssm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

@SuppressWarnings("serial")
public class BaseDispatcherServlet extends DispatcherServlet {
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tourl = null;
		String url = request.getRequestURI();
		if(url.startsWith(request.getContextPath() + "/app/")){
			tourl = "/exception/ajax/404";
		}else{
			String requestType = request.getHeader("X-Requested-With");
			if(requestType != null){
				tourl = "/exception/ajax/404";
			}else{
				//tourl = "/exception/page/404";
				super.noHandlerFound(request, response);
				return ;
			}
		}
		request.getRequestDispatcher(tourl).forward(request, response);
//		response.sendRedirect(request.getContextPath() + tourl);
//		if(tourl == null){
//			super.noHandlerFound(request, response);
//		}else{
//			response.sendRedirect(request.getContextPath() + tourl);
//		}
	}
}
