package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Member;
import com.lettucetech.me2.pojo.MemberKey;

import java.util.List;

public interface MemberService {
    int countByParams(Criteria example);

    Member selectByPrimaryKey(MemberKey key);

    List<Member> selectByParams(Criteria example);

    int deleteByPrimaryKey(MemberKey key);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Member record, Criteria example);

    int updateByParams(Member record, Criteria example);

    int insert(Member record);

    int insertSelective(Member record);
}