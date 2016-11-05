var Dialog = {
		
	init: function() {
		$("#dia_loader").popup();
		$("#dia_alert").popup();
		$("#dia_confirm").popup();
	},
		
	alert: function(content, funOk){
		$("#dia_alert_content").html(content);
		$("#dia_alert_btn").one("click", function(){
			$("#dia_alert").popup("close");
			if(funOk != null && funOk != undefined)
			{
				funOk();
			}
		});
		$("#dia_alert").popup("open");
	},
	
	confirm: function(content, funOk, funCancel){
		$("#dia_confirm_content").html(content);
		$("#dia_confirm_btn_ok").one("click", function(){
			$("#dia_confirm").popup("close");
			if(funOk != null && funOk != undefined)
			{
				funOk();
			}
		});
		$("#dia_confirm_btn_cancel").one("click", function(){
			$("#dia_confirm").popup("close");
			if(funCancel != null && funCancel != undefined)
			{
				funCancel();
			}
		});
		$("#dia_confirm").popup("open");
	},
	
	showProcess: function(){
		$("#dia_loader").popup("open");
	},
	
	hideProcess: function(){
		$("#dia_loader").popup("close");
	}
}