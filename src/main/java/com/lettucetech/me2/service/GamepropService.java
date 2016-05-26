package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gameprop;
import java.util.List;

public interface GamepropService {
    int countByParams(Criteria example);

    Gameprop selectByPrimaryKey(Integer id);

    List<Gameprop> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gameprop record);

    int updateByPrimaryKey(Gameprop record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Gameprop record, Criteria example);

    int updateByParams(Gameprop record, Criteria example);

    int insert(Gameprop record);

    int insertSelective(Gameprop record);
}