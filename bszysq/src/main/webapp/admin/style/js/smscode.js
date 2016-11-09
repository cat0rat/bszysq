$.extend(z, {
	clazz: 'smscode',
	gDlgHeight: 280
});
var S_Typen = {
	'0': '<span style="color: green;">注册</span>',
	'1': '忘记密码'
};
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
            {field: 'typen', title: '类型', width:200, align:'center', sortable: true,formatter:function(v){
            	return S_Typen[v] || '';
            }},
			{field: 'mobile', title: '手机号', width:200, align:'center'},	
			{field: 'code', title: '验证码', width:200, align:'center'}
		]);
		return cols;
	}
});
z.init();
