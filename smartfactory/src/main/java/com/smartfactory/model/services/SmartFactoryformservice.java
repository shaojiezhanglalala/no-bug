package com.smartfactory.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.dao.ForminfoMapper;
import com.smartfactory.model.dao.GoodinfoMapper;
import com.smartfactory.model.dao.OrderinfoMapper;
import com.smartfactory.model.dao.PlaninfoMapper;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.EquipmentinfoExample;
import com.smartfactory.model.entity.Forminfo;
import com.smartfactory.model.entity.ForminfoExample;
import com.smartfactory.model.entity.ForminfoExample.Criteria;
import com.smartfactory.model.entity.Orderinfo;
import com.smartfactory.model.entity.Planinfo;
import com.smartfactory.model.entity.PlaninfoExample;

/**
 * @author muwenxin
 *订单管理模块
 */
@Service
public class SmartFactoryformservice {
	
	@Autowired
	private ForminfoMapper formmapper;
	
	@Autowired
	private OrderinfoMapper ordermapper;
	
	@Autowired
	private PlaninfoMapper planmapper;
	
	@Autowired
	private GoodinfoMapper goodmapper;
	
	@Autowired
	private EquipmentinfoMapper equipmentmapper;
	
	public PageInfo<Forminfo> searchForm(Forminfo form,int pageNum,int pageSize){
		
		ForminfoExample example = new ForminfoExample();
		Criteria cc = example.createCriteria();
		
		//添加查询条件
		if(null!=form.getFormid()) {
			cc.andFormidEqualTo(form.getFormid());
		}
		if(null!=form.getOrderid()) {
			cc.andOrderidEqualTo(form.getOrderid());
		}
		if(null!=form.getGname()&&""!=form.getGname()) {
			cc.andGnameEqualTo(form.getGname());
		}
		
		//启动分页查询
		PageHelper.startPage(pageNum, pageSize);
		List<Forminfo> list = formmapper.selectByExample(example);
		return new PageInfo<Forminfo>(list);
	}
	
	/**
	 * 通过orderid查询gnum
	 * @param orderid
	 * @return
	 */
	public int searchgnumByorderid(int orderid) {
		Orderinfo orderinfo = ordermapper.selectByPrimaryKey(orderid);
		return orderinfo.getGnum();
	}
	
	/**
	 * 通过formid删除信息
	 * @param formid
	 * @return
	 */
	public boolean delForminfoByformid(int formid) {
		 int key = formmapper.deleteByPrimaryKey(formid);
		 return key>0;
	}
	
	/**
	 * 更新工单表状态
	 * @param form
	 * @return
	 */
	public boolean modForminfocond(Forminfo form) {
		int i = formmapper.updateByPrimaryKeySelective(form);
		return i>0;
	}
	
	
	/**
	 * 查询已启动计划
	 * @return
	 */
	public List<Planinfo> searchcompplan(){
		
		PlaninfoExample example = new PlaninfoExample();
		com.smartfactory.model.entity.PlaninfoExample.Criteria cc = example.createCriteria();
		
		//添加条件
		cc.andPcondEqualTo("已启动");
		
		List<Planinfo> list = planmapper.selectByExample(example);
		
		return list;
	}
	
	/**
	 * 通过计划表id查询所需设备
	 * @param orderid
	 * @return
	 */
	public int searcheqpid(int orderid) {
		//查询产品id
	    int gid = ordermapper.selectByPrimaryKey(orderid).getGid();
	    //查询产品所需设备名称
	     String name =goodmapper.selectByPrimaryKey(gid).getEqpname();
	    //查询设备id
	     EquipmentinfoExample example = new EquipmentinfoExample();
	     com.smartfactory.model.entity.EquipmentinfoExample.Criteria cc = example.createCriteria();
	     cc.andEqpnameEqualTo(name);
	     List<Equipmentinfo> list = equipmentmapper.selectByExample(example);
	     return list.get(0).getEqpid();
	}
	
	/**
	 * 查找产品名
	 * @param orderid
	 * @return
	 */
	public String searchgname(int orderid) {
		return ordermapper.selectByPrimaryKey(orderid).getGname();
	}
	
	/**
	 * 新建工单
	 * @param form
	 * @return
	 */
	public boolean addform(Forminfo form) {
		int i = formmapper.insert(form);
		return i>0;
	}
	

}
