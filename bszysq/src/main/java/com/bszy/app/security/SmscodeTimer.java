package com.bszy.app.security;

import java.util.HashMap;
import java.util.Map;

import com.mao.lang.Timer;

/**
 * 短信验证码 定时器, 默认 1小时失效
 * @author jzs 2016年11月21日 下午11:39:27
 */
public class SmscodeTimer {
	private final static Map<String, SmscodeTimer> smses = new HashMap<String, SmscodeTimer>();
	private String mobile;
	private String smscode;
	private Timer timer = new Timer(Timer.Hour, false);
	
	public SmscodeTimer(String mobile, String smscode) {
		this.mobile = mobile;
		this.smscode = smscode;
		smses.put(mobile, this);
	}
	
	/**
	 * 构造一个手机号和短信验证码的缓存
	 * @param mobile
	 * @param ss
	 */
	public static SmscodeTimer build(String mobile, String smscode){
		return new SmscodeTimer(mobile, smscode);
	}
	/**
	 * 手机号, 相对 ss秒, 剩下的时间, null 则不剩下了。
	 * @param mobile
	 * @param ss
	 * @return
	 */
	public static Long remaining(String mobile, int ss){
		SmscodeTimer st = smses.get(mobile);
		Long es = null;
		if(st != null){
			es = ss - st.getTimer().getTimeElapsed() / 1000;
			if(es < 1) es = null;
		}
		return es;
	}
	
	/**
	 * 通过手机号获取当前的短信验证码, 并检测是否过期
	 * @param mobile
	 */
	public static String smscode(String mobile){
		SmscodeTimer st = smses.get(mobile);
		if(st != null){
			if(st.getTimer().isNotUp()) return st.getSmscode();
			smses.remove(mobile);
		}
		return null;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public static Map<String, SmscodeTimer> getSmses() {
		return smses;
	}
	
}
