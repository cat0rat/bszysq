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
			z.loaded();
		});
	},
	// 页面加载完成,
	loaded: function(){
		var layout = $("#mainLayout");
        setTimeout(function () {
            layout.removeClass("hidden").layout("resize");
            $("#maskContainer").remove();
        }, 0);
	},
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
				toolbar: [
					{text:'添加', iconCls:'icon-standard-add', plain:true, handler: z.add.dlg_open},
					{text:'修改', iconCls:'icon-edit', plain:true, handler: z.edit.dlg_open},
					{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
					{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del}//,
//					'-',
//					{text:'禁用', iconCls:'icon-remove', plain:true, handler: zz.do_dis},
//					{text:'启用', iconCls:'icon-standard-tick', plain:true, handler: zz.do_undis}
				],
				url: '/admin/category/list.json',
				columns : [[ 
				    {field: 'ck', checkbox: true}, 
					{field: 'id', title: '编号', width:100, align:'center', sortable: true},
					{field: 'name', title: '名称', width:220, align:'center'},	
//					{field: 'img', title: '图片', width:150, align:'center', formatter: function(v, r, ix){
//						return '<img src="' + M.buildImgUrl(v, Imgs_Url) + '" alt="已删除" width="100px" height="60px" />';
//					}},
					{field: 'sortn', title: '序号', width:50, align:'center', sortable: true, formatter: function(v, r, ix){
						return '<div style="height:60px; line-height: 60px;" >' + v + '</div>';
					}}//,
//					{field: 'brief', title: '简介', width:220, align:'center'}//,
//					{field: 'isdel', title: '状态', width:220, align:'center', formatter: function(v, r, ix){
//						return S_Del[v] || '未知';
//					}}
				]],
				onLoadError : function() {
					$.messager.alert('温馨提示', '系统繁忙，请重试!', 'error');
				},
				loadFilter: function(data){
					return  data.data ? data.data : data;
				}
			});
		},
		/** 删除 */
		do_del: function(){
			var dg = z.dg.dg;
			M.eu.dg_del(dg, 'id', 'name', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids};
				$.ajax({
					url : "/admin/category/dels.json",
					data : ps,
					success : function(d) {
						$.messager.progress('close');
						if(d.success){
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
					url : "/admin/category/diss.json",
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
			var dg = z.dg.dg;
			M.eu.dg_ids_opts(dg, 'id', 'name', '启用', function(ids, sels){
				$.messager.progress();
				var ps = {ids: ids, status:'1'};
				$.ajax({
					url : "/admin/category/diss.json",
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
		init: function(){
			var zz = z.srch;
			// TODO 查询
			var frm = zz.frm = $('#search_form');
			zz.sbtn = $('#search_btn');
			zz.sbtn.on('click', function(){
				var ds = M.formValues('#search_form');
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
				height: 160,
				buttons:[
					{text: '添加', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});

			// 表单
			zz.frm = $('#addMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''};
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
				url: "/admin/category/add.json",
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
						z.index_nav.reload();
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
					{text: '修改', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#editMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''};
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.edit;
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				sel = M.apply({},  sel, zz.def_fvs);
				zz.dlg.dialog('open');
				zz.frm.form('load', sel);
				zz.frm.find('.t-img').attr("src", M.buildImgUrl(sel.img, Imgs_Url));
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		/** 提交 */
		submit: function(){
			var zz = z.edit;
			$.messager.progress();	// 显示一个进度条 
			M.ajaxSubmit(zz.frm, {
				url: "/admin/category/update.json",
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
						z.index_nav.reload();
					}else{
						M.err(d.message || '修改失败');
					}
				}
			});
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
				width:600,
				height:160,
				buttons:[
					{text: '关闭', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#lookMgrForm');
			zz.def_fvs = {id:'', name:'', img:'', sortn:'0', brief:'', isdel:'', imgFile:''};
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
				zz.frm.find('#img').attr("src", M.buildImgUrl(sel.img, Imgs_Url));
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

var z = window.topic = new Z;
z.init();

})();

