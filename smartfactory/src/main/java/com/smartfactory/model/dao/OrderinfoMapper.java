package com.smartfactory.model.dao;

import com.smartfactory.model.entity.Orderinfo;
import com.smartfactory.model.entity.OrderinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    long countByExample(OrderinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int deleteByExample(OrderinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int deleteByPrimaryKey(Integer orderid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int insert(Orderinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int insertSelective(Orderinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    List<Orderinfo> selectByExample(OrderinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    Orderinfo selectByPrimaryKey(Integer orderid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByExampleSelective(@Param("record") Orderinfo record, @Param("example") OrderinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByExample(@Param("record") Orderinfo record, @Param("example") OrderinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByPrimaryKeySelective(Orderinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orderinfo
     *
     * @mbg.generated Sun Jul 26 09:40:03 CST 2020
     */
    int updateByPrimaryKey(Orderinfo record);
}