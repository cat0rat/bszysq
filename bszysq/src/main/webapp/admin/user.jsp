<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>用户管理</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	<script src="/admin/style/js/user.js" type="text/javascript"></script>
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
			用户角色：
			<select name="xrole" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 150"> 
				<option value="">全部</option>
				<option value="9">管理员</option>   
			    <option value="1">用户</option> 
			</select>
		</div>
		<div class="l_search_td">
			用户名：
			<input type="text" name="uname" class="x_input"/>
		</div>
		<!-- 
		<div class="l_search_td">
			状态：
			<select name="isdel" class="easyui-combobox x_input"  
				data-options="panelHeight:'auto',editable:false, width: 150">
  					<option value="">全部</option>   
  					<option value="1">正常</option>  
				<option value="0">已禁用</option>   
			</select>
		</div>
		 -->
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
	<form id="addMgrForm" class="dlg-frm t-min" method="post">
	<table class="dlg-tb">
		<tr>
			<td class="t-l">角色：</td>
			<td class="t-l-c">
				<select name="xrole" class="easyui-combobox x_input" 
					data-options="panelHeight:'auto',required:true,editable:false, width: 200"> 
					<option value="">请选择</option>
					<option value="9">管理员</option>   
				    <option value="1">用户</option>
				</select>
			</td>
			<td class="t-r">&nbsp;</td>
			<td class="t-r-c">
				
			</td>
		</tr>
		<tr>
			<td class="t-l">账号：</td>
			<td class="t-l-c">
				<input type="text" name="uname" class="easyui-validatebox x_input"
					data-options="required:true, validType:'name[3, 20]'"/>
			</td>
			<td class="t-r">密码：</td>
			<td class="t-r-c">
				<input type="text" name="pwd" class="easyui-validatebox x_input"
					data-options="required:true, validType:'length[6, 20]'"/>
			</td>
		</tr>
		<tr>
			<td class="t-l">手机：</td>
			<td class="t-l-c">
				<input type="text" name="mobile" class="easyui-validatebox x_input"
					data-options="validType:['len[11]', 'mobile']"/>
			</td>
			<td class="t-r"></td>
			<td class="t-r-c">
				
			</td>
		</tr>
	</table>
	</form>
</div>

<!-- 修改用户 -->
<div id="editMgrDlg" style="padding-left:5px;">
	<form id="editMgrForm" class="dlg-frm t-min" method="post">
	<input type="hidden" name="id" />
	<table class="dlg-tb">
		<tr>
			<td class="t-l">角色：</td>
			<td class="t-l-c">
				<select name="xrole" class="easyui-combobox x_input" prompt="请选择"
					data-options="panelHeight:'auto',editable:false,required:true,validType:'comboEmp', width: 200">
					<option value="0">请选择</option>
					<option value="9">管理员</option>   
				    <option value="1">用户</option>
				</select>
			</td>
			<td class="t-r">&nbsp;</td>
			<td class="t-r-c">
				
			</td>
		</tr>
		<tr>
			<td class="t-l">账号：</td>
			<td class="t-l-c">
				<input type="text" name="uname" class="easyui-validatebox x_input"
					data-options="required:true, validType:'name[3, 20]'"/>
			</td>
			<td class="t-r">手机：</td>
			<td class="t-r-c">
				<input type="text" name="mobile" class="easyui-validatebox x_input"
					data-options="validType:['len[11]', 'mobile']"/>
			</td>
		</tr>
	</table>
	</form>
</div>

<!-- 查看用户 -->
<div id="lookMgrDlg" style="padding-left:5px;">
	<form id="lookMgrForm" class="dlg-frm t-min" method="post">
	<table class="dlg-tb">
		<tr>
			<td class="t-l">角色：</td>
			<td class="t-l-c">
				<select name="xrole" class="easyui-combobox x_input" 
					data-options="panelHeight:'auto',user_dg_deleditable:false, width: 200">
					<option value="">请选择</option>
					<option value="9">管理员</option>   
				    <option value="1">用户</option>
				</select>
			</td>
			<td class="t-r">&nbsp;</td>
			<td class="t-r-c">
				
			</td>
		</tr>
		<tr>
			<td class="t-l">账号：</td>
			<td class="t-l-c">
				<input type="text" name="uname" class="x_input"/>
			</td>
			<td class="t-r">手机：</td>
			<td class="t-r-c">
				<input type="text" name="mobile" class="x_input"/>
			</td>
		</tr>
	</table>
	</form>
</div>

</div>
</body>
</html>

