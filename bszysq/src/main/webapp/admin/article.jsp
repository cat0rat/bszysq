<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>文章管理</title>
	<meta name="description" content="后台管理" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/admin/inc/comm.jsp"%>
	<%@ include file="/admin/inc/easyui.jsp"%>
    <%@ include file="/admin/inc/ueditor.jsp" %>
	<script type="text/javascript" src="/admin/style/js/article.js"></script>
</head>
<body>
<div id="maskContainer">
	<div class="datagrid-mask" style="display: block;"></div>
	<div class="datagrid-mask-msg"
		style="display: block; left: 50%; margin-left: -52.5px;">
		正在加载...</div>
</div>

<div id="mainLayout" class="easyui-layout hidden" data-options="fit: true, border: false">

<div data-options="region:'north', border:false" class="s_show_north" style="height:70px;">
	<!-- 搜索区域 -->
<div class="l_search">
	<form id="search_form" >
		<div class="l_search_tr">
			<div class="l_search_td">
				版块：
				<select name="cateid" class="x_input i-search cate_comb" > 
					<option value="">全部</option>
				</select>
			</div>
			<div class="l_search_td">
				标签：
				<select name="tagid" class="x_input i-search tag_comb" > 
					<option value="">全部</option>
				</select>
			</div>
			<div class="l_search_td">
				标题：<input type="text" name="name" class="x_input"/>
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
	<form id="addMgrForm" class="dlg-frm" method="post" enctype="multipart/form-data">
	<table class="dlg-tb">
		<tr>
			<td class="t-l">栏目：</td>
			<td class="t-l-c">
				<select name="cid" class="x_input cate_comb" 
					data-options="panelHeight: 'auto', editable: false, width: 200,
						required:true, valueField: 'id', textField: 'name', 
						onShowPanel: art.add.cate.onShowPanel, onChange: art.add.cate.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
			<td class="t-r">&nbsp;</td>
			<td class="t-r-c"></td>
		</tr>
		<tr>
			<td class="t-l">主题：</td>
			<td class="t-l-c">
				<select name="tpid" class="x_input topicp_comb" 
					data-options="panelHeight:'auto', editable:false, width: 200, 
						required:true, valueField:'id', textField:'name', 
						onShowPanel: art.add.topicp.onShowPanel, onChange: art.add.topicp.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
			<td class="t-r">子主题：</td>
			<td class="t-r-c">
				<select name="tid" class="x_input topic_comb" 
					data-options="panelHeight:'auto', editable:false, width: 200, 
						required:true, valueField:'id', textField:'name', 
						onShowPanel: art.add.topic.onShowPanel, onChange: art.add.topic.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="required:true, validType:'length[2, 300]'"/>
			</td>
		</tr>
		<tr>
			<td class="t-l">序号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="sortn" class="easyui-numberbox x_input x-ipt3" value="1" min="0" max="1000"
					data-options="required:true" />
			</td>
		</tr>
		<tr>
	        <td class="t-l"><span>图片：</span></td>
			<td class="t-l-c">
				<input type="text" name="img" class="easyui-validatebox x_input"
					data-options="required:false" />
			</td>
			<td>&nbsp;</td>
			<td class="t-r-c">
				<input type="file" name="imgFile" />
			</td>
		</tr>
		<tr>
			<td class="t-l-c t-memo" colspan="4">
				图片尺寸: 如: 690 * 345
			</td>
		</tr>
		<tr>
			<td class="t-l">图片介绍：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="imgtitle" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="validType:'length[0, 300]'"/>
			</td>
		</tr>
		<tr>
	        <td class="t-l"><span>简介：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="brief" rows="5" class="x-ipt3"></textarea>
			</td>
		</tr>
		<tr>
			<td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<div class="ue-wpr" >
                    <script class="ue-srt" id="add-ue-cnt" type="text/plain" ></script>
                    <input type="hidden" name="cnt" class="easyui-validatebox" 
                    	data-options="validType:'length[0, 100000]'"/>
                </div>
			</td>
		</tr>
	</table>
	</form>
</div>  

<!-- 修改 -->
<div id="editMgrDlg" style="padding-left:5px;">
	<form id="editMgrForm" class="dlg-frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" />
	<table class="dlg-tb" style="padding:15px; padding-bottom:0px">
		<tr>
			<td class="t-l">栏目：</td>
			<td class="t-l-c">
				<select name="cid" class="x_input cate_comb" 
					data-options="panelHeight: 'auto', editable: false, width: 200,
						required:true, valueField: 'id', textField: 'name', 
						onShowPanel: art.edit.cate.onShowPanel, onChange: art.edit.cate.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
			<td class="t-r">&nbsp;</td>
			<td class="t-r-c"></td>
		</tr>
		<tr>
			<td class="t-l">主题：</td>
			<td class="t-l-c">
				<select name="tpid" class="x_input topicp_comb" 
					data-options="panelHeight:'auto', editable:false, width: 200, 
						required:true, valueField:'id', textField:'name', 
						onShowPanel: art.edit.topicp.onShowPanel, onChange: art.edit.topicp.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
			<td class="t-r">主题：</td>
			<td class="t-r-c">
				<select name="tid" class="x_input topic_comb" 
					data-options="panelHeight:'auto', editable:false, width: 200, 
						required:true, valueField:'id', textField:'name', 
						onShowPanel: art.edit.topic.onShowPanel, onChange: art.edit.topic.onChange"> 
					<option value="">请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="required:true, validType:'length[2, 300]'"/>
			</td>
		</tr>
		<tr>
			<td class="t-l">序号：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="sortn" class="easyui-numberbox x_input x-ipt3" value="1" min="0" max="1000"
					data-options="required:true" />
			</td>
		</tr>
		<tr>
	        <td class="t-l"><span>图片：</span></td>
			<td class="t-l-c">
				<input type="text" name="img" class="easyui-validatebox x_input"
					data-options="required:false" />
			</td>
			<td>&nbsp;</td>
			<td class="t-r-c">
				<input type="file" name="imgFile" />
			</td>
		</tr>
		<tr>
			<td class="t-l-c t-memo" colspan="4">
				图片尺寸: 如: 690 * 345
			</td>
		</tr>
		<tr>
			<td class="t-l">图片介绍：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="imgtitle" class="easyui-validatebox x_input x-ipt3" value=""
					data-options="validType:'length[0, 300]'"/>
			</td>
		</tr>
		<tr>
	        <td class="t-l"><span>简介：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="brief" rows="5" class="x-ipt3"></textarea>
			</td>
		</tr>
		<tr>
			<td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<div class="ue-wpr" >
                    <script id="edit-ue-cnt" class="ue-srt" type="text/plain" >
                    </script>
                    <input type="hidden" name="cnt" class="easyui-validatebox" 
                    	data-options="validType:'length[0, 100000]'"/>
                </div>
			</td>
		</tr>
	</table>
	</form>
</div>

<!-- 查看 -->
<div id="lookMgrDlg" style="padding-left:5px;">
	<iframe id="look_ifr" style="width: 100%; height: 100%; border: 0px; margin: 0px; paddinf:0px; overflow: hidden;"></iframe>
</div>

</div>
</body>
</html>

