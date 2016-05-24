package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Token;
import java.util.List;

public interface TokenService {
    int countByParams(Criteria example);

    Token selectByPrimaryKey(Integer id);

    List<Token> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Token record);

    int updateByPrimaryKey(Token record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Token record, Criteria example);

    int updateByParams(Token record, Criteria example);

    int insert(Token record);

    int insertSelective(Token record);
}