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
import com.bszy.admin.security.AdminCurUtil;
import com.bszy.admin.service.ArticleService;
import com.bszy.admin.service.ArttagService;
import com.bszy.admin.service.CategoryService;
import com.bszy.admin.vo.ArticleSearch;
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
		
		Long tagid = MUtil.toLong(form.getTagid());	// 标签
		if(!FormValid.isId(tagid)){ ar.t_fail("6102"); return ar; }
		
		Long cateid = MUtil.toLong(form.getCateid());	// 版块
		if(!FormValid.isId(cateid)){ ar.t_fail("6105"); return ar; }
		
		String content = form.getContent();	// 内容
		if(FormValid.isEmpty(content)){ ar.t_fail("6103"); return ar; }
		if(!FormValid.len(content, 1, 10000)){ ar.t_fail("6104"); return ar; }
		
		Long uid = AdminCurUtil.cur_uid();	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 处理图片
		String img = form.getImg();
		String imgs = form.getImgs();
		if(MUtil.isEmpty(img) && MUtil.isNotEmpty(imgs)){
			String[] imgar = MUtil.split2(imgs, ",", false);
			if(imgar != null && imgar.length > 0) img = imgar[0];
		}
		
		Article mo = new Article();
		mo.init_add();
		mo.setName(name);
		mo.setTagid(tagid);
		mo.setCateid(cateid);
		mo.setContent(content);
		mo.setImg(img);
		mo.setImgs(imgs);
		mo.setBrief(form.getBrief());
		mo.setUserid(uid);
		mo.setRecom(0);
		mo.setSortn(MUtil.toInt(form.getSortn(), 0));
		
		mo.setLookn(0L);
		mo.setLiken(0L);
		mo.setSharen(0L);
		mo.setCommn(0L);
		mo.setFavorn(0L);
		mo.setIsdel(0);
		
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
		
		String name = form.getName();	// 名称
		if(FormValid.isEmpty(name)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(name, 1, 20)){ ar.t_fail("6002"); return ar; }
		
		Long tagid = MUtil.toLong(form.getTagid());	// 标签
		if(!FormValid.isId(tagid)){ ar.t_fail("6102"); return ar; }
		
		Long cateid = MUtil.toLong(form.getCateid());	// 版块
		if(!FormValid.isId(cateid)){ ar.t_fail("6105"); return ar; }
		
		String content = form.getContent();	// 内容
		if(FormValid.isEmpty(content)){ ar.t_fail("6103"); return ar; }
		if(!FormValid.len(content, 1, 10000)){ ar.t_fail("6104"); return ar; }
		
		Long uid = AdminCurUtil.cur_uid();	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 处理图片
		String img = form.getImg();
		String imgs = form.getImgs();
		if(MUtil.isEmpty(img) && MUtil.isNotEmpty(imgs)){
			String[] imgar = MUtil.split2(imgs, ",", false);
			if(imgar != null && imgar.length > 0) img = imgar[0];
		}
		
		Article mo = new Article();
		mo.init_update();
		mo.setId(id);
		mo.setName(name);
		mo.setTagid(tagid);
		mo.setCateid(cateid);
		mo.setContent(content);
		mo.setImg(img);
		mo.setImgs(imgs);
		mo.setBrief(form.getBrief());
		mo.setRecom(MUtil.toInteger(form.getRecom()));
		mo.setSortn(MUtil.toInteger(form.getSortn()));
		
		mo.setLookn(MUtil.toLong(form.getLookn()));
		mo.setLiken(MUtil.toLong(form.getLiken()));
		mo.setSharen(MUtil.toLong(form.getSharen()));
		mo.setCommn(MUtil.toLong(form.getCommn()));
		mo.setFavorn(MUtil.toLong(form.getFavorn()));
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
