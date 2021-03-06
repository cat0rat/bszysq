package com.bszy.admin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.CommentForm;
import com.bszy.admin.pojo.Comment;
import com.bszy.admin.service.CommentService;
import com.bszy.admin.vo.CommentSearch;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/comment")
public class CommentController extends BaseController {
	
	@Inject
	private CommentService service;
	
	// TODO page
	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/comment";
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(){
		return "admin/comment/list";
	}
	@RequestMapping(value = "/add.do", method = RequestMethod.GET)
	public String add(){
		return "admin/comment/add";
	}
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/comment/edit";
	}
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String view(Long id, Model model) {
		model.addAttribute("bean", service.get(id));
		return "admin/comment/view";
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
	public void list_json(CommentSearch bs, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		ar.t_succ_not_null(service.list(bs));
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public void add_json(CommentForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		
		String content = form.getContent();	// 内容(<1000字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("6001"); return ; }
		if(!FormValid.len(content, 1, 20)){ ar.t_fail("6002"); return ; }
		
		Long artid = MUtil.toLong(form.getArtid());
		if(!FormValid.isId(artid)){ ar.t_fail("6003"); return ; }
		Long authorid = MUtil.toLong(form.getAuthorid());
		if(!FormValid.isId(authorid)){ ar.t_fail("6004"); return ; }
		Long userid = MUtil.toLong(form.getUserid());
		if(!FormValid.isId(userid)){ ar.t_fail("6005"); return ; }
		
		Comment mo = new Comment();
		mo.init_add();
		
		mo.setArtid(artid);
		mo.setAuthorid(authorid);
		mo.setUserid(userid);
		mo.setTouserid(MUtil.toLong(form.getTouserid()));
		
		mo.setContent(content);
		mo.setImgs(form.getImgs());
		boolean rb = service.add(mo);
		ar.t_result(rb);
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public void update_json(CommentForm form, HttpServletRequest request){
		AjaxResult ar = ajaxResult(request);
		if(form == null){ ar.t_fail("1501"); return ; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ; }
		
		String content = form.getContent();	// 内容(<1000字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("6001"); return ; }
		if(!FormValid.len(content, 1, 20)){ ar.t_fail("6002"); return ; }
		
		Long artid = MUtil.toLong(form.getArtid());
		if(!FormValid.isId(artid)){ ar.t_fail("6003"); return ; }
		Long authorid = MUtil.toLong(form.getAuthorid());
		if(!FormValid.isId(authorid)){ ar.t_fail("6004"); return ; }
		Long userid = MUtil.toLong(form.getUserid());
		if(!FormValid.isId(userid)){ ar.t_fail("6005"); return ; }
		
		Comment mo = new Comment();
		mo.init_update();
		mo.setId(id);
		
		mo.setArtid(artid);
		mo.setAuthorid(authorid);
		mo.setUserid(userid);
		mo.setTouserid(MUtil.toLong(form.getTouserid()));
		
		mo.setContent(content);
		mo.setImgs(form.getImgs());
		mo.setIsdel(MUtil.toInteger(form.getIsdel()));
		boolean rb = service.update(mo);
		ar.t_result(rb);
	}
	
}
