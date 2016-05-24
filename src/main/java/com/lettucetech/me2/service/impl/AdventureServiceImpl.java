package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.AdventureMapper;
import com.lettucetech.me2.pojo.Adventure;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.AdventureService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdventureServiceImpl implements AdventureService {
    @Autowired
    private AdventureMapper adventureMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdventureServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.adventureMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Adventure selectByPrimaryKey(Integer id) {
        return this.adventureMapper.selectByPrimaryKey(id);
    }

    public List<Adventure> selectByParams(Criteria example) {
        return this.adventureMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.adventureMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Adventure record) {
        return this.adventureMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Adventure record) {
        return this.adventureMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.adventureMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Adventure record, Criteria example) {
        return this.adventureMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Adventure record, Criteria example) {
        return this.adventureMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Adventure record) {
        return this.adventureMapper.insert(record);
    }

    public int insertSelective(Adventure record) {
        return this.adventureMapper.insertSelective(record);
    }
}