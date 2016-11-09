package com.mao.ssm;

import com.mao.lang.MUtil;

/**
 * 表单验证
 * @author Mao 2016年10月30日 下午11:52:05
 */
public class FormValid {
	
	public static boolean isEmpty(String val){
		return val == null || val.length() == 0;
	}
	
	public static boolean len(String val, int len){
		return val != null && val.length() == len;
	}
	
	public static boolean range(String val, int min, int max){
		int len = val == null ? 0 : val.length();
		return len >= min && len <= max;
	}
	public static boolean rangeAllowNull(String val, int min, int max){
		if(val == null || val.length() == 0) return true;
		int len = val.length();
		return len >= min && len <= max;
	}
	
	public static boolean isIds(String ids){
		return ids != null && ids.length() > 0;
	}
	
	public static boolean isId(String val){
		return MUtil.isId(val);
	}
	public static boolean isId(Long val){
		return MUtil.isId(val);
	}
	
	public static boolean isMobile(String val){
		return MUtil.isMobile(val);
	}
}
