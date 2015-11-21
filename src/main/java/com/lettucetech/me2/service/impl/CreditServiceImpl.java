package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CreditMapper;
import com.lettucetech.me2.pojo.Credit;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CreditService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl implements CreditService {
    @Autowired
    private CreditMapper creditMapper;

    private static final Logger logger = LoggerFactory.getLogger(CreditServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.creditMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Credit selectByPrimaryKey(Integer creditId) {
        return this.creditMapper.selectByPrimaryKey(creditId);
    }

    public List<Credit> selectByParams(Criteria example) {
        return this.creditMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer creditId) {
        return this.creditMapper.deleteByPrimaryKey(creditId);
    }

    public int updateByPrimaryKeySelective(Credit record) {
        return this.creditMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Credit record) {
        return this.creditMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.creditMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Credit record, Criteria example) {
        return this.creditMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Credit record, Criteria example) {
        return this.creditMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Credit record) {
        return this.creditMapper.insert(record);
    }

    public int insertSelective(Credit record) {
        return this.creditMapper.insertSelective(record);
    }
}