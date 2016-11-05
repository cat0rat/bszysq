var AfTool = {
	
	/**
	 * 用于ajax提交表单中的图片格式
	 */
	checkImg: function(inputValue){
		var cimg = /.*\.(jpg|jpeg|png|JPG|JPEG|PNG)/;
		return cimg.test(inputValue);
	},
	
	/**
	 * 用于修复IE10以下版本ajax提交表单返回的字符串（只限于JSON格式）
	 */
	fixResponseText: function(responseText){
		var text = responseText;
		if(responseText != null && responseText != undefined && responseText.indexOf("{") != 0)
		{
			text = responseText.substring(responseText.indexOf("{"), responseText.indexOf("}") + 1);
		}
		return text;
	},
	
	/**
	 * 尝试将ajax提交表单返回的字符串转换为JSON对象，失败返回null
	 */
	tryParseJSON: function(responseText){
		var text = AfTool.fixResponseText(responseText);
		try
		{
			return $.parseJSON(text);
		}
		catch(e)
		{
			return null;
		}
	}
};