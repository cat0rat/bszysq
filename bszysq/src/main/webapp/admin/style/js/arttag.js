$.extend(z, {
	clazz: 'arttag',
});
$.extend(z.dg, {
	gen_columns: function(zz){
		var cols = [[ 
		    {field: 'ck', checkbox: true}, 
			{field: 'id', title: '编号', width:100, align:'center', sortable: true},
			{field: 'name', title: '名称', width:220, align:'center'},	
			{field: 'sortn', title: '序号', width:50, align:'center', sortable: true}
		]];
		return cols;
	}
});
z.init();