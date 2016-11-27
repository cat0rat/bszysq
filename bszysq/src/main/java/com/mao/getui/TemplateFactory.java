package com.mao.getui;

import org.apache.log4j.Logger;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
 
public class TemplateFactory {
    private static Logger log = Logger.getLogger(TemplateFactory.class);
    private static String appId = AppConfig.appId;
    private static String appkey = AppConfig.appkey;
    /**
     * 创建可透传的消息模板
     * @param transType
     * @param content
     * @return
     */
    public static TransmissionTemplate bulidTransTemplate(TransType transType, String content){
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        //收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
        template.setTransmissionType(transType.toInt());
        //透传的内容
        template.setTransmissionContent(content);
        try {
            template.setPushInfo("actionLocKey", 3, "message", "sound", "payload", "locKey", "locArgs", "launchImage");
            return template;
        } catch (Exception e) {
            log.error("创建透传模板时发生错误.", e);
            return null;
        }
    }
     
    public static LinkTemplate bulidLinkTemplate(String title, String content, String logo, String logoUrl,
                                                    boolean isRing, boolean isVibrate, boolean isClearable,
                                                    String linkUrl) throws Exception {
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTitle(title);
        template.setText(content);
        template.setLogo(logo);
        template.setLogoUrl(logoUrl);
        template.setIsRing(isRing);
        template.setIsVibrate(isVibrate);
        template.setIsClearable(isClearable);
        template.setUrl(linkUrl);
        template.setPushInfo("actionLocKey", 1, "message", "sound", "payload",
                "locKey", "locArgs", "launchImage", 1);
        return template;
    }
     
    public static LinkTemplate bulidLinkTemplate(String title, String content, String logo, String logoUrl, String linkUrl) throws Exception {
        return bulidLinkTemplate(title, content, logo, logoUrl, true, true, true, linkUrl);
    }
     
    public static NotificationTemplate bulidNotifyTemplateDemo(String title, String content, String logoName, String logoUrl,
                                            boolean isRing, boolean isVibrate, boolean isClearable, String transContent)
            throws Exception {
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        template.setTitle(title);
        template.setText(content);
        template.setLogo(logoName);
        template.setLogoUrl(logoUrl);
        template.setIsRing(isRing);
        template.setIsVibrate(isVibrate);
        template.setIsClearable(isClearable);
        template.setTransmissionType(1);
        template.setTransmissionContent(transContent);
        template.setPushInfo("actionLocKey", 2, "message", "sound", "payload", "locKey", "locArgs", "launchImage");
        return template;
    }
     
    public static NotificationTemplate bulidNotifyTemplateDemo(String title, String content, String logoName, String logoUrl, String transContent) throws Exception {
        return bulidNotifyTemplateDemo(title, content, logoName, logoUrl, true, true, true, transContent);
    }
 
    /*public static NotyPopLoadTemplate bulidNotyPopLoadTemplate() {
        NotyPopLoadTemplate template = new NotyPopLoadTemplate();
        // 填写appid与appkey
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 填写通知标题和内容
        template.setNotyTitle("标题");
        template.setNotyContent("内容");
        // template.setLogoUrl("");
        // 填写图标文件名称
        template.setNotyIcon("text.png");
        // 设置响铃，震动，与可清除
        // template.setBelled(false);
        // template.setVibrationed(false);
        // template.setCleared(true);
 
        // 设置弹框标题与内容
        template.setPopTitle("弹框标题");
        template.setPopContent("弹框内容");
        // 设置弹框图片
        template.setPopImage("http://www-igexin.qiniudn.com/wp-content/uploads/2013/08/logo_getui1.png");
        template.setPopButton1("打开");
        template.setPopButton2("取消");
 
        // 设置下载标题，图片与下载地址
        template.setLoadTitle("下载标题");
        template.setLoadIcon("file://icon.png");
        template.setLoadUrl("http://gdown.baidu.com/data/wisegame/c95836e06c224f51/weixinxinqing_5.apk");
        template.setActived(true);
        template.setAutoInstall(true);
        template.setAndroidMark("");
        return template;
    }*/
     
    public enum TransType{
        Immediately{
            public int toInt(){
                return 1;
            }
        },
        Wait{
            public int toInt(){
                return 2;
            }
        };
         
        public int toInt(){
            throw new AbstractMethodError("该方法未实现");
        }
    }
}
