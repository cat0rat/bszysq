(function($){
	/** 内部私有函数, 不是easyui的风格 */
	var innerFns = {};
	/** 滚动面板到指定值处 */
	innerFns.scrollTo = function(target, value){
		var panel = $(target).combo('panel');
		var item = panel.find('div.combobox-item[value="' + value + '"]');
		if (item.length){
			if (item.position().top <= 0){
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else if (item.position().top + item.outerHeight() > panel.height()){
				var h = panel.scrollTop() + item.position().top + item.outerHeight() - panel.height();
				panel.scrollTop(h);
			}
		}
	};
	
	/** 设置禁用 */
	function setDisabled(target, disabled){
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		var panel = $(target).combo('panel');
		if (disabled){
			opts.disabled = true;
			$(target).attr('disabled', true);
			combo.find('.combo-value').attr('disabled', true);
			combo.find('.combo-text').attr('disabled', true);
			
			//增加禁用
			if(!opts.bk_bgColor) opts.bk_bgColor = panel.css('background-color');
			if(!opts.bk_pos) opts.bk_pos = panel.css('position');
			panel.css('position', 'relative');
			panel.css('background-color', '#eec');
//			var oh = panel.outerHeight();
//			var ow = panel.outerWidth();
//			var ih = panel.innerHeight();
//			var iw = panel.innerWidth();
//			var h = panel.height();
//			var w = panel.width();
			var sh = panel[0].scrollHeight;
			var sw = panel[0].scrollWidth;
			sw = sw == 0 ? '100%' : sw + 'px';
			sh = sh == 0 ? '100%' : sh + 'px';	//但还是未能解决存在滚动条的情况, 最好是在显示(display:block)后, 再调用禁用
			var disable_layer = panel.find(".combobox-disable-layer");
			if(disable_layer==undefined || disable_layer.length==0){
				panel.append('<div class="combobox-disable-layer" style="position: absolute; top:0px; left:0px; width: ' + sw + '; height: ' + sh + ';background-color:#EEEECC;opacity:0.1;"></div>');
			}
//			panel.append('<div class="combobox-disable-layer" style="position: absolute; top:0px; left:0px; width: 100%; height: 100%;"></div>');
//			var xxxx = "";
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
			combo.find('.combo-value').removeAttr('disabled');
			combo.find('.combo-text').removeAttr('disabled');
			
			//解除禁用
			if(opts.bk_pos) panel.css('position', opts.bk_pos);
			if(opts.bk_bgColor) panel.css('background-color', opts.bk_bgColor);
			panel.find('.combobox-disable-layer').remove();
		}
	}
	
	/** 选择上一项 */
	function selectPrev(target){
		var panel = $(target).combo('panel');
		var values = $(target).combo('getValues');
		var item = panel.find('div.combobox-item[value="' + values.pop() + '"]');
		if (item.length){
			var prev = item.prev(':visible');
			if (prev.length){
				item = prev;
			}
		} else {
			item = panel.find('div.combobox-item:visible:last');
		}
		var value = item.attr('value');
		target.innerFns.select(target, value);
//		setValues(target, [value]);
		target.innerFns.scrollTo(target, value);
	}
	
	/** 选择下一项 */
	innerFns.selectNext = function(target){
		var panel = $(target).combo('panel');
		var values = $(target).combo('getValues');
		var item = panel.find('div.combobox-item[value="' + values.pop() + '"]');
		if (item.length){
			var next = item.next(':visible');
			if (next.length){
				item = next;
			}
		} else {
			item = panel.find('div.combobox-item:visible:first');
		}
		var value = item.attr('value');
		target.innerFns.select(target, value);
//		setValues(target, [value]);
		target.innerFns.scrollTo(target, value);
	};
	
	/** 选择指定项 */
	innerFns.select = function(target, value){
		var opts = $.data(target, 'listbox').options;
		var data = $.data(target, 'listbox').data;
		
		if (opts.multiple){
			var values = $(target).combo('getValues');
			for(var i=0; i<values.length; i++){
				if (values[i] == value) return;
			}
			values.push(value);
			target.innerFns.setValues(target, values);
		} else {
			target.innerFns.setValues(target, [value]);
		}
		
		for(var i=0; i<data.length; i++){
			if (data[i][opts.valueField] == value){
				opts.onSelect.call(target, data[i]);
				return;
			}
		}
	};
	
	/** 反选指定项 */
	innerFns.unselect = function(target, value){
		var opts = $.data(target, 'listbox').options;
		var data = $.data(target, 'listbox').data;
		var values = $(target).combo('getValues');
		for(var i=0; i<values.length; i++){
			if (values[i] == value){
				values.splice(i, 1);
				target.innerFns.setValues(target, values);
				break;
			}
		}
		for(var i=0; i<data.length; i++){
			if (data[i][opts.valueField] == value){
				opts.onUnselect.call(target, data[i]);
				return;
			}
		}
	};
	
	/** 设置值(并选中) */
	innerFns.setValues = function(target, values, remainText){
		var opts = $.data(target, 'listbox').options;
		var data = $.data(target, 'listbox').data;
		var panel = $(target).combo('panel');
		var isMustExist = opts.isMustExist;
		
		var items = panel.find('div.combobox-item-selected');
		items.removeClass('combobox-item-selected');
		if(opts.checkbox){
			panel.find('span.listbox-checkbox.x-checkbox1').removeClass('x-checkbox1').addClass('x-checkbox0');
		}
		var vv = [], ss = [];
		for(var i=0; i<values.length; i++){
			var v = values[i];
			var s = v;
			var found = false;
			for(var j=0; j<data.length; j++){
				if (data[j][opts.valueField] == v){
					s = data[j][opts.textField];
					found = true;
					break;
				}
			}
			if(!isMustExist || found){
				vv.push(v);
				ss.push(s);
				var item = panel.find('div.combobox-item[value="' + v + '"]');
				item.addClass('combobox-item-selected');
				if(opts.checkbox){
					item.find('span.listbox-checkbox').removeClass('x-checkbox0').addClass('x-checkbox1');
				}
			}
		}
		
		$(target).combo('setValues', vv);
		if (!remainText){
			$(target).combo('setText', ss.join(opts.separator));
		}
	};
	
	/** 转换标签格式书写的参数 **/
	innerFns.transformData = function(target, ctrlName){
		var opts = $.data(target, ctrlName || 'listbox').options;
		var data = [];
		$('>option', target).each(function(){
			var item = {};
			item[opts.valueField] = $(this).attr('value')!=undefined ? $(this).attr('value') : $(this).html();
			item[opts.textField] = $(this).html();
			item['selected'] = $(this).attr('selected');
			data.push(item);
		});
		return data;
	};
	
	/** 加载 本地数据, 旧的数据项将被移除 */
	innerFns.loadData = function(target, data, remainText, ctrlName){
		ctrlName = ctrlName || 'listbox';
		var opts = $.data(target, ctrlName).options;
		var panel = $(target).combo('panel');
		
		$.data(target, ctrlName).data = data;	//备份数据
		
		if(opts.onRender) opts.onRender.call(target, data);
		
		var selected = $(target).listbox('getValues');
		panel.empty();	// clear old data
		for(var i=0; i<data.length; i++){
			var v = data[i][opts.valueField];
			var s = data[i][opts.textField];
			var item = $('<div class="combobox-item" style="cursor:pointer;"></div>').appendTo(panel);
			item.attr('value', v);
			var fmt_s;
			if (opts.formatter){
				fmt_s = opts.formatter.call(target, data[i]);
			} else {
				fmt_s = s;
			}
			if(opts.checkbox){
				item.css('lineHeight', '18px');
				var s_a = ['<span class="listbox-checkbox x-icon x-checkbox0"></span>',
					'&nbsp;<span class="listbox-title">', fmt_s, '</span>'
				];
				item.html(s_a.join(''));
			}else{
				item.html(fmt_s);
			}
			if (data[i]['selected']){
				(function(){
					for(var i=0; i<selected.length; i++){
						if (v == selected[i]) return;
					}
					selected.push(v);
				})();
			}
		}
		if (opts.multiple){
			target.innerFns.setValues(target, selected, remainText);
		} else {
			if (selected.length){
				target.	innerFns.setValues(target, [selected[selected.length-1]], remainText);
			} else {
				target.innerFns.setValues(target, [], remainText);
			}
		}
		
		opts.onLoadSuccess.call(target, data);
		
		var items = $('.combobox-item', panel);
		target.innerFns.setItemEvent(target, items, opts);
		
		if(opts.flow){
			items.css({
				'float': 'left',
				'width': opts.itemFlowWidth || 100
			});
		}
		
		//隐藏输入框
		var tb = $(target).combo('textbox');
		tb = tb.parent();
		tb.hide();
		tb.after(panel);
		panel.show();
		
		//禁用
		if(opts.disabled){
			$(target).listbox('disable');
		}
	};
	
	/** 获取 远程数据, 如果 url存在 */
	innerFns.request = function(target, url, param, remainText, ctrlName){
		var opts = $.data(target, ctrlName || 'listbox').options;
		if (url){
			opts.url = url;
		}
//		if (!opts.url) return;
		param = param || {};
		
		if (opts.onBeforeLoad.call(target, param) == false) return;

		opts.loader.call(target, param, function(data){
			target.innerFns.loadData(target, data, remainText);
		}, function(){
			opts.onLoadError.apply(this, arguments);
		});
	};
	
	/** 查询 请求 */
	function doQuery(target, q){
		var opts = $.data(target, 'listbox').options;
		
		if (opts.multiple && !q){
			target.innerFns.setValues(target, [], true);
		} else {
			target.innerFns.setValues(target, [q], true);
		}
		
		if (opts.mode == 'remote'){
			target.innerFns.request(target, null, {q:q}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('div.combobox-item').hide();
			var data = $.data(target, 'listbox').data;
			for(var i=0; i<data.length; i++){
				if (opts.filter.call(target, q, data[i])){
					var v = data[i][opts.valueField];
					var s = data[i][opts.textField];
					var item = panel.find('div.combobox-item[value="' + v + '"]');
					item.show();
					if (s == q){
						target.innerFns.setValues(target, [v], true);
						item.addClass('combobox-item-selected');
						if(opts.checkbox){
							item.find('span.listbox-checkbox').removeClass('x-checkbox0').addClass('x-checkbox1');
						}
					}
				}
			}
		}
	}
	
	/** 设置 指定项的事件
	 * @param target (Object) 控件Dom
	 * @param items (Object/Array) 项
	 * @param opts (Object) 控件 参数
	 */
	innerFns.setItemEvent = function(target, items, opts){
		items.hover(
			function(){$(this).addClass('combobox-item-hover');},
			function(){$(this).removeClass('combobox-item-hover');}
		);
		items.click(function(){
			var item = $(this);
			if (opts.multiple){
				if (item.hasClass('combobox-item-selected')){
					target.innerFns.unselect(target, item.attr('value'));
				} else {
					target.innerFns.select(target, item.attr('value'));
				}
			} else {
				target.innerFns.select(target, item.attr('value'));
			}
		});
	};
	
	/** 追加值, data数据格式[{text:'Mao', value:'1', selected:true}], pos:true追加到头部 */
	innerFns.appendData = function(target, data){
		var opts = $.data(target, 'listbox').options;
		var panel = $(target).combo('panel');
		var pos = false;
		if(data.pos != undefined){
			pos = data.pos;
			data = data.data;
		}
		var edata = $.data(target, 'listbox').data || [];
		if(pos){
			edata = [].concat(data, edata);
		}else{
			edata = [].concat(edata, data);
		}
		$.data(target, 'listbox').data = edata;
		
		var selected = [];
		//panel.empty();	// 不清空
		for(var i = 0; i < data.length; i++){
			var item = $('<div class="combobox-item"></div>');
			if(pos){
				item = item.prependTo(panel);
			}else{
				item = item.appendTo(panel);
			}
			var val = data[i][opts.valueField]; 
			var txt = data[i][opts.textField];
			item.attr('value', val);
			item.html(txt);
			if (data[i]['selected']){
				selected.push(val);
			}
			target.innerFns.setItemEvent(target, item, opts);
		}
		
		//多选
		if (opts.multiple){
			target.innerFns.setValues(target, selected);
		} else {
			if (selected.length){
				target.innerFns.setValues(target, [selected[0]]);
			} else {
				target.innerFns.setValues(target, []);
			}
		}
	};
	
	/** 创建控件 */
	function create(target){
		var opts = $.data(target, 'listbox').options;
		$(target).addClass('combobox-f');
		$(target).combo($.extend({}, opts, {
			onShowPanel: function(){
				$(target).combo('panel').find('div.combobox-item').show();
				target.innerFns.scrollTo(target, $(target).listbox('getValue'));
				opts.onShowPanel.call(target);
			}
		}));
		target.innerFns = innerFns;	//携带当前类的方法集
	}
	
	/** 构造函数 */
	$.fn.listbox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.listbox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combobox(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'listbox');
			if (state){
				$.extend(state.options, options);
				create(this);
			} else {
				state = $.data(this, 'listbox', {
					options: $.extend({}, $.fn.listbox.defaults, $.fn.listbox.parseOptions(this), options)
				});
				create(this);
				innerFns.loadData(this, innerFns.transformData(this));
			}
			if (state.options.data){
				innerFns.loadData(this, state.options.data);
			}
			innerFns.request(this);
		});
	};
	
	$.fn.listbox.innerFns = innerFns = $.extend({}, $.fn.combobox.innerFns, innerFns);	//继承私有方法
	
	$.fn.listbox.methods = {
		options: function(jq){
			var opts = $.data(jq[0], 'listbox').options;
			opts.originalValue = jq.combo('options').originalValue;
			return opts;
		},
		getData: function(jq){
			return $.data(jq[0], 'listbox').data;
		},
		setValues: function(jq, values){
			return jq.each(function(){
				innerFns.setValues(this, values);
			});
		},
		setValue: function(jq, value){
			return jq.each(function(){
				innerFns.setValues(this, [value]);
			});
		},
		disable: function(jq){
			return jq.each(function(){
				$(this).combo('disable');
				setDisabled(this, true);
//				bindEvents(this);
			});
		},
		enable: function(jq){
			return jq.each(function(){
				$(this).combo('enable');
				setDisabled(this, false);
//				bindEvents(this);
			});
		},
		clear: function(jq){
			return jq.each(function(){
				$(this).combo('clear');
				var panel = $(this).combo('panel');
				panel.find('div.combobox-item-selected').removeClass('combobox-item-selected');
			});
		},
		reset: function(jq){
			return jq.each(function(){
				var opts = $(this).listbox('options');
				if (opts.multiple){
					$(this).listbox('setValues', opts.originalValue);
				} else {
					$(this).listbox('setValue', opts.originalValue);
				}
			});
		},
		loadData: function(jq, data){
			return jq.each(function(){
				innerFns.loadData(this, data);
			});
		},
		appendData: function(jq, data){
			return jq.each(function(){
				innerFns.appendData(this, data);
			});
		},
		reload: function(jq, url){
			return jq.each(function(){
				innerFns.request(this, url);
			});
		},
		select: function(jq, value){
			return jq.each(function(){
				innerFns.select(this, value);
			});
		},
		unselect: function(jq, value){
			return jq.each(function(){
				innerFns.unselect(this, value);
			});
		}
	};
	
	$.fn.listbox.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target,[
			'valueField','textField','mode','method','url'
		]));
	};
	
	$.fn.listbox.defaults = $.extend({}, $.fn.combo.defaults, {
		valueField: 'value',
		textField: 'text',
		mode: 'local',	// or 'remote'
		method: 'post',
		url: null,
		data: null,
		multiple: false,
		checkbox: false,
		isMustExist: true,
		keyHandler: {
			up: function(){selectPrev(this);},
			down: function(){selectNext(this);},
			enter: function(){
				var values = $(this).listbox('getValues');
				$(this).listbox('setValues', values);
				$(this).listbox('hidePanel');
			},
			query: function(q){doQuery(this, q);}
		},
		filter: function(q, row){
			var opts = $(this).listbox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		formatter: function(row){
			var opts = $(this).listbox('options');
			return row[opts.textField];
		},
		loader: function(param, success, error){
			var opts = $(this).listbox('options');
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					success(data);
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		},
		onBeforeLoad: function(param){},
		onLoadSuccess: function(data){},
		onRender: function(data){},
		onLoadError: function(){},
		onSelect: function(record){},
		onUnselect: function(record){}
	});
	$.parser.plugins.push('listbox');	//加入到插件库
})(jQuery);

// TODO 列表框+右键菜单
(function($){
	/** 内部私有函数, 不是easyui的风格 */
	var innerFns = {};
	
	/** 转换标签格式书写的参数 **/
	innerFns.transformData = function(target){
		var opts = $.data(target, 'listmenu').options;
		var data = [];
		$('>option', target).each(function(){
			var item = {};
			item[opts.valueField] = $(this).attr('value')!=undefined ? $(this).attr('value') : $(this).html();
			item[opts.textField] = $(this).html();
			item['selected'] = $(this).attr('selected');
			data.push(item);
		});
		return data;
	};
	
	/** 设置 指定项的事件
	 * @param target (Object) 控件Dom
	 * @param items (Object/Array) 项
	 * @param opts (Object) 控件 参数
	 */
	innerFns.setItemEvent = function(target, items, opts){
		items.hover(
			function(){$(this).addClass('combobox-item-hover');},
			function(){$(this).removeClass('combobox-item-hover');}
		);
		
		function efn(e){
			var item = $(this);
			var opts = $.data(target, 'listmenu').options;
			var mopts = opts.menuOptions || {};
			if(!mopts.id) mopts.id = 'lm_' + new Date().getTime();
			var mc = $('#' + mopts.id);
			if(!mc.length){
				var mos = mopts.options || {};
				mc = $('<div id="' + mopts.id + '"></div>');
				mc.appendTo('body');
				mc = $('#' + mopts.id);
				if(mos.data){
					var ds = mos.data;
					var dos = mos.dataMap = {};
					for(var i = 0; i < ds.length; i++){
						var d = ds[i];
						dos[d.name] = d;
						var arr = ['<div data-options="',
							'name:\'', d.name, '\',',
							'text:\'', d.text, '\',',
							'iconCls:\'', d.iconCls, '\'',
							'">', d.text, '</div>'
						];
						mc.append(arr.join(''));
					}
				}
				var bk_ck = mos.onClick;
				if(!bk_ck){
					//菜单项 单击事件, 可以在这里分发到某个项的单击事件上
					bk_ck = function(item, itemOrg){
						var li = $(this.ptEl);	//获取 listbox列表项对象
						
						if(itemOrg.onclick){
							//分发到某个项的单击事件上
							itemOrg.onclick.call(this, item, li);
						}else{
							//其他 业务处理
						}
						//其他 业务处理
					};
				}
				mos.onClick = function(item){
					var dataMap = $.data(this, 'menu').options.dataMap;
					var itemOrg = dataMap[item.name];
					//item.onclick = itemOrg.onclick;
					bk_ck.call(this, item, itemOrg);
				}
				mc.menu(mos);
			}
			e.preventDefault();
			mc.menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			mc[0].ptEl = this;
			mc[0].ptCtrlEl = target;
			
//			if (opts.multiple){
//				if (item.hasClass('combobox-item-selected')){
//					innerFns.unselect(target, item.attr('value'));
//				} else {
//					innerFns.select(target, item.attr('value'));
//				}
//			} else {
//				innerFns.select(target, item.attr('value'));
//			}
		}
		
		items.on('click', efn);
		items.on('contextmenu', efn);
	};
	
	/**
	 * 创建控件
	 * create the component
	 */
	function create(target){
		var opts = $.data(target, 'listmenu').options;
		$(target).addClass('combobox-f');
		$(target).listbox($.extend({}, opts, {
			onShowPanel: function(){
				$(target).listbox('panel').find('div.combobox-item').show();
				target.innerFns.scrollTo(target, $(target).listmenu('getValue'));
				opts.onShowPanel.call(target);
			}
		}));
		target.innerFns = innerFns;	//携带当前类的方法集
	}
	
	
	$.fn.listmenu = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.listmenu.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.listbox(options, param);
			}
		}
		
		options = options || {};
		//innerFns = $.fn.listbox.innerFns || {};
		return this.each(function(){
			var state = $.data(this, 'listmenu');
			if (state){
				$.extend(state.options, options);
				create(this);
			} else {
				state = $.data(this, 'listmenu', {
					options: $.extend({}, $.fn.listmenu.defaults, $.fn.listmenu.parseOptions(this), options)
				});
				create(this);
				innerFns.loadData(this, innerFns.transformData(this));
			}
			if (state.options.data){
				innerFns.loadData(this, state.options.data);
			}
			innerFns.request(this);
		});
	};
	
	$.fn.listmenu.innerFns = innerFns = $.extend({}, $.fn.listbox.innerFns, innerFns);	//继承私有方法
	
	$.fn.listmenu.methods = {
	};
	
	$.fn.listmenu.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.listbox.parseOptions(target), $.parser.parseOptions(target,[
			
		]));	//'valueField','textField','mode','method','url'
	};
	
	$.fn.listmenu.defaults = $.extend({}, $.fn.listbox.defaults, {
		valueField: 'value',
		textField: 'text',
		mode: 'local',	// or 'remote'
		method: 'post',
		url: null,
		data: null,
		multiple: false
	});
	$.parser.plugins.push('listmenu');	//加入到插件库
})(jQuery);

// TODO 类似百度搜索框
(function($){
	var	filter = function(q, row){
		var opts = $(this).combobox("options");
		return row[opts.textField].indexOf(q) >= 0;
	}

	var formatter = function(row){
		//var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		//	'<span style="color:#888">' + row.value + '</span>';
		
		var box = $(this);
		var opts = $(this).combobox("options");
		var q = opts.queryVal;
		
		var textAlgin = textAlgin || 'left';
		
		var mv = row.name;
		//给查询字染色
		if(q){
			var ix = mv.indexOf(q);
			mv = mv.substr(0, ix) + '<font color="red">' + q + '</font>' + (mv.substr(ix + q.length));
		}
		
		
		var arr = ['<div style="cursor: pointer; height:14px; overflow: hidden; text-align: ', textAlgin, ';">&nbsp;', 
			'<li style="float: left; width: 45%;">',
				mv, 
			'</li>',
			'<li style="float: left; width: 43%;">',
				(row.orgName || "nbsp"), 
			'</li>',
		'</div>'];
		return arr.join('');
	}
	
	$.fn.indexbox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.indexbox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combobox(options, param);
			}
		}
		
		options = options || {};
		//innerFns = $.fn.listbox.innerFns || {};
		return this.each(function(){
			var box = $(this);
			options.filter = options.filter || filter;
			options.formatter = options.formatter || formatter;

			box.combobox(options);
			var ip = box.indexbox('textbox');
			ip.width(ip.width() + 18);	//去掉右边的箭头
		});
	};
	
	$.fn.indexbox.methods = {
	};
	
	$.fn.indexbox.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.combobox.parseOptions(target), $.parser.parseOptions(target,[
			
		]));	//'valueField','textField','mode','method','url'
	};
	
	$.fn.indexbox.defaults = $.extend({}, $.fn.combobox.defaults, {});
	$.parser.plugins.push('indexbox');	//加入到插件库
})(jQuery);

// TODO 类似百度搜索框, 为预报制作提供用户查询
(function($){
	var	filter = function(q, row){
		var opts = $(this).combobox("options");
		return row[opts.textField].indexOf(q) >= 0;
	}

	var formatter = function(row){
		//var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		//	'<span style="color:#888">' + row.value + '</span>';
		
		var box = $(this);
		var opts = $(this).combobox("options");
		var q = opts.queryVal;
		
		var textAlgin = textAlgin || 'left';
		
		var mv = row.name;
		//给查询字染色
		if(q){
			var ix = mv.indexOf(q);
			mv = mv.substr(0, ix) + '<font color="red">' + q + '</font>' + (mv.substr(ix + q.length));
		}
		
		var arr = ['<div style="cursor: pointer; height:14px; overflow: hidden; text-align: ', textAlgin, ';">', 
			mv, 
		'</div>'];
		return arr.join('');
	}
	
	//加载完后, 处理默认值
	function onLoadSuccess(data){
		var z = $(this);//$(target);
		var opts = z.foreUserIndexbox("options");
		if(data && data.length){	//有数据, 且是第一次加载时
			if(!opts.noFirstLoaded){
				opts.noFirstLoaded = true;
				if(opts.defVal){
					z.foreUserIndexbox('setValue', opts.defVal);
				}else if(opts.defText){
					z.foreUserIndexbox('setText', opts.defText);
				}
			}
		}else{
			z.foreUserIndexbox('setValue', '');
			z.foreUserIndexbox('setText', '');
		}
		//if(opts.onLoadSuccess_back) opts.onLoadSuccess_back.call(this, data);
	}
	
	$.fn.foreUserIndexbox = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.foreUserIndexbox.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.combobox(options, param);
			}
		}
		
		options = options || {};
		//innerFns = $.fn.listbox.innerFns || {};
		return this.each(function(){
			var box = $(this);
			options.filter = options.filter || filter;
			options.formatter = options.formatter || formatter;

			//处理默认值
			options.defVal = options.defVal || box.val() || box.attr('defVal');
			options.defText = box.attr('defText');
			if(options.onLoadSuccess){
				options.onLoadSuccess_back = options.onLoadSuccess;
			}
			options.onLoadSuccess = onLoadSuccess;

			box.combobox(options);
			if(options.isHideArraw){
				var ip = box.foreUserIndexbox('textbox');
				ip.width(ip.width() + 18);	//去掉右边的箭头
			}
		});
	};
	
	$.fn.foreUserIndexbox.methods = {
		getValue: function(jq){
			var z = $(jq[0]);
			var opts = z.foreUserIndexbox("options");
			var v = z.combobox('getValue');
			//处理值, true值必须存在于列表中
			if(opts.isMustExist){
				var data = $.data(z[0], 'combobox').data;
				if(data && data.length){
					var vf = opts.valueField;
					var found = false;
					for(var i = 0; i < data.length; i++){
						var d = data[i];
						var dv = d[vf];// + '';
						if(dv == v){
							found = true;
							break;
						}
					}
					if(found){
						return v;
					}
				}
				return undefined;
			}else{
				return v;
			}
		},
		setValue: function(jq, value){
			return jq.each(function(){
				var z = $(this);
				var opts = z.foreUserIndexbox("options");
				var v = '';
				//处理值, true值必须存在于列表中
				if(opts.isMustExist){
					var data = $.data(z[0], 'combobox').data;
					if(data && data.length){
						var vf = opts.valueField;
						for(var i = 0; i < data.length; i++){
							var d = data[i];
							var dv = d[vf];// + '';
							if(dv == value){
								v = value;
								break;
							}
						}
					}
				}else{
					v = value;
				}
				z.combobox('setValue', v);
			});
		},
		getText: function(jq){
			var z = $(jq[0]);
			var opts = z.foreUserIndexbox("options");
			var v = z.combobox('getText');
			//处理值, true值必须存在于列表中
			if(opts.isMustExist){
				var data = $.data(z[0], 'combobox').data;
				if(data && data.length){
					var vf = opts.textField;
					var found = false;
					for(var i = 0; i < data.length; i++){
						var d = data[i];
						var dv = d[vf];// + '';
						if(dv == v){
							found = true;
							break;
						}
					}
					if(found){
						return v;
					}
				}
				return undefined;
			}else{
				return v;
			}
		},
		setText: function(jq, text){
			return jq.each(function(){
				var z = $(this);
				var opts = z.foreUserIndexbox("options");
				var t = '';
				//处理值, true值必须存在于列表中
				if(opts.isMustExist){
					var data = $.data(z[0], 'combobox').data;
					if(data && data.length){
						var vf = opts.textField;
						for(var i = 0; i < data.length; i++){
							var d = data[i];
							var dv = d[vf];// + '';
							if(dv == text){
								t = text;
								var v = d[opts.valueField];
								z.combobox('setValue', v);
								//z.combobox('setText', t);
								break;
							}
						}
					}
				}else{
					t = text;
					z.combobox('setText', t);
				}
			});
		}
	};
	
	$.fn.foreUserIndexbox.parseOptions = function(target){
		var t = $(target);
		return $.extend({}, $.fn.combobox.parseOptions(target), $.parser.parseOptions(target,[
			
		]));	//'valueField','textField','mode','method','url'
	};
	
	$.fn.foreUserIndexbox.defaults = $.extend({}, $.fn.combobox.defaults, {});
	$.parser.plugins.push('foreUserIndexbox');	//加入到插件库
})(jQuery);

