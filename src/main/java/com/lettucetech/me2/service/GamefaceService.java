package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gameface;
import java.util.List;

public interface GamefaceService {
    int countByParams(Criteria example);

    Gameface selectByPrimaryKey(Integer id);

    List<Gameface> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gameface record);

    int updateByPrimaryKey(Gameface record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Gameface record, Criteria example);

    int updateByParams(Gameface record, Criteria example);

    int insert(Gameface record);

    int insertSelective(Gameface record);
}