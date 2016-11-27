<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// 控制角色权限(临时这样定写)
	// TODO  超级管理员
%>

<div id='i_menu' class='mainMenu' disc='菜单' style='border:none;'>
    <a href='#' class='easyui-menubutton ttt' data-options="menu:'#m_123'">
    <img src='/style/icons/icon-hamburg/32x32/address.png'/>系统管理</a>
    
    <a href='#' class='easyui-menubutton ttt' data-options="menu:'#m_134'">
    <img src='/style/icons/icon-hamburg/32x32/address.png'/>文章管理</a>
    
</div>


<!-- 文章管理 -->
<div id='m_134' class='subMenu menuBg1'>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/art/category" class='a_ac_tab aa' menuId='177' data-options='plain:true'>栏目管理</a>
	</div>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/art/topic" class='a_ac_tab aa' menuId='177' data-options='plain:true'>主题管理</a>
	</div>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/art/article" class='a_ac_tab aa' menuId='177' data-options='plain:true'>文章管理</a>
	</div>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/art/slider" class='a_ac_tab aa' menuId='177' data-options='plain:true'>轮播管理</a>
	</div>
</div>

<!-- 系统管理 -->
<div id='m_123' class='subMenu menuBg1'>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/sys/user_info"
			class='a_ac_tab aa' menuId='187' data-options='plain:true'>用户列表</a>
	</div>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/to_pwd_edit.php?adminId=${cur_user_session.id }"
			class='a_ac_tab aa' menuId='187' data-options='plain:true'>修改密码</a>
	</div>
	<div data-options="iconCls:'icon-redo'">
		<a href="/admin/logout.php" onclick="if (confirm('确定要退出吗？')) return true; else return false;"
			class='a_ac_tab aa' menuId='187' data-options='plain:true'>退出</a>
	</div>
</div>