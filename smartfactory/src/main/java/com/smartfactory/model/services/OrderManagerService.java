package com.smartfactory.model.services;


import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.dao.GoodinfoMapper;
import com.smartfactory.model.dao.OrderinfoMapper;
import com.smartfactory.model.dao.PlaninfoMapper;
import com.smartfactory.model.entity.EquipmentinfoExample;
import com.smartfactory.model.entity.EquipmentinfoExample.Criteria;
import com.smartfactory.model.entity.Goodinfo;
import com.smartfactory.model.entity.GoodinfoExample;
import com.smartfactory.model.entity.Orderinfo;
import com.smartfactory.model.entity.OrderinfoExample;
import com.smartfactory.model.entity.Planinfo;
import com.smartfactory.model.entity.PlaninfoExample;



@Service
public class OrderManagerService {
	 @Autowired     
		OrderinfoMapper omapper;
	 @Autowired
	  	GoodinfoMapper gmapper;
	 @Autowired
	 EquipmentinfoMapper emapper;
	 @Autowired
	 PlaninfoMapper pmapper;
	 /**
	  * 查询所有订单
	  * @return
	  */
	 public List<Orderinfo> searchAllOrderForm(){
		 List<Orderinfo> list=omapper.selectByExample(null);
		 return list;
	 }
	 /**
	  * 动态分页查询订单表
	  * @param cond
	  * @param pageNum
	  * @param pageSize
	  * @return
	  */
	 public PageInfo<Orderinfo> searchOrderinfoxxx(Orderinfo cond ,int pageNum,int pageSize){
			OrderinfoExample example=new OrderinfoExample();
			com.smartfactory.model.entity.OrderinfoExample.Criteria c=example.createCriteria();
			if(cond.getOrderid()!=0) {
				c.andOrderidEqualTo(cond.getOrderid());
			}
			if(cond.getOfcond()!=null && !cond.getOfcond().equals("")) {
				c.andOfcondEqualTo(cond.getOfcond());
			}
			if(cond.getGname()!=null&&!cond.getGname().equals("")) {
				c.andGnameLike("%"+cond.getGname()+"%");
			}
			//启动分页插件，
			PageHelper.startPage(pageNum, pageSize);
			//不要添加任何代码！
			//实时查询
			List<Orderinfo> list =omapper.selectByExample(example);
			//返回值
			return new PageInfo<Orderinfo>(list);
			
		}
	 /**
	  *查询订单生产的产品的产能
	  * @param orderid
	  * @return 订单生产的产品的产能
	  */
	    public int searchCapacityByOrderid(int orderid) {
	    	Orderinfo orderinfo=omapper.selectByPrimaryKey(orderid);
	    	int gid=orderinfo.getGid();
	    	//将订单内的截止日期转换成毫秒
	    	String endtimeString=orderinfo.getOendtime();
	    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
	    	try {
				Date endtime=sf.parse(endtimeString);
				long endtime1=endtime.getTime();
				//获得当前时间
		    	Date date = new Date();
		       long currenttime=date.getTime();
               int days=-1;
		       //获得两者的毫秒差
		    	if( endtime1>currenttime) {
		    		long time=endtime1- currenttime;
		    		days=(int)(time/86400000);
		    	}
		    	Goodinfo goodinfo=gmapper.selectByPrimaryKey(gid);
		    	//获得设备名称
		    	String eqpname=goodinfo.getEqpname();
		    	//获得产能
		    	int capacity=goodinfo.getGcap();
		    	EquipmentinfoExample example=new EquipmentinfoExample();
		    	Criteria c=example.createCriteria();
		    	c.andEqpcondEqualTo("停用");
		    	c.andEqpnameEqualTo(eqpname);
		    	int eqpnum= emapper.selectByExample(example).size();
		    	return capacity*eqpnum*days;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    	return -1;
	    }
	    /**
	     * 修改订单状态
	     * @param orderid
	     * @param ofcond
	     * @return  true修改成功 false返回失败
	     */
	    public boolean   modOrderConditionxxx(int orderid,String ofcond) {
	    	Orderinfo record=new Orderinfo();
	    	record.setOrderid(orderid);
	    	record.setOfcond(ofcond);
	    	int i=omapper.updateByPrimaryKeySelective(record);
	    	return i>0;
	    }
	    /**
	     * 修改订单状态 并加备注
	     * @param orderid
	     * @param ofcond
	     * @return  true修改成功 false返回失败
	     */
	    public boolean   modOrderConditionaddnotexxx(int orderid,String ofcond,String note) {
	    	Orderinfo record=new Orderinfo();
	    	record.setOrderid(orderid);
	    	record.setOfcond(ofcond);
	    	record.setNote(note);
	    	int i=omapper.updateByPrimaryKeySelective(record);
	    	return i>0;
	    }
	    /**
	     * 完成订单 并加备注，并将订单的所有计划置为已完成
	     * @param orderid
	     * @param ofcond
	     * @return  true修改成功 false返回失败
	     */
	    public boolean   modOrderFinishxxx(int orderid,String ofcond,String note) {
	    	//完成订单 并加备注
	    	Orderinfo record=new Orderinfo();
	    	record.setOrderid(orderid);
	    	record.setOfcond(ofcond);
	    	record.setNote(note);
	    	int i=omapper.updateByPrimaryKeySelective(record);
	    	//将订单的所有计划置为已完成
	    	Planinfo record1=new Planinfo();
	    	record1.setPcond("已完成");
	    	PlaninfoExample example= new PlaninfoExample();
	    	com.smartfactory.model.entity.PlaninfoExample.Criteria c=example.createCriteria();
	    	c.andOrderidEqualTo(orderid);
	    	pmapper.updateByExampleSelective(record1, example);
	    	return i>0;
	    }
	    /**
	     * 查找所有产品
	     * @return
	     */
	    public List<Goodinfo> searchAllGood(){
	    	return gmapper.selectByExample(null);
	    }
	    /**
	     * 新建订单
	     * @param orderinfo
	     * @return
	     */
	    public boolean addNewOrderxxx(Orderinfo orderinfo) {
	    	if(orderinfo.getOfsou()==null || "".equals(orderinfo.getOfsou())) {
	    		orderinfo.setOfsou("线下");
	    	}
	    	orderinfo.setOrderid(0);
	    	orderinfo.setOfcond("待接单");
	    	GoodinfoExample example=new GoodinfoExample();
	    	com.smartfactory.model.entity.GoodinfoExample.Criteria c=example.createCriteria();
	    	c.andGnameEqualTo(orderinfo.getGname());
	    	List<Goodinfo> list=gmapper.selectByExample(example);
	    	orderinfo.setGid(list.get(0).getGid());
	    	orderinfo.setComnum(0);
	    	String timeStr1=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    	orderinfo.setOstarttime(timeStr1);
	    	int i= omapper.insertSelective(orderinfo);
	    	 return i>0;
	    }
}
