package com.mao.init;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mao.ini.PathIniUtil;
import com.mao.lang.Timer;

/**
 * 项目工具类
 * @author mao
 * @version 2011-8-17 下午07:39:49
 */
@SuppressWarnings("rawtypes")
public class AppUtil implements ApplicationContextAware  {
	private static final Logger logger = LoggerFactory.getLogger(AppUtil.class);
	
	/** 单例 */
	static AppUtil appUtil = null;
	public static AppUtil getInst(){
		if(appUtil == null){
			appUtil = new AppUtil();
		}
		return appUtil;
	}
	
	private static ApplicationContext appContext;	//spring 上下文对象
	private static ServletContext servletContext = null;	//Servlet 上下文
	private static String classesPath;	//类文件根路径
	private static String rootPath;	//网站根路径
	private static String realPath;	//真实路径(物理路径)
	private static String appname;	//网站名称
	private static Map configMap = new HashMap();	//系统配置
	
	/** 初始 系统 */
	public void init(ServletContext in_servletContext) {
		logger.info("初始化系统");
		
		servletContext = in_servletContext;
		realPath = servletContext.getRealPath("/");
		classesPath = realPath + "WEB-INF/classes/";	//设置类根路径
		appname = servletContext.getContextPath();
		rootPath = appname + "/";
		appname = appname.length() == 0 ? appname : appname.substring(1);
		
		servletContext.setAttribute("__rootPath", rootPath);	//网站根路径
		servletContext.setAttribute("__realPath", realPath);	//真实路径(物理路径)
		
		logger.info("网站根路径:" + rootPath);
		logger.info("真实路径(物理路径)" + realPath);
		
		reloadSysConfig();
	}
	
	/** 设置spring 上下文对象, 由Spring注入 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
		if(appUtil == null) appUtil = this;
	}
	
	/**
	 * 通过 beanId获取 spring 中的配置的对象
	 * @param beanId (String) spring 配置文件中的对象名称
	 */
	public static Object getBean(String beanId) {
		if(beanTestMode){
			return getTestBean(beanId);
		}else{
			return appContext.getBean(beanId);
		}
	}
	
	/**
	 * 通过 bean类型获取 spring 中的配置的对象
	 * @param type (String) spring 配置文件中的对象类型
	 */
	public static <T> T getBean(Class<T> type) {
		if(beanTestMode){
			return getTestBean(type);
		}else{
			return appContext.getBean(type);
		}
	}
	
	/** 获取应用程序绝对路径(物理路径) */
	public static String getAppAbsolutePath() {
		return servletContext.getRealPath("/");
	}
	
	/** 重新加载系统配置信息, 被动调用, 可以将全局静态变量放在这里进行初始化，这是一个好方法 */
	public void reloadSysConfig() {
		logger.info("初始化path.ini");
		PathIniUtil.getConfig();
		
		Timer.s_isPrint = PathIniUtil.getConfig().isTrueConst("Timer_s_isPrint");
		logger.info("初始化时间工具:" + Timer.s_isPrint);
		
//		LogUtil.setLog_Level(PathsUtil.getIntConst("LogUtil_Log_Level", LogUtil.Exception));
		
		logger.info("初始化redis");
		//RedisUtil.getCacheInst().get("");
	}
	
	//--- get
	
	/** 获取 类文件根路径, 如: "E:\pro\java\tomcats\tomcat6-61\webapps\heifp\WEB-INF\classes\" */
	public static String getClassesPath(){
		return classesPath;
	}
	
	/** 获取 相对类文件根的路径, 如: "E:\pro\java\tomcats\tomcat6-61\webapps\heifp\WEB-INF\classes\" + path */
	public static String getClassesPath(String path){
		return classesPath + path;
	}
	
	/** 获取 Servlet 上下文 */
	public static ServletContext getServletContext(){
		return servletContext;
	}
	
	/** 获取 系统配置 */
	public static Map getSysConfig() {
		return configMap;
	}

	/** 网站根路径, 如: "/heifp/" */
	public static String getRootPath() {
		return rootPath;
	}
	
	/** 生成从网站根路径出发的路径 , 如: "/heifp/" + url */
	public static String getRootPath(String url) {
		return url == null ? rootPath : (rootPath + url);
	}
	
	/** 获取应用程序绝对路径(物理路径), 如: "E:\pro\java\tomcats\tomcat6-61\webapps\heifp\" */
	public static String getRealtPath() {
		return realPath;
	}
	
	/** 生成从物理根路径出发的路径, 如: "E:\pro\java\tomcats\tomcat6-61\webapps\heifp\" + url */
	public static String getRealtPath(String url) {
		return url == null ? realPath : (realPath + url);
	}

	/** 网站名称, 如: "heifp" */
	public static String getAppname() {
		return appname;
	}
	
	/** 是否生成js、css等文件url随机后缀 */
	public static boolean isBuildRndExt = true;
	
	/**
	 * 生成js、css等文件url随机后缀
	 * @return "?verRnd=222123123"
	 */
	public static String getRndExt(){
		return getRndExt(null, true);
	}
	/**
	 * 生成js、css等文件url随机后缀
	 * @param keyName (String) 默认键名"verRnd"
	 * @param isC (boolean) 调试时可以通过传false关闭
	 * @return "?verRnd=222123123"
	 */
	public static String getRndExt(String keyName, boolean isC){
		if(isBuildRndExt && isC){
			if(keyName == null || keyName.length() < 1) keyName = "verRnd";
			long t = new Date().getTime();
			keyName = "?" + keyName + "=" + t;
			return keyName;
		}
		return "";
	}
	
	// TODO -- 测试
	
	public static boolean beanTestMode = false;
	/** 测试时用来加载的Spring配置文件 */
//	public static String[] SprigConfig = new String[]{"classpath:applicationContext*.xml", "classpath:*/applicationContext*.xml"};
	public static String[] SprigConfig = new String[]{"ApplicationContext.xml"};
	private static ApplicationContext testCtx;
	/** 获取Spring的上下文 */
	public static ApplicationContext getTestCtx(){
		if(testCtx == null){
			testCtx = new ClassPathXmlApplicationContext(SprigConfig);
		}
		return testCtx;
	}
	/** 获取Spring中的Bean */
	public static Object getTestBean(String bean){
		Object o = null; 
		try {
			o =  getTestCtx().getBean(bean);
		} catch (BeansException e) {
			e.printStackTrace();
			testCtx = null;	//出错再尝试一次
			o =  getTestCtx().getBean(bean);
		}
		return o;
	}
	/** 获取Spring中的Bean */
	public static <T> T getTestBean(Class<T> type){
		T o = null; 
		try {
			o =  getTestCtx().getBean(type);
		} catch (BeansException e) {
			e.printStackTrace();
			testCtx = null;	//出错再尝试一次
			o =  getTestCtx().getBean(type);
		}
		return o;
	}
	
	public static void main(String[] args) {
//		Object ud = getTestBean("userInfoDao");
//		UserInfoDao uiDao = (UserInfoDao)ud;
//		List list = uiDao.findBySqlWithEntity("select pi.* from user_info pi", UserInfo.class);
//		System.out.println(list);
	}
	
}
