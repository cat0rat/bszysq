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
	if(xrole == 9){	// admin:超级管理员
%>

<div class="easyui-accordion" style="height:100%;">
    <div id="nav-art-mgr" title="内容管理" data-options="selected:true">  
		<ul id="art-tree"></ul>
    </div>  
    <div id="nav-sys-mgr" title="系统管理">  
        <ul id="sys-tree"></ul>
    </div>  
</div>  

<script src="/admin/style/js/admin_tree.js" type="text/javascript"></script>

<%
	}
%>