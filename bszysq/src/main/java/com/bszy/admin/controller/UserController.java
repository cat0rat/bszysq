package com.bszy.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.form.RepwdForm;
import com.bszy.admin.form.SysmsgForm;
import com.bszy.admin.form.UserForm;
import com.bszy.admin.pojo.User;
import com.bszy.admin.service.UserService;
import com.bszy.admin.vo.UserSearch;
import com.bszy.app.pojo.AppSysmsg;
import com.bszy.app.service.AppSysmsgService;
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
	@Inject
	private AppSysmsgService sysmsgService;
	
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
		mo.setRolex(MUtil.toInt(form.getRolex(), 0));
		
		mo.setUnionid(form.getUnionid());
		mo.setOpenid(form.getOpenid());
		
		mo.setAuthx(1);
		mo.setMobile(form.getMobile());
		mo.setTname(form.getTname());
		mo.setCitycode(form.getCitycode());
		mo.setCity(form.getCity());
		mo.setAddress(form.getAddress());
		
		mo.setSex(form.getSex());
		mo.setHead(FormValid.userHeadEmptyDef(form.getHead()));
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
		mo.setRolex(MUtil.toInt(form.getRolex(), 0));
		
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
	
	
	@ResponseBody
	@RequestMapping(value = "/send/sysmsg.json", method = RequestMethod.POST)
	public AjaxResult send_sysmsg_json(SysmsgForm form){
		AjaxResult ar = new AjaxResult();
		
		String name = form.getName();	// 标题(<200字符)
		if(FormValid.isEmpty(name)){ ar.t_fail("9001"); return ar; }		// 9001 = 标题不能为空
		if(!FormValid.len(name, 1, 200)){ ar.t_fail("9002"); return ar; }	// 9002 = 标题(<200字符)
		
		String content = form.getContent();	// 内容(<500字符)
		if(FormValid.isEmpty(content)){ ar.t_fail("9003"); return ar; }		// 9003 = 内容不能为空
		if(!FormValid.len(name, 1, 500)){ ar.t_fail("9004"); return ar; }	// 9004 = 内容(<500字符)
		
		String ids = form.getIds();		// 9005 = 请先选择目标用户
		if(FormValid.isEmpty(ids)){ ar.t_fail("9005"); return ar; }		// 9005 = 请先选择目标用户
		long[] ida = MUtil.strToLngArr(ids, ",", true);
		if(ida == null || ida.length == 0){ ar.t_fail("9005"); return ar; }	// 9005 = 请先选择目标用户
		
		AppSysmsg smsg = new AppSysmsg();
		smsg.setName(name);
		smsg.setContent(content);
		smsg.setTypex(AppSysmsg.Typex_Sys);
		int c = 0;
		List<Long> errids = new ArrayList<Long>();
		for(long id : ida){
			try {
				smsg.setUserid(id);
				if(sysmsgService.commMsg(smsg) != null){
					c++;
					errids.add(id);
				}
			} catch (Exception e) {
				c++;
				errids.add(id);
				e.printStackTrace();
			}
		}
		if(c > 0){
			ar.setMsg((ida.length - c) + "个用户发送成功, " + c + "个用户发送失败!");
			ar.setData(errids);
		}else{
			ar.t_succ();
		}
		return ar;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/repwd.json", method = RequestMethod.POST)
	public AjaxResult repwd_json(RepwdForm form){
		AjaxResult ar = new AjaxResult();
		
		Long id = MUtil.toLong(form.getId());	// 帐号
		if(!FormValid.isId(id)){ ar.t_fail("1284"); return ar; }		// 1284 = 请选择一个帐号
		
		String pwd = form.getPwd();	// 密码
		if(FormValid.isEmpty(pwd)){ ar.t_fail("2104"); return ar; }		// 2104 = 密码不能为空
		if(!FormValid.len(pwd, 6, 16)){ ar.t_fail("2105"); return ar; }	// 2105 = 密码(6~16位字母数字)
		pwd = DigestUtils.md5Hex(pwd);
		
		User mo = new User();
		mo.setId(id);
		mo.setPwd(pwd);
		boolean rb = service.repwd(mo);
		ar.t_result(rb);
		return ar;
	}
	
}
