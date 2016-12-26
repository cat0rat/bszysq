$.extend(z, {
	clazz: 'slider',
	gDlgHeight: 400,
	def_fvs: {pos:'', typex:'', artid:'', link:'', catename:'', artname:''}
});
$.extend(z.dg, {
	gen_columns: function(zz){
		var cols = z.dg.columnBase([
			{field: 'name', title: '标题', width:220, align:'center', sortable: true},	
			z.dg.columnMidImg({field: 'img', title: '配图'}),
			{field: 'sortn', title: '排序号', width:50, align:'center', sortable: true},
//			{field: 'pos', title: '位置', width:50, align:'center', sortable: true, formatter:function(v){
//				return v == 0 ? '首页' : '未知';
//			}},
			{field: 'typex', title: '跳转类型', width:60, align:'center', sortable: true, formatter:function(v){
				return v == 0 ? '外链' : '内部主题';
			}},	
			{field: 'link', title: '跳转地址', width:50, align:'center', formatter:function(v){
				return v ? ('<a href="' + v + '" target="__blank">查看</a>') : '';
			}},	
			{field: 'artid', title: '主题编号', width:50, align:'center', sortable: true},
			{field: 'catename', title: '版块', width:60, align:'center', sortable: true},
			{field: 'artname', title: '主题', width:200, align:'center', sortable: true}
		]);
		return cols;
	}
});
z.init();

