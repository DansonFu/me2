package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Credit;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface CreditService {
    int countByParams(Criteria example);

    Credit selectByPrimaryKey(Integer creditId);

    List<Credit> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer creditId);

    int updateByPrimaryKeySelective(Credit record);

    int updateByPrimaryKey(Credit record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Credit record, Criteria example);

    int updateByParams(Credit record, Criteria example);

    int insert(Credit record);

    int insertSelective(Credit record);
}