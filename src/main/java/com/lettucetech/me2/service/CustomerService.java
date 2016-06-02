package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import java.util.List;

public interface CustomerService {
    int countByParams(Criteria example);

    Customer selectByPrimaryKey(Integer customerId);

    List<Customer> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Customer record, Criteria example);

    int updateByParams(Customer record, Criteria example);

    int insert(Customer record);

    int insertSelective(Customer record);
    
    List<Customer> selectByPhoneOrUsername(Criteria example);

	Customer selectByParams4Rand(Criteria example);

	List<Customer> selectByParams4at(Criteria example);
	
	Customer selectByParamsPeopleSearch(Criteria example);
}