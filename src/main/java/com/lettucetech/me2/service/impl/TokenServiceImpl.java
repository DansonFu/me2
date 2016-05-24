package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TokenMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Token;
import com.lettucetech.me2.service.TokenService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tokenMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Token selectByPrimaryKey(Integer id) {
        return this.tokenMapper.selectByPrimaryKey(id);
    }

    public List<Token> selectByParams(Criteria example) {
        return this.tokenMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.tokenMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Token record) {
        return this.tokenMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Token record) {
        return this.tokenMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.tokenMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Token record, Criteria example) {
        return this.tokenMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Token record, Criteria example) {
        return this.tokenMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Token record) {
        return this.tokenMapper.insert(record);
    }

    public int insertSelective(Token record) {
        return this.tokenMapper.insertSelective(record);
    }
}