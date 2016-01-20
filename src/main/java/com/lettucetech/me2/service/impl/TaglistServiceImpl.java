package com.lettucetech.me2.service.impl;

import com.lettucetech.me2.dao.TaglistMapper;
import com.lettucetech.me2.pojo.Criteria;
import com.lettucetech.me2.pojo.Taglist;
import com.lettucetech.me2.service.TaglistService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaglistServiceImpl implements TaglistService {
    @Autowired
    private TaglistMapper taglistMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaglistServiceImpl.class);

    public int countByParams(Criteria example) {
        int count = this.taglistMapper.countByParams(example);
        logger.debug("count: {}", count);
        return count;
    }

    public Taglist selectByPrimaryKey(Integer id) {
        return this.taglistMapper.selectByPrimaryKey(id);
    }

    public List<Taglist> selectByParams(Criteria example) {
        return this.taglistMapper.selectByParams(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.taglistMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Taglist record) {
        return this.taglistMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Taglist record) {
        return this.taglistMapper.updateByPrimaryKey(record);
    }

    public int deleteByParams(Criteria example) {
        return this.taglistMapper.deleteByParams(example);
    }

    public int updateByParamsSelective(Taglist record, Criteria example) {
        return this.taglistMapper.updateByParamsSelective(record, example.getCondition());
    }

    public int updateByParams(Taglist record, Criteria example) {
        return this.taglistMapper.updateByParams(record, example.getCondition());
    }

    public int insert(Taglist record) {
        return this.taglistMapper.insert(record);
    }

    public int insertSelective(Taglist record) {
        return this.taglistMapper.insertSelective(record);
    }
}