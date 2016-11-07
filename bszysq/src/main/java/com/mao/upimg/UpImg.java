package com.mao.upimg;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class UpImg {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	public static String ACCESS_KEY = "4MIvb9rNQ0glzdTG8rvubygpsYQDm8UoHdATcEWf";
	public static String SECRET_KEY = "oroMT6nRxYU4bU1ZTk5lLOECoEhBFrf_A8PhMnZO";
	public static String NS = "http://oflfanlr8.bkt.clouddn.com/";
	// 要上传的空间
	public static String bucketname = "xxm-bbs";
	// 密钥配置
	public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// Token
	public static String token;
	
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
		//}
		return token;
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
		new UpImg().upload("E:\\bjjx\\公用\\jzs\\小项目\\bszy\\碧水庄园社区\\1首页.png", "head/test.png");
	}
	
}