package com.bszy.app.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.service.VersionxService;
import com.bszy.admin.vo.VersionxSearch;
import com.bszy.app.vo.AppVersionxAsia;
import com.mao.ssm.*;

@Controller
public class AppVersionxController extends BaseController {

	@Inject
	private VersionxService service;


	@ResponseBody
	@RequestMapping(value = "/app/version/get/{id}", method = RequestMethod.GET)
	public AjaxResult get_json_var(@PathVariable Long id) {
		AjaxResult ar = new AjaxResult();
		ar.t_succ_not_null(service.get(id));
		return ar;
	}

	@ResponseBody
	@RequestMapping(value = "/app/version{typex}/{packagex:.*}", method = RequestMethod.GET)
	public AjaxResult sys_list(@PathVariable Integer typex, @PathVariable String packagex) {
		AjaxResult ar = new AjaxResult();
		if (FormValid.isEmpty(packagex)) { ar.t_fail("15001"); return ar; }
		if (!FormValid.len(packagex, 1, 50)) { ar.t_fail("15002"); return ar; }
		if (typex == null) typex = 0;
		
		VersionxSearch bs = new VersionxSearch();
		bs.setTypex(typex);
		bs.setPackagex(packagex);
		ar.t_succ_not_null(AppVersionxAsia.fromAppVersionxs(service.for_packagex(bs)));
		return ar;
	}
}
