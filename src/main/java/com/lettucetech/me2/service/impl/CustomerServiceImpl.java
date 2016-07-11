package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CustomerMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Customer;
import com.lettucetech.me2.service.CustomerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.customerMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Customer selectByPrimaryKey(Integer customerId) {
        return this.customerMapper.selectByPrimaryKey(customerId);
    }

    public List<Customer> selectByParams(Criteria example) {
        return this.customerMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer customerId) {
        return this.customerMapper.deleteByPrimaryKey(customerId);
    }

    public int updateByPrimaryKeySelective(Customer record) {
        return this.customerMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Customer record) {
        return this.customerMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.customerMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Customer record, Criteria example) {
        return this.customerMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Customer record, Criteria example) {
        return this.customerMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Customer record) {
        return this.customerMapper.insert(record);
    }

    public int insertSelective(Customer record) {
        return this.customerMapper.insertSelective(record);
    }

    public int insertSelect(Customer record) {
        return this.customerMapper.insertSelect(record);
    }
    
	public List<Customer> selectByPhoneOrUsername(Criteria example) {
		return this.customerMapper.selectByPhoneOrUsername(example);
	}

	public Customer selectByParams4Rand(Criteria example) {
		return this.customerMapper.selectByParams4Rand(example);
	}

	public List<Customer> selectByParams4at(Criteria example) {
		return this.customerMapper.selectByParams4at(example);
	}
	public Customer selectByParamsPeopleSearch(Criteria example) {
		return this.customerMapper.selectByParamsPeopleSearch(example);
	}
}