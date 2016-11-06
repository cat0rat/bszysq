var F = {
	formSelor: '#data_form',
	dialogSelor: '',	// '#edit_arttag'
	dialogClose: function() {
		top.$(F.dialogSelor).dialog('close');
	},
	updateUrl: '',	// '/admin/arttag/update.json'
	updateInit: function (othfn) {
		if(F.upimgOpt.upimgSelor){
			F.upimgUtil(F.upimgOpt);
		}
		// 其他初始化操作
		othfn && othfn();
	},
	updateDo: function() {
		var frm = $(F.formSelor);
		var isv = frm.form('validate');
		if (isv){
			$.ajax({
				url : F.updateUrl,
				type : 'post',
				data : frm.serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.code == '200') {
						$.messager.alert('温馨提示', '保存成功!', '', function() {
							F.dialogClose();
						});
					} else {
						$.messager.alert('温馨提示', '保存失败!', 'error');
					}
				},
				error : function() {
					$.messager.alert('温馨提示', '系统繁忙，请重试!', 'error');
				}
			});
		}
	},
	addUrl: '',	// '/admin/arttag/update.json'
	addInit: function (othfn) {
		if(F.upimgOpt.upimgSelor){
			F.upimgUtil(F.upimgOpt);
		}
		// 其他初始化操作
		othfn && othfn();
	},
	addDo: function () {
		var frm = $(F.formSelor);
		var isv = frm.form('validate');
		if (isv){
			$.ajax({
				url : F.addUrl,
				type : 'post',
				data : frm.serialize(),
				dataType : 'json',
				success : function(data) {
					if (data.code == '200') {
						$.messager.alert('温馨提示', '保存成功!', '', function() {
							F.dialogClose();
						});
					} else {
						$.messager.alert('温馨提示', '保存失败!', 'error');
					}
				},
				error : function() {
					$.messager.alert('温馨提示', '系统繁忙，请重试!', 'error');
				}
			});
		}
	},
	// 上传工具方法
	upimgOpt:{
		upimgSelor: '',
		imgshowSelor: '#imgshow',
		imgvalSelor: '#imgval'
	},
	upimgUtil: function(opt){
		$(function(){
			$(opt.upimgSelor).on('click', function(){
				var upimg = F.upimg({
					name: 'file',
					change: function(){
						F.ajaxSubmit(upimg.frm, {
							url: '/admin/imgstore/upload.json',
							success: function(data){
								data = eval('(' + data + ')');
								console.log(data);
								if(data.code == '200'){
									$(opt.imgshowSelor)[0].src = data.data;
									$(opt.imgvalSelor).val(data.data);
								}else{
									alert('图片上传失败!');
								}
							}
						});
					}
				});
				upimg.ipt[0].click();
			});
		});
	},
	/** 异步提交form, 来自easyui-1.3.6 */
	ajaxSubmit:function (target, options){
		options = options || {};
		
		var param = {};
		if (options.onSubmit){
			if (options.onSubmit.call(target, param) == false) {
				return;
			}
		}
		
		var form = $(target);
		if (options.url){
			form.attr('action', options.url);
		}
		var frameId = 'easyui_frame_' + (new Date().getTime());
		var frame = $('<iframe id='+frameId+' name='+frameId+'></iframe>')
				.attr('src', window.ActiveXObject ? 'javascript:false' : 'about:blank')
				.css({
					position:'absolute',
					top:-1000,
					left:-1000
				});
		var t = form.attr('target'), a = form.attr('action');
		form.attr('target', frameId);
		
		var paramFields = $();
		try {
			frame.appendTo('body');
			frame.bind('load', cb);
			for(var n in param){
				var f = $('<input type="hidden" name="' + n + '">').val(param[n]).appendTo(form);
				paramFields = paramFields.add(f);
			}
			checkState();
			form[0].submit();
		} finally {
			form.attr('action', a);
			t ? form.attr('target', t) : form.removeAttr('target');
			paramFields.remove();
		}
		
		function checkState(){
			var f = $('#'+frameId);
			if (!f.length){return;};
			try{
				var s = f.contents()[0].readyState;
				if (s && s.toLowerCase() == 'uninitialized'){
					setTimeout(checkState, 100);
				}
			} catch(e){
				cb();
			}
		}
		
		var checkCount = 10;
		function cb(){
			var frame = $('#'+frameId);
			if (!frame.length){return;};
			frame.unbind();
			var data = '';
			try{
				var body = frame.contents().find('body');
				data = body.html();
				if (data == ''){
					if (--checkCount){
						setTimeout(cb, 100);
						return;
					}
//						return;
				}
				var ta = body.find('>textarea');
				if (ta.length){
					data = ta.val();
				} else {
					var pre = body.find('>pre');
					if (pre.length){
						data = pre.html();
					}
				}
			} catch(e){
				
			}
			if (options.success){
				options.success(data);
			}
			setTimeout(function(){
				frame.unbind();
				frame.remove();
			}, 100);
		}
	}
};
// 上传
(function(){
var ii = 0;
F.upimg = function(opts){
	opts = opts || {};
	var id = 'i-up-img-frm' + (ii++);
	var _id = '#' + id;
	var arr = ['<div id="', id, '" style="display: none;">',
		'<form method="post" enctype="multipart/form-data" >',
			'<input name="', opts.name, '" type="file" ', opts.multiple ? ' multiple="multiple"' : '', ' />',
		'</form>',
	'</div>'];
	var body = $('body');
	body.children(_id).remove();
	body.append(arr.join(''));
	
	var div = $(_id);
	var frm = div.find('form');
	var ipt = div.find('input');
	opts.div = div;
	opts.frm = frm;
	opts.ipt = ipt;
	
	ipt.off('change.mctr');
	if(opts.change || opts.imgctr || opts.on_img_loaded){
		ipt.on('change.mctr', fn_change);
	}
	function fn_change(e){
		var t = this;
		opts.target = t;
		if(opts.change) {
			opts.change.call(t, e);
		}else if(opts.imgctr){
			//通过file.size可以取得图片大小
			var reader = new FileReader();
			reader.onload = function(evt) {
				$(opts.imgctr)[0].src = opts.fpath = evt.target.result;
				opts.on_img_loaded && opts.on_img_loaded(opts);
			}
			var file = t.files[0];
			reader.readAsDataURL(file);
		}else if(opts.is_reader !== false){
			//通过file.size可以取得图片大小
			var reader = new FileReader();
			reader.onload = function(evt) {
				opts.fpath = evt.target.result;
				opts.on_img_loaded && opts.on_img_loaded(opts);
			}
			var file = t.files[0];
			reader.readAsDataURL(file);
		}else if(opts.on_img_loaded){
			opts.on_img_loaded(opts);
		}
	};
	return opts;
};
})();
