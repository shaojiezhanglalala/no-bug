package com.smartfactory.model.services;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;

import com.smartfactory.model.entity.Bforminfo;
import com.smartfactory.model.entity.Forminfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestProductionTrackingService {
	@Autowired
	ProductionTrackingService service;
	
	@Test
	public void testSearchBforminfo() {
		PageInfo<Bforminfo>list=service.searchBforminfo(501, 1, 3);
		System.out.println(list);
		for(Bforminfo info:list.getList()) {
			System.out.println(info);
		}
	}
	@Test
	public void testsearchAllForm() {
		List<Forminfo> list=service.searchAllForm();
		System.out.println(list);
	}
	@Test
	public void testSearchFcontionByFid() {
		System.out.println(service.SearchFcontionByFid(501));
	}
//	@Test
//	public void testsearchEqup() {
//		System.out.println(service.searchEqup(501));
//	}
	@Test
	public void testaddNewBform() {
		Bforminfo info=new Bforminfo(0, 501, null, 100, 98, "2020-07-24 07:21", "2020-07-24 19:21", 0, null);
		System.out.println(service.addNewBform(info));
	}
	@Test
	public void testmodFormCondition() {
		System.out.println(service.modFormCondition(501));
	}
	

}
