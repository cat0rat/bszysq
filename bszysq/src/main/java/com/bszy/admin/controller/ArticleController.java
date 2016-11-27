package com.bszy.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.ArticleForm;
import com.bszy.admin.pojo.Article;
import com.bszy.admin.pojo.ArticleSearch;
import com.bszy.admin.service.ArticleService;
import com.bszy.admin.service.ArttagService;
import com.bszy.admin.service.CategoryService;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.AjaxResultUtil;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/article")
public class ArticleController  extends BaseController {
	
	@Inject
	private ArticleService service;
	@Inject
	private CategoryService categoryService;
	@Inject
	private ArttagService arttagService;
	
	// TODO page

	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(Model model){
		Map<String, Object> jar = new HashMap<String, Object>();
		jar.put("cates", categoryService.list());
		jar.put("tags", arttagService.list());
		model.addAttribute("jar", AjaxResultUtil.toJsonStr(jar));
		return "admin/article";
	}
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/get.json", method = RequestMethod.POST)
	public AjaxResult get_json(Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public AjaxResult delete_json(Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_result(service.delete(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/dels.json", method = RequestMethod.POST)
	public AjaxResult dels_json(String ids){
		AjaxResult ar = new AjaxResult();
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ar; }
		ar.t_result(service.dels(ids));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/recoms.json", method = RequestMethod.POST)
	public AjaxResult recoms_json(String ids, Integer status){
		AjaxResult ar = new AjaxResult();
		if(ids == null || !FormValid.isIds(ids) || status == null){ ar.t_fail("1501"); return ar; }
		ar.t_result(service.recoms(ids, status));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public AjaxResult list_json(ArticleSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public AjaxResult add_json(ArticleForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();	// 名称
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(name, 1, 20)){ ar.t_fail("6002"); return ar; }
		
		Article mo = new Article();
		mo.init_add();
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		boolean rb = service.add(mo);
		ar.t_result(rb);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public AjaxResult update_json(ArticleForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(name, 1, 20)){ ar.t_fail("6002"); return ar; }
		
		Article mo = new Article();
		mo.init_update();
		mo.setId(id);
		mo.setName(name);
		mo.setImg(form.getImg());
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
