package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.VoteMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Vote;
import com.lettucetech.me2.service.VoteService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteMapper voteMapper;

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.voteMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Vote selectByPrimaryKey(Integer id) {
        return this.voteMapper.selectByPrimaryKey(id);
    }

    public List<Vote> selectByParams(Criteria example) {
        return this.voteMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.voteMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Vote record) {
        return this.voteMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Vote record) {
        return this.voteMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.voteMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Vote record, Criteria example) {
        return this.voteMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Vote record, Criteria example) {
        return this.voteMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Vote record) {
        return this.voteMapper.insert(record);
    }

    public int insertSelective(Vote record) {
        return this.voteMapper.insertSelective(record);
    }
}