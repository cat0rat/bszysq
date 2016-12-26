$.extend(z, {
	clazz: 'category',
	gDlgHeight: 280
});
$.extend(z.dg, {
	gen_columns: function(zz){
		var cols = z.dg.columnBase([
			{field: 'name', title: '名称', width:220, align:'center', sortable: true},	
			z.dg.columnMidImg({field: 'img', title: '配图'}),
			{field: 'sortn', title: '序号', width:50, align:'center', sortable: true}
		]);
		return cols;
	}
});
z.init();
