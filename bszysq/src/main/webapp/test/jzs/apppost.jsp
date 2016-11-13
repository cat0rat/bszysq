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
<style type="text/css">
input{ margin-top:5px; }
</style>
</head>
<body>
<input id="login_btn" type="button" value="登录" fn="AppLogin.login()" />
<input id="logout_btn" type="button" value="登出" fn="AppLogin.uc.logout()" />
<span id="nname"></span><img id="userhead" src="" style="width: 40px; height: 40px;" />
<br>
<hr />
注册：<br>
图形验证码：<img src="/app/captcha.ico" onclick="AppReg.recaptcha(this)" /> 
<input type="text" id="captcha" style="width: 40px;" />
短信验证码：<input type="text" id="smscode" style="width: 40px;" />
<input type="button" value="短信验证码" fn="AppReg.smscode()" />
<input type="button" value="注册" fn="AppReg.reg()" />
<br>
<hr />
<br>
<span style="color: red;">已登录：</span>
<br>
<hr />
用户：
<input type="button" value="我的信息" fn="AppUser.uc.mine()" />
<input type="button" value="我的简单信息" fn="AppUser.uc.simple()" />
<input type="button" value="认证" fn="AppUser.uc.auth()" /><br>
原密码：<input type="text" id="oldpwd" style="width: 40px;" value="111111" />
新密码：<input type="text" id="pwd" style="width: 40px;" value="111112" />
<input type="button" value="修改密码" fn="AppUser.uc.repwd()" />
<input type="button" value="添加" fn="Arttag.add()" />
<input type="button" value="更新" fn="Arttag.update()" />
<input type="button" value="删除" fn="Arttag.deletex(18)" />
<br>
<hr />
文章：
<input type="button" value="写主题" fn="AppArticle.uc.add()" />
<br>
<hr />
评论：
<input type="button" value="写评论" fn="AppComment.uc.add()" />
<br>
<hr />
<br>
<span style="color: red;">无需登录：</span>
<br>
<hr />
上传：
<input type="button" value="uptoken" fn="AppImgstore.uptoken()" />
<br>
<hr />
用户：
<input type="button" value="用户简单信息" fn="AppUser.simple(1005)" />
<input type="button" value="--找回密码" fn="AppUser.findpwd()" />
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
<input type="button" value="列表" fn="AppArticle.list({cateid: 3, lastid: 0})" />
<input type="button" value="查看" fn="AppArticle.get(119)" />
<br>
<hr />
评论：
<input type="button" value="列表" fn="AppComment.list({artid: 101})" />
<input type="button" value="查看" fn="AppComment.get(1)" />
<br>
<hr />
轮播图：
<input type="button" value="列表" fn="AppSlider.list()" />
<input type="button" value="查看" fn="AppSlider.get(1)" />
<br>
</body>
</html>
