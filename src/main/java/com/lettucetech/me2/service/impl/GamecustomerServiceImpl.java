package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.GamecustomerMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Gamecustomer;
import com.lettucetech.me2.service.GamecustomerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamecustomerServiceImpl implements GamecustomerService {
    @Autowired
    private GamecustomerMapper gamecustomerMapper;

    private static final Logger logger = LoggerFactory.getLogger(GamecustomerServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.gamecustomerMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Gamecustomer selectByPrimaryKey(Integer id) {
        return this.gamecustomerMapper.selectByPrimaryKey(id);
    }

    public List<Gamecustomer> selectByParams(Criteria example) {
        return this.gamecustomerMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.gamecustomerMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Gamecustomer record) {
        return this.gamecustomerMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Gamecustomer record) {
        return this.gamecustomerMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.gamecustomerMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Gamecustomer record, Criteria example) {
        return this.gamecustomerMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Gamecustomer record, Criteria example) {
        return this.gamecustomerMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Gamecustomer record) {
        return this.gamecustomerMapper.insert(record);
    }

    public int insertSelective(Gamecustomer record) {
        return this.gamecustomerMapper.insertSelective(record);
    }
}