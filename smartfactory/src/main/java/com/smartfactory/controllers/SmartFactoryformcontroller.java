package com.smartfactory.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Forminfo;

import com.smartfactory.model.entity.Planinfo;
import com.smartfactory.model.services.SmartFactoryformservice;

/**
 * @author muwenxin
 *生产调度管理controller
 */
@RestController
public class SmartFactoryformcontroller {
	
	@Autowired
	private SmartFactoryformservice formservice;
	
	@PostMapping("/searchform")
	public PageInfo<Forminfo> dosearchForm(Forminfo form,int pageNum,int pageSize){
		return formservice.searchForm(form, pageNum, pageSize);
	}
	
	@PostMapping("/searchgnum")
	public int dosearchgnumByorderid(int orderid) {
		return formservice.searchgnumByorderid(orderid);
	}
	
	@RequestMapping("/delform")
	public boolean dodelForminfoByformid(int formid) {
		return formservice.delForminfoByformid(formid);
	}
	
	@PostMapping("/modfcond")
	public boolean domodForminfocond(Forminfo form) {
		return formservice.modForminfocond(form);
	}
	
	@PostMapping("/compplan")
	public List<Planinfo> dosearchcompplan(){
		return formservice.searchcompplan();
	}
	
	@PostMapping("/eqpid")
	public int dosearcheqpid(int orderid) {
		return formservice.searcheqpid(orderid);
	}
	
	@PostMapping("/addform")
	public boolean doaddform(Forminfo form) {
		return formservice.addform(form);
	}

	@PostMapping(value="/gname",produces = "text/html; charset=UTF-8")
	public String dosearchgname(int orderid) {
		return formservice.searchgname(orderid);
	}
}
