package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Tagsconnection;
import java.util.List;

public interface TagsconnectionService {
    int countByParams(Criteria example);

    Tagsconnection selectByPrimaryKey(Integer id);

    List<Tagsconnection> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tagsconnection record);

    int updateByPrimaryKey(Tagsconnection record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Tagsconnection record, Criteria example);

    int updateByParams(Tagsconnection record, Criteria example);

    int insert(Tagsconnection record);

    int insertSelective(Tagsconnection record);
}