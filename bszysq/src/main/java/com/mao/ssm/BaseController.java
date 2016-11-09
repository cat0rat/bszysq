package com.mao.ssm;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mao.ini.PathIniUtil;
import com.mao.init.AppUtil;
import com.mao.lang.MUtil;
import com.mao.lang.Timer;

/**
 * 统一的控制器基类
 * @author Mao 2015-8-21 下午5:24:04
 */
@Controller
public class BaseController {
	protected static final String Key_AjaxResult = "_ajaxResult_";
	protected static final String Key_Exception = "_ctrl_ex_";
	protected static final String Key_Force_Jump = "_ctrl_force_jump_";
	protected static final String Key_Force_Jump_Url = "_ctrl_force_jump_url_";
	
	/** 统一的日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/** json 请求时放置的结果, 如果最后不为null将会被response输出  */
	//protected AjaxResult ar = null;
	
	
	// TODO 拦截器方法
	
	/** 统一的异常处理
	 * @deprecated
	 */
	//@ExceptionHandler(RuntimeException.class)
//	public String operateExp(RuntimeException ex, HttpServletRequest request){
//		logger.error("控制器未捕获的异常", ex);
//		//ExceptionHandler处理异常时，Model，是不能用的，否则会不起作用，这里用了HttpServletRequest
//		//mod.addAttribute("err", ex.getMessage()); 
//		request.setAttribute("err", ex.getMessage());
//		return "public/error";
//	}
	
	/**
	 * 统一的异常处理, 由 BaseInterceptor调用
	 */
	protected String operateException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("控制器未捕获的异常", ex);
		//ExceptionHandler处理异常时，Model，是不能用的，否则会不起作用，这里用了HttpServletRequest
		//mod.addAttribute("err", ex.getMessage()); 
		AjaxResult ar = (AjaxResult)request.getAttribute(Key_AjaxResult);
		if(ar != null){
			ar.t_fail("-1");
			//ar.tetKey(Key_Exception, "控制器未捕获的异常!");
			return "";
		}
		request.setAttribute(Key_Exception, "控制器未捕获的异常!");
		String path = request.getRequestURI();
		String to_url = "redirect:/error.html?code=500&bad_page=" + path;
		return to_url;
	}
	
	/**
	 * 统一的异常处理, 由 BaseExceptionHandler调用
	 */
	protected String resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		return operateException(request, response, handler, ex);
	}
	
	/**
	 * 预处理<br>
	 * 可以进行编码、安全控制等处理
	 */
	protected boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
	
	/**
	 * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）<br>
	 * 有机会修改ModelAndView
	 */
	protected void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}
	
	/**
	 * 返回处理（已经渲染了页面） <br>
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录
	 */
	protected void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(ex != null){
			operateException(request, response, handler, ex);
		}
		
		AjaxResult ar = (AjaxResult)request.getAttribute(Key_AjaxResult);
		if(ar != null){
			outJson(request, response, ar);
		}
	}
	
	
	// TODO 辅助
	
	/** 向response输出内容 */
	protected void outJson(HttpServletRequest request, HttpServletResponse response, Object vo) {
		AjaxResultUtil.write(request, response, vo);
	}
	
	/** 向response输出内容 */
	protected void outJson(HttpServletRequest request, HttpServletResponse response, AjaxResult ar) {
		AjaxResultUtil.write(request, response, ar);
	}
	
	/** 获取ajax返回结果对象 */
	protected AjaxResult ajaxResult(HttpServletRequest request) {
		return ajaxResult(request, null);
	}

	/** 设置ajax返回结果对象 */
	protected AjaxResult ajaxResult(HttpServletRequest request, AjaxResult ar) {
		if(ar == null){
			ar = (AjaxResult)request.getAttribute(Key_AjaxResult);
			if(ar == null){
				ar = new AjaxResult();
				request.setAttribute(Key_AjaxResult, ar);
			}
		}else{
			AjaxResult oldAr = (AjaxResult)request.getAttribute(Key_AjaxResult);
			request.setAttribute(Key_AjaxResult, ar);
			ar = oldAr;
		}
		return ar;
	}
	
	/** 移除ajax返回结果对象 */
	protected AjaxResult removeAjaxResult(HttpServletRequest request) {
		AjaxResult oldAr = (AjaxResult)request.getAttribute(Key_AjaxResult);
		request.removeAttribute(Key_AjaxResult);
		return oldAr;
	}
	
	
	/** 获取一个Serivce对象(通过Serivce类型) */
	protected <X> X serviceBean(Class<X> type){
		return AppUtil.getBean(type);
	}
	
	/** 获取一个Serivce对象(通过Serivce名) */
	protected Object serviceBean(String beanId){
		return AppUtil.getBean(beanId);
	}
	
	/** 判断是否是PC的代理信息: str通过request.getheader("user-agent")得到 */
	protected boolean isPcUserAgent(HttpServletRequest request){
		return MUtil.isPcUserAgent(request.getHeader("user-agent"));
	}
	
	/**
	 * 在请求中需要传递给前端的数据对象转换成json字符串, <br>
	 * 这是一个统一的函数，
	 * @param model (ModelMap)
	 * @param key (String)
	 * @param obj (Object)
	 * @return jsonstring
	 */
	protected String toJsonToModel(ModelMap model, String key, Object obj){
		String js = null;
		if(!PathIniUtil.getConfig().isFalseConst("C_Open_Sync_ToJsonToModel")){
			js = AjaxResultUtil.toJsonStr(obj);
			model.addAttribute(key, js);
		}
		return js;
	} 
	
	/** 将提交的参数全部放置到Map中 */
	protected Map<String, String> paramToMap(HttpServletRequest request){
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name = null;
		String val = null;
		
		StringBuilder sb = new StringBuilder();
		String[] cb_sa = null;
		while(names.hasMoreElements()){
			name = names.nextElement();
			cb_sa = request.getParameterValues(name);
			if(cb_sa.length == 1){
				val = cb_sa[0];
				
			}else{
				sb.setLength(0);
				for(int i = 0; i < cb_sa.length; i++){
					sb.append(cb_sa[i]).append(',');
				}
				sb.setLength(sb.length()-1);
				val = sb.toString();
			}
			params.put(name, val);
		}
		return params;
	}
	
	
	// TODO get / set
	
//	public void setAr(AjaxResult ar) {
//		this.ar = ar;
//	}

	public Logger getLogger() {
		return logger;
	}
	
	/**
	 * 获取上传文件
	 * @param request
	 * @param fieldName
	 * @return
	 */
	public MultipartFile getUploadFile(HttpServletRequest request, String fieldName){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(fieldName);
		return multipartFile;
	}
	
	
	// TODO 测试
	
	public static void main(String[] args) {
		Timer t = new Timer();
		t.printLastTimeElapsed("0");
		Logger logger = LoggerFactory.getLogger(BaseController.class);
		t.printLastTimeElapsed("1");
		Logger logger2 = LoggerFactory.getLogger(BaseController.class);
		t.printLastTimeElapsed("2");
		System.out.println(logger);
		System.out.println(logger2);
		System.out.println(logger == logger2);
	}
	
	
}
