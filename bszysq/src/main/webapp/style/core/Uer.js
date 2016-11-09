/** 对ueditor在线编辑器的工具类 */
;(function(){
	window["Uer"] = function(){};
	/** 对象元素复制 **/
	Uer.apply = function(o, c, x){
	    if(x){
	        Uer.apply(o, x);
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
	Uer.apply(Uer, {
        setContent:function(id){
            UE.getEditor('editor').addListener("ready", function () {
                // editor准备好之后才可以使用
                UE.getEditor('editor').setContent(id);
            });
        },
		/** 获取一个简单的编辑器 */
		getEditor: function(id, opt){
			return UE.getEditor(id, opt);
		},
		/** 获取一个文本编辑器 */
		getTxtEditor: function(id, opt){
			opt = Uer.apply({
				toolbars: [[
		            //'fullscreen', 'source', '|',
		            'bold', 'italic', 'underline', 'pasteplain', '|', 
		            'forecolor', 'fontfamily', 'fontsize', '|',
		            'link', 'unlink', 'emotion', 'horizontal', 'spechars'
		        ]]
			}, opt);
			var edt = UE.getEditor(id, opt);
			return edt;
		},
		/** 获取一个简单的编辑器 */
		getSimpleEditor: function(id, opt){
			opt = Uer.apply({
				toolbars: [[
		            'fullscreen', 'source', '|', 'undo', 'redo', '|',
		            'bold', 'italic', 'underline', 'removeformat', 'pasteplain', '|', 
		            'forecolor', 'backcolor', '|',
		            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		            'fontfamily', 'fontsize', '|',
		            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
		            'link', 'unlink', 'anchor', '|', 
		            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'map', 'template', 'background', '|',
		            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
		            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 
		            'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 
		            'splittocells', 'splittorows', 'splittocols', '|',
		            'print', 'preview', 'searchreplace', 'help', 'drafts'
		        ]]
			}, opt);
			var edt = UE.getEditor(id, opt);
			return edt;
		},
		/** 获取一个店铺介绍的编辑器 */
		getShopDescEditor: function(id, opt){
			opt = Uer.apply({
				toolbars: [[
		            //'fullscreen', 'source', '|', 'undo', 'redo', '|',
		            'bold', 'italic', 'underline', 'removeformat', 'pasteplain', '|', 
		            'forecolor', 'backcolor', '|',
		            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		            'fontfamily', 'fontsize', '|',
		            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 
		            'link', 'unlink', '|', 
		            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'map', 'template', '|',
		            'horizontal', 'spechars', 'snapscreen', '|',
		            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 
		            'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 
		            'splittocells', 'splittorows', 'splittocols', '|',
		            'print'
		        ]]
			}, opt);
			var edt = UE.getEditor(id, opt);
			return edt;
		}
	});
})();

