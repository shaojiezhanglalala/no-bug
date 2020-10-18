package com.smartfactory.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfactory.model.entity.Userinfo;
import com.smartfactory.model.services.UserManagerService;

/**
 * @author muwenxin
 *  用户管理模块
 */
@RestController
public class UserManagercontroller {
	
	public static final String INFO = "info";
	@Autowired
	private UserManagerService service;
	
	/**
	 * 注册
	 * @param user
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public Userinfo doLogin(Userinfo user,HttpSession session) {
		
		Userinfo result = service.checkLogin(user);
		if(result!=null) {
			session.setAttribute(INFO, result);
			return result;
		}else {
			return null;
		}	
	}
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@PostMapping("/signup")
	public boolean DoSignup(Userinfo user) {
		return service.AddNewUser(user);
	}
	
	
	/**
	 * 检测用户名是否已被使用
	 * @param username
	 * @return
	 */
	@PostMapping("/checkname")
	public boolean Docheckname(String username) {
		return service.CheckUsername(username);
	}
	
	/**
	 * 登陆成功后返回当前用户名
	 * @return
	 */
	@PostMapping("/getcuruser")
	public Userinfo doGetCurrentUserinfo(HttpSession session) {
		return (Userinfo)session.getAttribute(INFO);
		
	}
}
