package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Prop;
import java.util.List;

public interface PropService {
    int countByParams(Criteria example);

    Prop selectByPrimaryKey(Integer id);

    List<Prop> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Prop record);

    int updateByPrimaryKey(Prop record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Prop record, Criteria example);

    int updateByParams(Prop record, Criteria example);

    int insert(Prop record);

    int insertSelective(Prop record);
}