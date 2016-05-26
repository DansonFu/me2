package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.OthernameMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Othername;
import com.lettucetech.me2.service.OthernameService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OthernameServiceImpl implements OthernameService {
    @Autowired
    private OthernameMapper othernameMapper;

    private static final Logger logger = LoggerFactory.getLogger(OthernameServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.othernameMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Othername selectByPrimaryKey(Integer id) {
        return this.othernameMapper.selectByPrimaryKey(id);
    }

    public List<Othername> selectByParams(Criteria example) {
        return this.othernameMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.othernameMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Othername record) {
        return this.othernameMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Othername record) {
        return this.othernameMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.othernameMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Othername record, Criteria example) {
        return this.othernameMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Othername record, Criteria example) {
        return this.othernameMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Othername record) {
        return this.othernameMapper.insert(record);
    }

    public int insertSelective(Othername record) {
        return this.othernameMapper.insertSelective(record);
    }
}