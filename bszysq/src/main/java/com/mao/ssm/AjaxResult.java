package com.mao.ssm;

import java.util.HashMap;

public class AjaxResult {
	String code = "-1";
	String msg = "执行失败";
	Object data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	// 辅助方法
	public void t_result(boolean rb){
		if(rb){
			t_succ();
		}else{
			t_fail(null);
		}
	}
	
	/** 标记成功 */
	public void t_succ(){
		code = "200";
		msg = "";
	}
	/** 设置数据, 并标记成功 */
	public void t_succ(Object data){
		t_succ();
		this.data = data != null ? data : new HashMap();
	}
	/** 设置数据, 不为空则标记成功, 否则失败 */
	public void t_succ_not_null(Object data){
		if(data != null){
			t_succ(data);
		}else{
			t_fail(null);
		}
	}
	
	/** 设置错误代码, 并标记失败 */
	public void t_fail(String code){
		this.code = code != null ? code : "-1";
	}
	/** 设置错误代码及提示, 并标记失败 */
	public void t_fail(String code, String msg){
		this.code = code != null ? code : "-1";
		this.msg = msg != null ? msg : "";
	}
	
}
