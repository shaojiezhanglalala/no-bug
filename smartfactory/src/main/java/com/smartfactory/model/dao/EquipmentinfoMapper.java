package com.smartfactory.model.dao;

import com.smartfactory.model.entity.Equipmentinfo;
import com.smartfactory.model.entity.EquipmentinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EquipmentinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    long countByExample(EquipmentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int deleteByExample(EquipmentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int deleteByPrimaryKey(Integer eqpid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int insert(Equipmentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int insertSelective(Equipmentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    List<Equipmentinfo> selectByExample(EquipmentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    Equipmentinfo selectByPrimaryKey(Integer eqpid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByExampleSelective(@Param("record") Equipmentinfo record, @Param("example") EquipmentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByExample(@Param("record") Equipmentinfo record, @Param("example") EquipmentinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByPrimaryKeySelective(Equipmentinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table equipmentinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByPrimaryKey(Equipmentinfo record);
}