package com.smartfactory.model.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smartfactory.model.entity.Equipmentinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMainFrminfoService {
	@Autowired
      MainFrminfoService service;
	@Test
	public void testSearchEquipmentByEconditon() {
		List<Equipmentinfo> list= service.SearchEquipmentByEconditon("加工中");
		for(Equipmentinfo eq:list) {
			System.out.println(eq);
		}
		 int goodeqp=	service.SearchEquipmentByEconditon("加工中").size();
		 System.out.println(goodeqp);
		  int goodeqp1=service.SearchEquipmentByEconditon("待机").size();
		  System.out.println(goodeqp1);
		  int total=	service.SearchEquipmentByEconditon(null).size();
		  System.out.println(total);
		  double a=(double)(goodeqp+goodeqp1)/ total*100;
			String result=String.format("%.2f", a);
			System.out.println(result+"%");
	}
}
