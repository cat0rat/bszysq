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
	<script src="/admin/style/js/article.js" type="text/javascript"></script>
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
			<div class="l_search_td" style="width:130px">
				推荐：
				<select name="recom" class="x_input i-search easyui-combobox" data-options="panelHeight: 'auto', editable: false, width: 70" > 
					<option value="">全部</option> 
					<option value="0">正常</option> 
					<option value="1">推荐</option>
				</select>
			</div>
			<div class="l_search_td" style="width:180px">
				官方发布：
				<select name="adminadd" class="x_input i-search easyui-combobox" data-options="panelHeight: 'auto', editable: false, width: 90" > 
					<option value="">全部</option> 
					<option value="0">用户发布</option> 
					<option value="1">官方发布</option>
				</select>
			</div>
			<div class="l_search_td" style="width:160px">
				置顶：
				<select name="ding" class="x_input i-search easyui-combobox" data-options="panelHeight: 'auto', editable: false, width: 70" > 
					<option value="">全部</option> 
					<option value="0">正常</option> 
					<option value="1">置顶</option>
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
	<form id="addMgrForm" class="dlg-frm" method="post">
	<table class="dlg-tb">
		<tr><td class="t-l">栏目：</td>
			<td class="t-l-c" colspan="3">
				<select name="cateid" class="x_input x-ipt3 i_has_empty cate_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标签：</td>
			<td class="t-l-c" colspan="3">
				<select name="tagid" class="x_input x-ipt3 i_has_empty tag_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[1, 300]'"/>
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
		<tr><td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="content" rows="8" class="x-ipt3"></textarea>
			</td></tr>
		<tr class="tr-imgs"><td class="t-l" rowspan="3"><span>图片：</span></td>
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td></tr>
		<tr class="tr-imgs">
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td></tr>
	</table>
	</form>
</div>  

<!-- 修改 -->
<div id="editMgrDlg" style="padding-left:5px;">
	<form id="editMgrForm" class="dlg-frm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" />
	<table class="dlg-tb" style="padding:15px; padding-bottom:0px">
		<tr><td class="t-l">栏目：</td>
			<td class="t-l-c" colspan="3">
				<select name="cateid" class="x_input x-ipt3 i_has_empty cate_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标签：</td>
			<td class="t-l-c" colspan="3">
				<select name="tagid" class="x_input x-ipt3 i_has_empty tag_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[1, 300]'"/>
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
		<tr><td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="content" rows="8" class="x-ipt3"></textarea>
			</td></tr>
		<tr class="tr-imgs"><td class="t-l" rowspan="3"><span>图片：</span></td>
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td></tr>
		<tr class="tr-imgs">
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
				<input class="mi-upimg dis_block" field="imgs" type="button" value="选择图片" />
			</td></tr>
	</table>
	</form>
</div>

<!-- 查看
<div id="lookMgrDlg" style="padding-left:5px;">
	<iframe id="look_ifr" style="width: 100%; height: 100%; border: 0px; margin: 0px; paddinf:0px; overflow: hidden;"></iframe>
</div>
 -->
<!-- 查看 -->
<div id="lookMgrDlg" style="padding-left:5px;">
	<form id="lookMgrForm" class="dlg-frm">
	<table class="dlg-tb">
		<tr><td class="t-l">栏目：</td>
			<td class="t-l-c" colspan="3">
				<select name="cateid" class="x_input x-ipt3 i_has_empty cate_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标签：</td>
			<td class="t-l-c" colspan="3">
				<select name="tagid" class="x_input x-ipt3 i_has_empty tag_comb" > 
					<option value="">全部</option>
				</select>
			</td></tr>
		<tr><td class="t-l">标题：</td>
			<td class="t-l-c" colspan="3">
				<input type="text" name="name" class="easyui-validatebox x_input x-ipt3"
					data-options="required:true, validType:'length[1, 300]'"/>
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
		<tr><td class="t-l"><span>内容：</span></td>
			<td class="t-l-c" colspan="3">
				<textarea name="content" rows="8" class="x-ipt3"></textarea>
			</td></tr>
		<tr class="tr-imgs"><td class="t-l" rowspan="3"><span>图片：</span></td>
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
			</td></tr>
		<tr class="tr-imgs">
			<td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
			</td><td class="t-l td-img">
				<img class="mi-upimg-img" upimgshow="imgs" />
				<input upimgval="imgs" type="hidden" name="imgs" />
			</td></tr>
	</table>
	</form>
</div>


</div>
</body>
</html>

