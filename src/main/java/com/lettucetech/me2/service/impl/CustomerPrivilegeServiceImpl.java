package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CustomerPrivilegeMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.CustomerPrivilege;
import com.lettucetech.me2.service.CustomerPrivilegeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPrivilegeServiceImpl implements CustomerPrivilegeService {
    @Autowired
    private CustomerPrivilegeMapper customerPrivilegeMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomerPrivilegeServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.customerPrivilegeMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public CustomerPrivilege selectByPrimaryKey(Integer id) {
        return this.customerPrivilegeMapper.selectByPrimaryKey(id);
    }

    public List<CustomerPrivilege> selectByParams(Criteria example) {
        return this.customerPrivilegeMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.customerPrivilegeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(CustomerPrivilege record) {
        return this.customerPrivilegeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CustomerPrivilege record) {
        return this.customerPrivilegeMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.customerPrivilegeMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(CustomerPrivilege record, Criteria example) {
        return this.customerPrivilegeMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(CustomerPrivilege record, Criteria example) {
        return this.customerPrivilegeMapper.updateByParams(record, example.getCondition());
    }

    public int insert(CustomerPrivilege record) {
        return this.customerPrivilegeMapper.insert(record);
    }

    public int insertSelective(CustomerPrivilege record) {
        return this.customerPrivilegeMapper.insertSelective(record);
    }
}