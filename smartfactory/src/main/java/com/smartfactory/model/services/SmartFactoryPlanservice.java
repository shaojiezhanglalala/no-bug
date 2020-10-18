package com.smartfactory.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.OrderinfoMapper;
import com.smartfactory.model.dao.PlaninfoMapper;
import com.smartfactory.model.entity.Orderinfo;
import com.smartfactory.model.entity.OrderinfoExample;
import com.smartfactory.model.entity.Planinfo;
import com.smartfactory.model.entity.PlaninfoExample;
import com.smartfactory.model.entity.PlaninfoExample.Criteria;




@Service
public class SmartFactoryPlanservice {
	
	@Autowired
	private PlaninfoMapper planMapper;
	@Autowired
	private OrderinfoMapper orderMapper;
	
	
	public List<Planinfo> searchallplan(){
		return planMapper.selectByExample(null);
	}
	
	public boolean delPlan(int pid) {
		int num = planMapper.deleteByPrimaryKey(pid);
		return num>0;
	}
	
	
	public PageInfo<Planinfo>searchPlan(Planinfo plan,int pageNum,int pageSize) {
		PlaninfoExample example = new PlaninfoExample();
		Criteria cc = example.createCriteria();
		if(null!=plan.getPid()) {
			cc.andPidEqualTo(plan.getPid());
		}
		if(null!=plan.getOrderid()) {
			cc.andOrderidEqualTo(plan.getOrderid());
		}
		if(null!=plan.getPcond()&&!"".equals(plan.getPcond())) {
			cc.andPcondEqualTo(plan.getPcond());
			
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Planinfo> plist = planMapper.selectByExample(example);
		
		return new PageInfo<Planinfo>(plist);
		
	}
	 
	public List<Orderinfo> searchOrderbypid(int orderid){
		OrderinfoExample example1 = new OrderinfoExample();
		com.smartfactory.model.entity.OrderinfoExample.Criteria cc = example1.createCriteria();
		cc.andOrderidEqualTo(orderid);
		return orderMapper.selectByExample(example1);
		}
		
	
	public boolean modPlan(Planinfo plan) {
           int i = planMapper.updateByPrimaryKeySelective(plan);
		return i>0;
		
	}

	public boolean modPcondbypid(int pid) {
		Planinfo pcond = new Planinfo();
		pcond.setPcond("已启动");
		PlaninfoExample example = new PlaninfoExample();
		Criteria cc = example.createCriteria();
		cc.andPidEqualTo(pid);
        int i = planMapper.updateByExampleSelective(pcond, example);
        		return i>0;
		
	}
	
	
	public boolean addPlan(Planinfo plan) {
		plan.setPcond("未启动 ");
		int i = planMapper.insert(plan);
		return i>0;
	}
		
	public List<Orderinfo> searchOrderbycond(){
		OrderinfoExample example = new OrderinfoExample();
		com.smartfactory.model.entity.OrderinfoExample.Criteria cc = example.createCriteria();
		cc.andOfcondEqualTo("已接单");
	     return orderMapper.selectByExample(example);
		
	}
	public List<Orderinfo> searchorderbyOrderid(Orderinfo orderid){
		OrderinfoExample example = new OrderinfoExample();
		com.smartfactory.model.entity.OrderinfoExample.Criteria cc = example.createCriteria();
		cc.andOrderidEqualTo(orderid.getOrderid());
		return orderMapper.selectByExample(example);
	}
	public boolean modofcond(int orderid) {
		Orderinfo order = new Orderinfo();
		order.setOfcond("生产中");
		OrderinfoExample example = new OrderinfoExample();
		com.smartfactory.model.entity.OrderinfoExample.Criteria cc = example.createCriteria();
		cc.andOrderidEqualTo(orderid);
		int i = orderMapper.updateByExampleSelective(order, example);
		return i>0;
		
		
		
		
	}
}


