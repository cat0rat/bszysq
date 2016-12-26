<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>用户管理</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	<script src="/admin/style/js/user.js?r=${__rnd}" type="text/javascript"></script>
</head>
<body>
<div id="maskContainer">
	<div class="datagrid-mask" style="display: block;"></div>
	<div class="datagrid-mask-msg"
		style="display: block; left: 50%; margin-left: -52.5px;">
		正在加载...</div>
</div>

<div id="mainLayout" class="easyui-layout hidden" data-options="fit: true, border: false">

<div data-options="region:'north', border:false" class="s_show_north" style="height:50px;">
<!-- 搜索区域 -->
<div class="l_search">
<form id="search_form" >
	<div class="l_search_tr">
		<div class="l_search_td">
			用户角色： <select name="rolex" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
				<option value="9">超级管理员</option>   
			    <option value="0">普通用户</option> 
			</select>
		</div>
		<div class="l_search_td">
			帐号： <input type="text" name="name" class="x_input"/>
		</div>
		<div class="l_search_td">
			昵称： <input type="text" name="nname" class="x_input"/>
		</div>
		<div class="l_search_td">
			手机类型： <select name="phonetype" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
				<option value="0">安卓</option>
				<option value="1">苹果</option> 
			</select>
		</div>
		<div class="l_search_td">
			手机号： <input type="text" name="mobile" class="x_input"/>
		</div>
		<div class="l_search_td">
			认证状态： <select name="authx" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
			    <option value="0">已认证</option>
				<option value="1">未认证</option>   
				<option value="2">待审核</option>
			</select>
		</div>
		<div class="l_search_td">
			用户状态： <select name="isdel" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
			    <option value="0">正常</option>
				<option value="1">封号</option>   
			</select>
		</div>
		<div class="l_search_btn">
			<a id="search_btn" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</div>
		<div class="y_clear">&nbsp;</div>
	</div>
</form>
</div>
	
</div>
<div id="searchShow" data-options="region:'center', border:false" class="s_show_center" style="padding-top: 0px" >
	<table id="mgr_dg" ></table>
</div>
	
<!-- 添加用户对话框 -->
<div id="addMgrDlg" style="padding-left:5px;">
	<form id="addMgrForm" class="dlg-frm" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l">角色：</td>
			<td class="t-l-c" colspan="3">
				<select name="rolex" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false"> 
					<option value="9">超级管理员</option>   
				    <option value="0" selected="selected">普通用户</option>
				</select>
			</td>
		<tr><td class="t-l">昵称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="nname" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[2, 16]'"/>
			</td></tr>
		<tr><td class="t-l">账号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'name[3, 16]'"/>
			</td></tr>
		<tr><td class="t-l">密码：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="pwd" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[6, 16]'"/>
			</td></tr>
			
		<tr><td class="t-l">手机：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="mobile" class="easyui-validatebox x_input x-ipt3"
					data-options="validType:['len[11]', 'mobile']"/>
			</td></tr>
		<tr><td class="t-l">认证状态：</td>
			<td class="t-l-c" colspan="3">
				<select name="authx" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false"> 
					<option value="">请选择</option>
				    <option value="0">已认证</option>
					<option value="1" selected="selected">未认证</option>   
					<option value="2">待审核</option>
				</select>
			</td></tr>
		<!-- 
		<tr><td class="t-l">微信唯一id：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="unionid" class="easyui-validatebox x_input x-ipt3"
					data-options="validType:'length[20, 50]'"/>
			</td></tr>
		<tr><td class="t-l">微信id：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="openid" class="easyui-validatebox x_input x-ipt3"
					data-options="validType:'length[20, 50]'"/>
			</td></tr>
			
		<tr><td class="t-l">个推ClientID：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="getuicid" class="easyui-validatebox x_input x-ipt3"
					data-options="validType:'length[20, 50]'"/>
			</td></tr>
		<tr><td class="t-l">手机类型：</td>
			<td class="t-l-c" colspan="3">
				<select name="phonetype" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false, width: 200"> 
					<option value="">请选择</option>
				    <option value="0" selected="selected">安卓</option>
					<option value="9">苹果</option>   
				</select>
			</td></tr>
		-->
	</table>
	</form>
</div>

<!-- 修改用户 -->
<div id="editMgrDlg" style="padding-left:5px;">
	<form id="editMgrForm" class="dlg-frm" method="post">
	<input type="hidden" name="id" />
	<table class="dlg-tb">
		<tr><td class="t-l">角色：</td>
			<td class="t-l-c" colspan="3">
				<select name="rolex" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false"> 
					<option value="">请选择</option>
					<option value="9">超级管理员</option>   
				    <option value="0" selected="selected">普通用户</option>
				</select>
			</td>
		<tr><td class="t-l">昵称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="nname" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[2, 16]'"/>
			</td></tr>
		<tr><td class="t-l">账号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'name[3, 16]'"/>
			</td></tr>
			
		<tr><td class="t-l">手机：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="mobile" class="easyui-validatebox x_input x-ipt3"
					data-options="validType:['len[11]', 'mobile']"/>
			</td></tr>
		<tr><td class="t-l">认证状态：</td>
			<td class="t-l-c" colspan="3">
				<select name="authx" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false"> 
					<option value="">请选择</option>
				    <option value="0">已认证</option>
					<option value="1" selected="selected">未认证</option>   
					<option value="2">待审核</option>   
				</select>
			</td></tr>
	</table>
	</form>
</div>

<!-- 查看用户 -->
<div id="lookMgrDlg" style="padding-left:5px;">
	<form id="lookMgrForm" class="dlg-frm" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l"><span>头像：</span></td>
			<td class="t-l-c" colspan="3">
				<img class="mi-upimg-img" upimgshow="head" />
			</td></tr>
		<tr><td class="t-l">角色：</td>
			<td class="t-l-c" colspan="3">
				<select name="rolex" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto'"> 
					<option value="">请选择</option>
					<option value="9">超级管理员</option>   
				    <option value="0" selected="selected">普通用户</option>
				</select>
			</td>
		<tr><td class="t-l">昵称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="nname" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">账号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="x_input x-ipt3" />
			</td></tr>
		
		<tr><td class="t-l">姓名：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="tname" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">手机：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="mobile" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">地址：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="address" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">认证状态：</td>
			<td class="t-l-c" colspan="3">
				<select name="authx" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto'"> 
					<option value="">请选择</option>
				    <option value="0">已认证</option>
					<option value="1" selected="selected">未认证</option>   
					<option value="2">待审核</option>   
				</select>
			</td></tr>
		<!-- 
		<tr><td class="t-l">性别：</td>
			<td class="t-l-c" colspan="3">
				<select name="sex" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto'"> 
					<option value="">请选择</option>
					<option value="1" selected="selected">男</option>   
					<option value="2">女</option>   
				</select>
			</td></tr>
		 -->
		<tr><td class="t-l">微信唯一id：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="unionid" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">微信id：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="openid" class="x_input x-ipt3" />
			</td></tr>
			
		<tr><td class="t-l">个推ClientID：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="getuicid" class="x_input x-ipt3" />
			</td></tr>
		<tr><td class="t-l">手机类型：</td>
			<td class="t-l-c" colspan="3">
				<select name="phonetype" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto'"> 
					<option value="">请选择</option>
					<option value="0">安卓</option>
					<option value="1">苹果</option> 
				</select>
			</td></tr>
		<tr><td class="t-l">手机显示名：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="phonename" class="x_input x-ipt3" />
			</td></tr>
		<!-- 
		<tr><td class="t-l">邮箱：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="email" class="x_input x-ipt3" />
			</td></tr>
		 -->
	</table>
	</form>
</div>

<!-- 推送消息 -->
<div id="sysmsgMgrDlg" style="padding-left:5px;">
	<form id="sysmsgMgrForm" class="dlg-frm" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[2, 16]'"/>
			</td></tr>
		<tr><td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="content" rows="8" class="x-ipt3"></textarea>
			</td></tr>
	</table>
	</form>
</div>

<!-- 修改密码 -->
<div id="repwdMgrDlg" style="padding-left:5px;">
	<form id="repwdMgrForm" class="dlg-frm" method="post">
	<input type="hidden" name="id" />
	<table class="dlg-tb">
		<tr><td class="t-l">登录帐号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="x_input x-ipt3" readonly="readonly"/>
			</td></tr>
		<tr><td class="t-l">密码：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="pwd" class="x_input x-ipt3"/>
			</td></tr>
	</table>
	</form>
</div>

</div>
</body>
</html>

