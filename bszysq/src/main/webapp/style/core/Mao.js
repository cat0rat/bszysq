/** Mao作为我的Js库前导, 是对Js纯净支持.  
 * M作具体环境下某一种js库的再扩充，如：Ext、JQuery
 */

/** Mao和M 全局对象
 */
Mao = {
	__createTime:"2011-10-12",
	__finalTime:"2012-06-04",
	__author:"Mao",
	__version:"3.1.0"
};

(function(){
	/** 对象元素复制 **/
	Mao.apply = function(o, c, x){
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
	//如果 M已存在, 备份到 Mao._OldM中, 如果 M.__rootPath存在, 复制 M中的数据到 Mao
	if(window["M"]){
		Mao._OldM = M;
		if(M.__rootPath){
			Mao.apply(Mao, M);
		}
	}
	//定义 M全局变量
	window["M"] = Mao;
	
	/** js 基本方法 */
	M.apply(M, {
		/*----------常量----------*/
		_r:"\n",
		/*----------基本方法----------*/
		/** 通过 name 属性查找 标签 */
		getName: function(n){
			return document.getElementsByName(n);
		},
		/** 通过 id 属性查找 标签 */
		getId: function(id){
			return document.getElementById(id);
		},
		/** 先 id, 后 name 属性查找 标签 */
		get: function(x){
			var id = x;
			if(typeof(x) === "string"){
				id = M.getId(x);
				if(!id){
					id = M.getName(x);
					if(id){
						id = id[0];
					}
				}
			}
			return id;
		},
		/** 遍历 数组 */
		each : function(array, fn, scope){
            if(M.isEmpty(array, true)){
                return;
            }
            if(!M.isIterable(array) || M.isPrimitive(array)){
                array = [array];
            }
            for(var i = 0, len = array.length; i < len; i++){
                if(fn.call(scope || array[i], array[i], i, array) === false){
                    return i;
                };
            }
        },
        /** 遍历 数组或对象 */
        iterate : function(obj, fn, scope){
            if(M.isEmpty(obj)){
                return;
            }
            if(M.isIterable(obj)){
                M.each(obj, fn, scope);
                return;
            }else if(typeof obj == 'object'){
                for(var prop in obj){
                    if(obj.hasOwnProperty(prop)){
                        if(fn.call(scope || obj, prop, obj[prop], obj) === false){
                            return;
                        };
                    }
                }
            }
        },
        /** 定义命名空间 */
        namespace : function(){
            var o, d;
            M.each(arguments, function(v) {
                d = v.split(".");
                o = window[d[0]] = window[d[0]] || {};
                M.each(d.slice(1), function(v2){
                    o = o[v2] = o[v2] || {};
                });
            });
            return o;
        },
        /** 补充某对象 如: r.major.college是否是 {}, 如果不是制为 {}
         */
        fillNs: function(obj, ns){
			ns = ns.split(".");
			var o = obj,
				n;
			for(var i = 0; i < ns.length; i++){
				n = ns[i];
				if(!M.isObject(o[n])){
					o[n] = {};
				}
				o = o[n];
			}
		},
		/*----------路径方法----------*/
		/** 获取根据路径
		 * @param url (String) 如果 定义了 url, 将其追加到 根路径上
		 */
		getRootPath:function(url){
			return url ? M.__rootPath + url : M.__rootPath;
		},
		/** 将 url接到根路径上, buildUrl的轻量级方法
		 * @已过时
		 * @see M.getRootPath(url)
		 */
		rootUrl:function(url){
			return M.getRootPath(url);
		},
		/**生成统一的从根路径开始的 Url
		 * @param url (String) url路径, 如: "listUser.do"
		 * @param ns (String) 命名空间, 如: "info/"
		 */
		buildUrl:function(url, ns){
			if(url){
				// 4:"http://...", 	5:"https://...", 3:"ftp://..."
				if(url.length > 6 && 
					(url.charAt(4) === ':' || url.charAt(5) === ':' || url.charAt(3) === ':')
					){
					return url;
				}
				if(ns) url = ns + url;
				var rl = 0,
					cp = M.getRootPath();
				if(cp && (rl = cp.length) > 0){
					if(url.substr(0, rl) === cp){
						return url;
					}
					return cp + url;
				}
			}
			return M.getRootPath(url);
		},
		/** 参数化Url, Url中的参数转发成数据对象 */
		paramUrl: function(url, retAnchor){
			url = url || window.location.href;
			var ix = url.indexOf('?');
			var jx = url.lastIndexOf('#');
			var anchor;
			if(jx == -1){
				jx = url.length;
			}else if(jx+2 < url.length){
				anchor = url.subtr(jx+1);
			}
			if(ix != -1){
				url = url.substring(ix+1, jx);
			}
			if(!url) return ;
			
			var us = url.split('&');
			if(us && us.length){
				var data = {};
				for(var i = 0; i < us.length; i++){
					var kv = us[i].split('=');
					kv[0] = decodeURIComponent(kv[0]);
					kv[1] = kv[1] ? decodeURIComponent(kv[1]) : kv[1];
					data[kv[0]] = kv[1];
				}
			}
			if(retAnchor){
				return [data, anchor]
			}else{
				return data;
			}
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
		/** 返回顶层window对象, 若没有父窗口, 返回0 */
		desktopWindow:function(){
			var pw = 0;
			var pwN = window;
			do{
				pwN = pwN.parent != pwN ? pwN.parent : 0;
				if(pwN) pw = pwN;
			}while(pwN);
			return pw;
		},
		/*----------对象方法----------*/
		/** 存在不复制
		 */
		applyIf: function(o, c, x){
		    if(x){
		        M.applyIf(o, x);
		    }
		    if(o && c && typeof c == 'object'){
		        for(var p in c){
		        	if(o[p] === undefined){
		            	o[p] = c[p];
		        	}
		        }
		    }
		    return o;
		},
		/** 深度拷贝
		 */
		applyX: function(o, c, x){
		    if(x){
		        M.applyX(o, x);
		    }
		    if(o && c && typeof c == 'object'){
		        for(var p in c){
		        	if(M.isObject(c[p])){
		        		if(!M.isObject(o[p])){
		        			o[p] = {};
		        		}
	        			M.applyX(o[p], c[p]);
		        	}else if(M.isArray(c[p])){
		        		if(!M.isArray(o[p])){
		        			o[p] = [];
		        		}
		        		for(var i = 0; i < c[p].length; i++){
	        				M.applyX(o[p][i], c[p][i]);
		        		}
		        	}else{
		            	o[p] = c[p];
		        	}
		        }
		    }
		    return o;
		},
		/** 一层深度拷贝
		 */
		applyY: function(o, c, x){
		    if(x){
		        M.applyY(o, x);
		    }
		    if(o && c && typeof c == 'object'){
		        for(var p in c){
		        	if(M.isObject(c[p])){
	        			o[p] = M.apply({}, c[p]);
		        	}else if(M.isArray(c[p])){
	        			o[p] = c[p].slice(0);
		        	}else{
		            	o[p] = c[p];
		        	}
		        }
		    }
		    return o;
		},
		/** 两个对象复制时，两边子又同时为对象时进行覆盖
		 * @see M.copeX({a:{b:"b", c:"c"}}, {a:{b:"B", x:"X"}) -->{a:{b:"B", c:"c", x:"X"}}
		 */
		copeX:function(o, s, d){
			if(d) M.copeX(o, d);
			if(o && s && typeof s == 'object'){
				for(var k in s){
					if(o[k] instanceof Object && s[k] instanceof Object){
						M.copeX(o[k], s[k]);
					}else{
						o[k] = s[k];
					}
				}
			}
			return o;
		},
		/** 如果不是 Object 则转成 {} 
		 * @param obj (Object)
		 * @param trans (boolean) 如果 obj不是 Object类型是转换为{obj:true},默认 false返回{};
		 */
		toObject:function(obj, trans){
			if(obj instanceof Object) return obj;
			if(trans){
				trans = {};
				trans[obj] = true;
				return trans;
			}
			return {};
		},
		/** 转成数组<br>
		 * 如果不存在返回 []<br>
		 * 如果是数组返回原值<br>
		 * 其他返回 [arr] 
		 * @param isNew (String) 如果是数组, true: 复制一份; false(默认): 直接返回原数组
		 */
		toArray:function(arr, isNew){
			if(!arr) return [];
			if(arr instanceof Array) return isNew ? arr.slice(0) : arr;
			return [arr];
		},
		/*----------Cookie----------*/
		/**设置 Cookie
		 * @param key (String) 键名
		 * @param value (String) 值
		 * @param date (Date) 过期时间
		 * @param path (String) 存放到的位置
		 * @param domain
		 * @param secure
		 */
		setCookie:function (key, value, date, path, domain, secure) {
			document.cookie = key + "=" + escape(value)
				+ ((date) ? "; expires=" + date.toGMTString() : "")
				+ ((path) ? "; path=" + path : "") + ((domain) ? "; domain=" + domain : "")
				+ ((secure) ? "; secure" : "");
		},
		/** 获取 Cookie
		 * @param key (String) 键名
		 */
		getCookie:function(key) {
			var d = key + "=";
			var e = document.cookie.indexOf(d);	//在cookie中的位置
			if (e == -1) {
				return null;
			}
			var a = document.cookie.indexOf(";", e + d.length);	//从 = 号的位置开始找第一个分号,即如:mao=haozi;
			if (a == -1) {
				a = document.cookie.length;
			}
			var c = document.cookie.substring(e + d.length, a);
			return unescape(c);
		},
		/** 删除 Cookie
		 * @param key (String) 键名
		 * @param path (String) 存放到的位置
		 * @param domain
		 */
		deleteCookie:function(key, path, domain) {
			if (M.getCookie(key)) {
				document.cookie = key + "=" + ((path) ? "; path=" + path : "")
						+ ((domain) ? "; domain=" + domain : "")
						+ "; expires=Thu, 01-Jan-70 00:00:01 GMT";
			}
		},
		/*----------计算(Math)----------*/
		/** 返回一个随机数, 产生的伪随机数介于 0 和 1 之间(含 0，不含 1)
		 */
		random:function(){
			return Math.random();
		},
		/** 返回一个随机数, 产生的伪随机数介于 x和 y 之间(含 x，不含 y)
		 */
		randomi:function(x, y){
			
		},
		/** 返回一个16位长度的随机数字符串
		 */
		randoms16:function(){
			var s = (M.random() + "").substr(2);
			s += s;
			return s.substr(0, 16);
		},
		/** 返回一个指定长度的随机数字符串
		 */
		randoms:function(len){
			var n = M.randoms16();
			if(len){
				if(len > 16){
					while(n.length < len){
						n += M.randoms16();
					}
				}
				return n.substr(0, len);
			}else{
				return n;
			}
		},
		/** 取两者最小值 */
		min:function(a, b){
			return a > b ? b : a;
		},
		/** 取两者最大值 */
		max:function(a, b){
			return a > b ? a : b;
		},
		/** 取所有数中最小值 */
		minEx: function(){
			var arg = arguments,
				r = 0,
				arg0 = arg[0];
			if(M.isArray(arg0)){
				arg = arg0;
				arg0 = arg[0];
			}
			switch(arg.length){
				case 0:
					break;
				case 1:
					r = arg0;
					break;
				case 2:
					r = M.min(arg0, arg[1]);
					break;
				default:
					var i = 1,
						l = arg.length;
					r = arg0;
					for(; i < l; i++){
						r = r < arg[i] ? r : arg[i];
					}
					break;
			}
			return r;
		},
		/** 取所有数中最大值 */
		maxEx: function(){
			var arg = arguments,
				r = 0,
				arg0 = arg[0];
			if(M.isArray(arg0)){
				arg = arg0;
				arg0 = arg[0];
			}
			switch(arg.length){
				case 0:
					break;
				case 1:
					r = arg0;
					break;
				case 2:
					r = M.max(arg0, arg[1]);
					break;
				default:
					var i = 1,
						l = arg.length;
					r = arg0;
					for(; i < l; i++){
						r = r > arg[i] ? r : arg[i];
					}
					break;
			}
			return r;
		},
		/**控制图片显示的大小 (其他标签可能也行)
		 * 使用方法 如:  >img src="mao.jpg" load="M.displaySize(this, 100, 50)" /><br>
		 * 将控制图片如大小宽>100则设置其宽为100,小于100则原宽,高同理
		 */
		displaySize : function(tg, w, h){
			if(tg){
				if(w && tg.width > w){
					tg.width = w;
				}
				if(h && tg.height > h){
					tg.height = h;
				}
			}
		},
		/*----------类型判断----------*/
		toStringFn: Object.prototype.toString,
		/** 获取 变量的类型名, 如:var a = {}; 的类型名为: [object Object] */
		getTypeName: function(v){
			return M.toStringFn.apply(v);
		},
		/** 为 null、undefined、[]、'' 时返回 true;
		 * @param allowBlank (boolean) true: 去掉 ''的检测
		 */
		isEmpty: function(v, allowBlank){
	        return v === null || v === undefined || ((M.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
	    },
		isArray : function(v){
			return M.getTypeName(v) === '[object Array]';
		},
		isDate : function(v){
	        return M.getTypeName(v) === '[object Date]';
	    },
		isObject: function(v){
			return !!v && M.getTypeName(v) === '[object Object]';
		},
		isAnyObject: function(v){
			return typeof v === 'object';
		},
		/** javascript类型 基本类型: string、 number、 boolean
		 */
		isPrimitive : function(v){
	        return M.isString(v) || M.isNumber(v) || M.isBoolean(v);
	    },
		isFunction: function(v){
			return M.getTypeName(v) === '[object Function]';
		},
	    isNumber : function(v){
	        return typeof v === 'number';
	    },
		isString: function(v){
	        return typeof v === 'string';
	    },
	    isBoolean : function(v){
	        return typeof v === 'boolean';
	    },
	    /** HTMLElement
	     */
	    isElement : function(v) {
	        return v ? !!v.tagName : false;
	    },
	    isDefined : function(v){
	        return typeof v !== 'undefined';
	    },
		/** 判断一个变量是否已赋值 */
		isDefine:function(x){
			if(x || x === "" || x === 0 || x === false){
				return true;
			}
			return false;
		},
		/** 判断变量是否可迭代 */
		isIterable : function(v){
            if(M.isArray(v) || v.callee){
                return true;
            }
            if(/NodeList|HTMLCollection/.test(toString.call(v))){
                return true;
            }
            return ((typeof v.nextNode != 'undefined' || v.item) && M.isNumber(v.length));
        },
        /** 是否是 整型数
         */
        isInt:function(n){
        	if(M.isNumber(n)){
        		n += "";
        		if(n.indexOf(".") < 0){
        			return true;
        		}
        	}
        },
        /** 是否是 浮点数
         */
        isFloat:function(n){
        	if(M.isNumber(n)){
        		n += "";
        		if(n.indexOf(".") > -1){
        			return true;
        		}
        	}
        },
        /** 是否是 Java模型的 主键id
         */
        isModelId:function(id){
        	if(id){
	        	if(M.isInt(id)){
	        		return id > 0;
	        	}
	        	if(M.isString(id)){
		    		return M.isModelId(parseInt(id));
        		}
        	}
        },
        /** 是否是 ID
         */
        isId:function(n){
        	if(n > 0){
        		return n;
        	}
        },
		/*----------json----------*/
		/**获取 json对象的长度*/
		jsonLength:function(json){
			var i = 0;
			for(k in json){
				if(k) i++;
			}
			return i;
		},
		/** 将 jsonStr 字符串转换为 json对象 */
		decode: function(jsonStr){
			var json = null;
			try{
				json = eval("(" + jsonStr + ')');
			}catch(e){}
			return json;    
		},
		/** 将 json对象转换为 jsonStr 字符串 */
		encode: function(json){
			return Ext.encode(json);
		},
		/** 是否是 json格式的字符串
		 * @param v (String) 
		 * @param r (Boolean) true: 如果是 json字符串,则返回json对象, 默认false.
		 */
		isJsonStr: function(v, r){
			if(!v) return '';
			var j = true;
			try{
				j = M.decode(v);
			}catch(e){
				j = false;
			}
			if(r) return j;
			return j ? true : false;
		},
		/**调试 json*/
		debugJson:function(vs, s, noShow){
			s = s || "";
			var va = vs,
				v = "";
//			if(!M.isArray(vs)){
//				va = [vs];
//			}
//			for(var i = 0, len = va.length; i < len; i++){
//				vs = va[i];
				for(var k in vs){
					v = vs[k];
					s += k + ":" 
						+ (M.isObject(v) || M.isArray(v) ? M.debugJson(v, "", 1) : v)
						+ "<br>\n";
				}
//			}
			if(!noShow){
				M.alert(s);
			}
			return s;
		},
		/*----------Ajax----------*/
		/** 构造 Ajax成功回调函数
		 * @param callback (Function) 成功时执行的函数
		 */
		buildSuccessCallBack:function(callback){
			return function(arg, flg, res){
				var f = false,
					d = res ? res.responseText : "";
				if(flg){
					if(!res){
						
					}else if(d === "True"){
						f = true;
					}else{
						var x;
						x = M.isJsonStr(d, 1);
						if(x) {
							d = x;
							if(x.success || d.id || d.rows || d.root){
								f = true;
							}
						}
					}
				}
				callback.call(this, d, flg ? f : 0, arg, res);
			};
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
			Ext.Ajax.request(ps);
		},
		/** post方式提交 ajax
		 * @see M.ajax
		 */
		post:function(ps){
			ps.method = "post";
			M.ajax(ps);
		},
		/*----------消息框----------*/
		/** 消息框 */
		alert: function(s){
			alert(s);
		},
		/** 错误框 */
		err: function(s){
			alert(s);
		},
		/** 显示框 */
		show: function(o){
			M.alert(o.msg);
		},
		/** 必要变量不存在错误
		 */
		varUnErr:function(p, v, msg){
			if(!v){
				M.alert("系统错误", msg + " 未定义!");
				p.destroy();
			}else if(v instanceof Array){
				for(var i = 0; i < v.length; i++){
					this.varUnErr(p, v[i], msg[i]);
				}
			}
			return true;
		},
		/** 用户不存在错误(跳转到登陆页面)
		 */
		userUnErr:function(v, msg){
			if(!v){
				M.app.gotoOALoginPage(msg, 0, -1);
				return true;
			}
			return false;
		},
		/*----------页面----------*/
		/** 向 body追加元素
		 * @param name (String) 标签名
		 * @param args (Object) 参数
		 * @param ctn (String) innerHTML内容
		 */
		appendBody:function(name, agrs){
			var node;
			if(agrs && agrs.id){
				node = document.getElementById(agrs.id);
			}else{
				node = document.createElement(name);
			}
			M.apply(node, args || {});
			document.body.appendChild(node);
			return node;
		},
		/**返回 单击弹出大图的 Img 标签
		 * @param opts (Object) 参数项
		 * @param opts.title
		 * @param opts.url
		 * @param opts.width
		 * @param opts.height
		 * @param opts.style
		 * @param opts.type
		 */
		imgPopTag:function(opts){
			var o = opts || {};
			var arr = [
				'<img style="border:0;cursor:pointer;',
					o.style || '',
					'" onClick="new ',
					o.type || "M.win.ImageWin",
					'({imgUrl:this.src, title:\'', (o.title || '图片'), '\'}).show();"',
					o.width ? ' width=' + o.width + '' : o.height ? ' ' : 'width=24',
					o.height ? ' height=' + o.height + '' : o.width ? ' ' : 'height=24',
					' src="', o.url, '" border="0"/>'
			];
			return arr.join('');
		},
		/** 刷新页面
		 * @param page (String) 刷新为指定的页面
		 * @param pos (String) 刷新的位置, 未指定则在本页面, "_blank": 新窗口
		 */
		refreshPage: function(url, pos){
			url = M.buildUrl(url);
			if(pos){
				M.appendBody("a", {
					id: "__refreshPage_A",
					target:pos,
					href: url
				}).click();
			}else{
				document.location = url;
			}
		},
		/** 跳转到某网址
		 * @param page (String) 要跳转到的页面
		 * @param msg (String) 跳转前要提示的内容, 为空不提示, 为 undefined直接跳转
		 * @param timeout (int) 多久后跳转, 单位毫秒, 默认 4秒, < 0: 直接跳转, 1: 等待确认
		 * @param xtype (int) -1: 错误提示, 默认alert 
		 */
		gotoPage:function(page, msg, timeout, xtype){
			if(msg === undefined){
				M.refreshPage(page);
				return ;
			}
			if(timeout){
				if(timeout < 0){
					M.refreshPage(page);
					return ;
				}
			}else{
				timeout = 1500;
			}
			if(msg){
				if(xtype && xtype === -1){
					M.err(msg, function(){
						M.refreshPage(page);
					});
				}else{
					M.alert(msg, function(){
						M.refreshPage(page);
					});
				}
			}
			if(timeout !== 1){
				setTimeout(function(){
					M.refreshPage(page);
				}, timeout);
			}
		},
		/** 跳转到网站的主页
		 */
		gotoWebIndexPage:function(){
			M.gotoIndexPage(true);
		},
		/** 跳转到网站的 index.jsp页面
		 */
		gotoIndexPage:function(b){
			M.refreshPage("index.jsp", b ? "_blank" : "");
		},
		/*----------表单----------*/
		/**获取表单的所有值
		 * @param f (String) 表单的 id或name值
		 * @param pv (boolean) false: 不对为""的字段做 "-0"值处理, 默认 true
		 */
		getFormValues:function(f, pv){
			f = M.get(f);
			var es = f.elements;
			var vs = {};
			var v;
			for(var i = 0; i < es.length; i++){
				if(es[i].type == "radio" && !es[i].checked){
					continue;
				}
				v = es[i].value;
				if(pv !== false && v === ""){
					v = "-0";
				}
				vs[es[i].name] = v;
			}
			return vs;
		},
		/*----------格式方法----------*/
		/**首字母小写
		 */
		firstLower:function(str){
			return M.isString(str) && str.length ? (str.substr(0,1).toLowerCase() + str.slice(1)) : "";
		},
		/**省略
		 * @param s (String) 原文字
		 * @param len (int) 最大长度 截取
		 * @param notPoint (Boolean) false(默认): 超出加"..."; true: 不追加
		 */
		ellipsis:function(s, len, notPoint){
			if(len && s.length > len){
				s = s.substr(0, len);
//				return x === false || x === 0 ? s : s + "...";
				return notPoint === true ? s : s + "...";
			}
			return s;
		},
		/** 去掉两边的空白
		 */
		trim: function(v){
            return String(v).replace(/^\s+|\s+$/g, "");
        },
        /** 当前日期对象, 用于扩展成同步服务器时间 */
        date: function(){
        	return new Date();
        },
        /** 获取指定时间s秒后的日期对象*/
	    getDateVaildS: function(date, s){
	    	if(!date) date = new Date();
	    	var ms = s * 1000;
	    	ms += date.getTime();
	    	date = new Date(ms);
	    	return date;
	    },
		/**将yyyy-MM-dd HH:mm:ss 转为 yyyy-MM-dd
		 * @param date (String) 日期
		 * @param oppo (boolean) true:反向 
		 * @param init (boolean) true:初始化以保证返回值可用 
		 */
		toDateStr:function(date, oppo, init){
			if(M.isString(date) && date.length > 10){
				date = date.substr(0, 10);
				if(oppo){
					return date + " 00:00:00";
				}
				return date;
			}
			if(init){
				date = "2009-09-01";
				if(oppo){
					return date + " 00:00:00";
				}
			}
			return date;
		},
		/** 生成日期时间字符串
		 * 默认 yyyy-MM-dd HH:mm:ss 格式的当前时间 
		 */
		dateTimeStr: function(fmt, date){
			switch(fmt){
				case 101 : fmt = "yyyy-MM-dd HH:mm"; break;
				case 1: fmt = "yyyy-MM-dd"; break;
				case 41: fmt = "yyyy年MM月dd日"; break;
			}
			return (date || new Date()).fmt(fmt);
		},
		/** 格式化时间 字符串
		 * @param t (String) 时间字符串, 如: 2012/4/12 18:50
		 * @param f (Boolean) false: 日期时间(默认); true: 只返回日期
		 * @return 2012-4-12 18:50 或 2012-4-12
		 */
		fmtTime:function(t, f){
			t = t ? t.replace( /\//g, "-") : "";
			return f ? t.split(" ")[0] : t;
		},
		/** yyyy-MM-dd HH:mm:ss --> yyyy-MM-dd HH:mm 及反向
		 */
		fmtDateTime101:function(v, opo){
			if(v){
		    	var pos1 = v.indexOf(":"),
		    		pos2 = v.lastIndexOf(":");
		    	if(pos1 > 0 && pos1 === pos2){
		    		if(!opo){
		    			v += ":00";
		    		}
		    	}else if(opo){
		    		v = v.substring(0, pos2);
		    	}
	    	}
	    	return v;
		},
		/** 适配日期 字符串
		 * @param y (int) 用当前年 加这个数
		 * @param m (int) 指定月
		 * @param d (int) 指定日
		 */
		adapterDateStr:function(y, m, d){
			var date = new Date(),
				s;
			s = y ? date.getFullYear() + y  : date.getFullYear();
			s += "-";
			s += m ? M.fillZero(m) : "01";
			s += "-";
			s += d ? M.fillZero(d): "01";
			return s;
		},
		/** 适配的生日
		 */
		adapterBirthday: function(){
			return M.adapterDateStr(-20);
		},
		/** 适配的入学日期
		 */
		adapterIntoSchool: function(){
			return M.adapterDateStr(0, 9);
		},
		/** 缓存 日期
		 */
		cacheDate: new Date(),
		/** 当前年
		 */
		curYear:function(){
			return M.cacheDate.getFullYear();
		},
		/** 当前 学年
		 */
		curSchYear:function(month){
			var year = M.curYear();
			if(M.cacheDate.getMonth() < (month || 6)){
				year = parseInt(year) - 1;
			}
			return year;
		},
		/** 小于 10 前补零 */
		fillZero: function(n, fill){
			if(fill === false) return n;
			if(n < 10 && n >= 0){
				return "0" + n;
			}
			return n;
		},
		/** 从右边开始截取字符串 */
		rightStr: function(s, len, start){
			if(!s) return "";
			if(s.length <= start) return "";
			if(!start) start = 0;
			var l = s.length;
			var t = len + start;
			// "1234567890", 4, 0
			if(l >= t){
				return  s.substr(l - t, len);
			}else{
				return s.substr(0, l - start);
			}
		},
		/** 检查数字域
		 * @param id (String) id或name值
		 * @param rep (boolean) true: 置0
		 */
		ckNumField: function(id, rep){
			id = M.get(id);
			var v = id.value;
			var vv = "";
			var r = true;
			var re = /\D*/gim;
			if(v.length > 0){
				vv = v.replace(re, "");
				if(vv.length != v.length){
					if(!rep) id.value = vv;
					r =  false;
				}
			}
			if(v.length < 1){
				if(!rep) id.value = 0;
				r = false;
			}
			return r;
		},
		/** 去掉所有空白 */
		dropn: function(str){
			return str.replace(/\s*/g, "");
		},
		/** 去掉所有考试中可有可无的字符 */
		dropKs: function(str){
			return str.replace(/[\s，,。？%\\\/、\(\)\{\}：;"']*/g, "");
		},
		/** 跳转到指定 id的锚点 */
		toAnchor: function(id){
			this.location.href = "#link" + M.getId(id).value;
		},
		/*----------编码----------*/
		/** 编码 HTML
		 * @param str (String) 将 <div> --> "&lt;div&gt;"
		 */
		encodeHtml:function(str){
			var s = ""; 
			if (str.length == 0) return ""; 
			s = str.replace(/&/g, "&gt;"); 
			s = s.replace(/</g, "&lt;"); 
			s = s.replace(/>/g, "&gt;"); 
			s = s.replace(/ /g, "&nbsp;"); 
			s = s.replace(/\'/g, "&#39;"); 
			s = s.replace(/\"/g, "&quot;"); 
			s = s.replace(/\n/g, "<br>"); 
			return s;
		},
		/** 解码 HTML
		 * @param str (String) 将 "&lt;div&gt;" --> <div>
		 */
		decodeHtml:function(str){
			var s = ""; 
			if (str.length == 0) return ""; 
			s = str.replace(/&gt;/g, "&"); 
			s = s.replace(/&lt;/g, "<"); 
			s = s.replace(/&gt;/g, ">"); 
			s = s.replace(/&nbsp;/g, " "); 
			s = s.replace(/&#39;/g, "\'"); 
			s = s.replace(/&quot;/g, "\""); 
			s = s.replace(/<br>/g, "\n"); 
			return s;
		},
		/*----------高级方法----------*/
		/**通过类名及参数new 出一个对象
		 * @param ns (String) 包路径, 如: "tree" (不带 M)
		 * @param className (String) 类名
		 * @param params (Json) 参数
		 */
		newClass:function(ns, className, params) {
	//			try{
				if(!className || M.isObject(className)){	//如果直接传过来类名和参数, 如: M.newClass("M.gsp.MgrGSP", {});
					params = className;
					className = ns;
				}else{
					className = "M." + ns + "." + className; //如 : M.gsp.MgrGSP
				}
				var str = "new " + className;
				if (params != null) {
					str += "(params);";
				} else {
					str += "();";
				}
				return eval(str);
	//			}catch(e){
	//				M.alert("系统错误!", e);
	//			}
		},
		/**js 文件缓存
		 */
		jsCache:new Array(),
		/** 导入 js文件(简单方式)
		 * @param jsArr (Array) 要导入的 js路径文件名 数组
		 * @param callback (Function) 加调函数
		 */
		ImportSimpleJs:function(jsArr, callback) {
			ScriptMgr.load({
				scripts : jsArr,
				callback : function() {
					if (callback) {
						callback.call(this);
					}
				}
			});
		},
		/**创建一个文件上传对话框
		 */
		createUploadDialog:function(b) {
			var a = {
				file_cat : "others",
				url : M.buildUrl("file-upload"),
				reset_on_hide : false,
				upload_autostart : false,
				modal : true
			};
			M.apply(a, b);
			var c = new Ext.ux.UploadDialog.Dialog(a);
			return c;
		},
		/** 将 多维数组转为 1维数组
		 */
		arrNto1:function(nas, as){
			as = as || [];
			if(M.isArray(nas)){
				for(var i = 0; i < nas.length; i++){
					if(M.isArray(nas[i])){
						M.arrNto1(nas[i], as);
					}else{
						as.push(nas[i]);
					}
				}
			}
			return as;
		},
		/** 将 keys中的 带.值 转化为对象 <br>
		 * 如: [appUser.id, appUser.major.id] ==> {appUser:{major:1}}
		 */
		keysToObj:function(ms, obj){
			obj = obj || {};
			var ix,
				pre,
				ext;
			for(var i = 0,len = ms.length; i < len; i++){
				ix = ms[i].indexOf("."); 
				if(ix > 0){
					pre = ms[i].substring(0, ix);
					ext = ms[i].substr(ix + 1);
					if(ext.indexOf(".") > 0){
						if(!M.isObject(obj[pre])){
							obj[pre] = {};
						}
						M.keysToObj([ext], obj[pre]);
					}else{
						if(!obj[pre]){
							obj[pre] = 1;
						}
					}
				}
			}
			return obj;
		},
		/** 填充 obj中在mo中要求是对象的null值为{}
		 * @param obj (Object/Array) {name:'mao'}、[{name:'mao'}]两种格式 
		 * @param mo (Object) 映射模型, {role: 1, major:{college: 1}}格式
		 * @param depth (boolean) true: 深度, 防止数组过深入的转换; false: 不控制(默认) 
		 */
		fillNullObj:function(obj, mo, depth){
			if(M.objLength(mo)){
				if(M.isObject(obj)){
					for(var k in mo){
						if(!M.isObject(obj[k])){
							obj[k] = {};
						}
						if(M.isObject(mo[k])){
							M.fillNullObj(obj[k], mo[k]);
						}
					}
				}else if(M.arrLength(obj) && !depth && M.isObject(obj[0])){
					for(var i = 0, len = obj.length; i < len; i++){
						M.fillNullObj(obj[i], mo, true);
					}
				}
			}
			return obj;
		},
		/*----------杂项----------*/
		/** 生成 Action
		 * @param obj (Object) 寄主对象
		 * @param urls (Array) 动作， 如: ["get", "list"] 
		 * @param ns (String) 命名空间 
		 * @param cn (String) 类名 
		 * @param ext (String) 属性后缀, 如 "get" + ext --> getUrl 
		 */
		buildAction: function(obj, urls, ns, cn, ext){
			ns = ns ? ns + "/" : "";
			ext = ext ? ext : "";
			for(var i = 0; i < urls.length; i++){
				obj[urls[i] + ext] = M.rootUrl(ns + urls[i] + "Json" + cn + ".do");
			}
		},
		/** 计数器 */
		counter:0,
		/** id生成器 */
		buildId:function(s){
			return s + "_" + (M.counter++);
		},
		/** 定时器
		 */
		setTimeout:function(fn, time){
			return setTimeout(fn, time);
		},
		/** 检测当某个变量不为undefined时, 执行回调函数
		 * @param mv (Object/Funciton) 主对象(或函数)
		 * @param sub (String) 属性名称, 将mv[sub]这样使用(若mv为函数, 则sub为此函数的参数)
		 * @param fn (Funciton) 回调函数(mv[sub], isTimeout, mv, sub, c, c*st)
		 * @param st (ms) 间隔(毫秒), 默认200
		 * @param timeout (ms) 超时(毫秒), 默认30000
		 * 如:<pre>
		 * setTimeout(function(){
				window.abc = '1aa';
			}, 500);
			//变量方式
		 	M.checkVar(window, 'abc', function(v, isTimeout, mv, sub, c, ts){
				alert(v + ', ' + c + ', ' + ts);
			}, 200, 1000);
			//函数方式
			M.checkVar(function(){
				if(window.abc) return window.abc + 'SSS';
			}, 'abc', function(v, isTimeout, mv, sub, c, ts){
				alert(v + ', ' + c + ', ' + ts);
			}, 200, 1000);
		 * </pre>
		 */
		checkVar:function(mv, sub, fn, st, timeout){
			st = st || 200;
			var tt = (timeout || 30000) / st;
			var c = 0;
			var isTimeout = false;
			var isFn = M.isFunction(mv);
			var iv = setInterval(function(){
				if(c++ > tt){	//超时
					isTimeout = true;
				}
				var suc = false;
				var retv = false;	//结果值
				if(isFn){
					retv = mv.call(this, sub);
					if(retv !== undefined) suc = true;
				}else{
					if(mv[sub] !== undefined){
						suc = true;
						retv = mv[sub];
					}
				}
				if(isTimeout || suc){
					if(M.isFunction(fn)) fn.call(this, retv, isTimeout, mv, sub, c, c*st);
					clearInterval(iv);
					return ;
				}
			}, st);
		},
		t:function(){
			alert("sss");
		},
		/** 用于 缓存 url请求后的数据<br>
		 * @param url (String) 如: listJsonAppUser.do
		 * @param data (Json) 如: {ps:?, ds:?}
		 * @param data.ps 请求的参数 
		 * @param data.ds 对应的值
		 */
		cacheUrlData:{
			
		},
		/** 字典方法
		 * @param dn (String) 一级字典名, 如: M.dict.IdGroup, 也可以是Url[getJsonAppUuser.do]
		 * @param ps (Object) 用于Url的参数
		 * @param fn (Callback) 用于回调的函数
		 * 其中字典有四中形式:
		 * 1: nor:{Student: 1, Teacher: 2, Other: 3} 正常形式, 类似于定义常量
		 * 2: arr:[[1, "学生组"], [2, "教师组"], [3, "其他组"]] 数组形式, 用于combo
		 * 3: map:{1: "学生组", 2: "教师组", 3: "其他组" } 用于 通过 key显示值
		 * 4: 其他形式
		 */
		getDict: function(dn, ps, fn){
			var d = M.dict || {};
			var data = dn ? d[dn] || {} : d;
			
			return data;
		},
		/** 锁定按钮及表单等,防止单击过快
		 * @param h (Object) 寄主, 会操作 h._locked属性
		 * @param ts (int) 默认锁定 2秒钟, 单位毫秒, -1不自动解锁, 需要之后的代码解锁
		 * @param fn (function) 解锁时执行的函数
		 * @return 如果当前已锁定, 返回 true;
		 */
		lockBtn:function(h, ts, fn){
			if(h._locked){
				return true;
			}else{
				h._locked = true;
				if(ts !== -1){
					setTimeout(function(){
						h._locked = false;
					}, ts ? ts : 2000);
				}
				return false;
			}
		},
		/** 判断是否是图片路径,  */
		isImgPath:function(img){
			if(img && img.length > 4){
				img = img.substr(img.length-4, 4);
				if(img === ".jpg" || img === ".png" || img === ".gif" || img === ".bmp"){
					return true;
				}
			}
			return false;
		},
		/*----------XML----------*/
		/** 将 json转换为 XML, 辅助解析方法
		 */
		encodeXMLParse: function(o, kk){
			var xml = "";
			if(M.isObject(o)){
				var obj = {}, j = 0;
				xml += "<" + kk;
				for(var k in o){
					if(M.isObject(o[k]) || M.isArray(o[k])){
						obj[k] = o[k];
						j++;
					}else{
						xml += M.encodeXMLParse(o[k], k);
					}
				}
				if(j > 0){
					xml += ">";
					for(var k in obj){
						xml += M.encodeXMLParse(obj[k], k);
					}
					xml += "</" + kk + ">";
				}else{
					xml += "/>";
				}
				
			}else if(M.isArray(o)){
				for(var k = 0; k < o.length; k++){
					xml += M.encodeXMLParse(o[k], kk);
				}
			}else{
				xml += " " + kk + "='" + (M.isDate(o) ? M.dateTimeStr(1, o) : o) + "'";
			}
			return xml;
		},
		/** 将 json转换为 XML
		 */
		encodeXML:function(o){
			var xml = "";
			if(M.isObject(o)){
				for(var k in o){
					xml += M.encodeXMLParse(o[k], k);
				}
			}
			return xml;
		},
		/*----------子属性操作方法----------*/
		/** 检查 prop是否是arr的一个元素, 存在返回其索引, 不存在返回-1 */
		inArr: function(arr, prop){
			if(arr && arr.length){
				for(var i = 0; i < arr.length; i++){
					if(arr[i] === prop){
						return i;
					}
				}
			}
			return -1;
		},
		/** o的属性名为n的属性, 如果是数组进行保持或复制,否则置为[]
		 * @param o (Object) 寄主对象
		 * @param n (String) 属性名
		 * @param f (Boolean) false:保持(默认), true:复制
		 * @return o[n], 如果 o不为Object, 将置为 {}
		 */
		propArr:function(o, n, f){
			if(!M.isObject(o)){
				o = {};
				return o[n] = [];
			}
			if(!M.isArray(o[n])){
				return o[n] = [];
			}
			return f ? (o[n] = o[n].slice(0)) : o[n];
		},
		/** o的属性名为n的属性, 如果是Json进行保持或复制,否则置为{}
		 * @param o (Object) 寄主对象
		 * @param n (String) 属性名
		 * @param f (Boolean) false:保持(默认), true:复制
		 * @return o[n], 如果 o不为Object, 将置为 {}
		 */
		propObj:function(o, n, f){
			if(!M.isObject(o)){
				o = {};
				return o[n] = {};
			}
			if(!M.isObject(o[n])){
				return o[n] = {};
			}
			return f ? (o[n] = M.apply({}, o[n])) : o[n];
		},
		/** 获取 Ajax请求后返回的 root数组数据
		 * @param ds (Json)  Ajax请求返回的数据
		 * @param rn (String) 默认 root
		 * @return 没有数据或处理失败都会返回[];
		 */
		propRoot:function(ds, rn){
			var rs = [];
			if(!ds){
				return rs;
			}
			if(M.isArray(ds)){
				return ds;
			}
			rn = rn || "root";
			if(M.isArray(ds[rn])){
				return ds[rn];
			}
			return rs;
		},
		/** 取 p的sub子属性, 如: obj["root[0].name"]即取 obj.root[0].name, 获取不到返回空串""
		 */
		propSub:function(p, sub){
			var v = "";
			if(!p) return v;
			try{
				var pn = "p.";
				if(M.isArray(p)){
					pn = "p";
				}
				v = eval(pn + sub);
			}catch(e){}
			return v;
		},
		/**取对象的子对象的属性值
		 * @param o (Json) 主对象 如: appUser
		 * @param n (String) 属性名 如: role.id 或 name
		 * @return appUser.role.id 或 appUser.name 不存在返回 ""
		 * subValue({name:"aa", pw:"11"}, "name");
		 */
		subValue:function(o, n){
			if(o && n){
				var k = n.indexOf(".");
				if(k > 0){
					var so = n.substring(0, k),
						sp = n.substring(k + 1);
					if(o[so]){
						return M.subValue(o[so], sp);
					}
				}
				return o[n] === undefined ? "" : o[n];
			}
			return "";
		},
		/**给对象的子对象的属性值
		 * @param o (Json) 主对象 如: appUser
		 * @param n (String) 属性名 如: role.id 或 name
		 * @param v () 如:将 appUser.role.id 或 appUser.name 的值设为 v
		 * @param c (boolean) 如果子对象不存在是否创建, 默认 false 不创建
		 */
		setSubValue:function(o, n, v, c){
			var k = n.indexOf(".");
			if(k > 0){
				var so = n.substring(0, k);
				var sp = n.substring(k + 1);
				if(o[so]){
					o[so][sp] = v;
				}else if(c){
					o[so] = {};
					o[so][sp] = v;
				}
			}else{
				o[n] = v;
			}
		},
		/** 是否有子元素, 或指定的元素
		 * @param os (Object/Array) 操作项
		 * @param n (String) 元素名
		 */
		hasSub:function(os, n){
			if(n){
				
			}else{
				if(os){
					if(M.isArray(os)){
						return os.length;
					}else if(M.isObject()){
						return M.objLength(os);
					}
				}else{
					return 0;
				}
			}
		},
		// 取后代值
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
		/** 获得数组的长度 <br>
		 *  如果为 ,<br>
		 *  如果为 Array 返回 其长度,<br>
		 *  如果为其他(String, Object, null, undefine等) 返回 0<br>
		 * */
		arrLength:function(arr){
			if(!arr) return 0;
			if(arr instanceof Array) return arr.length;
			return 0;	
		},
		/**获取 Object对象的长度*/
		objLength:function(obj){
			var i = 0;
			if(M.isObject(obj)){
				for(k in obj){
					if(k) i++;
				}
			}
			return i;
		},
		/** 为 Array、Object补充前缀 
		 * @param os (Object/Array) 要追加的数组或对象
		 * @param pre (String) 前缀值
		 * @param enn (boolean/String) 如果os是Object则, enn:true 追加到 key上, false追加到value上(默认), <br>
		 * 								如果os是Array则, enn:false 追加到每个值上(默认), enn:"xx"追加到 arr[i]["xx"]上
		 * @param incs (Object) 操作在 incs中的属性, 默认全部
		 * @return os
		 */
		prefixOA:function(os, pre, enn, incs){
			var preLen = pre.length;
			if(M.isObject(os)){
				for(var i in os){
					if(enn){
						var b = true;
						if(incs && !incs[i]){
							b = false;
						}
						if(b){
							var en = i;
							if(en.substr(0, preLen) != pre){
								os[pre + en] = os[i];
								delete os[i];
							}
						}
					}else{
						var en = os[i];
						if(en.substr(0, preLen) != pre){
							os[i] = pre + en;
						}
					}
				}
			}
			if(M.isArray(os)){
				for(var i = 0, len = os.length; i < len; i++){
					if(enn){
						var en = os[i][enn];
						if(en.substr(0, preLen) != pre){
							os[i][enn] = pre + en;
						}
					}else{
						var en = os[i];
						if(en.substr(0, preLen) != pre){
							os[i] = pre + en;
						}
					}
				}
			}
			return os;
		},
		/** 将一维数组转为 二维数组, 如: 处理 ["a", "b"] 成 [["a", "a"], ["b", "b"]]
		 */
		arr22:function(ld){
			var arr = [];
			if(M.isArray(ld) && ld.length > 0 && !M.isArray(ld[0])){
				for(var i = 0, len = ld.length; i < len; i++){
					arr.push([ld[i], ld[i]]);
				}
				ld = arr;
			}
			return arr;
		},
		/** 将 二维数组 转换成 Object数组类型, 如: [[1, "mao"], [2, "haozi"]] --> [{id:1, name:"mao"}, {id:2, name:"haozi"}]
		 * @param isPaging (boolean) true:刚添加分页属性, 默认false
		 * @param maps (Object) 映射方法, 如:["id","name"]
		 * @param jcfg (Object) 分页属性, 如:{total:"totalProperty", root:"root"}
		 */
		arr22OA:function(a, maps, isPaging, jcfg){
			var oa, oo;
			oa = oo = [];
			if(M.hasSub(a) && M.isArray(a[0])){
				var len = a.length;
				if(!maps){
					maps = ["id","name"];
				}
				if(isPaging){
					if(!jcfg){
						jcfg = {total:"totalProperty", root:"root"};
					}
					oa = {};
					oa[jcfg.total] = len;
					oo = oa[jcfg.root] = [];
				}
				for(var i = 0; i < len; i++){
					var o = {};
					o[maps[0]] = a[i][0];
					o[maps[1]] = a[i][1];
					oo.push(o);
				}
			}
			return oa;
		},
		//TODO 正则表达式验证
		/** 检测是否为手机号 */
		isMobile: function(str){
			if(str && str.length > 10 &&  str.length < 20){
				return /^(1[34578][0-9])\d{8}$/.test(str);
			}
			return false;
		}
	});
	
	M.ns = M.namespace;
})();

//费时工具类-- 插件
;(function(){
	//--  费时工具类
	if(!window["M"]) M = {};
	/***
	 * 使用方法: <br>
	 *  var t  = new M.Timer();	 <br>
	 *  t.printLastTimeElapsed('1');	//打印 上次 toStringTime()已过去的时间, "1, 00:00:00:029" <br>
	 *  t.printLastTimeElapsed('2');	//打印 上次 toStringTime()已过去的时间, "2, 00:00:00:048" <br>
	 *  t.printElapsed('all');			//打印 计时器已过去的时间, "all, 00:00:00:078"
	 */
	M.Timer = function(timeLimit){
		return new M.Timer.fn.init(timeLimit);
	};
	M.Timer.Second = 1000;
	M.Timer.Minute = 60 * M.Timer.Second;
	M.Timer.Hour = 60 * M.Timer.Minute;
	M.Timer.Day = 24 * M.Timer.Hour;
	M.Timer.Month = 30 * M.Timer.Day;
	M.Timer.Year = 365 * M.Timer.Day;
	M.Timer.Elapsed_Type = 1;
	M.Timer.Remaining_Type = 2;
	M.Timer.LastTimeElapsed_Type = 3;
	M.Timer.fn = M.Timer.prototype = {	//对象中带有属性, 静态的属性和函数
		startTime: 0,	/** 开始时间 */
		endTime: 0,	/** 结束时间 */
		timeLimit: 0,	/** 间隔时间 */
		lastOutTime: 0,	/** 上一次 toStringTime()的时间, 用于中间取时间段 */
		isPrint: true,/** 是否向控制台输出信息, 默认true是, 方便控制信息的输出 */
		constructor: M.Timer,
		init: function(timeLimit){
			this.timeLimit = timeLimit || M.Timer.Year;
			this.startTime = new Date().getTime();
			this.endTime = this.startTime + this.timeLimit;
			this.lastOutTime = this.startTime;
			return this;
		},
		/** 已过去的时间 */
		getTimeElapsed: function() {
			return (new Date().getTime() - this.startTime);
		},
		/** 距离上次 toStringTime()已过去的时间 */
		getLastTimeElapsed: function() {
			return (new Date().getTime() - this.lastOutTime);
		},
		/** 剩余的时间 */
		getTimeRemaining: function() {
			if (this.isNotUp()){
				return (this.endTime - new Date().getTime());
			}else{
				return (0);
			}
		},
		/** true: 未过时 */
		isNotUp: function() {
			return new Date().getTime() < this.endTime;
		},
		/** true: 过时了 */
		isUp: function() {
			return new Date().getTime() >= this.endTime;
		},
		/** 过时则重置, 且返回true */
		isUpThenReset: function() {
			if (this.isUp()) {
				this.reset();
				return true;
			} else return false;
		},

		/** 重置 */
		reset: function() {
			this.endTime = new Date().getTime() + this.timeLimit;
		},
		/** 重新开始, all: false, 则只重置开始, 不重置结束 */
		restart: function(all){
			this.startTime = new Date().getTime();
			if(all !== false){
				this.endTime = this.startTime + this.timeLimit;
				this.lastOutTime = this.startTime;
			}
		},
		/**
		 * 以当前时间开始计时, ms毫秒后过时。
		 * @param ms (long) 追加的时间(毫秒)
		 */
		setTimerToEndIn: function(ms) {
			this.endTime = new Date().getTime() + ms;
			return this.endTime;
		},
		/** 转换成时间字符串, 内部方法, 会记录当前时间到lastTime */
		_toStringTime: function(time){
			this.lastOutTime = new Date().getTime();
			return M.Timer.toStringTime(time);
		},
		/** 已过去的时间 -- 字符串 */
		toStringTimeElapsed: function() {
			return (this._toStringTime(this.getTimeElapsed()));
		},
		/** 剩余的时间 -- 字符串 */
		toStringTimeRemaining: function() {
			return (this._toStringTime(this.getTimeRemaining()));
		},
		/** 距离上次 toStringTime()已过去的时间 -- 字符串 */
		toStringLastTimeElapsed: function(){
			return (this._toStringTime(this.getLastTimeElapsed()));
		},
		/** 打印已逝去的时间 */
		printElapsed: function(str){
			if(M.Timer.s_isPrint && this.isPrint){
				console.info(str + ", " + this.toStringTimeElapsed());
			}
		},
		/** 打印剩余的时间 */
		printRemaining: function(str){
			if(M.Timer.s_isPrint && this.isPrint){
				console.info(str + ", " + this.toStringTimeRemaining());
			}
		},
		/** 打印 上次 toStringTime()已过去的时间 */
		printLastTimeElapsed: function(str){
			if(M.Timer.s_isPrint && this.isPrint){
				console.info(str + ", " + this.toStringLastTimeElapsed());
			}
		},
		/** 设置是否向控制台输出信息 */
		setPrint: function(isPrint) {
			this.isPrint = isPrint;
		}
	};
	M.Timer.fn.init.prototype = M.Timer.fn;
	/** (全局)是否向控制台输出信息, 默认true是, 方便控制信息的输出 */
	M.Timer.s_isPrint = true;
	/** 一年的计时器 */
	M.Timer.yearTimer = new M.Timer(M.Timer.Year);
	/** 用于跨函数、跨类计时器 */
	M.Timer.fnTimer = new M.Timer(M.Timer.Year);
	/** 用于跨函数、跨类计时器 */
	M.Timer.getFnTimer = function(){
		if(!M.Timer.fnTimer){
			M.Timer.fnTimer = new M.Timer(M.Timer.Year); 
		}
		return M.Timer.fnTimer;
	};
	/** 转换成时间字符串 */
	M.Timer.toStringTime = function(time) {
		var t = [];
		var TotalSec = Math.floor(time / 1000);
		var TotalMin = Math.floor(TotalSec / 60);
		var TotalHour = Math.floor(TotalMin / 60);
		var mm = time % 1000;
		var second = TotalSec % 60;
		var minute = TotalMin % 60;
		var hour = TotalHour % 60;
		if (hour < 10)
			t.push("0");
		t.push(hour);
		t.push(":");
		if (minute < 10)
			t.push("0");
		t.push(minute);
		t.push(":");
		if (second < 10)
			t.push("0");
		t.push(second);
		t.push(":");
		if(mm < 10){
			t.push("00");
		}else if(mm < 100){
			t.push("0");
		}
		t.push(mm);
		return (t.join(''));
	};
})();

/** 数学算法 */
;(function(){
	if(!window["M"]) M = {};
	M.math = {
		/*----------点----------*/
		/** 判断点在多边形内部, (射线法), (验证正确) (double[2]表示1个点). 例:
		 * <code>
		 * var p = [128.48872, 47.44254];	//伊春站点
		 * var ps = M.gis.toPointsArr2(features[0].feature.geometry.components);
		 * var c = M.math.pointInPolygon(ps, p);
		 * alert(c ? "在" : "不在");
		 * </code>
		 * @param list(List<double[2]>) 如: 二维数组
		 * @param x0(double) 
		 * @param y0(double) 如果不传, x0必须是double[2]类型
		 */
		pointInPolygon: function(list, x0, y0) {
			if(!y0 && y0 !== 0) y0 = x0[1]; x0 = x0[0];	//处理直接传递过来一个(double[2]形式的)点
			
			var c = false;
			var pi, pj;	//j点到i点
			var Len = list.length;
		    for (var i = 0, j = Len - 1; i < Len; j = i++) {
		    	pi = list[i];
		    	pj = list[j];
		        if ( (
			        	((pi[1] <= y0) && (y0 < pj[1])) 
			        	|| 
			        	((pj[1] <= y0) && (y0 < pi[1]))
		            ) && (
		            	x0 < (pj[0] - pi[0]) * (y0 - pi[1]) / (pj[1] - pi[1]) + pi[0]
		            )
		         ){
		            c = !c;
		        }
		    }
		    return c;
		},
		/** 判断p点是否在点a、b构成的矩形内. 例:
		 * <code>
		 *  var p_p = [150, 50];
		 *  var p_a = [0, 0];
		 *  var p_b = [100, 100];
		 *  var p_c = M.math.pointInRect(p_p, p_a, p_b);
		 *  alert(p_c ? "在" : "不在");	//不在
		 *  </code>
		 * @param p (double[2]) 要判断的点
		 * @param a (double[2]) 矩形的左上角点
		 * @param b (double[2]) 矩形的右下角点
		 */
		pointInRect: function(p, a, b) {
			var minX = Math.min(a[0], b[0]);
			var minY = Math.min(a[1], b[1]);
			var maxX = Math.max(a[0], b[0]);
			var maxY = Math.max(a[1], b[1]);
			if(p[0] >= minX && p[0] <= maxX && p[1] >= minY && p[1] <= maxY){
				return true;
			}
			return false;
		},
		/** 两个点的值是否相等, dec精度值, 如: 0.0000000001. 例:
		 * <code>
		 * var p_a = [100.000000001, 100];
		 * var p_b = [100, 100];
		 * var p_c = M.math.pointEq(p_a, p_b);
		 * alert(p_c ? "相等" : "不等");	//不等
		 * </code>
		 * @param a (double[2]) 点
		 * @param b (double[2]) 点
		 * @param dec (double) 精度, 默认0.0000000001
		 */
		pointEq: function(a, b, dec){
			dec = dec || 0.0000000001;
			if(Math.abs(a[0]-b[0]) < dec && Math.abs(a[1]-b[1]) < dec){
				return true;
			}
			return false;
		},
		/** 从一组点中找出相等的两个点
		 * @param ps(List<double[2]>) N个参数(double[2]形式的点)
		 */
		pointArr2Eq: function(ps){
			if(!ps || !ps.length) return null;
			if(ps.length == 1) return ps[0];
			for(var i = 0; i < ps.length; i++){
				for(var j = i+1; j < ps.length; j++){
					if(M.math.pointEq(ps[i], ps[j])){
						return ps[i];
					}
				}
			}
			return null;
		}
	};
	
})();


//---- 重写 原生方法
/** 去字符串两端空白
 */
String.prototype.trim = function(){
	return this.replace(/^\s+|\s+$/g, "");
};
/** 去字符串两端空白并全部小写
 */
String.prototype.lc = function(){
	return this.replace(/^\s+|\s+$/g, "").toLowerCase();
};

Date.weekObj = {0:"星期日", 1:"星期一", 2:"星期二", 3:"星期三", 4:"星期四", 5:"星期五", 6:"星期六"};
Date.defFmt = "yyyy-MM-dd HH:mm:ss";
/** 格式化日期 */
Date.prototype.fmt = function(fs, fill0){
	if(!fs) fs = Date.defFmt;
	var d = this;
	fs = fs.replace("yyyy", d.getFullYear());
	fs = fs.replace("MM", M.fillZero(d.getMonth() + 1, fill0));
	fs = fs.replace("dd", M.fillZero(d.getDate(), fill0));
	fs = fs.replace("HH", M.fillZero(d.getHours(), fill0));
	fs = fs.replace("mm", M.fillZero(d.getMinutes(), fill0));
	fs = fs.replace("ss", M.fillZero(d.getSeconds(), fill0));
	fs = fs.replace("T", Date.weekObj[d.getDay()]);
	return fs;
};
/** 让window.setTimeout等支持带参数方法, 用法如: <br>
 * <pre>
 * for ( var i = 0; i < 20; i++) {
 *   window.setTimeout(fn.curry(i, i+1), 100);
 * }
 * </pre>
 */
Function.prototype.curry = function () {
    var slice = Array.prototype.slice,
        args = slice.apply(arguments),
        that = this ;
    return function () {
        return that.apply(null , args.concat(slice.apply(arguments)));
    };
};

/**=============================================================
 *   功能： 修改 window.setInterval ，使之可以传递参数和对象参数    
 *   方法： setInterval (回调函数,时间,参数1,,参数n)  参数可为对象:如数组等
 *============================================================= 
 */
//var __sto = setInterval;     
//window.setInterval = function(callback,timeout,param){     
//  var args = Array.prototype.slice.call(arguments,2);     
//  var _cb = function(){     
//      callback.apply(null,args);     
//  };     
//  return __sto(_cb,timeout);     
//};
