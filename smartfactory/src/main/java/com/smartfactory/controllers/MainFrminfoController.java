package com.smartfactory.controllers;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.services.MainFrminfoService;

@RestController
public class MainFrminfoController {
	@Autowired
    MainFrminfoService mservice;
	/**
	 * 通过设备状态查询设备
	 * @param condition
	 * @return
	 */
	@RequestMapping("/searchequipmentbyecondition")
	public List<Equipmentinfo> SearchEquipmentByEconditon(String condition){
		return mservice.SearchEquipmentByEconditon(condition);
	}
	/**
	 * 计算开机率
	 * @return
	 */
	@RequestMapping("/goodeqpproportio")
    public String GoodEqpProportion() {
	  int goodeqp=	mservice.SearchEquipmentByEconditon("加工中").size();
	  int goodeqp1=mservice.SearchEquipmentByEconditon("待机").size();
	  int total=	mservice.SearchEquipmentByEconditon(null).size();
	  double a=(double)(goodeqp+goodeqp1)/ total*100;
		String result=String.format("%.2f", a);
		return result+"%";
	}
	/**
	 * 计算故障率
	 * @return
	 */
	@RequestMapping("/badeqpproportion")
	public String BadEqpProportion() {
		 int badeqp=	mservice.SearchEquipmentByEconditon("停机").size();
		 int total=	mservice.SearchEquipmentByEconditon(null).size();
		 double a=(double)badeqp/ total*100;
			String result=String.format("%.2f", a);
			return result+"%";
	}
	/**
	 * 计算运行率
	 * @return
	 */
	@RequestMapping("/workeqpproportion")
	public String WorkEqpProportion() {
		int badeqp=	mservice.SearchEquipmentByEconditon("加工中").size();
		 int total=	mservice.SearchEquipmentByEconditon(null).size();
		 double a=(double)badeqp/ total*100;
			String result=String.format("%.2f", a);
			return result+"%";
	}
	/**
	 * 计算综合效率
	 * @return
	 */
	@RequestMapping("/averageeqpproportion")
	public String AverageEqpProportion() {
		int badeqp=	mservice.SearchEquipmentByEconditon("加工中").size();
		 int total=badeqp+	mservice.SearchEquipmentByEconditon("待机").size();
		 double a=(double)badeqp/ total*100;
			String result=String.format("%.2f", a);
			return result+"%";
	}
}
