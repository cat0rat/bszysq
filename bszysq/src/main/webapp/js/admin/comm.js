var L = {
dgLoadData: function(opt){
	var dgOpt = $.extend({
		//url : url,
		queryParams : {
			name : $("#name").val()
		},
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		striped : true,
		collapsible : true,
		width : '100%',
		height : 'auto',
		toolbar : '#toolbar',
		singleSelect : "true",
		onLoadError : function() {
			$.messager.alert('温馨提示', '系统繁忙，请重试!', 'error');
		},
		loadFilter: function(data){
			return  data.data ? data.data : data;
		}
	}, opt.dgOpt);
	L.dg = $(opt.dgSelor || '#data_list').datagrid(dgOpt);
	//设置分页控件 
	var p = L.dg.datagrid('getPager');
	$(p).pagination({
		pageSize : 10,//每页显示的记录条数，默认为10 
		pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表 
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	/*onBeforeRefresh:function(){
	    $(this).pagination('loading');
	    alert('before refresh');
	    $(this).pagination('loaded');
	}*/
	});
},
dgSearch: function(dealFn){
	var ps = L.dg.datagrid('options').queryParams;
	dealFn && dealFn(ps);
	L.dg.datagrid('options').queryParams = ps;
	L.dg.datagrid('reload');
},
dgChecked: function() {
	return L.dg.datagrid('getSelected');
},
dgDel: function(url){
	var seled = L.dgChecked(); //获取选中行
	if (seled) {
		$.messager.confirm('确认', '确认删除?', function(row) {
			if (row) {
				$.ajax({
					type : 'POST',
					data : 'id=' + seled.id,
					url : url,
					success : function() {
						$.messager.alert('温馨提示', '删除成功!', '', function() {
							L.dg.datagrid('reload');
						});
					},
					error : function() {
						$.messager.alert('温馨提示', '删除失败!', 'error');
					}
				});
			}
		});
	} else {
		$.messager.alert('温馨提示', '必须且仅能选择一条需要删除的纪录', 'warning');
	}
},
dgEdit: function(url, dlgName){
	var seled = L.dgChecked(); //获取选中行
	if (seled) {
		//弹出窗口预览
		url += "?id="+ seled.id;
		var topWidth = window.top.document.body.clientWidth - 200;
		var topHeight = window.top.document.body.clientHeight - 100;

		var html = "<div id='"+dlgName+"'>";
		html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
				+ url + "'></iframe>";
		html += "</div>";

		var dlg = window.top.$(html).appendTo(window.top.document.body);
		dlg.dialog({
			title : '修改数据',
			collapsible : false,
			draggable : false,
			resizable : false,
			minimizable : false,
			maximizable : false,
			cache : false,
			width : topWidth,
			height : topHeight,
			modal : true,
			onClose : function() {
				dlg.dialog("destroy");
				L.dg.datagrid("reload");
			}
		});
	} else {
		$.messager.alert('温馨提示', '必须且仅能选择一条需要修改的数据！', 'warning');
	}
},
dgView: function(url, dlgName) {
	var seled = L.dgChecked(); //获取选中行
	if (seled) {
		//弹出窗口预览
		url += "?id="+ seled.id;
		var topWidth = window.top.document.body.clientWidth - 200;
		var topHeight = window.top.document.body.clientHeight - 100;

		var html = "<div id='"+dlgName+"'>";
		html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
				+ url + "'></iframe>";
		html += "</div>";

		var dlg = window.top.$(html).appendTo(window.top.document.body);
		dlg.dialog({
			title : '查看详情',
			collapsible : false,
			draggable : false,
			resizable : false,
			minimizable : false,
			maximizable : false,
			cache : false,
			width : topWidth,
			height : topHeight,
			modal : true,
			onClose : function() {
				dlg.dialog("destroy");
			}
		});
	} else {
		$.messager.alert('温馨提示', '必须且仅能选择一条需要查看的数据！', 'warning');
	}
},
dgAdd: function(url, dlgName) {
	//弹出窗口预览
	var topWidth = window.top.document.body.clientWidth - 200;
	var topHeight = window.top.document.body.clientHeight - 100;

	var html = "<div id='"+dlgName+"'>";
	html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
			+ url + "'></iframe>";
	html += "</div>";

	var dlg = window.top.jQuery(html).appendTo(window.top.document.body);
	dlg.dialog({
		title : '添加数据',
		collapsible : false,
		draggable : false,
		resizable : false,
		minimizable : false,
		maximizable : false,
		cache : false,
		width : topWidth,
		height : topHeight,
		modal : true,
		onClose : function() {
			L.dg.datagrid('reload');
			dlg.dialog("destroy");
		}
	});
},
columnIsdel: function(){
	return {field: 'isdelStr', title: '状态', width: 60, align: 'center'};
},
columnImg: function(opt){
	return $.extend({field : 'img', title : '配图', width : 100, align : 'center', formatter: function(val){
		if(val) return '<img style="width:90px; height: 90px;" src="' + val + '" >';
	}}, opt);
},
columnHeadImg: function(opt){
	return $.extend({field : 'head', title : '头像', width : 60, align : 'center', formatter: function(val){
		if(val) return '<img style="width:60px; height: 60px;" src="' + val + '" >';
	}}, opt);
},
columnBase: function(cols){
	var arr = [{field: 'id', title: 'ID', width: 60, align: 'center'}];
	if(cols && cols.length){
		for(var i = 0; i < cols.length; i++){
			arr.push(cols[i]);
		}
	}
	arr.push({field: 'ctimeStr', title: '添加时间', width: 100, align: 'center'});
	arr.push({field: 'utimeStr', title: '更新时间', width: 100, align: 'center'});
	arr.push({field: 'isdelStr', title: '状态', width: 60, align: 'center'});
	return [arr];
},
loadUrl: function(url){
	window.location.href = url;
}
};







function addListDataTwo(url,dlgName) {
	//弹出窗口预览
	var topWidth = window.top.document.body.clientWidth - 200;
	var topHeight = window.top.document.body.clientHeight - 100;

	var html = "<div id='"+dlgName+"'>";
	html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
			+ url + "'></iframe>";
	html += "</div>";

	var dlg = window.top.jQuery(html).appendTo(
			window.top.document.body);
	dlg.dialog({
		title : '新增数据',
		collapsible : false,
		draggable : false,
		resizable : false,
		minimizable : false,
		maximizable : false,
		cache : false,
		width : topWidth,
		height : topHeight,
		modal : true,
		onClose : function() {
			dlg.dialog("destroy");
			window.location.href="$!link.getContextPath()/tc/list.do";
		}
	});
}

function addListDataThree(url,dlgName) {
	//弹出窗口预览
	var topWidth = window.top.document.body.clientWidth - 200;
	var topHeight = window.top.document.body.clientHeight - 100;

	var html = "<div id='"+dlgName+"'>";
	html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
			+ url + "'></iframe>";
	html += "</div>";

	var dlg = window.top.jQuery(html).appendTo(
			window.top.document.body);
	dlg.dialog({
		title : '添加数据',
		collapsible : false,
		draggable : false,
		resizable : false,
		minimizable : false,
		maximizable : false,
		cache : false,
		width : topWidth,
		height : topHeight,
		modal : true,
		onClose : function() {
			dlg.dialog("destroy");
			window.location.href="$!link.getContextPath()/tv/list.do";
		}
	});
}

function editListDataTwo(url,dlgName) {
	//弹出窗口预览
	var topWidth = window.top.document.body.clientWidth - 200;
	var topHeight = window.top.document.body.clientHeight - 100;

	var html = "<div id='"+dlgName+"'>";
	html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
			+ url + "'></iframe>";
	html += "</div>";

	var dlg = window.top.jQuery(html).appendTo(
			window.top.document.body);
	dlg.dialog({
		title : '添加数据',
		collapsible : false,
		draggable : false,
		resizable : false,
		minimizable : false,
		maximizable : false,
		cache : false,
		width : topWidth,
		height : topHeight,
		modal : true,
		onClose : function() {
			dlg.dialog("destroy");
			window.location.href="$!link.getContextPath()/tv/list.do";
		}
	});
}







function viewListData1(url, dlgName) {
	var selectedRow = dgChecked(); //获取选中行
	if (selectedRow) {
		//弹出窗口预览
		url += "?id="+ selectedRow.id;
		url += "&account="+ selectedRow.account;
		url += "&nickname="+ selectedRow.nickname;
		var topWidth = window.top.document.body.clientWidth - 200;
		var topHeight = window.top.document.body.clientHeight - 100;

		var html = "<div id='"+dlgName+"'>";
		html += "	<iframe id='"+dlgName+"' name='"+dlgName+"' width='100%' height='98%' frameborder='0' scrolling='auto' src='"
				+ url + "'></iframe>";
		html += "</div>";

		var dlg = window.top.jQuery(html).appendTo(
				window.top.document.body);
		dlg.dialog({
			title : '查看详情',
			collapsible : false,
			draggable : false,
			resizable : false,
			minimizable : false,
			maximizable : false,
			cache : false,
			width : topWidth,
			height : topHeight,
			modal : true,
			onClose : function() {
				dlg.dialog("destroy");
			}
		});
	} else {
		jQuery.messager.alert('温馨提示', '必须且仅能选择一条需要查看的数据！', 'warning');
	}
}


var MaskUtil = (function(){  
    var _mask,_maskMsg;
    var defMsg = '正在处理，请稍待。。。';  
    function init(){  
        if(!_mask){  
            _mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");  
        }  
        if(!_maskMsg){  
            _maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>").appendTo("body").css({'font-size':'12px'});  
        }  
        _mask.css({width:"100%",height:$(document).height()});  
        var scrollTop = $(document.body).scrollTop();  
        _maskMsg.css({  
            left:( $(document.body).outerWidth(true) - 190 ) / 2 ,top:( ($(window).height() - 45) / 2 ) + scrollTop  
        });          
    }
    return {  
        mask:function(msg){  
            init();  
            _mask.show();  
            _maskMsg.html(msg||defMsg).show();  
        }  
        ,unmask:function(){  
            _mask.hide();  
            _maskMsg.hide();  
        }  
    }  
}());