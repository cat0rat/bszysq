<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>栏目管理</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
	<script type="text/javascript" src="/admin/style/js/category.js"></script>
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
				名称：<input type="text" name="name" class="x_input"/>
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

<!-- 添加对话框 -->
 <div id="addMgrDlg" style="padding-left:5px;">
	<form id="addMgrForm" class="dlg-frm t-min" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l">名称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="required:true, validType:'length[2, 100]'"/>
			</td></tr>
		<tr><td class="t-l">序号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="sortn" class="easyui-numberbox x_input x-ipt3" value="1" min="0" max="1000"
					data-options="required:true" />
			</td></tr>
		<tr><td class="t-l"><span>配图：</span></td>
			<td class="t-l-c" colspan="3">
				<img class="mi-upimg-img" upimgshow="img" />
				<input upimgval="img" type="hidden" name="img" />
				<input class="mi-upimg" field="img" type="button" value="选择图片" />
			</td></tr>
		<tr><td class="t-l-c t-memo" colspan="4">
				图片尺寸: 如: 32*32, 64*64等;
			</td></tr>
		<tr style="display: none;">
	        <td class="t-l"><span>简介：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="brief" rows="5" class="x-ipt3"></textarea>
			</td>
		</tr>
	</table>
	</form>
</div>  

<!-- 修改 -->
<div id="editMgrDlg" style="padding-left:5px;">
	<form id="editMgrForm" class="dlg-frm t-min" method="post">
	<input type="hidden" name="id" />
	<table class="dlg-tb">
		<tr><td class="t-l">名称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="required:true, validType:'length[2, 100]'"/>
			</td></tr>
		<tr><td class="t-l">序号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="sortn" class="easyui-numberbox x_input x-ipt3" value="1" min="0" max="1000"
					data-options="required:true" />
			</td></tr>
		<tr style="display: none;">
			<td class="t-l-c t-img-sec" colspan="4">
				<img class="t-img" src="" />
			</td></tr>
		<tr><td class="t-l"><span>配图：</span></td>
			<td class="t-l-c" colspan="3">
				<img class="mi-upimg-img" upimgshow="img" />
				<input upimgval="img" type="hidden" name="img" />
				<input class="mi-upimg" field="img" type="button" value="选择图片" />
			</td></tr>
		<tr><td class="t-l-c t-memo" colspan="4">
				图片尺寸: 如: 32*32, 64*64等;
			</td></tr>
		<tr style="display: none;">
	        <td class="t-l"><span>简介：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="brief" rows="5" class="x-ipt3"></textarea>
			</td></tr>
	</table>
	</form>
</div>

<!-- 查看 -->
<div id="lookMgrDlg" style="padding-left:5px;">
	<form id="lookMgrForm" class="dlg-frm t-min" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l">名称：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="required:true, validType:'length[2, 100]'"/>
			</td></tr>
		<tr><td class="t-l">序号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="sortn" class="easyui-numberbox x_input x-ipt3" value="1" min="0" max="1000"
					data-options="required:true" />
			</td></tr>
		<tr><td class="t-l"><span>配图：</span></td>
			<td class="t-l-c" colspan="3">
				<img class="mi-upimg-img" upimgshow="img" />
			</td></tr>
		<tr style="display: none;">
	        <td class="t-l"><span>简介：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="brief" rows="5" class="x-ipt3"></textarea>
			</td></tr>
	</table>
	</form>
</div>

</div>
</body>
</html>

