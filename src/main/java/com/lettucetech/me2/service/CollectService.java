package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Collect;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface CollectService {
    int countByParams(Criteria example);

    Collect selectByPrimaryKey(Integer id);

    List<Collect> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Collect record, Criteria example);

    int updateByParams(Collect record, Criteria example);

    int insert(Collect record);

    int insertSelective(Collect record);
}