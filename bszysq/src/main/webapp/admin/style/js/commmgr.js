var S_Del = { 1: '正常', 0: '已禁用' };
var Imgs_Url = '';
var z = {
	clazz: '',	// 小写类名
	// 初始化
	init: function(){
		$(function(){
			z.dg.init();
			z.srch.init();
			z.add.init();
			z.edit.init();
			z.look.init();
			z.loaded();
			z.init_oth_jq();
		});
		m_i.upimgUtil();
	},
	init_oth_jq: function(){},
	// 页面加载完成,
	loaded: function(){
		var layout = $('#mainLayout');
        setTimeout(function () {
            layout.removeClass('hidden').layout('resize');
            $('#maskContainer').remove();
        }, 0);
	},
	def_fvs:{img:'', imgs:''},
	// TODO 表格
	dg:{
		init: function(){
			var zz = z.dg;
			//表格
			var dg = zz.dg = $('#mgr_dg');
			dg.datagrid({
				checkbox: true,
				fit: true,
				rownumbers: true,
				pagination: true,
				pageSize: 20,
				pageList: [10, 20, 50, 100],
				nowrap: false,
				striped: true,
				singleSelect: true,
				selectOnCheck: false,
				checkOnSelect: false,
				fitColumns: true,
				toolbar: zz.gen_toolbar(zz),
				url: '/admin/' + z.clazz + '/list.json',
				queryParams: $.extend({}, z.listps),
				columns : zz.gen_columns(zz),
				onLoadError : function() {
					$.messager.alert('温馨提示', '系统繁忙，请重试!', 'error');
				},
				loadFilter: function(data){
					data = data.data ? data.data : data;
					data || (data = {});
					var rows = data.rows;
					if(rows && rows.length){
						$.each(data.rows, function(ix, row){
							if(row){
								if(row.ctime){
									row.ctimeStr = m_i.to_dstr(row.ctime);
								}
								if(row.utime){
									row.utimeStr = m_i.to_dstr(row.utime);
								}
							}
						});
					}
					zz.loadFilter(rows, data);
					return data;
				}
			});
		},
		// 处理 datagrid请求返回的数据
		loadFilter: function(rows, data){
			
		},
		gen_toolbar: function(zz){
			var tb = [
				{text:'添加', iconCls:'icon-standard-add', plain:true, handler: z.add.dlg_open},
				{text:'修改', iconCls:'icon-edit', plain:true, handler: z.edit.dlg_open},
				{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
				{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del}//,
//				'-',
//				{text:'禁用', iconCls:'icon-remove', plain:true, handler: zz.do_dis},
//				{text:'启用', iconCls:'icon-standard-tick', plain:true, handler: zz.do_undis}
			];
			return tb;
		},
		gen_columns: function(zz){
			var cols = [[ 
			    {field: 'ck', checkbox: true}, 
				{field: 'id', title: '编号', width:100, align:'center', sortable: true},
				{field: 'name', title: '名称', width:220, align:'center', sortable: true},	
//				{field: 'img', title: '图片', width:150, align:'center', formatter: function(v, r, ix){
//					return '<img src="' + M.buildImgUrl(v, Imgs_Url) + '" alt="已删除" width="100px" height="60px" />';
//				}},
				{field: 'sortn', title: '序号', width:50, align:'center', sortable: true, formatter: function(v, r, ix){
					return '<div style="height:60px; line-height: 60px;" >' + v + '</div>';
				}}//,
//				{field: 'brief', title: '简介', width:220, align:'center'}//,
//				{field: 'isdel', title: '状态', width:220, align:'center', formatter: function(v, r, ix){
//					return S_Del[v] || '未知';
//				}}
			]];
			return cols;
		},
		/** 删除 */
		do_del: function(){
			var dg = z.dg.dg;
			M.eu.dg_del(dg, 'id', 'name', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids};
				$.ajax({
					url : '/admin/' + z.clazz + '/dels.json',
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.code == '200'){
							M.alert('删除成功');
							dg.datagrid('load');
							z.index_nav.reload();
						}else{
							M.err(d.message || '删除失败');
						}
					}
				});
			});
		},
		/** 禁用 */
		do_dis: function(){
			var dg = z.dg.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '禁用', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'0'};
				$.ajax({
					url : '/admin/' + z.clazz + '/diss.json',
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.code == '200'){
							M.alert('已禁用');
							dg.datagrid('load');
						}else{
							M.err(d.message || '禁用失败');
						}
					}
				});
			});
		},
		/** 启用 */
		do_undis: function(){
			var dg = z.dg.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '启用', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'1'};
				$.ajax({
					url : '/admin/' + z.clazz + '/diss.json',
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.code == '200'){
							M.alert('已启用');
							dg.datagrid('load');
						}else{
							M.err(d.message || '禁用失败');
						}
					}
				});
			});
		},
		/** 置顶 */
		do_ding: function(){
			var dg = z.dg.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '置顶', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'1'};
				$.ajax({
					url : '/admin/' + z.clazz + '/dings.json',
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.code == '200'){
							M.alert('已置顶');
							dg.datagrid('load');
						}else{
							M.err(d.message || '置顶失败');
						}
					}
				});
			});
		},
		/** 普通 */
		do_unding: function(){
			var dg = z.dg.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '设置为普通', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'0'};
				$.ajax({
					url : '/admin/' + z.clazz + '/dings.json',
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.code == '200'){
							M.alert('已设置为普通');
							dg.datagrid('load');
						}else{
							M.err(d.message || '设置为普通失败');
						}
					}
				});
			});
		},
		columnIsdel: function(){
			return {field: 'isdelStr', title: '状态', width: 60, align: 'center', sortable: true};
		},
		columnImg: function(opt){
			return $.extend({field : 'img', title : '配图', width : 100, align : 'center', formatter: function(val){
				if(val) return '<img style="width:90px; height: 90px;" src="' + val + '" >';
			}}, opt);
		},
		columnMidImg: function(opt){
			return $.extend({field : 'img', title : '配图', width : 100, align : 'center', formatter: function(val){
				if(val) return '<img style="width:60px; height: 60px;" src="' + val + '" >';
			}}, opt);
		},
		columnMinImg: function(opt){
			return $.extend({field : 'img', title : '配图', width : 100, align : 'center', formatter: function(val){
				if(val) return '<img style="width:30px; height: 30px;" src="' + val + '" >';
			}}, opt);
		},
		columnHeadImg: function(opt){
			return $.extend({field : 'head', title : '头像', width : 60, align : 'center', formatter: function(val){
				if(val) return '<img style="width:60px; height: 60px;" src="' + val + '" >';
			}}, opt);
		},
		columnDateTime:function(opt){
			return $.extend({field : 'ctime', title : '添加时间', width : 60, align : 'center', sortable: true, formatter: function(val){
				if(val) return new Date(val).fmt();
			}}, opt);
		},
		columnBase: function(cols){
			var arr = [{field: 'ck', checkbox: true}, 
						{field: 'id', title: '编号', width:60, align:'center', sortable: true}];
			if(cols && cols.length){
				for(var i = 0; i < cols.length; i++){
					arr.push(cols[i]);
				}
			}
			arr.push(z.dg.columnDateTime({field: 'ctime', title: '添加时间'}));
			arr.push(z.dg.columnDateTime({field: 'utime', title: '更新时间'}));
//			arr.push({field: 'ctimeStr', title: '添加时间', width: 80, align: 'center', sortable: true});
//			arr.push({field: 'utimeStr', title: '更新时间', width: 80, align: 'center', sortable: true});
			//arr.push({field: 'isdelStr', title: '状态', width: 60, align: 'center'});
			return [arr];
		},
	},
	// TODO 搜索
	srch: {
		init: function(){
			var zz = z.srch;
			// TODO 查询
			var frm = zz.frm = $('#search_form');
			zz.sbtn = $('#search_btn');
			zz.sbtn.on('click', zz.search);
			$(frm).keyup(function(event){
				if(event.keyCode ==13){
					zz.search();
					event.preventDefault();  //阻止默认行为 ( 表单提交 )
					event.stopPropagation();    //  阻止事件冒泡
					return false;
				}
			});
			frm[0].onsubmit = function(){
				//zz.search();
				return false;
			};
		},
		search: function(){
			var ds = M.formValues('#search_form');
			z.dg.dg.datagrid('load', $.extend({}, ds, z.listps));
		}
	},
	// TODO 添加对话框
	add:{
		init: function(){
			var zz = z.add;
			// 对话框
			var dlg = zz.dlg = $('#addMgrDlg');
			var opt = $.extend({
				title: '添加',
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: z.gDlgWidth || 600,
				height: z.gDlgHeight || 160,
				buttons:[
					{text: '添加', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			}, zz.dlgOpt);
			dlg.dialog(opt);

			// 表单
			zz.frm = $('#addMgrForm');
			zz.def_fvs = M.apply({id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''}, zz.def_fvs, z.def_fvs);
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.add;
			zz.dlg.dialog('open');
			if(zz.is_clear_before !== 0){
				zz.frm.form('load', zz.def_fvs);
				m_i.upimgShowUtil(zz.frm, zz.def_fvs);
			}
		},
		//isAjax: 0,
		/** 提交 */
		submit: function(){
			var zz = z.add;
			$.messager.progress();	// 显示一个进度条
			var isValid = zz.frm.form('validate');
			if (!isValid){
				$.messager.progress('close');	// 当form不合法的时候隐藏工具条
				return ;
			}
			var url = '/admin/' + z.clazz + '/add.json';
			if(zz.isAjax){
				var ps = M.formValues({selector:zz.frm});
				console.log(ps);
				var pps = {ps:ps};
				zz.on_submit_ps(ps, zz, pps);
				ps = pps.ps;
				$.ajax({
					data : ps,
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
			var zz = z.add;
			if(!zz.isAjax) d = Mao.decode(d);
			if(d.code == '200'){
				zz.dlg.dialog('close');
				z.dg.dg.datagrid('load');
				z.index_nav.reload();
			}else{
				M.err(d.msg || '添加失败');
			}
		}
	},
	// TODO 修改对话框
	edit:{
		init: function(){
			var zz = z.edit;
			// 修改对话框
			var dlg = zz.dlg = $('#editMgrDlg');
			var opt = $.extend({
				title: '修改',
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: z.gDlgWidth || 600,
				height: z.gDlgHeight || 160,
				buttons:[
					{text: '修改', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			}, zz.dlgOpt);
			dlg.dialog(opt);
			// 表单
			zz.frm = $('#editMgrForm');
			zz.def_fvs = M.apply({id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''}, zz.def_fvs, z.def_fvs);
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.edit;
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				sel = M.apply({},  sel, zz.def_fvs);
				zz.on_open && zz.on_open(zz, sel);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				m_i.upimgShowUtil(zz.frm, sel);
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		//isAjax: 0,
		/** 提交 */
		submit: function(){
			var zz = z.edit;
			$.messager.progress();	// 显示一个进度条 
			var isValid = zz.frm.form('validate');
			if (!isValid){
				$.messager.progress('close');	// 当form不合法的时候隐藏工具条
				return ;
			}
			var url = '/admin/' + z.clazz + '/update.json';
			if(zz.isAjax){
				var ps = M.formValues({selector:zz.frm});
				console.log(ps);
				var pps = {ps:ps};
				zz.on_submit_ps(ps, zz, pps);
				ps = pps.ps;
				$.ajax({
					data : ps,
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
		on_submit_success: function(d){
			$.messager.progress('close');	// 当成功提交之后隐藏进度条
			var zz = z.edit;
			if(!zz.isAjax) d = Mao.decode(d);
			if(d.code == '200'){
				zz.dlg.dialog('close');
				z.dg.dg.datagrid('load');
				z.index_nav.reload();
			}else{
				M.err(d.message || '修改失败');
			}
		}
	},
	// TODO 查看对话框
	look:{
		init: function(){
			var zz = z.look;
			// 查看对话框
			var dlg = zz.dlg = $('#lookMgrDlg');
			var opt = $.extend({
				title: '查看',
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: z.gDlgWidth || 600,
				height: z.gDlgHeight || 160,
				buttons:[
					{text: '关闭', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			}, zz.dlgOpt);
			dlg.dialog(opt);
			// 表单
			zz.frm = $('#lookMgrForm');
			zz.def_fvs = M.apply({id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''}, zz.def_fvs, z.def_fvs);
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.look;
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var zz = z.look;
				sel = M.apply({},  sel, zz.def_fvs);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				m_i.upimgShowUtil(zz.frm, sel);
			}else{
				M.alert('请先选择一条记录!');
			}
		}
	},
	index_nav:{
		reload:function(){
			var win = window.parent;
			win && win.reload_nav_tree_art && win.reload_nav_tree_art();
		}
	}
};

//var z = window.topic = new Z;
//z.init();

