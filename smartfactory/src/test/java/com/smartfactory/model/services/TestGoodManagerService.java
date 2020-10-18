package com.smartfactory.model.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.Goodinfo;
import com.smartfactory.model.services.GoodManagerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestGoodManagerService {

	@Autowired
	private GoodManagerService service;
	
	@Test
	public void testsearchEquipmentNoUsed() {
		List<Equipmentinfo>list= service.searchEquipmentNoUsed();
		for(Equipmentinfo info:list) {
			System.out.println(info);
		}
	}
	
	
//	/**
//	 * 修改
//	 *
//	 */
//	@Test
//	public void testModGoodinfo() {
//		boolean b = service.modGood(new Goodinfo(616, "dfgh", "56", "4","5","1"));
//		System.out.println(b);
//	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDelGoodById() {
		System.out.println(service.delGoodById(603));
	}
	
	
	
	
	/**
	 * 查找商品
	 */
	@Test
	public void testSearchGoodinfo() {
		PageInfo<Goodinfo> pageinfo = service.searchGoodinfo(new Goodinfo(), 1, 5);
		System.out.println(pageinfo);
		for(Goodinfo good:pageinfo.getList()) {
			System.out.println(good);
		}
	}
	
//	/**
//	 * 添加商品
//	 */
//	@Test
//	public void testAddNewGood() {
//		boolean addNewGood = service.addNewGood(new Goodinfo(null, "shangpin21", "2", "3", "4","2"));
//		System.out.println(addNewGood);
//	}
	
	@Test
	public void testCheckGoodname() {
			System.out.println(service.checkGoodname("good234tr"));
	}
	
	
}
