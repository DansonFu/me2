package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Employ;
import com.lettucetech.me2.pojo.EmployKey;

import java.util.List;

public interface EmployService {
    int countByParams(Criteria example);

    Employ selectByPrimaryKey(EmployKey key);

    List<Employ> selectByParams(Criteria example);

    int deleteByPrimaryKey(EmployKey key);

    int updateByPrimaryKeySelective(Employ record);

    int updateByPrimaryKey(Employ record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Employ record, Criteria example);

    int updateByParams(Employ record, Criteria example);

    int insert(Employ record);

    int insertSelective(Employ record);
}