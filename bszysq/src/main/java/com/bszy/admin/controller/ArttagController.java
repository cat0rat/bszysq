package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.ArttagForm;
import com.bszy.admin.pojo.Arttag;
import com.bszy.admin.pojo.ArttagSearch;
import com.bszy.admin.service.ArttagService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.BasePage;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/arttag")
public class ArttagController extends BaseController {
	
	@Inject
	private ArttagService service;
	
	// TODO page

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/arttag/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/arttag/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/arttag/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/arttag/view";
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping("/get.json")
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Arttag mo = service.get(id);
		ar.t_succ_not_null(mo);
	}
	
	@ResponseBody
	@RequestMapping("/delete.json")
	public void delete_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		boolean rb = service.delete(id);
		ar.t_result(rb);
	}
	

	@ResponseBody
	@RequestMapping("/list.json")
	public void list_json(ArttagSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		BasePage<Arttag> bp = service.list(bs);
		ar.t_succ_not_null(bp);
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(ArttagForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){
			ar.t_fail("1501");
			return ;
		}
		String name = form.getName();
		if(FormValid.isEmpty(name)){
			ar.t_fail("5001");
			return ;
		}
		if(!FormValid.range(name, 1, 20)){
			ar.t_fail("5002");
			return ;
		}
		
		Arttag mo = new Arttag();
		mo.init_add();
		mo.setName(name);
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(ArttagForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){
			ar.t_fail("1501");
			return ;
		}
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){
			ar.t_fail("1501");
			return ;
		}
		String name = form.getName();
		if(FormValid.isEmpty(name)){
			ar.t_fail("5001");
			return ;
		}
		if(!FormValid.range(name, 1, 20)){
			ar.t_fail("5002");
			return ;
		}
		
		Arttag mo = new Arttag();
		mo.init_update();
		mo.setId(id);
		mo.setName(name);
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}
	
}
