
function formatterdate(val,rec, row) {
	alert(val);
	var date = new Date(val);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
}

var Common = {

	// EasyUI用DataGrid用日期格式化
	TimeFormatter : function(value, rec, index) {
		if (value == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		value = value.substr(1, value.length - 2);
		var obj = eval('(' + "{Date: new " + value + "}" + ')');
		var dateValue = obj["Date"];
		if (dateValue.getFullYear() < 1900) {
			return "";
		}
		var val = dateValue.format("yyyy-mm-dd HH:MM");
		return val.substr(11, 5);
	},
	DateTimeFormatter : function(value, rec, index) {
		if (value == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		value = value.substr(1, value.length - 2);
		var obj = eval('(' + "{Date: new " + value + "}" + ')');
		var dateValue = obj["Date"];
		if (dateValue.getFullYear() < 1900) {
			return "";
		}

		return dateValue.format("yyyy-mm-dd HH:MM");
	},

	// EasyUI用DataGrid用日期格式化
	DateFormatter : function(value, rec, index) {
		if (value == undefined) {
			return "";
		}
		/* json格式时间转js时间格式 */
		value = value.substr(1, value.length - 2);
		var obj = eval('(' + "{Date: new " + value + "}" + ')');
		var dateValue = obj["Date"];
		if (dateValue.getFullYear() < 1900) {
			return "";
		}

		return dateValue.format("yyyy-mm-dd");
	}
};