package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Vote;
import java.util.List;

public interface VoteService {
    int countByParams(Criteria example);

    Vote selectByPrimaryKey(Integer id);

    List<Vote> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Vote record, Criteria example);

    int updateByParams(Vote record, Criteria example);

    int insert(Vote record);

    int insertSelective(Vote record);
}