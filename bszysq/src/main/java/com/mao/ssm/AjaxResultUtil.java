package com.mao.ssm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Ajax返回值工具类
 * @author Mao 2015-8-17 下午2:16:30
 */
public class AjaxResultUtil {
	
	
	/** 向response输出内容 */
	public static void write(HttpServletRequest request, HttpServletResponse response, Object vo) {
		if(vo != null){
			String result = toJsonStr(vo);
			out(request, response, result);
		}
	}
	
	/** 向response输出AjaxResult内容 */
	public static void write(HttpServletRequest request, HttpServletResponse response, AjaxResult ar) {
		if(ar != null){
			String result = toJsonStr(ar);
			out(request, response, result);
		}
	}
	
	/** 向response输出内容 */
	public static void write(HttpServletRequest request, HttpServletResponse response, String result) {
		out(request, response, result);
	}
	
	/**
	 * 打印结果 <br>
	 * 如果request提交参数中带有jsoncallback参数, 表示JSONP跨域提交
	 */
	private static void out(HttpServletRequest request, HttpServletResponse response, String result) {
		String jsonpName = request != null ? request.getParameter("jsoncallback") : null;
		out(request, response, result, jsonpName);
	}

	/**
	 * 打印结果 <br>
	 * 如果request提交参数中带有jsoncallback参数, 表示JSONP跨域提交
	 */
	private static void out(HttpServletRequest request, HttpServletResponse response, String result, String jsonpName) {
		PrintWriter pw = null;
		try {
			boolean isJsonp = jsonpName != null && jsonpName.length() > 0;
			response.setCharacterEncoding("utf-8");           
			response.setContentType("text/html; charset=utf-8");
			pw = response.getWriter();
			if(isJsonp){
				pw.write(jsonpName);
				pw.write('(');
			}
			pw.write(result);
			if(isJsonp){
				pw.write(')');
			}
			pw.flush();
			pw.close();		//一旦关闭，response的输出流将不能再输出
			pw = null;
		} catch (IOException e) {
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
	}
	
	// TODO 辅助
	
	/** 将对象转换成json字符串 */
	public static String toJsonStr(Object mo){
//		String result = new Gson().toJson(mo);
//		String result = new GsonBuilder()  
//			  .setDateFormat("yyyy-MM-dd HH:mm:ss")
//			  .create().toJson(mo);
		String result = JSONObject.toJSONString(mo);
		return result;
	}
	
	// TODO 测试
	
	public static void main(String[] args) {
		BasePojo mo = new BasePojo();
		mo.setUtime(new Date());
		mo.setIsdel(1);
		System.out.println(toJsonStr(mo));
		System.out.println(JSONObject.toJSONString(mo));
	}

}
