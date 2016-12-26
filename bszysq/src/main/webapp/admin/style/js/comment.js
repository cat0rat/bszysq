var t_ps = m_i.url_params();
var listps = {};
if(t_ps && t_ps.ps){
	var ps = t_ps.ps;
	listps.artid = ps.artid;
}
//ps && ps.ps && (function())();
$.extend(z, {
	clazz: 'comment',
	gDlgHeight: 380,
	listps: listps
});
$.extend(z.dg, {
	gen_toolbar: function(zz){
		var tb = [
			//{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
			{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del}
		];
		return tb;
	},
	gen_columns: function(zz){
		var cols = [[
 			{field: 'ck', checkbox: true}, 
			{field: 'id', title: '编号', width:30, align:'center', sortable: true},
			{field: 'artid', title: '主题编号', width:30, align:'center', sortable: true},	
			{field: 'artname', title: '主题', width:100, align:'center', sortable: true},	
			{field: 'authornname', title: '文章作者', width:50, align:'center', sortable: true},	
			{field: 'content', title: '评论内容', width:200, align:'center'},	
			{field: 'usernname', title: '评论者', width:50, align:'center', sortable: true},	
			{field: 'tousernname', title: '对谁的评论', width:50, align:'center', sortable: true},
			z.dg.columnDateTime({field: 'ctime', title: '评论时间'})
		]];
		return cols;
	},
	loadFilter: function(rows, data){
		rows && $.each(rows, function(ix, row){
			row.name = row.artname;
		});
	}
});
z.init();
