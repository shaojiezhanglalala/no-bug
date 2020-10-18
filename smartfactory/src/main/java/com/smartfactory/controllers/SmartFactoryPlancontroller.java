package com.smartfactory.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Orderinfo;

import com.smartfactory.model.entity.Planinfo;
import com.smartfactory.model.services.SmartFactoryPlanservice;


@RestController
public class SmartFactoryPlancontroller {
	@Autowired
	private SmartFactoryPlanservice service;
	

	
	@RequestMapping("/searchplan")
	public PageInfo<Planinfo>dosearchPlan(Planinfo plan,int pageNum,int pageSize){
		
		 return service.searchPlan(plan, pageNum, pageSize);
	}
	@RequestMapping("/searchorderpid")
	public List<Orderinfo> dosearchOrderbypid(int orderid){
	return service.searchOrderbypid(orderid);
	}
	@RequestMapping("/delplan")
	public boolean delPlan(int pid) {
		return service.delPlan(pid);
	}
	@RequestMapping("/modplan")
	public boolean domodPlan(Planinfo plan) {
		return service.modPlan(plan);
	}
	@RequestMapping("/allplan")
	public List<Planinfo> dosearchallplan(){
		
		return service.searchallplan();
	}
	@RequestMapping("/searchpcond")
	public boolean domodPcondbypid(int pid) {
		return service.modPcondbypid(pid);
	}
	@RequestMapping("/addplan")
	public boolean doaddPlan(Planinfo plan) {
		return service.addPlan(plan);
	}
	@RequestMapping("/searchorderofcond")
		public List<Orderinfo> dosearchOrderbycond(){
			return service.searchOrderbycond();
		}
	@RequestMapping("/searchorderbyorderid")
	public List<Orderinfo> dosearchorderbyOrderid(Orderinfo orderid){
		return service.searchorderbyOrderid(orderid);
	}
	@RequestMapping("/modofcondbyorderid")
		public boolean modofcond(int orderid) {
			return service.modofcond(orderid);
		}
	
}
