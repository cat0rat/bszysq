$(function(){
$('#login_btn').click(function(){
	login();
});
function login(){
	$.ajax({
		type: 'POST',
		url: '/app/login.json',
		data: {name: 'test3', pwd: '111111'}
	});
}
$('#logout_btn').click(function(){
	logout();
});
function logout(){
	$.ajax({
		type: 'POST',
		url: '/app/uc/logout.json'
	});
}
// 执行函数
$('[fn]').click(function(){
	var tt = $(this);
	var fn = tt.attr('fn');
	fn && eval('(' + fn + ')');
});

// TODO 用户
window.AppUser = {
	mine: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/uc/user/mine.json';
		opt.data = $.extend({
			
		}, data);
		$.ajax(opt);
	},
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/uc/user/mine.json';
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

//TODO 标签
window.AppArttag = {
	list_idval: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/arttag/list_idval.json';
		opt.data = $.extend({
			page: 1,
			limit: -1
		}, data);
		$.ajax(opt);
	},
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/arttag/list.json';
		opt.data = $.extend({
			page: 1,
			limit: -1
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/arttag/get.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	}
};

//TODO 版块
window.AppCategory = {
	list_art: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/category/list_art.json';
		opt.data = $.extend({
			page: 1,
			limit: 5
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/category/get.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	}
};

//TODO 文章
window.AppArticle = {
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/article/list.json';
		opt.data = $.extend({
			page: 1,
			limit: 5
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/article/get.json';
		opt.data = {
			id: id
		};
		$.ajax(opt);
	}
};


//TODO 轮播
window.AppSlider = {
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/slider/list.json';
		opt.data = $.extend({
			page: 1,
			limit: 5
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/slider/get.json';
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