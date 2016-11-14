package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.ArticleForm;
import com.bszy.admin.pojo.Article;
import com.bszy.admin.pojo.ArticleSearch;
import com.bszy.admin.service.ArticleService;
import com.bszy.app.security.AppUserCurUtil;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppArticleController extends BaseController {
	
	@Inject
	private ArticleService service;
	
	@ResponseBody
	@RequestMapping(value = "/article/get/{id}.json")
	public void get_var(@PathVariable Long id, HttpServletRequest request){
		get(id, request);
	}
	
	@ResponseBody
	@RequestMapping(value = "/article/get.json")
	public void get(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping("/article/list.json")
	public void list(ArticleSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}
	
	// TODO 需登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/article/add.json", method = RequestMethod.POST)
	public void add_json(ArticleForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String name = form.getName();	// 标题
		if(FormValid.isEmpty(name)){ ar.t_fail("6100"); return ; }
		if(!FormValid.len(name, 1, 500)){ ar.t_fail("6101"); return ; }
		
		Long tagid = MUtil.toLong(form.getTagid());	// 标签
		if(!FormValid.isId(tagid)){ ar.t_fail("6102"); return ; }
		
		Long cateid = MUtil.toLong(form.getCateid());	// 版块
		if(!FormValid.isId(cateid)){ ar.t_fail("6105"); return ; }
		
		String content = form.getContent();	// 内容
		if(FormValid.isEmpty(content)){ ar.t_fail("6103"); return ; }
		if(!FormValid.len(content, 1, 10000)){ ar.t_fail("6104"); return ; }
		
		Long uid = AppUserCurUtil.cur_uid();
		
		Article mo = new Article();
		mo.init_add();
		mo.setName(name);
		mo.setTagid(tagid);
		mo.setCateid(cateid);
		mo.setContent(content);
		mo.setImg(form.getImg());
		mo.setImgs(form.getImgs());
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
	}
	
	// 我的主题
	@ResponseBody
	@RequestMapping("/uc/article/list.json")
	public void uc_list_json(ArticleSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long uid = AppUserCurUtil.cur_uid();
		bs.setUserid(uid);
		ar.t_succ_not_null(service.list(bs));
	}
	

	// 回应的主题
	@ResponseBody
	@RequestMapping("/uc/article/commlist.json")
	public void uc_commlist_json(ArticleSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		Long uid = AppUserCurUtil.cur_uid();
		bs.setUserid(uid);
		ar.t_succ_not_null(service.commlist(bs));
	}
	
}
