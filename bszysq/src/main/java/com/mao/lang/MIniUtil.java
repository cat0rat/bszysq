package com.mao.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ini文件 读写工具类
 * @author Mao 2014-5-21 上午9:39:34
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MIniUtil {
	/** 配置信息对象 */
	private MIni prop;
	/** true: 已加载了配置文件 */
	private boolean isLoaded;
	/** 配置文件 */
	private String propFile;
	/** 错误信息 */
	private String error;

	// TODO 构造器
	public MIniUtil() {
	};

	public MIniUtil(String propFile) {
		this.propFile = propFile;
	};

	// TODO 常用配置值获取方式
	
	/** 获取值 */
	public String getKey(String key) {
		return getValue(key);
	}

	/** 设置值, value=null则不保存到ini文件 */
	public Object setKey(String key, String value) {
		return setValue(key, value);
	}

	/** 获取配置对应值 */
	public String getValue(String key){
		String value = null;
		if (init()) {
			value = getProp().get(key);
		}
		return value;
	}
	
	/** 设置值, value=null则不保存到ini文件 */
	public Object setValue(String key, String value) {
		return getProp().put(key, value);
	}
	
	/** 获取配置对应值 */
	public String getValue(String key, String def){
		String val = getValue(key);
		if(val == null || val.isEmpty()) return def;
		return val;
	}
	
	/** 返回配置文件中的常量key是不是true, 如果是true返回true, 否则返回false */
	public boolean isTrueConst(String key){
		return "true".equalsIgnoreCase(getValue(key));
	}
	/** 返回配置文件中的常量key是不是false, 如果是false返回true, 否则返回false */
	public boolean isFalseConst(String key){
		return "false".equalsIgnoreCase(getValue(key));
	}
	/** 获取整型常量值 */
	public Integer getIntegerConst(String key){
		String s = getValue(key);
		Integer i = MUtil.toInteger(s);
		if(i == null){
			System.out.println("获取整型常量值[" + key + "]出错!");
		}
		return i;
	}
	/** 获取整数常量值 */
	public int getIntConst(String key, int def){
		String s = getValue(key);
		def = MUtil.toInt(s, def);
		return def;
	}
	/** 获取小数常量值 */
	public Double getDoubleConst(String key){
		String s = getValue(key);
		Double d = MUtil.toDouble(s);
		if(d == null){
			System.out.println("获取小数常量值 [" + key + "]出错!");
		}
		return d;
	}
	/** 获取小数常量值 */
	public double getdoubleConst(String key, double def){
		String s = getValue(key);
		def = MUtil.todouble(s, def);
		return def;
	}
	
	// TODO 方法

	/** 获取所有键名 */
	public String[] getKeys() {
		String[] sa = getProp().keyArr();
		return sa;
	}

	/** 返回 Map&lt;键名, 值> */
	public Map toMap() {
		Map map = null;
		if (init()) {
			map = getProp().toMap();
		}
		return map;
	}
	
	/** 返回 Map&lt;键名, 值> */
	public Map<String, String> toMap(String[] keys){
		Map map = null;
		if (init()) {
			map = getProp().toMap(keys);
		}
		return map;
	}

	/** 返回 有序的关键字集合 */
	public List getKeyList() {
		List list = null;
		if (init()) {
			list = getProp().getKeyList();
		}
		return list;
	}

	/** 将Map的键值对翻转, 返回一个新的Map */
	public static Map reMapKv(Map map) {
		Map nm = new HashMap();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			Object k = iter.next();
			Object v = map.get(k);
			nm.put(v, k);
		}
		return nm;
	}

	/** 初始化 */
	public synchronized boolean init() {
		if (isLoaded) {
			return true;
		} else {
			return load(propFile);
		}
	}

	/**
	 * 加载 配置文件
	 * @param file (String) 配置文件
	 */
	public boolean load(String file) {
		try {
			File f = new File(file);
			getProp().load(f);
			isLoaded = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error = "配置文件加载失败!";
		}
		return false;
	}
	
	/**
	 * 加载 缓存读取器
	 * @param br (BufferedReader)
	 */
	public boolean load_br(BufferedReader br){
		try {
			getProp().load_br(br);
			isLoaded = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error = "缓存读取器加载失败!";
		}
		return false;
	}
	
	/**
	 * 加载 字符串
	 * @param str (String) 内容
	 */
	public boolean load_str(String str) {
		try {
			getProp().load_str(str);
			isLoaded = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			error = "字符串加载失败!";
		}
		return false;
	}


	/** 写入到文件 */
	public boolean flush() {
		return save();
	}

	/** 保存到文件 */
	public boolean save() {
		return saveAs(propFile);
	}

	/** 另存为 */
	public boolean saveAs(String other) {
		try {
			getProp().saveAs(new File(other));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** 开启Debug模式, 将key的后缀为debugKeyExt的值设置为主要值如: "ab_Debug_": 12 --> "ab":12 */
	public int openDebugKey(String debugKeyExt){
		return getProp().openDebugKey(debugKeyExt);
	}
	
	/**
	 * 配置符号, null:则保存默认
	 * @param note_char (Character) 注释前缀符, 默认#
	 * @param data_split (Character) 数据分割符, 默认=
	 * @param type_split_s (Character) 类型开始符, 默认[
	 * @param type_split_e (Character) 类型结束符, 默认]
	 * @param _r (Character) 换行符, 默认\r\n
	 * @param charset (Character) 字符编码, 默认UTF-8
	 */
	public void cfg_sign(Character note_char, Character data_split, Character type_split_s, Character type_split_e, String _r, String charset){
		getProp().cfg_sign(note_char, data_split, type_split_s, type_split_e, _r, charset);
	}

	// TODO get/set方法
	
	public MIni getProp() {
		if (prop == null) {
			prop = new MIni();
			isLoaded = false;
		}
		return prop;
	}

	public void setProp(MIni prop) {
		this.prop = prop;
	}

	public String getPropFile() {
		return propFile;
	}

	public void setPropFile(String propFile) {
		this.propFile = propFile;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	// --- 测试
	public static void main(String[] args) throws Exception {
		String file = "E:\\shanhongWeb\\HljRatData\\test\\path.ini2";
		MIniUtil pu = new MIniUtil(file);
		pu.init();
		pu.flush();
	}
}
