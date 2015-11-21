package com.lettucetech.me2.service;

import com.lettucetech.me2.pojo.Card;
import com.lettucetech.me2.pojo.Criteria;
import java.util.List;

public interface CardService {
    int countByParams(Criteria example);

    Card selectByPrimaryKey(Integer cardId);

    List<Card> selectByParams(Criteria example);

    int deleteByPrimaryKey(Integer cardId);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);

    int deleteByParams(Criteria example);

    int updateByParamsSelective(Card record, Criteria example);

    int updateByParams(Card record, Criteria example);

    int insert(Card record);

    int insertSelective(Card record);
}