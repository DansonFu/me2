package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.School;
import java.util.List;

public interface SchoolService {
    int countByParams(Criteria example);

    School selectByPrimaryKey(Integer id);

    List<School> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(School record, Criteria example);

    int updateByParams(School record, Criteria example);

    int insert(School record);

    int insertSelective(School record);
}