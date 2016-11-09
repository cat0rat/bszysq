$(function(){
	
	// TODO 登录
	var login_dlg = $("#login_dlg");
	var login_form = $('#login_form');
	login_dlg.dialog({
		title: "登录",
		modal:true,
		closed:false,
		closable:false,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		width:490,
		height:165,
		buttons:[
			{text: '登录', id: 'adm_login_btn', iconCls: 'icon-standard-tick', handler: login_dlg_login}
		]
	});
	$('#adm_login_btn, body').keydown(function(e){
		if(e.keyCode==13){
			login_dlg_login();
		};
	});
	
	function login_dlg_login(){
		var isValid = login_form.form('validate');
		if (isValid){
			var ds = M.formValues(login_form);
			$.messager.progress();
			$.ajax({
				url : "/admin/login.json",
				data : ds,
				success : function(d) {
					$.messager.progress('close');
					if(d.success){
						M.alert('登录成功, 正在跳转...');
						window.location.href = '/admin/';
					}else{
						M.err(d.message || '登录失败');
					}
				}
			});
		}
	}
	function captchaimg_fn(){
		captchaimg[0].src = '/login/captcha.ico?' + new Date().getTime();
	}
	var captchaimg = $('#captchaimg');
	captchaimg.on('click', captchaimg_fn);
	captchaimg_fn();

	
	
});

