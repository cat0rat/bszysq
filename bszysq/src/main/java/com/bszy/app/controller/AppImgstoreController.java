package com.bszy.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.upimg.UpImg;

@Controller
@RequestMapping("/app/imgstore")
public class AppImgstoreController extends BaseController {
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/uptoken", method = RequestMethod.GET)
	public AjaxResult token_json(){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(UpImg.tokenns(), "1602");
		return ar;
	}
	
}
