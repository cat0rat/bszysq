$(function(){
$('#login_btn').click(function(){
	login();
});
// 执行函数
$('[fn]').click(function(){
	var tt = $(this);
	var fn = tt.attr('fn');
	fn && eval('(' + fn + ')');
});
function login(){
	$.ajax({
		type: 'POST',
		url: '/admin/login.json',
		data: {name: 'admin', pwd: '111111'}
	});
}

// TODO 标签
window.Arttag = {
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/arttag/list.json';
		opt.data = $.extend({
			page: 1,
			limit: 5,
			name: '健身'
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/admin/arttag/get.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	},
	add: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/arttag/add.json';
		opt.data = data || {
			name: '工蕊3',
			sortn: 0
		};
		$.ajax(opt);
	},
	update: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/arttag/update.json';
		opt.data = data || {
			id: 7,
			name: '工蕊33',
			sortn: 10,
			isdel: 0
		};
		$.ajax(opt);
	},
	deletex: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/admin/arttag/delete.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	}
};

//TODO 版块
window.Category = {
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/category/list.json';
		opt.data = $.extend({
			page: 1,
			limit: 5
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/admin/category/get.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	},
	add: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/category/add.json';
		opt.data = data || {
			name: '工蕊3',
			sortn: 0
		};
		$.ajax(opt);
	},
	update: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/admin/category/update.json';
		opt.data = data || {
			id: 21,
			name: '工蕊33',
			sortn: 10,
			isdel: 0
		};
		$.ajax(opt);
	},
	deletex: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/admin/category/delete.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	}
};

window.Cmm = {
	ajax: function(){},
	build_opt: function(){
		var opt = {
			type: 'POST',
			dataType: 'json',
			success : function(d) {
				console.log(d);
			},
			error: function(){
				console.log('错误');
			}
		};
		return opt;
	}
};

});