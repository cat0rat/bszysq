package com.mao.lang;

/**
 * 计时器
 * @author mao 2012-5-9 下午07:43:14
 */
public class Timer extends java.lang.Object {
	public final static long Second = 1000;
	public final static long Minute = 60 * Second;
	public final static long Hour = 60 * Minute;
	public final static long Day = 24 * Hour;
	public final static long Month = 30 * Day;
	public final static long Year = 365 * Day;
	
	public final static long M_Nano = 1000000;
	
	/** 开始时间 */
	public long startTime;
	/** 结束时间 */
	public long endTime;
	/** 间隔时间 */
	public long timeLimit;
	/** 上一次 toStringTime()的时间, 用于中间取时间段 */
	public long lastOutTime;
	/** 是否向控制台输出信息, 默认true是, 方便控制信息的输出 */
	public boolean isPrint = true;
	/** (全局)是否向控制台输出信息, 默认true是, 方便控制信息的输出 */
	public static boolean s_isPrint = true;
	/** 精度, true:纳秒, false:毫秒 */
	public boolean nano = false;
	/** (全局)精度, true:纳秒, false:毫秒 */
	public static boolean s_nano = true;

	/** 构造器, 默认 1 Day */
	public Timer() {
		this(Day);
	}
	/** 一年的计时器 */
	public final static Timer yearTimer = new Timer(Year);
	
	/** 用于跨函数、跨类计时器 */
	public static Timer fnTimer = new Timer(Year);
	/** 用于跨函数、跨类计时器 */
	public static Timer getFnTimer(){
		if(fnTimer == null){
			fnTimer = new Timer(Year); 
		}
		return fnTimer;
	}
	
	/**
	 * 构造器
	 * @param timeLimit (long) 间隔时间, 持续时间(毫秒)
	 */
	public Timer(long timeLimit) {
		init(s_nano ? timeLimit * M_Nano : timeLimit, s_nano);
	}
	/**
	 * 构造器
	 * @param timeLimit (long) 间隔时间, 持续时间(毫秒/纳秒), 1毫秒=1*Timer.M_Nano;
	 * @param nano (boolean) 精度, true:纳秒, false:毫秒
	 */
	public Timer(long timeLimit, boolean nano) {
		init(timeLimit, nano);
	}
	
	/**
	 * 初化 变量
	 * @param timeLimit (long) 间隔时间, 持续时间(毫秒/纳秒), 1毫秒=1*Timer.M_Nano;
	 * @param nano (boolean) 精度, true:纳秒, false:毫秒
	 */
	public void init(long timeLimit, boolean nano){
		this.nano = nano;
		this.timeLimit = timeLimit;
		this.startTime = curTime();
		this.endTime = startTime + timeLimit;
		this.lastOutTime = this.startTime;
	}
	
	/**
	 * 返回当前系统时间
	 * @param nano (boolean) true: 返回纳秒
	 */
	public long curTime(){
		return curTime(nano);
	}
	
	/**
	 * 返回当前系统时间
	 * @param nano (boolean) true: 返回纳秒
	 */
	public long curTime(boolean nano){
		if(nano){
			return System.nanoTime();
		}else{
			return System.currentTimeMillis();
		}
	}

	/** 已过去的时间 */
	public long getTimeElapsed() {
		return (curTime() - startTime);
	}
	/** 距离上次 toStringTime()已过去的时间 */
	public long getLastTimeElapsed() {
		return (curTime() - lastOutTime);
	}

	/** 剩余的时间 */
	public long getTimeRemaining() {
		if (isNotUp())
			return (endTime - curTime());
		else
			return (0);
	}

	/** true: 未过时 */
	public boolean isNotUp() {
		if (curTime() < endTime)
			return true;
		else
			return false;
	}

	/** true: 过时了 */
	public boolean isUp() {
		if (curTime() >= endTime)
			return true;
		else
			return false;
	}

	/** 过时则重置, 且返回true */
	public boolean isUpThenReset() {
		if (isUp()) {
			reset();
			return true;
		} else
			return false;
	}

	/** 重置 */
	public void reset() {
		this.endTime = curTime() + timeLimit;
	}
	/** 重新开始, 重置开始及结束 */
	public void restart(){
		restart(true);
	}
	/** 重新开始, all: false, 则只重置开始, 不重置结束 */
	public void restart(boolean all){
		this.startTime = curTime();
		if(all){
			this.endTime = startTime + timeLimit;
			this.lastOutTime = this.startTime;
		}
	}

	/**
	 * 以当前时间开始计时, ms毫秒后过时。
	 * @param ms (long) 追加的时间(毫秒)
	 */
	public long setTimerToEndIn(long ms) {
		this.endTime = curTime() + ms;
		return endTime;
	}

	public String toString() {
		return getClass().getName() + '@' + Integer.toHexString(hashCode());
	}

	/** 转换成时间字符串 */
	public String toStringTime(long time) {
		final StringBuilder t = new StringBuilder();
		long tnano = 0;
		if(nano){
			tnano = time % M_Nano;
			time /= M_Nano;
		}
		final long TotalSec = time / 1000;
		final long TotalMin = TotalSec / 60;
		final long TotalHour = TotalMin / 60;
		final int mm = (int) time % 1000;
		final int second = (int) TotalSec % 60;
		final int minute = (int) TotalMin % 60;
		final int hour = (int) TotalHour % 60;
		if (hour < 10)
			t.append("0");
		t.append(hour);
		t.append(":");
		if (minute < 10)
			t.append("0");
		t.append(minute);
		t.append(":");
		if (second < 10)
			t.append("0");
		t.append(second);
		t.append(":");
		if(mm < 10){
			t.append("00");
		}else if(mm < 100){
			t.append("0");
		}
		t.append(mm);
		if(nano){
			t.append(".");
			if(tnano < 10){
				t.append("00000");
			}else if(tnano < 100){
				t.append("0000");
			}else if(tnano < 1000){
				t.append("000");
			}else if(tnano < 10000){
				t.append("00");
			}else if(tnano < 100000){
				t.append("0");
			}
			t.append(tnano);
		}
				
		return (t.toString());
	}
	
	/** 转换成时间字符串, 内部方法, 会记录当前时间到lastTime */
	public String _toStringTime(long time){
		lastOutTime = curTime();
		return toStringTime(time);
	}

	/** 已过去的时间 -- 字符串 */
	public String toStringTimeElapsed() {
		return (_toStringTime(getTimeElapsed()));
	}

	/** 剩余的时间 -- 字符串 */
	public String toStringTimeRemaining() {
		return (_toStringTime(getTimeRemaining()));
	}
	
	/** 距离上次 toStringTime()已过去的时间 -- 字符串 */
	public String toStringLastTimeElapsed(){
		return (_toStringTime(getLastTimeElapsed()));
	}
	
	public final static int Elapsed_Type = 1;
	public final static int Remaining_Type = 2;
	public final static int LastTimeElapsed_Type = 3;
	
	/** 打印已逝去的时间 */
	public void printElapsed(Object str){
		if(s_isPrint && isPrint){
			System.out.println(str + ", " + toStringTimeElapsed());
		}
	}
	/** 打印剩余的时间 */
	public void printRemaining(Object str){
		if(s_isPrint && isPrint){
			System.out.println(str + ", " + toStringTimeRemaining());
		}
	}
	/** 打印 上次 toStringTime()已过去的时间 */
	public void printLastTimeElapsed(Object str){
		if(s_isPrint && isPrint){
			System.out.println(str + ", " + toStringLastTimeElapsed());
		}
	}
	/** 设置是否向控制台输出信息 */
	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}


	/** 测试 */
	public static void main(String[] args) throws InterruptedException {
		Timer t = new Timer(1000);
		Thread.sleep(10);
		System.out.println(t.toStringLastTimeElapsed());
		Thread.sleep(10);
		System.out.println(t.toStringLastTimeElapsed());
		Thread.sleep(20);
		System.out.println(t.toStringLastTimeElapsed());
		Thread.sleep(50);
		System.out.println(t.toStringLastTimeElapsed());
		System.out.println(t.toStringTimeElapsed());
		System.out.println(t.toStringTimeRemaining());
	}
	
}