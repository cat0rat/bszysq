package com.mao.lang;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * 字符串 工具类
 * @author mao
 * @version 2011-5-17 下午09:27:39
 * @author mao 2012-9-18 下午10:12:07
 */
public class StringUtil {

	/**
	 * 不为空, 返回 true
	 * @param o (Object)
	 */
	public static boolean isNotEmpty(Object o){
		if(o == null || "".equals(o)){
			return false;
		}
		return true;
	}
	/**
	 * 不为空, 返回 true
	 * @param o (String)
	 */
	public static boolean isNotEmpty(String o){
		if(o == null || o.isEmpty() || o.trim().isEmpty()){
			return false;
		}
		return true;
	}
	
	/**
	 * 为空, 返回 true
	 * @param o (String)
	 */
	public static boolean isEmpty(String o){
		if(o == null || o.isEmpty() || o.trim().isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是数字
	 * @param o (String) 
	 */
	public static boolean isNumber(String o){
		if(isNotEmpty(o)){
			try{
				Long.parseLong(o);
				return true;
			}catch (Exception e) {
			}
		}
		return false;
	}
	
	/**
	 * 是否是大于 0的数, 常用于检测是否是 id
	 * @param o (Long) 
	 */
	public static boolean isId(Integer o){
		return o != null && o > 0;
	}
	
	/**
	 * 是否是大于 0的数, 常用于检测是否是 id
	 * @param o (Long) 
	 */
	public static boolean isId(Long o){
		return o != null && o > 0;
	}
	
	/**
	 * 是否是大于 0的数, 常用于检测是否是 id
	 * @param o (String) 
	 */
	public static boolean isId(String o){
		if(isNumber(o)){
			long l = Long.parseLong(o);
			if(l > 0){
				return true;
			}
		}
		return false;
	}
	
	/** 检测ids是否是由(数字,空格)组成 */
	public static boolean isIds(String ids){
		if(isNotEmpty(ids)){
			// 待完善
			return true;
		}
		return false;
	}
	
	/**
	 * 转换为 int, 如果转换不成功, 返回 0
	 * @param o (String)
	 */
	public static int toInt(String o){
		return toInt(o, 0);
	}
	
	/**
	 * 转换为 int, 如果转换不成功, 返回 默认值,
	 * @param o (String)
	 * @param i (int) 默认值
	 */
	public static int toInt(String o, int i){
		if(isNotEmpty(o)){
			try{
				i = Integer.parseInt(o);
			}catch (Exception e) {
			}
		}
		return i;
	}
	
	/**
	 * 转换为 long, 如果转换不成功, 返回 0
	 * @param o (String)
	 */
	public static long toLng(String o){
		return toLng(o, 0);
	}
	
	/**
	 * 转换为 long, 如果转换不成功, 返回 默认值,
	 * @param o (String)
	 * @param i (long) 默认值
	 */
	public static long toLng(String o, long i){
		if(isNotEmpty(o)){
			try{
				i = Long.parseLong(o);
			}catch (Exception e) {
			}
		}
		return i;
	}
	
	/**
	 * 如果 n 不为空返回 true
	 */
	public static boolean isNotEmpty(int n){
		if(!"".equals(n) && n > 0){
			return true;
		}
		return false;
	}
	/**
	 * 如果 n 不为空返回 true
	 */
	public static boolean isNotEmpty(long n){
		if(!"".equals(n) && n > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Sha256 方式加密
	 */
	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/** Md5方式加密 */
	public static synchronized String md5(String inputStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
		return new String(Base64.encodeBase64(digest));
	}
	
	public static Object[] removeNull(Object[] obj){
		Object[] o = new Object[6];
		if(null != obj){
			int j = 0;
			for(int i = 0; i < obj.length; i++){
				if(null != obj[i]){
					o[j++] = obj[i];
				}
			}
		}
		return o;
	}
	
	/**
	 * 首字母大写
	 * @param s (String)
	 */
	public static String firstUpperCase(String s){
		return s.replaceFirst(s.substring(0, 1),s.substring(0, 1).toUpperCase())  ; 
	}
	
	/**
	 * 首字母小写
	 * @param s (String)
	 */
	public static String firstLowerCase(String s){
		return s.replaceFirst(s.substring(0, 1),s.substring(0, 1).toLowerCase())  ; 
	}
	/**
	 * 将指定位置的字母大写
	 * @param s (String)
	 * @param i (int) 从 0开始
	 */
	public static String upperCaseX(String s, int i){
		if(i == 0) return firstUpperCase(s);
		int c = s.charAt(i);
		if(c > 96 && c < 123){	//是小写字母
			StringBuffer sb = new StringBuffer(s);
			sb.setCharAt(i, (char)(c - 32));
			return sb.toString();
		}
		return s;
	}
	/**
	 * 将指定位置的字母小写
	 * @param s (String)
	 * @param i (int) 从 0开始
	 */
	public static String lowerCaseX(String s, int i){
		if(i == 0) return firstLowerCase(s);
		int c = s.charAt(i);
		if(c > 64 && c < 91){	//是大写字母
			StringBuffer sb = new StringBuffer(s);
			sb.setCharAt(i, (char)(c + 32));
			return sb.toString();
		}
		return s;
	}
	
	/**
	 * 将 Java的属性大写以适应 get、set方法的拼接, 如 name --> Name, pId--> pId,<br>
	 * 其中有如下四种情况: name, a, pId, XCode, You, B 前两种最常见，需首字母大写， 其余四种不变量 
	 */
	public static String javaPropCap(String s){
		//一个字母
		if(s.length() == 1){
			return s.toUpperCase();
		}
		
		//如果第一个字母是大写
		int c = s.charAt(0);	
		if(c > 64 && c < 91){	//是大写字母
			return s;
		}
		//如果第二个字母是大写
		c = s.charAt(1);
		if(c > 64 && c < 91){	//是大写字母
			return s;
		}
		return firstUpperCase(s);
	}
	
	/**
	 * 去掉两端空白
	 */
	public static Object trim(Object o){
		o = (o instanceof String) ? ((String) o).trim() : o;
		return o;
	}
	
	
	/**
	 * 添加get/set前缀
	 * @arr (String) 被添加前缀的字符串数组, 其首字母被大写
	 * @s (String) 前缀名
	 */
	public static String addPrefix(String tag, String s){
		if(null == tag) return null;
		return s + firstUpperCase(tag);
	}
	
	/**
	 * 删除get/set前缀
	 * @arr (String) 被删除前缀的字符串, 其首字母被大写
	 * @s (String) 前缀名
	 */
	public static String removePrefix(String tag, String s){
		if(null == tag) return null;
		return tag.substring(s.length()-1);
	}
	
	/**
	 * 添加get/set前缀
	 * @arr (String[]) 被添加前缀的字符串数组, 其首字母被大写
	 * @s (String) 前缀名
	 */
	public static String[] prefixIntoArray(String[] arr, String s){
		if(null == arr) return null;
		
		for(int i = 0; i < arr.length; i++){
			arr[i] = s + firstUpperCase(arr[i]);
		}
		
		return arr; 
	}
		
	/**
	 * 删除get/set前缀
	 * @arr (String[]) 被删除前缀的字符串数组, 其首字母被大写
	 * @s (String) 前缀名
	 */
	public static String[] prefixOutArray(String[] arr, String s){
		if(null == arr) return null;
		
		for(int i = 0; i < arr.length; i++){
			arr[i] = arr[i].substring(s.length()-1);
		}
		
		return arr; 
	}

	/**
	 * 按指定的编码方式返回 byte[]
	 * @param s (String)
	 * @param encoding (String) 支持的编码方式--默认Unicode,其他GBK,UTF8
	 */
	public static byte[] getBytes(String s, String encoding){
		byte[] b = null;
		try {
			if (encoding != null)
				b = s.getBytes(encoding);
			else
				b = s.getBytes();
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
			System.out.println("不支持的编码方式:" + unsupportedencodingexception.toString());
		}
		return b;
	}
	
	/**
	 * 处理编码
	 * encode (String) 编码方式
	 * return "GBK" 或 "UTF-8"
	 */
	public static String dealEncode(String encode){
		if(isEmpty(encode)) return "UTF-8";
		if("UTF-8".equalsIgnoreCase(encode) || "UTF8".equalsIgnoreCase(encode)){
			return "UTF-8";
		}else if("GBK".equalsIgnoreCase(encode)){
			return "GBK";
		}else{
			return "UTF-8";
		}
	}
	
	//------ 字符操作
	public static void printCharValue(String s){
		if(isNotEmpty(s)){
			for(int i = 0 , len = s.length(); i < len; i++){
				System.out.println("--" + s.charAt(i) + "--" + (int)s.charAt(i));
			}
		}
	}
	
	/**
	 * 移除最后一个字符, 如果它是指定的字符
	 */
	public static String removeTrailingChar(final String str, final char ch) {
		if ((str == null) || str.isEmpty())
			return str;
		else if (str.length() == 1)
			return str.charAt(0) == ch ? "" : str;
		try {
			final int l = str.length() - 1;
			if (str.charAt(l) == ch)
				return str.substring(0, l);
			return str;
		} catch (final Exception e) {
			return str;
		}
	}
	

	/**
	 * 返回 第一个匹配上
	 */
	public static String firstMatch(final String regex, final String str) {
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		while (matcher.find())
			return matcher.group(1);
		return null;
	}
	
	/**
	 * 将指定的字符转为 转义字符, 如: ' --> \'
	 * @param s 
	 * @param c
	 */
	public static String replaceXChar(String s, String c){
		return s.replaceAll(c, "\\\\\\" + c);
	}
	
	/**
	 * 判断这个字符是不是数字
	 */
	public static boolean charIsNumber(char c){
		if(c < '0' || c > '9'){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断这个字符是不是字母
	 */
	public static boolean charIsAlpha(char c){
		if(c < 'A' || c > 'z'){
			return false;
		}
		if(c < 'a' && c > 'Z'){
			return false;
		}
		return true;
	}
	
	//------ 子串操作
	
	/**
	 * 截取指定区间的 子字符,<br>
	 * 如果 起始位 x大于字符串长度, 则返回""<br>
	 * 如果 结束位大于字符串长度, 则返回 x, len的子字符串
	 */
	public static String substring(String s, int x){
		return substring(s, x, 0);
	}
	/**
	 * 截取指定区间的 子字符,<br>
	 * 如果 起始位 x大于字符串长度, 则返回""<br>
	 * 如果 结束位大于字符串长度, 则返回 x到子字符串结束<br>
	 * @param y (int) 结束位, 如果 为 0则返回字符串的结束
	 */
	public static String substring(String s, int x, int y){
		if(isNotEmpty(s)){
			int len = s.length();
			if(x > len){
				return "";
			}
			if(y == 0 || y > len){
				y = len;
			}
			return s.substring(x, y);
		}
		return s;
	}
	
	/**
	 * 截取指定位起的指定长度的字符串, <br>
	 * 如果 起始位 x大于字符串长度, 则返回""<br>
	 * 如果 len超出字符串长度, 则返回 x到子字符串结束<br>
	 * @param s (String) 字符串
	 * @param x (int) 起始位
	 * @param len (int) 长度
	 */
	public static String substr(String s, int x, int len){
		if(len != 0){
			len += x;
		}
		return substring(s, x, len);
	}
	
	/**
	 * 指定位的字符
	 * @param i (int) 如果是负数, 从后面数, 最后一位是 -1,
	 */
	public static char charAt(String s, int i){
		int len = s.length();
		while(i < 0){
			i += len;
		}
		return s.charAt(i);
	}
	/**
	 * 去掉键前面的重复字符, 如 "0089" --> "89"
	 * @param s (String) 待处理字符串
	 * @param c (char) 要去掉的重复字符
	 */
	public static String dropRepre(String s, char c){
		int i = 0;
		int len = s.length();
		for(; i < len; i++){
			if(s.charAt(i) != c){
				break;
			}
		}
		return s.substring(i);
	}
	/**
	 * 去掉键前面的0, 如 "0089" --> "89"
	 * @param s (String) 待处理字符串
	 */
	public static String dropPreZero(String s){
		return dropRepre(s, '0');
	}
	
	/**
	 * 自定义字符串分割 完全风格
	 * {eg： "ab;c;defg;;"使用";"分割       result:["ab",c,defg,,]}
	 * @param str   目标字符串
	 * @param regex 风格字符串，不支持正则表达式
	 * @return      风格后得到的list
	 * @author liu
	 */
	public static List<String> mySplit(String str,String regex){
		List<String> list=new ArrayList<String>();
		while(str!=""&&str!=null){
			list.add(str.substring(0,str.indexOf(regex)));
			str=  str.substring(str.indexOf(regex)+1);
			if(str.indexOf(regex)==-1){
				list.add(str);
				break;
			}
		}
		return list;
	}
	
	/** 转换成字符串 */
	public static String toString(Object obj){
		if(obj == null){
			return null;
		}
		return obj.toString();
	}
	
	/** 日期转换成毫秒 */
	public static long toms(Date date, long ms){
		if(date == null){
			return ms;
		}
		return date.getTime();
	}
	
	/** 日期转换成毫秒 */
	public static Long toMs(Date date, Long ms){
		if(date == null){
			return ms;
		}
		return date.getTime();
	}
	
	public static void main(String[] args) {
//		System.out.println("ABCDEFGH".substring(1, -3));
		System.out.println(substr("ABCDEFGH", 3, 5));
		System.out.println(substring("ABCDEFGH", 3, 5));
		System.out.println(substr("ABCDEFGH", 3, 40));
		System.out.println(substring("ABCDEFGH", 3, 40));
	}
	
}
