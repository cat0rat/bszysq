<%@page import="com.shg.ini.PathIniUtil"%>
<%@page import="com.shg.init.AppUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String projectRootPath = AppUtil.getRealtPath();	// 项目的路径(tomcat下的)
	//String rootPath = application.getRealPath( "/" );
	String rootPath = PathIniUtil.getConfig().getValue("Imgs_Path", "uimgs/");	// 图片保存的根路径
	String Imgs_Url = PathIniUtil.getConfig().getValue("Imgs_Url", "/uimgs/");		// 项目图片访问路径前缀
	
	if(Imgs_Url != null && Imgs_Url.length() > 0){
		int epl = Imgs_Url.length() - 1;
		char ec = Imgs_Url.charAt(epl);
		if(ec == '/' || ec == '\\'){
			Imgs_Url = Imgs_Url.substring(0, epl);
		}
	}
	if(rootPath == null || rootPath.length() == 0){
		rootPath = projectRootPath;
	}else if('/' != rootPath.charAt(0)){
		rootPath = projectRootPath + rootPath;
	}
	
	out.write( new ActionEnter( request, rootPath, projectRootPath, Imgs_Url ).exec() );
	
%>