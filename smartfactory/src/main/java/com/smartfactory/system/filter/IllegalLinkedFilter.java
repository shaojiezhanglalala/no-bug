package com.smartfactory.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.smartfactory.controllers.UserManagercontroller;



public class IllegalLinkedFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	 /**
	   * 过滤器主要功能实现
	   */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("======盗链过滤器运行了======");
		//1.请求和响应对象进行类型转换
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse)response;
		//2.获取session，提取用户登陆的信息
		HttpSession session=req.getSession();
		Object user= session.getAttribute(UserManagercontroller.INFO);
		//3.判断是否登录
		if(null !=user) {
			//用户已经登录了
			chain.doFilter(request, response);
		}
		else {
			//用户没有登录
			//判断所访问的资源
			String url = req.getRequestURI();
			if( url.endsWith("/checkname")	|| url.endsWith("/signup")
				|| url.endsWith("/login") || url.endsWith("/login.html")
				|| url.endsWith("/register.html") || url.endsWith(".js") || url.endsWith(".css")) {
				chain.doFilter(request, response);
		      }else {
		    	  //盗链，跳转到登陆界面
		    	  resp.sendRedirect("/smartfactory/login.html");
		      }
		}
	}

	@Override
	public void destroy() {
	
	}
 
	
}
