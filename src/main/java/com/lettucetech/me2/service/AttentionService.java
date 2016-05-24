package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Attention;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface AttentionService {
    int countByParams(Criteria example);

    Attention selectByPrimaryKey(Integer id);

    List<Attention> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attention record);

    int updateByPrimaryKey(Attention record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Attention record, Criteria example);

    int updateByParams(Attention record, Criteria example);

    int insert(Attention record);

    int insertSelective(Attention record);
}