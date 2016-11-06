package com.bszy.admin.controller;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bszy.admin.form.ImgstoreForm;
import com.mao.lang.DateUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.upimg.UpImg;

@Controller
@RequestMapping("/admin/imgstore")
public class ImgstoreController extends BaseController {
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/upload.json", method = RequestMethod.POST)
	public void upload_json(ImgstoreForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		MultipartFile mfile = form.getFile();
		String url = null;
		if(mfile != null && !mfile.isEmpty()){
			String dir = DateUtil.getDateStr(new Date(), "yyyyMM");
			String ofn = mfile.getOriginalFilename();
			String newFn = UUID.randomUUID().toString() + ofn.substring(ofn.lastIndexOf('.'));
			String newFpath = dir + '/' + newFn;
			try {
				byte[] bytes = mfile.getBytes();
				UpImg upimg = new UpImg();
				url = upimg.upload(bytes, newFpath);
			} catch (Exception e) {
				logger.error("处理图片异常!", e);
			}
		}
		ar.t_succ_not_null(url, "1601");
	}
	
}
