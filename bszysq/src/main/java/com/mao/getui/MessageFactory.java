package com.mao.getui;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.log4j.Logger;
 
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.template.AbstractTemplate;
 
public class MessageFactory {
    private static Logger log = Logger.getLogger(MessageFactory.class);
    public static long ExpireTime_Hour = 1 * 1000 * 3600;
    /**
     * 创建推送到app的消息实例【所有在线都可以收到】
     * @param template
     * @param offlineExpireTime
     * @param appIdList
     * @param phoneTypeList
     * @param provinceList
     * @param tagList
     * @return
     */
    public static AppMessage bulidAppMessage(AbstractTemplate template, long offlineExpireTime, List<String> appIdList, List<String> phoneTypeList, List<String> provinceList, List<String> tagList){
        log.info("创建推送到app的消息实例...");
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(offlineExpireTime);
 
        message.setAppIdList(appIdList);
        message.setPhoneTypeList(phoneTypeList);
        message.setProvinceList(provinceList);
        message.setTagList(tagList);
        message.setPushNetWorkType(1);//根据WIFI推送设置
        return message;
    }
    /**
     * 创建推送到app的消息实例【所有在线都可以收到】
     * @param template
     * @param appIdList
     * @param phoneTypeList
     * @param provinceList
     * @param tagList
     * @return
     */
    public static AppMessage bulidAppMessage(AbstractTemplate template, List<String> appIdList, List<String> phoneTypeList, List<String> provinceList, List<String> tagList){
        return bulidAppMessage(template, ExpireTime_Hour, appIdList, phoneTypeList, provinceList, tagList);
    }
    /**
     * 创建推送到app的消息实例【所有在线都可以收到】
     * @param template
     * @return
     */
    public static AppMessage bulidAppMessage(AbstractTemplate template){
        List<String> appIdList = new ArrayList<String>(); 
        List<String> phoneTypeList = new ArrayList<String>(); 
        List<String> provinceList = new ArrayList<String>(); 
        List<String> tagList = new ArrayList<String>();
        appIdList.add(AppConfig.appId);
        phoneTypeList.add("ANDROID");
        phoneTypeList.add("APPLE");
        tagList.add("推送");
        return bulidAppMessage(template, appIdList, phoneTypeList, provinceList, tagList);
    }
    /**
     * 创建推送单个用户的消息实例
     * @param template
     * @param offlineExpireTime
     * @return
     */
    public static SingleMessage bulidSingleMessage(AbstractTemplate template, long offlineExpireTime){
        log.info("创建推送推送单个用户的消息实例...");
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        message.setOfflineExpireTime(offlineExpireTime);
        message.setData(template);
        message.setPushNetWorkType(1);//根据WIFI推送设置
        return message;
    }
    /**
     * 创建推送单个用户的消息实例
     * @param template
     * @return
     */
    public static SingleMessage bulidSingleMessage(AbstractTemplate template){
        return bulidSingleMessage(template, ExpireTime_Hour);
    }
}
