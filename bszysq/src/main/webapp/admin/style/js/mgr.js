(function(){

var S_Del = {
	1: '正常',
	0: '已禁用'
};
var Imgs_Url = '';

var Z = function(){};
Z.prototype = {
	// 初始化
	init: function(){
		$(function(){
			z.dg.init();
			z.srch.init();
			z.add.init();
			z.edit.init();
			z.look.init();
		});
	},
	// TODO 表格
	dg:{
		id: 'mgr_dg',
		def_opts:{
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
			fitColumns: true
		},
		url_del: '',
		opts:{},
		init: function(){
			var zz = z.dg;
			//表格
			var dg = zz.dg = $('#' + zz.id);
			zz.opts = m_i.applx({}, zz.opts, zz.def_opts);
			dg.datagrid(zz.opts);
//			dg.datagrid({
//				toolbar: [
//					{text:'添加', iconCls:'icon-standard-add', plain:true, handler: z.add.dlg_open},
//					{text:'修改', iconCls:'icon-edit', plain:true, handler: z.edit.dlg_open},
//					{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
//					{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del},
//					'-',
//					{text:'禁用', iconCls:'icon-remove', plain:true, handler: zz.do_dis},
//					{text:'启用', iconCls:'icon-standard-tick', plain:true, handler: zz.do_undis}
////					,
////					'-',
////					{text:'管理子主题', iconCls:'icon-search', plain:true, handler: z.sub.tab_open}
//				],
//				url: '/admin/art/topic/list.json',
//				columns : [[ 
//				    {field: 'ck', checkbox: true}, 
//					{field: 'id', title: '编号', width:100, align:'center', sortable: true},
//					{field: 'name', title: '名称', width:220, align:'center'},	
//					{field: 'img', title: '图片', width:150, align:'center', formatter: function(v, r, ix){
//						return '<img src="' + M.buildImgUrl(v, Imgs_Url) + '" alt="已删除" width="100px" height="60px" />';
//					}},
//					{field: 'brief', title: '简介', width:220, align:'center'},
//					{field: 'sortn', title: '序号', width:50, align:'center', sortable: true},
//					{field: 'isdel', title: '状态', width:220, align:'center', sortable: true, formatter: function(v, r, ix){
//						return S_Del[v] || '未知';
//					}}
//				]]
//			});
		},
		/** 删除 */
		do_del: function(){
			var zz = z.dg;
			var dg = zz.dg;
			M.eu.dg_del(dg, 'id', 'name', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids};
				$.ajax({
					url : zz.url_del,
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.success){
							M.alert('删除成功');
							dg.datagrid('load');
						}else{
							M.err(d.message || '删除失败');
						}
					}
				});
			});
		},
		/** 禁用 */
		do_dis: function(){
			var zz = z.dg;
			var dg = zz.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '禁用', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'0'};
				$.ajax({
					url : zz.url_dis,
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.success){
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
			var zz = z.dg;
			var dg = zz.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '启用', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'1'};
				$.ajax({
					url : zz.url_undis,
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.success){
							M.alert('已启用');
							dg.datagrid('load');
						}else{
							M.err(d.message || '禁用失败');
						}
					}
				});
			});
		}
	},
	// TODO 搜索
	srch: {
		id: 'search_form',
		bid: 'search_btn',
		init: function(){
			var zz = z.srch;
			// TODO 查询
			var frm = zz.frm = $('#' + z.id);
			zz.sbtn = $('#' + z.bid);
			zz.sbtn.on('click', function(){
				var ds = M.formValues('#' + z.srch.id);
				z.dg.dg.datagrid('load', ds);
			});
		}
	},
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
				width: 600,
				height: 280,
				buttons:[
					{text: '添加', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#addMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', sortn:'100', brief:'', isdel:'', imgFile:''};
			zz.cate.init();
			
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.add;
			zz.dlg.dialog('open');
			zz.frm.form('load', zz.def_fvs);
		},
		/** 提交 */
		submit: function(){
			var zz = z.add;
			$.messager.progress();	// 显示一个进度条 
			M.ajaxSubmit(zz.frm, {
				url: "/admin/art/topic/save.json",
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
		},
		/** 栏目 */
		cate:{
			init: function(){
				var zz = z.add.cate;
				zz.comb = z.add.frm.find('.cate_comb');
			},
			onShowPanel: function(){
				var zz = z.add.cate;
				if(zz.loaded) return ;
				zz.loaded = 1;
				zz.remote(zz.render, zz);
			},
			onChange: function(nval){
				var ts = z.add.topicp;
				ts.remote(ts.render, ts, {cid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/category/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取栏目失败');
						}
					}
				});
			},
			// 渲染数据
			render: function (iss, rows, zz){
				var rows_sch = [{id:'', name:'请选择', selected:1}];
				if(iss){
					rows_sch = rows_sch.concat(rows);
				}
				zz.comb.combobox('loadData', rows_sch);
			}
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
				width: 600,
				height:280,
				buttons:[
					{text: '修改', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#editMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', brief:'', isdel:'', imgFile:''};
			zz.cate.init();
		},
		/** 打开对话框 */
		dlg_open: function(){
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var zz = z.edit;
				sel = M.apply({},  sel, zz.def_fvs);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				zz.frm.find('#img').attr("src", M.buildImgUrl(sel.img, Imgs_Url));
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		/** 提交 */
		submit: function(){
			var zz = z.edit;
			$.messager.progress();	// 显示一个进度条 
			M.ajaxSubmit(zz.frm, {
				url: "/admin/art/topic/save.json",
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
		},
		/** 栏目 */
		cate:{
			init: function(){
				var zz = z.edit.cate;
				zz.comb = z.edit.frm.find('.cate_comb');
				zz.onShowPanel();
			},
			onShowPanel: function(){
				var zz = z.edit.cate;
				if(zz.loaded) return ;
				zz.loaded = 1;
				zz.remote(zz.render, zz);
			},
			onChange: function(nval){
				var ts = z.edit.topicp;
				ts.remote(ts.render, ts, {cid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/category/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取栏目失败');
						}
					}
				});
			},
			// 渲染数据
			render: function (iss, rows, zz){
				var rows_sch = [{id:'', name:'请选择', selected:1}];
				if(iss){
					rows_sch = rows_sch.concat(rows);
				}
				zz.comb.combobox('loadData', rows_sch);
			}
		}
	},
	// TODO 子主题管理
	sub: {
		tab_open: function(){
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var win = window.parent;
				win.M.tabs.toCenter({
					url: '/admin/art/subtopic/' + sel.id,
					title: sel.name + '-子主题管理',
					closeOld: 1
				});
			}else{
				M.alert('请先选择一条记录!');
			}
		}
	},
	// TODO 查看对话框
	look:{
		init: function(){
			var zz = z.look;
			// 查看对话框
			var dlg = zz.dlg = $("#lookMgrDlg");
			dlg.dialog({
				title: "查看",
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:false,
				width: 660,
				height: 280,
				buttons:[
					{text: '关闭', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#lookMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', brief:'', isdel:'', imgFile:''};
			zz.cate.init();
		},
		/** 打开对话框 */
		dlg_open: function(){
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var zz = z.look;
				sel = M.apply({},  sel, zz.def_fvs);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				zz.frm.find('#img').attr("src", M.buildImgUrl(sel.img, Imgs_Url));
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		/** 栏目 */
		cate:{
			init: function(){
				var zz = z.look.cate;
				zz.comb = z.look.frm.find('.cate_comb');
				zz.onShowPanel();
			},
			onShowPanel: function(){
				var zz = z.look.cate;
				if(zz.loaded) return ;
				zz.loaded = 1;
				zz.remote(zz.render, zz);
			},
			onChange: function(nval){
				var ts = z.look.topicp;
				ts.remote(ts.render, ts, {cid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/category/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取栏目失败');
						}
					}
				});
			},
			// 渲染数据
			render: function (iss, rows, zz){
				var rows_sch = [{id:'', name:'请选择', selected:1}];
				if(iss){
					rows_sch = rows_sch.concat(rows);
				}
				zz.comb.combobox('loadData', rows_sch);
			}
		}
	}
};

var z = window.topic = new Z;
z.init();

})();

