package com.mao.smscode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class MiaoDiSms {
	
	/**
	 * url前半部分
	 */
	public static final String Base_Url = "https://api.miaodiyun.com/20150822";

	/**
	 * 开发者注册后系统自动生成的账号，可在官网登录后查看
	 */
	public static final String Account_Sid = "e4fa672856d8435e85facae9e539c096";

	/**
	 * 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
	 */
	public static final String Auth_Token = "24812bbde786469f84a1147958f5a9d1";

	/**
	 * 响应数据类型, JSON或XML
	 */
	public static final String Resp_Data_Type = "json";
	
	private static String operation = "/industrySMS/sendSMS";
	private static String smsContent = "【碧水庄园社区】您的验证码为${code}，如非本人操作，请忽略此短信。";
	
	public static String sendSmscode(String mobile, String code){
		String cnt = smsContent.replaceAll("\\$\\{code\\}", code);
		String url = Base_Url + operation;
		String body = "accountSid=" + Account_Sid + "&to=" + mobile + "&smsContent=" + cnt
				+ createCommonParam();

		// 提交请求
		String result = post(url, body);
		if(result == null || result.length() == 0){
			result = "发送短信异常";
		}else if(result.indexOf("\"0000\"") != 0){
			result = null;
		}else{
			System.out.println(result);
			result = "发送短信异常";
		}
		return result;
	}
	
	public static void main(String[] args) {
		String result = sendSmscode("13370175853", "7890");
		System.out.println("result:" + System.lineSeparator() + result);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 构造通用参数timestamp、sig和respDataType
	 * 
	 * @return
	 */
	public static String createCommonParam() {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());

		// 签名
		String sig = DigestUtils.md5Hex(Account_Sid + Auth_Token+ timestamp);
		return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + Resp_Data_Type;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String body) {
//		System.out.println("url:" + System.lineSeparator() + url);
//		System.out.println("body:" + System.lineSeparator() + body);

		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
