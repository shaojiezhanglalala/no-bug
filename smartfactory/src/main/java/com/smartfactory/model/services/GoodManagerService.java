package com.smartfactory.model.services;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.dao.GoodinfoMapper;
import com.smartfactory.model.dao.OrderinfoMapper;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.EquipmentinfoExample;
import com.smartfactory.model.entity.Goodinfo;
import com.smartfactory.model.entity.GoodinfoExample;
import com.smartfactory.model.entity.GoodinfoExample.Criteria;
import com.smartfactory.model.entity.Orderinfo;
import com.smartfactory.model.entity.OrderinfoExample;

@Service
public class GoodManagerService {

	@Autowired
	private GoodinfoMapper goodMapper;
	@Autowired
	private OrderinfoMapper omapper;
	@Autowired
	private EquipmentinfoMapper emapper;
	
	
	/**
	 * 查询尚未与产品绑定的所有设备
	 * @return
	 */
	public List<Equipmentinfo> searchEquipmentNoUsed(){
		       List<Goodinfo> list=goodMapper.selectByExample(null);
		       List<String>  list1=new ArrayList<String>();
		       for(Goodinfo info:list) {
		    	   list1.add(info.getEqpname());
		       }
		       EquipmentinfoExample example =new EquipmentinfoExample();
		       com.smartfactory.model.entity.EquipmentinfoExample.Criteria c=example.createCriteria();
		       c.andEqpnameNotIn(list1);
		       return emapper.selectByExample(example);
	}
	
	
	
	
	
	/**
	 * 实现修改用户信息功能
	 * @param user
	 * @return
	 */
	public boolean modGood(Goodinfo good) {
		//判断用户名是合法
		//如果有名字和我相同但是userid和我不同的人，则我的名字就不合法
		GoodinfoExample example = new GoodinfoExample();
		Criteria cc = example.createCriteria();
		
		cc.andGnameEqualTo(good.getGname());
		cc.andGidNotEqualTo(good.getGid());
		
		List<Goodinfo> list = goodMapper.selectByExample(example);
		if(list.size() > 0) {
			//名字不合法
			return false;
		}
		
		//进行修改工作
		int i = goodMapper.updateByPrimaryKeySelective(good);
		return i>0;
	}
	
	
	
	/**
	 * 实现删除功能
	 * @param ofcond 
	 
	 * @param userid
	 * @return
	 */
	public boolean delGoodById(int gid) {
		OrderinfoExample example=new OrderinfoExample();
		com.smartfactory.model.entity.OrderinfoExample.Criteria c=example.createCriteria();
		c.andGidEqualTo(gid);
		List<String> values=new ArrayList<String>();
		values.add("生产中");
		values.add("已接单");
		c.andOfcondIn(values);
 		List<Orderinfo> list=omapper.selectByExample(example);
 		if(list.size()>0) {
 			return false;
 		}else {
 			 int i=goodMapper.deleteByPrimaryKey(gid);
 			 return i>0;
 		}
//		if(Goodinfo.ofcond != "1") {
//			return false;
//			
//		}else {
//			int num = goodMapper.deleteByPrimaryKey(gid);
//			return num >0;
//		}
//
//		int num = goodMapper.deleteByPrimaryKey(gid);
//		return num >0;
//		
//		
//		
	}
	
//public boolean delGoodById(Goodinfo info) {
//		
// 		
//		if(info.ofcond != "1") {
//			return false;
//		
//		}else {
//			int num = goodMapper.deleteByPrimaryKey(info.gid);
//			return num >0;
//		}
//	
//	}


	/**
	 * 实现动态条件分页查询功能
	 * @param cond 查询条件
	 * @param pageSize 每页查询的记录条数
	 * @param pageNum 当前页数
	 * @return 返回值pageHelper插件封装了分页信息和分页查询结果集的pageinfo对象
	 */
	public PageInfo<Goodinfo> searchGoodinfo(Goodinfo cond, int pageNum, int pageSize){
		GoodinfoExample example = new GoodinfoExample();
		Criteria cc = example.createCriteria();
		if(null != cond.getGid()) {
			//添加id查询条件
			cc.andGidEqualTo(cond.getGid());
		}
		if(null != cond.getGname() && !"".equals(cond.getGname())) {
			//添加用户名条件
			cc.andGnameLike("%"+cond.getGname()+"%");
		}
		
		//启动分页插件
		PageHelper.startPage(pageNum, pageSize);
		//实时查询
		List<Goodinfo> list = goodMapper.selectByExample(example);
		//返回值
		return new PageInfo<Goodinfo>(list);
		
		
	}
	
	
	
	
	
	
	
	/**
	 * 添加新商品
	 */
	public boolean addNewGood(Goodinfo good) {
		
		GoodinfoExample example = new GoodinfoExample();
		Criteria cc = example.createCriteria();
		
		cc.andGnameEqualTo(good.getGname());
		List<Goodinfo> list = goodMapper.selectByExample(example);
		if(list.size()> 0) {
			return false;
		}
		
		goodMapper.insert(good);
		
		return true;
	}
	
	
//	public boolean addNewGood(Goodinfo good) {
//		int i=goodMapper.insert(good);
//			return i>0;
//		
//	}
	
	
	
	
	/**
	 * 检查商品名是否可用
	 * @param user
	 * @return
	 */
	
	public boolean checkGoodname(String gname) {
		//检查用户名是否冲突
		GoodinfoExample example = new GoodinfoExample();
		Criteria cc = example.createCriteria();
		//添加用户名条件
		cc.andGnameEqualTo(gname);
		//查询
		List<Goodinfo> list = goodMapper.selectByExample(example);
		
		return list.size() == 0;
	}

    
	

	
	
	
}
