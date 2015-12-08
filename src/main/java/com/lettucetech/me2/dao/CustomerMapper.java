package com.lettucetech.me2.dao;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer customerId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Customer record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Customer record);

    /**
     * 根据条件查询记录集
     */
    List<Customer> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Customer selectByPrimaryKey(Integer customerId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Customer record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Customer record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Customer record);

	List<Customer> selectByPhoneOrUsername(Criteria example);

	Customer selectByParams4Rand(Criteria example);
}