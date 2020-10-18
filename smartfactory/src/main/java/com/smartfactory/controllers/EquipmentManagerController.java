package com.smartfactory.controllers;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.Goodinfo;

import com.smartfactory.model.services.EquipmentManagerService;

/**
 * 设备管理模块控制类
 * @author 17484
 *
 */
@RestController
public class EquipmentManagerController {
	
	@Autowired
	private EquipmentManagerService service;
	/**
	 * 新增设备
	 * @param eqp
	 * @return
	 */
	@RequestMapping("/addnewequipment")
	public boolean doAddNewEquipmentinfo(Equipmentinfo eqp) {
		return service.addNewEquipmentinfo(eqp);
	}
	/**
	 * 删除设备
	 * @param eqpid
	 * @return
	 */
	@RequestMapping("/deleteequipment")
	public boolean doDeleteEquipmentinfo(int eqpid) {
		return service.deleteEquipmentinfo(eqpid);
	}
	/**
	 * 修改设备
	 * @param eqp
	 * @return
	 */
	@RequestMapping("/modifyequipment")
	public boolean doModifyEquipmentinfo(Equipmentinfo eqp) {
		return service.modifyEquipmentinfo(eqp);
	}
	
//	}
	/**
	 * 设备查询
	 * @param cond
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/searchequipment")
	public PageInfo<Equipmentinfo> doSearchEquipment(Equipmentinfo cond, int pageNum, int pageSize){
		return service.searchEquipmentinfo(cond, pageNum, pageSize);
	}
	/**
	 * 通过产品名进行设备查询
	 * @param cond
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	
	@RequestMapping("/searchequipmentbygname")
	public PageInfo<Equipmentinfo> doSearchEquipmentByGname (Goodinfo cond, int pageNum, int pageSize) {
		return service.searchEquipmentByGname(cond, pageNum, pageSize);
	}
	/**
	 * 通过设备名查产品名
	 * @param eqpname
	 * @return
	 */
	@RequestMapping("/searchgnamebyeqpname")
	public PageInfo<Goodinfo> doSearchGnameByEqpname(Goodinfo cond, int pageNum, int pageSize) {
		return service.searchGnameByEqpname(cond,pageNum, pageSize);
	}
	
}