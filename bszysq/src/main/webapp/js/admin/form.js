var F = {
	formSelor: '#data_form',
	dialogSelor: '',	// '#edit_arttag'
	dialogClose: function() {
		top.$(F.dialogSelor).dialog('close');
	},
	updateUrl: '',	// '/admin/arttag/update.json'
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
	}
};
