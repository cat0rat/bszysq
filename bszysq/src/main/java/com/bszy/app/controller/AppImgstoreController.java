package com.bszy.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.upimg.UpImg;

@Controller
@RequestMapping("/app/imgstore")
public class AppImgstoreController extends BaseController {
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/uptoken.json")
	public void token_json(HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(UpImg.tokenns(), "1602");
	}
	
}
