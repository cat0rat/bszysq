package com.mao.lang;

import java.awt.Image;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 杂项 工具类
 * @author mao 2012-8-3 下午09:52:32
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MUtil extends StringUtil {

	/** 数组不为Null, 且有元素 */
	public static boolean isNotEmpty(Object[] os){
		if(os != null && os.length > 0){
			return true;
		}
		return false;
	}
	
	/** 包含 */
	public static boolean contains(Map map, Object key){
		if((key instanceof String)){
			key =((String)key).trim();
		}
		if(map.containsKey(key)){
			return true;
		}
		return false;
	}
	
	/**
	 * 返回指定键的值
	 * @param map (Map) 映射集
	 * @param key (Object) 键
	 */
	public static Object get(Map map, Object key){
		if((key instanceof String)){
			key =((String)key).trim();
		}
		return map.get(key);
	}
	
	/**
	 * 尽最大可能的获取指定键的值, 慎用<br>
	 * @param map (Map) 映射集
	 * @param key (Object) 键
	 * @author mao添加 2013-7-28
	 */
	public static Object getAs(Map map, Object key){
		Object ret = null;
		String keyStr = null;
		//常规查找
		if((key instanceof String)){
			key = keyStr = ((String)key).trim();
		}
		ret = map.get(key);
		
		//相似查找
		if(ret == null && keyStr != null && keyStr.length() > 0){
			//去掉键前面的0
			keyStr = dropPreZero(keyStr);
			ret = map.get(keyStr);
			
			if(ret == null && keyStr.length() > 0 && charIsNumber(keyStr.charAt(0))){
				//转换成数字
				try {
					int n = Integer.parseInt(keyStr);
					ret = map.get(n);
				} catch (NumberFormatException e) {
				}
			}
		}
		return ret;
	}
	
	/** 转换成String 类型的Map, 值为null的将忽略 */
	public static Map<String, String> toStrMap(Map map){
		Map<String, String> newmap = new HashMap<String, String>();
		if(map != null && map.size() > 0){
			Object key, val;
			for(Iterator iter = map.keySet().iterator(); iter.hasNext();){
				key = iter.next();
				val = map.get(key);
				if(val != null){
					if(key == null){
						newmap.put("null", val.toString());
					}else{
						newmap.put(key.toString(), val.toString());
					}
				}
			}
		}
		return newmap;
	} 
	
	// TODO 数组相关
	
	/**
	 * 搜索 一个字符串数组中是否包含指定的字符串
	 */
	public static int search(final String[] sa, String s) {
		int r = -1;
		if(isNotEmpty(sa) && s != null){
			for(int i = 0, len = sa.length; i < len; i++){
				if(s.equals(sa[i])){
					return i;
				}
			}
		}
		return r;
	}
	/**
	 * 搜索 一个字符串数组中是否包含指定的字符串
	 */
	public static int searchIgnoreCase(final String[] sa, String s) {
		int r = -1;
		if(isNotEmpty(sa) && s != null){
			for(int i = 0, len = sa.length; i < len; i++){
				if(s.equalsIgnoreCase(sa[i])){
					return i;
				}
			}
		}
		return r;
	}
	
	/**
	 * 检测一个字符串的结尾是否在 结尾集中, 如果是, 返回索引, 不是返回-1
	 * @param exts (String[]) 结尾集
	 * @param str (String) 
	 * @return 
	 */
	public static int endsWith(String[] exts, String str){
		int i = 0;
		for(; i < exts.length; i++){
			if(str.endsWith(exts[i])){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 检测一个字符串的开头是否在开头集中, 如果是, 返回索引, 不是返回-1
	 * @param prefs (String[]) 开头集
	 * @param str (String) 
	 * @return 
	 */
	public static int startsWith(String[] prefs, String str){
		int i = 0;
		for(; i < prefs.length; i++){
			if(str.startsWith(prefs[i])){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 一个字符串的结尾是否在 结尾集中, 如果是, 返回删除后的
	 * @param exts (String[]) 结尾集
	 * @param str (String) 
	 * @return 
	 */
	public static String delEndsWith(String[] exts, String str){
		int ix = endsWith(exts, str);
		if(ix != -1){
			str = str.substring(0, str.length() - exts[ix].length());
		}
		return str;
	}
	
	/**
	 * 一个字符串的开头是否在 开头集中, 如果是, 返回删除后的
	 * @param prefs (String[]) 开头集
	 * @param str (String) 
	 * @return 
	 */
	public static String delStartsWith(String[] prefs, String str){
		int ix = startsWith(prefs, str);
		if(ix != -1){
			str = str.substring(prefs[ix].length());
		}
		return str;
	}
	
	/**
	 * 初始化数组里的每一项值为 o的值
	 */
	public static boolean initArray(final Object[] oa, Object o){
		if(isNotEmpty(oa)){
			int i = 0, len = oa.length;
			for(; i < len; i++){
				oa[i] = o;
			}
			return i == oa.length;
		}
		return false;
	}
	
	//TODO 打印
	
	/**
	 * 打印一个数组
	 */
	public static void printArray(Object[] oa){
		if(oa == null) return ;
		StringBuffer sb = new StringBuffer(oa.getClass().getName()).append(" [");
		int i = 0, len = oa.length;
		for(; i < len; i++){
			sb.append(oa[i]).append(", ");
		}
		if(i > 0){
			sb.delete(sb.length() - 2, sb.length());
		}
		System.out.println(sb.append("]").toString());
	}
	
	/** 打印一个数组 */
	public static void printlnArray(Object[] oa){
		if(oa == null) return ;
		for(int i = 0, len = oa.length; i < len; i++){
			System.out.println(oa[i]);
		}
	}
	
	/** 打印一个数组 */
	public static void printlnList(List oa){
		if(oa == null) return ;
		for(int i = 0, len = oa.size(); i < len; i++){
			System.out.println(oa.get(i));
		}
	}
	
	/** 打印一个数组, 元素为Map */
	public static void printlnListMap(List oa){
		if(oa == null || oa.size() == 0) return ;
		int Len = oa.size();
		
		Map map = (Map)oa.get(0);
		int MLen = map.size();
		Object[] keys = new Object[MLen];
		Iterator iter = map.keySet().iterator();
		int i = 0;
		System.out.print("0\t");
		while(iter.hasNext()){
			keys[i] = iter.next();
			System.out.print(keys[i] + "\t");
			i++;
		}
		System.out.println();
		
		for(i = 0; i < Len; i++){
			map = (Map)oa.get(i);
			System.out.print((i+1) + "\t");
			for(int j = 0; j < MLen; j++){
				System.out.print(map.get(keys[j]) + "\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 将 List转换成二维数组, 注: List中的元素是数组的情况使用!
	 * @param oa (Object[])
	 * @return Object[][]
	 */
	public static Object[][] arr22(final List list){
		if(list == null || list.size() < 1){
			return null;
		}
		Object[][] a2 = null;
		int len = list.size();
		a2 = new Object[list.size()][];
		for(int i = 0; i < len; i++){
			a2[i] = (Object[])list.get(i);
		}
		return a2;
	} 
	/**
	 * 将一维数据转换成二维数组, 注: 一维数组中的元素是数组的情况使用!
	 * @param oa (Object[])
	 * @return Object[][]
	 */
	public static Object[][] arr22(Object[] oa){
		Object[][] a2 = null;
		if(oa != null && oa.length > 0){
			a2 = new Object[oa.length][];
			for(int i = 0; i < oa.length; i++){
				a2[i] = (Object[])oa[i];
			}
		}
		return a2;
	}public static Object getValue(String[] names, Object[] values, String serchName){
		if(null == names || null == values || names.length != values.length){
			return null;
		}
		int k = Arrays.binarySearch(names, serchName);
		if(k < 0){
			return null;
		}
		return values[k];
	}
	
	/**
	 * 将数据 按 split连成字符串, 
	 * 如: ["mao", "haozi"], ", " --> "mao, haozi"
	 */
	public static String join(Object[] oa, String split){
		if(MUtil.isNotEmpty(oa)){
			StringBuffer sb = new StringBuffer();
			if(split == null){
				split = " ";
			}
			for(int i = 0, len = oa.length; i < len; i++){
				sb.append(oa[i]).append(split);
			}
			if(sb.length() > 0) sb.setLength(sb.length() - split.length());
			return sb.toString();
		}
		return "";
	}
	/**
	 * 将数据 按 split连成字符串, 
	 * 如: ["mao", "haozi"], ", " --> "mao, haozi"
	 */
	public static String join(double[] oa, String split){
		if(MUtil.isNotEmpty(oa)){
			StringBuffer sb = new StringBuffer();
			if(split == null){
				split = " ";
			}
			for(int i = 0, len = oa.length; i < len; i++){
				sb.append(oa[i]).append(split);
			}
			if(sb.length() > 0) sb.setLength(sb.length() - split.length());
			return sb.toString();
		}
		return "";
	}
	/**
	 * 将数据 按 split连成字符串, 
	 * 如: ["mao", "haozi"], ", " --> "mao, haozi"
	 */
	public static String join(int[] oa, String split){
		if(MUtil.isNotEmpty(oa)){
			StringBuffer sb = new StringBuffer();
			if(split == null){
				split = " ";
			}
			for(int i = 0, len = oa.length; i < len; i++){
				sb.append(oa[i]).append(split);
			}
			if(sb.length() > 0) sb.setLength(sb.length() - split.length());
			return sb.toString();
		}
		return "";
	}
	/**
	 * 将数据 按 split连成字符串, 
	 * 如: ["mao", "haozi"], ", " --> "mao, haozi"
	 */
	public static String join(String[] oa, String split){
		if(MUtil.isNotEmpty(oa)){
			StringBuffer sb = new StringBuffer();
			if(split == null){
				split = " ";
			}
			for(int i = 0, len = oa.length; i < len; i++){
				sb.append(oa[i]).append(split);
			}
			if(sb.length() > 0) sb.setLength(sb.length() - split.length());
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 是否是 所有元素都为 空或null
	 */
	public static boolean allEmpty(Object[] oa){
		if(oa == null || oa.length == 0){
			return true;
		}
		for(Object o : oa){
			if(o == null || "".equals(o)){
				
			}else{
				return false;
			}
		}
		return true;
	}
	
	/** 检测 其中一个数组 为 null 或 没有元素, 则返回 false */
	public static boolean isOneNullOrEmpty(Object... all){
		if(all == null || all.length == 0){
			return true;
		}
		for(int i = 0, len = all.length; i< len; i++){
			Object[] oa = (Object[]) all[i];
			if(oa == null || oa.length == 0){
				return true;
			}
		}
		return false;
	}
	
	
	// TODO Map相关
	
	/**
	 * 为 map主 key为"1", "2, "3"... 的值补充 "01", "02", "03"...
	 * @param map (Map<String, Object>)
	 * @param addZreo (boolean) true: "1"--> "01", false: "01"--> "1"
	 */
	public static boolean zeroPrefixMap(Map map, boolean addZreo){
		if(map == null || map.size() < 1) return false;
		String k = null;
		String k2 = null;
		Object v = null;
		for(int i = 0; i < 10; i ++){
			if(addZreo){
				k = "" + i;
				k2 = "0" + i;
			}else{
				k = "0" + i;
				k2 = "" + i;
			}
			v = map.get(k);
			if(v != null){
				map.put(k2, v);
			}
		}
		return true;
	}
	
	/**
	 * 打印 Map
	 */
	public static String printMap(Map map){
		String ps = null;
		if(map != null && map.size() > 0){
			StringBuffer sb = new StringBuffer("Map {");
			final Set set = map.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Object key = iter.next();
				Object v = map.get(key);
				sb.append(key).append(": ").append(v).append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("}");
			ps = sb.toString();
		}
		System.out.println(ps);
		return ps;
	}
	
	// TODO 随机数
	
	/** 产生一个介于min和max间的随机数, 可能是min或max */
	public static double random(double min, double max){
		double r = Math.random();
		double v = 0;
		if(max < min){
			v = min;
			min = max;
			max = v;
		}
		v = r;
		double mm = max - min;
		if(r > max){
			v = max - mm * r;
		}else{
			v = min + mm * r;
		}
		return v;
	}
	
	
	// TODO 路径相关
	/** 
	 * 类根路径  如: "E:/java/"
	 */
	public static String classRootPath(){
		return classRootPath(null);
	}
	/** 
	 * 类根路径 + ex, 如: "E:/java/" + "mao/m.txt"
	 * @param ex (String) 补充的路径
	 */
	public static String classRootPath(String ex){
		URL url = Thread.currentThread().getContextClassLoader().getResource(ex == null ? "" : ex);
		String s = url == null ? Thread.currentThread().getContextClassLoader().getResource("").toString() + ex : url.toString();
		if(s.startsWith("jar:")){
			return s;
		}else if(s.startsWith("file:/")){
			return s.substring(6);
		}
		return s.substring(1);
	}
	
	// TODO 字符串判断
	
	/** 判断 str是否以s开头, 不区分大小写。 */
	public static boolean startWith(String str, String s){
		if(str == null || s == null || s.length() < 1 || str.length() < s.length()){
			return false;
		}
		return str.substring(0, s.length()).equalsIgnoreCase(s);
	}
	
	// TODO 字符串补位
	
	/** 不足3位在前面补零 */
	public static String fillZero3(int n){
		String str = n + "";
		if(str.length() == 1){
			str = "00" + str;
		}else if(str.length() == 2){
			str = "0" + str;
		}
		return str;
	}
	/** 不足3位在前面补零 */
	public static String fillZero3(String str){
		if(str == null || str.length() == 0){
			str = "000";
		}else if(str.length() == 1){
			str = "00" + str;
		}else if(str.length() == 2){
			str = "0" + str;
		}
		return str;
	}

	/** 不足2位在前面补零 */
	public static String fillZero2(int n) {
		String str = n + "";
		if (str.length() == 1) {
			str = "0" + str;
		}else if(str.length() > 2){
			return str.substring(0, 2);
		}
		return str;
	}
	
	/**
	 * 对一个字符串进行简单的分割
	 * @param str (String)
	 * @param sign (String)
	 */
	public static String[] split(String str, String sign){
		return split(str, sign, false);
	}
	
	/**
	 * 对一个字符串进行简单的分割
	 * @param str (String)
	 * @param sign (String)
	 * @param trim (boolean) 将原字符串的两端空白去掉
	 */
	public static String[] split(String str, String sign, boolean trim){
		if(str != null && str.length() > 0){
			if(trim) str = str.trim();
			return str.split(sign);
		}
		return null;
	}
	
	/**
	 * 对一个字符串进行简单的分割, <br>
	 * 注意, "~"则返回 {"", ""}, "A~", 将返回 {"A", ""}
	 * @param str (String)
	 * @param sign (String)
	 * @param trim (boolean) 将原字符串的两端空白去掉
	 */
	public static String[] splited(String str, String sign, boolean trim){
		if(str != null && str.length() > 0){
			if(trim) str = str.trim();
			return str.split(sign);
		}
		return null;
	}
	
	/**
	 * 对一个字符串进行简单的分割, 且只分割成两段
	 * @param str (String)
	 * @param sign (String) 不支持正则表达式
	 * @param trim (boolean) 将原字符串的两端空白去掉
	 * @return 若未找到返回{str, null}, 若分割符之后没有字符, 返回{str, ""}
	 */
	public static String[] split2(String str, String sign, boolean trim){
		if(str != null && str.length() > 0){
			if(trim) str = str.trim();
			int len = str.length();
			int SLen = sign.length();
			int ix = str.indexOf(sign);
			if(ix == -1){
				return new String[]{str, null};
			}else if(ix + SLen == len){
				return new String[]{str, ""};
			}else{
				return new String[]{str.substring(0, ix), str.substring(ix + SLen)};
			}
		}
		return null;
	}
		
	// TODO Double处理
	
	public static final String DecFmtStr = "0.0000000000";
	public static DecimalFormat[] DecFmts;
	public static final DecimalFormat DecFmt1 = new DecimalFormat("0.0");
	public static final DecimalFormat DecFmt2 = new DecimalFormat("0.00");
	
	/** 返回格式化多少位小数的格式代对象, 最多后10位, 如: dec=2 --> "0.00" */
	public static DecimalFormat getDecFmts(int dec){
		if(DecFmts == null){
			DecFmts = new DecimalFormat[DecFmtStr.length() - 1];
			DecFmts[0] = new DecimalFormat("0");
		}
		if(DecFmts[dec] == null){
			DecFmts[dec] = new DecimalFormat(DecFmtStr.substring(0, 2+dec));
		}
		return DecFmts[dec];
	}
	/** 保留dec位小数 */
	public static double getDecimals(double d, int dec){
		d = Double.parseDouble(getDecFmts(dec).format(d));
		return d;
	}
	/** 保留dec位小数, 返回字符串 */
	public static String getDecimalsStr(double d, int dec){
		return getDecFmts(dec).format(d);
	}
	/** 保留1位小数 */
	public static double getDecimals1(double d){
		d = Double.parseDouble(DecFmt1.format(d));
		return d;
	}
	/** 保留两位小数 */
	public static double getDecimals2(double d){
		d = Double.parseDouble(DecFmt2.format(d));
		return d;
	}
		
	// TODO 类型转换
	
	/** 字符串数组转double数组 */
	public static double[] strArrToDoubleArr(String[] sa){
		if(sa != null){
			int len = sa.length;
			double[] ia = new double[len];
			try {
				for(int i = 0; i < len; i++){
					ia[i] = Double.parseDouble(sa[i].trim());
				}
			} catch (NumberFormatException e) {
				ia = null;
				e.printStackTrace();
			}
			return ia;
		}
		return null;
	}
	
	/** 字符串数组转double数组, 出错填充默认值def */
	public static double[] strArrToIntArr(String[] sa, double def){
		if(sa != null){
			int len = sa.length;
			double[] ia = new double[len];
			for(int i = 0; i < len; i++){
				try {
					ia[i] = Double.parseDouble(sa[i].trim());
				} catch (NumberFormatException e) {
					ia[i] = def;
				}
			}
			return ia;
		}
		return null;
	}
	
	/** 字符串数组转int数组 */
	public static int[] strArrToIntArr(String[] sa){
		if(sa != null){
			int len = sa.length;
			int[] ia = new int[len];
			try {
				for(int i = 0; i < len; i++){
					ia[i] = Integer.parseInt(sa[i].trim());
				}
			} catch (NumberFormatException e) {
				ia = null;
				e.printStackTrace();
			}
			return ia;
		}
		return null;
	}
	
	/** 字符串数组转int数组, 出错填充默认值def */
	public static int[] strArrToIntArr(String[] sa, int def){
		if(sa != null){
			int len = sa.length;
			int[] ia = new int[len];
			for(int i = 0; i < len; i++){
				try {
					ia[i] = Integer.parseInt(sa[i].trim());
				} catch (NumberFormatException e) {
					ia[i] = def;
				}
			}
			return ia;
		}
		return null;
	}
	
	/** int数组转字符串数组, 出错填充默认值def */
	public static String[] intArrToStrArr(int[] sa){
		if(sa != null){
			int len = sa.length;
			String[] ia = new String[len];
			for(int i = 0; i < len; i++){
				ia[i] = sa[i] + "";
			}
			return ia;
		}
		return null;
	}
	
	/** 转成 Integer, 失败返回null */
	public static Integer toInteger(Object obj){
		return toInteger(obj, null);
	}
	
	/** 转成 Integer, 失败返回null */
	public static Integer toInteger(Object obj, Integer def){
		if(obj == null) return def;
		if(obj instanceof Integer){
			def = (Integer)obj;
		}else if(obj instanceof Number){
			def = ((Number)obj).intValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					def = Integer.parseInt(str);
				}
			} catch (Exception e) {
			}
		}
		return def;
	}
	
	/** 转成int型, 失败返回 def */
	public static int toInt(Object obj, int def){
		if(obj == null) return def;
		if(obj instanceof Integer){
			def = (Integer)obj;
		}else if(obj instanceof Number){
			def = ((Number)obj).intValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					def = Integer.parseInt(str);
				}
			} catch (Exception e) {
			}
		}
		return def;
	}

	/** 转成 Long, 失败返回null */
	public static Long toLong(Object obj){
		return toLong(obj, null);
	}
	
	/** 转成 Long, 失败返回null */
	public static Long toLong(Object obj, Long def){
		if(obj == null) return def;
		if(obj instanceof Long){
			def = (Long)obj;
		}else if(obj instanceof Number){
			def = ((Number)obj).longValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					def = Long.parseLong(str);
				}
			} catch (Exception e) {
			}
		}
		return def;
	}
	
	/** 转成long型, 失败返回 def */
	public static long tolong(Object obj, long def){
		if(obj == null) return def;
		if(obj instanceof Long){
			def = (Long)obj;
		}else if(obj instanceof Number){
			def = ((Number)obj).longValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					def = Long.parseLong(str);
				}
			} catch (Exception e) {
			}
		}
		return def;
	}
	
	/** 转成int型, 失败返回 def, 此方将使用 "A12B34"-->"12" */
	public static int toIntFirst(Object obj, int def){
		if(obj == null) return def;
		if(obj instanceof Number){
			def = ((Number)obj).intValue();
		}else if(obj instanceof String){
			String str = (String)obj;
			if((str = str.trim()).length() == 0){
				return def;
			}
			int i0_p = '0' - 1;
			int i9_n = '9' + 1;
			boolean used = false;
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < str.length(); i++){
				int c = str.charAt(i);
				if(i0_p < c && c < i9_n){
					sb.append((char)c);
				}else if(used){
					if(sb.length() > 0){
						break;
					}
				}else{
					used = true;
				}
			}
			if(sb.length() == 0){
				return def;
			}
			try {
				def = Integer.parseInt(sb.toString());
			} catch (Exception e) {
			}
		}
		return def;
	}

	/** 转成double型, 失败返回 def, 此方将使用 "A12.01B34"-->"12.01" */
	public static double toDoubleFirst(Object obj, double def){
		if(obj == null) return def;
		if(obj instanceof Number){
			def = ((Number)obj).doubleValue();
		}else if(obj instanceof String){
			String str = (String)obj;
			if((str = str.trim()).length() == 0){
				return def;
			}
			int i0_p = '0' - 1;
			int i9_n = '9' + 1;
			boolean used = false;
			boolean usedD = false;
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < str.length(); i++){
				int c = str.charAt(i);
				if(i0_p < c && c < i9_n){
					sb.append((char)c);
				}else if(c == '.'){
					if(usedD){
						if(sb.length() > 0){
							break;
						}
					}else{
						sb.append('.');
						usedD = true;
					}
				}else if(used){
					if(sb.length() > 0){
						break;
					}
				}else{
					used = true;
				}
			}
			if(sb.length() == 0){
				return def;
			}
			try {
				def = Double.parseDouble(sb.toString());
			} catch (Exception e) {
			}
		}
		return def;
	}
	
	/** 转成 Double, 失败返回null */
	public static Double toDouble(Object obj){
		return toDouble(obj, null);
	}
	
	/** 转成 Double, 失败返回null */
	public static Double toDouble(Object obj, Double def){
		if(obj == null) return def;
		if(obj instanceof Double){
			def = (Double)obj;
		}else if(obj instanceof Number){
			def = ((Number)obj).doubleValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					def = Double.parseDouble(str);
				}
			} catch (Exception e) {
			}
		}
		return def;
	}
	
	/** 转成 double, 失败返回defVal */
	public static double todouble(Object obj, double defVal){
		if(obj == null) return defVal;
		double d = defVal;
		if(obj instanceof Double){
			d = (Double)obj;
		}else if(obj instanceof Number){
			d = ((Number)obj).doubleValue();
		}else{
			try {
				String str = obj.toString();
				if(str.length() != 0 && (str = str.trim()).length() != 0){
					d = Double.parseDouble(str);
				}
			} catch (Exception e) {
			}
		}
		return d;
	}
	
	/** 将"3.1,3.2"--转换成 [3.1, 3.2] */
	public static double[] strTodoubleArr(String str, String sign, boolean trim){
		String[] sa = MUtil.split(str, sign, trim);
		return MUtil.strArrToDoubleArr(sa);
	}
	
	/** 求两个Long的各, null按0处理 */
	public static long longSum(Long a, Long b){
		long c = a == null ? 0L : a;
		c += b == null ? 0L : b;
		return c;
	}
	
	/** 求两个Integer的各, null按0处理 */
	public static int intSum(Integer a, Integer b){
		int c = a == null ? 0 : a;
		c += b == null ? 0 : b;
		return c;
	}
	
	// TODO 字符串拼接
	
	/** 将数组以逗号连接成串 */
	public static String joinArr(Object[] arr){
		return joinArr(arr, ", ");
	}
	
	/** 将数组以split连接成串 */
	public static String joinArr(Object[] arr, String split){
		if (arr == null) return "";
		if(arr.length == 1) return arr[0] + "";
		int iMax = arr.length - 1;
		if (iMax == -1) return "";
		
		boolean addSplit = true;
		if(split == null || split.length() == 0) addSplit = false;
		StringBuilder b = new StringBuilder();
		for (int i = 0; ; i++) {
			b.append(String.valueOf(arr[i]));
			if (i == iMax) {
				return b.toString();
			}
			if(addSplit) b.append(split);
		}
	}
	/** 将List以split连接成串 */
	public static String joinArr(List list, String split){
		if (list == null) 
			return "null";
		if(list.size() == 1)
			return list.get(0) + "";
		int iMax = list.size() - 1;
		if (iMax == -1) 
			return "";
		boolean addSplit = true;
		if(split == null || "".equals(split)) addSplit = false;
		StringBuilder b = new StringBuilder();
		for (int i = 0; ; i++) {
			b.append(String.valueOf(list.get(i)));
			if (i == iMax) 
				return b.toString();
			if(addSplit) b.append(split);
		}
	}
	
	// TODO 数组判断
	
	/** true:其中一个List为null或无元素, 或后面的List的元素个数少于第一个的元素个数 */
	public static boolean isEmptyAndSize(List... lsa){
		if(lsa == null || lsa.length == 0){
			return true;
		}
		if(lsa.length == 1 && (lsa[0] == null || lsa[0].size() == 0)){
			return true;
		}
		for(int i = 1; i < lsa.length; i++){
			if(lsa[i] == null || lsa[i].size() == 0){
				return true;
			}
			if(lsa[i].size() < lsa[0].size()){
				return true;
			}
		}
		return false;
	}
	
	/** 返回数组指定索引的值, 越界返回 def */
	public static String indexOf(String[] arr, int ix, String def){
		if(arr != null && arr.length > ix && ix >= 0){
			return arr[ix];
		}
		return def;
	}
	
	// TODO 排序
	
	/**
	 * 将数组进行排序
	 * @param its (int)
	 * @param desc (boolean) true: 递减
	 */
	public static void sortArr(int[] its, boolean desc){
		if(its == null || its.length < 2) return ;
		if(desc){
			for(int i = 0; i < its.length; i++){
				for(int j = i+1; j < its.length; j++){
					if(its[j] > its[i]){
						int t = its[j];
						its[j] = its[i];
						its[i] = t;
					}
				}
			}
		} else {
			for(int i = 0; i < its.length; i++){
				for(int j = i+1; j < its.length; j++){
					if(its[j] < its[i]){
						int t = its[j];
						its[j] = its[i];
						its[i] = t;
					}
				}
			}
		}
		
		
	}
	
	/**
	 * 将 "8:0630,1030;20:1630"格式的字符串转换成 {"8": "0630,1030", "20":"1630"}的Map
	 * @param str (String) 待转换的字符串
	 * @param sp1 (String) 第一级分割符, 如: ";"
	 * @param sp2 (String) 第二级分割符, 如: ":"
	 * @param isTrim (boolean) 是否去掉两端空白
	 */
	public static Map<String, String> strToMap(String str, String sp1, String sp2, boolean isTrim){
		Map<String, String> map = new HashMap<String, String>();
		if(str != null && (isTrim ? str = str.trim() : str).length() > 0){
			String[] s1s = str.split(sp1);
			if(s1s != null && s1s.length > 0){
				for(int i = 0; i < s1s.length; i++){
					String s1Str = isTrim ? s1s[i].trim() : s1s[i];
					if(s1Str.length() == 0) continue;
					String[] s2s = s1Str.split(sp2, 2);
					if(s2s != null && s2s.length > 0){
						map.put(s2s[0], s2s.length > 1 ? s2s[1] : "");
					}
				}
			}
		}
		return map;
	}
		
		
	/** 去掉字符串str中字符c后从end起的多余字符, 如: "30.123", '.', 3 --> */
	public static boolean substring(String str, char c, int len){
		return false;
	}
		
	// TODO 资源路径
	
	/**
	 * 通过名称获取 图片资源
	 * @param name (String) 在 classes/根目录下的文件路径, 如: img/a.jpg
	 */
	public static String getPath(String name){
		if(name != null && name.length() > 1 && name.charAt(1) == ':'){
			return name;
		}
		URL url = Thread.currentThread().getContextClassLoader().getResource(name == null ? "" : name);
		String s = url == null ? Thread.currentThread().getContextClassLoader().getResource("").getPath() + name : url.getPath();
		return s.substring(1);
	}
	
	/**
	 * 通过名称获取 图片资源
	 * @param name (String) 在 src/下的图片文件, 如: img/a.jpg
	 */
	public static Image getImgByName(String name){
		URL url = Thread.currentThread().getContextClassLoader().getResource(name);
		ImageIcon ii = new ImageIcon(url);
		return ii.getImage();
	}
	/**
	 * 通过名称获取 图片资源
	 * @param name (String) 在 src/下的图片文件, 如: img/a.jpg
	 */
	public static Icon getIconByName(String name){
		URL url = Thread.currentThread().getContextClassLoader().getResource(name);
		ImageIcon ii = new ImageIcon(url);
		return ii;
	}
	
	/**
	 * 获取项目WebRoot路径, 如:"E:/java/m10works/jxgc/jshop2/WebRoot/"
	 */
	public static String getWebRoot(){
		String path = getClassesPath();
		int ix = path.lastIndexOf("WEB-INF");
		if(ix > 0){
			path = path.substring(0, ix);
		}
		System.out.println(path);
		return path;
	}
	
	/**
	 * 获取当前classes路径, 如:"E:/java/m10works/jxgc/jshop2/WebRoot/WEB-INF/classes/"
	 */
	public static String getClassesPath(){
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int ix = path.indexOf(':');
		if(ix > 0){
			path = path.substring(ix - 1);
		}
		System.out.println(path);
		return path;
	}
		
	//TODO 正则表达式验证
		
	public static String RegExp_Mobile = "^(1[34578][0-9])\\d{8}$";
	public static Pattern Pat_Mobile = Pattern.compile(RegExp_Mobile);
	
	/** 检测是否为手机号 */
	public static boolean isMobile(String str){
		if(str != null && str.length() > 10 &&  str.length() < 20){
			Matcher m = Pat_Mobile.matcher(str);
			return m.find();
		}
		return false;
	}
	
	public static String RegExp_Birth = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";
	public static Pattern Pat_Birth = Pattern.compile(RegExp_Birth);
	/** 检测是否为生日"yyyy-MM-dd" */
	public static boolean isBirth(String str){
		if(str != null && str.length() == 10){
			Matcher m = Pat_Birth.matcher(str);
			return m.find();
		}
		return false;
	}
	
	/** 加密手机号 */
	public static String passMobile(String mobile){
		if(mobile != null && mobile.length() == 11){
			mobile = mobile.substring(0, 4) + "*****" + mobile.substring(9, 11);
		}
		return mobile;
	}
	
	/** 将右斜杠转为左斜杠 */
	public static String toLeftSlash(String str){
		if(isNotEmpty(str)){
			str = str.replaceAll("\\\\", "/");
		}
		return str;
	}
	
	/** 判断是否是PC的代理信息: str通过request.getheader("user-agent")得到 */
	public static boolean isPcUserAgent(String str){
		if(str != null && str.length() > 0){
			return str.indexOf("Mobile") < 0;
		}
		return true;
	}
	
	/** 当前时间 */
	public static Date now(){
		return new Date();
	}
	
	/**
	 * 生成随机token
	 * @param userStr
	 * @return
	 */
	public static String buildToken(){
		try {
			int seek = (int) ((Math.random() * 9 + 1) * 100000);
			String token = String.valueOf(System.currentTimeMillis()) + String.valueOf(seek);
			return token;
		} catch (Exception e) {
		}
		return "";
	}
	
	/** 过滤二次json化的字符串 */
	public static String filter_j2str(String jstr){
		if(jstr != null && jstr.length() > 2 && jstr.charAt(0) == '"'){
			jstr = jstr.substring(1, jstr.length()-1).replaceAll("\\\\", "");
		}
		return jstr;
	}
	
	/** 判断两个值是否equals, 都为null返回 true */
	public static boolean eq(Object a, Object b){
		if(a != null){
			return a.equals(b);
		}else if(b != null){
			return b.equals(a);
		}
		return true;
	}
	
	/**
	 * 用户登录token
	 * @param userStr
	 * @return
	 */
	public static String buildToken(String userStr){
		try {
			return MUtil.md5(userStr+"O(∩_∩)O哈哈~"+ new Date().getTime());
		} catch (Exception e) {
		}
		return null;
	}
		
	//TODO 测试
	
	public static void main(String[] args) {
		
		//System.out.println(isBirth("1989-11-05"));
		System.out.println(toLeftSlash("12233\\rurrir"));
	}
}

