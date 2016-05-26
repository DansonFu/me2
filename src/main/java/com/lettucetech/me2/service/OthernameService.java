package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Othername;
import java.util.List;

public interface OthernameService {
    int countByParams(Criteria example);

    Othername selectByPrimaryKey(Integer id);

    List<Othername> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Othername record);

    int updateByPrimaryKey(Othername record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Othername record, Criteria example);

    int updateByParams(Othername record, Criteria example);

    int insert(Othername record);

    int insertSelective(Othername record);
}