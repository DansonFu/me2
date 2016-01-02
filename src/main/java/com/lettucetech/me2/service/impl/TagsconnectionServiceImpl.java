package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TagsconnectionMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Tagsconnection;
import com.lettucetech.me2.service.TagsconnectionService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsconnectionServiceImpl implements TagsconnectionService {
    @Autowired
    private TagsconnectionMapper tagsconnectionMapper;

    private static final Logger logger = LoggerFactory.getLogger(TagsconnectionServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.tagsconnectionMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Tagsconnection selectByPrimaryKey(Integer id) {
        return this.tagsconnectionMapper.selectByPrimaryKey(id);
    }

    public List<Tagsconnection> selectByParams(Criteria example) {
        return this.tagsconnectionMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.tagsconnectionMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Tagsconnection record) {
        return this.tagsconnectionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Tagsconnection record) {
        return this.tagsconnectionMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.tagsconnectionMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Tagsconnection record, Criteria example) {
        return this.tagsconnectionMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Tagsconnection record, Criteria example) {
        return this.tagsconnectionMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Tagsconnection record) {
        return this.tagsconnectionMapper.insert(record);
    }

    public int insertSelective(Tagsconnection record) {
        return this.tagsconnectionMapper.insertSelective(record);
    }
}