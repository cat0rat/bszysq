package com.mao.upimg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mao.ini.PathIniUtil;
import com.qiniu.common.Config;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class UpImg {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
//	public static String ACCESS_KEY = "4MIvb9rNQ0glzdTG8rvubygpsYQDm8UoHdATcEWf";
//	public static String SECRET_KEY = "oroMT6nRxYU4bU1ZTk5lLOECoEhBFrf_A8PhMnZO";
//	public static String NS = "http://oflfanlr8.bkt.clouddn.com/";
//	public static String bucketname = "xxm-bbs";	// 要上传的空间
	
	public static String ACCESS_KEY = PathIniUtil.getConfig().getValue("Qiniu_Access_Key");
	public static String SECRET_KEY = PathIniUtil.getConfig().getValue("Qiniu_Secret_Key");
	public static String NS = PathIniUtil.getConfig().getValue("Qiniu_NS");
	public static String bucketname = PathIniUtil.getConfig().getValue("Qiniu_bucketname");	// 要上传的空间
	
	// 密钥配置
	public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// Token
	public static String token;
	static{
		Config.zone = Zone.zone1();
	}
	
	// 上传到七牛后保存的文件名
	String key = null;
	// 上传文件的路径
	String filePath = null;
	// 创建上传对象
	UploadManager uploadManager = new UploadManager();

	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public static String getUpToken() {
		//if(token == null){
			token = auth.uploadToken(bucketname);
			//System.out.println("七牛host:" + (Config.zone != null ? Config.zone.upHost : null));
		//}
		return token;
	}
	
	static Map<String, String> tokenns = new HashMap<String, String>();
	/** 返回uptoken和ns(Url前缀) */
	public static Map<String, String> tokenns(){
		String token = getUpToken();
		if(token == null || token.length() == 0) return null;
		tokenns.put("ns", NS);
		tokenns.put("uptoken", token);
		return tokenns;
	}
	
	public void upload(String filePath, String key) throws IOException {
		this.filePath = filePath;
		this.key = key;
		upload();
	}

	public void upload() throws IOException {
		try {
			// 调用put方法上传
			Response res = uploadManager.put(filePath, key, getUpToken());
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}
	
	public String upload(byte[] bytes, String key) throws IOException {
		try {
			// 调用put方法上传
			Response res = uploadManager.put(bytes, key, getUpToken());
			// 打印返回的信息
			System.out.println(res.bodyString());
			return NS + key;
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		new UpImg().upload("E:/temp/ass/gg.jpg", "head/test5.png");
	}
	
}
