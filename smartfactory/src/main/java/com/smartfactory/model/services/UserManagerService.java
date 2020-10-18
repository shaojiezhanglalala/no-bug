package com.smartfactory.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartfactory.model.dao.UserinfoMapper;
import com.smartfactory.model.entity.Userinfo;
import com.smartfactory.model.entity.UserinfoExample;
import com.smartfactory.model.entity.UserinfoExample.Criteria;


/**
 * 用户信息管理模块
 * @author muwenxin
 *
 */
@Service
public class UserManagerService {

	@Autowired
	private UserinfoMapper mapper;

	
	
	/**
	 * 用户登录信息验证
	 * @param user
	 * @return
	 */
	public Userinfo checkLogin(Userinfo user) {

		UserinfoExample example = new UserinfoExample();
		Criteria cc = example.createCriteria();
		/*
		 * 添加查询条件
		 */
		cc.andUsernameEqualTo(user.getUsername());
		cc.andUserpwdEqualTo(user.getUserpwd());
		
		List<Userinfo> list = mapper.selectByExample(example);
		
		if(list.size()>0) {
			return list.get(0);
		}
		else {
			return new Userinfo();
		}	
	}
	
	/**
	 * 用户注册验证
	 * @param user
	 * @return
	 */
	public Boolean AddNewUser(Userinfo user) {
		boolean result = CheckUsername(user.getUsername());
		if(result==false) {
			return false;
		}else {
			user.setUserpow(2);
			mapper.insert(user);
			return true;
		}
	}
	
	/**
	 * 用户名是否重复实时检测
	 * @param username
	 * @return
	 */
	public Boolean CheckUsername(String username) {
		
		UserinfoExample example = new UserinfoExample();
		Criteria cc = example.createCriteria();
		//添加条件
		cc.andUsernameEqualTo(username);
		
		List<Userinfo> list = mapper.selectByExample(example);
		
		if(list.size()>0) {
			return false;
		}else {
			return true;
		}
	}
	
}
