$(function(){
	$('#edi').click(function(){
		edi_win.dialog('open');
	});
	var edi_win = $('#edi_win');
	var edi_win_form = $('#edi_win_form');
	edi_win.dialog({    
	    title: '修改密码',   
		modal:true,
		closed:true,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		width:670,
		height:350,
		buttons:[
			{text: '修改', iconCls: 'icon-ok', handler: function(){
				$.messager.progress();	// 显示一个进度条 
				edi_win_form.form('submit', {
//				M.ajaxSubmit(edi_win_form, {
					url: "/admin/updatePassword.json",
					onSubmit: function(){
						var isValid = edi_win_form.form('validate');
						if (!isValid){
							$.messager.progress('close');	// 当form不合法的时候隐藏工具条
						}
						return isValid;	// 返回false将停止form提交 
					},
					success: function(d){
					//	d = Mao.decode(d);
						$.messager.progress('close');	// 当成功提交之后隐藏进度条
						if(d.success){
							M.alert('修改成功');
							edi_win.dialog('close');
							window.location = '/admin/logout.php';
						}else{
							M.err(d.message || '修改失败');
						}
					}
				}); 
			}},
			{text: '取消', iconCls: 'icon-cancel', handler: function(){
				$('#edi_win').dialog('close'); 
			}}
		]
	});    
});