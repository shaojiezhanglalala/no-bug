package com.smartfactory.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.dao.GoodinfoMapper;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.EquipmentinfoExample;
import com.smartfactory.model.entity.EquipmentinfoExample.Criteria;
import com.smartfactory.model.entity.Goodinfo;
import com.smartfactory.model.entity.GoodinfoExample;

/**
 * 设备管理模块业务逻辑实现类
 * @author 17484
 *
 */
@Service
public class EquipmentManagerService {
	@Autowired
	private EquipmentinfoMapper eqpMapper;
	@Autowired
	private GoodinfoMapper goodmapper;
	
	/**
	 * 新增设备
	 * @param eqp
	 * @return
	 */
	public boolean addNewEquipmentinfo(Equipmentinfo eqp) {
		eqpMapper.insert(eqp);
		return true;
	}
	/**
	 * 根据id删除设备
	 * @param eqpid
	 * @return
	 */
	public boolean deleteEquipmentinfo(int eqpid) {
		int num=0;
		Equipmentinfo eqpinfo=eqpMapper.selectByPrimaryKey(eqpid);
		if(!eqpinfo.getEqpcond().equals("启用")) {
			num = eqpMapper.deleteByPrimaryKey(eqpid);
		}
		return num > 0;
	}
	/**
	 * 修改设备
	 * @param eqp
	 * @return
	 */
	public boolean modifyEquipmentinfo(Equipmentinfo eqp) {
		
		int i = eqpMapper.updateByPrimaryKeySelective(eqp);
		return i > 0;
	}
	/**
	 * 实现动态分页查询功能
	 * @param cond查询条件
	 * @param pageNum当前页数
	 * @param pageSize每页查询的记录条数
	 * @return	返回是由pageHelper插件封装了分页信息和分页查询结果集的PageInfo对象
	 */
	public PageInfo<Equipmentinfo> searchEquipmentinfo(Equipmentinfo cond, int pageNum, int pageSize){
		EquipmentinfoExample example = new EquipmentinfoExample();
		Criteria cc = example.createCriteria();
		if(null != cond.getEqpname()&&!"".equals(cond.getEqpname())) {
			//添加设备名条件
			cc.andEqpnameLike("%"+cond.getEqpname()+"%");	
		}
		if(null != cond.getEqpid()) {
			//添加id查询条件
			cc.andEqpidEqualTo(cond.getEqpid());
		}
		//启动分页插件
		PageHelper.startPage(pageNum, pageSize);
		//实时查询
		List<Equipmentinfo> list = eqpMapper.selectByExample(example);
		//返回值
		return new PageInfo<Equipmentinfo>(list);
	}
	/**
	 * 根据产品名搜索设备
	 * */
	public PageInfo<Equipmentinfo> searchEquipmentByGname (Goodinfo cond, int pageNum, int pageSize) {
		//通过产品名在产品表里找设备名
		GoodinfoExample gexample = new GoodinfoExample();
		com.smartfactory.model.entity.GoodinfoExample.Criteria gcc=gexample.createCriteria();
			if(null != cond.getGname()&&!"".equals(cond.getGname())) {
				gcc.andGnameLike("%"+cond.getGname()+"%");
			}
			PageHelper.startPage(pageNum, pageSize);
		List<Goodinfo> glist=goodmapper.selectByExample(gexample);
		//将设备名存储到设备集合
		List<String> eqpnameList = new ArrayList<String>();
		 List<Equipmentinfo> list = new ArrayList<Equipmentinfo>();
		for (int i = 0; i < glist.size(); i++) {
			Goodinfo ginfo=glist.get(i);
			String geqpname=ginfo.getEqpname();
			eqpnameList.add(geqpname);
		}
		//通过设备名字的集合查找设备
		for (int i = 0; i < eqpnameList.size(); i++) {
			String eqpname = eqpnameList.get(i);
			EquipmentinfoExample example = new EquipmentinfoExample();
			Criteria cc = example.createCriteria();
			if(null != eqpname && !"".equals(eqpname)) {
				cc.andEqpnameEqualTo(eqpname);
			}
			
			list.add((eqpMapper.selectByExample(example).get(0)));
			
		}
		
		//启动分页插件
		PageHelper.startPage(pageNum, pageSize);
				//返回值
		
		return new PageInfo<Equipmentinfo>(list);
	}
	/**
	 * 根据设备名查产品名
	 */
	public  PageInfo<Goodinfo> searchGnameByEqpname(Goodinfo cond, int pageNum, int pageSize) {
		GoodinfoExample gexample = new GoodinfoExample();
		com.smartfactory.model.entity.GoodinfoExample.Criteria gcc=gexample.createCriteria();
		if(null != cond.getEqpname()&&!"".equals(cond.getEqpname())) {
			gcc.andEqpnameEqualTo(cond.getEqpname());
		}
		List<Goodinfo> glist=goodmapper.selectByExample(gexample);
		return new PageInfo<Goodinfo>(glist);
	}
	
}
