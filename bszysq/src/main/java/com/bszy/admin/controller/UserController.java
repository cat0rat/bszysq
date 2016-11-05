package com.bszy.admin.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bszy.admin.pojo.User;
import com.bszy.admin.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	
	@Inject
	private UserService service;
	
	@ResponseBody
	@RequestMapping("/get.json")
	public User get(Long id){
		User user = service.get(id);
		return user;
	}
	
}
