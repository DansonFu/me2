package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CardMapper;
import com.lettucetech.me2.pojo.Card;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CardService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardMapper cardMapper;

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.cardMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Card selectByPrimaryKey(Integer cardId) {
        return this.cardMapper.selectByPrimaryKey(cardId);
    }

    public List<Card> selectByParams(Criteria example) {
        return this.cardMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer cardId) {
        return this.cardMapper.deleteByPrimaryKey(cardId);
    }

    public int updateByPrimaryKeySelective(Card record) {
        return this.cardMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Card record) {
        return this.cardMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.cardMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Card record, Criteria example) {
        return this.cardMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Card record, Criteria example) {
        return this.cardMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Card record) {
        return this.cardMapper.insert(record);
    }

    public int insertSelective(Card record) {
        return this.cardMapper.insertSelective(record);
    }
}