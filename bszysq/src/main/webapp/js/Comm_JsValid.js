jQuery.extend(jQuery.fn.validatebox.defaults.rules, { 
	idcard:{
		validator: function(value, param){
			var button=false;
			var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
			var isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/; 
			if(isIDCard1.test(value)){
				button=true;
			}
			if(isIDCard2.test(value)){
				button=true;
			}
			//(/^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2]\d)|(3[0|1]))\d{4}$/).test(value)
			return button;
		},
		message:'请输入正确的身份证号'
	},
	phone:{
		validator: function(value, param){
			return (/^0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}$/).test(value);
		},
		message:'请输入正确的座机号码'
	},
    mobile: {  
        validator: function(value,param){  
        	return (/^1[3458][0-9]\d{8}$/).test(value);
        	//(/^(\d{3,4}-\d{7,8})|(\d{11})$/).test(value); 
        },  
        message: '请输入正确的11位手机号码'  
    },
    illChart: {
    	validator: function(value,param){  
        	return !(/^['"()]+$/).test(value); 
        },  
        message: '内容不能包含有半角单引号、双引号、左右括号，请使用全角符号.'
    },
    equals: {
    	validator: function(value,param){  
        	 return value == $(param[0]).val(); 
        },  
        message: '两次输入的值不一样.'
    },
    isEnglish: {
    	validator: function(value,param){  
        	 return /^[A-Za-z]+$/.test(value); 
        },  
        message: '该输入项由字母组成.'
    },
    isAPKFile: {
    	validator: function(value,param){  
        	 return /apk$/i.test(value);
        },  
        message: '版本文件输入项必须为APK文件'
    } 
});