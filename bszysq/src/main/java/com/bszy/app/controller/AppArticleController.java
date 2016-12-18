package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.ArticleForm;
import com.bszy.admin.pojo.Article;
import com.bszy.admin.service.ArticleService;
import com.bszy.admin.service.CategoryService;
import com.bszy.admin.vo.ArticleSearch;
import com.bszy.admin.vo.CategoryArtAuth;
import com.bszy.app.service.AppUserService;
import com.bszy.app.vo.AppUserAuth;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppArticleController extends BaseController {
	
	@Inject
	private ArticleService service;
	@Inject
	private CategoryService categoryService;
	@Inject
	private AppUserService appUserService;
	
	@ResponseBody
	@RequestMapping(value = "/article/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.detail(id));
		return ar;
	}
	@ResponseBody
	@RequestMapping(value = "/article/get_comms/{id}", method = RequestMethod.GET)
	public AjaxResult get_comms_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get_comms(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/article/list", method = RequestMethod.POST)
	public AjaxResult list(@RequestBody ArticleSearch bs){
		AjaxResult ar = new AjaxResult();
		Long cateid = bs.getCateid();
		if (!FormValid.isId(cateid)) {
			ar.t_fail("6105");
			return ar;
		}
		CategoryArtAuth caa = categoryService.artauth(cateid);
		if (caa == null) { ar.t_fail("4101"); return ar; }
		Long uid = bs.getUid();
		// 认证用户才能查看, 0: 无需认证; 1: 需要认证; 2: 无需登录
		if (!FormValid.eq(caa.getLookartauth(), Integer.valueOf(2))) {
			if (!FormValid.isId(uid)) { ar.t_fail("1001"); return ar; }
			AppUserAuth aua = appUserService.user_auth(uid);
			if (aua == null) { ar.t_fail("1011"); return ar; }
			if (FormValid.eq(aua.getIsdel(), Integer.valueOf(1))) { ar.t_fail("1012"); return ar;
			}
			if (FormValid.eq(caa.getLookartauth(), Integer.valueOf(1)) && !FormValid.eq(aua.getAuthx(), Integer.valueOf(0))) {
				ar.t_fail("4102");
				return ar;
			}
		}
		ar.t_succ_not_null(service.list_for_app(bs));
		return ar;
	}
	
	// TODO 需登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/article/add", method = RequestMethod.POST)
	public AjaxResult add_json(@RequestBody ArticleForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();	// 标题
		if(FormValid.isEmpty(name)){ ar.t_fail("6100"); return ar; }
		if(!FormValid.len(name, 1, 500)){ ar.t_fail("6101"); return ar; }
		
		Long tagid = MUtil.toLong(form.getTagid());	// 标签
		if(!FormValid.isId(tagid)){ ar.t_fail("6102"); return ar; }
		
		Long cateid = MUtil.toLong(form.getCateid());	// 版块
		if(!FormValid.isId(cateid)){ ar.t_fail("6105"); return ar; }
		
		String content = form.getContent();	// 内容
		if(FormValid.isEmpty(content)){ ar.t_fail("6103"); return ar; }
		if(!FormValid.len(content, 1, 10000)){ ar.t_fail("6104"); return ar; }
		
		Long uid = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 检测 版块权限
		// addart 禁止用户发布主题, 0: 正常; 1: 禁止
		CategoryArtAuth caa = categoryService.artauth(cateid);
		if (caa == null) { ar.t_fail("4101"); return ar; }	// 4101 = 版块不存在或已删除
		// 4103 = 该版块不可以发表主题
		if (FormValid.eq(caa.getAddart(), 1)) { ar.t_fail("4103"); return ar; }
		// addartauth 认证用户才能发布主题, 0: 无需认证; 1: 需要认证
		if (FormValid.eq(caa.getAddartauth(), 1)) {
			AppUserAuth aua = appUserService.user_auth(uid);
			if (aua == null) { ar.t_fail("1011"); return ar; }	// 1011 = 检测用户失败, 请重新登录
			// 1012 = 您的帐号已被封号
			if (FormValid.eq(aua.getIsdel(), 1)) { ar.t_fail("1012"); return ar; }
			// 4104 = 该版块认证用户才能发表主题
			if (!FormValid.eq(aua.getAuthx(), 0)) { ar.t_fail("4104"); return ar; }
		}
		
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
		if(rb) ar.t_succ_not_null(mo.getId());
		return ar;
	}
	
	// 我的主题
	@ResponseBody
	@RequestMapping(value = "/uc/article/list", method = RequestMethod.POST)
	public AjaxResult uc_list_json(@RequestBody ArticleSearch bs){
		AjaxResult ar = new AjaxResult();
		Long uid = MUtil.toLong(bs.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 检测 用户状态
		AppUserAuth aua = appUserService.user_auth(uid);
		if (aua == null) { ar.t_fail("1011"); return ar; }	// 1011 = 检测用户失败, 请重新登录
		// 1012 = 您的帐号已被封号
		if (FormValid.eq(aua.getIsdel(), Integer.valueOf(1))) { ar.t_fail("1012"); return ar; }
		
		bs.setUserid(uid);
		ar.t_succ_not_null(service.list_simple(bs));
		return ar;
	}
	

	// 回应的主题
	@ResponseBody
	@RequestMapping(value = "/uc/article/commlist", method = RequestMethod.POST)
	public AjaxResult uc_commlist_json(@RequestBody ArticleSearch bs){
		AjaxResult ar = new AjaxResult();
		Long uid = MUtil.toLong(bs.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 检测 用户状态
		AppUserAuth aua = appUserService.user_auth(uid);
		if (aua == null) { ar.t_fail("1011"); return ar; }	// 1011 = 检测用户失败, 请重新登录
		// 1012 = 您的帐号已被封号
		if (FormValid.eq(aua.getIsdel(), Integer.valueOf(1))) { ar.t_fail("1012"); return ar; }
		
		bs.setUserid(uid);
		ar.t_succ_not_null(service.commlist(bs));
		return ar;
	}
	
	// 浏览的主题---
	@ResponseBody
	@RequestMapping(value = "/uc/article/looklist", method = RequestMethod.POST)
	public AjaxResult uc_looklist_json(@RequestBody ArticleSearch bs){
		AjaxResult ar = new AjaxResult();
		Long uid = MUtil.toLong(bs.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		// 检测 用户状态
		AppUserAuth aua = appUserService.user_auth(uid);
		if (aua == null) { ar.t_fail("1011"); return ar; }	// 1011 = 检测用户失败, 请重新登录
		// 1012 = 您的帐号已被封号
		if (FormValid.eq(aua.getIsdel(), Integer.valueOf(1))) { ar.t_fail("1012"); return ar; }
		
		bs.setUserid(uid);
		ar.t_succ_not_null(service.commlist(bs));
		return ar;
	}
	
}
