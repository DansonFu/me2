package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Adventure;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface AdventureService {
    int countByParams(Criteria example);

    Adventure selectByPrimaryKey(Integer id);

    List<Adventure> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Adventure record);

    int updateByPrimaryKey(Adventure record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Adventure record, Criteria example);

    int updateByParams(Adventure record, Criteria example);

    int insert(Adventure record);

    int insertSelective(Adventure record);
}