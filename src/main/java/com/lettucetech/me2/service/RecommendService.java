package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Recommend;
import com.lettucetech.me2.pojo.Tagshot;

import java.util.List;

public interface RecommendService {
    int countByParams(Criteria example);

    Recommend selectByPrimaryKey(Integer id);

    List<Recommend> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Recommend record, Criteria example);

    int updateByParams(Recommend record, Criteria example);

    int insert(Recommend record);

    int insertSelective(Recommend record);
    List<Recommend> selectByParams4Matching(Criteria example);
}