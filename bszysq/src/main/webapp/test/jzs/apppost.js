$(function(){

//TODO 登录
window.AppLogin = {
	login: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/login.json';
		opt.data = $.extend({
			name: 'test3', 
			pwd: '111111'
		}, data);
		opt.success = function(d) {
			if(d.code == '200'){
				AppUser.showUserInfo(d.data);
			}
		};
		$.ajax(opt);
	},
	uc:{
		logout: function(){
			var opt = Cmm.build_opt();
			opt.url = '/app/uc/logout.json';
			opt.success = function(d) {
				if(d.code == '200'){
					AppUser.showUserInfo({});
				}
			};
			$.ajax(opt);
		}
	}
};

//TODO 注册
window.AppReg = {
	smscode: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/smscode.json';
		opt.data = {
			mobile: '13370175853',
			captcha: $('#captcha').val()
		};
		opt.success = function(d) {
			if(d.code == '200'){
				$('#smscode').val(d.data);
			}
		};
		$.ajax(opt);
	},
	recaptcha: function(t){
		if(!t.org_src) t.org_src = t.src;
		t.src = t.org_src + '?' + Math.random();
	},
	reg: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/reg.json';
		opt.data = {
			name: '13370175853',
			pwd: '111111',
			smscode: $('#smscode').val() || '4444',
			captcha: $('#captcha').val()
		};
		$.ajax(opt);
	}
};


// TODO 用户
window.AppUser = {
	uc: {
		mine: function(data){
			var opt = Cmm.build_opt();
			opt.url = '/app/uc/user/mine.json';
			opt.data = $.extend({
				
			}, data);
			opt.success = function(d) {
				if(d.code == '200'){
					AppUser.showUserInfo(d.data);
				}
			};
			$.ajax(opt);
		},
		simple: function(id){
			var opt = Cmm.build_opt();
			opt.url = '/app/uc/user/simple.json';
			opt.data = {
				id: id
			};
			$.ajax(opt);
		},
		auth: function(id){
			var opt = Cmm.build_opt();
			opt.url = '/app/uc/user/auth.json';
			opt.data = {
				tname: '小懒猫',
				mobile: '13370175853',
				address: '高教圆'
			};
			$.ajax(opt);
		},
		repwd: function(){
			var opt = Cmm.build_opt();
			opt.url = '/app/uc/user/repwd.json';
			opt.data = {
				oldpwd:  $('#oldpwd').val(),
				pwd:  $('#pwd').val()
			};
			$.ajax(opt);
		}
	},
	showUserInfo: function(info){
		$('#nname').html(info.nname || '');
		$('#userhead')[0].src = info.head || '';
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
	simple: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/user/simple.json';
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

// TODO 无需登录

//TODO 上传
window.AppImgstore = {
	uptoken: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/imgstore/uptoken.json';
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

//TODO 评论
window.AppComment = {
	list: function(data){
		var opt = Cmm.build_opt();
		opt.url = '/app/comment/list.json';
		opt.data = $.extend({
			page: 1,
			limit: 5
		}, data);
		$.ajax(opt);
	},
	get: function(id){
		var opt = Cmm.build_opt();
		opt.url = '/app/comment/get.json';
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

//执行函数
$('[fn]').click(function(){
	var tt = $(this);
	var fn = tt.attr('fn');
	fn && eval('(' + fn + ')');
});

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