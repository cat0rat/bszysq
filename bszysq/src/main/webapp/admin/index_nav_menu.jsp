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

<div class="easyui-accordion" style="height:100%;">  
    <div id="nav-art-mgr" title="文章管理" data-options="selected:true">  
		<ul class="mgr-list">  
		   <li href="/admin/art/category" class='xitem a_ac_tab' 
		   		data-options="iconCls:'icon-standard-anchor'">  
                <span>栏目管理</span>  
            </li> 
		    <li href="/admin/art/topic" class='xitem a_ac_tab' 
		    	data-options="iconCls:'icon-standard-anchor'">  
		        <span>主题管理</span>  
		    </li>  
		    <li href="/admin/art/article" class='xitem a_ac_tab' 
		    	data-options="iconCls:'icon-standard-anchor'">  
		        <span>文章管理</span>  
		    </li>  
		    <li href="/admin/art/slider"
		    	class='xitem a_ac_tab' data-options="iconCls:'icon-standard-anchor'">  
		        <span>轮播管理</span>  
		    </li>  
		</ul>
    </div>  
    <div id="nav-sys-mgr" title="系统管理">  
        <ul class="mgr-list">  
		   <li href="/admin/sys/user_info"
		   		class='xitem a_ac_tab' data-options="iconCls:'icon-standard-anchor'">  
                <span>用户列表</span>  
            </li> 
		    <li class='xitem a_ac_tab' data-options="iconCls:'icon-standard-anchor'">  
		        <span>修改密码</span>  
		    </li>  
		    <li class='xitem' data-options="iconCls:'icon-standard-anchor'" 
		    	onclick="if(confirm('您确定要退出吗？')){ return window.location = '/admin/logout';};">  
		        <span>退出</span>  
		    </li>  
		</ul>
    </div>  
</div>  


<%
	}
%>