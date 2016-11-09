/** m_i 全局对象, 提供给前端的扩展功能函数, 用于处理后端特殊数据, mao, 2015-12-15 16:08 **/
(function(window){
	window.m_i = window.m_i || {};
	/** 对象元素复制 **/
	m_i.apply = function(o, c, x){
	    if(x){
	        M.apply(o, x);
	    }
	    if(o && c && typeof c == 'object'){
	    	if(Object.prototype.toString.apply(c) === '[object Array]'){	//数组
	    		for(var i = 0; i < c.length; i++){
		            o[i] = c[i];
		        }
	    	}else{
		        for(var p in c){
		            o[p] = c[p];
		        }
		    }
	    }
	    return o;
	};
	m_i.applx = m_i.apply;
	
	m_i.apply(m_i, {
		is_string:function(c){
			return Object.prototype.toString.apply(c) === '[object String]';
		},
		is_array:function(c){
			return Object.prototype.toString.apply(c) === '[object Array]';
		},
		is_object:function(c){
			return Object.prototype.toString.apply(c) === '[object Object]';
		},
		is_date:function(c){
			return Object.prototype.toString.apply(c) === '[object Date]';
		},
		is_number:function(c){
			return Object.prototype.toString.apply(c) === '[object Number]';
		},
		is_arr_str: function(c){
			return c && m_i.is_string(c) && c.length > 1
					&& c.charAt(0) == '[' && c.charAt(c.length-1) == ']';
		},
		is_obj_str: function(c){
			return c && m_i.is_string(c) && c.length > 1
			&& c.charAt(0) == '{' && c.charAt(c.length-1) == '}';
		},
		date_str_str: function(str, fmt){
			if(!str) return '';
			str = str.replace(/-/g,"/");
			var date = new Date(str);
			return date.fmt(fmt);
		},
		ssub_val: function(mo, nstr){
			if(!nstr && mo){
				nstr = mo;
				mo = window;
			}
			if(nstr == '.') return ;
			var ns = nstr.split('.');
			if(!ns || ns.length == 0) return ;
			var n;
			for(var i = 0; i < ns.length; i++){
				n = ns[i];
				if(!n || !mo[n]){
					return ;
				}
				mo = mo[n];
			}
			return mo;
		},
		/**
		 * 处理ajax返回值
		 * @param data (json) 参数data下的result属性, 由 jsonstring--> json对象
		 * @param rkey (String) 默认处理data.result字段
		 * @param fck (boolean) true:强制转换(即,如果无法转换则置undefined)
		 * @return 
		 *  <code>
		    $.ajax({
		    	// ...
		    	success: function(data){
		    		m_i.deal_result(data);	// 增加此处理代码
		    		// data  正常使用
				}
			)};
		 *  </code>
		 */
		deal_result:function(data, rkey, fck){
			rkey = rkey || 'result';
			if(!data || !data[rkey]) return ;
			var rs = data[rkey];
			if(m_i.is_array(rs)){
				for(var i = 0; i < rs.length; i++){
					try {
						rs[i] = m_i.to_json_obj(rs[i]);
					} catch (e) {}
				}
			}else if(m_i.is_array(rs) || m_i.is_object(rs)){
				
			}else if(m_i.is_arr_str(rs) || m_i.is_obj_str(rs)){
				data[rkey] = m_i.to_json(rs);
			}else if(fck){
				data[rkey] = undefined;
			}

		},
		to_json: function(js){
			try {
				js = eval('(' + js + ')');
			} catch (e) {}
			return js;
		},
		to_json_obj: function(js){
			try {
				if(m_i.is_obj_str(js)){
					js = eval('(' + js + ')');
				}
			} catch (e) {}
			return js;
		},
		/** 将o的指定属性, 由时间毫秒(多个)转换成日期字符串 */
		to_arr_prop_dstr: function(o, props, ms, fmt){
			if(!o || !m_i.is_array(o)) return ;
			for(var k in o){
				m_i.to_prop_dstr(o[k], props, ms, fmt);
			}
		},
		/** 将o的指定属性, 由时间毫秒(多个)转换成日期字符串 */
		to_prop_dstr: function(o, props, ms, fmt){
			if(!o || !props) return ;
			!m_i.is_array(props) && (props = [props]);
			var prop, val;
			for(var k in props){
				prop = props[k];
				val = o[prop];
				val && (o[prop] = m_i.to_dstr(val));
			}
		},
		/** 将时间毫秒转换成日期字符串 */
		to_dstr: function(ms, fmt){
			if(!ms) return '';
			var r = '';
			fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
			if(m_i.is_string(ms)){
				if(/^\d+$/.test(ms)){
					ms = parseInt(ms);
					var date = new Date(ms);
					r = date.fmt(fmt);
				}else{
					var str = ms.replace(/-/g,"/");;
					try {
						var date = new Date(str);
						r = date.fmt(fmt);
					} catch (e) {
						r = ms;
					}
					
				}
			}else if(m_i.is_date(ms)){
				r = ms.fmt(fmt);
			}else if(m_i.is_number(ms)){
				var date = new Date(ms);
				r = date.fmt(fmt);
			}
			return r;
		},
		to_dtstr: function(ms){
			
		},
		/** 构建图片完整路径, 待完善 */
		buildImgUrl:function(url, root){
			if(!root) return url;
			if(url && url.length > 5){
				if(url.substr(0, 5) == 'http:' || url.substr(0, 6) == 'https:' || url.substr(0, 2) == '//'){
					return url;
				}
			}
			return root + url;
		},
		/** 加载脚本 */
		load_script:function(url){
			var hm = document.createElement("script");
			hm.src = url;
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s);
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
		},
		ii: 0,
		// 上传
		upimg: function(opts){
			opts = opts || {};
			var id = 'i-up-img-frm' + (m_i.ii++);
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
		},
		upimgUtil: function(opt){
			$(function(){
				opt = opt || {};
				$('body').on('click', opt.upimgSelor || '.mi-upimg', function(){
					var upbtn = $(this);
					var upimg = m_i.upimg({
						name: opt.field || 'file',
						change: function(){
							m_i.ajaxSubmit(upimg.frm, {
								url: opt.url || '/admin/imgstore/upload.json',
								success: function(data){
									data = eval('(' + data + ')');
									console.log(data);
									if(data.code == '200'){
										var pnt = upbtn.parent();
										var upbtnid = upbtn.attr('field');
										var imgshow = pnt.find('[upimgshow=' + upbtnid + ']');
										var imgval = pnt.find('[upimgval=' + upbtnid + ']');
										imgshow[0].src = data.data;
										imgval.val(data.data);
										//$(opt.imgshowSelor)[0].src = data.data;
										//$(opt.imgvalSelor).val(data.data);
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
		upimgShowUtil: function(frm, data){
			frm.find('[upimgshow]').each(function(ix, t){
				var tt = $(t);
				var key = tt.attr('upimgshow');
				t.src = data[key];
			});
		},
		// TODO 布局类
		fixed_foot: function(cnt, foot){
			var ah = window.screen.availHeight; 		// 屏幕高度
			var sh = document.body.scrollHeight - 2;	// 内容高度
			
			// 屏幕高度大于内容高度
			var _mc = $(cnt);
			if(ah < sh){
				_mc.height('auto');
				return ;
			}
			
			var ch = _mc.outerHeight();
			var fh = $(foot).outerHeight();
			if(ch + fh < sh){
				_mc.height(sh - fh - 2);
			}else{
				_mc.height('auto');
			}
		}
	});
	
})(window);

Date.weekObj = {0:"星期日", 1:"星期一", 2:"星期二", 3:"星期三", 4:"星期四", 5:"星期五", 6:"星期六"};
Date.defFmt = "yyyy-MM-dd HH:mm:ss";
/** 格式化日期 */
Date.prototype.fmt = function(fs, f0){
	if(!fs) fs = Date.defFmt;
	var d = this;
	fs = fs.replace("yyyy", d.getFullYear())
		.replace("MM", Date.f0(d.getMonth() + 1, f0))
		.replace("M", d.getMonth() + 1)
		.replace("dd", Date.f0(d.getDate(), f0))
		.replace("d", d.getDate())
		.replace("HH", Date.f0(d.getHours(), f0))
		.replace("mm", Date.f0(d.getMinutes(), f0))
		.replace("ss", Date.f0(d.getSeconds(), f0))
		.replace("T", Date.weekObj[d.getDay()]);
	return fs;
};

/** 小于 10 前补零 */
Date.f0 = function(n, f0){
	if(f0 === false) return n;
	if(n < 10 && n >= 0){
		return "0" + n;
	}
	return n;
};

