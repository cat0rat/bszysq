package com.bszy.app.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.CommentForm;
import com.bszy.admin.pojo.Comment;
import com.bszy.admin.pojo.CommentSearch;
import com.bszy.admin.service.CommentService;
import com.bszy.app.security.AppUserCurUtil;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/app")
public class AppCommentController extends BaseController {
	
	@Inject
	private CommentService service;
	
	
	// TODO json
	
	@ResponseBody
	@RequestMapping(value = "/comment/get.json", method = RequestMethod.POST)
	public void get_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.get(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/list.json", method = RequestMethod.POST)
	public void list_json(CommentSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}
	
	// TODO 需登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/comment/delete.json", method = RequestMethod.POST)
	public void delete_json(Long id, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_result(service.delete(id));
	}
	
	@ResponseBody
	@RequestMapping(value = "/uc/comment/dels.json", method = RequestMethod.POST)
	public void dels_json(String ids, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(ids == null || !FormValid.isIds(ids)){ ar.t_fail("1501"); return ; }
		ar.t_result(service.dels(ids));
	}

	@ResponseBody
	@RequestMapping(value = "/uc/comment/add.json", method = RequestMethod.POST)
	public void add_json(CommentForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String content = form.getContent();	// 内容(<1000字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("6001"); return ; }
		if(!FormValid.len(content, 1, 1000)){ ar.t_fail("6002"); return ; }
		
		Long artid = MUtil.toLong(form.getArtid());	// 文章
		if(!FormValid.isId(artid)){ ar.t_fail("6003"); return ; }
		
		Long authorid = MUtil.toLong(form.getAuthorid());	// 文章作者
		if(!FormValid.isId(authorid)){ ar.t_fail("6004"); return ; }
		
		Long uid = AppUserCurUtil.cur_uid();
		
		Comment mo = new Comment();
		mo.init_add();
		
		mo.setArtid(artid);
		mo.setAuthorid(authorid);
		mo.setUserid(uid);
		mo.setTouserid(MUtil.toLong(form.getTouserid()));
		
		mo.setContent(content);
		mo.setImgs(form.getImgs());
		
		mo.setLiken(0L);
		mo.setCommn(0L);
		mo.setIsdel(0);
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
}