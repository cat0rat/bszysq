package com.bszy.admin.controller;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.UserForm;
import com.bszy.admin.pojo.User;
import com.bszy.admin.service.UserService;
import com.bszy.admin.vo.UserSearch;
import com.mao.lang.DateUtil;
import com.mao.lang.MUtil;
import com.mao.ssm.AjaxResult;
import com.mao.ssm.BaseController;
import com.mao.ssm.BaseStatusForm;
import com.mao.ssm.FormValid;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	
	@Inject
	private UserService service;
	
	// TODO page

	@RequestMapping(value = {"/", "/page.do"}, method = RequestMethod.GET)
	public String page(){
		return "admin/user";
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
	public AjaxResult list_json(UserSearch bs){
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.list(bs));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	public AjaxResult add_json(UserForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		
		String name = form.getName();	// 用户名
		if(FormValid.isEmpty(name)){ ar.t_fail("2102"); return ar; }
		if(!FormValid.len(name, 3, 16)){ ar.t_fail("2103"); return ar; }
		
		String nname = form.getNname();	// 昵称
		if(!FormValid.lenAllowNull(nname, 2, 16)){ ar.t_fail("2101"); return ar; }
		
		String pwd = form.getPwd();	// 密码
		if(FormValid.isEmpty(pwd)){ ar.t_fail("2104"); return ar; }
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("2105"); return ar; }
		pwd = DigestUtils.md5Hex(pwd);
		
		User mo = new User();
		mo.init_add();
		mo.setName(name);
		mo.setPwd(pwd);
		mo.setNname(nname);
		
		mo.setUnionid(form.getUnionid());
		mo.setOpenid(form.getOpenid());
		
		mo.setAuthx(1);
		mo.setMobile(form.getMobile());
		mo.setTname(form.getTname());
		mo.setCitycode(form.getCitycode());
		mo.setCity(form.getCity());
		mo.setAddress(form.getAddress());
		
		mo.setSex(form.getSex());
		mo.setHead(form.getHead());
		mo.setEmail(form.getEmail());
		mo.setBirth(DateUtil.toDate(form.getBirth(), null, false));
		mo.setLcount(0);
		mo.setLstat(0);
		
		boolean rb = service.add(mo);
		ar.t_result(rb);
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	public AjaxResult update_json(UserForm form){
		AjaxResult ar = new AjaxResult();
		if(form == null){ ar.t_fail("1501"); return ar; }
		Long id = MUtil.toLong(form.getId());
		if(!MUtil.isId(id)){ ar.t_fail("1501"); return ar; }
		
		String nname = form.getNname();
		if(!FormValid.lenAllowNull(nname, 2, 16)){ ar.t_fail("2101"); return ar; }
		
		User mo = new User();
		mo.init_update();
		mo.setId(id);
		mo.setNname(nname);
		
		mo.setUnionid(form.getUnionid());
		mo.setOpenid(form.getOpenid());
		
		mo.setAuthx(MUtil.toInteger(form.getAuthx()));
		mo.setMobile(form.getMobile());
		mo.setTname(form.getTname());
		mo.setCitycode(form.getCitycode());
		mo.setCity(form.getCity());
		mo.setAddress(form.getAddress());
		
		mo.setSex(form.getSex());
		mo.setHead(form.getHead());
		mo.setEmail(form.getEmail());
		mo.setBirth(DateUtil.toDate(form.getBirth(), null, false));
		
		boolean rb = service.update(mo);
		ar.t_result(rb);
		return ar;
	}

	
	@ResponseBody
	@RequestMapping(value = "/option/authx.json", method = RequestMethod.POST)
	public AjaxResult option_authx_json(BaseStatusForm form){
		AjaxResult ar = new AjaxResult();
		boolean rb = false;
		Long id = MUtil.toLong(form.getId());
		if(MUtil.isId(id)){
			rb = service.option_authx(form);
		}else if(MUtil.isNotEmpty(form.getIds())){
			rb = service.option_authxs(form);
		}else{ ar.t_fail("1501"); return ar; }
		
		ar.t_result(rb);
		return ar;
	}

	
	@ResponseBody
	@RequestMapping(value = "/option/isdel.json", method = RequestMethod.POST)
	public AjaxResult option_isdel_json(BaseStatusForm form){
		AjaxResult ar = new AjaxResult();
		boolean rb = false;
		Long id = MUtil.toLong(form.getId());
		if(MUtil.isId(id)){
			rb = service.option_isdel(form);
		}else if(MUtil.isNotEmpty(form.getIds())){
			rb = service.option_isdels(form);
		}else{ ar.t_fail("1501"); return ar; }
		
		ar.t_result(rb);
		return ar;
	}
	
}
