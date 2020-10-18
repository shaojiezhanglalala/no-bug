package com.smartfactory.model.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smartfactory.model.dao.BforminfoMapper;
import com.smartfactory.model.dao.EquipmentinfoMapper;
import com.smartfactory.model.dao.ForminfoMapper;
import com.smartfactory.model.dao.OrderinfoMapper;
import com.smartfactory.model.entity.Bforminfo;
import com.smartfactory.model.entity.BforminfoExample;
import com.smartfactory.model.entity.BforminfoExample.Criteria;

import com.smartfactory.model.entity.Forminfo;
import com.smartfactory.model.entity.ForminfoExample;
import com.smartfactory.model.entity.Orderinfo;

@Service
public class ProductionTrackingService {

	@Autowired
         BforminfoMapper bfmapper;
	@Autowired
	     ForminfoMapper fmapper;
	@Autowired
	   	 EquipmentinfoMapper emapper;
	@Autowired
		OrderinfoMapper omapper;
	 		/**
	 		 *通过fid 分页查询所有报工单
	 		 * @param fid  工单id
	 		 * @param pageNum  当前页数
	 		 * @param pageSize    每一页的记录条数
	 		 * @return  
	 		 */
	      public PageInfo<Bforminfo> searchBforminfo(int fid,int pageNum,int pageSize) {
	    	   BforminfoExample example=new BforminfoExample();
	    	   Criteria c=example.createCriteria();
	    	   if(fid!=0) {
	    		   c.andFidEqualTo(fid);
	    	   }
	    	 //启动分页插件，
	   		PageHelper.startPage(pageNum, pageSize);
	   		//不要添加任何代码！
			//实时查询
	   		List<Bforminfo> list =bfmapper.selectByExample(example);
	    	//返回值ֵ
	   		return new PageInfo<Bforminfo>(list);
	    	   
	      }
	      /**
	       * 查询所有工单
	       * @return
	       */
	      public List<Forminfo> searchAllForm(){
	    	  ForminfoExample example=new ForminfoExample();
	    	  com.smartfactory.model.entity.ForminfoExample.Criteria c=example.createCriteria();
	    	  List<String> list=new ArrayList<>();
	    	  list.add("生产中");list.add("已完成");
	    	  c.andFcondIn(list);
	    	  return fmapper.selectByExample(example);
	      }
	      /**
	       *通过formid查询工单状态״̬
	       * @param fid  工单id
	       * @return    工单״̬
	       */
	      public Forminfo SearchFcontionByFid(int formid) {
	    	  Forminfo forminfo=  fmapper.selectByPrimaryKey(formid);
	    	 return forminfo;
	      }
	      /**不需要了，待删除
	       * 通过formid查询正在使用的设备
	       * @param formid
	       * @return 
	       */
//	      public List<Equipmentinfo> searchEqup(int formid){
//	    	  EquipmentinfoExample example=new EquipmentinfoExample();
//	    	  com.smartfactory.model.entity.EquipmentinfoExample.Criteria c=example.createCriteria();
//	    	  c.andFormidEqualTo(formid);
//	    	  List<Equipmentinfo> list=emapper.selectByExample(example);
//	    	  return list;
//	      }
	      /**
	       * 添加新报工单
	       * @param info
	       * @return
	       */
	      public boolean addNewBform(Bforminfo info) {
	    	   int fid=info.getFid();
	    	   int qnum1=info.getQnum();
	    	   int orderid=fmapper.selectByPrimaryKey(fid).getOrderid();
	    	   int num=omapper.selectByPrimaryKey(orderid).getComnum();
	    	   Orderinfo record=new Orderinfo();
	    	   Forminfo record1=new Forminfo();
	    	   record.setOrderid(orderid);
	    	   record.setComnum(num+qnum1);
	    	   record1.setFormid(fid);
	    	   record1.setComnum(num+qnum1);
	    	   omapper.updateByPrimaryKeySelective(record);
	    	   fmapper.updateByPrimaryKeySelective(record1);
	    	   String gname=omapper.selectByPrimaryKey(orderid).getGname();
	    	   info.setGname(gname);
	    	   int i=  bfmapper.insert(info);
	    	   return i>0;
	      }
	      /**
	       * 报工完成后修改订单状态
	       * @param formid
	       * @return
	       */
	      public boolean modFormCondition(int formid) {
	    	  Forminfo record=new Forminfo();
	    	  record.setFcond("已完成");
	    	  ForminfoExample example=new ForminfoExample();
	    	  com.smartfactory.model.entity.ForminfoExample.Criteria c=example.createCriteria();
	    	  c.andFormidEqualTo(formid);
	    	  int i=fmapper.updateByExampleSelective(record, example);
	    	  return i>0;
	      }
	      

}
