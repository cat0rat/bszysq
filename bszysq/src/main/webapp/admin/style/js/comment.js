$.extend(z, {
	clazz: 'comment',
	gDlgHeight: 380
});
$.extend(z.dg, {
	gen_toolbar: function(zz){
		var tb = [
			{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
			{text:'删除', iconCls:'icon-standard-cancel', plain:true, handler: zz.do_del}
		];
		return tb;
	},
	gen_columns: function(zz){
		var cols = z.dg.columnBase([
			{field: 'artname', title: '文章标题', width:220, align:'center'},	
			{field: 'authornname', title: '文章作者', width:220, align:'center'},	
			{field: 'context', title: '内容', width:220, align:'center'},	
			{field: 'usernname', title: '评论者', width:220, align:'center'},	
			{field: 'tousernname', title: '对谁的评论', width:220, align:'center'}
		]);
		return cols;
	}
});
z.init();
