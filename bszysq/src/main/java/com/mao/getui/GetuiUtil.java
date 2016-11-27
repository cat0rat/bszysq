package com.mao.getui;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GetuiUtil {
	//定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "WytD1A1DZ5APOQXUPzBkb9";
    private static String appKey = "GZdaaqFq8572joeMowwZw2";
    private static String masterSecret = "mGiDUrSev7772amZWCk5Q1";
    //private static String AppSecret = "znKJHWnTr79VfRvv0GivR9";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    
    public static int Offline_Expire_Time = 24 * 3600 * 1000;
    
    public static TransmissionTemplate transmissionTemplate(String msg) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(msg);
        //template.setTransmissionContent("请输入需要透传的内容");
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
//    public static void main(String[] args) {
//    	GetuiPojo mo = new GetuiPojo("消息", "小懒猫推推~");
//    	String msg = "小懒猫推推~";//toJsonStr(mo);
//    	pushMessageToSingle("58dfb1708d3805ac9049d614d610c60b", msg);
//	}
    
    public static String toJsonStr(Object mo){
    	String result = JSONObject.toJSONString(mo);
		return result;
    }
    /**
     * 给指定客户端发送消息
     * @param cid
     * @param msg
     */
    public static void pushMessageToSingle(String cid, String msg){
    	pushMessageToSingle(cid, msg, true);
    }
    
    /**
     * 给指定客户端发送消息
     * @param cid
     * @param msg
     */
    public static void pushMessageToSingle(String cid, String msg, boolean isan){
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        //String cid = "58dfb1708d3805ac9049d614d610c60b";
        ITemplate template = transmissionTemplate(msg);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(Offline_Expire_Time);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
    		ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }
    
}
