package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Mytask;
import java.util.List;

public interface MytaskService {
    int countByParams(Criteria example);

    Mytask selectByPrimaryKey(Integer id);

    List<Mytask> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mytask record);

    int updateByPrimaryKey(Mytask record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Mytask record, Criteria example);

    int updateByParams(Mytask record, Criteria example);

    int insert(Mytask record);

    int insertSelective(Mytask record);
}