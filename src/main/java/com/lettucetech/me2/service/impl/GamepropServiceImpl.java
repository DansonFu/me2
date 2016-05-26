package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GamepropMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gameprop;
import com.lettucetech.me2.service.GamepropService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamepropServiceImpl implements GamepropService {
    @Autowired
    private GamepropMapper gamepropMapper;

    private static final Logger logger = LoggerFactory.getLogger(GamepropServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gamepropMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Gameprop selectByPrimaryKey(Integer id) {
        return this.gamepropMapper.selectByPrimaryKey(id);
    }

    public List<Gameprop> selectByParams(Criteria example) {
        return this.gamepropMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.gamepropMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Gameprop record) {
        return this.gamepropMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Gameprop record) {
        return this.gamepropMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gamepropMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Gameprop record, Criteria example) {
        return this.gamepropMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Gameprop record, Criteria example) {
        return this.gamepropMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Gameprop record) {
        return this.gamepropMapper.insert(record);
    }

    public int insertSelective(Gameprop record) {
        return this.gamepropMapper.insertSelective(record);
    }
}