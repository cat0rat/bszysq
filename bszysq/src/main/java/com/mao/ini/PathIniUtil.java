package com.mao.ini;

import com.mao.lang.MIniUtil;

/**
 * 从配置文件中获取SQL
 * @author Mao 2015-7-7 下午5:21:20
 */
public class PathIniUtil {
	// --- 配置信息操作
	/** 配置信息文件 */
	public static String configFile = "/path.ini";
	/** 配置信息对象 */
	public static MIniUtil config;
	private final static Boolean config_sync = new Boolean(true);

	/** 获取配置 */
	public static MIniUtil getConfig() {
		if (config == null) {
			synchronized (config_sync) {
				config = new MIniUtil(getRealPath(configFile));
				String dkey = config.getKey("C_OpenDebugKey"); // "_Debug_"
				config.openDebugKey(dkey);
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
		return config.saveAs(getRealPath(configFile));
	}

	/** 另存为 属性配置文件 */
	public static boolean saveConfAs(String other) {
		return config.saveAs(other);
	}
	
	/** 开启 线上模式 */
	public static void openOnline(){
		getConfig().setValue("C_OpenDebugKey", "");
		getConfig().save();
		reLoadConfig();
	}
	
	/** 开启 测试模式 */
	public static void openDebug(){
		getConfig().setValue("C_OpenDebugKey", "_Debug_");
		getConfig().save();
		reLoadConfig();
	}
	
	/** 开启 本地测试模式 */
	public static void openLDebug(){
		getConfig().setValue("C_OpenDebugKey", "_Debug_,_LDebug_");
		getConfig().save();
		reLoadConfig();
	}

	/** 开启 本地上线模式 */
	public static void openODebug(){
		getConfig().setValue("C_OpenDebugKey", "_LDebug_,_ODebug_");
		getConfig().save();
		reLoadConfig();
	}
	
	// TODO 方法
	
	/** 获取类根路径 */
	public static String getClassRoot(){
		String path = PathIniUtil.class.getResource("/").getPath();
		if(path != null && path.length() > 1){
			if(path.indexOf(':') > 0){
				return path.substring(1);
			}
			return path;
		}
		return "/";
	}
	
	/** 获取真实的路径 */
	public static String getRealPath(String path){
		if(path.charAt(0) == '/'){
			path = getClassRoot() + path.substring(1);
		}else if(path.charAt(1) != ':'){
			path = getClassRoot() + path;
		}
		return path;
	}
	
	/**获取当前项目在Tomcat下根路径，格式为：E:/workspace/jee_workspace/heifp/*/
	public static String getProjectRoot(){
		String root = PathIniUtil.getClassRoot();
		return root.substring(0, root.indexOf("WEB-INF/classes/"));
	}
}
