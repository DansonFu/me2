package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Present;
import java.util.List;

public interface PresentService {
    int countByParams(Criteria example);

    Present selectByPrimaryKey(Integer id);

    List<Present> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Present record);

    int updateByPrimaryKey(Present record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Present record, Criteria example);

    int updateByParams(Present record, Criteria example);

    int insert(Present record);

    int insertSelective(Present record);
}