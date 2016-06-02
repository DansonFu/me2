package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Privilege;
import java.util.List;

public interface PrivilegeService {
    int countByParams(Criteria example);

    Privilege selectByPrimaryKey(Integer id);

    List<Privilege> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Privilege record, Criteria example);

    int updateByParams(Privilege record, Criteria example);

    int insert(Privilege record);

    int insertSelective(Privilege record);
}