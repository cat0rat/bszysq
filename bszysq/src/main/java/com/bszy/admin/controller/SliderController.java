package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.SliderForm;
import com.bszy.admin.pojo.Slider;
import com.bszy.admin.pojo.SliderSearch;
import com.bszy.admin.service.SliderService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/slider")
public class SliderController extends BaseController {
	
	@Inject
	private SliderService service;
	
	// TODO page
	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/slider";
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public AjaxResult get_json(Long id){
		AjaxResult ar = new AjaxResult();;
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public AjaxResult delete_json(Long id){
		AjaxResult ar = new AjaxResult();;
		ar.t_result(service.delete(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/dels.json", method = RequestMethod.POST)
	public AjaxResult dels_json(String ids){
		AjaxResult ar = new AjaxResult();;
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ar; }
		ar.t_result(service.dels(ids));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public AjaxResult list_json(SliderSearch bs){
		AjaxResult ar = new AjaxResult();;
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public AjaxResult add_json(SliderForm form){
		AjaxResult ar = new AjaxResult();;
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String img = form.getImg();	// 配图(<500字符，完整网址)
		if(FormValid.isEmpty(img)){ ar.t_fail("7001"); return ar; }
		if(!FormValid.len(img, 1, 500)){ ar.t_fail("7002"); return ar; }
		
		Slider mo = new Slider();
		mo.init_add();
		mo.setName(form.getName());
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		mo.setPos(MUtil.toInt(form.getPos(), 0));
		mo.setBrief(form.getBrief());
		mo.setLink(form.getLink());
		boolean rb = service.add(mo);
		ar.t_result(rb);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public AjaxResult update_json(SliderForm form){
		AjaxResult ar = new AjaxResult();;
		if(form == null){ ar.t_fail("1501"); return ar; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ar; }
		
		String img = form.getImg();	// 配图(<500字符，完整网址)
		if(FormValid.isEmpty(img)){ ar.t_fail("7001"); return ar; }
		if(!FormValid.len(img, 1, 500)){ ar.t_fail("7002"); return ar; }
		
		Slider mo = new Slider();
		mo.init_update();
		mo.setId(id);
		mo.setName(form.getName());
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		mo.setPos(MUtil.toInteger(form.getPos()));
		mo.setBrief(form.getBrief());
		mo.setLink(form.getLink());
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
