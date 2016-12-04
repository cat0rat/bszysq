package com.mao.smscode;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliDayuSms {
	private static String url = "https://eco.taobao.com/router/rest";
	private static String appkey = "23535847";
	private static String secret = "24fb9fda7cf4531ca7ad16689e25deeb";

	public static void main(String[] args) throws ApiException {
//		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//		req.setExtend("");
//		req.setSmsType("normal");
//		req.setSmsFreeSignName("小懒猫");
//		req.setSmsParamString("{\"code\":\"1234\"}");
//		req.setRecNum("13370175853");
//		req.setSmsTemplateCode("SMS_27540177");
//		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
		System.out.println(sendSmscode2("13370175853", "1111"));
	}
	
	public static String sendSmscode2(String mobile, String code){
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName("小懒猫");
		req.setSmsParamString("{\"code\":\"" + code + "\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_27540177");
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		String rstr = "发送短信验证码失败";
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				rstr = null;
			}else{
				if(rsp.getMsg() != null) throw new ApiException(rsp.getMsg());
				rstr = rsp.getSubMsg();
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return rstr;
	} 
}
