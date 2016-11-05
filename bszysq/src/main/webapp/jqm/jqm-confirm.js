$(function() {
	 $.extend($.mobile, {
		 ajaxEnabled: false,
		 allowCrossDomainPages: true
	 });
	 Dialog.init();
	 if(initComplete != undefined) {
		 initComplete();
	 }
});