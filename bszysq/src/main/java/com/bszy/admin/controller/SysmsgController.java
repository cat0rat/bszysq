package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.SysmsgForm;
import com.bszy.admin.pojo.Sysmsg;
import com.bszy.admin.service.SysmsgService;
import com.bszy.admin.vo.SysmsgSearch;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/sysmsg")
public class SysmsgController extends BaseController {
	
	@Inject
	private SysmsgService service;
	@Inject
	private SysmsgService sysmsgService;
	
	// TODO page
	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/sysmsg";
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
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	public AjaxResult list_json(SysmsgSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public AjaxResult add_json(SysmsgForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		Integer rangx = MUtil.toInteger(form.getRangx());	// 9006 = 请先选择推送范围
		if(rangx == null || rangx < 0){ ar.t_fail("9006"); return ar; }
		
		String name = form.getName();	// 9001 = 标题不能为空
		if(FormValid.isEmpty(name)){ ar.t_fail("9001"); return ar; }
		// 9002 = 标题(<200字符)
		if(!FormValid.len(name, 1, 200)){ ar.t_fail("9002"); return ar; }
		
		String content = form.getContent();	// 9003 = 内容不能为空
		if(FormValid.isEmpty(content)){ ar.t_fail("9003"); return ar; }
		// 9004 = 内容(<500字符)
		if(!FormValid.len(content, 1, 500)){ ar.t_fail("9004"); return ar; }
		
		Sysmsg mo = new Sysmsg();
		mo.init_add();
		mo.setName(name);
		mo.setContent(content);
		mo.setTypex(0);
		mo.setRangx(rangx);
		
		boolean rb = false;
		try {
			String emsg = sysmsgService.commMsg(mo);
			rb = emsg == null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ar.t_result(rb);
		return ar;
	}
	
	
}
