package com.smartfactory.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.Goodinfo;
import com.smartfactory.model.services.GoodManagerService;

@RestController
public class GoodManagerController {

	
	
	
	@Autowired
	private GoodManagerService service;
	
	/**
	 * 查询尚未与产品绑定的所有设备
	 * @return
	 */
	@RequestMapping("/searchequipmentnoused")
	public List<Equipmentinfo> searchEquipmentNoUsed(){
		return service.searchEquipmentNoUsed();
	}
	
	
	/**
	 * 修改用户
	 * @param good
	 */
	@RequestMapping("/modgood")
	public boolean doModGood(Goodinfo good) {
		return service.modGood(good);
	}
	
	/**
	 * 删除用户
	 * @param gid
	 * @return
	 */
	@RequestMapping("/delgood")
	public boolean doDelGoodById(int gid) {
		return service.delGoodById(gid);
	}
	
	/**
	 * 
	 * @param cond
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/searchgood")
	public PageInfo<Goodinfo> searchGoodinfo(Goodinfo cond, int pageNum, int pageSize){
		return service.searchGoodinfo(cond, pageNum, pageSize);
	}

	
	/**
	 * 添加商品
	 * @param good
	 * @return
	 */
	@RequestMapping("/addgname")
    public boolean addNewGood(Goodinfo good) {
        return service.addNewGood(good);
        
        }
    	
	
	/**
	 * 检查商品名可用
	 */
	@RequestMapping("/checkgname")
	public boolean doCheckName(Goodinfo good){
		return service.checkGoodname(good.getGname());
	}
	
	

	
	
	
}
