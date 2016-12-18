<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>推送管理</title>
	<meta name="description" content="推送管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	<script src="/admin/style/js/sysmsg.js" type="text/javascript"></script>
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
			消息类型： <select name="rang" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
				<option value="0">系统消息</option>
				<option value="1">主题或评论</option> 
			</select>
		</div>
		<div class="l_search_td">
			推送范围： <select name="rang" class="easyui-combobox x_input" 
				data-options="panelHeight:'auto',editable:false, width: 90"> 
				<option value="">全部</option>
				<option value="1">安卓用户</option>
				<option value="2">IOS用户</option> 
				<option value="20">全部用户</option> 
			</select>
		</div>
		<div class="l_search_td">
			标题： <input type="text" name="name" class="x_input"/>
		</div>
		<div class="l_search_td">
			内容： <input type="text" name="content" class="x_input"/>
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
		<tr><td class="t-l">推送范围：</td>
			<td class="t-l-c" colspan="3">
				<select name="rang" class="easyui-combobox x_input x-ipt3" 
					data-options="panelHeight:'auto',required:true,editable:false"> 
					<option value="">请选择</option>
					<option value="1">安卓用户</option>
					<option value="2">IOS用户</option> 
					<option value="20">全部用户</option> 
				</select>
			</td>
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
					<option value="9">管理员</option>   
				    <option value="0" selected="selected">用户</option>
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
					<option value="9">管理员</option>   
				    <option value="0" selected="selected">用户</option>
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
				    <option value="0" selected="selected">安卓</option>
					<option value="9">苹果</option>   
				</select>
			</td></tr>
		<tr><td class="t-l">手机显示名：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="phonename" class="x_input x-ipt3" />
			</td></tr>
			
		<tr><td class="t-l">邮箱：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="email" class="x_input x-ipt3" />
			</td></tr>
	</table>
	</form>
</div>

</div>
</body>
</html>

