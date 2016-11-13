package com.mao.ini;

import com.mao.lang.MIniUtil;

/**
 * 从配置文件中获取错误码
 * @author Mao 2016年11月12日 下午10:12:54
 */
public class ErrorCodeUtil {
	// --- 配置信息操作
	/** 配置信息文件 */
	public static String configFile = "/config/error_code.ini";
	/** 配置信息对象 */
	public static MIniUtil config;
	/** 获取配置 */
	public static MIniUtil getConfig() {
		if (config == null) {
			synchronized (ErrorCodeUtil.class) {
				config = new MIniUtil(PathIniUtil.getRealPath(configFile));
			}
		}
		return config;
	}

	/** 重新加载配置文件 */
	public static void reLoadConfig() {
		config = null;
		getConfig();
	}

	/** 保存 属性配置文件 */
	public static boolean saveConf() {
		return config.saveAs(PathIniUtil.getRealPath(configFile));
	}

	/** 另存为 属性配置文件 */
	public static boolean saveConfAs(String other) {
		return config.saveAs(other);
	}
	
}
