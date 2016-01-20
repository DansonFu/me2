package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GamecommentMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gamecomment;
import com.lettucetech.me2.service.GamecommentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamecommentServiceImpl implements GamecommentService {
    @Autowired
    private GamecommentMapper gamecommentMapper;

    private static final Logger logger = LoggerFactory.getLogger(GamecommentServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gamecommentMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Gamecomment selectByPrimaryKey(Integer id) {
        return this.gamecommentMapper.selectByPrimaryKey(id);
    }

    public List<Gamecomment> selectByParams(Criteria example) {
        return this.gamecommentMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.gamecommentMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Gamecomment record) {
        return this.gamecommentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Gamecomment record) {
        return this.gamecommentMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gamecommentMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Gamecomment record, Criteria example) {
        return this.gamecommentMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Gamecomment record, Criteria example) {
        return this.gamecommentMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Gamecomment record) {
        return this.gamecommentMapper.insert(record);
    }

    public int insertSelective(Gamecomment record) {
        return this.gamecommentMapper.insertSelective(record);
    }
}