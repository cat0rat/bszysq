<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>post请求测试</title>
<meta name="description" content="" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/style/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="apppost.js" type="text/javascript"></script>
</head>
<body>
<input id="login_btn" type="button" value="登录" /> 
<input id="logout_btn" type="button" value="登出" />
<br>
<hr />
登录用户：
<input type="button" value="我的信息" fn="AppUser.mine()" />
<input type="button" value="查看" fn="Arttag.get(18)" />
<input type="button" value="添加" fn="Arttag.add()" />
<input type="button" value="更新" fn="Arttag.update()" />
<input type="button" value="删除" fn="Arttag.deletex(18)" />
<br>
<hr />
标签：
<input type="button" value="列表(id,nanme)" fn="AppArttag.list_idval({})" />
<input type="button" value="列表" fn="AppArttag.list({})" />
<input type="button" value="查看" fn="AppArttag.get(1)" />
<br>
<hr />
版块：
<input type="button" value="列表带一条文章" fn="AppCategory.list_art({})" />
<input type="button" value="查看" fn="AppCategory.get(1)" />
<br>
<hr />
文章：
<input type="button" value="列表" fn="AppArticle.list({cateid: 3, lastid: 118})" />
<input type="button" value="查看" fn="AppArticle.get(1)" />
<br>
<hr />
轮播图：
<input type="button" value="列表" fn="AppSlider.list()" />
<input type="button" value="查看" fn="AppSlider.get(1)" />
<br>
</body>
</html>
