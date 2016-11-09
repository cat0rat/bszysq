<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<%-- 这是一个通用的jsp头包含文件 --%>

<%-- 
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
--%>


<!-- 0、引入 jquery 基础库 -->
<!--<script src="/style/jquery-1.11.1.js" type="text/javascript"></script>-->
<script src="/style/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/style/core/Mao.js" type="text/javascript"></script>
<script src="/style/js/core/m_i.js" type="text/javascript"></script>
<script type="text/javascript" id="jar">var jar = [${jar}]; jar = jar.length ? jar[0] : {};</script>

<!-- 百度统计
<script type="text/javascript">
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?5febd8dcd83506d39843ffd5ac126398";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
 -->
<style type="text/css">
a{ cursor: pointer; }
.mi-upimg{width: 100px;}
.mi-upimg-img{width: 90px; height: 90px;}
</style>

<link href="/admin/style/css/comm-mgr.css" rel="stylesheet" type="text/css" />

<%@ include file="/admin/inc/alertErr.jsp"%>