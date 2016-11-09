<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	// 如果服务器返回错误信息, 提示
	var error = '${error }';
	if(error && error != 'null'){
		alert(error);
	}
</script>