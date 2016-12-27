/** M 全局对象, 提供对EasyUI的扩展功能函数 **/
if(!window["Mao"]){
	alert("请导入 Mao.js文件");
}

(function(){
	M.apply(M, {
		__hostJs:"JQuery",	//寄主 js库, 默认 ExtJs
		__extHostJs:"EasyUI",
		/*----------常量----------*/
		_r:"<br>",
		/*----------Ajax----------*/
		/** 构造 Ajax成功回调函数
		 * @param callback (Function) 成功时执行的函数
		 */
		buildSuccessCallBack:function(callback){
			return function(arg, flg, res){
				var f = false;
				var d = res ? res.responseText : "";
				if(flg){
					if(!res){
						
					}else if(d === "True"){
						f = true;
					}else{
						var x;
						x = M.isJsonStr(d, 1);
						if(x) {
							d = x;
							if(M.isObject(d) || M.isArray(d)){
								if(d.success || !d.failure){
									f = true;
								}
							}else{
								d = {msg:"未知错误!"};
							}
							//系统错误
							var sys = d.__sys;
							if(M.isObject(sys)){
								if(sys.out){
									M.App.gotoOALoginPage(d.msg);
								}
							}
						}
					}
				}else{
					d = {msg:""};
					d.msg += "服务器未响应!" + M._r;
					d.msg += res.status + "(" + res.statusText + ")";// + M._r;
//					if(M.__isDebug){
//					}
				}
				arg.d = d;
				arg.f = f;
				arg.res = res;
				var h = arg.h;
				if(h && h.masking){
					h.masking = false;
					if(h.el && h.el.unmask){
						h.el.unmask();
					}
				}
				callback.call(this, d, f, arg, res);
			}
		},
		/** ajax方法
		 * @param ps.url (String) 处理 ajax请求的地址
		 * @param ps.method (String) 提交方法 "post", "get"
		 * @param ps.params (Json) 参数
		 * @param ps.success (Function) 成功处理函数
		 * @param ps.failure (Function) 失败处理函数
		 */
		ajax:function(ps){
			ps.url = M.buildUrl(ps.url, ps.ns);
			if(ps.proxy === false){
				
			}else{
				var oldSuc = ps.success,
					oldFai = ps.failure;
				ps.successOrig = ps.success;
				ps.failureOrig = ps.failure;
				delete ps.success;
				delete ps.failure;
				if(M.isFunction(oldSuc)){	
					ps.callback = M.buildSuccessCallBack(oldSuc);
				}
			}
			
			var h = ps.h = ps.el || ps._host || ps._fhost;
			if(h){
				h.el = h.mask ? h : h.el;
				h.masking = true;
				h.el.mask(ps.maskMsg || "请求中...", ps.maskCls || "x-mask-loading");
				setTimeout(function(h){
					if(h.masking){
						h.masking = false;
						if(h.el && h.el.unmask){
							h.el.unmask();
						}
					}
				}, ps.timeout || 30000, h);
			}
			Ext.Ajax.request(ps);
		},
		/** post方式提交 ajax
		 * @see M.ajax
		 */
		post:function(ps){
			ps.method = ps.method || 'post';
			ps.dataType = ps.dataType || 'json';
			M.ajax(ps);
		},
		/** 将 json对象转换为 jsonStr 字符串 */
		encode: function(json){
			if(window['JSON']){
				return JSON.stringify(json);
			}
			return "";
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
		/*----------消息框----------*/
		alert:function(t, msg, fn){
			if(msg){
				if(M.isFunction(msg)){
					return $.messager.alert("温馨提示", t, 'info', msg);
				}else{
					return $.messager.alert(t, msg, 'info', fn);
				}
			}else{
				return $.messager.alert("温馨提示", t, 'info', fn);
			}
		},
		err:function(t, msg, fn){
			if(msg){
				if(M.isFunction(msg)){
					return $.messager.alert("错误", t, 'error', msg);
				}else{
					return $.messager.alert(t, msg, 'error', fn);
				}
			}else{
				return $.messager.alert("错误", t, 'error', fn);
			}
		},
		show: function(o){
			if(!o) o = {};
			Ext.Msg.show(M.apply({
				title: "温馨提示",
				timeout: 3000
			}, o));
		},
		/*----------杂项----------*/
		/** 创建一个 win
		 * @param single (boolean) false: 可以开多个窗口
		 */
		newWin:function(className, params, single){
			if(single !== false && M.isObject(params) && params.id){
				var win = Ext.getCmp(params.id);
				if(win){
					win.close();
				}
			}
			return M.newClass(className, params);
		},
		/*----------路径方法----------*/
		/**返回加上ext图片路径后的url
		 */
		extImgUrl:function(url){
			return M.getRootPath("themes/icons/" + url);
		},
		/**返回附属文件路径
		 */
		attachUrl:function(url){
			return M.getRootPath("attachFiles/" + url);
		},
		/**学生半身像*/
		bustUrl:function(url){
			return M.getRootPath("attachFiles/appUser/bust/" + url);
		},
		/*----------下载相关----------*/
		/** 下载用隐藏 iframe层
		 */
//		downIframesDom:null,
		/** 下载指定 Url的文件 (Extjs实现方式)
		 */
		downUrl: function(url, scr){
			if(!url) return false;
			if (!M.downIframesDom) {
				M.downIframesDom = Ext.DomHelper.append(document.body, {
					id : "downIframesDom-div" + M.randoms(),
					style : "position:absolute; width: 0px; height: 0px; hidden: true; margin: 0px; padding: 0px; overflow: hidden;"
				}, true);
			}
			///sms/doc/ExcelExport/20121202163308981.xls
			var html = Ext.DomHelper.append(M.downIframesDom, {
				html : '<iframe frameborder="0" src="' + url + '">' + scr + '</iframe>'
			}, true);
//					model.alignTo(document, "t-t");
			html.pause(1000).ghost("t", {
				remove : true
			});
		},
		/*----------调试----------*/
		_R_N:'<br>',
		/**调试 json*/
		debugJson:function(vs, s){
			s = s || "";
			for(var k in vs){
				s += k + ":" + vs[k] + (M._R_N || '\n');
			}
			M.alert(s);
		},
		/*----------日志----------*/
		/** 简单的日志记录器 */
		log:function(e, msg){
			
		},
		/*----------日期----------*/
		/** 用于datebox控件的日期格式化, 格式化为2014-11-5的格式 */
		fmtDateCtrl:function(date){
			date = date || new Date();
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		},
		/** 用于datebox控件的日期字符串解析, 解析2014-11-5的格式 */
		parseDateCtrl:function(s){
			if (!s) return new Date();
			var ss = s.split('-');
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		},
		/** 日期方法
		 * @param hour (int) -2: 往前推两小时
		 * @param day (int)  -2: 往前推两天
		 * @param date (Date) 日期对象, 默认当前时间
		 * @return M.dateSmart(-2, 1) --> (假设当前时间为2014-07-07 18:00:15)
		 * 则返回{
		 * 	datetime:'2014-07-08 16:00:15', 
		 * 	date:'2014-07-08', 
		 * 	time:'16:00', 
		 * 	hour:16, 
		 * 	dateObj: 修改后的日期对象,
		 * 	orgDateTime: '2014-07-07 18:00:15'(原始的), 
		 * 	orgDate:'2014-07-07'(原始的), 
		 * 	orgDateObj: 原始的日期对象
		 * }
		 */
		dateSmart: function(hour, day, date){
			date = date || new Date();
			day = day || 0;
			hour = hour || 0;
			var orgDateObj = new Date();	//原始的日期
			orgDateObj.setTime(date.getTime());
			var hr = date.getHours() + hour;
			date.setHours(hr);
			date.setDate(date.getDate() + day);
			var obj = {
				datetime: M.dateTimeStr('yyyy-MM-dd HH:mm:ss', date),
				date: M.dateTimeStr('yyyy-MM-dd', date),
				time: hr + ":00",
				hour: hr,
				dateObj: date,
				orgDateTime: M.dateTimeStr('yyyy-MM-dd HH:mm:ss', orgDateObj),
				orgDate: M.dateTimeStr('yyyy-MM-dd', orgDateObj),
				orgDateObj: orgDateObj
			};
			return obj;
		},
		/*----------表单----------*/
		/**获取表单的所有值
		 * @param opts.selector (String) 表单的 id或name值
		 * @param opts.igEmpty (boolean) true: 忽略空值字段
		 */
		formValues:function(opts){
			if(M.isString(opts)){
				opts = {selector: opts};
			}
			var f = $(opts.selector);
			if(!f.length) return ;
			var fm = f[0];
			var es = fm.elements;
			if(!es.length) return ;
			var fn = null;
			if(M.isFunction(opts.formatter)) fn = opts.formatter;
			var igEmpty = opts.igEmpty;
			
			var vs = {};
			var v;
			for(var i = 0; i < es.length; i++){
				var fe = es[i];
				if(!fe.name) continue;
				if(fe.type == "radio" && !fe.checked) continue;
				var e = $(fe);
				v = e.val();
				if(fn){
					v = fn.cal(fe, v, fm);
				}
				if(igEmpty && !v && v !== 0) continue;
				var vsv = vs[fe.name];
				if(!vsv) vsv = vs[fe.name] = [];
				vsv.push(v);
			}
			var nvs = {};
			for(var k in vs){
				v = vs[k];
				nvs[k] = v.length > 1 ? v.join(',') : v[0];
			}
			return nvs;
		},
		/*----------数据查找----------*/
		/** 从数据中查找节点, 用于树节点的属性查找, 如果找到了返回节点数据 */
		findNodeByData: function(data, key, val){
			if(data && data.length){
				for(var i = 0; i < data.length; i++){
					var d = data[i];
					if(key in d){
						if(d[key] == val){
							return d;
						}
					}else if(d.bean && M.isObject(d.bean)){
						if((key in d.bean) && d.bean[key] == val){
							return d;
						}
					}else if(d.children && M.isArray(d.children)){
						//查询 子节点
						var r_d = M.findNodeByData(d.children, key, val);
						if(r_d) return r_d;
					}
				}
			}
		},
		/** 查找{}中key的值, 如果是[]将遍历, 子孙值中为{}、[]的都会被检测, <br> 
		 * 结果保存在在 flg中{found: 是否找到, val: 找到的值}, 暂不知此函数有啥用
		 */ 
		findPropVal: function(o, key, flg){
			flg = flg || {};
			if(M.isObject(o)){
				if(key in o) {
					flg.found = true;
					flg.val = o[key];
					return flg;
				}else{
					//不在{}中, 则从{}中找值为{}、[]中找
					for(var k in o){
						M.findPropVal(o[k], key, flg);
						if(flg.found) return flg;
					}
				}
			}else if(M.isArray(o)){
				for(var i = 0; i < o.length; i++){
					M.findPropVal(o[i], key, flg);
					if(flg.found) return flg;
				}
			}
			return flg;
		},
		/*----------初始化----------*/
		/** 根路径 */
		//__rootPath: "/",
		/** 未检测根路径 */
		ckRootPathed: false,
		/** 检测根路径 */
		ckRootPath: function(){
			if(M.ckRootPathed){
			}else{
				var url = window.location.pathname;
				var l = url.length;
				if(l > 1){
					var s2 = url.substr(l - 4);
					if(s2 === ".asp"){
						if(!M.__rootPath) M.__rootPath = "/";
						M.isAsp = true;
					}else{
						var arr = url.split("/");
						if(!M.__rootPath) M.__rootPath = "/" + arr[1] + "/";
						M.isJsp = true;
					}
					M.ckRootPathed = true;
				}
			}
		},
		/** 初始化时 要调用的函数集 */
		initFns:[],
		/** 未 初始化内部 */
		inited: false,
		/** 接收 内部 初始化时 要调用的函数
		 * @param fn (Function) 函数
		 */
		init: function(fn){
			if(M.isFunction(fn)){
				M.initFns.push(fn);
			}
		},
		/** 内部 初始化时 执行(1次) */
		__init: function(){
			if(M.inited) return;
			M.ckRootPath();	//初始化根路径
			// 启动 计时器
			setInterval(function(){	
				M.counter++;
			}, 1000);
			var i = 0,
				fns = M.initFns,
				l = fns.length;
			for(; i < l; i++){
				fns[i].call(this);
			}
		}
	});
	
	//对 Ext的支持
//	M.extend = Ext.extend;
//	M.reg = Ext.reg;
//	M.override = Ext.override;
//	
//	M.Window = Ext.Window;
//	M.Panel = Ext.Panel;
	
})();

//简单地导入 js文件方法, 此方法名 在 Ext.ux.Fckeditor中会用到
$ImportSimpleJs = M.ImportSimpleJs;

//TODO 对jQuery的工具方法
(function(){
	//重写 jquery的ajax方法, 实现未登录自动跳转到登录界面
	$.oldAjax = $.ajax;
	$.ajax = function(opts){
		opts = opts || {};
		opts.type = opts.type || 'POST';
		if(opts.dataType !== '') opts.dataType = opts.dataType || 'json';
		opts.oldComplete = opts.complete;
		opts.complete = function(req, status){
			var z = this;
			var txt = req.responseText;
			var ds = M.isJsonStr(txt, true);
			if( (ds && ds.__err === 'unlogin') 
				|| (txt.length && txt.length > 500 && txt.indexOf('<title>登录界面</title>') > 0) ){
				//因为部分 ajax请求未使用 "ajax_"开头, 导致未登录时返回login.jsp的内容, 所以才做第二个或判断				
				window.location = M.buildUrl('login.jsp');
			}
			if(M.isFunction(z.oldComplete)) z.oldComplete(req, status);
		};
		$.ajaxSettings.traditional = opts.hasArrArg === false ? false : true;	//有数组参数, 开启旧的参数转换模式
		$.oldAjax(opts);
		$.ajaxSettings.traditional = false;
	};
	
	//POST请求
	M.post = function(opts){
		opts = $.extend({
			type : "post",
			dataType : "json"
		}, opts);
		opts.url = M.buildUrl(opts.url);
		$.ajax(opts);
	}
})(jQuery);

//TODO 对Easy的工具方法
(function(){
	//TODO 对Easy的tabs工具方法
	M.tabs = {
		centerId:'#i_tabs',	//中心 id
		/** 追加Tab页, title:标题, aurl(或href):地址, 
		 * tabsId:Tab框架ID(默认主页主Tabs),
		 * opener: 向 iframe中传递打开页面(一般传window, 默认不传递)
		 */
		toCenter: function(ps){
			var z;
			var o;
			if(ps && ps.altKey === undefined){	
				//自定义方式
				o = ps;
				o.url = o.url || o.aurl || o.href;
				if(!o.url) return false;
			}else{
				z = $(this);	// a 标签触发
				o = {};
				o.isA = true;	//A标签触发
				o.url = z.attr("aurl") || z.attr("href");
				if(!o.url) return false;
				o.title = z.text();
				o.menuId = z.attr('menuId');
				o.foreTypeCode = z.attr('foreTypeCode');
				o._id = z.attr("name");
			}
			
			var tsId = o.tabsId || M.tabs.centerId;
			var ts = $(tsId);
			if(ts.tabs("exists", o.title)){
				if(o.closeOld){
					M.tabs.forceCloseTab(ts, o.title);
				}else{
					ts.tabs("select", o.title);
					return false;
				}
			}
			
			//Tab的id
			o.tabId = o.tabId || (tsId + '_tab_' + new Date().getTime() + M.randoms(3));
			if(o.tabId.charAt(0) === '#') o.tabId = o.tabId.substring(1);
			
			//存放每个Tab的信息
			var opts = $.data(ts[0], 'tabs').options;
			var tis = opts.m_tiMap = opts.m_tiMap || {};
			var ifrId = o.tabId + '_ifr';
			var tio = tis[o.title] = {
				tabId: o.tabId,
				ifrId: ifrId,
				menuId: o.menuId,
				foreTypeCode: o.foreTypeCode,
				title: o.title
			};
			if(o._id){
				ifrId = o._id;
			}
			o.url = M.buildUrl(o.url);
			ts.tabs('add',{
				title: o.title,
				content:'<iframe id="' + ifrId + '" frameborder="0" src="' + o.url + '" width="100%" height="100%"></iframe>',
				closable: o.closable === false || o.closable === 'false' ? false : true
			});
			
			//如果需要向 iframe中传递打开页面, opener = window
			if(ps.opener !== false){
				M.checkVar(function(){
					var ifr = $('#' + ifrId);
					if(ifr.length){
						var ifrS = ifr[0];
						if(ifrS.window || ifrS.contentWindow){
							var w = ifrS.window || ifrS.contentWindow;
							var ow = M.isAnyObject(ps.opener) ? ps.opener : window;
							if(w.M) w.M.w_opener = ow;
							w.w_opener = ow;
							return w;
						}
					}
				});
			}
			
			//if(z.attr("href")) return !h_f;	//屏蔽单击跳转
			return false;
		},
		/** 获取中心Tabs，当前激活的Tab的信息 */
		getCurTabInfo:function(tabs){
			var ts = $(tabs || M.tabs.centerId);
			if(!ts.length){
				ts = M.tabs.getDesktopCenter();
				if(!ts || !ts.length){
					return M.paramUrl();
				}
			}
			var t = ts.find(".tabs-selected .tabs-title.tabs-closable");
			if(t.length){
				var title = t.html();
				var opts = ts.tabs('options');//$.data(ts[0], 'tabs').options;
				var tis = opts.m_tiMap || {};
				return tis[title];
			}
			
		},
		/** 获取中心Tabs，id为M.tabs.centerId定义, w:默认当前Window */
		getCenter:function(w){
			w = w || window;
			var tabs = w.$.call(w, w.M.tabs.centerId);
			if(tabs.length) return tabs;
		},
		/** 获取顶层窗口的中心Tabs，id为M.tabs.centerId定义 */
		getDesktopCenter:function(){
			var pw = M.desktopWindow();
			if(pw){
				return M.tabs.getCenter(pw);
			}
		},
		/**关闭指定的(t)Tab或当前对象所在的tab*/
		closeTab:function(tabs, t){
			tabs = $(tabs || M.tabs.centerId);
			if(tabs.length && t){
				tabs.tabs("close", t);
			}
		},
		/** 强制关闭指定的(t)Tab */
		forceCloseTab: function(tabs, t){
			tabs = $(tabs || M.tabs.centerId);
			var opts = tabs.tabs('options');
			var bc = opts.onBeforeClose;
			opts.onBeforeClose = function(){};  // allowed to close now
			tabs.tabs('close', t);
			opts.onBeforeClose = bc;  // restore the event function
		},
		/**关闭当前活动 tab*/
		closeCurTab:function(tabs){
			var tabs = $(tabs || M.tabs.centerId);
			var t = tabs.find(".tabs-selected .tabs-title.tabs-closable");
			tabs.tabs("close", t.html());
		},
		/** 获取Tab下放置的iframe */
		getIframe:function(tabs, t){
			var opts = $.data(tabs[0], 'tabs').options;
			var ti = (opts.m_tiMap || {})[t];
			if(ti && ti.ifrId){
				var ifr = $('#' + ti.ifrId);
				if(ifr.length) return ifr;
			}
		}
	};
	
	// TODO Tab上右键菜单
	M.TabRMenu = {
		/**当前右键点击的对象*/
		curObj:"",
		init:function(o){
			M.TabRMenu.createMenu(o);
		},
		createMenu:function(o){
			var tabs = $('#' + o.tabsId);
			if(!tabs.length) return ;	//必须有上层
			
			var p = $(o.toTag || "body");
			o.mid = o.mid || ('tab_lm_' + (tabs[0].id || (tabs[0].id = M.date().getTime())));
			var rm = p.find('#' + o.mid);
			if(o.delOld){
				rm.remove();
			}
			if(!rm.length){
				o.onId = o.onId || 'ul.tabs li';
				tabs.addClass('has_tab_menu');
				tabs.data('tabMenuOpts', o);
				M.TabRMenu.on(o);
				var arr = ['<div id="', o.mid, '" class="tab_menu" tabsId="', o.tabsId, '" ',
					'style="width:120px;">',
					'<div data-options="iconCls:\'icon-reload\'" rmenu="refresh">刷新</div>',
					'<div data-options="iconCls:\'icon-no\'" rmenu="close">关闭</div>',
					'<div data-options="iconCls:\'icon-no\'" rmenu="closeOther">关闭其他</div>',
					'<div data-options="iconCls:\'icon-no\'" rmenu="closeAll">关闭全部</div>',
				'</div>'];
				p.append(arr.join(""));
				var rm = p.find('#' + o.mid);
				rm.menu();
				//为每个选项添加点击事件,前提是其存在 rmenu属性,且其值为M.TabRMenu中的方法或全局方法
				rm.find("[rmenu]").click(function(){
					var z = $(this);
					var n = z.attr("rmenu");
					if(n){
						var fn = M.TabRMenu[n];
						if(!M.isFunction(fn)){
							fn = window[n];
						}
						if(fn){
							var pm = z.parents('.tab_menu');
							var tabs = $('#' + pm.attr('tabsId'));
							var opts = tabs.data('tabMenuOpts');
							fn.call(tabs, opts);
						}
					}
				});
			}
		},
		/**默认方法*/
		refresh:function(opts){
			var curTab = opts.curTag;
			if(!curTab) return ;
			var z = $(this);
			var t = curTab.find(".tabs-title");	//.tabs-closable
			var tab = z.tabs('getTab', t.html());
			z.tabs('update', {
				tab: tab,
				options:{
					content:tab.html()
				}
			});
		},
		/**默认关闭方法*/
		close:function(opts){
			var curTab = opts.curTag;
			if(curTab)
			var t = curTab.find(".tabs-title.tabs-closable");
			if(t.length){
				$(this).tabs("close", t.html());
			}
		},
		/**默认关闭全部方法*/
		closeAll:function(opts){
			var z = $(this);
			var ts = z.find(".tabs-title.tabs-closable");
			/**获取 tabs 选项卡*/
			ts.each(function(){
				var t = $(this);
				if(t.length){
					z.tabs("close", t.html());
				}
			});
		},
		/**默认关闭其他方法*/
		closeOther:function(opts){
			var z = $(this);
			var ts = z.find(".tabs-title.tabs-closable");
			if(ts.length > 0){
				var curTab = opts.curTag;
				if(!curTab) return ;
				var curTitle = curTab.find(".tabs-title.tabs-closable").html();
				ts.each(function(){
					var title = $(this).html();
					if(title != curTitle){
						z.tabs("close", title);
					}
				});
			}
		},
		/**右键监听*/
		on:function(o){
			$("body").on("contextmenu", "o.onId", function(e){
				var z = $(this);
				//M.TabRMenu.curObj = z;
				var p = z.parents('.has_tab_menu');
				var opts = p.data('tabMenuOpts');
				opts.curTag = z;
				$('#' + opts.mid).menu('show', {
					left: e.pageX,
					top: e.pageY
				});
				return false;
			});
		}
	};
	
	//TODO 对Easy的window窗口工具方法
	M.win = {
		/** 将窗口移动到某个位置
		 * win (misc) 窗口
		 * pos (misc) 方位 ['l', 'lt', 't', 'rt', 'r', 'rb', 'b', 'lb', 'c', 'ct', 'cb', 'cx'], 或 {top:0, left:0} <br>
		 * 	'at': 居中,若窗口太高, 将顶部露出来; 'ab': 露出底部; <br>
		 * 	'x': 居中, 若窗口太高(_h=true), 将调整窗口的大小以适应。
		 * pad (int) 间隔, 默认[3, 3], [左, 上], 或[右, 下]
		 */
		move: function(win, pos, pad, body, scr){
			win = $(win);
			if(M.isObject(pos)){
				win.window('move', pos);
			}else if(M.isString(pos)){
				if(!pad) pad = [3, 3];
				var ret = M.win.pos(win.parent('.window'), pos, pad, body, scr);
				if(pos == 'cx' && (ret._hx || ret._wx)){
					win.window('resize', {width: ret._w, height: ret._h});
				}
				win.window('move', ret);
			}
		},
		/** 打开窗口
		 * win (misc) 窗口
		 * ui (String) 默认'window', 还可能为'dialog'等
		 * pos (misc) 方位 ['l', 'lt', 't', 'rt', 'r', 'rb', 'b', 'lb', 'c', 'ct', 'cb', 'cx'], 或 {top:0, left:0} <br>
		 * 	'at': 居中,若窗口太高, 将顶部露出来; 'ab': 露出底部; <br>
		 * 	'x': 居中, 若窗口太高(_h=true), 将调整窗口的大小以适应。
		 * pad (int) 间隔, 默认[3, 3], [左, 上], 或[右, 下]
		 */
		open: function(win, ui, pos, pad, body, scr){
			win = $(win);
			if(pos){
				M.win.move(win, pos, pad, body, scr);
			}
			ui = ui || 'window';
			win[ui]('open', 'M');
		},
		/** 返回窗口(或div)相对 body(或指定容器)的位置<br>
		 *  若是窗口, 应该这样调用 M.win.pos(win.parent(), pos, pad);
		 * win (misc) 窗口(或div)
		 * pos (misc) 方位 ['l', 'lt', 't', 'rt', 'r', 'rb', 'b', 'lb', 'c', 'ct', 'cb', 'cx']
		 * pad (int) 间隔, 默认[0, 0], [左, 上], 或[右, 下]
		 * body (misc) 相对谁的位置, 默认相对 body标签, 
		 */
		pos: function(win, pos, pad, body, scr){
			var ret = {};
			pad = pad || [0, 0];	//
			if(body) body = $(body);
			if(!body || !body.length) body = $('body');
			if(scr !== false) pad[1] += body.scrollTop();
			
			var w_w = win.outerWidth();
			var w_h = win.outerHeight();
			var b_w = body.width();
			var b_h = body.height();
			switch(pos){
				case 'l':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = pad[0];
					break;
				case 'lt':
					ret.top = pad[1];
					ret.left = pad[0];
					break;
				case 't':
					ret.top = pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					break;
				case 'rt':
					ret.top = pad[1];
					ret.left = b_w - w_w - pad[0];
					break;
				case 'r':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = b_w - w_w - pad[0];
					break;
				case 'rb':
					ret.top = b_h - w_h - pad[1];
					ret.left = b_w - w_w - pad[0];
					break;
				case 'b':
					ret.top = b_h - w_h - pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					break;
				case 'lb':
					ret.top = b_h - w_h - pad[1];
					ret.left = pad[0];
					break;
				case 'c':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					break;
				case 'ct':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					if(ret.top < 0) ret.top = pad[1];	//居中,若窗口太高, 将顶部露出来
					break;
				case 'cb':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					if(ret.top + w_h > b_h) ret.top = b_h - w_h;	//居中,若窗口太高, 将底部露出来
					break;
				case 'cx':
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					//居中, 若窗口太高(_h=true), 将调整窗口的大小以适应
					if(ret.top < 0){
						ret._h = b_h - pad[1] - pad[1];
						ret.top = pad[1];
						ret._hx = true;
					}
					if(ret.left < 0){
						ret._w = b_w - pad[0] - pad[0];
						ret.left = pad[0];
						ret._wx = true;
					}else if(ret._hx){
						ret._w = win.width();
					}
					
					if(ret._wx && !ret._hx){
						ret._h = win.height();
					}
					
					break;
				default:
					ret.top = (b_h - w_h) / 2 + pad[1];
					ret.left = (b_w - w_w) / 2 + pad[0];
					break;
			}
			return ret;
		}
	}	
	
	/** 调整第一个面板刚好能容下第二个面板
	 * @param a (String) 父面板 id
	 * @param b (String) 子面板 id
	 * @param n (int) 偏差值
	 * @param mh (mh) 最大高度
	 */
	M.adjustHeight = function(p, b, n, mh){
		p = $(p);
		b = $(b);
		var h = b.height() + (n || 0);
		if($.browser.msie){
			h += 4;
		}
		h = mh && h > mh ? mh : h;
		p.height(h);
	};

	/** 调整子面板刚好能被父面板容下
	 * @param p (String) 父面板 id
	 * @param dg (String) 子面板 id
	 * @param mw (int) 最大宽度
	 * @param mh (mh) 最大高度
	 */
	M.fitHeight = function(p, b, n, mh){
		p = $(p);
		b = $(b);
		var h = p.innerHeight()-(p.outerHeight() - p.height()) + (n || 0);// - p.scrollTop();
		if($.browser.msie){
			//h += 4;
		}
		h = mh && h > mh ? mh : h;
		b.height(h);
	};
	
	
	//TODO 定时器
	M.timer = {
		fns: [],
		i:0,
		ms:300,
		add: function(fn){
			M.timer.fns.push(fn);
		},
		/** 执行到此处时触发 */
		fire:function(){
			$(window).resize(function(){
				if(++M.timer.i==2){
					setTimeout(function(){
						var fns = M.timer.fns;
						for(var i = 0; i < fns.length; i++){
							fns[i].call();
						}
						M.timer.i = 0;
					}, M.timer.ms || 300);
				}
			});
			
			//初始化全局环境变量
			M.__curUrl = window.location.href;
			M.__curPathName = window.location.pathname;
			M.__curParam = window.location.search;
		},
		/** 当页面加载完后触发 */
		afterLoadFire: function(){
			if(M.__curParam.match("__version")){
				var arr = [
					'作者:<font color="red">', M.__author || 'Mao', '</font><br>',
					'当前js版本:<font color="red">', M.__version || '1.0.0', '</font><br>',
					'创建时间:<font color="red">', M.__createTime, '</font><br>',
					'最后更新时间:<font color="red">', M.__finalTime, '</font><br>'
				];
				$.messager.show({
					title:"关于我们",
					height: 130,
					msg: arr.join(''),
					timeout:8000
				});
			}
		}
	};
	
	
	// TODO datagrid的工具方法
	M.dg = {
		/** 通过索引删除一行记录, 此方式只适用【本地数据】、【每行后面的删除按钮的点击事件】
		 * @param tid datagrid的id, 将这样使用: $(tid);
		 */
		delByIndex: function(tid, ix, v, row, fn){
			var dg = $(tid);
			dg.datagrid('deleteRow', ix);
			var datas = dg.datagrid('getData');
			dg.datagrid('loadData', datas);
		},
		/** 动态显示/隐藏列, 通过属性值筛选, 其他的列将隐藏/显示, (不存在 prop属性的列不参与显示/隐藏)
		 * @param dg (misc) datagrid对象或标识
		 * @param prop (String) 筛选属性名, 如: 'field'、'group'
		 * @param vals (Object) 筛选属性的值, 如: 'title'、'weat'
		 * @param hide (boolean) true: 隐藏, 默认显示
		 * @return 如: M.dg.showColumnByProp(dg, 'group', 'abc', true);
		 */
		showColumnByPropOnly: function(dg, prop, vals, hide){
			var cols = dg.datagrid('options').columns;
			if(cols && cols.length){
				cols = cols[1] ? cols[1] : cols[0];
			}
			if(!M.isArray(vals)) vals = [vals];
			if(cols){
				var hcs = [];	// hide操作的
				var nos = [];	// !hide操作的
				for(var i = 0; i < cols.length; i++){
					var col = cols[i];
					if(col.hasOwnProperty(prop)){
						for(var j = 0; j < vals.length; j++){
							var val = vals[i];
							if(col[prop] == val){
								hcs.push(col);
							}
						}
					}
				}
				M.dg.showColumn(dg, hcs, hide);
			}
		},
		/** 动态显示/隐藏列, 通过属性值筛选
		 * @param dg (misc) datagrid对象或标识
		 * @param prop (String) 筛选属性名, 如: 'field'、'group'
		 * @param val (Object) 筛选属性的值, 如: 'title'、'weat'
		 * @param hide (boolean) true: 隐藏, 默认显示
		 * @return 如: M.dg.showColumnByProp(dg, 'group', 'abc', true);
		 */
		showColumnByProp: function(dg, prop, val, hide){
			var cols = dg.datagrid('options').columns;
			if(cols && cols.length){
				cols = cols[1] ? cols[1] : cols[0];
			}
			if(cols){
				var hcs = [];
				for(var i = 0; i < cols.length; i++){
					var col = cols[i];
					if(col[prop] == val){
						hcs.push(col);
					}
				}
				M.dg.showColumn(dg, hcs, hide);
			}
		},
		/** 动态显示/隐藏列
		 * @param dg (misc) datagrid对象或标识
		 * @param items (misc) 要显示或隐的列, 支持以下格式: <br>
		 * 	'name'、['name', 'age']、{field: 'name'}、[{field: 'name'},{field: 'age'}]
		 * @param hide (boolean) true: 隐藏, 默认显示
		 * @return 如: M.dg.showColumn('#dg', ['name', 'age'], true);
		 */
		showColumn: function(dg, items, hide){
			if(!items) return ;
			dg = $(dg);
			var isInner = false;	//true, 传的是options.columns
			if(M.isString(items)){
				items = [items];
			}else if(M.isObject(items) && items.field){
				items = [items];
				isInner = true;
			}else if(M.isArray(items) && items.length){
				if(M.isObject(items[0])){
					isInner = true;
				}
			}else{
				return ;
			}
			hide = hide !== false;
			return dg.each(function(){
				var z = $(this);
				var pnl = z.datagrid('getPanel');
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					var field = isInner ? item.field : item;
					var ip = pnl.find("td[field=\""+ field +"\"]");
					if(hide){
						ip.hide();
					}else{
						ip.show();
					}
					if(isInner){
						item.hidden = hide;
					}else{
						z.datagrid("getColumnOption", field).hidden = hide;
					}
				}
				z.datagrid("fitColumns");
			});
		}
	};
	
	// TODO 对GIS支持, 工具类 
	M.gis = {
		/** 将feature.geometry.components格式的点集转为数组格式<br>
		 * 如: [{x:1, y:1}, {x:2, y:2}] -> ['1 1', '2 2']
		 */
		toPointsXY: function(points){
			var len = points.length;
			var arr = [];
			for (var i = 0; i < len; i++) {
				arr.push(points[i].x + ' ' + points[i].y);
			}
			return arr;
		},
		/** 将feature.geometry.components格式的点集转为二维数组格式<br>
		 * 如: [{x:1, y:1}, {x:2, y:2}] -> [[1, 1], [2, 2]]
		 */
		toPointsArr2: function(points){
			var len = points.length;
			var arr = [];
			for (var i = 0; i < len; i++) {
				arr.push([points[i].x, points[i].y]);
			}
			return arr;
		},
		/** 将feature.geometry.components格式的点集转换成逗号","分隔点, 空格" "分隔经纬度的字符串 <br>
		 * 如: [{x:1, y:1}, {x:2, y:2}] -> "1 1,2 2"
		 */
		toPointsStr: function(points){
			var len = points.length;
			var t = new M.Timer();
			var str = "";
			var arr = [];
			for (var i = 0; i < len; i++) {
				arr.push(points[i].x + " " + points[i].y);
			}
			str = arr.join(',');
			t.printLastTimeElapsed('Array');
			return str;
		},
		/** 传递到后台获取圈选区域feature 的反演信息(区域、站点)
		 * @param data.points (Object) 转换好的点集信息, 如果为null, 则从feature.geometry.components中获取
		 * @param data.feature (Object)  feature.geometry.components中存放着圈的点集信息
		 * @param data.distId (long) 地区Id, 指定范围, 将只查询这个地区的下属地区
		 * @param data.distType (String) (1省、2市、3县区、4乡镇), 指定范围, 将只查询这种类型下的地区
		 * @param data.staOrgId (long) 传单位id, 查询属于这个单位的站点
		 * @param data.staType (String) (1自动站, 2区域站), 其他值不区分站点类型
		 * @param data.invType (int) 反演类型(10~29), 有: <br>
		 * 10: 只反演 [地区代码, ...]; <br>
		 * 11: 只反演 [地区名, ...]; <br>
		 * 15: 反演 [[代码, 地区名], ...]; <br>
		 * 16: 反演 [[地区代码, ...], [地区名, ...]]; <br>
		 * 20: 只反演 [站号, ...]; <br>
		 * 21: 只反演 [站点名, ...]; <br>
		 * 25: 反演 [[站号, 站点名], ...]; <br>
		 * 26: 反演 [[站号, ...], [站点名, ...]] <br>
		 * @param success (function) 成功的因调函数
		 * @return 示例:<pre>
		 * 		M.gis.inverseInfo({
					data:{
						feature: feature,
						distType: "2",	// 地区类型(2市级)
						staType: "1",	// 站点类型(1自动站)
						invType: 1121 	// 地区名组  +  站点名组
					},
					success: function(d){
						var c = d.result;	//d.result = [  [地区名, ...],  [站点名, ...]  ]
					}
				});
		 * </pre>
		 */
		inverseInfo: function(ps){
			var data = ps.data;
			data.cir = data.points || M.gis.toPointsXY(data.feature.geometry.components);
			//删除没用的
			delete data.points; 
			delete data.feature;
			
			ps.url = ps.url || M.buildUrl("ajax_fore_ForePublic_inverseInfo.action");
			ps.type = ps.type || "POST";
			ps.dataType = ps.dataType || "json";
			
			$.ajaxSettings.traditional=true ;
			$.ajax(ps) ;
			$.ajaxSettings.traditional=false ;
		}
	}
	
	// TODO 对GIS支持类
	M.gis.Gis = function(opts){
		this.opts = M.apply({}, opts);
		this.init(this.opts);
		return this;
	};
	M.gis.Gis.fn = M.gis.Gis.prototype = {
		init:function(opts){
			return this;
		}
	};
	
	/** esayui组件简易方法 */
	M.ecmps = {
		/** 给一个esayui组件设置/删除函数, 返回旧函数<br>
		 * 如: M.ecmps.setFn('#date', 'datebox', 'onSelect', function(){var i=0});	//给组件设置一个onSelect监听事件<br>
		 * M.ecmps.setFn('#date', 'datebox', 'onSelect');	//将删除onSelect监听事件<br>
		 * @param cmp (Object/String) 组件, 如: date, '#date'
		 * @param cn (String) 组件的名称, 如: 'datebox' 
		 * @param fnCn (String) 要设置的组件的新函数名。如: 'onSelect'
		 * @param fn (Function) 要设置的组件的新函数。 null: 则删除
		 * @return (Function) 返回旧函数
		 */
		setFn: function(cmp, cn, fnCn, fn){
			cmp = $(cmp);
			var opts = cmp[cn]('options');
			var oldFn = opts[fnCn];
			opts[fnCn] = fn || M.ecmps.emptyFn;
			return oldFn;
		},
		/** 将esayui组件的某个函数在移除和恢复间切换<br>
		 * 如: M.ecmps.toggleFn('#date', 'datebox', 'onSelect');	
		 * @param cmp (Object/String) 组件, 如: date, '#date'
		 * @param cn (String) 组件的名称, 如: 'datebox' 
		 * @param fnCn (Function) 要设置的组件的新函数名。如: 'onSelect'
		 * @return true: 函数被恢复; false: 当前函数被移除。
		 */
		toggleFn: function(cmp, cn, fnCn){
			cmp = $(cmp);
			var opts = cmp[cn]('options');
			var bkFcn = fnCn + '___back';
			var hasFn;
			if(opts[bkFcn]){	//存在备份函数
				opts[fnCn] = opts[bkFcn];
				opts[bkFcn] = null;
				hasFn = true;
			}else{
				opts[bkFcn] = opts[fnCn];
				opts[fnCn] = M.ecmps.emptyFn;
				hasFn = false;
			}
			return hasFn;
		},
		emptyFn:function(){}
	}
	
	{
		// TODO 对datagrid的编辑功能的扩展
		var mths = $.fn.datagrid.methods;	//方法
		var defs = $.fn.datagrid.defaults;	//默认对数
		
		$.extend(defs, {
			editIxArgs: {},
			/** 点击 标志列时触发, 用于修改多个数据
			 * @param editIxArgs (Object) o.index为当前行, o.field为当前列字段名, o.udata将更新到此行的数据。
			 * @param row (Object) 当前行的值
			 * @param acols (Object) 所有列信息
			 * @param opts (Object) 构造时的参数
			 */
			onFlagField: function(editIxArgs, row, acols, opts){},
			flagSyncTv: false,
			syncEditType: ['weateditor', 'winddirspeededitor', 'combobox'],
			/** 标记 当前编辑时行号, 字段名 */
			flagEditIxArgs: function(index, field, xargs){
				var as = xargs || editIxArgs;
				as.preIx = as.index;
				as.preField = as.field;
				as.index = index;
				as.field = field;
			},
			/** 结束列编辑 */
			endCellEditing: function(editIxArgs){
				var target = this;
				var z = $(target);
				var opts = $.data(target, "datagrid").options;
				editIxArgs = editIxArgs || opts.editIxArgs;
				
				if (editIxArgs.index == undefined) return true;
				if (z.datagrid('validateRow', editIxArgs.index)){
					
					opts.updateRowDataForCell.call(target, editIxArgs);
					
					z.datagrid('endEdit', editIxArgs.index);
					if($.isFunction(opts.onEndCellEditAfter)){
						var rows = z.datagrid('getRows');
						var row = rows[editIxArgs.index];
						// 结束列编辑后触发事件, onEndCellEditAfter(ri, row, rows, field, opts)[行号, 行数据, 全部数据, 单元格名, 操作参数]
						opts.onEndCellEditAfter.call(target, editIxArgs.index, row, rows, editIxArgs.field, opts);
					}
					opts.flagEditIxArgs.call(target, undefined, undefined, editIxArgs);
					return true;
				} else {
					return false;
				}
			},
			/** 编辑列时, 更新字段 */
			updateRowDataForCell: function(editIxArgs){
				var target = this;
				var z = $(target);
				var opts = $.data(target, "datagrid").options;
				editIxArgs = editIxArgs || opts.editIxArgs;
				
				var field = editIxArgs.field;
				var index = editIxArgs.index;	//当前点击的行号
				var preIx = editIxArgs.preIx;	//上一次点击的行号
				
				// 在修改某列时, 可以在这里修改其他列的值
				if(opts.onUpdateRowDataForCell){
					var rows = z.datagrid('getRows');
					var row = rows[index];
					opts.onUpdateRowDataForCell.call(target, editIxArgs, row, rows);
				}
				
				if(M.inArr(editIxArgs.flagFields, field) != -1){
					var rows = z.datagrid('getRows');
					var row = rows[index];
					
					var irs = $.data(target, "datagrid").insertedRows;
					var urs = $.data(target, "datagrid").updatedRows;
					if(M.inArr(irs, row) == -1){
						if(M.inArr(urs, row) == -1){
							urs.push(row);
						}
					}
					//同步所有字段
					if(opts.flagSyncTv){
						var fields = z.datagrid('getColumnFields',true).concat(z.datagrid('getColumnFields'));
						M.editors.syncTv(z, index, fields);
					}
				}else{
					// 对特殊类型的编辑框处理。
					M.editors.syncTv(z, index, field);
				}
			},
			/** 单击单元格时触发 */
			onClickCellBefore: function(index, field, val, row){
				var target = this;
				var z = $(target);
				var opts = $.data(target, "datagrid").options;
				if(opts.editCellable){
					var editIxArgs = opts.editIxArgs;
					//如果点击了 特定列
					if(M.inArr(editIxArgs.flagFields, field) != -1){
						//如果之前在编辑, 结束
						opts.endCellEditing.call(target, editIxArgs);
						opts.flagEditIxArgs.call(target, index, field, editIxArgs);
						
						// 统一编辑, 放在开始编辑之前, 以便之后进行同步
						if(opts.onFlagField){
							editIxArgs.udata = undefined;
							var cols = opts.columns;
							var acols = [];
							if(cols && cols.length){
								if(cols.length == 1) acols = cols[0];
								if(cols.length == 1) acols = cols[0].concat(cols[1]);
							}
							opts.onFlagField.call(target, editIxArgs, row, acols, opts);
						}
						M.apply(row, editIxArgs.udata);
						//editIxArgs.udata = undefined; //不做清除, 用于之后的对比
						
						// 如果要同步 修改的值, 开始编辑, 否则开启列编辑
						if(opts.flagSyncTv){
							z.datagrid('selectRow', index).datagrid('beginEdit', index);
							opts.endCellEditing.call(target, editIxArgs);
						}else{
							z.datagrid('selectRow', index).datagrid('editCell', editIxArgs);
							setTimeout(function(){
								opts.endCellEditing.call(target, editIxArgs);
							}, 50);
						}
					}else{
						if(opts.endCellEditing.call(target, editIxArgs)){
							opts.flagEditIxArgs.call(target, index, field, editIxArgs);
							z.datagrid('selectRow', index).datagrid('editCell', editIxArgs);
						}
					}
				}else{
					opts.onClickCell.call(z, index, field, val, row);
				}
			}
		});
		$.extend(mths, {
			/** 开始 编辑单元格, 如: $('#dg').datagrid('editCell', {index: 1, field: 'name'}); */
			editCell: function(jq, param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor_bk = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor_bk;
					}
				});
			},
			/** 结束 编辑单元格, 如: $('#dg').datagrid('endCellEditing');  */
			endCellEditing: function(jq, param){
				return jq.each(function(){
					var target = this;
					var opts = $(target).datagrid('options');
					opts.endCellEditing.call(target, param || opts.editIxArgs);
				});
			}
		});
	}
	
	{
		// TODO 对linkbutton的编辑功能的扩展
		var mths = $.fn.linkbutton.methods;
		$.extend(mths, {
			/**
			 * 切换 标题(并触发函数)
			 * <pre>
			 * $('#btn').on('click', function(){
					$(this).linkbutton('toggleText', ['Add', 'Edit', 'Del']);
				});
				$(this).linkbutton('toggleText', {
					texts: ['Add', 'Edit', 'Del'], 
					fns: [function(text, newText, ix, preIx, opts){
						M.alert(text + ',' + newText);
					},function(text, newText, ix, preIx, opts){
						M.alert(text + ',' + newText);
					},function(text, newText, ix, preIx, opts){
						M.alert(text + ',' + newText);
					}]
				});
			 * </pre>
			 */
			toggleText: function(jq, param){
				if(!param) return ;
				return jq.each(function(){
					var target = this;
					var z = $(target);
					var opts = $.data(target, 'linkbutton').options;
					if(M.isArray(param)){
						var ix = M.inArr(param, opts.text);
						var preIx = (ix+1) % param.length;
						z.find('span.l-btn-text').html(param[preIx]);
						opts.text = param[preIx];
					}else if(param.texts){
						var ts = param.texts;
						var fns = param.fns;
						var ix = M.inArr(ts, opts.text);
						var preIx = (ix+1) % ts.length;
						var sw_t = true;
						var newText = ts[preIx];
						if(fns[ix]) sw_t = fns[ix].call(target, ix, preIx, opts.text, newText, opts);
						if(param.switchText !== false && sw_t !== false){
							z.find('span.l-btn-text').html(newText);
							opts.text = newText;
						}
					}
				});
			}
		});
	}
	
	// TODO 精细化时用的表格编辑组件
	/**1.天气转, {l_opts:左下拉框的参数, r_opts:右下拉框的参数, defText:默认显示值}*/
	/** 使用举例
	 <th data-options="field:'weaName',width:160,align:'right',editor:{type:'weateditor',options:{
		l_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			url:'../datagrid/weatcode.json'
		},
		r_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			url:'../datagrid/weatcode.json',
			required:true
		}
	}},formatter:M.fmts.weateditorFmt">天气</th>
	 */
	$.extend($.fn.datagrid.defaults.editors, {   
		weateditor: {
			init: function(container, options){
				var splitSign = options.splitSign || '_';	//'转'
				var l_opts = options.l_opts;
				var r_opts = options.r_opts;
				var input = $('<div><input id="i_t_l"><span style="padding:0px 5px;">转</span><input id="i_t_r"></div>').appendTo(container);
				input.attr('splitSign', splitSign);
				if(options.valueField){
					input.attr('valueField', options.valueField);
				}
				if(options.textField){
					input.attr('textField', options.textField);
				}
				if(options.syncTv){
					input.attr('syncTv', true);
				}
				
				var dts = (options.defText || '').split(splitSign);
				
				var l_b = input.find('#i_t_l');
				l_b.css('width', l_opts.width || 80);
				l_b.combobox(l_opts);
				l_b.combobox('setValue', dts[0] || '');
				
				var r_b = input.find('#i_t_r');
				r_b.css('width', r_opts.width || 80);
				r_b.combobox(r_opts);
				r_b.combobox('setValue', dts[1] || '');
				
				return input;   
			},   
			getValue: function(target){
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var l_b = tg.find('#i_t_l');	//左下拉
				var r_b = tg.find('#i_t_r');	//右下拉
				
				var l_bv = l_b.combobox('getValue');
				var v = '';
				if(l_bv){
					var l_bt = l_b.combobox('getValue');
					v += l_bt;
				}
				v += splitSign;
				var r_bv = r_b.combobox('getValue');
				if(r_bv){
					var r_bt = r_b.combobox('getValue');
					v += r_bt;
				}
				return v;   
			},   
			setValue: function(target, value){   
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var dts = (value || '').split(splitSign);
				var l_b = tg.find('#i_t_l');
				var r_b = tg.find('#i_t_r');
				
				l_b.combobox('setValue', dts[0] || '');
				r_b.combobox('setValue', dts[1] || '');
			},   
			resize: function(target, width){   
				var input = $(target);   
				if ($.boxModel == true){   
					input.width(width - (input.outerWidth() - input.width()));   
				} else {   
					input.width(width);   
				}   
			},
			getTv: function(target){
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var l_b = tg.find('#i_t_l');	//左下拉
				var r_b = tg.find('#i_t_r');	//右下拉
				
				var l_bv = l_b.combobox('getValue');
				var v = '';
				var t = '';
				if(l_bv){
					var l_bt = l_b.combobox('getText');
					t += l_bt;
					v += l_bv;
				}
				v += splitSign;
				t += splitSign;
				var r_bv = r_b.combobox('getValue');
				if(r_bv){
					var r_bt = r_b.combobox('getText');
					t += r_bt;
					v += r_bv;
				}
				return [v, t]; 
			}
		}
	});
	window['M'] = window['M'] || {};
	M.data = M.data || {};
	M.data.WindDirData = [
		{"code":"0", "name":"静风"},
		{"code":"1", "name":"东北风"},
		{"code":"2", "name":"偏东风"},
		{"code":"3", "name":"东南风"},
		{"code":"4", "name":"偏南风"},
		{"code":"5", "name":"西南风"},
		{"code":"6", "name":"偏西风"},
		{"code":"7", "name":"西北风"},
		{"code":"8", "name":"偏北风"},
		{"code":"9", "name":"旋转"}
	];
	M.data.WindSpeedData = [
		{"code":"0", "name":"2-3级"},
		{"code":"1", "name":"3-4级"},
		{"code":"2", "name":"4-5级"},
		{"code":"3", "name":"5-6级"},
		{"code":"4", "name":"6-7级"},
		{"code":"5", "name":"7-8级"},
		{"code":"6", "name":"8-9级"},
		{"code":"7", "name":"9-10级"},
		{"code":"8", "name":"10-11级"},
		{"code":"9", "name":"11-12级"}
	];

	/**1.风向风速, {l_dir_opts:左-风向下拉框的参数, l_speed_opts:左-风速下拉框的参数, r_dir_opts:右-风向下拉框的参数, r_speed_opts:右-风速下拉框的参数, defText:默认显示值}*/
	/** 使用举例
	<th data-options="field:'wds',width:260,align:'right',editor:{type:'winddirspeededitor',options:{
		l_dir_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			panelHeight: 'auto',
			data: M.data.WindDirData
		},
		l_speed_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			panelHeight: 'auto',
			data: M.data.WindSpeedData
		},
		r_dir_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			panelHeight: 'auto',
			data: M.data.WindDirData
		},
		r_speed_opts:{
			width: 60,
			panelWidth: 120,
			valueField:'code',
			textField:'name',
			panelHeight: 'auto',
			data: M.data.WindSpeedData
		}
	}},formatter:M.fmts.winddirspeededitorFmt">风向风速</th>
	*/
	$.extend($.fn.datagrid.defaults.editors, {   
		winddirspeededitor: {   
			init: function(container, options){
				var splitSign = options.splitSign || '_';	//'转'
				var subSplitSign = options.subSplitSign || ';';	//风向风速 间的分隔符
				var l_dir_opts = options.l_dir_opts;
				var l_speed_opts = options.l_speed_opts;
				var r_dir_opts = options.r_dir_opts;
				var r_speed_opts = options.r_speed_opts;

				var input = $('<div><input id="i_t_l_dir"><input id="i_t_l_speed"><span style="padding:0px 5px;">转</span><input id="i_t_r_dir"><input id="i_t_r_speed"></div>').appendTo(container);
				input.attr('splitSign', splitSign);
				input.attr('subSplitSign', subSplitSign);
				if(options.valueField){
					input.attr('valueField', options.valueField);
				}
				if(options.textField){
					input.attr('textField', options.textField);
				}
				if(options.syncTv){
					input.attr('syncTv', true);
				}
				
				var dts = (options.defText || '').split(splitSign);
				var l_dts = (dts[0] || '').split(subSplitSign);	//左
				var r_dts = (dts[1] || '').split(subSplitSign);	//右
				
				var l_b_dir = input.find('#i_t_l_dir');	//左-风向
				l_b_dir.css('width', l_dir_opts.width || 80);
				l_b_dir.combobox(l_dir_opts);
				l_b_dir.combobox('setValue', l_dts[0] || '');

				var l_b_speed = input.find('#i_t_l_speed');	//左-风速
				l_b_speed.css('width', l_speed_opts.width || 80);
				l_b_speed.combobox(l_speed_opts);
				l_b_speed.combobox('setValue', l_dts[1] || '');
				
				var r_b_dir = input.find('#i_t_r_dir');	//右-风向
				r_b_dir.css('width', r_dir_opts.width || 80);
				r_b_dir.combobox(r_dir_opts);
				r_b_dir.combobox('setValue', r_dts[0] || '');

				var r_b_speed = input.find('#i_t_r_speed');	//右-风速
				r_b_speed.css('width', r_speed_opts.width || 80);
				r_b_speed.combobox(r_speed_opts);
				r_b_speed.combobox('setValue', r_dts[1] || '');
				
				return input;   
			},   
			getValue: function(target){
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var subSplitSign = tg.attr('subSplitSign') || ';';
				var l_b_dir = tg.find('#i_t_l_dir');	//左-风向
				var l_b_speed = tg.find('#i_t_l_speed');	//左-风速
				var r_b_dir = tg.find('#i_t_r_dir');	//右-风向
				var r_b_speed = tg.find('#i_t_r_speed');	//右-风速

				var v = '';
				var l_b_dir_v = l_b_dir.combobox('getValue');	//左-风向
				if(l_b_dir_v){
					var l_b_dir_t = l_b_dir.combobox('getValue');
					v += l_b_dir_t;
					var l_b_speed_v = l_b_speed.combobox('getValue');	//左-风速
					if(l_b_speed_v){
						var l_b_speed_t = l_b_speed.combobox('getValue');
						v += subSplitSign + l_b_speed_t;
					}
				}
				v += splitSign;	//转标志
				var r_b_dir_v = r_b_dir.combobox('getValue');	//右-风向
				if(r_b_dir_v){
					var r_b_dir_t = r_b_dir.combobox('getValue');
					v += r_b_dir_t;
					var r_b_speed_v = r_b_speed.combobox('getValue');	//右-风速
					if(r_b_speed_v){
						var r_b_speed_t = r_b_speed.combobox('getValue');
						v += subSplitSign +  r_b_speed_t;
					}
				}
				return v;
			},   
			setValue: function(target, value){
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var subSplitSign = tg.attr('subSplitSign') || ';';
				var l_b_dir = tg.find('#i_t_l_dir');	//左-风向
				var l_b_speed = tg.find('#i_t_l_speed');	//左-风速
				var r_b_dir = tg.find('#i_t_r_dir');	//右-风向
				var r_b_speed = tg.find('#i_t_r_speed');	//右-风速

				var dts = (value || '').split(splitSign);
				var l_dts = (dts[0] || '').split(subSplitSign);	//左
				var r_dts = (dts[1] || '').split(subSplitSign);	//右

				l_b_dir.combobox('setValue', l_dts[0] || '');
				l_b_speed.combobox('setValue', l_dts[1] || '');
				r_b_dir.combobox('setValue', r_dts[0] || '');
				r_b_speed.combobox('setValue', r_dts[1] || '');
			},   
			resize: function(target, width){   
				var input = $(target);   
				if ($.boxModel == true){   
					input.width(width - (input.outerWidth() - input.width()));   
				} else {   
					input.width(width);   
				}   
			},
			getTv: function(target){
				var tg = $(target);
				var splitSign = tg.attr('splitSign') || '_';
				var subSplitSign = tg.attr('subSplitSign') || ';';
				var l_b_dir = tg.find('#i_t_l_dir');	//左-风向
				var l_b_speed = tg.find('#i_t_l_speed');	//左-风速
				var r_b_dir = tg.find('#i_t_r_dir');	//右-风向
				var r_b_speed = tg.find('#i_t_r_speed');	//右-风速

				var v = '';
				var t = '';
				var l_b_dir_v = l_b_dir.combobox('getValue');	//左-风向
				if(l_b_dir_v){
					var l_b_dir_t = l_b_dir.combobox('getText');
					v += l_b_dir_v;
					t += l_b_dir_t;
					var l_b_speed_v = l_b_speed.combobox('getValue');	//左-风速
					if(l_b_speed_v){
						var l_b_speed_t = l_b_speed.combobox('getText');
						v += subSplitSign + l_b_speed_v;
						t += subSplitSign + l_b_speed_t;
					}
				}
				v += splitSign;	//转标志
				t += splitSign;	//转标志
				var r_b_dir_v = r_b_dir.combobox('getValue');	//右-风向
				if(r_b_dir_v){
					var r_b_dir_t = r_b_dir.combobox('getText');
					v += r_b_dir_v;
					t += r_b_dir_t;
					var r_b_speed_v = r_b_speed.combobox('getValue');	//右-风速
					if(r_b_speed_v){
						var r_b_speed_t = r_b_speed.combobox('getText');
						v += subSplitSign +  r_b_speed_v;
						t += subSplitSign +  r_b_speed_t;
					}
				}
				return [v, t];
			}
		}   
	});
	/*  举例:
		 高温:
		 editor:{type:'maxmintempeditor',options:{isMinCtrl: false, l_opts:{precision:1}, r_opts:{precision:1}}}, formatter:M.fmts.maxtempeditorFmt
		 低温:
		 editor:{type:'maxmintempeditor',options:{isMinCtrl: true, l_opts:{precision:1}, r_opts:{precision:1}}}, formatter:M.fmts.mintempeditorFmt
	 */
	//高低温度
	$.extend($.fn.datagrid.defaults.editors, {   
		maxmintempeditor: {   
			init: function(container, options){
				var opts = options;
				var l_opts = opts.l_opts || {};
				var r_opts = opts.r_opts || {};
				var input = 0;
				var isMm = opts.isMm !== false;
				if(isMm){
					if(opts.isMinCtrl){
						input = $('<div><input id="i_t_l"><span style="padding:0px 5px;">_</span><input id="i_t_r"></div>').appendTo(container);
						input.attr('isMinCtrl', 1);
					}else{
						input = $('<div><input id="i_t_l"><span style="padding:0px 5px;">_</span><input id="i_t_r"></div>').appendTo(container);
						input.attr('isMinCtrl', 0);
					}
					input.attr('isMm', 1);
					var dts = ((opts.defText || '') + '').split('_');
					var l_b = input.find('#i_t_l');
					l_b.css('width', l_opts.width || 40);
					l_b.numberbox(l_opts);
					l_b.numberbox('setValue', dts[0] || '');
					
					var r_b = input.find('#i_t_r');
					r_b.css('width', r_opts.width || 40);
					r_b.numberbox(r_opts);
					r_b.numberbox('setValue', dts[1] || '');
				}else{
					input = $('<input id="i_t_l">').appendTo(container);
					input.numberbox(opts.l_opts || opts);
					input.numberbox('setValue', (opts.defText || ''));
				}
				
				return input;   
			},   
			getValue: function(target){
				var tg = $(target);
				var l_b = tg.find('#i_t_l');	//左下拉
				var r_b = tg.find('#i_t_r');	//右下拉
				var isMm = tg.attr('isMm');
				var isMinCtrl = 0;
				var v = '';
				if(isMm == '1'){
					var l_bv = l_b.numberbox('getValue');
					var r_bv = r_b.numberbox('getValue');
					return l_bv + '_' + r_bv;
				}else{
					v = tg.numberbox('getValue');
				}
				return v; 
			},   
			setValue: function(target, value){   
				var tg = $(target);
				var isMm = tg.attr('isMm');
				if(isMm){
					var dts = ((value || '') + '').split('_');
					var l_b = tg.find('#i_t_l');
					var r_b = tg.find('#i_t_r');
					
					l_b.numberbox('setValue', dts[0] || '');
					r_b.numberbox('setValue', dts[1] || '');
				}else{
					//var l_b = tg.find('#i_t_l');
					tg.numberbox('setValue', value);
				}
			},   
			resize: function(target, width){   
				var input = $(target);   
				if ($.boxModel == true){   
					input.width(width - (input.outerWidth() - input.innerWidth()));   
				} else {   
					input.width(width - 4);   
				}   
			}   
		}   
	});
	M.fmts = M.fmts || {};
	M.fmts.weateditorFmt = function(val, r, ix){
		var col = this;
		var v ;
		if(col.editor){
			var c_opts = col.editor.options;
			if(c_opts && c_opts.textField){
				v = r[c_opts.textField];
			}
		}
		v = v || r.weaName;
		if(!v) return '';

		if(v.charAt(0) == '_'){				//如:'_晴'、'_'
			v = v.replace('_', '');
		}else if(v.charAt(v.length-1) == '_'){	//如:'晴_'
			v = v.replace('_', '');
		}else if(v.indexOf('_') < 0){	//如:'晴'
			return v;
		}else{							//如:'晴_多云'
			var vs = v.split('_');		//v = v.replace('_', '转');
			v = vs[0] == vs[1] ? vs[0] : (vs[0] + '转' + vs[1]);
		}
		return v;
	};
	M.fmts.winddirspeededitorFmt = function(val, r, ix){
		var col = this;
		var v ;
		if(col.editor){
			var c_opts = col.editor.options;
			if(c_opts && c_opts.textField){
				v = r[c_opts.textField];
			}
		}
		v = v || r.windsCode;
		if(!v) return '';
		
		if(v.charAt(0) == '_'){
			v = v.replace('_', '');
		}else if(v.charAt(v.length-1) == '_'){
			v = v.replace('_', '');
		}else if(v.indexOf('_') < 0){
			
		}else{
			var vs = v.split('_');		//v = v.replace('_', '转');
			v = vs[0] == vs[1] ? vs[0] : (vs[0] + '转' + vs[1]);
		}
		v = v.replace(/;/g, '');	//去掉风向风速间的分隔
		return v;
	};
	M.fmts.maxtempeditorFmt = function(v, r, ix){
		if(v && M.isString(v)){
			var col = this;
			if(col.editor && col.editor.options){
				var copts = col.editor.options;
				if(copts.isMm === false){
					return v;
				}
			}
			var v = v.split('_');
			var l_v = v[0] ? parseFloat(v[0]) : '';
			var r_v = v[1] ? parseFloat(v[1]) : '';
			var lm = false;
			if(l_v === '' || r_v == ''){
				lm = l_v === '' ? false : true ;
			}else{
				lm = l_v > r_v;
			}
			l_v = M.isNumber(l_v) ? l_v.toFixed(1) : l_v;
			r_v = M.isNumber(r_v) ? r_v.toFixed(1) : r_v;
			var hs = '<span style=\'display:none\'>';
			return [lm ? '<span>' : hs, l_v, '</span>', lm ? hs : '<span>', r_v, '</span>'].join('');
		}
		return '';
	};
	M.fmts.mintempeditorFmt = function(v, r, ix){
		if(v && M.isString(v)){
			var col = this;
			if(col.editor && col.editor.options){
				var copts = col.editor.options;
				if(copts.isMm === false){
					return v;
				}
			}
			var v = v.split('_');
			var l_v = v[0] ? parseFloat(v[0]) : '';
			var r_v = v[1] ? parseFloat(v[1]) : '';
			var lm = false;
			if(l_v === '' || r_v == ''){
				lm = l_v === '' ? false : true ;
			}else{
				lm = l_v < r_v;
			}
			l_v = M.isNumber(l_v) ? l_v.toFixed(1) : l_v;
			r_v = M.isNumber(r_v) ? r_v.toFixed(1) : r_v;
			var hs = '<span style=\'display:none\'>';
			return [lm ? '<span>' : hs, l_v, '</span>', lm ? hs : '<span>', r_v, '</span>'].join('');
		}
		return '';
	}

	// TODO datagrid编辑器工具类
	M.editors = M.editors || {};
	//同步字段, 举例: 在endEditing()函数中， M.editors.syncTv('#dg', editIndex, 'weatCode');
	M.editors.syncTv = function(dg0, editIndex, fields){
		if(!fields) return ;
		var dgs = $(dg0);
		dgs.each(function(){
			var dg = $(this);
			var opts = $.data(dg[0], "datagrid").options;
			var aetyps = opts.syncEditType;		//可同步的编辑类型
			if(!M.isArray(fields)) fields = [fields];
			for(var i = 0; i < fields.length; i++){
				var field = fields[i];
				var col = dg.datagrid('getColumnOption', field);
				if(col.editor){
					var etype = col.editor.type;
					if(etype && M.inArr(aetyps, etype) != -1){
						if(etype == 'combobox'){
							var stf = col.editor.syncTextField;
							if(stf){
								var ed = dg.datagrid('getEditor', {index: editIndex, field: field});
								var txt = $(ed.target).combobox('getText');
								dg.datagrid('getRows')[editIndex][stf] = txt;
							}
						}else{
							// 如果编辑器在可同步的编辑类型中, 将其同步
							var ed = dg.datagrid('getEditor', {index: editIndex, field: field});
							var edTg = ed.target;
							var sync = edTg.attr('syncTv');
							if(sync){
								var vf = edTg.attr('valueField');
								var tf = edTg.attr('textField');
								var cn = ed.actions.getTv(edTg);
								var r = dg.datagrid('getRows')[editIndex];
								r[tf] = cn[1];
							}
						}
					}
				}
			}
		});
	};
	
	//提供一些控制用的参数
	M.opts = M.opts || {};
	M.opts.distDirMenuOptions = {
		options:{
			width: 120,
			onClick: function(item, itemOrg){
				var list = $(this.ptCtrlEl);	//获取 listbox列表
				var li = $(this.ptEl);	//获取 listbox列表项对象
				if(itemOrg.mtype == 1){
					list.listmenu('select', li.val());
					
					var dir = item.name;
					var c = li.html();
					var ix = c.indexOf('(');
					if(ix > -1){
						c = c.substring(0, ix);
					}
					if(dir) c += "(" + dir + ")";
					li.html(c);
					li.attr('dir', dir);
				}else if(itemOrg.mtype == 2){
					list.listmenu('unselect', li.val());
					
					var c = li.html();
					var ix = c.indexOf('(');
					if(ix > -1){
						c = c.substring(0, ix);
					}
					li.html(c);
					li.attr('dir', '');
				}
			},
			data:[{
				mtype: 1,
				name:'东部',
				text:'东部',
				iconCls:'icon-edit'
			},{
				mtype: 1,
				name:'西部',
				text:'西部'
			},{
				mtype: 1,
				name:'南部',
				text:'南部'
			},{
				mtype: 1,
				name:'北部',
				text:'北部'
			},{
				mtype: 1,
				name:'',
				text:'选择',
				iconCls:'icon-ok'
			},{
				mtype: 2,
				name:'取消选择',
				text:'取消选择',
				iconCls:'icon-cancel'
			}]
		}
	};
	
	// TODO 类似百度搜索框, 为预报制作提供用户查询(只查询当前用户所在的预报中心)
	(function($){
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
		
		$.fn.curForeUserIndexbox = function(options, param){
			if (typeof options == 'string'){
				return this.foreUserIndexbox(options, param);
			}
			return this.each(function(){
				var box = $(this);
				var opts =  $.extend({
					valueField: 'pkId', 
					textField: 'name', 
					mode: 'remote', 
					width: 150, 
					panelWidth: 150, 
					panelHeight: 'auto',
					isMustExist: true,	//true值必须存在于列表中
					isHideArraw: false,
					url: M.buildUrl('ajax_gensys_UserInfo_findLikeNameForForeJson.action?limit=-1')
				}, $.fn.combobox.parseOptions(this), options || {});
				
				opts.formatter = opts.formatter || formatter;
	
				box.foreUserIndexbox(opts);
			});
		};
		
		$.parser.plugins.push('curForeUserIndexbox');	//加入到插件库
	})(jQuery);
	
	// TODO 类似百度搜索框, 为预报制作提供用户查询(只查询当前用户所在的预报中心排到班的人)
	(function($){
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
		
		$.fn.curMakeForeUserIndexbox = function(options, param){
			if (typeof options == 'string'){
				return this.foreUserIndexbox(options, param);
			}
			return this.each(function(){
				var box = $(this);
				options = options || {};
				// 过滤省级短时、短期、市县排班
				var pstr = '';
				if(!options.ftype && options.ftype !== 0){
					var pms = M.paramUrl();
					if(pms && pms.foreTypeCode){
						var fc = pms.foreTypeCode; //'p_shortTime_'、'p_shortTime_'
						//省级短时、短期
						if(fc.length > 10){
							fc = fc.substr(0, 11);
							if(fc == 'p_shortTime'){
								options.ftype = '1';
							}else if(fc == 'p_shortDate'){
								options.ftype = '2';
							}
						}
						// 市、县
						if(fc.length > 1){
							fc = fc.substr(0, 2);
							if(fc == 'c_' || fc == 'x_'){
								options.ftype = '0';
							}
						}
					}
				}
				pstr += '&ftype=' + (options.ftype || '') + '&flvl=' + (options.flvl || '');
				var opts =  $.extend({
					valueField: 'pkId', 
					textField: 'name', 
					mode: 'remote', 
					width: 150, 
					panelWidth: 150, 
					panelHeight: 'auto',
					isMustExist: true,	//true值必须存在于列表中
					isHideArraw: false,
					editable:false,
					url: M.buildUrl('ajax_gensys_UserInfo_findLikeNameForMakeForeJson.action?limit=-1' + pstr)
				}, $.fn.combobox.parseOptions(this), options || {});
				
				opts.formatter = opts.formatter || formatter;
	
				box.foreUserIndexbox(opts);
			});
		};
		
		$.parser.plugins.push('curMakeForeUserIndexbox');	//加入到插件库
	})(jQuery);
	
	// TODO 日期(按钮形式)
	(function($){
		$.fn.dateboxBtn = function(options, param){
			if (typeof options == 'string'){
				return this.datebox(options, param);
			}
			return this.each(function(){
				var box = $(this);
				var opts =  $.extend({
					width: 16
				}, options || {});
	
				box.datebox(opts);
				
				var tb = box.datebox('textbox');
				tb.css({padding:0, margin:0, width:0});
				
				var tpnl = tb.parent();
				tpnl.css({width: 16, height: 16});
				tpnl.css({padding:0, margin:0, border: 'none'});
				
				var icon = tpnl.find('.combo-arrow');
				icon.css({width: 16, height: 16});
			});
		};
		
		$.parser.plugins.push('dateboxBtn');	//加入到插件库
	})(jQuery);

	//------- 初始化 内部
	M.__init();
	$(M.timer.afterLoadFire);
	M.timer.fire();
	
})();

(function(){
	M.getUrlParam = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		return r ? decodeURI(r[2]) : null;
	};
	M.log = function(s){
		console.log(s);
	};
})();

// TODO 2015-09-04
(function(){
	$.extend($.fn.validatebox.defaults.rules, {   
	    mobile: {   
	        validator: function(value, param){   
	            return /^(1[34578][0-9])\d{8}$/.test(value);   
	        },   
	        message: '手机号不正确.'
	    },   
	    tel: {   
	    	validator: function(value, param){   
	    		return /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test(value);   
	    	},   
	    	message: '座机号不正确.'
	    },
	    phone: {
	    	validator: function(value, param){   
	    		return /^(1[34578][0-9])\d{8}$|^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test(value);   
	    	},   
	    	message: '电话号码不正确.'
	    },
	    len: {   
	    	validator: function(value, param){
	    		var len = $.trim(value).length;
	    		return len == param[0];
	    	},   
	    	message: '必须为{0}个字符.'
	    }, 
	    username: {   
	    	validator: function(value, param){
	    		var len = $.trim(value).length;
	    		var reg = new RegExp("^[a-zA-z][a-zA-Z0-9_]{" + (param[0]-1) + "," + (param[1]-1) + "}$");
	    		return len >= param[0] && len <= param[1] && reg.test(value);
	    	},   
	    	message: '字母开头,{0}-{1}位英文字母,数字,下划线组成.'
	    }, 
	    name: {   
	    	validator: function(value, param){
	    		var len = $.trim(value).length;
	    		if(len >= param[0] && len <= param[1]){
	    			var reg = new RegExp("^[a-zA-Z0-9_]{" + (param[0]) + "," + (param[1]) + "}$");
		    		return reg.test(value);
	    		}
	    		return false;
	    	},   
	    	message: '字母开头,{0}-{1}位英文字母,数字,下划线组成.'
	    },
	    /** 下拉框内容不能为空和0 */
	    comboEmp:{
	    	validator: function(value){
	    		value = $.trim(value);
	    		if(value){
	    			var z = $(this);
	    			var cb = z.parent('.combo').prev('.combo-f');
		    		var prompt = cb.attr('prompt');
		    		return value != prompt;
	    		}
	    		return false;
	    	},   
	    	message: '请选择一项.'
	    },
	    /** 验证码 */
	    captcha:{
	    	validator: function(value, param){
	    		var len = $.trim(value).length;
	    		if(len == param[0]){
	    			var reg = new RegExp("^[a-zA-Z0-9]{" + (param[0]) + "}$");
		    		return reg.test(value);
	    		}
	    		return false;
	    	},   
	    	message: '验证码由{0}位字符和数字组成.'
	    }
	});
	
	// TODO 对easyui的原始方法的改动
	M.apply($.fn.datagrid.methods, {
		/** 获取当前check的记录, 如果没有, 返回select的记录 */
		getCks:function(jq){
			jq = $(jq[0]);
			var cks = jq.datagrid('getChecked');
			if(cks && cks.length){
				return cks;
			}else{
				var sels = jq.datagrid('getSelections');
				return sels;
			}
		},
		/** 获取当前check的记录, 如果没有, 返回select的记录(第1条) */
		getCk:function(jq){
			jq = $(jq[0]);
			var cks = jq.datagrid('getChecked');
			if(cks && cks.length){
				return cks[0];
			}else{
				var sel = jq.datagrid('getSelected');
				return sel;
			}
		},
		/** 获取当前check的记录, 如果没有, 返回select的记录(第1条) */
		getCkOne:function(jq){
			jq = $(jq[0]);
			var cks = jq.datagrid('getChecked');
			if(cks && cks.length){
				return cks[0];
			}
		},
		/** 获取当前check的记录数 */
		getCkCount:function(jq){
			jq = $(jq[0]);
			var cks = jq.datagrid('getChecked');
			return cks && cks.length ? cks.length : 0;
		},
		/** 获取当前select的记录, 如果没有, 返回check的记录 */
		getSels:function(jq){
			jq = $(jq[0]);
			var sels = jq.datagrid('getSelections');
			if(sels && cks.length){
				return sels;
			}else{
				var cks = jq.datagrid('getChecked');
				return cks;
			}
		},
		/** 获取当前select的记录, 如果没有, 返回check的记录(第1条) */
		getSel:function(jq){
			jq = $(jq[0]);
			var sel = jq.datagrid('getSelected');
			if(sel){
				return sel;
			}else{
				var cks = jq.datagrid('getChecked');
				if(cks && cks.length){
					return cks[0];
				}
			}
		},
		/** 获取查询参数, ps对参数覆盖 */
		getQueryParams:function(jq, ps){
			jq = $(jq[0]);
			var opts = $.data(jq[0], "datagrid").options;
			var qps = $.extend({}, opts.queryParams);
			if(opts.pagination){
				$.extend(qps,{page:opts.pageNumber, limit:opts.pageSize});
			}
			if(opts.sortName){
				$.extend(qps, {sort:opts.sortName, order:opts.sortOrder});
			}
			$.extend(qps, ps);
			return qps;
		}
	});
	
	
	// TODO 对easyui的工具方法
	M.eu = {
		input_invalid: function(ipt, msg){
			ipt = $(ipt);
			ipt.addClass("validatebox-invalid");
			//m_eu_input_tip(ipt, msg);
			//$('.tooltip').html(msg);
			M.err(msg);
		},
		build_del_eg: function(ar, key, limit){
			var str = '';
			if(ar && ar.length){
				limit = limit || 10;
				if(!key || ar.length > limit){
					str = '这' + limit + '条记录';
				}else{
					var as = [];
					for(var i = 0; i < ar.length; i++){
						as.push(ar[i][key]);
					}
					str = '【' + as.join(', ') + '】';
				}
			}
			return str;
		},
		build_fields: function(ar, key){
			var str = '';
			if(ar && ar.length){
				var as = [];
				for(var i = 0; i < ar.length; i++){
					as.push(ar[i][key]);
				}
				str = as.join();
			}
			return str;
		},
		/**
		 *  datagrid 删除
		 * @param dg 
		 * @param idn (String) id字段名
		 * @param sn (String) 删除提示时的字段名
		 * @param fn (function) 确认删除时被调用
		 * @param filter (function) 过滤数据函数
		 * @see <code>
		 * 	M.eu.dg_del(user_dg, 'id', 'userName', function(ids, sels){
				M.alert(ids);
			});
		 * </code>
		 */
		dg_del: function(dg, idn, sn, fn, filter){
			//var sels = dg.datagrid('getCks');
			var sels = dg.datagrid('getChecked');
			if(sels && sels.length){
				filter && (sels = filter(sels));
				if(sels && sels.length){
					var dstr = M.eu.build_del_eg(sels, sn, 10);
					$.messager.confirm('删除','您确定要删除' + dstr + '?', function(r){   
					    if (r){
					    	var ids = M.eu.build_fields(sels, idn);
					    	fn.call(dg, ids, sels);
					    }   
					});
					return ;
				}
			}
			M.alert('请先勾选一条记录!');
		},
		/**
		 *  datagrid 批量操作
		 * @param dg 
		 * @param idn (String) id字段名
		 * @param sn (String) 禁用提示时的字段名
		 * @param title (String) 禁用/启用
		 * @param fn (function) 确认禁用时被调用
		 * @see <code>
		 * 	M.eu.dg_dis(user_dg, 'id', 'userName', function(ids, sels){
				M.alert(ids);
			});
		 * </code>
		 */
		dg_ids_opts: function(dg, idn, sn, title, fn, filter){
			//var sels = dg.datagrid('getCks');
			var sels = dg.datagrid('getChecked');
			filter && (sels = filter(sels));
			var dstr = M.eu.build_del_eg(sels, sn, 10);
			if(dstr){
				title = title || '操作';
				$.messager.confirm(title,'您确定要' + title + dstr + '?', function(r){   
				    if (r){
				    	var ids = M.eu.build_fields(sels, idn);
				    	fn.call(dg, ids, sels);
				    }   
				});
			}else{
				M.alert('请先勾选一条记录!');
			}
		},
		dg_ck_one: function(dg, fn){
			var cks = dg.datagrid('getChecked');
			if(cks && cks.length){
				if(cks.length > 1){
					M.alert('只能勾选一条记录!');
				}else{
					fn(cks[0]);
				}
			}else{
				M.alert('请先勾选一条记录!');
			}
		}
	};
	
	
})();

