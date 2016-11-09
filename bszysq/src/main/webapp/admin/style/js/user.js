var S_Role = { 9: '管理员', 1: '用户' };
$.extend(z, {
	clazz: 'category',
	gDlgheight: 200
});
$.extend(z.dg, {
	gen_columns: function(zz){
		var cols = [[ 
		    {field: 'ck', checkbox: true}, 
			{field: 'id', title: '编号', width:100, align:'center', sortable: true},
			{field: 'name', title: '登录账号', width:220, align:'center', sortable: true, formatter: function(v, r, ix){
				return '<b>' + v + '</b>';
			}},
			{field: 'rolex', title: '角色', width:220, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_Role[v] || '未知';
			}},
			{field: 'mobile', title: '手机', width:220, align:'center', sortable: true},
			{field: 'utime', title: '更新时间', width:220, align:'center', sortable: true, sortable: true}
		]];
		return cols;
	}
});
z.init();

(function(){
var Z = function(){};
Z.prototype = {
	// TODO 添加对话框
	add:{
		init: function(){
			var zz = z.add;
			// 对话框
			var dlg = zz.dlg = $("#addMgrDlg");
			dlg.dialog({
				title: "添加",
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width:600,
				height:200,
				buttons:[
					{text: '添加', iconCls: 'icon-ok', handler: zz.valid_submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#addMgrForm');
			zz.def_fvs = {id:'', uname:'', pwd:'', mobile:'', xrole:''};
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.add;
			zz.dlg.dialog('open');
			zz.frm.form('load', zz.def_fvs);
		},
		/** 验证后提交 */
		valid_submit: function(){
			var zz = z.add;
			var isValid = zz.frm.form('validate');
			if (isValid){
				var unameCtl = zz.frm.find('[name=uname]');
				var uname = unameCtl.val();
				var ps = {'uname': uname};
				$.ajax({
					type : "POST",
					url : "/api/pub/has_uname.json",
					data : ps,
					success : function(msg) {
						if(msg){
							M.eu.input_invalid(unameCtl, "帐号已经在!");
						}else{
							zz.submit();	// (实际)提交添加
						}
					}
				}); 
			}
			return isValid;
		},
		/** 提交 */
		submit: function(){
			var zz = z.add;
			$.messager.progress();	// 显示一个进度条 
			M.ajaxSubmit(zz.frm, {
				url: "/admin/sys/user/save.json",
				onSubmit: function(){
					var isValid = zz.frm.form('validate');
					if (!isValid){
						$.messager.progress('close');	// 当form不合法的时候隐藏工具条
					}
					return isValid;	// 返回false将停止form提交 
				},
				success: function(d){
					d = Mao.decode(d);
					$.messager.progress('close');	// 当成功提交之后隐藏进度条
					if(d.success){
						zz.dlg.dialog('close');
						z.dg.dg.datagrid('load');
					}else{
						M.err(d.message || '添加失败');
					}
				}
			});
		}
	},
	// TODO 修改对话框
	edit:{
		init: function(){
			var zz = z.edit;
			// 修改对话框
			var dlg = zz.dlg = $("#editMgrDlg");
			dlg.dialog({
				title: "修改",
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width:600,
				height:160,
				buttons:[
					{text: '修改', iconCls: 'icon-ok', handler: zz.valid_submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#editMgrForm');
			zz.def_fvs = {id:'', uname:'', pwd:'', mobile:'', xrole:''};
		},
		/** 打开对话框 */
		dlg_open: function(){
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var zz = z.edit;
				sel = M.apply({},  sel, zz.def_fvs);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				
				var unameCtl = z.edit.frm.find('[name=uname]');
				unameCtl.attr('valbk', unameCtl.val());	//备份原始值(帐号), 未修改时,无需要验证
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		/** 验证后提交 */
		valid_submit: function(){
			var zz = z.edit;
			var isValid = zz.frm.form('validate');
			if (isValid){
				var unameCtl = zz.frm.find('[name=uname]');
				var uname = unameCtl.val();
				var unbk = unameCtl.attr('valbk');
				if(uname != unbk){	//修改了登录名, 验证
					var ps = {'uname': uname};
					$.ajax({
						type : "POST",
						url : "/api/pub/has_uname.json",
						data : ps,
						success : function(msg) {
							if(msg){
								M.eu.input_invalid(unameCtl, "用户名已经在!");
							}else{
								zz.submit();	// (实际)提交添加
							}
						}
					}); 
				}else{
					zz.submit();	// (实际)提交添加
				}
			}
			return isValid;
		},
		/** 提交 */
		submit: function(){
			var zz = z.edit;
			$.messager.progress();	// 显示一个进度条 
			M.ajaxSubmit(zz.frm, {
				url: "/admin/sys/user/save.json",
				onSubmit: function(){
					var isValid = zz.frm.form('validate');
					if (!isValid){
						$.messager.progress('close');	// 当form不合法的时候隐藏工具条
					}
					return isValid;	// 返回false将停止form提交 
				},
				success: function(d){
					d = Mao.decode(d);
					$.messager.progress('close');	// 当成功提交之后隐藏进度条
					if(d.success){
						zz.dlg.dialog('close');
						z.dg.dg.datagrid('load');
					}else{
						M.err(d.message || '修改失败');
					}
				}
			});
		}
	}
};

})();

