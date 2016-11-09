package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/slider/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/slider/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/slider/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/slider/view";
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public void delete_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_result(service.delete(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/dels.json", method = RequestMethod.POST)
	public void dels_json(String ids, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ; }
		ar.t_result(service.dels(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public void list_json(SliderSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(SliderForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String img = form.getImg();	// 配图(<500字符，完整网址)
		if(FormValid.isEmpty(img)){ ar.t_fail("7001"); return ; }
		if(!FormValid.range(img, 1, 500)){ ar.t_fail("7002"); return ; }
		
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
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(SliderForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ; }
		
		String img = form.getImg();	// 配图(<500字符，完整网址)
		if(FormValid.isEmpty(img)){ ar.t_fail("7001"); return ; }
		if(!FormValid.range(img, 1, 500)){ ar.t_fail("7002"); return ; }
		
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
	}
	
}
