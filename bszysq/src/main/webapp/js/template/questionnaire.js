var basePath = "";
var version = 0;

function init(_basePath) {
	basePath = _basePath;
	MaskUtil.mask();
	var url = basePath + "template/questionnaire/getTemplate.do";
	var params = "_=" + Math.random();
	$.ajax({
		url: url,
		data: params,
		type: "post",
		success: function(data) {
			MaskUtil.unmask();
			ButtonController.bind();
			AddRowDialogController.bind();
			AddValueDialogController.bind();
			var json = $.parseJSON(data);
			version = parseInt(json.version, 10);
			$("#pnlTitle").panel("body").html("当前模板版本：" + version);
			if(version < 1) {
				return;
			}
			var jrows = json.rows;
			for(var m = 0; m < jrows.length; m++) {
				var jrow = jrows[m];
				var row = new Row(jrow.type);
				row.limit = parseInt(jrow.limit, 10);
				row.nullAble = jrow.nullable == "1";
				row.showTitle = jrow.showtitle == "1";
				row.title = jrow.title;
				var jvalues = jrow.values;
				for(var n = 0; n < jvalues.length; n++) {
					var jvalue = jvalues[n];
					var value = new RowValue(row);
					value.index = row.valueIndex;
					row.valueIndex++;
					value.text = jvalue.text;
					value.isDefault = jvalue.isDefault == "1";
					row.values.push(value);
				}
				RowController.addRow(row);
			}
		}
	});
}

var ButtonController = {
	
	bind: function() {
		var btns = $(".buttonrow").find("a");
		btns.each(function(index, ele) {
			var btn = $(ele);
			if(btn.attr("iconCls") == "icon-save") {
				btn.click(function() {
					ButtonController.onSave();
				});
			} else {
				btn.click(function() {
					ButtonController.onAdd();
				});
			}
		});
	},
	
	onSave: function() {
		RowController.checkAndSave();
	},
	
	onAdd: function() {
		AddRowDialogController.show();
	}
};

var AddRowDialogController = {
		
	dialog: null,
	select: null,
	
	bind: function() {
		AddRowDialogController.dialog = $("#win_addrow");
		AddRowDialogController.select = AddRowDialogController.dialog.find("select");
		AddRowDialogController.select.append($('<option value="' + RowType.TEXT.id + '">' + RowType.TEXT.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.TEXTBOX.id + '">' + RowType.TEXTBOX.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.CHECKBOX.id + '">' + RowType.CHECKBOX.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.RADIOBUTTON.id + '">' + RowType.RADIOBUTTON.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.SELECT.id + '">' + RowType.SELECT.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.DATEBOX.id + '">' + RowType.DATEBOX.name + '</option>'));
		AddRowDialogController.select.append($('<option value="' + RowType.TEXTAREA.id + '">' + RowType.TEXTAREA.name + '</option>'));
		AddRowDialogController.dialog.find("a").each(function(index, ele) {
			var btn = $(ele);
			var icon = btn.attr("iconCls");
			if(icon == "icon-add") {
				btn.click(function() {
					AddRowDialogController.onAdd();
				});
			} else if(icon == "icon-back") {
				btn.click(function() {
					AddRowDialogController.close();
				});
			}
		});
	},
	
	show: function() {
		AddRowDialogController.dialog.window("open");
		AddRowDialogController.dialog.window("center");
	},
	
	onAdd: function() {
		var row = new Row(AddRowDialogController.select.val());
		RowController.addRow(row);
		AddRowDialogController.close();
	},
	
	close: function() {
		AddRowDialogController.dialog.window("close");
	}
};

var AddValueDialogController = {
	
	dialog: null,
	currentRow: null,
	
	bind: function() {
		AddValueDialogController.dialog = $("#win_addvalue");
		AddValueDialogController.dialog.find("a").each(function(index, ele) {
			var btn = $(ele);
			var icon = btn.attr("iconCls");
			if(icon == "icon-add") {
				btn.click(function() {
					AddValueDialogController.onAdd();
				});
			} else if(icon == "icon-back") {
				btn.click(function() {
					AddValueDialogController.close();
				});
			}
		});
	},
	
	show: function(row) {
		AddValueDialogController.currentRow = row;
		AddValueDialogController.dialog.find("input").each(function(index, ele) {
			var con = $(ele);
			if(con.attr("type") == "text") {
				con.val("");
			} else {
				ele.checked = false;
			}
		});
		AddValueDialogController.dialog.window("open");
		AddValueDialogController.dialog.window("center");
	},
	
	close: function() {
		AddValueDialogController.currentRow = null;
		AddValueDialogController.dialog.window("close");
	},
	
	onAdd: function() {
		var text = null;
		var def = false;
		AddValueDialogController.dialog.find("input").each(function(index, ele) {
			var con = $(ele);
			if(con.attr("type") == "text") {
				text = con.val();
			} else {
				def = ele.checked;
			}
		});
		if($.trim(text) == "") {
			$.messager.alert("温馨提示", "值不能为空", "info");
			return;
		}
		AddValueDialogController.currentRow.addRowValue(text, def);
		AddValueDialogController.close();
	}
};

var RowController = {
	
	rowIndex: 1,
	rows: new Array(),
	
	addRow: function(row) {
		row.index = RowController.rowIndex;
		row.draw();
		RowController.rows.push(row);
		RowController.rowIndex++;
	},
	
	deleteRow: function(index) {
		RowController.rowIndex = 1;
		var temp = new Array();
		for(var i = 0; i < RowController.rows.length; i++) {
			var row = RowController.rows[i];
			if(row.index == index) {
				row.destroy();
				continue;
			}
			row.resetIndex(RowController.rowIndex);
			temp.push(row);
			RowController.rowIndex++;
		}
		RowController.rows = temp;
	},
	
	checkAndSave: function() {
		var len = RowController.rows.length;
		if(len == 0) {
			$.messager.alert("温馨提示", "模板中应该至少拥有1行。", "error");
			return;
		}
		var errors = null;
		var errorIndex = 1;
		for(var i = 0; i < len; i++) {
			var row = RowController.rows[i];
			var error = row.checkAndBind();
			if(error == null) {
				continue;
			}
			if(errors == null) {
				errors = "检测到如下错误：";
			}
			errors += "<br/>" + errorIndex + "." + error;
			errorIndex++;
		}
		if(errors != null) {
			$.messager.alert("温馨提示", errors);
			return;
		}
		$.messager.confirm("温馨提示", "您要保存并发布新版本的调查问卷模板吗？", function(r) {
			if(!r) {
				return;
			}
			RowController.doSaveRows();
		});
	},
	
	doSaveRows: function() {
		MaskUtil.mask();
		var json = {};
		json.version = version;
		var datas = [];
		for(var i = 0; i < RowController.rows.length; i++) {
			var row = RowController.rows[i];
			var data = row.toJSON();
			datas.push(data);
		}
		json.datas = datas;
		var url = basePath + "template/questionnaire/addTemplate.do";
		var params = "jdatas=" + encodeURIComponent(JSON.stringify(json));
		$.ajax({
			url: url,
			data: params,
			type: "post",
			success: function(data) {
				MaskUtil.unmask();
				var json = $.parseJSON(data);
				if(json.res == "N") {
					$.messager.alert("温馨提示", json.mes, "error");
					return;
				}
				$("#pnlTitle").panel("body").html("当前模板版本：" + json.version);
				version = parseInt(json.version, 10);
				$.messager.alert("温馨提示", "新版本发布成功，版本号为" + json.version + "。", "info");
			}
		});
	}
};

function Row(_type) {
	
	this.index = 0;
	this.valueIndex = 0;
	this.type = parseInt(_type, 10);
	this.title = "";
	this.showTitle = true;
	this.nullAble = true;
	this.limit = 0;
	this.boxCon = null;
	this.valueContainer = null;
	this.values = new Array();
	
	this.addRowValue = function(text, isDefault) {
		var value = new RowValue(this);
		value.text = text;
		value.isDefault = isDefault;
		value.index = this.valueIndex;
		value.draw();
		this.valueIndex++;
		this.values.push(value);
	};
	
	this.deleteRowValue = function(index) {
		var temp = new Array();
		for(var i = 0; i < this.values.length; i++) {
			var value = this.values[i];
			if(value.index == index) {
				value.destroy();
				continue;
			}
			temp.push(value);
		}
		this.values = temp;
	},
	
	this.draw = function() {
		this.boxCon = $('<div class="easyui-panel" style="padding: 10px; width: 700px;" data-options="style:{marginTop:10,marginLeft:10}"></div>');
		var cons = $(".easyui-panel");
		var con = $(cons[cons.length - 1]);
		var title = "第" + this.index + "行[" + RowType.getName(this.type) + "]";
		$(".rowcontainer").append(this.boxCon);
		var thisObject = this;
		this.boxCon.panel({
			title: title,
			tools: [
			        {
			        	iconCls: "icon-cancel",
			        	handler: function() {
			        		$.messager.confirm("温馨提示", "您确定要删除选中的行吗？", function(r) {
			    				if(!r) {
			    					return;
			    				}
			    				RowController.deleteRow(thisObject.index);
			    			});
			        	}
			        }
			       ]
		});
		this.addBody();
	};
	
	this.addBody = function() {
		var table = $('<table border="0" style="width: 100%;"></table>');
		var titleInput = $('<input type="text" style="width: 220px;" inputType="title" />');
		titleInput.val(this.title);
		var tr = $('<tr>');
		tr.append($('<td width="60" align="right">行标题：</td>'));
		var td = $('<td>');
		td.append(titleInput);
		tr.append(td);
		tr.append($('<td width="60" align="right">字符限制：</td>'));
		td = $('<td>');
		var limitInput = $('<input type="text" style="width: 60px;" inputType="limit" />');
		limitInput.val(this.limit);
		if(this.type != RowType.TEXTBOX.id && this.type != RowType.TEXTAREA.id) {
			limitInput.attr("disabled", "disabled");
		}
		td.append(limitInput);
		tr.append(td);
		td = $('<td>');
		td.html("&nbsp;&nbsp;");
		var label = $('<label>');
		var nullAbleInput = $('<input type="checkbox" inputType="nullable" />');
		if(this.nullAble) {
			nullAbleInput.attr("checked", "checked");
		}
		if(this.type == RowType.TEXT.id) {
			nullAbleInput.attr("disabled", "disabled");
		}
		label.append(nullAbleInput);
		label.append($("<span>准许不填写</span>"));
		td.append(label);
		tr.append(td);
		td = $('<td>');
		td.html("&nbsp;&nbsp;");
		label = $('<label>');
		var showTitleInput = $('<input type="checkbox" inputType="showtitle" />');
		if(this.showTitle) {
			showTitleInput.attr("checked", "checked");
		}
		label.append(showTitleInput);
		label.append($("<span>显示标题</span>"));
		td.append(label);
		tr.append(td);
		table.append(tr);
		this.boxCon.panel("body").append(table);
		this.addBodyForType();
	};
	
	this.addBodyForType = function() {
		this.valueContainer = $('<table border="0" style="width: 100%;"></table>');
		this.boxCon.panel("body").append(this.valueContainer);
		if(this.type == RowType.TEXT.id || this.type == RowType.TEXTAREA.id) {
			var tr = $('<tr>');
			tr.append($('<td width="60" align="right">默认值：</td>'));
			var td = $('<td>');
			var textarea = $('<textarea style="width: 100%; height: 80px;" inputType="defaultvalue"></textarea>');
			if(this.values.length > 0) {
				textarea.val(this.values[0].text);
			}
			td.append(textarea);
			tr.append(td);
			this.valueContainer.append(tr);
		} else if(this.type == RowType.TEXTBOX.id || this.type == RowType.DATEBOX.id) {
			var tr = $('<tr>');
			tr.append($('<td width="60" align="right">默认值：</td>'));
			var td = $('<td>');
			var input = $('<input type="text" style="width: 220px;" inputType="defaultvalue" />');
			if(this.values.length > 0) {
				input.val(this.values[0].text);
			}
			td.append(input);
			tr.append(td);
			tr.append($('<td width="60" align="center">&nbsp;</td>'));
			this.valueContainer.append(tr);
		} else if(this.type == RowType.RADIOBUTTON.id || this.type == RowType.CHECKBOX.id || this.type == RowType.SELECT.id) {
			var tr = $('<tr>');
			tr.append($('<td width="60" align="right">值列表：</td>'));
			var td = $('<td>');
			var btnAdd = $('<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:\'icon-add\'">添加值</a>');
			var thisObject = this;
			btnAdd.click(function() {
				AddValueDialogController.show(thisObject);
			});
			btnAdd.linkbutton();
			td.append(btnAdd);
			tr.append(td);
			this.valueContainer.append(tr);
			for(var i = 0; i < this.values.length; i++) {
				var value = this.values[i];
				value.draw();
			}
		} else {
			alert("ERROR:行类型不正确");
			return;
		}
	};
	
	this.resetIndex = function(index) {
		this.index = index;
		var title = "第" + this.index + "行[" + RowType.getName(this.type) + "]";
		this.boxCon.panel("setTitle", title);
	};
	
	this.destroy = function() {
		this.boxCon.panel("destroy");
	};
	
	//行校验并绑定数据,发生任何错误返回错误描述.
	this.checkAndBind = function() {
		var title = $.trim(this.boxCon.find("input[inputType='title']").val());
		if(title == "") {
			return "第" + this.index + "的标题不能为空。";
		}
		var limit = this.boxCon.find("input[inputType='limit']").val();
		var climit = /^\d+$/;
		if(!climit.test(limit)) {
			return "第" + this.index + "的字符数限制只能是0或大于0的正整数。";
		}
		this.title = title;
		this.limit = parseInt(limit, 10);
		var ncon = this.boxCon.find("input[inputType='nullable']:checked");
		this.nullAble = ncon.length > 0;
		var scon = this.boxCon.find("input[inputType='showtitle']:checked");
		this.showTitle = scon.length > 0;
		if(this.type == RowType.TEXT.id || this.type == RowType.TEXTBOX.id || this.type == RowType.TEXTAREA.id || this.type == RowType.DATEBOX.id) {
			var defaultVal = null;
			if(this.type == RowType.TEXT.id || this.type == RowType.TEXTAREA.id) {
				defaultVal = $.trim(this.boxCon.find("textarea[inputType='defaultvalue']").val());
			} else {
				defaultVal = $.trim(this.boxCon.find("input[inputType='defaultvalue']").val());
			}
			if(this.type == RowType.TEXT.id && defaultVal == "") {
				return "第" + this.index + "为文本类型，必须填写默认值。";
			}
			if(this.type == RowType.DATEBOX.id && defaultVal != "") {
				var cdate = /^\d\d\d\d-\d\d-\d\d$/;
				if(!cdate.test(defaultVal)) {
					return "第" + this.index + "为日期类型，格式必须为yyyy-MM-dd，如2015-01-21。";
				}
			}
			if(this.values.length == 0) {
				var value = new RowValue(this);
				value.text = defaultVal;
				value.isDefault = true;
				this.values.push(value);
			} else {
				this.values[0].text = defaultVal;
			}
		} else if(this.type == RowType.SELECT.id || this.type == RowType.RADIOBUTTON.id || this.type == RowType.CHECKBOX.id) {
			if(this.values.length == 0) {
				return "第" + this.index + "至少要设置一个值。";
			}
			if(this.type == RowType.SELECT.id || this.type == RowType.RADIOBUTTON.id) {
				var snum = 0;
				for(var i = 0; i < this.values.length; i++) {
					var value = this.values[i];
					if(value.isDefault) {
						snum++;
					}
					if(snum > 1) {
						return  "第" + this.index + "是单选框或下拉框类型，只能有一个选中的值。"
					}
				}
			}
		} else {
			return "第" + this.index + "为未知的行类型。"
		}
		return null;
	};
	
	this.toJSON = function() {
		var rowdata = {};
		rowdata.type = this.type;
		rowdata.title = this.title;
		rowdata.nullable = this.nullAble;
		rowdata.showtitle = this.showTitle;
		rowdata.limit = this.limit;
		var rowvalues = [];
		for(var i = 0; i < this.values.length; i++) {
			var value = this.values[i];
			var rowvalue = {};
			rowvalue.text = value.text;
			rowvalue.isDefault = value.isDefault;
			rowvalues.push(rowvalue);
		}
		rowdata.values = rowvalues;
		return rowdata;
	};
};

function RowValue(_row) {
	
	this.index = 0;
	this.row = _row;
	this.text = "";
	this.isDefault = false;
	this.con = null;
	
	this.draw = function() {
		this.con = $('<tr>');
		this.con.append($('<td align="right">可选值：</td>'));
		var val = this.text;
		if(this.isDefault) {
			val += "[选中]";
		}
		this.con.append($('<td>' + val + '</td>'));
		var td = $('<td align="center"></td>');
		var btn = $('<a href="javascript:void(0);" style="color: #cc0000;">删除</a>');
		var thisObject = this;
		btn.click(function() {
			$.messager.confirm("温馨提示", "您确定要删除选中的值吗？", function(r) {
				if(!r) {
					return;
				}
				thisObject.row.deleteRowValue(thisObject.index);
			});
		});
		td.append(btn);
		this.con.append(td);
		this.row.valueContainer.append(this.con);
	};
	
	this.destroy = function() {
		this.con.die();
		this.con.remove();
	};
};

//1.文本 2.输入框 3.复选框 4.单选框 5.下拉菜单 6.日期 7.文本域
var RowType = {
	
	TEXT: {id: 1, name: "文本"},
	TEXTBOX: {id: 2, name: "文本框"},
	CHECKBOX: {id: 3, name: "复选框"},
	RADIOBUTTON: {id: 4, name: "单选框"},
	SELECT: {id: 5, name: "下拉框"},
	DATEBOX: {id: 6, name: "日期框"},
	TEXTAREA: {id: 7, name: "文本域"},
	
	getName: function(id) {
		if(id == RowType.TEXT.id) {
			return RowType.TEXT.name;
		}
		if(id == RowType.TEXTBOX.id) {
			return RowType.TEXTBOX.name;
		}
		if(id == RowType.CHECKBOX.id) {
			return RowType.CHECKBOX.name;
		}
		if(id == RowType.RADIOBUTTON.id) {
			return RowType.RADIOBUTTON.name;
		}
		if(id == RowType.SELECT.id) {
			return RowType.SELECT.name;
		}
		if(id == RowType.DATEBOX.id) {
			return RowType.DATEBOX.name;
		}
		if(id == RowType.TEXTAREA.id) {
			return RowType.TEXTAREA.name;
		}
		return "";
	}
};

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