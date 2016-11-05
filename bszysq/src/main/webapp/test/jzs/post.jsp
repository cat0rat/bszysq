<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>post请求测试</title>
<meta name="description" content="商家自助服务平台后台管理" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="/js/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="post.js"></script>
</head>
<body>
<input id="login_btn" type="button" value="登录" /> <br>
<hr />
标签：
<input type="button" value="列表" fn="Arttag.list({})" />
<input type="button" value="查看" fn="Arttag.get(18)" />
<input type="button" value="添加" fn="Arttag.add()" />
<input type="button" value="更新" fn="Arttag.update()" />
<input type="button" value="删除" fn="Arttag.deletex(18)" />
<br>
<hr />
版块：
<input type="button" value="列表" fn="Category.list({name:'活动'})" />
<input type="button" value="查看" fn="Category.get(21)" />
<input type="button" value="添加" fn="Category.add()" />
<input type="button" value="更新" fn="Category.update()" />
<input type="button" value="删除" fn="Category.deletex(16)" />
<br>
</body>
</html>
