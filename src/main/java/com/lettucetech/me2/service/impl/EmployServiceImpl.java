package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.EmployMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Employ;
import com.lettucetech.me2.pojo.EmployKey;
import com.lettucetech.me2.service.EmployService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl implements EmployService {
    @Autowired
    private EmployMapper employMapper;

    private static final Logger logger = LoggerFactory.getLogger(EmployServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.employMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Employ selectByPrimaryKey(EmployKey key) {
        return this.employMapper.selectByPrimaryKey(key);
    }

    public List<Employ> selectByParams(Criteria example) {
        return this.employMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(EmployKey key) {
        return this.employMapper.deleteByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(Employ record) {
        return this.employMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Employ record) {
        return this.employMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.employMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Employ record, Criteria example) {
        return this.employMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Employ record, Criteria example) {
        return this.employMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Employ record) {
        return this.employMapper.insert(record);
    }

    public int insertSelective(Employ record) {
        return this.employMapper.insertSelective(record);
    }
}