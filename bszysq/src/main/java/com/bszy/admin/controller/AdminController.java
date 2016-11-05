package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bszy.admin.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Inject
	private AdminService service;
	
	
}
