package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Taglist;
import java.util.List;

public interface TaglistService {
    int countByParams(Criteria example);

    Taglist selectByPrimaryKey(Integer id);

    List<Taglist> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Taglist record);

    int updateByPrimaryKey(Taglist record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Taglist record, Criteria example);

    int updateByParams(Taglist record, Criteria example);

    int insert(Taglist record);

    int insertSelective(Taglist record);
}