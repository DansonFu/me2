package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GameMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Game;
import com.lettucetech.me2.service.GameService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameMapper gameMapper;

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gameMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Game selectByPrimaryKey(Integer gameId) {
        return this.gameMapper.selectByPrimaryKey(gameId);
    }

    public List<Game> selectByParams(Criteria example) {
        return this.gameMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer gameId) {
        return this.gameMapper.deleteByPrimaryKey(gameId);
    }

    public int updateByPrimaryKeySelective(Game record) {
        return this.gameMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Game record) {
        return this.gameMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gameMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Game record, Criteria example) {
        return this.gameMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Game record, Criteria example) {
        return this.gameMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Game record) {
        return this.gameMapper.insert(record);
    }

    public int insertSelective(Game record) {
        return this.gameMapper.insertSelective(record);
    }
}