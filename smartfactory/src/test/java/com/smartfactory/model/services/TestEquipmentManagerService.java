package com.smartfactory.model.services;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Equipmentinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestEquipmentManagerService {
	@Autowired
          EquipmentManagerService eservice;
//	@Test
//	public void testsearchGnameByEqpname() {
//		System.out.println(eservice.searchGnameByEqpname("设备一"));
//	}
//	@Test
//	public void testsearchEquipmentByGname1() {
//		PageInfo<Equipmentinfo> list=eservice.searchEquipmentByGname1("一", 1, 10);
//		for(Equipmentinfo ei:list.getList()) {
//			System.out.println(ei);
//		}
//	}
}
