package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Grade;
import java.util.List;

public interface GradeService {
    int countByParams(Criteria example);

    Grade selectByPrimaryKey(Integer id);

    List<Grade> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Grade record, Criteria example);

    int updateByParams(Grade record, Criteria example);

    int insert(Grade record);

    int insertSelective(Grade record);
}