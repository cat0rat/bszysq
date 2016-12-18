package com.mao.ssm;

import com.mao.ini.PathIniUtil;
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
	
	/** 字符串长度在{min, max}之间(包含) */
	public static boolean len(String val, int min, int max){
		int len = val == null ? 0 : val.length();
		return len >= min && len <= max;
	}
	/** 是否为指定的长度, 允许为null或"" */
	public static boolean lenAllowNull(String val, int min, int max){
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
	
	/**
	 * 检测 两个值是否相等, 都为null也算不相等
	 * @param v0 (Object)
	 * @param v1 (Object)
	 * @return 相等返回true
	 */
	public static boolean eq(Object v0, Object v1){
		return v0 != null && v0.equals(v1);
	}
	
	/** val为null或""时, 返回def */
	public static String emptyDef(String val, String def){
		return val != null && val.length() != 0 ? val : def;
	}
	
	/** val为null或""时, 返回用户头像配置值 */
	public static String userHeadEmptyDef(String val){
		return val != null && val.length() != 0 ? val : PathIniUtil.getConfig().getValue("user_head");
	}
}
