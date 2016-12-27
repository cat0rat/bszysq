<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	application.setAttribute("__rnd", "20161228");
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>后台管理</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	<link href="/admin/style/css/index.css?r=${__rnd}" rel="stylesheet" type="text/css">
</head>
<body>
<div id="maskContainer">
	<div class="datagrid-mask" style="display: block;"></div>
	<div class="datagrid-mask-msg"
		style="display: block; left: 50%; margin-left: -52.5px;">
		正在加载...</div>
</div>

<div id="mainLayout" class="easyui-layout hidden" data-options="fit: true, border: false">

	<div id="i_header" data-options="region:'north', border:false, height:40" >
		<div class="head-pnl">
			<div class="login-pnl">
		         <p style="padding-top:0px">
		        	<span id="orgNameId"></span>
		         </p>
		         <p style="padding-top:0px">
		         	<span class="hand" style="padding-top:5px" on2click="(function(){reload_nav_tree_art()})()">
		         		${admin.name }
		         	</span>
		            <span class="hand" style="padding-top:5px">
		            	<img class="exit" src="/style/img/logout.png" 
		            		id="exit" title="退出" onclick="if(confirm('您确定要退出吗？')){ return window.location = '/admin/logout.do';};"/>
		            </span>
		        </p>
		    </div>
		    <div class="sys-title">
		    	<span>后台管理</span>
		    	<img alt="" src="/style/img/point5.png">
		    </div>
		</div>
	</div>
	<div data-options="region:'west', collapsible:false,split:false,title:'导航'" 
		style="width:160px;">
		<%@include file="index_nav_tree.jsp" %>
	</div>
	<div id="i_center" data-options="region:'center'" disc="主中心区">
		<div id="i_tabs" disc="主Tabs" style="width:700px;height:250px"></div>
	</div>
    <div id="edi_win" style="padding-left:5px;">
	<form id="edi_win_form" method="post">
		<table style="padding:15px; padding-bottom:0px">
	    	<tr>
		        <td>旧密码：</td>
				<td>
					<input type="text" id="oldPwd" name="oldPwd" class="easyui-validatebox x_input" data-options="required:true, validType:'length[6, 32]'"/>
				</td>
		    </tr> 
		    <tr></tr>
	    	<tr>
		        <td>新密码：</td>
				<td>
					<input type="text" id="newPwd" name="newPwd" class="easyui-validatebox x_input" data-options="required:true, validType:'length[6, 32]'"/>
				</td>
		    </tr> 
		    <tr></tr>
		    <tr>
		        <td>确认新密码：</td>
				<td>
					<input type="text" id="reNewPwd" name="reNewPwd" class="easyui-validatebox x_input" data-options="required:true, validType:'length[6, 32]'"/>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
<script src="/admin/style/js/index.js?r=${__rnd}" type="text/javascript"></script>
<script src="/admin/style/js/edi_win.js?r=${__rnd}" type="text/javascript"></script>
</html>