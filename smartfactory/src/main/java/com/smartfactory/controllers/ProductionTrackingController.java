package com.smartfactory.controllers;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Bforminfo;

import com.smartfactory.model.entity.Forminfo;
import com.smartfactory.model.services.ProductionTrackingService;

@RestController
public class ProductionTrackingController {
	@Autowired
	 ProductionTrackingService service;
	/**
		 *通过fid 分页查询所有报工单
		 * @param fid  工单id
		 * @param pageNum  当前页数
		 * @param pageSize    每一页的记录条数
		 * @return  
		 */
	@PostMapping("/searchbforminfo")
	 public PageInfo<Bforminfo> searchBforminfo(int fid,int pageNum,int pageSize){
		 return service.searchBforminfo(fid, pageNum, pageSize);
	 }
	 /**
     * 查询所有工单
     * @return
     */
	@PostMapping("/searchallform")
	public List<Forminfo> searchAllForm(){
		return service.searchAllForm();
	}
	 /**
     *通过fid查询工单状态״̬
     * @param fid  工单id
     * @return    工单状态״̬
     */
	@PostMapping("/searchfconditionbyfid")
	public Forminfo SearchFcontionByFid(int formid) {
		return service.SearchFcontionByFid(formid);
	}
//	/**
//	 * 查设备
//	 * 待删除
//	 * @param formid
//	 * @return
//	 */
//	@RequestMapping("/searchequp")
//	public List<Equipmentinfo> searchEqup(int formid) {
//		return service.searchEqup(formid);
//		}
	/**
	 * 进行报工
	 * @param info
	 * @return
	 */
	@PostMapping("/addnewbform")
	public boolean addNewBform(Bforminfo info) {
		return service.addNewBform(info);
	}
	/**
	 * 改变工单状态
	 * @param formid
	 * @return
	 */
	@PostMapping("/modformcondition")
	 public boolean modFormCondition(int formid) {
		 return service.modFormCondition(formid);
	 }
}
