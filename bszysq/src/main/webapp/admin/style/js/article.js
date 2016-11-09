$.extend(z, {
	clazz: 'article',
	gDlgHeight: 500
});
$.extend(z.dg, {
	gen_toolbar: function(zz){
		var tb = [
			{text:'添加', iconCls:'icon-standard-add', plain:true, handler: z.add.dlg_open},
			{text:'修改', iconCls:'icon-edit', plain:true, handler: z.edit.dlg_open},
			{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
			{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del},
//			'-',
//			{text:'禁用', iconCls:'icon-remove', plain:true, handler: zz.do_dis},
//			{text:'启用', iconCls:'icon-standard-tick', plain:true, handler: zz.do_undis},
//			'-',
			{text:'推荐', iconCls:'icon-remove', plain:true, handler: zz.do_recom},
			{text:'取消推荐', iconCls:'icon-standard-tick', plain:true, handler: zz.do_unrecom}
		];
		return tb;
	},
	gen_columns: function(zz){
		var cols = z.dg.columnBase([
			{field: 'name', title: '标题', width:220, align:'center'},	
			{field: 'catename', title: '版块', width:90, align:'center'},
			{field: 'tagname', title: '标签', width:90, align:'center'},
			z.dg.columnImg({field: 'img', title: '配图'}),
			{field: 'recom', title: '是否推荐', width:200, align:'center', formatter: function(v){
				return v == 1 ? '<span style="color:red;">推荐</span>' : '正常';
			}},
			{field: 'uname', title: '发布者', width:90, align:'center'}
		]);
		return cols;
	}
});
$.extend(z.add, {
	dlgOpt: {
		height: 280
	}
});
z.init();


$(function(){
	
	//$('#jar').remove();
	
	$('.i-search.cate_comb').combobox({
		panelHeight: 'auto', 
		editable: false, 
		width: 150,
		valueField: 'id', 
		textField: 'name',
		data: [{id:'', name: '全部'}].concat(jar.cates)
	});

	$('.i-search.tag_comb').combobox({
		panelHeight: 'auto', 
		editable: false, 
		width: 150,
		valueField: 'id', 
		textField: 'name',
		data: [{id:'', name: '全部'}].concat(jar.tags)
	});
	
});

(function(){
var Z = function(){};
Z.prototype = {
	// TODO 搜索
	srch: {
		/** 主题 */
		topicp:{
			init: function(){
				var zz = z.srch.topicp;
				zz.comb = z.srch.frm.find('.topicp_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
				var ts = z.srch.topic;
				ts.remote(ts.render, ts, {pid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/topic/list.json",
					data : M.apply({limit: -1, pid:0}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取主题失败');
						}
					}
				});
			},
			// 渲染数据
			render: function (iss, rows, zz){
				var rows_sch = [{id:'', name:'全部', selected:1}];
				if(iss){
					rows_sch = rows_sch.concat(rows);
				}
				zz.comb.combobox('loadData', rows_sch);
			}
		},
		/** 子主题 */
		topic:{
			init: function(){
				var zz = z.srch.topic;
				zz.comb = z.srch.frm.find('.topic_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/subtopic/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取子主题失败');
						}
					}
				});
			},
			// 渲染数据
			render: function (iss, rows, zz){
				var rows_sch = [{id:'', name:'全部', selected:1}];
				if(iss){
					rows_sch = rows_sch.concat(rows);
				}
				zz.comb.combobox('loadData', rows_sch);
			}
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
				maximizable:true,
				width:700,
				height:500,
				buttons:[
					{text: '添加', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			
			// 表单
			zz.frm = $('#addMgrForm');
			// 编辑器初始化
			zz.ue = Uer.getEditor('add-ue-cnt');
			zz.def_fvs = {id:'', cid:'', tpid:'', tid:'', name:'', 
					ftitle:'', img:'', imgtitle:'', sortn:'0', brief:'', 
					cnt:'', isdel:'', imgFile:''};
			
			zz.cate.init();
			zz.topicp.init();
			zz.topic.init();
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.add;
			zz.dlg.dialog('open');
			zz.frm.form('load', zz.def_fvs);
			zz.ue_show('');
			
			if(!zz.__maxed){
				zz.__maxed = 1;
				var max_btn = zz.dlg.parent().find('a.panel-tool-max');
				max_btn.trigger('click');
			}
		},
		/** 提交 */
		submit: function(){
			var zz = z.add;
			$.messager.progress();	// 显示一个进度条 
			zz.ue_swval();
			M.ajaxSubmit(zz.frm, {
				url: "/admin/art/article/save.json",
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
		/** 显示UE中并赋值 */
		ue_show: function(cnt){
	    	window.setTimeout(function(){
	    		z.add.ue.setContent(cnt);
	    	},50);
		},
		/** 将UE中的值赋值到对应的input中 */
		ue_swval: function(){
			var zz = z.add;
	        var cnt = zz.ue.getContent();
	        var cnt_ctrl = zz.frm.find("[name=cnt]");
	        cnt_ctrl.val(cnt);
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
		},
		/** 主题 */
		topicp:{
			init: function(){
				var zz = z.add.topicp;
				zz.comb = z.add.frm.find('.topicp_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
				var ts = z.add.topic;
				ts.remote(ts.render, ts, {pid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/topic/list.json",
					data : M.apply({limit: -1, pid:0}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取主题失败');
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
		},
		/** 子主题 */
		topic:{
			init: function(){
				var zz = z.add.topic;
				zz.comb = z.add.frm.find('.topic_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/subtopic/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取子主题失败');
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
			// 对话框
			var dlg = zz.dlg = $("#editMgrDlg");
			dlg.dialog({
				title: "修改",
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:true,
				width:600,
				height:500,
				buttons:[
					{text: '修改', iconCls: 'icon-ok', handler: zz.submit},
					{text: '取消', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
			// 表单
			zz.frm = $('#editMgrForm');
			// 编辑器初始化
			zz.ue = Uer.getEditor('edit-ue-cnt');
			// 字段默认值
			zz.def_fvs = {id:'', cid:'', tpid:'', tid:'', name:'', 
					ftitle:'', img:'', imgtitle:'', sortn:'', brief:'', 
					cnt:'', isdel:'', imgFile:''};
			
			zz.cate.init();
			zz.topicp.init();
			zz.topic.init();
		},
		/** 打开对话框 */
		dlg_open: function(){
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				var zz = z.edit;
				zz.remote(sel.id);
				
				if(!zz.__maxed){
					zz.__maxed = 1;
					var max_btn = zz.dlg.parent().find('a.panel-tool-max');
					max_btn.trigger('click');
				}
			}else{
				M.alert('请先选择一条记录!');
			}
		},
		// 远程加载待编辑数据
		remote: function(id, sopts){
			var zz = z.edit;
			$.ajax({
				url : "/admin/art/article/edit_load.json",
				data : M.apply({id: id}, sopts),
				success : function(d) {
					if(d && d.result){
						zz.edata = d.result;
						var sel = M.apply({}, d.result, zz.def_fvs);
						zz.dlg.dialog('open');
						var map = d.map;
						zz.cate.render(1, M.ssub_val(map, 'cms.rows'), zz.cate);
						zz.topicp.render(1, M.ssub_val(map, 'tpms.rows'), zz.topicp);
						zz.topic.render(1, M.ssub_val(map, 'tms.rows'), zz.topic);
						
						setTimeout(function(){
							zz.frm.form('load', sel);
							//zz.frm.find('#img').attr("src", M.buildImgUrl(sel.img, Imgs_Url));
							zz.ue_show(sel.cnt || '');
						}, 500);
					}else{
						zz.edata = '';
						M.err(d.message || '获取数据失败');
					}
				}
			});
			
		},
		/** 提交 */
		submit: function(){
			var zz = z.edit;
			$.messager.progress();	// 显示一个进度条 
			zz.ue_swval();
			M.ajaxSubmit(zz.frm, {
				url: "/admin/art/article/save.json",
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
		/** 显示UE中并赋值 */
		ue_show: function(cnt){
	    	window.setTimeout(function(){
	    		z.edit.ue.setContent(cnt);
	    	},50);
		},
		/** 将UE中的值赋值到对应的input中 */
		ue_swval: function(){
			var zz = z.edit;
	        var cnt = zz.ue.getContent();
	        var cnt_ctrl = zz.frm.find("[name=cnt]");
	        cnt_ctrl.val(cnt);
		},
		/** 栏目 */
		cate:{
			init: function(){
				var zz = z.edit.cate;
				zz.comb = z.edit.frm.find('.cate_comb');
			},
			onShowPanel: function(){
				var zz = z.edit.cate;
				if(zz.loaded) return ;
				zz.loaded = 1;
				//zz.remote(zz.render, zz);
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
		},
		/** 主题 */
		topicp:{
			init: function(){
				var zz = z.edit.topicp;
				zz.comb = z.edit.frm.find('.topicp_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
				var ts = z.edit.topic;
				ts.remote(ts.render, ts, {pid: nval});
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/topic/list.json",
					data : M.apply({limit: -1, pid:0}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取主题失败');
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
		},
		/** 子主题 */
		topic:{
			init: function(){
				var zz = z.edit.topic;
				zz.comb = z.edit.frm.find('.topic_comb');
			},
			onShowPanel: function(){
			},
			onChange: function(nval){
			},
			remote: function(cbfn, zz, sopts){
				$.ajax({
					url : "/admin/art/subtopic/list.json",
					data : M.apply({limit: -1}, sopts),
					success : function(d) {
						if(d && d.rows){
							zz.rows = d.rows;
							cbfn(1, d.rows, zz);
						}else{
							cbfn(0);
							zz.rows = '';
							M.err(d.message || '获取子主题失败');
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
	// TODO 查看对话框
	look: {
		init: function(){
			var zz = z.look;
			// TODO 查看对话框
			var dlg = zz.dlg = $("#lookMgrDlg");
			dlg.dialog({
				title: "查看",
				modal:true,
				closed:true,
				collapsible:false,
				minimizable:false,
				maximizable:true,
				width:414,
				height:500,
				buttons:[
					{text: '关闭', iconCls: 'icon-cancel', handler: function(){
						dlg.dialog('close'); 
					}}
				]
			});
		},
		/** 打开对话框 */
		dlg_open: function(){
			var zz = z.look;
			var sel = z.dg.dg.datagrid('getSel');
			if(sel && sel.id){
				zz.dlg.dialog('open');
				$('#look_ifr')[0].src = '/article/' + sel.id;
			}else{
				M.alert('请先选择一条记录!');
			}
		}
	},
	// TODO 业务函数
	fn:{
		remote_categorys: function(cbfn){
			$.ajax({
				url : "/admin/art/category/list.json",
				data : {limit: 100},
				success : function(d) {
					if(d && d.rows){
						categorys = d.rows;
						cbfn(1, categorys);
					}else{
						cbfn(0);
						categorys = '';
						M.err(d.message || '获取栏目失败');
					}
				}
			});
		},
		// 初始化 栏目下拉框
		init_categorys_comb: function (iss, rows){
			var rows_sch = [{id:'', name:'全部', selected:1}];
			var rows_e = [{id:'', name:'请选择', selected:1}];
			if(iss){
				rows_sch = rows_sch.concat(rows);
				rows_e = rows_e.concat(rows);
				rows_e[0].selected = 1;
			}
			
			var comb = $('.cate_comb');
			comb.combobox('loadData', rows_sch);
			
			var comb_e = $('.cate_comb_e');
			comb_e.combobox('loadData', rows_e);
		},
		remote_topics: function(cbfn){
			$.ajax({
				url : "/admin/art/topic/list.json",
				data : {limit: -1},
				success : function(d) {
					if(d && d.rows){
						topics = d.rows;
						cbfn(1, topics);
					}else{
						cbfn(0);
						categorys = '';
						M.err(d.message || '获取主题失败');
					}
				}
			});
		},
		// 初始化  主题下拉框
		init_topics_comb: function (iss, rows){
			var rows_sch = [{id:'', name:'全部', selected:1}, {id:'0', name:'一级'}];
			var rows_e = [];
			if(iss){
				rows = z.fn.lvl_topics(rows);
				rows_sch = rows_sch.concat(rows);
				rows_e = rows_e.concat(rows);
			}

			var comb = $('.l1_topics_comb');
			comb.combobox('loadData', rows_sch);
			
			var comb_e = $('.l1_topics_comb_e');
			comb_e.combobox('loadData', rows_e);
		},
		// 主题分级
		lvl_topics: function(rows){
			var nrs = [];
			var nobj = {};
			var row, nsobj;
			for(var i = 0; i < rows.length; i++){
				row = rows[i];
				nsobj = nobj[row.pid];
				if(!nsobj) nsobj = nobj[row.pid] = [];
				nsobj.push(row);
			}
			var tl1 = nobj[0];
			if(tl1){
				var tl2, sr;
				for(var i = 0; i < tl1.length; i++){
					row = tl1[i];
					nrs.push(row);
					tl2 = nobj[row.id];
					if(tl2){
						for(var k = 0; k < tl2.length; k++){
							sr = tl2[k];
							sr.name = '++++ ' + sr.name;
							nrs.push(sr);
						}
					}
				}
			}
			return nrs;
		}
	}
};

})();

