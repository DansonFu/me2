package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GamefaceMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gameface;
import com.lettucetech.me2.service.GamefaceService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamefaceServiceImpl implements GamefaceService {
    @Autowired
    private GamefaceMapper gamefaceMapper;

    private static final Logger logger = LoggerFactory.getLogger(GamefaceServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gamefaceMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Gameface selectByPrimaryKey(Integer id) {
        return this.gamefaceMapper.selectByPrimaryKey(id);
    }

    public List<Gameface> selectByParams(Criteria example) {
        return this.gamefaceMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.gamefaceMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Gameface record) {
        return this.gamefaceMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Gameface record) {
        return this.gamefaceMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gamefaceMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Gameface record, Criteria example) {
        return this.gamefaceMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Gameface record, Criteria example) {
        return this.gamefaceMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Gameface record) {
        return this.gamefaceMapper.insert(record);
    }

    public int insertSelective(Gameface record) {
        return this.gamefaceMapper.insertSelective(record);
    }
}