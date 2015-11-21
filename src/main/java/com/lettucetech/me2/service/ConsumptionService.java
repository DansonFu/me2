package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Consumption;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface ConsumptionService {
    int countByParams(Criteria example);

    Consumption selectByPrimaryKey(Integer consumptionId);

    List<Consumption> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer consumptionId);

    int updateByPrimaryKeySelective(Consumption record);

    int updateByPrimaryKey(Consumption record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Consumption record, Criteria example);

    int updateByParams(Consumption record, Criteria example);

    int insert(Consumption record);

    int insertSelective(Consumption record);
}