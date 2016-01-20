package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gamecomment;
import java.util.List;

public interface GamecommentService {
    int countByParams(Criteria example);

    Gamecomment selectByPrimaryKey(Integer id);

    List<Gamecomment> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gamecomment record);

    int updateByPrimaryKey(Gamecomment record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Gamecomment record, Criteria example);

    int updateByParams(Gamecomment record, Criteria example);

    int insert(Gamecomment record);

    int insertSelective(Gamecomment record);
}