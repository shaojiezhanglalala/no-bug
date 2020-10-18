package com.smartfactory.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.EquipmentinfoExample;
import com.smartfactory.model.entity.EquipmentinfoExample.Criteria;

@Service
public class MainFrminfoService {
	@Autowired
       EquipmentinfoMapper emapper;
	/**
	 * 通过设备状态查找设备
	 * @param condition
	 * @return
	 */
	public List<Equipmentinfo> SearchEquipmentByEconditon(String condition){
		if(condition==null) {
			List<Equipmentinfo> list=emapper.selectByExample(null);
			return list;
		}
		EquipmentinfoExample example=new EquipmentinfoExample();
		Criteria c=example.createCriteria();
		c.andEqpcondEqualTo(condition);
		List<Equipmentinfo> list=emapper.selectByExample(example);
		return list;
	}

}
