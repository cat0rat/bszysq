package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.CommentForm;
import com.bszy.admin.pojo.Comment;
import com.bszy.admin.pojo.CommentSearch;
import com.bszy.admin.service.CommentService;
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
	@RequestMapping(value = "/comment/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable Long id){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/list", method = RequestMethod.POST)
	public AjaxResult list_json(@RequestBody CommentSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}
	
	// TODO 需登录
	
	@ResponseBody
	@RequestMapping(value = "/uc/comment/add", method = RequestMethod.POST)
	public AjaxResult add_json(@RequestBody CommentForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String content = form.getContent();	// 内容(<1000字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(content, 1, 1000)){ ar.t_fail("6002"); return ar; }
		
		Long artid = MUtil.toLong(form.getArtid());	// 主题
		if(!FormValid.isId(artid)){ ar.t_fail("6003"); return ar; }
		
		Long uid = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		Comment mo = new Comment();
		mo.init_add();
		
		mo.setArtid(artid);
		mo.setUserid(uid);
		mo.setTouserid(MUtil.toLong(form.getTouserid()));
		
		mo.setContent(content);
		mo.setImgs(form.getImgs());
		
		mo.setLiken(0L);
		mo.setCommn(0L);
		mo.setIsdel(0);
		boolean rb = service.add(mo);
		if(rb) ar.t_succ_not_null(mo.getId());
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/uc/comment/add_comm", method = RequestMethod.POST)
	public AjaxResult add_comm_json(@RequestBody CommentForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String content = form.getContent();	// 内容(<1000字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("6001"); return ar; }
		if(!FormValid.len(content, 1, 1000)){ ar.t_fail("6002"); return ar; }
		
		Long commid = MUtil.toLong(form.getCommid());	// 已有的评论ID
		if(!FormValid.isId(commid)){ ar.t_fail("6006"); return ar; }
		
		Comment oldmo = service.refids(commid);
		if(oldmo == null){ ar.t_fail("6007"); return ar; }
		
		Long uid = MUtil.toLong(form.getUid());	// 检查当前用户ID(登录)
		if(!FormValid.isId(uid)){ ar.t_fail("1001"); return ar; }
		
		Comment mo = new Comment();
		mo.init_add();
		
		mo.setCommid(commid);
		mo.setArtid(oldmo.getArtid());
		mo.setAuthorid(oldmo.getAuthorid());
		mo.setUserid(uid);
		mo.setTouserid(oldmo.getUserid());
		
		mo.setContent(content);
		mo.setImgs(form.getImgs());
		
		mo.setLiken(0L);
		mo.setCommn(0L);
		mo.setIsdel(0);
		boolean rb = service.add(mo);
		if(rb) ar.t_succ_not_null(mo.getId());
		return ar;
	}
	
}
