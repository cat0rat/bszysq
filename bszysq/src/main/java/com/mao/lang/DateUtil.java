package com.mao.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * 日期格式
 * @author mao
 * @version 2011-4-23 下午12:57:55
 */
public class DateUtil {

	//----- Date类操作
	
    /**
     * 返回系统当前的完整日期时间 <br>
     * 格式 1：2008-05-02 13:12:44 <br>
     * 格式 2：2008/05/02 13:12:44 <br>
     * 格式 3：2008年5月2日 13:12:44 <br>
     * 格式 4：2008年5月2日 13时12分44秒 <br>
     * 格式 5：2008年5月2日 星期五 13:12:44 <br>
     * 格式 6：2008年5月2日 星期五 13时12分44秒 <br>
     * @param 参数(formatType) :格式代码号
     * @return 字符串
     */
    public static String get(int formatType) {
        SimpleDateFormat sdf = null;
        if(formatType==1) {
            sdf = getOneSfd("yyyy-MM-dd HH:mm:ss");
        } else if(formatType==2) {
            sdf = getOneSfd("yyyy/MM/dd HH:mm:ss");
        } else if(formatType==3) {
            sdf = getOneSfd("yyyy年MM月dd日 HH:mm:ss");
        } else if(formatType==4) {
            sdf = getOneSfd("yyyy年MM月dd日 HH时mm分ss秒");
        } else if(formatType==5) {
            sdf = getOneSfd("yyyy年MM月dd日 E HH:mm:ss");
        } else if(formatType==6) {
            sdf = getOneSfd("yyyy年MM月dd日 E HH时mm分ss秒");
        }
        return sdf.format(new java.util.Date());
    }
    
    /**
     * 返回系统当前的完整日期 <br>
     * 格式 1：2008-05-02<br>
     * 格式 2：2008/05/02<br>
     * 格式 3：2008年5月2日<br>
     * 格式 4：2008年5月2日 <br>
     * 格式 5：2008年5月2日<br>
     * 格式 6：2008年5月2日 <br>
     * @param 参数(formatType) :格式代码号
     * @return 字符串
     */
    public static String getDate(int formatType) {
    	SimpleDateFormat sdf = null;
    	if(formatType==1) {
    		sdf = getOneSfd("yyyy-MM-dd");
    	} else if(formatType==2) {
    		sdf = getOneSfd("yyyy/MM/dd");
    	} else if(formatType==3) {
    		sdf = getOneSfd("yyyy年MM月dd日");
    	} else if(formatType==4) {
    		sdf = getOneSfd("yyyy年MM月dd日");
    	} else if(formatType==5) {
    		sdf = getOneSfd("yyyy年MM月dd日");
    	} else if(formatType==6) {
    		sdf = getOneSfd("yyyy年MM月dd日");
    	}
    	return sdf.format(new java.util.Date());
    }
    
    /**
     * 返回系统当前的完整日期+星期 <br>
     * 格式 1：2008-05-02 星期五<br>
     * 格式 2：2008/05/02 星期五<br>
     * 格式 3：2008年5月2日 星期五<br>
     * 格式 4：2008年5月2日 星期五 <br>
     * 格式 5：2008年5月2日 星期五<br>
     * 格式 6：2008年5月2日 星期五 <br>
     * @param 参数(formatType) :格式代码号
     * @return 字符串
     */
    public static String getWeek(int formatType) {
    	SimpleDateFormat sdf = null;
    	if(formatType==1) {
    		sdf = getOneSfd("yyyy-MM-dd E");
    	} else if(formatType==2) {
    		sdf = getOneSfd("yyyy/MM/dd E");
    	} else if(formatType==3) {
    		sdf = getOneSfd("yyyy年MM月dd日  E");
    	} else if(formatType==4) {
    		sdf = getOneSfd("yyyy年MM月dd日  E");
    	} else if(formatType==5) {
    		sdf = getOneSfd("yyyy年MM月dd日  E");
    	} else if(formatType==6) {
    		sdf = getOneSfd("yyyy年MM月dd日 E");
    	}
    	return sdf.format(new java.util.Date());
    }
    
  //----- Calendar类操作

    /** 日期时间格式，yyMMdd */
    public static final SimpleDateFormat Date_NoFmtDdf = getOneSfd("yyMMdd");
    /** 日期时间格式，yyyyMMdd */
    public static final SimpleDateFormat Date_Long_NoFmtDdf = getOneSfd("yyyyMMdd");
    /** 日期时间格式，yyyyMMddHHmmss */
    public static final SimpleDateFormat DateTime_NoFmtDdf = getOneSfd("yyyyMMddHHmmss");
    /** 日期时间格式，yyyy-MM-dd HH:mm:ss */
    public static final SimpleDateFormat DateTimeFmtDdf = getOneSfd("yyyy-MM-dd HH:mm:ss");
    /** 日期时间格式，yyyy/MM/dd HH:mm:ss */
    public static final SimpleDateFormat DateTime_LeftFmtDdf = getOneSfd("yyyy/MM/dd HH:mm:ss");
    /** 中文日期时间格式，yyyy年MM月dd日 HH:mm:ss */
    public static final SimpleDateFormat DateTime_ZhFmtDdf = getOneSfd("yyyy年MM月dd日 HH:mm:ss");
    /** 日期格式，yyyy-MM-dd */
    public static final SimpleDateFormat DateFmtDdf = getOneSfd("yyyy-MM-dd");
    /** 日期格式，yyyy/MM/dd */
    public static final SimpleDateFormat Date_LeftFmtDdf = getOneSfd("yyyy/MM/dd");
    /** 中文日期格式，yyyy年MM月dd日 */
    public static final SimpleDateFormat Date_ZhFmtDdf = getOneSfd("yyyy年MM月dd日");
    /** CST格式，EEE MMM dd HH:mm:ss CST yyyy, 如: "Wed May 27 15:31:48 CST 2015" */
    public static final SimpleDateFormat DateTime_CSTFmtDdf = getOneSfd("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    
    public static final SimpleDateFormat Date_Single = getOneSfd("yyyy-M-d");
    
    /** 日期字符串转 Calendar, 自动识别格式 */
    public static Calendar toCalendar(String dateStr){
    	return toCalendar(dateStr, null);
    }
    /**
     * 日期字符串转 Calendar, fmt=null, 则自动识别格式
     * @param dateStr (String) 日期字符串, 如果为 null, 则直接返回当前日期。
     * @param fmt (Stirng) 日期格式, 如果为null, 则自动识别格式。
     * @param allowNow (boolean) true: 解析失败返回当前时间
     */
    public static Calendar toCalendar(String dateStr, String fmt){
    	return toCalendar(dateStr, fmt, true);
    }
    /**
     * 日期字符串转 Calendar, fmt=null, 则自动识别格式
     * @param dateStr (String) 日期字符串, 如果为 null, 则直接返回当前日期。
     * @param fmt (Stirng) 日期格式, 如果为null, 则自动识别格式。
     */
    public static Calendar toCalendar(String dateStr, String fmt, boolean allowNow){
    	if(dateStr == null || dateStr.length() < 5){
			return allowNow ? Calendar.getInstance() : null;
		}
    	Calendar cal = null;
    	Date date = null;
    	try {
    		if(fmt == null){
    			if(dateStr.length() > 25){
    				//"Wed May 27 15:31:48 CST 2015";
    				if(dateStr.indexOf("CST") > 0){
    					date = DateTime_CSTFmtDdf.parse(dateStr);
    				}else{
	    				cal = Calendar.getInstance();
	    				cal.setTimeInMillis(Date.parse(dateStr));
    				}
    			}else if(dateStr.length() > 13){	// 2013-1-1 1:1:1 格式，至少 14
	    			if(dateStr.indexOf('-') > 0){
	    				date = DateTimeFmtDdf.parse(dateStr);
	    			}else if(dateStr.indexOf('/') > 0){		// 2013/1/1 1:1:1 格式，至少 14
	    				date = DateTime_LeftFmtDdf.parse(dateStr);
	    			}else if(dateStr.indexOf("年") > 0){		// 2013年1月1日 1:1:1 格式，至少 14
	    				date = DateTime_ZhFmtDdf.parse(dateStr);
	    			}
	    		}else{	// 2013-1-1 或 2013-10-10格式, 8~10字符
	    			if(dateStr.indexOf('-') > 0){
	    				date = DateFmtDdf.parse(dateStr);
	    			}else if(dateStr.indexOf('/') > 0){
	    				date = Date_LeftFmtDdf.parse(dateStr);
	    			}else if(dateStr.indexOf("年") > 0){
	    				date = Date_ZhFmtDdf.parse(dateStr);
	    			}
	    		}
    		}else{
    			date = getOneSfd(fmt).parse(dateStr);
    		}
    		if(date != null){
    			cal = Calendar.getInstance();
    			cal.setTime(date);
    		}
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return cal == null && allowNow ? Calendar.getInstance() : cal;
    }
    /**
     * 日期字符串转 Calendar, fmt=null, 则自动识别格式
     * @param dateStr (String) 日期字符串, 如果为 null, 则直接返回当前日期。
     * @param fmt (Stirng) 日期格式, 如果为null, 则自动识别格式。
     */
    public static Date toDate(String dateStr, String fmt){
    	return toDate(dateStr, fmt, true);
    }
    /**
     * 日期字符串转 Calendar, fmt=null, 则自动识别格式
     * @param dateStr (String) 日期字符串, 如果为 null, 则直接返回当前日期。
     * @param fmt (Stirng) 日期格式, 如果为null, 则自动识别格式。
     * @param allowNow (boolean) true: 解析失败返回当前时间
     */
    public static Date toDate(String dateStr, String fmt, boolean allowNow){
    	Calendar cal = toCalendar(dateStr, fmt, allowNow);
    	Date date = null;
    	if(cal == null){
    		if(allowNow){
    			date = new Date();
    		}
    	}else{
    		date = cal.getTime();
    	}
    	return date;
    }

    /**
     * 返回需要的日期格式 <br>
     * @param cal (Calendar) 日期， null则是当前日期
     * @param fmt (String) 日期格式
     */
    public static String getDateStr(Calendar cal, String fmt){
    	SimpleDateFormat sdf = getOneSfd(fmt);
        return sdf.format(cal != null ? cal.getTime() : Calendar.getInstance().getTime());
    }
    /**
     * 返回需要的日期格式 <br>
     * @param date (Date) 日期， null则是当前日期
     * @param fmt (String) 日期格式
     */
    public static String getDateStr(Date date, String fmt){
    	SimpleDateFormat sdf = getOneSfd(fmt);
    	return sdf.format(date != null ? date : new Date());
    }
    
    /**
     * 获取时间的前一天
     * @param date (Date) 日期， null则是当前日期
     * @param fmt (String) 日期格式
     */
    public static String getYesterDay(Date date,String fmt){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date != null ? date : new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return getDateStr(date, fmt);
    }
    /**
     * 在date基础上加上某天
     * @param date
     * @param plus
     * @param fmt
     * @return
     */
    public static String plusSomeDay(Date date,int plus,String fmt){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date != null ? date : new Date());
		calendar.add(Calendar.DAY_OF_MONTH, plus);
		date = calendar.getTime();
		return getDateStr(date, fmt);
    }
    
    /**
     * 获取年份
     */
    public static int getYear(){
    	Calendar calendar = Calendar.getInstance();
    	return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获取一定格式的日期,由字符串转成一定格式的date
     * @param fmtDate  String类型的日期
     * @param fmt 日期的格式
     * @param finalFmt 最终得到的日期的格式类型
     * 注意：另个参数格式要保持一致
     * 例: fmtDate="yyyy年MM月dd日";      fmt="2014年08月08日";   finalFmt="yyyy-MM-dd";
     *    fmtDate="2014-07-24 13:47";   fmt="yyyy-MM-dd";     finalFmt="yyyy年MM月dd日"
     */
    public static String getS_To_Date_S(String fmtDate,String fmt,String finalFmt){
    	SimpleDateFormat sdf = getOneSfd(fmt);
    	Date date=null;
    	try {
			date = sdf.parse(fmtDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return getDateStr(date,finalFmt);
    }
    
    /**
     * 比较日期的大小
     * @param date         Date类型的日期
     * @param dateString   字符串类型的日期
     * @param fmt          日期格式
     * dateString和fmt应保持一致
     * 例：compareDate(new Date(),"2014-09-01","yyyy-MM-dd")
     */
    public static boolean compareDate(Date date,String dateString,String fmt){
    	SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.CHINA);
    	Date d=null;
		try {
			d = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.before(date);
    }
    /**
     * @param ftmDate    时间字符串
     * @param fmt		  时间格式
     * @return
     * 注：ftmDate 和fmt要保持格式一致
     * Date date =DateUtil.stringToDate("2014-12-31", "yyyy-MM-dd");
     */
    public static Date stringToDate(String ftmDate,String fmt){
    	SimpleDateFormat sdf = getOneSfd(fmt);
    	try {
			return sdf.parse(ftmDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static boolean isCacheSfd = false;
    private static Map<String, SimpleDateFormat> Sfd_Lib;
    /** 获取一个日期格式对象, 用于节省重复创建对象的时间 */
    public static SimpleDateFormat getOneSfd(String fmt){
    	return getOneSfd(fmt, null);
    }
	/** 获取一个日期格式对象, 用于节省重复创建对象的时间 */
	public static SimpleDateFormat getOneSfd(String fmt, Locale locale){
    	SimpleDateFormat sfd = null;
    	if(isCacheSfd){
	    	if(Sfd_Lib == null) Sfd_Lib = new HashMap<String, SimpleDateFormat>();
	    	sfd = Sfd_Lib.get(fmt);
	    	if(sfd == null){
	    		if(Sfd_Lib.size() > 200) Sfd_Lib = new HashMap<String, SimpleDateFormat>();
	    		if(locale != null){
	    			sfd = new SimpleDateFormat(fmt, locale);
	    		}else{
	    			sfd = new SimpleDateFormat(fmt);
	    		}
	    		Sfd_Lib.put(fmt, sfd);
	    	}
    	}else{
    		if(locale != null){
    			sfd = new SimpleDateFormat(fmt, locale);
    		}else{
    			sfd = new SimpleDateFormat(fmt);
    		}
    	}
    	return sfd;
    }
    
    
    // TODO 日期修改
    
    /** 设置分、秒、毫秒 */
    public static void setCalMSN(Calendar cal, int m, int s, int n){
    	cal.set(Calendar.MINUTE, m);
    	cal.set(Calendar.SECOND, s);
    	cal.set(Calendar.MILLISECOND, n);
    }
    
    /** 设置时、分、秒、毫秒 */
    public static void setCalHMSN(Calendar cal, int h, int m, int s, int n){
    	cal.set(Calendar.HOUR_OF_DAY, m);
    	cal.set(Calendar.MINUTE, m);
    	cal.set(Calendar.SECOND, s);
    	cal.set(Calendar.MILLISECOND, n);
    }
    /** 设置分、秒、毫秒 */
    public static Date setDateMSN(Date date, int m, int s, int n){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	setCalMSN(cal, m, s, n);
    	return cal.getTime();
    }
    
    /** 获取一个分、秒、毫秒都为0的时间, date为null将返回当前时间 */
    public static Calendar getCal0MSN(Date date){
    	Calendar cal = Calendar.getInstance();
    	if(date != null) cal.setTime(date);
    	setCalMSN(cal, 0, 0, 0);
    	return cal;
    }
    
    /** 获取一个分、秒、毫秒都为0的时间, date为null将返回当前时间 */
    public static Date getDate0MSN(){
    	Date date = new Date();
    	date = getCal0MSN(date).getTime();
    	return date;
    }
    
    /** 获取指定时间s秒后的日期对象*/
    public static Date getDateVaildS(Date date, int s){
    	if(date == null) date = new Date();
    	long ms = s * 1000;
    	ms += date.getTime();
    	date = new Date(ms);
    	return date;
    }
    
    // TODO 比较
    
    /** true: 第一个时间减第二时间的差大于st秒 */
    public static boolean gtMs(Date base, Date oth, long st){
    	if(base == null) base = new Date();
    	st *= 1000;
    	return base.getTime() - oth.getTime() > st;
    }
    /** true: 第一个时间减第二时间的差小于st秒 */
    public static boolean ltMs(Date base, Date oth, long st){
    	if(base == null) base = new Date();
    	st *= 1000;
    	return base.getTime() - oth.getTime() < st;
    }
    /** true: 当前时间毫秒-old毫秒 > h小时, 已过去h小时以上 */
    public static boolean now_gt_h(long old, int h){
    	long newl = new Date().getTime();
    	return newl - old > (h * 3600 * 1000);
    }
    /** true: 当前时间毫秒-old毫秒 > h小时, 已过去h小时以上 */
    public static boolean now_gt_h(Date old, int h){
    	return now_gt_h(old.getTime(), h);
    }
    /** true: 当前时间毫秒-old毫秒 > h分钟, 已过去h分钟以上 */
    public static boolean now_gt_m(long old, int h){
    	long newl = new Date().getTime();
    	return newl - old > (h * 60 * 1000);
    }
    /** true: 当前时间毫秒-old毫秒 > h分钟, 已过去h分钟以上 */
    public static boolean now_gt_m(Date old, int h){
    	return now_gt_m(old.getTime(), h);
    }
    /** true: 当前时间毫秒-old毫秒 > h秒, 已过去h秒以上 */
    public static boolean now_gt_s(long old, int h){
    	long newl = new Date().getTime();
    	return newl - old > (h * 1000);
    }
    /** true: 当前时间毫秒-old毫秒 > h秒, 已过去h秒以上 */
    public static boolean now_gt_s(Date old, int h){
    	return now_gt_s(old.getTime(), h);
    }
    
    /** true: 第一个时间比第二时间大y年 */
    public static boolean gtYear(Date base, Date oth, long y){
    	if(base == null) base = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(base);
    	int by = cal.get(Calendar.YEAR);
    	
    	cal.setTime(oth);
    	int oy = cal.get(Calendar.YEAR);
    	
    	return by - oy > y;
    }
    
    /** true: 第一个时间比第二时间小y年 */
    public static boolean ltYear(Date base, Date oth, long y){
    	if(base == null) base = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(base);
    	int by = cal.get(Calendar.YEAR);
    	
    	cal.setTime(oth);
    	int oy = cal.get(Calendar.YEAR);
    	
    	return by - oy < y;
    }
    
    /** true: 第一个时间的年减第二个时间的年在[min~max]之间 */
    public static boolean betweenYear(Date base, Date oth, int min, int max){
    	if(base == null) base = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(base);
    	int by = cal.get(Calendar.YEAR);
    	
    	cal.setTime(oth);
    	int oy = cal.get(Calendar.YEAR);
    	
    	int cy = by - oy;
    	return !(cy < min || cy > max);
    }
    
    /** 当前日期 */
    public static Date now(){
    	return new Date();
    }
    
    /** 克隆一个日期 */
    public static Date cloneDate(Date date){
    	return new Date(date.getTime());
    }
    
    /** 添加 n天 */
    public static Date addDay(Date date, int n){
    	if(date == null) date = now();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DAY_OF_YEAR, n);
    	date = cal.getTime();
    	return date;
    }
    
    /** 去掉时分秒 */
    public static Date dropHms(Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	setCalHMSN(cal, 0, 0, 0, 0);
    	date = cal.getTime();
    	return date;
    }
    /** 毫秒数转换成日期类型 */
    public static Date msToDate(Object ms, boolean isNew){
    	return ms_to_date(ms, isNew);
    }
    /** 毫秒数转换成日期类型 */
    public static Date ms_to_date(Object ms, boolean isNew){
    	Date date = null;
    	Long l_ms = MUtil.toLong(ms);
		if(l_ms != null){
			date = new Date(l_ms);
		}else if(isNew){
			date = new Date();
		}
    	return date;
    }
    
    /**
     * 获取日期的毫秒字符串
     * @param date (Date)
     * @param def (String) 默认值
     */
    public static String ms_str(Date date, String def){
    	if(date != null){
    		def = Long.toString(date.getTime());
    	}
    	return def;
    }
    
    /**
     * 毫秒转日期字符串
     * @param ms (Object) 毫秒数
     * @param fmt (String) 日期格式, 默认"yyyy-MM-dd HH:mm:ss"
     * @param def (String) 默认值
     */
    public static String ms_to_str(Object ms, String fmt, String def){
    	Date st_date = DateUtil.ms_to_date(ms, false);
    	if(st_date != null){
    		def = DateUtil.getDateStr(st_date, fmt != null ? fmt : "yyyy-MM-dd HH:mm:ss");
    	}
    	return def;
    }
    
    //-------  与业务相关的文件操作方法
    
    public static void main(String[] args) {
//    	test1();
//    	test2();
//    	testMoreThread();
    	
//    	String dateStr = "Wed May 27 15:31:48 CST 2015";
//    	System.out.println(dateStr);
//    	System.out.println(getDateStr(new Date(Date.parse(dateStr)), "yyyy年MM月dd日 HH:mm:ss"));
//    	Calendar cal = toCalendar(dateStr, null, false);
//    	System.out.println(getDateStr(cal, "yyyy年MM月dd日 HH:mm:ss"));
//    	System.out.println(getDateStr(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
    	//Calendar.getInstance().
    	
    	Date odate = toDate("2015-09-10", "yyyy-MM-dd");
    	
    	System.out.println(betweenYear(null, odate, 1, 25));
    	
	}
    
    public static void test1(){
    	String str = "Mon Jan 13 14:55:13 GMT+0800 2014";
		Calendar cal = toCalendar(str);
		str = DateTimeFmtDdf.format(cal.getTime());
		System.out.println(str);
    	System.out.println(getS_To_Date_S("2014-07-24 13:47","yyyy-MM-dd","yyyy年MM月dd日"));
    	System.out.println(compareDate(new Date(),"2014-09-01到2014-09-03","yyyy-MM-dd"));
    }
    
    public static void test2(){
    	Timer t = new Timer();
    	SimpleDateFormat sdf0 = getOneSfd("yyyy");
    	int Len = 1000;
    	t.printLastTimeElapsed("1");
    	for(int i = 0; i < Len; i++){
    		SimpleDateFormat sdf = getOneSfd("yyyy");
    		String str = sdf.format(new Date());
    	}
    	t.printLastTimeElapsed("11");
    	for(int i = 0; i < Len; i++){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    		String str = sdf.format(new Date());
    	}
    	t.printLastTimeElapsed("22");
    }
    
    public static void testMoreThread(){
    	isCacheSfd = true;	//存在多线程安全问题
    	Date date = stringToDate("2014-04-14", "yyyy-MM-dd");
    	Date date2 = stringToDate("2014-04-14", "yyyy-MM-dd");
    	Date date3 = stringToDate("2014-04-14", "yyyy-MM-dd");
    	Date date4 = stringToDate("2014-04-14", "yyyy-MM-dd");
    	System.out.println(getDateStr(date, "yyyy-MM-dd"));
    	System.out.println(getDateStr(date2, "yyyy-MM-dd"));
    	System.out.println(getDateStr(date3, "yyyy-MM-dd"));
    	System.out.println(getDateStr(date4, "yyyy-MM-dd"));
    	
    	for(int i = 0; i < 50; i++){
	    	Thread t = new Thread(){
	    		public void run() {
	    			Date date = stringToDate("2014-04-14", "yyyy-MM-dd");
	    	    	Date date2 = stringToDate("2014-04-14", "yyyy-MM-dd");
	    	    	Date date3 = stringToDate("2014-04-14", "yyyy-MM-dd");
	    	    	Date date4 = stringToDate("2014-04-14", "yyyy-MM-dd");
	    	    	System.out.println(getDateStr(date, "yyyy-MM-dd"));
	    	    	System.out.println(getDateStr(date2, "yyyy-MM-dd"));
	    	    	System.out.println(getDateStr(date3, "yyyy-MM-dd"));
	    	    	System.out.println(getDateStr(date4, "yyyy-MM-dd"));
	    		}
	    	};
	    	t.start();
    	}
    }
    
}
