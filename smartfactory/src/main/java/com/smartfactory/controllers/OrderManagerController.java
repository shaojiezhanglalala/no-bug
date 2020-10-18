package com.smartfactory.controllers;




import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;


import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Goodinfo;

import com.smartfactory.model.entity.Orderinfo;

import com.smartfactory.model.services.OrderManagerService;

@RestController
public class OrderManagerController {
	@Autowired
        OrderManagerService oservice;
    
	 /**
	  * 查询所有订单
	  * @return
	  */
	@PostMapping("/searchallorderformxxx")
	 public List<Orderinfo> searchAllOrderForm(){
		 return oservice.searchAllOrderForm();
	 }
	 /**
	  * 动态分页查询订单表
	  * @param cond
	  * @param pageNum
	  * @param pageSize
	  * @return
	  */
	@PostMapping("/searchorderinfoxxx")
	 public PageInfo<Orderinfo> searchOrderinfoxxx(Orderinfo cond ,int pageNum,int pageSize){
			return oservice.searchOrderinfoxxx(cond, pageNum, pageSize);		
		}
	 /**
	  *查询订单生产的产品的产能
	  * @param orderid
	  * @return 订单生产的产品的产能
	  */
	@PostMapping("/searchcapacitybyorderid")
	    public int searchCapacityByOrderid(int orderid) {
	    	return oservice.searchCapacityByOrderid(orderid);
	    }
	
	  /**
     * 修改订单状态
     * @param orderid
     * @param ofcond
     * @return  true修改成功 false返回失败
     */
	@PostMapping("/modorderconditionxxx")
    public boolean   modOrderConditionxxx(int orderid,String ofcond) {
    	return oservice.modOrderConditionxxx(orderid, ofcond);
    }
	 /**
     * 修改订单状态 并加备注
     * @param orderid
     * @param ofcond
     * @return  true修改成功 false返回失败
     */
	@PostMapping("/modorderconditionaddnotexxx")
    public boolean   modOrderConditionaddnotexxx(int orderid,String ofcond,String note) {
    	return oservice.modOrderConditionaddnotexxx(orderid, ofcond, note);
    }
	 /**
     * 完成订单 并加备注，并将订单的所有计划置为已完成
     * @param orderid
     * @param ofcond
     * @return  true修改成功 false返回失败
     */
	@PostMapping("/modorderfinishxxx")
	 public boolean   modOrderFinishxxx(int orderid,String ofcond,String note) {
	    	return oservice.modOrderFinishxxx(orderid, ofcond, note);
	    }
	  /**
     * 查找所有产品
     * @return
     */
	@PostMapping("/searchallgoodxxx")
    public List<Goodinfo> searchAllGood(){
    	return oservice.searchAllGood();
    }
	/**
     * 新建订单
     * @param orderinfo
     * @return
     */
	@PostMapping("/addneworderxxx")
    public boolean addNewOrderxxx(Orderinfo orderinfo) {
     return oservice.addNewOrderxxx(orderinfo);
    }
}
