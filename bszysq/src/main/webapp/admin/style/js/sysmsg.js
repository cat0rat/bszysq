var S_typex = { 0: '<font style="color: #00f">系统消息</font>', 1: '主题或评论' };
var S_rangx = { 0: '单用户', 1: '安卓用户', 2: 'IOS用户', 20: '全部用户' };
$.extend(z, {
	clazz: 'sysmsg',
	gDlgHeight: 300
});
$.extend(z.dg, {
	gen_toolbar: function(zz){
		var tb = [
			{text:'添加', iconCls:'icon-add', plain:true, handler: z.add.dlg_open},
//			{text:'查看', iconCls:'icon-search', plain:true, handler: z.look.dlg_open},
			{text:'删除', iconCls:'icon-cancel', plain:true, handler: zz.do_del}
		];
		return tb;
	},
	gen_columns: function(zz){
		var cols = [[
			{field: 'ck', checkbox: true}, 
			{field: 'id', title: '编号', width:40, align:'center', sortable: true},
			{field: 'typex', title: '消息类型', width:40, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_typex[v] || '未知';
			}},
			{field: 'rangx', title: '推送范围', width:40, align:'center', sortable: true, formatter: function(v, r, ix){
				return S_rangx[v] || '未知';
			}},
			{field: 'name', title: '标题', width:120, align:'center', sortable: true},
			{field: 'content', title: '内容', width:200, align:'center', sortable: true},
			{field: 'ctimeStr', title: '推送时间', width: 100, align: 'center'}
			]];
		return cols;
	}
});
$.extend(z.add, {
	is_clear_before: 0
});
z.init();

