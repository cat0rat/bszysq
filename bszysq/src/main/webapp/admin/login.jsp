<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>后台登录</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	
	<link href="/admin/style/css/login.css?r=${__rnd}" rel="stylesheet" type="text/css" />
	<script src="/admin/style/js/login.js?r=${__rnd}" type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<div id="i_center" data-options="region:'center'" style="background-color: #ecfafa;">
	</div>
	<!-- 
	<div id="i_bottom" data-options="region:'south'" style="height: 30px; background-color: #2C51A1">
		<div style="line-height: 28px; color: #fff; width: 240px; margin: 0px auto; padding-left:20px; font-family: arial;">
			<span>后台管理</span>
			&nbsp;|&nbsp;
			
		</div>
	</div>
	 -->
	
	<!-- 登录 -->
	<div id="login_dlg" style="padding-left:5px;">
		<form id="login_form" method="post">
		<table style="padding:15px; padding-bottom:0px">
			<tr>
				<td>账号：</td>
				<td>
					<input type="text" name="uname" class="easyui-validatebox x_input" value=""
						data-options="required:true, validType:'name[3, 20]'"/>
				</td>
				<td class="aright">密码：</td>
				<td>
					<input type="password" name="pwd" class="easyui-validatebox x_input" value=""
						data-options="required:true, validType:'length[6, 20]'"/>
				</td>
			</tr>
			<tr>
				<td>验证码：</td>
				<td>
					<div>
						<img id="captchaimg" src="" width="50" height="24" style="cursor:pointer;margin-right: 10px;" alt="换一张" />
						<input type="text" name="cap" class="easyui-validatebox" style="width: 60px;" value=""
							data-options="required:true, validType:'captcha[4]'"/>
					</div>
				</td>
				<td class="aright"></td>
				<td>
					
				</td>
			</tr>
		</table>
		</form>
	</div>
	
</body>
</html>
