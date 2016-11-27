<%@page import="com.mao.lang.MUtil"%>
<%@page import="com.bszy.admin.pojo.Admin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	// 控制角色权限(临时这样定写)
	Admin admin = (Admin)session.getAttribute("admin_session");
	long xrole = 0;
	if(admin != null){
		xrole = MUtil.tolong(admin.getRolex(), 0);
	}
	if(xrole == 1){	// admin:超级管理员
%>
	<%@ include file="/admin/admin_menu.jsp" %>
<%
	}
%>