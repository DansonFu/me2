package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.CustomerPrivilege;
import java.util.List;

public interface CustomerPrivilegeService {
    int countByParams(Criteria example);

    CustomerPrivilege selectByPrimaryKey(Integer id);

    List<CustomerPrivilege> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerPrivilege record);

    int updateByPrimaryKey(CustomerPrivilege record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(CustomerPrivilege record, Criteria example);

    int updateByParams(CustomerPrivilege record, Criteria example);

    int insert(CustomerPrivilege record);

    int insertSelective(CustomerPrivilege record);
}