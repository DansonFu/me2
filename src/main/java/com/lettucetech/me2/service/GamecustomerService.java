package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gamecustomer;
import java.util.List;

public interface GamecustomerService {
    int countByParams(Criteria example);

    Gamecustomer selectByPrimaryKey(Integer id);

    List<Gamecustomer> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gamecustomer record);

    int updateByPrimaryKey(Gamecustomer record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Gamecustomer record, Criteria example);

    int updateByParams(Gamecustomer record, Criteria example);

    int insert(Gamecustomer record);

    int insertSelective(Gamecustomer record);
}