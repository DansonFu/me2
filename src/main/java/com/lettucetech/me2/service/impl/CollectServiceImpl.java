package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.CollectMapper;
import com.lettucetech.me2.pojo.Collect;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.service.CollectService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CollectServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.collectMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Collect selectByPrimaryKey(Integer id) {
        return this.collectMapper.selectByPrimaryKey(id);
    }

    public List<Collect> selectByParams(Criteria example) {
        return this.collectMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.collectMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Collect record) {
        return this.collectMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Collect record) {
        return this.collectMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.collectMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Collect record, Criteria example) {
        return this.collectMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Collect record, Criteria example) {
        return this.collectMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Collect record) {
        return this.collectMapper.insert(record);
    }

    public int insertSelective(Collect record) {
        return this.collectMapper.insertSelective(record);
    }
}