var S_rolex = { 0: '普通用户', 9: '<font style="color: #00f">超级管理员</font>' };
var S_phonetype = { 0: '安卓', 1: '<font style="color: #00f">苹果</font>' };
var S_sex = { 1: '男', 2: '<font style="color: #00f">女</font>' };
var S_authx = { 0: '<font style="color: #00f">已认证</font>', 1: '未认证', 2: '<font style="color: #f00">待审核</font>' };
var S_isdel = { 0: '正常', 1: '<font style="color: #f00">已封号</font>' };
var S_sendable = { 0: '正常', 1: '<font style="color: #999">不能</font>' };
$.extend(z, {
	clazz: 'user',
	gDlgHeight: 300,
	init_oth_jq: function(){
		z.sysmsg.init();
		z.repwd.init();
	},
	// TODO 推送消息
	sysmsg:{
		init: function(){
			var zz = z.sysmsg;
			zz.data = {};
			// 对话框
			var dlg = zz.dlg = $('#sysmsgMgrDlg');
			var opt = $.extend({
				title: '推送消息',
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: zz.dlgWidth || z.gDlgWidth || 600,
				height: zz.dlgHeight || z.gDlgHeight || 160,
				buttons:[
					{text: '推送', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			}, zz.dlgOpt);
			dlg.dialog(opt);

			// 表单
			zz.frm = $('#sysmsgMgrForm');
			zz.def_fvs = 0;//M.apply({id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''}, zz.def_fvs, z.def_fvs);
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.sysmsg;
			zz.dlg.dialog('open');
			zz.def_fvs && zz.frm.form('load', zz.def_fvs);
		},
		isAjax: 1,
		/** 提交 */
		submit: function(){
			var zz = z.sysmsg;
			$.messager.progress();	// 显示一个进度条
			var isValid = zz.frm.form('validate');
			if (!isValid){
				$.messager.progress('close');	// 当form不合法的时候隐藏工具条
				return ;
			}
			var url = '/admin/' + z.clazz + '/send/sysmsg.json';
			if(zz.isAjax){
				var ps = M.formValues({selector:zz.frm});
				ps.ids = zz.data.ids;
				var pps = {ps:ps};
				zz.on_submit_ps(ps, zz, pps);
				ps = pps.ps;
				$.ajax({
					data : ps,
					url : url,
					type: 'POST',
					success : zz.on_submit_success,
					error: function(){ $.messager.progress('close'); M.err('请求失败'); }
				});
			}else{
				M.ajaxSubmit(zz.frm, {
					url: url,
					success: zz.on_submit_success
				});
			}
		},
		on_submit_ps: function(ps, pps){
			
		},
		on_submit_success: function(d){
			$.messager.progress('close');	// 当成功提交之后隐藏进度条
			var zz = z.sysmsg;
			if(!zz.isAjax) d = Mao.decode(d);
			if(d.code == '200'){
				zz.dlg.dialog('close');
				M.alert('推送成功');
			}else{
				M.err(d.msg || '推送失败');
			}
		}
	},
	// TODO 修改密码
	repwd:{
		init: function(){
			var zz = z.repwd;
			zz.data = {};
			// 对话框
			var dlg = zz.dlg = $('#repwdMgrDlg');
			var opt = $.extend({
				title: '修改密码',
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: 600,
				height: 160,
				buttons:[
					{text: '修改', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			}, zz.dlgOpt);
			dlg.dialog(opt);

			// 表单
			zz.frm = $('#repwdMgrForm');
			zz.def_fvs = M.apply({id:'', name:'', pwd:''}, zz.def_fvs, z.def_fvs);
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.repwd;
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				sel.pwd !== undefined && (delete sel.pwd);
				sel = M.apply({},  sel, zz.def_fvs);
				zz.on_open && zz.on_open(zz, sel);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				m_i.upimgShowUtil(zz.frm, sel);
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		isAjax: 1,
		/** 提交 */
		submit: function(){
			var zz = z.repwd;
			$.messager.progress();	// 显示一个进度条
			var isValid = zz.frm.form('validate');
			if (!isValid){
				$.messager.progress('close');	// 当form不合法的时候隐藏工具条
				return ;
			}
			var url = '/admin/' + z.clazz + '/repwd.json';
			if(zz.isAjax){
				var ps = M.formValues({selector:zz.frm});
				var pps = {ps:ps};
				zz.on_submit_ps(ps, zz, pps);
				ps = pps.ps;
				$.ajax({
					data : ps,
					type: 'POST',
					url : url,
					success : zz.on_submit_success,
					error: function(){ $.messager.progress('close'); M.err('请求失败'); }
				});
			}else{
				M.ajaxSubmit(zz.frm, {
					url: url,
					success: zz.on_submit_success
				});
			}
		},
		on_submit_ps: function(ps, pps){
			
		},
		on_submit_success: function(d){
			$.messager.progress('close');	// 当成功提交之后隐藏进度条
			var zz = z.repwd;
			if(!zz.isAjax) d = Mao.decode(d);
			if(d.code == '200'){
				zz.dlg.dialog('close');
				M.alert('修改密码成功');
			}else{
				M.err(d.msg || '修改密码失败');
			}
		}
	}
});
$.extend(z.dg, {
	gen_toolbar: function(zz){
		var tb = [
			{text:'添加', iconCls:'icon-add', plain:true, handler: z.add.dlg_open},
			{text:'修改', iconCls:'icon-edit', plain:true, handler: z.edit.dlg_open},
			{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
			{text:'删除', iconCls:'icon-cancel', plain:true, handler: zz.do_del},
//			'-',
//			{text:'禁用', iconCls:'icon-remove', plain:true, handler: zz.do_dis},
//			{text:'启用', iconCls:'icon-tick', plain:true, handler: zz.do_undis},
			'-',
			{text:'认证通过', iconCls:'icon-ok', plain:true, handler: zz.do_authx_ok},
			{text:'认证不通过', iconCls:'icon-no', plain:true, handler: zz.do_authx_no},
			'-',
			{text:'恢复', iconCls:'icon-ok', plain:true, handler: zz.do_isdel_ok},
			{text:'封号', iconCls:'icon-no', plain:true, handler: zz.do_isdel_no},
			'-',
			{text:'推送消息', iconCls:'icon-ok', plain:true, handler: zz.do_sysmsg},
			{text:'修改密码', iconCls:'icon-ok', plain:true, handler: z.repwd.dlg_open}
		];
		return tb;
	},
	gen_columns: function(zz){
		var cols = z.dg.columnBase([
			z.dg.columnMinImg({field: 'head', title: '头像', width:35}),
			{field: 'name', title: '登录账号', width:90, align:'center', sortable: true, formatter: function(v, r, ix){
				return '<b>' + v + '</b>';
			}},
			{field: 'nname', title: '昵称', width:90, align:'center', sortable: true},
			{field: 'rolex', title: '角色', width:60, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_rolex[v] || '未知';
			}},
			
//			{field: 'unionid', title: '微信唯一id', width:90, align:'center'},
//			{field: 'openid', title: '微信id', width:90, align:'center'},
//			{field: 'getuicid', title: '个推ClientID', width:90, align:'center'},
			
			{field: 'phonetype', title: '手机类型', width:50, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_phonetype[v] || '未知';
			}},
			{field: 'authx', title: '认证状态', width:60, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_authx[v] || '未知';
			}},
			{field: 'tname', title: '认证姓名', width:60, align:'center', sortable: true},
			{field: 'address', title: '认证地址', width:90, align:'center', sortable: true},
			{field: 'sendable', title: '可推送消息', width:60, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_sendable[v] || '未知';
			}},
			{field: 'isdel', title: '用户状态', width:60, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_isdel[v] || '未知';
//			}},
//			{field: 'sex', title: '性别', width:60, align:'center', sortable: true, formatter: function(v, r, ix){
//				return S_sex[v] || '未知';
			}}
			]);
		return cols;
	},
	loadFilter: function(rows, data){
		rows && $.each(rows, function(ix, row){
			row.sendable = row.isdel === 0 && row.getuicid ? 0 : 1;
		});
	},
	/** 认证通过 */
	do_authx_ok: function(){
		var dg = z.dg.dg;
		M.eu.dg_ids_opts(dg, 'id', 'nname', '设置认证通过', function(ids, sels){
			$.messager.progress();
			var ps = {ids: ids, status:0};
			$.ajax({
				url : '/admin/' + z.clazz + '/option/authx.json',
				data : ps,
				success : function(d) {
					$.messager.progress('close');
					if(d.code == '200'){
						M.alert('已设置认证通过');
						dg.datagrid('load');
					}else{
						M.err(d.message || '设置认证通过失败');
					}
				}
			});
		});
	},
	/** 认证不通过 */
	do_authx_no: function(){
		var dg = z.dg.dg;
		M.eu.dg_ids_opts(dg, 'id', 'nname', '设置认证不通过', function(ids, sels){
			$.messager.progress();
			var ps = {ids: ids, status:1};
			$.ajax({
				url : '/admin/' + z.clazz + '/option/authx.json',
				data : ps,
				success : function(d) {
					$.messager.progress('close');
					if(d.code == '200'){
						M.alert('已设置认证不通过');
						dg.datagrid('load');
					}else{
						M.err(d.message || '设置认证不通过失败');
					}
				}
			});
		});
	},
	/** 恢复用户 */
	do_isdel_ok: function(){
		var dg = z.dg.dg;
		M.eu.dg_ids_opts(dg, 'id', 'nname', '恢复用户', function(ids, sels){
			$.messager.progress();
			var ps = {ids: ids, status:0};
			$.ajax({
				url : '/admin/' + z.clazz + '/option/isdel.json',
				data : ps,
				success : function(d) {
					$.messager.progress('close');
					if(d.code == '200'){
						M.alert('已恢复用户');
						dg.datagrid('load');
					}else{
						M.err(d.message || '恢复用户失败');
					}
				}
			});
		});
	},
	/** 封号用户 */
	do_isdel_no: function(){
		var dg = z.dg.dg;
		M.eu.dg_ids_opts(dg, 'id', 'nname', '封号用户', function(ids, sels){
			$.messager.progress();
			var ps = {ids: ids, status:1};
			$.ajax({
				url : '/admin/' + z.clazz + '/option/isdel.json',
				data : ps,
				success : function(d) {
					$.messager.progress('close');
					if(d.code == '200'){
						M.alert('已封号用户');
						dg.datagrid('load');
					}else{
						M.err(d.message || '封号用户失败');
					}
				}
			});
		});
	},
	/** 推送消息 */
	do_sysmsg: function(){
		var dg = z.dg.dg;
		M.eu.dg_ids_opts(dg, 'id', 'nname', '推送消息', function(ids, sels){
			z.sysmsg.data = {ids: ids};
			z.sysmsg.dlg_open();
		}, function(sels){
			var nss = [];
			$.each(sels, function(ix, sel){
				sel.sendable === 0 && nss.push(sel);
			});
			return nss;
		});
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

