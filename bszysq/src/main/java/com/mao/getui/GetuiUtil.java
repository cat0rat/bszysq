package com.mao.getui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.mao.ini.PathIniUtil;

public class GetuiUtil {
	// 定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	// private static String appId = "WytD1A1DZ5APOQXUPzBkb9";
	// private static String appKey = "GZdaaqFq8572joeMowwZw2";
	// private static String masterSecret = "mGiDUrSev7772amZWCk5Q1";
	// //private static String AppSecret = "znKJHWnTr79VfRvv0GivR9";
	// private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

	private static String appId = PathIniUtil.getConfig().getValue("Getui_appId");
	private static String appKey = PathIniUtil.getConfig().getValue("Getui_appKey");
	private static String masterSecret = PathIniUtil.getConfig().getValue("Getui_masterSecret");
	private static String AppSecret = PathIniUtil.getConfig().getValue("Getui_AppSecret");
	private static String url = PathIniUtil.getConfig().getValue("Getui_url");
	public static int Offline_Expire_Time = 24 * 3600 * 1000;

	public static TransmissionTemplate transmissionTemplate(String msgstr) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(msgstr);
		// template.setTransmissionContent("请输入需要透传的内容");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static void forIosTmpl(TransmissionTemplate template) {
		APNPayload payload = new APNPayload();
		payload.setBadge(1);
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("");
		payload.setAlertMsg(new SimpleAlertMsg("碧水庄园社区"));
		template.setAPNInfo(payload);
	}

	private static DictionaryAlertMsg getDictionaryAlertMsg() {
		DictionaryAlertMsg alertMsg = new DictionaryAlertMsg();
		alertMsg.setBody("您有新的消息~");
		alertMsg.setActionLocKey("ActionLockey");
		alertMsg.setLocKey("LocKey");
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		alertMsg.setTitle("碧水庄园社区");
		alertMsg.setTitleLocKey("TitleLocKey");
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

	public static String toJsonStr(Object mo) {
		String result = JSONObject.toJSONString(mo);
		return result;
	}

	/**
	 * 给指定客户端发送消息
	 * @param cid
	 * @param msg
	 */
	public static void pushMsgToSingle(String cid, String msg) {
		pushMsgToSingle(cid, msg, true);
	}

	/**
	 * 给指定客户端发送消息
	 * @param cid (String) ClientID
	 * @param msgstr (String) 消息内容
	 * @param isan (boolean) true: Android, false: IOS
	 */
	public static boolean pushMsgToSingle(String cid, String msgstr, boolean isan) {
		if(cid == null || cid.length() == 0 || msgstr == null || msgstr.length() == 0) return false;
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		// String cid = "58dfb1708d3805ac9049d614d610c60b";
		TransmissionTemplate template = transmissionTemplate(msgstr);
		SingleMessage msg = new SingleMessage();
		msg.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		msg.setOfflineExpireTime(Offline_Expire_Time);
		msg.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		msg.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cid);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			if (!isan)
				forIosTmpl(template);
			ret = push.pushMessageToSingle(msg, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(msg, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			return true;
		} else {
			System.out.println("服务器响应异常");
		}
		return false;
	}
	

	/**
	 * 给一组客户端发送消息
	 * @param cids (List<String>) ClientIDs
	 * @param msg (String) 消息内容
	 * @param isan (boolean) true: Android, false: IOS
	 */
	public static boolean pushMsgToList(List<String> cids, String msgstr, boolean isan) {
		if(msgstr == null || msgstr.length() == 0) return false;
		
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		// String cid = "58dfb1708d3805ac9049d614d610c60b";
		TransmissionTemplate template = transmissionTemplate(msgstr);
		ListMessage msg = new ListMessage();
		msg.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		msg.setOfflineExpireTime(Offline_Expire_Time);
		msg.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		msg.setPushNetWorkType(0);
		
		// 配置推送目标
		List<Target> targets = new ArrayList<>();
		Target target = null;
		for(String cid : cids){
			target = new Target();
			target.setAppId(appId);
			target.setClientId(cid);
			// target.setAlias(Alias);
			targets.add(target);
		}
		IPushResult ret = null;
		try {
			if (!isan)
				forIosTmpl(template);
			// taskId用于在推送时去查找对应的message
			String taskId = push.getContentId(msg);
			ret = push.pushMessageToList(taskId, targets);
		} catch (RequestException e) {
			e.printStackTrace();
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			return true;
		} else {
			System.out.println("服务器响应异常");
		}
		return false;
	}
	

	/**
	 * 给App所有用户发送消息
	 * @param msg (String) 消息内容
	 * @param phonex (Integer) 1: Android, 2: IOS, 其他: 不区分
	 */
	public static boolean pushMsgToApp(String msgstr, Integer phonex) {
		if(msgstr == null || msgstr.length() == 0) return false;
		
		IGtPush push = new IGtPush(url, appKey, masterSecret);
		// String cid = "58dfb1708d3805ac9049d614d610c60b";
		TransmissionTemplate template = transmissionTemplate(msgstr);
		AppMessage msg = new AppMessage();
		msg.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		msg.setOfflineExpireTime(Offline_Expire_Time);
		msg.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		msg.setPushNetWorkType(0);
		
		//推送给App的目标用户需要满足的条件
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		msg.setAppIdList(appIdList);
		
		// 手机类型
		if(phonex != null){
			List<String> phoneTypeList = new ArrayList<String>();
			if(phonex == 1) phoneTypeList.add("ANDROID");
			if(phonex == 2) phoneTypeList.add("IOS");
			if(phoneTypeList.size() > 0) cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		}
		
		// cdt.addCondition(AppConditions.REGION, provinceList);
		// cdt.addCondition(AppConditions.TAG, tagList);
		msg.setConditions(cdt);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToApp(msg);	//, new Date().getTime() + "_toApp");
		} catch (RequestException e) {
			e.printStackTrace();
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			return "ok".equals(ret.getResponse().get("result"));
		} else {
			System.out.println("服务器响应异常");
		}
		return false;
	}
	

}
