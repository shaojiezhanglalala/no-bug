package com.smartfactory.model.services;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;

import com.smartfactory.model.entity.Orderinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestOrderManagerService {
           @Autowired
           OrderManagerService oservice;
           
           @Test
           public void testsearchAllOrderForm() {
        	   List<Orderinfo> list=oservice.searchAllOrderForm();
        	   for(Orderinfo oi:list) {
        		   System.out.println(oi);
        	   }
           }
           @Test
           public void testsearchOrderinfoxxx() {
        	   Orderinfo cond=new Orderinfo();
        	   cond.setGname("一");
        	   cond.setOrderid(0);
//        	   cond.setOfcond("生产中");
        	   PageInfo<Orderinfo> list=oservice.searchOrderinfoxxx(cond, 1, 3);
        	   System.out.println(list);
        	   for(Orderinfo oi:list.getList()) {
        		   System.out.println(oi);
        	   }
           }
           @Test
           public void testsearchCapacityByOrderid() {
        	  int i= oservice.searchCapacityByOrderid(302);
        	  System.out.println(i);
           }
           @Test
           public void testmodOrderConditionxxx() {
        	   System.out.println(oservice.modOrderConditionxxx(301, "已完成"));
           }
           @Test
           public void testmodOrderConditionaddnotexxx() {
        	   System.out.println(oservice.modOrderConditionaddnotexxx(302, "已拒单", "产能不够"));
           }
           @Test
           public void testmodOrderFinishxxx() {
        	   System.out.println(oservice.modOrderFinishxxx(301, "已完成", "任务完成"));
           }
           @Test
           public void testsearchAllGood() {
        	 System.out.println(oservice.searchAllGood());
           }
           @Test
           public void testaddNewOrderxxx() {
        	   Orderinfo orderinfo=new Orderinfo();
        	   orderinfo.setGname("产品三");
        	   orderinfo.setGnum(600);
        	   orderinfo.setOendtime("2020-07-30");
        	   System.out.println(oservice.addNewOrderxxx(orderinfo));
           }
}
